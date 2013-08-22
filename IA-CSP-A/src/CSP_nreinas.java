import csp.*;
import nreinas.EstadoReinas;
import nreinas.Nreinas;

/**
 * Clase principal, determina qu� modo de ejecuci�n aplicar
 *  @author Rub�n Bern�l Cervig�n, Luis Gasco Poderoso y Helmuth Winkovics
 *  @link userInterface.Main
 *  @link userInterface.Ventana
 */
public class CSP_nreinas {
	
	static private void usage(){
		System.out.println("Modo de uso: ");
		System.out.println("nreinas                    - Inicio gr�fico");
		System.out.println("nreinas N [<metodo> [<heur>]]  - X reinas, <metodo>, <heur>");
		System.out.println();
		System.out.println("<metodo> Puede ser: ");
		System.out.println("\"va\" : Vuelta atr�s cronol�gica (por defecto)");
		System.out.println("\"vahd\" : Vuelta atr�s cronol�gica con comprobaci�n hacia delante");
		System.out.println("\"mac\" : Metodo MAC");
		System.out.println();
		System.out.println("<heur> Puede ser: ");
		System.out.println("\"min\" : Minimo valor restante");
		System.out.println("\"grheur\" : Grado Heur�stico");
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
	static void imprimirSoluciones(Nreinas nr){
		EstadoReinas[] sols = nr.getSoluciones();
		
		for(int i=0; i<sols.length; i++){
			System.out.println(sols[i]);
		}
	}

	/**
	 * M�todo main del programa, se encarga de diferenciar casos y generar los
	 * objetos oportunos para cada caso.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Nreinas p;
		
		try{
		switch(args.length){
			case 0:
				new userInterface.Main();
				break;
			case 1:
				//System.out.println("Numerito");
				p = new Nreinas(Integer.parseInt(args[0]));
				p.setMetodo(new Vuelta_atras());
				p.setHeuristica(new Heuristica());
				p.resolverTodas();
				imprimirSoluciones(p);
				break;
			case 2:
				//System.out.println("Numerito y  metodo");
				p = new Nreinas(Integer.parseInt(args[0]));
				p.setMetodo(idMetodo(args[1]));  // Identificar metodo
				p.setHeuristica(new Heuristica());
				p.resolverTodas();
				imprimirSoluciones(p);
				break;
			case 3:
				//System.out.println("Numerito,  metodo y heur");
				p = new Nreinas(Integer.parseInt(args[0]));
				// Identificar metodo y heuristica
				p.setMetodo(idMetodo(args[1]));
				p.setHeuristica(idHeur(args[2]));
				p.resolverTodas();
				imprimirSoluciones(p);
				break;
			default:
				usage();
				break;
		}
		}catch(Exception e){
			System.out.println(e);
			usage();
		}
	}

}
