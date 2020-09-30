package ca.retrylife.mc.actionbridge.lexer.tokens;

import net.minecraft.command.argument.EntityArgumentType;

/**
 * SinglePlayerSelector is a selector for a single player
 */
public class SinglePlayerSelector extends ConfigurableEntitySelector {

    /**
     * Create a SinglePlayerSelector
     * 
     * @param varName Variable name
     */
    public SinglePlayerSelector(String varName) {
        super(varName, EntityArgumentType.player());
    }

}