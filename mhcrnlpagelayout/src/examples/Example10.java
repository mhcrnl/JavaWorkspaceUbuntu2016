package examples;
import pagelayout.*;
import static pagelayout.CellGrid.*;
import static pagelayout.EasyCell.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;
/**


	This example creates the
<a href='http://weblogs.java.net/blog/joconner/archive/2006/10/layout_manager.html'> 
	GUI</a> proposed as a <i>challenge</i> problem for layout managers.


	<center>
	<img src="doc-files\trans.gif" height="50" width="10"/>
	</center>
	<br>

	<center>
	<img src="doc-files/example10.gif" width="765" height="314"/>
	</center>

<br>
<b>Note that the code requires that the static methods of the class <code>EasyCell</code> be imported using the <code>import static pagelayout.EasyCell.*</code>
statement.</b>

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
		JLabel country=new JLabel("Country");
		JTextField countryE=new JTextField("USA",20);
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

		CellGrid grid=null;
		Row buttons=null;

		<b>//Create the top level row.</b>
		Row topLevel =
                 row(scroll, // JList on left
		   column(grid=grid(
		                lastName,lastNameE,firstName,firstNameE, eol(),<b>//Line 1</b>
		                phone,phoneE,
		                      row(none,center,email,emailE),
				          span(),eol(),<b>//Line 2</b>
		                address1,address1E,span(),span(),eol(),<b>//Line 3</b>
	     	                address2,address2E,span(),span(),eol(),<b>//Line 4</b>
		                city,cityE,eol(),<b>//Line 5</b>
                                state,stateE,postal,postalE,eol(),<b>//Line 6</b>
	                        country,countryE),<b>//Line 7</b>
		          buttons=row(justified,center,b1,b2,b3,b4,b5)));


		<b>// Alignment of the cells in the grid.</b>
		grid.setAlignments(
			new int[][]{{right,none,left,none}},
			new int[][]{{center,center,center,center}});

		buttons.linkWidth(b5,1,b1,b2,b3,b4);
		

		<b>// Create the layout.</b>
		topLevel.createLayout(container);

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
public class Example10
{
	
	public static void createGUI()
	{
		JFrame frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container container=frame.getContentPane();

		JLabel lastName=new JLabel("Last Name");
		JTextField lastNameE=new JTextField("Martian",10);
		JLabel firstName=new JLabel("First Name");
		JTextField firstNameE=new JTextField("Marvin",10);
		JLabel phone=new JLabel("Phone");
		JTextField phoneE=new JTextField("805-123-4567",10);
		JLabel email=new JLabel("Email");
		JTextField emailE=new JTextField("marvin@wb.com",10);
		JLabel address1=new JLabel("Address1");
		JTextField address1E=new JTextField("1001001010110 Martian Way ",30);
		JLabel address2=new JLabel("Addres2");
		JTextField address2E=new JTextField("Suite 101101011 ",20);
		JLabel city=new JLabel("City");
		JTextField cityE=new JTextField("Ventura ",10);
		JLabel state=new JLabel("State");
		JTextField stateE=new JTextField("CA ",10);
		JLabel country=new JLabel("Country");
		JTextField countryE=new JTextField("USA",10);
		JLabel postal=new JLabel("Postal Code");
		JTextField postalE=new JTextField("93001 ",10);
		JButton b1=new JButton("New");
		JButton b2=new JButton("Delete");
		JButton b3=new JButton("Edit");
		JButton b4=new JButton("Save");
		JButton b5=new JButton("Cancel");
		JList list=new JList(new StringListModel());
		list.setBorder(BorderFactory.createLoweredBevelBorder());
		JScrollPane scroll=new JScrollPane(list);

		
		CellGrid gridcell=null;
		Row buttons=null;
		Row topLevel =
                  row(scroll, // JList on left
			hgap(10),
		      column(gridcell=grid(
		      /*Line 1*/ lastName,lastNameE,firstName,firstNameE, eol(),
		      /*Line 2*/ phone,phoneE,
		 	           row(none,center,email,emailE),
					span(),eol(),
		      /* Line 3*/address1,address1E,span(),span(),eol(),
	     	      /* Line 4*/address2,address2E,span(),span(),eol(),
		      /* Line 5*/city,cityE,eol(),
                      /* Line 6*/state,stateE,postal,postalE,eol(),
	              /* Line 7*/country,countryE),
                             vgap(10),
		             buttons=row(right,center,b1,b2,b3,b4,b5)));
		gridcell.setComponentGaps(7,5);


		// Alignment of the cells in the grid.
		gridcell.setAlignments(
			new int[][]{{right,none,left,none}},
			new int[][]{{center,center,center,center}});

		buttons.linkWidth(b5,1,b1,b2,b3,b4);

		// Create the layout.
		topLevel.createLayout(container);

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
		public int getSize(){ return names.length;}
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












 

