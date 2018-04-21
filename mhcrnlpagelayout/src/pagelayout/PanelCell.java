/*
	------------------------------------------------
	PanelCell.java
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
	A <code>PanelCell</code> is a cell associated with a child component
	(usually a panel, 
	an object of the class <code>javax.swing.Janel</code>) 
	which itself contains other child components.  The layout of such
	a panel can be managed in one of the two ways. The
	first option is to set a new layout manager for the panel,
	which may or may not be an instance
	of the class {@link pagelayout.PageLayout PageLayout}, and
	to add the panel to its container in 
	the manner required by the
	layout manager of the parent of the panel (e.g. 
	<code>PageLayout</code> requires
	that the panel not be directly be added to the parent, but to
	the tree of cells whose root is the parent's top level cell). The
	{@link pagelayout.PanelCell PanelCell} offers an alternative
	that may be simpler, at least within the context of
	<code>PageLayout</code>.
	An object of the class <code>PanelCell</code> is created by 
	using the panel together with its
	top level cell.  Once instantiated, this  <code>PanelCell</code>
	object can be added to other cells of the parent container just like
	any other cell, and thus there is no need to create a separate
	layout manager for the panel.

	@version  1.16  05/10/2008
**/




public class PanelCell extends Cell
{
	private Cell cell;
	private int vgap, hgap;
	private boolean isTopLevelCell;
	private Container container;
	/**
		Creates the <code>PanelCell</code> associated with
		the given container.

		@param container the container of this <code>PanelCell</code>.

		@param cell the top level cell of the container.
	**/
	public PanelCell (Container container,Cell cell)
	{
		this.container=container;
		this.cell=cell;
		vgap=ComponentCell.VGAP;
		hgap=ComponentCell.HGAP;
		container.setLayout(null);
		isTopLevelCell=false;
		cell.setParent(this);
	}
/**
	Computes the BoundSpring of the panel associated with this
	object on the basis of the BoundSpring of the top level cell.
	
	@return The computed two-dimensional spring.
**/
	public BoundSpring computeBoundSpring()
	{
		BoundSpring bs= cell.computeBoundSpring();
		Insets inset=container.getInsets();
		bs.setInset(2*hgap+inset.left+inset.right,
			2*vgap+inset.top+inset.bottom);
		container.setMinimumSize(
		 new Dimension(bs.getMinimumWidth(),bs.getMinimumHeight()));
		container.setPreferredSize(
		 new Dimension(bs.getPreferredWidth(),bs.getPreferredHeight()));
		return bs;
	
	}

/**
	Adds the panel of this PanelCell to the parent, and the 
	components contained in the top level cell of this object
	to the panel.

	@param parent The container that contains the panel of this PanelCell.
**/
	

	public void addComponentsToContainer(Container parent)
	{	
		if(parent!=container)
		{
			if(container.getParent()!=parent)
			parent.add(container);
			isTopLevelCell=false;
		}
		else isTopLevelCell=true;
		cell.addComponentsToContainer(container);
	}
/**
	Returns 1, as a panel cell has only one child, the top level cell. 

	@return  1, as a panel cell has only one child, the top level cell. 
**/
	public int numberOfChildren()
	{
		return 1;
	}
/**
	Return the top level cell of this panel.

	@param index the index of the cell to be returned. This is ignored.
	@return The top level cell.

**/
	public Cell getChildAt(int index)
	{
		return cell;
	}
/**
	Lays out panel of this PanelCell and its children
	within the specified rectangle of the panel's parent.
	
	@param x the x-coordinate of the top left of the rectangle.
	@param y the y-coordinate of the top left of the rectangle.
	@param width the width of the rectangle.
	@param height the height of the rectangle.
	
**/
	protected void setBounds(int x, int y, int width, int height)
	{
		BoundSpring bs=getBoundSpring();
		int w=width;
		int h=height;
		Insets inset=container.getInsets();
		if(bs.isXFixed())width=bs.getPreferredWidth();
		else width-=(2*hgap+inset.left+inset.right);

		if(bs.isYFixed())height=bs.getPreferredHeight();
		else height-=(2*vgap+inset.top+inset.bottom);
		if(!isTopLevelCell)
		container.setBounds(x,y,w,h);
		cell.setBounds(hgap+inset.left,vgap+inset.top,width,height);
		
	}
/**
	Removes the panel associated with this object from its parent,
	and all the components that are in the tree of the cells that
	has its root the top level cell of the panel.
	

	@param parent not used.
	
	@return The parent of the panel.

**/
	public Container removeAllComponents(Container parent)
	{
		parent=container.getParent();
		if(parent!=null) parent.remove(container);
		cell.removeAllComponents(container);
		return parent;
	}
/**
	Returns <code>true</code> as this cell encloses a component.

	@return <code>true</code> as this cell encloses a component.

**/
	public boolean isComponentCell()
	{
		return true;
	}
/**
	Returns the panel wrapped by this object.
	component. 

	@return The panel wrapped by this object. 

**/	
	public Component getComponent()
	{
		return container;
	}
/**
	Replaces an existing cell by a new cell. If the first argument is 
	<code>null</code>, and this cell is a row or a column,
	the <code>newCell</code> is added to this cell.

	@param currentCell current cell that is to be replaced. 
	@param newCell the new cell that replaces the current cell at the specified index. 

	@param parent the container in which the components of the cells are placed.
	@return <code>NOT_FOUND</code> if the <code>currentCell</code> was not found, <code>FOUND_NOTREPLACED</code> if it was found but could not be replaced, and <code>FOUND_REPLACED</code> if it was found and successfully replaced.

**/
	public int replaceCell(Cell currentCell, Cell newCell, Container parent)
	{
		return cell.replaceCell(currentCell,newCell,container);
	}
/**
	Sets the size of the empty area around all enclosed components.
	
	@param hgap the width of the vertical strips around the vertical edges.
	@param vgap the height of the horizontal strips around the horizontal edges.
**/
	public void setComponentGaps(int hgap,int vgap)
	{
		int n=numberOfChildren();
		for(int i=0;i<n;i++)
		{
			Cell c=getChildAt(i);
			c.setComponentGaps(hgap,vgap);
		}
		invalidate();
	}
	public void setContainerGaps(int hgap, int vgap)
	{
		this.vgap=vgap;
		this.hgap=hgap;
		invalidate();
		
	}
	public Cell duplicate(ComponentDuplicator c)
	{
		PanelCell p=new PanelCell(
			c.dupContainer(container),cell.duplicate(c));
		p.setContainerGaps(hgap,vgap);
		return p;
	}
	public Cell getCell()
	{
		return cell;
	}
	public void xmlserialize(XMLPrintStream out, ComponentXMLSerializer c)
	{
		String element="Cell";
		String cname="PanelCell";
		out.beginElement(element);
		  out.addAttribute("TypeName",cname);
		out.addAttribute("name",getName());
		  out.addAttribute("xgap",hgap);
		  out.addAttribute("ygap",vgap);
		  out.beginChildrenList(element);
			getCell().xmlserialize(out,c);
			c.xmlserialize(out,container);
		  out.endChildrenList(element);
		out.endElement(element);

	}
	public ComponentCell getComponentCell()
	{
		return getCell().getComponentCell();
	}

}
