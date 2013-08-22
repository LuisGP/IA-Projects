package csp;

import java.util.Vector;

/**
 * Clase de heurística por defecto. Para definir nuevas heurísticas se debe 
 * heredar de esta clase.
 * 
 * Destacar que aún no se encuentra totalmente sincronizadas las huerísticas
 * con las representaciones de estados y metodos de busqueda
 * 
 * @author Rubén Bernál Cervigón, Luis Gasco Poderoso y Helmuth Winkovics
 * @version En pruebas
 */
public class Heuristica {
	/** Heurística para selecciona la próxima variable a asignar */
	public Variable getVariables(CSP prob){
		Estado aux = prob.getActual();
		return prob.getVariables()[aux.getLength()];
	}
	
	/** Heurística para elegir el próximo valor del dominio*/
	public Object getNextValor(Variable var){
		Vector dominio = var.getDominio();
		//Object valor = var.getValor();
		
		//return dominio.elementAt(((Integer)valor).intValue());
		return dominio.elementAt(0);
	}
	
	/** Heurística para elegir el próximo valor del dominio*/
	public Object getNextValor(Vector dominio){
		return dominio.elementAt(0);
	}
}
