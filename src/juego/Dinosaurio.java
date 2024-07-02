package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Dinosaurio {
	double x;
	double y;
	int contDisparo;
	Image img1;
	Image img2;
	double escala;
	double alto;
	double ancho;
	boolean estaApoyado;
	boolean puedeMoverse;
	boolean dir; // false = izquierda true = derecha
	public Dinosaurio(int x, int y, boolean direccion) {
		this.x = x;
		this.y = y;
		img1 = Herramientas.cargarImagen("enemigoizq.png");
		img2 = Herramientas.cargarImagen("enemigoder.png");
		dir=direccion;
		estaApoyado=false;
		puedeMoverse=true;
		escala=0.05;
		alto=img1.getHeight(null)*escala;
		ancho=img1.getWidth(null)*escala;
	}
	public void dibujarse(Entorno entorno) {
		if(dir) {
			entorno.dibujarImagen(img1, x, y, 0,escala);
		}
		else {
			entorno.dibujarImagen(img2, x, y, 0,escala);
		}
	}
	public void moverse(boolean direccion) {
		if(estaApoyado && puedeMoverse) {
			if(!direccion) {
				this.x-=1;
			}
			if(direccion) {
				this.x+=1;
			}
			this.dir = direccion;	
		}
	}

	public void caer() {
		if(!estaApoyado) {
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
		return x + ancho/3;
	}
	public double getIzq() {
		return x - ancho/3;
	}
}

		

