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
    private Button setPol;

    @FXML
    private Button setRus;

    @FXML
    private Text text_info;

    @FXML
    private Text text_title;

    boolean polLang = false;
    boolean ruLang = true;


    @FXML
    void initialize() {

        // insert api here
        String api = "02adaff5c8f8e814f17149a8b6b40737";

        getCityName.setOnAction(event -> {
            String city = cityName.getText().trim();
            if(!getCityName.equals("")) {
                String formatted = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s", city, api);
                String output = getContent(formatted);
                System.out.println(output);

                if (!output.isEmpty()) {
                    if (ruLang) {
                        JSONObject jsonObject = new JSONObject(output);
                        current.setText("Сейчас: " + (jsonObject.getJSONObject("main").getInt("temp") - 273));
                        feelsLike.setText("Ощущается: " + (jsonObject.getJSONObject("main").getInt("feels_like") - 273));
                        maxTemp.setText("Максимальная: " + (jsonObject.getJSONObject("main").getInt("temp_max") - 273));
                        minTemp.setText("Минимальная: " + (jsonObject.getJSONObject("main").getInt("temp_min") - 273));
                    } else {
                        JSONObject jsonObject = new JSONObject(output);
                        current.setText("Teraz:: " + (jsonObject.getJSONObject("main").getInt("temp") - 273));
                        feelsLike.setText("Odczuwa sie: " + (jsonObject.getJSONObject("main").getInt("feels_like") - 273));
                        maxTemp.setText("Maksymalna: " + (jsonObject.getJSONObject("main").getInt("temp_max") - 273));
                        minTemp.setText("Minimalna: " + (jsonObject.getJSONObject("main").getInt("temp_min") - 273));
                    }
                }
            }
        });

        setPol.setOnAction(event -> {
            polLang = true;
            ruLang = false;

            text_title.setText("Pogoda");
            cityName.setPromptText("Wpisz miasto:");
            getCityName.setText("    Zatwierdz    ");
            text_info.setText("  Informacja:");
            current.setText("Teraz:");
            feelsLike.setText("Odczuwa sie:");
            maxTemp.setText("Maksymalna");
            minTemp.setText("Minimalna");
        });

        setRus.setOnAction(event -> {
            polLang = false;
            ruLang = true;

            text_title.setText("Погода");
            cityName.setPromptText("Введите город:");
            getCityName.setText("Узнать погоду");
            text_info.setText("Информация:");
            current.setText("Сейчас:");
            feelsLike.setText("Ощущается:");
            maxTemp.setText("Максимальная:");
            minTemp.setText("Минимальная:");
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
