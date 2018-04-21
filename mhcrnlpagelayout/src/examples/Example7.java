package examples;
import pagelayout.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**

	A simple example of {@link pagelayout.CellGrid CellGrid}. 
	At the top is a regular grid in
	which all the elements of the grid are specified to be panels.
	Below it is a grid in which some of the elements are blank spaces. 
	The example illustrates the use of both of the
	two <code>createCellGrid</code> methods described below.

	

	<center>
	<img src="doc-files\trans.gif" height="50" width="10"/>
	</center>
	<br>

	<center>
	<img src="doc-files\example7.gif" height="568" width="482"/>
	</center>

<pre>

	public static int NUMROWS=5;
	public static int NUMCOLUMNS=6;
	public Example7()
	{

		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container =frame.getContentPane();
		frame.setBounds(100,100,400,600);
		Color topColor=new Color(200,200,255);
		Color bottomColor=new Color(255,200,200);
		

		<b>// Component cells for the top regular grid.</b>
		ComponentCell cells[][]=new ComponentCell[NUMROWS][NUMCOLUMNS];
		for(int i=0;i&ltNUMROWS;i++)
			for(int j=0;j&ltNUMCOLUMNS;j++)
			{
				JPanel p=new JPanel();
				p.setBorder(
				 BorderFactory.createLoweredBevelBorder());
				p.setBackground(topColor);
				cells[i][j]=new ComponentCell(p);
			}
		
		<b>// Create the top grid</b>
		CellGrid topGrid=
			CellGrid.createCellGrid(cells);


		<b>// Component cells for the bottom grid.</b>
		int rowIndices[]={0,0,2,1,4,3};
		int columnIndices[]={2,1,4,3,0,2};
		int n=rowIndices.length;
		ComponentCell bottomCells[]=new ComponentCell[n];
		for(int i=0;i&ltn;i++)
		{
			JPanel p=new JPanel();
			p.setBackground(bottomColor);
				p.setBorder(
				 BorderFactory.createRaisedBevelBorder());
			bottomCells[i]=new ComponentCell(p);
		}
		<b>// Create the bottom grid</b>
		CellGrid bottomGrid=CellGrid.createCellGrid(bottomCells,rowIndices,columnIndices);

		<b>// Put the two grids in a column and create the layout manager.</b>
		Column column=new Column(topGrid,bottomGrid);
		column.createLayout(container);
	
		
		frame.pack();
		frame.setVisible(true);

	}
</pre>
**/

public class Example7 
{
	public static int NUMROWS=5;
	public static int NUMCOLUMNS=6;
	public Example7()
	{

		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container =frame.getContentPane();
		frame.setBounds(100,100,400,600);
		Color topColor=new Color(200,200,255);
		Color bottomColor=new Color(255,200,200);
		

		// Component cells for the top regular grid.
		ComponentCell cells[][]=new ComponentCell[NUMROWS][NUMCOLUMNS];
		for(int i=0;i<NUMROWS;i++)
			for(int j=0;j<NUMCOLUMNS;j++)
			{
				JPanel p=new JPanel();
				p.setBorder(
				 BorderFactory.createLoweredBevelBorder());
				p.setBackground(topColor);
				cells[i][j]=new ComponentCell(p);
			}
		
		CellGrid topGrid=
			CellGrid.createCellGrid(cells);


		// Component cells for the bottom grid.
		int rowIndices[]={0,0,2,1,4,3};
		int columnIndices[]={2,1,4,3,0,2};
		int n=rowIndices.length;
		ComponentCell bottomCells[]=new ComponentCell[n];
		for(int i=0;i<n;i++)
		{
			JPanel p=new JPanel();
			p.setBackground(bottomColor);
				p.setBorder(
				 BorderFactory.createRaisedBevelBorder());
			bottomCells[i]=new ComponentCell(p);
		}
		CellGrid bottomGrid=CellGrid.createCellGrid(bottomCells,							rowIndices,columnIndices);

		// Put the two grids in a column and create the layout manager.
		Column column=new Column(topGrid,bottomGrid);
		column.createLayout(container);
	
		
		frame.pack();
		frame.setVisible(true);

	}
	public static void main(String args[])
	{
		Example7 ex=new Example7();
	}
	public static void run()
	{
		Example7 ex=new Example7();
	}
}

