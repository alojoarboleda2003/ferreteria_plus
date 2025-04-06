package formulariosGUI;

import javax.swing.*;
import java.awt.*;

public class FondoPanel extends JPanel {
    private Image imagen;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Verificamos si la imagen se encuentra
        System.out.println("Ruta imagen: " + getClass().getResource("/img/unnamed.jpg"));

        try {
            imagen = new ImageIcon(getClass().getResource("/img/unnamed.jpg")).getImage();
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        } catch (Exception e) {
            System.out.println("❌ Error al cargar la imagen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
