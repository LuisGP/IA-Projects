package nreinas;

import java.util.Vector;

import csp.CSP;
import csp.Estado;
import csp.Heuristica;
import csp.Metodo;
import csp.Restriccion;
import csp.Valores;
import csp.Variable;

/**
 * CSP específico de las Nreinas
 * 
 * @author Rubén Bernál Cervigón, Luis Gasco Poderoso y Helmuth Winkovics
 *
 */
public class Nreinas extends CSP{
	//private CSP csp_reinas;
	//private VariableReinas[] vars;
	//private EstadoReinas inicial;
	/** Tamaño del tablero y número de reinas */
	private int n;
	/** Metodo a usar */
	private Metodo metodo;
	/** Heuristica a usar */
	private Heuristica heur = new Heuristica();
	
	/** Array de soluciones */
	private EstadoReinas[] soluciones;

	public Nreinas(Variable[] var, Estado init) {
		super(var, init);
	}

	public Nreinas(int n){
		this.n = n;
		super.vars = new VariableReinas[n];
		super.inicial = new EstadoReinas(this.n);
		super.actual = new EstadoReinas(this.n);
		inicializar();
		//csp_reinas = new CSP(vars,inicial);
		//super.vars = vars;
		//super.inicial = inicial;
		super.actual = new EstadoReinas(inicial);
	}
	
	/** Resuelve el problema */
	public EstadoReinas resolver(){
		return new EstadoReinas(this.solve(metodo,heur));
	}
	
	/** Devuelve y establece todas las soluciones al problema */
	public EstadoReinas[] resolverTodas(){
		Estado[] temp = this.getAllSolutions(metodo,heur);
		soluciones = new EstadoReinas[temp.length];
		
		for(int i = 0; i<temp.length; i++){
			soluciones[i] = new EstadoReinas(temp[i]);
		}
		return soluciones;
	}
	
	/** Muestras las soluciones al problema */
	public void mostrarSoluciones(){
		this.resolverTodas();
		for(int i=0; i<soluciones.length; i++){
			System.out.println(soluciones[i]);
		}
	}
	
	/** Inicializa el problema, construyendo restricciones
	 * variables, dominios, etc
	 *
	 */
	private void inicializar(){
		Vector valor_aux;
		Restriccion rest_aux;
		//Variable var_aux;
		Vector dominio = new Vector();
		Vector rests;
		Valores valor;
		Object[] vals;
		
		for(int i=0; i<this.n; i++){
			dominio.add(new Integer(i));
		}
		for(int i=0; i<this.n; i++){
			this.vars[i] = new VariableReinas(null,null,dominio);
		}
		
		/* Generar las restricciones hacia abajo*/
		for(int i = 0; i < this.n; i++){  // Fila actual
			//var_aux = new Variable(null,null,dominio);
			this.vars[i].setValue(new Integer(i));
			rests = new Vector();
			for(int j = i+1; j < this.n; j++){  // Sig fila
				valor_aux = new Vector();
				for(int vali = 0; vali < this.n; vali++){
					int diff = j-i;
					for(int valj = 0; valj < n; valj++){  // Columnas
						if((valj != vali) && (valj != vali-diff) && (valj != vali+diff)){
							valor = new Valores(new Integer(vali),new Integer(valj));
							valor_aux.add(valor); 
						}
					}
				}
				vals = valor_aux.toArray();
				rest_aux = new Restriccion(this.vars[j],transformarArrayVal(vals));
				rests.add(rest_aux);
			}
			this.vars[i].setRestricciones(transformarArrayRes(rests.toArray()));
			this.vars[i].setId(i);
			//this.vars[i] = var_aux;
		}
		
		/* Generar las restricciones hacia arriba*/
		for(int i = this.n-1; i >=0; i--){  // Fila actual
			//var_aux = new Variable(null,null,dominio);
			this.vars[i].setValue(new Integer(i));
			rests = new Vector();
			
			// Recupera las restricciones anteriores
			for(int aux = 0; aux < this.vars[i].getRestricciones().length; aux++){
				rests.add(this.vars[i].getRestricciones()[aux]);
			}
			
			for(int j = i-1; j >= 0; j--){  // Sig fila
				valor_aux = new Vector();
				for(int vali = 0; vali < this.n; vali++){
					int diff = i-j;
					for(int valj = 0; valj < n; valj++){  // Columnas
						if((valj != vali) && (valj != vali-diff) && (valj != vali+diff)){
							valor = new Valores(new Integer(vali),new Integer(valj));
							valor_aux.add(valor); 
						}
					}
				}
				vals = valor_aux.toArray();
				rest_aux = new Restriccion(this.vars[j],transformarArrayVal(vals));
				rests.add(rest_aux);
			}
			this.vars[i].setRestricciones(transformarArrayRes(rests.toArray()));
			this.vars[i].setId(i);
			//this.vars[i] = var_aux;
		}
	}
	
	/** Metodo auxiliar para transformar un array de objetos en un array de valores */
	private Valores[] transformarArrayVal(Object[] array){
		Valores[] aux = new Valores[array.length];
		
		for(int i=0; i<array.length; i++){
			aux[i] = (Valores)array[i];
		}
		
		return aux;
	}
	
	/** Metodo auxiliar para transformar un array de objetos en un array de restricciones */
	private Restriccion[] transformarArrayRes(Object[] array){
		Restriccion[] aux = new Restriccion[array.length];
		
		for(int i=0; i<array.length; i++){
			aux[i] = (Restriccion)array[i];
		}
		
		return aux;
	}
	
	public void setMetodo(Metodo met){
		this.metodo = met;
	}
	
	public void setHeuristica(Heuristica heur){
		this.heur = heur;
	}
	
	public Variable[] getVariables(){
		return this.vars;
	}
	
	/** Constructor de copia */
	public Nreinas(Nreinas csp){
		this.vars = new VariableReinas[csp.getVariables().length];
		
		for(int i=0; i<this.vars.length; i++){
			this.vars[i] = new VariableReinas(csp.getVariables()[i]);
		}
		
		//Reestablecer enlaces en restricciones
		for(int i=0; i<this.vars.length; i++){
			this.vars[i].mantenerRestricciones(this.vars,csp.getVariables());
		}
		
		this.inicial = new EstadoReinas(this.vars.length);
		this.actual = new EstadoReinas(this.vars.length);
		int indice;
		for(int i=0; i<csp.getActual().getLength(); i++){
			indice = csp.getActual().getVariable(i).getId();
			this.inicial.setVariable(this.vars[indice]);
			this.actual.setVariable(this.vars[indice]);
		}
	}
	
	public EstadoReinas[] getSoluciones(){
		if(this.soluciones == null){
			return this.resolverTodas();
		}else{
			return this.soluciones;
		}
	}
	/*
	/** Metodo main para depuración */
	/*
	static public void main(String[] args){
		Nreinas prueba = new Nreinas(7);
		//System.out.println(prueba.resolver().toString());
		prueba.mostrarSoluciones();
	}*/
}
