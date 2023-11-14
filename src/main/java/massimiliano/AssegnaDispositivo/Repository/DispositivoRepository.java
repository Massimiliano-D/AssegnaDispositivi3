package massimiliano.AssegnaDispositivo.Repository;

import massimiliano.AssegnaDispositivo.Entities.Dispositivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, Integer> {

}