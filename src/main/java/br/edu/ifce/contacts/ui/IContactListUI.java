package br.edu.ifce.contacts.ui;

import br.edu.ifce.contacts.persistence.models.ContactGroup;

public interface IContactListUI extends IContactView {
    void setRootContactGroup(ContactGroup group);
    ContactGroup getCurrentParent();
}
