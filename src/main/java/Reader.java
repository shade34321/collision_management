public class Reader implements Runnable {
    private data_buffer<Integer> buffer;
    private data_buffer<Integer> buffer2;

    public Reader(data_buffer<Integer> buffer, data_buffer<Integer> buffer2) {
        this.buffer = buffer;
        this.buffer2 = buffer2;
    }

    @Override
    public void run() {
        try {
            while (!buffer.isFull() && !buffer2.isFull()) {
                Thread.sleep(100);
            }

            if(buffer.isFull()) {
                readBuffer(buffer);
            } else if (buffer2.isFull()) {
                readBuffer(buffer2);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void readBuffer(data_buffer<Integer> b) {
        try {
            while (!buffer.isEmpty()) {
                System.out.printf("Read: %d from %s\n", buffer.read(), buffer.getName());
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
