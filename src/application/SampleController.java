package application;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import bean.Fermata;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class SampleController {
	
	private Model m = new Model();
	
	public void setModel(Model m){
		this.m=m;
	}

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Fermata> comboStart;

    @FXML
    private ComboBox<Fermata> comboEnd;

    @FXML
    private Button btnPercorso;

    @FXML
    private TextArea txtResult;

    @FXML
    void doPercorso(ActionEvent event) {   //vedi fermate intermedie
    	txtResult.clear();
    	Fermata start = comboStart.getValue();
    	Fermata end = comboEnd.getValue();
    	if(start ==null || end ==null){
    		txtResult.appendText("Seleziona due fermate\n");
    		return;
    	}
    	if(start.equals(end)){
    		txtResult.appendText("Seleziona due fermate diverse!\n");
    		return;
    	}

    	m.buildGraph();
    	List<Fermata> camminoMinimo= m.getCamminoMinimo(start, end);
    	if(camminoMinimo.isEmpty()){
    		txtResult.appendText("Le due fermate non sono collegate!\n");
    		return;
    	}
    	for(Fermata f : camminoMinimo){
    		txtResult.appendText(f+" \n");
    	}
    }

    @FXML
    void initialize() {
        assert comboStart != null : "fx:id=\"comboStart\" was not injected: check your FXML file 'Sample.fxml'.";
        assert comboEnd != null : "fx:id=\"comboEnd\" was not injected: check your FXML file 'Sample.fxml'.";
        assert btnPercorso != null : "fx:id=\"btnPercorso\" was not injected: check your FXML file 'Sample.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Sample.fxml'.";

        comboStart.getItems().addAll(m.getAllFermate());
        comboEnd.getItems().addAll(m.getAllFermate());
    }
}
