import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SearchButton extends JButton implements ActionListener {

    ImageIcon searchPic;

    public SearchButton(){

        searchPic = new ImageIcon("C:\\Code\\Java\\WeatherApp\\src\\assets\\search.png");
        Image image = searchPic.getImage();
        Image newImage = image.getScaledInstance(25,25, Image.SCALE_SMOOTH);
        searchPic = new ImageIcon(newImage);

        this.setIcon(searchPic);

        this.setFocusable(false);
        this.setPreferredSize(new Dimension(40,40));


    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
