package massimiliano.AssegnaDispositivo.Controller;

import massimiliano.AssegnaDispositivo.Entities.Utente;
import massimiliano.AssegnaDispositivo.Exeptions.BadRequestException;
import massimiliano.AssegnaDispositivo.Payloads.UtenteDTO;
import massimiliano.AssegnaDispositivo.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/utenti")
public class UtenteController {
    @Autowired
    private UtenteService utenteService;

    @GetMapping("")
    public Page<Utente> getUtente(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy) {

        return utenteService.getUtente(page, size, orderBy);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public Utente saveUtente(@RequestBody @Validated UtenteDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        }
        return utenteService.save(body);
    }

    @GetMapping("/{id}")
    public Utente findById(@PathVariable int id) {
        return utenteService.findById(id);
    }

    @PutMapping("/{id}")
    public Utente findAndUpdateById(@PathVariable int id, @RequestBody Utente body) {
        return utenteService.findAndUpdateById(id, body);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // <-- 204 NO CONTENT
    public void findAndDeleteById(@PathVariable int id) {
        utenteService.findAndDeleteById(id);
    }

    @PostMapping("/{id}/upload")
    public Utente uploadAvatar(@RequestParam("avatar") MultipartFile body, @PathVariable int id) throws IOException {
        return utenteService.uploadPicture(body, id);
    }
}
