package ec.edu.unl.babernow.dominio;
/*
*@authors: Camilo Merino, Carlos Cevallos, Tyrone Encarnación, David Luna
**/
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Barbero {

    private int idBarbero;
    private String nombre;
    private String especialidad;
    private boolean activo;
    private ArrayList<Cita> agenda = new ArrayList<>();

    public Barbero(int idBarbero, String nombre, String especialidad) {
        this.idBarbero = idBarbero;
        setNombre(nombre);
        setEspecialidad(especialidad);
        this.activo = true;
    }

    public void agregarCita(Cita cita) {
        agenda.add(cita);
    }

    public ArrayList<Cita> consultarAgenda() {
        return agenda;
    }

    public void actualizarDisponibilidad(boolean activo) {
        this.activo = activo;
    }

    // por ahora solo compara la hora exacta de inicio de las citas
    public boolean estaDisponible(LocalDateTime fechaHora) {
        if (!activo) {
            return false;
        }
        for (Cita c : agenda) {
            if (!c.estaCancelada() && c.getFechaHora().equals(fechaHora)) {
                return false;
            }
        }
        return true;
    }

    public int getIdBarbero() {
        return idBarbero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del barbero no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        if (especialidad == null || especialidad.trim().isEmpty()) {
            throw new IllegalArgumentException("La especialidad no puede estar vacía.");
        }
        this.especialidad = especialidad.trim();
    }

    public boolean isActivo() {
        return activo;
    }

    @Override
    public String toString() {
        String texto = "[" + idBarbero + "] " + nombre + " - " + especialidad;
        if (!activo) {
            texto = texto + " (inactivo)";
        }
        return texto;
    }
}
