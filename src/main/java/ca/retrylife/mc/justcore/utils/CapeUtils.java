package ca.retrylife.mc.justcore.utils;

import net.minecraft.client.texture.NativeImage;

/**
 * Utilities for working with capes
 */
public class CapeUtils {

    /**
     * Takes a texture, and crops it to match the cape texture sizing (this is a
     * copy operation)
     * 
     * @param image Source image
     * @return New cropped image
     */
    public static NativeImage cleanCapeTexture(NativeImage image) {

        // Bounds of a cape texture
        int capeTextureWidth = 64;
        int capeTextureHeight = 32;

        // Handle scaling the texture
        while (capeTextureWidth < image.getWidth() || capeTextureHeight < image.getHeight()) {
            capeTextureWidth *= 2;
            capeTextureHeight *= 2;
        }

        // Define a new, properly sized image
        NativeImage outputImage = new NativeImage(capeTextureWidth, capeTextureHeight, true);

        // Copy the source image over the new one
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                // Set the new pixel
                outputImage.setPixelColor(x, y, image.getPixelColor(x, y));
            }
        }

        // Return the new image
        image.close();
        return outputImage;
    }

}