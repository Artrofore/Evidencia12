package datos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BaseDatos {
    private static final String ARCHIVO_JSON = "trabajadores.json";
    private final Gson gson;
    private List<Trabajador> trabajadores;

    public BaseDatos() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.trabajadores = new ArrayList<>();
        cargarDatos();
    }

    public void agregarTrabajador(Trabajador trabajador) {
        trabajadores.add(trabajador);
        guardarDatos();
    }

    public void eliminarTrabajador(String rut) {
        trabajadores.removeIf(t -> t.getRut().equals(rut));
        guardarDatos();
    }

    public List<Trabajador> obtenerTrabajadores() {
        return trabajadores;
    }

    public Trabajador buscarPorRut(String rut) {
        return trabajadores.stream()
                .filter(t -> t.getRut().equals(rut))
                .findFirst()
                .orElse(null);
    }

    private void cargarDatos() {
        try (Reader reader = new FileReader(ARCHIVO_JSON)) {
            Type listType = new TypeToken<ArrayList<Trabajador>>(){}.getType();
            trabajadores = gson.fromJson(reader, listType);
            if (trabajadores == null) {
                trabajadores = new ArrayList<>();
            }
        } catch (IOException e) {
            trabajadores = new ArrayList<>();
        }
    }

    public void guardarDatos() {
        try (Writer writer = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(trabajadores, writer);
        } catch (IOException e) {
            System.err.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    public void actualizarTrabajador(Trabajador trabajador) {
        for (int i = 0; i < trabajadores.size(); i++) {
            if (trabajadores.get(i).getRut().equals(trabajador.getRut())) {
                trabajadores.set(i, trabajador);
                guardarDatos();
                break;
            }
        }
    }
}