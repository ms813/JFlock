package GUI;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Mouse;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smithma on 05/02/15.
 */
public class GUI {
    private Vector2i guiWindowSize = new Vector2i(218, 330);
    private RenderWindow guiWindow = new RenderWindow();

    List<GuiButton> buttons = new ArrayList<GuiButton>();

    public GUI() {
        guiWindow.create(new VideoMode(guiWindowSize.x, guiWindowSize.y), "JFlock Control Panel");
    }

    public void pack(){
        float cols = 2.0f;

        if(buttons.size() > 0){
            for(int i = 0; i < buttons.size(); i++){
                /*TODO fix me!*/
                GuiButton b = buttons.get(i);
                b.setSize(new Vector2f(guiWindowSize.x / cols, guiWindowSize.y / (buttons.size() / cols)));

                float x = (i % cols) * (guiWindowSize.x / cols) + b.getOutlineThickness() * (i % cols + 1);
                float y = (i % (buttons.size()/cols)) * (guiWindowSize.y / (buttons.size()/2)) + b.getOutlineThickness();
                System.out.println(x + ", " + y);
                b.setPosition(new Vector2f(x, y));
            }
        }
    }

    public void addButton(GuiButton btn){
        buttons.add(btn);
    }

    public void render() {
        guiWindow.clear();
        for (GuiButton b : buttons) {
            b.render(guiWindow);
        }
        guiWindow.display();
    }

    public void update() {
        for (GuiButton b : buttons) {
            b.update();
        }
    }

    public void processEvents() {
        for (Event e : guiWindow.pollEvents()) {
            if (e.type == Event.Type.CLOSED) {
                guiWindow.close();
            }

            if (e.type == Event.Type.MOUSE_MOVED) {
                processMouseMoveEvent(getHoveredButton(new Vector2f(Mouse.getPosition(guiWindow))));
            }

            if(e.type == Event.Type.MOUSE_BUTTON_PRESSED){
                processMouseDownEvent(getHoveredButton(new Vector2f(Mouse.getPosition(guiWindow))));
            }
        }
    }

    private void processMouseMoveEvent(GuiButton btn) {
        for(GuiButton b : buttons){
            b.setMouseOver(false);
        }
        if(btn != null){
            btn.setMouseOver(true);
        }
    }

    private void processMouseDownEvent(GuiButton btn){
        if(btn != null){
            btn.onClick();
        }
    }

    private GuiButton getHoveredButton(Vector2f mousePos) {
        GuiButton btn = null;

        for (GuiButton b : buttons) {
            if (mousePos.x > b.getPosition().x && mousePos.x < b.getPosition().x + b.getWidth() && mousePos.y > b.getPosition().y && mousePos.y < b.getPosition().y + b.getHeight()) {
                btn = b;
            }
        }

        return btn;
    }
}
