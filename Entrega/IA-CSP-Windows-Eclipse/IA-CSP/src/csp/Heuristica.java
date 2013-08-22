package csp;

import java.util.Vector;

/**
 * Clase de heur�stica por defecto. Para definir nuevas heur�sticas se debe 
 * heredar de esta clase.
 * 
 * Esta heur�stica devuelve Variables y Valores por orden
 * 
 * @author Rub�n Bern�l Cervig�n, Luis Gasco Poderoso y Helmuth Winkovics
 * @version En pruebas
 */
public class Heuristica {
	
	/** Heur�stica para selecciona la pr�xima variable a asignar */
	public Variable getVariables(CSP prob){
		Estado aux = prob.getActual();

		return prob.getVariables()[aux.getLength()];
	}
	
	/** Heur�stica para elegir el pr�ximo valor del dominio*/
	public Object getNextValor(Variable var){
		Vector dominio = var.getDominio();
		
		return dominio.elementAt(0);
	}
	
	/** Heur�stica para elegir el pr�ximo valor del dominio*/
	public Object getNextValor(Vector dominio){
		return dominio.elementAt(0);
	}
}
