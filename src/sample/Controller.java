package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text temp_feel;

    @FXML
    private Text temp_info;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    private Text temp_pressure;

    @FXML
    void initialize() {
        getData.setOnAction(actionEvent -> {
            String getUserCity = city.getText().trim();
            if (!getUserCity.isEmpty()) {
                String output = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&APPID=814a94853ccecc613b0ec6761514e0f1");
                System.out.println(output);
                if (!output.isEmpty()) {
                    JSONObject obj = new JSONObject(output);
                    temp_info.setText("Температура: " + (int)(obj.getJSONObject("main").getDouble("temp") - 273.15));
                    temp_feel.setText("Ощущается: " + (int)(obj.getJSONObject("main").getDouble("feels_like") - 273.15));
                    temp_min.setText("Минимум: " + (int)(obj.getJSONObject("main").getDouble("temp_min") - 273.15));
                    temp_max.setText("Максимум: " + (int)(obj.getJSONObject("main").getDouble("temp_max") - 273.15));
                    temp_pressure.setText("Давление: " + (int)obj.getJSONObject("main").getDouble("pressure"));
                }
            }
        });
    }
    private static String getUrlContent(String urlAdress){
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null){
                content.append(line + '\n');
            }
            bufferedReader.close();
        }
        catch(Exception e){
            System.out.println("Такой город не найден.");
        }
        return content.toString();
    }
}
