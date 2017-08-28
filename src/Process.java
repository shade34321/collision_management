
public class Process extends AbstractThread {
    private static int ID_counter = 0;
    final private int ID = ID_counter++;
    private int target;

    private boolean writeBool;
    private boolean readBool;
    private Buffer[] writeBuffers;
    private Buffer[] readBuffers;
    private int writeID = 0;
    private int readID = 0;
    private int currentWrite = 0;
    private int currentRead = 0;
    private int counter = 1;
    private int interval;

    public Process(Buffer[] writeBuffers, Buffer[] readBuffers) {
        RealTime RT = RealTime.getInstance();
        this.interval = RT.interval;
        this.target = RT.target;
        if(writeBuffers.length > 0){
            this.writeBool = true;
            this.writeBuffers = writeBuffers;
        }
        if(readBuffers.length > 0){
            this.readBool = true;
            this.readBuffers = readBuffers;
        }
    }

    @Override
    public void run(){
        while ((!Thread.currentThread().isInterrupted())) {
            if(writeBool){
                write(currentWrite, counter);
            }
            if(readBool && readBuffers[readID].isInitialized() ) {
                int msg = read(currentRead);
                System.out.println(msg);
            }

            counter++;
            next();
            sleep(interval);

            // Stop Threads
            if(counter > target){
                writeBool = false;
            }

            if(!writeBool && !readBool){
                stop();
            }
        }
    }

    public void next(){
        if(writeBool){
            currentWrite = ++currentWrite % this.writeBuffers[writeID].getBufferSize();
            if(currentWrite == 0){
                writeBuffers[writeID].setInitialized(true);
                writeBuffers[writeID].bufferReadReset(false);
                writeID = ++writeID % writeBuffers.length;
            }

        }

        if(readBool) {
            currentRead = ++currentRead % this.readBuffers[readID].getBufferSize();
            if(currentRead == 0){
                readBuffers[readID].setBufferRead(ID, true);
                readID = ++readID % readBuffers.length;

                if(allBuffersRead())
                    readBool = false;
            }
        }

    }

    public void write(int key, int data){
        try{
            writeBuffers[writeID].write(key, data);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error:  Process write array out of bounds");
            e.printStackTrace();
        }

    }

    public int read(int key){
        int msg = 0;
        try{
            msg = readBuffers[readID].read(key);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error:  Process read array out of bounds");
            e.printStackTrace();
        }
        return msg;
    }

    public boolean allBuffersRead(){
        for(int i = 0; i < readBuffers.length; i++){
            if(!readBuffers[i].isBufferRead(ID)){
                return false;
            }
        }
        return true;
    }
}
