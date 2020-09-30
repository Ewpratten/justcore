package ca.retrylife.mc.actionbridge;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.ArgumentBuilder;

import ca.retrylife.mc.actionbridge.lexer.tokens.Token;

/**
 * Utilities for building Minecraft commands
 */
public class ActionBridge {

    /**
     * Create a new command
     * 
     * @param action       Action to be run
     * @param isClientSide Is this command client side only?
     * @param tokens       Tokens that make up the command
     * @return Brigadier ArgumentBuilder
     */
    public static ArgumentBuilder newCommand(Command action, boolean isClientSide, Token... tokens) {

        // Build a tracker element containing the last token, and an execute step
        ArgumentBuilder builder = tokens[tokens.length - 1].addSelfToFrontOfCommandChain(isClientSide, null)
                .executes(action);

        // Loop through the token list backwards
        for (int i = tokens.length - 2; i >= 0; i--) {
            // Add another layer to the builder
            builder = tokens[i].addSelfToFrontOfCommandChain(isClientSide, builder);
        }

        // Return the builder
        return builder;
    }

    /**
     * Create a new client-side command
     * 
     * @param action Action to be run
     * @param tokens Tokens that make up the command
     * @return Brigadier ArgumentBuilder
     */
    public static ArgumentBuilder newClientSideCommand(Command action, Token... tokens) {
        return newCommand(action, true, tokens);
    }

    /**
     * Create a new server-side command
     * 
     * @param action Action to be run
     * @param tokens Tokens that make up the command
     * @return Brigadier ArgumentBuilder
     */
    public static ArgumentBuilder newServerSideCommand(Command action, Token... tokens) {
        return newCommand(action, false, tokens);
    }
}