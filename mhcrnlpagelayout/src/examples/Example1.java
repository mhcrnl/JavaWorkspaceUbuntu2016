package examples;
import pagelayout.*;

import java.awt.*;

import javax.swing.*;

/**
	Simplest example of using PageLayout.
	Displays two panels in center of the screen. The size of the panels
	is linked to the size of the container by means of calls to 
	the {@link pagelayout.PageLayout#linkToContainerWidth linkToContainerWidth}
	and {@link pagelayout.PageLayout#linkToContainerHeight linkToContainerHeight }
	methods.

	<center>
	<img src="doc-files\trans.gif" height="50" width="10"/>
	</center>
	<br>

	<center>
	<img src="doc-files/example1.gif"/>
	</center>

<pre>
	public static void createGUI()
	{

		<b>// Main Frame</b>
		JFrame frame=new JFrame();
		frame.setBounds(new Rectangle(100,100,600,600));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Container container=frame.getContentPane();

		<b>// Create Components</b>
		JPanel center=new JPanel();
		center.setBorder(BorderFactory.createLineBorder(Color.black,2));
		center.setBackground(new Color(200,200,255));
		center.setPreferredSize(new Dimension(300,300));
		JPanel bottom=new JPanel();
		bottom.setBorder(BorderFactory.createLineBorder(Color.black,2));
		bottom.setBackground(new Color(255,200,200));

		<b>// Top level cell. row</b>
		Column topLevel=new Column();

		<b>// First row</b>
		topLevel.newRow(Cell.CENTER,Cell.NO_ALIGNMENT,center);

		<b>// Second row</b>
		topLevel.newRow(Cell.CENTER,Cell.NO_ALIGNMENT,bottom);


		<b>// create the layout</b>
		PageLayout lm=topLevel.createLayout(container);

		<b>// Constrain component sizes to be fractions of container size.</b>
		lm.linkToContainerWidth(new Component[]{center,bottom},
					new double[][]{{.9},{.60}});
		lm.linkToContainerHeight(new Component[]{center,bottom},
					new double[][]{{.5},{.5,-20}});

		frame.show();
	}
<pre>
**/

public class Example1
{
	@SuppressWarnings("deprecation")
	public static void createGUI()
	{

		// Main Frame
		JFrame frame=new JFrame();
		frame.setBounds(new Rectangle(100,100,600,600));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Container container=frame.getContentPane();

		// Create Components
		JPanel center=new JPanel();
		center.setBorder(BorderFactory.createLineBorder(Color.black,2));
		center.setBackground(new Color(200,200,255));
		center.setPreferredSize(new Dimension(300,300));
		JPanel bottom=new JPanel();
		bottom.setBorder(BorderFactory.createLineBorder(Color.black,2));
		bottom.setBackground(new Color(255,200,200));

		Column topLevel=new Column();

		// First row
		topLevel.newRow(Cell.CENTER,Cell.NO_ALIGNMENT,center);
		// Second row
		topLevel.newRow(Cell.CENTER,Cell.NO_ALIGNMENT,bottom);


		// create the layout
		PageLayout lm=topLevel.createLayout(container);
		lm.linkToContainerWidth(new Component[]{center,bottom},
					new double[][]{{.9},{.60}});
		lm.linkToContainerHeight(new Component[]{center,bottom},
					new double[][]{{.5},{.5,-20}});

		frame.show();
	}
	public static void main(String args[])
	{
		createGUI();
	}
	public static void run()
	{
		createGUI();
	}
}

