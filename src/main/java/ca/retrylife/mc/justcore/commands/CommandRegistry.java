package ca.retrylife.mc.justcore.commands;

import java.util.ArrayList;
import java.util.function.Supplier;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.server.command.ServerCommandSource;

/**
 * A registry of server commands
 */
public class CommandRegistry {

    // Instance
    private static CommandRegistry instance = null;

    // Logger
    private Logger logger = LogManager.getLogger(getClass());

    // List of commands
    private ArrayList<Supplier<LiteralArgumentBuilder<ServerCommandSource>>> registeredCommands = new ArrayList<>();

    // Base command for any core commands
    public static final String JUST_CORE_BASE_COMMAND_PREFIX = "jc";

    private CommandRegistry() {

    }

    public static CommandRegistry getInstance() {
        if (instance == null) {
            instance = new CommandRegistry();
        }
        return instance;
    }

    /**
     * Add a new command
     * @param description Command name/description
     * @param commandConstructor Command supplier
     */
    public void addCommand(String description,
            Supplier<LiteralArgumentBuilder<ServerCommandSource>> commandConstructor) {
        logger.log(Level.INFO, String.format("Registered a new command: %s", description));
        registeredCommands.add(commandConstructor);
    }

    /**
     * Enable all commands. Call this in onInitialize()
     */
    public void enableAllCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            
            // Set up every command in the list
            for (Supplier<LiteralArgumentBuilder<ServerCommandSource>> constructor : registeredCommands) {
                
                // Register the command
                dispatcher.register(constructor.get());

            }
        });
    }

}