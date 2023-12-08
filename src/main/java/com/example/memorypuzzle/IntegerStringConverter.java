package com.example.memorypuzzle;

import javafx.util.StringConverter;

public class IntegerStringConverter extends StringConverter {

    @Override
    public String toString(Object o) {
        return o.toString();
    }

    @Override
    public Integer fromString(String string) {
        return Integer.parseInt(string);
    }

}
