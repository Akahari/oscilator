package pl.edu.agh.siatka.oscilator;

import java.util.Random;

/**
 * Created by Akahari on 02-Apr-17.
 */
public class VectorTest {
    public static void main(String[] args) {
        final int DECIMAL_PLACES = 2;
        Random randomGenerator = new Random();                                              //initializing instance Random class
        int multiplier = randomGenerator.nextInt(10);                               //generatin a multipier

        double x = Util.round(15*randomGenerator.nextDouble(), DECIMAL_PLACES);      //generating values of vector coordinates
        double y = Util.round(15*randomGenerator.nextDouble(), DECIMAL_PLACES);
        Vector2D vector1 = new Vector2D(x,y);                                               //creating first object with generated coordinates
        x = Util.round(15*randomGenerator.nextDouble(), DECIMAL_PLACES);
        y = Util.round(15*randomGenerator.nextDouble(), DECIMAL_PLACES);
        Vector2D vector2 = new Vector2D(x,y);                                               //creating second object with generated new coordinates
        Vector2D summedVectors = vector1.sumOfVectors(vector2);                             //creating new object which are a sum, a difference, a normalised vectors and a multiplied vector
        Vector2D extractedVectors = vector1.differenceOfVectors(vector2);
        Vector2D normalisedVector1 = vector1.normalisedVector();
        Vector2D normalisedVector2 = vector2.normalisedVector();
        Vector2D multipliedVector1 = vector1.multipliedVector(multiplier);
        System.out.println("Vector 1");                                                     //printing values
        printVectorInfo(vector1);
        System.out.println("Vector 2");
        printVectorInfo(vector2);
        System.out.println("Sum of vector 1 and vector 2");
        printVectorInfo(summedVectors);
        System.out.println("Difference of vector 1 and vector 2");
        printVectorInfo(extractedVectors);
        System.out.println("Normalised vector 1");
        printVectorInfo(normalisedVector1);
        System.out.println("Normalised vector 2");
        printVectorInfo(normalisedVector2);
        System.out.println("Vector 1 multplied by " + multiplier);
        printVectorInfo(multipliedVector1);
    }

    public static void printVectorInfo(Vector2D vector){                                    //method used to print values
        System.out.println("Vector coordinate X:" + vector.coordinateX);
        System.out.println("Vector coordinate Y:" + vector.coordinateY);
        System.out.println("Vector length:" + vector.lengthOfVector());
        System.out.println("-----------------------------------");
    }
}
