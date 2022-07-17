package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.alura.forum.controller.dto.TopicoDto;
import br.com.alura.forum.controller.form.AtualizacaoTopicoForm;
import br.com.alura.forum.controller.form.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping(value = "/topicos")
public class TopicosController {

    @Autowired
    private TopicoRepository topicoRepository;

    public static final String CACHE = "Topicos.Listar";

    @Autowired
    private CursoRepository cursoRepository;

    @RequestMapping(method = RequestMethod.GET)
    @Cacheable(value = TopicosController.CACHE)
    public Page<TopicoDto> listar(
            @RequestParam(required = false) String nomeCurso,
            @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable paginacao) {

        //Pageable paginacao = PageRequest.of(pagina, qtd, Sort.Direction.ASC, ordenacao);

        if(nomeCurso==null) {
            Page<Topico> topicos = topicoRepository.findAll(paginacao);
            return TopicoDto.converter(topicos);
        } else {
            Page<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso, paginacao);
            return TopicoDto.converter(topicos);
        }
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = TopicosController.CACHE, allEntries = true)
    public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm topicoForm,
                                               UriComponentsBuilder uriBuilder) {

        Topico topico = topicoForm.converter(cursoRepository);
        Topico topicoSaved = topicoRepository.save(topico);

        URI uri = uriBuilder.path("/topicos/{id}")
                .buildAndExpand(topicoSaved.getId())
                .toUri();
        return ResponseEntity.created(uri)
                .body(new TopicoDto(topicoSaved));

    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable("id") Long umId) {
        Optional<Topico> topico = topicoRepository.findById(umId);
        if (topico.isPresent()) {
            return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = TopicosController.CACHE, allEntries = true)
    public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id,
                                               @RequestBody @Valid AtualizacaoTopicoForm form) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            Topico topicoUpdate = form.atualizar(id, topico.get());
            return ResponseEntity.ok(new TopicoDto(topicoUpdate));
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = TopicosController.CACHE, allEntries = true)
    public ResponseEntity remover(@PathVariable Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        if (topico.isPresent()) {
            topicoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
