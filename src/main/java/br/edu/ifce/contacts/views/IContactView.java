package br.edu.ifce.contacts.views;

import br.edu.ifce.contacts.views.listeners.IContactListener;

public interface IContactView extends IView {
    void setContactListener(IContactListener listener);
}
