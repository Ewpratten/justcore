package ca.retrylife.mc.actionbridge;

import java.util.function.Supplier;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import ca.retrylife.mc.actionbridge.lexer.tokens.Token;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;

public class ActionBridge {
    public static Supplier<LiteralArgumentBuilder<CottonClientCommandSource>> newClientSideCommand(
            Command<CottonClientCommandSource> action, Token... tokens) {

    }
}