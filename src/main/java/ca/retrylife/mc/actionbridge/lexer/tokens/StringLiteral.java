package ca.retrylife.mc.actionbridge.lexer.tokens;

import com.mojang.brigadier.builder.ArgumentBuilder;

import io.github.cottonmc.clientcommands.ArgumentBuilders;
import net.minecraft.server.command.CommandManager;

public class StringLiteral implements Token {

    private String value;

    /**
     * Create a String literal
     * 
     * @param value Literal String value
     */
    public StringLiteral(String value) {
        this.value = value;
    }

    @Override
    public String getName() {
        return value;
    }

    @Override
    public ArgumentBuilder<?, ?> addSelfToFrontOfCommandChain(boolean isClientSide, ArgumentBuilder nextCommand) {
        // Handle no next command
        if (nextCommand == null) {
            // Handle client side vs server side
            if (isClientSide) {
                return ArgumentBuilders.literal(getName());
            } else {
                return CommandManager.literal(getName());
            }
        } else {
            // Handle client side vs server side
            if (isClientSide) {
                return ArgumentBuilders.literal(getName()).then(nextCommand);
            } else {
                return CommandManager.literal(getName()).then(nextCommand);
            }
        }
    }

}