package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import nreinas.EstadoReinas;
import nreinas.Nreinas;
import csp.Grado_Heuristico;
import csp.Heuristica;
import csp.MAC;
import csp.MinValRes;
import csp.VA_haciaDelante;
import csp.Vuelta_atras;


/**
 * Interfaz de la aplicación
 * 
 * @author Rubén Bernál Cervigón, Luis Gasco Poderoso y Helmuth Winkovics
 *
 */
public class Ventana implements ActionListener //, MouseListener
{
	private JRadioButton[][] tablero;
	private ButtonGroup[] grupo; 
	private int n;
	
    private JButton boton1, boton2, boton3, boton_dibuja;
    private JRadioButton mac, haciadelante, vueltaatras, noheur, heurminValores, gradoheur;
    private JLabel eti,eti2,eti3;
    private JFrame ventana;
    private JComboBox combo;
    
    private EstadoReinas[] soluciones;
    private EstadoReinas user_solution;
    private Nreinas problema;
    
    /* ICONOS */
    private ImageIcon reinamaron=new ImageIcon("reinamaron.gif");
    private ImageIcon maron=new ImageIcon("maron.gif");
    private ImageIcon reinanegro=new ImageIcon("reinanegro.gif");
    private ImageIcon negro=new ImageIcon("negro.gif");

    public Ventana(int n)
    {
    	this.n=n;
    	this.problema = new Nreinas(n);
    	//creamos la ventana
        ventana = new JFrame("Inteligencia Artificial: -=N-Reinas=-");
        
        ventana.setSize(40*n+300,35*n+320);
        ventana.setResizable(false);
        //ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //creamos los componentes y especificamos coordenadas
        tablero = new JRadioButton[n][n];
        grupo = new ButtonGroup[n];
        for(int i=0; i<n; i++){
        	grupo[i]=new ButtonGroup();
        	for (int j=0; j<n ;j++){
        		tablero[i][j]=new JRadioButton();
        		if ((((j % 2)==0) && ((i % 2)==0)) || (((j % 2)==1) && ((i % 2)==1)))
        		{
        			tablero[i][j].setIcon(negro);
        			tablero[i][j].setSelectedIcon(reinanegro);
        		}
        		else
        		{
        			tablero[i][j].setIcon(maron);
        			tablero[i][j].setSelectedIcon(reinamaron);
        		}
        		tablero[i][j].setBounds(35*(j+1), 35*(i+1), 35, 32); 
        		grupo[i].add(tablero[i][j]);
        	}
        }

        this.boton1= new JButton("Comprobar");
        this.boton2= new JButton("Buscar solución");
        this.boton3= new JButton("Buscar todas las soluciones");
        this.boton_dibuja = new JButton("Pinta solución");
        this.boton1.setBounds(35*n+70,35,100,30);
        this.boton2.setBounds(35*n+70,330,130,30);
        this.boton3.setBounds(35*n+70,365,200,30);
        this.boton_dibuja.setBounds(35*n+175, 35, 120, 30);
        this.boton_dibuja.setEnabled(false);
        
        this.vueltaatras = new JRadioButton("Cronological backtracking"); 
        this.haciadelante = new JRadioButton("Forward checking"); 
        this.mac = new JRadioButton("Metodo MAC"); 
        this.mac.setSelected(true);
        
        this.heurminValores = new JRadioButton("Mínimos valores restantes"); 
        this.gradoheur = new JRadioButton("Grado heurístico");  
        this.noheur = new JRadioButton("Sin heurística");
        this.noheur.setSelected(true);
        
        this.mac.setBounds(35*n+70,110,130,30);
        this.haciadelante.setBounds(35*n+70,140,130,30);
        this.vueltaatras.setBounds(35*n+70,170,190,30);
        
        this.heurminValores.setBounds(35*n+70,230,190,30);
        this.gradoheur.setBounds(35*n+70,260,130,30);
        this.noheur.setBounds(35*n+70,290,130,30);
        
        ButtonGroup g = new ButtonGroup();
        g.add(this.mac);
        g.add(this.haciadelante);
        g.add(this.vueltaatras);
        
        ButtonGroup g2 = new ButtonGroup();
        g2.add(this.heurminValores);
        g2.add(this.gradoheur);
        g2.add(this.noheur);
        
        this.eti = new JLabel("Seleccione un método:");
        this.eti.setBounds(35*n+50,80,190,30);
        this.eti2 = new JLabel("Seleccione una solución:");
        this.eti2.setBounds(35,35*n+100,210,30);
        this.eti3 = new JLabel("Seleccione una heurística:");
        this.eti3.setBounds(35*n+50,200,190,30);
        
        this.combo = new JComboBox();
        this.combo.setBounds(35,35*n+130,170,30);
        this.combo.addItem(makeObj("Apriete Buscar todas las soluciones"));

        //añadimos todos los componentes al panel de contenido
        JPanel panelDeContenido = new JPanel(null);
 
        panelDeContenido.add(this.boton1);
        panelDeContenido.add(this.boton2);
        panelDeContenido.add(this.boton3);
        panelDeContenido.add(this.boton_dibuja);
        panelDeContenido.add(this.mac);
        panelDeContenido.add(this.haciadelante);
        panelDeContenido.add(this.vueltaatras);
        this.vueltaatras.setSelected(true);
        panelDeContenido.add(this.eti);
        panelDeContenido.add(this.eti2);
        panelDeContenido.add(this.eti3);
        panelDeContenido.add(this.combo);
        panelDeContenido.add(this.gradoheur);
        panelDeContenido.add(this.noheur);
        panelDeContenido.add(this.heurminValores);
        for(int i=0; i<n; i++){
        	for (int j=0; j<n ;j++){
        		panelDeContenido.add(tablero[i][j]);
        	}
        }
        
        ventana.setContentPane(panelDeContenido);
        
        this.boton1.addActionListener(this);
        this.boton2.addActionListener(this);
        this.boton3.addActionListener(this);
        this.boton_dibuja.addActionListener(this);
        ventana.setVisible(true);
    }
    
    private Object makeObj(final String item)  {
        return new Object() { public String toString() { return item; } };
    }

    public void setTablero(int[] t)
    {
    	for (int i=0; i<t.length; i++)
    	{
    		tablero[i][t[i]].setSelected(true);
    	}
    }
    
    public int[] getTablero()
    {
    	int[] tab= new int[this.n];
    	for(int i=0; i<this.n; i++){
        	for (int j=0; j<this.n ;j++){
        		if (tablero[i][j].isSelected())
        			tab[i]=j;
        	}
    	}
    	return tab;
    }
    
    public boolean comprobarSolucion(int[] estado,Nreinas reinas){
    	this.user_solution = new EstadoReinas(estado.length);
    	this.user_solution.setFromTablero(estado,reinas);
    	return this.user_solution.esMeta();
    }
    
    private void setMetodo(){
    	if(this.mac.isSelected()){
    		this.problema.setMetodo(new MAC());
    	}else{
    		if(this.haciadelante.isSelected()){
    			this.problema.setMetodo(new VA_haciaDelante());
    		}else{
    			if(this.vueltaatras.isSelected()){
    				this.problema.setMetodo(new Vuelta_atras());
    			}
    		}
    	}
    }

    /*public static void main(String[] args)
    {
        new Ventana(5);
    }*/
    
    public void actionPerformed(ActionEvent e)
    {
    	
    	if((JButton)e.getSource()==this.boton1)
    	{
    		//System.out.println("Has presionado Comprobar");
    		
    		if(this.comprobarSolucion(this.getTablero(),this.problema)){
    			JOptionPane.showMessageDialog(this.ventana,"Es solución!","SOLUCION ;)",
    					JOptionPane.INFORMATION_MESSAGE,this.reinamaron);
    		}else{
    			JOptionPane.showMessageDialog(this.ventana,"No es solución!","NO SOLUCION :(",
    					JOptionPane.INFORMATION_MESSAGE,this.reinanegro);
    		}
    	}
    	
    	if((JButton)e.getSource()==this.boton2)
    	{
//    		System.out.println("buscar - Una");
    		this.problema = new Nreinas(this.n);
    		
    		/*Establecer método*/
    		this.setMetodo();
    		this.setHeuristica();
    		
    		Calendar c = new GregorianCalendar();
    		long t1 =c.getTimeInMillis();
    		this.user_solution = (EstadoReinas)this.problema.resolver();
    		Calendar c2 = new GregorianCalendar();
    		long t2 =c2.getTimeInMillis();
    		long ttot= t2-t1;
    		System.out.println("Tiempo respuesta: " + ttot);
    		
    		int[] t = this.user_solution.getTablero();
    		
    		setTablero(t);
    		
    		if(t != null)
    			System.out.println(this.user_solution);

    	}
    	
    	if((JButton)e.getSource()==this.boton3)
    	{
    		//System.out.println("Has presionado el Todas");
    		this.problema = new Nreinas(this.n);
    		
    		/*Establecer método*/
    		this.setMetodo();
    		this.setHeuristica();
    		
    		Calendar c = new GregorianCalendar();    		
    		long t1 =c.getTimeInMillis();
    		this.soluciones = this.problema.resolverTodas();
    		Calendar c2 = new GregorianCalendar();
    		long t2 =c2.getTimeInMillis();
    		
    		long ttot= t2-t1;
    		
    		this.combo.removeAllItems();
    		
    		for(int i=0; i<this.soluciones.length; i++){
    			this.combo.addItem(this.soluciones[i]);
    		}
    		
    		if(this.soluciones != null && this.soluciones.length > 0)
    			setTablero(this.soluciones[0].getTablero());
    		else
    			System.out.println("No hay solucion!!");
    		
    		this.boton_dibuja.setEnabled(true);
    		System.out.println("Tiempo respuesta: " + ttot +"\n");
    	}   
    	
    	if((JButton)e.getSource() == this.boton_dibuja){
    		//System.out.println("Has presionado el Dibuja");
    		
    		int[] t = ((EstadoReinas)this.combo.getSelectedItem()).getTablero();
    		
    		setTablero(t);
    	}  
    }

	private void setHeuristica() {
		if(this.heurminValores.isSelected()){
    		this.problema.setHeuristica(new MinValRes());
    		System.out.println("Mínimos Valores restantes en pruebas\nOutOfMemory para MAC con 6 o mayor");
    	}else{
    		if(this.gradoheur.isSelected()){
    			this.problema.setHeuristica(new Grado_Heuristico());
    			System.out.println("Grado Heurístico en pruebas\nNo completo para VA_haciaDelante");
    		}else{
    			this.problema.setHeuristica(new Heuristica());
    		}
    	}
	}
}