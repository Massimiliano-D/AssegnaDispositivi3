package massimiliano.AssegnaDispositivo.Exeptions;

public class NotFoundExceptionDispositivo extends RuntimeException {
    public NotFoundExceptionDispositivo(int id) {
        super("Dispositivo:" + " " + id + " " + "NON trovato!!");
    }
}
