package com.jeff;

import java.util.*;

public class Console {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("STARTING");
        int min = 1;
        int max = 50;

        DoubleBuffer<Integer> buffer = new DoubleBuffer<>(10, 1);
        List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());

        Thread processA = new ProcessA(min, max, buffer);
        Thread processB = new ProcessB("B", buffer, list);
        Thread processC = new ProcessB("C", buffer, list);

        processA.start();
        processB.start();
        processC.start();

        processA.join();
        processB.join();
        processC.join();

        //verify results
        Set<Integer> uniqueInt = new HashSet<>(list);
        System.out.println("PULLED VALUES: should be " + (max - min + 1) + ", was " + list.size() + ", " + ((max - min + 1) == list.size() ? "SUCCESS" : "FAILED"));
        System.out.println("UNIQUE VALUES: should be " + (max - min + 1) + ", was " + uniqueInt.size() + ", " + ((max - min + 1) == uniqueInt.size() ? "SUCCESS" : "FAILED"));
        System.out.println("MIN SIZE: should be " + min + ", was " + Collections.min(list) + ", " + (min == Collections.min(list) ? "SUCCESS" : "FAILED"));
        System.out.println("MAX: should be " + max + ", was " + Collections.max(list) + ", " + (max == Collections.max(list) ? "SUCCESS" : "FAILED"));


    }


}