package br.edu.ifce.contacts.ui;

import br.edu.ifce.contacts.exceptions.ApplicationExitException;
import br.edu.ifce.contacts.models.Contact;
import br.edu.ifce.contacts.views.IContactCreateView;
import br.edu.ifce.contacts.views.listeners.IContactListener;
import com.googlecode.lanterna.gui2.dialogs.TextInputDialogBuilder;

import java.util.regex.Pattern;

public class CreateContactView extends BaseConsoleView implements IContactCreateView {
    private IContactListener listener;

    private final String title = "Create a new Contact";
    private final Pattern namePattern = Pattern.compile(".{2,20}");
    private final Pattern phonePattern = Pattern.compile("[ \\-_+()0-9]{8,}");
    private final Pattern emailPattern = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");

    public CreateContactView(ConsoleRenderer renderer) {
        super(renderer);
    }

    @Override
    public void setContactListener(IContactListener listener) {
        this.listener = listener;
    }

    @Override
    public void onStart() {
        Contact contact = new Contact(requestTel());
        contact.setName(requestName());
        contact.setEmail(requestEmail());

        this.listener.onCreate(contact);
    }

    private String requestName() {
        String name = null;

        while (name == null || name.isEmpty()) {
            name = new TextInputDialogBuilder()
                    .setTitle(title)
                    .setDescription("Type a name:")
                    .setValidationPattern(namePattern, "Type a name between 2-20 characters.")
                    .build()
                    .showDialog(getRenderer().getGui());
        }

        return name;
    }

    private String requestEmail() {
        String email = null;

        while (email == null || email.isEmpty()) {
            email = new TextInputDialogBuilder()
                    .setTitle(title)
                    .setDescription("Type a e-mail:")
                    .setValidationPattern(emailPattern, "Type a valid e-mail")
                    .build()
                    .showDialog(getRenderer().getGui());
        }

        return email;
    }

    private String requestTel() {
        String tel= null;

        while (tel == null || tel.isEmpty()) {
            tel =  new TextInputDialogBuilder()
                    .setTitle(title)
                    .setDescription("Type a phone number:")
                    .setValidationPattern(phonePattern, "Type a valid phone number")
                    .build()
                    .showDialog(getRenderer().getGui());
        }

        return tel;
    }

}
