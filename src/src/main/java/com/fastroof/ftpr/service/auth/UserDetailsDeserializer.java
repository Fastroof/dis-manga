package com.fastroof.ftpr.service.auth;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fastroof.ftpr.security.UserDetailsImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.Iterator;

/**
 * The UserDetailsDeserializer Class.
 */
public class UserDetailsDeserializer extends JsonDeserializer<UserDetailsImpl> {

    /**
     * Deserialize.
     *
     * @param jsonParser the json parser
     * @param deserializationContext the deserialization context
     * @return the user details impl
     * @throws IOException Signals that an I/O exception has occurred.
     */
    @Override
    public UserDetailsImpl deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {

        UserDetailsImpl userDetails = new UserDetailsImpl();

        ObjectCodec oc = jsonParser.getCodec();
        JsonNode node = oc.readTree(jsonParser);

        userDetails.setId(node.get("id").asLong());
        userDetails.setEmail(node.get("username").asText());
        userDetails.setUsername(node.get("pib").asText());

        Iterator<JsonNode> elements = node.get("authorities").elements();
        while (elements.hasNext()) {
            JsonNode next = elements.next();
            JsonNode authority = next.get("authority");
            userDetails.getAuthorities().add(new SimpleGrantedAuthority(authority.asText()));
        }
        return userDetails;
    }
}