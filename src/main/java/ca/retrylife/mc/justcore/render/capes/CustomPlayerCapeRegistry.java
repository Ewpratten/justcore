package ca.retrylife.mc.justcore.render.capes;

import java.util.HashMap;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.retrylife.mc.justcore.model.JustPlayer;

/**
 * A registry of custom capes for players
 */
public class CustomPlayerCapeRegistry {
    private Logger logger = LogManager.getLogger(getClass());
    private static CustomPlayerCapeRegistry instance;

    // Registry map
    private HashMap<JustPlayer, CapeContainer> capeRegistry = new HashMap<>();

    private CustomPlayerCapeRegistry() {

    }

    public static CustomPlayerCapeRegistry getInstance() {
        if (instance == null) {
            instance = new CustomPlayerCapeRegistry();
        }
        return instance;
    }

    /**
     * Overwrite a player's cape with a custom one
     * 
     * @param player  Player
     * @param newCape New cape
     */
    public void overwritePlayerCape(JustPlayer player, Cape newCape) {
        // Construct a container for the cape
        CapeContainer container = new CapeContainer();
        container.cape = newCape;

        // Add the cape for the player
        capeRegistry.put(player, container);
        logger.log(Level.INFO, String.format("Player %s has been given a custom cape", player.getNameOrId()));
    }

    /**
     * Set a player's custom cape visibility. If they have a minecon cape, it will
     * be show when the custom cape is not
     * 
     * @param player  Player
     * @param visible Should show custom cape?
     */
    public void setPlayerCapeVisibility(JustPlayer player, boolean visible) {
        // Ensure the player is in the registry
        if (!capeRegistry.containsKey(player)) {
            logger.log(Level.WARN,
                    String.format(
                            "Player %s is not in the cape registry, but a call was made to set their cape visibility",
                            player.getNameOrId()));
            return;
        }

        // Set the visibility
        capeRegistry.get(player).visible = visible;
        logger.log(Level.INFO,
                String.format("Player %s had their cape visibility set to %b", player.getNameOrId(), visible));
    }

    /**
     * Check if a player has a custom cape
     * 
     * @param player Player
     * @return Has custom cape?
     */
    public boolean checkPlayerHasCustomCape(JustPlayer player) {
        return capeRegistry.containsKey(player);
    }

    /**
     * Get the custom cape for a player
     * 
     * @param player Player
     * @return Custom cape
     */
    public Cape getCapeForPlayer(JustPlayer player) {
        return capeRegistry.get(player).cape;
    }

    /**
     * Get if a player's custom cape should be shown
     * 
     * @param player Player
     * @return Should show cape?
     */
    public boolean checkCustomCapeVisibleForPlayer(JustPlayer player) {
        return capeRegistry.get(player).visible;
    }

}