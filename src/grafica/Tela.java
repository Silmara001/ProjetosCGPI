package grafica;

import controle.ControleTela;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Tela {
	int indicePonto=1;
	GraphicsContext gc;
	Button btnPonto = new Button(" . ");
	Button btnReta = new Button(" / ");
	Button btnCirculo = new Button(" O ");
	Button btnRetangulo = new Button(" [] ");
	Button btnLimparTela = new Button("Limpa Tela");
	Button btnSelecionarItem = new Button(" Selecionar Forma ");
	HBox hBoxBotoes = new HBox();
	
	ControleTela controleTela = new ControleTela();
	Definicao definicao = controleTela.getDefinicao();
	Canvas canvas = new Canvas(definicao.getHPixels(), definicao.getVPixels());
	
	public Tela(Stage palco){
		// define titulo da janela
		palco.setTitle("Testa Mouse");

		// define largura e altura da janela
		palco.setWidth(definicao.getHPixels());
		palco.setHeight(definicao.getVPixels());

		// Painel para os componentes
		BorderPane pane = new BorderPane();

		// componente para desenho
		
		// componente para desenhar graficos
				
		gc = canvas.getGraphicsContext2D();
		
		// Eventos de mouse
		controleTela.mostrarCoordenadasMouse(canvas, palco);	
		
		pane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		pane.setCenter(canvas); // posiciona o componente de desenho
		pane.setTop(adicionaBotoes(btnPonto, btnReta , btnCirculo, btnRetangulo, btnLimparTela, btnSelecionarItem));
		
		// cria e insere cena
		Scene scene = new Scene(pane);
		palco.setScene(scene);
		palco.show();	
	}
	
	public int getIndice(){
		return this.indicePonto;
	}
	
	public GraphicsContext getGc(){
		return this.gc;
	}
	
	public HBox adicionaBotoes(Button btnPonto, Button btnReta , Button btnCirculo, Button btnRetangulo, Button btnLimparTela, Button btnSelecionarItem){
		HBox hBoxBotoes = new HBox();
		btnPonto = controleTela.clicarPonto(canvas,gc, btnPonto);
		btnReta = controleTela.clicarReta(canvas,gc, btnReta);
		btnCirculo = controleTela.clicarCirculo(canvas,gc, btnCirculo);
		btnRetangulo =  controleTela.clicarRetangulo(canvas, gc, btnRetangulo);
		btnLimparTela = controleTela.limparTela(gc, btnLimparTela );
		btnSelecionarItem = controleTela.selecionarForma(canvas, gc, btnSelecionarItem );
		
		hBoxBotoes.getChildren().addAll(btnPonto, btnReta, btnCirculo, btnRetangulo, btnLimparTela, btnSelecionarItem );
		return hBoxBotoes;
	}
}
