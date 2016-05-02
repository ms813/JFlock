import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;

/**
 * Created by smithma on 19/02/15.
 */
public interface BirdCreationInstructions {
    public void setSpecies(Bird b);
    public void setMotionHandler(Bird b);
    public void setSprite(Bird b, Vector2f pos, float scale);
}
