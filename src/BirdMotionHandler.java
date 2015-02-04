import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.List;

/**
 * Created by smithma on 27/01/15.
 */
public interface BirdMotionHandler {
    public Vector2f getFlockForce(List<Bird> nearbyBirds, Vector2f myPos);
}


