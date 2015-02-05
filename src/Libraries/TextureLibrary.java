package Libraries;

import org.jsfml.graphics.Texture;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Matthew on 26/02/14.
 */
public class TextureLibrary {

    private static HashMap<String, Texture> textureHash = new HashMap<String, Texture>();

    public static Texture getTexture(String label){

        Texture texture = new Texture();
        boolean found = false;

        for(Map.Entry<String, Texture> entry : textureHash.entrySet()){

            //if texture is already in the hash, return it
            if(entry.getKey().equals(label)){
                texture = entry.getValue();
                found = true;
            }
        }

        //if texture is not in the hash, add it
        if(!found){
            try{
                texture.loadFromFile(Paths.get("resources" + File.separatorChar + "textures" + File.separatorChar + label + ".png" ));
                textureHash.put(label, texture);
            } catch(IOException e){
                e.printStackTrace();
                System.err.println("Cannot find texture '" + label + ".png' in resources/textures");
            }
        }
        return texture;
    }

}
