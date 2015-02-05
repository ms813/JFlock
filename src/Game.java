import GUI.GUI;
import GUI.GuiButton;
import GUI.BtnFunction;
import Libraries.TextureLibrary;
import org.jsfml.graphics.Color;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.Sprite;
import org.jsfml.graphics.View;
import org.jsfml.system.Clock;
import org.jsfml.system.Time;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smithma on 22/01/15.
 */
public class Game {

    private Vector2i windowSize = new Vector2i(1200, 600);
    private RenderWindow window = new RenderWindow();

    private GUI gui = new GUI();

    private View view;
    private View bgView = new View();
    private Sprite bg = new Sprite(TextureLibrary.getTexture("skybg"));
    private float colorCycleRate = 0.05f;
    private boolean colorCycle = false;

    private int sparrowCount = 200;
    private int hawkCount = 5;

    private static final Time timePerFrame = Time.getSeconds(1.0f/60.0f);
    private static int frameCount = 0;

    private List<Bird> birds = new ArrayList<Bird>();

    private void init(){
        window.create(new VideoMode(windowSize.x, windowSize.y), "JFlock");

        view = (View) window.getView();

        BirdFactory birdFactory = new BirdFactory(windowSize);
        for(int i = 0; i < sparrowCount; i++){
            birds.add(birdFactory.createBird(BirdSpecies.SPARROW));
        }

        for(int i = 0; i < hawkCount; i++){
            birds.add(birdFactory.createBird(BirdSpecies.HAWK));
        }

        createGUI();
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
        gui.processEvents();
        for (Event event : window.pollEvents()) {
            if (event.type == Event.Type.CLOSED) {
                window.close();
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
        gui.render();
        window.clear();

        window.setView(bgView);
        window.draw(bg);
        window.setView(view);

        for(Actor a : birds){
            a.render(window);
        }
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

    private void createGUI(){
        gui.addButton(new GuiButton("Sparrow +", new BtnFunction() {
            @Override
            public void onClick() {
                System.out.println("Sparrow +");
            }
        }));

        gui.addButton(new GuiButton("Sparrow -", new BtnFunction() {
            @Override
            public void onClick() {
                System.out.println("Sparrow -");
            }
        }));

        gui.addButton(new GuiButton("Hawk +", new BtnFunction() {
            @Override
            public void onClick() {
                System.out.println("Hawk +");
            }
        }));
        gui.pack();
    }

    private Color colorCycle(){
        int r = (int) Math.round(Math.sin(colorCycleRate * frameCount * (Math.PI / 180)) * 255);
        int g = (int) Math.round(Math.sin(colorCycleRate / 2 * frameCount * (Math.PI / 180)) * 255);
        int b = (int) Math.round(Math.sin(colorCycleRate / 3 * frameCount * (Math.PI / 180)) * 255);
        return new Color(r, g, b, 255);
    }

}
