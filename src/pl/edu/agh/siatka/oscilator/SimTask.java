package pl.edu.agh.siatka.oscilator;
import java.util.TimerTask;

/**
 * Created by Akahari on 02-Apr-17.
 */
public class SimTask extends TimerTask{             //new class inheriting class TimerTask
    private SimEngine engine;
    private SpringApplet applet;
    private double stepTime;
    private int runCounter = 0;
    //private Vector2D previousSpringVector;

    public SimTask(SimEngine engine, SpringApplet applet, double stepTime){
        this.engine = engine;
        this.applet = applet;
        this.stepTime = stepTime;
    }

        public void run(){                              //overwritten methode run()
            runCounter++;
            System.out.println("Run Count: " + runCounter);
            engine.calculateCourse(stepTime / 1000);
            System.out.println("Finished calculations");
            applet.validate();
            System.out.println("Validated");
            applet.repaint();
            System.out.println("Repainted");
        }
}
