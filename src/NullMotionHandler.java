import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.List;

/**
 * Created by smithma on 27/01/15.
 */
public class NullMotionHandler implements BirdMotionHandler {

    @Override
    public Vector2f getFlockForce(List<Bird> nearbyBirds, Vector2f myPos) {
        return Vector2f.ZERO;
    }

    @Override
    public Vector2f getEdgeForce(Vector2i windowSize, Vector2f myPos) {
        return Vector2f.ZERO;
    }
}
