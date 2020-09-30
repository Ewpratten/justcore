package ca.retrylife.mc.actionbridge.lexer.tokens;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;

import io.github.cottonmc.clientcommands.ArgumentBuilders;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;

/**
 * StringInput is a selector for any String
 */
public class StringInput implements Token {

    private String name;

    /**
     * Create a StringInput
     * 
     * @param varName  Variable name
     */
    public StringInput(String varName) {
        this.name = varName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ArgumentBuilder<?, ?> addSelfToFrontOfCommandChain(boolean isClientSide, ArgumentBuilder nextCommand) {
        // Handle no next command
        if (nextCommand == null) {
            // Handle client side vs server side
            if (isClientSide) {
                return ArgumentBuilders.argument(getName(), StringArgumentType.string());
            } else {
                return CommandManager.argument(getName(), StringArgumentType.string());
            }
        } else {
            // Handle client side vs server side
            if (isClientSide) {
                return ArgumentBuilders.argument(getName(), StringArgumentType.string()).then(nextCommand);
            } else {
                return CommandManager.argument(getName(), StringArgumentType.string()).then(nextCommand);
            }
        }
    }

}