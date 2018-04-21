/*
	------------------------------------------------
	GridRow.java
	------------------------------------------------

	This software is distribiuted unde the 
	GNU Lesser General Public  License (LGPL) Version 2.1.

	You should have received a copy of the license with this
	distribution.

	The license is also available at

	http://www.gnu.org/licenses/lgpl.txt	
	
	Copyright (c) 2006 Mathnium Associates.
*/
package  pagelayout;
import static pagelayout.CellGrid.*;
import static pagelayout.Cell.*;
import java.util.*;
import java.awt.*;
/**
	GridRow is a utility class to make it easier to create
	a grid.

	Each row of a grid may be constructed by creating
	an object of the class GridRow. Components or cells can be added
	to the row by using one of the {@link pagelayout.GridRow#add add}
	methods of this class. Any of the components or cells 
	can be made to span
	more than one column by using the {@link pagelayout.GridRow#span span}
	method, and empty columns may be added by using
	the {@link pagelayout.GridRow#skip skip} method of this class. Note
	that a  cell or a component in a column may be made to span more than
	one row by adding the cell
	{@link pagelayout.CellGrid#HSPANCELL CellGrid.VSPANCELL} to the 
	column exactly below the spanning cell/component in the next row.

	 Once all the rows have been 
	so created, the  
	{@link pagelayout.CellGrid#createCellGrid(GridRow... rows) 
		createCellGrid(GridRow... rows) }
	method of {@link pagelayout.CellGrid CellGrid} can be used to construct the grid.

	@version  1.16  05/10/2008

@see examples.Example10
**/
	

public class GridRow
{
	private Vector<Cell> cells;
	private Vector<Align> aligns;
	private static Cell NULLCELL=new Gap(0);

/**
	Creates the GridRow object, to which components or cells can be
	added by using one of the <code>add</code> methods of this class.
**/

	public GridRow()
	{
		init();
	}
/**
	Creates a GridRow object and adds the array of components specified
	as inputs.
	
	@param components the array of components to be added after creattion
	of the object.

**/	
	public GridRow(Component... components)
	{
		init();
		for(Component c:components)add(c);
	} 
	private void init()
	{
		cells=new Vector<Cell>();
		aligns=new Vector<Align>();
	}
/**
	Creates a GridRow object and adds the array of components specified
	as inputs. Each component is added with the specified alignments.
	
	@param halign the horizontal alignment of the components
	@param valign the vertical alignment of the components
	@param components the array of components to be added after creattion
	of the object.

**/	
	public GridRow(int halign, int valign,Component... components)
	{
		init();
		for(Component c:components)add(halign,valign,c);
	} 

/**
	Creates a GridRow object and adds the array of cells specified
	as inputs.
	
	@param cells the array of cells to be added after creattion
	of the object.

**/	
	public GridRow(Cell... cells)
	{
		init();
		for(Cell c:cells)add(c);
	} 
/**
	Creates a GridRow object and adds the array of cells specified
	as inputs. Each cells is added with the specified alignments.
	
	@param halign the horizontal alignment of the cells
	@param valign the vertical alignment of the cells
	@param cells the array of cells to be added after creattion
	of the object.

**/	
	public GridRow(int halign, int valign,Cell... cells)
	{
		init();
		for(Cell c:cells)add(halign,valign,c);
	} 
/**
	Add an array of components to the row.

	@param components the  array of components to be added.

	@return This object.
**/
	public GridRow add(Component... components)
	{
		for(Component component: components)
		{
			cells.add(new ComponentCell(component));
			aligns.add(NOALIGN);
		}
		return this;
	}
/**
	Add an array of components with specified alignments to the row.

	@param halign the horizontal alignment of the components.
	@param valign the vertical alignment of the components.

	@param components  the arary of components to be added.

	@return This object.
**/
	public GridRow add(int halign,int valign,Component... components)
	{
		Align a=new Align(halign,valign);
		for(Component component: components)
		{
			cells.add(new ComponentCell(component));
			aligns.add(a);
		}
		return this;
	}
/**
	Add an array of cells to the row.

	@param cells  the array of cells to be added.

	@return This object.
**/
	public GridRow add(Cell... cells)
	{
		for(Cell cell:cells)
		{
			this.cells.add(cell);
			aligns.add(NOALIGN);
		}
		return this;
	}
/**
	Add an array of cells with specified alignments to the row.

	@param halign the horizontal alignment of the cells.
	@param valign the vertical alignment of the cells.

	@param cells  the arary of cells to be added.

	@return This object.
**/

	
	public GridRow add(int halign,int valign,Cell... cells)
	{
		Align a=new Align(halign,valign);
		for(Cell cell:cells)
		{
			this.cells.add(cell);
			aligns.add(a);
		}

		return this;
	}
/**
	Specifies the number of columns that should be spanned by the
	current component/cell of the row.

	@param n (&gt;=2) the number of columns spanned by the current cell/component.

	@return This GridRow object.
**/

	public GridRow span(int n)
	{
		for(int i=1;i<n;i++)
			add(HSPANCELL);
		return this;
	}
/**
	Adds empty columns to the row at the current position.

	@param n the number of empty columns to add.

	@return This GridRow object.
**/

	public GridRow skip(int n)
	{
		for(int i=0;i<n;i++)
			add(NULLCELL);
		return this;
	}
	private static class Align
	{
		protected byte halign;
		protected byte valign;
		public Align(int halign,int valign)
		{
			this.halign=(byte)halign;
			this.valign=(byte)valign;
		}
	}
/**
	Used by  <code>CellGrid</code>.
	
	The method
	{@link pagelayout.CellGrid#createCellGrid(GridRow... rows) 
		createCellGrid(GridRow... rows) } 
	uses this method to convert 
	a <code>GridRow</code> object to an array of cells.

	@return The array of cells in this row.

**/
	protected Cell[] getCells()
	{
		int n=cells.size();
		Cell[] array=new Cell[n];
		for(int i=0;i<n;i++)
		{
			Cell c=cells.elementAt(i);
			if(c==NULLCELL)c=null;
			array[i]=c;
		}
		return array;
	}
/**
	Used by  <code>CellGrid</code>.

	The method
	{@link pagelayout.CellGrid#createCellGrid(GridRow... rows) 
		createCellGrid(GridRow... rows) } uses this
	method to obtain 
	the vertical alignment of the cell at the given index.
	

	@param index the index of the cell whose alignment is needed.
	@return The vertical alignment of the cell specified by the input.

**/
	protected byte getVerticalAlignment(int index)
	{
		int n=aligns.size();
		if((index>=n)||(index<0))return NO_ALIGNMENT;
		byte v=aligns.elementAt(index).valign;
		if((v>BOTTOM)||(v<0))v=0;
		return v;
	}
/**
	Used by  <code>CellGrid</code>.

	The method
	{@link pagelayout.CellGrid#createCellGrid(GridRow... rows) 
		createCellGrid(GridRow... rows) } 
	uses this method to obtain 
	the horizontal alignment of the cell at the given index.
	

	@param index the index of the cell whose alignment is needed.
	@return The horizontal alignment of the cell specified by the input.

**/
	protected byte getHorizontalAlignment(int index)
	{
		int n=aligns.size();
		if((index>=n)||(index<0))return Cell.NO_ALIGNMENT;
		byte h= aligns.elementAt(index).halign;
		if((h>RIGHT)||(h<0))h=0;
		return h;
	}
/**
	Returns the number of cells in the row.

	@return The number of cells in this row.
**/
	public int size()
	{
		return cells.size();
	}

/**
	Specifies the number of columns that should be spanned by the
	current component/cell of the row. Same as <code>span</code>.

	@param n (&gt;=2) the number of columns spanned by the current cell/component.

	@return This GridRow object.
**/
	public GridRow spanHorizontal(int n)
	{
		return span(n);
	}
/**
	This method is used to specify that the component/cell in the previous
	row immediately above the current column of the row shoud span this
	column.	
	@return This GridRow object.
**/
	public GridRow spanVertical()
	{
		add(VSPANCELL);
		return this;
	}


	private static Align NOALIGN=new Align(NO_ALIGNMENT,NO_ALIGNMENT);
}
