package csp;

/**
 * Heurística de Mínimos Valores Restantes
 * Sólo es aplicable para la selección de variables, 
 * Para elegir valores de un dominio se usará la heurística por defecto.
 * 
 * Devuelve la variable no asignada con menos valores en su dominio.
 * 
 * @author Rubén Bernál Cervigón, Luis Gasco Poderoso y Helmuth Winkovics
 *
 */
public class MinValRes extends Heuristica {
	
	public Variable getVariables(CSP prob){
		Variable[] aux = prob.getVariables();
		Estado act = prob.getActual();
		int min = Integer.MAX_VALUE, ind = -1;
		
		for(int i = 0; i < aux.length; i++){
			if(act.getVariable(i)==null && (aux[i].getDominio().size() < min)){
				ind = i;
				min = aux[i].getDominio().size();
			}
		}
		
		if(ind == -1){
			return null;
		}
		
		return aux[ind];
	}
}
