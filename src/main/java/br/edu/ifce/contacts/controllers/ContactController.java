package br.edu.ifce.contacts.controllers;

import br.edu.ifce.contacts.models.Contact;
import br.edu.ifce.contacts.models.ContactGroup;
import br.edu.ifce.contacts.models.IContactItem;
import br.edu.ifce.contacts.persistence.IContactDatabase;
import br.edu.ifce.contacts.ui.ApplicationView;
import br.edu.ifce.contacts.views.IContactCreateGroupView;
import br.edu.ifce.contacts.views.IContactCreateView;
import br.edu.ifce.contacts.views.IContactListView;
import br.edu.ifce.contacts.views.listeners.IContactListener;

public class ContactController implements IContactListener {

    private IContactDatabase database;

    private ApplicationView view;

    private IContactListView listView;
    private IContactCreateView createView;
    private IContactCreateGroupView createGroupView;

    public ContactController(IContactDatabase database) {
        this.database = database;
    }

    //<editor-fold desc="Setters" default-state="collapsed">
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
        this.createView.setContactListener(this);
    }

    public void setCreateGroupView(IContactCreateGroupView view) {
        this.createGroupView = view;
        this.createGroupView.setContactListener(this);
    }
    //</editor-fold>

    public void showContactsList() {
        this.view.setCurrentView(this.listView);
    }

    @Override
    public void onRequestCreate() {
        this.view.setCurrentView(this.createView);
    }

    @Override
    public void onRequestCreateGroup() {
        this.view.setCurrentView(this.createGroupView);
    }

    @Override
    public void onRequestDelete(ContactGroup parent, int currentItemIdx) {
        parent.removeAt(currentItemIdx);
        database.saveChanges();
    }

    @Override
    public void onCreate(IContactItem item) {
        listView.getCurrentParent().add(item);
        database.saveChanges();

        this.view.setCurrentView(this.listView);
    }

}
