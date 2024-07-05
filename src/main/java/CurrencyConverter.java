import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter {
    private static final String API_KEY = "865314104ccf322e5d4abce6";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static Map<String, Double> rates = new HashMap<>();

}
