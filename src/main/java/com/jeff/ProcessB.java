package com.jeff;

public class ProcessB extends Thread {

    private final DoubleBuffer<String[][][]> _bufferAB;
    private final DoubleBuffer<Object[][]> _bufferCD;

    public ProcessB(String[][][] initialState, DoubleBuffer<String[][][]> bufferAB, DoubleBuffer<Object[][]> bufferCD) {
        _bufferAB = bufferAB;
        _bufferCD = bufferCD;
        _bufferCD.push(SummarizeState(initialState));
    }

    @Override
    public void run() {

        String[][][] state;

        while (!_bufferAB.isShutdown()) {
            state = _bufferAB.pull();
            System.out.println("B pulled: " + state.length);
            Object[][] summary = SummarizeState(state);
            _bufferCD.push(summary);
        }
        System.out.println("BufferAB completed");
        _bufferCD.startShutdown();
    }

    private Object[][] SummarizeState(String[][][] state) {
        Object[][] summary = new Object[state.length][3];
        boolean brk = false;
        for (int plane = 0; plane < state.length; plane++) {
            for (int row = 0; row < Plane.rows; row++) {
                for (int col = 0; col < Plane.cols; col++) {
                    if(state[plane][row][col] != "") {
                        summary[plane][0] = state[plane][row][col];
                        summary[plane][1] = row;
                        summary[plane][2] = col;
                        System.out.println(state[plane][row][col] + " : " + row + " : " + col);
                        brk = true;
                        break;
                    }
                }
                if (brk) break;
            }
            brk = false;
        }
        return summary;
    }
}

