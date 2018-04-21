/*
	------------------------------------------------
	Cell.java
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
import java.util.HashSet;
import java.util.Vector;
import static java.lang.Math.*;
/**
	<p>
	Cells and the associated objects of the class 
	{@link pagelayout.BoundSpring BoundSpring}, which encapsulates the
	geometrical characteristics of a cell, are the two fundamental
	type of objects that form the basis for the method used by 
	{@link pagelayout.PageLayout PageLayout}  to layout components 
	within a container. 
	</p>

	<p> The methods of this class and its subclasses allow the relative 
	location of the components to be modeled as a set of nested cells that
	form a tree with a top level cell as its root. The leaves
	of this tree are only of two types:
	gaps, which are just empty boxes which may or may not be rigid,
	and component cells, which are cells
	containing components (objects of the type 
	<code>java.awt.Component</code> ). We call these the 
	<i>primitive</i> cells, as opposed to <i>composite</i> cells
	that contain other cells.
	Using the methods of
	the <code>BoundSpring</code> class, each composite cell computes its 
	<code>BoundSpring</code> from the <code>BoundSprings</code> of its
	children. Note that the <code>BoundSpring</code> for each cell
	can be computed
	once and for all immediately after the tree of the 
	cells has been constructed( or modified), and, for this computation,
	there is no need for any information on the size of the container
	on which the components have to be ultimately laid out.</p>

	<p>Whereas the computation of the <code>BoundSpring</code> 
	of a composite
	cell is based on the <code>BoundSprings</code> of its children, 
	during the layout process, the size of the rectangle within which a 
	cell is laid out is determined by its parent. Once the  
	size of the rectangle of the container is known, the top level
	cell computes the part of the rectangle that it will use for
	drawing itself based upon its <code>BoundSpring</code>, and then
	computes the sizes and locations of the
	rectangles that will be assigned to its children. This process
	is recursively applied to descending levels of the tree and
	terminates when each of the cells in the tree has been assigned a 
	rectangle within the container in which to draw itself.</p>

	Any classes that extend this class should at a minimum implement
	the following methods:
	
	<ul>
	<li>{@link pagelayout.Cell#computeBoundSpring computeBoundSpring}, and </li>
	<li>{@link pagelayout.Cell#setBounds setBounds}. Although
	this is not an abstract method, any subclasses that contain components
	should obviously override this method to actually 
	set the bounds of the components. 	
	The class {@link pagelayout.Gap Gap} does not have any components, and
	so it does not override this method.</li>
	</ul>

	Depending upon the behavior that they model, the subclasses may
	also override the following methods of this class.
	
	<ul>
	<li>{@link pagelayout.Cell#numberOfChildren numberOfChildren},</li>
	<li>{@link pagelayout.Cell#getChildAt getChildAt},</li>
	<li>{@link pagelayout.Cell#replaceCell replaceCell},</li>
	<li>{@link pagelayout.Cell#replaceChild replaceChild},</li>
	<li>{@link pagelayout.Cell#addComponentsToContainer addComponentsToContainer},</li>
	<li>{@link pagelayout.Cell#getComponent getComponent},</li>
	<li>{@link pagelayout.Cell#getComponentCell getComponentCell}, and</li>
	<li>{@link pagelayout.Cell#removeComponent removeComponent}.</li>
	</ul>


	@version  1.16  05/10/2008
**/

public abstract class Cell
{
	/**
		Constant used for especifying maximum length of 
		a gap with maximum flexibility.
	**/
	public final static int MAX=32767;
	/**
		Constant for specifying no alignment. Same as TOP or LEFT.
	**/
	public final static int NO_ALIGNMENT=0;
	/**
		Constant for specifying top justified vertical alignment. 
	**/
	public final static int TOP=1;
	/**
		Constant for specifying center justified alignment. 
	**/
	public final static int CENTER=2;
	/**
		Constant for specifying bottom justified vertical alignment. 
	**/
	public final static int BOTTOM=3;
	/**
		Constant for specifying left justified horizontal alignment. 
	**/
	public final static int LEFT=1;
	/**
		Constant for specifying right justified horizontal alignment. 
	**/
	public final static int RIGHT=3;
	/**
		Constant for specifying fully justified alignment. 
	**/
	public final static int JUSTIFIED=4;
	/**
		Constant for specifying Baseline alignment (for rows). 
	**/
	public final static int BASELINE=5;
	/**
		Returned by {@link pagelayout.Cell#replaceCell replaceCell} when
		the cell to replaced is found and successfully replaced.
	**/
	public final static int	FOUND_REPLACED=0;
	/**
		Returned by {@link pagelayout.Cell#replaceCell replaceCell} when
		the cell to be replaced is not found.
	**/
	public final static int	NOT_FOUND=-1;
	/**
		Returned by {@link pagelayout.Cell#replaceCell replaceCell} when
		the cell to replaced is found but could not be replaced.
	*/
	public final static int	FOUND_NOTREPLACED=1;
        private Cell parent;
	private String name=null;
	protected BoundSpring boundSpring; 
	private boolean hFixed, wFixed;
	protected byte halign, valign;
	protected int filledSizeX, filledSizeY;
	protected boolean fixMaxSize;
	protected Vector<PageLayout.ContainerSizeLink> links;
/**
	Creates a cell object. Since this class is abstract, it can be called
	only by the classes that extend it.

**/
	public Cell()
	{
		boundSpring=null;
		hFixed=wFixed=false;
		halign=valign=-1;
		constraint=null;
		filledSizeX=filledSizeY=0;
		fixMaxSize=false;
		links=null;
		parent=null;
	}
        protected void setParent(Cell cell)
	{
		parent=cell;
	}
        public Cell getParent()
	{
		return parent;
	}
	public Cell getRootCell()
	{
		Cell p=this;
		for(;true;)
		{
			Cell p1=p.getParent();
			if(p1==null)break;
			p=p1;
		}
		return p;
	}
/**
	Used by {@link pagelayout.CellContainer CellContianer} to set the
	alignment of the elements of the rows and columns which overrides
	the default alignment of these containers.

	@param coord the direction: 0 for for horizontal alignment, 1 for
	vertical alignment.

	@param align the alignment. 
**/
	protected void setAlignment(int coord, int align)
	{
		if(coord==0)halign=(byte)align;
		else valign=(byte)align;
	}
/**
	Used by {@link pagelayout.Row Row} and   
	{@link pagelayout.Column Column} 
	during layout of the elements to determine if the alignment
	of this cell overrides the default alignment for the container.

	@param coord the direction: 0 for for horizontal alignment, 1 for
	vertical alignment.

	@param defaultValue the default alignment for the row or column.

	@return <code>defaultValue</code> if the alignment has not
	been specified for this cell to override the default, or the
	specified alignment.
**/
	public int getAlignment(int coord, int defaultValue)
	{
		int x=(coord==0?halign:valign);
		return (x<0?defaultValue:x);
	}
/**
	Creates the <code>PageLayout</code> object for the specified container,
	and sets it be the layout manager for the container.

	@param  container the container for which the layout manager is to be
		created.
	@return The created <code>PageLayout</code> layout manager. 
**/
	public PageLayout createLayout(Container container)
	{
		PageLayout lm= new PageLayout(container,this);
		container.setLayout(lm);
		if(links!=null)lm.setLinks(links);
		return lm;
	}
/**
	This method of the top level cell is called by the layout manager
	whenever the container calls the manager to 
	layout the components within it.
	Depending on the preferred and maximum size of the cell, appropriately
	modified parameters of the bounding rectangle within the container
	are passed to the {@link pagelayout.Cell#setBounds setBounds} method
	of this cell.

	In each cell, the the two-dimensional spring associated with
	each child is used to determine the actual layout dimension of the
	box within which the child is to laid out. This process continues 
	recursively till all the components have been laid out.

	@param x the x-coordinate of the top left of the rectangle.
	@param y the y-coordinate of the top left of the rectangle.
	@param width the width of the rectangle.
	@param height the height of the rectangle.
**/
	
	protected void layout(int x, int y, int width,int height)
	{
		BoundSpring p=getBoundSpring();
		int wd=width;
		int hd=height;
		if(p.isXFixed())
			wd=p.getPreferredWidth();
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
			hd=p.getPreferredHeight();
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
/**
	Specifies an identifier for the cell. If each cell is given a unique
	name, the identifier may be used to extract the associated cell by
	name by calling {@link pagelayout.Cell#getCellByName getCellByName} 
	method of the top level cell.

	@param name the specified identifier for the cell.
**/
	
	public void setName(String name)
	{
		this.name=name;
	}
/**
	Returns the name of the cell if it has been given one, otherwise <code>null</code>.

	@return The name of the cell.
**/
	public String getName()
	{
		return name==null?"":name;	
	}
/**
	This method 
	may be over-ridden to add components to the 
	container in all the subclasses
	whose instances may contain components.
	This method
	is called when the {@link pagelayout.PageLayout} is first constructed,
	but may also be called whenever a new component is added via the
	{@link pagelayout.Cell#replaceCell replaceCell} method of this class.
	Even in the later case, there is no need to call this method
	directly. 

	@param container the java.awt.Container object to which the
		components should be added. 

**/ 
	public void addComponentsToContainer(Container container)
	{
		Component c=getComponent();
		if(c!=null)
		{
			Container old=c.getParent();
			if(old!=container)
			{
				if(old!=null)old.remove(c);
				container.add(c);
			}
		}
		int n=numberOfChildren();
		for(int i=0;i<n;i++)
		{
			Cell cell=getChildAt(i);
			if(cell==null)continue;
			cell.addComponentsToContainer(container);
		}
	}

/**
	This method does nothing here, but all the subclasses must over-ride
	it to layout, within the specified rectangle of the container,
	the components which they or their children enclose. This
	method is called every time the container calls the layout manager
	to layout the components within it.

	@param x the x-coordinate of the top left of the rectangle.
	@param y the y-coordinate of the top left of the rectangle.
	@param width the width of the rectangle.
	@param height the height of the rectangle.

**/
	protected void setBounds(int x, int y, int width, int height)
	{
	}
/**
	Should be implemented by all the subclasses to compute the two-dimensional
	spring associated with the cell.
	
	@return The computed two-dimensional spring.

**/
	abstract public BoundSpring computeBoundSpring();
/**
	Returns the two-dimensional spring associated with the cell. If the cell
	has been invalidated, the spring is recomputed.

	@return The two-dimensional spring associated with the cell.
	  
**/
	public BoundSpring getBoundSpring()
	{
		if(boundSpring==null)
		{
			boundSpring=computeBoundSpring();
			if(wFixed)boundSpring.setFixedWidth();
			if(hFixed)boundSpring.setFixedHeight();
		}
		if(fixMaxSize)boundSpring.fixMax();
		return boundSpring;
	}
/**
	Invalidates the tree formed with this cell as the root. This should
	be called whenever any cell in the root has been replaced or removed
	so that the two-dimensional springs associated with cells are 
	recomputed when subsequently needed.
**/
	public void invalidate()
	{
		boundSpring=null;
		int n=numberOfChildren();
		for(int i=0;i<n;i++)
		{
			Cell cell=getChildAt(i);
			if(cell!=null)cell.invalidate();
		}
		
	}
/**
	Should be over-ridden by sub-classes that contain child-cells to
	return the child associated with the given index, or <code>null</code> if
	the index is out of bounds.

	@param index the index of the cell that is needed.

	@return The cell at the given index if it exists or <code>null</code> otherwise.

**/
	public Cell getChildAt(int index)
	{
		return null;
	}
/**
	Should be over-ridden by sub-classes that contain child-cells to
	return the number of children that the object has.

	@return The number of child cells that this cell has.

**/
	public int numberOfChildren()
	{
		return 0;
	}

/**
	Recursively traverses the tree of cells formed with this cell as the root
	to retrieve the cell with the given name. 
	Returns <code>null</code> if no such
	cell is found. Note that it is upto the user to associate unique
	names with cells.

	@param name the name of the cell (as specified in <code>setName</code>)
	to be retrieved.
	
	@return The cell with the given name, 
	and <code>null</code> if no such cell exists.

**/
	public Cell getCellByName(String name)
	{
		String thisName=getName();
		if((thisName!=null)&&(thisName.equals(name)))return this;
		int n=numberOfChildren();
		for(int i=0;i<n;i++)
		{
			Cell child=getChildAt(i);
			if(child==null)continue;
			Cell match=child.getCellByName(name);
			if(match!=null)return match;
		}	
		return null;
	}
/**
	If this cell is a component cell, this method returns the enclosed
	component. Otherwise <code>null</code> is returned.

	@return The component enclosed within this cell, <code>null</code> if none exists.

**/	
	public Component getComponent()
	{
		return null;
	}
/**
	Recursively traverses the tree of cells formed 
	with this cell as the root
	to retrieve the cell containing the given component. 
	Returns null if no such
	cell is found. 

	@param component the component within the cell to be retrieved.
	
	@return The cell enclosing the given component, and null if no such cell exists.

**/
	public ComponentCell getComponentCell(Component component)
	{
		return getComponentCell(component,(Cell[])null);
	}
	private ComponentCell getComponentCell(
		Component component, Cell[] parentHolder)
	{
		if(component==null)return null;
		if(isComponentCell())
		{
			if(component==getComponent())
				return (ComponentCell)this;
		}
		int n=numberOfChildren();
		for(int i=0;i<n;i++)
		{
			Cell child=getChildAt(i);
			if(child==null)continue;
			if((child instanceof ComponentCell)&&
				(child.getComponent()==component))
			{
				if(parentHolder!=null)
					parentHolder[0]=this;
				return (ComponentCell)child;
			}
			ComponentCell match=child.getComponentCell(component,
						parentHolder);
			if(match!=null)return match;
		}	
		return null;
	}
/**
	Should be over-ridden by sub-classes that contain child-cells to
	replace the child associated with the given index by the
	new cell.

	@param index the index of the cell that is to be replaced.
	@param newCell the new cell that replaces the current cell at the specified index. 

	@param parent the container in which the components of the cells are placed.
	@return <code>true</code> if the replacement was successful, otherwise <code>false</code>.

**/
	public boolean replaceChild(int index, Cell newCell, Container parent)
	{
		return false;
	}
/**
	Returns <code>true</code> if this cell encloses a component.

	@return <code>true</code> if this cell encloses a component, <code>false</code> otherwise.

**/
	public boolean isComponentCell()
	{
		return getClass().equals(ComponentCell.class);
	}
/**
	Removes, from the tree that has this cell as the root,
	the component cell that contains the given component. Does nothing
	if no cell containg the given component is found in the tree.

	@param component the component within the cell to be removed.
	

**/
	public void removeComponent(Component component)
	{
		Cell cell=getComponentCell(component);
		if(cell==null)return;
		Container parent=null;
		int result=replaceCell(cell,null,
			parent=component.getParent());
		if(result==FOUND_REPLACED)
		{
			invalidate();
			parent.validate();
		}
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
		if(currentCell==null) return NOT_FOUND;
		/*
		{
			if(CellContainer.class.isInstance(this))
			{
				((CellContainer)this).add(newCell);
				if(parent!=null)
				  newCell.addComponentsToContainer(parent);
				return FOUND_REPLACED;
			}
			return NOT_FOUND;
		}
		*/
		int n=numberOfChildren();
		for(int i=0;i<n;i++)
		{
			Cell child=getChildAt(i);
			if(child==currentCell)
			  return (replaceChild(i, newCell,parent)?
				FOUND_REPLACED:FOUND_NOTREPLACED);
			int result= child.replaceCell(currentCell,newCell,parent);
			if(result!=NOT_FOUND)
				return result;
						
		}	
		return NOT_FOUND;	
	}

	private static void setSize(Component component, int widths[], int[] heights)
	{
		component.setMinimumSize(new Dimension(widths[0],heights[0]));
		component.setMaximumSize(new Dimension(widths[2],heights[2]));
		component.setPreferredSize(new Dimension(widths[1],heights[1]));
	}


/**
	Depending upon the value of the second parameter, 
	returns the minimum, preferred or the maximum size of 
	the given component.

	@param component the component whose size is to be retrieved.

	@param type 0 for retrieving the minimum size, 1 for retrieving the maximum size, and any other number for retrieving the preferred size.

	@return The minimum, preferred or the maximum size of the component.


**/
	public static Dimension getSize(Component component, int type)
	{
		switch(type)
		{
			case 0: return component.getMinimumSize();
			case 1: return component.getMaximumSize();
			default: return component.getPreferredSize();
		}
	}
/**
	Depending upon the value of the third parameter, 
	sets the minimum, preferred or the maximum size of 
	the given component to the specified value.

	@param component the component whose size needs to be reset.

	@param dimension the new dimension of the component.

	@param type 0 for setting the minimum size, 1 for setting the maximum size, and any other number for setting the preferred size.

**/
	public static void setSize(Component component, Dimension dimension,int type)
	{
		switch(type)
		{
			case 0: component.setMinimumSize(dimension);break;
			case 1: component.setMaximumSize(dimension);break;
			default: component.setPreferredSize(dimension);
		}
	}
/**
	Recursively traverses the tree of cells formed with this cell as the root
	to remove all the cells that contain only components.

	@param parent the container in  which the components are placed. If it is <code>null</code>, the parent is retrieved from the first component that is found in the tree.
	
	@return The parent of the removed components.

**/
	public Container removeAllComponents(Container parent)
	{
		Component c=getComponent();
		if(c!=null)
		{
			if(parent==null)parent=c.getParent();
			if(parent!=null) parent.remove(c);
			return parent;	
		}
		int n=numberOfChildren();
		for(int i=0;i<n;i++)
		{
			Cell child=getChildAt(i);
			Container p= child.removeAllComponents(null);
			if(parent==null)parent=p;
		}
		return parent;
	}
/**
	Deprected. Use <code>linkHeight(Component, double,Component...)</code>.

	@param anchor the component whose height determines the height of the
		other components.

	@param components the array of components whose height is 
	linked to the height of the <code>anchor</code>.

	@param factor numerical factor linking the heights of the components 
	to the height of the anchor. Thus the height of each of the component
	is constrained to be the fraction
	<code>factor</code> of the height of the <code>anchor</code>.

	
**/
	public void linkHeight(Component anchor, Component[] components,
			double factor)
	{
		if(components==null)return;
		int n=components.length;
		double[] f=new double[n];
		for(int i=0;i<n;i++)f[i]=factor;
		linkHeight(anchor,components,f);
	}
/**
	This method may be used to constrain the heights of a set of 
	components in a cell to be specified fractions of the width of
	a specified component.The component cells containing the
	anchor and the components must all be the immediate children of
	the same parent. If anchor height is fixed, the 
	heights of the components are also set to be fixed to be the
	specified fraction of the height of the anchor. Otherwise the
	minimum, maximum, and preferred heights of the components are set to
	be the specified fraction of the corresponding heights of the 
	anchor. 

	@param anchor the component whose height determines the height of the
		other components.

	@param factor numerical factor linking the heights of the components 
	to the height of the anchor. Thus the height of each of the component
	is constrained to be the fraction
	<code>factor</code> of the height of the <code>anchor</code>.

	@param components the array of components whose height is 
	linked to the height of the <code>anchor</code>.

	
**/
	public void linkHeight(Component anchor, 
			double factor,Component... components)
	{
		if(components==null)return;
		int n=components.length;
		double[] f=new double[n];
		for(int i=0;i<n;i++)f[i]=factor;
		linkHeight(anchor,components,f);
	}
/**
	Deprecated. Use <code>linkWidth(Component, double, Component...)</code>.

	@param anchor the component whose width determines the width of the
		other components.

	@param components the array of components whose width is 
	linked to the width of the <code>anchor</code>.

	@param factor numerical factors linking the widths of the components 
	to the width of the anchor. Thus the width of each of the component
	is constrained to be the fraction
	<code>factor</code> of the width of the <code>anchor</code>.

	
**/
	public void linkWidth(Component anchor, Component[] components,
			double factor)
	{
		if(components==null)return;
		int n=components.length;
		double[] f=new double[n];
		for(int i=0;i<n;i++)f[i]=factor;
		linkWidth(anchor,components,f);
	}
	public void linkMaximumWidth(Component[] components)
	{
		int n=(components==null?0:components.length);
		if(n<=1)return;
		int wm=components[0].getPreferredSize().width;
		int im=0;
		int k=0;
		for(int i=1;i<n;i++)
		{
		   int w=components[i].getPreferredSize().width;
		   if(w!=wm)k++;
		   if(w<=wm)continue;
		   wm=w;
                   im=i;
		}
		if(k==0)return;
		Component[] nc=new Component[n-1];
		k=0;
		for(int i=0;i<n;i++)if(i!=im)nc[k++]=components[i];
		linkWidth(components[im],nc,1.0);
	}
	public void linkMaximumHeight(Component[] components)
	{
		int n=(components==null?0:components.length);
		if(n<=1)return;
		int hm=components[0].getPreferredSize().height;
		int im=0;
		int k=0;
		for(int i=1;i<n;i++)
		{
		   int h=components[i].getPreferredSize().height;
		   if(h!=hm)k++;
		   if(h<=hm)continue;
		   hm=h;
                   im=i;
		}
		if(k==0)return;
		Component[] nc=new Component[n-1];
		k=0;
		for(int i=0;i<n;i++)if(i!=im)nc[k++]=components[i];
		linkHeight(components[im],nc,1.0);
	}
	
/**
	This method may be used to constrain the widths of a set of 
	components in a cell to be specified fractions of the width of
	a specified component, the anchor. The component cells containing the
	anchor and the components must all be the immediate children of
	the same parent.  If anchor width is fixed, the 
	widths of the components are also set to be fixed to be the
	specified fraction of the width of the anchor. Otherwise the
	minimum, maximum, and preferred widths of the components are set to
	be the specified fraction of the corresponding witdths of the 
	anchor.
		

	@param anchor the component whose width determines the width of the
		other components.


	@param factor numerical factors linking the widths of the components 
	to the width of the anchor. Thus the width of each of the component
	is constrained to be the fraction
	<code>factor</code> of the width of the <code>anchor</code>.
	@param components the array of components whose width is 
	linked to the width of the <code>anchor</code>.

	
**/
	public void linkWidth(Component anchor,double factor, 
			Component... components)
	{
		if(components==null)return;
		int n=components.length;
		double[] f=new double[n];
		for(int i=0;i<n;i++)f[i]=factor;
		linkWidth(anchor,components,f);
	}
/**
	This method may be used to constrain the widths of a set of 
	components in a cell to be specified fractions of the width of
	a specified component.The component cells containing the
	anchor and the components must all be the immediate children of
	the same parent. If anchor width is fixed, the 
	widths of the components are also set to be fixed to be the
	specified fraction of the width of the anchor. Otherwise the
	minimum, maximum, and preferred widths of the components are set 
	to the the specified fraction of the corresponding witdths of the 
	anchor.

	@param anchor the component whose width determines the width of the
		other components.

	@param components the array of components whose width is 
	linked to the width of the <code>anchor</code>.

	@param factors numerical factors linking the widths of the components 
	to the width of the anchor. Thus the width of the component
	<code>components[i]</code> is constrained to be the fraction
	<code>factors[i]</code> of the width of the <code>anchor</code>.

	
**/
	public void linkWidth(Component anchor, Component[] components,
			double factors[])
	{
		linkDim(anchor,components,factors,0);
	}
	private void linkChildDim(Cell anchorCell,
		Component anchor, Component[] components,
				double[] factors,int coord)
	{
		int m=components.length;
		int[] index=new int[m];
		int n=numberOfChildren();
		int nf=(factors==null?0:factors.length);
		for(int i=0;i<m;i++)index[i]=-1;
		int anchorIndex=-1;
		int numValid=0;
		for(int i=0;i<n;i++)
		{
			Cell c=getChildAt(i);
			Component cc=c.getComponent();
			if(cc==null)continue;
			if(anchorIndex<0)
			{
				if(cc==anchor)
				{
					anchorIndex=i;
					continue;
				}
			}
			for(int j=0;j<m;j++)
			{
				if(index[j]>=0)continue;
				if(cc==components[j])
				{
					index[j]=i;
					numValid++;
				}
			}
			
		}
		
		if((numValid==0)||(anchorIndex<0))	
			return;
		Component[] c=components;
		Dimension dd[]=new Dimension[3];
		Dimension da[]=new Dimension[3];
		for(int type=0;type<3;type++)
		{
			da[type]=getSize(anchor,type);
		}
		for(int i=0;i<m;i++)
		{
			if(index[i]<0)
				continue;
		        double f=(i<nf?factors[i]:1);
			for(int type=0;type<3;type++)
			{
				Dimension d=da[type];
				int anchorDim=getDim(d,coord);
				Dimension dc=getSize(c[i],type);
				int componentDim=(int)round(f*anchorDim);
				if(anchorDim==Cell.MAX)componentDim=Cell.MAX;
				dc=new Dimension(dc.width,dc.height);
				setDim(dc,coord,componentDim);
				dd[type]=new Dimension(dc);
			}
			EasyCell.setDimensions(c[i],dd);
			
		}
		invalidate();
		if((numValid==0)||(anchorIndex<0))	
			return;
		boolean add= ((coord==0)&& (this instanceof Column));
		add=add||((coord==1)&&(this instanceof Row));
		if(add)
		{
		  if(constraint==null)
			constraint=new Constraint(numberOfChildren());
		  constraint.updateConstraint(anchorIndex,index,factors);
		}
	}
	protected Constraint constraint;
/**
	Used internally by the {@link pagelayout.Row Row}
	and {@link pagelayout.Column Column} objects for imposing the
	constraints created by the calls to  the 
	{@link pagelayout.Cell#linkWidth linkWidth} and 
	{@link pagelayout.Cell#linkHeight linkHeight} methods.
**/
	protected Constraint getConstraint()
	{
		return constraint;
	}
	protected void dupConstraint(Cell c)
	{
		if(c.constraint!=null)
		constraint=new Constraint(c.constraint);
	}
	protected static class Constraint
	{
	  int[] anchorIndices;
	  double[] factors;
	  boolean[] isAnchor;
	  int numCells;
	  public Constraint(int n)
	  {
		anchorIndices=new int[n];
		factors=new double[n];
		isAnchor=new boolean[n];
		numCells=n;
		for(int i=0;i<n;i++)
		{
			anchorIndices[i]=-1;
			isAnchor[i]=false;
		}
	  }
	  public Constraint(Constraint c)
	  {
		this(c.numCells);
		for(int i=0;i<numCells;i++)
		{
			anchorIndices[i]=c.anchorIndices[i];
			factors[i]=c.factors[i];
			isAnchor[i]=c.isAnchor[i];
		}
	  }
	  public void updateConstraint(int anchorIndex, 
		int[] cellIndices, double scale[])
	  {
		int nc=cellIndices.length;
		for(int i=0;i<nc;i++)
			if(cellIndices[i]==anchorIndex)cellIndices[i]=-1;
		if(anchorIndex>=numCells)return;
		if(isAnchor[anchorIndex])
		{
			for(int i=0;i<nc;i++)
			{
				int ind=cellIndices[i];
				if(ind<0)continue;
				if(ind>=numCells)continue;
				if(isAnchor[ind])
				{
					for(int j=0;j<numCells;j++)
					{
						if(anchorIndices[j]==ind)
						{
							anchorIndices[j]=
								anchorIndex;
							factors[j]*=scale[i];
						}
					}
					isAnchor[ind]=false;
				}
			}
			for(int i=0;i<nc;i++)
			{
				int ind=cellIndices[i];
				if(ind<0)continue;
				if(ind>=numCells)continue;
				factors[ind]=scale[i];
				anchorIndices[ind]=anchorIndex;
			}
		}	
		else if(anchorIndices[anchorIndex]>=0)
		{
			int aind=anchorIndices[anchorIndex];
			for(int i=0;i<nc;i++)
			{
				int ind=cellIndices[i];
				if(ind<0)continue;
				if(ind>=numCells)continue;
				anchorIndices[ind]=aind;
				factors[ind]*=scale[i];
			}
		}
		else
		{
			isAnchor[anchorIndex]=true;
			for(int i=0;i<nc;i++)
			{
				int ind=cellIndices[i];
				if(ind<0)continue;
				if(ind>=numCells)continue;
				anchorIndices[ind]=anchorIndex;
				factors[ind]=scale[i];
			}
		}
	   }
	   public void setSize(int size[], int n, int available)
	   {
		for(int i=0;i<n;i++)size[i]=-1;
		for(int i=0;(i<numCells)&&(i<n);i++)
		{
			if(!isAnchor[i])continue;
			double fmax=1;
			for(int j=0;(j<numCells)&&(j<n);j++)
			{
				if(anchorIndices[j]==i)
					if(fmax<factors[j])fmax=factors[j];
			}
			double f=available/fmax;
			size[i]=(int)round(f);
			for(int j=0;(j<numCells)&&(j<n);j++)
			{
				if(anchorIndices[j]==i)
				{
					size[j]=(int)round(factors[j]*f);
				}
			}
		}	
	   }
	}
/**
	This method may be used to constrain the heights of a set of 
	components in a cell to be specified fractions of the height of
	a specified component.The component cells containing the
	anchor and the components must all be the immediate children of
	the same parent. If anchor height is fixed, the 
	heights of the components are also set to be fixed to be the
	specified fraction of the height of the anchor. Otherwise the
	minimum, maximum, and preferred heights of the components are set 
	to be the specified fraction of the corresponding heights of the 
	anchor. 

	@param anchor the component whose height determines the height of the
		other components.

	@param components the array of components whose height 
	is linked to the height of the <code>anchor</code>.

	@param factors numerical factors linking the height of the components 
	to the height of the anchor. Thus the height of the component
	<code>components[i]</code> is constrained to be the fraction
	<code>factors[i]</code> of the height of the <code>anchor</code>.

	
**/
	public void linkHeight(Component anchor, Component[] components,
			double factors[])
	{
		linkDim(anchor,components,factors,1);
	}
	public static int getDim(Dimension d, int coord)
	{
		return (coord==0? d.width:d.height);
	}
	public static void setDim(Dimension d,int coord, int value)
	{
		if(coord==0)d.setSize(value,d.height);
		else d.setSize(d.width,value);
	}
	private void linkDim(Component anchor, Component[] components,
			double factors[],int coord)
	{
		Component[] c=components;
		Dimension d=null;
		int anchord[]=new int[3];
		for(int i=0;i<3;i++)
		{
			d=getSize(anchor,i);
			anchord[i]=getDim(d,coord);	
		}
		Cell[] parentHolder=new Cell[1];
		int m=c.length;
		int n=(factors==null?0:factors.length);
		Dimension dd[]=new Dimension[3];
		for(int i=0;i<m;i++)
		{
			Cell cc=getComponentCell(components[i],
					parentHolder);
			//if(cc==null)continue;
			//if(parentHolder[0]==null)continue;
			for(int type =0;type<3;type++)
			{
			       double f =(i<n?factors[i]:1);
                               int anchorSize=(int)round(f*anchord[type]);
			       d=getSize(c[i],type);
			       d=new Dimension(d.width,d.height);
			       setDim(d,coord,anchorSize);
			       dd[type]=d;
			}
			EasyCell.setDimensions(c[i],dd);
			if(cc!=null)cc.invalidate();
			if(parentHolder[0]!=null)
				parentHolder[0].invalidate();
		}
		if((anchord[0]==anchord[1])&&(anchord[1]==anchord[2]))
		{
		  invalidate();
		  return;
		}
		Cell anchorCell=getComponentCell(anchor,parentHolder);
		if((anchorCell==null)||(parentHolder[0]==null))
		{
			return;
		}
		parentHolder[0].linkChildDim(anchorCell,
			anchor,components,factors,coord);
		invalidate();
	}
/**
	Depending upon the value of the
	second parameter sets the width of a component to be fixed or 
	changeable if the space allows it.

	@param component The component to be modfied
	@param fixed If <code>true</code> the horizontal 
			spring of the component is modified to be fixed.
		     Otherwise it's modified to be flexible.
		
**/
	
	public void setFixedWidth(Component component, boolean fixed)
	{
		if(fixed)
		{
			Dimension d=component.getPreferredSize();
			Dimension dm=component.getMaximumSize();
			if(dm.width!=d.width)
			component.setMaximumSize(new Dimension(d.width,dm.height));	
		}
		else
		{
			Dimension dm=component.getMaximumSize();
			component.setMaximumSize(new Dimension(MAX,dm.height));
		}
		invalidate();
	}
/**
	Depending upon the value of the
	second parameter sets the width of a set of components to be fixed or 
	changeable if the space allows it.
        Deprected. Use <code>setFixedWidth(boolean, Component...)</code>.

	@param components The array of components to be modfied
	@param fixed If <code>true</code> the horizontal 
			springs of the components are modified to be fixed.
		     Otherwise they are modified to be flexible.
		
**/
	public void setFixedWidth(Component[] components, boolean fixed)
	{
		for(Component component:components)
			setFixedWidth(component,fixed);
		invalidate();
	}
/**
	Depending upon the value of the
	first parameter sets the width of a set of components to be fixed or 
	changeable if the space allows it.

	@param fixed If <code>true</code> the horizontal 
			springs of the components are modified to be fixed.
		     Otherwise they are modified to be flexible.
	@param components The components to be modfied
		
**/
	public void setFixedWidth(boolean fixed, Component... components )
	{
		for(Component component:components)
			setFixedWidth(component,fixed);
		invalidate();
	}
/**
	Depending upon the value of the
	second parameter sets the height of a component to be fixed or 
	changeable if the space allows it.

	@param component The component to be modfied
	@param fixed If <code>true</code> the vertical 
			spring of the component is modified to be fixed.
		     Otherwise it's modified to be flexible.
		
**/
	public void setFixedHeight(Component component, boolean fixed)
	{
		
		if(fixed)
		{
			Dimension d=component.getPreferredSize();
			Dimension dm=component.getMaximumSize();
			if(dm.height!=d.height)
			component.setMaximumSize(new Dimension(dm.width,d.height));	
		}
		else
		{
			Dimension dm=component.getMaximumSize();
			component.setMaximumSize(new Dimension(dm.width,MAX));
		}
		invalidate();
	}


/**
	<p>
	Depending upon the value of the
	first parameter sets the height of a set of components to be fixed or 
	changeable if the space allows it.
	Deprecated. Use <code>setFixedHeight(boolean,Component... components)</code>.



	@param components The array of components to be modfied
	@param fixed If <code>true</code> the vertical 
			springs of the components are modified to be fixed.
		     Otherwise they are modified to be flexible.
		
**/
	public void setFixedHeight(Component[] components, boolean fixed)
	{
           for(Component component:components)setFixedHeight(component,fixed);
	   invalidate();
	}
/**
	<p>
	Depending upon the value of the
	first parameter sets the height of a set of components to be fixed or 
	changeable if the space allows it.

	@param fixed If <code>true</code> the vertical 
			springs of the components are modified to be fixed.
		     Otherwise they are modified to be flexible.
	@param components The array of components to be modfied
		
**/
	public void setFixedHeight(boolean fixed, Component... components)
	{
           for(Component component:components)setFixedHeight(component,fixed);
	   invalidate();
	}
/**
	Depending upon the value of the
	second parameter sets the width of a set of cells to be fixed or 
	changeable if the space allows it.
	Deprecated. Use <code>setFixedWidth(boolean,Cell...)</code>

	@param cells The array of cells to be modfied
	@param fixed If <code>true</code> the horizontal 
			springs of the cells are modified to be fixed.
		     Otherwise they are modified to be flexible.
		
**/
	public void setFixedWidth(Cell[] cells, boolean fixed)
	{
		int n=(cells==null?0:cells.length);	
		for(int i=0;i<n;i++)
		{
			cells[i].wFixed=fixed;
			cells[i].invalidate();
		}
		invalidate();
	}
/**
	Depending upon the value of the
	first parameter sets the width of a set of cells to be fixed or 
	changeable if the space allows it.

	@param fixed If <code>true</code> the horizontal 
			springs of the cells are modified to be fixed.
		     Otherwise they are modified to be flexible.
	@param cells The array of cells to be modfied
		
**/
	public void setFixedWidth(boolean fixed, Cell... cells)
	{
		int n=(cells==null?0:cells.length);	
		for(int i=0;i<n;i++)
		{
			cells[i].wFixed=fixed;
			cells[i].invalidate();
		}
		invalidate();
	}
/**
	Depending upon the value of the
	second parameter sets the height of a set of cells to be fixed or 
	changeable if the space allows it.
	Deprecated. Use <code>setFixedHeight(boolean, Cell...)</code>.

	@param cells The array of cells to be modfied
	@param fixed If <code>true</code> the vertical 
			springs of the cells are modified to be fixed.
		     Otherwise they are modified to be flexible.
		
**/
	public void setFixedHeight(Cell[] cells, boolean fixed)
	{
		int n=(cells==null?0:cells.length);	
		for(int i=0;i<n;i++)
		{
			cells[i].hFixed=fixed;
			cells[i].invalidate();
		}
		invalidate();
	}
/**
	Depending upon the value of the
	first parameter sets the height of a set of cells to be fixed or 
	changeable if the space allows it.
	Deprecated. Use <code>setFixedHeight(boolean, Cell...)</code>.

	@param fixed If <code>true</code> the vertical 
			springs of the cells are modified to be fixed.
		     Otherwise they are modified to be flexible.
	@param cells The array of cells to be modfied
		
**/
	public void setFixedHeight(boolean fixed, Cell... cells)
	{
		int n=(cells==null?0:cells.length);	
		for(int i=0;i<n;i++)
		{
			cells[i].hFixed=fixed;
			cells[i].invalidate();
		}
		invalidate();
	}
/**
	Depending upon the value of the
	second parameter sets the size of a component to be fixed or 
	changeable if the space allows it.

	@param component The component to be modfied
	@param fixed If <code>true</code> the horizontal and vertical
			springs of the component is modified to be fixed.
		     Otherwise they are modified to be flexible.
		
**/
	public void setFixedSize(Component component, boolean fixed)
	{
		if(fixed)
		{
			Dimension d=component.getPreferredSize();
			component.setMaximumSize(new Dimension(d.width,d.height));	
		}
		else
		{
			component.setMaximumSize(new Dimension(MAX,MAX));
		}
		invalidate();
	}	

/**
	Depending upon the value of the
	second parameter sets the size of a component to be fixed or 
	changeable if the space allows it.
	Deprecated. Use <code>setFixedSize(boolean,Component...)</code>.

	@param components The components to be modfied
	@param fixed If <code>true</code> the horizontal and vertical
			springs of the component is modified to be fixed.
		     Otherwise they are modified to be flexible.
		
**/
	public void setFixedSize(Component[] components, boolean fixed)
	{
	   for(Component component: components)
		setFixedSize(component,fixed);
	   invalidate();
	}	
/**
	Depending upon the value of the
	first parameter sets the size of a component to be fixed or 
	changeable if the space allows it.

	@param fixed If <code>true</code> the horizontal and vertical
			springs of the component is modified to be fixed.
		     Otherwise they are modified to be flexible.
	@param components The components to be modfied
		
**/
	public void setFixedSize(boolean fixed,Component... components )
	{
	   for(Component component: components)
		setFixedSize(component,fixed);
	   invalidate();
	}	
/**
	Returns <code>true</code>
	 if the height of the component can change if the size of
	the container allows it.

	@param component the component whose property is being queried.
	@return <code>true</code> if the height of the component may change
	depending upon the available space, otherwise <code>false</code>.
**/
	public boolean isFixedHeight(Component component)
	{
		Dimension d=component.getPreferredSize();
		Dimension dm=component.getMaximumSize();
		return (d.height==dm.height);
	}
/**
	Returns <code>true</code>
	 if the width of a component can change if the size of
	the container allows it.

	@param component the component whose property is being queried.
	@return <code>true</code> if the width  of the component may change
	depending upon the available space, otherwise <code>false</code>.
**/
	public boolean isFixedWidth(Component component)
	{
		Dimension d=component.getPreferredSize();
		Dimension dm=component.getMaximumSize();
		return (d.width==dm.width);
	}
/**
	Returns <code>true</code>
	 if the width or height of a component can change if the size of
	the container allows it.

	@param component the component whose property is being queried.
	@return <code>true</code> if the width or height of the 
	component may change depending upon the available space, 
	otherwise <code>false</code>.
**/
	public boolean isFixedSize(Component component)
	{
		Dimension d=component.getPreferredSize();
		Dimension dm=component.getMaximumSize();
		return (d.width==dm.width)&&(d.height==dm.height);
	}
/**
	Sets the size of the empty area around a component.
	
	@param hgap the width of the vertical strips around the vertical edges.
	@param vgap the height of the horizontal strips around the horizontal edges.
**/
	public void setComponentGaps(int hgap,int vgap)
	{
		int n=numberOfChildren();
		for(int i=0;i<n;i++)
		{
			Cell c=getChildAt(i);
			if(c==null)continue;
			c.setComponentGaps(hgap,vgap);
		}
		invalidate();
	}
/**
	Returns the <i>baseline</i> of a cell. The baseline is defined 
	for components which display a single line of text, and it is
	the distance between the baseline of the text and the upper edge
	of the component. In the general case, the baseline is not defined,
	and this method return -1. The subclasses of this class should 
	override this method to return appropriate values.

	@return -1, as for the general case the baseline is not defined.


@see  ComponentCell#getBaseline
@see  Row#getBaseline
@see  Column#getBaseline
**/
	public int getBaseline()
	{
		return -1;
	}
/**
	Aligns components along the baseline.  See 
	{@link pagelayout.Cell#getBaseline getBaseline} for the definition
	of the baseline.

	@param components array of components which have to aligned. Every
	component which is an element of this array
	must be in the tree rooted at this cell. For the
	alignment to make any sense, the components should have been placed
	in the same {@link pagelayout.Row Row}  or
	{@link pagelayout.GridRow GridRow}. 

@see  Cell#getBaseline
**/

	public void alignBaseline(Component... components)
	{
		int n=components.length;
		if(n<=1)return;
		ComponentCell[] cells=new ComponentCell[n];
		int k=0;
		for(int i=0;i<n;i++)
		{
			ComponentCell c=cells[i]=
				getComponentCell(components[i]);
			if(c!=null)k++;
		}
		if(k<=1)return;
		alignBaseline(cells,n);
	}
	protected  void alignBaseline(ComponentCell cells[], int n)
	{
		int k=selectForBaselineAlignment(cells,n);
		if(k<=1)return;
		int bmax=0;
		k=0;
		for(int i=0;i<n;i++)
		{
			Cell c=cells[i];
			if(c==null)continue;
			int b= c.getBaseline();
			if(b<0)continue;
			k++;
			if(bmax<b)bmax=b;
		}
		if(k<=1)return;
		for(int i=0;i<n;i++)
		{
			ComponentCell c=cells[i];
			if(c==null)continue;
			int b= c.getBaseline();
			if(b<0)continue;
			c.setBaselineOffset(bmax-b);
		}
	}
	
	public static void checkArgs(String method,Object... o)
	{
		for(Object obj:o)
		{
			if(!isCellOrComponent(obj))
				throw new IllegalArgumentException(
					"The arguments to "+method+
					" must be objects of the type Cell "+
					" or Component");
		}
	}
	protected static void addArgs(CellContainer c, Object... o)
	{
		for(Object obj:o)
		{
			if(isCell(obj))
				c.add((Cell)obj);
			else c.add((Component)obj);
		}
	}
	protected static boolean isCell(Object obj)
	{
		if(obj==null)return false;
		Class clazz=obj.getClass();
		return Cell.class.isAssignableFrom(clazz);
	}
	protected static boolean isCellOrComponent(Object obj)
	{
		if(obj==null)return false;
		Class clazz=obj.getClass();
		return Cell.class.isAssignableFrom(clazz)||
			Component.class.isAssignableFrom(clazz);
	}
	private static HashSet<Class> baselineClasses;
	static
	{
		initBaselineClasses();
	}
	private static void initBaselineClasses()
	{
		baselineClasses=new HashSet<Class>();
		baselineClasses.add(JLabel.class);
		baselineClasses.add(JButton.class);
		baselineClasses.add(JRadioButton.class);
		baselineClasses.add(JTextField.class);
		baselineClasses.add(JTextArea.class);
		baselineClasses.add(JCheckBox.class);
		baselineClasses.add(JComboBox.class);
		baselineClasses.add(pagelayout.util.NamedSeparator.class);
	}
	private int selectForBaselineAlignment(ComponentCell cells[], int n )
	{
		
		int k=0;
		for(int i=0;i<n;i++)
		{
			ComponentCell cell=cells[i];
			if(cell==null)continue;
			if(!baselineClasses.contains(
				cell.getComponent().getClass()))
				cells[i]=null;
			else k++;
		}
		return k;
		
	}
	protected final static Cell NEWROW=new Gap(0);
	protected final static Cell SKIP=new Gap(0);
	abstract public Cell duplicate(ComponentDuplicator c);
	abstract public void xmlserialize(XMLPrintStream output,
						ComponentXMLSerializer c);
	public ComponentCell getComponentCell()
	{
		return null;
	}
	public int getFilledSizeX(){ return filledSizeX;}
	public int getFilledSizeY(){ return filledSizeY;}
	public void linkToContainerHeight(Component[] components, 
			double[][] affineConstants)
	{
		PageLayout.ContainerSizeLink link=
			PageLayout.createLink(components,affineConstants,false);
		if(link==null)return;
		if(links==null)
			links=new Vector<PageLayout.ContainerSizeLink>();
		links.add(link);
	}
	public void linkToContainerWidth(Component[] components, 
			double[][] affineConstants)
	{
		PageLayout.ContainerSizeLink link=
			PageLayout.createLink(components,affineConstants,true);
		if(link==null)return;
		if(links==null)
			links=new Vector<PageLayout.ContainerSizeLink>();
		links.add(link);
	}
	public static void setBounds(Cell cell, int x,int y, int w, int h)
	{
		cell.setBounds(x,y,w,h);
	}
	public Container getContainer()
	{
		if(parent!=null)return null;
		Component c=getComponent();
		if(c==null)
		{
			int n=numberOfChildren();
			for(int i=0;i<n;i++)
			{
				Cell cell=getChildAt(i);
				Component cc=cell.getComponent();
				if(cc!=null)
				{
					c=cc;
					break;
				}
			}
		}
		if(c==null)return null;
		return c.getParent();

	}
/**
	Shows or hides all the components in the cells rooted at this cell.

	@param flag <code>true</code> if the components are to be
	shown, and <code>false</code> if they are to be hidden.
**/
	public void setVisible(boolean flag)
	{
		Component c=getComponent();
		if(c!=null)c.setVisible(flag);
		int n=numberOfChildren();
		for(int i=0;i<n;i++)
		{
			Cell cell=getChildAt(i);
			cell.setVisible(flag);
		}
	}
	/**
		Ceates a container whose top level cell is this cell. This
		container can be used as the input to the 
		<code>setViewportView</code> method of the 
		<code>JScrollPane</code>, or any of the 
		<code>setLeftComponent</code>,
		<code>setRightComponent</code>,
		<code>setTopComponent</code>, and 
		<code>setBottomComponent</code> methods of
		<code>JSplitPane</code>.

		@return a container whose layout manager is an instance of the 
		PageLayout, with its top level cell being this cell.

	**/
	public Container createContainer()
	{
		JPanel p=new JPanel();
		PageLayout pl=createLayout(p);
		return p;
	}
}
