package br.edu.ifce.contacts;

import br.edu.ifce.contacts.persistence.ContactFileDatabase;

import java.io.IOException;

public class Application {
    private final ContactManagerTS presentation;

    public Application() throws IOException {
        this.presentation = new ContactManagerTS();
        this.presentation.showContactListScreen();
    }

    public void mainLoop() {
        presentation.mainLoop();
    }

    public static void main(String args[]) throws IOException {
        Application application = new Application();
        application.mainLoop();
    }
}
