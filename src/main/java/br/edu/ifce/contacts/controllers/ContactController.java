package br.edu.ifce.contacts.controllers;

import br.edu.ifce.contacts.persistence.ContactFileDatabase;
import br.edu.ifce.contacts.views.ContactManagerWindow;

public class ContactController {

    private ContactManagerWindow view;
    private ContactFileDatabase database;

    public ContactController() {
        this.database = new ContactFileDatabase("contacts.json");
        this.view = new ContactManagerWindow();
    }

    public ContactManagerWindow getView() {
        return view;
    }

    public void initialize() {
        this.database.initialize();
        this.view.initialize();

        this.view.buildContactTree(this.database.getRoot());
    }

}
