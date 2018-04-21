package examples; 
import pagelayout.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import static pagelayout.EasyCell.*;
/**

        This example illustrates the use of 
	{@link pagelayout.CellGrid CellGrid} to arrange the
	 cells in a rectangular grid. The use of <code>CellGrid</code> is 
	indicated here 
	because the two columns  around the white panel and the row
	below it are all required to align with it. Without the two side
	columns, it would be sufficient to create a single column containing
	 the main white panel and the bottom row of buttons. Likewise, without
	the bottom row of buttons, it would be sufficient to create a 
	single row of the three columns on the top. 

	<center>
	<img src="doc-files\trans.gif" height="50" width="10"/>
	</center>
	<br>

	<center>
	<img src="doc-files/example4.gif" width="600" height="400"/>
</center>
<br>
<b>Note that the code requires that the static methods of the class <code>EasyCell</code> be imported using the <code>import static pagelayout.EasyCell.*</code>
statement.</b>

<pre>
	public static void createGUI()
	{
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container=frame.getContentPane();

		<b>// Top Left column</b>
		JLabel j1=new JLabel("java1");
		JLabel j2=new JLabel("java2");
		JLabel j3=new JLabel("java3");
		JTextField j4=new JTextField();


		<b>// Top middle column</b>
		JPanel midpanel=new JPanel();
		midpanel.setBackground(Color.white);
		midpanel.setBorder(
		 BorderFactory.createLineBorder(Color.blue,2));

		<b>// Bottom middle column</b>
		JButton b1=new JButton("Button1");
		JButton b2=new JButton("Button2");
		JButton b3=new JButton("Button3");
		JButton b4=new JButton("Button4");
		JButton b5=new JButton("Button5");
		JButton b6=new JButton("Button6");
		JButton b7=new JButton("Button7");


		<b>// Top right Column</b>
		JPanel c1=new JPanel();c1.setBackground(Color.blue);
		JPanel c2=new JPanel();c2.setBackground(Color.red);
		JPanel c3=new JPanel();c3.setBackground(Color.green);
		JPanel c4=new JPanel();c4.setBackground(Color.white);
		c4.setBorder(
		 BorderFactory.createLineBorder(Color.black,1));
		

		JPanel d1=new JPanel();d1.setBackground(Color.green);
		JPanel d2=new JPanel();d2.setBackground(Color.white);
		JPanel d3=new JPanel();d3.setBackground(Color.yellow);
		JPanel d4=new JPanel();d4.setBackground(Color.cyan);
		d2.setBorder(
		 BorderFactory.createLineBorder(Color.black,1));

		<b>// Create the grid</b>
		CellGrid eg=grid(
			column(right,center,j1,j2,j3,j4),
			midpanel,
			column(none,justified,
					row(column(c1,c2),column(c3,c4)),
					row(column(d1,d2),column(d3,d4))),
			eol(),
			skip(),
			row(justified,none,
				row(column(b1,b2),column(b6,b7)),
				row(b3,b4,b5)),	
			skip());

		eg.linkWidth(b1,1,b2,b6,b7,c1,c2,c3,c4,d1,d2,d3,d4);
		eg.linkHeight(b1,1,b2,b6,b7,c1,c2,c3,c4,d1,d2,d3,d4);
		eg.linkWidth(j3,2,j4);
		eg.setFixedWidth(true,j4);

		<b>// Create the layout</b>
		eg.createLayout(container);

		<b>// pack and show</b>
		frame.pack();
		frame.setSize(frame.getPreferredSize());
		frame.show();
	}
<pre>
	
**/
public class Example4
{
	
	public static void createGUI()
	{
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container=frame.getContentPane();

		// Top Left column
		JLabel j1=new JLabel("java1");
		JLabel j2=new JLabel("java2");
		JLabel j3=new JLabel("java3");
		JTextField j4=new JTextField();

		JPanel midpanel=new JPanel();
		midpanel.setBackground(Color.white);
		midpanel.setBorder(
		 BorderFactory.createLineBorder(Color.blue,2));


		// Bottom middle column
		JButton b1=new JButton("Button1");
		JButton b2=new JButton("Button2");
		JButton b3=new JButton("Button3");
		JButton b4=new JButton("Button4");
		JButton b5=new JButton("Button5");
		JButton b6=new JButton("Button6");
		JButton b7=new JButton("Button7");


		// Top right Column
		JPanel c1=new JPanel();c1.setBackground(Color.blue);
		JPanel c2=new JPanel();c2.setBackground(Color.red);
		JPanel c3=new JPanel();c3.setBackground(Color.green);
		JPanel c4=new JPanel();c4.setBackground(Color.white);
		c4.setBorder(
		 BorderFactory.createLineBorder(Color.black,1));

		JPanel d1=new JPanel();d1.setBackground(Color.green);
		JPanel d2=new JPanel();d2.setBackground(Color.white);
		JPanel d3=new JPanel();d3.setBackground(Color.yellow);
		JPanel d4=new JPanel();d4.setBackground(Color.cyan);
		d2.setBorder(
		 BorderFactory.createLineBorder(Color.black,1));


		// Create the grid
		CellGrid eg=grid("left","top",
			column(right,center,j1,j2,j3,j4),
			midpanel,
			column(none,justified,
					row(column(c1,c2),column(c3,c4)),
					row(column(d1,d2),column(d3,d4))),
			eol(),
			skip(),
			row(justified,none,
				row(column(b1,b2),column(b6,b7)),
				row(b3,b4,b5)),	
			skip());
		eg.linkWidth(b1,1,b2,b6,b7,c1,c2,c3,c4,d1,d2,d3,d4);
		eg.linkHeight(b1,1,b2,b6,b7,c1,c2,c3,c4,d1,d2,d3,d4);
		eg.linkWidth(j3,2,j4);
		eg.setFixedWidth(true,j4);

		// Create the layout
		eg.createLayout(container);

		// pack and show
		frame.pack();
		frame.setSize(frame.getPreferredSize());
		frame.show();
	}
	public static void main(String[] args)
	{
		createGUI();
	}
	public static void run()
	{
		createGUI();
	}
}

