import java.awt.image.BufferedImage;
import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class media3x3 {
    public static void main(String[] args) {
        String imagePath = "C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\ImagemFiltro\\2.png";
        BufferedImage imagem = null;
        try {
            imagem = ImageIO.read(new File(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Criar o filtro médio 3x3
        float[][] filtro = {{1f/9, 1f/9, 1f/9},
                            {1f/9, 1f/9, 1f/9},
                            {1f/9, 1f/9, 1f/9}};

        BufferedImage imagemFiltrada = aplicarFiltro(imagem, filtro);
        exibirImagem(imagem, "Imagem Original");
        exibirImagem(imagemFiltrada, "Imagem Filtrada 3x3");
    }

    public static BufferedImage aplicarFiltro(BufferedImage imagem, float[][] filtro) {
        int width = imagem.getWidth();
        int height = imagem.getHeight();
        BufferedImage novaImagem = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                float[] pixel = new float[3];
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int rgb = imagem.getRGB(x + i, y + j);
                        pixel[0] += ((rgb >> 16) & 0xFF) * filtro[i + 1][j + 1];
                        pixel[1] += ((rgb >> 8) & 0xFF) * filtro[i + 1][j + 1];
                        pixel[2] += (rgb & 0xFF) * filtro[i + 1][j + 1];
                    }
                }
                int newRGB = (((int) pixel[0]) << 16) | (((int) pixel[1]) << 8) | ((int) pixel[2]);
                novaImagem.setRGB(x, y, newRGB);
            }
        }
        return novaImagem;
    }

    public static void exibirImagem(BufferedImage imagem, String title) {
        ImageIcon icon = new ImageIcon(imagem);
        JFrame frame = new JFrame(title);
        JLabel label = new JLabel();
        label.setIcon(icon);
        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
