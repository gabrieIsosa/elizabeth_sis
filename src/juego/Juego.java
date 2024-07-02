package juego;

import java.awt.Color;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	// El objeto Entorno que controla el tiempo y otros
	Image fondo;
	private Entorno entorno;
	Princesa princesa;
	Princesa princesa2;
	Piso[] piso;
	Jefe jefe;
	Magia magia;
	Magia magia2;
	Dinosaurio[] dinosaurios;
	boolean debeCaer;
	boolean ultimaCaida;
	boolean ganaste;
	boolean imagenGanador;
	boolean imagenPerdedor;
	Bomba[] bombas;
	Meteorito[] meteoritos;
	Bomba[] bombasaux;
	int cantidadItems;
	int contEsperar;
	Item[] items;
	Piso plataforma;
	Bloque aux;
	Lava lava;
	Menu menu;
	int cantjugadores;
	int puntaje;
	Image imgGanador;
	Image imgPerdedor;
	int contDinosaurios;
	public Juego() {
		
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Prueba del Entorno", 1280, 680);
		fondo = Herramientas.cargarImagen("fondo.png");
		imgGanador = Herramientas.cargarImagen("ganasteCompleto.png");
		imgPerdedor = Herramientas.cargarImagen("perdisteCompleto.png");
		menu = new Menu();
		princesa = new Princesa(640,610,false);
		princesa2 = new Princesa(650,610,true);
		
		puntaje = 0;
		piso = new Piso[9];
		dinosaurios = new Dinosaurio[(piso.length-1) * 2];
		for(int i = 0; i < piso.length-1; i++) {
			int numero = (int)(Math.random()*(1200-950+1)+950);
			int numero2 = (int)(Math.random()*(150)+100);
			piso[i] = new Piso(680 - i * (entorno.alto() / 4 + 10),0);
			cantidadItems+=piso[i].bloques.length;
			if(i==0) {
				dinosaurios[i*2]= new Dinosaurio(100,(int) (piso[i].y - 100),true);
				dinosaurios[i*2 +1]= new Dinosaurio(1200,(int) (piso[i].y - 100),false);
			}
			if(i>0 && i<piso.length-2) {
				dinosaurios[i*2]= new Dinosaurio(numero2,(int) (piso[i].y - 100),true);
				dinosaurios[i*2 +1]= new Dinosaurio(numero,(int) (piso[i].y - 100),false);
			}		
		}
		debeCaer=false;
		ultimaCaida=false;
		ganaste=false;
		imagenGanador=false;
		imagenPerdedor=false;
		items = new Item[cantidadItems];
		bombas = new Bomba[dinosaurios.length];
		meteoritos = new Meteorito[3];
		bombasaux = new Bomba[dinosaurios.length];
		lava = new Lava(entorno.alto()+450);
		piso[piso.length-1] = new Piso(680 - piso.length * (entorno.alto() / 4 + 10),20);
		aux = new Bloque(0,0,0);
		contDinosaurios=0;
		// Inicializar lo que haga falta para el juego
		// ...

		// Inicia el juego!
		this.entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y 
	 * por lo tanto es el método más importante de esta clase. Aquí se debe 
	 * actualizar el estado interno del juego para simular el paso del tiempo 
	 * (ver el enunciado del TP para mayor detalle).
	 */
	public void tick() {
		
		if(imagenGanador) {
			entorno.dibujarImagen(imgGanador, entorno.ancho()/2,entorno.alto()/2, 0,1);
			if(entorno.sePresiono(entorno.TECLA_ESPACIO)) {
				imagenGanador=false;
				menu = new Menu();
			}
		}
		if(imagenPerdedor) {
			entorno.dibujarImagen(imgPerdedor, entorno.ancho()/2,entorno.alto()/2, 0,1);
			if(entorno.sePresiono(entorno.TECLA_ESPACIO)) {
				imagenPerdedor=false;
				menu = new Menu();
			}
		}
		if(menu!=null) {
			if(entorno.estaPresionada(entorno.TECLA_ABAJO)) {
				menu.cantJugador=true;
			}
			if(entorno.estaPresionada(entorno.TECLA_ARRIBA)) {
				menu.cantJugador=false;
			}
			menu.mostrar(entorno);
			if(entorno.estaPresionada(entorno.TECLA_ENTER)) {
				if(!menu.cantJugador) {
					cantjugadores=1;
				}
				else {
					cantjugadores=2;
				}
				menu=null;
			}
			
		}
		
		
		
		if(menu==null && cantjugadores==1 && !imagenPerdedor && !imagenGanador) {
			entorno.dibujarImagen(fondo, entorno.ancho()/2,entorno.alto()/2, 0,1);
			if(princesa!=null) {
				if(entorno.estaPresionada(entorno.TECLA_DERECHA) && !debeCaer && !ganaste) {
					princesa.moverseder(true);
				}
				if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA)&& !debeCaer && !ganaste) {
					princesa.moverseizq(false);
				}
				if(entorno.sePresiono(entorno.TECLA_ARRIBA) && princesa.estaApoyado && !debeCaer && !ganaste) {
					princesa.estaSaltando=true;
				}
				if(magia == null && entorno.sePresiono(entorno.TECLA_ESPACIO) && !debeCaer && !ganaste) {
					magia = new Magia(princesa.x, princesa.y, princesa.dir);
				}
				princesa.dibujarse(entorno);
				princesa.caer();
				princesa.invencible();
				princesa.estrella();
				if(princesa.y<140 && princesa.estaApoyado) {
					debeCaer=true;
					
				}
				else {
					if(princesa.y>256) {
						debeCaer=false;
					}
				}
				if(princesa.vida==0) {
					princesa=null;
				}
			}


			for(int i=0;i<piso.length;i++) {
				piso[i].mostrar(entorno);
				if(piso[piso.length-2].y>=310 && piso[piso.length-2].y<680) {
					piso[i].bajar(i);
					piso[piso.length-2].recomponer();
					ultimaCaida=true;
					debeCaer=true;
					if(princesa.dir && princesa.x<1280) {
						princesa.x++;
					}
					if(!princesa.dir && princesa.x>0) {
						princesa.x--;
					}
				}
				if(piso[piso.length-2].y==680) {
					//piso[i].recomponer();
					piso[piso.length-2].y++;
					lava=null;
					debeCaer=false;
					ultimaCaida=false;
					jefe = new Jefe(entorno.ancho()/2, 500, true);
					//plataforma = new Piso(490,16);
					
				}
				
			}
			if(debeCaer && princesa != null) {
				princesa.y++;
				
				if(magia!=null) {
					magia.y++;
				}
				if(!ultimaCaida) {
					for(int i = 0; i < piso.length; i++){
						if(piso[i]!=null) {
							piso[i].bajar(i);
							
						}			
					}
				}
				
			}

			
			for(int j = 0; j < dinosaurios.length; j++){
				if(dinosaurios[j]!= null){
					
	                dinosaurios[j].dibujarse(entorno);
	                dinosaurios[j].caer();
	                
	            	dinosaurios[j].moverse(dinosaurios[j].dir);

	            	
	            	if(debeCaer) {
	            		dinosaurios[j].y++;
	            		dinosaurios[j].puedeMoverse=false;
	            	}
	            	else {
	            		dinosaurios[j].puedeMoverse=true;
	            		dinosaurios[j].contDisparo++;
	            	}

		            if(detectarApoyo(dinosaurios[j], piso)) {
		            	dinosaurios[j].estaApoyado = true;
		            }
		            else {
		            	dinosaurios[j].estaApoyado = false;
		            }
		    		if(detectarFinal(dinosaurios[j], entorno)) {
		    			dinosaurios[j].dir=!dinosaurios[j].dir;
		    		}
		    		if(dinosaurios[j].contDisparo==500 && dinosaurios[j].estaApoyado && bombas[j]==null) {
		    			int numero = (int)(Math.random()*2+1);
		    			if(numero==1) {
		    				bombas[j] = new Bomba(dinosaurios[j].x, dinosaurios[j].y-30,dinosaurios[j].dir);
		    				bombasaux[j] = new Bomba(dinosaurios[j].x, dinosaurios[j].y-30,dinosaurios[j].dir);
		    			}
		    			dinosaurios[j].contDisparo=0;
		    		}
		    		for(int i = 0; i < dinosaurios.length; i++){
		    			if(dinosaurios[i]!= null && dinosaurios[j]!=null && i!=j){
		    				if(detectarColisionDinosaurios(dinosaurios[j],dinosaurios[i])) {
		    					dinosaurios[j].dir= !dinosaurios[j].dir;
		    				}
		    			}
		    		}
					if(princesa!=null && !princesa.invencibilidad && !princesa.tieneEstrella && dinosaurios[j] != null && (detectarAtaqueDinosaurioIzq(dinosaurios[j],princesa) 
						|| detectarAtaqueDinosaurioDer(dinosaurios[j],princesa) || detectarAtaqueDinosaurioMedio(dinosaurios[j],princesa))) {
						princesa.restarVida();
					}
					if(princesa!=null && princesa.tieneEstrella && dinosaurios[j] != null && (detectarAtaqueDinosaurioIzq(dinosaurios[j],princesa) 
							|| detectarAtaqueDinosaurioDer(dinosaurios[j],princesa) || detectarAtaqueDinosaurioMedio(dinosaurios[j],princesa))) {
						dinosaurios[j]=null;
					}
		            if(dinosaurios[j] != null && magia!= null && (detectarAtaqueIzq(magia,dinosaurios[j]) || detectarAtaqueDer(magia,dinosaurios[j]))) {
		            	dinosaurios[j]=null;
		            	magia=null;
		            	puntaje+=2;
		            	
		            }
		            	
		            if(lava!=null && dinosaurios[j]!=null && detectarColisionLava(dinosaurios[j],lava)){
		            	dinosaurios[j]=null;
		            }
		            if(cantidadDinosaurios(dinosaurios)<=1 && dinosaurios[j]==null && piso[piso.length-2].y<0) {
		            	int numero = (int)(Math.random()*1200);
		    		
		            	dinosaurios[j]= new Dinosaurio(numero,50,false);
		            }
				}
			}
			for(int k = 0; k < bombas.length; k++){
				if(bombas[k] != null) {
					bombas[k].dibujarse(entorno);
					if(!debeCaer) {
						bombas[k].moverse();
					}
					
					bombas[k].caerBala(bombasaux[k].y);
					if(debeCaer) {
						bombas[k].y++;
	            	}
					if((bombas[k].x < -0.1 * entorno.ancho() || bombas[k].x > 1.1 * entorno.ancho())) {
						bombas[k]=null;
					}
					if(magia!= null && bombas[k] != null && (detectarAtaqueIzq(magia,bombas[k]) 
						|| detectarAtaqueDer(magia,bombas[k]))) {
		            	bombas[k]=null;
		            	magia=null;
		            	
		            }
					if(princesa!=null && !princesa.invencibilidad && !princesa.tieneEstrella && bombas[k] != null && (detectarAtaqueBombaIzq(bombas[k],princesa) 
						|| detectarAtaqueBombaDer(bombas[k],princesa))) {
						bombas[k]=null;
						princesa.restarVida();
					}
				}
				
			}
			for(int l = 0; l < items.length; l++){
				if(items[l] != null) {
					items[l].dibujarse(entorno);
					items[l].caer();
					if(detectarApoyo(items[l], piso)) {
						items[l].estaApoyado = true;
		            }
		            else {
		            	items[l].estaApoyado = false;
		            }
					if(princesa!=null && items[l] != null && (detectarColisionCorazonIzq(items[l],princesa) 
							|| detectarColisionCorazonDer(items[l],princesa) || detectarColisionCorazonMedio(items[l],princesa))) {
						if(items[l].tipo == 0) {
							if(princesa.vida<5) {
								princesa.vida++;
								princesa.contHud=0;
							}
						}
						if(items[l].tipo == 1) {	
							princesa.tieneEstrella=true;
						}
						items[l]=null;
						
					}
				}
				
			}
	
			
			if(magia != null) {
				magia.dibujarse(entorno);
				if(!debeCaer) {
					magia.moverse();
				}
				
			}
			//System.out.println("Techo Princesa: " + princesa.getTecho() + " Piso bloque: " + block.getPiso());
			//System.out.println("Piso Princesa: " + princesa.getPiso() + " Techo bloque: " + block.getTecho());

			
			if(magia != null && (magia.x < -0.1 * entorno.ancho() || magia.x > 1.1 * entorno.ancho())) {
				magia=null;
			}
			if(lava!=null) {
				lava.dibujarse(entorno);
			}
			
			if(!debeCaer && lava!=null) {
				lava.subir();
			}
			if(debeCaer && lava!=null) {
				lava.bajar();
			}
			
			if(jefe!=null) {
				jefe.dibujarse(entorno);
                jefe.caer();
                jefe.ataque();
               
                //System.out.println(jetAtaque);
                if(jefe.contAtaque==301) {
                		if(jefe.ataqueRandom==2) {
                    		jefe.estaDasheando=true;
                        	if(jefe.x>1 && jefe.x<1279) {
                        		jefe.dash(jefe.dir);
                        	}
                        	else {
                        		jefe.estaDasheando=false;
                        		jefe.contAtaque=0;
                        	}
                    	}
                		if(jefe.ataqueRandom==1) {
                			for(int i = 0; i < meteoritos.length; i++){
                				if(meteoritos[i]==null) {
                					int xrandom = (int)(Math.random()*(400)+i*400);
                					meteoritos[i]=new Meteorito(xrandom+1,0);
                				}
                				jefe.contAtaque=0;
                			}
                			
                    	}
            		
                	
                	
                }
                if(jefe.x>350 && !jefe.dir && !jefe.estaDasheando) {
                	jefe.moverse(jefe.dir);
                }
                if(jefe.x<950 && jefe.dir && !jefe.estaDasheando) {
                	jefe.moverse(jefe.dir);
                }
//                System.out.println(princesa.x-jefe.x);
                if(princesa!= null && princesa.getDer()-jefe.getIzq()<0 && !jefe.estaDasheando) {
                	jefe.dir=false;
                }
                if(princesa!= null && princesa.getIzq()-jefe.getDer()>0 && !jefe.estaDasheando) {
                	jefe.dir=true;
                }
				if(detectarApoyo(jefe, piso)) {
					jefe.estaApoyado=true;
				}
				else {
					jefe.estaApoyado=false;
				}
				if(princesa!=null && !princesa.invencibilidad && !princesa.tieneEstrella && jefe != null && (detectarAtaqueJefeIzq(jefe,princesa) 
						|| detectarAtaqueJefeDer(jefe,princesa) || detectarAtaqueJefeMedio(jefe,princesa))) {
						princesa.restarVida();
				}
				if(jefe != null && magia!= null && (detectarAtaqueIzq(magia,jefe) || detectarAtaqueDer(magia,jefe))) {
	            	jefe.vida--;
	            	magia=null;
	            	
	            }
				if(jefe.vida==0) {
					jefe=null;
					ganaste=true;
				}
				
			}
			for(int i = 0; i < meteoritos.length; i++){
				if(meteoritos[i]!=null) {
					meteoritos[i].dibujarse(entorno);
					meteoritos[i].caer();
					//System.out.println(meteoritos[i].getPiso()-princesa.getTecho());
					if(meteoritos[i].y > entorno.alto()) {
						meteoritos[i]=null;
					}
					if(jefe==null) {
						meteoritos[i]=null;
					}
					if(princesa!=null && !princesa.invencibilidad && !princesa.tieneEstrella && meteoritos[i] != null && (detectarAtaqueMeteoritoIzq(meteoritos[i],princesa) 
							|| detectarAtaqueMeteoritoDer(meteoritos[i],princesa) || detectarAtaqueMeteoritoMedio(meteoritos[i],princesa))) {
							meteoritos[i]=null;
							princesa.restarVida();
						}
				}
				
				
			}
			if(princesa!=null) {
				if(detectarApoyoDer(princesa, piso)) {
					princesa.apoyadoDer=true;
				}
				else {
					princesa.apoyadoDer=false;
				}
				if(detectarApoyoIzq(princesa, piso)) {
					princesa.apoyadoIzq=true;
				}
				else{
					princesa.apoyadoIzq=false;
				}
				if(detectarApoyo(princesa, piso)) {
					princesa.estaApoyado=true;
				}
				else {
					princesa.estaApoyado=false;
				}
		
				if(detectarColision(princesa,piso)) {
					princesa.estaSaltando=false;
					princesa.contSalto=0;
					
					
				}
			
				if(lava!=null && !princesa.invencibilidad && detectarColisionLava(princesa,lava)) {
					princesa.restarVida();
					
				}
				princesa.dibujarHud(entorno, 30, 640);
			}
			
			
			else {
				for(int k = 0; k < piso.length; k++){	
					piso[k]=null;
				}
				for(int j = 0; j < dinosaurios.length; j++){
					if(dinosaurios[j]!=null) {
						dinosaurios[j].puedeMoverse=false;
					}
					
					dinosaurios[j]=null;
				}
				for(int i = 0; i < bombas.length; i++){	
					bombas[i]=null;
				}
				for(int l = 0; l < bombas.length; l++){	
					items[l]=null;
				}
				for(int m = 0; m < meteoritos.length; m++){	
					meteoritos[m]=null;
				}
				if(jefe!=null) {
					jefe=null;
				}
				piso = new Piso[9];
				dinosaurios = new Dinosaurio[(piso.length-1) * 2];
				for(int i = 0; i < piso.length-1; i++) {
					int numero = (int)(Math.random()*(1200-950+1)+950);
					int numero2 = (int)(Math.random()*(150)+100);
					piso[i] = new Piso(680 - i * (entorno.alto() / 4 + 10),0);
					cantidadItems+=piso[i].bloques.length;
					if(i==0) {
						dinosaurios[i*2]= new Dinosaurio(100,(int) (piso[i].y - 100),true);
						dinosaurios[i*2 +1]= new Dinosaurio(1200,(int) (piso[i].y - 100),false);
					}
					if(i>0 && i<piso.length-2) {
						dinosaurios[i*2]= new Dinosaurio(numero2,(int) (piso[i].y - 100),true);
						dinosaurios[i*2 +1]= new Dinosaurio(numero,(int) (piso[i].y - 100),false);
					}		
				}
				piso[piso.length-1] = new Piso(680 - piso.length * (entorno.alto() / 4 + 10),20);
				items = new Item[cantidadItems];
				puntaje=0;
				meteoritos = new Meteorito[3];
				princesa = new Princesa(640,610,false);
				lava = new Lava(entorno.alto()+450);
				imagenPerdedor=true;
				if(ganaste) {
					imagenGanador=true;
					ganaste=false;
					imagenPerdedor=false;
				}

					
				
			}
			if(ganaste) {
				if(princesa.dir && princesa.estaApoyado) {
					princesa.x+=2;
				}
				if(!princesa.dir && princesa.estaApoyado) {
					princesa.x-=2;
				}
				if(princesa.x>entorno.ancho() || princesa.x<0) {
					princesa=null;
				}
			}
			entorno.cambiarFont("Couture", 18, Color.white);
			entorno.escribirTexto("Puntaje: " + puntaje, 1100, 30);
			
			
			
		}
		//entorno.dibujarImagen(lava, 200, 600, 0,1);
		
		
		
		
		
		
		
		if(menu==null && cantjugadores==2 && !imagenPerdedor && !imagenGanador) {
			entorno.dibujarImagen(fondo, entorno.ancho()/2,entorno.alto()/2, 0,1);
			if(princesa!=null) {
				if(entorno.estaPresionada(entorno.TECLA_DERECHA) && !debeCaer && !ganaste) {
					princesa.moverseder(true);
				}
				if(entorno.estaPresionada(entorno.TECLA_IZQUIERDA) && !debeCaer && !ganaste) {
					princesa.moverseizq(false);
				}
				if(entorno.sePresiono(entorno.TECLA_ARRIBA) && princesa.estaApoyado && !debeCaer && !ganaste) {
					princesa.estaSaltando=true;
				}
				if(magia == null && entorno.sePresiono(entorno.TECLA_ESPACIO) && !debeCaer && !ganaste) {
					magia = new Magia(princesa.x, princesa.y, princesa.dir);
				}
				princesa.dibujarse(entorno);
				princesa.caer();
				princesa.invencible();
				princesa.estrella();
				if(princesa.vida==0) {
					princesa=null;
				}
			}
			if(princesa2!=null) {
				if(entorno.estaPresionada('d') && !debeCaer && !ganaste) {
					princesa2.moverseder(true);
				}
				if(entorno.estaPresionada('a') && !debeCaer && !ganaste) {
					princesa2.moverseizq(false);
				}
				if(entorno.sePresiono('w') && princesa2.estaApoyado && !debeCaer && !ganaste) {
					princesa2.estaSaltando=true;
				}
				if(magia2 == null && entorno.sePresiono('f') && !debeCaer && !ganaste) {
					magia2 = new Magia(princesa2.x, princesa2.y, princesa2.dir);
				}
				princesa2.dibujarse(entorno);
				princesa2.caer();
				princesa2.invencible();
				princesa2.estrella();
				if(princesa2.vida==0) {
					princesa2=null;
				}
			}
			if(princesa!= null && princesa2 !=null && princesa.y<140 && princesa.estaApoyado && princesa2.y<140 && princesa2.estaApoyado ) {
				debeCaer=true;
				
			}
			else {
				if(princesa!=null && princesa2!=null && princesa.y>256 && princesa2.y>256) {
					debeCaer=false;
				}
			}
			if(princesa== null && princesa2 !=null && princesa2.y<140 && princesa2.estaApoyado ) {
				debeCaer=true;
				
			}
			else {
				if(princesa==null && princesa2!=null && princesa2.y>256) {
					debeCaer=false;
				}
			}
			if(princesa!= null && princesa2 ==null && princesa.y<140 && princesa.estaApoyado ) {
				debeCaer=true;
				
			}
			else {
				if(princesa!=null && princesa2==null && princesa.y>256) {
					debeCaer=false;
				}
			}
			
			for(int i=0;i<piso.length;i++) {
				if(piso[i]!=null) {
					piso[i].mostrar(entorno);
				}		
				if(piso[i] != null && piso[piso.length-2].y>=310 && piso[piso.length-2].y<680) {
					piso[i].bajar(i);
					piso[piso.length-2].recomponer();
					ultimaCaida=true;
					debeCaer=true;
					if(princesa!=null && princesa.dir && princesa.x<1280) {
						princesa.x++;
					}
					if(princesa != null && !princesa.dir && princesa.x>0) {
						princesa.x--;
					}
					if(princesa2!=null && princesa2.dir && princesa2.x<1280) {
						princesa2.x++;
					}
					if(princesa2 != null && !princesa2.dir && princesa2.x>0) {
						princesa2.x--;
					}
				}
				if(piso[piso.length-2]!=null && piso[piso.length-2].y==680) {
					//piso[i].recomponer();
					piso[piso.length-2].y++;
					lava=null;
					debeCaer=false;
					ultimaCaida=false;
					jefe = new Jefe(entorno.ancho()/2, 500, true);
					//plataforma = new Piso(490,16);
					
				}
				
			}
			if(debeCaer) {
				if(princesa!=null) {
					princesa.y++;
				}
				if(princesa2!=null) {
					princesa2.y++;
				}
				
				if(magia!=null) {
					magia.y++;
				}
				
				if(magia2!=null) {
					magia2.y++;
				}
				
				if(!ultimaCaida) {
					for(int i = 0; i < piso.length; i++){
						if(piso[i]!=null) {
							piso[i].bajar(i);
							
						}			
					}
				}
				
			}
			
			for(int j = 0; j < dinosaurios.length; j++){
				if(dinosaurios[j]!= null){
	                dinosaurios[j].dibujarse(entorno);
	                dinosaurios[j].caer();
	            	dinosaurios[j].moverse(dinosaurios[j].dir);
	            	if(debeCaer) {
	            		dinosaurios[j].y++;
	            		dinosaurios[j].puedeMoverse=false;
	            	}
	            	else {
	            		dinosaurios[j].puedeMoverse=true;
	            		dinosaurios[j].contDisparo++;
	            	}
		           
		            if(detectarApoyo(dinosaurios[j], piso)) {
		            	dinosaurios[j].estaApoyado = true;
		            }
		            else {
		            	dinosaurios[j].estaApoyado = false;
		            }
		    		if(detectarFinal(dinosaurios[j], entorno)) {
		    			dinosaurios[j].dir=!dinosaurios[j].dir;
		    		}
		    		if(dinosaurios[j].contDisparo==500 && dinosaurios[j].estaApoyado && bombas[j]==null) {
		    			int numero = (int)(Math.random()*2+1);
		    			if(numero==1) {
		    				bombas[j] = new Bomba(dinosaurios[j].x, dinosaurios[j].y-30,dinosaurios[j].dir);
		    				bombasaux[j] = new Bomba(dinosaurios[j].x, dinosaurios[j].y-30,dinosaurios[j].dir);
		    			}
		    			dinosaurios[j].contDisparo=0;
		    		}
		    		for(int i = 0; i < dinosaurios.length; i++){
		    			if(dinosaurios[i]!= null && dinosaurios[j]!=null && i!=j){
		    				if(detectarColisionDinosaurios(dinosaurios[j],dinosaurios[i])) {
		    					dinosaurios[j].dir=!dinosaurios[j].dir;
		    				}
		    			}
		    		}
		    		if(princesa!=null && !princesa.invencibilidad && !princesa.tieneEstrella && dinosaurios[j] != null && (detectarAtaqueDinosaurioIzq(dinosaurios[j],princesa) 
							|| detectarAtaqueDinosaurioDer(dinosaurios[j],princesa) || detectarAtaqueDinosaurioMedio(dinosaurios[j],princesa))) {
		    			princesa.restarVida();
					}
		    		if(princesa!=null && princesa.tieneEstrella && dinosaurios[j] != null && (detectarAtaqueDinosaurioIzq(dinosaurios[j],princesa) 
							|| detectarAtaqueDinosaurioDer(dinosaurios[j],princesa) || detectarAtaqueDinosaurioMedio(dinosaurios[j],princesa))) {
		    			
		    			dinosaurios[j]=null;
					}
		    		
		    		 if(magia!= null && dinosaurios[j]!=null && (detectarAtaqueIzq(magia,dinosaurios[j]) || detectarAtaqueDer(magia,dinosaurios[j]))) {
			            	dinosaurios[j]=null;
			            	magia=null;
			            	puntaje+=2;
			            	
			            }
		    		 if(princesa2!=null && !princesa2.invencibilidad && dinosaurios[j] != null && (detectarAtaqueDinosaurioIzq(dinosaurios[j],princesa2) 
								|| detectarAtaqueDinosaurioDer(dinosaurios[j],princesa2) || detectarAtaqueDinosaurioMedio(dinosaurios[j],princesa2))) {
		    			 princesa2.restarVida();
						}
		    		 if(princesa2!=null && princesa2.tieneEstrella && dinosaurios[j] != null && (detectarAtaqueDinosaurioIzq(dinosaurios[j],princesa2) 
								|| detectarAtaqueDinosaurioDer(dinosaurios[j],princesa2) || detectarAtaqueDinosaurioMedio(dinosaurios[j],princesa2))) {
							dinosaurios[j]=null;
						}
		    		 if(magia2!= null && dinosaurios[j]!=null && (detectarAtaqueIzq(magia2,dinosaurios[j]) || detectarAtaqueDer(magia2,dinosaurios[j]))) {
			            	dinosaurios[j]=null;
			            	magia2=null;
			            	puntaje+=2;
			            	
			            }
		    		 if(dinosaurios[j]!=null && detectarColisionLava(dinosaurios[j],lava)){
			            	dinosaurios[j]=null;
			            }
		    		 if(cantidadDinosaurios(dinosaurios)<=1 && dinosaurios[j]==null && piso[piso.length-2].y<0) {
			            	int numero = (int)(Math.random()*1200);
			    		
			            	dinosaurios[j]= new Dinosaurio(numero,50,false);
			            }
				}
			}
			for(int k = 0; k < bombas.length; k++){
				if(bombas[k] != null) {
					bombas[k].dibujarse(entorno);
					if(!debeCaer) {
						bombas[k].moverse();
					}
					if(debeCaer) {
						bombas[k].y++;
	            	}
					bombas[k].caerBala(bombasaux[k].y);		
					if((bombas[k].x < -0.1 * entorno.ancho() || bombas[k].x > 1.1 * entorno.ancho())) {
						bombas[k]=null;
					}
					if(magia!= null && bombas[k] != null && (detectarAtaqueIzq(magia,bombas[k]) || detectarAtaqueDer(magia,bombas[k]))) {
		            	bombas[k]=null;
		            	magia=null;
		            	
		            }
					if(magia2!= null && bombas[k] != null && (detectarAtaqueIzq(magia2,bombas[k]) || detectarAtaqueDer(magia2,bombas[k]))) {
		            	bombas[k]=null;
		            	magia2=null;
		            	
		            }
					if(princesa!=null && !princesa.invencibilidad && !princesa.tieneEstrella && bombas[k] != null && (detectarAtaqueBombaIzq(bombas[k],princesa) 
							|| detectarAtaqueBombaDer(bombas[k],princesa))) {
							bombas[k]=null;
							princesa.restarVida();
						}
					if(princesa2!=null && !princesa2.invencibilidad && !princesa2.tieneEstrella && bombas[k] != null && (detectarAtaqueBombaIzq(bombas[k],princesa2) 
							|| detectarAtaqueBombaDer(bombas[k],princesa2))) {
							bombas[k]=null;
							princesa2.restarVida();
						}
				}
				
			}
			
			for(int l = 0; l < items.length; l++){
				if(items[l] != null) {
					items[l].dibujarse(entorno);
					items[l].caer();
					if(detectarApoyo(items[l], piso)) {
						items[l].estaApoyado = true;
		            }
		            else {
		            	items[l].estaApoyado = false;
		            }
					if(princesa!=null && items[l] != null && (detectarColisionCorazonIzq(items[l],princesa) 
							|| detectarColisionCorazonDer(items[l],princesa) || detectarColisionCorazonMedio(items[l],princesa))) {
						if(items[l].tipo == 0) {
							if(princesa.vida<5) {
								princesa.vida++;
								princesa.contHud=0;
							}
						}
						if(items[l].tipo == 1) {	
							princesa.tieneEstrella=true;
						}
						items[l]=null;
						
					}
					if(princesa2!=null && items[l] != null && (detectarColisionCorazonIzq(items[l],princesa2) 
							|| detectarColisionCorazonDer(items[l],princesa2) || detectarColisionCorazonMedio(items[l],princesa2))) {
						if(items[l].tipo == 0) {
							if(princesa2.vida<5) {
								princesa2.vida++;
								princesa2.contHud=0;
							}
						}
						if(items[l].tipo == 1) {	
							princesa2.tieneEstrella=true;
						}
						items[l]=null;
						
					}
				}
				
			}
			
			if(magia != null) {
				magia.dibujarse(entorno);
				if(!debeCaer) {
					magia.moverse();
				}
				
			}
			if(magia2 != null) {
				magia2.dibujarse(entorno);
				if(!debeCaer) {
					magia2.moverse();
				}
				
			}
			//System.out.println("Techo Princesa: " + princesa.getTecho() + " Piso bloque: " + block.getPiso());
			//System.out.println("Piso Princesa: " + princesa.getPiso() + " Techo bloque: " + block.getTecho());
			for(int i=0;i<piso.length;i++) {
				if(piso[i]!=null) {
					piso[i].mostrar(entorno);
				}
				
			}
			
			if(magia != null && (magia.x < -0.1 * entorno.ancho() || magia.x > 1.1 * entorno.ancho())) {
				magia=null;
			}
			if(magia2 != null && (magia2.x < -0.1 * entorno.ancho() || magia2.x > 1.1 * entorno.ancho())) {
				magia2=null;
			}
			if(lava!=null) {
				lava.dibujarse(entorno);
			}
			
			if(!debeCaer && lava!=null) {
				lava.subir();
			}
			if(debeCaer && lava!=null) {
				lava.bajar();
			}
			if(jefe!=null) {
				jefe.dibujarse(entorno);
                jefe.caer();
                jefe.ataque();
                //System.out.println(jefe.contAtaque);
                if(jefe.contAtaque==301) {
                		if(jefe.ataqueRandom==2) {
                    		jefe.estaDasheando=true;
                        	if(jefe.x>1 && jefe.x<1279) {
                        		jefe.dash(jefe.dir);
                        	}
                        	else {
                        		jefe.estaDasheando=false;
                        		jefe.contAtaque=0;
                        	}
                    	}
                		if(jefe.ataqueRandom==1) {
                			for(int i = 0; i < meteoritos.length; i++){
                				if(meteoritos[i]==null) {
                					int xrandom = (int)(Math.random()*(400)+i*400);
                					meteoritos[i]=new Meteorito(xrandom+1,0);
                				}
                				jefe.contAtaque=0;
                			}
                			
                    	}
            		
                	
                	
                }
                if(jefe.x>350 && !jefe.dir && !jefe.estaDasheando) {
                	jefe.moverse(jefe.dir);
                }
                if(jefe.x<950 && jefe.dir && !jefe.estaDasheando) {
                	jefe.moverse(jefe.dir);
                }
//                System.out.println(princesa.x-jefe.x);
                if(princesa!= null && princesa2 != null && princesa.getDer()-jefe.getIzq()<0 && !jefe.estaDasheando) {
                	jefe.dir=false;
                }
                if(princesa!= null && princesa2 != null &&  princesa.getIzq()-jefe.getDer()>0 && !jefe.estaDasheando) {
                	jefe.dir=true;
                }
                if(princesa!= null && princesa2 == null && princesa.getDer()-jefe.getIzq()<0 && !jefe.estaDasheando) {
                	jefe.dir=false;
                }
                if(princesa!= null && princesa2 == null &&  princesa.getIzq()-jefe.getDer()>0 && !jefe.estaDasheando) {
                	jefe.dir=true;
                }
                if(princesa2!= null && princesa == null && princesa2.getDer()-jefe.getIzq()<0 && !jefe.estaDasheando) {
                	jefe.dir=false;
                }
                if(princesa2!= null && princesa == null &&  princesa2.getIzq()-jefe.getDer()>0 && !jefe.estaDasheando) {
                	jefe.dir=true;
                }
				if(detectarApoyo(jefe, piso)) {
					jefe.estaApoyado=true;
				}
				else {
					jefe.estaApoyado=false;
				}
				if(princesa!=null && !princesa.invencibilidad && !princesa.tieneEstrella && jefe != null && (detectarAtaqueJefeIzq(jefe,princesa) 
						|| detectarAtaqueJefeDer(jefe,princesa) || detectarAtaqueJefeMedio(jefe,princesa))) {
						princesa.restarVida();
				}
				if(princesa2!=null && !princesa2.invencibilidad && !princesa2.tieneEstrella && jefe != null && (detectarAtaqueJefeIzq(jefe,princesa2) 
						|| detectarAtaqueJefeDer(jefe,princesa2) || detectarAtaqueJefeMedio(jefe,princesa2))) {
						princesa2.restarVida();
				}
				if(jefe != null && magia!= null && (detectarAtaqueIzq(magia,jefe) || detectarAtaqueDer(magia,jefe))) {
	            	jefe.vida--;
	            	magia=null;
	            	
	            }
				if(jefe != null && magia2!= null && (detectarAtaqueIzq(magia2,jefe) || detectarAtaqueDer(magia2,jefe))) {
	            	jefe.vida--;
	            	magia2=null;
	            	
	            }
				if(jefe.vida==0) {
					jefe=null;
					ganaste=true;
				}
				
			}
			for(int i = 0; i < meteoritos.length; i++){
				if(meteoritos[i]!=null) {
					meteoritos[i].dibujarse(entorno);
					meteoritos[i].caer();
					//System.out.println(meteoritos[i].getPiso()-princesa.getTecho());
					if(meteoritos[i].y > entorno.alto()) {
						meteoritos[i]=null;
					}
					if(jefe==null) {
						meteoritos[i]=null;
					}
					if(princesa!=null && !princesa.invencibilidad && !princesa.tieneEstrella && meteoritos[i] != null && (detectarAtaqueMeteoritoIzq(meteoritos[i],princesa) 
							|| detectarAtaqueMeteoritoDer(meteoritos[i],princesa) || detectarAtaqueMeteoritoMedio(meteoritos[i],princesa))) {
							meteoritos[i]=null;
							princesa.restarVida();
						}
					if(princesa2!=null && !princesa2.invencibilidad && !princesa2.tieneEstrella && meteoritos[i] != null && (detectarAtaqueMeteoritoIzq(meteoritos[i],princesa2) 
							|| detectarAtaqueMeteoritoDer(meteoritos[i],princesa2) || detectarAtaqueMeteoritoMedio(meteoritos[i],princesa2))) {
							meteoritos[i]=null;
							princesa2.restarVida();
						}
				}
				
				
			}
	
			if(princesa!=null ) {
				if(detectarApoyoDer(princesa, piso)) {
					princesa.apoyadoDer=true;
				}
				else {
					princesa.apoyadoDer=false;
				}
				if(detectarApoyoIzq(princesa, piso)) {
					princesa.apoyadoIzq=true;
				}
				else{
					princesa.apoyadoIzq=false;
				}
				if(detectarApoyo(princesa, piso)) {
					princesa.estaApoyado=true;
				}
				else {
					princesa.estaApoyado=false;
				}
		
				if(detectarColision(princesa,piso)) {
					princesa.estaSaltando=false;
					princesa.contSalto=0;
					
					}
				if(lava!=null && !princesa.invencibilidad && !princesa.tieneEstrella && detectarColisionLava(princesa,lava)) {
					princesa.restarVida();
					
				}
				princesa.dibujarHud(entorno, 30, 640);
			}
			if(princesa2!=null ) {
				if(detectarApoyoDer(princesa2, piso)) {
					princesa2.apoyadoDer=true;
				}
				else {
					princesa2.apoyadoDer=false;
				}
				if(detectarApoyoIzq(princesa2, piso)) {
					princesa2.apoyadoIzq=true;
				}
				else{
					princesa2.apoyadoIzq=false;
				}
				if(detectarApoyo(princesa2, piso)) {
					princesa2.estaApoyado=true;
				}
				else {
					princesa2.estaApoyado=false;
				}
		
				if(detectarColision(princesa2,piso)) {
					princesa2.estaSaltando=false;
					princesa2.contSalto=0;
					
					}
				if(lava!=null && !princesa2.invencibilidad && !princesa2.tieneEstrella && detectarColisionLava(princesa2,lava)) {
					princesa2.restarVida();
					
				}
				princesa2.dibujarHud(entorno, 1250, 640);
			}
			
			if(princesa2==null && princesa==null) {
				for(int k = 0; k < piso.length; k++){	
					piso[k]=null;
				}
				for(int j = 0; j < dinosaurios.length; j++){
					if(dinosaurios[j]!=null) {
						dinosaurios[j].puedeMoverse=false;
					}
					
					dinosaurios[j]=null;
				}
				for(int i = 0; i < bombas.length; i++){	
					bombas[i]=null;
				}
				for(int l = 0; l < bombas.length; l++){	
					items[l]=null;
				}
				for(int m = 0; m < meteoritos.length; m++){	
					meteoritos[m]=null;
				}
				if(jefe!=null) {
					jefe=null;
				}
				piso = new Piso[9];
				dinosaurios = new Dinosaurio[(piso.length-1) * 2];
				for(int i = 0; i < piso.length-1; i++) {
					int numero = (int)(Math.random()*(1200-950+1)+950);
					int numero2 = (int)(Math.random()*(150)+100);
					piso[i] = new Piso(680 - i * (entorno.alto() / 4 + 10),0);
					cantidadItems+=piso[i].bloques.length;
					if(i==0) {
						dinosaurios[i*2]= new Dinosaurio(100,(int) (piso[i].y - 100),true);
						dinosaurios[i*2 +1]= new Dinosaurio(1200,(int) (piso[i].y - 100),false);
					}
					if(i>0 && i<piso.length-2) {
						dinosaurios[i*2]= new Dinosaurio(numero2,(int) (piso[i].y - 100),true);
						dinosaurios[i*2 +1]= new Dinosaurio(numero,(int) (piso[i].y - 100),false);
					}		
				}
				piso[piso.length-1] = new Piso(680 - piso.length * (entorno.alto() / 4 + 10),20);
				items = new Item[cantidadItems];
				puntaje=0;
				meteoritos = new Meteorito[3];
				princesa = new Princesa(640,610,false);
				princesa2 = new Princesa(650,610,true);
				lava = new Lava(entorno.alto()+450);
				imagenPerdedor=true;
				if(ganaste) {
					imagenGanador=true;
					ganaste=false;
					imagenPerdedor=false;
				}
			
			}
			if(ganaste) {
				if(princesa!= null && princesa.dir && princesa.estaApoyado) {
					princesa.x+=2;
				}
				if(princesa!= null && !princesa.dir && princesa.estaApoyado) {
					princesa.x-=2;
				}
				if(princesa2!= null && princesa2.dir && princesa2.estaApoyado) {
					princesa2.x+=2;
				}
				if(princesa2!= null && !princesa2.dir && princesa2.estaApoyado) {
					princesa2.x-=2;
				}
				if(princesa!= null && (princesa.x>entorno.ancho() || princesa.x<0)) {
					princesa=null;
				}
				if(princesa2!= null && (princesa2.x>entorno.ancho() || princesa2.x<0)) {
					princesa2=null;
				}
			}
			entorno.cambiarFont("Couture", 18, Color.white);
			entorno.escribirTexto("Puntaje: " + puntaje, 1100, 30);
		}
		

		
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
		
	}
	public boolean detectarApoyo(Princesa prin, Bloque bl) {
		return Math.abs(prin.getPiso()-bl.getTecho())<5 && prin.getIzq()<bl.getDer() && prin.getDer()>bl.getIzq();

	}
	public boolean detectarApoyo(Princesa prin, Piso pi) {
		for(int i = 0; i < pi.bloques.length;i++) {
			if(pi.bloques[i]!=null && detectarApoyo(prin, pi.bloques[i])) {
				return true;
			}
		}
		return false;
	}
	public boolean detectarApoyo(Princesa prin, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(detectarApoyo(prin, pisos[i])) {
				return true;
			}
		}
		return false;
	}
	public boolean detectarColision(Princesa prin, Bloque bl) {
		return Math.abs(prin.getTecho()-bl.getPiso())<10 && prin.getIzq()<bl.getDer() && prin.getDer()>bl.getIzq();

	}
	public boolean detectarColision(Princesa prin, Piso pi) {
		for(int i = 0; i < pi.bloques.length;i++) {
			if(pi.bloques[i]!=null && detectarColision(prin, pi.bloques[i])) {
				if(pi.bloques[i].rompible) {
					if(pi.bloques[i].tieneItem) {
						items[i] = new Item(pi.bloques[i].x, pi.bloques[i].y);
					}
					pi.bloques[i]=null;
				}
				
				return true;
			}
		}
		return false;
	}
	public boolean detectarColision(Princesa prin, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(detectarColision(prin, pisos[i])) {
				return true;
			}
		}
		return false;
	}
	public boolean detectarApoyoIzq(Princesa prin, Bloque bl) {
		return Math.abs((prin.getIzq()-bl.getDer()))<3 && (prin.getTecho()-bl.getPiso()<3 && prin.getPiso()-bl.getTecho()>3);

	}
	public boolean detectarApoyoIzq(Princesa prin, Piso pi) {
		for(int i = 0; i < pi.bloques.length;i++) {
			if(pi.bloques[i]!=null && detectarApoyoIzq(prin, pi.bloques[i])) {
				return true;
			}
		}
		return false;
	}
	public boolean detectarApoyoIzq(Princesa prin, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(detectarApoyoIzq(prin, pisos[i])) {
				return true;
			}
		}
		return false;
	}
	public boolean detectarApoyoDer(Princesa prin, Bloque bl) {
		return Math.abs((prin.getDer()-bl.getIzq()))<3 && (prin.getTecho()-bl.getPiso()<3 && prin.getPiso()-bl.getTecho()>3);

	}
	public boolean detectarApoyoDer(Princesa prin, Piso pi) {
		for(int i = 0; i < pi.bloques.length;i++) {
			if(pi.bloques[i]!=null && detectarApoyoDer(prin, pi.bloques[i])) {
				return true;
			}
		}
		return false;
	}
	public boolean detectarApoyoDer(Princesa prin, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(detectarApoyoDer(prin, pisos[i])) {
				return true;
			}
		}
		return false;
	}

	public boolean detectarApoyo(Dinosaurio din, Bloque bl) {
		return Math.abs(din.getPiso()-bl.getTecho())<5 && din.getIzq()<bl.getDer() && din.getDer()>bl.getIzq();
	
	}

	public boolean detectarApoyo(Dinosaurio din, Piso pi) {
		for(int i = 0; i < pi.bloques.length;i++) {
			if(pi.bloques[i]!=null && detectarApoyo(din, pi.bloques[i])) {
				return true;
			}
		}
		return false;
	}
	public boolean detectarApoyo(Dinosaurio din, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(detectarApoyo(din, pisos[i])) {
				return true;
			}
		}
		return false;
	}
	
	public boolean detectarApoyo(Jefe jef, Bloque bl) {
		return Math.abs(jef.getPiso()-bl.getTecho())<5 && jef.getIzq()<bl.getDer() && jef.getDer()>bl.getIzq();
	
	}

	public boolean detectarApoyo(Jefe jef, Piso pi) {
		for(int i = 0; i < pi.bloques.length;i++) {
			if(pi.bloques[i]!=null && detectarApoyo(jef, pi.bloques[i])) {
				return true;
			}
		}
		return false;
	}
	public boolean detectarApoyo(Jefe jef, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(detectarApoyo(jef, pisos[i])) {
				return true;
			}
		}
		return false;
	}
	public boolean detectarFinal(Dinosaurio din, Entorno e) {
		if (din.x>=e.ancho()) {
			return true;
		}
		if(din.x<=0) {
			return true;
		}
		return false;
	}
	public boolean detectarAtaqueIzq(Magia hue, Dinosaurio din) {
		return Math.abs((hue.getIzq()-din.getDer()))<3 && (hue.getTecho()-din.getPiso()<3 && hue.getPiso()-din.getTecho()>3);

	}
	public boolean detectarAtaqueDer(Magia hue, Dinosaurio din) {
		return Math.abs((hue.getDer()-din.getIzq()))<3 && (hue.getTecho()-din.getPiso()<3 && hue.getPiso()-din.getTecho()>3);

	}
	
	public boolean detectarAtaqueIzq(Magia hue, Bomba bom) {
		return Math.abs((hue.getIzq()-bom.getDer()))<3 && (hue.getTecho()-bom.getPiso()<3 && hue.getPiso()-bom.getTecho()>3);

	}
	public boolean detectarAtaqueDer(Magia hue, Bomba bom) {
		return Math.abs((hue.getDer()-bom.getIzq()))<3 && (hue.getTecho()-bom.getPiso()<3 && hue.getPiso()-bom.getTecho()>3);

	}
	public boolean detectarAtaqueBombaIzq(Bomba bom, Princesa prin) {
		return Math.abs((bom.getIzq()-prin.getDer()))<3 && (bom.getTecho()-prin.getPiso()<3 && bom.getPiso()-prin.getTecho()>3);

	}
	public boolean detectarAtaqueBombaDer(Bomba bom, Princesa prin) {
		return Math.abs((bom.getDer()-prin.getIzq()))<3 && (bom.getTecho()-prin.getPiso()<3 && bom.getPiso()-prin.getTecho()>3);

	}
	public boolean detectarAtaqueDinosaurioIzq(Dinosaurio din, Princesa prin) {
		return Math.abs((din.getIzq()-prin.getDer()))<3 && (din.getTecho()-prin.getPiso()<3 && din.getPiso()-prin.getTecho()>3);

	}
	public boolean detectarAtaqueDinosaurioDer(Dinosaurio din, Princesa prin) {
		return Math.abs((din.getDer()-prin.getIzq()))<3 && (din.getTecho()-prin.getPiso()<3 && din.getPiso()-prin.getTecho()>3);

	}
	public boolean detectarAtaqueDinosaurioMedio(Dinosaurio din, Princesa prin) {
		return (Math.abs(din.x-prin.x)<20 && Math.abs(din.y-prin.y)<=25);

	}
	public boolean detectarApoyo(Bomba bom, Bloque bl) {
		return Math.abs(bom.getPiso()-bl.getTecho())<5 && bom.getIzq()<bl.getDer() && bom.getDer()>bl.getIzq();

	}
	public boolean detectarApoyo(Bomba bom, Piso pi) {
		for(int i = 0; i < pi.bloques.length;i++) {
			if(pi.bloques[i]!=null && detectarApoyo(bom, pi.bloques[i])) {
				return true;
			}
		}
		return false;
	}
	public boolean detectarApoyo(Bomba bom, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(detectarApoyo(bom, pisos[i])) {
				return true;
			}
		}
		return false;
	}
	public boolean detectarApoyo(Item cor, Bloque bl) {
		return Math.abs(cor.getPiso()-bl.getTecho())<5 && cor.getIzq()<bl.getDer() && cor.getDer()>bl.getIzq();

	}
	public boolean detectarApoyo(Item cor, Piso pi) {
		for(int i = 0; i < pi.bloques.length;i++) {
			if(pi.bloques[i]!=null && detectarApoyo(cor, pi.bloques[i])) {
				return true;
			}
		}
		return false;
	}
	public boolean detectarApoyo(Item cor, Piso[] pisos) {
		for(int i = 0; i < pisos.length; i++) {
			if(detectarApoyo(cor, pisos[i])) {
				return true;
			}
		}
		return false;
	}
	public boolean detectarColisionCorazonIzq(Item cor, Princesa prin) {
		return Math.abs((cor.getIzq()-prin.getDer()))<3 && (cor.getTecho()-prin.getPiso()<3 && cor.getPiso()-prin.getTecho()>3);

	}
	public boolean detectarColisionCorazonDer(Item cor, Princesa prin) {
		return Math.abs((cor.getDer()-prin.getIzq()))<3 && (cor.getTecho()-prin.getPiso()<3 && cor.getPiso()-prin.getTecho()>3);

	}
	public boolean detectarColisionCorazonMedio(Item cor, Princesa prin) {
		return (Math.abs(cor.x-prin.x)<20 && Math.abs(cor.y-prin.y)<=25);

	}
	public boolean detectarColisionLava(Princesa prin, Lava lav) {
		return (prin.getPiso()-30>lav.getTecho());

	}
	
	public boolean detectarColisionLava(Dinosaurio din, Lava lav) {
		return (din.getPiso()-30>lav.getTecho());

	}
	public boolean detectarColisionDinosaurios(Dinosaurio din, Dinosaurio din2) {
		return (din.y==din2.y && (Math.abs(din.x-din2.x)<35) && (din.dir==din2.dir));

	}
	public boolean detectarAtaqueJefeIzq(Jefe din, Princesa prin) {
		return Math.abs((din.getIzq()-prin.getDer()))<3 && (din.getTecho()-prin.getPiso()<3 && din.getPiso()-prin.getTecho()>3);

	}
	public boolean detectarAtaqueJefeDer(Jefe din, Princesa prin) {
		return Math.abs((din.getDer()-prin.getIzq()))<3 && (din.getTecho()-prin.getPiso()<3 && din.getPiso()-prin.getTecho()>3);

	}
	public boolean detectarAtaqueJefeMedio(Jefe din, Princesa prin) {
		return (Math.abs(din.x-prin.x)<50 && (prin.y>din.getTecho()));

	}
	public boolean detectarAtaqueIzq(Magia hue, Jefe bom) {
		return Math.abs((hue.getIzq()-bom.getDer()))<3 && (hue.getTecho()-bom.getPiso()<3 && hue.getPiso()-bom.getTecho()>3);

	}
	public boolean detectarAtaqueDer(Magia hue, Jefe bom) {
		return Math.abs((hue.getDer()-bom.getIzq()))<3 && (hue.getTecho()-bom.getPiso()<3 && hue.getPiso()-bom.getTecho()>3);

	}
	public boolean detectarAtaqueIzq(Magia hue, Meteorito bom) {
		return Math.abs((hue.getIzq()-bom.getDer()))<3 && (hue.getTecho()-bom.getPiso()<3 && hue.getPiso()-bom.getTecho()>3);

	}
	public boolean detectarAtaqueDer(Magia hue, Meteorito bom) {
		return Math.abs((hue.getDer()-bom.getIzq()))<3 && (hue.getTecho()-bom.getPiso()<3 && hue.getPiso()-bom.getTecho()>3);

	}
	public boolean detectarAtaqueMeteoritoIzq(Meteorito din, Princesa prin) {
		return Math.abs((din.getIzq()-prin.getDer()))<3 && (din.getTecho()-prin.getPiso()<3 && din.getPiso()-prin.getTecho()>3);

	}
	public boolean detectarAtaqueMeteoritoDer(Meteorito din, Princesa prin) {
		return Math.abs((din.getDer()-prin.getIzq()))<3 && (din.getTecho()-prin.getPiso()<3 && din.getPiso()-prin.getTecho()>3);

	}
	public boolean detectarAtaqueMeteoritoMedio(Meteorito din, Princesa prin) {
		return (Math.abs(din.x-prin.x)<20 && (din.getPiso()-prin.getTecho())>=-1);

	}
	public int cantidadDinosaurios(Dinosaurio[] din) {
		int cantDinosaurios=0;
		for(int i = 0; i < din.length;i++) {
			if(din[i] != null && din[i].y>50) {
				cantDinosaurios++;
			}
		}
		return cantDinosaurios;
	}


}
