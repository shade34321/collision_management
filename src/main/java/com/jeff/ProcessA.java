package com.jeff;

import java.util.concurrent.ThreadLocalRandom;

public class ProcessA extends Thread {

    private final int _min;
    private final int _max;
    private final DoubleBuffer<Integer> _buffer;

    public ProcessA(int min, int max, DoubleBuffer<Integer> buffer) {
        _min = min;
        _max = max;
        _buffer = buffer;
    }

    @Override
    public void run() {
        int counter = _min;
        while (counter <= _max) {
            _buffer.push(counter);
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 1000 + 1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("A PUSHED: " + counter);
            counter++;
        }
        _buffer.startShutdown();
        System.out.println("A FINISHED - NO MORE INBOUND VALUES");
    }
}

