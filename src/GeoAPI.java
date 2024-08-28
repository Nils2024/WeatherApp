import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GeoAPI {

    String urlAddress = "https://geocoding-api.open-meteo.com/v1/search?name=";
    String urlParameter = "&count=1&language=en&format=json";
    JSONObject data;

    public GeoAPI(){



    }

    public void setData(String city){

        city = city.replaceAll(" ", "+");
        String cityURL = urlAddress + city + urlParameter;

        HttpURLConnection connection = createApiConnection(cityURL);
        String cityInformation = readApiResponse(connection);
        System.out.println(cityInformation);

        try {
            JSONParser parser = new JSONParser();
            data = (JSONObject) parser.parse(cityInformation);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Beim Parsen in ein JSONObjekt gab es einen Fehler");
        }

        JSONArray dataArray = (JSONArray) data.get("results");
        data = (JSONObject) dataArray.get(0);

    }

    public double getLatitude(){
        double latitude = (double) data.get("latitude");
        return latitude;
    }

    public double getLongitude(){
        double longitude = (double) data.get("longitude");
        return longitude;
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


}
