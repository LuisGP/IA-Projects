package userInterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Ventana de selección del tamaño del problema, padre del interfaz
 * 
 * @author Rubén Bernál Cervigón, Luis Gasco Poderoso y Helmuth Winkovics
 *
 */
public class Main implements ActionListener
{
	private JButton botonOK;
    private JLabel eti;
    private JTextField texto;
    
	public Main() {
		JFrame ventana = new JFrame("-=N-Reinas=-");
		ventana.setSize(200,100);
        ventana.setResizable(false);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.eti=new JLabel("Inserte el tamaño del problema:");
        this.botonOK= new JButton("OK");
        this.texto= new JTextField(5);
        
        JPanel panelDeContenido = new JPanel();
        
        panelDeContenido.add(this.eti);
        panelDeContenido.add(this.texto);
        panelDeContenido.add(this.botonOK);
        
        this.botonOK.addActionListener(this);
        
        ventana.setContentPane(panelDeContenido);
        ventana.setVisible(true);
	}
	/*
	public static void main(String[] args) {
		new Main();
	}*/
	public void actionPerformed(ActionEvent arg0) {
		Integer i= Integer.valueOf(texto.getText());
		new Ventana(i.intValue());
		
	}

}
