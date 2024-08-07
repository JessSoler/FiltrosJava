import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;

public class FiltroCanny {
    public static void main(String[] args) {
        String imagePath = "C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\ImagemFiltro\\5.png";

        try {
            BufferedImage originalImage = ImageIO.read(new File(imagePath));

            displayImage(originalImage, "Imagem Original");

            BufferedImage edgesImage = applyCannyFilter(originalImage);
            
            displayImage(edgesImage, "Bordas (Filtro Canny)");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static BufferedImage applyCannyFilter(BufferedImage originalImage) {

        BufferedImage grayImage = toGrayScale(originalImage);

        double[][] gaussianKernel = {{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
        BufferedImage blurredImage = applyConvolution(grayImage, gaussianKernel);

        double[][] sobelX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        double[][] sobelY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        BufferedImage gradientX = applyConvolution(blurredImage, sobelX);
        BufferedImage gradientY = applyConvolution(blurredImage, sobelY);
        BufferedImage magnitudeImage = calculateGradientMagnitude(gradientX, gradientY);
        double lowThreshold = 30.0;
        double highThreshold = 100.0;
        BufferedImage edgesImage = applyThreshold(magnitudeImage, lowThreshold, highThreshold);

        return edgesImage;
    }

    private static BufferedImage toGrayScale(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                int grayValue = (int) (0.2989 * color.getRed() + 0.5870 * color.getGreen() + 0.1140 * color.getBlue());
                grayImage.setRGB(x, y, new Color(grayValue, grayValue, grayValue).getRGB());
            }
        }
        return grayImage;
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

    private static BufferedImage calculateGradientMagnitude(BufferedImage gradientX, BufferedImage gradientY) {
        int width = gradientX.getWidth();
        int height = gradientX.getHeight();
        BufferedImage magnitudeImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int gx = new Color(gradientX.getRGB(x, y)).getRed();
                int gy = new Color(gradientY.getRGB(x, y)).getRed();
                int magnitude = (int) Math.sqrt(gx * gx + gy * gy);
                magnitude = Math.min(Math.max(magnitude, 0), 255);
                magnitudeImage.setRGB(x, y, new Color(magnitude, magnitude, magnitude).getRGB());
            }
        }
        return magnitudeImage;
    }

    private static BufferedImage applyThreshold(BufferedImage image, double lowThreshold, double highThreshold) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage resultImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelValue = new Color(image.getRGB(x, y)).getRed();
                if (pixelValue < lowThreshold) {
                    resultImage.setRGB(x, y, Color.BLACK.getRGB());
                } else if (pixelValue > highThreshold) {
                    resultImage.setRGB(x, y, Color.WHITE.getRGB());
                } else {
                    boolean hasStrongNeighbor = false;
                    outerloop:
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dx = -1; dx <= 1; dx++) {
                            if (x + dx >= 0 && x + dx < width && y + dy >= 0 && y + dy < height) {
                                int neighborValue = new Color(image.getRGB(x + dx, y + dy)).getRed();
                                if (neighborValue > highThreshold) {
                                    hasStrongNeighbor = true;
                                    break outerloop;
                                }
                            }
                        }
                    }
                    if (hasStrongNeighbor) {
                        resultImage.setRGB(x, y, Color.WHITE.getRGB());
                    } else {
                        resultImage.setRGB(x, y, Color.BLACK.getRGB());
                    }
                }
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
