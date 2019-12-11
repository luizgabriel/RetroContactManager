package br.edu.ifce.contacts;

import br.edu.ifce.contacts.controllers.ContactController;
import br.edu.ifce.contacts.exceptions.ApplicationExitException;
import br.edu.ifce.contacts.persistence.ContactFileDatabase;
import br.edu.ifce.contacts.persistence.IContactDatabase;
import br.edu.ifce.contacts.ui.ApplicationView;
import br.edu.ifce.contacts.ui.ApplicationScreen;
import br.edu.ifce.contacts.ui.ContactManagerView;
import br.edu.ifce.contacts.ui.CreateContactView;

import java.io.IOException;

public class Application {
    private final IContactDatabase database;
    private final ApplicationScreen screen;

    private final ApplicationView baseView;

    private boolean isRunning;

    public Application() throws IOException {
        this.isRunning = true;
        this.database = new ContactFileDatabase("contacts.json");
        this.screen = new ApplicationScreen();

        this.baseView = new ApplicationView(screen.getRenderer());
        ContactManagerView contactManagerView = new ContactManagerView(screen.getRenderer());
        CreateContactView createContactView = new CreateContactView(screen.getRenderer());

        ContactController contactController = new ContactController(database);
        contactController.setView(baseView);
        contactController.setListView(contactManagerView);
        contactController.setCreateView(createContactView);

        contactController.listContacts();
    }

    public void mainLoop() {
        try {
            screen.start();
            baseView.onStart();

            while (isRunning) {
                try {
                    baseView.onUpdate();
                } catch (ApplicationExitException e) {
                    isRunning = false;
                }
            }

            baseView.onFinish();
            screen.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }

        database.saveChanges();
    }

    public static void main(String args[]) throws IOException {
        Application application = new Application();
        application.mainLoop();
    }
}
