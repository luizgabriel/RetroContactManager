package br.edu.ifce.contacts;

import br.edu.ifce.contacts.controllers.ContactController;
import br.edu.ifce.contacts.views.ApplicationBaseView;

import java.io.IOException;

public class Application {

    public static void main(String args[]) throws IOException {
        ApplicationBaseView base = new ApplicationBaseView();
        ContactController contactController = new ContactController();
        contactController.initialize();

        base.startScreen();

        base.getGui().addWindowAndWait(contactController.getView());

        base.stopScreen();
    }
}
