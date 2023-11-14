package massimiliano.AssegnaDispositivo.Payloads;

import java.util.Date;
import java.util.List;

public record ErrorsReponseWithListDTO(String message, Date timeStamp, List<String> errorList) {

}
