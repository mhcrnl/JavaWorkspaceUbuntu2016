/*
	------------------------------------------------
	CellContainer.java
	------------------------------------------------

	This software is distributed under the 
	GNU Lesser General Public  License (LGPL) Version 2.1.

	You should have received a copy of the license with this
	distribution.

	The license is also available at

	http://www.gnu.org/licenses/lgpl.txt	
	
	Copyright (c) 2006 Mathnium Associates.
*/
package pagelayout;
import java.awt.*;
import javax.swing.*;
import java.util.*;

/**
	The class <code>CellContainer</code> is the (abstract) superclass of 
	the classes 
	{@link pagelayout.Column Column} and
	{@link pagelayout.Row Row} 
	in which the elements are
	arranged sequentially in the vertical or horizontal direction. 
	Components, gaps, or {@link pagelayout.Cell Cell} objects can be
	added to a row or column by using the 
	{@link pagelayout.CellContainer#add add} methods of this class.

	@version  1.16  05/10/2008

@see Cell  
@see Column  
@see Row 
**/

public abstract class CellContainer extends Cell
{
/**
	The vertical alignment parameter.
**/
	protected int valignment; 
/**
	The horizontal alignment parameter.
**/
	protected int halignment;
/**
	The collection of cells in a <code>Vector</code> of <code>Cell</code> objects.
**/
	protected CellVector cells;

/**
	Returns <code>true</code> if this container is a row.

	@return <code>true</code> for a row, and <code>false</code> for a column.
**/
	protected abstract boolean isHorizontal();

	protected Vector<Integer> parallelAlignment;
/**
	Creates a CellContainer with the given alignment specifications and
	orientation.

	@param halignment the horizontal alignment parameter.
	@param valignment the vertical alignment parameter.
	@param isRow <code>true</code> if this cell-container is a row, <code>false</code> if column.
**/

	public CellContainer (int halignment,int valignment, 
			boolean isRow)
	{
		this.valignment=limitAlignment(valignment,isRow);
		this.halignment=limitAlignment(halignment,false);
		cells=new CellVector(isRow? halignment:
					valignment,isRow);
	}
	
	
/**
	The abstract method for computing the <code>BoundSpring</code> of the
	cell-container.

	@return The two-dimensional spring of associated with the row or column.
**/
	public abstract BoundSpring computeBoundSpring();

/**
	Adds a components to this cell-container.

	@param components the components to be added.
	
	@return This object, so that multiple calls to <code>add</code> may be made in a single statement.
**/
	public CellContainer add(Component... components)
	{

		for(Component component:components)
		{
		  ComponentCell ce=new ComponentCell(component);
		  cells.add(ce);
		  ce.setParent(this);
		}
		return this;
		
	}
/**
	Adds  components to the cell-container with specified alignment that
	may be different from the alignment for the container. 

	@param align the alignment of the components to be added. 
	This overrides the default alignment of the row or column. 
	For a row, it is the
	vertical alignment and for a column it is the horizontal alignment.

	@param components the components to be added.
	
	@return This object, so that multiple calls to <code>add</code> may be made in a single statement.
**/
	public CellContainer add(int align, Component... components)
	{

		boolean isRow=isHorizontal();
		ensureParallelAlignmentExists();
		for(Component component:components)
		{
		  ComponentCell ce=new ComponentCell(component);
		  parallelAlignment.add(
			new Integer(limitAlignment(align,isRow)));
		  cells.add(ce);
		  ce.setParent(this);
		}
		return this;
		
	}
	protected int getParallelAlignment(int i)
	{
		if(parallelAlignment==null)return -1;
		if(parallelAlignment.size()<=i)return -1;
		return parallelAlignment.elementAt(i).intValue();
	}
	private  void ensureParallelAlignmentExists()
	{
		  if(parallelAlignment==null)
		  {
			parallelAlignment=new Vector<Integer>();
			int n=cells.size();
			Integer i=new Integer(-1);
			for(int j=0;j<n;j++)
		  		parallelAlignment.add(i);
			
		  }
	}
/**
	Adds a component to this cell-container with specified gaps around the component.

	@param component the component to be added.
	@param horizontalGap the width of empty strip to add around the vertical edges of the component.
	@param verticalGap the height of empty strip to add around the horizontal edges of the component.
	
	@return This object, so that multiple calls to <code>add</code> may be made in a single statement.
**/
	public CellContainer add(Component component, 
			int horizontalGap, 
			int verticalGap)
	{
                Cell c=new ComponentCell(component,horizontalGap,verticalGap); 
		cells.add(c);
                c.setParent(this);
                    
		return this;
	}
/**
	Adds a fixed gap to the cell-container. The orientation of the gap
	is vertical for a column, and horizontal for a row.

	@param gap the length of the gap to be added.
	
	@return This object, so that multiple calls to <code>add</code> may be made in a single statement.
**/
	public CellContainer add(int gap)
	{
		cells.add( new Gap(gap,isHorizontal()));
		return this;
	}
/**
	Adds a flexible gap to the cell-container. The orientation of the gap
	is vertical for a column, and horizontal for a row.

	@param minGap the minimum length of the gap to be added.
	@param preferredGap the preferred length of the gap to be added.
	@param maxGap the maximum length of the gap to be added.
	
	@return This object, so that multiple calls to <code>add</code> may be made in a single statement.
**/
	public CellContainer add(int minGap,int preferredGap,int maxGap)
	{
		cells.add( new Gap(minGap,preferredGap,maxGap,isHorizontal()));
		return this;
	}
/**
	Adds  cells to the cell-container. 

	@param cellarray the {@link pagelayout.Cell Cell} objects to be added.
	
	@return This object, so that multiple calls to <code>add</code> may be made in a single statement.
**/
	public CellContainer add(Cell... cellarray)
	{
		for(Cell cell:cellarray)
		{
		  cells.add(cell);
                  cell.setParent(this);
		}
		return this;
	}
/**
	Adds  cells to the cell-container with specified alignment that
	may be different from the alignment for the container. 

	@param align the alignment of the cells to be added.
	This overrides the default alignment for the column or row. 
	For a row, it is the
	vertical alignment and for a column it is the horizontal alignment.

	@param cellarray the {@link pagelayout.Cell Cell} objects to be added.
	
	@return This object, so that multiple calls to <code>add</code> may be made in a single statement.
**/
	public CellContainer add(int align,Cell... cellarray)
	{
		boolean isRow=isHorizontal();
	  	ensureParallelAlignmentExists();
		for(Cell cell:cellarray)
		{
		  	parallelAlignment.add(
				new Integer(limitAlignment(align,isRow)));
			cells.add(cell);
                	cell.setParent(this);
		}
		return this;
	}
	public static int limitAlignment(int x,boolean isRow)
	{
		switch(x)
		{
			case Cell.TOP:
			case Cell.CENTER:
			case Cell.BOTTOM:
			case Cell.JUSTIFIED:
					return x;
			
		}
		return Cell.NO_ALIGNMENT;
	}
/**
	Returns a string representation of this object.
	
	@return The string representation of the object.
**/

	public String toString()
	{
		int n=cells.size();
		String s=getClass().getName()+ " ";
		for(int i=0;i<n;i++)
		{
			s=s+"\n\t"+cells.elementAt(i).toString();
		}
		return s+"\n";
	}
/**
	Recursively calls the 
	{@link pagelayout.Cell#addComponentsToContainer addComponentsToContainer} 
	method of each cell within this row or column to add the components of the cells
	to the container of the type <code>java.awt.Container</code> .

	@param container the container to which the components of the cells need to be added.

**/
	public void addComponentsToContainer(Container container)
	{	
		cells.removeLastGapIfNeeded();
		int n=cells.size();
		for(int i=0;i<n;i++)
			cells.elementAt(i).addComponentsToContainer(container);
	}
/**
	Returns the number of elements contained in this row or column.

	@return The number of elements in this cell-container.
**/
	public int numberOfChildren()
	{
		return cells.size();
	}
/**
	Return the cell associated with the given 
	index within this cell-container , with the first cell corresponding the the zero'th index.

	@param index the index of the cell to be returned.
	@return The cell associated with the given index.

**/
	public Cell getChildAt(int index)
	{
		return cells.elementAt(index);
	}
/**
	Replace the cell at the specified index with a new cell.

	@param index the index of the cell to be replaced.
	@param newCell the cell that replaces the current cell at the specified index.
	@param parent the container (<code>java.awt.Container</code>) in which the components of the cells are placed.
	@return <code>true</code> if the replacement was successful, otherwise <code>false</code>.

**/
	public boolean replaceChild(int index, Cell newCell, Container parent)
	{
		if(index>=cells.size())return false;
		if(index<0)return false;
		Cell child=getChildAt(index);
		if(child==null)return false;
		if(parent!=null)
		{
			child.removeAllComponents(parent);
		}
		if(newCell!=null)
		{
			cells.setElementAt(newCell,index);
                	newCell.setParent(this);
			if(parent!=null)
			newCell.addComponentsToContainer(parent);
		}
		else cells.removeElementAt(index);
		return true;
	}
	public void changeAlignment(int halign, int valign)
	{
		boolean isRow=isHorizontal();
		valign=limitAlignment(valign,isRow);
		halign=limitAlignment(halign,false);
		if((halign==halignment)&&(valign==valignment))return;
		valignment=valign;
		halignment=halign;
		cells.changeAlignment(isRow? halignment:valignment,isRow);
		invalidate();
	}
	public int getAlignment(int coord, int defaultValue)
	{
		int x=(coord==0?halignment:valignment);
		return (x<0?defaultValue:x);
	}
	protected void layout(int x, int y, int width, int height)
	{
		BoundSpring p=getBoundSpring();
		int wd=width;
		int hd=height;
		int va=valignment;
		int ha=halignment;
		if(p.isXFixed())
		{
			wd=p.getPreferredWidth();
			if(ha==CENTER)x+=(wd<width? (width-wd)/2:0);
			else if(ha==RIGHT)x+=(wd<width? (width-wd):0);
		}
		else
		{
			if(wd>p.getMaximumWidth())
				wd=p.getMaximumWidth();
			/*
			else if(wd<p.getPreferredWidth())
				wd=p.getPreferredWidth();
			*/
		}
		if(p.isYFixed())
		{
			hd=p.getPreferredHeight();
			if(va==CENTER)y+=(hd<height? (height-hd)/2:0);
			else if(va==BOTTOM)y+=(hd<height? (height-hd):0);
		}
		else
		{
			if(hd>p.getMaximumHeight())
				hd=p.getMaximumHeight();
			/*
			else if(hd<p.getPreferredHeight())
				hd=p.getPreferredHeight();
			*/
		}
		setBounds(x,y,wd,hd);
	}	
	public void xmlserialize(XMLPrintStream out, ComponentXMLSerializer c)
	{
		String cname=(isHorizontal()?"Row":"Column");
		String element="Cell";
		out.beginElement(element);
		out.addAttribute("TypeName",cname);
		out.addAttribute("name",getName());
		out.addAttribute("xalignment",halignment);
		out.addAttribute("yalignment",valignment);
		out.beginChildrenList(element);
		int n=numberOfChildren();
		for(int i=0;i<n;i++)
		{
			Cell cell=getChildAt(i);
			if(CellVector.isFiller(cell))continue;
			cell.xmlserialize(out,c);
		}
		out.endChildrenList(element);
		out.endElement(element);
	}
	public void clear()
	{
		cells.clear();
	}
}
