package com.jeff;

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

    private final int _delayMs = 1000;
    private final DoubleBuffer<String[][][]> _buffer;
    private final Display _display;

    public ProcessA(DoubleBuffer<String[][][]> buffer, Display display, Console console) {
        super(console);
        _buffer = buffer;
         _display = display;
    }

    @Override
    public void run() {
        int counter = 0;
        int totalPlanes =  _display.GetPlanes().size();

        String[][][] currentState = _display.GetCurrentState();
        _buffer.push(currentState.clone());
        System.out.println("A PUSHED: " + counter);

        while (counter < _display.Seconds) {
            counter++;
            for (int i = 0; i < totalPlanes; i++) {
                currentState[i] = _display.GetPlanes().get(i).MoveOne();
            }

            _buffer.push(currentState.clone());
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

