import java.util.Scanner;

public class Calculadora {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite uma expressão matemática:");
        String expressao = scanner.nextLine();

        try {
            double resultado = avaliarExpressao(expressao);
            System.out.println("Resultado: " + resultado);
        } catch (IllegalArgumentException e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public static double avaliarExpressao(String expressao) {
        // Análise léxica e sintática: divisão da expressão em tokens e validação da estrutura
        // Aqui assumimos que a expressão é uma simples operação matemática entre dois operandos e um operador (+, -, *, /)
        String[] tokens = expressao.split(" ");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("Expressão inválida");
        }

        double operando1 = Double.parseDouble(tokens[0]);
        char operador = tokens[1].charAt(0);
        double operando2 = Double.parseDouble(tokens[2]);

        // Análise semântica: execução da operação
        double resultado;
        switch (operador) {
            case '+':
                resultado = operando1 + operando2;
                break;
            case '-':
                resultado = operando1 - operando2;
                break;
            case '*':
                resultado = operando1 * operando2;
                break;
            case '/':
                if (operando2 == 0) {
                    throw new IllegalArgumentException("Divisão por zero");
                }
                resultado = operando1 / operando2;
                break;
            default:
                throw new IllegalArgumentException("Operador inválido");
        }

        return resultado;
    }
}


