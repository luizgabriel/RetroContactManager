package br.edu.ifce.contacts.views.listeners;

import br.edu.ifce.contacts.models.ContactGroup;
import br.edu.ifce.contacts.models.IContactItem;

public interface IContactListener {
    void onRequestCreate();
    void onRequestCreateGroup();

    void onCreate(IContactItem item);

    void onRequestDelete(ContactGroup parent, int currentItemIdx);
}
