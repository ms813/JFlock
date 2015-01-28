import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.List;

/**
 * Created by smithma on 27/01/15.
 */
public class SparrowMotionHandler implements BirdMotionHandler {

    private float alignStrength = 0.3f;
    private float separateStrength = 0.8f;
    private float coherenceStrength = 0.3f;

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
        Vector2f alignment = align(nearbyBirds, myPos),
                separation = separate(nearbyBirds, myPos),
                cohesion = coherence(nearbyBirds, myPos);

        return Vector2f.add(Vector2f.add(alignment, separation), cohesion);
    }

    public Vector2f getEdgeForce(Vector2i wS, Vector2f myPos){
        float edgePadding = 50.0f;
        Vector2f windowSize = new Vector2f(wS);
        float fx = 0, fy = 0;

        if(myPos.x < edgePadding){
            fx = edgePadding - myPos.x;
        }

        if(myPos.x > (windowSize.x - edgePadding)){
            fx = -1 * (edgePadding - (windowSize.x - myPos.x));
        }

        if(myPos.y < edgePadding){
            fy = edgePadding - myPos.y;
        }

        if(myPos.y > (windowSize.y - edgePadding)){
            fy = -1 * (edgePadding - (windowSize.y - myPos.y));
        }

        return new Vector2f (fx, fy);
    }
}
