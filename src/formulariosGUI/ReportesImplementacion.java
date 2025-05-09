package formulariosGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import conexionBD.ConexionBD;

import java.io.FileOutputStream;

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
        configurarColumnas(new String[]{"Fecha", "Total Ventas"});

        try {
            String sql = "";
            String groupBy = "";
            String dateFormat = "";

            switch (periodo.toLowerCase()) {
                case "diario":
                    sql =  "SELECT DATE(fecha) AS fecha, COUNT(*) AS num_ordenes " +
                            "FROM ventas " +
                            "WHERE DATE(fecha) = CURDATE()"+
                            "GROUP BY DATE(fecha) " +
                            "ORDER BY fecha DESC";
                    break;
                case "semanal":
                    sql = "SELECT " +
                        "YEAR(fecha) AS anio, " +
                        "WEEK(fecha, 1) AS semana, " +
                        "CONCAT('Semana ', WEEK(fecha, 1), ' - ', YEAR(fecha)) AS periodo, " +
                        "COUNT(*) AS num_ordenes " +
                        "FROM ventas " +
                        "WHERE DATE(fecha) >= CURDATE() - INTERVAL 1 WEEK " +
                        "GROUP BY YEAR(fecha), WEEK(fecha, 1) " +
                        "ORDER BY semana DESC";


                    break;
                case "mensual":
                    sql = "SELECT " +
                        "YEAR(fecha) AS anio, " +
                        "MONTH(fecha) AS mes, " +
                        "CONCAT('Mes ', MONTH(fecha), ' - ', YEAR(fecha)) AS periodo, " +
                        "COUNT(*) AS num_ordenes " +
                        "FROM ventas " +
                        "WHERE DATE(fecha) >= CURDATE() - INTERVAL 1 MONTH " +
                        "GROUP BY YEAR(fecha), MONTH(fecha) " +
                        "ORDER BY mes DESC";

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
                            rs.getInt("num_ordenes")
                    };
                } else {
                    row = new Object[]{
                            rs.getString("periodo"),
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
                    "COUNT(v.id_venta) AS num_ventas, " +
                    "SUM(v.total) AS total_generado " +
                    "FROM inventario p " +
                    "JOIN ventas v ON p.id_inventario = v.id_inventario " +
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
            String sql = "SELECT c.id_cliente, c.nombre AS nombre_cliente, " +
                    "COUNT(o.id_venta) AS num_compras, " +
                    "SUM(o.total) AS total_gastado, " +  // Asegúrate de que "total" es el campo que almacena el monto total de la venta.
                    "MAX(o.fecha) AS ultima_compra " +
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
        Document document = new Document();


        try {

            // Ruta y nombre del archivo
            String ruta = System.getProperty("user.home") + "/Desktop/"+tipoReporte+".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(ruta));
            document.open();

            // Fuente
            Font tituloFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font contenidoFont = new Font(Font.FontFamily.HELVETICA, 12);

            // Título
            Paragraph titulo = new Paragraph("Reporte - " + tipoReporte.toUpperCase(), tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);
            document.add(new Paragraph(" ")); // Espacio
            PdfWriter.getInstance(document, new FileOutputStream(ruta));
            document.open();

            // Cargar una imagen desde el archivo
            String rutaImagen = "pdf/logo.jpg"; // Ruta de la imagen
            Image imagen = Image.getInstance(rutaImagen);

            // Ajustar el tamaño de la imagen (opcional)
            imagen.scaleToFit(200, 200); // Ajustar la imagen a un tamaño de 200x200 píxeles

            // Centrar la imagen en la página
            imagen.setAlignment(Image.ALIGN_CENTER);

            // Agregar la imagen al documento
            document.add(imagen);

            // Agregar un título
            Font fontTitulo = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Font fontinit = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            Paragraph ln = new Paragraph("--------------------------------------------------------------------------", fontinit);
            //Paragraph titulo = new Paragraph("Ferreteria Style True", fontTitulo);
            Paragraph init = new Paragraph("Reporte creado por la empresa ferreria style true", fontinit);

            Paragraph la= new Paragraph("--------------------------------------------------------------------------", fontinit);

            //CENTRAR
            ln.setAlignment(Element.ALIGN_CENTER);
            titulo.setAlignment(Element.ALIGN_CENTER);
            init.setAlignment(Element.ALIGN_CENTER);
            la.setAlignment(Element.ALIGN_CENTER);

            //MOSTAR
            document.add(ln);
            document.add(titulo);
            document.add(init);
            document.add(la);

            // Crear la fuente para el "ENCARGADO:" en negrita
            Font fontNegrita = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            // Crear la fuente para el nombre del empleado en normal
            Font fontNormal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

            // Crear el párrafo del encargado con dos partes
            Paragraph encargado = new Paragraph();
            encargado.add(new Chunk("Reporte Generado por: "+ "alonso", fontNegrita));
            //encargado.add(new Chunk(nombreEmpleado, fontNormal));

            encargado.setAlignment(Element.ALIGN_CENTER);
            document.add(encargado);



            // Agregar un espacio después del título
            document.add(Chunk.NEWLINE);
            Paragraph titulo1 = new Paragraph("Reporte", fontTitulo);
            titulo1.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo1);

            // Agregar un espacio después del título
            document.add(Chunk.NEWLINE);


            PdfPTable tabla = new PdfPTable(5);
            float[] anchos = {4f, 4f, 4f, 4f, 4f};  // Anchos relativos de las columnas
            tabla.setWidths(anchos);

            // Definir las fuentes
            Font fontHeader = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
            Font fontCell = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL);

            // Agregar la fila de encabezado con un color de fondo
            PdfPCell cell = new PdfPCell(new Phrase("Id Detalle Orden", fontHeader));
            cell.setBorderColor(BaseColor.BLACK);  // Color de borde
            cell.setBorderWidth(1f);
            cell.setBackgroundColor(BaseColor.DARK_GRAY); // Color de fondo
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setFixedHeight(30f);// Alineación centrada

            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Nombre Producto", fontHeader));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setBorderColor(BaseColor.BLACK);  // Color de borde
            cell.setBorderWidth(1f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Cantidad", fontHeader));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setBorderColor(BaseColor.BLACK);  // Color de borde
            cell.setBorderWidth(1f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Subtotal", fontHeader));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setBorderColor(BaseColor.BLACK);  // Color de borde
            cell.setBorderWidth(1f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(cell);

            cell = new PdfPCell(new Phrase("Estado", fontHeader));
            cell.setBackgroundColor(BaseColor.DARK_GRAY);
            cell.setBorderColor(BaseColor.BLACK);  // Color de borde
            cell.setBorderWidth(1f);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            tabla.addCell(cell);
            document.add(tabla);
            document.close();

            JOptionPane.showMessageDialog(null, "PDF generado correctamente:\n" + ruta,
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al generar el PDF:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
