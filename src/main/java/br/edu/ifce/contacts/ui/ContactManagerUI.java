package br.edu.ifce.contacts.ui;

import br.edu.ifce.contacts.persistence.models.Contact;
import br.edu.ifce.contacts.persistence.models.ContactGroup;
import br.edu.ifce.contacts.persistence.models.IContactItem;
import br.edu.ifce.contacts.IContactListener;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.util.ArrayDeque;
import java.util.Deque;


public class ContactManagerUI extends BaseConsoleView implements IContactListUI {

    private ContactGroup root;

    private int row = 0;
    private int col = 0;

    private Deque<ContactGroup> groupStack;

    private IContactItem currentItem;
    private int currentItemIdx;

    private IContactListener contactListener;

    public ContactManagerUI(ConsoleRenderer renderer) {
        super(renderer);
        this.groupStack = new ArrayDeque<>();
    }

    @Override
    public void setRootContactGroup(ContactGroup group) {
        this.root = group;
        this.groupStack.addLast(this.root);
    }

    @Override
    public void onStart() {
        this.currentItemIdx = 0;

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

    @Override
    public void setContactListener(IContactListener listener) {
        this.contactListener = listener;
    }

    @Override
    public void onUpdate() {
        poolInput();

        int width = getRenderer().getScreenWidth();
        int height = getRenderer().getScreenHeight();

        getRenderer().clearRect(0, 1, width, height - 2, false);

        row = 1;
        col = 1;

        if (root.size() > 0) {
            for (IContactItem item : root.getItems()) {
                if (item instanceof ContactGroup)
                    draw((ContactGroup) item);
                else
                    draw((Contact) item);
            }
        } else {
            getRenderer().text("NO CONTACTS REGISTERED. PRESS [A] TO ADD A NEW CONTACT", (int) (width * 0.2), (int) (height * 0.5));
        }

        if (currentItem != null) {
            int x = (int)(width * 0.4);
            int y = (int)(height * 0.2);
            drawCurrentItemBox(x, y);
        }
    }

    private void drawCurrentItemBox(int x, int y) {
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

    private void poolInput() {
        KeyStroke key = getRenderer().pollInput();

        if (key != null) {

            if (key.getKeyType() == KeyType.ArrowDown) {
                moveDown();
            } else if (key.getKeyType() == KeyType.ArrowUp) {
                moveUp();
            } else if (key.getKeyType() == KeyType.ArrowRight) {
                enterGroup();
            } else if (key.getKeyType() == KeyType.ArrowLeft) {
                exitGroup();
            } else if (key.getCharacter() != null && (key.getCharacter() == 'A' || key.getCharacter() == 'a')) {
                contactListener.onRequestCreate();
            } else if (key.getCharacter() != null && (key.getCharacter() == 'G' || key.getCharacter() == 'g')) {
                contactListener.onRequestCreateGroup();
            } else if (key.getCharacter() != null && (key.getCharacter() == 'X' || key.getCharacter() == 'x')) {
                contactListener.onRequestDelete(getLastParent(), currentItemIdx);
            }

        }

    }

    private void exitGroup() {
        if (groupStack.size() > 1)
            groupStack.pollLast();

        assert getLastParent() != null;
        currentItemIdx = 0;
        currentItem = getLastParent().getItems().get(currentItemIdx);
    }

    private void enterGroup() {
        if (currentItem instanceof ContactGroup) {
            ContactGroup group = (ContactGroup) currentItem;
            if (group.size() > 0) {
                currentItemIdx = 0;
                groupStack.addLast(group);
                currentItem = group.getItems().get(currentItemIdx);
            }
        }
    }

    private void moveUp() {
        assert getLastParent() != null;
        if (currentItemIdx - 1 >= 0)
            currentItem =  getLastParent().getItems().get(--currentItemIdx);
        else
            exitGroup();
    }

    private void moveDown() {
        assert getLastParent() != null;
        if (currentItemIdx + 1 < getLastParent().size())
            currentItem = getLastParent().getItems().get(++currentItemIdx);
    }

    @Override
    public ContactGroup getCurrentParent() {
        if (currentItem instanceof ContactGroup)
            return (ContactGroup) currentItem;
        else
            return getLastParent();
    }

    private ContactGroup getLastParent() {
        return groupStack.peekLast();
    }

}
