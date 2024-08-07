import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FiltroSobel {
    public static void main(String[] args) {
        String imagePath =  "C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\ImagemFiltro\\8.png";
        BufferedImage imagem = null;
        try {
            imagem = ImageIO.read(new File(imagePath));
        } catch (Exception e) {
            e.printStackTrace();
        }

        float[][] filtroX = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        float[][] filtroY = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        BufferedImage imagemFiltradaX = aplicarFiltro(imagem, filtroX);
        BufferedImage imagemFiltradaY = aplicarFiltro(imagem, filtroY);
        BufferedImage imagemResultado = combinarImagens(imagemFiltradaX, imagemFiltradaY);
        exibirImagem(imagem, "Imagem Original");
        exibirImagem(imagemResultado, "Detecção de Bordas com Filtro de Sobel"); }
    public static BufferedImage aplicarFiltro(BufferedImage imagem, float[][] filtro) {
        int width = imagem.getWidth();
        int height = imagem.getHeight();
        BufferedImage novaImagem = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int filterSize = filtro.length;
        int filterOffset = filterSize / 2;
        for (int y = filterOffset; y < height - filterOffset; y++) {
            for (int x = filterOffset; x < width - filterOffset; x++) {
                float pixelX = 0;
                float pixelY = 0;
                for (int i = 0; i < filterSize; i++) {
                    for (int j = 0; j < filterSize; j++) {
                        int rgb = imagem.getRGB(x + j - filterOffset, y + i - filterOffset);
                        pixelX += ((rgb >> 16) & 0xFF) * filtro[i][j];
                        pixelY += ((rgb >> 16) & 0xFF) * filtro[i][j];
                    }  }
                int magnitude = clamp((int) Math.sqrt(pixelX * pixelX + pixelY * pixelY));
                novaImagem.setRGB(x, y, (magnitude << 16) | (magnitude << 8) | magnitude);
            }}
        return novaImagem; }
    public static BufferedImage combinarImagens(BufferedImage imagemX, BufferedImage imagemY) {
        int width = imagemX.getWidth();
        int height = imagemX.getHeight();
        BufferedImage novaImagem = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixelX = (imagemX.getRGB(x, y) >> 16) & 0xFF;
                int pixelY = (imagemY.getRGB(x, y) >> 16) & 0xFF;
                int magnitude = clamp((int) Math.sqrt(pixelX * pixelX + pixelY * pixelY));
                novaImagem.setRGB(x, y, (magnitude << 16) | (magnitude << 8) | magnitude); } }
        return novaImagem; }
    public static void exibirImagem(BufferedImage imagem, String title) {
        ImageIcon icon = new ImageIcon(imagem);
        JFrame frame = new JFrame(title);
        JLabel label = new JLabel();
        label.setIcon(icon);
        frame.add(label);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true); }
    public static int clamp(int value) {
        return Math.max(0, Math.min(value, 255));
    }
}
