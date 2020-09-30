package ca.retrylife.mc.api;

import ca.retrylife.mc.justcore.model.JustPlayer;
import net.minecraft.client.MinecraftClient;

/**
 * Interact with yourself
 */
public class ThePlayer {

    // Instance
    private static ThePlayer instance;
    private MinecraftClient mc;

    // Player profile
    private JustPlayer playerProfile;

    private ThePlayer() {

        // Get access to the client
        this.mc = MinecraftClient.getInstance();
        mc.player.

    }

    public static ThePlayer getInstance() {
        if (instance == null) {
            instance = new ThePlayer();
        }
        return instance;
    }

    public JustPlayer getProfile() {
        if (playerProfile == null) {
            playerProfile = new JustPlayer(this.mc.player.getGameProfile());
        }
        return playerProfile;
    }

}