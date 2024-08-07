import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CircularMask {
    public static void main(String[] args) {
        try {
            BufferedImage faceImage = ImageIO.read(new File("C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\Imagem\\Jessica.jpeg"));
            BufferedImage resultCircularMask = aplicarMascaraCircular(faceImage);
            exibirImagem(resultCircularMask, "Resultado Máscara Circular");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static BufferedImage aplicarMascaraCircular(BufferedImage img) {
        int centroX = img.getWidth() / 2;
        int centroY = img.getHeight() / 2;
        int raio = Math.min(centroX, centroY) / 2;
        BufferedImage resultado = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < img.getWidth(); x++) {
                int dx = x - centroX;
                int dy = y - centroY;
                if (dx * dx + dy * dy <= raio * raio) {
                    resultado.setRGB(x, y, img.getRGB(x, y));
                } else {
                    resultado.setRGB(x, y, 0); 
                }
            }
        }
        return resultado;
    }
    private static void exibirImagem(BufferedImage img, String titulo) {
        JFrame frame = new JFrame(titulo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        JLabel label = new JLabel(new ImageIcon(img));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centralizar na tela
        frame.setVisible(true);
    }
}
