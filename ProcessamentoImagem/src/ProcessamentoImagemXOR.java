import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProcessamentoImagemXOR {

    public static void main(String[] args) {
        try {
            BufferedImage imagem1 = ImageIO.read(new File("C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\Imagem\\Jessica.jpeg"));
            BufferedImage imagem2 = ImageIO.read(new File("C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\Imagem\\PlacaMae\\Placa.jpg"));
            BufferedImage resultadoXor = aplicarXOR(imagem1, imagem2);
            exibirImagem(resultadoXor, "Resultado Operação XOR");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static BufferedImage aplicarXOR(BufferedImage img1, BufferedImage img2) {
        int largura = Math.min(img1.getWidth(), img2.getWidth());
        int altura = Math.min(img1.getHeight(), img2.getHeight());
        BufferedImage resultado = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);

                int novoRGB = rgb1 ^ rgb2;

                resultado.setRGB(x, y, novoRGB);
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
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
