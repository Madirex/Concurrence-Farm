package com.madirex.eggmaster;

import com.madirex.eggmaster.Farm.FarmProgramApplication;

public class App {
    public static void main(String[] args) {
        //FARM
        System.out.println("┏━━━┓\n" +
                "┃┏━━┛\n" +
                "┃┗━━┳━━┳━┳┓┏┓\n" +
                "┃┏━━┫┏┓┃┏┫┗┛┃\n" +
                "┃┃╋╋┃┏┓┃┃┃┃┃┃\n" +
                "┗┛╋╋┗┛┗┻┛┗┻┻┛\n");
        new FarmProgramApplication(3, 10, 20, 3, 10);
    }
}
