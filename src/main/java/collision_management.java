/**
 * Created by shade on 8/24/17.
 */

public class collision_management {
    public static void main(String[] args) throws InterruptedException {
        data_buffer<Integer> b = new data_buffer<Integer>("Buffer 1");
        data_buffer<Integer> b2 = new data_buffer<Integer>("Buffer 2");

        Reader reader[] = new Reader[10];
        Writer writer = new Writer(b, b2);
        Thread wthread = new Thread(writer, "writer");
        Thread rthread[] = new Thread[10];

        for (int i = 0; i < 10; i++) {
            reader[i] = new Reader(b, b2);
            rthread[i] = new Thread(reader[i], "reader_" + i);
        }

        wthread.start();
        for (int i = 0; i < 10; i++){
            rthread[i].start();
        }
    }
}
