package pl.edu.agh.siatka.oscilator;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;

/**
 * Created by Akahari on 02-Apr-17.
 */
public class SpringApplet extends JApplet {             // new class inheriting class JApplet

    private SimEngine engine;
    private SimTask simTask;
    private Timer timer;
    private int startCoordinateX;                       // fields helping drawing the spring
    private int startCoordinateY;
    private int endCoordinateX;
    private int endCoordinateY;
    private double stepTime = 100;
    private long stepTimeLong = (long) this.stepTime;   //methods in Timer and TimerTask uses 'long' format whereas I used double format in my previous modules
    private int paintCounter = 0;

    public void init(){                 //overwritten method init()
        this.setSize(new Dimension(1000, 800));         // setting size of applet window so the whole spring movement can be seen
        this.engine = new SimEngine( 0.5, 4, 0.6, 2, 5, 7, 2, 1.5, 0, 0);   // creating a new simulation model
        setDrawingCoordinates( engine.getValue("hookCoordinateX") * 100 + 500,  engine.getValue("hookCoordinateY") * 100, engine.getVector("springVector"));    // setting coordinates used by paint() method to draw the spring
        this.simTask = new SimTask(this.engine, this, stepTime);
        this.timer = new Timer();
        timer.scheduleAtFixedRate(simTask, stepTimeLong, stepTimeLong);         // scheduling new task to be repeated according to step time
    }

    public void paint(Graphics graph){       //overwritten method paint()
        if(paintCounter > 150) {            // every 15 seconds the simulation will be repeated
            engine.reset();
            paintCounter = 0;
        }
        paintCounter++;
        printEngineInfo(this.engine);       //printing values of velocity, acceleration and forces; values control
        setDrawingCoordinates( engine.getValue("hookCoordinateX") * 100 + 500,  engine.getValue("hookCoordinateY") * 100, engine.getVector("springVector"));  // updating the position of the end of the spring
        graph.clearRect(0, 0, 1000, 2000);
        graph.drawLine(startCoordinateX, startCoordinateY, endCoordinateX, endCoordinateY);         // drawing the spring
        graph.drawOval(endCoordinateX - 10, endCoordinateY - 10, 20, 20);       // drawing the object (mass)
        graph.drawString("Time: " + (paintCounter / 10) + "s", 100, 100);                   // drawing additional information: simulation time
    }

    public static void printEngineInfo(SimEngine engine){                   // method allowing for values control
        engine.get("massCoordinateX");
        engine.get("massCoordinateY");
        engine.get("massVelocityX");
        engine.get("massVelocityY");
        engine.get("hookCoordinateX");
        engine.get("hookCoordinateY");
        engine.get("elasticityForceVectorX");
        engine.get("elasticityForceVectorY");
        engine.get("dampingForceVectorX");
        engine.get("dampingForceVectorY");
        engine.get("netForceX");
        engine.get("netForceY");
        engine.get("accelerationX");
        engine.get("accelerationY");
    }

    public void setDrawingCoordinates(double x, double y, Vector2D vector){             // method facilitating drawing the spring
        this.startCoordinateX = (int) x;
        this.startCoordinateY = (int) y;
        this.endCoordinateX =  ((int) x + (int) (vector.coordinateX * 100) );
        this.endCoordinateY =  ((int) y + (int) (vector.coordinateY * 100) );
    }

}
