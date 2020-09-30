package ca.retrylife.mc.justcore;

import java.io.IOException;
import java.net.URL;

import com.mojang.brigadier.arguments.ArgumentType;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.retrylife.mc.justcore.commands.ClientSideCommandRegistry;
import ca.retrylife.mc.justcore.commands.CommandRegistry;
import ca.retrylife.mc.justcore.model.JustPlayer;
import ca.retrylife.mc.justcore.render.capes.Cape;
import ca.retrylife.mc.justcore.render.capes.CustomPlayerCapeRegistry;
import ca.retrylife.mc.justcore.utils.ChatUtils;
import io.github.cottonmc.clientcommands.ArgumentBuilders;
import net.fabricmc.api.ModInitializer;
import net.minecraft.command.argument.ArgumentTypes;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;

public class JustCore implements ModInitializer {
	private Logger logger = LogManager.getLogger(getClass());

	@Override
	public void onInitialize() {
		logger.log(Level.INFO, "Starting JustCore");

		// Give mod developers their capes
		try {
			registerModDeveloperCapes();
		} catch (IOException e) {
			logger.log(Level.WARN,
					"Failed to award mod developers with their capes. Maybe the resource is not available?");
		} catch (Throwable e) {
			logger.log(Level.WARN, "An unknown failure occurred while trying to award developers with capes");
			e.printStackTrace();
		}

		// Register all server commands
		CommandRegistry.getInstance().enableAllCommands();

		logger.log(Level.INFO, "JustCore Ready");
	}

	/**
	 * Registers custom capes for all mod developers
	 */
	private void registerModDeveloperCapes() throws Throwable {
		// Define the mod developer cape
		Cape devCape = new Cape(
				new URL("https://raw.githubusercontent.com/Ewpratten/justcore/master/assets/contributor-cape.png"));

		// Add every user account
		CustomPlayerCapeRegistry.getInstance().overwritePlayerCape(new JustPlayer("Xnor50"), devCape);
		CustomPlayerCapeRegistry.getInstance().overwritePlayerCape(new JustPlayer("redstoneninja"), devCape);

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
										devCape);

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