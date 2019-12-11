package br.edu.ifce.contacts;

import br.edu.ifce.contacts.exceptions.ApplicationExitException;
import br.edu.ifce.contacts.persistence.models.IContactItem;
import br.edu.ifce.contacts.ui.*;
import br.edu.ifce.contacts.ui.IContactCreateGroupView;
import br.edu.ifce.contacts.ui.IContactCreateUI;
import br.edu.ifce.contacts.ui.IContactListUI;

import java.io.IOException;

public class ContactManagerPresentation implements IContactPresentation {
    private final ApplicationScreen screen;

    private final ApplicationUI baseView;
    private final IContactListUI contactManagerView;
    private final IContactCreateUI createContactView;
    private final IContactCreateGroupView createContactGroupView;

    public ContactManagerPresentation() throws IOException {
        this.screen = new ApplicationScreen();
        this.baseView = new ApplicationUI(screen.getRenderer());

        contactManagerView = new ContactManagerUI(screen.getRenderer());
        createContactView = new CreateContactUI(screen.getRenderer());
        createContactGroupView = new CreateContactGroupUI(screen.getRenderer());
    }

    public void setBusiness(IContactBusiness business) {
        contactManagerView.setContactListener(business);
        createContactView.setContactListener(business);
        createContactGroupView.setContactListener(business);

        contactManagerView.setRootContactGroup(business.getRootContactGroup());
    }

    @Override
    public void showContactListScreen() {
        this.baseView.setCurrentView(contactManagerView);
    }

    @Override
    public void showCreateContactScreen() {
        this.baseView.setCurrentView(createContactView);
    }

    @Override
    public void showCreateContactGroupScreen() {
        this.baseView.setCurrentView(createContactGroupView);
    }

    @Override
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
    }
}
