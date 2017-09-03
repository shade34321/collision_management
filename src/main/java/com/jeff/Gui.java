package com.jeff;

import java.io.IOException;

public class Gui {
    public static void main(String[] args) throws IOException, InterruptedException {
        Display display = ConfigureDisplay();

        DoubleBuffer<String[][][]> bufferAB = new DoubleBuffer<>(1);
        DoubleBuffer<Object[][]> bufferCD = new DoubleBuffer<>(1);

        ProcessA processA = new ProcessA(bufferAB, display);
        processA.start();

        ProcessB processB = new ProcessB(display.GetCurrentState(), bufferAB, bufferCD);
        processB.start();

        ProcessC processC = new ProcessC(bufferCD, display);
        processC.start();

        processA.join();
        processB.join();
        processC.join();
        System.out.println("DONE");

    }

    private static Display ConfigureDisplay() throws IOException {
        Display display = new Display(2);
        display.AddPlane("Plane for X", Plane.Movement.X, "X", 0, 0, false);
        display.AddPlane("Plane for Y", Plane.Movement.Y, "Y", 0, 2, false);
        display.AddPlane("Plane for Z", Plane.Movement.Z, "Z",3,6,false);
        display.AddPlane("Process C output for X, Y, Z", Plane.Movement.Set, "",-1,-1,true);
        display.Refresh(1000);

        return display;
    }
}

