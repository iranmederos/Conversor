package apiCurrencies;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;

public class ApiRequest extends Exception{
    private static final String key = "975acd4d4a4d4d568baaf8a644074168";

    public static String[] getCurrencies() throws Exception {
        // Hacer la solicitud HTTP GET a la URL
        URL url = new URL("https://openexchangerates.org/api/currencies.json");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        // Leer la respuesta como una cadena JSON
        Scanner scanner = new Scanner(url.openStream());
        String json = scanner.useDelimiter("\\Z").next();
        scanner.close();
        conn.disconnect();

        // Convertir la cadena JSON en un mapa de Java
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> currencyMap = mapper.readValue(json, Map.class);

        // Convertir los valores del mapa en una matriz de cadenas
        String[] currencies = currencyMap.entrySet().stream()
                .map(entry -> entry.getKey() + "-" + entry.getValue())
                .toArray(String[]::new);

        return currencies;
    }

    public static Double getPrice(String symbol) throws Exception{
        try {
            // Construir el JSON de la petición
            RequestRateBody requestBodyObj = new  RequestRateBody(symbol);

            // Convertir la instancia de la clase en una cadena JSON
            ObjectMapper mapper = new ObjectMapper();
            String requestBody = mapper.writeValueAsString(requestBodyObj);

            // Establecer la conexión HTTP
            URL url = new URL("https://openexchangerates.org/api/latest.json?app_id="+key);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-Type", "application/json");

            // Enviar la petición
            con.setDoOutput(true);
            con.getOutputStream().write(requestBody.getBytes());

            // Leer la respuesta JSON
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String response = "";
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response += inputLine;
            }
            in.close();
            con.disconnect();

            // Analizar el JSON y obtener el valor de "symbol"
            ObjectMapper mapper_answer = new ObjectMapper();
            JsonNode rootNode = mapper_answer.readTree(response);
            JsonNode ratesNode = rootNode.get("rates");
            double rate = ratesNode.get(symbol).asDouble();

            // Usar el valor obtenido
            return rate;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 0.0;
        }
    }
}
