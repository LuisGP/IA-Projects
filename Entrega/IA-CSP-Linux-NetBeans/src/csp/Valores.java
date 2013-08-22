package csp;

/**
 * Esta clase representa los pares de valores posibles para las restricciones.
 * Es genérica, por ellos podría ser necesario heredar de la misma para
 * realizar implementaciones más eficientes. 
 * 
 * @author Rubén Bernál Cervigón, Luis Gasco Poderoso y Helmuth Winkovics
 *
 */
public class Valores {
	Object val1;
	Object val2;
	
	public Valores(Object a, Object b){
		val1 = a;
		val2 = b;
	}
	
	/** Devuelve el valor para la primera variable */
	public Object getValor1(){
		return val1;
	}
	
	/** Devuelve el valor para la segunda variable */
	public Object getValor2(){
		return val2;
	}
	
	/** Devuelve una representación de ambos valores */
	public String toString(){
		return new String("("+val1.toString()+", "+val2.toString()+")");
	}
}
