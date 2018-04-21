import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class AgeCalculatorGui extends JFrame implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		/**
		 * Setarea unei ferestre nu mai era necesara initializarea, deorece mostenea
		 */
		JFrame fereastra = new JFrame(); 
		fereastra.setTitle("Calculator Varsta");
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2,5,5));
		
		panel.add(new JLabel("Numele Complet:"));
		JTextField numeComplet = new JTextField();
		panel.add(numeComplet);
		
		panel.add(new JLabel("An nastere: "));
		JTextField anNastere = new JTextField();
		panel.add(anNastere);
		
		panel.add(new JLabel("Luna nasterii: "));
		JTextField lunaNasterii = new JTextField();
		panel.add(lunaNasterii);
		
		panel.add(new JLabel("Ziua nasterii: "));
		JTextField ziNastere = new JTextField();
		panel.add(ziNastere);
		
		MyExitButton exitButon = new MyExitButton("Exit");
		panel.add(exitButon);
		panel.add(exitButon);
		
		fereastra.add(panel);
		fereastra.setSize(300, 300);
		fereastra.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//fereastra.pack();
		fereastra.setVisible(true);
		
	}
	public static void main(String[] args){
		SwingUtilities.invokeLater(new AgeCalculatorGui());
	}

}
