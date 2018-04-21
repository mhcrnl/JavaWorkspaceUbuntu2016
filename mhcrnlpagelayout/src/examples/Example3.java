package examples;
import pagelayout.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import static pagelayout.EasyCell.*;

/**

	This example provides an alternate code using the Pagelayout 
	for the gui in the following
<a href="https://tablelayout.dev.java.net/articles/TableLayoutTutorialPart2/TableLayoutTutorialPart2.html"> tutorial on Table Layout.</a>  The simplicity of
the program to create this GUI is quite notworthy, in that all the components are placed in a vertically stacked set of rows with proper alignment specified for
each row.

	<center>
	<img src="doc-files\trans.gif" height="50" width="10"/>
	</center>
	<br>
	<center>
	<img src="doc-files/example3.gif"/>
	</center>
<br>
<b>Note that the code requires that the static methods of the class <code>EasyCell</code> be imported using the <code>import static pagelayout.EasyCell.*</code>
statement.</b>
<SPACER TYPE="VERTICAL" SIZE="50"/>
<pre>
	public static void createGUI()
	{


		<b>// Frame</b>
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container=frame.getContentPane();

		<b>// Components</b>
		JLabel connect=new JLabel("Connect using:");
		JTextField  choice=new JTextField();
		JButton config=new JButton("Configure:");
		JLabel info=new JLabel(
			"This connection use the following items:");
		JTextArea tbox=new JTextArea();
		tbox.setRows(15);
		tbox.setBorder(
		 BorderFactory.createLineBorder(Color.black,1));
		
		JButton install=new JButton("Install");
		JButton uninstall=new JButton("Uninstall");
		JButton props=new JButton("Properties");
		JCheckBox showi=new JCheckBox(
			"Show icon notification area when connected");
		JCheckBox notifybox=new JCheckBox(
		 "Notify me when this connection has limited or no connectivity");
		JButton ok=new JButton("OK");
		JButton cancel=new JButton("Cancel");

		<b>// The components are laid out in a column.</b>
		Column topLevel=column( connect,
					row(choice,config),
					info,
					tbox,
					row(justified,none,
						install,uninstall,props),
					showi,
					notifybox,
					row(right,none,ok,cancel));

		<b>// Construct the Layout from the page</b>
		topLevel.createLayout(container);

		<b>// Dimension constraints</b>
		topLevel.linkHeight(config,1,choice);
		



		frame.pack();
		frame.setSize(frame.getPreferredSize());
		frame.show();

	}
</pre>
**/

public class Example3
{
	public static void createGUI()
	{


		// Frame
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container=frame.getContentPane();

		// Components
		JLabel connect=new JLabel("Connect using:");
		JTextField  choice=new JTextField();
		JButton config=new JButton("Configure:");
		JLabel info=new JLabel(
			"This connection use the following items:");
		JTextArea tbox=new JTextArea();
		tbox.setRows(15);
		tbox.setBorder(
		 BorderFactory.createLineBorder(Color.black,1));
		JButton install=new JButton("Install");
		JButton uninstall=new JButton("Uninstall");
		JButton props=new JButton("Properties");
		JCheckBox showi=new JCheckBox(
			"Show icon notification area when connected");
		JCheckBox notifybox=new JCheckBox(
		 "Notify me when this connection has limited or no connectivity");
		JButton ok=new JButton("OK");
		JButton cancel=new JButton("Cancel");

		// Page has only one column
		Column topLevel=column( connect,
					row(choice,config),
					info,
					tbox,
					row(justified,none,
						install,uninstall,props),
					showi,
					notifybox,
					row(right,none,ok,cancel));

		// Dimension constraints
		topLevel.linkHeight(config,1,choice);
		topLevel.linkHeight(install,1, uninstall,props);

		// Construct the Layout from the page
		topLevel.createLayout(container);
		

		frame.pack();
		frame.setSize(frame.getPreferredSize());
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

