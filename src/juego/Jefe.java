package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Jefe {
	double x;
	double y;
	int contAtaque;
	int vida;
	int ataqueRandom;
	Image img1;
	Image img2;
	Image img3;
	Image img4;
	Image img5;
	Image img6;
	Image img7;
	Image img8;
	double escala;
	double alto;
	double ancho;
	boolean estaApoyado;
	boolean estaDasheando;
	boolean puedeMoverse;
	boolean dir; // false = izquierda true = derecha
	public Jefe(int x, int y, boolean direccion) {
		this.x = x;
		this.y = y;
		img1 = Herramientas.cargarImagen("jefeDerecha.png");
		img2 = Herramientas.cargarImagen("jefeIzquierda.png");
		img3 = Herramientas.cargarImagen("jefeDerechaAtaque3.png");
		img4 = Herramientas.cargarImagen("jefeIzquierdaAtaque3.png");
		img5 = Herramientas.cargarImagen("jefeDerechaAtaque2.png");
		img6 = Herramientas.cargarImagen("jefeIzquierdaAtaque2.png");
		img7 = Herramientas.cargarImagen("jefeDerechaAtaque.png");
		img8 = Herramientas.cargarImagen("jefeIzquierdaAtaque.png");
		dir=direccion;
		estaApoyado=false;
		puedeMoverse=true;
		estaDasheando=false;
		escala=0.18;
		alto=img1.getHeight(null)*escala;
		ancho=img1.getWidth(null)*escala;
		vida=10;
	}
	public void dibujarse(Entorno entorno) {
		if(dir && !estaDasheando) {
			entorno.dibujarImagen(img1, x, y, 0,escala);
		}
		if(!dir && !estaDasheando) {
			entorno.dibujarImagen(img2, x, y, 0,escala);
		}
		if(dir && estaDasheando) {
			entorno.dibujarImagen(img3, x, y, 0,escala);
		}
		if(!dir && estaDasheando) {
			entorno.dibujarImagen(img4, x, y, 0,escala);
		}
	}
	public void ataque() {
		if(this.contAtaque<301) {
			this.contAtaque++;
			this.ataqueRandom = (int)(Math.random()*(2)+1);
		}	
	}

	public void moverse(boolean direccion) {
		if(this.estaApoyado && this.puedeMoverse) {
			if(!direccion) {
				this.x-=0.5;
			}
			if(direccion) {
				this.x+=0.5;
			}
			this.dir = direccion;	
		}
	}
	public void dash(boolean direccion) {
		if(this.estaApoyado && this.puedeMoverse) {
			if(!direccion) {
				this.x-=5;
			}
			if(direccion) {
				this.x+=5;
			}
			this.dir = direccion;	
		}
	}

	public void caer() {
		if(!this.estaApoyado) {
			this.y+=5;
		}
	}
	public double getTecho() {
		return y - alto/2;
	}
	public double getPiso() {
		return y + alto/2;
	}
	public double getDer() {
		return x + ancho/2;
	}
	public double getIzq() {
		return x - ancho/2;
	}
	
}
	