package gui;

import datos.BaseDatos;
import datos.Trabajador;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainView extends JFrame {
    private final BaseDatos baseDatos;
    private JTable tablaTrabajadores;
    private DefaultTableModel modeloTabla;
    private JTextField txtNombre, txtApellido, txtRut, txtIsapre, txtAfp;
    private JButton btnAgregar, btnBuscar, btnEliminar, btnLimpiar;
    private JButton btnEditarNombre, btnEditarApellido, btnEditarIsapre, btnEditarAfp;

    public MainView() {
        this.baseDatos = new BaseDatos();
        configurarVentana();
        inicializarComponentes();
        actualizarTabla();
    }

    private void configurarVentana() {
        setTitle("💼 Sistema de Gestión de Trabajadores");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 650);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 245, 245));
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
    }

    private void inicializarComponentes() {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(250, 250, 250));

        // Panel de formulario
        JPanel formulario = new JPanel(new GridLayout(5, 2, 10, 10));
        formulario.setBorder(BorderFactory.createTitledBorder("✏️ Datos del Trabajador"));
        formulario.setBackground(new Color(250, 250, 250));

        txtNombre = crearCampoTexto();
        txtApellido = crearCampoTexto();
        txtRut = crearCampoTexto();
        txtIsapre = crearCampoTexto();
        txtAfp = crearCampoTexto();

        formulario.add(crearLabel("Nombre:"));
        formulario.add(txtNombre);
        formulario.add(crearLabel("Apellido:"));
        formulario.add(txtApellido);
        formulario.add(crearLabel("RUT:"));
        formulario.add(txtRut);
        formulario.add(crearLabel("Isapre:"));
        formulario.add(txtIsapre);
        formulario.add(crearLabel("AFP:"));
        formulario.add(txtAfp);

        // Botones principales
        JPanel botonesPrincipales = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        botonesPrincipales.setBackground(new Color(250, 250, 250));

        btnAgregar = crearBoton("Agregar");
        btnBuscar = crearBoton("Buscar");
        btnEliminar = crearBoton("Eliminar");
        btnLimpiar = crearBoton("Limpiar");

        botonesPrincipales.add(btnAgregar);
        botonesPrincipales.add(btnBuscar);
        botonesPrincipales.add(btnEliminar);
        botonesPrincipales.add(btnLimpiar);

        // Botones de edición
        JPanel botonesEdicion = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        botonesEdicion.setBackground(new Color(250, 250, 250));
        botonesEdicion.setBorder(BorderFactory.createTitledBorder("🛠️ Opciones de Edición"));

        btnEditarNombre = crearBoton("Editar Nombre");
        btnEditarApellido = crearBoton("Editar Apellido");
        btnEditarIsapre = crearBoton("Editar Isapre");
        btnEditarAfp = crearBoton("Editar AFP");

        botonesEdicion.add(btnEditarNombre);
        botonesEdicion.add(btnEditarApellido);
        botonesEdicion.add(btnEditarIsapre);
        botonesEdicion.add(btnEditarAfp);

        // Tabla
        String[] columnas = {"RUT", "Nombre", "Apellido", "Isapre", "AFP"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaTrabajadores = new JTable(modeloTabla);
        tablaTrabajadores.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tablaTrabajadores.setRowHeight(24);
        tablaTrabajadores.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        JScrollPane scrollPane = new JScrollPane(tablaTrabajadores);
        scrollPane.setPreferredSize(new Dimension(750, 250));
        scrollPane.setBorder(BorderFactory.createTitledBorder("📋 Lista de Trabajadores"));

        // Estructura general
        panelPrincipal.add(formulario);
        panelPrincipal.add(Box.createVerticalStrut(15));
        panelPrincipal.add(botonesPrincipales);
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(botonesEdicion);
        panelPrincipal.add(Box.createVerticalStrut(10));
        panelPrincipal.add(scrollPane);

        configurarEventos();

        add(panelPrincipal);
    }

    private JLabel crearLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return label;
    }

    private JTextField crearCampoTexto() {
        JTextField campo = new JTextField();
        campo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        campo.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        return campo;
    }

    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.setBackground(new Color(59, 89, 152));
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        boton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        return boton;
    }

    private void configurarEventos() {
        btnAgregar.addActionListener(e -> agregarTrabajador());
        btnBuscar.addActionListener(e -> buscarTrabajador());
        btnEliminar.addActionListener(e -> eliminarTrabajador());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        btnEditarNombre.addActionListener(e -> editarCampo("nombre"));
        btnEditarApellido.addActionListener(e -> editarCampo("apellido"));
        btnEditarIsapre.addActionListener(e -> editarCampo("isapre"));
        btnEditarAfp.addActionListener(e -> editarCampo("afp"));

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                baseDatos.guardarDatos();
            }
        });
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        List<Trabajador> trabajadores = baseDatos.obtenerTrabajadores();
        for (Trabajador t : trabajadores) {
            modeloTabla.addRow(new Object[]{
                    t.getRut(), t.getNombre(), t.getApellido(), t.getIsapre(), t.getAfp()
            });
        }
    }

    private void agregarTrabajador() {
        try {
            String rut = txtRut.getText();
            if (baseDatos.buscarPorRut(rut) != null) {
                JOptionPane.showMessageDialog(this, "Ya existe un trabajador con ese RUT.");
                return;
            }

            Trabajador t = new Trabajador(
                    txtNombre.getText(),
                    txtApellido.getText(),
                    rut,
                    txtIsapre.getText(),
                    txtAfp.getText()
            );

            baseDatos.agregarTrabajador(t);
            actualizarTabla();
            limpiarCampos();
            JOptionPane.showMessageDialog(this, "Trabajador agregado exitosamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar trabajador.");
        }
    }

    private void buscarTrabajador() {
        String rut = JOptionPane.showInputDialog(this, "Ingrese el RUT del trabajador:");
        if (rut != null && !rut.isEmpty()) {
            Trabajador t = baseDatos.buscarPorRut(rut);
            if (t != null) {
                txtNombre.setText(t.getNombre());
                txtApellido.setText(t.getApellido());
                txtRut.setText(t.getRut());
                txtIsapre.setText(t.getIsapre());
                txtAfp.setText(t.getAfp());
            } else {
                JOptionPane.showMessageDialog(this, "Trabajador no encontrado.");
            }
        }
    }

    private void eliminarTrabajador() {
        String rut = JOptionPane.showInputDialog(this, "Ingrese el RUT a eliminar:");
        if (rut != null && !rut.isEmpty()) {
            Trabajador t = baseDatos.buscarPorRut(rut);
            if (t != null) {
                baseDatos.eliminarTrabajador(rut);
                actualizarTabla();
                limpiarCampos();
                JOptionPane.showMessageDialog(this, "Trabajador eliminado.");
            } else {
                JOptionPane.showMessageDialog(this, "Trabajador no encontrado.");
            }
        }
    }

    private void editarCampo(String campo) {
        String rut = JOptionPane.showInputDialog(this, "Ingrese el RUT del trabajador:");
        if (rut != null && !rut.isEmpty()) {
            Trabajador t = baseDatos.buscarPorRut(rut);
            if (t != null) {
                String nuevoValor = JOptionPane.showInputDialog(this, "Nuevo valor para " + campo + ":");
                if (nuevoValor != null && !nuevoValor.isEmpty()) {
                    switch (campo) {
                        case "nombre" -> t.setNombre(nuevoValor);
                        case "apellido" -> t.setApellido(nuevoValor);
                        case "isapre" -> t.setIsapre(nuevoValor);
                        case "afp" -> t.setAfp(nuevoValor);
                    }
                    baseDatos.actualizarTrabajador(t);
                    actualizarTabla();
                    JOptionPane.showMessageDialog(this, "Campo actualizado correctamente.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Trabajador no encontrado.");
            }
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellido.setText("");
        txtRut.setText("");
        txtIsapre.setText("");
        txtAfp.setText("");
    }
}
