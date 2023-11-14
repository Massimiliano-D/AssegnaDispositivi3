package massimiliano.AssegnaDispositivo.Service;

import massimiliano.AssegnaDispositivo.Entities.Dispositivo;
import massimiliano.AssegnaDispositivo.Entities.Utente;
import massimiliano.AssegnaDispositivo.Exeptions.BadRequestException;
import massimiliano.AssegnaDispositivo.Exeptions.NotFoundExceptionDispositivo;
import massimiliano.AssegnaDispositivo.Payloads.DispositivoDTO;
import massimiliano.AssegnaDispositivo.Repository.DispositivoRepository;
import massimiliano.AssegnaDispositivo.Repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class DispositivoService {
    @Autowired
    private DispositivoRepository dispositivoRepository;
    @Autowired
    private UtenteRepository utenteRepository;

    public Dispositivo save(DispositivoDTO body) {
        Dispositivo newDispositivo = new Dispositivo();

        newDispositivo.setTipo(body.tipo());
        newDispositivo.setStato(body.stato());
        return dispositivoRepository.save(newDispositivo);
    }

    public Page<Dispositivo> getDispositivo(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
        return dispositivoRepository.findAll(pageable);
    }

    public Dispositivo findById(int id) {

        return dispositivoRepository.findById(id).orElseThrow(() -> new NotFoundExceptionDispositivo(id));

    }

    public Dispositivo findAndUpdateById(int id, Dispositivo body) {
        Dispositivo found1 = this.findById(id);

        found1.setId(id);
        found1.setStato(body.getStato());
        found1.setTipo(body.getTipo());


        return found1;

    }


    public void findAndDeleteById(int id) {
        Dispositivo found1 = this.findById(id);
        dispositivoRepository.delete(found1);
    }

    public void AssignDeviceToUser(int utenteId, int dispositivoId) throws ChangeSetPersister.NotFoundException {
        Dispositivo target = dispositivoRepository.findById(dispositivoId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        if (target.getStato().equals("assegnato")) {
            throw new BadRequestException("il dispositivo e' gia assegnato");
        } else if (target.getStato().equals("manutenzione")) {
            throw new BadRequestException("il dispositivo e' in manutenzione");
        } else if (target.getStato().equals("dismesso")) {
            throw new BadRequestException("il dispositivo e' stato dismesso");
        } else if (target.getStato().equals("disponibile")) {
            Utente owner = utenteRepository.findById(utenteId).orElseThrow(ChangeSetPersister.NotFoundException::new);
            target.setUtente(owner);
            target.setStato("assegnato");
            dispositivoRepository.save(target);
        }


    }
}

