package massimiliano.AssegnaDispositivo.Exeptions;


public class NotFoundExceptionUtente extends RuntimeException {
    public NotFoundExceptionUtente(int id) {
        super("Utente:" + " " + id + " " + "NON trovato!!");
    }

    public NotFoundExceptionUtente(String message) {
        super(message);
    }
}