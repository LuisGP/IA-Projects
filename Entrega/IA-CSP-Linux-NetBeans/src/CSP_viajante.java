import java.util.Calendar;
import java.util.GregorianCalendar;

import viajante.Viajante;
import csp.Estado;
import csp.Grado_Heuristico;
import csp.Heuristica;
import csp.MAC;
import csp.Metodo;
import csp.MinValRes;
import csp.VA_haciaDelante;
import csp.Vuelta_atras;

/**
 * Clase principal, determina qu� modo de ejecuci�n aplicar
 *  @author Rub�n Bern�l Cervig�n, Luis Gasco Poderoso y Helmuth Winkovics
 *  @link viajante.Viajante
 */
public class CSP_viajante {
	
	static private void usage(){
		System.out.println("Modo de uso: ");
		System.out.println("viajante fichero [-m<metodo>] [-h<heur>] [-c<Ciudad de Inicio>] [-all] [-paint]");
		System.out.println();
		System.out.println("-m: Especifica un m�todo: <metodo> Puede ser: ");
		System.out.println("\"va\" : Vuelta atr�s cronol�gica (por defecto)");
		System.out.println("\"vahd\" : Vuelta atr�s cronol�gica con comprobaci�n hacia delante");
		System.out.println("\"mac\" : Metodo MAC");
		System.out.println();
		System.out.println("-h: Especifica una heur�stica: <heur> Puede ser: ");
		System.out.println("\"min\" : Minimo valor restante");
		System.out.println("\"grheur\" : Grado Heur�stico");
		System.out.println();
		System.out.println("-c: Especifica una ciudad de origen");
		System.out.println();
		System.out.println("-all: Busca todas las soluciones");
		System.out.println();
		System.out.println("-paint: Pinta el grafo");
		System.out.println();
	}
	
	/**
	 * Este m�todo identifica qu� m�todo de resoluci�n nos han pasado por consola
	 * 
	 * Puede ser: 
	 * "va" : Vuelta atr�s cronol�gica (por defecto)
	 * "vahd" : Vuelta atr�s cronol�gica con comprobaci�n hacia delante
	 * "mac" : Metodo MAC
	 * 
	 * @param str El par�metro
	 * @return Metodo
	 */
	static private Metodo idMetodo(String str){
		if(str.equals("va")){
			return new Vuelta_atras();
		}else{
		if(str.equals("vahd")){
			return new VA_haciaDelante();
		}else{
		if(str.equals("mac")){
			return new MAC();
		}
		}
		}
		return new Vuelta_atras();
	}
	
	/**
	 * Este m�todo identifica qu� heur�stica nos han indicado por consola
	 * 
	 * Puede ser: 
	 * "min" : Minimo valor restante
	 * "grheur" : Grado Heur�stico
	 * 
	 * @param str El par�metro
	 * @return Heuristica
	 */
	static private Heuristica idHeur(String str){
		if(str.equals("min")){
			return new MinValRes();
		}else{
		if(str.equals("grheur")){
			return new Grado_Heuristico();
		}
		}
		return new Heuristica();
	}
	
	/**
	 * Imprime por pantalla las soluciones del problema de las NReinas que le pasemos
	 * como par�metro.
	 * 
	 * @param nr Problema de las NReinas solucionado
	 */
	static void imprimirSoluciones(Viajante v){
		Estado[] sols = v.getSoluciones();
		
		for(int i=0; i<sols.length; i++){
			System.out.println(v.representa(sols[i]));
		}
	}

	/**
	 * M�todo main del programa, se encarga de diferenciar casos y generar los
	 * objetos oportunos para cada caso.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Viajante p;
		
		String fichero;
		String ciudad = null;
		String metodo = "va";
		String heuristica = "noheur";
		boolean todas = false;
		boolean pinta = false;
		
		
		
		try{
			if(args.length == 0 || args.length > 6){
				usage();
				System.exit(0);
			}
			
			fichero = args[0];
			
			for(int i = 1; i < args.length; i++){
					if(args[i].startsWith("-m")){
						metodo = args[i].substring(2);
					}else if(args[i].startsWith("-h")){
						heuristica = args[i].substring(2);
					}else if(args[i].startsWith("-c")){
						ciudad = args[i].substring(2);
					}else if(args[i].equals("-all")){
						todas = true;
					}else if(args[i].equals("-paint")){
						pinta = true;
					}else{
						System.out.println("Argumento no v�lido: "+args[i]);
					}
			}
			
			p = new Viajante(fichero);
			// Identificar metodo y heuristica
			p.setMetodo(idMetodo(metodo));
			p.setHeuristica(idHeur(heuristica));
			p.ciudadInicial(ciudad);
			
			Calendar c = new GregorianCalendar();
			long t1 =c.getTimeInMillis();
			
			if(todas)
				p.resolverTodas();
			else
				p.solve();
			
			Calendar c2 = new GregorianCalendar();
			long t2 =c2.getTimeInMillis();
			long ttot= t2-t1;
			
			imprimirSoluciones(p);
			System.out.println("Tiempo respuesta: " + ttot);
			
			if(pinta){
				p.pintaGrafo();
			}
				
			
		}catch(Exception e){
			System.out.println(e);
			usage();
		}
	}

}
