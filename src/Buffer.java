import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Buffer {

    /**
     * Provides lock for synchronization
     * NOTE: if we need to synchronize anything, synchronize on "lock"
     * lock.writeLock().lock();
     * lock.writeLock().unlock();
     */
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    private int[][] buffer;
    private int rowSize;
    private int colSize;
    private boolean initialized = false;
    private int readProcesses;
    private int currentReads = 0;


    public Buffer(int row, int col, int sharedProcesses) {
        this.rowSize = row;
        this.colSize = col;
        this.readProcesses = sharedProcesses;
        this.buffer = new int[rowSize][colSize];
        reset();
    }


    public int read(int row, int col) {
        int msg =0;
        lock.readLock().lock();
        try{
            msg = buffer[row][col];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error:  Buffer array out of bounds");
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
            return msg;
        }
    }

    public void write(int row, int col, int data) {
        lock.writeLock().lock();
        try{
            this.buffer[row][col] = data;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error:  Buffer array out of bounds");
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
    public int getRowSize(){
        return rowSize;
    }
    public int getColSize(){
        return colSize;
    }


    public boolean isInitialized(){
        boolean bool = false;
        lock.readLock().lock();
        try{
            bool= initialized;
        } finally {
            lock.readLock().unlock();
            return bool;
        }
    }

    public void setInitialized(boolean flag){
        // put a lock
        lock.writeLock().lock();
        try{
            initialized = flag;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public boolean isBufferEmpty(){
        boolean bool = false;
        lock.readLock().lock();
        try{
            bool= readProcesses >= currentReads;
        } finally {
            lock.readLock().unlock();
            return bool;
        }
    }

    public void setBufferRead(){
        lock.writeLock().lock();
        try{
            currentReads++;
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void intializeProcessorForRead(){
        lock.writeLock().lock();
        try{
            readProcesses++;
        } finally {
            lock.writeLock().unlock();
        }

    }

    public void reset(){
        lock.writeLock().lock();
        try{
            for(int j = 0; j < rowSize; j++){
                for(int i = 0; i < colSize; i++)
                    buffer[j][i] = 0;
            }
            initialized = false;
            currentReads = 0;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error:  Buffer array out of bounds");
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }

    }

}
