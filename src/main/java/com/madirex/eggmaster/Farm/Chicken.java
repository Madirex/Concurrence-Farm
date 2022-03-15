package com.madirex.eggmaster.Farm;

import com.madirex.eggmaster.Util.Util;

public class Chicken extends Thread {
    private int id;
    private Farm farm;

    public Chicken(Integer id, Farm farm) {
        this.id = id;
        this.farm = farm;
    }

    @Override
    public void run() {
        while (!farm.getStop()) {
            try {
                Thread.sleep((long) Util.getRandom(1000, 10000));
                farm.putEgg(id);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
