public class Process_1 extends AbstractThread {
    RealTime RT;
    private Buffer write;
    private Buffer read;
    private int current_r = 0;
    private int current_w = 1;
    private int target = 20;

    @Override
    public void run() {
        RT = RealTime.getInstance();
        RT.setP1_running(true);
        read = RT.getBuffer_AB()[current_r];
        write = RT.getBuffer_AB()[current_w];
        System.out.println("Process 1: Initializing Data");

        initializeData();
        System.out.println("Process 1: Starting");
        while ((!Thread.currentThread().isInterrupted())) {


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
            target--;
            if (target <= 0) {
                RT.setP1_running(false);
                stop();
                System.out.println("Process 1: shutting down");
            } else {
                toggleBuffers();
                clearBuffer();
            }

        }
    }

    /**
     * @param row  of read buffer, int
     * @param col  of read buffer, int
     * @param data int value in buffer
     * @return boolean if there is a collision
     * Writes Plane to buffer array
     */
    private boolean writeToBuffer(int row, int col, int data) {
        boolean x = false;
        boolean y = false;
        boolean z = false;
        switch (data) {
            case 1: // X
                x = moveX(row, col);
                break;
            case 3: // Y
                y = moveY(row, col);
                break;
            case 5: // Z
                z = moveZ(row, col);
                break;

            /* Collision Cases */

            case 4: // XY
                x = moveX(row, col);
                y = moveY(row, col);
                break;
            case 6: // XZ
                x = moveX(row, col);
                z = moveZ(row, col);
                break;
            case 8:  // YZ
                y = moveY(row, col);
                z = moveZ(row, col);
                break;
            case 9:  // XYZ
                x = moveX(row, col);
                y = moveY(row, col);
                z = moveZ(row, col);
                break;
        }
        return x | y | z;

    }

    private boolean moveX(int row, int col) {
        int nextRow = (row + 1) % RT.getRow();
        int nextCol = (col + 1) % RT.getCol();
        return processMove(nextRow, nextCol, RT.getX());
    }

    private boolean moveY(int row, int col) {
        int nextRow = (row + 1) % RT.getRow();
        int nextCol = 2;
        return processMove(nextRow, nextCol, RT.getY());
    }

    private boolean moveZ(int row, int col) {
        int nextRow = 3;
        int nextCol = (col + 1) % RT.getCol();
        return processMove(nextRow, nextCol, RT.getZ());
    }

    private boolean processMove(int nextRow, int nextCol, int plane) {
        boolean collision = false;
        int val = write.read(nextRow, nextCol);
        if (val > 0)
            collision = true;
        write.write(nextRow, nextCol, val + plane);
        return collision;
    }

    private void initializeData() {
        RT.getBuffer_A().write(0, 0, RT.getX());
        RT.getBuffer_A().write(0, 2, RT.getY());
        RT.getBuffer_A().write(3, 6, RT.getZ());
        RT.getBuffer_A().setInitialized(true);
    }

    private void toggleBuffers() {
        current_r = (current_r + 1) % RT.getBuffer_AB().length;
        current_w = (current_w + 1) % RT.getBuffer_AB().length;
        read = RT.getBuffer_AB()[current_r];
        write = RT.getBuffer_AB()[current_w];
    }

    private void clearBuffer() {
        while (!write.isBufferEmpty())
            sleep(0);

        write.reset();
    }
}
