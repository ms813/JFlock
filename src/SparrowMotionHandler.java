import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;

import java.util.List;

/**
 * Created by smithma on 27/01/15.
 */
public class SparrowMotionHandler implements BirdMotionHandler {

    private float alignStrength = 1.0f;

    public Vector2f align(List<Bird> nearbyBirds){
        Vector2f sum = Vector2f.ZERO;

        //sum together the directions of the nearby birds to find the overall direction
        for(Bird b : nearbyBirds){
            Vector2f dir = VMath.normalize(b.getVelocity());
            sum = Vector2f.add(sum, dir);
        }

        return Vector2f.mul(VMath.normalize(sum), alignStrength);
    }

    public Vector2f separate(List<Bird> nearbyBirds){
        return Vector2f.ZERO;
    }

    public Vector2f coherence(List<Bird> nearbyBirds){
        return Vector2f.ZERO;
    }

    @Override
    public Vector2f calculateForce(List<Bird> nearbyBirds) {
        Vector2f alignment = align(nearbyBirds),
                separation = separate(nearbyBirds),
                cohesion = coherence(nearbyBirds);

        return Vector2f.add(Vector2f.add(alignment, separation), cohesion);
    }
}
