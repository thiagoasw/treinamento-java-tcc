package com.totvs.tjc.infra.json;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.totvs.tjc.carteira.Cnpj;
import com.totvs.tjc.infra.json.CnpjConverter.CnpjDeserializer;
import com.totvs.tjc.infra.json.CnpjConverter.CnpjSerializer;

@DisplayName("Teste de serializac\u00E3o de cnpj (json)")
public class CnpjConverterTest {

    private static Writer writer;

    private static JsonGenerator generator;

    private static SerializerProvider provider;
    
    private static DeserializationContext context;

    private static ObjectMapper mapper;

    private static CnpjSerializer serializer;

    private static CnpjDeserializer deserializer;

    private static String cnpjValido;

    private static String cnpjInvalido;

    @BeforeAll
    static void initAll() {
        mapper = new ObjectMapper();
        serializer = new CnpjSerializer();
        deserializer = new CnpjDeserializer();
        cnpjValido = "19861350000170";
        cnpjInvalido = "06305901000177";
    }

    @BeforeEach
    void init() throws IOException {
        writer = new StringWriter();
        generator = new JsonFactory().createGenerator(writer);
        provider = mapper.getSerializerProvider();
        context = mapper.getDeserializationContext();
    }

    @Test
    void cnpjSerializer() throws IOException, JsonProcessingException {
        serializer.serialize(Cnpj.from(cnpjValido), generator, provider);
        generator.flush();
        assertEquals("\"" + cnpjValido + "\"", writer.toString());
    }

    @Test
    void cnpjSerializerNull() throws IOException, JsonProcessingException {
        assertThrows(NullPointerException.class, () -> serializer.serialize(null, generator, provider));
    }

    @Test
    void cnpjDeserializer() throws IOException, JsonProcessingException {

        String json = String.format("{\"cnpj\":%s}", "\"" + cnpjValido + "\"");
        JsonParser parser = mapper.getFactory().createParser(json);

        Cnpj actual = deserializer.deserialize(parser, context);

        assertNotNull(actual);
        assertEquals(Cnpj.from(cnpjValido), actual);
    }
    
    @Test
    void cnpjDeserializerSemNo() throws IOException, JsonProcessingException {

        String json = String.format("%s", "\"" + cnpjValido + "\"");
        JsonParser parser = mapper.getFactory().createParser(json);
        
        Cnpj actual = deserializer.deserialize(parser, context);

        assertNotNull(actual);
        assertEquals(Cnpj.from(cnpjValido), actual);
    }
    
    @Test
    void cnpjInvalidoDeserializer() throws IOException, JsonProcessingException {

        String json = String.format("{\"cnpj\":%s}", "\"" + cnpjInvalido + "\"");
        JsonParser parser = mapper.getFactory().createParser(json);

        assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize(parser, context));
    }
    
    @Test
    void cnpjNuloDeserializer() throws IOException, JsonProcessingException {

        String json = String.format("\"" + null + "\"");
        JsonParser parser = mapper.getFactory().createParser(json);

        assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize(parser, context));
    }
    
}
