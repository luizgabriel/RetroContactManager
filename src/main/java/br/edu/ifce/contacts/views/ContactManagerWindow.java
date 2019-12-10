package br.edu.ifce.contacts.views;

import br.edu.ifce.contacts.models.Contact;
import br.edu.ifce.contacts.models.ContactGroup;
import br.edu.ifce.contacts.models.IContactItem;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

public class ContactManagerWindow extends BasicWindow {

    private Panel rootPanel;

    public ContactManagerWindow() {
        super("Meus contatos");
        rootPanel = new Panel();
    }

    public void initialize() {
        this.rootPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));
        setComponent(this.rootPanel);
    }

    public void buildContactTree(ContactGroup group) {
        Panel subPanel = new Panel();
        subPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        this.rootPanel.addComponent(subPanel);

        for (IContactItem item: group.getItems()) {
            if (item instanceof ContactGroup)
                build(subPanel, (ContactGroup) item);
            else
                build(subPanel, (Contact) item);
        }
    }

    private void build(Panel panel, ContactGroup group) {
        Panel subPanel = new Panel();
        panel.addComponent(new Label(group.getName()));
        panel.addComponent(panel);

        for (IContactItem item: group.getItems()) {
            if (item instanceof ContactGroup)
                build(subPanel, (ContactGroup) item);
            else
                build(subPanel, (Contact) item);
        }
    }

    private void build(Panel panel, Contact contact) {
        panel.addComponent(new Label(contact.getName()));
    }

}
