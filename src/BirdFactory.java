import org.jsfml.graphics.CircleShape;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RectangleShape;
import org.jsfml.graphics.Shape;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * Created by smithma on 27/01/15.
 */
public class BirdFactory {

    private Vector2i windowSize;

    public BirdFactory(Vector2i windowSize){
        this.windowSize = windowSize;
    }

    public Bird createBird(BirdSpecies species, Vector2f position){

        Bird b = new Bird();
        Shape s;
        if(species == BirdSpecies.SPARROW){

            s = new CircleShape(2.5f);
            s.setFillColor(Color.GREEN);
            b.setMaxSpeed(15.0f);
            b.setMotionHandler(new SparrowMotionHandler());
            b.setSpecies(BirdSpecies.SPARROW);
            b.setFriction(0.9999f);
            b.setMass(0.2f);
            b.setLocalityRadius(50.0f);

        } else if(species == BirdSpecies.HAWK){

            s = new RectangleShape(new Vector2f(7.5f, 7.5f));
            s.setFillColor(Color.RED);
            b.setMaxSpeed(3.0f);
            b.setMotionHandler(new NullMotionHandler());
            b.setSpecies(BirdSpecies.HAWK);
            b.setFriction(10.f);
            b.setMass(1.0f);

        } else {
            s = new CircleShape(5.0f);
            s.setFillColor(Color.YELLOW);
        }

        s.setPosition(position);

        b.setShape(s);
        b.setVelocity(VMath.rndVector2f(b.getMaxSpeed()));

        return b;
    }

    //if no position passed return bird with random position
    public Bird createBird(BirdSpecies species){
        Vector2f rndPos = new Vector2f((float) Math.random() * windowSize.x, (float) Math.random()*windowSize.y);
        return createBird(species, rndPos);
    }
}
