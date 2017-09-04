import java.util.Random;

public class Writer implements Runnable {
    private data_buffer<Integer> buffer;
    private data_buffer<Integer> buffer2;

    public Writer(data_buffer<Integer> buffer, data_buffer<Integer> buffer2) {
        this.buffer = buffer;
        this.buffer2 = buffer2;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            if (buffer.isEmpty()) {
                writeBuffer(buffer);
            } else if (buffer2.isEmpty()) {
                writeBuffer(buffer2);
            }
        }
    }

    private void writeBuffer(data_buffer<Integer> b) {
        try {
            for (int i = 0; i < 10; i++) {
                Random rand = new Random();
                int randomNum = rand.nextInt(1001);
                b.write(randomNum);
                System.out.printf("%s wrote: %d\n", b.getName(), randomNum);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
