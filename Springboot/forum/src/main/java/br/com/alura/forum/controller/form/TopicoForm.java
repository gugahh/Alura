package br.com.alura.forum.controller.form;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import lombok.Getter;
import lombok.Setter;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TopicoForm {

    @NotNull
    @Size(min = 5)
    private String titulo;

    @NotNull
    @Size(min = 5, message = "O campo mensagem deve ter no minimo 5 caracteres.")
    private String mensagem;

    @NotNull
    @Size(min = 3)
    private String nomeCurso;

    public Topico converter(CursoRepository cursoRepository) {
        return new Topico(
                this.getTitulo(),
                this.getMensagem(),
                cursoRepository.findByNome(this.nomeCurso));
    }
}
