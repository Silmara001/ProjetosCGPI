package app;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Escreva a descrição da classe Aplicacao aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class App extends Application {
	public static void main(String args[]) {
		launch(args);
	}
	
	  @Override
	    public void start(Stage palco) throws Exception {
	        new Tela(palco);
	}
}
