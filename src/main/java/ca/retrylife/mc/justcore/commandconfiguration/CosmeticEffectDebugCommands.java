package ca.retrylife.mc.justcore.commandconfiguration;

import com.mojang.brigadier.context.CommandContext;

import ca.retrylife.mc.actionbridge.ActionBridge;
import ca.retrylife.mc.actionbridge.lexer.tokens.SinglePlayerSelector;
import ca.retrylife.mc.actionbridge.lexer.tokens.StringInput;
import ca.retrylife.mc.actionbridge.lexer.tokens.StringLiteral;
import ca.retrylife.mc.justcore.commands.ClientSideCommandRegistry;
import ca.retrylife.mc.justcore.commands.CommandRegistry;
import ca.retrylife.mc.justcore.model.JustPlayer;
import ca.retrylife.mc.justcore.render.cosmetics.CosmeticEffect;
import ca.retrylife.mc.justcore.render.cosmetics.CustomPlayerCosmeticsRegistry;
import ca.retrylife.mc.justcore.utils.ChatUtils;

public class CosmeticEffectDebugCommands {

	public CosmeticEffectDebugCommands() {

		// Add a command for enabling a cosmetic effect for a player
		ClientSideCommandRegistry.getInstance().addCommand("AddPlayerCosmetics", () -> {
			return ActionBridge.newClientSideCommand((c) -> {
				return changePlayerCosmetics(c, true);
			}, new StringLiteral(CommandRegistry.JUST_CORE_BASE_COMMAND_PREFIX), new StringLiteral("addEffect"),
					new SinglePlayerSelector("player"), new StringInput("effect"));
		});

		// Add a command for disabling a cosmetic effect for a player
		ClientSideCommandRegistry.getInstance().addCommand("RemovePlayerCosmetics", () -> {
			return ActionBridge.newClientSideCommand((c) -> {
				return changePlayerCosmetics(c, false);
			}, new StringLiteral(CommandRegistry.JUST_CORE_BASE_COMMAND_PREFIX), new StringLiteral("removeEffect"),
					new SinglePlayerSelector("player"), new StringInput("effect"));
		});
	}

	private int changePlayerCosmetics(CommandContext<?> ctx, boolean show) {
		// Parse out the player name and effect
		String[] commandSplit = ctx.getLastChild().getInput().split(" ");
		String playerName = commandSplit[commandSplit.length - 2];
		String effectName = commandSplit[commandSplit.length - 1];

		// Get the effect to handle
		CosmeticEffect newEffect = CosmeticEffect.stringNameToEffectObject(effectName);

		// Make registry call
		if (show) {
			CustomPlayerCosmeticsRegistry.getInstance().addCosmeticEffectToPlayer(new JustPlayer(playerName),
					newEffect);
		} else {
			CustomPlayerCosmeticsRegistry.getInstance().removeCosmeticEffectToPlayer(new JustPlayer(playerName),
					newEffect);
		}

		// Notify the user
		ChatUtils.sendSelfChatMessage(
				String.format("%s effect %s for user: %s", (show) ? "Applying" : "Removing", effectName, playerName));
		return 0;
	}

}