
package gui;

import datos.BaseDatos;
import datos.Trabajador;
import java.util.List;
import java.util.Scanner;

public class InterfazCLI {
    private final Scanner sc = new Scanner(System.in);
    private final BaseDatos baseDatos;

    public InterfazCLI() {
        this.baseDatos = new BaseDatos();
        menu();
    }

    private void buscarTrabajador() {
        System.out.println("Ingrese el RUT del trabajador:");
        String rut = sc.nextLine();
        Trabajador trabajador = baseDatos.buscarPorRut(rut);
        if (trabajador == null) {
            System.out.println("Trabajador no encontrado.");
        } else {
            mostrarTrabajador(trabajador);
        }
    }

    private Trabajador buscarYValidarTrabajador() {
        System.out.println("Ingrese el RUT del trabajador a editar:");
        String rut = sc.nextLine();
        Trabajador trabajador = baseDatos.buscarPorRut(rut);
        if (trabajador == null) {
            System.out.println("No se encontró un trabajador con ese RUT.");
            return null;
        }
        return trabajador;
    }

    private void editarNombre() {
        Trabajador trabajador = buscarYValidarTrabajador();
        if (trabajador != null) {
            trabajador.setNombre(nuevoNombre());
            baseDatos.actualizarTrabajador(trabajador);
            System.out.println("Nombre actualizado exitosamente.");
        }
    }

    private void editarApellido() {
        Trabajador trabajador = buscarYValidarTrabajador();
        if (trabajador != null) {
            trabajador.setApellido(nuevaApellido());
            baseDatos.actualizarTrabajador(trabajador);
            System.out.println("Apellido actualizado exitosamente.");
        }
    }

    private void editarIsapre() {
        Trabajador trabajador = buscarYValidarTrabajador();
        if (trabajador != null) {
            trabajador.setIsapre(nuevaIsapre());
            baseDatos.actualizarTrabajador(trabajador);
            System.out.println("Isapre actualizada exitosamente.");
        }
    }

    private void editarAfp() {
        Trabajador trabajador = buscarYValidarTrabajador();
        if (trabajador != null) {
            trabajador.setAfp(nuevaAfp());
            baseDatos.actualizarTrabajador(trabajador);
            System.out.println("AFP actualizada exitosamente.");
        }
    }

    private void listarTrabajadores() {
        List<Trabajador> trabajadores = baseDatos.obtenerTrabajadores();
        if (trabajadores.isEmpty()) {
            System.out.println("No hay trabajadores registrados.");
        } else {
            System.out.println("Lista de trabajadores:");
            for (Trabajador t : trabajadores) {
                System.out.println("--------------------------------");
                System.out.println(t);
            }
        }
    }

    private void agregarTrabajador() {
        Trabajador nuevoTrabajador = leerTrabajador();
        if (baseDatos.buscarPorRut(nuevoTrabajador.getRut()) != null) {
            System.out.println("Ya existe un trabajador con ese RUT.");
            return;
        }
        baseDatos.agregarTrabajador(nuevoTrabajador);
        System.out.println("Trabajador agregado exitosamente.");
    }

    private void eliminarTrabajador() {
        System.out.println("Ingrese el RUT del trabajador a eliminar:");
        String rut = sc.nextLine();
        Trabajador trabajadorAEliminar = baseDatos.buscarPorRut(rut);
        if (trabajadorAEliminar == null) {
            System.out.println("No se encontró un trabajador con ese RUT.");
            return;
        }
        baseDatos.eliminarTrabajador(rut);
        System.out.println("Trabajador eliminado exitosamente.");
    }

    public Trabajador leerTrabajador() {
        System.out.println("Ingrese nombre:");
        String nombre = sc.nextLine();
        System.out.println("Ingrese apellido:");
        String apellido = sc.nextLine();
        System.out.println("Ingrese rut:");
        String rut = sc.nextLine();
        System.out.println("Ingrese isapre:");
        String isapre = sc.nextLine();
        System.out.println("Ingrese afp:");
        String afp = sc.nextLine();

        return new Trabajador(nombre, apellido, rut, isapre, afp);
    }

    public void mostrarTrabajador(Trabajador trabajador) {
        System.out.println("Datos del trabajador: ");
        System.out.println(trabajador);
    }

    public void menu() {
        String opcion;
        do {
            mostrarOpciones();
            opcion = leerOpcion();
            ejecutarOpcion(opcion);
        } while (!opcion.equals("8"));
    }

    public void mostrarOpciones() {
        System.out.println("\n=============================");
        System.out.println("   Sistema de Trabajadores        ");
        System.out.println("      Opciones      ");
        System.out.println("============================= ");
        System.out.println("    [0] Listar trabajadores");
        System.out.println("    [1] Buscar trabajador");
        System.out.println("    [2] Agregar trabajador");
        System.out.println("    [3] Eliminar trabajador");
        System.out.println("    [4] Editar nombre   ");
        System.out.println("    [5] Editar apellido   ");
        System.out.println("    [6] Editar isapre      ");
        System.out.println("    [7] Editar afp  ");
        System.out.println("    [8] Salir         ");
        System.out.println("============================= ");
        System.out.print("      Opcion: ");
    }

    public String leerOpcion() {
        return sc.nextLine();
    }

    public void ejecutarOpcion(String opcion) {
        switch (opcion) {
            case "0" -> listarTrabajadores();
            case "1" -> buscarTrabajador();
            case "2" -> agregarTrabajador();
            case "3" -> eliminarTrabajador();
            case "4" -> editarNombre();
            case "5" -> editarApellido();
            case "6" -> editarIsapre();
            case "7" -> editarAfp();
            case "8" -> {
                System.out.println("Guardando cambios...");
                baseDatos.guardarDatos();
                System.out.println("Saliendo...");
            }
            default -> System.out.println("Opción inválida...");
        }
    }

    private String nuevoNombre(){
        System.out.println("Ingrese nuevo nombre: ");
        return sc.nextLine();
    }

    private String nuevaApellido(){
        System.out.println("Ingrese nuevo apellido");
        return sc.nextLine();
    }

    private String nuevaIsapre(){
        System.out.println("Ingrese nuevo isapre");
        return sc.nextLine();
    }

    private String nuevaAfp(){
        System.out.println("Ingrese nuevo afp");
        return sc.nextLine();
    }
}