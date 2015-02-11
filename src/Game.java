import Gui.Gui;
import Gui.BtnFunction;
import Libraries.TextureLibrary;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.View;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.WindowStyle;
import org.jsfml.window.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smithma on 22/01/15.
 */
public class Game {

    private Vector2i windowSize = new Vector2i(1000, 500);
    private RenderWindow window = new RenderWindow();

    private Gui gui;

    private View view;
    private View bgView = new View();
    private Sprite bg = new Sprite(TextureLibrary.getTexture("skybg"));
    private float colorCycleRate = 0.05f;
    private boolean colorCycle = false;

    private int startingSparrows = 1;
    private int sparrowLimit = 250;
    private int startingHawks = 0;
    private int hawkLimit = 50;

    private static final Time timePerFrame = Time.getSeconds(1.0f/60.0f);
    private static int frameCount = 0;

    private List<Bird> birds = new ArrayList<Bird>();

    private void init(){
        window.create(new VideoMode(windowSize.x, windowSize.y), "JFlock", WindowStyle.NONE);

        view = (View) window.getView();

        setupGui();

        BirdFactory birdFactory = new BirdFactory(windowSize);
        for(int i = 0; i < startingSparrows; i++){
            birds.add(birdFactory.createBird(BirdSpecies.SPARROW));
        }

        for(int i = 0; i < startingHawks; i++){
            birds.add(birdFactory.createBird(BirdSpecies.HAWK));
        }
    }

    private void setupGui(){
        gui = new Gui(window);

        gui.addButton("Sparrow +", new BtnFunction() {
            @Override
            public void onClick() {
                int sparrowCount = 0;
                for(Bird b : birds){
                    if(b.getSpecies() == BirdSpecies.SPARROW){
                        sparrowCount++;
                    }
                }

                if(sparrowCount < sparrowLimit){
                    BirdFactory bf = new BirdFactory(windowSize);
                    birds.add(bf.createBird(BirdSpecies.SPARROW));
                    System.out.println("Adding a sparrow. There are now " + (sparrowCount + 1) + " sparrows.");
                } else{
                    System.out.println("Cannot add more than " + sparrowLimit + " sparrows!");
                }

            }
        });

        gui.addButton("Sparrow -", new BtnFunction() {
            @Override
            public void onClick() {
                int sparrowCount = 0;
                int lastIndex = 0;
                for(int i = 0; i < birds.size(); i++){
                    if(birds.get(i).getSpecies() == BirdSpecies.SPARROW){
                        sparrowCount++;
                        lastIndex = i;
                    }
                }

                if(sparrowCount > 0){
                    birds.remove(lastIndex);
                    System.out.println("Sparrow removed. There are now " + (sparrowCount - 1) + " sparrows remaining.");
                } else{
                    System.out.println("There are no sparrows left to remove!");
                }
            }
        });

        gui.addButton("Hawk +", new BtnFunction() {
            @Override
            public void onClick() {
                int hawkCount = 0;
                for(Bird b : birds){
                    if(b.getSpecies() == BirdSpecies.HAWK){
                        hawkCount++;
                    }
                }

                if(hawkCount < hawkLimit){
                    BirdFactory bf = new BirdFactory(windowSize);
                    birds.add(bf.createBird(BirdSpecies.HAWK));
                    System.out.println("Adding a hawk. There are now " + (hawkCount + 1) + " hawks.");
                } else{
                    System.out.println("Cannot add more than " + hawkLimit + " hawks!");
                }

            }
        });

        gui.addButton("Hawk -", new BtnFunction() {
            @Override
            public void onClick() {
                int hawkCount = 0;
                int lastIndex = 0;
                for(int i = 0; i < birds.size(); i++){
                    if(birds.get(i).getSpecies() == BirdSpecies.HAWK){
                        hawkCount++;
                        lastIndex = i;
                    }
                }

                if(hawkCount > 0){
                    birds.remove(lastIndex);
                    System.out.println("Hawk removed. There are now " + (hawkCount - 1) + " hawks remaining.");
                } else{
                    System.out.println("There are no hawks left to remove!");
                }
            }
        });

        gui.pack(window);
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

            if (event.type == Event.Type.MOUSE_MOVED) {
                gui.processMouseMoveEvent(new Vector2f(Mouse.getPosition(window)));
            }

            if(event.type == Event.Type.MOUSE_BUTTON_PRESSED){
                gui.processMouseDownEvent(new Vector2f(Mouse.getPosition(window)));
            }
        }
    }

    private void update(Time dt){

        gui.update();

        if(colorCycle){
            bg.setColor(colorCycle());
        }
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

            a.update(dt);
            edgeWrap(a);
        }

        //view.setCenter(Vector2f.div(sumPos, birds.size()));
    }

    private void render(){

        window.clear();

        window.setView(bgView);
        window.draw(bg);

        window.setView(view);

        for(Actor a : birds){
            a.render(window);
        }

        gui.render(window);

        window.display();
        frameCount++;
    };


    private void edgeWrap(Actor a){
        Vector2f pos = a.getPosition();
        float height = a.getHeight(), width = a.getWidth();
        float x = pos.x, y = pos.y;

        if(pos.x < 0){
            x = windowSize.x;
            //System.out.println("wrap left");
        }

        if(pos.x > windowSize.x){
            x = 0;
            //System.out.println("wrap right");
        }

        if(pos.y < 0){
            y = windowSize.y;
            //System.out.println("wrap top");
        }

        if(pos.y > windowSize.y){
            y = 0;
            //System.out.println("wrap bottom");
        }
        if(pos.x != x || pos.y != y){
            a.setPosition(new Vector2f(x, y));
        }
    }

    private Color colorCycle(){
        int r = (int) Math.round(Math.sin(colorCycleRate * frameCount * (Math.PI / 180)) * 255);
        int g = (int) Math.round(Math.sin(colorCycleRate / 2 * frameCount * (Math.PI / 180)) * 255);
        int b = (int) Math.round(Math.sin(colorCycleRate / 3 * frameCount * (Math.PI / 180)) * 255);
        return new Color(r, g, b, 255);
    }

}
