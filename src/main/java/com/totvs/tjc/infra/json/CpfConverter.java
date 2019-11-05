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
import com.totvs.tjc.carteira.Cpf;

@JsonComponent
public class CpfConverter {

    public static class CpfSerializer extends JsonSerializer<Cpf> {
        @Override
        public void serialize(Cpf value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
            gen.writeString(value.toString());
        }
    }

    public static class CpfDeserializer extends JsonDeserializer<Cpf> {
        @Override
        public Cpf deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

            String value = null;
            TreeNode treeNode = p.getCodec().readTree(p);
            
            if (treeNode.isObject()) {
                value = ((TextNode) treeNode.get("cpf")).asText();
            } else {
                value = treeNode.toString();
            }
            
            return Cpf.from(value);
        }
    }

}
