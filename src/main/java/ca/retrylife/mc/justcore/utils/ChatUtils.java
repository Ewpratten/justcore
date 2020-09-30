package ca.retrylife.mc.justcore.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.MessageType;
import net.minecraft.text.LiteralText;

public class ChatUtils {

    /**
     * Send the player a message to their chat
     * @param message Message
     */
    public static void sendSelfChatMessage(String message) {
        MinecraftClient instance = MinecraftClient.getInstance();
        
        // instance.player.sendChatMessage(message);
        instance.inGameHud.addChatMessage(MessageType.GAME_INFO, new LiteralText(message), instance.player.getUuid());
    }

}