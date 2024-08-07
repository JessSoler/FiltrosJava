import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Histograma {
    public static void main(String[] args) {
        try {
            BufferedImage img1 = ImageIO.read(new File("C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\Imagem\\Jessica.jpeg"));
            BufferedImage img2 = ImageIO.read(new File("C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\Imagem\\Arvore.jpeg"));

            img2 = resizeImage(img2, img1.getWidth(), img1.getHeight());

            BufferedImage resultImg = new BufferedImage(img1.getWidth(), img1.getHeight(), BufferedImage.TYPE_INT_RGB);
            for (int y = 0; y < img1.getHeight(); y++) {
                for (int x = 0; x < img1.getWidth(); x++) {
                    Color c1 = new Color(img1.getRGB(x, y));
                    Color c2 = new Color(img2.getRGB(x, y));
                    int red = Math.min(c1.getRed() + c2.getRed(), 255);
                    int green = Math.min(c1.getGreen() + c2.getGreen(), 255);
                    int blue = Math.min(c1.getBlue() + c2.getBlue(), 255);
                    Color sumColor = new Color(red, green, blue);
                    resultImg.setRGB(x, y, sumColor.getRGB());
                }
            }
            equalizeHistogram(resultImg);
            displayImage(resultImg);  
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();
        return resizedImage;
    }
    public static void equalizeHistogram(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();
        int[] histogram = new int[256];
        int[] cdf = new int[256];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);
                int gray = (rgb >> 16) & 0xFF; 
                histogram[gray]++;
            }
        }
        int sum = 0;
        for (int i = 0; i < histogram.length; i++) {
            sum += histogram[i];
            cdf[i] = sum;
        }
        int cdfMin = cdf[0];
        for (int i = 0; i < cdf.length; i++) {
            cdf[i] = (int)(((double)(cdf[i] - cdfMin) / ((double)width * height - cdfMin)) * 255);
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = img.getRGB(x, y);
                int gray = (rgb >> 16) & 0xFF; 
                int newGray = cdf[gray];
                Color newColor = new Color(newGray, newGray, newGray);
                img.setRGB(x, y, newColor.getRGB());
            }
        }
    }
    public static void displayImage(BufferedImage img) {
        JFrame frame = new JFrame();
        frame.getContentPane().add(new JLabel(new ImageIcon(img)));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
