package examples;
import pagelayout.*;
import static pagelayout.EasyCell.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

/**

	An example that illustrates how to add or remove components. On launching the first image appears with two buttons. When the <i>Remove Cancel</i>
button is pressed, the <i>Cancel</i> button disappears as is shown in the second image, and the text in the first button changes to <i>Add Cancel</i>. The window changes to the first image when the <i>Add Cancel</i> button is pressed.

	<center>
	<img src="doc-files\trans.gif" height="50" width="10"/>
	</center>
	<br>

	<center>
	<img src="doc-files\example6a.gif"/>
	<br>
	<br>
	<img src="doc-files\example6b.gif"/>
	</center>

<pre>
	private JButton cancel;
	private JButton mutate;
	private Cell cancelCell;
	private boolean cancelShown;
	private Container container;
	private Row mainRow;
	private PageLayout lay=null;
	public Example6()
	{

		<b>// The main frame.</b>
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		container =frame.getContentPane();

		<b>// The components</b>
		mutate=new JButton("Remove Cancel");
		cancel=new JButton("Cancel");
		mutate.addActionListener(this);

		<b>// The main row.</b>
		cancelCell=new ComponentCell(cancel);
		mainRow=new Row(mutate);
		mainRow.add(cancelCell);
		cancelShown=true;

		<b>// Construct the layout</b>
		lay=mainRow.createLayout(container);
		
		frame.pack();
		frame.setLocation(300,300);
		frame.setVisible(true);

	}
	public void actionPerformed(ActionEvent e)
	{
		if(cancelShown)
		{
			<b>//Remove the Cancel button and change text.</b>
			mainRow.removeComponent(cancel);
			mutate.setText("Add Cancel");
			
		}
		else
		{
			<b>//Add the Cancel button and change text.</b>
			mainRow.add(cancelCell);
			cancelCell.addComponentsToContainer(container);
			mutate.setText("Remove Cancel");
		}
		mainRow.invalidate();
		cancelShown=!cancelShown;
		container.validate();
		container.repaint();
	}
</pre>
**/

public class Example6 implements ActionListener
{
	private JButton mutate;
	private CardCell card;
	private boolean showCancel;
	public Example6()
	{

		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		mutate=new JButton("Remove Cancel");
		mutate.addActionListener(this);

                card=cardcell("CancelButton",new JButton("Cancel"),
		               "Nothing"    ,    vgap(1));


		row(mutate,card).createLayout(frame.getContentPane());

		showCancel=true;
		
		frame.pack();
		frame.setLocation(300,300);
		frame.setSize(frame.getPreferredSize());
		frame.setVisible(true);

	}
	public void actionPerformed(ActionEvent e)
	{
		showCancel=!showCancel;
		mutate.setText(showCancel?
				"Remove Cancel":" Add Cancel ");
		card.showCell(showCancel? 
				"CancelButton":"Nothing");
	}
	public static void main(String args[])
	{
		run();
	}
	public static void run()
	{
		Example6 ex=new Example6();
	}
}

