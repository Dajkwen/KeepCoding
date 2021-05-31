package com.jkwen.s5;

/**
 * 相比较于 Singleton2，提高了并发性能
 */
public class Singleton3 {
    private Singleton3() {}

    private volatile static Singleton3 s;

    public static Singleton3 getInstance() {
        if (s == null) {
            synchronized(Singleton3.class) {
                if (s == null) {
                    s = new Singleton3();
                }
            }
        }
        return s;
    }
}
