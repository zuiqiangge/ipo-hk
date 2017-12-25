/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pengyue.weixin.wxhelper.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class StringConverter implements JsonSerializer<String>, JsonDeserializer<String> {

    @Override
    public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {
        if (src == null) {
            return new JsonPrimitive("");
        } else {
            return new JsonPrimitive(src.toString());
        } 
    }

    @Override
    public String deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return json.getAsJsonPrimitive().getAsString(); 
    }


     
}
