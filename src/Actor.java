import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;

/**
 * Created by smithma on 22/01/15.
 */
public abstract class Actor {
    protected Sprite sprite;
    protected Vector2f velocity = Vector2f.ZERO;

    abstract public void update(Time dt);

    abstract void render(RenderWindow window);

    abstract public void applyForce(Vector2f dir, float magnitude);

    public Vector2f getPosition(){
        return sprite.getPosition();
    }

    public void setPosition(Vector2f pos){
        sprite.setPosition(pos);
    }

    public void setVelocity(Vector2f vel){
        this.velocity = vel;
    }

    public Vector2f getVelocity(){
        return velocity;
    }

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
    }

    public float getHeight(){
        return sprite.getGlobalBounds().height;
    }

    public float getWidth(){
        return sprite.getGlobalBounds().width;
    }

    public float getTop(){
        return sprite.getGlobalBounds().top;
    }

    public float getLeft(){
        return sprite.getGlobalBounds().left;
    }

    public FloatRect getGlobalBounds(){ return sprite.getGlobalBounds(); }





}
