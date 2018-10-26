package matematica;
import javafx.geometry.Point2D;


public class Ponto extends Point2D {
 
    private double x;
	private double y;

	protected Ponto() {
		super(0, 0);
		 this.x = 0;
	     this.y = 0;
	}
  

    public Ponto(double x, double y) {
    	super(x, y);
    	 this.x = x;
	     this.y = y;
    }

    public Ponto(Ponto p) {
    	super(p.getX(), p.getY()); 		
    }

    public double calcularDistancia(Ponto p) {
		return distance(p);
	}
    
    public double calcularDistancia(double x, double y) {
		return distance(x, y);
	}

//    public void transladar(int fatorX, int fatorY){
//        this.x = this.x + fatorX;
//        this.y = this.y + fatorY;
//    }
//
//    public void escalar(double fatorDeEscala){
//        this.x = this.x * fatorDeEscala;
//        this.y = this.y * fatorDeEscala;
//    }
//
//    public void rotacionar(Ponto p, double angulo) {
//        //transladar -p até a origem
//        this.transladar((int)(-1*p.getX()), (int)(-1*p.getY()));
//
//        //rotacionar angulo
//        double rad = Math.toRadians(angulo);
//        double old_x = this.x;
//        double old_y = this.y;
//        this.x = old_x * Math.cos(rad) - old_y * Math.sin(rad);
//        this.y = old_x * Math.sin(rad) + old_y * Math.cos(rad);
//
//        //desfazer a translacao
//
//        this.transladar((int)(p.getX()), (int)(p.getY()));
//    }



}