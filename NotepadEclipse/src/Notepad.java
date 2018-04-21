import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Panel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;


public class Notepad extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	static JTextArea textArea;
	static JFileChooser fileChooser;
	static JFrame frame;
	
	public Notepad(){
		initUI();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		Panel p = new Panel();
		p.setLayout(new BorderLayout());
		
		Panel flowLayoutPanel = new Panel();
		flowLayoutPanel.setLayout(new FlowLayout());
		
		textArea = new JTextArea();
		
		MyCloseButton exitButton = new MyCloseButton("Exit");
		/*
		exit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
					
		});
		*/
		MyOpenButton saveButton = new MyOpenButton("Open");
		/*
		saveButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		
		});
		*/
		MySaveButton mySaveButton =new MySaveButton("Save");
		//setVisible(mb);
		p.add(flowLayoutPanel, BorderLayout.SOUTH);
		flowLayoutPanel.add(exitButton);
		flowLayoutPanel.add(saveButton);
		flowLayoutPanel.add(mySaveButton);
		p.add(textArea, BorderLayout.CENTER);   
		add(p);
		
		createMenu();
		
		setTitle("Text Editor");
		setSize(600, 600);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	private void createMenu() {
		// TODO Auto-generated method stub
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		mb.setVisible(true);
		
		JMenu menu = new JMenu("File");
		mb.add(menu);
		//mb.getComponent();
		menu.add(new JMenuItem("Exit"));
		
		
	}

	public static void main (String[] args){
		EventQueue.invokeLater(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Notepad n =new Notepad();
				n.setVisible(true);
			}
			
		});
	}
}
