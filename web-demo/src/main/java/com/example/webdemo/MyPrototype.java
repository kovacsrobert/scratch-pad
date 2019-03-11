package com.example.webdemo;

public class MyPrototype implements IMyPrototype {

    private static int index = 0;

    public MyPrototype() {
        MyPrototype.index++;
    }

    @Override
    public int getIndex() {
        return MyPrototype.index;
    }
}
