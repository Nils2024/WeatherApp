import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherAPI {

    String urlAddress = "https://api.open-meteo.com/v1/forecast?latitude=";
    String urlParameter = "&current=temperature_2m,relative_humidity_2m,wind_speed_10m,weathercode";
    JSONObject data;

    public void setData(double latitude, double longitude){

        String cityURL = urlAddress + latitude + "&longitude=" + longitude + urlParameter;

        HttpURLConnection connection = createApiConnection(cityURL);
        String cityInformation = readApiResponse(connection);
        System.out.println("Weather Data: " + cityInformation);

        try {
            JSONParser parser = new JSONParser();
            data = (JSONObject) parser.parse(cityInformation);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Beim Parsen in ein JSONObjekt gab es einen Fehler");
        }


    }

    public HttpURLConnection createApiConnection(String fullURL) {

        try {
            URL url = new URL(fullURL);

            HttpURLConnection apiConnection = (HttpURLConnection) url.openConnection();

            apiConnection.setRequestMethod("GET");

            return apiConnection;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Verbindung fehlgeschlagen" + fullURL);
        }
        return null;
    }

    public String readApiResponse(HttpURLConnection apiConnection){

        try {

            StringBuilder result = new StringBuilder();

            Scanner scanner = new Scanner(apiConnection.getInputStream());

            while (scanner.hasNext()){
                result.append(scanner.nextLine());
            }

            scanner.close();

            return result.toString();

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Lesen fehlgeschlagen");
        }
        return null;
    }

    public String getTemperature(){
        String temperature = ((JSONObject) data.get("current")).get("temperature_2m").toString();
        String temperatureSymbol = ((JSONObject) data.get("current_units")).get("temperature_2m").toString();
        return temperature + "" + temperatureSymbol;
    }

    public String getHumidity(){
        String humidity = ((JSONObject) data.get("current")).get("relative_humidity_2m").toString();
        String humiditySymbol = ((JSONObject) data.get("current_units")).get("relative_humidity_2m").toString();
        return humidity + "" + humiditySymbol;
    }

    //"latitude":49.62,"longitude":11.02,"generationtime_ms":0.033020973205566406,"utc_offset_seconds":0,"timezone":"GMT","timezone_abbreviation":"GMT","elevation":282.0,"current_units":{"time":"iso8601","interval":"seconds","temperature_2m":"°C","relative_humidity_2m":"%","wind_speed_10m":"km/h"},"current":{"time":"2024-08-28T17:00","interval":900,"temperature_2m":29.2,"relative_humidity_2m":44,"wind_speed_10m":3.3}}

    public String getWindSpeed(){
        String windSpeed = ((JSONObject) data.get("current")).get("wind_speed_10m").toString();
        String windSpeedSymbol = ((JSONObject) data.get("current_units")).get("wind_speed_10m").toString();
        return windSpeed + "" + windSpeedSymbol;
    }

    private String convertWeatherCodeToString(int weatherCodeNumber){            //
        if(weatherCodeNumber == 0)
            return "Sunny";
        else if(weatherCodeNumber > 0 && weatherCodeNumber <= 3)
            return "Cloudy";
        else if((weatherCodeNumber >= 51 && weatherCodeNumber <= 67) || (weatherCodeNumber >= 80 && weatherCodeNumber <= 99))
            return "Rain";
        else if(weatherCodeNumber >= 71 && weatherCodeNumber <= 77)
            return "Snow";
        else
            return "NOT_FOUND";
    }

    public String getWeatherCode(){                             //Gibt den WeatherCode (z.b. "Sunny") als String zurück
        String weatherCode = ((JSONObject) data.get("current")).get("weathercode").toString();          //bekommt hier 0, wandelt ihn in String um
        int weatherCodeInt = Integer.parseInt(weatherCode);                                             // Wandelt den gespeicherten Wettercode "0" etc in 0 etc (INT) um
        String readableWeatherCode = convertWeatherCodeToString(weatherCodeInt);                        // Wandelt den Int Code in einen Lesbaren Text wie "Sunny" um
        return readableWeatherCode;                                                                     // GIbt umgewandelten Wettercode (lesbar) zurück
    }


}
