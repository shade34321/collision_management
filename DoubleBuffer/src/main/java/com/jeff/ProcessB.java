package com.jeff;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ProcessB extends Thread {

    private final String _label;
    private final DoubleBuffer<Integer> _buffer;
    private final List<Integer> _list;

    public ProcessB(String label, DoubleBuffer<Integer> buffer, List<Integer> list) {
        _label = label;
        _buffer = buffer;
        _list = list;
    }

    @Override
    public void run() {

        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(100, 2000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (!_buffer.isShutdown()) {
            int pulled = _buffer.pull();
            if (pulled > 0) {
                _list.add(pulled);
                System.out.println(_label + " pulled: " + pulled);

                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(100, 1000 + 1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
