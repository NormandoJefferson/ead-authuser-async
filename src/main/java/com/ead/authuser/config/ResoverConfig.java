package com.ead.authuser.config;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * Converte os parâmetros passados nos controllers para tipos básicos java (ex: enums, date, localDate).
 */
@Configuration
public class ResoverConfig extends WebMvcConfigurationSupport {

    /**
     * Adiciona resolvers personalizados para argumentos de métodos nos controllers.
     *
     * <p>
     * <ul>
     *   <li>{@link SpecificationArgumentResolver} – Permite o uso de filtros dinâmicos via query parameters,
     *       geralmente usado com Spring Data Specifications.</li>
     *   <li>{@link PageableHandlerMethodArgumentResolver} – Permite a injeção automática de objetos {@link org.springframework.data.domain.Pageable}
     *       nos métodos dos controladores, com suporte a paginação via parâmetros como {@code ?page=0&size=10}.</li>
     * </ul>
     * </p>
     *
     * @param argumentResolvers lista de resolvers de argumentos onde os resolvers personalizados serão adicionados.
     */
    @Override
    public void addArgumentResolvers (
            List<HandlerMethodArgumentResolver> argumentResolvers
    ) {
        argumentResolvers.add(new SpecificationArgumentResolver()); // Biblioteca inserida no pom.xml
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();
        argumentResolvers.add(resolver);
        super.addArgumentResolvers(argumentResolvers); // metodh do WebMvcConfigurationSupport
    }

}