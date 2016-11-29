package br.udesc.rakes.chat.server.model;


import br.udesc.rakes.chat.common.model.Action;
import br.udesc.rakes.chat.common.model.ChatMessage;
import br.udesc.rakes.chat.common.model.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private Map<User, ObjectOutputStream> mapOnlines = new HashMap<User, ObjectOutputStream>();

    public Server(int port) {
        try {
            serverSocket = new ServerSocket(port);

            System.out.println("Servidor on!");

            while(true) {
                System.out.println("Esperando accept!");
                socket = serverSocket.accept();

                new Thread(new SocketListener(this)).start();
            }

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public Socket getSocket() {
        return socket;
    }

    public Map<User, ObjectOutputStream> getMapOnlines() {
        return mapOnlines;
    }

    public boolean connect(ChatMessage message, ObjectOutputStream output) {
        if (mapOnlines.size() == 0) {
            message.setMessage("YES");
            send(message, output);
            return true;
        }

        if (mapOnlines.containsKey(message.getSender())) {
            message.setMessage("NO");
            send(message, output);
            return false;
        } else {
            message.setMessage("YES");
            send(message, output);
            return true;
        }
    }

    public void disconnect(ChatMessage message, ObjectOutputStream output) {
        mapOnlines.remove(message.getSender());

        message.setMessage(" até logo!");

        message.setAction(Action.SEND_ONE);

        sendAll(message);

        System.out.println("User " + message.getSender() + " sai da sala");
    }

    public void send(ChatMessage message, ObjectOutputStream output) {
        try {
            output.writeObject(message);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void sendOne(ChatMessage message) {
        for (Map.Entry<User, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            if (kv.getKey().equals(message.getReceiver())) {
                try {
                    kv.getValue().writeObject(message);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void sendAll(ChatMessage message) {
        for (Map.Entry<User, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            if (!kv.getKey().equals(message.getSender())) {
                message.setAction(Action.SEND_ONE);
                try {
                    kv.getValue().writeObject(message);
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void sendOnlines() {
        Set<User> setNames = new HashSet<User>();
        for (Map.Entry<User, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            setNames.add(kv.getKey());
        }

        ChatMessage message = new ChatMessage();
        message.setAction(Action.USERS_ONLINE);
        message.setSetOnlines(setNames);

        for(Map.Entry<User, ObjectOutputStream> kv : mapOnlines.entrySet()) {
            message.setSender(kv.getKey());
            try {
                kv.getValue().writeObject(message);
            } catch (IOException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}