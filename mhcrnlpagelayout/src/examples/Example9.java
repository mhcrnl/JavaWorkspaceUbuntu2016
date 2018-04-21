package examples;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import pagelayout.*; 
import static pagelayout.Cell.*; 
/**
This is an example for a GUI from a 
<a href='http://www.eclipse.org/articles/Article-Understanding-Layouts/Understanding-Layouts.htm'>tutorial</a> for
an SWT layout manager. Apart from its relative complexity, its main features 
are (a) the use of a panel cell for the 'Owner Info' part of
the GUI shown in the figure below, and (b) baseline alignment. Note that the panel cell is
needed only because the GUI requires a panel with a titled border.

	<center>
	<img src="doc-files\trans.gif" height="50" width="10"/>
	</center>
	<br>

	<center>
	<img src="doc-files/example9.gif" width="592" height="412"/>
	</center>

<pre>
	<b>// Utility function for creating a JTextField with border</b>
	public static JTextField createTextField(String text,int len)
	{
		JTextField ed=new JTextField(text,len);
		ed.setBorder(BorderFactory.createLoweredBevelBorder());
		return ed;
	}
	public static PanelCell ownerInfoPanelCell()
	{
		<b>// Create the cell for owner info editors</b>

		<b>// The panel</b>
		JPanel panel=new JPanel();
		
		
		Border border=BorderFactory.createEtchedBorder();
		border=BorderFactory.createTitledBorder(border,"Owner Info");
		panel.setBorder(border);
		
		<b>// Create components</b>
		JLabel name=new JLabel("Name");
		JLabel phone=new JLabel("Phone");
		JTextField nameEditor=createTextField("Jane Doe",10);
		JTextField phoneEditor=createTextField("555-3245",10);

		GridRows rows=new GridRows();

		<b>// First row</b>
		rows.newRow().add(name,nameEditor);

		<b>// First row</b>
		rows.newRow().add(phone,phoneEditor);

		CellGrid grid=rows.createCellGrid();

		<b>// Baseline alignment</b>
		grid.alignBaseline(name,nameEditor);
		grid.alignBaseline(phone,phoneEditor);

		<b>// Create and return the panel cell </b>
		return  new PanelCell(panel,grid);
	}
 	public static String [] breedCategories= { 
          "Best of Breed", "Prettiest Female", "Handsomest Male", 
          "Best Dressed", "Fluffiest Ears", "Most Colors", 
          "Best Performer", "Loudest Bark", "Best Behaved", 
          "Prettiest Eyes", "Most Hair", "Longest Tail", 
          "Cutest Trick"};
	public static String [] breeds=
		{"Collie", "Pitbull", "Poodle", "Scottie"};
	public static void createGUI()
	{
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container=frame.getContentPane();

		<b>// Create Components</b>
		JComboBox combo=new JComboBox(breeds);
		JLabel dogName=new JLabel("Dog's Name");
		JTextField dogNameEditor=createTextField("Fifi",20);
		JLabel breed=new JLabel("Breed");
		JLabel photo=new JLabel("Photo");
		JLabel categories=new JLabel("Categories");
		JButton browse=new JButton("Browse..");
		JButton delete=new JButton("Delete");
		JButton enter=new JButton("Enter");
		JPanel imagePanel=new JPanel();
		imagePanel.setBackground(new Color(200,200,255));
		imagePanel.setBorder(BorderFactory.createLoweredBevelBorder());

		JList list=new JList(breedCategories);
		list.setBorder(BorderFactory.createLoweredBevelBorder());
		list.setBackground(Color.white);
		list.setVisibleRowCount(100);
		
		GridRows rows=new GridRows();
		
		<b>// Row 1 starting with "Dog's Name"</b>
		rows.newRow().add(dogName,dogNameEditor).span(2);

		<b>// Row 2  starting with "Breed"</b>
		rows.newRow().add(breed,combo).add(CENTER,CENTER,categories);

		<b>// Row 3  starting with "Photo"</b>
		<b>// imagePanel spans three rows and list spans four rows.</b>
		rows.newRow().add(photo,imagePanel,list);

		<b>// Row 4  starting with "Browse" button.
	        //  Note the call to 'spanVertical' for 
                //  the imagePanel and the list.</b>
		rows.newRow().add(RIGHT,Cell.NO_ALIGNMENT,browse);
		rows.getCurrentRow().spanVertical().spanVertical();

		<b>// Rows 5  starting with "Delete" button.
		//    Note the call to 'spanVertical' for the 
		//	imagePanel and the list.</b>
		rows.newRow().add(RIGHT,NO_ALIGNMENT,delete);
		rows.getCurrentRow().spanVertical().spanVertical();

		<b>// Row 6  starting with "Owner Info" panel</b>
		<b>//    Note the call to 'spanVertical' for the list.</b>
		rows.newRow().add(ownerInfoPanelCell()).span(2).spanVertical();

		<b>// Row 7 terminating with "Enter" button</b>
		rows.newRow().add(RIGHT,CENTER,enter).span(3);

		<b>// Create the main grid</b>
		CellGrid grid=rows.createCellGrid();

		<b>// Size constraints</b>
		grid.linkWidth(categories,new Component[]{list},2);
		grid.linkWidth(browse,new Component[]{delete},1);

		<b>// Make the list expandible in both directions.</b>
		grid.setFixedWidth(new Component[]{list},false);
		grid.setFixedHeight(new Component[]{list},false);

		<b>// Fix the combo box height</b>
		grid.setFixedHeight(new Component[]{combo},true);

		<b>// Baseline alignments</b>
		grid.alignBaseline(dogName,dogNameEditor);
		grid.alignBaseline(breed,combo,categories);



		<b>// Create the layout</b>
		grid.createLayout(container);

		frame.pack();
		frame.setSize(frame.getPreferredSize());
		frame.setVisible(true);
	}
</pre>

*/
public class Example9
{
	// Utility function for creating a JTextField with border
	public static JTextField createTextField(String text,int len)
	{
		JTextField ed=new JTextField(text,len);
		ed.setBorder(BorderFactory.createLoweredBevelBorder());
		return ed;
	}
	public static PanelCell ownerInfoPanelCell()
	{
		// Create the cell for owner info editors

		// The panel
		JPanel panel=new JPanel();
		
		
		Border border=BorderFactory.createEtchedBorder();
		border=BorderFactory.createTitledBorder(border,"Owner Info");
		panel.setBorder(border);
		
		// Create components
		JLabel name=new JLabel("Name");
		JLabel phone=new JLabel("Phone");
		JTextField nameEditor=createTextField("Jane Doe",10);
		JTextField phoneEditor=createTextField("555-3245",10);

		GridRows rows=new GridRows();

		// First row
		rows.newRow().add(name,nameEditor);

		// First row
		rows.newRow().add(phone,phoneEditor);

		CellGrid grid=rows.createCellGrid();

		// Baseline alignment
		grid.alignBaseline(name,nameEditor);
		grid.alignBaseline(phone,phoneEditor);

		// Create and return the panel cell 
		return  new PanelCell(panel,grid);
	}
 	public static String [] breedCategories= { 
          "Best of Breed", "Prettiest Female", "Handsomest Male", 
          "Best Dressed", "Fluffiest Ears", "Most Colors", 
          "Best Performer", "Loudest Bark", "Best Behaved", 
          "Prettiest Eyes", "Most Hair", "Longest Tail", 
          "Cutest Trick"};
	public static String [] breeds=
		{"Collie", "Pitbull", "Poodle", "Scottie"};
	public static void createGUI()
	{
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container=frame.getContentPane();

		// Create Components
		JComboBox combo=new JComboBox(breeds);
		JLabel dogName=new JLabel("Dog's Name");
		JTextField dogNameEditor=createTextField("Fifi",20);
		JLabel breed=new JLabel("Breed");
		JLabel photo=new JLabel("Photo");
		JLabel categories=new JLabel("Categories");
		JButton browse=new JButton("Browse..");
		JButton delete=new JButton("Delete");
		JButton enter=new JButton("Enter");
		JPanel imagePanel=new JPanel();
		imagePanel.setBackground(new Color(200,200,255));
		imagePanel.setBorder(BorderFactory.createLoweredBevelBorder());

		JList list=new JList(breedCategories);
		list.setBorder(BorderFactory.createLoweredBevelBorder());
		list.setBackground(Color.white);
		list.setVisibleRowCount(100);
		
		GridRows rows=new GridRows();
		
		// Row 1 starting with "Dog's Name"
		rows.newRow().add(dogName,dogNameEditor).span(2);

		// Row 2  starting with "Breed"
		rows.newRow().add(breed,combo).add(CENTER,CENTER,categories);

		// Row 3  starting with "Photo"
		// imagePanel spans three rows and list spans four rows.
		rows.newRow().add(photo,imagePanel,list);

		// Row 4  starting with "Browse" button
		//    Not the call to 'spanVertical' for the imagePanel and the list.
		rows.newRow().add(RIGHT,Cell.NO_ALIGNMENT,browse);
		rows.getCurrentRow().spanVertical().spanVertical();

		// Rows 5  starting with "Delete" button
		//    Not the call to 'spanVertical' for the imagePanel and the list.
		rows.newRow().add(RIGHT,NO_ALIGNMENT,delete);
		rows.getCurrentRow().spanVertical().spanVertical();

		// Row 6  starting with "Owner Info" panel
		//    Not the call to 'spanVertical' for the list.
		rows.newRow().add(ownerInfoPanelCell()).span(2).spanVertical();

		// Row 7 terminating with "Enter" button
		rows.newRow().add(RIGHT,CENTER,enter).span(3);

		// Create the main grid
		CellGrid grid=rows.createCellGrid();

		// Size constraints
		grid.linkWidth(categories,new Component[]{list},2);
		grid.linkWidth(browse,new Component[]{delete},1);
		grid.setRowMargins(LEFT,20,3,4);

		// Make the list expandible in both directions.
		grid.setFixedWidth(new Component[]{list},false);
		grid.setFixedHeight(new Component[]{list},false);
		// Fix the combo box height
		grid.setFixedHeight(new Component[]{combo},true);

		// Baseline alignments
		grid.alignBaseline(dogName,dogNameEditor);
		grid.alignBaseline(breed,combo,categories);



		// Create the layout
		grid.createLayout(container);

		frame.pack();
		frame.setSize(frame.getPreferredSize());
		frame.setVisible(true);
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
