package ec.edu.unl.babernow.vista;

import ec.edu.unl.babernow.dominio.Barbero;
import ec.edu.unl.babernow.dominio.Cita;
import ec.edu.unl.babernow.dominio.Cliente;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static ArrayList<Cliente> clientes = new ArrayList<>();
    private static ArrayList<Barbero> barberos = new ArrayList<>();

    public static void main(String[] args) {
        // barberos de prueba para poder reservar desde el inicio
        barberos.add(new Barbero(1, "Carlos Pérez", "Corte clásico"));
        barberos.add(new Barbero(2, "Marco Ríos", "Barba y degradado"));

        int opcion = -1;
        do {
            System.out.println("\n===== BaberNow =====");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Registrar barbero");
            System.out.println("3. Reservar cita");
            System.out.println("4. Consultar citas de un cliente");
            System.out.println("5. Cancelar cita");
            System.out.println("6. Activar/desactivar barbero");
            System.out.println("7. Ver contador de citas");
            System.out.println("0. Salir");
            try {
                opcion = leerEntero("Opción: ");
                switch (opcion) {
                    case 1:
                        registrarCliente();
                        break;
                    case 2:
                        registrarBarbero();
                        break;
                    case 3:
                        reservarCita();
                        break;
                    case 4:
                        consultarCitas();
                        break;
                    case 5:
                        cancelarCita();
                        break;
                    case 6:
                        cambiarEstadoBarbero();
                        break;
                    case 7:
                        System.out.println("Citas creadas: " + Cita.getContadorCitas());
                        break;
                    case 0:
                        System.out.println("Hasta pronto.");
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    private static void registrarCliente() {
        String nombre = leerTexto("Nombre: ");
        String telefono = leerTexto("Teléfono: ");
        // el id se asigna según la cantidad de clientes registrados
        Cliente c = new Cliente(clientes.size() + 1, nombre, telefono);
        clientes.add(c);
        System.out.println("Cliente registrado: " + c);
    }

    private static void registrarBarbero() {
        String nombre = leerTexto("Nombre: ");
        String especialidad = leerTexto("Especialidad: ");
        Barbero b = new Barbero(barberos.size() + 1, nombre, especialidad);
        barberos.add(b);
        System.out.println("Barbero registrado: " + b);
    }

    private static void reservarCita() {
        Cliente cliente = buscarCliente(leerEntero("ID del cliente: "));
        mostrarBarberos();
        Barbero barbero = buscarBarbero(leerEntero("ID del barbero: "));
        int anio = leerEntero("Año: ");
        int mes = leerEntero("Mes: ");
        int dia = leerEntero("Día: ");
        int hora = leerEntero("Hora (0-23): ");
        int minuto = leerEntero("Minuto: ");
        LocalDateTime fechaHora = LocalDateTime.of(anio, mes, dia, hora, minuto);
        Cita cita = cliente.reservarCita(barbero, fechaHora);
        System.out.println("Reserva exitosa: " + cita);
    }

    private static void consultarCitas() {
        Cliente cliente = buscarCliente(leerEntero("ID del cliente: "));
        ArrayList<Cita> citas = cliente.consultarCitas();
        if (citas.isEmpty()) {
            System.out.println("El cliente no tiene citas.");
        }
        for (Cita c : citas) {
            System.out.println(c);
        }
        System.out.println("Total de citas: " + citas.size());
    }

    private static void cancelarCita() {
        Cliente cliente = buscarCliente(leerEntero("ID del cliente: "));
        cliente.cancelarCita(leerEntero("ID de la cita: "));
        System.out.println("Cita cancelada correctamente.");
    }

    private static void cambiarEstadoBarbero() {
        mostrarBarberos();
        Barbero barbero = buscarBarbero(leerEntero("ID del barbero: "));
        barbero.actualizarDisponibilidad(!barbero.isActivo());
        System.out.println("Barbero actualizado: " + barbero);
    }

    private static void mostrarBarberos() {
        System.out.println("-- Barberos --");
        for (Barbero b : barberos) {
            System.out.println(b);
        }
    }

    private static Cliente buscarCliente(int id) {
        for (Cliente c : clientes) {
            if (c.getId() == id) {
                return c;
            }
        }
        throw new IllegalArgumentException("No existe un cliente con id " + id);
    }

    private static Barbero buscarBarbero(int id) {
        for (Barbero b : barberos) {
            if (b.getIdBarbero() == id) {
                return b;
            }
        }
        throw new IllegalArgumentException("No existe un barbero con el id: " + id);
    }

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return sc.nextLine();
    }

    private static int leerEntero(String mensaje) {
        System.out.print(mensaje);
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ingrese un número entero.");
        }
    }
}
