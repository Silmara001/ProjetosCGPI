package controle;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import controle.ControlePonto;
import controle.ControleReta;

import grafica.Definicao;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import controle.ControleCirculo;

public class ControleTela implements OnDrawForm{
	GraphicsContext gc;
	Color currentColor = Color.BLACK;
	
	Color corFormaSelecinada;
	
	OnDrawForm mListener = this;
	
	Forma formaSelecionada;
	List<Forma> formas = new ArrayList<>();
	
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
			adicionarForma(controleReta.clicarReta(canvas, gc, this));
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
			controleRetangulo.clicarRetangulo(canvas, gc, this);
		});
		return btnRetangulo;
	}
	
	

	public Definicao getDefinicao(){
		return this.definicao;
	}

	public Button selecionarForma(Canvas canvas, GraphicsContext gc2, Button btnSelecionarItem) {
	
		btnSelecionarItem.setOnMousePressed(e-> {
			selecionarForma(canvas);
		});
		return btnSelecionarItem;
	}

	public void selecionarForma(Canvas canvas) {
		deselecionarForma();
		boolean encontrouForma = false;
		int margemDeErro = 10;
		
		canvas.setOnMousePressed(event -> {
			if (event.getButton() == MouseButton.PRIMARY) {
				if (formaSelecionada == null) {
					int x = (int)event.getX();
					int y = (int)event.getY();
					System.out.println("Selected: FORM in ("+ x + ", " + y + ")");
			        System.out.println(encontrouForma);
				} 
			}
		});
	}

	private void deselecionarForma() {
	    if(formaSelecionada != null && corFormaSelecinada != null) {
            formaSelecionada.set_cor(corFormaSelecinada);
            formaSelecionada = null;
            corFormaSelecinada = null;
            gc.clearRect(0, 0, 1200, 600);
        }
    }

	@Override
	public void formaDesenhada(Forma forma) {
		// TODO Auto-generated method stub
		adicionarForma(forma);
		
	}
	
private void adicionarForma(Forma forma) {
		
		if(forma!=null) {
			formas.add(forma);
		}
		
	}
	
	
}
