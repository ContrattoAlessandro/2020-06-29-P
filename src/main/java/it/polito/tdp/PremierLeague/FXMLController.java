/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Arco;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnConnessioneMassima"
    private Button btnConnessioneMassima; // Value injected by FXMLLoader

    @FXML // fx:id="btnCollegamento"
    private Button btnCollegamento; // Value injected by FXMLLoader

    @FXML // fx:id="txtMinuti"
    private TextField txtMinuti; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMese"
    private ComboBox<String> cmbMese; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM1"
    private ComboBox<Match> cmbM1; // Value injected by FXMLLoader

    @FXML // fx:id="cmbM2"
    private ComboBox<Match> cmbM2; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doConnessioneMassima(ActionEvent event) {
    	for(Arco a: model.connessioneMassima()) {
    		txtResult.appendText(a + "\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	int minuti = 0;
    	try {
    		minuti = Integer.parseInt(txtMinuti.getText());
    	}catch(NumberFormatException e) {
    		txtResult.setText("Inserire un numero\n");
    		return;
    	}
    	String mese = cmbMese.getValue();
    	if(mese == null) {
    		txtResult.setText("Seleziona un mese\n");
    		return;
    	}
    	model.creaGrafo(minuti, Integer.parseInt(mese));
    	txtResult.setText("Il grafo ha " + model.getGrafo().vertexSet().size() + " vertici\n");
    	txtResult.appendText("Il grafo ha " + model.getGrafo().edgeSet().size() + " archi\n");
    	cmbM1.getItems().addAll(model.getGrafo().vertexSet());
    	cmbM2.getItems().addAll(model.getGrafo().vertexSet());
    }

    @FXML
    void doCollegamento(ActionEvent event) {
    	Match m1 = cmbM1.getValue();
    	Match m2 = cmbM2.getValue();
    	if(cmbM1 == null || cmbM2 == null) {
    		txtResult.setText("Seleziona una partita");
    		return;
    	}
    	model.collegamento(m1,m2);
    	txtResult.setText("Il peso migliore è: " + model.getnMigliore() + "\n");
    	for(Match m: model.collegamento(m1, m2)) {
    		txtResult.appendText(m + "\n");
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnConnessioneMassima != null : "fx:id=\"btnConnessioneMassima\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCollegamento != null : "fx:id=\"btnCollegamento\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMinuti != null : "fx:id=\"txtMinuti\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMese != null : "fx:id=\"cmbMese\" was not injected: check your FXML file 'Scene.fxml'.";        assert cmbM1 != null : "fx:id=\"cmbM1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbM2 != null : "fx:id=\"cmbM2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbMese.getItems().add("01");
    	cmbMese.getItems().add("02");
    	cmbMese.getItems().add("03");
    	cmbMese.getItems().add("04");
    	cmbMese.getItems().add("05");
    	cmbMese.getItems().add("06");
    	cmbMese.getItems().add("07");
    	cmbMese.getItems().add("08");
    	cmbMese.getItems().add("09");
    	cmbMese.getItems().add("10");
    	cmbMese.getItems().add("11");
    	cmbMese.getItems().add("12");
    }
    
    
}
