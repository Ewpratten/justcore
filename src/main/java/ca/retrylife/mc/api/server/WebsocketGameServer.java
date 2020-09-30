package ca.retrylife.mc.api.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import ca.retrylife.mc.actionbridge.ActionBridge;
import ca.retrylife.mc.actionbridge.lexer.tokens.StringLiteral;
import ca.retrylife.mc.justcore.commands.ClientSideCommandRegistry;
import ca.retrylife.mc.justcore.commands.CommandRegistry;

/**
 * A class that can be used to expose game functionality via a websocket
 */
public class WebsocketGameServer {
    private static WebsocketGameServer instance;
    private Logger logger = LogManager.getLogger(getClass());

    // Service default port
    private static final int DEFAULT_WEBSOCKET_PORT = 7929;

    // Internal websocket server
    private WebSocketServer server;

    // List of active subscribers
    private class CallbackWrapper {
        String key;
        Consumer<String> callback;
    }

    private ArrayList<CallbackWrapper> subscribers = new ArrayList<>();

    private WebsocketGameServer() {
        // Set up a client command to expose this server on 0.0.0.0
        ClientSideCommandRegistry.getInstance().addCommand("Expose internal websocket globally", () -> {
            return ActionBridge.newClientSideCommand((ctx) -> {
                try {
                    serveOn(new InetSocketAddress("0.0.0.0", DEFAULT_WEBSOCKET_PORT));
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    return -1;
                }
                return 0;
            }, new StringLiteral(CommandRegistry.JUST_CORE_BASE_COMMAND_PREFIX), new StringLiteral("exposeWS"));
        });
    }

    public static WebsocketGameServer getInstance() {
        if (instance == null) {
            instance = new WebsocketGameServer();
        }
        return instance;
    }

    /**
     * Publish a message
     * 
     * @param key   Message key (do not include a #)
     * @param value Message value
     */
    public void publishMessage(String key, String value) {
        if (server != null) {
            server.broadcast(String.format("%s#%s", key.replace(" ", "_"), value));
        } else {
            logger.error(String.format("Failed to publish message with key %s because the websocket server was null",
                    key.replace(" ", "_")));
        }
    }

    /**
     * Subscribe to messages with a key
     * 
     * @param key      Key to listen to
     * @param callback Callback for value changes
     */
    public void subscribeTo(String key, Consumer<String> callback) {
        logger.error(String.format("A new subscriber is listening to key %s", key.replace(" ", "_")));
        CallbackWrapper wrapper = new CallbackWrapper();
        wrapper.key = key.replace(" ", "_");
        wrapper.callback = callback;
        subscribers.add(wrapper);
    }

    /**
     * Start serving locally
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    public void serve() throws IOException, InterruptedException {
        serveOn(new InetSocketAddress(DEFAULT_WEBSOCKET_PORT));
    }

    /**
     * Serve on any interface
     * 
     * @param address Address to serve with
     * @throws IOException
     * @throws InterruptedException
     */
    public void serveOn(InetSocketAddress address) throws IOException, InterruptedException {

        // Stop any running server
        if (server != null) {
            server.stop();
        }

        // Create server
        server = new WebSocketServer(address) {

            @Override
            public void onOpen(WebSocket conn, ClientHandshake handshake) {
                onConnection(this, conn, handshake);
            }

            @Override
            public void onClose(WebSocket conn, int code, String reason, boolean remote) {
                onDisconnection(this, conn, code, reason, remote);
            }

            @Override
            public void onMessage(WebSocket conn, String message) {
                onMessageReceived(this, conn, message);
            }

            @Override
            public void onError(WebSocket conn, Exception ex) {
                onServerError(this, conn, ex);
            }

            @Override
            public void onStart() {
                onServerStart(this);
            }
        };

        // Start the server
        server.start();
    }

    private void onConnection(WebSocketServer server, WebSocket conn, ClientHandshake handshake) {

    }

    private void onDisconnection(WebSocketServer server, WebSocket conn, int code, String reason, boolean remote) {

    }

    private void onMessageReceived(WebSocketServer server, WebSocket conn, String message) {

        // Determine the key adn value
        int firstHashIndex = message.indexOf("#");
        String key = message.substring(0, firstHashIndex);
        String value = message.substring(firstHashIndex + 1);

        // Handle callback
        for (CallbackWrapper wrapper : subscribers) {
            if (wrapper.key.equals(key)) {
                wrapper.callback.accept(value);
            }
        }

    }

    private void onServerError(WebSocketServer server, WebSocket conn, Exception e) {
        e.printStackTrace();
    }

    private void onServerStart(WebSocketServer server) {
        logger.info("Websocket server started");
        server.setConnectionLostTimeout(0);
        server.setConnectionLostTimeout(100);
    }

}