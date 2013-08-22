package csp;

/**
 * Heurística de Mínimos Valores Restantes
 * Sólo es aplicable para la selección de variables, 
 * Para elegir valores de un dominio se usará la heurística por defecto
 * 
 * @author Rubén Bernál Cervigón, Luis Gasco Poderoso y Helmuth Winkovics
 *
 */
public class MinValRes extends Heuristica {
	public Variable getVariables(CSP prob){
		Variable[] aux = prob.getVariables();
		int min = Integer.MAX_VALUE, ind = -1;
		
		for(int i = prob.getActual().getLength(); i < aux.length; i++){
			if(aux[i].getDominio().size() < min){
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
