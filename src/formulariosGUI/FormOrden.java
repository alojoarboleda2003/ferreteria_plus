package formulariosGUI;

import DAO.DetalleOrdenDAO;
import com.itextpdf.text.Font;
import conexionBD.ConexionBD;
import modelos.Cliente;
import modelos.DetetalleOrden;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.*;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.*;
import java.util.Date;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.*;
import javax.swing.*;
import java.awt.*;
import java.io.*;

import static java.awt.SystemColor.text;


public class FormOrden extends JFrame {
    private JPanel Forden;
    private JTable table1;
    private JButton verButton;
    private JTextField encargado;
    private JTextField cliente;
    private JTable table2;
    private JComboBox estado2;
    private JTextField total;
    private JScrollPane Tproductos;
    private JButton seleccionarButton;
    private JButton generarFacturaButton;
    int filas = 0;
    int filas1 = 0;
    int idOrden = 0;


    DetalleOrdenDAO detalleOrdenDAO = new DetalleOrdenDAO();

    public FormOrden() {
        setContentPane(Forden);  // Asegúrate de que 'Fclientes' sea el panel que contiene todos los componentes
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cierra solo esta ventana al cerrarla
        setSize(1050, 500);  // Establece el tamaño de la ventana
        setResizable(false);  // Establece que la ventana no sea redimensionable
        setLocationRelativeTo(null);


        obtener_datos();
        agregar_datos_p(idOrden);


        verButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                // Obtenemos la fila seleccionada en table1
                int selectedRow = table1.getSelectedRow();

                if (selectedRow >= 0) {
                    // Obtenemos el ID de la orden de la columna correspondiente
                    // Asegúrate de que la columna 0 tiene el ID de la orden en tu JTable
                    idOrden = Integer.parseInt(table1.getValueAt(selectedRow, 0).toString());

                    // Verifica que el idOrden se está obteniendo correctamente
                    System.out.println("ID Orden seleccionada: " + idOrden);

                    // Llamamos al método para agregar los detalles de la orden en table2
                    agregar_datos_p(idOrden);

                    // Llamamos al método para mostrar los datos del cliente y encargado
                    mostrar_cliente(idOrden);


                }



            }

        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectFila = table1.getSelectedRow();

                if (selectFila >= 0) {
                    table1.getValueAt(selectFila, 0);
                    table1.getValueAt(selectFila, 1);
                    table1.getValueAt(selectFila, 2);


                    filas = selectFila;
                }
            }
        });

        table2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int selectFila = table2.getSelectedRow();

                if (selectFila >= 0) {
                    table2.getValueAt(selectFila, 0);
                    table2.getValueAt(selectFila, 1);
                    table2.getValueAt(selectFila, 2);
                    table2.getValueAt(selectFila, 3);
                    table2.getValueAt(selectFila, 4);


                    filas1 = selectFila;

                }


            }
        });
        encargado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        cliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });

        estado2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // Obtener la fila seleccionada
                int filaSeleccionada = table2.getSelectedRow();

                // Verificar si se seleccionó alguna fila
                if (filaSeleccionada != -1) {
                    // Obtener el valor de la columna "id_detalle_orden" (suponiendo que es la primera columna)
                    String idDetalleOrdenStr = (String) table2.getValueAt(filaSeleccionada, 0); // Obtiene el valor como String
                    int id_detalle_orden = Integer.parseInt(idDetalleOrdenStr);

                    // Mostrar el id_detalle_orden o hacer algo con él
                    System.out.println("ID Detalle Orden seleccionado: " + id_detalle_orden);

                    // Aquí puedes llamar a tu DAO o a cualquier función que necesite este id
                    String estado = (String) estado2.getSelectedItem(); // Estado seleccionado en el JComboBox

                    // Llamar al método de actualización con el id_detalle_orden y estado
                    detalleOrdenDAO.actualizar(id_detalle_orden, estado);
                } else {
                    // Si no hay fila seleccionada, puedes mostrar un mensaje
                    JOptionPane.showMessageDialog(null, "Por favor, selecciona una fila de la tabla.");
                }



            }
        });
        total.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


            }
        });
        seleccionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtenemos la fila seleccionada en table1
                int selectedRow = table2.getSelectedRow();

                if (selectedRow >= 0) {
                    // Obtenemos el ID de la orden de la columna correspondiente
                    // Asegúrate de que la columna 0 tiene el ID de la orden en tu JTable
                    idOrden = Integer.parseInt(table2.getValueAt(selectedRow, 0).toString());

                    mostrar_subtotal(idOrden);


                }


            }
        });
        generarFacturaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent r ) {
                Document document = new Document();
                Date fechaFactura = new Date();
                Connection con = null;
                PreparedStatement pst = null;
                ResultSet rs = null;
                String nombreEmpleado = "";
                String nombreSubtotal = "";


                try {
                    int row2 = table2.getSelectedRow();  // Obtener la fila seleccionada
                    Object selectedValue2 = table2.getValueAt(row2, 0);  // Obtener el valor de la primera columna
                    int table2Value2 = Integer.parseInt(selectedValue2.toString());
                    // Obtener el nombre del empleado desde la base de datos
                    con = conexionBD.getConnection();
                    pst = con.prepareStatement("SELECT e.nombre AS nombre_empleado " +
                            "FROM detalle_orden_compra o " +
                            "JOIN empleado e ON o.id_empleado = e.id_empleado " +
                            "WHERE o.id_detalle_orden = ?");
                    pst.setInt(1, table2Value2);  // Establecer el ID del empleado
                    rs = pst.executeQuery();

                    // Verifica si se obtuvo el nombre del empleado
                    if (rs.next()) {
                        nombreEmpleado = rs.getString("nombre_empleado");
                    } else {
                        System.out.println("No se encontró el nombre del empleado.");
                    }

                    int row3 = table2.getSelectedRow();  // Obtener la fila seleccionada
                    Object selectedValue3 = table2.getValueAt(row3, 0);  // Obtener el valor de la primera columna
                    int table2Value3 = Integer.parseInt(selectedValue3.toString());
                    // Obtener el nombre del empleado desde la base de datos
                    con = conexionBD.getConnection();
                    pst = con.prepareStatement("SELECT subtotal FROM detalle_orden_compra WHERE id_detalle_orden = ?");
                    pst.setInt(1, table2Value3);  // Establecer el ID del empleado
                    rs = pst.executeQuery();

                    // Verifica si se obtuvo el nombre del empleado
                    if (rs.next()) {
                        nombreSubtotal = String.valueOf(Double.parseDouble(rs.getString("subtotal")));
                    } else {
                        System.out.println("No se encontró el subtotal de la orden.");
                    }

                    String ruta = System.getProperty("user.home") + "/Desktop/Factura.pdf";
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
                    Paragraph titulo = new Paragraph("Ferreteria Style True", fontTitulo);
                    Paragraph init = new Paragraph("Nit 28475232003-3", fontinit);
                    Paragraph lugar = new Paragraph("CALLE 13 AVENIDA SUR  #45", fontinit);
                    Paragraph tel = new Paragraph("Tel: 3176741761", fontinit);
                    Paragraph la= new Paragraph("--------------------------------------------------------------------------", fontinit);

                    //CENTRAR
                    ln.setAlignment(Element.ALIGN_CENTER);
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    init.setAlignment(Element.ALIGN_CENTER);
                    lugar.setAlignment(Element.ALIGN_CENTER);
                    tel.setAlignment(Element.ALIGN_CENTER);
                    la.setAlignment(Element.ALIGN_CENTER);

                    //MOSTAR
                    document.add(ln);
                    document.add(titulo);
                    document.add(init);
                    document.add(lugar);
                    document.add(tel);
                    document.add(la);

                    // Crear la fuente para el "ENCARGADO:" en negrita
                    Font fontNegrita = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
                    // Crear la fuente para el nombre del empleado en normal
                    Font fontNormal = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

                    // Crear el párrafo del encargado con dos partes
                    Paragraph encargado = new Paragraph();
                    encargado.add(new Chunk("Encargado: ", fontNegrita));
                    encargado.add(new Chunk(nombreEmpleado, fontNormal));

                    encargado.setAlignment(Element.ALIGN_CENTER);
                    document.add(encargado);

                    // Aquí puedes agregar más campos si lo deseas
                    // Ejemplo: Agregar la fecha de la factura
                    String fechaf = String.valueOf(fechaFactura);// Puedes obtener la fecha actual con java.util.Date
                    Paragraph fecha = new Paragraph();
                    fecha.add(new Chunk("Fecha Creacion: ", fontNegrita));
                    fecha.add(new Chunk(fechaf, fontNormal));
                    Paragraph la2= new Paragraph("--------------------------------------------------------------------------", fontinit);
                    fecha.setAlignment(Element.ALIGN_CENTER);
                    la2.setAlignment(Element.ALIGN_CENTER);
                    document.add(fecha);
                    document.add(la2);


                    // Agregar un espacio después del título
                    document.add(Chunk.NEWLINE);
                    Paragraph titulo1 = new Paragraph("Productos Solicitados", fontTitulo);
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



                    try {
                        // Obtener el valor desde la tabla2 (por ejemplo, un JTable)
                        int row = table2.getSelectedRow();  // Obtener la fila seleccionada
                        Object selectedValue = table2.getValueAt(row, 0);  // Obtener el valor de la primera columna
                        int table2Value = Integer.parseInt(selectedValue.toString());  // Convertir a tipo adecuado

                        con = conexionBD.getConnection();
                        pst = con.prepareStatement("SELECT d.id_detalle_orden, " +
                                "r.nombres AS nombre_inventario, " +
                                "d.cantidad, d.subtotal, d.estado " +
                                "FROM detalle_orden_compra d " +
                                "JOIN inventario r ON r.id_inventario = d.id_inventario " +
                                "WHERE d.id_detalle_orden = ?");

                        // Establecer el valor de table2Value como parámetro de la consulta
                        pst.setInt(1, table2Value);

                        rs = pst.executeQuery();
                        boolean datosAgregados = false;
                        if (rs.next()) {
                            do {
                                tabla.addCell(rs.getString(1));
                                tabla.addCell(rs.getString(2));
                                tabla.addCell(rs.getString(3));
                                tabla.addCell(rs.getString(4));
                                tabla.addCell(rs.getString(5));
                                datosAgregados = true;
                            } while (rs.next());
                            document.add(tabla);
                        }

                        if (!datosAgregados) {
                            System.out.println("No se agregaron datos a la tabla.");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (rs != null) rs.close();
                            if (pst != null) pst.close();
                            if (con != null) con.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Factura creada Exitosamente.");


                    // Agregar un espacio después del título
                    document.add(Chunk.NEWLINE);

                    Paragraph la3= new Paragraph("--------------------------------------------------------------------------", fontinit);
                    // Crear el párrafo del encargado con dos partes
                    Paragraph subt = new Paragraph();
                    Paragraph iva = new Paragraph();
                    iva.add(new Chunk("IVA : 19% ", fontNegrita));
                    subt.add(new Chunk("TOTAL: ", fontNegrita));
                    subt.add(new Chunk(String.valueOf(nombreSubtotal), fontNormal));

                    subt.setAlignment(Element.ALIGN_RIGHT);
                    iva.setAlignment(Element.ALIGN_RIGHT);
                    la3.setAlignment(Element.ALIGN_RIGHT);
                    document.add(la3);
                    document.add(iva);
                    document.add(subt);


                    document.close();


                    // Verificar si el archivo fue creado
                    File archivo = new File(ruta);
                    if (archivo.exists()) {
                        System.out.println("El archivo PDF fue creado correctamente en: " + ruta);
                        // Abrir el archivo PDF automáticamente
                        Desktop.getDesktop().open(archivo);
                    } else {
                        System.out.println("El archivo PDF no fue creado.");
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
    }


        ConexionBD conexionBD = new ConexionBD();

    public void obtener_datos() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID Orden");
        model.addColumn("Cliente");
        model.addColumn("Encargado");



        table1.setModel(model);
        String[] dato = new String[3];
        Connection con = conexionBD.getConnection();

        try {
            Statement start = con.createStatement();
            String query = "SELECT o.id_orden, c.id_cliente AS id_cliente, e.id_empleado AS id_empleado " +
                    "FROM orden_compra o " +
                    "JOIN cliente c ON o.id_cliente = c.id_cliente " +
                    "JOIN empleado e ON o.id_empleado = e.id_empleado" ;



            ResultSet rs = start.executeQuery(query);

            while (rs.next()) {
                dato[0] = rs.getString(1);
                dato[1] = rs.getString(2);
                dato[2] = rs.getString(3);

                model.addRow(dato);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void agregar_datos_p(int idOrden) {
        // Creamos el modelo de la tabla para que tenga las columnas correspondientes.
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("ID Detalle O");
        model.addColumn("Nombre Producto");
        model.addColumn("Cantidad");
        model.addColumn("Subtotal");
        model.addColumn("Estado");


        table2.setModel(model);

        String[] dato = new String[5]; // Datos por cada fila (ID, Cliente, Encargado, Cantidad, Subtotal)
        Connection con = conexionBD.getConnection();

        try {
            // Realizamos la consulta para obtener los detalles de la orden
            String query = "SELECT d.id_detalle_orden, " +
                    "r.nombres AS nombre_inventario, " +
                    "d.cantidad, d.subtotal, d.estado " +
                    "FROM detalle_orden_compra d " +
                    "JOIN inventario r ON r.id_inventario = d.id_inventario " +
                    "WHERE d.id_detalle_orden = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, idOrden);  // Usamos el ID de la orden pasada como parámetro
            ResultSet rs = stmt.executeQuery();



            // Iteramos sobre los resultados y los añadimos a la tabla
            while (rs.next()) {
                dato[0] = rs.getString(1); // ID del detalle de la orden
                dato[1] = rs.getString(2); // Nombre del producto (ahora el inventario)
                dato[2] = rs.getString(3); // Cantidad
                dato[3] = rs.getString(4); // Subtotal
                dato[4] = rs.getString(5); // estado



                model.addRow(dato);  // Añadimos la fila a la tabla
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void mostrar_cliente(int idOrden) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = conexionBD.getConnection();

            // Consulta SQL con los espacios correctamente colocados
            String query = "SELECT c.nombre AS nombre_cliente, e.nombre AS nombre_empleado " +
                    "FROM orden_compra o " +
                    "JOIN cliente c ON o.id_cliente = c.id_cliente " +
                    "JOIN empleado e ON o.id_empleado = e.id_empleado " +
                    "WHERE o.id_orden = ?";

            // Preparar la sentencia SQL
            stmt = con.prepareStatement(query);
            stmt.setInt(1, idOrden);  // Usamos idOrden aquí como parámetro numérico
            rs = stmt.executeQuery();

            // Verificar si se encuentra el cliente y empleado
            if (rs.next()) {
                String nombreCliente = rs.getString("nombre_cliente");
                String nombreEmpleado = rs.getString("nombre_empleado");

                // Establecer los valores en los JTextFields
                cliente.setText(nombreCliente);
                encargado.setText(nombreEmpleado);

            } else {
                System.out.println("Cliente o empleado no encontrados.");
                cliente.setText("No encontrado");
                encargado.setText("No encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mostrar_subtotal(int idOrden) {
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            con = conexionBD.getConnection();

            // Consulta SQL con los espacios correctamente colocados
            String query = "SELECT subtotal FROM detalle_orden_compra WHERE id_detalle_orden = ?";

            // Preparar la sentencia SQL
            stmt = con.prepareStatement(query);
            stmt.setInt(1, idOrden);  // Usamos idOrden aquí como parámetro numérico
            rs = stmt.executeQuery();

            // Verificar si se encuentra el cliente y empleado
            if (rs.next()) {
                double subtotales = rs.getDouble("subtotal");


                // Establecer los valores en los JTextFields
                total.setText(String.valueOf(subtotales));


            } else {
                System.out.println("subtotal no encontrados.");
                total.setText("No encontrado");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String obtenerce(Object id_empleado) {
        String nombreEmpleado = null;

        try {
            // Establecer conexión a la base de datos
            Connection con = conexionBD.getConnection();

            // Consulta SQL para obtener el nombre del empleado por su ID
            String sql = "SELECT e.nombre AS nombre_empleado " +
                    "FROM detalle_orden_compra o " +
                    "JOIN empleado e ON o.id_empleado = e.id_empleado " +
                    "WHERE o.id_detalle_orden = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setObject(1, id_empleado);  // Establecer el parámetro de la consulta con el ID del empleado

            // Ejecutar la consulta
            ResultSet rs = pst.executeQuery();

            // Si el resultado tiene un valor, obtener el nombre
            if (rs.next()) {
                nombreEmpleado = rs.getString("nombre");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();  // Manejo de errores en la base de datos
        }

        return nombreEmpleado;
    }




    private String obtenerNombreEmpleado(int idEmpleado) {
        return "";
    }

    private String obtenerNombreCliente(int idCliente) {
        return "";
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("FormOrden");
        frame.setContentPane(new FormOrden().Forden);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(900, 400);
        frame.setResizable(false);
    }
}

