package examples;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import pagelayout.*;
import static pagelayout.EasyCell.*;
/**
	An example to illustrate the use of the class 
	{@link pagelayout.CardCell CardCell} for switching between
	the two GUIs shown below.

	<center>
	<table>
	<tr>
	<td><img src="doc-files\CardExample1Grid.gif"/></td>
	<td>&nbsp;&nbsp;&nbsp;</td>
	<td><img src="doc-files\CardExample1Row.gif"/></td>
	</tr>
	</table>
	</center>
	<br/>
        <p>The GUI contains a CardCell at the bottom. The CardCell has two 
	cells as its elements. The first one is a form laid out in
	a grid, and the other is a form laid out in a row.
	When the user clicks the button with
	text <i>Grid</i>, the form with the grid
	is displayed in the card. The other button
	is associated  the form containing the row in a similar fashion.</p> 

<pre>
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import pagelayout.*;
import static pagelayout.EasyCell.*;

public class CardExample1
{
	public static void createGUI()
	{
		<b>// Create the cardcell</b>
		CardCell card=new CardCell();

		<b>// Create the grid for the card.</b>
		card.add("Grid",
			grid(new JLabel("Name"),new JTextField(10),eol(),
			     new JLabel("Address"),new JTextField(10)));

		<b>// Create the row for the card.</b>
		card.add("Row",
			row(new JLabel("Name"),new JTextField(10),
				new JLabel("Address"),new JTextField(10)));

		JFrame f=new JFrame();
		ButtonGroup bg=new ButtonGroup();

		<b>// Create the button for the grid.</b>
		JRadioButton buttonForGrid=new JRadioButton("Grid");
		buttonForGrid.addActionListener(
					new ButtonAction(card,"Grid"));
		bg.add(buttonForGrid);

		<b>// Create the button for the row.</b>
		JRadioButton buttonForRow=new JRadioButton("Row");
		buttonForRow.addActionListener(
					new ButtonAction(card,"Row"));
		bg.add(buttonForRow);

		<b>// Create the layout.</b>
		column(center, center,
		  	buttonForGrid,
			buttonForRow,
			card).createLayout(f.getContentPane());

		f.pack();
		f.show();
	}
	<b>// Action listener for the button</b>
	public static class ButtonAction implements ActionListener
	{
		private CardCell card;
		private String name;
		public ButtonAction(CardCell card, String name)
		{
			this.card=card;
			this.name=name;
		}
		<b>// Show the named cell in the card.</b>
		public void actionPerformed(ActionEvent e)
		{
			card.showCell(name);
		}
	}
	public static class GUICreator implements Runnable
	{
		public void run()
		{
			createGUI();
		}
	}
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater( new GUICreator());
				
	}
}
</pre>
	

**/

public class CardExample1
{
	public static void createGUI()
	{
		// Create the cardcell
		CardCell card=new CardCell();

		// Create the grid for the card.
		card.add("Grid",
			grid(new JLabel("Name"),new JTextField(10),eol(),
			     new JLabel("Address"),new JTextField(10)));

		// Create the row for the card.
		card.add("Row",
			row(new JLabel("Name"),new JTextField(10),
				new JLabel("Address"),new JTextField(10)));

		JFrame f=new JFrame();
		ButtonGroup bg=new ButtonGroup();

		// Create the button for the grid.
		JRadioButton buttonForGrid=new JRadioButton("Grid");
		buttonForGrid.addActionListener(
					new ButtonAction(card,"Grid"));
		bg.add(buttonForGrid);

		// Create the button for the row.
		JRadioButton buttonForRow=new JRadioButton("Row");
		buttonForRow.addActionListener(
					new ButtonAction(card,"Row"));
		bg.add(buttonForRow);

		// Create the layout.
		column(center, center,
		  	buttonForGrid,
			buttonForRow,
			card).createLayout(f.getContentPane());

		f.pack();
		f.show();
	}
	// Action listener for the button
	public static class ButtonAction implements ActionListener
	{
		private CardCell card;
		private String name;
		public ButtonAction(CardCell card, String name)
		{
			this.card=card;
			this.name=name;
		}
		// Show the named cell in the card.
		public void actionPerformed(ActionEvent e)
		{
			card.showCell(name);
		}
	}
	public static class GUICreator implements Runnable
	{
		public void run()
		{
			createGUI();
		}
	}
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater( new GUICreator());
				
	}
}
