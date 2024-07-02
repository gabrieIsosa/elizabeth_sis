package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Magia {
	double x;
	double y;
	Image img1;
	Image img2;
	double escala;
	double alto;
	double ancho;
	boolean dir;
	
	public Magia(double x, double y, boolean direccion) {
		this.x = x;
		this.y = y;
		img1 = Herramientas.cargarImagen("magiaIzquierda2.png");
		img2 = Herramientas.cargarImagen("magiaDerecha2.png");
		dir=direccion;
		escala=0.1;
		alto=img1.getHeight(null)*escala;
		ancho=img1.getWidth(null)*escala;
	}
	public void dibujarse(Entorno entorno) {
		if(dir) {
			entorno.dibujarImagen(img2, x, y, 0,escala);
		}
		else {
			entorno.dibujarImagen(img1, x, y, 0,escala);
		}
	}
	public void moverse() {
		if(this.dir) {
			this.x+=5;
		}
		else {
			this.x-=5;
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


