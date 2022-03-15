package com.madirex.eggmaster.Farm;

import java.util.List;

public class EggPack {
    private int idDrone;
    private List<Egg> eggs;

    public EggPack(List<Egg> eggs, int idDrone) {
        this.eggs = eggs;
        this.idDrone = idDrone;
    }

    public List<Egg> getEggs() {
        return eggs;
    }

    public int getIdDrone() {
        return idDrone;
    }
}
