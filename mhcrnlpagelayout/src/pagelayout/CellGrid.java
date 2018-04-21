/*
	------------------------------------------------
	CellGrid.java
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
import static java.lang.Math.*;

/**
	<p>
	A <code>CellGrid</code> is a collection of 
	{@link pagelayout.Cell Cell} objects
	whose elements are
	laid out in the container in a rectangular grid.  
	When a {@link pagelayout.Row Row} 
	object is constructed by adding to it a set of
	{@link pagelayout.Column Column} objects, the rows of the columns 
	may not necessarily align along the horizontal direction, 
	as the internal layout of each of the columns is done independently 
	of the other columns. So, if it is required to align the rows of 
	a group of columns, the <code>CellGrid</code> 
	object should be used instead of columns and rows.</p>

	<p>A <code>CellGrid</code> object is 
	most easily constructed by first building the rows
	of the grid by using the 
	methods of the {@link pagelayout.GridRows GridRows} class
	and by calling its 
	{@link pagelayout.GridRows#createCellGrid createCellGrid } method.

	<p>A <code>CellGrid</code> object may also be 
	constructed by first building
	the elements of the array of cells that form the grid,
	 and then using one of the 
	{@link pagelayout.CellGrid#createCellGrid createCellGrid} 
	methods of this class. </p>

	A number of methods are available to
	modify the layout of the grid once it has been constructed.

	<ul>
	<li> The method {@link pagelayout.CellGrid#setAlignments setAlignments} 
	may be used to specify 
	the vertical and horizontal alignments of each of the cells. </li>
	<li> The method {@link pagelayout.CellGrid#setRowMargins setRowMargins} 
	may be used to specify 
	the left or right margin of one or more of the rows.</li>
	<li> The method 
	{@link pagelayout.CellGrid#setColumnMargins setColumnMargins} 
	may be used to specify 
	the top or bottom margin of one or more of the columns.</li>
	</ul>
	

	<p>The two examples {@link examples.Example4 Example4} 
	 and {@link examples.Example7 Example7} illustrate the  
	use of a <code>CellGrid</code> and the 
	{@link pagelayout.CellGrid#createCellGrid createCellGrid} methods
	of this class. {@link examples.Example9 Example9} illustrates a
	case in which a component spans more than one column. In 
	{@link examples.Example10 Example10} a grid is created by
	first constructing the rows by using the 
	{@link pagelayout.GridRow GridRow} class.
	Finally, in {@link examples.Example11 Example11} a grid is created by
	first constructing the rows by using the 
	{@link pagelayout.GridRows GridRows} class.
	</p>


	@version  1.16  05/10/2008

@see Cell  
@see Column  
@see Row  
@see GridRow  
	
**/
public class CellGrid extends Cell
{

	public final static Cell NULLCELL=new Gap(0);
	protected Cell[][] elements;
	private int nr, nc;
	private BoundSpring rowSprings[];
	private BoundSpring colSprings[];
	private int[][][] alignments;
	protected byte[][] vspan, hspan;
	private boolean fixedRowHeight[], fixedColumnWidth[];
	protected boolean[][] draw;
	protected int colWidths[];
	protected int rowHeights[];
	public final static int NULL=0;
	public final static int HSPAN=1;
	public final static int VSPAN=2;
	public final static int DRAW=3;
/**
	Cell to be inserted in a grid of cells for vertical spanning.
**/
	public final static Cell VSPANCELL=new Gap(2);
/**
	Cell to be inserted in a grid of cells for horizontal spanning.
**/
	public final static Cell HSPANCELL=new Gap(2,true);
/**
	Component to be inserted in a grid of components for horizontal spanning.
**/
	public final static Component HSPANComponent=new javax.swing.JPanel();
/**
	Component to be inserted in a grid of components for vertical spanning.
**/
	public final static Component VSPANComponent=new javax.swing.JPanel();
	protected CellGrid(Cell[][] elements,int m,int n)
	{
		initCellGrid(elements,m,n);
	}
	public void initCellGrid(Cell[][] elements, int m, int n)
	{
		this.elements=elements;
		vspan=new byte[m][n];
		hspan=new byte[m][n];
		draw=new boolean[m][n];
		alignments=new int[2][m][n];
		fixedRowHeight=new boolean[m];
		fixedColumnWidth=new boolean[n];
		for(int i=0;i<m;i++)
		{
			fixedRowHeight[i]=false;
		}
		for(int i=0;i<n;i++)
		{
			fixedColumnWidth[i]=false;
		}
		for(int i=0;i<m;i++)
			for(int j=0;j<n;j++)
			{
				vspan[i][j]=hspan[i][j]=0;
				alignments[0][i][j]=NO_ALIGNMENT;
				alignments[1][i][j]=NO_ALIGNMENT;
			}
		for(int i=0;i<m;i++)
		{
			for(int j=0;j<n;j++)
			{
				Cell c=elements[i][j];
				draw[i][j]=(c!=null)&&(c!=VSPANCELL)&&
						(c!=HSPANCELL);
			
				if(c==null)elements[i][j]=NULLCELL;
				else c.setParent(this);
				if(!draw[i][j])continue;
				int k=1;
				for( ;(j+k)<n;k++)
					if(elements[i][j+k]!=HSPANCELL)break;
				hspan[i][j]=(byte)k;
				for(k=1 ;(i+k)<m;k++)
					if(elements[i+k][j]!=VSPANCELL)break;
				vspan[i][j]=(byte)k;
				int ky=0;
				for(int p=i;(ky<vspan[i][j])&&(p<nr);p++,ky++)
				{
					int kx=0;
					for(int q=j;(kx<hspan[i][j])
							&&(q<nc);q++,kx++)
					{
						if((p==i)||(q==j))continue;
						elements[p][q]=NULLCELL;
					}
				}
				
			}
		}
		nr=m;
		nc=n;
		propagateAlignments();		
	}
	private void propagateAlignments()
	{
		for(int i=0;i<nr;i++)
		{
			for(int j=0;j<nc;j++)
			{
				Cell cell=elements[i][j];
				if(!CellContainer.class.isInstance(cell))
						continue;
				alignments[0][i][j]=
					(byte)cell.getAlignment(0,
							alignments[0][i][j]);
				alignments[1][i][j]=
					(byte)cell.getAlignment(1,
							alignments[1][i][j]);
				
			}
		}
		if(nc<=1)return;
		ComponentCell[] cells=new ComponentCell[nc];
		int p=0;
		int yalign=0;	
		for(int i=0;i<nr;i++)
		{
			int k=0;
			for(int j=0;j<nc;j++)
			{
				cells[j]=null;
				Cell cell=elements[i][j];
				if(cell==null)continue;
				ComponentCell cc=cell.getComponentCell();
				if(cc==null)continue;
				if(k==0)
					yalign=alignments[1][i][j];
				if(alignments[1][i][j]==yalign)
				{
					cells[j]=cc;
					k++;
				}
			}
			if(k>1)alignBaseline(cells,nc);
		}
			
	}
	public void update()
	{
		for(int i=0;i<nr;i++)
		{
			for(int j=0;j<nc;j++)
			{
				Cell c=elements[i][j];
				draw[i][j]=(c!=null)&&(c!=VSPANCELL)&&
						(c!=HSPANCELL);
				if(!draw[i][j])continue;
				if(c!=null)c.setParent(this);
				int k=1;
				for( ;(j+k)<nc;k++)
					if(elements[i][j+k]!=HSPANCELL)break;
				hspan[i][j]=(byte)k;
				for(k=1 ;(i+k)<nr;k++)
					if(elements[i+k][j]!=VSPANCELL)break;
				vspan[i][j]=(byte)k;
				int ky=0;
				for(int p=i;(ky<vspan[i][j])&&(p<nr);p++,ky++)
				{
					int kx=0;
					for(int q=j;(kx<hspan[i][j])&&(q<nc);
								q++,kx++)
					{
						if((p==i)||(q==j))continue;
						replaceChild(
							getIndex(p,q),
							NULLCELL,null);
					}
				}
				
			}
		}
		propagateAlignments();
	}
/**
	Recursively calls the 
	{@link pagelayout.Cell#addComponentsToContainer addComponentsToContainer} 
	method of each cell within the grid to add the components of the cells
	to the container.

	@param container the container to which the components of the cells need to added.

**/
	public void addComponentsToContainer(Container container)
	{
		for(int i=0;i<nr;i++)
			for(int j=0;j<nc;j++)
			  if(elements[i][j]!=null)
				elements[i][j].addComponentsToContainer(container);
	}
/**
	Lays out the cells of the grid within the specified rectangle of the container.

	@param x the x-coordinate of the top left of the rectangle.
	@param y the y-coordinate of the top left of the rectangle.
	@param width the width of the rectangle.
	@param height the height of the rectangle.
**/
	protected void setBounds(int x, int y, int width, int height)
	{
		BoundSpring gridSpring=getBoundSpring();
		colWidths=Row.getColumnWidths(
				x,y,width,height,gridSpring,colSprings,nc);
		rowHeights=Column.getRowHeights(
				x,y,width,height,gridSpring,rowSprings,nr);
		for(int i=0;i<nr;i++)
		{
			int x0=x;
			int rh=rowHeights[i];
			for(int j=0;j<nc;j++)
			{
				int cw=colWidths[j];
				if(draw[i][j])
				{
					int ew=cw;
					for(int k=1;k<hspan[i][j];k++)
						ew+=colWidths[j+k];
					int eh=rh;
					for(int k=1;k<vspan[i][j];k++)
						eh+=rowHeights[i+k];
					setBounds(elements[i][j],
						x0,y,ew,eh,
						alignments[0][i][j],
						alignments[1][i][j],i,j);

					//elements[i][j].setBounds(x0,y,ew,eh);
				}
				x0+=cw;
			} 
			y+=rh;
		}
		
	}
	private void setBounds(Cell c, int x,int y, int w,int h, int halign,
				int valign,int i,int j)
	{
		if(FillerPanelCell.class.isInstance(c))
		{
			c.getComponent().setBounds(x,y,w,h);
			c.setBounds(x,y,w,h);
			return;
		}
		BoundSpring bs=c.getBoundSpring();
		if(bs.isXFixed()&& 
			(halign!=Cell.NO_ALIGNMENT||halign!=Cell.LEFT))
		{
			int ew=bs.getPreferredWidth();
			
			if(w>ew)
			{
				int dw=w-ew;
				if(halign==RIGHT)x+=dw;
				else if(halign==CENTER)x+=(dw/2);
				w=ew;
			}
		}
		if(bs.isYFixed()&& 
			(valign!=Cell.NO_ALIGNMENT||valign!=Cell.BOTTOM))
		{
			int eh=bs.getPreferredHeight();
			if(h>eh)
			{
				int dh=h-eh;
				if(valign==BOTTOM)y+=dh;
				else if(valign==CENTER)y+=(dh/2);
				h=eh;
			}
		}
		c.setBounds(x,y,w,h);
	}	
/**
	This method can be use to set the horizontal and vertical
	alignments of each of the cells in the grid.

	@param halign the two-dimensional array of desired horizontal
	     alignments. If number of rows in this array is the less
		than the  number
	     of rows in the grid, the alignments of the last specified
	     array are copied to the alignments of the rest of the rows.
	     If the number of columns in the array is less than the number of 
	     columns of the grid, no alignments are specified for
	    the remaining columns.

	@param valign	two-dimensional array of desired vertical 
		alignments. If number of rows in this array is the less
	     of rows in the grid, the alignments of the last specified
	     array are copied to the alignments of the rest of the rows.
	     If the number of columns in the array is less than the number of 
	     columns of the grid, no alignments are specified for
	    the remaining columns.

**/
	public void setAlignments(int[][] halign, int[][] valign)
	{
		setAlignments(halign,0);
		setAlignments(valign,1);
	}
/**
	Sets the vertical and horizontal alignments of a cell in the frid.

	@param rowIndex the row index of the cell whose alignment is specified.

	@param columnIndex the column index of the cell whose alignment is specified.
	@param halign horizontal alignment 
	@param valign vertical alignment 
**/
	public void setAlignment(int rowIndex, int columnIndex, int halign, 
			int valign)
	{
		if((rowIndex<0)||(rowIndex>=nr))return;
		if((columnIndex<0)||(columnIndex>=nc))return;
		alignments[0][rowIndex][columnIndex]=
					CellContainer.limitAlignment(
						halign,false);
		alignments[1][rowIndex][columnIndex]=
					CellContainer.limitAlignment(
						valign,false);;
		
	}
	private void setAlignments(int[][] align, int coord) 
	{	
		int[][] array=alignments[coord];
		int n=(align==null? 0:align.length);
		for(int i=0;i<min(n,nr);i++)
		{
			if(align[i]==null)continue;
			int m=align[i].length;
			for(int j=0;j<min(m,nc);j++)
			{
				array[i][j]=
					CellContainer.limitAlignment(
						align[i][j],false);;
			}
		}
		if(n>0)
		  for(int i=n;i<nr;i++)
		  {
			for(int j=0;j<nc;j++)
				array[i][j]=array[i-1][j];
			
		  }
	}
/**
	Creates grid of cells from an array of <code>GridRow</code>
	objects.

	@param rows the array of {@link pagelayout.GridRow GridRow} objects
	from which to create the grid.

**/
	public static CellGrid createCellGrid(GridRow... rows)
	{
		int m=rows.length;
		Cell[][] cells=new Cell[m][];	
		int n=0;
		for(int i=0;i<m;i++)
		{
			cells[i]=rows[i].getCells();
			int k=cells[i].length;
			if(n<k)n=k;
		}
		for(int i=0;i<m;i++)
		{
			int k=cells[i].length;
			if(k<n)
			{
				Cell[] c=new Cell[n];
				for(int j=0;j<k;j++)c[j]=cells[i][j];
				cells[i]=c;
			}
		}
		CellGrid grid=new CellGrid(cells,m,n);
		for(int i=0;i<m;i++)
		{
			for(int j=0;j<n;j++)
			{
				grid.alignments[1][i][j]=
					rows[i].getVerticalAlignment(j);	
				grid.alignments[0][i][j]=
					rows[i].getHorizontalAlignment(j);	
			}
		}
		return grid;
	}
/**
	<p>
	Creates a <code>CellGrid</code> from a two dimensional array of 
	components.
	Not every element of the input array need contain a component. 
	If an element is <code>null</code>, a gap is automatically created.
	</p>

	<p>
	If an element <code>cellArray[i][j]</code> is set to  
	<code>CellGrid.HSPANComponent</code> the component 
        <code>cellArray[i][j-1]</code> is spanned across the row to this
	element.  
	</p>

	<p>
        In this fashion a component may be placed to
	extend to more than one element along a row.
	</p>
		
	<p>
	If an element <code>cellArray[i][j]</code> is set to  
	<code>CellGrid.VSPANComponent</code> the component 
        <code>cellArray[i-1][j]</code> is spanned across the column to this
	element.  
	</p>

	<p>
        In this fashion a component may be placed to
	extend to more than one element along a column.
	</p>

	

	@param cellArray the two-dimensional array of components.
	@return The <code>CellGrid</code> object constucted from the input array.
**/
	public  static CellGrid createCellGrid(Component[][] cellArray)
	{
		int n=cellArray.length;
		Cell[][] cells=new Cell[n][];
		for(int i=0;i<n;i++)
		{
			int m=cellArray[i].length;
			cells[i]=new Cell[m];
			for( int j=0;j<m;j++)
			{
				Component c=cellArray[i][j];
				Cell cell=null;
				if(c==null)cell=null;
				else if(c==HSPANComponent)cell=HSPANCELL;
				else if(c==VSPANComponent)cell=VSPANCELL;
				else cell=new ComponentCell(c);
				cells[i][j]=cell;
			}
		}
		return createCellGrid(cells);
		
	}


/**
	<p>
	Creates a <code>CellGrid</code> from a two dimensional array of cells.
	Not every element of the input array need contain a cell. 
	If an element is <code>null</code>, a gap is automatically created.
	</p>

	<p>
	If an element <code>cellArray[i][j]</code> is set to  
	<code>CellGrid.HSPANCELL</code> the cell 
        <code>cellArray[i][j-1]</code> is spanned across the row to this
	element.  
	</p>

	<p>
        In this fashion a cell may be placed to
	extend to more than one element along a row.
	</p>
		
	<p>
	If an element <code>cellArray[i][j]</code> is set to  
	<code>CellGrid.VSPANCELL</code> the cell 
        <code>cellArray[i-1][j]</code> is spanned across the column to this
	element.  
	</p>

	<p>
        In this fashion a cell may be placed to
	extend to more than one element along a column.
	</p>

	@param cellArray the two-dimensional array of cells.
	@return The <code>CellGrid</code> object constucted from the input array.
**/
	public  static CellGrid createCellGrid(Cell[][] cellArray)
	{
		Cell[][] e=cellArray;
		int nr=e.length;
		int nc=0;
		int numRows=0;
		for(int i=0;i<nr;i++)
		{
			if(e[i]==null)continue;
			numRows++;	
			int m=e[i].length;
			if(nc<m)nc=m;
		}
		Cell[][] p=new Cell[numRows][nc];
		int k=0;
		for(int i=0;i<nr;i++)
		{
			if(e[i]==null)continue;
			int m=e[i].length;
			for(int j=0;j<m;j++)
			{
				p[k][j]=e[i][j];
			}
			k++;
		
		}
		nr=numRows;
		return new CellGrid(p,nr,nc);
	}
/**
	Creates a <code>CellGrid</code> from a one dimensional array of cells.
	The row and column indices of the elements of each of the 
	cells within the grid are specified  separately. Thus, for example,  
	if <code>rowIndices[2]</code> and <code>colIndices[2]</code> are
	both zero, the the cell object <code>cellArray[2]</code> is placed
	in the top left corner of the grid. This method is convenient
	if a large number of elements of the grid do not contain any cells. 

	The element of the grid whose indices are not referenced by any 
	elements of <code>rowIndices</code> and <code>colIndices</code> 
	arrays are filled with gaps.

	@param cellArray the one-dimensional array of cells.
	@param rowIndices an array of the same of same size as the first argument containing the row indices.
	@param colIndices an array of the same of same size as the first argument containing the column indices.
	@return The <code>CellGrid</code> object constucted from the input array.
**/
	public  static CellGrid createCellGrid(Cell[] cellArray,
				int[] rowIndices, int colIndices[])
	{
		Cell[] e=cellArray;
		int ne=e.length;
		int nri=rowIndices.length;
		int nci=colIndices.length;
		int nr=0;
		int nc=0;
		ne=min(min(ne,nri),nci);
		for(int i=0;i<ne;i++)
		{
			if(nr<rowIndices[i])nr=rowIndices[i];
			if(nc<colIndices[i])nc=colIndices[i];
		}
		nr++;
		nc++;
		Cell p[][]=new Cell[nr][nc];
		for(int i=0;i<ne;i++)
			p[rowIndices[i]][colIndices[i]]=e[i];
		return new CellGrid(p,nr,nc);	
	}
/**
	Computes the two-dimensional spring associated with the grid 
	that results from  the approriate combination of the springs 
	of the child cells.

	@return The computed two-dimensional spring.
**/
	public BoundSpring computeBoundSpring()
	{
		propagateAlignments();
		BoundSpring rh[]=rowSprings;
		if((rh==null)||(rh.length!=nr))
		{
			rh=new BoundSpring[nr];
			rowSprings=rh;
		}
		BoundSpring cw[]=colSprings;
		if((cw==null)||(cw.length!=nc))
		{
			cw=new BoundSpring[nc];
			colSprings=cw;
		}
		Cell[][] p=elements;
		for(int i=0;i<nr;i++)
		{
			int k=0;
			BoundSpring rb=null;
			for(int j=0;j<nc;j++)
			{
				if(!draw[i][j])continue;
				if(vspan[i][j]>1)continue;
			        BoundSpring bs=p[i][j].getBoundSpring();
				if(k==0)rb=
				 new BoundSpring(bs);
				else
				 BoundSpring.addx(rb,bs);
				k++;
			}
			if(k>0)rh[i]=rb;
			else rh[i]=new BoundSpring((BoundSpring)null);
		}
		for(int i=0;i<nr;i++)
		{
			for(int j=0;j<nc;j++)
			{
				if(!draw[i][j])continue;
				if(vspan[i][j]<=1)continue;
			        BoundSpring bs=p[i][j].getBoundSpring();
				modifySpannedSize(bs,vspan[i][j],i,rh,1);
			}
		}
		for(int j=0;j<nc;j++)
		{
			int k=0;
			BoundSpring cb=null;
			for(int i=0;i<nr;i++)
			{
				if(p[i][j]==null)continue;
				if(hspan[i][j]>1)continue;
			        BoundSpring bs=p[i][j].getBoundSpring();
				if(k==0)cb=
				 new BoundSpring(bs);
				else
				 BoundSpring.addy(cb,bs);
				k++;
			}
			if(k>0)cw[j]=cb;
			else cw[j]=new BoundSpring((BoundSpring)null);
		}
		for(int i=0;i<nr;i++)
		{
			for(int j=0;j<nc;j++)
			{
				if(!draw[i][j])continue;
				if(hspan[i][j]<=1)continue;
			        BoundSpring bs=p[i][j].getBoundSpring();
				modifySpannedSize(bs,hspan[i][j],j,cw,0);
			}
		}
		BoundSpring vert=new BoundSpring(rh[0]);
		for(int i=1;i<nr;i++)
			BoundSpring.addy(vert,rh[i]);
		for(int i=0;i<nr;i++)
			if(fixedRowHeight[i])rh[i].setFixedHeight();
		BoundSpring horiz=new BoundSpring(cw[0]);
		for(int i=1;i<nc;i++)BoundSpring.addx(horiz,cw[i]);
		for(int i=0;i<nc;i++)
			if(fixedColumnWidth[i])cw[i].setFixedWidth();
		BoundSpring b= BoundSpring.outerProduct(horiz,vert);
		return b;
	}
/**
	Fixes the widths of the specified columns so they do not change
	even if the enclosing cell size changes. Otherwise this property
	is determined by the properties of the child cells.

	@param columns the array of indices of columns whose width should be fixed. 
**/
	public void setFixedColumnWidth(int[] columns)
	{
		int n=(columns==null?0:columns.length);
		for(int i=0;i<n;i++)if((columns[i]>=0)&&(columns[i]<nc))
					fixedColumnWidth[columns[i]]=true;
		invalidate();
	}
/**
	Fixes the heights of the specified rows so they do not change
	even if the enclosing cell size changes. Otherwise this property
	is determined by the properties of the child cells.

	@param rows the array of indices of rows whose height should be fixed. 
**/
	public void setFixedRowHeight(int[] rows)
	{
		int n=(rows==null?0:rows.length);
		for(int i=0;i<n;i++)if((rows[i]>=0)&&(rows[i]<nr))
					fixedRowHeight[rows[i]]=true;
		invalidate();
	}
/**
	Returns the number of cells in the grid.
	@return The number of (empty as well non-empty) cells in the grid.

**/
	public int numberOfChildren()
	{
		return nr*nc;
	}
/**
	Returns the number of rows in the grid.
	@return The number of rows in the grid.
**/
	public int numberOfRows()
	{
		return nr;
	}
/**
	Returns the number of columns in the grid.
	@return The number of  columns in the grid.
**/
	public int numberOfColumns()
	{
		return nc;
	}
/**
	Returns the cell at the given row and column indices.
	@param rowIndex the rowIndex of the cell to be retrieved.
	@param columnIndex the columnIndex of the cell to be retrieved.
	@return The cell at the given row and column indices.
**/
	public Cell getCell(int rowIndex, int columnIndex)
	{
		if((rowIndex<0)||(rowIndex>=nr)||
		   (columnIndex<0)||(columnIndex>=nc)) return null;
		return elements[rowIndex][columnIndex];
	}
/**
	Return the child cell of the grid that is associated 
	with the given (linear) index. 
	For this purpose the cells are indexed from row to row, i.e., the index 
	<code>0</code> represents the cell on the top left corner, the index
	<code>1</code> represents the cell on the second column of the top row, etc., with the index wrapping around to the second row at the end the top row.

	@param index the linear index of the cell to be returned.
	@return The cell associated with the given index.

**/
	public Cell getChildAt(int index)
	{
		return elements[index/nc][index%nc];
	}
/**
	Replace the cell at the specified index with a new cell.

	@param index the linear index of the cell to be replaced.
	@param newCell the cell that replaces the current cell at the specified index.
	@param parent the container in which the components of the cells are placed.
	@return <code>true</code> if the replacement was successful, otherwise <code>false</code>.

**/
	public boolean replaceChild(int index, Cell newCell, Container parent)
	{
		if(index<0)return false;
		int row=index/nc;
		int col=index%nc;
		if((row>=nr)||(col>=nc))return false;
		Cell child=elements[row][col];
		if(child!=null)child.removeAllComponents(parent);
		if(newCell!=null)
		{
			elements[row][col]=newCell;
			if(parent!=null)
			newCell.addComponentsToContainer(parent);
		}
		else elements[row][col]=NULLCELL;
		update();
		return true;
	}
	public int getIndex(int rowIndex, int columnIndex)
	{
		return rowIndex*nc+columnIndex;
	}
	private void modifySpannedSize(BoundSpring bs,int nspan,
			int beg, BoundSpring[] e,int coordinate)
	{
		BoundSpring span=new BoundSpring(e[beg]);
		if(nspan<=1)return;
		for(int i=1;i<nspan;i++)
			if(coordinate==0)span.addx(span,e[beg+i]);
			else	span.addy(span,e[beg+i]);
		for(int type=0;type<3;type++)
		{
			int hs=span.getSize(type,coordinate);
			int es=bs.getSize(type,coordinate);
			if(es>hs)
			{
		  	  if(hs==0)
			  {
				int nf=0;
				for(int i=0;i<nspan;i++)
					if(!e[beg+i].isFixed(coordinate))nf++;
				if(nf==0)
				{
				   e[beg+nspan-1].setSize(es,type,coordinate);
				}
				else
				{
					int d=(int)round(es/(nspan-nf));
					for(int i=0;i<nspan;i++)
					  if(!e[beg+i].isFixed(coordinate))
					  e[beg+i].setSize(d,type,coordinate);
				}
			  }
			  else
			  {
				int nf=0;
				for(int i=0;i<nspan;i++)
					if(!e[beg+i].isFixed(coordinate))nf++;
				if(nf==0)
				{
			           int v=e[beg+nspan-1].getSize(
					type,coordinate);
				   e[beg+nspan-1].setSize(v+es-hs,
					type,coordinate);
				   continue;
				}
				int fs=0;
				for(int i=0;i<nspan;i++)
				{
				  if(!e[beg+i].isFixed(coordinate))
					fs+=e[beg+i].getSize(type,coordinate);
				}
				double f=(double)(es-hs)/(double)fs;
				for(int i=0;i<nspan;i++)
				{
				       if(e[beg+i].isFixed(coordinate))continue;
					  
                                        int v=e[beg+i].getSize(type,coordinate);
					int d=(int)round(
					  f*v+v);
					e[beg+i].setSize(d,type,coordinate);
				}
			   }
			}
		}
	}
/**
	Sets the width of the margins (empty space ) at the beginning or
	end of the specified rows.

	@param side Cell.LEFT for specifying left margin, Cell.RIGHT for the
		right margin.

	@param value  the width of the margin.

	@param rows the list of indices  of the rows whose margins
		are to be set.

**/
	public void setRowMargins(int side,int value,int... rows)
	{
		Cell gap=new Gap(value,true);
		for(int row:rows)
		{
			if((row<0)||(row>=nr))continue;
			Row newCell=new Row();
			
			if(side==Cell.LEFT)
			{
			  if(draw[row][0])
			  elements[row][0]=
				newCell.add(gap).add(elements[row][0]);
			}
			else 
			{
			  if(draw[row][nc-1])
			    elements[row][nc-1]
				=newCell.add(elements[row][nc-1]).add(gap);
			  else
			  {
				int i=nc-2;
				int k=2;
				for(;i>=0;i--,k++)
					if(draw[row][i])break;
				if(i<0)continue;
				if(hspan[row][i]==k)
			  	   elements[row][i]
			  	   =newCell.add(elements[row][i]).add(gap);
				
			  }
			}
		}
		invalidate();
	}
/**
	Sets the height of the margins or empty space at the top or
	bottom of the specified columns.

	@param side Cell.TOP for specifying top margin, Cell.BOTTOM for the
		bottom margin.

	@param value  the height of the margin.

	@param columns the list of indices  of the columns whose margins
		are to be set.

**/
	public void setColumnMargins(int side,int value,int... columns)
	{
		Cell gap=new Gap(value);
		for(int column:columns)
		{
			if((column<0)||(column>=nc))continue;
			Column newCell=new Column();
			
			if(side==Cell.TOP)
			{
			  if(draw[0][column])
			  elements[0][column]=
				newCell.add(gap).add(elements[0][column]);
			}
			else 
			{
                          if(draw[nr-1][column])
			     elements[nr-1][column]
				=newCell.add(elements[nr-1][column]).add(gap);
			  else
			  {
				int i=nr-2;
				int k=2;
				for(;i>=0;i--,k++)
					if(draw[i][column])break;
				if(i<0)continue;
				if(vspan[i][column]==k)
			  	   elements[i][column]
			  	   =newCell.add(elements[i][column]).add(gap);
			  }	
			}
		}
		invalidate();
	}
	protected static CellGrid createCellGridFromCells(Cell... cells)
	{
		int n=cells.length;
		int m=0;
		int k=0;
		Cell cell;
		for(int i=0;i<n;i++)
		{
			cell=cells[i];
			if(cell==Cell.NEWROW)
			{
				if(k>0)
					m++;	
				k=0;
			}	
			else k++;
		}
		if(k>0)m++;
		Cell[][] grid=new Cell[m][];
		m=k=0;	
		Cell[] row;
		int beg=0;
		for(int i=0;i<n;i++)
		{
			cell=cells[i];
			if(cell==Cell.NEWROW)
			{
				if(k>0)
				{
					row=new Cell[k];
					for(int j=beg;j<i;j++)
						row[j-beg]=cells[j];
					grid[m]=row;
					m++;
				}
				k=0;
				beg=i+1;	
			}	
			else k++;
		}
		if(k>0)
		{
			row=new Cell[k];
			for(int j=beg;j<n;j++)
				row[j-beg]=cells[j];
			grid[m]=row;
		}
		return createCellGrid(grid);
	}
	protected static CellGrid createCellGridFromObjects(Object... o)
	{
		int n=o.length;
		for(Object obj:o)
		{
			if(!isCellOrComponent(obj))
				throw new IllegalArgumentException(
				"The inputs to createCellGrid(Object ...)"+
				" should all be cells or components."+
				" Found "+obj.getClass());
		}
		Cell[] cells;
		cells=new Cell[n];
		int k=0;
		for(Object obj:o)
		{
			if(isCell(obj))
			{
				if(obj==Cell.SKIP)cells[k]=null;
				else cells[k]=(Cell)obj;
			}
			else cells[k]=new ComponentCell((Component)obj);
			k++;
		}
		return createCellGridFromCells(cells);
	}
	public int[] getColWidths(){return colWidths;}
	public int[] getRowHeights(){return rowHeights;}
	public Cell duplicate(ComponentDuplicator c)
	{
		Cell cells[][]=new Cell[nr][nc];
		for(int i=0;i<nr;i++)
			for(int j=0;j<nc;j++)
			{
				Cell cell=elements[i][j];
				if(cell==NULLCELL)cell=null;
				else if((cell!=HSPANCELL)&&(cell!=VSPANCELL))
					cell=cell.duplicate(c);
				cells[i][j]=cell;
			}
		CellGrid grid=new CellGrid(cells,nr,nc);
		int a[][][] = grid.alignments;
		for(int i=0;i<nr;i++)
			for(int j=0;j<nc;j++)
			{
				a[0][i][j]=alignments[0][i][j];
				a[1][i][j]=alignments[1][i][j];
			}
		return grid;
	}
	public int getAlignment(int i, int j, int coord, int defaultValue)
	{
		if((i<0)||(i>=nr)||(j<0)||(j>=nc))return defaultValue;
		coord=(coord!=0?1:0);
		return alignments[coord][i][j];
	}
	public void xmlserialize(XMLPrintStream out, ComponentXMLSerializer c)
	{
		String cname="Grid";
		String element="Cell";
		out.beginElement(element);
		out.addAttribute("TypeName",cname);
		out.addAttribute("name",getName());
		out.addAttribute("rows",nr);
		out.addAttribute("columns",nc);
		out.beginChildrenList(element);
		int n=numberOfChildren();
		for(int i=0;i<nr;i++)
		{
			for(int j=0;j<nc;j++)
			{
				Cell cell=elements[i][j];
				String type= (cell==NULLCELL?"null":
					    (cell==HSPANCELL?"hspan":
					     (cell==VSPANCELL?"vspan":"draw")
					    )
                                           );
                                             
				String elem="GridElement";
				out.beginElement(elem);
				out.addAttribute("type",type);
				if(type.equals("draw"))
				{
				     out.addAttribute("xalignment",
						alignments[0][i][j]);
				     out.addAttribute("yalignment",
						alignments[1][i][j]);
				     out.beginChildrenList(elem);
				     cell.xmlserialize(out,c);
				     out.endChildrenList(elem);
				}
				out.endElement(elem);
			}
		}
		out.endChildrenList(element);
		out.endElement(element);
	}
	public int[][][] getAlignments(){ return alignments;}
}
