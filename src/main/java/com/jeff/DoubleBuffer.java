package com.jeff;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * Generic double buffer implementation by Jeff Adkisson
 * <p>
 * To use:
 * new DoubleBuffer<T>(inboundSize, msWaitForOutboundToExceedZero);
 */
public class DoubleBuffer<T> {
    private final int _inboundSize;
    private final LinkedList<T> _queueA;
    private final LinkedList<T> _queueB;
    private final Semaphore _overallSem = new Semaphore(1);
    private final Semaphore _inboundSem = new Semaphore(1);
    private final Semaphore _outboundSem = new Semaphore(1);
    private boolean _startShutdown = false;
    private boolean _isShutdown = false;
    private LinkedList<T> _inboundQueue;
    private LinkedList<T> _outboundQueue;
    private boolean _aIsInbound = false;

    public DoubleBuffer(int inboundSize) {
        _inboundSize = inboundSize;
        _queueA = new LinkedList<>();
        _queueB = new LinkedList<>();
        toggleInboundQueue();
    }

    public final void startShutdown() {
        _startShutdown = true;
    }

    private boolean isAcceptingInput() {
        return !_startShutdown;
    }

    public final boolean isShutdown() {
        if (_isShutdown) {
            return true;
        }
        int remaining = _outboundQueue.size() + _inboundQueue.size();
        return _isShutdown = _startShutdown && remaining == 0;
    }

    private void toggleInboundQueue() {
        try {
            _overallSem.acquire();
            _aIsInbound = !_aIsInbound;
            _inboundQueue = _aIsInbound ? _queueA : _queueB;
            _outboundQueue = !_aIsInbound ? _queueA : _queueB;
            _overallSem.release();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public final void push(T value) {
        if (value == null) {
            throw new IllegalArgumentException("You cannot submit null");
        }

        try {
            _inboundSem.acquire();
            if (_inboundQueue.size() >= _inboundSize) {
                while (isAcceptingInput() && !_outboundQueue.isEmpty()) {
                    Thread.sleep(0);
                }
                toggleInboundQueue();
            }

            if (isAcceptingInput()) {
                _inboundQueue.offer(value);
            } else {
                throw new IllegalStateException("Buffer is not accepting input");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } finally {
            _inboundSem.release();
        }
    }

    public final T pull() {
        T result = null;
        try {
            _outboundSem.acquire();
            if (_outboundQueue.isEmpty()) {
                //if outbound queue has nothing in it and active, we have to wait
                while (isAcceptingInput() && _outboundQueue.isEmpty()) {
                    Thread.sleep(0);
                }

                if (!isAcceptingInput() && !_inboundQueue.isEmpty()) {
                    //not accepting input, but we have stuff in the inbound queue that still needs processing
                    //switch to the inbound queue and get that stuff out to the callers
                    toggleInboundQueue();
                }
            }

            if (!_outboundQueue.isEmpty()) {
                result = _outboundQueue.poll();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
        _outboundSem.release();
        return result;
    }
}

