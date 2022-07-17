package br.com.alura.forum.config.validacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErroDeValidacaoHandler {

    @Autowired
    MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErroDeFormularioDto> trataErroValidacao(MethodArgumentNotValidException exception) {
        List<ErroDeFormularioDto> errosDtoList = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        fieldErrors.forEach((umErro) -> {

            // Obtem a mensagem de erro localizada
            String messagemErro = messageSource.getMessage(umErro, LocaleContextHolder.getLocale());

            errosDtoList.add(
                    ErroDeFormularioDto.builder()
                            .campo(umErro.getField())
                            .erro(messagemErro)
                            .build());
        });

        return errosDtoList;
    }

//    @ExceptionHandler(EntityNotFoundException.class)
//    public List<ErroEntityNotFoundDto> trataErroEntity(EntityNotFoundException exception) {
//        List<ErroEntityNotFoundDto> erroDtoList = new ArrayList<>();
//        exception.
//
//
//        return null;
//    }

}
