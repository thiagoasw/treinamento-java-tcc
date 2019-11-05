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
import com.totvs.tjc.carteira.Cpf;
import com.totvs.tjc.infra.json.CpfConverter.CpfDeserializer;
import com.totvs.tjc.infra.json.CpfConverter.CpfSerializer;

@DisplayName("Teste de serializac\u00E3o de cpf (json)")
public class CpfConverterTest {

    private static Writer writer;

    private static JsonGenerator generator;

    private static SerializerProvider provider;
    
    private static DeserializationContext context;

    private static ObjectMapper mapper;

    private static CpfSerializer serializer;

    private static CpfDeserializer deserializer;

    private static String cpfValido;

    private static String cpfInvalido;

    @BeforeAll
    static void initAll() {
        mapper = new ObjectMapper();
        serializer = new CpfSerializer();
        deserializer = new CpfDeserializer();
        cpfValido = "04642835903";
        cpfInvalido = "96183390269";
    }

    @BeforeEach
    void init() throws IOException {
        writer = new StringWriter();
        generator = new JsonFactory().createGenerator(writer);
        provider = mapper.getSerializerProvider();
        context = mapper.getDeserializationContext();
    }

    @Test
    void cpfSerializer() throws IOException, JsonProcessingException {
        serializer.serialize(Cpf.from(cpfValido), generator, provider);
        generator.flush();
        assertEquals("\"" + cpfValido + "\"", writer.toString());
    }

    @Test
    void cpfSerializerNull() throws IOException, JsonProcessingException {
        assertThrows(NullPointerException.class, () -> serializer.serialize(null, generator, provider));
    }

    @Test
    void cpfDeserializer() throws IOException, JsonProcessingException {

        String json = String.format("{\"cpf\":%s}", "\"" + cpfValido + "\"");
        JsonParser parser = mapper.getFactory().createParser(json);

        Cpf actual = deserializer.deserialize(parser, context);

        assertNotNull(actual);
        assertEquals(Cpf.from(cpfValido), actual);
    }
    
    @Test
    void cpfDeserializerSemNo() throws IOException, JsonProcessingException {

        String json = String.format("%s", "\"" + cpfValido + "\"");
        JsonParser parser = mapper.getFactory().createParser(json);
        
        Cpf actual = deserializer.deserialize(parser, context);

        assertNotNull(actual);
        assertEquals(Cpf.from(cpfValido), actual);
    }
    
    @Test
    void cpfInvalidoDeserializer() throws IOException, JsonProcessingException {

        String json = String.format("{\"cpf\":%s}", "\"" + cpfInvalido + "\"");
        JsonParser parser = mapper.getFactory().createParser(json);

        assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize(parser, context));
    }
    
    @Test
    void cpfNuloDeserializer() throws IOException, JsonProcessingException {

        String json = String.format("\"" + null + "\"");
        JsonParser parser = mapper.getFactory().createParser(json);

        assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize(parser, context));
    }
    
}
