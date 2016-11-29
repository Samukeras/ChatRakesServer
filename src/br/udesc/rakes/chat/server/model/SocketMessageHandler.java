package br.udesc.rakes.chat.server.model;

import br.udesc.rakes.chat.common.model.ChatMessage;
import java.io.ObjectOutputStream;

public class SocketMessageHandler {

    private SocketMessageHandler() {
        throw new RuntimeException("This class shouldn't be instantiated");
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new RuntimeException("This class shouldn't be cloned");
    }

    public static void handleChatMessage(Server server, ObjectOutputStream stream, ChatMessage message) {
        switch(message.getAction()) {
            case CONNECT:
                connect(server, stream, message);
                break;

            case DISCONNECT:
                disconnect(server, stream, message);
                break;

            case SEND_ONE:
                sendToOne(server, message);
                break;

            case SEND_ALL:
                sendToAll(server, message);
                break;

            default:
                throw new RuntimeException("Deu ruim no handler!!!");
        }
    }

    private static void connect(Server server, ObjectOutputStream stream, ChatMessage message) {
        if(server.connect(message, stream)) {
            server.getMapOnlines().put(message.getSender(), stream);
            server.sendOnlines();
        }
    }

    private static void disconnect(Server server, ObjectOutputStream stream, ChatMessage message) {
        server.disconnect(message, stream);
        server.sendOnlines();
    }

    private static void sendToOne(Server server, ChatMessage message) {
        server.sendOne(message);
    }

    private static void sendToAll(Server server, ChatMessage message) {
        server.sendAll(message);
    }

}