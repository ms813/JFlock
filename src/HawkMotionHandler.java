import org.jsfml.system.Vector2f;

import java.util.List;
import java.util.Random;

/**
 * Created by smithma on 04/02/15.
 */
public class HawkMotionHandler implements BirdMotionHandler {

    private static Random rnd = new Random();

    private Bird target = null;
    private int maxPursueTime = 9000;
    private int pursueTime = rnd.nextInt(maxPursueTime);
    private int currentPursueTime = 0;

    @Override
    public Vector2f getFlockForce(List<Bird> nearbyBirds, Vector2f myPos) {
        if(target == null){
            if(nearbyBirds.size() > 0){
                target = chooseTarget(nearbyBirds);
            }
        }

        if(currentPursueTime > pursueTime){
            //if spent too long pursuing, choose a new target
            target = chooseTarget(nearbyBirds);
            currentPursueTime = 0;
            pursueTime = rnd.nextInt(maxPursueTime);
        }

        currentPursueTime++;

        Vector2f pursueForce = target != null ? Vector2f.sub(target.getPosition(), myPos) : VMath.rndVector2f(BirdSpecies.HAWK.getMaxSpeed()/4);
        return Vector2f.mul(VMath.normalize(pursueForce), BirdSpecies.HAWK.getMaxSpeed());
    }

    private Bird chooseTarget(List<Bird> nearbyBirds){
        if(nearbyBirds.size() > 0){
            return nearbyBirds.get(rnd.nextInt(nearbyBirds.size()));
        } else{
            return null;
        }
    }

}
