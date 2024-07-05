import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConversorMoeda {
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

    private static double realizarConversao(int option, double amount) {
        String fromCurrency = "";
        String toCurrency = "";

        switch (option) {
            case 1:
                fromCurrency = "USD";
                toCurrency = "BRL";
                break;
            case 2:
                fromCurrency = "BRL";
                toCurrency = "USD";
                break;
            case 3:
                fromCurrency = "EUR";
                toCurrency = "BRL";
                break;
            case 4:
                fromCurrency = "BRL";
                toCurrency = "EUR";
                break;
            case 5:
                fromCurrency = "ARS";
                toCurrency = "BRL";
                break;
            case 6:
                fromCurrency = "BRL";
                toCurrency = "ARS";
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                return -1;
        }

        return convert(amount, fromCurrency, toCurrency);
    }
    private static boolean getRates(String baseCurrency) {
        String url = API_URL + baseCurrency;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                Gson gson = new Gson();
                JsonObject jsonResponse = gson.fromJson(response.body(), JsonObject.class);
                JsonObject conversionRates = jsonResponse.getAsJsonObject("conversion_rates");
                for (String currency : conversionRates.keySet()) {
                    rates.put(currency, conversionRates.get(currency).getAsDouble());
                }
                return true;
            } else {
                System.out.println("Erro ao obter taxas de câmbio: " + response.statusCode());
            }
        } catch (IOException e) {
            System.out.println("Erro de conexão: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("A conexão foi interrompida: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
        return false;
    }
    private static double convert(double amount, String fromCurrency, String toCurrency) {
        if (rates.containsKey(fromCurrency) && rates.containsKey(toCurrency)) {
            double rateFrom = rates.get(fromCurrency);
            double rateTo = rates.get(toCurrency);
            return amount * (rateTo / rateFrom);
        }
        return 0;
    }
}



