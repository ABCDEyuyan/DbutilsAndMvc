package com.zl.mvc.view;


import com.zl.mvc.util.StreamUtils;
import com.zl.mvc.util.StringUtils;
import com.zl.mvc.util.StreamUtils;
import com.zl.mvc.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 此类是一个响应文件数据给前端的ViewResult，通常用在文件下载以及显示图片等，比如
 * <pre class="code">
 *     <img src="http://localhost:8080/file/download?filename=a.jpg"/>
 * </pre>
 * @see StreamViewResult
 * @see com.zl.mvc.ViewResult
 * @see com.zl.mvc.handler.HandlerHelper
 */
public class FileViewResult extends StreamViewResult {

    /**
     * realPath指的文件的物理路径,比如D:/downloads/1.jpg
     */
    private String realPath;
    private String filename;

    public FileViewResult(String realPath) {
        this(realPath, new HashMap<>());
    }

    public FileViewResult(String realPath, Map<String,String> headers) {
        super(StreamUtils.getInputStreamFromRealPath(realPath));
        this.realPath = realPath;
        this.filename = StringUtils.getFilename(realPath);
    }

    @Override
    protected void writeContentType(HttpServletResponse resp) throws Exception {
        resp.setContentType(getMediaType(this.filename));
    }

    @Override
    protected void writeHeaders(HttpServletResponse resp) throws Exception {
        //attachment表示以附件的形式下载，对文件名编码以防止中文文件名在保存对话框中是乱码的
        resp.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        super.writeHeaders(resp);
    }

    protected String getMediaType(String filename) {
        //guessContentTypeFromName是从文件名猜测其内容类型，如果为null就猜测失败
        String midiaType = URLConnection.guessContentTypeFromName(filename);
        if (midiaType == null) {
            midiaType = StreamUtils.APPLICATION_OCTET_STREAM_VALUE;
        }
        return midiaType;
    }
}

