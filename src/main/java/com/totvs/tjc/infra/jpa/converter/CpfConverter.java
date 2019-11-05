package com.totvs.tjc.infra.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.totvs.tjc.carteira.Cpf;

@Converter(autoApply = true)
public class CpfConverter implements AttributeConverter<Cpf, String> {

    @Override
    public String convertToDatabaseColumn(Cpf attribute) {
        return attribute.toString();
    }

    @Override
    public Cpf convertToEntityAttribute(String dbData) {
        return Cpf.from(dbData);
    }

}
