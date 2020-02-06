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

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>");
        deadlockTest();

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


    private static void deadlockTest() throws InterruptedException {
        Resource resourceOne = new Resource("res One");
        Resource resourceTwo = new Resource("res Two");

        Thread threadOne = new Thread(() -> {
            System.out.println(resourceTwo.getName());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(resourceOne.getName());
        });

        Thread threadTwo = new Thread(() -> {
            System.out.println(resourceOne.getName());
            threadOne.start();
            System.out.println(resourceTwo.getName());
        });


        threadOne.start();

        threadTwo.start();

    }


    private static class Resource {
        private String name;

        Resource(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }


}
