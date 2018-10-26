package App;
import java.util.ArrayList;
import java.util.List;

import formas.ClippingAlgoritmo;
import formas.ControleLinha;
import formas.ControleReta;
import formas.ControleRetangulo;
import formas.Forma;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
	
	ClippingAlgoritmo clippingArea;

	Forma formaSelecionada;
	Color corFormaSelecinada;
	
	private Image offScreenImage = null;
	private GraphicsContext offScreenGraphics = null;

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
		} else if (tipo != ModosDeTrabalho.ROTACIONAR) {
			deselecionarForma();
		} else {
			//
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
//		//TODO checar este código
//		else if (e.getButton() == 1 && tipo == ModosDeTrabalho.ROTACIONAR){
//
//			try{
//				String anguloString = JOptionPane.showInputDialog("Insira quantos graus deseja rotacionar:");
//				Double angulo = Double.parseDouble(anguloString);
//				rotacionarFormaSelecionada(new Ponto(e.getX(), e.getY()), angulo);
//			} catch (Exception ex) {
//				JOptionPane.showMessageDialog(null, "Valor Inválido");
//			}
//
//			deselecionarForma();
//
//
//		} 
		
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
			desenharPrimitivos(g);
		});
	}

	public void mouseReleased(Canvas canvas, GraphicsContext g) {
		canvas.setOnMouseReleased(e->{
			System.out.println("entra na f: mouseReleased");
			if (tipo == ModosDeTrabalho.RETAS && p1 != null && p2 != null) {
				formas.add(new ControleReta(p1, p2, currentColor));
			} else if (tipo == ModosDeTrabalho.CIRCULOS && p1 != null && p2 != null) {
				formas.add(new Circulo((int)p1.getX(), (int)p1.getY(), (int)p2.calcularDistancia(p1), currentColor));
			} else if (tipo == ModosDeTrabalho.RETANGULOS && p1 != null && p2 != null) {
				formas.add(new ControleRetangulo(p1, p2, currentColor));
			}else if (tipo == ModosDeTrabalho.CLIPP && p1 != null && p2 != null){
				clipArea(new ControleRetangulo(p1, p2, currentColor));
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
		    f.desenhar(g);
        }

		if(lp != null) {
			lp.desenhar(g);
		}
		//g.fillRect(x, y, w, h);
		
		//****setBackground(java.awt.Color.white);
	}

	private void deselecionarForma() {
	    if(formaSelecionada != null && corFormaSelecinada != null) {
            formaSelecionada.set_cor(corFormaSelecinada);
            formaSelecionada = null;
            corFormaSelecinada = null;
          //****repaint();
        }
    }
	
	public Button modoSelecionar(Canvas canvas, Button btn, ModosDeTrabalho tipo){
		btn.setOnAction(e->{
			setTipo(tipo);
			canvas.setOnMousePressed(event -> {
				System.out.println("Entra na f: modoSelecionar");
				if (event.getButton() == MouseButton.PRIMARY) {
					int x = (int)event.getX();
					int y = (int)event.getY();
					selecionarForma(x, y);
				}
			});
		});
		return btn;
	}
	
	public void selecionarForma(int x, int y) {	
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
 				//****repaint();
 				break;
 			}
 		}
 		//***appGUI.buttonStatus(encontrouForma);
 	 	System.out.println(encontrouForma);
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

	private void clipArea(ControleRetangulo r) {

		ClippingAlgoritmo clipArea = new ClippingAlgoritmo(r);
		setClippingArea(clipArea);
		
		int width = (int)( r.getVertice2().getX() - r.getVertice1().getX());
		int height=(int)( r.getVertice2().getY() - r.getVertice1().getY());;
		//setSize(width, height);
		
	}

	private void setClippingArea(ClippingAlgoritmo clipArea) {
		this.clippingArea = clipArea;

		
	}

	public void apagarFormaSelecionada() {
		if(formaSelecionada != null && corFormaSelecinada != null) {
			formas.remove(formaSelecionada);
			formaSelecionada = null;
			corFormaSelecinada = null;
			//***repaint();
		}
	}
}
