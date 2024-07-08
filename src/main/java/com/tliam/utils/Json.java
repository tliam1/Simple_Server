package com.tliam.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.IOException;

public class Json {
//   This mapper (or, data binder, or codec) provides functionality for
//   converting between Java objects (instances of JDK provided core classes, beans),
//   and matching JSON constructs. It will use instances of JsonParser and
//   JsonGenerator for implementing actual reading/writing of JSON.
    private static ObjectMapper myObjectMapper = defaultObjectMapper();

    private static ObjectMapper defaultObjectMapper(){
        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return om;
    };

    /*
     * readValue() can be used for any and all types, including JsonNode.
     * readTree() only works for JsonNode (tree model); and is added for convenience.
     */

    public static JsonNode parse(String jsonSrc) throws IOException {
        return myObjectMapper.readTree(jsonSrc);
    }

    /*
     * The most convenient way to convert a JsonNode into a Java object
     *  is the treeToValue API:
     */
    public static <T> T fromJson(JsonNode node, Class<T> _class) throws JsonProcessingException {
        return myObjectMapper.treeToValue(node, _class);
    }

    /*
     * A node may be converted from a Java object by calling the
     * valueToTree(Object fromValue) method on the ObjectMapper:
     */
    public static JsonNode toJson(Object obj){
        return myObjectMapper.valueToTree(obj);
    }


    public static String strigify(JsonNode node) throws JsonProcessingException {
        return generateJson(node, false);
    }

    public static String strigifyPretty(JsonNode node) throws JsonProcessingException {
        return generateJson(node, true);
    }

    private static String generateJson(Object o, boolean makePretty) throws JsonProcessingException {
        ObjectWriter objectWriter = myObjectMapper.writer();
        if(makePretty)
            objectWriter.with(SerializationFeature.INDENT_OUTPUT); // make dump look nicer
        return objectWriter.writeValueAsString(o);
    }
}
