import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Shape;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

/**
 * Created by smithma on 22/01/15.
 */
public abstract class Actor {
    protected Shape shape;
    protected Vector2f velocity = Vector2f.ZERO;
    protected float mass = 0;

    abstract public void update(Time dt);

    public void render(RenderWindow window){
        window.draw(shape);
    }

    public void applyForce(Vector2f dir, float magnitude){
        velocity = Vector2f.add(velocity, Vector2f.mul(dir, (magnitude / mass)));
    }

    public Vector2f getPosition(){
        return shape.getPosition();
    }

    public void setPosition(Vector2f pos){
        shape.setPosition(pos);
    }

    public void setVelocity(Vector2f vel){
        this.velocity = vel;
    }

    public Vector2f getVelocity(){
        return velocity;
    }

    public void setShape(Shape shape){
        this.shape = shape;
    }

    public void setMass(float mass){
        this.mass = mass;
    }

    public float getHeight(){
        return shape.getGlobalBounds().height;
    }

    public float getWidth(){
        return shape.getGlobalBounds().width;
    }

    public float getTop(){
        return shape.getGlobalBounds().top;
    }

    public float getLeft(){
        return shape.getGlobalBounds().left;
    }





}
