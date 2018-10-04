package controle;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Classe de definição das formas e funções permitidas.
 * @author alinemurakami
 *
 */
public abstract class Forma {
	 Color _cor;
	 

//	    public abstract void desenhar();

	    public void set_cor(Color _cor) {
	        this._cor = _cor;
	    }

	    public Color get_cor(){
	        return this._cor;
	    }

}
