/*
	------------------------------------------------
	EasyCell.java
	------------------------------------------------

	This software is distribiuted unde the 
	GNU Lesser General Public  License (LGPL) Version 2.1.

	You should have received a copy of the license with this
	distribution.

	The license is also available at

	http://www.gnu.org/licenses/lgpl.txt	
	
	Copyright (c) 2006 Mathnium Associates.

*/
package pagelayout;
import static pagelayout.Cell.*;
import java.awt.*;
import javax.swing.*;
/**
	<p>This class contains static methods that provide convenient ways
	to call the various constructors and <code>createCellGrid</code> 
	methods. These methods can be used be adding the 
	<code>import static pagelayout.EasyCell.*</code> statements to
	the set of import statements of the calling class.</p>
	
	<p>The various constants for justfication in the class 
	<code>Cell</code> are repeated here with lower case counterparts,
	with <code>NO_ALIGNMENT</code> replaced by <code>none</code>.

	<p>Of special interest are the methods 
	{@link #row(Object...) row},
	{@link #row(int , int, Object...) row},
	{@link #column(Object...) column},
	{@link #column(int , int, Object...) column},
	{@link #grid(Object...) grid},
	which can be called with arbitrary
	combinations of objects of the type <code>Component</code>
	as well of the type <code>Cell</code>
	(rows, columns, grids, gaps, and component cells). Note
	that the type checking in these methods is performed at run time,
	and so they should be used with care.</p>

	<p>The classes {@link examples.Example2 Example2}, 
	{@link examples.Example3 Example3}, 
	{@link examples.Example4 Example4}, 
	{@link examples.Example5 Example5}, 
	{@link examples.Example10 Example10}, 
	illustrate the use of these methods.</p>

	<p>An alternative to using these methods is to use the corresponding
	methods 
	{@link #row(Cell...) row},
	{@link #row(int , int, Cell...) row},
	{@link #column(Cell...) column},
	{@link #column(int , int, Cell...) column},
	{@link #grid(Cell...) grid},
	that use <code>Cell</code> objects, and  wrap components in a cell
	by using the 
	{@link #cell(Component) cell} method of this class. This will ensure
	that type checking as perfromed by the compiler.</p>

	<p>For the methods 
	{@link #grid(Cell...) grid} and
	{@link #grid(Object...) grid}, that can be used to create grids, the
	followin methods are useful:
	<ul>
	<li>
	{@link #span() span} or
	{@link #hspan() hspan} 
	 should be used to specify that a cell/component
	span more than one column in a row,</li>
	<li>
	{@link #vspan() vspan} should be used to specify that a cell/component
	span more than one row in a column,</li>
	<li>
	{@link #skip() skip}  should be used to specify an empty column in
	a row of the grid, and </li>
	<li>
	{@link #eol() eol}  should be used to specify the
	termination of a row in the grid and the beginning of the next. </li>
	</ul>

       <p>As an example of all these methods, consider the following 
       scehmatic layout:</p>
	<br>
	<br>
	<center>
	<img src="doc-files/easycell.gif" width="385" height="187"/>
	</center>
	<br>
	<br>
	<p>
	With <code>c1, c2,</code> etc. representing the various components,
	grid may be laid out by the following code:
	<pre> 
		CellGrid cellgrid=grid(c1,c2,skip(),eol(),
	                               vspan(),row(c3,c4),span(),eol(),
                                       vspan(),c5,span(),span(),eol(),
                                       c6,c7,c8);
	</pre>
	@version  1.16  05/10/2008
**/
public class EasyCell
{
	/**
		Constant for specifying no alignment. Same as TOP or LEFT.
	**/
	public final static int none=Cell.NO_ALIGNMENT;
	/**
		Constant for specifying top justified vertical alignment. 
	**/
	public final static int top=Cell.TOP;
	/**
		Constant for specifying center justified alignment. 
	**/
	public final static int center=Cell.CENTER;
	/**
		Constant for specifying bottom justified vertical alignment. 
	**/
	public final static int bottom=Cell.BOTTOM;
	/**
		Constant for specifying left justified horizontal alignment. 
	**/
	public final static int left=Cell.LEFT;
	/**
		Constant for specifying right justified horizontal alignment. 
	**/
	public final static int right=Cell.RIGHT;
	/**
		Constant for specifying fully justified alignment. 
	**/
	public final static int justified=Cell.JUSTIFIED;
	/**
		Constant for specifying Baseline alignment (for rows). 
	**/
	public final static int baseline=Cell.BASELINE;
	
/**
	Convenienence cover method for 
	{@link pagelayout.ComponentCell#ComponentCell(Component) ComponentCell} constructor.
**/
	public static Cell cell(JComponent c)
	{
		return new ComponentCell(c);
	}
/**
	Convenienence cover method for the
	{@link pagelayout.Row#Row(int,int,Component...) Row} constructor.
**/
	public static Row row(int hjust,int vjust, Component... c)
	{
		return new Row(hjust,vjust,c);	
	}
/**
	Convenienence cover method for the
	{@link pagelayout.Column#Column(int,int,Component...) Column} constructor.
**/
	public static Column column(int hjust, int vjust, Component... c)
	{
		return new Column(hjust,vjust,c);
	}
/**
	Convenienence cover method for the
	{@link pagelayout.Row#Row(Component...) Row} constructor.
**/
	public static Row row(Component... c)
	{
		return new Row(c);	
	}
/**
	Convenienence cover method for the
	{@link pagelayout.Column#Column(Component...) Column} constructor.
**/
	public static Column column(Component... c)
	{
		return new Column(c);
	}
/**
	Convenienence cover method for the
	{@link pagelayout.Row#Row(int,int,Cell...) Row} constructor.
**/
	public static Row row(int hjust,int vjust, Cell... c)
	{
		return new Row(hjust,vjust,c);	
	}
	public static Row createRow(Number hjust,Number vjust, Cell... c)
	{
		return new Row(hjust.intValue(),vjust.intValue(),c);	
	}
	public static Row createRow(Number hjust,Number vjust, Cell c)
	{
		return new Row(hjust.intValue(),vjust.intValue(),c);	
	}
/**
	Convenienence cover method for the
	{@link pagelayout.Column#Column(int,int,Cell...) Column} constructor.
**/
	public static Column column(int hjust, int vjust, Cell... c)
	{
		return new Column(hjust,vjust,c);
	}
/**
	Convenienence cover method for the
	{@link pagelayout.Row#Row(Cell...) Row} constructor.
**/
	public static Row row(Cell... c)
	{
		return new Row(c);	
	}
/**
	Convenienence cover method for the
	{@link pagelayout.Column#Column(Cell...) Column} constructor.
**/
	public static Column column(Cell... c)
	{
		return new Column(c);
	}
/**
	Returns a cell  object that must be used to separate the rows
	in a grid when the
	{@link pagelayout.EasyCell#grid(Cell...) grid} or
	{@link pagelayout.EasyCell#grid(Object...) grid} 
	method is used.
**/
	public static Cell eol()
	{
		return NEWROW;
	}
/**
	Create a row from cells or components. Throws an 
	<code>IllegalArgument</code> exception if any other type of object
	is used as the input.
	
	@param objects List of cells or components

	@return a row of the input component cells
**/
	public static Row row(Object... objects)
	{
		return row(none,none,objects);
	}
/**
	Create a row from cells or components. Throws an 
	<code>IllegalArgument</code> exception if any other type of object
	is used as the input.
	
	@param hjust horizontal justification for the row
	@param vjust vertical justification for the row
	@param objects List of cells or components

	@return a row of the input component cells.
**/
	public static Row row(int hjust, int vjust, Object... objects)
	{
		Cell.checkArgs("Cell.row",objects);
		Row r=new Row(hjust,vjust);
		Cell.addArgs(r,objects);
		return r;
	}
/**
	Create a column from cells or components. Throws an 
	<code>IllegalArgument</code> exception if any other type of object
	is used as the input.
	
	@param objects List of cells or components

	@return a column of the input component cells
**/
	public static Column column(Object... objects)
	{
		return column(none,none,objects);
	}
/**
	Create a column from cells or components. Throws an 
	<code>IllegalArgument</code> exception if any other type of object
	is used as the input.
	
	@param hjust horizontal justification for the row
	@param vjust vertical justification for the row
	@param objects List of cells or components

	@return a column of the input component cells.
**/
	public static Column column(int hjust, int vjust, Object... objects)
	{
		Cell.checkArgs("Cell.column",objects);
		Column col=new Column(hjust,vjust);
		Cell.addArgs(col,objects);
		return col;
	}
/**
	Returns a cell  object that must be used to specify
	that the previous cell in the current row
	 span the current cell as well when
	{@link pagelayout.EasyCell#grid(Cell...) grid} or
	{@link pagelayout.EasyCell#grid(Object...) grid} 
	method is used.
**/

	public static Cell span(){ return CellGrid.HSPANCELL;}
/**
	Returns a cell  object that must be used to specify
	that the previous cell in the current row
	 span the current cell as well when
	{@link pagelayout.EasyCell#grid(Cell...) grid} or
	{@link pagelayout.EasyCell#grid(Object...) grid} 
	method is used.
**/
	public static Cell hspan(){ return CellGrid.HSPANCELL;}
/**
	Returns a cell  object that must be used to specify
	that the cell in the current column of the previous row
	 span the current cell as well when
	{@link pagelayout.EasyCell#grid(Cell...) grid} or
	{@link pagelayout.EasyCell#grid(Object...) grid} 
	method is used.
**/
	public static Cell vspan(){ return CellGrid.VSPANCELL;}
/**
	Convenienence cover method for the
	{@link pagelayout.Gap#Gap(int) Gap} constructor.
**/
	public static Cell vgap(int min){ return new Gap(min);}
/**
	Convenienence cover method for the
	{@link pagelayout.Gap#Gap(int,int,int) Gap} constructor.
**/
	public static Cell vgap(int min,int pref,int max)
		{ return new Gap(min,pref,max);}
/**
	Convenienence cover method for the
	{@link pagelayout.Gap#Gap(int,boolean) Gap} constructor, with
	the second argument set to true, to return a horizontal gap.
**/
	public static Cell hgap(int min){ return new Gap(min,true);}
/**
	Convenienence cover method for the
	{@link pagelayout.Gap#Gap(int,int,int,boolean) Gap} constructor, with
	the second argument set to true, to return a horizontal gap.
**/
	public static Cell hgap(int min,int pref,int max)
		{ return new Gap(min,pref,max,true);}
/**
	Creates a grid from a list of cells. 

	@param cells the list of cells in the grid, with each row separated
	         by the cell returned by the 
	{@link pagelayout.EasyCell#eol eol}  method of this class.

	@return the grid of cells.
**/
	public static CellGrid grid(Cell... cells)
	{
		return CellGrid.createCellGridFromCells(cells);
	}
/**
	Creates a grid from a list of cells or components. 

	@param objects the list of cells or components
	 in the grid, with each row separated
	         by the cell returned by the 
	{@link pagelayout.EasyCell#eol eol}  method of this class.

	@return the grid of cells.
**/
	public static CellGrid grid(Object... objects)
	{
		return CellGrid.createCellGridFromObjects(objects );
	}
/**
	Creates a grid from a list of cells or components, with all the
        cells aligned according to the first two arguments. 

	@param xa the x alignment of the components: left, center, right or
         justified

	@param ya the y alignment of the components: top, center, bottom or
         justified

	@param cells the list of cells or components
	 in the grid, with each row separated
	         by the cell returned by the 
	{@link pagelayout.EasyCell#eol eol}  method of this class.

	@return the grid of cells.
**/
	public static CellGrid grid(int xa, int ya, Object... cells)
	{
		CellGrid g=grid(cells);
		int nr=g.numberOfRows();
		int nc=g.numberOfColumns();
		for(int i=0;i<nr;i++)
			for(int j=0;j<nc;j++)
				g.setAlignment(i,j,xa,ya);
		g.update();
		return g;
	}
/**
	Returns a cell  object that must be used to specify
	that the cell in the current column remain empty when the
	{@link pagelayout.EasyCell#grid(Cell...) grid} or
	{@link pagelayout.EasyCell#grid(Object...) grid} 
	method is used.
**/
	public static Cell skip()
	{
		return Cell.SKIP;
	}
	public static void setDimensions(Component c, Dimension dd[])
	{
		setDimensions((JComponent)c,false,
			dd[0].width,dd[0].height,
			dd[1].width,dd[1].height,
			dd[2].width,dd[2].height);
	}
	public static void setDimensions(JComponent c,
		int minW, int minH, int maxW, int maxH, int prefW, int prefH)
	{
		setDimensions(c,true,
			minW, minH, maxW, maxH, prefW, prefH);
	}
	public static void setDimensions(JComponent c,boolean useRules,
		int minW, int minH, int maxW, int maxH, int prefW, int prefH)
	{
		if(JTextArea.class.isInstance(c))
		{
			JTextArea ta=(JTextArea)c;
			setTADimensions(ta,ta.getRows(),ta.getColumns(),
			minW,minH,maxW,maxH,prefW,prefH);
			return;
		}
		else if(JTextField.class.isInstance(c))
		{
			JTextField ta=(JTextField)c;
			setDimensions(ta,ta.getColumns(),
			minW,minH,maxW,maxH,prefW,prefH);
			return;
		}
		if(useRules)
		{
			if(JLabel.class.isInstance(c))return;
			else if(JButton.class.isInstance(c))return;
			else if(JRadioButton.class.isInstance(c))return;
			else if(JToggleButton.class.isInstance(c))return;
			else if(JRadioButton.class.isInstance(c))return;
			else if(JCheckBox.class.isInstance(c))return;
		}
		c.setMinimumSize(new Dimension(minW,minH));
		c.setMaximumSize(new Dimension(maxW,maxH));
		c.setPreferredSize(new Dimension(prefW,prefH));
	}
	public static void setDimensions(JTextField c, int col,
		int minW, int minH, int maxW, int maxH, int prefW, int prefH)
	{
		if(col<=0)col=10;	
		c.setColumns(col);
		Dimension d=c.getPreferredSize();
		int nc=(int)((double)prefW*col/((double)d.width));
		c.setColumns(nc);
		d=c.getPreferredSize();
		if((prefW==minW)&&(prefW==maxW))
			prefW=minW=maxW=d.width;
		c.setPreferredSize(new Dimension(prefW,prefH));
		c.setMinimumSize(new Dimension(minW,prefH));
		c.setMaximumSize(new Dimension(maxW,prefH));
	}
	public static void setTADimensions(JTextArea c, int rows, int col,
		int minW, int minH, int maxW, int maxH, int prefW, int prefH)
	{
		if(col<=0)col=10;	
		if(rows<=0)rows=5;	
		c.setColumns(col);
		c.setRows(rows);
		Dimension d=c.getPreferredSize();
		int nc=(int)((double)prefW*col/((double)d.width));
		c.setColumns(nc);
		int nr=(int)((double)prefH*rows/((double)d.height));
		c.setRows(nr);
		c.setPreferredSize(new Dimension(prefW,prefH));
		c.setMinimumSize(new Dimension(minW,minH));
		c.setMaximumSize(new Dimension(maxW,maxH));
	}
/**
	Effectively replaces a panel by the given component when the
	panel is placed in a container. This is useful for modifying
	an existing GUI built by a GUI builder that does not allow
	all possible types of components to be placed in the design.
	
	@param panel The panel to be covered.
	@param c     The component that should cover the panel.
**/
	public static void coverPanel(JPanel panel, JComponent c)
	{
		ComponentCell cc=new ComponentCell(c);
		cc.setComponentGaps(0,0);
		panel.setBackground(c.getBackground());
		PageLayout pl=(PageLayout)(cc.createLayout(panel));	
		pl.setContainerGaps(0,0);
		panel.validate();
	}
/**
	Creates a row from a list of cells or components, with all the
        cells aligned according to the first two arguments. The alignments
	are specified as strings.

	@param xalign the x alignment of the components: "left", "center", "right" or
         "justified"

	@param yalign the y alignment of the components: "top", "center", "bottom" or
         "justified"

	@param cells the list of cells or components
	 in the row.

	@return the row of cells/components.
**/
	public static Row row(String xalign, String yalign, Object... cells)
	{
		int xa=getXAlign(xalign);	
		int ya=getYAlign(yalign);	
		return row(xa,ya,cells);
	}
/**
	Creates a column from a list of cells or components, with all the
        cells aligned according to the first two arguments. The alignments
	are specified as strings.

	@param xalign the x alignment of the components: "left", "center", "right" or
         "justified"

	@param yalign the y alignment of the components: "top", "center", "bottom" or
         "justified"

	@param cells the list of cells or components
	 in the column.

	@return the column of cells/components.
**/
	public static Column column(String xalign, String yalign, Object... cells)
	{
		int xa=getXAlign(xalign);	
		int ya=getYAlign(yalign);	
		return column(xa,ya,cells);
	}
/**
	Creates a grid from a list of cells or components, with all the
        cells aligned according to the first two arguments. The alignments
	are specified as strings.

	@param xalign the x alignment of the components: "left", "center", "right" or
         "justified"

	@param yalign the y alignment of the components: "top", "center", "bottom" or
         "justified"

	@param cells the list of cells or components
	 in the grid, with each row separated
	         by the cell returned by the 
	{@link pagelayout.EasyCell#eol eol}  method of this class.

	@return the grid of cells.
**/
	public static CellGrid grid(String xalign, String yalign, Object... cells)
	{
		int xa=getXAlign(xalign);	
		int ya=getYAlign(yalign);	
		return grid(xa,ya,cells);
	}
		
	public static int getXAlign(String xalign)
	{
		if(xalign==null)return none;
		xalign=xalign.toUpperCase();
		if(("RIGHT").startsWith(xalign))return right;
		else if(("LEFT").startsWith(xalign))return left;
		else if(("CENTER").startsWith(xalign))return center;
		else if(("JUSTIFIED").startsWith(xalign))return justified;
		return none;
	}
	public static int getYAlign(String yalign)
	{
		if(yalign==null)return none;
		yalign=yalign.toUpperCase();
		if(("TOP").startsWith(yalign))return top;
		else if(("BOTTOM").startsWith(yalign))return bottom;
		else if(("CENTER").startsWith(yalign))return center;
		else if(("JUSTIFIED").startsWith(yalign))return justified;
		return none;
	}
/**
	Creates a tabbed panel from a list of pairs of the form 
	(name, cell/component)
        where name is the tab label of the associated cell/component.

	Example: tpc=tabbedcell("Tab1",new JPanel(), 
				"Tab2",column(new JLabel("Name"),
					      new JTextArea()));

	@param objects the list of  (name, cell/component) pairs.

	@return the tabbed panel.
**/
	public static TabbedPaneCell tabbedcell(Object... objects)
	{
		Object[] o=objects;
		int n=(o==null?0:o.length);
		int k=0;
		
		for(int i=0;i<(n-1);i+=2)
		{
			if(String.class.isInstance(o[i])&&
				(Cell.class.isInstance(o[i+1])||
				  JComponent.class.isInstance(o[i+1])))
			k++;
		}
		if(k==0)throw new IllegalArgumentException(
			"The inputs to 'tabcell' must be pairs of the form"+
			" (name, cell/component) ");
		TabbedPaneCell t=new TabbedPaneCell();
		for(int i=0;i<(n-1);i+=2)
		{
			if(String.class.isInstance(o[i]))
			{
				if(Cell.class.isInstance(o[i+1]))
					t.add((String)o[i],(Cell)o[i+1]);
				else if(JComponent.class.isInstance(o[i+1]))
					t.add((String)o[i],(JComponent)o[i+1]);
			}
		} 
		return t;
	}
/**
	Creates a cardcell from a list of pairs of the form 
	(name, cell/component)
        where name is the card name of the associated cell/component.

	Example: cc=cardcell("Card 1",new JPanel(), 
				"Card 2",column(new JLabel("Name"),
					      new JTextArea()));

	@param objects the list of  (name, cell/component) pairs.

	@return the cardcell.
**/
	public static CardCell cardcell(Object... objects)
	{
		Object[] o=objects;
		int n=(o==null?0:o.length);
		int k=0;
		
		for(int i=0;i<(n-1);i+=2)
		{
			if(String.class.isInstance(o[i])&&
				(Cell.class.isInstance(o[i+1])||
				  JComponent.class.isInstance(o[i+1])))
			k++;
		}
		if(k==0)throw new IllegalArgumentException(
			"The inputs to 'cardcell' must be pairs of the form"+
			" (name, cell/component) ");
		CardCell t=new CardCell();
		for(int i=0;i<(n-1);i+=2)
		{
			if(String.class.isInstance(o[i]))
			{
				if(Cell.class.isInstance(o[i+1]))
					t.add((String)o[i],(Cell)o[i+1]);
				else if(JComponent.class.isInstance(o[i+1]))
					t.add((String)o[i],(JComponent)o[i+1]);
			}
		} 
		return t;
	}
/**
	Creates a JPanel from the given top level cell. The layout manager
	of the panel is constructed from the top level cell, and
	the panel can be used in any GUI that may or may not use
	the Pagelayout layout manager.

	@param topLevelCell the cell that contains the GUI to be placed
	in the panel.

	@return the panel.
**/
	public static JPanel pagepanel(Cell topLevelCell)
	{
		JPanel p=new JPanel();
		topLevelCell.createLayout(p);
		return p;
	}
}
