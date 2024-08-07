import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Sul {
    public static void main(String[] args) {
        String imagePath = "C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\Imagem\\Jessica.jpeg";
        BufferedImage imagem = null;
        try {
            imagem = ImageIO.read(new File(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        float[][] filtro = {{1, 2, 1},
                            {0, 0, 0},
                            {-1, -2, -1}};

        BufferedImage imagemFiltrada = aplicarFiltro(imagem, filtro);
        exibirImagem(imagem, "Imagem Original");
        exibirImagem(imagemFiltrada, "Bordas na Direção Sul");
    }

    public static BufferedImage aplicarFiltro(BufferedImage imagem, float[][] filtro) {
        int width = imagem.getWidth();
        int height = imagem.getHeight();
        BufferedImage novaImagem = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        int filterSize = filtro.length;
        int filterOffset = filterSize / 2;

        for (int y = filterOffset; y < height - filterOffset; y++) {
            for (int x = filterOffset; x < width - filterOffset; x++) {
                float[] pixel = new float[3];
                for (int i = -filterOffset; i <= filterOffset; i++) {
                    for (int j = -filterOffset; j <= filterOffset; j++) {
                        int rgb = imagem.getRGB(x + i, y + j);
                        pixel[0] += ((rgb >> 16) & 0xFF) * filtro[i + filterOffset][j + filterOffset];
                        pixel[1] += ((rgb >> 8) & 0xFF) * filtro[i + filterOffset][j + filterOffset];
                        pixel[2] += (rgb & 0xFF) * filtro[i + filterOffset][j + filterOffset];
                    }
                }
                int newRGB = clamp((int) Math.sqrt(pixel[0] * pixel[0] + pixel[1] * pixel[1] + pixel[2] * pixel[2]));
                novaImagem.setRGB(x, y, (newRGB << 16) | (newRGB << 8) | newRGB);
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
