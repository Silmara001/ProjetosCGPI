package App;
import formas.*;
import matematica.Circulo;
import matematica.Ponto;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class PainelDesenho extends JPanel implements MouseListener, MouseMotionListener {
	AppGUI appGUI;
	JLabel msg;
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
	private Graphics offScreenGraphics = null;

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

	public PainelDesenho(JLabel msg, ModosDeTrabalho tipo, AppGUI appGUI) {
		this.tipo = tipo;
		this.msg = msg;
		this.appGUI = appGUI;
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public void setTipo(ModosDeTrabalho tipo) {
		this.tipo = tipo;
		p1 = null;
		p2 = null;
		
		msg.setText("Primitivo: "+tipo.name().replace("_", " "));
		
		if(this.tipo == ModosDeTrabalho.LINHA_POLIGONAL || this.tipo == ModosDeTrabalho.POLIGONO) {
			lp = new ControleLinha(new ArrayList<>(), currentColor);
		} else if (tipo != ModosDeTrabalho.ROTACIONAR) {
			deselecionarForma();
		} else {
			JOptionPane.showMessageDialog(null, "Clique no ponto de referência da rotação");
		}

	}

	public ModosDeTrabalho getTipo() {
		return tipo;
	}


	public void paintComponent(Graphics g) {
		
		final Dimension d = getSize();

		if(offScreenImage == null) {
			offScreenImage = createImage(d.width, d.height);
			
		}
		offScreenGraphics = offScreenImage.getGraphics();
		offScreenGraphics.setColor(Color.WHITE);
		offScreenGraphics.fillRect(0, 0, d.width, d.height);
	
		desenharPrimitivos(offScreenGraphics);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	public void mousePressed(MouseEvent e) {
		if(e.getButton() == 1 && ( tipo == ModosDeTrabalho.LINHA_POLIGONAL ||  tipo == ModosDeTrabalho.POLIGONO)) {
			Ponto po = new Ponto((double)e.getX(), (double)e.getY());
			lp.addPonto(po);
			
			p1 = new Ponto(e.getX(), e.getY());
			repaint();

		} else if (e.getButton() == 1 && tipo != ModosDeTrabalho.SELECIONAR && tipo != ModosDeTrabalho.ROTACIONAR) {
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
		
		else if (e.getButton() == 3 && tipo == ModosDeTrabalho.LINHA_POLIGONAL) {
			p1 = null;
			p2 = null;
			formas.add(lp);
			lp = new ControleLinha(new ArrayList<>(), currentColor);
			repaint();
		} else if (e.getButton() == 3 && tipo == ModosDeTrabalho.POLIGONO) {
			p1 = null;
			p2 = null;
			lp.setPoligono_fechado(true);
            formas.add(lp);
			lp = new ControleLinha(new ArrayList<>(), currentColor);
			repaint();
		} else if (e.getButton() == 1 && tipo == ModosDeTrabalho.SELECIONAR) {
			selecionarForma(e.getX(), e.getY());
		}
	}

	public void mouseReleased(MouseEvent e) {

		if (tipo == ModosDeTrabalho.RETAS && p1 != null && p2 != null) {
            formas.add(new ControleReta(p1, p2, currentColor));
		} else if (tipo == ModosDeTrabalho.CIRCULOS && p1 != null && p2 != null) {
            formas.add(new Circulo(p1, p2.calcularDistancia(p1), currentColor));
		} else if (tipo == ModosDeTrabalho.RETANGULOS && p1 != null && p2 != null) {
            formas.add(new ControleRetangulo(p1, p2, currentColor));
		}else if (tipo == ModosDeTrabalho.CLIPP && p1 != null && p2 != null){
			clipArea(new ControleRetangulo(p1, p2, currentColor));
			
		}
		
		if(tipo != ModosDeTrabalho.LINHA_POLIGONAL && tipo != ModosDeTrabalho.POLIGONO) {
			p1 = null;
			p2 = null;
		}
	}

	public void mouseMoved(MouseEvent e) {
		//message(e);
		if(tipo == ModosDeTrabalho.LINHA_POLIGONAL || tipo == ModosDeTrabalho.POLIGONO) {
			if(p1 != null) {
				oldP2 = p2;
				p2 = new Ponto(e.getX(), e.getY());
				repaint();
			}
		}
	}

	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {
		if(tipo != ModosDeTrabalho.LINHA_POLIGONAL && tipo != ModosDeTrabalho.POLIGONO) {
			oldP2 = p2;
			p2 = new Ponto(e.getX(), e.getY());
			repaint();
		}
	}

	public void repaintAll(Graphics g) {
		for(Forma f : formas){
		    f.desenhar(g);
        }

		if(lp != null) {
			lp.desenhar(g);
		}
		
		setBackground(java.awt.Color.white);
	}

	private void deselecionarForma() {
	    if(formaSelecionada != null && corFormaSelecinada != null) {
            formaSelecionada.set_cor(corFormaSelecinada);
            formaSelecionada = null;
            corFormaSelecinada = null;
            repaint();
        }
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
				repaint();
				break;
			}
        }

		appGUI.buttonStatus(encontrouForma);
        System.out.println(encontrouForma);
	}
	  

	public void message(MouseEvent e){
		msg.setText("Primitivo: "+tipo.name().replace("_", " ")+" (" + e.getX() + ", " + e.getY() + ")");
	}

	public void desenharPrimitivos(Graphics g) {

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
		setSize(width, height);
		
	}

	private void setClippingArea(ClippingAlgoritmo clipArea) {
		this.clippingArea = clipArea;

		
	}

	public void apagarFormaSelecionada() {
		if(formaSelecionada != null && corFormaSelecinada != null) {
			formas.remove(formaSelecionada);
			formaSelecionada = null;
			corFormaSelecinada = null;
			repaint();
		}
	}
//
//	public void transladarFormaSelecionada(int fatorX, int fatorY) {
//		if(formaSelecionada != null && corFormaSelecinada != null) {
//			formaSelecionada.transladar(fatorX, fatorY);
//			repaint();
//		}
//	}
//
//	public void escalarFormaSelecionada(double fatorEscala) {
//		if(formaSelecionada != null && corFormaSelecinada != null) {
//			formaSelecionada.escalar(fatorEscala);
//			repaint();
//		}
//	}
//
//	public void rotacionarFormaSelecionada(Ponto ponto, double angulo) {
//		if(formaSelecionada != null && corFormaSelecinada != null) {
//			formaSelecionada.rotacionar(ponto, angulo);
//			repaint();
//		}
//	}
}
