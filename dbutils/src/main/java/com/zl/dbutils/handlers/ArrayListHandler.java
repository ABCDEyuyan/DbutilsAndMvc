package com.zl.dbutils.handlers;

import com.zl.dbutils.RowProcessor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArrayListHandler extends AbstractResultSetHandler<List<Object[]>> {

    public ArrayListHandler() {

    }

    public ArrayListHandler(RowProcessor rowProcessor) {
        super(rowProcessor);
    }

    @Override
    public List<Object[]> handle(ResultSet rs) throws SQLException {

        List<Object[]> list = new ArrayList<>();

        while (rs.next()) {
            Object[] row = rowProcessor.toArray(rs);
            list.add(row);
        }

        return list;

    }


}
