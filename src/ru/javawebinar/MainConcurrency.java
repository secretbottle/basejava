package ru.javawebinar;

import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    public static final int THREADS_NUMBER = 10000;
    private static int counter;
    private static final Object LOCK = new Object();


    public static void main(String[] args) throws InterruptedException {
        System.out.println(Thread.currentThread().getName());
        new Thread() {
            @Override
            public void run() {
                System.out.println(getName());
            }
        }.start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        }).start();


        final MainConcurrency mainConc = new MainConcurrency();
        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConc.inc();
                }
            }).start();
        }

        Thread.sleep(500);
        System.out.println(counter);

    }


    private synchronized void inc() {
//        synchronized (this) {
//        synchronized (MainConcurrency.class) {
        counter++;
//                wait();
//                readFile
//                ...
//        }
    }


}
