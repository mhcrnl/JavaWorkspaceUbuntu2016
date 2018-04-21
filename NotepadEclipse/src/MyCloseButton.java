import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


public class MyCloseButton extends JButton implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MyCloseButton(String text){
		super.setText(text);
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		System.exit(0);
		
	}

}
