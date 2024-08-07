import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main {
    public static void main(String[] args) {
        try {
            File file = new File("C:\\\\Users\\\\Jéssica Soler\\\\OneDrive\\\\Imagens\\\\Jessica.jpeg");
            BufferedImage image = ImageIO.read(file);
            
            BufferedImage imageWithText = writeTextOnImage(image, "");
            
            displayImage(resizeImage(imageWithText, 300, 300));
        } catch (Exception e) {
            System.out.println("Erro ao carregar a imagem: " + e.getMessage());
        }
    }
    
    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();
        return resizedImage;
    }
    
    private static BufferedImage writeTextOnImage(BufferedImage originalImage, String text) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        
        BufferedImage imageWithText = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = imageWithText.createGraphics();
        g2d.drawImage(originalImage, 0, 0, null);
        
        // Define a fonte e a cor do texto
        Font font = new Font("Arial", Font.BOLD, 24);
        g2d.setFont(font);
        g2d.setColor(Color.BLACK);
        
        // Obtém as métricas da fonte para calcular a posição do texto
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int x = (width - textWidth) / 2;
        int y = height / 2;
        
        // Desenha o texto no centro da imagem
        g2d.drawString(text, x, y);
        g2d.dispose();
        
        return imageWithText;
    }
    
    private static void displayImage(BufferedImage image) {
        JFrame frame = new JFrame();
        frame.setSize(image.getWidth(), image.getHeight());
        JLabel label = new JLabel(new ImageIcon(image));
        frame.add(label);
        frame.setVisible(true);
    }
}



