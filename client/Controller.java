import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.WebSocket;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class Controller {
    public static final int LENGTH_ACCOUNT = 4; /* Account ID byte array length */
    public static final int LENGTH_POSITION = (new byte[] {((Integer) /* | */ 450 /* | */ ).byteValue()}).length; /* Vertical Screen Resolution length */

    Background world;
    public Communications communications = new Communications();
    WebSocket.Listener listener;
    HttpClient client;
    WebSocket webSocket;

    public Controller(Background background, String uri) {
        world = background;
        listener = new ControllerConnection(world, communications);
        client = HttpClient.newHttpClient();

        try {
            webSocket = client.newWebSocketBuilder().buildAsync(URI.create(uri), listener).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
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
        final Byte OP_UNREGISTER = 2;

        public void register(String username) {
            ByteBuffer message = ByteBuffer.allocate(2 + username.getBytes().length);

            message.put(TYPE_AUTHENTICATION);
            message.put(OP_REGISTER);
            message.put(username.getBytes());

            webSocket.sendBinary(message, true);
        }

        public void unregister() {
            ByteBuffer message = ByteBuffer.allocate(2);

            message.put(TYPE_AUTHENTICATION);
            message.put(OP_UNREGISTER);

            webSocket.sendBinary(message, true);
        }
    }

    static class Session {
        final Byte OP_READY = 1;
        final Byte OP_UNREADY = 2;
        final Byte OP_JOINED = 3;
        final Byte OP_UPDATE = 4;
        final Byte OP_AVATAR = 5;
        final Byte OP_START = 6;

        public void ready() {
            ByteBuffer message = ByteBuffer.allocate(2);

            message.put(TYPE_SESSION);
            message.put(OP_READY);

            webSocket.sendBinary(message, true);
        }

        public void unready() {
            ByteBuffer message = ByteBuffer.allocate(2);

            message.put(TYPE_SESSION);
            message.put(OP_UNREADY);

            webSocket.sendBinary(message, true);
        }

        public void publish(int positionY) {
            ByteBuffer message = ByteBuffer.allocate(2 + Controller.LENGTH_POSITION);

            message.put(TYPE_SESSION);
            message.put(OP_UPDATE);
            message.put((byte) positionY);

            webSocket.sendBinary(message, true);
        }

        public void avatar(Byte avatar) {
            ByteBuffer message = ByteBuffer.allocate(3);

            message.put(TYPE_SESSION);
            message.put(OP_AVATAR);
            message.put(avatar);

            webSocket.sendBinary(message, true);
        }
    }
}

class ControllerConnection implements WebSocket.Listener {
    Background world;
    Communications communications;
    ByteArrayOutputStream message = new ByteArrayOutputStream();

    ControllerConnection(Background background, Communications communications) {
        this.world = background;
        this.communications = communications;
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {
        message.writeBytes(data.array());

        if (!last) {
            return WebSocket.Listener.super.onBinary(webSocket, data, false);
        }

        byte[] action = message.toByteArray();
        message.reset();

        if (action[0] == Communications.TYPE_AUXILIARY) {
            if (action[1] == communications.auxiliary.OP_MESSAGE) {
                world.onAuxiliaryMessage(ByteBuffer.wrap(getContent(action)).toString());
            }
        }

        else if (action[0] == Communications.TYPE_AUTHENTICATION) {
            if (action[1] == communications.authentication.OP_REGISTER) {
                world.onAuthenticationRegistered();
            }

            else if (action[1] == communications.authentication.OP_UNREGISTER) {
                world.onAuthenticationUnregistered();
            }
        }

        else if (action[0] == Communications.TYPE_SESSION) {
            if (action[1] == communications.session.OP_START) {
                world.onSessionStart();
            }

            else if (action[1] == communications.session.OP_JOINED) {
                byte[] content = getContent(action);
                world.onSessionUserJoined(getAccount(content), ByteBuffer.wrap(getAccountPayload(content)).toString());
            }

            else if (action[1] == communications.session.OP_UPDATE) {
                byte[] content = getContent(action);
                world.onSessionPositionUpdate(getAccount(content), ByteBuffer.wrap(getAccountPayload(content)).getInt());
            }

            else if (action[1] == communications.session.OP_AVATAR) {
                byte[] content = getContent(action);
                world.onSessionAvatarUpdate(getAccount(content), content[0]);
            }
        }

        return WebSocket.Listener.super.onBinary(webSocket, data, true);
    }

    byte[] getContent(byte[] action) {
        return Arrays.copyOfRange(action, 2, action.length - 1);
    }

    byte[] getAccount(byte[] content) {
        return Arrays.copyOfRange(content, 0, Controller.LENGTH_ACCOUNT - 1);
    }

    byte[] getAccountPayload(byte[] content) {
        return Arrays.copyOfRange(content, Controller.LENGTH_ACCOUNT, content.length - 1);
    }
}