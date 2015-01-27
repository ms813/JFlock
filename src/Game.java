import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import sun.misc.VM;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smithma on 22/01/15.
 */
public class Game {

    private Vector2i windowSize = new Vector2i(200,150);
    private RenderWindow window = new RenderWindow();

    private static final Time timePerFrame = Time.getSeconds(1f/60f);

    private List<Bird> birds = new ArrayList<Bird>();

    private void init(){
        window.create(new VideoMode(windowSize.x, windowSize.y), "JFlock");
        BirdFactory birdFactory = new BirdFactory(windowSize);
        for(int i = 0; i < 20; i++){
            birds.add(birdFactory.createBird(BirdSpecies.SPARROW));
        }
        birds.add(birdFactory.createBird(BirdSpecies.HAWK));
    }

    public void run(){
        init();
        Clock clock = new Clock();
        Time timeSinceLastUpdate = Time.ZERO;


        while(window.isOpen()){
            Time elapsedTime = clock.restart();
            timeSinceLastUpdate = Time.add(timeSinceLastUpdate, elapsedTime);

            while(timeSinceLastUpdate.asSeconds() > timePerFrame.asSeconds()){
                timeSinceLastUpdate = Time.sub(timeSinceLastUpdate, timePerFrame);
                processEvents();
                update(timePerFrame);
            }

            render();
        }
    }

    private void processEvents(){
        for (Event event : window.pollEvents()) {
            if (event.type == Event.Type.CLOSED) {
                window.close();
            }
        }
    }
    private void update(Time dt){
        for(int i = 0; i < birds.size(); i++){
            Bird a = birds.get(i);
            List<Bird> nearbyBirds = new ArrayList<Bird>();

            for(int j = 0; j < birds.size(); j++){
                Bird b = birds.get(j);
                //don't count yourself in the local cluster
                if(i != j){
                    Vector2f dir = Vector2f.sub(a.getPosition(), b.getPosition());
                    float dist = VMath.magnitude(dir);
                    if(dist < a.getLocalityRadius()){
                        nearbyBirds.add(b);
                    }
                }
            }

            Vector2f force = a.calculateForce(nearbyBirds);
            a.applyForce(VMath.normalize(force), VMath.magnitude(force));

            a.update(dt);
            edgeWrap(a);
        }
    }
    private void render(){
        window.clear();
        for(Actor a : birds){
            a.render(window);
        }
        window.display();
    };

    private void edgeWrap(Actor a){
        Vector2f pos = a.getPosition();
        float height = a.getHeight(), width = a.getWidth();
        float x = pos.x, y = pos.y;

        if(pos.x < 0){
            x = windowSize.x;
            System.out.println("wrap left");
        }

        if(pos.x > windowSize.x){
            x = 0;
            System.out.println("wrap right");
        }

        if(pos.y < 0){
            y = windowSize.y;
            System.out.println("wrap top");
        }

        if(pos.y > windowSize.y){
            y = 0;
            System.out.println("wrap bottom");
        }
        if(pos.x != x || pos.y != y){
            a.setPosition(new Vector2f(x, y));
        }

    }
}
