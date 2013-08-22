package csp;

/**
 * Heur�stica de Grado Heur�stico
 * S�lo es aplicable para la selecci�n de variables, 
 * Para elegir valores de un dominio se usar� la heur�stica por defecto
 * 
 * Debido a particularidades de la representaci�n de estado y a c�mo se
 * mantiene arco consistencia y se propagan las restricciones, esta heur�stica
 * est� en proceso de ser completa y optima para MAC y para Vuelta Atr�s
 * con comprobaci�n hacia delante.
 * 
 * @author Rub�n Bern�l Cervig�n, Luis Gasco Poderoso y Helmuth Winkovics
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
