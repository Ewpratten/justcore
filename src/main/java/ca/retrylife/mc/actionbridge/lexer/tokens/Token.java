package ca.retrylife.mc.actionbridge.lexer.tokens;

import com.mojang.brigadier.builder.ArgumentBuilder;

public interface Token {

    /**
     * Get this token's name
     * 
     * @return Name
     */
    public String getName();

    /**
     * Given a following command or null, add self in front
     * 
     * @param isClientSide True if this is a client side command
     * @param nextCommand  Next command or null
     * @return Chained commands
     */
    public ArgumentBuilder<?, ?> addSelfToFrontOfCommandChain(boolean isClientSide, ArgumentBuilder nextCommand);

}