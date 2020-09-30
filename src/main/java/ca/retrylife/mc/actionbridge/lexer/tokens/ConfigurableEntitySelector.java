package ca.retrylife.mc.actionbridge.lexer.tokens;

import com.mojang.brigadier.builder.ArgumentBuilder;

import io.github.cottonmc.clientcommands.ArgumentBuilders;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;

/**
 * ConfigurableEntitySelector is a selector for any type of entity(s)
 */
public class ConfigurableEntitySelector implements Token {

    private String name;
    private EntityArgumentType selector;

    /**
     * Create a ConfigurableEntitySelector
     * 
     * @param varName  Variable name
     * @param selector Selector
     */
    public ConfigurableEntitySelector(String varName, EntityArgumentType selector) {
        this.name = varName;
        this.selector = selector;
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
                return ArgumentBuilders.argument(getName(), selector);
            } else {
                return CommandManager.argument(getName(), selector);
            }
        } else {
            // Handle client side vs server side
            if (isClientSide) {
                return ArgumentBuilders.argument(getName(), selector).then(nextCommand);
            } else {
                return CommandManager.argument(getName(), selector).then(nextCommand);
            }
        }
    }

}