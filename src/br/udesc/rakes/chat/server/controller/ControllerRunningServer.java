package br.udesc.rakes.chat.server.controller;

import br.udesc.rakes.chat.server.model.Server;
import br.udesc.rakes.chat.server.view.DialogRunning;

public class ControllerRunningServer extends Controller {


    private final DialogRunning dialogRunning;
    private int                 port;


    public ControllerRunningServer(java.awt.Frame parent, int port) {
        dialogRunning = new DialogRunning(parent, true);
        this.port     = port;

        initComponents();
    }


    private void startServer(int port) {
        new Server(port);
    }

    @Override
    public void execute() {
        startServer(this.port);
        super.execute();
    }

    @Override
    public final void initComponents() {
        
    }

    @Override
    protected void showScreen() {
        this.dialogRunning.setVisible(true);
    }

    @Override
    protected void hideScreen() {
        this.dialogRunning.setVisible(false);
    }

}