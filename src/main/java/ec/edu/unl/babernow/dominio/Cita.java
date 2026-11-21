package ec.edu.unl.babernow.dominio;
/*
@authors: Camilo Merino, Carlos Cevallos, Tyrone Encarnación, David Luna
**/
import java.time.LocalDateTime;

public class Cita {

    public static final String ESTADO_RESERVADA = "RESERVADA";
    public static final String ESTADO_CONFIRMADA = "CONFIRMADA";
    public static final String ESTADO_CANCELADA = "CANCELADA";

    // lo comparten todas las citas y sirve para asignar el id
    private static int contadorCitas = 0;

    private int idCita;
    private Cliente cliente;
    private Barbero barbero;
    private LocalDateTime fechaHora;
    private String estado;

    public Cita(Cliente cliente, Barbero barbero, LocalDateTime fechaHora) {
        if (cliente == null || barbero == null) {
            throw new IllegalArgumentException("El cliente y el barbero son obligatorios.");
        }
        if (fechaHora == null || !fechaHora.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("La fecha y hora de la cita deben ser futuras.");
        }
        this.cliente = cliente;
        this.barbero = barbero;
        this.fechaHora = fechaHora;
        this.estado = ESTADO_RESERVADA;
        contadorCitas++;
        this.idCita = contadorCitas;
    }

    public void confirmar() {
        if (estaCancelada()) {
            throw new IllegalArgumentException("No se puede confirmar una cita cancelada.");
        }
        estado = ESTADO_CONFIRMADA;
    }

    public void cancelar() {
        if (estaCancelada()) {
            throw new IllegalArgumentException("La cita ya está cancelada.");
        }
        estado = ESTADO_CANCELADA;
    }

    public boolean estaCancelada() {
        return estado.equals(ESTADO_CANCELADA);
    }

    public static int getContadorCitas() {
        return contadorCitas;
    }

    public int getIdCita() {
        return idCita;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Barbero getBarbero() {
        return barbero;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Cita #" + idCita + " | " + fechaHora.toString().replace("T", " ")
                + " | Cliente: " + cliente.getNombre()
                + " | Barbero: " + barbero.getNombre() + " | " + estado;
    }
}
