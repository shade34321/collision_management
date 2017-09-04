public class Process_2 extends AbstractThread {
    RealTime RT;
    private Buffer write;
    private Buffer read;
    private int current_w = 0;
    private int current_r = 0;


    @Override
    public void run() {
        RT = RealTime.getInstance();
        RT.setP2_running(true);
        this.write = RT.getBuffer_CD()[current_w];
        this.read = RT.getBuffer_AB()[current_r];

        System.out.println("Process 2: Starting");
        while ((!Thread.currentThread().isInterrupted())) {
            while (!read.isInitialized()) {
                sleep(0);
            }

            for (int row = 0; row < read.getRowSize(); row++) {
                for (int col = 0; col < read.getColSize(); col++) {
                    int data = read.read(row, col);
                    if (data > 0)
                        writeToBuffer(row, col, data);
                }
            }
            read.setBufferRead();
            write.setInitialized(true);

            sleep(RT.getInterval());
            toggleBuffers();
            clearBuffer();

            if (!RT.isP1_running() && read.isBufferEmpty()) {
                RT.setP2_running(false);
                stop();
                System.out.println("Process 2: shutting down");
                break;
            }

        }
    }

    private void writeToBuffer(int row, int col, int data) {

        int plane_x = 0;
        int plane_y = 1;
        int plane_z = 2;
        switch (data) {
            case 1: // X
                summarize(row, col, plane_x);
                break;
            case 3: // Y
                summarize(row, col, plane_y);
                break;
            case 5: // Z
                summarize(row, col, plane_z);

                break;

            /* Collision Cases */

            case 4: // XY
                summarize(row, col, plane_x);
                summarize(row, col, plane_y);
                break;
            case 6: // XZ
                summarize(row, col, plane_x);
                summarize(row, col, plane_z);
                break;
            case 8:  // YZ
                summarize(row, col, plane_y);
                summarize(row, col, plane_z);
                break;
            case 9:  // XYZ
                summarize(row, col, plane_x);
                summarize(row, col, plane_y);
                summarize(row, col, plane_z);
                break;
        }
    }

    private void summarize(int row, int col, int plane) {
        write.write(plane, 0, plane);
        write.write(plane, 1, row);
        write.write(plane, 2, col);
    }


    private void toggleBuffers() {
        current_w = (current_w + 1) % RT.getBuffer_CD().length;
        current_r = (current_r + 1) % RT.getBuffer_AB().length;
        this.write = RT.getBuffer_CD()[current_w];
        this.read = RT.getBuffer_AB()[current_r];
    }

    private void clearBuffer() {
        while (!write.isBufferEmpty())
            sleep(0);

        write.reset();
    }
}
