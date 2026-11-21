package ec.edu.unl.babernow.dominio;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Cliente {

    private int id;
    private String nombre;
    private String telefono;
    private ArrayList<Cita> citas = new ArrayList<>();

    public Cliente(int id, String nombre, String telefono) {
        this.id = id;
        setNombre(nombre);
        setTelefono(telefono);
    }

    public Cita reservarCita(Barbero barbero, LocalDateTime fechaHora) {
        if (barbero == null) {
            throw new IllegalArgumentException("El barbero es obligatorio.");
        }
        if (!barbero.estaDisponible(fechaHora)) {
            throw new IllegalArgumentException("El barbero " + barbero.getNombre()
                    + " no está disponible en ese horario.");
        }
        Cita cita = new Cita(this, barbero, fechaHora);
        cita.confirmar();
        // la cita se guarda en el cliente y en la agenda del barbero
        citas.add(cita);
        barbero.agregarCita(cita);
        return cita;
    }

    public ArrayList<Cita> consultarCitas() {
        return citas;
    }

    public void cancelarCita(int idCita) {
        for (Cita c : citas) {
            if (c.getIdCita() == idCita) {
                c.cancelar();
                return;
            }
        }
        throw new IllegalArgumentException("No existe la cita con id " + idCita + " para este cliente.");
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente no puede estar vacío.");
        }
        this.nombre = nombre.trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null) {
            throw new IllegalArgumentException("El teléfono es obligatorio.");
        }
        String numero = telefono.trim();
        if (numero.length() < 7 || numero.length() > 10) {
            throw new IllegalArgumentException("El teléfono debe tener entre 7 y 10 dígitos.");
        }
        // revisa que todos los caracteres sean números
        for (int i = 0; i < numero.length(); i++) {
            if (!Character.isDigit(numero.charAt(i))) {
                throw new IllegalArgumentException("El teléfono solo debe contener números.");
            }
        }
        this.telefono = numero;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + nombre + " - Tel: " + telefono;
    }
}
