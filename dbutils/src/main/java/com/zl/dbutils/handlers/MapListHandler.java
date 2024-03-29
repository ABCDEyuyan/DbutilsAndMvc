package com.zl.dbutils.handlers;

import com.zl.dbutils.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapListHandler extends AbstractResultSetHandler<List<Map<String,Object>>> {

    public MapListHandler() {
    }

    public MapListHandler(RowProcessor rowProcessor) {
        super(rowProcessor);
    }

    @Override
    public List<Map<String, Object>> handle(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        while (rs.next()) {
            list.add(this.rowProcessor.toMap(rs));
        }
        return list;
    }
}
