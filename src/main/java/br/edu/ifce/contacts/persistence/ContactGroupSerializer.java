package br.edu.ifce.contacts.persistence;

import br.edu.ifce.contacts.persistence.models.Contact;
import br.edu.ifce.contacts.persistence.models.ContactGroup;
import br.edu.ifce.contacts.persistence.models.IContactItem;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ContactGroupSerializer implements JsonSerializer<ContactGroup>, JsonDeserializer<ContactGroup> {

    private final ContactSerializer contactSerializer;

    public ContactGroupSerializer(ContactSerializer contactSerializer) {
        this.contactSerializer = contactSerializer;
    }

    @Override
    public JsonElement serialize(ContactGroup src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();
        JsonArray arr = new JsonArray();
        for (IContactItem item: src.getItems()) {
            if (item instanceof Contact) {
                arr.add(this.contactSerializer.serialize((Contact) item, Contact.class, context));
            } else if (item instanceof ContactGroup) {
                arr.add(this.serialize((ContactGroup) item, ContactGroup.class, context));
            }
        }

        obj.add("name", new JsonPrimitive(src.getName()));
        obj.add("items", arr);

        return obj;
    }

    @Override
    public ContactGroup deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        JsonArray arr = obj.get("items").getAsJsonArray();

        ContactGroup group = new ContactGroup(obj.get("name").getAsString());

        for (JsonElement element: arr) {
            JsonObject itemObj = element.getAsJsonObject();
            if (itemObj.has("items"))
                group.add(this.deserialize(element, ContactGroup.class, context));
            else
                group.add(this.contactSerializer.deserialize(element, Contact.class, context));
        }

        return group;
    }
}
