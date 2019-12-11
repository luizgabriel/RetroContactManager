package br.edu.ifce.contacts.ui;

import br.edu.ifce.contacts.models.ContactGroup;
import br.edu.ifce.contacts.views.IContactCreateGroupView;
import br.edu.ifce.contacts.views.listeners.IContactListener;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;

import java.util.regex.Pattern;

public class CreateContactGroupView extends BaseConsoleView implements IContactCreateGroupView {
    private IContactListener listener;

    private final Pattern namePattern = Pattern.compile(".{2,20}");

    public CreateContactGroupView(ConsoleRenderer renderer) {
        super(renderer);
    }

    @Override
    public void setContactListener(IContactListener listener) {
        this.listener = listener;
    }

    @Override
    public void onStart() {
        ContactGroup contact = new ContactGroup(requestName());
        this.listener.onCreate(contact);
    }

    private String requestName() {
        String name = null;

        while (name == null || name.isEmpty()) {
            name = new TextInputDialogBuilder()
                    .setTitle("Create a new Group")
                    .setDescription("Type a name:")
                    .setValidationPattern(namePattern, "Type a name between 2-20 characters.")
                    .build()
                    .showDialog(getRenderer().getGui());
        }

        return name;
    }

}
