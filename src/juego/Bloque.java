package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bloque {
	double x,y,alto,ancho,escala;
	boolean rompible;
	boolean tieneItem;
	Image img;
	public Bloque(double x, double y, int forzarRompible) {
		this.x=x;
		this.y=y;
		this.rompible=true;
		this.tieneItem=false;
		if(forzarRompible==0) {
			if(Math.random()>0.8) {
				this.rompible=false;
			}
		}
		else {
			this.rompible=false;
		}
		
		if(Math.random()>0.9 && rompible) {
			tieneItem=true;
		}
		if(this.rompible) {
			img = Herramientas.cargarImagen("bloque.png");
		}
		else {
			img = Herramientas.cargarImagen("bloqueDuro.png");
		}
		escala=0.07;
		
		alto=img.getHeight(null)*escala;
		ancho=img.getWidth(null)*escala;
	}
	public void dibujarse(Entorno entorno) {
		entorno.dibujarImagen(img, x, y, 0,escala);
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
