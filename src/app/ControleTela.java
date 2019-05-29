package app;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import formas.ClippingAlgoritmo;
import formas.ControleLinha;
import formas.ControleReta;
import formas.ControleRetangulo;
import formas.Forma;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import matematica.Circulo;
import matematica.Ponto;

public class ControleTela {
	Label msg;
	ModosDeTrabalho tipo;
	Ponto p1;
	Ponto p2, oldP2;
	List<Forma> formas = new ArrayList<>();
	Color currentColor = Color.BLACK;
	ControleLinha lp = null;
	Double angle;

	ClippingAlgoritmo clippingArea;

	Forma formaSelecionada;
	Color corFormaSelecinada;
	private OnSelectedForm onSelectedForm;

	//private Image offScreenImage = null;
	//private GraphicsContext offScreenGraphics = null;

	public void setCor(Color cor) {
		this.currentColor = cor;

		if(lp != null) {
			lp.set_cor(cor);
		}		
	}

	public void setFormas(List<Forma> formas) {
		this.formas = formas;
	}

	public List<Forma> getFormas() {
		return formas;
	}

	public void setTipo(ModosDeTrabalho tipo) {
		this.tipo = tipo;
		p1 = null;
		p2 = null;

		if(this.tipo == ModosDeTrabalho.LINHA_POLIGONAL || this.tipo == ModosDeTrabalho.POLIGONO) {
			lp = new ControleLinha(new ArrayList<>());
		} else if (tipo == ModosDeTrabalho.ROTACIONAR || tipo == ModosDeTrabalho.ESCALAR || tipo == ModosDeTrabalho.TRANSLADAR) {
			return;
		} else {
			//
			deselecionarForma();
		}
	}

	public ModosDeTrabalho getTipo() {
		return tipo;
	}

	public void mousePressed(Canvas canvas, GraphicsContext g) {
		canvas.setOnMousePressed(e -> {
			if(e.getButton() == MouseButton.PRIMARY && ( tipo == ModosDeTrabalho.LINHA_POLIGONAL ||  tipo == ModosDeTrabalho.POLIGONO)) {
				Ponto po = new Ponto((double)e.getX(), (double)e.getY());
				lp.addPonto(po);

				p1 = new Ponto(e.getX(), e.getY());
				//****repaint();

			} else if (e.getButton() == MouseButton.PRIMARY && tipo != ModosDeTrabalho.SELECIONAR && tipo != ModosDeTrabalho.ROTACIONAR) {
				p1 = new Ponto(e.getX(), e.getY());
				p2 = null;
			} 

			else if (e.getButton() == MouseButton.PRIMARY && tipo == ModosDeTrabalho.ROTACIONAR){

				//				try{
				//					String anguloString = JOptionPane.showInputDialog("Insira quantos graus deseja rotacionar:");
				//					Double angulo = Double.parseDouble(anguloString);
				//					rotacionarFormaSelecionada(new Ponto(e.getX(), e.getY()), angulo);
				//				} catch (Exception ex) {
				//					JOptionPane.showMessageDialog(null, "Valor Inválido");
				//				}

//				deselecionarForma();


			} 

			else if (e.getButton() == MouseButton.SECONDARY && tipo == ModosDeTrabalho.LINHA_POLIGONAL) {
				p1 = null;
				p2 = null;
				formas.add(lp);
				lp = new ControleLinha(new ArrayList<>());
				//****repaint();
			} else if (e.getButton() == MouseButton.SECONDARY && tipo == ModosDeTrabalho.POLIGONO) {
				p1 = null;
				p2 = null;
				lp.setPoligono_fechado(true);
				formas.add(lp);
				lp = new ControleLinha(new ArrayList<>());
				//****repaint();
			} else if (e.getButton() == MouseButton.PRIMARY && tipo == ModosDeTrabalho.SELECIONAR) {
				selecionarForma((int)e.getX(), (int)e.getY());
			}

			System.out.println("entra na f: mousePressed");
			desenharPrimitivos(g);
		});
	}

	public void mouseReleased(Canvas canvas, GraphicsContext g) {
		canvas.setOnMouseReleased(e->{

			limparTela(g);
			System.out.println("entra na f: mouseReleased");
			if (tipo == ModosDeTrabalho.RETAS && p1 != null && p2 != null) {
				formas.add(new ControleReta(p1, p2, currentColor));
			} else if (tipo == ModosDeTrabalho.CIRCULOS && p1 != null && p2 != null) {
				formas.add(new Circulo((int)p1.getX(), (int)p1.getY(), (int)p2.calcularDistancia(p1), currentColor));
			} else if (tipo == ModosDeTrabalho.RETANGULOS && p1 != null && p2 != null) {
				formas.add(new ControleRetangulo(p1, p2, currentColor));
			}else if (tipo == ModosDeTrabalho.CLIPP && p1 != null && p2 != null){
				clipArea(new ControleRetangulo(p1, p2, currentColor), g);
			}

			else if (e.getButton() == MouseButton.PRIMARY && tipo == ModosDeTrabalho.TRANSLADAR) {
				Ponto ref = new Ponto(e.getX(), e.getY());
				transladarFormaSelecionada(g, ref);

			}
			else if(e.getButton() == MouseButton.PRIMARY && tipo == ModosDeTrabalho.ESCALAR) {
				Ponto ref = new Ponto(e.getX(), e.getY());
				criarTextInputDialogEscalar(ref);
			}
			else if(e.getButton() == MouseButton.PRIMARY && tipo == ModosDeTrabalho.ROTACIONAR) {
				Ponto ref = new Ponto(e.getX(), e.getY());
				criarTextInputDialogRotacionar(ref);

			}

			if(tipo != ModosDeTrabalho.LINHA_POLIGONAL && tipo != ModosDeTrabalho.POLIGONO) {
				p1 = null;
				p2 = null;
			}
			desenharPrimitivos(g);
		});
	}



	public void mouseMoved(Canvas canvas, GraphicsContext g) {
		canvas.setOnMouseMoved(e->{ 
			System.out.println("entra na f: mouseMoved");
			if(tipo == ModosDeTrabalho.LINHA_POLIGONAL || tipo == ModosDeTrabalho.POLIGONO) {
				if(p1 != null) {
					oldP2 = p2;
					p2 = new Ponto(e.getX(), e.getY());
					//****repaint();
					desenharPrimitivos(g);
				}
			}
		});
	}

	public void mouseClicked(Canvas canvas) {}

	public void mouseEntered(Canvas canvas) {}

	public void mouseExited(Canvas canvas) {}

	public void mouseDragged(Canvas canvas, GraphicsContext g) {
		canvas.setOnMouseDragged(e->{
			if(tipo != ModosDeTrabalho.LINHA_POLIGONAL && tipo != ModosDeTrabalho.POLIGONO) {
				oldP2 = p2;
				p2 = new Ponto(e.getX(), e.getY());
				//**repaint();
				desenharPrimitivos(g);
			}
		});
	}

	public Button movimentosMouse(Canvas canvas, GraphicsContext g,  Button btn, ModosDeTrabalho tipo){
		btn.setOnAction(e->{
			setTipo(tipo);
			mousePressed(canvas, g);
			mouseReleased(canvas, g);
			mousePressed(canvas, g);
			mouseDragged(canvas, g);
			System.out.println("entra na f: movimentosMouse");
		});

		return btn;
	}

	public void repaintAll(GraphicsContext g) {
		for(Forma f : formas){
			if(f!=null)
			f.desenhar(g);
			else {
				formas.remove(f);	
			}
		}
		if(lp != null) {
			lp.desenhar(g);
		}
	}

	private void deselecionarForma() {
		if(formaSelecionada != null && corFormaSelecinada != null) {
			formaSelecionada.set_cor(corFormaSelecinada);
			formaSelecionada = null;
			corFormaSelecinada = null;
			onSelectedForm.setDisableTransformationMenu(true);
		}
	}

	public Button modoSelecionar(Canvas canvas, Button btn, ModosDeTrabalho tipo, OnSelectedForm onSelectedForm){
		btn.setOnAction(e->{
			setTipo(tipo);
			canvas.setOnMousePressed(event -> {
				System.out.println("Entra na f: modoSelecionar");
				if (event.getButton() == MouseButton.PRIMARY) {
					int x = (int)event.getX();
					int y = (int)event.getY();

					this.onSelectedForm = onSelectedForm;
					onSelectedForm.setDisableTransformationMenu(!selecionarForma(x, y));
				}
			});
		});
		return btn;
	}

	public boolean selecionarForma(int x, int y) {	
		if(tipo == ModosDeTrabalho.SELECIONAR) {
			deselecionarForma();

			System.out.println("Selected: FORM in ("+ x + ", " + y + ")");
			boolean encontrouForma = false;
			int margemDeErro = 10;

			for (Forma forma : formas) {
				if (forma.pontoNaForma(new Ponto(x, y), margemDeErro) ) {
					encontrouForma = true;
					formaSelecionada = forma;
					corFormaSelecinada = forma.get_cor();
					formaSelecionada.set_cor(Color.RED);


					break;
				}
			}

			System.out.println(encontrouForma);
			return encontrouForma;

		}

		return true;
	}

	public void desenharPrimitivos(GraphicsContext g) {

		if ((tipo == ModosDeTrabalho.RETAS || tipo == ModosDeTrabalho.LINHA_POLIGONAL || tipo == ModosDeTrabalho.POLIGONO) && (p1 != null) && (p2 != null)) {

			ControleReta rOld = null;
			if(oldP2 != null) {
				rOld = new ControleReta(p1, oldP2, Color.WHITE);
				rOld.desenhar(g);
			}

			ControleReta r = new ControleReta(p1, p2, currentColor);
			r.desenhar(g);
		}

		if ((tipo == ModosDeTrabalho.CIRCULOS) && (p1 != null) && (p2 != null)) {
			Circulo r = new Circulo(p1, p2.calcularDistancia(p1), currentColor);

			Circulo rOld = null;
			if(oldP2 != null) {
				rOld = new Circulo(p1, oldP2.calcularDistancia(p1), Color.WHITE);
				rOld.desenhar(g);
			}

			r.desenhar(g);
		}

		if ((tipo == ModosDeTrabalho.RETANGULOS ||tipo == ModosDeTrabalho.CLIPP ) && (p1 != null) && (p2 != null)) {
			ControleRetangulo r = new ControleRetangulo(p1, p2, currentColor);

			ControleRetangulo rOld = null;
			if(oldP2 != null) {
				rOld = new ControleRetangulo(p1, oldP2, Color.WHITE);
				rOld.desenhar(g);
			}

			try {
				r.desenhar(g);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		repaintAll(g);
	}

	private void clipArea(ControleRetangulo r, GraphicsContext g) {

		ClippingAlgoritmo clipArea = new ClippingAlgoritmo(r);
		setClippingArea(clipArea);

		Iterator<Forma> forma = formas.iterator();
		while (forma.hasNext()) {
			if(!clipArea.clipForma(forma.next())) {
				forma.remove();
			};
		}
		
		limparTela(g);
		repaintAll(g);
	}

	private void setClippingArea(ClippingAlgoritmo clipArea) {
		this.clippingArea = clipArea;
	}

	public void apagarFormaSelecionada() {
		if(formaSelecionada != null && corFormaSelecinada != null) {
			formas.remove(formaSelecionada);
			formaSelecionada = null;
			corFormaSelecinada = null;
		}
	}

	public Button deletarItemSelecionado(Canvas canvas, Button btnDeletar, ModosDeTrabalho selecionar, GraphicsContext g) {
		btnDeletar.setOnAction(e->{
			deletarForma(g);	
		});
		return btnDeletar;
	}

	private void deletarForma(GraphicsContext g) {
		System.out.println("deletar forma");
		if(formaSelecionada!=null) {
			apagarFormaSelecionada();
			limparTela(g);
			repaintAll(g);
		}
	}

	public void limparTela(GraphicsContext g) {
		g.clearRect(0, 0, 1000, 800);
	}

	//Transformações de forma
	public void escalarForma(GraphicsContext gc, ModosDeTrabalho escalar) {
		this.tipo = escalar;
		String txtTitle = "Escalar Forma";
		String txtHeader = "Selecione o ponto de referência deseja escalar a forma: ";
		criarAlerta(txtTitle, txtHeader);
	}

	public void escalarFormaSelecionada(GraphicsContext gc, double fatorEscala, Ponto ref) {
		if(formaSelecionada != null && corFormaSelecinada != null) {
			limparTela(gc);
			formaSelecionada.escalar(ref, fatorEscala);
			formaSelecionada.desenhar(gc);
			repaintAll(gc);
		}
	}


	public void criarTextInputDialogEscalar(Ponto ref) {

		String content = "Quantas vezes deseja escalar?";
		String title = "Escalar Forma";
		TextInputDialog dialog = new TextInputDialog("fator");
		dialog.setTitle(title);
		//						dialog.setHeaderText("Look, a Text Input Dialog");
		dialog.setContentText(content);

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()){
			System.out.println("Fator " + result.get());
			String fator = result.get();

			try{
				if(fator.matches("[0-9]{1,13}(\\.[0-9]*)?")){
					//todo
					formaSelecionada.escalar(ref, Double.parseDouble(fator));
				} else {
					throw new Exception("Valor fora do padrão Double");
				}
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Erro no formato");
				alert.setHeaderText(null);
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
	}

	private void criarTextInputDialogRotacionar(Ponto ref) {

		String content = "Insira quantos graus deseja rotacionar";
		String title = "Rotacionar Forma";
		TextInputDialog dialog = new TextInputDialog("graus");
		dialog.setTitle(title);
		//						dialog.setHeaderText("Look, a Text Input Dialog");
		dialog.setContentText(content);

		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()){
			System.out.println("graus " + result.get());
			String fator = result.get();

			try{
				formaSelecionada.rotacionar(ref,  Double.parseDouble(fator));

			} catch (Exception e) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Erro no formato");
				alert.setHeaderText(null);
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
		}
	}


	public void rotacionarForma(GraphicsContext gc2, ModosDeTrabalho rotacionar) {
		tipo = rotacionar;

		String txtTitle = "Rotacionar Forma";
		String txtHeader = "Selecione o ponto de referência deseja mover a forma: ";
		criarAlerta(txtTitle, txtHeader);
	}

	public void rotacionarFormaSelecionada(GraphicsContext gc, double angulo, Ponto ref) {
		if(formaSelecionada != null && corFormaSelecinada != null) {
			formaSelecionada.rotacionar(ref, angulo);
			formaSelecionada.desenhar(gc);
			limparTela(gc);
			repaintAll(gc);
		}
	}



	public void transladarForma(GraphicsContext gc, ModosDeTrabalho transladar) {
		tipo = transladar;
		String txtTitle = "Transladar Forma";
		String txtHeader = "Selecione o ponto que deseja mover a forma: ";
		criarAlerta(txtTitle,txtHeader);
	}

	public void transladarFormaSelecionada(GraphicsContext gc, Ponto p) {
		if(formaSelecionada != null && corFormaSelecinada != null) {
			limparTela(gc);
			formaSelecionada.transladar(p);
			formaSelecionada.desenhar(gc);
			repaintAll(gc);
		}
	}

	public void criarAlerta(String txtTitle, String txtheader) {	
		//	
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(txtTitle);
		alert.setHeaderText(txtheader);

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK){
			// ... user chose OK

		} else {
			if(onSelectedForm!=null) {
				deselecionarForma();
				
				onSelectedForm.setDisableTransformationMenu(true);
			} 
		}
	}

	public void ativarModoClip(ModosDeTrabalho clipp) {
		this.tipo = clipp;
		
	}

}
