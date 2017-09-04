/**
 * Created by sean on 8/28/17.
 */
public class RealTime {
    final private static int row = 8;
    final private static int col = 7;
    final private static int X = 1;
    final private static int Y = 3;
    final private static int Z = 5;
    final private static int shared = 2;
    final private static int notShared = 1;
    final private static int interval = 200;

    private boolean p1_running = false;
    private boolean p2_running = false;
    private boolean p3_running = false;

    private Buffer buffer_A = new Buffer(row, col, shared);
    private Buffer buffer_B = new Buffer(row, col, shared);
    private Buffer buffer_C = new Buffer(3, 3, 1);
    private Buffer buffer_D = new Buffer(3, 3, 1);

    private Buffer[] buffer_AB = new Buffer[]{buffer_A, buffer_B};
    private Buffer[] buffer_CD = new Buffer[]{buffer_C, buffer_D};

    private Process_1 p1 = new Process_1();
    private Process_2 p2 = new Process_2();
    private Process_3 p3 = new Process_3();

    /* Constructor */
    public RealTime() {
    }

    public void startProcesses() {
        p1.start();
        p2.start();
        p3.start();
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

    public static int getRow() {
        return row;
    }

    public static int getCol() {
        return col;
    }

    public static int getX() {
        return  X;
    }

    public static int getY() {
        return  Y;
    }

    public static int getZ() {
        return  Z;
    }


    public static int getInterval() {
        return interval;
    }


    public Buffer[] getBuffer_AB() {
        return buffer_AB;
    }

    public Buffer[] getBuffer_CD() {
        return buffer_CD;
    }

    public boolean isP1_running() {
        return p1_running;
    }

    public void setP1_running(boolean p1_running) {
        this.p1_running = p1_running;
    }

    public boolean isP2_running() {
        return p2_running;
    }

    public void setP2_running(boolean p2_running) {
        this.p2_running = p2_running;
    }

    public boolean isP3_running() {
        return p3_running;
    }

    public void setP3_running(boolean p3_running) {
        this.p3_running = p3_running;
    }
}
