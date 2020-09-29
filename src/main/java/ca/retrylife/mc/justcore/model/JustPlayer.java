package ca.retrylife.mc.justcore.model;

import java.util.UUID;

import com.mojang.authlib.GameProfile;

/**
 * Player model. An extension of the Minecraft game profile
 */
public class JustPlayer extends GameProfile {

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

    /**
     * Get the player name or fall back to their UUID
     * 
     * @return Name or UUID
     */
    public String getNameOrId() {
        return (hasName()) ? getName() : getId().toString();
    }

}