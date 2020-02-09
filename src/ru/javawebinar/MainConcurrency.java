package ru.javawebinar;

import java.util.concurrent.*;

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

        //java.util.concurrent.*;
        CountDownLatch cdLatch = new CountDownLatch(THREADS_NUMBER);
        ExecutorService executorService = Executors.newCachedThreadPool();


        //List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Future<Integer> future = executorService.submit(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConc.inc();
                }
                cdLatch.countDown();
                return 5;
            });


/*
            executorService.submit(()->{
                for (int j = 0; j < 100; j++) {
                    mainConc.inc();
                }
                cdLatch.countDown();

            });
            */

/*

            new Thread(() -> {
                for (int j = 0; j < 100; j++) {
                    mainConc.inc();
                }
                cdLatch.countDown();
            }).start();
            //threads.add(thread);
            */

        }

        Thread.sleep(500);
        System.out.println(counter);

/*

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
*/
        cdLatch.await(10, TimeUnit.SECONDS);
        executorService.shutdown();

        System.out.println(mainConc.counter);

        System.out.println("Deadlock Test");
        deadlock();

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

    private static void deadlock() {
        String resourceOne = "res One";
        String resourceTwo = "res Two";

        threadRes("threadOne", resourceOne, resourceTwo).start();
        threadRes("threadTwo", resourceTwo, resourceOne).start();

    }

    private static Thread threadRes(String threadName, Object objectOne, Object objectTwo) {
        return new Thread(() -> {
            synchronized (objectOne) {
                System.out.println(objectOne);

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (objectTwo) {
                    System.out.println(objectTwo);
                }
            }
        }, threadName);

    }

}
