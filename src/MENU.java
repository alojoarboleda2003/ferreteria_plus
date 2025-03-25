import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MENU extends JFrame {

    public MENU() {
        // Configuración de la ventana
        setTitle("Menú Desplegable");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centra la ventana

        // Crear un menú y un menú desplegable
        JMenuBar barraDeMenu = new JMenuBar();
        JMenu menu = new JMenu("Archivo");

        // Crear opciones para el menú
        JMenuItem itemAbrir = new JMenuItem("Abrir");
        JMenuItem itemGuardar = new JMenuItem("Guardar");
        JMenuItem itemSalir = new JMenuItem("Salir");

        // Agregar acciones a los ítems del menú
        itemAbrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Opción Abrir seleccionada.");
            }
        });

        itemGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Opción Guardar seleccionada.");
            }
        });

        itemSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);  // Cierra la aplicación
            }
        });

        // Agregar los ítems al menú
        menu.add(itemAbrir);
        menu.add(itemGuardar);
        menu.addSeparator();  // Añadir un separador
        menu.add(itemSalir);

        // Agregar el menú a la barra de menús
        barraDeMenu.add(menu);

        // Establecer la barra de menús en el JFrame
        setJMenuBar(barraDeMenu);
    }

    public static void main(String[] args) {
        // Crear la interfaz gráfica en el hilo de eventos
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MENU().setVisible(true);
            }
        });
    }
}
