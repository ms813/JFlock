import Libraries.TextureLibrary;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by smithma on 27/01/15.
 */
public class BirdFactory {

    private Vector2i windowSize;

    private static HashMap<BirdSpecies, BirdCreationInstructions> registeredBirdSpecies = new HashMap<BirdSpecies, BirdCreationInstructions>();

    public BirdFactory(Vector2i windowSize){
        this.windowSize = windowSize;
    }

    public static void registerBirdSpecies(BirdSpecies species, BirdCreationInstructions bc){
        if(!registeredBirdSpecies.containsKey(species)){
            registeredBirdSpecies.put(species, bc);
        } else{
            System.out.println(species + " already registered!");
        }
    }

    public Bird createBird(BirdSpecies species, Vector2f position){
        Random rnd = new Random();

        Bird b = new Bird();

        if(registeredBirdSpecies.containsKey(species)){
            BirdCreationInstructions bc = registeredBirdSpecies.get(species);
            bc.setSpecies(b);
            bc.setMotionHandler(b);
            bc.setSprite(b, position, 0.05f);
        }

        b.setVelocity(VMath.rndVector2f());
        b.init();

        return b;
    }

    //if no position passed return bird with random position
    public Bird createBird(BirdSpecies species){
        Random rnd = new Random();
        Vector2f rndPos = new Vector2f(rnd.nextFloat() * windowSize.x, rnd.nextFloat() * windowSize.y);
        return createBird(species, rndPos);
    }
}
