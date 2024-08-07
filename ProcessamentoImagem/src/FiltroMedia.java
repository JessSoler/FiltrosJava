import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FiltroMedia {

    // Método para carregar uma imagem de arquivo
    public static int[][] loadImage(String filePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(filePath));
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] pixels = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[y][x] = image.getRGB(x, y) & 0xFF; // Converte o valor ARGB para escala de cinza
            }
        }

        return pixels;
    }

    // Aplica o filtro de média 3x3 em uma matriz de pixels
    public static int[][] filter3x3(int[][] image) {
        int height = image.length;
        int width = image[0].length;
        int[][] filteredImage = new int[height][width];

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                int sum = 0;
                for (int dy = -1; dy <= 1; dy++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        sum += image[y + dy][x + dx];
                    }
                }
                filteredImage[y][x] = sum / 9; // 9 é o tamanho do filtro (3x3)
            }
        }

        return filteredImage;
    }

    // Aplica o filtro de média 5x5
    public static int[][] filter5x5(int[][] image) {
        int height = image.length;
        int width = image[0].length;
        int[][] filteredImage = new int[height][width];

        for (int y = 2; y < height - 2; y++) {
            for (int x = 2; x < width - 2; x++) {
                int sum = 0;
                for (int dy = -2; dy <= 2; dy++) {
                    for (int dx = -2; dx <= 2; dx++) {
                        sum += image[y + dy][x + dx];
                    }
                }
                filteredImage[y][x] = sum / 25; // 25 é o tamanho do filtro (5x5)
            }
        }

        return filteredImage;
    }

    // Aplica o filtro de média 7x7
    public static int[][] filter7x7(int[][] image) {
        int height = image.length;
        int width = image[0].length;
        int[][] filteredImage = new int[height][width];

        for (int y = 3; y < height - 3; y++) {
            for (int x = 3; x < width - 3; x++) {
                int sum = 0;
                for (int dy = -3; dy <= 3; dy++) {
                    for (int dx = -3; dx <= 3; dx++) {
                        sum += image[y + dy][x + dx];
                    }
                }
                filteredImage[y][x] = sum / 49; // 49 é o tamanho do filtro (7x7)
            }
        }

        return filteredImage;
    }

    // Aplica o filtro de média 9x9
    public static int[][] filter9x9(int[][] image) {
        int height = image.length;
        int width = image[0].length;
        int[][] filteredImage = new int[height][width];

        for (int y = 4; y < height - 4; y++) {
            for (int x = 4; x < width - 4; x++) {
                int sum = 0;
                for (int dy = -4; dy <= 4; dy++) {
                    for (int dx = -4; dx <= 4; dx++) {
                        sum += image[y + dy][x + dx];
                    }
                }
                filteredImage[y][x] = sum / 81; // 81 é o tamanho do filtro (9x9)
            }
        }

        return filteredImage;
    }

    // Método para exibir uma matriz de pixels na tela
    public static void printImage(int[][] image) {
        for (int[] row : image) {
            for (int pixel : row) {
                System.out.print(pixel + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try {
            String filePath = "C:\\Users\\Jéssica Soler\\OneDrive\\Área de Trabalho\\Filtros\\Imagem\\Jessica.jpeg";
            int[][] image = loadImage(filePath);

            // Aplica e mostra os filtros de média
            System.out.println("Filtro de Média 3x3:");
            int[][] filteredImage3x3 = filter3x3(image);
            printImage(filteredImage3x3);
            System.out.println();

            System.out.println("Filtro de Média 5x5:");
            int[][] filteredImage5x5 = filter5x5(image);
            printImage(filteredImage5x5);
            System.out.println();

            System.out.println("Filtro de Média 7x7:");
            int[][] filteredImage7x7 = filter7x7(image);
            printImage(filteredImage7x7);
            System.out.println();

            System.out.println("Filtro de Média 9x9:");
            int[][] filteredImage9x9 = filter9x9(image);
            printImage(filteredImage9x9);
            System.out.println();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
