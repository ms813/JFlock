import org.jsfml.system.Vector2f;

import java.util.List;

/**
 * Created by smithma on 27/01/15.
 */
public class NullMotionHandler implements BirdMotionHandler {
    @Override
    public Vector2f calculateForce(List<Bird> nearbyBirds) {
        return Vector2f.ZERO;
    }
}
