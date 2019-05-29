package matematica;
//import javafx.geometry.Point2D;

public class Ponto  {

	private double px;
	private double py;

	protected Ponto() {
		//		super(0, 0);
		this.px = 0;
		this.py = 0;
	}

	public Ponto(double x, double y) {
		//    	super(x, y);
		this.px = x;
		this.py = y;
	}

	public Ponto(Ponto p) {
		//    	super(p.getX(), p.getY()); 
		this.px = p.getX();
		this.py = p.getY();
	}

	public double getY() {
		return py;
	}

	public double getX() {
		return px;
	}

	public double calcularDistancia(Ponto p) {
		return Math.sqrt(Math.pow((p.getY() - getY()),2) +
				Math.pow((p.getX() - getX()),2));

	}

	public double calcularDistancia(double x, double y) {

		return Math.sqrt(Math.pow((y - getY()),2) +
				Math.pow((x - getX()),2));

	}

	public void transladar(Ponto ref){
//		x’=x+tx, y’= y+ty
//		double fatorX = ref.getX();
//		double fatorY = ref.getY();

		this.px = this.px + ref.getX() ;
		this.py = this.py + ref.getY();

	}

	public void escalar(double fatorDeEscala){
		//    	xâ€™= x.Sx + xf (1-Sx) 
		//    	yâ€™= y.Sy + yf (1-Sy)
		double refX = px;
		double refY = py;

		setX((refX * fatorDeEscala) + refX*(1-fatorDeEscala)) ;
		setY((refY * fatorDeEscala) + refY*(1-fatorDeEscala));
	}

	public void escalar(Ponto refPoint, double fatorDeEscala){
		//    	xâ€™= x.Sx + xf (1-Sx) 
		//    	yâ€™= y.Sy + yf (1-Sy)
		double refX = refPoint.getX();
		double refY = refPoint.getY();

		setX((px * fatorDeEscala) + refX*(1-fatorDeEscala)) ;
		setY((py * fatorDeEscala) + refY*(1-fatorDeEscala));
	}

	private void setY(double d) {
		this.py=d;
	}

	private void setX(double d) {
		this.px = d;	
	}

	public void rotacionar(double angulo) {

		//transladar -p atÃ© a origem
		this.transladar(this);

		//rotacionar angulo
		double rad = Math.toRadians(angulo);
		double old_x = this.px;
		double old_y = this.py;
		this.px = old_x * Math.cos(rad) - old_y * Math.sin(rad);
		this.py = old_x * Math.sin(rad) + old_y * Math.cos(rad);

		//desfazer a translacao

		this.transladar(this);
	}


	public void rotacionar(Ponto r, double angulo) {

		//    	xâ€™= xR + (x-xR)cos ï�± - (y-yR)sen ï�±
		//    			yâ€™= yR + (x-xR)sen ï�± + (y-yR)cos ï�±

		//transladar -p atÃ© a origem
		//        this.transladar((int)(-1*p.getX()), (int)(-1*p.getY()));

		//rotacionar angulo
		double rad = Math.toRadians(angulo);
		double old_x = this.px;
		double old_y = this.py;
		this.px = r.getX()+ (old_x-r.getX())* Math.cos(rad) - (old_y-r.getY()) * Math.sin(rad);
		this.py = r.getY()+ (old_x-r.getX()) * Math.sin(rad) + (old_y-r.getY()) * Math.cos(rad);


		//desfazer a translacao

		//        this.transladar((int)(p.getX()), (int)(p.getY()));
	}


}