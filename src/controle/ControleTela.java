package controle;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;

import javafx.stage.Stage;
import controle.ControlePonto;
import controle.ControleReta;
import grafica.Definicao;
import controle.ControleCirculo;

public class ControleTela {
	GraphicsContext gc;
	
	Definicao definicao = new Definicao();
	ControlePonto controlePonto = new ControlePonto();
	ControleReta controleReta = new ControleReta(definicao);
	ControleCirculo controleCirculo = new ControleCirculo(definicao);
	ControleRetangulo controleRetangulo = new ControleRetangulo(definicao, controleReta);
	
	public void mostrarCoordenadasMouse(Canvas canvas, Stage palco) {
		canvas.setOnMouseMoved(event -> {
			palco.setTitle("Coodenadas Cursor:"+" (" + (int)event.getX() + ", " + (int)event.getY() + ")");
		});
	}
	
	public Button limparTela(GraphicsContext gc, Button btnLimparTela) { //parametros: x, y, w, h
		btnLimparTela.setOnMousePressed(e-> {
			gc.clearRect(0, 0, 1200, 600);
		});
		return btnLimparTela;
	}
	
	public Button clicarPonto(Canvas canvas, GraphicsContext gc, Button btnPonto){
		btnPonto.setOnMousePressed(e-> {
			controlePonto.clicarPonto(canvas, gc);
		});
		return btnPonto;
	}
	
	public Button clicarReta(Canvas canvas, GraphicsContext gc, Button btnReta){
		btnReta.setOnMousePressed(e-> {
			controleReta.clicarReta(canvas, gc);
		});
		return btnReta;
	}
	
	public Button clicarCirculo(Canvas canvas, GraphicsContext gc, Button btnCirculo){
		btnCirculo.setOnMousePressed(e-> {
			controleCirculo.clicarCirculo(canvas, gc);
		});
		return btnCirculo;
	}
	
	public Button clicarRetangulo(Canvas canvas, GraphicsContext gc, Button btnRetangulo){
		btnRetangulo.setOnMousePressed(e-> {
			controleRetangulo.clicarRetangulo(canvas, gc);
		});
		return btnRetangulo;
	}
	
	public Definicao getDefinicao(){
		return this.definicao;
	}
}
