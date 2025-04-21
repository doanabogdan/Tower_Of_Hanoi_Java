import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import static org.junit.Assert.*;
import static org.junit.Assert.fail;

public class Tower extends JPanel implements MouseListener,MouseMotionListener{
    
    public static final long serialVersionUID=0xff;
    private static long bestTime = -1;
    private long startTime = -1;
    private long winTime = -1;
    private Stack<Rectangle2D.Double> s[]=new Stack[3];
    private Stack<Color> disk_color[]=new Stack[3];
    private static Rectangle2D.Double top=null;
    private Color top_color=null;
    private double ax,ay,w,h;
    private boolean draggable=false,firstTime=false;
    private Image backgroundImage;
    
    private int numberOfDisks;
    private int[] disks;

    public Tower(int numberOfDisks) {
        this.numberOfDisks = numberOfDisks;
        disks = new int[numberOfDisks];
        for (int i = 0; i < numberOfDisks; i++) {
            disks[i] = i;
        }
    }

    public long getBestTime() {
        return bestTime;
    }
    
    public void updateBestTime(long newTime) {
        if (bestTime == -1 || newTime < bestTime) {
            bestTime = newTime;
        }
    }
    
    public int getNumberOfDisks() {
        return numberOfDisks;
    }

    public int getNumberOfDisksOnTower(int tower) {
        return disks[tower];
    }

    public void moveDisk(int fromTower, int toTower) {
        if (disks[fromTower] > disks[toTower]) {
            throw new IllegalArgumentException("Discul de pe turnul sursă trebuie să fie mai mic sau egal cu discul de pe turnul țintă");
        }

        int temp = disks[fromTower];
        disks[fromTower] = disks[toTower];
        disks[toTower] = temp;
    }
    
    
    
    
    
    public Tower() {
     startTime = System.currentTimeMillis();
     firstTime=true;
     init(3);
     addMouseListener(this);
     addMouseMotionListener(this);
    }
    
    public void init(int val){
      Color c[]={Color.lightGray,Color.orange,Color.green,Color.magenta,Color.cyan,Color.pink,Color.blue,Color.red,Color.yellow};
      
      String imagePath = "C:\\Users\\doana\\eclipse-workspace\\ProiectIP\\icon\\panda.jpg"; // Schimbă această cale cu calea către imaginea de fundal din calculator
      backgroundImage = new ImageIcon(imagePath).getImage();
      
      s[0]=new Stack<Rectangle2D.Double>();
      s[1]=new Stack<Rectangle2D.Double>();
      s[2]=new Stack<Rectangle2D.Double>();
      
      disk_color[0]=new Stack<Color>();
      disk_color[1]=new Stack<Color>();
      disk_color[2]=new Stack<Color>();
            
      for (int i = 0; i<val; i++) {
      Rectangle2D.Double r=new Rectangle2D.Double();
   
     double x = getWidth()/6 ;
     x = (x == 0)?109 : x;
     double wr = val*25-20*i;
     r.setFrame(x-wr/2,190-i*20,wr,20);
        s[0].push(r);
        disk_color[0].push(c[i]);
      }
     
     top=null;
     top_color=null;
     ax=0.0; ay=0.0;  w=0.0;  h=0.0;
     draggable=false;
     repaint();
    }
    
    public void mouseClicked(MouseEvent ev){}
    
    public void mousePressed(MouseEvent ev){
     Point pos=ev.getPoint();
     int n=current_tower(pos);
     if(!s[n].empty()){
         top=s[n].peek();
         if(top.contains(pos)){
          top=s[n].pop();
          top_color=disk_color[n].pop();
          ax=top.getX();    ay=top.getY();
          w=pos.getX()-ax;   h=pos.getY()-ay;
          draggable=true;  //allowing dragging if current mouse position is in top disk
         }
         else{
          top=null;
          top_color=Color.black;
          draggable=false;
         }
     }
    }
    
    public void mouseReleased(MouseEvent ev){
        if (top != null && draggable == true) {
            int tower = current_tower(ev.getPoint());
            double x, y;
            if (!s[tower].empty()) {
                if (s[tower].peek().getWidth() > top.getWidth()) {
                    y = s[tower].peek().getY() - 20;  // calculăm ay pentru discul trasat pentru plasare
                } else {
                    JOptionPane.showMessageDialog(this, "Mutare greșită", "Tower Of Hanoi", JOptionPane.ERROR_MESSAGE);
                    tower = current_tower(new Point((int) ax, (int) ay));
                    if (!s[tower].empty()) {
                        y = s[tower].peek().getY() - 20;
                    } else {
                        y = getHeight() - 40;
                    }
                }
            } else {
                y = getHeight() - 40;  // dacă nu există disc anterior în turn
            }

            x = (int) (getWidth() / 6 + (getWidth() / 3) * tower - top.getWidth() / 2);
            top.setFrame(x, y, top.getWidth(), top.getHeight());
            s[tower].push(top);
            disk_color[tower].push(top_color);

            top = null;
            top_color = Color.black;
            draggable = false;
            repaint();

            if (checkWin(3) || checkWin(4) || checkWin(5)) {
                // Nu mai afișăm mesajul aici, ci doar în checkWin
            }
        }
    }

    private boolean checkWin(int numDisks) {
        boolean hasWon = s[2].size() == numDisks;
        if (hasWon && winTime == -1) { // Verificăm dacă nu s-a înregistrat deja câștigul
            winTime = System.currentTimeMillis();
            long elapsedTime = (winTime - startTime) / 1000;

            // Actualizăm cel mai scurt timp înregistrat
            updateBestTime(elapsedTime);

            JOptionPane.showMessageDialog(this, "Felicitări, ai câștigat! Timp: " + elapsedTime + " secunde", "Tower Of Hanoi", JOptionPane.INFORMATION_MESSAGE);
            resetGame();
        }
        return hasWon;
    }
    
    public void mouseEntered(MouseEvent ev){}
    
    public void mouseExited(MouseEvent ev){}
    
    public void mouseMoved(MouseEvent ev){}
    
    public void mouseDragged(MouseEvent ev){
     int cx=ev.getX();   //getting current mouse position
     int cy=ev.getY();
     if(top!=null && draggable==true){
        top.setFrame(cx-w,cy-h,top.getWidth(),top.getHeight());
        repaint();  //repainting if dragging a disk
     }
    }
    
   @Override
    public void paintComponent(Graphics g){
     Graphics2D g2d=(Graphics2D)g;
     if (backgroundImage != null) {
         g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
     } else {
         g2d.setColor(Color.black);
         g2d.fillRect(0, 0, getWidth(), getHeight());
     }
     
     int holder_x=getWidth()/6;
     int holder_y1=getHeight()-10*20,holder_y2=getHeight()-20;
     
     g2d.setColor(Color.orange);
     g2d.setStroke(new BasicStroke(5)); 
     g2d.drawLine(holder_x,holder_y1,holder_x,holder_y2);
     g2d.drawLine(3*holder_x,holder_y1,3*holder_x,holder_y2);
     g2d.drawLine(5*holder_x,holder_y1,5*holder_x,holder_y2);
     g2d.drawLine(0,holder_y2,getWidth(),holder_y2);
     
     g2d.setStroke(new BasicStroke(1));
     
       g2d.setColor(top_color);
     
       if(draggable==true && top!=null)
      g2d.fill(top);  //drawing dragged disk
     
     drawtower(g2d,0); //drawing disks of each tower
     drawtower(g2d,1);
     drawtower(g2d,2);
    }
    
    private void drawtower(Graphics2D g2d,int n){
    	if (!s[n].empty()) {
            for (int i = 0; i < s[n].size(); i++) {
                g2d.setColor(disk_color[n].get(i));

                // Schimbă Rectangle2D.Double cu RoundRectangle2D.Double
                RoundRectangle2D.Double roundRect = new RoundRectangle2D.Double();
                roundRect.setRoundRect(
                        s[n].get(i).getX(),  // x
                        s[n].get(i).getY(),  // y
                        s[n].get(i).getWidth(),  // width
                        s[n].get(i).getHeight(),  // height
                        10, 10); // raza pentru colțurile rotunjite (poți ajusta aceste valori)

                g2d.fill(roundRect);
            }
        }
    }
    
    public void resetGame() {
    	startTime = System.currentTimeMillis(); // Repornim cronometrul
        winTime = -1; // Resetăm winTime la -1 pentru a permite verificarea corectă a câștigului
        init(3); // Sau valoarea pe care dorești să o ai pentru inițializarea jocului
        repaint();
    }
    
    private int current_tower(Point p){ //return current tower position with respect to Point p
     Rectangle2D.Double
      rA=new Rectangle2D.Double(),
      rB=new Rectangle2D.Double(),
      rC=new Rectangle2D.Double();
      
     rA.setFrame(0,0,getWidth()/3,getHeight());
     rB.setFrame(getWidth()/3,0,getWidth()/3,getHeight());
     rC.setFrame(2*getWidth()/3,0,getWidth()/3,getHeight());
     
     if(rA.contains(p))
      return 0;
     else if(rB.contains(p))
      return 1;
     else if(rC.contains(p))
      return 2;
     else
      return -1;
    }
    
}