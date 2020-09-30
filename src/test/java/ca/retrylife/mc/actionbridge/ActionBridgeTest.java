package ca.retrylife.mc.actionbridge;

import ca.retrylife.mc.actionbridge.lexer.tokens.SinglePlayerSelector;
import ca.retrylife.mc.actionbridge.lexer.tokens.StringLiteral;

import com.mojang.brigadier.builder.ArgumentBuilder;

import org.junit.Test;

public class ActionBridgeTest {

    @Test
    public void ensureABDoesNotCrashWhenRun() {
        ArgumentBuilder testBuilder = ActionBridge.newClientSideCommand((ctx) -> {
            return 0;
        }, new StringLiteral("hi"), new SinglePlayerSelector("player"));
    }

}