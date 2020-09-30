package ca.retrylife.mc.justcore.commands;

import com.mojang.brigadier.CommandDispatcher;

import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;

/**
 * This class provides a non-singleton wrapper for use by the Fabric loader
 */
public class ClientSideCommandRegistryRuntimeWrapper implements ClientCommandPlugin {

    @Override
    public void registerCommands(CommandDispatcher<CottonClientCommandSource> dispatcher) {
        ClientSideCommandRegistry.getInstance().registerCommands(dispatcher);
    }

}