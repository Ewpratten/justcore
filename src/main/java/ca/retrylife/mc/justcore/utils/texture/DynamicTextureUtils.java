package ca.retrylife.mc.justcore.utils.texture;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

/**
 * Utilities for working with dynamic textures
 */
public class DynamicTextureUtils {

    /**
     * Registers a new texture with the minecraft client from an image
     * 
     * @param id    Texture ID
     * @param image Image
     * @return Texture Identifier
     */
    public static Identifier registerTextureFromImage(String id, NativeImage image) {
        return MinecraftClient.getInstance().getTextureManager().registerDynamicTexture(id,
                new NativeImageBackedTexture(image));
    }
}