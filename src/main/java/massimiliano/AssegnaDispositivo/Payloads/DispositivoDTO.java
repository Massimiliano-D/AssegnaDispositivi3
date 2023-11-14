package massimiliano.AssegnaDispositivo.Payloads;

import jakarta.validation.constraints.NotEmpty;

public record DispositivoDTO(@NotEmpty(message = "Campo del tipo Obbligatorio!")
                             String tipo,
                             @NotEmpty(message = "Campo dello stato Obbligatorio!")
                             String stato) {
}
