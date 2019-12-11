package br.edu.ifce.contacts.ui;

import br.edu.ifce.contacts.models.Contact;
import br.edu.ifce.contacts.models.ContactGroup;
import br.edu.ifce.contacts.models.IContactItem;

public class ContactManagerView extends BaseConsoleView {

    private int row = 0;
    private int col = 0;

    public ContactManagerView(ConsoleRenderer renderer) {
        super(renderer);
    }

    public void buildContactTree(ContactGroup group) {
        row = 0;
        col = 0;

        for (IContactItem item: group.getItems()) {
            if (item instanceof ContactGroup)
                build((ContactGroup) item);
            else
                build((Contact) item);
        }
    }

    private void build(ContactGroup group) {
        getRenderer().text(group.getName(), row, col);

        col += 4;

        for (IContactItem item: group.getItems()) {
            if (item instanceof ContactGroup)
                build((ContactGroup) item);
            else
                build((Contact) item);
        }
    }

    private void build(Contact contact) {
        getRenderer().text(contact.getName(), row, col);

        row += 1;
    }


    //region View Lifetime
    @Override
    public void onUpdate() {
        //
    }

    @Override
    public void onStart() {
        //
    }

    @Override
    public void onFinish() {
        //
    }
    //endregion

}
