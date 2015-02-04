import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.List;
import java.util.Vector;

/**
 * Created by smithma on 27/01/15.
 */
public class SparrowMotionHandler implements BirdMotionHandler {

    private float alignStrength = 0.3f;
    private float separateStrength = 0.8f;
    private float coherenceStrength = 0.3f;
    private float dangerStrength = 1.0f;

    private Vector2f align(List<Bird> nearbyBirds, Vector2f myPos){
        Vector2f sum = Vector2f.ZERO;

        //sum together the directions of the nearby birds to find the overall direction
        for(Bird b : nearbyBirds){
            if(b.getSpecies() == BirdSpecies.SPARROW){
                Vector2f dir = VMath.normalize(b.getVelocity());
                sum = Vector2f.add(sum, dir);
            }
        }

        return Vector2f.mul(VMath.normalize(Vector2f.sub(sum, myPos)), BirdSpecies.SPARROW.getMaxSpeed() * alignStrength);
    }

    private Vector2f separate(List<Bird> nearbyBirds, Vector2f myPos){

        Vector2f sum = Vector2f.ZERO;

        for(Bird b: nearbyBirds){
            if(b.getSpecies() == BirdSpecies.SPARROW){
                Vector2f pos = Vector2f.sub(myPos, b.getPosition());
                float dist = VMath.magnitude(pos);
                float repelForce = 1.0f/((float) Math.pow(dist, 2.0f)) * BirdSpecies.SPARROW.getMaxSpeed() *  separateStrength;
                sum = Vector2f.add(sum, Vector2f.mul(VMath.normalize(pos), repelForce));
            }
        }

        return sum;
    }

    private Vector2f coherence(List<Bird> nearbyBirds, Vector2f myPos){

        Vector2f sum = Vector2f.ZERO;

        for(Bird b : nearbyBirds){
            if(b.getSpecies() == BirdSpecies.SPARROW){
                Vector2f pos = b.getPosition();
                sum = Vector2f.add(sum, pos);
            }
        }

        return Vector2f.mul(VMath.normalize(Vector2f.sub(sum, myPos)), BirdSpecies.SPARROW.getMaxSpeed() * coherenceStrength);
    }

    @Override
    public Vector2f getFlockForce(List<Bird> nearbyBirds, Vector2f myPos) {
        boolean safe = true;
        for(Bird b : nearbyBirds){
            if(b.getSpecies() == BirdSpecies.HAWK){
                safe = false;
            }
        }
        if(safe){
            return Vector2f.add(Vector2f.add(align(nearbyBirds, myPos), separate(nearbyBirds, myPos)), coherence(nearbyBirds, myPos));
        } else{
            return dangerForce(nearbyBirds, myPos);
        }
    }

    private Vector2f dangerForce(List<Bird> nearbyBirds, Vector2f myPos){
        Vector2f sum = Vector2f.ZERO;
        for(Bird b : nearbyBirds){
            if(b.getSpecies() == BirdSpecies.HAWK){
                Vector2f pos = Vector2f.sub(myPos, b.getPosition());
                sum = VMath.normalize(pos);
            }
        }

        return Vector2f.mul(VMath.normalize(sum), dangerStrength * BirdSpecies.SPARROW.getMaxSpeed());
    }
}
