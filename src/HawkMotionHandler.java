import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.List;

/**
 * Created by smithma on 04/02/15.
 */
public class HawkMotionHandler implements BirdMotionHandler {

    private Vector2f closestSparrowDir(List<Bird> nearbyBirds, Vector2f myPos){
        Bird closest = nearbyBirds.size() > 0 ? nearbyBirds.get(0) : null;
        float minDist = VMath.magnitude(Vector2f.sub(myPos, closest == null ? new Vector2f(999999,999999) : closest.getPosition()));
        for(Bird b : nearbyBirds){
            if(b.getSpecies() == BirdSpecies.SPARROW){
                float dist = VMath.magnitude(Vector2f.sub(myPos, b.getPosition()));
                if(dist < minDist){
                    closest = b;
                    minDist = dist;
                }
            }
        }
        return VMath.normalize(Vector2f.sub(myPos, closest == null ? VMath.rndVector2f() : closest.getPosition()));
    }

    @Override
    public Vector2f getFlockForce(List<Bird> nearbyBirds, Vector2f myPos) {
        return Vector2f.mul(closestSparrowDir(nearbyBirds, myPos), BirdSpecies.HAWK.getMaxSpeed());
    }
}
