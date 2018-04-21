package examples;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import pagelayout.*; 
/**
	<p>
	This is another example of using {@link pagelayout.GridRows GridRows}
	to create a grid. It uses  
	{@link pagelayout.CellGrid#setRowMargins setRowMargins} to set
	left and right margins.</p>

	<p>
	The example has been taken from a tutorial for 
	<a href='http://www.jgoodies.com/freeware/forms/index.html'>
	FormLayout</a>.
	</p>


	<center>
	<img src="doc-files/trans.gif" height="50" width="10"/>
	</center>
	<br>

	<center>
	<img src="doc-files/example13.gif" width="446" height="236"/>
	</center>

<pre>
	<b>// A utility function to create a textfield with
	// specified number of columns and a bevel border.</b>
	public static JTextField createTextField(int n)
	{
		JTextField ed=new JTextField(n);
		ed.setBorder(BorderFactory.createLoweredBevelBorder());
		return ed;
	}
	<b>// A utility function to create a row of a 
	// separator of fixed height with a leading label.</b>
	public static Row createNamedSeparator(String text)
	{
		Row row=new Row(Cell.JUSTIFIED,Cell.CENTER);
		row.add(new JLabel(text));
		JSeparator sep=new JSeparator();
		row.add(sep);
		Dimension d=sep.getPreferredSize();
		sep.setMaximumSize(new Dimension(Cell.MAX,d.height));
		return row;
	}
	<b>// A utility function to add a label
	// and an editor to a GridRow object.</b>
	public static void addLabelAndEditor(
			GridRow row, String text, int n)
	{
		row.add(Cell.RIGHT,Cell.CENTER,new JLabel(text));
		row.add(Cell.NO_ALIGNMENT,Cell.CENTER,createTextField(n));
	}
	public static void createGUI()
	{

		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container=frame.getContentPane();

		<b>// GridRows object</b>
		GridRows rows=new GridRows();
		
		
		
		<b>// Row 1 The top separator.</b>
		<b>// The Row (cell) containing the label
		// and the separator spans all the four columns of the grid.</b>
		rows.newRow().add(createNamedSeparator("General")).span(4);

		<b>// Row 2 The 'Company' label and editor.</b>
		GridRow row=rows.newRow();
		addLabelAndEditor(row,"Company",20);
		<b>// The editor spans three columns of the grid.</b>
		row.span(3);

		<b>// Row 3 The 'Contact' label and editor</b>
		row=rows.newRow();
		addLabelAndEditor(row,"Contact",20);
		<b>// The editor spans three columns of the grid.</b>
		row.span(3);

		<b>// Row 4 An empty row for vertical spacing.</b>
		<b>// It is enough to add a vertical gap in
		// the first column. The trailing blanks in 
		// the row are automaticall created.</b>
		rows.newRow().add(new Gap(10));

		<b>// Row 5 The 'Propeller' separator.</b>
		<b>// The Row (cell) containing the label
		// and the separator spans all the four columns of the grid.</b>
		rows.newRow().add(createNamedSeparator("Propeller")).span(4);

		<b>// Row 6 The 'PTI' and 'Power' labels and editors in the same row.</b>
		row=rows.newRow();
		addLabelAndEditor(row,"PTI[kW]",10);
		addLabelAndEditor(row,"  Power[kW]",10);
		

		<b>// Row 7 The 'R' and 'D' labels and editors in the same row.</b>
		row=rows.newRow();
		addLabelAndEditor(row,"R[mm]",10);
		addLabelAndEditor(row,"  D[mm]",10);


		CellGrid cellgrid=rows.createCellGrid();

		<b>// Left row margins for the rows with the editors.</b> 
		<b>// Sets Left Margin Size = 20 for rows 1,2,5, and 6.</b> 
		cellgrid.setRowMargins(Cell.LEFT,20,1, 2, 5, 6);

		<b>// Right row margins for the rows with the editors.</b> 
		<b>// Sets Right Margin Size = 20 for rows 1,2,5, and 6.</b> 
		cellgrid.setRowMargins(Cell.RIGHT,20,1, 2, 5, 6);

		cellgrid.createLayout(container);


		frame.pack();
		frame.setSize(frame.getPreferredSize());
		frame.show();
	}
</pre>
**/

public class Example13
{
	public static JTextField createTextField(int n)
	{
		JTextField ed=new JTextField(n);
		ed.setBorder(BorderFactory.createLoweredBevelBorder());
		return ed;
	}
	public static Row createNamedSeparator(String text)
	{
		Row row=new Row(Cell.JUSTIFIED,Cell.CENTER);
		row.add(new JLabel(text));
		JSeparator sep=new JSeparator();
		row.add(sep);
		Dimension d=sep.getPreferredSize();
		sep.setMaximumSize(new Dimension(Cell.MAX,d.height));
		return row;
	}
	public static void addLabelAndEditor(
			GridRow row, String text, int n)
	{
		row.add(Cell.RIGHT,Cell.CENTER,new JLabel(text));
		row.add(Cell.NO_ALIGNMENT,Cell.CENTER,createTextField(n));
	}
	public static void createGUI()
	{

		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container=frame.getContentPane();

		GridRows rows=new GridRows();
		
		
		
		// Row 1
		rows.newRow().add(createNamedSeparator("General")).span(4);

		// Row 2
		GridRow row=rows.newRow();
		addLabelAndEditor(row,"Company",20);
		row.span(3);

		// Row 3
		row=rows.newRow();
		addLabelAndEditor(row,"Contact",20);
		row.span(3);

		// Row 4
		rows.newRow().add(new Gap(10));;

		// Row 5
		rows.newRow().add(createNamedSeparator("Propeller")).span(4);

		// Row 6
		row=rows.newRow();
		addLabelAndEditor(row,"PTI[kW]",10);
		addLabelAndEditor(row,"  Power[kW]",10);
		

		// Row 7
		row=rows.newRow();
		addLabelAndEditor(row,"R[mm]",10);
		addLabelAndEditor(row,"  D[mm]",10);


		CellGrid cellgrid=rows.createCellGrid();

		cellgrid.setRowMargins(Cell.LEFT,20,1, 2, 5, 6);
		cellgrid.setRowMargins(Cell.RIGHT,20,1, 2, 5, 6);
		cellgrid.createLayout(container);


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

