package ca.retrylife.mc.justcore.render.cosmetics;

import java.util.HashMap;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.retrylife.mc.justcore.model.JustPlayer;

public class CustomPlayerCosmeticsRegistry {

    private Logger logger = LogManager.getLogger(getClass());
    private static CustomPlayerCosmeticsRegistry instance;

    // Registry map
    private HashMap<String, CosmeticEffect> cosmeticsRegistry = new HashMap<>();

    private CustomPlayerCosmeticsRegistry() {

    }

    public static CustomPlayerCosmeticsRegistry getInstance() {
        if (instance == null) {
            instance = new CustomPlayerCosmeticsRegistry();
        }
        return instance;
    }

    /**
     * Add a cosmetic effect to a player
     * 
     * @param player Player
     * @param effect Cosmetic effect
     */
    public void addCosmeticEffectToPlayer(JustPlayer player, CosmeticEffect effect) {
        // Handle this being the first effect for this player
        if (!cosmeticsRegistry.containsKey(player.getNameOrId())) {
            cosmeticsRegistry.put(player.getNameOrId(), effect);
        } else {
            // Add to the current effect
            cosmeticsRegistry.get(player.getNameOrId()).apply(effect);
        }

        logger.log(Level.INFO, "Added new cosmetic effect to player " + player.getNameOrId());
    }

    /**
     * Remove a cosmetic effect from a player
     * 
     * @param player Player
     * @param effect Cosmetic effect
     */
    public void removeCosmeticEffectToPlayer(JustPlayer player, CosmeticEffect effect) {
        // Handle no effects being applied
        if (!cosmeticsRegistry.containsKey(player.getNameOrId())) {
            return;
        } else {
            // Remove from the current effect
            cosmeticsRegistry.get(player.getNameOrId()).remove(effect);
        }

        logger.log(Level.INFO, "Removed cosmetic effect from player " + player.getNameOrId());

    }

    /**
     * Check if any effects are applied to a player
     * 
     * @param player Player to check
     * @return Are any effects applied?
     */
    public boolean checkIfPlayerHasAnyEffects(JustPlayer player) {
        return cosmeticsRegistry.containsKey(player.getNameOrId())
                && cosmeticsRegistry.get(player.getNameOrId()).hasAnyEffect();
    }

    /**
     * Get all cosmetic effects applied to a player
     * 
     * @param player Player
     * @return Applied effects
     */
    public CosmeticEffect getEffectsAppliedToPlayer(JustPlayer player) {
        return cosmeticsRegistry.get(player.getNameOrId());
    }

}