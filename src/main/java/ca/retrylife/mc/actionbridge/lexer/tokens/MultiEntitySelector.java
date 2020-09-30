package ca.retrylife.mc.actionbridge.lexer.tokens;

import net.minecraft.command.argument.EntityArgumentType;

/**
 * MultiEntitySelector is a selector for multiple entities
 */
public class MultiEntitySelector extends ConfigurableEntitySelector {

    /**
     * Create a MultiEntitySelector
     * 
     * @param varName Variable name
     */
    public MultiEntitySelector(String varName) {
        super(varName, EntityArgumentType.entities());
    }

}