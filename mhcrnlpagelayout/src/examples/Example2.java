package examples;
import pagelayout.*;
import java.awt.*;
import javax.swing.*;
import static pagelayout.EasyCell.*;

/**

	This example provides an alternate code for the gui in the following
<a href='http://weblogs.java.net/blog/tpavek/archive/2006/03/getting_to_know.html'> tutorial on Group Layout.</a>

	<center>
	<img src="doc-files\trans.gif" height="50" width="10"/>
	</center>
	<br>
	<center>
	<img src="doc-files/example2.gif"/>
	</center>

<b>Note that the code requires that the static methods of the class <code>EasyCell</code> be imported using the <code>import static pagelayout.EasyCell.*</code>
statement.</b>
<pre>
	public static void createGUI()
	{


		<b>// Main frame and its container</b>
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container=frame.getContentPane();

		<b>// Create the components</b>
		JLabel findWhat=new JLabel("Find What:");
		JTextField textInput=new JTextField();
		JButton find=new JButton("Find");
		JCheckBox matchCase=new JCheckBox("Match Case");
		JCheckBox wrapAround= new JCheckBox("Wrap Around");
		JButton cancel=new JButton("Cancel");
		JCheckBox wholeWords=new JCheckBox("Whole Words");
		JCheckBox searchBackwards=new JCheckBox("Search Backwards");

		<b> The components are laid out in a row of three columns. </b>
		//The components are laid out in a row of three columns.
		Row topLevel=   row( column(findWhat),
			              column(textInput,
                                             row(matchCase,wrapAround),
			                     row(wholeWords,searchBackwards)),
			              column(find,cancel));


		// Add size constraints
		topLevel.linkHeight(find,1,textInput);
		topLevel.linkWidth(cancel,1,find);
		topLevel.linkWidth(wholeWords,1,matchCase);

		// Construct the layout
		topLevel.createLayout(container);
		
		frame.pack();
		frame.setSize(frame.getPreferredSize());
		frame.setResizable(false);
		frame.show();

	}
</pre>
**/

public class Example2
{
	public static void createGUI()
	{


		// Main frame and its container
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container=frame.getContentPane();

		// Create the components
		JLabel findWhat=new JLabel("Find What:");
		JTextField textInput=new JTextField();
		JButton find=new JButton("Find");
		JCheckBox matchCase=new JCheckBox("Match Case");
		JCheckBox wrapAround= new JCheckBox("Wrap Around");
		JButton cancel=new JButton("Cancel");
		JCheckBox wholeWords=new JCheckBox("Whole Words");
		JCheckBox searchBackwards=new JCheckBox("Search Backwards");
	
		//The components are laid out in a row of three columns.
		Row topLevel=   row( column(findWhat),
			             column(textInput,
                                             row(matchCase,wrapAround),
			                     row(wholeWords,searchBackwards)),
			              column(find,cancel));


		// Add size constraints
		topLevel.linkHeight(find,1,textInput);
		topLevel.linkWidth(cancel,1,find);
		topLevel.linkWidth(wholeWords,1,matchCase);

		// Construct the layout
		topLevel.createLayout(container);
		
		frame.pack();
		frame.setSize(frame.getPreferredSize());
		frame.setResizable(false);
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

