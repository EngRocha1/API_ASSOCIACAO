package com.associacao.api.SpringDoc;

import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringDocConfig {

    @Value("${api.dominio}")
    private String apiDominio;

    @Value("${api.service}")
    private String apiService;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .openapi("3.1.0")
                .info(new Info()
                        .title("Documentação da API ASSOCIAÇÃO")
                        .description("<b>Comando para acrescentar o usuário root no banco de dados:<br><br>" +
                                "#Listar bancos de dados: \n<br>" +
                                "SHOW DATABASES;\n<br><br>" +
                                "USE M4r14DB_4dm;\n<br><br>" +
                                "INSERT INTO `users` (`id`, `created_by`, `created_date`, `modified_by`, `modified_date`, `ativo`, `login`, `password`, `role`, `verify_code`, `secret`) VALUES\n" +
                                "(UUID(), 'root', CURRENT_TIMESTAMP, 'root', CURRENT_TIMESTAMP, b'1', 'root', '$2a$10$O1x8PmQOdIy5Vq1vBwtNTum3OrMlJevhsOpaj9dLKN2xvp7wGEqjC', 0, 0,NULL);\n" +
                                "<br><br>" +
                                "QUIT\n")
                        .version("v1.0")
                        .contact(new Contact().name("Equipe de Suporte").email("tarcisio.rocha.engenheiro@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .servers(List.of(new Server().url(apiDominio + apiService)))
                .components(new Components().addSecuritySchemes("bearer-key",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("login")
                .displayName("1 - Acesso Inicial")
                .pathsToMatch("/auth/login")
                .build();
    }

    @Bean
    public GroupedOpenApi operationalApi() {
        return GroupedOpenApi.builder()
                .group("gestao")
                .displayName("2 - Gestão Operacional")
                .pathsToMatch(
                        "/servidor/**",
                        "/file/**",
                        "/documentospessoais/**",
                        "/tipodedocumento/**",
                        "/dadosbancarios/**",
                        "/banco/**",
                        "/informacoesassentamento/**",
                        "/diariooficial/**",
                        "/superintendencia/**",
                        "/diretoria/**",
                        "/lotacao/**",
                        "/orgaogov/**",
                        "/simbolo/**",
                        "/vinculo/**",

                        // --- Módulos Adicionais (Preservados) ---
                        "/treinamentos/**",
                        "/tipotreinamentos/**",
                        "/treinamentocurso/**",
                        "/grupo-do-curso/**",
                        "/afastamentos/**",
                        "/tipoafastamento/**",
                        "/fluxoaprovacao/**",
                        "/suspensao/**",
                        "/cid/**",
                        "/informacoesescolares/**",
                        "/curso/**",
                        "/formacaoacademica/**",
                        "/instituicaoensino/**",
                        "/nivelensino/**",
                        "/periodododia/**",
                        "/semestrecurso/**",
                        "/statusformacao/**",
                        "/tipoinstituicao/**"
                )
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("auditoria")
                .displayName("3 - Auditoria e Admin")
                .pathsToMatch(
                        "/auth/**",
                        "/v1/audit/**",
                        "/informacoessensiveis/**",
                        "/genero/**",
                        "/expressaodegenero/**",
                        "/estadocivil/**",
                        "/orientacaosexual/**",
                        "/pronomepreferido/**",
                        "/racaetnia/**",
                        "/nacionalidades/**",
                        "/neurodiversidade/**",
                        "/cordao/**",
                        "/deficiencias/**",
                        "/geracoes/**",
                        "/doencascronicasraras/**",
                        "/corpele/**"
                )
                .build();
    }
}