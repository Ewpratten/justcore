package ca.retrylife.mc.justcore.commandconfiguration;

import ca.retrylife.mc.justcore.commands.ClientSideCommandRegistry;
import ca.retrylife.mc.justcore.commands.CommandRegistry;
import ca.retrylife.mc.justcore.model.JustPlayer;
import ca.retrylife.mc.justcore.render.capes.Cape;
import ca.retrylife.mc.justcore.render.capes.CustomPlayerCapeRegistry;
import ca.retrylife.mc.justcore.utils.ChatUtils;
import io.github.cottonmc.clientcommands.ArgumentBuilders;
import net.minecraft.command.argument.EntityArgumentType;

public class DeveloperCapeDebugCommands {
    public DeveloperCapeDebugCommands(Cape defaultCape) {
        // Set up a server command for temporarily awarding players a developer cape for
        // testing
        ClientSideCommandRegistry.getInstance().addCommand("TempAwardDevCapeCommand", () -> {
            return ArgumentBuilders.literal(CommandRegistry.JUST_CORE_BASE_COMMAND_PREFIX)
                    .then(ArgumentBuilders.literal("awardTempDevCape")
                            .then(ArgumentBuilders.argument("player", EntityArgumentType.player()).executes(ctx -> {

                                // Parse out the player name
                                String[] commandSplit = ctx.getLastChild().getInput().split(" ");
                                String playerName = commandSplit[commandSplit.length - 1];

                                // Make a call to the cape registry
                                CustomPlayerCapeRegistry.getInstance().overwritePlayerCape(new JustPlayer(playerName),
                                        defaultCape);

                                // Notify the user
                                ChatUtils.sendSelfChatMessage(
                                        String.format("Temporarily awarded a developer cape to user: %s", playerName));
                                return 0;
                            })));
        });

        // Set up a command for showing a player's dev cape
        ClientSideCommandRegistry.getInstance().addCommand("TempShowDevCapeCommand", () -> {
            return ArgumentBuilders.literal(CommandRegistry.JUST_CORE_BASE_COMMAND_PREFIX)
                    .then(ArgumentBuilders.literal("showDevCape")
                            .then(ArgumentBuilders.argument("player", EntityArgumentType.player()).executes(ctx -> {

                                // Parse out the player name
                                String[] commandSplit = ctx.getLastChild().getInput().split(" ");
                                String playerName = commandSplit[commandSplit.length - 1];

                                // Make a call to the cape registry
                                CustomPlayerCapeRegistry.getInstance()
                                        .setPlayerCapeVisibility(new JustPlayer(playerName), true);

                                // Notify the user
                                ChatUtils.sendSelfChatMessage(
                                        String.format("Showing custom cape to user: %s", playerName));
                                return 0;
                            })));
        });

        // Set up a command for hiding a player's dev cape
        ClientSideCommandRegistry.getInstance().addCommand("TempHideDevCapeCommand", () -> {
            return ArgumentBuilders.literal(CommandRegistry.JUST_CORE_BASE_COMMAND_PREFIX)
                    .then(ArgumentBuilders.literal("hideDevCape")
                            .then(ArgumentBuilders.argument("player", EntityArgumentType.player()).executes(ctx -> {

                                // Parse out the player name
                                String[] commandSplit = ctx.getLastChild().getInput().split(" ");
                                String playerName = commandSplit[commandSplit.length - 1];

                                // Make a call to the cape registry
                                CustomPlayerCapeRegistry.getInstance()
                                        .setPlayerCapeVisibility(new JustPlayer(playerName), false);

                                // Notify the user
                                ChatUtils.sendSelfChatMessage(
                                        String.format("Hiding custom cape to user: %s", playerName));
                                return 0;
                            })));
        });
    }
}