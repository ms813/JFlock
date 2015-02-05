package GUI;

import Libraries.FontLibrary;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;
import org.jsfml.system.Vector2i;

/**
 * Created by smithma on 05/02/15.
 */
public class GuiButton {

    BtnFunction funct;

    private RectangleShape shape = new RectangleShape(new Vector2f(0,0));
    private Text label = new Text();
    private boolean mouseOver = false;
    private boolean highlight= false;

    public GuiButton(String label, BtnFunction funct){

        this.funct = funct;

        this.shape.setFillColor(Color.BLUE);
        shape.setOutlineThickness(3.0f);
        shape.setOutlineColor(Color.TRANSPARENT);

        this.label.setString(label);
        this.label.setFont(FontLibrary.getFont("arial"));
        this.label.setColor(Color.WHITE);
        this.label.setCharacterSize(10);
        updateLabelPos();
    }

    public void render(RenderWindow window){
        window.draw(shape);
        window.draw(label);
    }

    public void update(){
        if(mouseOver && !highlight){
            highlight(true);
        }

        if(!mouseOver && highlight){
            highlight(false);
        }
    }

    private void highlight(boolean b){
        if(b){
            highlight = true;
            shape.setOutlineColor(Color.WHITE);
        } else{
            highlight = false;
            shape.setOutlineColor(Color.TRANSPARENT);
        }
    }

    public void onClick(){
        funct.onClick();
    }

    public Vector2f getPosition(){
        return shape.getPosition();
    }

    public float getWidth(){
        return shape.getGlobalBounds().width;
    }

    public float getHeight(){
        return shape.getGlobalBounds().height;
    }

    public void setMouseOver(boolean b){
        this.mouseOver = b;
    }

    public void setPosition(Vector2f pos){
        shape.setPosition(pos);
        updateLabelPos();
    }

    private void updateLabelPos(){
        label.setPosition(Vector2f.add(shape.getPosition(), new Vector2f(15, 10)));
    }

    public float getOutlineThickness(){
        return shape.getOutlineThickness();
    }

    public void setSize(Vector2f size){
        shape.setSize(size);
    }

}
