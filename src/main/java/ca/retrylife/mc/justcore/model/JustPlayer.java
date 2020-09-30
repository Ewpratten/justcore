package ca.retrylife.mc.justcore.model;

import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.PropertyMap;

/**
 * Player model. An extension of the Minecraft game profile
 */
public class JustPlayer extends GameProfile {

    // For use by copy constructor
    private PropertyMap properties = null;
    private Boolean legacy = null;

    /**
     * Copy constructor
     * 
     * @param profile GameProfile
     */
    public JustPlayer(GameProfile profile) {
        this(profile.getId(), profile.getName());

        // Fill in other data
        this.properties = profile.getProperties();
        this.legacy = profile.isLegacy();
    }

    /**
     * Create a player from their UUID
     * 
     * @param id Player UUID
     */
    public JustPlayer(UUID id) {
        this(id, null);
    }

    /**
     * Create a player from their username
     * 
     * @param name Username
     */
    public JustPlayer(String name) {
        this(null, name);
    }

    /**
     * Create a player manually
     * 
     * @param id   Player UUID
     * @param name Player name
     */
    public JustPlayer(UUID id, String name) {
        super(id, name);
    }

    /**
     * Check if this player has a UUID
     * 
     * @return Has UUID?
     */
    public boolean hasUUID() {
        return getId() != null;
    }

    /**
     * Check if this player has a name
     * 
     * @return Has name?
     */
    public boolean hasName() {
        return getName() != null;
    }

    @Override
    public PropertyMap getProperties() {
        if (this.properties != null) {
            return this.properties;
        }
        return super.getProperties();
    }

    @Override
    public boolean isLegacy() {
        if (this.legacy != null) {
            return this.legacy;
        }
        return super.isLegacy();
    }

    /**
     * Get the player name or fall back to their UUID
     * 
     * @return Name or UUID
     */
    public String getNameOrId() {
        return (hasName()) ? getName() : getId().toString();
    }

}