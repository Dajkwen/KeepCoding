package com.jkwen.s5;

public class Singleton4 {
    private Singleton4() {}

    private static class SingletonHolder {
        public final static Singleton4 s = new Singleton4();
    }

    public static Singleton4 getInstance() {
        return SingletonHolder.s;
    }
}
