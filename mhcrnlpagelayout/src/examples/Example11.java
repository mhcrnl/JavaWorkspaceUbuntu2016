package examples;
import pagelayout.*;
import static pagelayout.Cell.*;
import static pagelayout.CellGrid.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
/**

        This example illustrates the use of the 
	{@link pagelayout.GridRows GridRows} objects to
	create the rows of a grid. This example is makes the same GUI as
	{@link examples.Example10 Example10}, but a little bit less verbose,
	due to the use of <code>GridRows</code>.
		

	This example creates the
<a href='http://weblogs.java.net/blog/joconner/archive/2006/10/layout_manager.html'> 
	GUI</a> proposed as a <i>challenge</i> problem for layout managers. Note
	that the use of the {@link pagelayout.GridRows GridRows} class  makes the
	construction of the grid much easier than the other possibilities that
	entail the use of one of the other 
	({@link pagelayout.CellGrid#createCellGrid(Cell[][] cellArray) createCellGrid(Cell[][] cellArray)} and 
	{@link pagelayout.CellGrid#createCellGrid(Component[][] cellArray) createCellGrid(Component[][] cellArray)}) methods for creation of a grid. 


	<center>
	<img src="doc-files\trans.gif" height="50" width="10"/>
	</center>
	<br>

	<center>
	<img src="doc-files/example10.gif" width="765" height="314"/>
	</center>

<pre>

	public static void createGUI()
	{
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container=frame.getContentPane();

		JLabel lastName=new JLabel("Last Name");
		JTextField lastNameE=new JTextField("Martian",20);
		JLabel firstName=new JLabel("First Name");
		JTextField firstNameE=new JTextField("Marvin",20);
		JLabel phone=new JLabel("Phone");
		JTextField phoneE=new JTextField("805-123-4567",20);
		JLabel email=new JLabel("Email");
		JTextField emailE=new JTextField("marvin@wb.com",20);
		JLabel address1=new JLabel("Address1");
		JTextField address1E=new JTextField("1001001010110 Martian Way ",30);
		JLabel address2=new JLabel("Addres2");
		JTextField address2E=new JTextField("Suite 101101011 ",30);
		JLabel city=new JLabel("City");
		JTextField cityE=new JTextField("Ventura ",20);
		JLabel state=new JLabel("State");
		JTextField stateE=new JTextField("CA ",20);
		JLabel county=new JLabel("County");
		JTextField countyE=new JTextField("USA",20);
		JLabel postal=new JLabel("Postal Code");
		JTextField postalE=new JTextField("93001 ",20);
		JButton b1=new JButton("New");
		JButton b2=new JButton("Delete");
		JButton b3=new JButton("Edit");
		JButton b4=new JButton("Save");
		JButton b5=new JButton("Cancel");
		JList list=new JList(new StringListModel());
		list.setBorder(BorderFactory.createLoweredBevelBorder());
		JScrollPane scroll=new JScrollPane(list);
		
		GridRows rows=new GridRows();
		
		<b>// First row </b>
		rows.newRow().add(lastName,lastNameE,firstName,firstNameE);

		
		<b>// Second row </b>
		rows.newRow().add(phone,phoneE)
		    .add(new Row(NO_ALIGNMENT,CENTER,email,emailE)).span(2);
		
		
		<b>// Third row </b>
		rows.newRow().add(address1,address1E).span(3);

		<b>// Fourth row </b>
		rows.newRow().add(address2,address2E).span(3);
		
		<b>// Fifth row </b>
		rows.newRow().add(city,cityE);
		
		<b>// Sixth row </b>
		rows.newRow().add(state,stateE,postal,postalE);

		<b>// Seventh row</b>
		rows.newRow().add(county,countyE);

		CellGrid grid=rows.createCellGrid();;

		<b>// Alignment of the cells in the grid.</b>
		grid.setAlignments(
			new int[][]{{RIGHT,NO_ALIGNMENT,LEFT,NO_ALIGNMENT}},
			new int[][]{{CENTER,CENTER,CENTER,CENTER}});

		<b>// The row of buttons.</b>
		Row buttons=new Row(Row.JUSTIFIED,Row.CENTER,b1,b2,b3,b4,b5);

		<b>// Create a column using the grid and the row of buttons.</b>
		Column col=new Column(grid,buttons);

		<b>// Constrain the buttons to be of equal width</b>
		col.linkWidth(b5,new Component[]{b1,b2,b3,b4},1);

		
		<b>// Create the row containing the list and the column with the</b>
		<b>// grid and the button row. This row is is the top level</b>
		<b>// cell.</b>
		Row row =new Row();
		row.add(scroll).add(col);

		<b>// Create the layout.</b>
		row.createLayout(container);

		frame.pack();
		frame.setSize(frame.getPreferredSize());
		frame.show();
	}
	public static class StringListModel implements ListModel
	{
		public void addListDataListener (ListDataListener l){}
		String[] names={
			"Bunny Buggs",	
			"Cat, Sylvester ",	
			"Coyote, Willie E. ",	
			"Devil, Tasmanian",	
			"Duck, Daffy",	
			"Fudd, Elmer",	
			"LePew, Pepe",	
			"Martian, MArvin"
		};
		public Object getElementAt(int index)
		{
			if(index&lt;names.length)return names[index];
			return new String("                       ");
		}
		public int getSize(){ return 30;}
		public void removeListDataListener (ListDataListener l){}
	}	

	public static void main(String[] args)
	{
		createGUI();
	}
</pre>
	
**/
public class Example11
{
	
	public static void createGUI()
	{
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container=frame.getContentPane();

		JLabel lastName=new JLabel("Last Name");
		JTextField lastNameE=new JTextField("Martian",20);
		JLabel firstName=new JLabel("First Name");
		JTextField firstNameE=new JTextField("Marvin",20);
		JLabel phone=new JLabel("Phone");
		JTextField phoneE=new JTextField("805-123-4567",20);
		JLabel email=new JLabel("Email");
		JTextField emailE=new JTextField("marvin@wb.com",20);
		JLabel address1=new JLabel("Address1");
		JTextField address1E=new JTextField("1001001010110 Martian Way ",30);
		JLabel address2=new JLabel("Addres2");
		JTextField address2E=new JTextField("Suite 101101011 ",30);
		JLabel city=new JLabel("City");
		JTextField cityE=new JTextField("Ventura ",20);
		JLabel state=new JLabel("State");
		JTextField stateE=new JTextField("CA ",20);
		JLabel county=new JLabel("County");
		JTextField countyE=new JTextField("USA",20);
		JLabel postal=new JLabel("Postal Code");
		JTextField postalE=new JTextField("93001 ",20);
		JButton b1=new JButton("New");
		JButton b2=new JButton("Delete");
		JButton b3=new JButton("Edit");
		JButton b4=new JButton("Save");
		JButton b5=new JButton("Cancel");
		JList list=new JList(new StringListModel());
		list.setBorder(BorderFactory.createLoweredBevelBorder());
		JScrollPane scroll=new JScrollPane(list);

		
		GridRows rows=new GridRows();

		// First row 
		rows.newRow().add(lastName,lastNameE,firstName,firstNameE);

		
		// Second row 
		rows.newRow().add(phone,phoneE)
		    .add(new Row(NO_ALIGNMENT,CENTER,email,emailE)).span(2);
		
		
		// Third row 
		rows.newRow().add(address1,address1E).span(3);

		// Fourth row 
		rows.newRow().add(address2,address2E).span(3);
		
		// Fifth row 
		rows.newRow().add(city,cityE);
		
		// Sixth row 
		rows.newRow().add(state,stateE,postal,postalE);

		// Seventh row
		rows.newRow().add(county,countyE);

		CellGrid grid=rows.createCellGrid();;

		// Alignment of the cells in the grid.
		grid.setAlignments(
			new int[][]{{RIGHT,NO_ALIGNMENT,LEFT,NO_ALIGNMENT}},
			new int[][]{{CENTER,CENTER,CENTER,CENTER}});

		// The row of buttons.
		Row buttons=new Row(Row.RIGHT,Row.CENTER,b1,b2,b3,b4,b5);

		// Make a column using the grid and the row of buttons.
		Column col=new Column(grid,buttons);

		// Make the buttons of equal width
		col.linkWidth(b5,new Component[]{b1,b2,b3,b4},1);

		
		// Create the row containing the list and the column with the
		// grid and the button row. This row is is the top level
		// cell.
		Row row =new Row();
		row.add(scroll).add(col);

		// Create the layout.
		row.createLayout(container);

		frame.pack();
		frame.setSize(frame.getPreferredSize());
		frame.show();
	}
	private static class StringListModel implements ListModel
	{
		public void addListDataListener (ListDataListener l){}
		String[] names={
			"Bunny Buggs",	
			"Cat, Sylvester ",	
			"Coyote, Willie E. ",	
			"Devil, Tasmanian",	
			"Duck, Daffy",	
			"Fudd, Elmer",	
			"LePew, Pepe",	
			"Martian, MArvin"
		};
		public Object getElementAt(int index)
		{
			if(index<names.length)return names[index];
			return new String("                       ");
		}
		public int getSize(){ return 30;}
		public void removeListDataListener (ListDataListener l){}
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












 

