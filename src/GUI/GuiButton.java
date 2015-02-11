package Gui;

import Libraries.FontLibrary;
import org.jsfml.graphics.*;
import org.jsfml.system.Vector2f;

/**
 * Created by smithma on 05/02/15.
 */
public class GuiButton {

    BtnFunction funct;
    int borderThickness = 3;

    private RectangleShape shape = new RectangleShape(new Vector2f(0,0));
    private Text label = new Text();
    private boolean mouseOver = false;
    private boolean highlight= false;

    public GuiButton(String label, BtnFunction funct){

        this.funct = funct;

        this.shape.setFillColor(Color.BLACK);
        shape.setOutlineThickness(borderThickness);
        shape.setOutlineColor(Color.TRANSPARENT);

        this.label.setString(label);
        this.label.setFont(FontLibrary.getFont("arial"));
        this.label.setColor(Color.WHITE);
        this.label.setStyle(TextStyle.BOLD);
        this.label.setOrigin(getCenter(this.label));
        updateLabelPos();
    }

    public void render(RenderWindow window){
        window.draw(shape);
        window.draw(label);
    }

    public Vector2f getCenter(Text t){
        return new Vector2f(t.getGlobalBounds().width/2, t.getGlobalBounds().height/2);
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
        shape.setPosition(Vector2f.add(pos, new Vector2f(borderThickness, borderThickness)));
        updateLabelPos();
    }

    public void setPosition(float x, float y){
        shape.setPosition(x + borderThickness, y + borderThickness);
    }

    private void updateLabelPos(){
        label.setCharacterSize(Math.round(shape.getSize().y / 3));
        label.setOrigin(getCenter(this.label));
        label.setPosition(shape.getGlobalBounds().left + shape.getGlobalBounds().width/ 2, shape.getGlobalBounds().top + shape.getGlobalBounds().height/ 2);
    }

    public float getOutlineThickness(){
        return shape.getOutlineThickness();
    }

    public void setSize(Vector2f size){
        shape.setSize(Vector2f.sub(size, new Vector2f(borderThickness, borderThickness)));
        updateLabelPos();
    }

    public void setSize(float w, float h){
        setSize(new Vector2f(w, h));
    }

    public String getLabel(){
        return label.getString();
    }

}
