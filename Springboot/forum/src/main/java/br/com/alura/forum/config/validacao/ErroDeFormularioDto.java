package br.com.alura.forum.config.validacao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
public class ErroDeFormularioDto {

    private String campo;

    private String erro;
}
