package br.com.alura.forum.config.validacao;

import lombok.Getter;

@Getter
public class ErroEntityNotFoundDto {

    private String entityName;
    private String message;
    private String path;

    public ErroEntityNotFoundDto(String entityName, String message, String path) {
        this.entityName = entityName;
        this.message = message;
        this.path = path;
    }
}
