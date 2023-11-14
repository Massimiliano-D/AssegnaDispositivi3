package massimiliano.AssegnaDispositivo.Controller;

import io.jsonwebtoken.io.IOException;
import massimiliano.AssegnaDispositivo.Entities.Utente;
import massimiliano.AssegnaDispositivo.Exeptions.BadRequestException;
import massimiliano.AssegnaDispositivo.Payloads.UtenteDTO;
import massimiliano.AssegnaDispositivo.Payloads.UtenteLoginDTO;
import massimiliano.AssegnaDispositivo.Payloads.UtenteLoginSuccDTO;
import massimiliano.AssegnaDispositivo.Service.AuthService;
import massimiliano.AssegnaDispositivo.Service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UtenteService utenteService;

    @PostMapping("/login")
    public UtenteLoginSuccDTO login(@RequestBody UtenteLoginDTO body) {

        return new UtenteLoginSuccDTO(authService.authenticateUser(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public Utente saveUser(@RequestBody @Validated UtenteDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return utenteService.save(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}