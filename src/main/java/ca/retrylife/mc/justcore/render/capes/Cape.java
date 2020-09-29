package ca.retrylife.mc.justcore.render.capes;

import java.net.URL;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.IOException;

import ca.retrylife.mc.justcore.utils.CapeUtils;
import ca.retrylife.mc.justcore.utils.texture.DynamicTextureUtils;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.util.Identifier;

/**
 * A wrapper around the player cape
 */
public class Cape {

    // Texture
    private NativeImage textureImage;
    private String textureName;
    private Identifier texture;

    /**
     * Create a new cape from a URL to it's texture
     * 
     * @param remoteTexture
     * @throws IOException Texture resource URL
     */
    public Cape(URL remoteTexture) throws IOException {
        // Load the image from its remote stream
        NativeImage nativeRemoteImage = NativeImage.read(remoteTexture.openStream());

        // Get a cropped texture for the cape
        NativeImage croppedTexture = CapeUtils.cleanCapeTexture(nativeRemoteImage);

        // Register a new texture with the Minecraft client
        this.textureName = "CAPE_" + Integer.toString(nativeRemoteImage.hashCode());
        this.textureImage = croppedTexture;
    }

    /**
     * Create a cape from an image
     * 
     * @param name  Texture name
     * @param image Image
     */
    public Cape(String name, NativeImage image) {
        // Get a cropped texture for the cape
        NativeImage croppedTexture = CapeUtils.cleanCapeTexture(image);

        // Register a new texture with the Minecraft client
        this.textureName = name;
        this.textureImage = croppedTexture;
    }

    /**
     * Get the cape texture
     * 
     * @return Texture
     */
    public Identifier getTexture() {

        // If this is the first load, register the texture
        if (this.texture == null) {
            this.texture = DynamicTextureUtils.registerTextureFromImage(textureName, textureImage);
        }
        return this.texture;
    }

}