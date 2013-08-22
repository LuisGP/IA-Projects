package csp;


/**
 * Estado genérico. Puede ser necesario establecer un estado concreto para
 * ciertos problemas, para ello, se debe crear una clase que herede de ésta.
 * 
 * @author Rubén Bernál Cervigón, Luis Gasco Poderoso y Helmuth Winkovics
 *
 */
public class Estado {
	/** Variables asignadas al estado */
	private Variable[] vars;
	/** Numero de variables asignadas */
	private int length;
	
	/** Ultima variable asignada */
	private int ultima;
	
	public Estado(int n){
		vars = new Variable[n];
		length = 0;
	}
	
	/** Constructor de copia */
	public Estado(Estado st){
		if(st != null){
			this.length = st.getLength();
			this.vars = new Variable[st.getVariableArray().length];
			for(int i = 0; i< this.length; i++){
				if(st.getVariable(i) != null){
					this.vars[i] = new Variable(st.getVariable(i).getValor(),
							st.getVariable(i).getRestricciones(),st.getVariable(i).getDominio());
				}
			}
		}else{
			this.length = 0;
			this.vars = new Variable[0];
		}
	}
	
	/** En el método Vuelta atrás con comprobación hacia delante debemos
	 * propagar las restricciones para cada Variable asignada.
	 * Como se asignan en orden, basta propagar para la última.
	 */ 
	public void propagarRestricciones(){
		vars[this.ultima].propagarRestricciones();
	}
	
	public int getUltimo(){
		return ultima;
	}
	
	public int getLength(){
		return length;
	}
	
	public Variable getVariable(int i){
		return vars[i];
	}
	
	public Variable[] getVariableArray(){
		return vars;
	}
	
	public void setVariable(Variable var){
		this.setVariable(var,length);
	}
	
	public void setVariable(Variable var,int pos){
		if(length < vars.length && pos < vars.length){
			vars[pos] = var;
			length++;
			ultima = pos;
		}
	}
	/*
	public void delVariable(){
		if(length > 0)
			vars[--length] = null;
	}*/
	
	public void delVariable(Variable var){
		if(length > 0){
			vars[var.getId()] = null;
			length--;
		}
	}
	
	/** Nos sirva para comprobar si es meta un estado dado */
	public boolean esMeta(){
		return (length == vars.length)&&(cumpleRestricciones());
	}
	
	/** Devuelve un booleano según este estado cumpla o no las Restricciones */
	public boolean cumpleRestricciones(){
		for(int i=0; i<vars.length-1; i++)
			for(int j=i+1; j<vars.length; j++){
				if(vars[i] != null && vars[j] != null){
					if(!vars[i].check(vars[j].getValor(),vars[j])){
						return false;
					}
				}
			}
		return true;
	}
	
	/** Compara dos estados 
	 * En el caso de que un estado herede de éste, se ha de asegurar
	 * que las variables sean a su vez comparables. 
	 */
	public boolean iguales(Estado st){
		if(st==null){
			return false;
		}		
		if(this.length == st.getLength()){
			for(int i=0; i<this.vars.length; i++){
				if(this.vars[i] != null && st.getVariable(i)!=null){
					if(!this.vars[i].equals(st.getVariable(i))){
						return false;
					}
				}else{
					if(this.vars[i]!=st.getVariable(i)){
						return false;
					}
				}
			}
		}else{
			return false;
		}
		return true;
	}
	
	/** Devuelve los valores que toman las variables asignadas */
	public String toString(){
		String s = new String();

		for (int i = 0; i < this.vars.length; i++)
			if(vars[i] != null)
				s += " " + vars[i];
			else
				s += " " + "N";
		return s;
	}
}
