package com.entities;

import com.common.Helper;
import com.common.QueryHelper;
import org.json.JSONObject;

public abstract class Entity {

    private String query;

    private Column[] columns;
    private int id;
    private String table;
    private String selectQuery = "select id from poll.? where id=";


    public Entity(final int id) {

    }


    private void setQuery() {
        JSONObject object = QueryHelper.getData(selectQuery
                .replaceFirst("\\?", table),
                "pull-string");
        if (object.has("message")
                && Helper.isThing(object.getString("message"))) {

        }
    }

    public abstract void setColumns();


}
