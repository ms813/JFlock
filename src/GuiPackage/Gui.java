package GuiPackage;

import org.jsfml.graphics.FloatRect;
import org.jsfml.graphics.RenderWindow;
import org.jsfml.graphics.View;
import org.jsfml.system.Vector2f;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smithma on 11/02/15.
 */
public class Gui {
    private View guiView;
    private float height = 0.1f, width = 1.0f;
    private int cols = 6;

    private List<GuiButton> buttons = new ArrayList<GuiButton>();

    public Gui(RenderWindow window){
        float h = height * window.getSize().y;
        float w = width * window.getSize().x;
        guiView = new View(new FloatRect(0, 0, window.getSize().x, window.getSize().y));
    }

    public void render(RenderWindow window){
        window.setView(guiView);
        for(GuiButton b : buttons){
            b.render(window);
        }
    }

    public void update(){
        for(GuiButton b : buttons){
            b.update();
        }
    }

    public void addButton(String lbl, BtnFunction funct){
        buttons.add(new GuiButton(lbl, funct));
    }

    public void pack(RenderWindow window){
        float w = width * window.getSize().x / (buttons.size() < cols ? buttons.size() : cols);
        float h = (window.getSize().y * height) / (float)Math.ceil((float)buttons.size() / cols);

        for(int i = 0; i < buttons.size(); i++){
            GuiButton b = buttons.get(i);
            b.setSize(new Vector2f(w, h));
            float left = (float) Math.floor(i % cols) * w;
            float top =  (float) Math.floor(i / cols) * h;
            System.out.println(buttons.get(i).getLabel() + ", " + left + ", " + top);
            b.setPosition(new Vector2f(left, top));
        }
    }

    public void processMouseMoveEvent(Vector2f mousePos) {
        GuiButton hoveredBtn = getHoveredButton(mousePos);
        for(GuiButton b : buttons){
            b.setMouseOver(false);
        }
        if(hoveredBtn != null){
            hoveredBtn.setMouseOver(true);
        }
    }

    public void processMouseDownEvent(Vector2f mousePos){
        GuiButton clickedBtn = getHoveredButton(mousePos);
        if(clickedBtn != null){
            clickedBtn.onClick();
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
