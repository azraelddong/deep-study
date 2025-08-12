package com.example.mockito;


import java.util.Random;

public class DemoDao {

    public int getRandom() {
        int result = new Random().nextInt();
        System.out.println("===========> demoDao result=" + result);
        return result;
    }
}
