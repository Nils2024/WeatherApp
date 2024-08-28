import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class MyGUI extends JFrame implements ActionListener {

    SearchButton searchButton = new SearchButton();
    SearchBar searchBar = new SearchBar();
    GeoAPI geoAPI;
    WeatherAPI weatherAPI;

    // Icons
    JLabel weatherIcon;
    JLabel humidityIcon;
    JLabel windIcon;

    // Texts
    JLabel lblTemp;
    JLabel lblHumidity;
    JLabel lblWindspeed;

    JLabel lblHeaderTemp;
    JLabel lblHeaderHumidity;
    JLabel lblHeaderWindspeed;



    HashMap<String, ImageIcon> weatherConditionPictures;

    public MyGUI() {

        this.setBounds(0, 0, 500, 650);
        setLocationRelativeTo(null);
        this.setTitle("Weather App");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);

        geoAPI = new GeoAPI();
        weatherAPI = new WeatherAPI();

        // Icons
        weatherIcon = new JLabel();
        humidityIcon = new JLabel();
        windIcon = new JLabel();

        humidityIcon.setIcon(new ImageIcon("C:\\Code\\Java\\WeatherApp\\src\\assets\\humidity.png"));
        windIcon.setIcon(new ImageIcon("C:\\Code\\Java\\WeatherApp\\src\\assets\\windspeed.png"));

        lblTemp = new JLabel();
        lblHumidity = new JLabel();
        lblWindspeed = new JLabel();

        lblHeaderTemp = new JLabel();
        lblHeaderHumidity = new JLabel();
        lblHeaderWindspeed = new JLabel();

        weatherConditionPictures = new HashMap<>();
        weatherConditionPictures.put("Sunny", new ImageIcon("C:/Code/Java/WeatherApp/src/assets/clear.png"));
        weatherConditionPictures.put("Cloudy", new ImageIcon("C:/Code/Java/WeatherApp/src/assets/cloudy.png"));
        weatherConditionPictures.put("Rain", new ImageIcon("C:/Code/Java/WeatherApp/src/assets/rain.png"));
        weatherConditionPictures.put("Snow", new ImageIcon("C:/Code/Java/WeatherApp/src/assets/snow.png"));

        searchButton.addActionListener(this);
        searchBar.addActionListener(this);

        Container myContentpane = this.getContentPane();
        myContentpane.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        JPanel centerPanel = new JPanel();

        myContentpane.add(topPanel, BorderLayout.NORTH);
        myContentpane.add(centerPanel, BorderLayout.CENTER);

        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10,25));
        topPanel.add(searchBar);
        topPanel.add(searchButton);

        centerPanel.setLayout(null);

        weatherIcon.setBounds(125, 10, 450, 217);
        centerPanel.add(weatherIcon);
        humidityIcon.setBounds(15,420,74,66);
        centerPanel.add(humidityIcon);
        windIcon.setBounds(250, 420, 74, 66);
        centerPanel.add(windIcon);

        lblTemp.setFont(new Font("Comic Sans", Font.BOLD, 30));
        lblTemp.setText("GRAD");
        lblTemp.setBounds(125,240, 245, 30);
        lblTemp.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(lblTemp);

        lblHeaderTemp.setFont(new Font("Comic Sans", 0, 25));
        lblHeaderTemp.setText("CLOUDY?");
        lblHeaderTemp.setBounds(125,280, 245, 25);
        lblHeaderTemp.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(lblHeaderTemp);

        lblHeaderHumidity.setFont(new Font("Comic Sans", 1, 17));
        lblHeaderHumidity.setText("Humidity");
        lblHeaderHumidity.setBounds(95,420, 225, 40);
        centerPanel.add(lblHeaderHumidity);

        lblHumidity.setFont(new Font("Comic Sans", 0, 17));
        lblHumidity.setText("PROZENT");
        lblHumidity.setBounds(95,440, 225, 40);
        centerPanel.add(lblHumidity);

        lblHeaderWindspeed.setFont(new Font("Comic Sans", 1, 17));
        lblHeaderWindspeed.setText("Windspeed");
        lblHeaderWindspeed.setBounds(340,420, 225, 40);
        centerPanel.add(lblHeaderWindspeed);

        lblWindspeed.setFont(new Font("Comic Sans", 0, 17));
        lblWindspeed.setText("KMH");
        lblWindspeed.setBounds(340,440, 225, 40);
        centerPanel.add(lblWindspeed);

        displayCity("Nuernberg");
        this.setVisible(true);

    }

    private void displayCity(String currentCity) {
        geoAPI.setData(currentCity);
        weatherAPI.setData(geoAPI.getLatitude(), geoAPI.getLongitude());
        weatherIcon.setIcon(weatherConditionPictures.get(weatherAPI.getWeatherCode()));
        lblHumidity.setText(weatherAPI.getHumidity());
        lblWindspeed.setText(weatherAPI.getWindSpeed());
        lblHeaderTemp.setText(weatherAPI.getWeatherCode());
        lblTemp.setText(weatherAPI.getTemperature());

        System.out.println(weatherAPI.getTemperature());
        System.out.println(weatherAPI.getHumidity());
        System.out.println(weatherAPI.getWindSpeed());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String currentCity = searchBar.getText();
        geoAPI.setData(currentCity);
        weatherAPI.setData(geoAPI.getLatitude(), geoAPI.getLongitude());
        weatherIcon.setIcon(weatherConditionPictures.get(weatherAPI.getWeatherCode()));
        System.out.println(weatherAPI.getTemperature());
        System.out.println(weatherAPI.getHumidity());
        System.out.println(weatherAPI.getWindSpeed());

        // BERNHARD
        displayCity(currentCity);
    }
}
