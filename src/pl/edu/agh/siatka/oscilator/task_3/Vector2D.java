package pl.edu.agh.siatka.oscilator.task_3;
/**
 * Created by Akahari on 02-Apr-17.
 */
public class Vector2D {
    public double coordinateX, coordinateY;                 //public fields of vector coordinates
    private final int DECIMAL_PLACES = 2;

    public Vector2D(Vector2D vector2D){                                      //default constructor
        coordinateX = 0;
        coordinateY = 0;
    }

    public Vector2D(double x, double y){                    //constructor with arguments
        this.coordinateX = x;
        this.coordinateY = y;
    }

    public Vector2D sumOfVectors(Vector2D additionalVector){                //method returning an object being a sum of vectors
        Vector2D newVector = new Vector2D(Util.round(this.coordinateX + additionalVector.coordinateX, DECIMAL_PLACES), Util.round(this.coordinateY + additionalVector.coordinateY, DECIMAL_PLACES));
        return newVector;
    }

    public Vector2D differenceOfVectors(Vector2D additionalVector){         //method returning an object being a difference of vectors
        Vector2D newVector = new Vector2D(Util.round(this.coordinateX - additionalVector.coordinateX, DECIMAL_PLACES), Util.round(this.coordinateY - additionalVector.coordinateY, DECIMAL_PLACES));
        return newVector;
    }

    public Vector2D multipliedVector(double multiplier){                    //method returning an object being a multiplied vector
        Vector2D newVector = new Vector2D(this.coordinateX * multiplier, this.coordinateY * multiplier);
        return newVector;
    }

    public double lengthOfVector(){                                         //method returning the length of the vector
        return Util.round( Math.sqrt(Math.pow(this.coordinateX, 2) + Math.pow(this.coordinateY, 2)),DECIMAL_PLACES);
    }

    public Vector2D normalisedVector(){                                     //method returning a normalised vector
        Vector2D newVector = new Vector2D( Util.round(this.coordinateX / this.lengthOfVector(), DECIMAL_PLACES + 2) , Util.round(this.coordinateY / this.lengthOfVector() , DECIMAL_PLACES + 2));
        return newVector;
    }
}
