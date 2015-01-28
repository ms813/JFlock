import com.sun.deploy.config.VerboseDefaultConfig;
import org.jsfml.system.Vector2f;

import java.util.Random;

/**
 * Created by smithma on 27/01/15.
 */
public class VMath {

    private static Random rnd = new Random();

    public static Vector2f normalize(Vector2f v){
        return new Vector2f(v.x/magnitude(v), v.y/magnitude(v));
    }

    public static float magnitude(Vector2f v){
        return (float) Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.y, 2));
    }

    public static Vector2f rndVector2f(float magnitude){
        return Vector2f.mul(rndVector2f(), magnitude);
    }

    public static Vector2f rndVector2f(){

        //generate a random normalised vector between -1 and 1 in each direction
        return normalize(new Vector2f(rnd.nextFloat()*2 - 1, rnd.nextFloat() * 2 - 1));
    }

    public static float dot(Vector2f v1, Vector2f v2){
        return (v1.x * v2.x) + (v1.y * v2.y);
    }

    public static float degreesBetween(Vector2f v1, Vector2f v2){
        return (float) Math.toDegrees(radiansBetween(v1, v2));
    }

    public static float radiansBetween(Vector2f v1, Vector2f v2){
        return (float) Math.acos(dot(v1,v2)/(magnitude(v1)*magnitude(v2)));
    }

    public static Vector2f radiansToUnitVector(float rads){
        return new Vector2f((float) Math.sin(rads), (float) Math.cos(rads));
    }
}
