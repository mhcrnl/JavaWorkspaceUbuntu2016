/*
	------------------------------------------------
	CardCell.java
	------------------------------------------------

	This software is distributed unde the 
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
	An object of the class {@link pagelayout.CardCell CardCell} is a 
        collection of named cells of which only one is visible at a 
        given time. The method {@link pagelayout.CardCell#showCell showCell} of
        this class can be used to change the currently visible cell.

        For an example, see {@link examples.CardExample CardExample}.

       
	@version  1.16  05/10/2008
        
**/

public class CardCell extends Cell
{
	private Hashtable<String,Cell> cells;
	private String currentKey;
	private Cell currentCell;
	private BoundSpring maxBounds;
	private boolean useMaxbounds;
	/**
		Creates a <code>CardCell</code> object.
	**/
	public CardCell()
	{
		cells=new Hashtable<String,Cell>();
		currentKey=null;
		currentCell=null;
		maxBounds=null;
		useMaxbounds=true;
	}
	/**
		Adds a component to the <code>CardCell</code>. 

		@param name the identifier for the component. 
		The name can be used
		in the call to 
		the method {@link pagelayout.CardCell#showCell showCell}
		of this class to hide the currently visible component/cell
		and show the component with the given name.

		@param component the component to be added. To add
	**/
	public void add(String name, Component component)
	{
		add(name,new ComponentCell(component));
	}
	/**
		Adds a cell to the <code>CardCell</code>. 

		@param name the identifier for the cell. 
		The name can be used
		in the call to 
		the method {@link pagelayout.CardCell#showCell showCell}
		of this class to hide the currently visible component/cell
		and show the component with the given name.

		@param cell the cell to be added.
	**/
	public void add(String name, Cell cell)
	{
		cells.put(name,cell);
		if(currentKey==null)
		{
			currentKey=name;
			currentCell=cell;
		}
	}
/**
	Computes the two-dimensional spring associated with the currently 
	visible cell or component.

	@return The computed two-dimensional spring.
**/
	public BoundSpring computeBoundSpring()
	{
		if(useMaxbounds)
		{
			computeMaxBounds();
			return maxBounds;
		}
		if(currentCell==null)return (new Gap(0)).computeBoundSpring();
		return currentCell.computeBoundSpring();
	}
	public void invalidate()
	{
		super.invalidate();
		maxBounds=null;
		Enumeration<Cell> e=cells.elements();
		while(e.hasMoreElements())
			e.nextElement().invalidate();
	}
	/**
		There are two options for laying out the components in the
		card cell: (i) keep the size of
		the area in which the currently selected
		component is laid out independent of which component is 
		currently selected, or (ii) let only the size of the currently
		selected component determine the area in which the component
		is laid out. In the latter case, changing the currently selected
		component may also
		lead to change in the size of the area in which
		other components outside the card cell are laid out.

		@param flag set to false if the currently selected component
			is laid out in an area whose size is to be determined by
			the size of this component alone, and true otherwise.
			The default value is true.
	**/
	public void setUseMaxbounds(boolean flag)
	{
		useMaxbounds=flag;
	}
	private void computeMaxBounds()
	{
		Enumeration<Cell> e=cells.elements();
		BoundSpring bs;
		if(e.hasMoreElements())
			bs=e.nextElement().computeBoundSpring();
		else bs=(new Gap(0)).computeBoundSpring();
		while(e.hasMoreElements())
			bs.encloseBoundSpring(
				e.nextElement().computeBoundSpring());
		maxBounds=bs;
	}
/**
	Recursively calls the 
	{@link pagelayout.Cell#addComponentsToContainer addComponentsToContainer} 
	method of each cell within the card to add the components of the cells
	to the container.

	@param parent the container to which the components of the cells need to added.

**/

	public void addComponentsToContainer(Container parent)
	{	
		Enumeration<String> keys=cells.keys();
		while(keys.hasMoreElements())
		{
			String key=keys.nextElement();	
			Cell cell=cells.get(key);
			cell.addComponentsToContainer(parent);
			cell.setVisible(false);
		}
		if(currentCell!=null)
			currentCell.setVisible(true);
	}
/**
	Lays out the currently visble cell or component
        within the specified rectangle of the container.

	@param x the x-coordinate of the top left of the rectangle.
	@param y the y-coordinate of the top left of the rectangle.
	@param width the width of the rectangle.
	@param height the height of the rectangle.
**/
	protected void setBounds(int x, int y, int width, int height)
	{
		if(currentCell!=null)
		currentCell.setBounds(x,y,width,height);
	}
/**
	Calls the {@link pagelayout.Cell#removeAllComponents removeAllComponents}
	method for all the cells in this <code>CardCell</code>.
	

	@param parent the container in  which the components are placed. If it is <code>null</code>, the parent is retrieved from the first component that is found in the tree.
	
	@return The parent of the removed components.

**/
	public Container removeAllComponents(Container parent)
	{
		Enumeration<String> keys=cells.keys();
		Container c=null;
		while(keys.hasMoreElements())
		{
			String key=keys.nextElement();	
			Cell cell=cells.get(key);
			Container p=cell.removeAllComponents(parent);
			if(c==null)c=p;
		}
		return c;
	}
/**
	Returns <code>true</code> if the currently visble cell encloses a component.

	@return <code>true</code> if the currently visible cell encloses a component, <code>false</code> otherwise.

**/
	public boolean isComponentCell()
	{
		if(currentCell!=null)
		currentCell.isComponentCell();
		return false;
	}
/**
	If the currently visble cell is a component cell, 
	this method returns the enclosed
	component. Otherwise <code>null</code> is returned.

	@return The component enclosed within the currently visible cell, <code>null</code> if none exists.

**/
	public Component getComponent()
	{
		return currentCell.getComponent();
	}
	public Cell duplicate(ComponentDuplicator c)
	{
		CardCell card=
		  new CardCell();
		Enumeration<String> keys=cells.keys();
		while(keys.hasMoreElements())
		{
			String key=keys.nextElement();	
			Cell cell=cells.get(key);
			cell=cell.duplicate(c);
			card.add(key,cell);
		}
		return card;
	}
	public void xmlserialize(XMLPrintStream out, ComponentXMLSerializer c)
	{
		String element="Cell";
		String cname="CardCell";
		out.beginElement(element);
	  	out.addAttribute("TypeName",cname);
		out.addAttribute("name",getName());
		out.beginChildrenList(element);
		Enumeration<String> keys=cells.keys();
		while(keys.hasMoreElements())
		{
			String key=keys.nextElement();	
			Cell cell=cells.get(key);
			out.beginElement("KeyCellPair");
			out.addAttribute("Key",key);
			out.beginElement("Cell");
			cell.xmlserialize(out,c);
		  	out.endChildrenList("Cell");
		  	out.endElement("Cell");
		  	out.endChildrenList("KeyCellPair");
		  	out.endElement("KeyCellPair");
			
		}
		out.endChildrenList(element);
		out.endElement(element);
	}
/**
	Returns <code>1</code>, since only one cell is visible at any time.

	@return The number of child cells that this cell has.

**/
	public int numberOfChildren(){ return 1;}
/**
	@return The currently visible cell.

**/
	public Cell getChildAt(int i){ return currentCell;}
/**
	Changes the currently visible cell.
	
	@param name the name of the cell used in the call to the
	{@link pagelayout.CardCell#add(String name, Component c) add} or
	{@link pagelayout.CardCell#add(String name, Cell c) add} method of
	this class.
	
	
**/
	public void showCell(String name)
	{
		if(!cells.containsKey(name))return;
		if(name.equals(currentKey))return;
		Cell cell=cells.get(name);
		if(cell==null)return;
		if(currentCell!=null)currentCell.setVisible(false);
		cell.setVisible(true);
		currentKey=name;
		currentCell=cell;
		Cell root=getRootCell();
		root.invalidate();
		invalidate();
		Container c=root.getContainer();
		if(c!=null)
		{
			c.validate();
			c.repaint();
		}
	}
	public void setComponentGaps(int hgap,int vgap)
	{
		Enumeration<String> keys=cells.keys();
		while(keys.hasMoreElements())
		{
			String key=keys.nextElement();	
			Cell c=cells.get(key);
			c.setComponentGaps(hgap,vgap);
		}
		invalidate();
	}
}
