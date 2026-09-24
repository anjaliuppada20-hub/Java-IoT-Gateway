```java
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class IoTGateway {

    // IoT server URL
    private static final String SERVER_URL =
            "http://localhost:8080/api/sensor";

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println("       JAVA IoT GATEWAY");
        System.out.println("================================");

        Random random = new Random();

        while (true) {

            try {
                // Simulate sensor readings
                double temperature =
                        20 + (30 - 20) * random.nextDouble();

                double humidity =
                        40 + (80 - 40) * random.nextDouble();

                int light =
                        random.nextInt(1024);

                // Display readings
                System.out.println();
                System.out.println("Sensor Data");
                System.out.println("----------------------------");

                System.out.printf(
                        "Temperature : %.2f °C%n",
                        temperature
                );

                System.out.printf(
                        "Humidity    : %.2f %% %n",
                        humidity
                );

                System.out.println(
                        "Light       : " + light
                );

                // Create JSON
                String json =
                        "{"
                        + "\"temperature\":" + temperature + ","
                        + "\"humidity\":" + humidity + ","
                        + "\"light\":" + light
                        + "}";

                // Send data to IoT server
                sendData(json);

                // Wait 5 seconds
                Thread.sleep(5000);

            } catch (Exception e) {

                System.out.println(
                        "Gateway error: " + e.getMessage()
                );

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    /*
     * Sends sensor data to the IoT server.
     */
    private static void sendData(String json)
            throws IOException {

        URL url = new URL(SERVER_URL);

        HttpURLConnection connection =
                (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("POST");

        connection.setRequestProperty(
                "Content-Type",
                "application/json"
        );

        connection.setDoOutput(true);

        // Send JSON
        try (OutputStream output =
                     connection.getOutputStream()) {

            byte[] data =
                    json.getBytes(StandardCharsets.UTF_8);

            output.write(data);
        }

        // Read server response
        int responseCode =
                connection.getResponseCode();

        System.out.println(
                "Server Response: " + responseCode
        );

        connection.disconnect();
    }
}
```
