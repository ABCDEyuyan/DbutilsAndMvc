package com.zl.dbutils.handlers;

import com.zl.dbutils.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MapHandler extends AbstractResultSetHandler<Map<String,Object>> {
    public MapHandler() {
    }

    public MapHandler(RowProcessor rowProcessor) {
        super(rowProcessor);
    }

    @Override
    public Map<String, Object> handle(ResultSet rs) throws SQLException {
        return rs.next()?rowProcessor.toMap(rs):null;
    }
}
