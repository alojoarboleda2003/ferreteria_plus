package formulariosGUI;

import conexionBD.ConexionBD;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;




public class Reportes extends JFrame {
    private JPanel main;
    private JComboBox comboBox1;
    private JTextField fecha;
    private JButton exportarAPdfButton;
    private JButton limpiarButton;
    private JButton generarReporteButton;
    private JTable table1;
    private JButton regresarButton;
    private JSpinner spinner1;
    private JLabel limiteR;
    private JComboBox empleadoC;

    private DefaultTableModel tableModel;
    private Connection conexion;
    private ReportesImplementacion reportesImpl;
    public JPanel getMainPanel() {
        return main;
    }





    public Reportes() {
        this(ConexionBD.getConnection());

        if (this.conexion == null) {
            JOptionPane.showMessageDialog(this,
                    "No se pudo establecer conexión con la base de datos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Reportes(Connection conexion) {

        setContentPane(main);
        setTitle("Sistema de Reportes - Ferretería");
        setSize(1006, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // Asignar correctamente el parámetro de conexión al campo de la clase
        this.conexion = conexion;



        // Configurar fecha actual
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fecha.setText(dateFormat.format(new Date()));

        // Inicializar componentes adicionales
        inicializarComponentes();

        // Cargar empleados desde la base de datos
        cargarEmpleados();

        // Configurar tabla de reportes
        configurarTabla();

        // Inicializar implementación de reportes
        reportesImpl = new ReportesImplementacion(conexion, table1, tableModel);

        // Configurar botones
        configurarBotones();

        setVisible(true);
    }

    private void inicializarComponentes() {
        // Configurar tipos de reportes disponibles
        if (comboBox1.getItemCount() == 0) {
            comboBox1.addItem("Seleccione un tipo de reporte");
            comboBox1.addItem("Ventas Diarias");
            comboBox1.addItem("Ventas Semanales");
            comboBox1.addItem("Ventas Mensuales");
            comboBox1.addItem("Productos Más Vendidos");
            comboBox1.addItem("Clientes con Más Compras");
            comboBox1.addItem("Stock Bajo");

        }

        // Configurar el spinner para parámetros
        spinner1 = new JSpinner(new SpinnerNumberModel(5, 1, 100, 1));
        limiteR = new JLabel("Límite de registros:");

        // Añadir estos componentes al panel, aunque no están definidos en el código original
        // Tendrías que añadirlos a tu diseño de GUI

        // Configurar listener para el cambio de tipo de reporte
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoSeleccionado = (String) comboBox1.getSelectedItem();
                actualizarParametrosPorTipoReporte(tipoSeleccionado);
            }
        });
    }

    private void actualizarParametrosPorTipoReporte(String tipoReporte) {
        if (limiteR == null || spinner1 == null) {
            return; // Evitar NullPointerException si no están inicializados
        }

        if (tipoReporte == null || tipoReporte.equals("Seleccione un tipo de reporte")) {
            limiteR.setVisible(false);
            spinner1.setVisible(false);
            return;
        }

        switch (tipoReporte) {
            case "Productos Más Vendidos":
            case "Clientes con Más Compras":
                limiteR.setText("Límite de registros:");
                spinner1.setValue(5);
                limiteR.setVisible(true);
                spinner1.setVisible(true);
                break;
            case "Stock Bajo":
                limiteR.setText("Umbral de stock:");
                spinner1.setValue(10);
                limiteR.setVisible(true);
                spinner1.setVisible(true);
                break;
            default:
                limiteR.setVisible(false);
                spinner1.setVisible(false);
                break;
        }
    }

    private void cargarEmpleados() {
        try {
            // Comprobar si la conexión es nula antes de usarla
            if (conexion == null) {
                System.out.println("La conexión es nula en cargarEmpleados()");
                conexion = ConexionBD.getConnection();
                if (conexion == null) {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo establecer conexión con la base de datos.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            PreparedStatement stmt = conexion.prepareStatement("SELECT id_empleado, nombre FROM empleado ORDER BY nombre");
            ResultSet rs = stmt.executeQuery();

            empleadoC.addItem("Seleccione un empleado");

            while (rs.next()) {
                empleadoC.addItem(rs.getInt("id_empleado") + " - " + rs.getString("nombre"));
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar empleados: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configurarTabla() {
        tableModel = new DefaultTableModel();
        // Columnas iniciales, se actualizarán según el tipo de reporte
        tableModel.addColumn("ID");
        tableModel.addColumn("Tipo");
        tableModel.addColumn("Fecha");
        tableModel.addColumn("Empleado ID");
        tableModel.addColumn("Descripción");

        table1.setModel(tableModel);
    }

    private void cargarReportes() {
        tableModel.setRowCount(0);

        try {
            // Comprobar si la conexión es nula antes de usarla
            if (conexion == null) {
                System.out.println("La conexión es nula en cargarReportes()");
                conexion = ConexionBD.getConnection();
                if (conexion == null) {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo establecer conexión con la base de datos.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            PreparedStatement stmt = conexion.prepareStatement(
                    "SELECT * FROM reportes_generados ORDER BY fecha_compra DESC");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[5];
                row[0] = rs.getInt("id_reporte");
                row[1] = rs.getString("tipo_reporte");
                row[2] = rs.getString("fecha_compra");
                row[3] = rs.getInt("id_empleado");

                String descripcion1 = rs.getString("descripcion");
                if (descripcion1 != null && descripcion1.length() > 30) {
                    descripcion1 = descripcion1.substring(0, 30) + "...";
                }
                row[4] = descripcion1;

                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar reportes: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void configurarBotones() {
        generarReporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoReporte = (String) comboBox1.getSelectedItem();

                if (tipoReporte.equals("Seleccione un tipo de reporte")) {
                    JOptionPane.showMessageDialog(Reportes.this,
                            "Por favor, seleccione un tipo de reporte",
                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                switch (tipoReporte) {
                    case "Ventas Diarias":
                        reportesImpl.generarReporteVentasPorPeriodo("diario");
                        break;
                    case "Ventas Semanales":
                        reportesImpl.generarReporteVentasPorPeriodo("semanal");
                        break;
                    case "Ventas Mensuales":
                        reportesImpl.generarReporteVentasPorPeriodo("mensual");
                        break;
                    case "Productos Más Vendidos":
                        int limite = (Integer) spinner1.getValue();
                        reportesImpl.generarReporteProductosMasVendidos(limite);
                        break;
                    case "Clientes con Más Compras":
                        limite = (Integer) spinner1.getValue();
                        reportesImpl.generarReporteClientesConMasCompras(limite);
                        break;
                    case "Stock Bajo":
                        int umbral = (Integer) spinner1.getValue();
                        reportesImpl.generarReporteStockBajo(umbral);
                        break;
                    case "Reporte Personalizado":
                        generarReporte(); // Usa el método original de generación de reportes
                        break;
                    default:
                        JOptionPane.showMessageDialog(Reportes.this,
                                "Tipo de reporte no implementado",
                                "Error", JOptionPane.ERROR_MESSAGE);
                }

                // Registrar que se generó un reporte
                guardarRegistroReporte(tipoReporte);
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarFormulario();
            }
        });

        exportarAPdfButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipoReporte = (String) comboBox1.getSelectedItem();
                if (tipoReporte.equals("Seleccione un tipo de reporte")) {
                    JOptionPane.showMessageDialog(Reportes.this,
                            "Por favor, primero genere un reporte para exportar",
                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                reportesImpl.exportarReporteActualAPDF(tipoReporte);
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                this.dispose();
                // Aquí puedes abrir la ventana anterior si es necesario
            }

            private void dispose() {
            }
        });

        regresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void guardarRegistroReporte(String tipoReporte) {
        try {
            if (conexion == null) {
                conexion = ConexionBD.getConnection();
                if (conexion == null) {
                    return;
                }
            }

            String empleadoSeleccionado = (String) empleadoC.getSelectedItem();
            if (empleadoSeleccionado == null || empleadoSeleccionado.equals("Seleccione un empleado")) {
                empleadoSeleccionado = "1 - Sistema"; // Valor por defecto
            }

            int idEmpleado = Integer.parseInt(empleadoSeleccionado.split(" - ")[0]);
            String descripcion1 = "Reporte generado: " + tipoReporte;



            PreparedStatement stmt = conexion.prepareStatement(
                    "INSERT INTO reportes_generados (tipo_reporte, id_empleado, descripcion) VALUES (?, ?, ?)");
            stmt.setString(1, tipoReporte);
            stmt.setInt(2, idEmpleado);
            stmt.setString(3, descripcion1);

            stmt.executeUpdate();
            stmt.close();

        } catch (SQLException e) {
            System.out.println("Error al guardar registro de reporte: " + e.getMessage());
        }
    }

    private void generarReporte() {
        String tipoReporte = (String) comboBox1.getSelectedItem();
        String empleadoSeleccionado = (String) empleadoC.getSelectedItem();


        if (empleadoSeleccionado == null || empleadoSeleccionado.equals("Seleccione un empleado")) {
            JOptionPane.showMessageDialog(this, "Por favor seleccione un empleado",
                    "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }



        // Extraer ID de empleado
        int idEmpleado = Integer.parseInt(empleadoSeleccionado.split(" - ")[0]);

        try {
            // Comprobar si la conexión es nula antes de usarla
            if (conexion == null) {
                System.out.println("La conexión es nula en generarReporte()");
                conexion = ConexionBD.getConnection();
                if (conexion == null) {
                    JOptionPane.showMessageDialog(this,
                            "No se pudo establecer conexión con la base de datos.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            PreparedStatement stmt = conexion.prepareStatement(
                    "INSERT INTO reportes_generados (tipo_reporte, id_empleado) VALUES (?, ?)");
            stmt.setString(1, tipoReporte);
            stmt.setInt(2, idEmpleado);


            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                JOptionPane.showMessageDialog(this, "Reporte generado correctamente",
                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarReportes();
                limpiarFormulario();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo generar el reporte",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }

            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al generar reporte: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        comboBox1.setSelectedIndex(0);
        empleadoC.setSelectedIndex(0);
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fecha.setText(dateFormat.format(new Date()));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Connection conexion = ConexionBD.getConnection();
            if (conexion != null) {
                new Reportes(conexion);
            } else {
                JOptionPane.showMessageDialog(null,
                        "No se pudo establecer conexión con la base de datos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}