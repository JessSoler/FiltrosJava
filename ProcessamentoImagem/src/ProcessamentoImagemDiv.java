import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProcessamentoImagemDiv {
    public static void main(String[] args) {
        try {
            BufferedImage imagem1 = ImageIO.read(new File("C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\Imagem\\Jessica.jpeg"));
            BufferedImage imagem2 = ImageIO.read(new File("C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\Imagem\\PlacaMae\\Placa.jpg"));
            BufferedImage resultadoDivisao = aplicarDivisao(imagem1, imagem2);
            exibirImagem(resultadoDivisao, "Resultado Divisão");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage aplicarDivisao(BufferedImage img1, BufferedImage img2) {
        int largura = Math.min(img1.getWidth(), img2.getWidth());
        int altura = Math.min(img1.getHeight(), img2.getHeight());
        BufferedImage resultado = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int rgb1 = img1.getRGB(x, y);
                int rgb2 = img2.getRGB(x, y);

                int r1 = (rgb1 >> 16) & 0xFF;
                int g1 = (rgb1 >> 8) & 0xFF;
                int b1 = rgb1 & 0xFF;

                int r2 = (rgb2 >> 16) & 0xFF;
                int g2 = (rgb2 >> 8) & 0xFF;
                int b2 = rgb2 & 0xFF;

                if (r2 == 0) r2 = 1;
                if (g2 == 0) g2 = 1;
                if (b2 == 0) b2 = 1;

                int novoR = Math.min(r1 / r2, 255); 
                int novoG = Math.min(g1 / g2, 255);
                int novoB = Math.min(b1 / b2, 255);

                int novoRGB = (novoR << 16) | (novoG << 8) | novoB;
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
