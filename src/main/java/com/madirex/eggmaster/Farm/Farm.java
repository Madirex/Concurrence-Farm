package com.madirex.eggmaster.Farm;

import com.madirex.eggmaster.Util.Util;

import java.util.ArrayList;
import java.util.List;

public class Farm {
    private boolean puttingEgg;
    private boolean gettingEgg;
    private ArrayList<Egg> eggs;
    private ArrayList<EggPack> packEggs;
    private boolean dronePacker = false;
    private boolean unPacking = false;
    private final int numPacksPerDeliveryRecolect;
    private final int maxDeliveryRecolect;
    private int packetsSent;
    private boolean exitProgram;

    public Farm(int maxDeliveryRecolect, int numPacksPerDeliveryRecolect) {
        this.maxDeliveryRecolect = maxDeliveryRecolect;
        this.numPacksPerDeliveryRecolect = numPacksPerDeliveryRecolect;
        puttingEgg = false;
        gettingEgg = false;
        eggs = new ArrayList<>();
        packEggs = new ArrayList<>();
        packetsSent = 0;
        exitProgram = false;
    }

    public synchronized void putEgg(Integer chickenId) throws InterruptedException {
        while (puttingEgg || gettingEgg) {
            if (puttingEgg) {
                System.out.println("Como ya hay una gallina poniendo, la gallina " + chickenId + " 🐔 se espera.");
            } else if (gettingEgg) {
                System.out.println("Como hay un dron recolectando, la gallina " + chickenId + " 🐔 se intimida y se espera.");
            }
            wait();
        }

        puttingEgg = true;
        System.out.println("La gallina " + chickenId + " se prepara para poner un huevo 🥚");

        //30% de probabilidad de que salga de 2 yemas
        int numYolks = 1;
        if (Util.getRandom(1, 100) <= 30) {
            numYolks += 1;
        }
        Egg egg = new Egg(numYolks, chickenId);
        eggs.add(egg);

        System.out.println("La gallina " + chickenId + " pone un huevo 🥚 de " + numYolks + " yemas.");
        puttingEgg = false;
        notifyAll();
    }

    public synchronized Egg getEgg(int droneId) throws InterruptedException {
        while (puttingEgg || gettingEgg || eggs.isEmpty()) {
            if (puttingEgg) {
                System.out.println("Como hay una gallina poniendo un huevo, el dron " + droneId + " se espera.");
            } else if (gettingEgg) {
                System.out.println("Como ya hay un dron recolectando, el dron " + droneId + " se espera.");
            } else if (eggs.isEmpty()) {
                System.out.println("Como no hay huevos que recolectar el dron " + droneId + " se espera.");
            }
            wait();
        }
        gettingEgg = true;
        System.out.println("El dron " + droneId + " está recolectando el huevo 🥚...");
        System.out.println("El dron ha recolectado el huevo 🥚");
        Egg egg = eggs.get(0);
        eggs.remove(0);
        gettingEgg = false;
        notifyAll();
        return egg;
    }

    public synchronized void packEggs(List<Egg> eggs, Integer droneId) throws InterruptedException {
        while (dronePacker) {
            System.out.println("Ya hay un dron empaquetador, así que el dron " + droneId + " se espera a que empaquete.");
            wait();
        }
        dronePacker = true;
        System.out.println("El dron " + droneId + " se pone a empaquetar...");
        EggPack eggPack = new EggPack(eggs, droneId);
        System.out.println("El dron " + droneId + " ha empaquetado un pack de " + eggs.size() + " huevos 🥚");
        packEggs.add(eggPack);
        dronePacker = false;
        notifyAll();
    }

    public synchronized List<EggPack> unpackEggs() throws InterruptedException {
        while (this.packEggs.isEmpty() || unPacking || dronePacker) {
            if (this.packEggs.size() >= numPacksPerDeliveryRecolect) {
                System.out.println("El repatidor se espera a que haya paquetes.");
            } else if (unPacking) {
                System.out.println("Ya hay un repartidor, así que el repatidor 🚚 se espera.");
            } else if (dronePacker) {
                System.out.println("Ya hay un dron empaquetador, así que el repartidor 🚚 se espera a que el dron termine de empaquetar.");
            }
            wait();
        }

        unPacking = true;
        System.out.println("El repartidor se lleva el paquete de huevos...");
        List<EggPack> pack = new ArrayList<>();
        while (this.packEggs.size() > 0) {
            pack.add(this.packEggs.get(0));
            this.packEggs.remove(0);
        }
        unPacking = false;
        packetsSent += 1;
        checkNumberDeliveryRecolect();
        notifyAll();
        return pack;
    }

    private void checkNumberDeliveryRecolect() {
        if (packetsSent >= maxDeliveryRecolect) {
            exitProgram = true;
        }
    }

    public boolean getStop() {
        return exitProgram;
    }

    public int getPacketsSent() {
        return packetsSent;
    }
}
