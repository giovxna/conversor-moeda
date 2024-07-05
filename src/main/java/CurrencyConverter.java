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
}

