package com.ead.authuser.clients;

import com.ead.authuser.dtos.CourseDto;
import com.ead.authuser.dtos.ResponsePageDto;
import com.ead.authuser.services.UtilsService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Log4j2
@Component
public class CourseClient {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UtilsService utilsService;

    @Value("${ead.api.url.course}")
    String REQUEST_URL_COURSE;

//    @Retry(name = "retryInstance", fallbackMethod = "retryFallback") // Instância de retentativas do resilience4j
//    @CircuitBreaker(name = "circuitbreakerInstance", fallbackMethod = "circuitbreakerfallback")
    @CircuitBreaker(name = "circuitbreakerInstance")
    public Page<CourseDto> getAllCoursesByUser(
            UUID userId, Pageable pageable
    ) {
        List<CourseDto> searchResult = null;

        // Construir a url
        String url = REQUEST_URL_COURSE + utilsService.creatUrlGetAllCoursesByUser(userId, pageable);

        log.debug("Request URL: {} ", url);
        log.info("Request URL: {} ", url);

        try {
            // Parametriza a resposta com a nossa ResponsePageDto
            ParameterizedTypeReference<ResponsePageDto<CourseDto>> responseType =
                    new ParameterizedTypeReference<ResponsePageDto<CourseDto>>() {};

            // Chamada passando a url, 'metodo', e nossa resposta parametrizada com ResponsePageDto
            ResponseEntity<ResponsePageDto<CourseDto>> result =
                    restTemplate.exchange(url, HttpMethod.GET, null, responseType);

            // Atribui a resposta à List<CourseDto>
            searchResult = result.getBody().getContent();
            log.debug("Response Number of Elements: {} ", searchResult.size());

        } catch (HttpStatusCodeException e) {
            log.error("Error request /courses {} ", e);
        }

        log.info("Ending request /courses userId {} ", userId);

        // Retorna a paginação de CourseDto
        return new PageImpl<>(searchResult);
    }

    public Page<CourseDto> circuitbreakerfallback (
            UUID userId,
            Pageable pageable,
            Throwable t
    ) {
        log.error("Inside rety retryfallback, cause - {}", t.toString());
        List<CourseDto> searchResult = new ArrayList<>(); // Paginação vazia, pois o retorno e parâmetros tem que ser iguais aos do metodo original.
        return new PageImpl<>(searchResult);
    }

    public Page<CourseDto> retryFallback (
            UUID userId,
            Pageable pageable,
            Throwable t
    ) {
        log.error("Inside rety retryfallback, cause - {}", t.toString());
        List<CourseDto> searchResult = new ArrayList<>(); // Paginação vazia, pois o retorno e parâmetros tem que ser iguais aos do metodo original.
        return new PageImpl<>(searchResult);
    }

}