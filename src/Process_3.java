/**
 * Created by sean on 9/3/17.
 */
public class Process_3 extends AbstractThread{
    RealTime RT;
    private Buffer read;
    private int current_r = 0;


    @Override
    public void run() {
        RT = RealTime.getInstance();
        RT.setP3_running(true);
        this.read = RT.getBuffer_CD()[current_r];

        System.out.println("Process 3: Starting");
        while ((!Thread.currentThread().isInterrupted())) {
            while (!read.isInitialized()) {
                sleep(0);
            }
            System.out.println("-----------------------------");
            for (int row = 0; row < read.getRowSize(); row++) {
                String msg = "";
                msg += getPlane(row);
                for (int col = 1; col < read.getColSize(); col++) {
                    int data = read.read(row, col);
                    msg += " |" + data;
                }
                System.out.println(msg);
            }
            read.setBufferRead();

            sleep(RT.getInterval());
            toggleBuffers();

            if (!RT.isP2_running() && read.isBufferEmpty()) {
                RT.setP3_running(false);
                stop();
                System.out.println("Process 2: shutting down");
                break;
            }

        }
    }

    private String getPlane(int data){
        String str = "";
        switch (data){
            case 0:
                str = "X";
                break;
            case 1:
                str = "Y";
                break;
            case 2:
                str = "Z";
                break;
        }
        return str;
    }


    private void toggleBuffers() {
        current_r = (current_r + 1) % RT.getBuffer_CD().length;
        this.read = RT.getBuffer_CD()[current_r];
    }
}
