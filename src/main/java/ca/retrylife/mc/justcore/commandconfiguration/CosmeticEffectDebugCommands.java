package ca.retrylife.mc.justcore.commandconfiguration;

import com.mojang.brigadier.arguments.StringArgumentType;

import ca.retrylife.mc.justcore.commands.ClientSideCommandRegistry;
import ca.retrylife.mc.justcore.commands.CommandRegistry;
import ca.retrylife.mc.justcore.model.JustPlayer;
import ca.retrylife.mc.justcore.render.cosmetics.CosmeticEffect;
import ca.retrylife.mc.justcore.render.cosmetics.CustomPlayerCosmeticsRegistry;
import ca.retrylife.mc.justcore.utils.ChatUtils;
import io.github.cottonmc.clientcommands.ArgumentBuilders;
import net.minecraft.command.argument.EntityArgumentType;

public class CosmeticEffectDebugCommands {

    public CosmeticEffectDebugCommands() {

        // Add a command for enabling a cosmetic effect for a player
        ClientSideCommandRegistry.getInstance().addCommand("AddPlayerCosmetics", () -> {
            return ArgumentBuilders.literal(CommandRegistry.JUST_CORE_BASE_COMMAND_PREFIX)
                    .then(ArgumentBuilders.literal("addEffect")
                            .then(ArgumentBuilders.argument("player", EntityArgumentType.player()).then(
                                    ArgumentBuilders.argument("effect", StringArgumentType.string()).executes(ctx -> {

                                        // Parse out the player name and effect
                                        String[] commandSplit = ctx.getLastChild().getInput().split(" ");
                                        String playerName = commandSplit[commandSplit.length - 2];
                                        String effectName = commandSplit[commandSplit.length - 1];

                                        // Get the effect to apply
                                        CosmeticEffect newEffect = CosmeticEffect.stringNameToEffectObject(effectName);

                                        // Make registry call
                                        CustomPlayerCosmeticsRegistry.getInstance()
                                                .addCosmeticEffectToPlayer(new JustPlayer(playerName), newEffect);

                                        // Notify the user
                                        ChatUtils.sendSelfChatMessage(String.format("Applying effect %s to user: %s",
                                                effectName, playerName));
                                        return 0;
                                    }))));
        });

        // Add a command for disabling a cosmetic effect for a player
        ClientSideCommandRegistry.getInstance().addCommand("RemovePlayerCosmetics", () -> {
            return ArgumentBuilders.literal(CommandRegistry.JUST_CORE_BASE_COMMAND_PREFIX)
                    .then(ArgumentBuilders.literal("removeEffect")
                            .then(ArgumentBuilders.argument("player", EntityArgumentType.player()).then(
                                    ArgumentBuilders.argument("effect", StringArgumentType.string()).executes(ctx -> {

                                        // Parse out the player name and effect
                                        String[] commandSplit = ctx.getLastChild().getInput().split(" ");
                                        String playerName = commandSplit[commandSplit.length - 2];
                                        String effectName = commandSplit[commandSplit.length - 1];

                                        // Get the effect to remove
                                        CosmeticEffect newEffect = CosmeticEffect.stringNameToEffectObject(effectName);

                                        // Make registry call
                                        CustomPlayerCosmeticsRegistry.getInstance()
                                                .removeCosmeticEffectToPlayer(new JustPlayer(playerName), newEffect);

                                        // Notify the user
                                        ChatUtils.sendSelfChatMessage(String.format("Removing effect %s from user: %s",
                                                effectName, playerName));
                                        return 0;
                                    }))));
        });
    }

}