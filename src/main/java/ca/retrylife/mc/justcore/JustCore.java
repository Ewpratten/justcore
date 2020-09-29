package ca.retrylife.mc.justcore;

import java.io.IOException;
import java.net.URL;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.retrylife.mc.justcore.model.JustPlayer;
import ca.retrylife.mc.justcore.render.capes.Cape;
import ca.retrylife.mc.justcore.render.capes.CustomPlayerCapeRegistry;
import net.fabricmc.api.ModInitializer;

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

		logger.log(Level.INFO, "JustCore Ready");
	}

	/**
	 * Registers custom capes for all mod developers
	 */
	private void registerModDeveloperCapes() throws IOException {
		// Define the mod developer cape
		Cape devCape = new Cape(new URL("https://raw.githubusercontent.com/Ewpratten/justcore/master/assets/contributor-cape.png"));

		// Add every user account
		CustomPlayerCapeRegistry.getInstance().overwritePlayerCape(new JustPlayer("Xnor50"), devCape);
	}

}