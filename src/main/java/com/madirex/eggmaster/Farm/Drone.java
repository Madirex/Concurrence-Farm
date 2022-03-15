package com.madirex.eggmaster.Farm;

import com.madirex.eggmaster.Util.Util;

import java.util.ArrayList;

public class Drone extends Thread {
    private int id;
    private Farm farm;
    private ArrayList<Egg> eggs;
    private int maxEggs;
    private int battery;

    public Drone(Integer id, Farm farm, Integer maxEggs) {
        eggs = new ArrayList<>();
        this.id = id;
        this.farm = farm;
        this.maxEggs = maxEggs;
        this.battery = 100;
    }

    @Override
    public void run() {
        while (!farm.getStop()) {
            try {

                //Si se acaba la batería del dron...
                if (battery <= 0) {
                    System.out.println("El dron " + id + " está recargando su batería...");
                    Thread.sleep((long) Util.getRandom(700, 1200));
                    System.out.println("El dron " + id + " ha recargado su batería 🔋");
                }

                Thread.sleep((long) Util.getRandom(700, 1200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Si tiene demasiados huevos encima...
            try {
                if (eggs.size() >= maxEggs) {
                    farm.packEggs(eggs, id);
                } else {
                    eggs.add(farm.getEgg(id));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Restar batería
            battery -= Util.getRandom(1, 20);
        }
    }
}
