package examples;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import pagelayout.*;
import static pagelayout.EasyCell.*;
/**
	An example to illustrate the use of the class 
	{@link pagelayout.CardCell CardCell} for the GUI shown below.

	<center>
		<img src="doc-files\cardexample.gif"/>
	</center>
        <p>The GUI contains a CardCell at the bottom. The CardCell has three 
	JLabel objects as its elements, with their display texts being 
	<i>Mutable Component 1</i>, <i>Mutable Component 2</i>, and
	<i>Mutable Component 3</i>. When the user clicks the button with
	text <i>Show Component 2</i>, the label with the text
	<i>Mutable Component 2</i> in the card is displayed. The
	the other buttons are
	associated with 
	the labels in a similar fashion.</p> 

<pre>
public class CardExample
{
	public static String MUTABLECELLNAME="Mutable Component ";
	public static void createGUI()
	{
		<b>// Create the card with the three labels.</b>
		CardCell card=new CardCell();
		for(int i=1;i<=3;i++)
		{
			String name=MUTABLECELLNAME+i;
			card.add(name,namedPanel(name));
		}
		JFrame f=new JFrame();
		<b>// The top level cell.</b>
		ButtonGroup bg=new ButtonGroup();
		<b>// Create the three buttons with appropriate action listeners.</b>
		JRadioButton buttons[]=new JRadioButton[3];
		for(int i=1;i<=3;i++)
		{
			JRadioButton button=new JRadioButton("Show Component "+i);
			button.addActionListener(
					new ButtonAction(card,i));
			bg.add(button);
			buttons[i-1]=button;
		}
	
		<b>// Create the layout.</b>
		column(center,center,
		       buttons[0],buttons[1],
		       buttons[2],card).createLayout(f.getContentPane());

		f.pack();
		f.show();
	}
	//<b>The action listener for the radio buttons.</b>
	public static class ButtonAction implements ActionListener
	{
		private CardCell card;
		private int index;
		public ButtonAction(CardCell card, int index)
		{
			this.card=card;
			this.index=index;
		}
		<b>//Show the associated label in the card.</b>
		public void actionPerformed(ActionEvent e)
		{
			card.showCell(MUTABLECELLNAME+index);
		}
	}
	public static class GUICreator implements Runnable
	{
		public void run()
		{
			createGUI();
		}
	}
	public static JComponent namedPanel(String name)
	{
		JLabel j=new JLabel(name,SwingConstants.CENTER);
		j.setMaximumSize(new Dimension(Cell.MAX,Cell.MAX));	
		return j;
	}
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater( new GUICreator());
				
	}
}
</pre>
	

**/

public class CardExample
{
	public static String MUTABLECELLNAME="Mutable Component ";
	public static void createGUI()
	{
		CardCell card=new CardCell();
		for(int i=1;i<=3;i++)
		{
			String name=MUTABLECELLNAME+i;
			card.add(name,namedPanel(name));
		}
		JFrame f=new JFrame();
		ButtonGroup bg=new ButtonGroup();
		JRadioButton buttons[]=new JRadioButton[3]; 
		for(int i=1;i<=3;i++)
		{
			JRadioButton button=new JRadioButton("Show Component "+i);
			button.addActionListener(
					new ButtonAction(card,i));
			bg.add(button);
			buttons[i-1]=button;
		}
		column(center,center,
		       buttons[0],buttons[1],
		       buttons[2],card).createLayout(f.getContentPane());
		f.pack();
		f.show();
	}
	public static class ButtonAction implements ActionListener
	{
		private CardCell card;
		private int index;
		public ButtonAction(CardCell card, int index)
		{
			this.card=card;
			this.index=index;
		}
		public void actionPerformed(ActionEvent e)
		{
			card.showCell(MUTABLECELLNAME+index);
		}
	}
	public static class GUICreator implements Runnable
	{
		public void run()
		{
			createGUI();
		}
	}
	public static JComponent namedPanel(String name)
	{
		JLabel j=new JLabel(name,SwingConstants.CENTER);
		j.setMaximumSize(new Dimension(Cell.MAX,Cell.MAX));	
		return j;
	}
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater( new GUICreator());
				
	}
}
