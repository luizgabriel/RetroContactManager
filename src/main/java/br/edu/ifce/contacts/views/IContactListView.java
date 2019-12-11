package br.edu.ifce.contacts.views;

import br.edu.ifce.contacts.models.ContactGroup;

public interface IContactListView extends IContactView {
    void setRootContactGroup(ContactGroup group);
    ContactGroup getCurrentParent();
}
