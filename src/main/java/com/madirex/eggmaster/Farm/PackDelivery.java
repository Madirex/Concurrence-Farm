package com.madirex.eggmaster.Farm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.madirex.eggmaster.Util.Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PackDelivery extends Thread {
    private Farm farm;

    public PackDelivery(Farm farm) {
        this.farm = farm;
    }

    @Override
    public void run() {
        while (!farm.getStop()) {
            try {
                Thread.sleep((long) Util.getRandom(1000, 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //Si tiene demasiados huevos encima...
            try {
                List<EggPack> eggPack = farm.unpackEggs();
                System.out.println("Imprimiendo paquete...");
                packJSON(eggPack);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void packJSON(List<EggPack> eggPack) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(eggPack);
            String filePath = "pack" + farm.getPacketsSent() + ".json";
            File file = new File(filePath);
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
            System.out.println("JSON " + filePath + " guardado:");
            System.out.println(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
