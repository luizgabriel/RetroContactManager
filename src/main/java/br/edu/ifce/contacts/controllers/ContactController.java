package br.edu.ifce.contacts.controllers;

import br.edu.ifce.contacts.models.Contact;
import br.edu.ifce.contacts.persistence.IContactDatabase;
import br.edu.ifce.contacts.ui.ApplicationView;
import br.edu.ifce.contacts.views.IContactCreateView;
import br.edu.ifce.contacts.views.IContactListView;
import br.edu.ifce.contacts.views.listeners.IContactListener;

public class ContactController implements IContactListener {

    private IContactDatabase database;

    private ApplicationView view;

    private IContactListView listView;
    private IContactCreateView createView;

    public ContactController(IContactDatabase database) {
        this.database = database;
    }

    public void setView(ApplicationView view) {
        this.view = view;
    }

    public void setListView(IContactListView view) {
        this.listView = view;
        this.listView.setRootContactGroup(this.database.getRoot());
        this.listView.setContactListener(this);
    }

    public void setCreateView(IContactCreateView view) {
        this.createView = view;
        this.listView.setContactListener(this);
    }

    public void listContacts() {
        this.view.setCurrentView(this.listView);
    }

    public void createContact() {
        this.view.setCurrentView(this.createView);
    }

    @Override
    public void onRequestCreate() {
        this.createContact();
    }

    @Override
    public void onRequestCreateGroup() {

    }

    @Override
    public void onCreate(Contact contact) {
        //
    }
}
