package br.edu.ifce.contacts.ui;

import br.edu.ifce.contacts.IContactListener;

public interface IContactView extends IUI {
    void setContactListener(IContactListener listener);
}
