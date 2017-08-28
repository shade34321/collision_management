public abstract class AbstractThread {
    /**
     * The thread
     */
    private Thread thread = new Thread() {
        @Override
        public void run() {
            AbstractThread.this.run();
        }
    };

    /**
     * Abstract run method that determines what will
     * happen when the thread is started
     */
    protected abstract void run();

    /**
     * Starts the thread
     */
    public void start() {
        thread.start();
    }

    /**
     * Stops the thread
     */
    public void stop() {
        thread.interrupt();
    }

    /**
     * Sleeps the thread for the specified amount of time
     *
     * @param milliseconds
     */
    public void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
