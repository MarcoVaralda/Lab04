package it.polito.tdp.lab04;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;
	String codiceCorso=null;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Corso> comboBox;

    @FXML
    private TextField matricola;

    @FXML
    private TextField nome;

    @FXML
    private TextField cognome;

    @FXML
    private TextArea Result;
    
    @FXML
    private Button btnIscrivi;

    @FXML
    void HandleCombo(ActionEvent event) {
    	if(comboBox.getValue().getCodice() != "")
        	codiceCorso = comboBox.getValue().getCodice();
    	else 
    		codiceCorso="";
    }

    @FXML
    void doCerca(ActionEvent event) {
    	String stringaMatricola = this.matricola.getText();
    	int matricolaStudente;
    	try {
    		matricolaStudente = Integer.parseInt(stringaMatricola);
    	}
    	catch(NumberFormatException nfe) {
    		this.Result.setText("ERRORE! Inserire una matricola valida");
    		return;
    	}
    	
    	if(codiceCorso==null) {
    		this.Result.setText("Devi selezionare un corso!");
    		return;
    	}
    	
    	if(codiceCorso=="") {
    		this.Result.setText("Devi selezionare un solo corso!");
    		return;
    	}
    	
    	if(model.cercaIscrizione(matricolaStudente, codiceCorso)==true)
    		this.Result.setText("Lo studente è già iscritto al corso!");
    	else
    		this.Result.setText("Lo studente non è iscritto al corso!");
    	
    	
    }
    
    @FXML
    void handleIscrivi(ActionEvent event) {
    	
    	String stringaMatricola = this.matricola.getText();
    	int matricolaStudente;
    	try {
    		matricolaStudente = Integer.parseInt(stringaMatricola);
    	}
    	catch(NumberFormatException nfe) {
    		this.Result.setText("ERRORE! Inserire una matricola valida");
    		return;
    	}
    	
    	if(codiceCorso==null) {
    		this.Result.setText("Devi selezionare un corso!");
    		return;
    	}
    	
    	if(codiceCorso=="") {
    		this.Result.setText("Devi selezionare un solo corso!");
    		return;
    	}
    	
    	if(model.inscriviStudenteACorso(matricolaStudente, codiceCorso)==true)
    		this.Result.setText("Studente inserito correttamente!");
    	else
    		this.Result.setText("Studente già iscritto al corso!");
    	
    }

    @FXML
    void doCercaCorsi(ActionEvent event) {
    	String stringaMatricola = this.matricola.getText();
    	int matricolaStudente=1;
    	try {
    		matricolaStudente = Integer.parseInt(stringaMatricola);
    	}
    	catch(NumberFormatException nfe) {
    		this.Result.setText("ERRORE! Inserire una matricola valida");
    		return;
    	}
    	
    	List<Corso> elenco = model.getIscrizioniStudente(matricolaStudente);
    	
    	if(elenco.isEmpty()) {
    		this.Result.setText("Studente non ancora iscritto ad alcun corso!");
    		return;
    	}
    	
        StringBuilder sb = new StringBuilder();
    	
    	for(Corso c : elenco) {
    		this.Result.appendText(c.stampa() + "\n");
    		
    		sb.append(String.format("%-50s ", c.getCodice()));
    		sb.append(String.format("%-11d ", c.getCrediti()));
    		sb.append(String.format("%-50s ", c.getNome()));
    		sb.append(String.format("%-11d\n", c.getPeriodo()));
        }
    }

    @FXML
    void doCercaIscritti(ActionEvent event) {
    	if(codiceCorso==null) {
    		this.Result.setText("Devi selezionare un corso!");
    		return;
    	}
    	
    	List<Studente> elenco = new LinkedList<Studente>();
    	
    	if(codiceCorso=="") {
    		elenco.addAll(model.getStudentiIscritti());
    	}
    	else {
    		elenco.addAll(model.getStudentiIscrittiAlCorso(codiceCorso));
    	}
    	
    	if(elenco.isEmpty()) {
    		this.Result.setText("Nessuno studente trovato!");
    		return;
    	}
    	
    	StringBuilder sb = new StringBuilder();
    	
    	for(Studente s : elenco) {
    		this.Result.appendText(s.toString() + "\n");
    		
    		sb.append(String.format("%-11d ", s.getMatricola()));
    		sb.append(String.format("%-50s ", s.getCognome()));
    		sb.append(String.format("%-50s ", s.getNome()));
    		sb.append(String.format("%-50s\n", s.getCds()));
        }
    }

    @FXML
    void doReset(ActionEvent event) {   	
    	this.Result.clear();
    	this.cognome.clear();
    	this.nome.clear();
    	this.matricola.clear();
    }

    @FXML
    void doSpunta(ActionEvent event) {
    	String stringaMatricola = this.matricola.getText();
    	int matricolaStudente=1;
    	try {
    		matricolaStudente = Integer.parseInt(stringaMatricola);
    	}
    	catch(NumberFormatException nfe) {
    		this.Result.setText("ERRORE! Inserire una matricola valida");
    		return;
    	}
    	
    	Studente s = model.getStudente(matricolaStudente);
    	this.nome.setText(s.getNome());
    	this.cognome.setText(s.getCognome());
    }

    @FXML
    void initialize() {
        assert comboBox != null : "fx:id=\"comboBox\" was not injected: check your FXML file 'Scene.fxml'.";
        assert matricola != null : "fx:id=\"matricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert nome != null : "fx:id=\"nome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cognome != null : "fx:id=\"cognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert Result != null : "fx:id=\"Result\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model=model;
    	this.comboBox.getItems().addAll(model.getTuttiICorsi());
    	this.Result.setStyle("-fx-font-family: monospace");
    }
}