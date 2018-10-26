package App;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import io.Conversor;
import io.FileReader;
import io.FileWriter;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Tela {
	int indicePonto=1, hpixel=600, vpixel= 600;
	GraphicsContext gc;
	Button btnPonto = new Button(" . ");
	Button btnReta = new Button(" / ");
	Button btnCirculo = new Button(" O ");
	Button btnRetangulo = new Button(" [] ");
	Button btnPoligono = new Button("Poligono");
	Button btnLinhaPoligonal = new Button("L. Poligonal");
	Button btnSelecionarItem = new Button("Selecionar");
	Button btnSave = new Button("Salvar");
	Button btnCarregar = new Button("Carregar");

	HBox hBoxBotoes = new HBox();
	VBox vBoxPainel = new VBox();
	
	Canvas canvas = new Canvas(hpixel, vpixel);
	ControleTela controleTela = new ControleTela();
	
	public Tela(Stage palco){
		// define largura e altura da janela
		palco.setWidth(hpixel);
		palco.setHeight(vpixel);

		// Painel para os componentes
		BorderPane pane = new BorderPane();
			
		gc = canvas.getGraphicsContext2D();
				
		// Eventos de mouse
		mostrarCoordenadasMouse(canvas, palco);	
				
		//montagem do painel
		Group root = new Group();
		MenuBar menuBar = montarPainel(palco);
		root.getChildren().add(menuBar);
				
		pane.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		pane.setCenter(canvas); // posiciona o componente de desenho
				
		vBoxPainel.getChildren().addAll(menuBar, adicionaBotoes(btnReta , btnCirculo, btnRetangulo, btnPoligono, btnLinhaPoligonal, btnSelecionarItem) );
		pane.setTop(vBoxPainel);
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
	
	private void limparTela() {
		controleTela.setFormas(new ArrayList<>());
	}
	
	public HBox adicionaBotoes(Button btnReta , Button btnCirculo, Button btnRetangulo, Button btnPoligono, Button btnLinhaPoligonal, Button btnSelecionarItem){
		HBox hBoxBotoes = new HBox();
		btnReta = controleTela.movimentosMouse(canvas, gc, btnReta, ModosDeTrabalho.RETAS);
		btnCirculo = controleTela.movimentosMouse(canvas, gc, btnCirculo, ModosDeTrabalho.CIRCULOS);
		btnRetangulo =  controleTela.movimentosMouse(canvas, gc, btnRetangulo, ModosDeTrabalho.RETANGULOS);
		btnPoligono = controleTela.movimentosMouse(canvas, gc, btnPoligono, ModosDeTrabalho.POLIGONO);
		btnLinhaPoligonal = controleTela.movimentosMouse(canvas, gc, btnLinhaPoligonal, ModosDeTrabalho.LINHA_POLIGONAL);
		btnSelecionarItem = controleTela.modoSelecionar(canvas, btnSelecionarItem, ModosDeTrabalho.SELECIONAR);
		hBoxBotoes.getChildren().addAll(btnReta, btnCirculo, btnRetangulo, btnPoligono, btnLinhaPoligonal, btnSelecionarItem);
		return hBoxBotoes;
	}
	
	public Button clicarSalvar(Button btn){
		btn.setOnAction(e->{
			saveFile();
		});
		
		return btn;
	}
	
	public Button clicarCarregar(Button btn){
		btn.setOnAction(e->{});
			loadFile();
		return btn;
	}
	
	private void loadFile() {
		JFileChooser openFile = new JFileChooser();
		openFile.setFileFilter(new FileNameExtensionFilter("xml file","xml"));
        openFile.showOpenDialog(null);
        
		FileReader fr = new FileReader();
		File f = openFile.getSelectedFile();
		
		if(f != null) {
			boolean fullLoad = fr.readFile(f.getPath());
			
			if(fullLoad) {
				System.out.println("Só imprimo depois de ler tudo");
				controleTela.setFormas(fr.getFormas());
				//repaint();
				
			} else {
				System.out.println("Arquivo não pode ser lido");
			}
		}
	}
	
	private void saveFile() {
		JFileChooser result = new JFileChooser();
		result.setFileFilter(new FileNameExtensionFilter("xml file","xml"));
		result.showSaveDialog(null);
            
        File targetFile = result.getSelectedFile();
        if(targetFile != null) {
		    if (! Conversor.fileExt(targetFile.getName()).equalsIgnoreCase("xml")) {
		    		targetFile = new File(targetFile.toString() + ".xml");
		    }
		    
		    try {
				targetFile.createNewFile();
				FileWriter.write(targetFile, controleTela.getFormas());
			} catch (IOException e) {
				e.printStackTrace();
			}    
        }
	}
	
	public void mostrarCoordenadasMouse(Canvas canvas, Stage palco) {
		canvas.setOnMouseMoved(event -> {
			palco.setTitle("Coodenadas Cursor:"+" (" + (int)event.getX() + ", " + (int)event.getY() + ")");
		});
	}
	
	//menu
	public MenuBar montarPainel(Stage palco){
		MenuBar menuBar = new MenuBar();   
		Menu menuArq = new Menu("Arquivo");
		MenuItem novo = new MenuItem("Novo");
		novoMenu(novo, gc);
		menuArq.getItems().add(novo);

		MenuItem salvar = new MenuItem("Salvar");
		salvarMenu(salvar);
		menuArq.getItems().add(salvar);
		
		MenuItem carregar = new MenuItem("Carregar");
		salvarMenu(carregar);
		menuArq.getItems().add(carregar);
		
		menuBar.getMenus().add(menuArq);
				
		Menu menuOpcoes = new Menu("Op��es");
		menuOpcoes.getItems().add(new MenuItem("Sobre"));
		//menuOpcoes.getItems().add(new SeparatorMenuItem());
		MenuItem sair = new MenuItem("Sair");
		sairMenu(sair);
		menuOpcoes.getItems().add(sair);
		menuBar.getMenus().add(menuOpcoes);
				
		menuBar.prefWidthProperty().bind(palco.widthProperty());
		return menuBar;
	}
	
		public void sairMenu(MenuItem item){
			item.setOnAction(new EventHandler<ActionEvent>() {
			    public void handle(ActionEvent t) {
			        System.exit(0);
			    }
			});
		}
		
		public void salvarMenu(MenuItem item){
			item.setOnAction(new EventHandler<ActionEvent>() {
			    public void handle(ActionEvent t) {
			    	saveFile();
			    }
			});
		}
		
		public void carregarMenu(MenuItem item){
			item.setOnAction(new EventHandler<ActionEvent>() {
			    public void handle(ActionEvent t) {
			    	//loadFile();
			    }
			});
		}
		
		public void novoMenu(MenuItem item, GraphicsContext gc){
			item.setOnAction(new EventHandler<ActionEvent>() {
			    public void handle(ActionEvent t) {
			    	Alert alert = new Alert(AlertType.CONFIRMATION);
			    	alert.setTitle("Apagar Tudo");
			    	//alert.setHeaderText("");
			    	alert.setContentText("Deseja apagar tudo?");
			    	
			    	ButtonType btnSim = new ButtonType("Sim");
			    	ButtonType btnNao = new ButtonType("N�o");
			    	alert.getButtonTypes().setAll(btnSim, btnNao);

			    	Optional<ButtonType> result = alert.showAndWait();
			    	if (result.get() == btnSim){
			    	    // ... user chose OK
			    		limparTela();
			    		gc.clearRect(0, 0, vpixel, vpixel);
			    	} else {
			    	    // ... user chose CANCEL or closed the dialog
			    		
			    	} 
			    }
			});
		}
}
