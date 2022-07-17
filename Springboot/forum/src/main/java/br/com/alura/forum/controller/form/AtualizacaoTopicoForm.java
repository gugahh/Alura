package br.com.alura.forum.controller.form;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AtualizacaoTopicoForm {

    @NotNull
    @Size(min = 5)
    private String titulo;

    @NotNull
    @Size(min = 5, message = "O campo mensagem deve ter no minimo 5 caracteres.")
    private String mensagem;

    public Topico atualizar(Long id, Topico topico) {

        topico.setTitulo(this.titulo);
        topico.setMensagem(this.mensagem);

        return topico;
    }
}
