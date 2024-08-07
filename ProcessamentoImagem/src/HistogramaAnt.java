import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class HistogramaAnt {
    public static void main(String[] args) {
        try {

            BufferedImage originalImage = ImageIO.read(new File("C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\ImagemFiltro\\2.png"));
            int width = originalImage.getWidth();
            int height = originalImage.getHeight();
            

            int[] histogram = new int[256];
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = originalImage.getRGB(x, y);
                    int grayValue = (int) (0.299 * ((rgb >> 16) & 0xFF) + 0.587 * ((rgb >> 8) & 0xFF) + 0.114 * (rgb & 0xFF)); // Convertendo para escala de cinza
                    histogram[grayValue]++;
                }
            }
            int[] cdf = new int[256];
            cdf[0] = histogram[0];
            for (int i = 1; i < 256; i++) {
                cdf[i] = cdf[i - 1] + histogram[i];
            }
            int totalPixels = width * height;
            float[] normalizedCdf = new float[256];
            for (int i = 0; i < 256; i++) {
                normalizedCdf[i] = (float) cdf[i] / totalPixels * 255;
            }
            BufferedImage equalizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int rgb = originalImage.getRGB(x, y);
                    int grayValue = (int) (0.299 * ((rgb >> 16) & 0xFF) + 0.587 * ((rgb >> 8) & 0xFF) + 0.114 * (rgb & 0xFF)); // Convertendo para escala de cinza
                    int newGrayValue = (int) normalizedCdf[grayValue];
                    int newRGB = (newGrayValue << 16) | (newGrayValue << 8) | newGrayValue;
                    equalizedImage.setRGB(x, y, newRGB);
                }
            }
            JFrame frame = new JFrame("Imagem Equalizada");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new ImagePanel(equalizedImage));
            frame.pack();
            frame.setVisible(true);
        } catch (IOException e) {
            System.out.println("Erro ao processar a imagem: " + e.getMessage());
        }
    }

    static class ImagePanel extends JPanel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BufferedImage image;

        public ImagePanel(BufferedImage image) {
            this.image = image;
            Dimension size = new Dimension(image.getWidth(), image.getHeight());
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
            setLayout(null);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }
}
