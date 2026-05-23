package main.main.java.util;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Random;

public class Barcode {
    // 1. Generar número aleatorio
    public static int ramdomNum(int digitos) {
        Random random = new Random();
        int minimo = (int) Math.pow(10, digitos - 1);
        int maximo = (int) Math.pow(10, digitos) - 1;
        return random.nextInt(maximo - minimo + 1) + minimo;

    }
    // 2 y 4. Convertir a código de barras y guardar en disco
    public static void createBarcode(String numero, int ancho, int alto, String rutaArchivo) throws Exception {
        Code128Writer escritorBarcode = new Code128Writer();

        // Codifica el string en una matriz de bits
        BitMatrix matrizBits = escritorBarcode.encode(numero, BarcodeFormat.CODE_128, ancho, alto);

        // Convierte la matriz de bits y la guarda directamente como un archivo PNG
        Path path = FileSystems.getDefault().getPath(rutaArchivo);
        MatrixToImageWriter.writeToPath(matrizBits, "PNG", path);
    }

    // 5. Mostrar la imagen generada en una ventana emergente (JFrame)
    public static void ShowWindow(String rutaImagen, String titulo) {
        // Ejecutar en el hilo de interfaz gráfica de Java
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Código de Barras: " + titulo);
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setSize(400, 200);
            ventana.setLocationRelativeTo(null); // Centrar la ventana en la pantalla

            // Cargar la imagen generada y meterla en un contenedor de texto (JLabel)
            ImageIcon icono = new ImageIcon(rutaImagen);
            JLabel etiquetaImagen = new JLabel(icono);
            etiquetaImagen.setHorizontalAlignment(SwingConstants.CENTER);

            // Agregar un texto abajo para que se vea el número real
            JLabel etiquetaTexto = new JLabel("Valor: " + titulo, SwingConstants.CENTER);
            etiquetaTexto.setFont(new Font("Arial", Font.BOLD, 14));

            // Organizar componentes en la ventana
            ventana.setLayout(new BorderLayout());
            ventana.add(etiquetaImagen, BorderLayout.CENTER);
            ventana.add(etiquetaTexto, BorderLayout.SOUTH);

            // Hacer visible la pestaña
            ventana.setVisible(true);
        });
    }
}
