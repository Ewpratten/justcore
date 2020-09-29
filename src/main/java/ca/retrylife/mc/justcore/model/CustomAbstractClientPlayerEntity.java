package ca.retrylife.mc.justcore.model;

import com.mojang.authlib.GameProfile;

import ca.retrylife.mc.justcore.render.capes.Cape;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;

/**
 * CustomAbstractClientPlayerEntity is a class that allows us to overwrite
 * information about a player at render time
 */
public class CustomAbstractClientPlayerEntity extends AbstractClientPlayerEntity {

    // Custom cape texture override
    private Identifier customCapeTextureIdentifier = null;

    public CustomAbstractClientPlayerEntity(ClientWorld world, GameProfile profile) {
        super(world, profile);
    }

    /**
     * Override the player cape with that of a custom cape object
     * 
     * @param cape Custom cape object
     */
    public void setCustomCapeTexture(Cape cape) {
        setCustomCapeTexture(cape.getTexture());
    }

    /**
     * Override a player cape texture
     * 
     * @param texture New cape texture
     */
    public void setCustomCapeTexture(Identifier texture) {
        this.customCapeTextureIdentifier = texture;
    }

    @Override
    public Identifier getCapeTexture() {
        // Select the correct cape texture for the player
        return (customCapeTextureIdentifier != null) ? customCapeTextureIdentifier : super.getCapeTexture();
    }

}