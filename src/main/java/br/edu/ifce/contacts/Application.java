package br.edu.ifce.contacts;

import br.edu.ifce.contacts.persistence.ContactFileDatabase;
import br.edu.ifce.contacts.persistence.IContactPersistance;

import java.io.IOException;

public class Application {
    private final IContactPersistance persistence;

    private final ContactManagerBusiness business;
    private final ContactManagerPresentation presentation;

    public Application() throws IOException {
        this.persistence = new ContactFileDatabase("contacts.json");
        this.business = new ContactManagerBusiness();
        this.presentation = new ContactManagerPresentation();

        this.business.setPersistence(this.persistence);
        this.business.setPresentation(this.presentation);

        this.presentation.setBusiness(this.business);

        this.presentation.showContactListScreen();
    }

    public void mainLoop() {
        presentation.mainLoop();
        persistence.saveChanges();
    }

    public static void main(String args[]) throws IOException {
        Application application = new Application();
        application.mainLoop();
    }
}
