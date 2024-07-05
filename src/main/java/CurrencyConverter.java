import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CurrencyConverter {
    private static final String API_KEY = "865314104ccf322e5d4abce6";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static Map<String, Double> rates = new HashMap<>();

    public static void main(String[] args) {
        String[] currencies = {"USD", "BRL", "EUR", "ARS", "BOB", "CLP", "COP"};
        String baseCurrency = "USD";

        if (!getRates(baseCurrency)) {
            System.out.println("Erro ao obter as taxas de câmbio.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            exibirMenu();
            int option = obterOpcao(scanner);

            if (option == 0) {
                break;
            }

            double amount = obterValor(scanner);
            if (amount < 0) {
                System.out.println("Valor inválido. Tente novamente.");
                continue;
            }

            double result = realizarConversao(option, amount);
            if (result >= 0) {
                System.out.printf("Resultado: %.2f\n", result);
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("Escolha a conversão desejada:");
        System.out.println("1. Dólar para Real");
        System.out.println("2. Real para Dólar");
        System.out.println("3. Euro para Real");
        System.out.println("4. Real para Euro");
        System.out.println("5. Peso Argentino para Real");
        System.out.println("6. Real para Peso Argentino");
        System.out.println("0. Sair");
    }

    private static int obterOpcao(Scanner scanner) {
        int option = -1;
        while (option < 0 || option > 6) {
            System.out.print("Digite a opção desejada: ");
            if (scanner.hasNextInt()) {
                option = scanner.nextInt();
            } else {
                System.out.println("Opção inválida. Tente novamente.");
                scanner.next();
            }
        }
        return option;
    }

    private static double obterValor(Scanner scanner) {
        double amount = -1;
        while (amount < 0) {
            System.out.print("Digite o valor a ser convertido: ");
            if (scanner.hasNextDouble()) {
                amount = scanner.nextDouble();
            } else {
                System.out.println("Valor inválido. Tente novamente.");
                scanner.next();
            }
        }
        return amount;
    }
}

