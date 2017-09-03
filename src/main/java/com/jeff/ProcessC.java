package com.jeff;

import java.io.IOException;

public class ProcessC extends Thread {

    private final DoubleBuffer<Object[][]> _bufferCD;
    private Plane _statusPlane;
    private Display _display;
    private int _second = 1;

    public ProcessC(DoubleBuffer<Object[][]> bufferCD, Display display) {
        _bufferCD = bufferCD;
        _display = display;
        _statusPlane = display.GetStatusPlane();
    }

    @Override
    public void run() {

        Object[][] state;

        while (!_bufferCD.isShutdown()) {
            state = _bufferCD.pull();
            System.out.println("C pulled: " + state.length);
            ShowState(state);
            _second++;
        }
        System.out.println("BufferCD completed");
    }

    private void ShowState(Object[][] state) {
        _statusPlane.ResetDisplay();
        _display.UpdateStatus("Process C Second " + _second);
        for (int plane = 0; plane < state.length; plane++) {
            String marker = (String)state[plane][0];
            int row = (int)state[plane][1];
            int col = (int)state[plane][2];
            String currentMarker = _statusPlane.GetMarker(row, col) + marker;
            boolean collision = currentMarker.length() > 1;
            _statusPlane.ShowMarker(row, col, collision, currentMarker);
            if (collision) _display.UpdateStatus(", COLLISION " + currentMarker, true);
        }
        try {
            _display.Refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

