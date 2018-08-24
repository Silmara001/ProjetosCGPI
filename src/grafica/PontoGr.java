package grafica;

import matematica.Ponto;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Representa um ponto gr�fico
 * 
 * @author Julio Arakaki
 * @version v1.0 Data: 2018/05/06
 * 
 */
public class PontoGr extends Ponto {
	Color corPonto = Color.BLACK; // cor do ponto
	String nomePonto = ""; // nome do ponto
	Color corNomePonto = Color.BLACK; // cor do nome  
	int diametro = 1; // diametro do ponto, default = 1

	/**
	 * Constroi um ponto na posicao x, y com diametro 1 e cor Black
	 *
	 * @param x coordenada x
	 * @param y coordenada y
	 */
	public PontoGr(int x, int y){
		super((double)x, (double)y);
	}

	/**
	 * Constroi um ponto na posicao x, y com diametro 1 e com os atributos
	 * 
	 * @param x coordenada x
	 * @param y coordenada y
	 * @param corPonto cor do ponto a ser construido
	 */
	public PontoGr(int x, int y, Color corPonto){
		this(x, y);
		setCorPonto(corPonto);	 
	}

	/**
	 * Constroi um ponto na posicao x, y e com os atributos
	 * 
	 * @param x coordenada x
	 * @param y coordenada y
	 * @param corPonto cor do ponto a ser construido
	 * @param diametro diametro do ponto
	 */
	public PontoGr(int x, int y, Color corPonto, int diametro){
		this(x, y, corPonto);
		setDiametro(diametro);
	}

	/**
	 * Constroi um ponto na posicao x, y e com os atributos
	 * 
	 * @param x coordenada x
	 * @param y coordenada y
	 * @param corPonto cor do ponto a ser construido
	 * @param nomePonto nome do ponto
	 */
	public PontoGr(int x, int y, Color corPonto, String nomePonto){
		this(x, y, corPonto);
		setNomePonto(nomePonto);
	}

	/**
	 * Constroi um ponto na posicao x, y e com os atributos
	 * 
	 * @param x coordenada x
	 * @param y coordenada y
	 * @param corPonto cor do ponto a ser construido
	 * @param corNomePonto cor do ponto a ser construido
	 * @param nomePonto nome do ponto
	 */
	public PontoGr(int x, int y, Color corPonto, Color corNomePonto, String nomePonto){
		this(x, y, corPonto);
		setCorNomePonto(corNomePonto);
		setNomePonto(nomePonto);
	}

	/**
	 * Constroi um ponto na posicao x, y e com os atributos
	 * 
	 * @param x coordenada x
	 * @param y coordenada y
	 * @param corPonto cor do ponto a ser construido
	 * @param nomePonto nome do ponto
	 * @param diametro diametro do ponto
	 */
	public PontoGr(int x, int y, Color corPonto, String nomePonto, int diametro){
		this(x, y, corPonto, diametro);
		setNomePonto(nomePonto);
	}
	/**
	 * Constroi um ponto baseado em outro ponto grafico
	 * 
	 * @param pg outro ponto
	 * @param corPonto cor do ponto a ser construido
	 * @param diametro diametro do ponto
	 */
	public PontoGr(PontoGr pg, Color corPonto, int diametro){
		this((int)pg.getX(),(int)pg.getY(), corPonto, diametro);	 
	}


	/**
	 * Constroi um ponto na posicao 0, 0 com diametro e cor Black
	 * 
	 * @param diametro
	 */
	public PontoGr(int diametro){
		this(0,0);
		setDiametro(diametro);
	}

	private Color getCorPonto() {
		return corPonto;
	}

	private String getNomePonto() {
		return nomePonto;
	}

	private Color getCorNomePonto() {
		return corNomePonto;
	}

	private int getDiametro() {
		return diametro;
	}

	private void setDiametro(int diametro) {
		this.diametro = diametro;
	}

	private void setCorPonto(Color corPonto){
		this.corPonto = corPonto;
	}
	private void setCorNomePonto(Color corNomePonto){
		this.corNomePonto = corNomePonto;
	}
	private void setNomePonto(String nomePonto){
		this.nomePonto = nomePonto;
	}

	/**
	 * desenha um ponto utilizando o oval 
	 * 
	 * @param g contexto grafico
	 */
	public void desenharPonto(GraphicsContext g) {
		// desenha ponto como um oval
		g.setFill(getCorPonto());
		g.fillOval((int)getX() -(getDiametro()/2), (int)getY() - (getDiametro()/2), getDiametro(), getDiametro());

		// desenha nome do ponto
		g.setFill(getCorNomePonto());
		g.strokeText(getNomePonto(), (int)getX() + getDiametro(), (int)getY());
	}
}
