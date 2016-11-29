package br.udesc.rakes.chat.server.model;

import br.udesc.rakes.chat.common.model.ChatMessage;
import br.udesc.rakes.chat.common.model.Action;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketListener implements Runnable {

    private Server             server;

    private ObjectOutputStream output;
    private ObjectInputStream  input;


    public SocketListener(Server server) {
        try {
            this.server = server;

            this.output = new ObjectOutputStream(server.getSocket().getOutputStream());
            this.input  = new ObjectInputStream(server.getSocket().getInputStream());
        } catch (IOException ex) {
            try {
                server.getSocket().close();
            } catch (IOException ex1) {
                Logger.getLogger(SocketListener.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

    }

    @Override
    public void run() {
        ChatMessage message = null;
            try {
                System.out.println("Lendo mensagem!");
                while ((message = (ChatMessage) input.readObject()) != null) {
                    System.out.println("Deu boa pra ler!");
                    Action action = message.getAction();

                    if (action.equals(Action.CONNECT)) {
                        boolean isConnect = server.connect(message, output);
                        if (isConnect) {
                            server.getMapOnlines().put(message.getSender(), output);
                            server.sendOnlines();
                        }
                    } else if (action.equals(Action.DISCONNECT)) {
                        server.disconnect(message, output);
                        server.sendOnlines();
                        return;
                    } else if (action.equals(Action.SEND_ONE)) {
                        server.sendOne(message);
                    } else if (action.equals(Action.SEND_ALL)) {
                        server.sendAll(message);
                    }
                }
            } catch (IOException ex) {
                System.out.println("Deu ruim pra ler!");
                ChatMessage cm = new ChatMessage();
                cm.setSender(message.getSender());
                server.disconnect(cm, output);
                server.sendOnlines();
                System.out.println(message.getSender() + " deixou o chat!");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

}