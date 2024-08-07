import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

public class FiltroLaplace {
    public static void main(String[] args) {
        String imagePath = "C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\ImagemFiltro\\9.png";
        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));
            displayImage(originalImage, "Imagem Original");
            BufferedImage edgesImage = applyLaplaceFilter(originalImage);
            displayImage(edgesImage, "Bordas (Filtro Laplace)");
        } catch (Exception ex) {
            ex.printStackTrace(); } }
    private static BufferedImage applyLaplaceFilter(BufferedImage originalImage) {
        double[][] laplacianKernel = {{0, 1, 0}, {1, -4, 1}, {0, 1, 0}};
        BufferedImage filteredImage = applyConvolution(originalImage, laplacianKernel);
        return filteredImage;
    }
    private static BufferedImage applyConvolution(BufferedImage image, double[][] kernel) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        int kernelWidth = kernel[0].length;
        int kernelHeight = kernel.length;
        int kernelOffset = kernelWidth / 2;
        for (int y = kernelOffset; y < height - kernelOffset; y++) {
            for (int x = kernelOffset; x < width - kernelOffset; x++) {
                double sum = 0.0;
                for (int ky = 0; ky < kernelHeight; ky++) {
                    for (int kx = 0; kx < kernelWidth; kx++) {
                        int pixelValue = new Color(image.getRGB(x - kernelOffset + kx, y - kernelOffset + ky)).getRed();
                        sum += pixelValue * kernel[ky][kx];
                    }
                }
                int newValue = (int) Math.round(sum);
                newValue = Math.min(Math.max(newValue, 0), 255);
                resultImage.setRGB(x, y, new Color(newValue, newValue, newValue).getRGB());
            }
        }
        return resultImage;
    }
    private static void displayImage(BufferedImage image, String title) {
        ImageIcon icon = new ImageIcon(image);
        JFrame frame = new JFrame(title);
        frame.setLayout(new FlowLayout());
        frame.setSize(image.getWidth() + 50, image.getHeight() + 50);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

