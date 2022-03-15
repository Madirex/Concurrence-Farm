package com.madirex.eggmaster.Farm;

public class Egg {
    private int numYolks;
    private int idChicken;

    public Egg(int numYolks, int idChicken) {
        this.numYolks = numYolks;
        this.idChicken = idChicken;
    }

    public int getNumYolks() {
        return numYolks;
    }

    public int getIdChicken() {
        return idChicken;
    }
}
