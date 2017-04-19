package pl.edu.agh.siatka.oscilator;
import java.util.TimerTask;

/**
 * Created by Akahari on 02-Apr-17.
 */
public class SimTask extends TimerTask{             //new class inheriting class TimerTask
    private SimEngine engine;
    private SpringApplet applet;
    private double stepTime;


    public SimTask(SimEngine engine, SpringApplet applet, double stepTime){
        this.engine = engine;
        this.applet = applet;
        this.stepTime = stepTime;
    }

        public void run(){                              //overwritten methode run()
            engine.calculateCourse(stepTime / 1000);        // calculating how the situation changes after step time
            applet.validate();
            applet.repaint();           // updating the spring drawing in applet
        }
}
