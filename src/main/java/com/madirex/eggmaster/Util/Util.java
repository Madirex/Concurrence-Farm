package com.madirex.eggmaster.Util;

public class Util {
    public static double getRandom(double min, double max) {
        return Math.random() * (max - min) + min;
    }
}
