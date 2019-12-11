package br.edu.ifce.contacts;

import br.edu.ifce.contacts.persistence.models.IContactItem;

public interface IContactPresentation {

    void showContactListScreen();

    void showCreateContactScreen();

    void showCreateContactGroupScreen();

    void addContactItem(IContactItem item);
}
