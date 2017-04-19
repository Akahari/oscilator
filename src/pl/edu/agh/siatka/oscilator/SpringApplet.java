package pl.edu.agh.siatka.oscilator;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.Random;

/**
 * Created by Akahari on 02-Apr-17.
 */
public class SpringApplet extends JApplet {             // new class inheriting class JApplet

    private SimEngine engine;
    private SimTask simTask;
    private Timer timer;
    private int startCoordinateX;
    private int startCoordinateY;
    private int endCoordinateX;
    private int endCoordinateY;
    private double stepTime = 100;
    private long stepTimeLong = (long) this.stepTime;
    private int initCounter = 0;
    private int paintCounter = 0;
    private int setCoordinateCounter = 0;


    public void init(){                 //overwritten method init()
        this.setSize(new Dimension(1000, 800));
        initCounter++;
        System.out.println("Init Count: " + initCounter);
        this.engine = new SimEngine( 0.5, 4, 0.6, 2, 5, 7, 2, 1.5, 0, 0);
        setDrawingCoordinates( engine.getValue("hookCoordinateX") * 100 + 500,  engine.getValue("hookCoordinateY") * 100, engine.getVector("springVector"));
        this.simTask = new SimTask(this.engine, this, stepTime);
        this.timer = new Timer();
        timer.scheduleAtFixedRate(simTask, stepTimeLong, 100);
    }

    public void paint(Graphics graph){       //overwritten method paint()
        paintCounter++;
        System.out.println("Paint Count: " + paintCounter);
        setDrawingCoordinates( engine.getValue("hookCoordinateX") * 100 + 500,  engine.getValue("hookCoordinateY") * 100, engine.getVector("springVector"));
        graph.clearRect(0, 0, 1000, 2000);
        graph.drawLine(startCoordinateX, startCoordinateY, endCoordinateX, endCoordinateY);
        graph.drawOval(endCoordinateX - 10, endCoordinateY - 10, 20, 20);
        graph.drawString("Time: " + (paintCounter / 10) + "s", 100, 100);
    }

    public static void printVectorInfo(Vector2D vector){                                    //method used to print values
        System.out.println("Vector coordinate X:" + vector.coordinateX);
        System.out.println("Vector coordinate Y:" + vector.coordinateY);
        System.out.println("Vector length:" + vector.lengthOfVector());
        System.out.println("-----------------------------------");
    }

    public static void printEngineInfo(SimEngine engine){
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


    public void setDrawingCoordinates(double x, double y, Vector2D vector){
        setCoordinateCounter++;
        System.out.println("Set Coordinate Count: " + setCoordinateCounter);
        this.startCoordinateX = (int) x;
        this.startCoordinateY = (int) y;
        this.endCoordinateX =  ((int) x + (int) (vector.coordinateX * 100) );
        this.endCoordinateY =  ((int) y + (int) (vector.coordinateY * 100) );
    }

}
