package Libraries;

import org.jsfml.graphics.Font;
import org.jsfml.graphics.Texture;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matthew on 26/02/14.
 */
public class FontLibrary {

    public static HashMap<String, Font> fontLibrary = new HashMap<String, Font>();

    public static Font getFont(String label){

        Font font = new Font();
        boolean found = false;

        for(Map.Entry<String, Font> entry : fontLibrary.entrySet()){

            //if font is already in the hash, return it
            if(entry.getKey().equals(label)){
                font = entry.getValue();
                found = true;
            }
        }

        //if texture is not in the hash, add it
        if(!found){
            try{
                font.loadFromFile(Paths.get("resources" + File.separatorChar + "fonts" + File.separatorChar + label + ".ttf"));
                fontLibrary.put(label, font);
            } catch(IOException e){
                e.printStackTrace();
                System.err.println("Cannot find font '" + label + ".ttf' in resources/fonts");
            }
        }
        return font;
    }

}
