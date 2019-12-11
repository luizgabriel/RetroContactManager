package br.edu.ifce.contacts;

import br.edu.ifce.contacts.persistence.models.ContactGroup;
import br.edu.ifce.contacts.persistence.models.IContactItem;
import br.edu.ifce.contacts.persistence.IContactPersistance;

public class ContactManagerBusiness implements IContactBusiness {

    private IContactPersistance persistenceLayer;
    private IContactPresentation presentation;

    public ContactManagerBusiness() {
    }

    public void setPresentation(IContactPresentation presentation) {
        this.presentation = presentation;
    }

    @Override
    public void showContactsList() {
        this.presentation.showContactListScreen();
    }

    @Override
    public void onRequestCreate() {
        this.presentation.showCreateContactScreen();
    }

    @Override
    public void onRequestCreateGroup() {
        this.presentation.showCreateContactGroupScreen();
    }

    @Override
    public void onRequestDelete(ContactGroup parent, int currentItemIdx) {
        parent.removeAt(currentItemIdx);
        persistenceLayer.saveChanges();
    }

    @Override
    public void onCreate(IContactItem item) {
        presentation.addContactItem(item);
        persistenceLayer.saveChanges();

        presentation.showContactListScreen();
    }

    @Override
    public ContactGroup getRootContactGroup() {
        return persistenceLayer.getRoot();
    }

    public void setPersistence(IContactPersistance persistence) {
        this.persistenceLayer = persistence;
    }
}
