package br.edu.ifce.contacts;

import br.edu.ifce.contacts.persistence.models.ContactGroup;
import br.edu.ifce.contacts.persistence.models.IContactItem;

public interface IContactListener {
    void showContactsList();

    void onRequestCreate();
    void onRequestCreateGroup();

    void onCreate(IContactItem item);

    void onRequestDelete(ContactGroup parent, int currentItemIdx);

    ContactGroup getRootContactGroup();
}
