package org.example.demo2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;
import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Controller {

    @FXML
    private TextField cityName;

    @FXML
    private Text current;

    @FXML
    private Text feelsLike;

    @FXML
    private Button getCityName;

    @FXML
    private Text maxTemp;

    @FXML
    private Text minTemp;

    @FXML
    void initialize() {
        String api = "02adaff5c8f8e814f17149a8b6b40737";
        getCityName.setOnAction(event -> {
            String city = cityName.getText().trim();
            if(!getCityName.equals("")) {
                String formatted = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, api);
                String output = getContent(formatted);
                System.out.println(output);

                if (!output.isEmpty()) {
                    JSONObject jsonObject = new JSONObject(output);
                    current.setText("Сейчас: " + (jsonObject.getJSONObject("main").getInt("temp") - 273));
                    feelsLike.setText("Ощущается: " + (jsonObject.getJSONObject("main").getInt("feels_like") - 273));
                    maxTemp.setText("Максимальная: " + (jsonObject.getJSONObject("main").getInt("temp_max") - 273));
                    minTemp.setText("Минимальная: " + (jsonObject.getJSONObject("main").getInt("temp_min") - 273));
                }
            }
        });
    }

    private static String getContent(String urlAdress) {
        StringBuffer content = new StringBuffer();

        try {
            URL url = new URL(urlAdress);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                content.append(line);
            }
        } catch (Exception e) {
            System.out.println("Город не был найден!");
        }
        return content.toString();
    }
}
