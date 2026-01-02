package com.associacao.api.v1.SuperClasses.cors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CorsConfiguration é uma classe de configuração que implementa WebMvcConfigurer
 * para definir as regras de CORS (Cross-Origin Resource Sharing).
 *
 * <p>Esta configuração permite que o servidor aceite requisições de origens
 * especificadas e métodos HTTP permitidos. A configuração de CORS é aplicada
 * globalmente a todas as rotas do aplicativo.</p>
 *
 * <p>Os valores de origem permitidos, métodos permitidos, cabeçalhos permitidos,
 * cabeçalhos expostos e a flag de credenciais são definidos através de variáveis
 * de ambiente, permitindo flexibilidade para ajustar as configurações de CORS
 * sem modificar o código-fonte.</p>
 */
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;

    @Value("${cors.allowed-methods}")
    private String allowedMethods;

    @Value("${cors.allowed-headers}")
    private String allowedHeaders;

    @Value("${cors.exposed-headers}")
    private String exposedHeaders;

    @Value("${cors.allow-credentials}")
    private boolean allowCredentials;

    /**
     * Configura as regras de CORS para o aplicativo.
     * <p>Permite que qualquer origem definida na variável de ambiente 'cors.allowed-origins'
     * acesse os recursos do servidor usando os métodos HTTP especificados.</p>
     *
     * @param registry O registro de mapeamento de CORS .
     */

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(allowedOrigins)
                .allowedMethods(allowedMethods.split(","))
                .allowedHeaders(allowedHeaders.split(","))
                .exposedHeaders(exposedHeaders.split(","))
                .allowCredentials(allowCredentials);
    }
}
