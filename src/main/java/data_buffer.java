import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class data_buffer<K> {
    private ReentrantLock rwlock;
    private Condition empty;
    private Condition full;
    private List<K> data;

    private String name;
    private int max_size;
    private int current_size;

    // Defaults to size 10 due to requirements from class
    public data_buffer(String name) {
        this(10, name);
    }

    public data_buffer(int s, String name) {
        max_size = s;
        current_size = 0;
        this.name = name;

        rwlock = new ReentrantLock(true);
        empty = rwlock.newCondition();
        full = rwlock.newCondition();
        data = new ArrayList<K>();
    }

    public int getSize() {
        return current_size;
    }

    public int getMax_size() {
        return max_size;
    }

    public String getName() { return name; }

    public boolean isFull() { return current_size == max_size;}

    public boolean isEmpty() { return current_size == 0; };

    public boolean getLock() { return rwlock.tryLock();}

    public void write(K k) {
        if (k == null) {
            throw new IllegalArgumentException("Value can't be null....");
        }

        rwlock.lock();

        try {
            while (isFull()) {
                empty.await();
            }

            if (current_size < max_size) {
                data.add(k);
                current_size++;
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }

            full.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwlock.unlock();
        }
    }

    public K read() {
        K val = null;
        rwlock.lock();
        try {
            while (isEmpty()) {
                full.await();
            }
            if (current_size > 0) {
                val = data.remove(0);
                current_size--;
            } else {
                throw new ArrayIndexOutOfBoundsException();
            }

            empty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            rwlock.unlock();
        }

        return val;
    }
}