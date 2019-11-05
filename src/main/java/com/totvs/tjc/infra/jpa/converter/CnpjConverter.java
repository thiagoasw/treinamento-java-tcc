package com.totvs.tjc.infra.jpa.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.totvs.tjc.carteira.Cnpj;

@Converter(autoApply = true)
public class CnpjConverter implements AttributeConverter<Cnpj, String> {

    @Override
    public String convertToDatabaseColumn(Cnpj attribute) {
        return attribute.toString();
    }

    @Override
    public Cnpj convertToEntityAttribute(String dbData) {
        return Cnpj.from(dbData);
    }

}
