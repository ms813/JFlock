import com.sun.java.swing.plaf.windows.resources.windows;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.Texture;
import org.jsfml.graphics.View;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by smithma on 22/01/15.
 */
public class Game {

    private Vector2i windowSize = new Vector2i(600, 250);
    private RenderWindow window = new RenderWindow();
    private View view;
    private View bgView = new View();
    private Sprite bg = new Sprite(loadTexture("skybg.jpg"));

    private static final Time timePerFrame = Time.getSeconds(1f/60f);

    private List<Bird> birds = new ArrayList<Bird>();

    private void init(){
        window.create(new VideoMode(windowSize.x, windowSize.y), "JFlock");
        view = (View) window.getView();

        BirdFactory birdFactory = new BirdFactory(windowSize);
        for(int i = 0; i < 100; i++){
            birds.add(birdFactory.createBird(BirdSpecies.SPARROW));
        }
        //birds.add(birdFactory.createBird(BirdSpecies.HAWK));
    }

    private Texture loadTexture(String textureName){
        Texture t = new Texture();
        try{
            t.loadFromFile(Paths.get("resources" + File.separatorChar + textureName));
            t.setRepeated(true);
        }catch (IOException e){
            e.printStackTrace();
        }
        return t;
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
        Vector2f sumPos = Vector2f.ZERO;

        for(int i = 0; i < birds.size(); i++){
            Bird a = birds.get(i);
            List<Bird> nearbyBirds = new ArrayList<Bird>();

            sumPos = Vector2f.add(sumPos, a.getPosition());

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

            Vector2f flockForce = a.getFlockForce(nearbyBirds);
            a.applyForce(VMath.normalize(flockForce), VMath.magnitude(flockForce));

            //Vector2f edgeForce = a.getEdgeForce(windowSize, a.getPosition());
            //a.applyForce(VMath.normalize(edgeForce), VMath.magnitude(edgeForce));

            a.update(dt);
            //edgeWrap(a);
        }

        view.setCenter(Vector2f.div(sumPos, birds.size()));
    }

    private void render(){
        window.clear();
                
        window.setView(bgView);
        window.draw(bg);

        window.setView(view);
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
