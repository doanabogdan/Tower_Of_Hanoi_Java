import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu {
    private JFrame frame;
    private Tower towerGame; // Declarația instanței de Tower

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainMenu window = new MainMenu();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainMenu() {
        initialize();
    }

    private void initialize() {
    	 frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnNewButton = new JButton("New Game");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        btnNewButton.setBounds(114, 92, 207, 50);
        frame.getContentPane().add(btnNewButton);

        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose(); // Închide fereastra MainMenu
            }
        });
        btnExit.setBounds(114, 164, 207, 50);
        frame.getContentPane().add(btnExit);

        JLabel lblNewLabel = new JLabel("Tower of Hanoi");
        lblNewLabel.setFont(new Font("Gabriola", Font.PLAIN, 40));
        lblNewLabel.setBounds(107, 32, 221, 72);
        frame.getContentPane().add(lblNewLabel);
    }
}