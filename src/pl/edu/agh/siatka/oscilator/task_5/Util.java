package pl.edu.agh.siatka.oscilator.task_5;

import java.awt.*;

/**
 * Created by Akahari on 02-Apr-17.
 */
public class Util {
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    public static long round(long value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return tmp / factor;
    }

    public static Point realMousePosition(Point mousePosition){
        return new Point(mousePosition.x - 8, mousePosition.y - 65);
    }
}
