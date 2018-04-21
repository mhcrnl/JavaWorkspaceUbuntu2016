package examples;
import pagelayout.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import static pagelayout.EasyCell.*;

/**

	In this example the top level container contains a panel
	which itself contains child components. The layout of
	the panel is managed by using an object of the class  
	{@link pagelayout.PanelCell PanelCell}, without a separate
	layout manager for the panel.

	<center>
	<img src="doc-files\trans.gif" height="50" width="10"/>
	</center>
	<br>
	
	<center>
	<img src="doc-files\example5.gif"/>
	</center>

<b>Note that the code requires that the static methods of the class <code>EasyCell</code> be imported using the <code>import static pagelayout.EasyCell.*</code>
statement.</b>
<pre>
	public static void createGUI()
	{


		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container=frame.getContentPane();

		<b>// Create the components</b>
		JLabel findWhat=new JLabel("Find What:");
		JTextField textInput=new JTextField();

		JButton find=new JButton("Find Next");
		JButton cancel=new JButton("Cancel");

		JCheckBox matchWhole=new JCheckBox("Match Whole Word Only");
		JCheckBox matchCase= new JCheckBox("Match Case");
		JPanel p=new JPanel();

		
		<b>// Create Button group</b>
		PanelCell panelCell=createButtonGroup(p);

	       <b>// Top level GUI consists of a row of two columns,</b>
	       <b>// The second of these consists of the buttons on the right,</b>
	       <b>// and the rest of the components are in the first column.</b>
	       Row topLevel=row(
			column(row(findWhat,textInput),
                               row(column(matchWhole,matchCase),panelCell)),
			column(find,cancel));

		<b>// Add size constraints</b>
		topLevel.linkHeight(find,1,textInput);
		topLevel.linkWidth(find,1,cancel);

		<b>// Construct the layout</b>
		topLevel.createLayout(container);
		
		
		frame.pack();
		frame.setSize(frame.getPreferredSize());
		frame.setVisible(true);
              
               

	}
	public static PanelCell createButtonGroup(JPanel p)
	{
		 <b>// Create Button group</b>
		ButtonGroup bg=new ButtonGroup();
		JRadioButton b1=new JRadioButton("Up");
		JRadioButton b2=new JRadioButton("Down");
		bg.add(b1);
		bg.add(b2);
		Border b=BorderFactory.createLineBorder(Color.black);
		p.setBorder(b=BorderFactory.createTitledBorder(b,"Direction"));
		<b>//The row of buttons.</b>
		Row row=new Row();
		row.add(b1).add(b2).add(5,5,Cell.MAX);
		<b>//The main column</b>
		<b>//Create the layout.</b>
		return new PanelCell(p,row);
	}
</pre>

**/

public class Example5
{
	public static void createGUI()
	{


		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container=frame.getContentPane();

		// Create the components
		JLabel findWhat=new JLabel("Find What:");
		JTextField textInput=new JTextField();

		JButton find=new JButton("Find Next");
		JButton cancel=new JButton("Cancel");

		JCheckBox matchWhole=new JCheckBox("Match Whole Word Only");
		JCheckBox matchCase= new JCheckBox("Match Case");
		JPanel p=new JPanel();

		
		// Create Button group
		PanelCell panelCell=createButtonGroup(p);

               // Top level GUI consists of a row of two columns,
	       // The second of these consists of the buttons on the right,
	       // and the rest of the components are in the first column.

		Row topLevel=row(
			column(row(findWhat,textInput),
                               row(column(matchWhole,matchCase),panelCell)),
			column(find,cancel));
					
		// Add size constraints
		topLevel.linkHeight(find,1,textInput);
		topLevel.linkWidth(find,1,cancel);

		// Construct the layout
		topLevel.createLayout(container);
		
		frame.pack();
		frame.setSize(frame.getPreferredSize());
		frame.setVisible(true);
              
               

	}
	public static PanelCell createButtonGroup(JPanel p)
	{
		 // Create Button group
		ButtonGroup bg=new ButtonGroup();
		JRadioButton b1=new JRadioButton("Up");
		JRadioButton b2=new JRadioButton("Down");
		bg.add(b1);
		bg.add(b2);
		Border b=BorderFactory.createLineBorder(Color.black);
		p.setBorder(b=BorderFactory.createTitledBorder(b,"Direction"));
		return new PanelCell(p,row(b1,b2));
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

