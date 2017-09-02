package com.jeff;

import java.awt.*;
import java.io.IOException;

public class Gui {
    public static void main(String[] args) throws IOException {

        Display d = new Display(2);
        Plane x = d.AddPlane("Plane for X", Plane.Movement.X, "X");
        Plane y = d.AddPlane("Plane for Y", Plane.Movement.Y, "Y");
        Plane z = d.AddPlane("Plane for Z", Plane.Movement.Z, "Z");
        Plane xyz = d.AddPlane("Plane for X, Y, Z",  Plane.Movement.Set,"");
        x.Initialize(0,0);
        y.Initialize(0,2);
        z.Initialize(3,6);
        boolean cont = true;
        while (cont) {
            try {
                Thread.sleep(750);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Point px = x.MoveOne();
            Point py = y.MoveOne();
            Point pz = z.MoveOne();
            xyz.InitAll();
            xyz.ShowMarker(new Point[] { px, py, pz}, new String[] { "X", "Y", "Z"});
            d.Refresh();
        }
        d.UpdateStatus("hello!");
        d.Refresh();




    }
}

