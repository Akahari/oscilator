package pl.edu.agh.siatka.oscilator.task_5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.awt.event.MouseListener;
//import java.awt.event.MouseMotionListener;
import java.util.Timer;

/**
 * Created by Akahari on 02-Apr-17.
 */
public class SpringApplet extends JApplet implements MouseListener, MouseMotionListener {             // new class inheriting class JApplet

    private SimEngine engine;
    private SimTask simTask;
    private Timer timer;
    private double mass = 0.5;
    private double elasticity = 4;
    private double damping = 0.6;
    private double springLooseLength = 2;
    private double massCoordinateX = 2;
    private double massCoordinateY = 1;
    private double massVelocityX = 0;
    private double massVelocityY = 0;
    private int startCoordinateX;                       // fields helping drawing the spring
    private int startCoordinateY;
    private int endCoordinateX;
    private int endCoordinateY;
    private double stepTime = 33;                       // 30 fps
    private long stepTimeLong = (long) this.stepTime;   //methods in Timer and TimerTask uses 'long' format whereas I used double format in my previous modules
    private int paintCounter = 0;
    private boolean mouseIsDragged;

    public void init(){                 //overwritten method init()
        this.mouseIsDragged = false;
        addMouseListener(this);
        addMouseMotionListener(this);
        this.setSize(new Dimension(1000, 800));         // setting size of applet window so the whole spring movement can be seen
        this.engine = new SimEngine( this.mass, this.elasticity, this.damping, this.springLooseLength, this.massCoordinateX, this.massCoordinateY, this.massVelocityX, this.massVelocityY, 0, 0);   // creating a new simulation model
        setDrawingCoordinates( this.engine.getValue("hookCoordinateX") * 100 + 500,  this.engine.getValue("hookCoordinateY") * 100, this.engine.getVector("springVector"));    // setting coordinates used by paint() method to draw the spring
        this.simTask = new SimTask(this.engine, this, stepTime);
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(this.simTask, this.stepTimeLong, this.stepTimeLong);         // scheduling new task to be repeated according to step time
    }

    public void paint(Graphics graph){       //overwritten method paint()
        if(this.paintCounter > (15 * 1000 / this.stepTime ) ) {            // every 15 seconds the simulation will be repeated
            this.engine.reset();
            this.paintCounter = 0;
        }
        this.paintCounter++;
        //printEngineInfo(this.engine);       //printing values of velocity, acceleration and forces; values control
        setDrawingCoordinates( this.engine.getValue("hookCoordinateX") * 100 + 500,  this.engine.getValue("hookCoordinateY") * 100, this.engine.getVector("springVector"));  // updating the position of the end of the spring
        graph.clearRect(0, 0, 1000, 2000);
        graph.drawLine(this.startCoordinateX, this.startCoordinateY, this.endCoordinateX, this.endCoordinateY);         // drawing the spring
        graph.drawOval(this.endCoordinateX - 10, this.endCoordinateY - 10, 20, 20);       // drawing the object (mass)
        graph.drawString("Time: " + (this.paintCounter * this.stepTime / 1000 ) + "s", 100, 100);                   // drawing additional information: simulation time
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point realMousePosition = Util.realMousePosition(e.getLocationOnScreen());
        if( Math.sqrt( Math.pow( realMousePosition.x - this.endCoordinateX, 2) + Math.pow( realMousePosition.y - this.endCoordinateY, 2) )  <= 10) {
            this.mouseIsDragged = true;
            this.engine.reset();
            this.timer.cancel();
            this.timer.purge();
            this.paintCounter = 0;
        }
        e.consume();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(this.mouseIsDragged){
            this.mouseIsDragged = false;
            this.engine = new SimEngine( this.mass, this.elasticity, this.damping, this.springLooseLength, this.massCoordinateX, this.massCoordinateY, this.massVelocityX, this.massVelocityY, 0, 0);   // creating a new simulation model
            setDrawingCoordinates( this.engine.getValue("hookCoordinateX") * 100 + 500,  this.engine.getValue("hookCoordinateY") * 100, this.engine.getVector("springVector"));
            this.simTask = new SimTask(this.engine, this, stepTime);
            this.timer = new Timer();
            this.timer.scheduleAtFixedRate(this.simTask, this.stepTimeLong, this.stepTimeLong);
        }
        e.consume();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(mouseIsDragged){
            Point realMousePosition = Util.realMousePosition(e.getLocationOnScreen());
            this.massCoordinateX = (double) (realMousePosition.x - 500)/100;
            this.massCoordinateY = (double) (realMousePosition.y)/100;
        }
        e.consume();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
