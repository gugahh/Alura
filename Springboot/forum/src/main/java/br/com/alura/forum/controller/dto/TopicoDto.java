package br.com.alura.forum.controller.dto;

import br.com.alura.forum.modelo.Topico;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class TopicoDto {

    private Long id;
    private String titulo;
    private String mensagem;

    private LocalDateTime dataCriacao;

    public TopicoDto(Topico topico) {
        this.id = topico.getId();
        this.mensagem = topico.getMensagem();
        this.titulo = topico.getTitulo();
        this.dataCriacao = topico.getDataCriacao();
    }

    public TopicoDto() {
    }

    public static Page<TopicoDto> converter(Page<Topico> topicos) {
        return topicos.map(TopicoDto::new);
    }

}

