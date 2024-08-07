import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FiltroAgucamento {
    public static void main(String[] args) {
        String imagePath = "C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\Imagem\\Jessica.jpeg";
        BufferedImage imagem = null;
        try {
            imagem = ImageIO.read(new File(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        float[][] filtro = {
            {-1, -1, -1},
            {-1,  9, -1},
            {-1, -1, -1}
        };

        BufferedImage imagemAguçada = aplicarFiltro(imagem, filtro);

        exibirImagem(imagem, "Imagem Original");
        exibirImagem(imagemAguçada, "Imagem com Agucamento");
    }

    public static BufferedImage aplicarFiltro(BufferedImage imagem, float[][] filtro) {
        int width = imagem.getWidth();
        int height = imagem.getHeight();
        BufferedImage novaImagem = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int filterSize = filtro.length;
        int filterOffset = filterSize / 2;

        for (int y = filterOffset; y < height - filterOffset; y++) {
            for (int x = filterOffset; x < width - filterOffset; x++) {
                float pixel = 0;
                for (int i = 0; i < filterSize; i++) {
                    for (int j = 0; j < filterSize; j++) {
                        int rgb = imagem.getRGB(x + j - filterOffset, y + i - filterOffset);
                        pixel += ((rgb >> 16) & 0xFF) * filtro[i][j];
                    }
                }
                int newPixel = clamp((int) pixel);
                novaImagem.setRGB(x, y, (newPixel << 16) | (newPixel << 8) | newPixel);
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

    public static int clamp(int value) {
        return Math.max(0, Math.min(value, 255));
    }
}
