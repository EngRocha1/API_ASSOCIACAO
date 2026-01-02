package com.example.work3.SpringDoc;
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
                        .title("Documentação da API WORK³")
                        .description("<b>O projeto Work 3, é um projeto voltado para o cadastro de informações voltadas ao gerenciamento de pessoas.<br>" +
                                "O projeto contempla além do cadastro funcional dos servidores:<br><br>" +
                                " - Dados de afastamentos(férias, médico, licenças, etc..),<br>" +
                                " - Treinamentos(frequência, certificados, etc.), <br>" +
                                " - Matriz de competência(feedback, plano de desenvolvimento pessoal).<br><br>" +
                                "Atualização nº 22 de 21/01/2025 <b> - 1ª Entrega do ano de 2024, já com o cadastro funcional liberado e plataforma de acondicionamento de afastamentos.<br>" +
                                "<br><br>" +
                                "Atualização do ano de 2025:" + "<br><br>" +
                                "SPRINT 01 - 03/01/2025:" + "<br>" +
                                "Implementação de nova lógica de negócio em referência a release de 2025 com Fernando, será dividivo em novas SPRINTs ao longo do mês de Janeiro e Fevereiro" + "<br><br>" +
                                "SPRINT 02 - 10/01/2025:" + "<br>" +
                                "Implementação da nova lógica de delete/update em: 2 classes de Treinamento." + "<br><br>" +
                                "SPRINT 03 - 17/01/2025:" + "<br>" +
                                "Implementação da nova lógica de delete/update em: 2 classes de Servidor e Users." + "<br><br>" +
                                "SPRINT 04 - 24/01/2025:" + "<br>" +
                                "Resolução de bugs relacionados a atualização da classe Users e atualização das 9 classes Afastamentos, Dados bancários e Documentos pessoais." + "<br><br>" +
                                "SPRINT 05 - 03/02/2025:" + "<br>" +
                                "Implementação da nova lógica de delete/update em: 18 classes dentro de Assentamentos e Informações escolares." + "<br><br>" +
                                "SPRINT 06 - 10/02/2025:" + "<br>" +
                                "Implementação da nova lógica de delete/update em: 8 classes dentro de Informações Sensíveis." + "<br><br>" +
                                "SPRINT 07 - 17/02/2025:" + "<br>" +
                                "Implementação da API https://cid10.cpp-ti.com.br/api, para atualização da base de dados de CID médicos, de forma massiva na rota atualizar-cids." + "<br><br>" +
                                "SPRINT 08 - 24/02/2025:" + "<br>" +
                                "Implementação da Classe Treinamento Curso, para imputar na classe treinamentos." + "<br><br>" +
                                "SPRINT 09 - 03/03/2025:" + "<br>" +
                                "Implementação da classe Treinamento Curso dentro da Classe Treinamento e suas dependências." + "<br><br>" +
                                "SPRINT 10 - 10/03/2025:" + "<br>" +
                                "Atualização do SpringDoc para visualização das atualizações realizadas e execução em novo POD para checar atualizações na API via Browser." + "<br><br>" +
                                "<br>" +
                                "SPRINT 11 - 17/03/2025:" + "<br>" +
                                "Implantação do FronEnd em Next.js 11, conforme projeobase da DIRETORIA, aba de Login." + "<br><br>" +
                                "<br>" +
                                "SPRINT 12 - 24/03/2025:" + "<br>" +
                                "Verificação da lógica de reutilização do id usuario servidor, Correção do método de criação e validação do token jwt, para encaminhar o id do usuário via token." + "<br><br>" +
                                "<br>" +
                                "Acesse o Banco de dados no Mysql para acrescentar o usuário root, para assim poder realizar os testes, utilize os comando abaixo no prompt do Linux:<br><br>" +
                                "#Acesse o Container do Mysql\n<br>" +
                                "docker exec -it mysql mysql -uM4r14DB_4dm -p0m4r14c0ns3b1d4s3mp3c4d0\n<br><br>" +
                                "Comando para acrescentar o usuário root no banco de dados:<br><br>" +
                                "#Listar bancos de dados: \n<br>" +
                                "SHOW DATABASES;\n<br><br>" +
                                "\n" +
                                "#Selecionar o banco de dados que você criou: \n<br>" +
                                "USE M4r14DB_4dm;\n<br><br>" +
                                "\n" +
                                "#Inserir Usuário root no banco de dados com senha padrão root123@: \n<br>" +
                                "\n" +
                                "INSERT INTO `users` (`id`, `created_by`, `created_date`, `modified_by`, `modified_date`, `ativo`, `login`, `password`, `role`, `verify_code`, `secret`) VALUES\n" +
                                "(UUID(), 'root', CURRENT_TIMESTAMP, 'root', CURRENT_TIMESTAMP, b'1', 'root', '$2a$10$O1x8PmQOdIy5Vq1vBwtNTum3OrMlJevhsOpaj9dLKN2xvp7wGEqjC', 0, 0,NULL);\n" +
                                "<br><br>" +
                                "<#Sair do Mysql:\n<br>" +
                                "QUIT\n" +
                                "<br><br>" +
                                "<table border=1>" +
                                        "<tr><td colspan=3 bgcolor=#003300>" +
                                        "<center><font color=white>" +
                                        "<b>Usuarios Padrão de teste:" +
                                        "</font></center>" +
                                        "</b></td></tr>" +
                                "<tr bgcolor=#003300>" +
                                        "<td><font color=white>" +
                                "<center><b>login</b></center></font></td>" +
                                        "<td><font color=white>" +
                                "<center><b>password</b></center></font></td>" +
                                        "<td><font color=white>" +
                                "<center><b>MFA Google Aut.:</b></center></font></td>" +
                                "</tr>" +
                                "<tr>" +
                                         "<td><center>root</center></td>" +
                                         "<td><center>root123@</center></td>" +
                                         "<td><center>" +
                                         "<img src='' alt='QR Code'></center></td>" +
                                "</tr>" +

                                 "</table>")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Equipe de Suporte")
                                .email("tarcisio.rocha@segov.pi.gov.br")
                                .url("https://example.com/support"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .servers(List.of(new Server().url(apiDominio + apiService)))
                .components(new Components().addSecuritySchemes("bearer-key",
                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")))
                .addSecurityItem(new SecurityRequirement().addList("bearer-key"));
    }
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
               .group("public")
               .pathsToMatch(
                       "/auth/**",
                       "/servidor/**",
                       "/file/**",

                       "/treinamentos/**",
                       "/tipotreinamentos/**",
                       "/treinamentocurso/**" +
                       "/grupo-do-curso/**" +

                       "/documentospessoais/**",
                       "/tipodedocumento/**",

                       "/dadosbancarios/**",
                       "/banco/**",

                       "/afastamentos/**",
                       "/tipoafastamento/**",
                       "/fluxoaprovacao/**",
                       "/suspensao/**",
                       "/cid/**",

                       "/informacoesassentamento/**",
                       "/diariooficial/**",
                       "/superintendencia/**",
                       "/diretoria/**",
                       "/lotacao/**",
                       "/orgaogov/**",
                       "/simbolo/**",
                       "/vinculo/**",

                       "/informacoesescolares/**",
                       "/curso/**",
                       "/formacaoacademica/**",
                       "/instituicaoensino/**",
                       "/nivelensino/**",
                       "/periodododia/**",
                       "/semestrecurso/**",
                       "/statusformacao/**",
                       "/tipoinstituicao/**",

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
                       "/corpele/**")

                .build();
    }

}
