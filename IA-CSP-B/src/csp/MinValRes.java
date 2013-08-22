package csp;

/**
 * Heur�stica de M�nimos Valores Restantes
 * S�lo es aplicable para la selecci�n de variables, 
 * Para elegir valores de un dominio se usar� la heur�stica por defecto
 * 
 * @author Rub�n Bern�l Cervig�n, Luis Gasco Poderoso y Helmuth Winkovics
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
