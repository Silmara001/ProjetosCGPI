package app;

import javafx.application.Application;
import javafx.stage.Stage;
import grafica.Tela;

public class Aplicacao extends Application{
     public static void main (String args[]) {
        launch(args);
    }

    @Override
    public void start(Stage palco) throws Exception {
        new Tela(palco);
    }
}
