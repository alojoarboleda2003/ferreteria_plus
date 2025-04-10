package formulariosGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Reportes {
    private JPanel FReportes;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JTextArea textArea1;
    private JButton generarReporteButton;
    private JButton actualizarButton;
    private JTable table1;
    private JComboBox comboBox2;

    public Reportes() {
        generarReporteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para generar el reporte
                String selectedOption = (String) comboBox1.getSelectedItem();
                String inputText = textField1.getText();
                String reportContent = "Reporte generado con opción: " + selectedOption + " y texto: " + inputText;
                textArea1.setText(reportContent);
            }
        });
        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Reportes");
        frame.setContentPane(new Reportes().FReportes);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}