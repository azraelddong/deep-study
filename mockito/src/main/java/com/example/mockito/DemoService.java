package com.example.mockito;

public class DemoService {

    private DemoDao demoDao;

    public DemoService(DemoDao demoDao) {
        this.demoDao = demoDao;
    }

    public int getRandom() {
        int result = demoDao.getRandom();
        System.out.println("===========> demoService result=" + result);
        return result;
    }
}
