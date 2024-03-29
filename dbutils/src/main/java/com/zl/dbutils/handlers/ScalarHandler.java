package com.zl.dbutils.handlers;

import com.zl.dbutils.ResultSetHandler;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScalarHandler <T> implements ResultSetHandler<T> {
   private final int columnIndex;
   private final String columnName;

    public ScalarHandler() {
        this(1, null);
    }

    public ScalarHandler(int columnIndex) {
        this(columnIndex, null);
    }

    public ScalarHandler(String columnName) {
        this(1, columnName);
    }

    /**
     * 是取第一行的某一列的数据
     * 所以这里传2个参数是没有意义
     * @param columnIndex
     * @param columnName
     */
    private ScalarHandler(int columnIndex, String columnName) {
        this.columnIndex = columnIndex;
        this.columnName = columnName;
        //构造函数一般不写复杂的代码，都是上面这种给字段赋值的代码
        //如果还有额外的初始化的逻辑，最好是封装成一个方法，名字一般叫init
        //init();
    }



    @Override
    public T handle(ResultSet rs) throws SQLException {
        if (rs.next()) {
            if (this.columnName != null) {
                return (T) rs.getObject(columnName);
            }
            return (T)rs.getObject(columnIndex);
        }

        return null;

    }
}
