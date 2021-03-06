package formas;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import matematica.Ponto;

public class ControleRetangulo extends Forma {
        //vertice 1 é oposto ao 2
        //vertice 3 é oposto ao 4
        Ponto vertice1, vertice2, vertice3, vertice4;
        Double fatorEscala = 1D;

    //Construtores
    ControleRetangulo(int x1, int y1, int x2, int y2){

        setVertice1(new Ponto(x1, y1));
        setVertice2(new Ponto(x2, y2));

        this.vertice3 = new Ponto(this.getVertice1().getX(), this.getVertice2().getY() );
        this.vertice4 = new Ponto(this.getVertice2().getX(), this.getVertice1().getY());
        this._cor = Color.BLACK;
    }

    ControleRetangulo(Ponto p1, Ponto p2){
        setVertice1(new Ponto(p1));
        setVertice2(new Ponto(p2));
        this.vertice3 = new Ponto(this.getVertice1().getX(), this.getVertice2().getY() );
        this.vertice4 = new Ponto(this.getVertice2().getX(), this.getVertice1().getY());
        this._cor = Color.BLACK;
    }

    //Construtores
    public ControleRetangulo(int x1, int y1, int x2, int y2, Color color){

        setVertice1(new Ponto(x1, y1));
        setVertice2(new Ponto(x2, y2));
        this.vertice3 = new Ponto(this.getVertice1().getX(), this.getVertice2().getY() );
        this.vertice4 = new Ponto(this.getVertice2().getX(), this.getVertice1().getY());
        this._cor =  color;
    }

    public ControleRetangulo(Ponto p1, Ponto p2, Color color){
        setVertice1(new Ponto(p1));
        setVertice2(new Ponto(p2));
        this.vertice3 = new Ponto(this.getVertice1().getX(), this.getVertice2().getY() );
        this.vertice4 = new Ponto(this.getVertice2().getX(), this.getVertice1().getY());
        this._cor =  color;
    }


    public Ponto getVertice1() {
        return vertice1;
    }

    public void setVertice1(Ponto vertice1) {
        this.vertice1 = vertice1;
    }

    public Ponto getVertice2() {
        return vertice2;
    }

    public void setVertice2(Ponto vertice2) {
        this.vertice2 = vertice2;
    }

    private ArrayList<ControleReta> calcularRetas() {
        ArrayList<ControleReta> controleRetas = new ArrayList<>();

        controleRetas.add(new ControleReta(vertice1, vertice3, this._cor));
        controleRetas.add(new ControleReta(vertice1, vertice4, this._cor));
        controleRetas.add(new ControleReta(vertice2, vertice3, this._cor));
        controleRetas.add(new ControleReta(vertice2, vertice4, this._cor));

        return controleRetas;
    }

    @Override
    public void desenhar(GraphicsContext g) {
        ArrayList<ControleReta> controleRetas = calcularRetas();
        for(ControleReta r: controleRetas) {
            r.desenhar(g);
        }
    }

    @Override
    public boolean pontoNaForma(Ponto p, int margemDeErro) {

        boolean encontrouForma = false;

        ArrayList<ControleReta> controleRetas = calcularRetas();

        for(ControleReta r: controleRetas) {
            if(r.pontoNaForma(p, margemDeErro)){
                encontrouForma = true;
                break;
            }
        }

        return encontrouForma;
    }
/*
	@Override
	public void desenhar(GraphicsContext g) {
		// TODO Auto-generated method stub
		
	}
*/
    @Override
    public void rotacionar(Ponto referencia, double angulo) {
    	double xcenter = vertice2.getX() - ((vertice2.getX() - vertice1.getX())/2);
    	double ycenter =vertice2.getY() - ((vertice2.getY() - vertice1.getY())/2);
    	
    	Ponto centro = new Ponto(xcenter, ycenter);
    	
        this.vertice1.rotacionar(centro, angulo);
        this.vertice2.rotacionar(centro, angulo);
        this.vertice3.rotacionar(centro, angulo);
        this.vertice4.rotacionar(centro, angulo);
        
    }

    @Override
    public void escalar(Ponto referencia, double fatorEscala) {
        this.vertice1.escalar(referencia, fatorEscala);
        this.vertice2.escalar(referencia, fatorEscala);
        this.vertice3.escalar(referencia, fatorEscala);
        this.vertice4.escalar(referencia, fatorEscala);
    }

    @Override
    public void transladar(Ponto p) {
//    	x’=x+tx, y’= y+ty
    			double fatorX = p.getX();
    			double fatorY = p.getY();
    			;
    			//vetor de transação
    		Ponto t = new Ponto((fatorX) - vertice1.getX(),
    				(fatorY) - vertice1.getY());
    	
        this.vertice1.transladar(t);
        this.vertice2.transladar(t);
        this.vertice3.transladar(t);
        this.vertice4.transladar(t);
        
    }


	
}
