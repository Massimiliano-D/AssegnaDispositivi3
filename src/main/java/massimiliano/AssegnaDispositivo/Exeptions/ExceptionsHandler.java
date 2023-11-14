package massimiliano.AssegnaDispositivo.Exeptions;


import massimiliano.AssegnaDispositivo.Payloads.ErrorsReponseDTO;
import massimiliano.AssegnaDispositivo.Payloads.ErrorsReponseWithListDTO;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // 400
    public ErrorsReponseWithListDTO handleBadRequest(BadRequestException e) {
        if (e.getErrors() != null) {
            List<String> errorList = e.getErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
            return new ErrorsReponseWithListDTO(e.getMessage(), new Date(), errorList);
        } else {
            return new ErrorsReponseWithListDTO(e.getMessage(), new Date(), new ArrayList<>());
        }

    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    public ErrorsReponseDTO handleNotFound(ChangeSetPersister.NotFoundException e) {
        return new ErrorsReponseDTO(e.getMessage(), new Date());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
    public ErrorsReponseDTO handleGeneric(Exception e) {
        e.printStackTrace();
        return new ErrorsReponseDTO("Ha provato a spegnere e riaccendere...?", new Date());
    }
}