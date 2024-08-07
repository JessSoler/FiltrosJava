import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;


public class FiltrosSuavizacao {
    public static void main(String[] args) {
        String imagePath = "C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\ImagemFiltro\\9.png";

        try {
            BufferedImage imagemOriginal = ImageIO.read(new File(imagePath));
            BufferedImage suavizacaoMedia = aplicarSuavizacaoMedia(imagemOriginal);
            BufferedImage suavizacaoMediana = aplicarSuavizacaoMediana(imagemOriginal);
            BufferedImage suavizacaoBilateral = aplicarSuavizacaoBilateral(imagemOriginal);

            salvarImagem(suavizacaoMedia, "SuavizacaoMedia.jpg");
            salvarImagem(suavizacaoMediana, "SuavizacaoMediana.jpg");
            salvarImagem(suavizacaoBilateral, "SuavizacaoBilateral.jpg");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static BufferedImage aplicarSuavizacaoMedia(BufferedImage imagemOriginal) {
        int width = imagemOriginal.getWidth();
        int height = imagemOriginal.getHeight();
        BufferedImage suavizacaoMedia = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int media = calcularMediaVizinhanca(imagemOriginal, x, y);
                suavizacaoMedia.setRGB(x, y, new java.awt.Color(media, media, media).getRGB());
            }
        }
        return suavizacaoMedia;
    }
    private static int calcularMediaVizinhanca(BufferedImage imagem, int x, int y) {
        int soma = 0;
        int totalPixels = 0;
        int raio = 1; 

        for (int i = -raio; i <= raio; i++) {
            for (int j = -raio; j <= raio; j++) {
                int pixelX = Math.min(Math.max(x + i, 0), imagem.getWidth() - 1);
                int pixelY = Math.min(Math.max(y + j, 0), imagem.getHeight() - 1);
                int rgb = imagem.getRGB(pixelX, pixelY);
                int valorIntensidade = (rgb >> 16) & 0xFF; 
                soma += valorIntensidade;
                totalPixels++;
            }
        }
        return soma / totalPixels;
    }

    
    private static BufferedImage aplicarSuavizacaoMediana(BufferedImage imagemOriginal) {
        int width = imagemOriginal.getWidth();
        int height = imagemOriginal.getHeight();
        BufferedImage suavizacaoMediana = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int[] vizinhos = obterVizinhos(imagemOriginal, x, y);
                Arrays.sort(vizinhos);
                int mediana = vizinhos[vizinhos.length / 2];
                suavizacaoMediana.setRGB(x, y, new java.awt.Color(mediana, mediana, mediana).getRGB());
            } }
        return suavizacaoMediana;
    }  
    private static int[] obterVizinhos(BufferedImage imagem, int x, int y) {
        int raio = 1; 
        int[] intensidades = new int[(2 * raio + 1) * (2 * raio + 1)];
        int index = 0;
        for (int i = -raio; i <= raio; i++) {
            for (int j = -raio; j <= raio; j++) {
                int pixelX = Math.min(Math.max(x + i, 0), imagem.getWidth() - 1);
                int pixelY = Math.min(Math.max(y + j, 0), imagem.getHeight() - 1);
                int rgb = imagem.getRGB(pixelX, pixelY);
                int intensidade = (rgb >> 16) & 0xFF; 
                intensidades[index++] = intensidade;
            } }
        return intensidades;
    }   
    private static BufferedImage aplicarSuavizacaoBilateral(BufferedImage imagemOriginal) {
        int width = imagemOriginal.getWidth();
        int height = imagemOriginal.getHeight();
        BufferedImage suavizacaoBilateral = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        double sigmaSpace = 10; 
        double sigmaIntensity = 50; 
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgbOriginal = imagemOriginal.getRGB(x, y);
                int intensidadeOriginal = (rgbOriginal >> 16) & 0xFF;
                double somaPonderada = 0;
                double pesoTotal = 0;
                for (int j = -1; j <= 1; j++) {
                    for (int i = -1; i <= 1; i++) {
                        int pixelX = Math.min(Math.max(x + i, 0), width - 1);
                        int pixelY = Math.min(Math.max(y + j, 0), height - 1);
                        int rgbVizinho = imagemOriginal.getRGB(pixelX, pixelY);
                        int intensidadeVizinho = (rgbVizinho >> 16) & 0xFF;
                        double diferencaEspacial = Math.sqrt((i * i + j * j));
                        double diferencaIntensidade = Math.abs(intensidadeVizinho - intensidadeOriginal);
                        double peso = Math.exp(-(diferencaEspacial * diferencaEspacial) / (2 * sigmaSpace * sigmaSpace))
                                * Math.exp(-(diferencaIntensidade * diferencaIntensidade) / (2 * sigmaIntensity * sigmaIntensity));
                        somaPonderada += intensidadeVizinho * peso;
                        pesoTotal += peso;
                    }
                }
                int novaIntensidade = (int) (somaPonderada / pesoTotal);
                suavizacaoBilateral.setRGB(x, y, new java.awt.Color(novaIntensidade, novaIntensidade, novaIntensidade).getRGB());
            }
        }
        return suavizacaoBilateral;
    }


    private static void salvarImagem(BufferedImage imagem, String caminho) {
        try {
            File outputfile = new File(caminho);
            ImageIO.write(imagem, "jpg", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
