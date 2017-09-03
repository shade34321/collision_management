package com.jeff;

import java.io.IOException;

public class ProcessA extends Thread {

    private final int _rounds = 20;
    private final int _delayMs = 5000;
    private final DoubleBuffer<String[][][]> _buffer;
    private final Display _display;

    public ProcessA(DoubleBuffer<String[][][]> buffer, Display display) {
         _buffer = buffer;
         _display = display;
    }

    @Override
    public void run() {
        int counter = 0;
        int totalPlanes =  _display.GetPlanes().size();

        String[][][] currentState = _display.GetCurrentState();
        _buffer.push(currentState);
        System.out.println("A PUSHED: " + counter);

        while (counter < _rounds) {
            counter++;
            for (int i = 0; i < totalPlanes; i++) {
                currentState[i] = _display.GetPlanes().get(i).MoveOne();
            }

            _buffer.push(currentState);
            try {
                Thread.sleep(_delayMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("A PUSHED: " + counter);
        }
        _buffer.startShutdown();
        System.out.println("A FINISHED - NO MORE INBOUND VALUES");
    }
}

