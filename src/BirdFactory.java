import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.Random;

/**
 * Created by smithma on 27/01/15.
 */
public class BirdFactory {

    private Vector2i windowSize;

    public BirdFactory(Vector2i windowSize){
        this.windowSize = windowSize;
    }

    public Bird createBird(BirdSpecies species, Vector2f position){
        Random rnd = new Random();

        Bird b = new Bird();
        Sprite s;
        if(species == BirdSpecies.SPARROW){

            String txtName = "bird" +  rnd.nextInt(5);
            s = new Sprite(TextureLibrary.getTexture(txtName));
            b.setSpecies(BirdSpecies.SPARROW);
            b.setMotionHandler(new SparrowMotionHandler());

        } else if(species == BirdSpecies.HAWK){

            s = new Sprite(TextureLibrary.getTexture("hawk"));
            b.setSpecies(BirdSpecies.HAWK);
            b.setMotionHandler(new HawkMotionHandler());

        } else {
            s = new Sprite(TextureLibrary.getTexture("bird0"));
        }
        s.setScale(0.05f, 0.05f);
        s.setPosition(position);
        b.setSprite(s);
        b.setVelocity(VMath.rndVector2f());

        return b;
    }

    //if no position passed return bird with random position
    public Bird createBird(BirdSpecies species){
        Random rnd = new Random();
        Vector2f rndPos = new Vector2f(rnd.nextFloat() * windowSize.x, rnd.nextFloat()*windowSize.y);
        return createBird(species, rndPos);
    }
}
