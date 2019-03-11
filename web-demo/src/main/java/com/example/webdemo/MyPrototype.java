package com.example.webdemo;

public class MyPrototype {

    private static int index = 0;

    public MyPrototype() {
        MyPrototype.index++;
    }

    public int getIndex() {
        return MyPrototype.index;
    }
}
