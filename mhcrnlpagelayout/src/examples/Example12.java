package examples;
import pagelayout.*;
import static pagelayout.Cell.*;
import static pagelayout.CellGrid.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
/**

        This example illustrates the 
	{@link pagelayout.Cell#BASELINE BASELINE} alignment
	to align comoponents which contain text.
	This example is taken from
	an article on 
	<a href='http://www.onjava.com/pub/a/onjava/2002/09/18/relativelayout.html'>
	Relative Layout.</a> Without the use of BASELINE alignment in the top level
	cell, the text on the label <b>Sample Text</b> and the check box 
	<b>Strikethrough</b> would not be properly aligned.
		

	<center>
	<img src="doc-files\trans.gif" height="50" width="10"/>
	</center>
	<br>

	<center>
	<img src="doc-files/example12.gif" width="400" height="229"/>
	</center>

<pre>
	public static void createGUI()
	{
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container=frame.getContentPane();

		<b>// Create components</b>
		JCheckBox bold=new JCheckBox("Bold");
		JCheckBox italic=new JCheckBox("Italic");
		JCheckBox underline=new JCheckBox("Underline");
		JCheckBox strikeThrough=new JCheckBox("Strikethrough");
		JCheckBox teletype=new JCheckBox("Teletype");
		JCheckBox emphasis=new JCheckBox("Emphasis");
		JCheckBox strong=new JCheckBox("Strong");
		JLabel sampleText=new JLabel("Sample Text");
		JTextArea textArea=new JTextArea("This is sample text");
		textArea.setRows(5);
		textArea.setColumns(15);
		textArea.setBorder(BorderFactory.createLoweredBevelBorder());
		JButton apply=new JButton("Apply");

		<b>// Top Level cell. The components are laid out in a row</b>
		<b>// of three columns. Not the vertical alignment specification.</b>
		
		Row topLevel= new Row(Cell.NO_ALIGNMENT,Cell.BASELINE);

		<b>// left column</b>
		topLevel.newColumn(Cell.LEFT,Cell.NO_ALIGNMENT,
			bold,italic,underline);

		<b>// middle  column</b>
		topLevel.newColumn(Cell.LEFT,Cell.NO_ALIGNMENT,
				strikeThrough,teletype,emphasis,strong);

		<b>// Column of the Text Area and the Button to center the button</b>
		Column textAndButton
			=new Column(Cell.CENTER,Cell.NO_ALIGNMENT,textArea,apply);

		<b>// Right column</b>
		<b>// Since sampleText is a component, and textAndButton</b>
		<b>// is a cell, we have to make a separate call to 'add',</b>
		<b>// rather than use both in the 'newColumn' call.</b>
		topLevel.newColumn(Cell.LEFT,Cell.NO_ALIGNMENT,sampleText).
							add(textAndButton);


		topLevel.createLayout(container);
`

		frame.pack();
		frame.setSize(frame.getPreferredSize());
		frame.show();
	}
	public static void main(String[] args)
	{
		createGUI();
	}

</pre>
	
**/
public class Example12
{
	
	public static void createGUI()
	{
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container=frame.getContentPane();

		// Create components
		JCheckBox bold=new JCheckBox("Bold");
		JCheckBox italic=new JCheckBox("Italic");
		JCheckBox underline=new JCheckBox("Underline");
		JCheckBox strikeThrough=new JCheckBox("Strikethrough");
		JCheckBox teletype=new JCheckBox("Teletype");
		JCheckBox emphasis=new JCheckBox("Emphasis");
		JCheckBox strong=new JCheckBox("Strong");
		JLabel sampleText=new JLabel("Sample Text");
		JTextArea textArea=new JTextArea("This is sample text");
		textArea.setRows(5);
		textArea.setColumns(15);
		textArea.setBorder(BorderFactory.createLoweredBevelBorder());
		JButton apply=new JButton("Apply");

		/* Top Level cell. The components are laid out in a row
		   of three columns. Not the vertical alignment specification.
		*/
		Row topLevel= new Row(Cell.NO_ALIGNMENT,Cell.BASELINE);

		// left column
		topLevel.newColumn(Cell.LEFT,Cell.NO_ALIGNMENT,
			bold,italic,underline);

		// middle  column
		topLevel.newColumn(Cell.LEFT,Cell.NO_ALIGNMENT,
				strikeThrough,teletype,emphasis,strong);

		// Column of the Text Area and the Button to center the button
		Column textAndButton
			=new Column(Cell.CENTER,Cell.NO_ALIGNMENT,textArea,apply);

		// Right column
		// Since sampleText is a component, and textAndButton
		// is a cell, we have to make a separate call to 'add',
		// rather than use both in the 'newColumn' call.
		topLevel.newColumn(Cell.LEFT,Cell.NO_ALIGNMENT,sampleText).
							add(textAndButton);

		topLevel.alignBaseline(strikeThrough,sampleText);
		topLevel.alignBaseline(teletype,textArea);

		topLevel.createLayout(container);

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
