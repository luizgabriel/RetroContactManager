package br.edu.ifce.contacts.controllers;

import br.edu.ifce.contacts.persistence.IContactDatabase;
import br.edu.ifce.contacts.ui.ContactManagerView;

public class ContactController {

    private IContactDatabase database;
    private ContactManagerView view;

    public ContactController(IContactDatabase database) {
        this.database = database;
    }

    public void setView(ContactManagerView view) {
        this.view = view;
        this.view.setRootContactGroup(this.database.getRoot());
    }

    public ContactManagerView getView() {
        return view;
    }
}
