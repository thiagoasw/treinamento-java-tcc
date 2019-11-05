package com.totvs.tjc.infra.json;

import java.io.IOException;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.node.TextNode;
import com.totvs.tjc.carteira.Cnpj;

@JsonComponent
public class CnpjConverter {

    public static class CnpjSerializer extends JsonSerializer<Cnpj> {
        @Override
        public void serialize(Cnpj value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.toString());
        }
    }

    public static class CnpjDeserializer extends JsonDeserializer<Cnpj> {
        @Override
        public Cnpj deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

            String value = null;
            TreeNode treeNode = p.getCodec().readTree(p);
            
            if (treeNode.isObject()) {
                value = ((TextNode) treeNode.get("cnpj")).asText();
            } else {
                value = treeNode.toString();
            }
            
            return Cnpj.from(value);
        }
    }

}
