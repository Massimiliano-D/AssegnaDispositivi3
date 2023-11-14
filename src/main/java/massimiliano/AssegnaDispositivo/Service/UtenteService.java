package massimiliano.AssegnaDispositivo.Service;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import massimiliano.AssegnaDispositivo.Entities.Utente;
import massimiliano.AssegnaDispositivo.Exeptions.BadRequestException;
import massimiliano.AssegnaDispositivo.Exeptions.NotFoundExceptionUtente;
import massimiliano.AssegnaDispositivo.Payloads.UtenteDTO;
import massimiliano.AssegnaDispositivo.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class UtenteService {
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private UtenteRepository utenteRepository;

    public Utente save(UtenteDTO body) {
        utenteRepository.findByEmail(body.email()).ifPresent(user -> {
            throw new BadRequestException("L'email " + user.getEmail() + " è già utilizzata!");
        });
        Utente newUtente = new Utente();
        if (body.avatar() == null) {
            newUtente.setAvatar("http://ui-avatars.com/api/?name=" + body.name() + "+" + body.surname());
        } else {
            newUtente.setAvatar(body.avatar());
        }

        newUtente.setName(body.name());
        newUtente.setSurname(body.surname());
        newUtente.setEmail(body.email());
        newUtente.setUsername(body.username());
        return utenteRepository.save(newUtente);
    }

    public Page<Utente> getUtente(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return utenteRepository.findAll(pageable);
    }

    public Utente findById(int id) {
        return utenteRepository.findById(id).orElseThrow(() -> new NotFoundExceptionUtente(id));

    }

    public Utente findAndUpdateById(int id, Utente body) {
        Utente found = this.findById(id);
        found.setId(id);
        found.setName(body.getName());
        found.setSurname(body.getSurname());
        found.setUsername(body.getUsername());
        return found;
    }

    public void findAndDeleteById(int id) {
        Utente found = this.findById(id);
        utenteRepository.delete(found);
    }

    public Utente uploadPicture(MultipartFile file, int id) throws IOException {
        Utente found = this.findById(id);
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        found.setAvatar(url);
        return utenteRepository.save(found);
    }

    public Utente findByEmail(String email) {
        return utenteRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundExceptionUtente("Utente con email " + email + " non trovato!"));
    }

}
