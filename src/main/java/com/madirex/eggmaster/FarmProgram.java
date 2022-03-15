package com.madirex.eggmaster;

import com.madirex.eggmaster.Farm.Chicken;
import com.madirex.eggmaster.Farm.Drone;
import com.madirex.eggmaster.Farm.Farm;
import com.madirex.eggmaster.Farm.PackDelivery;

import java.util.ArrayList;
import java.util.List;

public class FarmProgram {
    private int maxDeliveryRecolect;
    private int numPacksPerDeliveryRecolect;
    private Farm farm;
    private int numChickens;
    private int numDrones;
    private int maxEggsPerDron;
    private List<Chicken> chickens;
    private List<Drone> drones;
    private PackDelivery packDelivery;

    public FarmProgram(int maxDeliveryRecolect, int numPacksPerDeliveryRecolect, int numChickens, int numDrones, int maxEggsPerDron) {
        this.maxDeliveryRecolect = maxDeliveryRecolect;
        this.numPacksPerDeliveryRecolect = numPacksPerDeliveryRecolect;
        this.numChickens = numChickens;
        this.numDrones = numDrones;
        this.maxEggsPerDron = maxEggsPerDron;
        farm = new Farm(maxDeliveryRecolect, numPacksPerDeliveryRecolect);
        chickens = new ArrayList<>();
        drones = new ArrayList<>();
        runProgram();
    }

    private void runProgram() {
        addAndStartThreads();
        waitForThreads();
        System.out.println("Programa de la granja terminado ❌");
    }

    private void addAndStartThreads() {
        for (int n = 0; n < numChickens; n++) {
            Chicken chicken = new Chicken(n, farm);
            chickens.add(chicken);
            chicken.start();
        }

        for (int n = 0; n < numDrones; n++) {
            Drone drone = new Drone(n, farm, maxEggsPerDron);
            drones.add(drone);
            drone.start();
        }

        packDelivery = new PackDelivery(farm);
        packDelivery.start();
    }

    private void waitForThreads() {
        try {
            for (Chicken c : chickens) {
                c.join();
            }

            for (Drone d : drones) {
                d.join();
            }

            packDelivery.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
