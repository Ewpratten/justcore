package ca.retrylife.mc.actionbridge.lexer.tokens;

import net.minecraft.command.argument.EntityArgumentType;

/**
 * MultiPlayerSelector is a selector for multiple players
 */
public class MultiPlayerSelector extends ConfigurableEntitySelector {

    /**
     * Create a MultiPlayerSelector
     * 
     * @param varName Variable name
     */
    public MultiPlayerSelector(String varName) {
        super(varName, EntityArgumentType.players());
    }

}