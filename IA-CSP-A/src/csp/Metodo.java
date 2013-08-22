package csp;

/**
 * Cualquier m�todo de b�squeda para problemas CSP ha de implementar esta interfaz
 * o heredar de una clase que lo haga.
 * 
 * @author Rub�n Bern�l Cervig�n, Luis Gasco Poderoso y Helmuth Winkovics
 *
 */
public interface Metodo {
	public Estado solve(CSP prob, Estado actual, Heuristica heur);
	public Estado[] getAllSolutions(CSP prob, Estado actual, Heuristica heur);
}
