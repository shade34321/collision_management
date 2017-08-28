import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class data_buffer<K> {
    private ReadWriteLock rwlock = new ReentrantReadWriteLock();
    private Lock read = rwlock.readLock();
    private Lock write = rwlock.writeLock();
    private List<K> data = new ArrayList<K>();

    private int max_size;
    private int current_size;

    public data_buffer() {
        this(10);
    }

    public data_buffer(int s) {
        max_size = s;
        current_size = 0;
    }

    public int getSize() {
        return current_size;
    }

    public int getMax_size() {
        return max_size;
    }

    public void add(K k) {
        write.lock();
        try {
            data.add(k);
        } finally {
            write.unlock();
        }
    }

    public <K> k read() {
        read.lock();
        try {
            return data.remove(0);
        } except {
    }
}