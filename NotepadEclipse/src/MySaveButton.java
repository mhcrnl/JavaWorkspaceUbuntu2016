import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;


public class MySaveButton extends JButton implements ActionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JFileChooser fileChooser = new JFileChooser();
	JFrame frame = new JFrame();
	//JTextArea textArea = new JTextArea();
	
	public MySaveButton(String text){
		super.setText(text);
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		//System.exit(0);
		if(JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(frame)){
			File file = fileChooser.getSelectedFile();
			PrintWriter out = null;
			try {
				out = new PrintWriter(file);
				String output = Notepad.textArea.getText();
				System.out.println(output);
				out.println(output);
			}catch (Exception ex){
				ex.printStackTrace();
			}finally {
				try {out.flush();} catch(Exception ex1){}
				try {out.close();} catch(Exception ex1){}
			}
			
		}
	}
	

}
