import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FiltroPrewitt {
    public static void main(String[] args) {
        String imagePath = "C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\ImagemFiltro\\9.png";

        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));


            BufferedImage filteredImage = applyPrewittFilter(originalImage);

            displayImage(originalImage, "Imagem Original");
            displayImage(filteredImage, "Imagem Filtrada (Prewitt)");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static BufferedImage applyPrewittFilter(BufferedImage originalImage) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics g = grayImage.getGraphics();
        g.drawImage(originalImage, 0, 0, null);
        g.dispose();

        BufferedImage prewittImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int gx = ((grayImage.getRGB(x - 1, y - 1) & 0xFF) +
                        (grayImage.getRGB(x - 1, y) & 0xFF) +
                        (grayImage.getRGB(x - 1, y + 1) & 0xFF) -
                        (grayImage.getRGB(x + 1, y - 1) & 0xFF) -
                        (grayImage.getRGB(x + 1, y) & 0xFF) -
                        (grayImage.getRGB(x + 1, y + 1) & 0xFF));
                int gy = ((grayImage.getRGB(x - 1, y - 1) & 0xFF) +
                        (grayImage.getRGB(x, y - 1) & 0xFF) +
                        (grayImage.getRGB(x + 1, y - 1) & 0xFF) -
                        (grayImage.getRGB(x - 1, y + 1) & 0xFF) -
                        (grayImage.getRGB(x, y + 1) & 0xFF) -
                        (grayImage.getRGB(x + 1, y + 1) & 0xFF));
                int gray = (int) Math.sqrt(gx * gx + gy * gy);
                prewittImage.setRGB(x, y, (gray << 16) | (gray << 8) | gray);
            }
        }

        return prewittImage;
    }

    private static void displayImage(BufferedImage image, String title) {
        ImageIcon icon = new ImageIcon(image);
        JFrame frame = new JFrame(title);
        JLabel label = new JLabel();
        label.setIcon(icon);
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
