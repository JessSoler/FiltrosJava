import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Media7x7 {
    public static void main(String[] args) {
        String imagePath = "C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\Imagem\\Jessica.jpeg";
        BufferedImage imagem = null;
        try {
            imagem = ImageIO.read(new File(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        float[][] filtro = new float[7][7];
        float valor = 1.0f / 49.0f; 
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                filtro[i][j] = valor;
            }
        }

        BufferedImage imagemFiltrada = aplicarFiltro(imagem, filtro);
        exibirImagem(imagem, "Imagem Original");
        exibirImagem(imagemFiltrada, "Imagem Filtrada 7x7");
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
