package com.totvs.tjc;

import static com.totvs.tjc.MoneySupportConfiguration.AMOUNT_FIELD_NAME;
import static com.totvs.tjc.MoneySupportConfiguration.CURRENCY_FIELD_NAME;

import java.math.BigDecimal;

import javax.money.MonetaryAmount;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.javamoney.moneta.FastMoney;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.RoundedMoney;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.totvs.tjc.carteira.Cnpj;
import com.totvs.tjc.carteira.ContaId;
import com.totvs.tjc.carteira.Cpf;
import com.totvs.tjc.emprestimo.EmprestimoId;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

    @Bean
    Docket api() {

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
            .useDefaultResponseMessages(false)
            .forCodeGeneration(true)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.regex("/api/.*"))
            .build();

        return registerModelSubstitutes(docket)
            .apiInfo(apiInfo());
    }

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("Java TCC REST API :: Gest\u00E3o de Carteira")
            .description("Interface de contratos REST para aplicação de Gest\u00E3o de Carteira.")
            .build();
    }

    Docket registerModelSubstitutes(Docket docket) {

        docket.directModelSubstitute(ContaId.class, String.class);
        docket.directModelSubstitute(EmprestimoId.class, String.class);

        docket.directModelSubstitute(Cpf.class, String.class);
        docket.directModelSubstitute(Cnpj.class, String.class);

        docket.directModelSubstitute(Money.class, MonetaryAmountApiRepresentation.class);
        docket.directModelSubstitute(FastMoney.class, MonetaryAmountApiRepresentation.class);
        docket.directModelSubstitute(RoundedMoney.class, MonetaryAmountApiRepresentation.class);
        docket.directModelSubstitute(MonetaryAmount.class, MonetaryAmountApiRepresentation.class);

        return docket;
    }

    @ApiModel("MonetaryAmount")
    interface MonetaryAmountApiRepresentation {

        @NotNull
        @NotBlank
        @ApiModelProperty(name = AMOUNT_FIELD_NAME, required = true)
        BigDecimal getValor();

        @NotNull
        @NotBlank
        @Size(min = 3, max = 3)
        @ApiModelProperty(name = CURRENCY_FIELD_NAME, required = true, allowableValues = "BRL")
        String getMoeda();

    }

}
