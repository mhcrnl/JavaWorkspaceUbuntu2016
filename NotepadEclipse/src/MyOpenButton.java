import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class MyOpenButton extends JButton implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JFileChooser fileChooser = new JFileChooser();
	JFrame frame = new JFrame();
	
	public MyOpenButton(String text){
		super.setText(text);
		addActionListener(this);
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(frame)) {
			File file = fileChooser.getSelectedFile();
			Notepad.textArea.setText("");
			Scanner in = null;
			try {
				in = new Scanner(file);
				while(in.hasNext()) {
					String line = in.nextLine();
					Notepad.textArea.append(line+"\n");
				}
			}catch (Exception ex){
				ex.printStackTrace();
			}finally {
				in.close();
			}
		}
		
	}

}
