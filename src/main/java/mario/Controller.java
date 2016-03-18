package mario;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

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
    private Label labcast;
    @FXML
    private Label labang;
    @FXML
    private Label labfranc;
    @FXML
    private ChoiceBox choisebox;
    @FXML
    private Rectangle rect;

    private Connection con = null;
    private String idioma;
    public ResultSet resultat;
    private Alert alert;


    @FXML
    public void initialize(){
        cargarChoise();
    }


    public void cargarChoise(){
        choisebox.getItems().addAll("Anglès","Castellà","Català","Francés");
        choisebox.getSelectionModel().select(2);
    }


    public void errorconnecion(){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error de conexion");
        alert.setContentText("No se ha podido conectar a la base de datos");
        alert.showAndWait();
    }

    public void errocolornoencontrado(){
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Error en la busqueda");
        alert.setContentText("El color que usted esta buscando no existe pruebe buscar otro color");
        alert.showAndWait();
    }


    public void pintaridiomas() throws SQLException {
       if(idioma.equals("angles")){
           labang.setText("Català");
           labcast.setText("Castellà");
           labfranc.setText("Francés");
           textAngles.setText(resultat.getString("nom"));
           textCastella.setText(resultat.getString("castella"));
           textFrances.setText(resultat.getString("frances"));
       }
       if(idioma.equals("nom")){
           labang.setText("Anglès");
           labcast.setText("Castellà");
           labfranc.setText("Francés");
           textAngles.setText(resultat.getString("angles"));
           textCastella.setText(resultat.getString("castella"));
           textFrances.setText(resultat.getString("frances"));
       }
       if(idioma.equals("frances")){
           labfranc.setText("Català");
           labang.setText("Anglès");
           labcast.setText("Castellà");
           textAngles.setText(resultat.getString("Angles"));
           textCastella.setText(resultat.getString("castella"));
           textFrances.setText(resultat.getString("nom"));
       }
       if(idioma.equals("castella")){
           labcast.setText("Català");
           labfranc.setText("Francés");
           labang.setText("Anglès");
           textAngles.setText(resultat.getString("Angles"));
           textCastella.setText(resultat.getString("nom"));
           textFrances.setText(resultat.getString("frances"));
       }

    }

    public void detectarIdioma(){
        if(choisebox.getSelectionModel().getSelectedIndex()==0){
            idioma = "angles";
        }if(choisebox.getSelectionModel().getSelectedIndex()==1){
            idioma = "castella";
        }if(choisebox.getSelectionModel().getSelectedIndex()==2){
            idioma = "nom";
        }if(choisebox.getSelectionModel().getSelectedIndex()==3){
            idioma = "frances";
        }
    }

    @FXML
    public void buscarColores(Event event){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //con = DriverManager.getConnection("jdbc:mysql://192.168.4.1/traductor","foot","ball");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cendrassos","mario","qcmmer2sa!");
            if(!con.isClosed()){
                detectarIdioma();
                PreparedStatement peticion  =con.prepareStatement("SELECT DISTINCT nom, angles, castella, frances  FROM colors WHERE "+idioma+"=? ;");
                peticion.setString(1, textBuscar.getText());

                resultat = peticion.executeQuery();
                if(resultat.isBeforeFirst()) {
                    while (resultat.next()) {
                        pintaridiomas();
                        rect.setFill(Paint.valueOf(resultat.getString("angles")));
                    }
                }else{
                    errocolornoencontrado();
                }
            }else{
                errorconnecion();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
