package br.edu.ifce.contacts;

import br.edu.ifce.contacts.exceptions.ApplicationExitException;
import br.edu.ifce.contacts.persistence.ContactFileDatabase;
import br.edu.ifce.contacts.persistence.models.ContactGroup;
import br.edu.ifce.contacts.persistence.models.IContactItem;
import br.edu.ifce.contacts.ui.*;

import java.io.IOException;

public class ContactManagerTS implements IContactListener {
    private final ApplicationScreen screen;

    private final ApplicationUI baseView;
    private final ContactManagerUI contactManagerView;
    private final CreateContactUI createContactView;
    private final CreateContactGroupUI createContactGroupView;
    
    private final ContactFileDatabase database;

    public ContactManagerTS() throws IOException {
        this.database = new ContactFileDatabase("contacts.json");
        
        this.screen = new ApplicationScreen();
        this.baseView = new ApplicationUI(screen.getRenderer());

        contactManagerView = new ContactManagerUI(screen.getRenderer());
        createContactView = new CreateContactUI(screen.getRenderer());
        createContactGroupView = new CreateContactGroupUI(screen.getRenderer());

        contactManagerView.setContactListener(this);
        createContactView.setContactListener(this);
        createContactGroupView.setContactListener(this);

        contactManagerView.setRootContactGroup(database.getRoot());
    }

    @Override
    public void showContactsList() {
        this.showContactListScreen();
    }

    @Override
    public void onRequestCreate() {
        this.showCreateContactScreen();
    }

    @Override
    public void onRequestCreateGroup() {
        this.showCreateContactGroupScreen();
    }

    @Override
    public void onRequestDelete(ContactGroup parent, int currentItemIdx) {
        parent.removeAt(currentItemIdx);
        database.saveChanges();
    }

    @Override
    public void onCreate(IContactItem item) {
        addContactItem(item);
        database.saveChanges();

        showContactListScreen();
    }

    @Override
    public ContactGroup getRootContactGroup() {
        return database.getRoot();
    }

    public void showContactListScreen() {
        this.baseView.setCurrentView(contactManagerView);
    }

    public void showCreateContactScreen() {
        this.baseView.setCurrentView(createContactView);
    }

    public void showCreateContactGroupScreen() {
        this.baseView.setCurrentView(createContactGroupView);
    }

    public void addContactItem(IContactItem item) {
        this.contactManagerView.getCurrentParent().add(item);
    }

    void mainLoop() {
        boolean running = true;

        try {
            screen.start();
            baseView.onStart();

            while (running) {
                try {
                    baseView.onUpdate();
                } catch (ApplicationExitException e) {
                    running = false;
                }
            }

            baseView.onFinish();
            screen.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }

        database.saveChanges();
    }
}
