package com.entities;

import lombok.Getter;
import lombok.Setter;

public class Column {

    public enum Type {
        VARCHAR, INT, BLOB, DATE
    }

    @Getter @Setter
    private String name;

    @Getter @Setter
    private Type type;

    @Getter @Setter
    private Object value;
}
