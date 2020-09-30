package ca.retrylife.mc.actionbridge.lexer.tokens;

import net.minecraft.command.argument.EntityArgumentType;

/**
 * SingleEntitySelector is a selector for a single entity
 */
public class SingleEntitySelector extends ConfigurableEntitySelector {

    /**
     * Create a SingleEntitySelector
     * 
     * @param varName Variable name
     */
    public SingleEntitySelector(String varName) {
        super(varName, EntityArgumentType.entity());
    }

}