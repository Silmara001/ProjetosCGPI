package controle;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import controle.ControlePonto;
import controle.ControleReta;
import controle.ControleCirculo;
import grafica.RetaGr;

public class ControleTela {
	GraphicsContext gc;
	
	ControlePonto controlePonto = new ControlePonto();
	ControleReta controleReta = new ControleReta();
	ControleCirculo controleCirculo = new ControleCirculo();
	RetaGr retaGr = new RetaGr();
	
	public void mostrarCoordenadasMouse(Canvas canvas, Stage palco) {
		canvas.setOnMouseMoved(event -> {
			palco.setTitle("Coodenadas Cursor:"+" (" + (int)event.getX() + ", " + (int)event.getY() + ")");
		});
	}
	
	public Button limparTela(GraphicsContext gc, Button btnLimparTela) { //parametros: x, y, w, h
		btnLimparTela.setOnMousePressed(e-> {
			gc.clearRect(0, 0, 1200, 600);
			resetar();
		});
		return btnLimparTela;
	}
	
	public Button clicarPonto(Canvas canvas, GraphicsContext gc, Button btnPonto){
		btnPonto.setOnMousePressed(e-> {
			controlePonto.clicarPonto(canvas, gc);
			resetar();
		});
		return btnPonto;
	}
	
	public Button clicarReta(Canvas canvas, GraphicsContext gc, Button btnReta){
		btnReta.setOnMousePressed(e-> {
			controleReta.clicarReta(canvas, gc);
			resetar();
		});
		return btnReta;
	}
	
	public Button clicarCirculo(Canvas canvas, GraphicsContext gc, Button btnCirculo){
		btnCirculo.setOnMousePressed(e-> {
			controleCirculo.clicarCirculo(canvas, gc);
			resetar();
		});
		return btnCirculo;
	}
	
	public void resetar() {
		controlePonto.setIndicePonto(1);
		controleReta.setIndicePonto(1);
		controleCirculo.setContClique(0);
		controleReta.setContClique(0);
	}
}
