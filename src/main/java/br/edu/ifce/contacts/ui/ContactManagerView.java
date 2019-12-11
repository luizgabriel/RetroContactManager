package br.edu.ifce.contacts.ui;

import br.edu.ifce.contacts.models.Contact;
import br.edu.ifce.contacts.models.ContactGroup;
import br.edu.ifce.contacts.models.IContactItem;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayDeque;
import java.util.Deque;


public class ContactManagerView extends BaseConsoleView {

    private ContactGroup root;

    private int row = 0;
    private int col = 0;

    private Deque<ContactGroup> groupStack;

    private IContactItem currentItem;
    private int currentItemIdx;

    public ContactManagerView(ConsoleRenderer renderer) {
        super(renderer);
        this.groupStack = new ArrayDeque<>();
    }

    public void setRootContactGroup(ContactGroup group) {
        this.root = group;
        this.currentItemIdx = 0;
        this.groupStack.addLast(this.root);

        if (this.root.size() > 0)
            this.currentItem = this.root.getItems().get(this.currentItemIdx);
        else
            this.currentItem = null;
    }

    private void draw(ContactGroup group) {
        boolean selected =  group.getName().equals(currentItem.getName());
        getRenderer().text("> " + group.getName(), col, row, selected);

        col += 4;
        row += 1;

        for (IContactItem item: group.getItems()) {
            if (item instanceof ContactGroup)
                draw((ContactGroup) item);
            else
                draw((Contact) item);
        }

        col += -4;
    }

    private void draw(Contact contact) {
        boolean selected =  contact.getName().equals(currentItem.getName());
        getRenderer().text(contact.getName(), col, row, selected);

        row += 1;
    }


    //region View Lifetime
    @Override
    public void onUpdate() {
        poolInput();

        row = 1;
        col = 1;

        for (IContactItem item: root.getItems()) {
            if (item instanceof ContactGroup)
                draw((ContactGroup) item);
            else
                draw((Contact) item);
        }

        if (currentItem != null) {
            int x = (int)(getRenderer().getScreenWidth() * 0.4);
            int y = (int)(getRenderer().getScreenHeight() * 0.2);
            getRenderer()
                    .rectangle(x, y, 40, 5, true)
                    .text(currentItem.getName() + ":", x + 1, y + 1, true);

            if (currentItem instanceof ContactGroup) {
                ContactGroup group = ((ContactGroup) currentItem);
                getRenderer().text(group.size() + " contact(s)", x + 2, y + 2, true);
            } else if (currentItem instanceof Contact) {
                Contact contact = ((Contact) currentItem);

                getRenderer().text(contact.getTel(), x + 2, y + 3, true);

                if (contact.getEmail() != null && !contact.getEmail().isEmpty())
                    getRenderer().text(contact.getEmail(), x + 2, y + 4, true);
            }

        }
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

    private void poolInput() {
        KeyStroke key = getRenderer().pollInput();

        if (key != null) {

            if (key.getKeyType() == KeyType.ArrowDown) {
                assert groupStack.peekLast() != null;
                if (currentItemIdx + 1 < groupStack.peekLast().size())
                    currentItem = groupStack.peekLast().getItems().get(++currentItemIdx);
            }

            if (key.getKeyType() == KeyType.ArrowUp) {
                assert groupStack.peekLast() != null;
                if (currentItemIdx - 1 >= 0)
                    currentItem = groupStack.peekLast().getItems().get(--currentItemIdx);
            }

            if (key.getKeyType() == KeyType.ArrowRight) {
                if (currentItem instanceof ContactGroup) {
                    ContactGroup group = (ContactGroup) currentItem;
                    currentItemIdx = 0;
                    groupStack.addLast(group);
                    currentItem = group.getItems().get(currentItemIdx);
                }
            }

            if (key.getKeyType() == KeyType.ArrowLeft) {
                if (groupStack.size() > 1)
                    groupStack.pollLast();

                assert groupStack.peekLast() != null;
                currentItemIdx = 0;
                currentItem = groupStack.peekLast().getItems().get(currentItemIdx);
            }

        }

    }

}
