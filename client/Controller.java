import greenfoot.Greenfoot;

import javax.swing.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class Controller {
    public static final int LENGTH_ACCOUNT = 36; /* Account ID byte array length */
    public static final int LENGTH_POSITION = (new byte[] {((Integer) /* | */ 450 /* | */ ).byteValue()}).length; /* Maximum Position length */

    Lobby lobby;
    Instance world;
    public Communications communications = new Communications();
    WebSocket.Listener listener;
    HttpClient client;
    WebSocket webSocket;

    public Controller(Lobby lobby, Instance background, String uri) {
        this.lobby = lobby;
        this.world = background;
        listener = new ControllerConnection(world, communications);
        client = HttpClient.newHttpClient();

        try {
            webSocket = client.newWebSocketBuilder().buildAsync(URI.create(uri), listener).get();
        } catch (InterruptedException | ExecutionException e) {
            JOptionPane.showMessageDialog(null, "Could not connect to server.");
            lobby.connect();
        }

        communications.setWebSocket(webSocket);
    }
}

class Communications {
    public Auxiliary auxiliary = new Auxiliary();
    public Authentication authentication = new Authentication();
    public Session session = new Session();

    public static WebSocket webSocket;

    public static final Byte TYPE_AUXILIARY = 1;
    public static final Byte TYPE_AUTHENTICATION = 2;
    public static final Byte TYPE_SESSION = 3;

    public void setWebSocket(WebSocket newWebSocket) {
        webSocket = newWebSocket;
    }

    static class Auxiliary {
        final Byte OP_MESSAGE = 1;
    }

    static class Authentication {
        final Byte OP_REGISTER = 1;
        final Byte OP_TAKEN = 2;
        final Byte OP_UNREGISTER = 3;

        public void register(String username) {
            ByteBuffer message = ByteBuffer.allocate(2 + username.getBytes().length);

            message.put(TYPE_AUTHENTICATION);
            message.put(OP_REGISTER);
            message.put(username.getBytes());

            webSocket.sendBinary(message.rewind(), true);
        }

        public void unregister() {
            ByteBuffer message = ByteBuffer.allocate(2);

            message.put(TYPE_AUTHENTICATION);
            message.put(OP_UNREGISTER);

            webSocket.sendBinary(message.rewind(), true);
        }
    }

    static class Session {
        final Byte OP_READY = 1;
        final Byte OP_UNREADY = 2;
        final Byte OP_JOINED = 3;
        final Byte OP_LEAVE = 4;
        final Byte OP_UPDATE = 5;
        final Byte OP_AVATAR = 6;
        final Byte OP_START = 7;
        final Byte OP_OBSTACLE = 8;

        public void ready() {
            ByteBuffer message = ByteBuffer.allocate(2);

            message.put(TYPE_SESSION);
            message.put(OP_READY);

            webSocket.sendBinary(message.rewind(), true);
        }

        public void unready() {
            ByteBuffer message = ByteBuffer.allocate(2);

            message.put(TYPE_SESSION);
            message.put(OP_UNREADY);

            webSocket.sendBinary(message.rewind(), true);
        }

        public void publish(int positionY) {
            int offset = (int) Math.ceil((float) positionY / 255);
            ByteBuffer message = ByteBuffer.allocate(2 + offset);

            message.put(TYPE_SESSION);
            message.put(OP_UPDATE);

            message.put(2 + offset - 1, (byte) (positionY - (255 * offset - 1)));
            webSocket.sendBinary(message.rewind(), true);
        }

        public void avatar(int avatar) {
            ByteBuffer message = ByteBuffer.allocate(6);

            message.put(TYPE_SESSION);
            message.put(OP_AVATAR);
            message.putInt(avatar);

            webSocket.sendBinary(message.rewind(), true);
        }
    }
}

class ControllerConnection implements WebSocket.Listener {
    Instance world;
    Communications communications;

    ControllerConnection(Instance background, Communications communications) {
        this.world = background;
        this.communications = communications;
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        JOptionPane.showMessageDialog(null, "Connected.");
        WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
        JOptionPane.showMessageDialog(null, "Connection closed.");
        Greenfoot.setWorld(new Lobby());
        return WebSocket.Listener.super.onClose(webSocket, statusCode, reason);
    }

    @Override
    public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer message, boolean last) {
        byte[] action = new byte[message.remaining()];
        message.get(action);

        if (!last) {
            return WebSocket.Listener.super.onBinary(webSocket, message, false);
        }

        System.out.println(Arrays.toString(action));

        if (action[0] == Communications.TYPE_AUXILIARY) {
            if (action[1] == communications.auxiliary.OP_MESSAGE) {
                world.onAuxiliaryMessage(new String(getContent(action), StandardCharsets.UTF_8));
            }
        }

        else if (action[0] == Communications.TYPE_AUTHENTICATION) {
            if (action[1] == communications.authentication.OP_REGISTER) {
                world.onAuthenticationRegistered(new String(getContent(action), StandardCharsets.UTF_8));
            }

            if (action[1] == communications.authentication.OP_TAKEN) {
                world.onAuthenticationTaken();
            }

            else if (action[1] == communications.authentication.OP_UNREGISTER) {
                world.onAuthenticationUnregistered();
            }
        }

        else if (action[0] == Communications.TYPE_SESSION) {
            if (action[1] == communications.session.OP_READY) {
                world.onSessionReady(getAccount(getContent(action)));
            }

            if (action[1] == communications.session.OP_UNREADY) {
                world.onSessionUnReady(getAccount(getContent(action)));
            }

            if (action[1] == communications.session.OP_START) {
                world.onSessionStart();
            }

            else if (action[1] == communications.session.OP_JOINED) {
                byte[] content = getContent(action);
                world.onSessionUserJoined(getAccount(content), new String(getAccountPayload(content), StandardCharsets.UTF_8));
            }

            else if (action[1] == communications.session.OP_LEAVE) {
                world.onSessionUserLeft(getAccount(getContent(action)));
            }

            else if (action[1] == communications.session.OP_UPDATE) {
                byte[] content = getContent(action);
                byte[] payload = getAccountPayload(content);

                int value = (payload.length - 1) * 255;
                value += payload[payload.length - 1] & 0xFF;

                System.out.println(value);

                world.onSessionPositionUpdate(getAccount(content), value);
            }

            else if (action[1] == communications.session.OP_AVATAR) {
                byte[] content = getContent(action);
                world.onSessionAvatarUpdate(getAccount(content), ByteBuffer.wrap(getAccountPayload(content)).getInt());
            }

            else if (action[1] == communications.session.OP_OBSTACLE) {
                byte[] content = getContent(action);
                int value = (content.length - 1) * 255;
                value += content[content.length - 1] & 0xFF;

                System.out.println(value);

                world.onSessionObstacle(value);
            }
        }

        return WebSocket.Listener.super.onBinary(webSocket, message, last);
    }

    byte[] getContent(byte[] action) {
        return Arrays.copyOfRange(action, 2, action.length);
    }

    String getAccount(byte[] content) {
        return new String(Arrays.copyOfRange(content, 0, Controller.LENGTH_ACCOUNT), StandardCharsets.UTF_8);
    }

    byte[] getAccountPayload(byte[] content) {
        return Arrays.copyOfRange(content, Controller.LENGTH_ACCOUNT, content.length);
    }
}