package interfaz;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import viajante.Viajante;

/**
 * Esta clase dibuja el grafo
 * 
 * @author LuisGP
 *
 */
public class GrafoPanel extends JPanel implements MouseListener, MouseMotionListener{
	
	private Viajante viajante;
	BufferedImage bi;
	Graphics2D big;
	int x, y;
	//Rectangle area, startpt, endpt, rect;
	Rectangle area, startpt, endpt, rect;
	Line2D.Double line = new Line2D.Double();
	Point2D.Double start, end, point;
	boolean firstTime = true;
	boolean pressOut = false;
	ArrayList<Point2D.Double> ciudades;
	ArrayList<Rectangle> rectangulos;
	ArrayList<Line2D.Double> enlace;
	ArrayList<Color> colores;
	
	public GrafoPanel(Viajante v){
		
		setBackground(Color.white);
		addMouseMotionListener(this);
		addMouseListener(this);
		
		this.viajante = v;
		this.ciudades = new ArrayList<Point2D.Double>();
		this.rectangulos = new ArrayList<Rectangle>();
		for(int i = 0; i < v.getCiudades().size(); i++){
			this.ciudades.add(new Point2D.Double());
			this.rectangulos.add(new Rectangle(0, 0, 8, 8));
		}
		
		this.enlace = new ArrayList<Line2D.Double>();
		for(int i = 0; i < v.getEnlaces().size(); i++){
			this.enlace.add(new Line2D.Double());
			int indA = v.getEnlaces().get(i)[0];
			int indB = v.getEnlaces().get(i)[1];
			this.enlace.get(i).setLine(this.ciudades.get(indA),this.ciudades.get(indB));
		}
		/*
		 start = new Point2D.Double();
		 //one = new Point2D.Double();
		  //two = new Point2D.Double();
		   end = new Point2D.Double();
		   
		   line.setLine(start, end);
		   startpt = new Rectangle(0, 0, 8, 8);
		   endpt = new Rectangle(0, 0, 8, 8);
		   */
		
		this.colores = new ArrayList<Color>();
		for(int i = 0; i < this.ciudades.size(); i++){
			int red=(int)(Math.random()*255);
			int green=(int)(Math.random()*255);
			int blue=(int)(Math.random()*255);
			
			colores.add(new Color(red, green, blue));
		}
	}
	
	public void mousePressed(MouseEvent e){
		
		x = e.getX();
		y = e.getY();
		
		for(int i = 0; i < this.rectangulos.size(); i++){
			if(this.rectangulos.get(i).contains(x, y)){
				rect = this.rectangulos.get(i);
				point = this.ciudades.get(i);
				x = this.rectangulos.get(i).x - e.getX();
				y = this.rectangulos.get(i).y - e.getY();
				updateLocation(e);
				return;
			}
		}/*
		else if(endpt.contains(x, y)){
		rect = endpt;
		point = end;
		x = endpt.x - e.getX();
		y = endpt.y - e.getY();
		updateLocation(e);
		*/
		pressOut = true;
	}
	
	public void mouseDragged(MouseEvent e){
		if(!pressOut) {
			updateLocation(e);
		}
		
	}
	
	public void mouseReleased(MouseEvent e){
		for(int i = 0; i < this.rectangulos.size(); i++){
			
			if(this.rectangulos.get(i).contains(e.getX(), e.getY())){
				rect = this.rectangulos.get(i);
				point = this.ciudades.get(i);
				updateLocation(e);
				return;
			}
		}/*
		else if(endpt.contains(e.getX(), e.getY())){
		rect = endpt;
		point = end;
		updateLocation(e);
		}
		else {*/
		pressOut = false;
		//       }
	}
	
	public void mouseMoved(MouseEvent e){}
	
	public void mouseClicked(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	
	public void updateLocation(MouseEvent e){
		
		rect.setLocation((x + e.getX())-4, (y + e.getY())-4);
		point.setLocation(x + e.getX(), y + e.getY());
		
		checkPoint();
		
		for(int i = 0; i < this.enlace.size(); i++){
			int indA = this.viajante.getEnlaces().get(i)[0];
			int indB = this.viajante.getEnlaces().get(i)[1];
			this.enlace.get(i).setLine(this.ciudades.get(indA),this.ciudades.get(indB));
		}
		//line.setLine(start, end);  // Pensar como apañar
		repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		update(g);
	}
	
	public void update(Graphics g){
		
		Graphics2D g2 = (Graphics2D)g;
		Dimension dim = getSize();
		int w = dim.width;
		int h = dim.height; 
		
		if(firstTime){
			bi = (BufferedImage)createImage(w, h);
			big = bi.createGraphics();
			
			double lado = Math.ceil(Math.sqrt(this.ciudades.size()));
			double ws = w / lado;
			double hs = h / lado;
			double posw;
			double posh;
			
			for(int i = 0; i < this.ciudades.size();i++){
				//this.ciudades.get(i).setLocation(w/2-50, h/2);
				//posw = w*Math.random();
				posw = (ws*(i%lado)+ws/2*Math.random())%w;
				//posh = h*Math.random();
				posh = (hs*(i/lado)+hs/2*Math.random())%h;
				this.ciudades.get(i).setLocation(posw, posh);
			}
			//start.setLocation(w/2-50, h/2);
			//end.setLocation(w/2+50, h/2);
			
			for(int i = 0; i < this.rectangulos.size();i++){
				this.rectangulos.get(i).setLocation((int)((this.ciudades.get(i).x)-4),
						(int)((this.ciudades.get(i).y)-4));
			}
			//startpt.setLocation((int)((start.x)-4), (int)((start.y)-4));
			//endpt.setLocation((int)((end.x)-4), (int)((end.y)-4));
			//ctrl1.setLocation((int)((one.x)-4), (int)((one.y)-4));
			//ctrl2.setLocation((int)((two.x)-4), (int)((two.y)-4));
			
			for(int i = 0; i < this.enlace.size(); i++){
				int indA = this.viajante.getEnlaces().get(i)[0];
				int indB = this.viajante.getEnlaces().get(i)[1];
				this.enlace.get(i).setLine(this.ciudades.get(indA),this.ciudades.get(indB));
			}
			//line.setLine(start, end);
			big.setColor(Color.black);
			big.setStroke(new BasicStroke(5.0f));
			big.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			area = new Rectangle(dim);
			firstTime = false;
		} 
		
		// Clears the rectangle that was previously drawn.
		big.setColor(Color.white);
		big.clearRect(0, 0, area.width, area.height);
		
		// Draws and fills the newly positioned rectangle to the buffer.
		big.setPaint(Color.BLACK);
		for(int i = 0; i < this.enlace.size(); i++){
			big.draw(this.enlace.get(i));
		}
		
		for(int i = 0; i < this.rectangulos.size();i++){
			Rectangle rect = this.rectangulos.get(i);
			big.setPaint(colores.get(i));
			big.fill(rect);
		}
		
		/*
		 big.setPaint(Color.black);
		 big.draw(line);
		 big.setPaint(Color.red);
		 big.fill(startpt);
		 big.setPaint(Color.magenta);
		 big.fill(endpt);
		 */
		
		// Draws the buffered image to the screen.
		g2.drawImage(bi, 0, 0, this);
		
	}
	/*
	 * Checks if the rectangle is contained within the applet window.  If the rectangle
	 * is not contained withing the applet window, it is redrawn so that it is adjacent
	 * to the edge of the window and just inside the window.
	 */
	
	void checkPoint(){
		
		if (area == null) {
			return;
		}
		
		if((area.contains(rect)) && (area.contains(point))){
			return;
		}
		int new_x = rect.x;
		int new_y = rect.y;
		
		double new_px = point.x;
		double new_py = point.y;
		
		if((rect.x+rect.width)>area.getWidth()){
			new_x = (int)area.getWidth()-(rect.width-1);
		}
		if(point.x > area.getWidth()){
			new_px = (int)area.getWidth()-1;
		}
		if(rect.x < 0){  
			new_x = -1;
		}
		if(point.x < 0){
			new_px = -1;
		}
		if((rect.y+rect.width)>area.getHeight()){
			new_y = (int)area.getHeight()-(rect.height-1);
		}
		if(point.y > area.getHeight()){
			new_py = (int)area.getHeight()-1;
		}
		if(rect.y < 0){  
			new_y = -1;
		}
		if(point.y < 0){
			new_py = -1;
		}
		rect.setLocation(new_x, new_y);
		point.setLocation(new_px, new_py);
		
	}
}

