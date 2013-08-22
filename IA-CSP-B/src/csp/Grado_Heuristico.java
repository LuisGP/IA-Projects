package csp;

/**
 * Heurística de Grado Heurístico
 * Sólo es aplicable para la selección de variables, 
 * Para elegir valores de un dominio se usará la heurística por defecto
 * 
 * Debido a particularidades de la representación de estado y a cómo se
 * mantiene arco consistencia y se propagan las restricciones, esta heurística
 * está en proceso de ser completa y optima para MAC y para Vuelta Atrás
 * con comprobación hacia delante.
 * 
 * @author Rubén Bernál Cervigón, Luis Gasco Poderoso y Helmuth Winkovics
 *
 */
public class Grado_Heuristico extends Heuristica {
	public Variable getVariables(CSP prob){
		Variable[] aux = prob.getVariables();
		Estado act = prob.getActual();
		Restriccion[] restAux;
		int min = Integer.MAX_VALUE, ind = -1;
		
		for(int i = 0; i < aux.length; i++){
			restAux = aux[i].getRestricciones();
			if(restAux.length < min && act.getVariable(i)==null){
				ind = i;
				min = restAux.length;
			}
		}
		
		if(ind == -1){
			return null;
		}
		
		return aux[ind];
	}
}
