package pl.edu.agh.siatka.oscilator.task_5;
/**
 * Created by Akahari on 02-Apr-17.
 */
import java.lang.String;

public class SimEngine {
    private final double GRAVITATIONAL_ACCELERATION = 9.86;
    private final int DECIMAL_PLACES = 2;

    private double mass;
    private double elasticity;
    private double damping;
    private double springLooseLength;
    private double massCoordinateX;
    private double massCoordinateY;
    private double massVelocityX;
    private double massVelocityY;
    private double hookCoordinateX;
    private double hookCoordinateY;

    private Vector2D springVector;
    private Vector2D massVelocityVector;
    private Vector2D elasticityForceVector;
    private Vector2D dampingForceVector;
    private Vector2D gravityForceVector;
    private Vector2D netForce;
    private Vector2D acceleration;

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

        this.gravityForceVector = new Vector2D(0, this.mass * GRAVITATIONAL_ACCELERATION);
        this.springVector = new Vector2D(this.massCoordinateX - this.hookCoordinateX, this.massCoordinateY - this.hookCoordinateY);
        this.massVelocityVector = new Vector2D (this.massVelocityX , this.massVelocityY);
        this.elasticityForceVector = this.springVector.normalisedVector().multipliedVector( ( this.springLooseLength - this.springVector.lengthOfVector() ) * this.elasticity );
        this.dampingForceVector = new Vector2D(-1 * this.damping * this.massVelocityVector.coordinateX, -1 * this.damping * massVelocityVector.coordinateY);
        this.netForce = gravityForceVector.sumOfVectors( elasticityForceVector.sumOfVectors(dampingForceVector) );
        this.acceleration = netForce.multipliedVector( (1/this.mass) );
    }

    public void calculateCourse(double stepTime){
        this.massCoordinateX = this.massCoordinateX + this.massVelocityX * stepTime + this.acceleration.coordinateX * Math.pow(stepTime,2) / 2;             // updating position using equation s = s0 + vt + 0.5at^2
        this.massCoordinateY = this.massCoordinateY + this.massVelocityY * stepTime + this.acceleration.coordinateY * Math.pow(stepTime,2) / 2;
        this.massVelocityX = this.massVelocityX + this.acceleration.coordinateX * stepTime;                                                                 // updating speed using equation v = v0 + at
        this.massVelocityY = this.massVelocityY + this.acceleration.coordinateY * stepTime;
        this.springVector = new Vector2D(this.massCoordinateX - this.hookCoordinateX, this.massCoordinateY - this.hookCoordinateY);                 // updating spring vector regarding mass displacement
        this.massVelocityVector = new Vector2D (this.massVelocityX , this.massVelocityY);                                                                   // assembling velocity vector from its components
        this.elasticityForceVector = this.springVector.normalisedVector().multipliedVector( ( this.springLooseLength - this.springVector.lengthOfVector() ) * this.elasticity );    // updating spring reaction force based on new mass position/new spring lenght
        this.dampingForceVector = new Vector2D(-1 * this.damping * this.massVelocityVector.coordinateX, -1 * this.damping * massVelocityVector.coordinateY);                // updating damping force based on new velocity vector
        this.netForce = gravityForceVector.sumOfVectors( elasticityForceVector.sumOfVectors(dampingForceVector) );                                          // updating net force
        this.acceleration = netForce.multipliedVector( (1/this.mass) );                                                                                     // updating acceleration
    }

    public void set(String name, double value){                 // values setter (unused in this programme; in this case values are assigned by calculations done in calculateCourse(stepTime) )
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

    public void get(String name){                           // read-only getter
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

    public double getValue(String name){                            // value fields getter
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

    public Vector2D getVector(String name){             // Vector2D fields getter
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
        this.massCoordinateX = 0;
        this.massCoordinateY = 1;
        this.massVelocityX = 0;
        this.massVelocityY = 0;
        this.hookCoordinateX = 0;
        this.hookCoordinateY = 1;

        this.springVector = new Vector2D(0,0);
        this.massVelocityVector = new Vector2D (0,0);
        this.elasticityForceVector = new Vector2D(0,0);
        this.dampingForceVector = new Vector2D(0,0);
        this.netForce = new Vector2D(0,0);
        this.acceleration = new Vector2D(0, 0);
    }
}
