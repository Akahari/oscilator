package pl.edu.agh.siatka.oscilator;
/**
 * Created by Akahari on 02-Apr-17.
 */
import java.lang.String;

public class SimEngine {
    private final double GRAVITATIONAL_ACCELERATION = 9.86;
    private final int DECIMAL_PLACES = 2;
    private int calculateCounter = 0;

    private double mass = 0;
    private double elasticity = 0;
    private double damping = 0;
    private double springLooseLength = 0;
    private double massCoordinateX = 0;
    private double massCoordinateY = 0;
    private double massVelocityX = 0;
    private double massVelocityY = 0;
    private double hookCoordinateX = 0;
    private double hookCoordinateY = 0;

    private double startMassCoordinateX = 0;
    private double startMassCoordinateY = 0;
    private double startMassVelocityX = 0;
    private double startMassVelocityY = 0;
    private double startHookCoordinateX = 0;
    private double startHookCoordinateY = 0;

    private Vector2D springVector = new Vector2D(0,0);
    private Vector2D massVelocityVector = new Vector2D (0 , 0);
    private Vector2D elasticityForceVector = new Vector2D(0, 0);
    private Vector2D dampingForceVector = new Vector2D(0, 0);
    private Vector2D gravityForceVector = new Vector2D(0, 0);
    private Vector2D netForce = new Vector2D(0,0);
    private Vector2D acceleration = new Vector2D(0,0);

    public SimEngine (double mass, double elasticity, double damping, double springLooseLength, double massCoordinateX, double massCoordinateY, double massVelocityX, double massVelocityY, double hookCoordinateX, double hookCoordinateY){
        this.mass = mass;
        this.elasticity = elasticity;
        this.damping = damping;
        this.springLooseLength = springLooseLength;

        this.massCoordinateX = massCoordinateX;
        this.massCoordinateY = massCoordinateY;
        this.massVelocityX = massVelocityX;
        this.massVelocityY = massVelocityY;
        this.hookCoordinateX = hookCoordinateX;
        this.hookCoordinateY = hookCoordinateY;

        this.startMassCoordinateX = massCoordinateX;
        this.startMassCoordinateY = massCoordinateY;
        this.startMassVelocityX = massVelocityX;
        this.startMassVelocityY = massVelocityY;
        this.startHookCoordinateX = hookCoordinateX;
        this.startHookCoordinateY = hookCoordinateY;

        this.gravityForceVector = new Vector2D(0, this.mass * GRAVITATIONAL_ACCELERATION);
        this.springVector = new Vector2D(this.massCoordinateX - this.hookCoordinateX, this.massCoordinateY - this.hookCoordinateY);
        this.massVelocityVector = new Vector2D (this.massVelocityX , this.massVelocityY);
        this.elasticityForceVector = this.springVector.normalisedVector().multipliedVector( ( this.springLooseLength - this.springVector.lengthOfVector() ) * this.elasticity );
        this.dampingForceVector = new Vector2D(-1 * this.damping * this.massVelocityVector.coordinateX, -1 * this.damping * massVelocityVector.coordinateY);
        this.netForce = gravityForceVector.sumOfVectors( elasticityForceVector.sumOfVectors(dampingForceVector) );
        this.acceleration = netForce.multipliedVector( (1/this.mass) );
    }

    public void calculateCourse(double stepTime){
        calculateCounter++;
        System.out.println("Calculate Count: " + calculateCounter);
        this.massCoordinateX = this.massCoordinateX + this.massVelocityX * stepTime + this.acceleration.coordinateX * Math.pow(stepTime,2) / 2;
        this.massCoordinateY = this.massCoordinateY + this.massVelocityY * stepTime + this.acceleration.coordinateY * Math.pow(stepTime,2) / 2;
        this.massVelocityX = this.massVelocityX + this.acceleration.coordinateX * stepTime;
        this.massVelocityY = this.massVelocityY + this.acceleration.coordinateY * stepTime;
        this.springVector = new Vector2D(this.massCoordinateX - this.hookCoordinateX, this.massCoordinateY - this.hookCoordinateY);
        this.massVelocityVector = new Vector2D (this.massVelocityX , this.massVelocityY);
        this.elasticityForceVector = this.springVector.normalisedVector().multipliedVector( ( this.springLooseLength - this.springVector.lengthOfVector() ) * this.elasticity );
        this.dampingForceVector = new Vector2D(-1 * this.damping * this.massVelocityVector.coordinateX, -1 * this.damping * massVelocityVector.coordinateY);
        this.netForce = gravityForceVector.sumOfVectors( elasticityForceVector.sumOfVectors(dampingForceVector) );
        this.acceleration = netForce.multipliedVector( (1/this.mass) );
    }

    public void set(String name, double value){
        switch(name){
            case "mass": this.mass = value; break;
            case "elasticity": this.elasticity = value; break;
            case "damping": this.damping = value; break;
            case "springLooseLength": this.springLooseLength = value; break;
            case "massCoordinateX": this.massCoordinateX = value; break;
            case "massCoordinateY": this.massCoordinateY = value; break;
            case "massVelocityX": this.massVelocityX = value; break;
            case "massVelocityY": this.massVelocityY = value; break;
            case "hookCoordinateX": this.hookCoordinateX = value; break;
            case "hookCoordinateY": this.hookCoordinateY = value; break;
            default: throw new IllegalArgumentException("Invalid field: " + name);
        }
    }

    public void get(String name){
        switch(name){
            case "mass": System.out.println("Mass value is: " + Util.round(this.mass, DECIMAL_PLACES)); break;
            case "elasticity": System.out.println("Elasticity value is: " + Util.round(this.elasticity, DECIMAL_PLACES)); break;
            case "damping": System.out.println("Damping value is: " + Util.round(this.damping, DECIMAL_PLACES)); break;
            case "springLooseLength": System.out.println("Spring loose length value is: " + Util.round(this.springLooseLength, DECIMAL_PLACES)); break;
            case "massCoordinateX": System.out.println("Mass x coordinate value is: " + Util.round(this.massCoordinateX, DECIMAL_PLACES)); break;
            case "massCoordinateY": System.out.println("Mass y coordinate value is: " + Util.round(this.massCoordinateY, DECIMAL_PLACES)); break;
            case "massVelocityX": System.out.println("Mass x velocity value is: " + Util.round(this.massVelocityX, DECIMAL_PLACES)); break;
            case "massVelocityY": System.out.println("Mass y velocity value is: " + Util.round(this.massVelocityY, DECIMAL_PLACES)); break;
            case "hookCoordinateX": System.out.println("Hook x coordinate value is: " + Util.round(this.hookCoordinateX, DECIMAL_PLACES)); break;
            case "hookCoordinateY": System.out.println("Hook y coordinate value is: " + Util.round(this.hookCoordinateY, DECIMAL_PLACES)); break;
            case "elasticityForceVectorX": System.out.println("elasticityForceVectorX value is: " + Util.round(this.elasticityForceVector.coordinateX, DECIMAL_PLACES)); break;
            case "elasticityForceVectorY": System.out.println("elasticityForceVectorY value is: " + Util.round(this.elasticityForceVector.coordinateY, DECIMAL_PLACES)); break;
            case "dampingForceVectorX": System.out.println("dampingForceVectorX value is: " + Util.round(this.dampingForceVector.coordinateX, DECIMAL_PLACES)); break;
            case "dampingForceVectorY": System.out.println("dampingForceVectorY value is: " + Util.round(this.dampingForceVector.coordinateY, DECIMAL_PLACES)); break;
            case "netForceX": System.out.println("netForceX value is: " + Util.round(this.netForce.coordinateX, DECIMAL_PLACES)); break;
            case "netForceY": System.out.println("netForceY value is: " + Util.round(this.netForce.coordinateY, DECIMAL_PLACES)); break;
            case "accelerationX": System.out.println("accelerationX value is: " + Util.round(this.acceleration.coordinateX, DECIMAL_PLACES)); break;
            case "accelerationY": System.out.println("accelerationY value is: " + Util.round(this.acceleration.coordinateY, DECIMAL_PLACES)); break;
            case "all": {
                System.out.println("Mass value is: " + Util.round(this.mass, DECIMAL_PLACES));
                System.out.println("Elasticity value is: " + Util.round(this.elasticity, DECIMAL_PLACES));
                System.out.println("Damping value is: " + Util.round(this.damping, DECIMAL_PLACES));
                System.out.println("Spring loose length value is: " + Util.round(this.springLooseLength, DECIMAL_PLACES));
                System.out.println("Mass x coordinate value is: " + Util.round(this.massCoordinateX, DECIMAL_PLACES));
                System.out.println("Mass y coordinate value is: " + Util.round(this.massCoordinateY, DECIMAL_PLACES));
                System.out.println("Mass x velocity value is: " + Util.round(this.massVelocityX, DECIMAL_PLACES));
                System.out.println("Mass y velocity value is: " + Util.round(this.massVelocityY, DECIMAL_PLACES));
                System.out.println("Hook x coordinate value is: " + Util.round(this.hookCoordinateX, DECIMAL_PLACES));
                System.out.println("Hook y coordinate value is: " + Util.round(this.hookCoordinateY, DECIMAL_PLACES));
                break;
            }
            default: throw new IllegalArgumentException("Invalid field: " + name);
        }
    }

    public double getValue(String name){
        switch(name){
            case "mass": return Util.round(this.mass, DECIMAL_PLACES);
            case "elasticity": return Util.round(this.elasticity, DECIMAL_PLACES);
            case "damping": return Util.round(this.damping, DECIMAL_PLACES);
            case "springLooseLength": return Util.round(this.springLooseLength, DECIMAL_PLACES);
            case "massCoordinateX": return Util.round(this.massCoordinateX, DECIMAL_PLACES);
            case "massCoordinateY": return Util.round(this.massCoordinateY, DECIMAL_PLACES);
            case "massVelocityX": return Util.round(this.massVelocityX, DECIMAL_PLACES);
            case "massVelocityY": return Util.round(this.massVelocityY, DECIMAL_PLACES);
            case "hookCoordinateX": return Util.round(this.hookCoordinateX, DECIMAL_PLACES);
            case "hookCoordinateY": return Util.round(this.hookCoordinateY, DECIMAL_PLACES);
            case "elasticityForceVectorX": return Util.round(this.elasticityForceVector.coordinateX, DECIMAL_PLACES);
            case "elasticityForceVectorY": return Util.round(this.elasticityForceVector.coordinateY, DECIMAL_PLACES);
            case "dampingForceVectorX": return Util.round(this.dampingForceVector.coordinateX, DECIMAL_PLACES);
            case "dampingForceVectorY": return Util.round(this.dampingForceVector.coordinateY, DECIMAL_PLACES);
            case "netForceX": return Util.round(this.netForce.coordinateX, DECIMAL_PLACES);
            case "netForceY": return Util.round(this.netForce.coordinateY, DECIMAL_PLACES);
            case "accelerationX": return Util.round(this.acceleration.coordinateX, DECIMAL_PLACES);
            case "accelerationY": return Util.round(this.acceleration.coordinateY, DECIMAL_PLACES);
            default: throw new IllegalArgumentException("Invalid vector: " + name);
        }
    }

    public Vector2D getVector(String name){
        switch(name){
            case "springVector": return this.springVector;
            case "massVelocityVector": return this.massVelocityVector;
            case "elasticityForceVector": return this.elasticityForceVector;
            case "dampingForceVector": return this.dampingForceVector;
            case "gravityForceVector": return this.gravityForceVector;
            case "netForce": return this.netForce;
            case "acceleration": return this.acceleration;
            default: throw new IllegalArgumentException("Invalid vector: " + name);
        }
    }

    public void reset(){
        this.massCoordinateX = this.startMassCoordinateX;
        this.massCoordinateY = this.startMassCoordinateY;
        this.massVelocityX = this.startMassVelocityX;
        this.massVelocityY = this.startMassVelocityY;
        this.hookCoordinateX = this.startHookCoordinateX;
        this.hookCoordinateY = this.startHookCoordinateY;

        this.springVector = new Vector2D(this.massCoordinateX - this.hookCoordinateX, this.massCoordinateY - this.hookCoordinateY);
        this.massVelocityVector = new Vector2D (this.massVelocityX , this.massVelocityY);
        this.elasticityForceVector = this.springVector.normalisedVector().multipliedVector( ( this.springLooseLength - this.springVector.lengthOfVector() ) * this.elasticity );
        this.dampingForceVector = new Vector2D(-1 * this.damping * this.massVelocityVector.coordinateX, -1 * this.damping * massVelocityVector.coordinateY);
        this.netForce = gravityForceVector.sumOfVectors( elasticityForceVector.sumOfVectors(dampingForceVector) );
        this.acceleration = netForce.multipliedVector( (1/this.mass) );
    }
}
