import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ProcessamentoImagemNOT {
    public static void main(String[] args) {
        try {
            BufferedImage imagem1 = ImageIO.read(new File("C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\Imagem\\Jessica.jpeg"));
            BufferedImage imagem2 = ImageIO.read(new File("C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\Imagem\\PlacaMae\\Placa.jpg"));
            BufferedImage resultadoNot1 = aplicarNOT(imagem1);
            exibirImagem(resultadoNot1, "Resultado Operação NOT - Imagem 1");
            BufferedImage resultadoNot2 = aplicarNOT(imagem2);
            exibirImagem(resultadoNot2, "Resultado Operação NOT - Imagem 2");
        } catch (IOException e) {
            e.printStackTrace();} }
    private static BufferedImage aplicarNOT(BufferedImage img) {
        int largura = img.getWidth();
        int altura = img.getHeight();
        BufferedImage resultado = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int rgb = img.getRGB(x, y);

                int novoR = 255 - (rgb >> 16 & 0xFF); // Inverter vermelho
                int novoG = 255 - (rgb >> 8 & 0xFF); // Inverter verde
                int novoB = 255 - (rgb & 0xFF); // Inverter azul
                int novoRGB = (novoR << 16) | (novoG << 8) | novoB;
                resultado.setRGB(x, y, novoRGB);
            } }
        return resultado;}
    private static void exibirImagem(BufferedImage img, String titulo) {
        JFrame frame = new JFrame(titulo);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        JLabel label = new JLabel(new ImageIcon(img));
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
