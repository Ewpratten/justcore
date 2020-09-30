package ca.retrylife.mc.justcore.commands;

import java.util.ArrayList;
import java.util.function.Supplier;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.cottonmc.clientcommands.ClientCommandPlugin;
import io.github.cottonmc.clientcommands.CottonClientCommandSource;

/**
 * A registry of client commands
 */
public class ClientSideCommandRegistry implements ClientCommandPlugin {

    // Instance
    private static ClientSideCommandRegistry instance = null;

    // Logger
    private Logger logger = LogManager.getLogger(getClass());

    // List of commands
    private ArrayList<Supplier<ArgumentBuilder>> registeredCommands = new ArrayList<>();

    // Base command for any core commands
    public static final String JUST_CORE_BASE_COMMAND_PREFIX = "jc";

    private ClientSideCommandRegistry() {

    }

    public static ClientSideCommandRegistry getInstance() {
        if (instance == null) {
            instance = new ClientSideCommandRegistry();
        }
        return instance;
    }

    /**
     * Add a new command
     * 
     * @param description        Command name/description
     * @param commandConstructor Command supplier
     */
    public void addCommand(String description,
            Supplier<ArgumentBuilder> commandConstructor) {
        logger.log(Level.INFO, String.format("Registered a new command: %s", description));
        registeredCommands.add(commandConstructor);
    }

    @Override
    public void registerCommands(CommandDispatcher<CottonClientCommandSource> dispatcher) {
        
        // Set up every command in the list
        for (Supplier<ArgumentBuilder> constructor : registeredCommands) {

            // Register the command
            dispatcher.register((LiteralArgumentBuilder<CottonClientCommandSource>)constructor.get());

        }

    }

}