import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Run implements ActionListener{
    
    private static JFrame f = new JFrame();
    private static Tower t;
    private static JMenuBar menu_bar=new JMenuBar();
    
    private JMenuItem
     new_game=new JMenuItem("New Game"),
     best_time=new JMenuItem("Best Time"),
     exit=new JMenuItem("Exit"),
     about=new JMenuItem("How to play");
     
    private JMenu
     help=new JMenu("Help"),
     game=new JMenu("Game");
     
    public Run(String title) {
    	best_time.addActionListener(this);
 f.setTitle(title);
     build();
    }
    
    public void actionPerformed(ActionEvent ev){
     if(ev.getSource()==new_game){
      Object values[]= { 3,4,5,6,7,8,9 };
      Object val=JOptionPane.showInputDialog(f,"Number of disks : ","Input",
                 JOptionPane.INFORMATION_MESSAGE,null,values,values[0]);
      if((int)val!=JOptionPane.CANCEL_OPTION)
       t.init((int)val);
     }
     else if(ev.getSource()==exit)
      System.exit(0);
    }
    
    
    
    private void build(){
     
     game.add(new_game);
     game.add(best_time);
     game.add(exit);
     help.add(about);
     
     menu_bar.add(game);
     menu_bar.add(help);
     
     new_game.addActionListener(this);
     exit.addActionListener(this);
     
     best_time.addActionListener(new ActionListener() {
    	 public void actionPerformed(ActionEvent ev) {
    	        if (ev.getSource() == best_time) {
    	            Tower tower = new Tower(); // Creează o nouă instanță a clasei Tower
    	            long bestTime = tower.getBestTime(); // Folosește această instanță pentru a apela metoda getBestTime()
    	            if (bestTime != -1) {
    	                JOptionPane.showMessageDialog(f, "Cel mai scurt timp: " + bestTime + " secunde", "Best Time", JOptionPane.INFORMATION_MESSAGE);
    	            } else {
    	                JOptionPane.showMessageDialog(f, "Nu există un cel mai scurt timp înregistrat.", "Best Time", JOptionPane.INFORMATION_MESSAGE);
    	            }
    	        }
    	    }
    	});
     
     about.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent ev) {
             String helpText = "Mutați toate discurile stivuite pe primul turn în ultimul turn folosind un turn de ajutor în mijloc. La mutarea discurilor, trebuie respectate anumite reguli. Acestea sunt :\n\n" +
                               "1. Numai un singur disc poate fi mutat.\n" +
                               "2. Un disc mai mare nu poate fi plasat pe un disc mai mic. \n \n" +
                               
								"Move all the disks stacked on the first tower over to the last tower using a helper tower in the middle. While moving the disks, certain rules must be followed. These are :\n\n" +
								"1. Only one disk can be moved.\n" +
								"2. A larger disk can not be placed on a smaller disk.";

                               

             JOptionPane.showMessageDialog(f, helpText, "Ajutor", JOptionPane.INFORMATION_MESSAGE);
         }
         
     });
  
 f.setJMenuBar(menu_bar);
        f.setSize(660, 280); 
        f.setResizable(false);
        f.setLocationRelativeTo(null); 
        f.setVisible(true);
 f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    } 
    
    
    
    public static void main(String[] args) {
    SwingUtilities.invokeLater(
       () -> {
             Run obj = new Run("Tower Of Hanoi v1.0");
         t=new Tower();
                f.getContentPane().add(t);
  });
    
    
    }
}