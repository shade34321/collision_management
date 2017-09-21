package com.jeff;

import java.io.IOException;

abstract class ProcessBase extends Thread {

    private final Console _console;
    protected ProcessBase(Console console) {
        _console = console;
    }

    public void ConsoleWriteLine() {
        ConsoleWriteLine("");
    }

    public void ConsoleWriteLine(String output) {
        _console.WriteLine(output);
    }

    public void ConsoleWait() {
        _console.Wait();
    }
}

public class ProcessA extends ProcessBase {

    private final int _delayMs;
    private final DoubleBuffer<String[][][]> _buffer;
    private final Display _display;

    public ProcessA(int delayMs, DoubleBuffer<String[][][]> buffer, Display display, Console console) {
        super(console);
        _buffer = buffer;
        _display = display;
        _delayMs = delayMs;
    }

    @Override
    public void run() {
        if (_delayMs == 0) ConsoleWriteLine("SINGLE STEP MODE - PRESS ENTER TO PROGRESS\r\n");

        int counter = 0;
        int totalPlanes =  _display.GetPlanes().size();

        String[][][] currentState = _display.GetCurrentState();
        _buffer.push(currentState.clone());

        while (counter < _display.Seconds) {
            System.out.println("A PUSHED: " + counter);
            if (_delayMs > 0) {
                try {
                    Thread.sleep(_delayMs);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                //if delay is 0, then single step
                _display.AwaitKeypress();
            }

            counter++;
            for (int i = 0; i < totalPlanes; i++) {
                currentState[i] = _display.GetPlanes().get(i).MoveOne();
            }
            try {
                _display.Refresh();
            } catch (IOException e) {
                e.printStackTrace();
            }

            _buffer.push(currentState.clone());
        }
        _buffer.startShutdown();
        System.out.println("A FINISHED - NO MORE INBOUND VALUES");
    }
}

