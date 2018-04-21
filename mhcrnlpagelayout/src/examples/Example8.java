package examples;
import pagelayout.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**
	This example combines all the examples in the
	<a href='http://java.sun.com/docs/books/tutorial/uiswing/layout/spring.html'>
	Java SpringLayout Tutorial</a> in a single frame. The obvious manner in
	which the tutorial examples could be combined (if the intent
	was to use <code>SpringLayout</code>)  would be to have independent
	<code>JPanels</code> for each of the three different layouts, attach a separate
	<code>SpringLayout</code> manager for each of these panels, and then specify
	the layout for the composite container. From a certain perspective, the solution
	shown here might appear to be considerably simpler.

	<center>
	<img src="doc-files\trans.gif" height="50" width="10"/>
	</center>
	<br>

	<center>
	<img src="doc-files\example8.gif" height="674" width="512"/>
	</center>

<pre>
	public static int NUMROWS=10;
	public static int NUMCOLUMNS=10;
	public Example8()
	{

		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container =frame.getContentPane();
		frame.setBounds(100,100,400,600);

		<b>// The main column</b>
		Column mainColumn=new Column(Cell.CENTER,Cell.CENTER);

		<b>// The top row of buttons</b>
	        JButton b1=new JButton("Button1");	
	        JButton b2=new JButton("Button2");	
	        JButton b3=new JButton("Button3");	
	        JButton b4=new JButton("Long Named Button 4");	
	        JButton b5=new JButton("5");
		Row row=new Row(b1,b2,b3,b4,b5);
		mainColumn.add(row);

		<b>// The entry form in the middle</b>
		String text[]={"Name:","Fax:","Email:","Address:"};
		int numText=text.length;
		Cell[][] midCells=new Cell[numText][2];
		for(int i=0;i &lt numText;i++)
		{
			JLabel label=new JLabel(text[i],SwingConstants.RIGHT); 
			JTextField f=new JTextField(15);
			Cell left=new ComponentCell(label);
			Cell right=new ComponentCell(f);
			right.setFixedSize(f,true);
			midCells[i][0]=left;
			midCells[i][1]=right;
		}
		mainColumn.add(CellGrid.createCellGrid(midCells));
		

		<b>// Component cells for the bottom regular grid.</b>
		ComponentCell cells[][]=new ComponentCell[NUMROWS][NUMCOLUMNS];
		for(int i=0;i &lt NUMROWS;i++)
			for(int j=0;j &lt NUMCOLUMNS;j++)
			{
				int x=(int)Math.pow(i,j);
				JTextField f=new JTextField(
						Integer.toString(x));
				cells[i][j]=new ComponentCell(f);
				cells[i][j].setFixedHeight(f,true);
			}
		
		CellGrid regularGrid=
			CellGrid.createCellGrid(cells);
		mainColumn.add(regularGrid);

		<b>// create the layout.</b>

		mainColumn.createLayout(container);
	
		
		frame.pack();
		frame.setVisible(true);

	}

	
</pre>
**/

public class Example8 
{
	public static int NUMROWS=10;
	public static int NUMCOLUMNS=10;
	public Example8()
	{

		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container =frame.getContentPane();
		frame.setBounds(100,100,400,600);

		// The main column
		Column mainColumn=new Column(Cell.CENTER,Cell.CENTER);

		// The top row of buttons
	        JButton b1=new JButton("Button1");	
	        JButton b2=new JButton("Button2");	
	        JButton b3=new JButton("Button3");	
	        JButton b4=new JButton("Long Named Button 4");	
	        JButton b5=new JButton("5");
		mainColumn.newRow(b1,b2,b3,b4,b5);

		// The entry form in the middle
		String text[]={"Name:","Fax:","Email:","Address:"};
		int numText=text.length;
		Cell[][] midCells=new Cell[numText][2];
		for(int i=0;i<numText;i++)
		{
			JLabel label=new JLabel(text[i],SwingConstants.RIGHT); 
			JTextField f=new JTextField(15);
			Cell left=new ComponentCell(label);
			Cell right=new ComponentCell(f);
			right.setFixedSize(f,true);
			midCells[i][0]=left;
			midCells[i][1]=right;
		}
		mainColumn.add(CellGrid.createCellGrid(midCells));
		

		// Component cells for the bottom regular grid.
		ComponentCell cells[][]=new ComponentCell[NUMROWS][NUMCOLUMNS];
		for(int i=0;i<NUMROWS;i++)
			for(int j=0;j<NUMCOLUMNS;j++)
			{
				int x=(int)Math.pow(i,j);
				JTextField f=new JTextField(
						Integer.toString(x));
				cells[i][j]=new ComponentCell(f);
				cells[i][j].setFixedHeight(f,true);
			}
		
		CellGrid regularGrid=
			CellGrid.createCellGrid(cells);

		mainColumn.add(regularGrid);
		// create the layout.

		mainColumn.createLayout(container);
	
		
		frame.pack();
		frame.setVisible(true);

	}
	public static void main(String args[])
	{
		Example8 ex=new Example8();
	}
	public static void run()
	{
		Example8 ex=new Example8();
	}
}

