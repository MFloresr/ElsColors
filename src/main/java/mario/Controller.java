package mario;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.sql.*;

/**
 * Created by Vipi on 15/03/2016.
 */
public class Controller {
    @FXML
    private TextField textCastella;
    @FXML
    private TextField textFrances;
    @FXML
    private  TextField textAngles;
    @FXML
    private TextField textBuscar;
    @FXML
    private Button btnBuscar;
    @FXML
    private ChoiceBox choisebox;
    private Connection con = null;
    private ObservableList<String> idiomas=FXCollections.observableArrayList();


    public void cargarChoise(){
        choisebox.getItems().addAll("Anglès","Castellà","Català","Francés");
    }

    @FXML
    public void buscarColores(Event event){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://192.168.4.1/traductor","foot","ball");

            PreparedStatement peticion  =con.prepareStatement("SELECT DISTINCT nom, angles,castella,frances  FROM colors WHERE nom=? ");
            peticion.setString(1, textBuscar.getText());

            //PreparedStatement peticion  =con.prepareStatement("SELECT DISTINCT nom, angles,castella,frances  FROM colors ");
            ResultSet resultat = peticion.executeQuery();

            while (resultat.next()){
                System.out.println(resultat.getString("nom")+" "+resultat.getString("angles")+" "+resultat.getString("frances")+" "+ resultat.getString("castella"));
                textAngles.setText(resultat.getString("angles"));
                textCastella.setText(resultat.getString("castella"));
                textFrances.setText(resultat.getString("frances"));
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
