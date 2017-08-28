/**
 * Created by sean on 8/28/17.
 */
public class RealTime {
    final public static int bufferSize = 10;
    final public static int numberOfProcesses =2;
    final public static int interval = 200;
    final public static int target = 50;

    private Buffer buffer_A = new Buffer(bufferSize, numberOfProcesses);
    private Buffer buffer_B = new Buffer(bufferSize, numberOfProcesses);

    private Buffer[] empty = new Buffer[0];
    private Process sender = new Process(new Buffer[]{buffer_A, buffer_B}, empty);
    private Process receive = new Process(empty, new Buffer[]{buffer_B, buffer_A});

    /* Constructor */
    public RealTime() {
    }

    public void startProcesses(){
        sender.start();
        receive.start();
    }

    /* Returning Instance */
    private static RealTime instance = new RealTime();

    /* Getters and Setters */
    public static RealTime getInstance() {
        return instance;
    }

    public Buffer getBuffer_A() {
        return buffer_A;
    }

    public Buffer getBuffer_B() {
        return buffer_B;
    }

    public Process getSender() {
        return sender;
    }

    public Process getReceive() {
        return receive;
    }
}
