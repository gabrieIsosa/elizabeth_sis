package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Lava {
	double y,alto,ancho;
	double escala;
	Image img1;
	int contImagen=0;
	public Lava(double y) {
		this.y=y;
		escala=1;
		img1 = Herramientas.cargarImagen("lava_1.png");
		alto=img1.getHeight(null)*escala;
		ancho=img1.getWidth(null)*escala;
		
	}
	public void dibujarse(Entorno entorno) {
		if(contImagen<25) {
			img1 = Herramientas.cargarImagen("lava_1.png");
			entorno.dibujarImagen(img1, entorno.ancho()/2, y, 0,escala);
			this.contImagen++;
		}
		if(contImagen>=25 && contImagen<50) {
			img1 = Herramientas.cargarImagen("lava_2.png");
			entorno.dibujarImagen(img1, entorno.ancho()/2, y, 0,escala);
			this.contImagen++;
		}
		if(contImagen>=50 && contImagen<75) {
			img1 = Herramientas.cargarImagen("lava_3.png");
			entorno.dibujarImagen(img1, entorno.ancho()/2, y, 0,escala);
			this.contImagen++;
		}
		if(contImagen>=75 && contImagen<100) {
			img1 = Herramientas.cargarImagen("lava_4.png");
			entorno.dibujarImagen(img1, entorno.ancho()/2, y, 0,escala);
			this.contImagen++;
		}
		if(contImagen>=100 && contImagen<125) {
			img1 = Herramientas.cargarImagen("lava_5.png");
			entorno.dibujarImagen(img1, entorno.ancho()/2, y, 0,escala);
			this.contImagen++;
		}
		if(contImagen>=125 && contImagen<150) {
			img1 = Herramientas.cargarImagen("lava_6.png");
			entorno.dibujarImagen(img1, entorno.ancho()/2, y, 0,escala);
			this.contImagen++;
		}
		if(contImagen>=150 && contImagen<175) {
			img1 = Herramientas.cargarImagen("lava_7.png");
			entorno.dibujarImagen(img1, entorno.ancho()/2, y, 0,escala);
			this.contImagen++;
		}
		if(contImagen>=175 && contImagen<200) {
			img1 = Herramientas.cargarImagen("lava_8.png");
			entorno.dibujarImagen(img1, entorno.ancho()/2, y, 0,escala);
			this.contImagen++;
		}
		if(contImagen>=200 && contImagen<225) {
			img1 = Herramientas.cargarImagen("lava_9.png");
			entorno.dibujarImagen(img1, entorno.ancho()/2, y, 0,escala);
			this.contImagen++;
		}
		if(contImagen>=225) {
			this.contImagen=0;
		}
		
	}
	public void subir() {
		this.y-=0.2;
	}
	public void bajar() {
		
		this.y=y+0.8;
		
	}
	public double getTecho() {
		return y - alto/2;
	}
	public double getPiso() {
		return y + alto/2;
	}
}
