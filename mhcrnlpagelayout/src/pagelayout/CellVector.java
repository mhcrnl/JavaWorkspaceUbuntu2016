/*
	------------------------------------------------
	CellVector.java
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
import java.util.Vector;
/**
	An object of the type <code>CellVector</code> is associated
	with every cell-container, i.e., a {@link pagelayout.Row Row} or a 
	{@link pagelayout.Column Column} object. It is basically a vector
	to store the cells that are added to the associated cell-container.
        However, depending upon the
	alignment of the row or the column, it transparently inserts 
	suitable flexible gaps whenever a cell is added to it. Thus for example,	if the row or the column is  justified, it automatically adds 
	a flexible gaps  whenever a cell is added to the 
	associated cell-container after the first or before the last addition.

	@version  1.16  05/10/2008

**/
	
public class CellVector extends Vector<Cell>
{
	
/**
	The preferred length of a flexible gap.
**/
	public final static int FLEXGAP=1;
/**
	The specified alignment.
**/
	protected int alignment;
/**
	Is the associated row/colum centered?
**/
	protected boolean isCenter;
/**
	Is the associated row/colum justified?
**/
	protected boolean isJustified;
/**
	Is the associated row/colum right/bottom justified?
**/
	protected boolean isRight;
/**
	The default flexible vertical gap.
**/
	protected static Gap VGAP=
		new Gap(0,FLEXGAP,Cell.MAX,false);
/**
	The default flexible horizontal gap.
**/
	protected static Gap HGAP=
		new Gap(0,FLEXGAP,Cell.MAX,true);
/**
	The default flexible gap for this object.
**/
	protected Gap flexGap;
/**
	Does the specified justification require that there be no gaps at
	the tail end of the vector?
**/
	protected boolean noGapsAtEnd;
/**
	Creates a <code>CellVector</code> for a cell-container with given
	alignment and orientation.

	@param align specified alignment (horizontal alignment for a row,or
	vertical alignment for a column).

	@param isRow <code>true</code> for a row, <code>false</code> otherwise. 
**/
	protected CellVector(int align, boolean isRow)
	{
		super();
		setAlignment(align, isRow);
		init();
	}
	protected void init()
	{
		if(isRight)
		{
			super.add(flexGap);
		}
		else if(isCenter)
		{
			super.add(flexGap);
			super.add(flexGap);
		}
	}
	public void clear()
	{
		super.clear();
		init();
	}
	private  void setAlignment(int align, boolean isRow)
	{
		this.alignment=align;
		isCenter=(alignment==Cell.CENTER);
		isJustified=(alignment==Cell.JUSTIFIED);
		isRight=(alignment==(isRow?Cell.RIGHT:Cell.BOTTOM));
		flexGap=(isRow? HGAP:VGAP);
		noGapsAtEnd=(alignment==Cell.RIGHT)||
				(alignment==Cell.JUSTIFIED);
	}
/**
	Adds a cell to this vector with appropriate flexible gap inserted depending upon the specified alignment.

	@param cell the cell object to be added.

	@return <code>true</code> if addition was successful, <code>false</code> otherwise. 
**/
	public boolean add(Cell cell)
	{
		int n=size();
		if(isJustified)
		{
			if(n>0)super.add(flexGap);
			return super.add(cell);
		}
		else if(isCenter) 
			super.add(n-1,cell);
		else return super.add(cell);
		return true;
	}
/**
	Removes the gap at the tail end of the vector if the specified
	alignment so requires.
**/
	protected void removeLastGapIfNeeded()
	{
		if(!noGapsAtEnd)
		{
			return;
		}
		int n=size();
		for(n--;n>=0;n--)
		{
			Cell c=elementAt(n);
			if(c instanceof Gap)
			{
				
				if(c==flexGap)
				{
					remove(n);
				}
			}
			else break;
		}
	}
	public static boolean isFiller(Cell cell)
	{
		return (cell==VGAP)||(cell==HGAP);
	}
	public void changeAlignment(int newalignment, boolean isRow)
	{
		if(alignment==newalignment)return;
		unalign(size());
		alignment=newalignment;
		isCenter=(alignment==Cell.CENTER);
		isJustified=(alignment==Cell.JUSTIFIED);
		isRight=(alignment==(isRow?Cell.RIGHT:Cell.BOTTOM));
		noGapsAtEnd=(alignment==Cell.RIGHT)||
				(alignment==Cell.JUSTIFIED);	
		int n=size();
		if(isCenter)
		{
			insertElementAt(flexGap,0);
			insertElementAt(flexGap,n+1);
		}
		else if(isRight)
			insertElementAt(flexGap,0);
		else if(isJustified)justify(n);
	}
	// Insert in reverse order so that the addition of elements
	// does not change the indices of elements to be added.
	private void justify(int n )
	{
		for(int i=n-1;i>0;i--)
			insertElementAt(flexGap,i);
	}
	// Remove in reverse order so that the removal of the elements
	// does not change the indices of elements to be removed.
	private void unjustify(int n)
	{
		for(int i=n-2;i>0;i-=2)
		{
			removeElementAt(i,n);
		}
	}
	private void unalign(int n)
	{
		if(isRight&&(n>0))removeElementAt(0,n);
		else if(isCenter)
		{
			if(n>0)removeElementAt(n-1,n);
			if(n>1)removeElementAt(0,n);
		}
		else if(isJustified&&(n>0))unjustify(n);
	}
	public void removeElementAt(int i,int n)
	{
		Cell c=elementAt(i);
		if(c!=flexGap)
			return;
		super.removeElementAt(i);
	}
}
