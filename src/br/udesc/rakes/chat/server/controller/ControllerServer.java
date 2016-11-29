package br.udesc.rakes.chat.server.controller;

import br.udesc.rakes.chat.server.view.FrameServer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControllerServer extends Controller {

    private final FrameServer frameServer;


    public ControllerServer() {
        this.frameServer = new FrameServer();

        initComponents();
    }

    protected void showScreen() {
        this.frameServer.setVisible(true);
    }

    protected void hideScreen() {
        this.frameServer.setVisible(false);
    }

    protected final void initComponents() {
        this.frameServer.getBtNewServer().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openRunningDialog();
            }
        });
    }

    private void openRunningDialog() {
        hideScreen();
        (new ControllerRunningServer(frameServer, Integer.parseInt(frameServer.getTfPort().getText()))).execute();
    }

}