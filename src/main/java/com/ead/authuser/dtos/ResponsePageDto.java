package com.ead.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ResponsePageDto<T> extends PageImpl<T> {

    /**
     * Construtor utilizado para desserializar uma resposta paginada em JSON
     * para um objeto {@link ResponsePageDto}, que extends {@link org.springframework.data.domain.PageImpl}
     *
     * {@link com.fasterxml.jackson.annotation.JsonCreator} Faz com que esse DTO seja usado na desserialização
     *
     * Usamos o modo {@code PROPERTIES}, pois recebemos vários parâmetros
     *
     * Os parâmetros recebidos no métodos são os mesmos retornados pela API (ex.: Postman)
     * */
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public ResponsePageDto(@JsonProperty("content") List<T> content,
                           @JsonProperty("number") int number,
                           @JsonProperty("size") int size,
                           @JsonProperty("totalElements") Long totalElements,
                           @JsonProperty("pageable") JsonNode pageable,
                           @JsonProperty("last") boolean last,
                           @JsonProperty("totalPages") int totalPages,
                           @JsonProperty("sort") JsonNode sort,
                           @JsonProperty("first") boolean first,
                           @JsonProperty("empty") boolean empty) {

        super(content, PageRequest.of(number, size), totalElements);
    }

    // Implementação obrigatória do PageImpl
    public ResponsePageDto(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    // Implementação obrigatória do PageImpl
    public ResponsePageDto(List<T> content) {
        super(content);
    }

}