package com.example.availablesensors;

public class MySensor {

    private final String name;
    private final String type;
    private final boolean state;


    public MySensor(String name, String type, boolean state) {
        this.name = name;
        this.type = type;
        this.state = state;
    }

    public boolean getState() {
        return state;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }
}
