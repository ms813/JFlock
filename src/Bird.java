import org.jsfml.graphics.*;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

import java.util.List;

/**
 * Created by smithma on 22/01/15.
 */
public class Bird extends Actor{

    private BirdSpecies species;
    private BirdMotionHandler motionHandler;
    CircleShape localRadius;

    private boolean drawLocalRadius = false;

    boolean facingLeft = true;

    public Bird(){
       //default white circle if nothing is specified
        sprite = new Sprite();

    }

    public void init(){
        localRadius = new CircleShape(species.getLocalityRadius());
        localRadius.setFillColor(Color.TRANSPARENT);
        localRadius.setOrigin(localRadius.getGlobalBounds().width/2, localRadius.getGlobalBounds().height/2);
        localRadius.setOutlineThickness(2.0f);
        localRadius.setOutlineColor(Color.BLACK);
        localRadius.setPosition(sprite.getPosition());
    }

    @Override
    public void update(Time dt){
        velocity = Vector2f.mul(velocity, species.getFriction());
        if(VMath.magnitude(velocity) > species.getMaxSpeed()){
            velocity = Vector2f.mul(VMath.normalize(velocity), species.getMaxSpeed());
        }

        if(facingLeft && velocity.x > 0){
            int w = sprite.getTexture().getSize().x;
            int h = sprite.getTexture().getSize().y;
            //System.out.println("flip right");
            sprite.setTextureRect(new IntRect(w, 0, -w, h));
            facingLeft = false;
        }

        if(!facingLeft && velocity.x < 0){
            int w = sprite.getTexture().getSize().x;
            int h = sprite.getTexture().getSize().y;
            //System.out.println("flip left");
            sprite.setTextureRect(new IntRect(0, 0, w, h));
            facingLeft = true;
        }

        Vector2f dist = Vector2f.mul(velocity, dt.asSeconds());
        move(dist);
        localRadius.setPosition(sprite.getPosition());
    }

    @Override
    void render(RenderWindow window) {
        window.draw(sprite);
        if(drawLocalRadius){
            window.draw(localRadius);
        }
    }

    @Override
    public void applyForce(Vector2f dir, float magnitude) {
        velocity = Vector2f.add(velocity, Vector2f.mul(dir, (magnitude / species.getMass())));
    }

    public Vector2f getFlockForce(List<Bird> nearbyBirds){
        return motionHandler.getFlockForce(nearbyBirds, getPosition());
    }

    public void move(Vector2f offset){
        sprite.move(offset);
    }

    public float getMaxSpeed(){
        return species.getMaxSpeed();
    }

    public void setSpecies(BirdSpecies species){
        this.species = species;
    }

    public BirdSpecies getSpecies(){
        return species;
    }

    public float getLocalityRadius(){
        return species.getLocalityRadius();
    }

    public void setMotionHandler(BirdMotionHandler mh){
        this.motionHandler = mh;
    }


}
