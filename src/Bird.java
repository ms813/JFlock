import org.jsfml.graphics.CircleShape;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

import java.util.List;

/**
 * Created by smithma on 22/01/15.
 */
public class Bird extends Actor{

    private BirdSpecies species;

    private float maxSpeed, friction, localityRadius;

    private BirdMotionHandler motionHandler = new NullMotionHandler();

    public Bird(){
       //default white circle if nothing is specified
        shape = new CircleShape(5);
    }

    @Override
    public void update(Time dt){
        velocity = Vector2f.mul(velocity, friction);
        if(VMath.magnitude(velocity) > maxSpeed){
            velocity = Vector2f.mul(VMath.normalize(velocity), maxSpeed);
        }
        shape.move(Vector2f.mul(velocity, dt.asSeconds()));
    }

    public Vector2f calculateForce(List<Bird> nearbyBirds){
        return motionHandler.calculateForce(nearbyBirds);
    }

    public float getMaxSpeed(){
        return maxSpeed;
    }

    public void setMaxSpeed(float maxSpeed){
        this.maxSpeed = maxSpeed;
    }

    public void setMotionHandler(BirdMotionHandler fh){
        this.motionHandler = fh;
    }

    public void setSpecies(BirdSpecies species){
        this.species = species;
    }

    public BirdSpecies getSpecies(){
        return species;
    }

    public void setFriction(float friction){
        this.friction = friction;
    }

    public void setLocalityRadius(float r){
        this.localityRadius = r;
    }

    public float getLocalityRadius(){
        return localityRadius;
    }


}
