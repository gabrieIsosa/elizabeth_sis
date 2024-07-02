package juego;

import entorno.Entorno;

public class Piso {
	Bloque[] bloques;
	Bloque aux;
	double y;
	int cantItems;
	boolean debeBajar;
	
	public Piso(double y, int cant) {
		aux = new Bloque(0,0,0);
		this.y=y;
		cantItems=0;
		debeBajar=false;
		if(cant==0) {
			bloques = new Bloque[(int) ((1280/aux.ancho) + 1)];
			for(int i= 0; i < bloques.length; i++) {
				bloques[i] = new Bloque((i+0.5)*aux.ancho,y,0);
				if(bloques[i].tieneItem) {
					cantItems++;
				}
			}
		}
		else {
			bloques = new Bloque[cant];
			for(int j= 0; j < bloques.length/2; j++) {
				if(j<4) {
					bloques[j] = new Bloque(100+(j+0.5)*aux.ancho,y+180,1);
				}
				if(j>=4 && j<10) {
					bloques[j] = new Bloque(100+(j+0.5)*aux.ancho,y+10,1);
				}
			}
			for(int j= 10; j < bloques.length; j++) {
				if(j<16) {
					bloques[j] = new Bloque(500+(j+0.5)*aux.ancho,y+10,1);
				}
				if(j>=16 && j<20) {
					bloques[j] = new Bloque(500+(j+0.5)*aux.ancho,y+180,1);
				}
			}
		}
		
		
		
	}
	public void mostrar(Entorno e) {
		for(int i= 0; i < bloques.length; i++) {
			if(bloques[i] != null) {
				bloques[i].dibujarse(e);
			}
			
		}
	}
	public void bajar(int piso) {
		for(int i= 0; i < bloques.length; i++) {
			if(bloques[i] != null ) {
				if(bloques[i].y<=680) {
					bloques[i].y++;
					this.y=bloques[i].y;
				}
				if (bloques[i].y>680) {
					bloques[i]=null;
				}
				
			}	
		}
	}
	public void recomponer() {
		for(int i= 0; i < bloques.length; i++) {
			if(bloques[i] == null ) {
				bloques[i] = new Bloque((i+0.5)*aux.ancho,y,0);		
			}
			
		}
	}
}
