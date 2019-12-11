package br.edu.ifce.contacts.persistence;

import br.edu.ifce.contacts.models.Contact;
import br.edu.ifce.contacts.models.ContactGroup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ContactFileDatabase implements IContactDatabase {
    private String fileName;
    private Gson gson;

    private ContactGroup root;

    public ContactFileDatabase(String filePath) {
        this.fileName = filePath;
        this.root = null;

        ContactSerializer contactSerializer = new ContactSerializer();
        ContactGroupSerializer contactGroupSerializer = new ContactGroupSerializer(contactSerializer);

        this.gson = new GsonBuilder()
                .registerTypeAdapter(Contact.class, contactSerializer)
                .registerTypeAdapter(ContactGroup.class, contactGroupSerializer)
                .create();

        this.initialize();
    }

    private void initialize() {
        try {
            FileReader reader = new FileReader(this.fileName);
            this.root = this.gson.fromJson(reader, ContactGroup.class);
        } catch (FileNotFoundException e) {
            this.root = null;
        }

        if (this.root == null) {
            this.root = new ContactGroup("ROOT");
            this.saveChanges();
        }
    }

    public void saveChanges() {
        try {
            FileWriter writer = new FileWriter(this.fileName);
            this.gson.toJson(this.root, ContactGroup.class, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ContactGroup getRoot() {
        return root;
    }

}
