package br.edu.ifce.contacts.persistence;

import br.edu.ifce.contacts.persistence.models.Contact;
import com.google.gson.*;

import java.lang.reflect.Type;

public class ContactSerializer implements JsonSerializer<Contact>, JsonDeserializer<Contact> {

    @Override
    public JsonElement serialize(Contact src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject obj = new JsonObject();

        if (src.getName() != null && !src.getName().isEmpty())
            obj.add("name", new JsonPrimitive(src.getName()));

        if (src.getTel() != null && !src.getTel().isEmpty())
            obj.add("tel", new JsonPrimitive(src.getTel()));

        if (src.getEmail() != null && !src.getEmail().isEmpty())
            obj.add("email", new JsonPrimitive(src.getEmail()));

        return obj;
    }

    @Override
    public Contact deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject obj = json.getAsJsonObject();
        Contact contact = new Contact(obj.get("tel").getAsString());

        if (obj.has("email"))
            contact.setEmail(obj.get("email").getAsString());

        if (obj.has("name"))
            contact.setName(obj.get("name").getAsString());

        return contact;
    }
}
