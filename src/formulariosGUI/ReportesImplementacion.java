package formulariosGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportesImplementacion {
    private Connection conexion;
    private JTable table1;
    private DefaultTableModel tableModel;

    public ReportesImplementacion(Connection conexion, JTable reportesTable, DefaultTableModel tableModel) {
        this.conexion = conexion;
        this.table1 = reportesTable;
        this.tableModel = tableModel;
    }

    /**
     * este metodo se genera los reportes por perido de tiempo diario,semanal o mensual
     * @param periodo
     */
    // Método para generar reporte de ventas por periodo
    public void generarReporteVentasPorPeriodo(String periodo) {
        tableModel.setRowCount(0);

        // Configurar columnas específicas para este reporte
        configurarColumnas(new String[]{"Fecha", "Total Ventas", "Cantidad de Órdenes"});

        try {
            String sql = "";
            String groupBy = "";
            String dateFormat = "";

            switch (periodo.toLowerCase()) {
                case "diario":
                    sql =  "SELECT DATE(fecha) AS fecha, SUM(cantidad) AS cantidad, COUNT(*) AS num_ordenes " +
                            "FROM ventas " +
                            "WHERE fecha >= CURDATE() - INTERVAL 30 DAY " +
                            "GROUP BY DATE(fecha) " +
                            "ORDER BY fecha DESC";
                    break;
                case "semanal":
                    sql = "SELECT YEARWEEK(fecha, 1) as semana, " +
                            "CONCAT('Semana ', WEEK(fecha, 1), ' - ', YEAR(fecha)) as periodo, " +
                            "SUM(cantidad) as cantidad, COUNT(*) as num_ordenes " +
                            "FROM ventas " +
                            "WHERE fecha >= DATE_SUB(CURRENT_DATE(), INTERVAL 12 WEEK) " +
                            "GROUP BY YEARWEEK(fecha, 1) " +
                            "ORDER BY semana DESC";
                    break;
                case "mensual":
                    sql = "SELECT CONCAT(YEAR(fecha), '-', MONTH(fecha)) as mes, " +
                            "CONCAT(MONTHNAME(fecha), ' ', YEAR(fecha)) as periodo, " +
                            "SUM(cantidad) as cantidad, COUNT(*) as num_ordenes " +
                            "FROM ventas " +
                            "WHERE fecha >= DATE_SUB(CURRENT_DATE(), INTERVAL 12 MONTH) " +
                            "GROUP BY YEAR(fecha), MONTH(fecha) " +
                            "ORDER BY YEAR(fecha) DESC, MONTH(fecha) DESC";
                    break;
                default:
                    throw new IllegalArgumentException("Periodo no válido: " + periodo);
            }

            PreparedStatement stmt = conexion.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row;
                if (periodo.equalsIgnoreCase("diario")) {
                    row = new Object[]{
                            rs.getString("fecha"),
                            String.format("$%.2f", rs.getDouble("cantidad")),
                            rs.getInt("num_ordenes")
                    };
                } else {
                    row = new Object[]{
                            rs.getString("periodo"),
                            String.format("$%.2f", rs.getDouble("cantidad")),
                            rs.getInt("num_ordenes")
                    };
                }
                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar reporte de ventas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * este metodo es el reporte de productos mas vendidos
     * @param limite
     */

    // Método para generar reporte de productos más vendidos
    public void generarReporteProductosMasVendidos(int limite) {
        tableModel.setRowCount(0);

        // Configurar columnas específicas para este reporte
        configurarColumnas(new String[]{"ID Producto", "Nombre Producto", "Categoría", "Cantidad Vendida", "Total Generado"});

        try {
            String sql = "SELECT p.id_inventario, p.nombres, p.categoria, " +
                    "COUNT(o.precio) as num_ventas, " +
                    "SUM(o.precio) as total_generado " +
                    "FROM inventario p " +
                    "JOIN inventario o ON p.id_inventario = o.id_inventario " +
                    "GROUP BY p.id_inventario, p.nombres, p.categoria " +
                    "ORDER BY num_ventas DESC, total_generado DESC " +
                    "LIMIT ?";

            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, limite);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[]{
                        rs.getInt("id_inventario"),
                        rs.getString("nombres"),
                        rs.getString("categoria"),
                        rs.getInt("num_ventas"),
                        String.format("$%.2f", rs.getDouble("total_generado"))
                };
                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar reporte de productos más vendidos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * se genera el reporte del cliente con mas compraas
     * @param limite
     */
    // Método para generar reporte de clientes con más compras
    public void generarReporteClientesConMasCompras(int limite) {
        tableModel.setRowCount(0);

        // Configurar columnas específicas para este reporte
        configurarColumnas(new String[]{"ID Cliente", "Nombre Cliente", "Compras Realizadas", "Total Gastado", "Última Compra"});

        try {

            //  La tabla clientes con id_cliente y nombre
            String sql = "SELECT c.id_cliente, c.nombre as nombre_cliente, " +
                    "COUNT(o.id_venta) as num_compras, " +
                    "SUM(o.cantidad) as total_gastado, " +
                    "MAX(o.fecha) as ultima_compra " +
                    "FROM cliente c " +
                    "JOIN ventas o ON c.id_cliente = o.id_cliente " +
                    "GROUP BY c.id_cliente, c.nombre " +
                    "ORDER BY num_compras DESC, total_gastado DESC " +
                    "LIMIT ?";

            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, limite);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[]{
                        rs.getInt("id_cliente"),
                        rs.getString("nombre_cliente"),
                        rs.getInt("num_compras"),
                        String.format("$%.2f", rs.getDouble("total_gastado")),
                        rs.getTimestamp("ultima_compra")
                };
                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar reporte de clientes con más compras: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * se genera el reporte de stock bajo q es cuando un producto esta debajo de la cantidad de 5
     * @param umbralStock
     */
    // Método para generar reporte de productos con stock bajo
    public void generarReporteStockBajo(int umbralStock) {
        tableModel.setRowCount(0);

        // Configurar columnas específicas para este reporte
        configurarColumnas(new String[]{"ID Producto", "Nombre Producto", "Categoría", "Stock Actual", "Precio", "Proveedor ID"});

        try {
            String sql = "SELECT p.id_inventario, p.nombres, p.categoria, " +
                    "p.cant_disponible, p.precio, p.proveedor_asoc " +
                    "FROM inventario p " +
                    "WHERE p.cant_disponible <= ? " +
                    "ORDER BY p.cant_disponible ASC, p.nombres ASC";

            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setInt(1, umbralStock);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Object[] row = new Object[]{
                        rs.getInt("id_inventario"),
                        rs.getString("nombres"),
                        rs.getString("categoria"),
                        rs.getInt("cant_disponible"),
                        String.format("$%.2f", rs.getDouble("precio")),
                        rs.getString("proveedor_asoc")
                };
                tableModel.addRow(row);
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al generar reporte de stock bajo: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * se gera acomodar el nombre de lo q trae la bd segun el reporte selecionado el titulo de las columna
     * @param columnas
     */
    // Método auxiliar para configurar las columnas de la tabla según el tipo de reporte
    private void configurarColumnas(String[] columnas) {
        tableModel.setColumnCount(0);
        for (String columna : columnas) {
            tableModel.addColumn(columna);
        }
    }

    // Método para exportar reporte actual a PDF
    public void exportarReporteActualAPDF(String tipoReporte) {
        // Implementación de la exportación a PDF
        // Este es un método que se completaría con una biblioteca como iText o JasperReports
        // Esta por implementar
        JOptionPane.showMessageDialog(null,
                "La exportación a PDF para el reporte " + tipoReporte + " será implementada con una biblioteca como iText.",
                "Exportación a PDF", JOptionPane.INFORMATION_MESSAGE);
    }
}
