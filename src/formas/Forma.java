package formas;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import matematica.Ponto;

public abstract class Forma {

    protected Color _cor;

    public abstract void desenhar(GraphicsContext g);
    public abstract boolean pontoNaForma(Ponto p, int margemDeErro);

    public void set_cor(Color _cor) {
        this._cor = _cor;
    }

    public Color get_cor(){
        return this._cor;
    }
    
    //TODO métodos comuns à todas as formas serão definidos nesta classe:
  public abstract void rotacionar(Ponto referencia, double angle);
  public abstract void escalar(Ponto referencia, double fatorEscala);
  public abstract void transladar(Ponto referencia);
 

}
