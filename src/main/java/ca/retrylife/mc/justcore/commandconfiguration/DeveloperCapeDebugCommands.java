package ca.retrylife.mc.justcore.commandconfiguration;

import com.mojang.brigadier.context.CommandContext;

import ca.retrylife.mc.actionbridge.ActionBridge;
import ca.retrylife.mc.actionbridge.lexer.tokens.SinglePlayerSelector;
import ca.retrylife.mc.actionbridge.lexer.tokens.StringLiteral;
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
			return ActionBridge.newClientSideCommand((c) -> {
				return awardTempDevCape(c, defaultCape);
			}, new StringLiteral(CommandRegistry.JUST_CORE_BASE_COMMAND_PREFIX), new StringLiteral("awardTempDevCape"),
					new SinglePlayerSelector("player"));
		});

		// Set up a command for showing a player's dev cape
		ClientSideCommandRegistry.getInstance().addCommand("TempShowDevCapeCommand", () -> {
			return ActionBridge.newClientSideCommand((c) -> {
				return setTempDevCapeVisibility(c, true);
			}, new StringLiteral(CommandRegistry.JUST_CORE_BASE_COMMAND_PREFIX), new StringLiteral("showDevCape"),
					new SinglePlayerSelector("player"));
		});

		// Set up a command for hiding a player's dev cape
		ClientSideCommandRegistry.getInstance().addCommand("TempHideDevCapeCommand", () -> {
			return ActionBridge.newClientSideCommand((c) -> {
				return setTempDevCapeVisibility(c, false);
			}, new StringLiteral(CommandRegistry.JUST_CORE_BASE_COMMAND_PREFIX), new StringLiteral("hideDevCape"),
					new SinglePlayerSelector("player"));
		});
	}

	public int awardTempDevCape(CommandContext<?> ctx, Cape cape) {
		// Parse out the player name
		String[] commandSplit = ctx.getLastChild().getInput().split(" ");
		String playerName = commandSplit[commandSplit.length - 1];

		// Make a call to the cape registry
		CustomPlayerCapeRegistry.getInstance().overwritePlayerCape(new JustPlayer(playerName), cape);

		// Notify the user
		ChatUtils.sendSelfChatMessage(String.format("Temporarily awarded a developer cape to user: %s", playerName));
		return 0;
	}

	public int setTempDevCapeVisibility(CommandContext<?> ctx, boolean visible) {
		// Parse out the player name
		String[] commandSplit = ctx.getLastChild().getInput().split(" ");
		String playerName = commandSplit[commandSplit.length - 1];

		// Make a call to the cape registry
		CustomPlayerCapeRegistry.getInstance().setPlayerCapeVisibility(new JustPlayer(playerName), visible);

		// Notify the user
		ChatUtils.sendSelfChatMessage(String.format("Hiding custom cape to user: %s", playerName));
		return 0;
	}
}