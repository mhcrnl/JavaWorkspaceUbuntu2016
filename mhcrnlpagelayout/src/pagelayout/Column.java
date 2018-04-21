/*
	------------------------------------------------
	Column.java
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
import static java.lang.Math.*;
/**
	A <code>Column</code> is a collection of {@link pagelayout.Cell Cell} objects
	in which the elements are
	arranged sequentially in the vertical direction from top to bottom. 
	Both horizontal and vertical alignment of the elements can be specified. Components and {@link pagelayout.Cell Cell} objects (including {@link pagelayout.Gap Gap} objects which are also cells) that form the rows of a column can be specified in one of the constructors of this class, or may be added to an already constructed column by using the various {@link pagelayout.CellContainer#add add} methods of the class {@link pagelayout.CellContainer CellContainer} which is the superclass of <code>Column</code>.  Note that by using the 
{@link pagelayout.CellContainer#add(int align, Component... components) add}
or
{@link pagelayout.CellContainer#add(int align, Cell... cellarray) add}
method of the  superclass 
{@link pagelayout.CellContainer CellContainer}, the various rows of the
column may be assigned horizontal alignments which differ from the alignment
specified in the constructor.

	@version  1.16  05/10/2008
**/

public class Column extends CellContainer
{
	private BoundSpring rowSprings[];
/**
		Creates a column with the specified alignments.
		
		@param halignment Horizontal alignment specification. May have one of the following four values:       {@link pagelayout.Cell#LEFT Cell.LEFT}, {@link pagelayout.Cell#CENTER Cell.CENTER}, {@link pagelayout.Cell#RIGHT Cell.RIGHT}, {@link pagelayout.Cell#NO_ALIGNMENT Cell.NO_ALIGNMENT}.

		@param valignment Vertical alignment specification. May have one of the following four values:       {@link pagelayout.Cell#TOP Cell.TOP}, {@link pagelayout.Cell#CENTER Cell.CENTER}, {@link pagelayout.Cell#BOTTOM Cell.BOTTOM}, {@link pagelayout.Cell#NO_ALIGNMENT Cell.NO_ALIGNMENT}.
**/
	public Column (int halignment,int valignment)
	{
		super(halignment,valignment,false);
	}
/**
	Creates a column with no alignment.
**/
	public Column()
	{
		super(Cell.NO_ALIGNMENT,Cell.NO_ALIGNMENT,false);
	}
/**
	Creates a column with no alignment. The input cells are arranged sequentially from top to bottom.
	
	@param cellrows The variable argument list of cells, one for each row.
**/
	public Column(Cell... cellrows)
	{
		super(Cell.NO_ALIGNMENT,Cell.NO_ALIGNMENT,false);
		for(Cell cellrow:cellrows)
			cells.add(cellrow);
	}
/**
	Creates a column with the specified alignments. The input cells are arranged sequentially from top to bottom.
	
	@param halign Horizontal alignment specification.
	@param valign Vertical alignment specification.
	@param cellrows The variable argument list of cells, one for each row.
**/
       public Column(int halign,int valign,Cell... cellrows)
	{
		super(halign,valign,false);
		for(Cell cellrow:cellrows)
			cells.add(cellrow);
	} 
/**
	Creates a column with no alignment. The input components are arranged sequentially from top to bottom.
	
	@param components The variable argument list of components, one for each row.
**/
	public Column(Component... components)
	{
		super(Cell.NO_ALIGNMENT,Cell.NO_ALIGNMENT,false);
		for(Component component:components)add(component);
	}
/**
	Creates a column with the specified alignments. The input components are arranged sequentially from top to bottom.
	
	@param halign Horizontal alignment specification.
	@param valign Vertical alignment specification.
	@param components The variable argument list of components, one for each row.
**/
	public Column(int halign,int valign,Component... components)
	{
		super(halign,valign,false);
		for(Component component:components)add(component);
	}
/**
	Creates a column with the specified alignments. Each of the input component arrays is used to construct a row. The rows are arranged sequentially from top to bottom.
	
	@param halign Horizontal alignment specification.
	@param valign Vertical alignment specification.
	@param rows The variable argument list of component arrays, one for each row.
**/
	public Column(int halign,int valign,Component[]... rows)
	{
		super(halign,valign,false);
		if(rows.length==1)
		{
			for (Component[] crow:rows)
			{
				int n=crow.length;
				for(int i=0;i<n;i++)
					add(crow[i]);
			}		
		}	
		else
		for(Component[] row:rows)
			add(new Row(row));
		
	}
/**
	Creates a column with no alignment. Each of the input component arrays is used to construct a row. The rows are arranged sequentially from top to bottom.
	
	@param rows The variable argument list of component arrays, one for each row.
**/
	public Column(Component[]... rows)
	{
		super(Cell.NO_ALIGNMENT,Cell.NO_ALIGNMENT,false);
		if(rows.length==1)
		{
			for (Component[] crow:rows)
			{
				int n=crow.length;
				for(int i=0;i<n;i++)
					add(crow[i]);
			}		
		}	
		for(Component[] row:rows)
			add(new Row(row));
	}
/**
	Specifies that this {@link pagelayout.CellContainer CellContainer} is a column.

	@return false.
**/
	protected boolean isHorizontal()
	{
		return false;
	}
/**
	@return The string represntation of the column.
**/
	public String toString()
	{
		int n=cells.size();
		
		String s=getClass().getName()+ " ";
		boolean vjust=false;
		if(valignment==Cell.BOTTOM)
			s+=("\n"+CellVector.VGAP);
		else if(valignment==Cell.CENTER)
			s+=("\n"+CellVector.VGAP);
		else vjust=(valignment==Cell.JUSTIFIED);
		for(int i=0;i<n;i++)
		{
			Object o=cells.elementAt(i);
			if(vjust&&(i>0)&&
			(!o.getClass().equals(Gap.class)))
				s+=("\n"+CellVector.VGAP);
			s+=("\n"+o.toString());
		}
		if(valignment==Cell.CENTER)
			s+=("\n"+CellVector.VGAP);
		return s;	
	}
/**
	Arranges the rows of the column within the specified rectangle of the container.

	@param x the x-coordinate of the top left of the rectangle.
	@param y the y-coordinate of the top left of the rectangle.
	@param width the width of the rectangle.
	@param height the height of the rectangle.
**/
	protected void setBounds(int x, int y, int width, int height)
	{
		int w=width;
		int h=height;
		int n=cells.size();
		filledSizeX=filledSizeY=0;
		int y0=y;
		if(n==0)return;
		int rowHeight[]=getRowHeights(x,y,w,h);
		int wi=w;
		int xi=x;
		int dw=0;
		int[] constrainedWidth=new int[n];
		for(int i=0;i<n;i++)constrainedWidth[i]=-1;
		Constraint constraint=getConstraint();
		filledSizeY=y;
		if(constraint!=null)
		{
			constraint.setSize(constrainedWidth,n,w);	
		}
		for(int i=0;i<n;i++)
		{
			wi=w;
			int cwi=constrainedWidth[i];
			xi=x;
			Cell c=cells.elementAt(i);
			if(c==null)continue;
			//int halign=getParallelAlignment(i);
			//halign=(halign<0?halignment:halign);
			int halign=halignment;
			BoundSpring bs=c.getBoundSpring();
			if(bs==null)continue;
			if(bs.isXFixed()||(cwi>0))
			{
				if(cwi>0)wi=cwi;
				else
				wi=bs.getPreferredWidth();
				if(wi<w)
				{
					switch(halign)
					{
					    case Cell.RIGHT:xi+=(w-wi);break;
					    case Cell.CENTER:
						xi+=((w-wi)/2); 
					}
				}
				else wi=w;
			}	
			Cell cell=cells.elementAt(i);
			cell.setBounds(xi,y,wi,rowHeight[i]);	
			y+=rowHeight[i];
			if(!CellVector.isFiller(cell))
				filledSizeY=y;
		}
		filledSizeY-=y0;
	}
	private int[] getRowHeights(int x,int y,int w,int h)
	{
		int n=cells.size();
		if(n==0)return null;
		if((rowSprings==null)||(rowSprings.length<n))
			invalidate();
		BoundSpring p=getBoundSpring();
		return getRowHeights(x,y,w,h,p,rowSprings,n);
	}
/**
	Computes the heights of the individual rows contained in a column bounded by the specified rectangle. This method is used by this class and {@link pagelayout.CellGrid CellGrid} to layout the rows of a column when the method {@link pagelayout.Column#setBounds setBounds} of the two classes is called.

	@param x the x-coordinate of the top left of the rectangle.
	@param y the y-coordinate of the top left of the rectangle.
	@param width the width of the rectangle.
	@param height the height of the rectangle.
	@param boundSpring the two-dimensional spring associated with the column.
	@param rowSprings the array of two-dimensional springs associated with the  rows of the column.
	@param n the number of rows in the column.

	@return The array of heights of the individual rows to fit in the specified rectangle.
**/
	protected static int[] getRowHeights(int x,int y,int width,int height,
			BoundSpring boundSpring, BoundSpring[] rowSprings,int n)
	{
		int w=width;
		int h=height;
		BoundSpring p=boundSpring;
		int[] heightArray=new int[n]; 
		//int ph=p.getPreferredHeight();
		int ph=p.getMinimumHeight();
		if(h>ph)
		{
			float available=0;
			int last=-1;
			int mingap=0;
			int lastr=-1;;
			int numGap=0;
			int numComp=0;
			int comp=0;
			for(int i=0;i<n;i++)
			{
				int rh=rowSprings[i].getPreferredHeight();
				if(rowSprings[i].isYFixed())
				{
					heightArray[i]=rh;
					h-=rh;
				}
				else 
				{
					available+=rh;
					last=i;
					if(rowSprings[i].isGap())
					{
					  mingap+=
					   rowSprings[i].getMinimumHeight();
					  numGap++;
					}
					else
					{
					  lastr=i;
					  numComp++;
					  comp+=rh;
				        }
				} 
			}
			if(last>=0)
			{
			   int used=0;
			   if((numGap==0)||(mingap>h)||(numComp==0))
			   {
			     float ratio=h/available;
			     int minh=0;
			     int toolow=0;
			     available=0;
			     for(int i=0;i<last;i++)
			     {
				if(rowSprings[i].isYFixed())continue;
				int phe=rowSprings[i].getPreferredHeight();
				int rh=(int)round(phe*ratio);
				heightArray[i]=rh;
				int mh=rowSprings[i].getMinimumHeight();
				if(rh<mh)
				{
					toolow++;
					minh+=mh;
				}	
				else available+=phe;
				used+=rh;
			     }
			     heightArray[last]=(int)round(h-used);
			     if(heightArray[last]<
				 rowSprings[last].getMinimumHeight())
			     {
					toolow++;
					minh+=
				        rowSprings[last].getMinimumHeight();
			     }
			     else available+=
				 rowSprings[last].getPreferredHeight();
			     if((toolow>0)&&(minh<h))
			     {
				
			        ratio=(h-minh)/available;
				used=0;
			        for(int i=0;i<last;i++)
			        {
				  if(rowSprings[i].isYFixed())continue;
				  int rh=rowSprings[i].getPreferredHeight();
				  rh=(int)round(rh*ratio);
				  heightArray[i]=rh;
				  int mh=rowSprings[i].getMinimumHeight();
				  if(rh<mh)
					rh=heightArray[i]=mh;
				  used+=rh;
			        }
			        heightArray[last]=(int)round(h-used);
			     }
			   }
			   else
			   {
	 	              float hc=h-mingap;
			      float ratio=hc/comp;
			      int minh=0;
			      comp=0;
	                      int rh=0;
                              for(int i=0;i<=last;i++)
			      {
				  if(rowSprings[i].isYFixed())continue;
                                  if(rowSprings[i].isGap())
				  {
				  	heightArray[i]=rowSprings[i].
							getMinimumHeight();
					continue;
				  }
				  int phe=rowSprings[i].getPreferredHeight
							();
				  if(i<lastr)
				  {
					rh=(int)round(phe*ratio);
					heightArray[i]=rh;
					used+=rh;	
				  }
				  else
			      		rh=heightArray[i]=(int)round(hc-used);
				  int mh=rowSprings[i].getMinimumHeight();
				  if(rh<mh)minh+=mh;
				  else comp+=phe;

                              }
			      if((minh>0)&&(minh<hc))
			      {
			      	ratio=(hc-minh)/comp;
                                used=0;
			        for(int i=0;i<=last;i++)
			        {
				  if(rowSprings[i].isYFixed())continue;
                                  if(rowSprings[i].isGap())continue;
				  int mh=rowSprings[i].getMinimumHeight();
				  if(mh>heightArray[i])
					rh=mh;
				  else
				  {
				        int phe=rowSprings[i].getPreferredHeight();
					rh=(int)round(phe*ratio);
				  }
				  if(i<lastr)
				  {
					heightArray[i]=rh;
					used+=rh;	
				  }
				  else
			      		rh=heightArray[i]=(int)round(hc-used);

                                 }
			      }
			   
			   }
			}
		}
		else for(int i=0;i<n;i++)
			heightArray[i]=rowSprings[i].getMinimumHeight();
			//heightArray[i]=rowSprings[i].getPreferredHeight();
		return heightArray;
	}
/**
	Computes the two-dimensional spring associated with the column that results from stacking vertically the two-dimensional springs associated with each row of the column.

	@return The computed two-dimensional spring.
**/
	
	public BoundSpring computeBoundSpring()
	{
		int n=cells.size();
		if(n==0)return new BoundSpring((BoundSpring)null);
		cells.removeLastGapIfNeeded();
		BoundSpring[] bs=rowSprings=new BoundSpring[n];
		BoundSpring b=bs[0]=cells.elementAt(0).getBoundSpring();
		b=new BoundSpring(b);
		if(n>1)
		{
			b=b.addy(bs[1]=cells.elementAt(1).getBoundSpring());	
			
		}
		for(int i=2;i<n;i++)
		{
			b=BoundSpring.addy(b,
				bs[i]=cells.elementAt(i).getBoundSpring());
		}
		return b;
	    
	}
/**
	Returns the <i>baseline</i> of the topmost cell in this column.

	@return The baseline for the topmost cell in this column, 
	if defined, and -1 if not.

@see ComponentCell#getBaseline
@see Row#getBaseline
@see Cell#getBaseline
**/
	public int getBaseline()
	{
		int n=cells.size();
		if(n==0)return -1;
		return cells.elementAt(0).getBaseline();
	}
/**
	Creates a row of components with the specified alignments. The input components are arranged sequentially from left to right. The newly created row is added to the column
and returned as the output of this method.
	
	@param halign Horizontal alignment specification.
	@param valign Vertical alignment specification.
	@param components The variable argument list of cells.

	@return The newly created row.
**/
	public Row newRow(int halign, int valign, Component... components)
	{
		Row r=new Row(halign,valign,components);
		add(r);
		return r;
	}
/**
	Creates a row of cells with the specified alignments. The input cells are arranged sequentially from left to right. The newly created row is added to the column
and returned as the output of this method.
	
	@param halign Horizontal alignment specification.
	@param valign Vertical alignment specification.
	@param cells The variable argument list of cells.

	@return The newly created row.
**/
	public Row newRow(int halign, int valign, Cell... cells)
	{
		Row r=new Row(halign,valign,cells);
		add(r);
		return r;
	}
/**
	Creates a row of components with default alignments. The input components are arranged sequentially from left to right. The newly created row is added to the column
and returned as the output of this method.
	
	@param components The variable argument list of cells.

	@return The newly created row.
**/
	public Row newRow( Component... components)
	{
		Row r=new Row(components);
		add(r);
		return r;
	}
/**
	Creates a row of cells with default alignments. The input cells are arranged sequentially from left to right. The newly created row is added to the column
and returned as the output of this method.
	
	@param cells The variable argument list of cells.

	@return The newly created row.
**/
	public Row newRow(Cell... cells)
	{
		Row r=new Row(cells);
		add(r);
		return r;
	}
/**
	Creates an empty row with the specified alignments. The newly created row is added to the column
and returned as the output of this method.
	
	@param halign Horizontal alignment specification.
	@param valign Vertical alignment specification.

	@return The newly created row.
**/
	public Row newRow(int halign, int valign)
	{
		Row r=new Row(halign,valign);
		add(r);
		return r;
	}
/**
	Creates an empty row with the default alignments. The newly created row is added to the column
and returned as the output of this method.
	

	@return The newly created row.
**/
	public Row newRow()
	{
		Row r=new Row();
		add(r);
		return r;
	}
	public Cell duplicate(ComponentDuplicator c)
	{
		Column col=new Column(halignment,valignment);
		int n=numberOfChildren();
		for(int i=0;i<n;i++)
		{
			Cell cell=getChildAt(i);
			if(CellVector.isFiller(cell))continue;
			col.add(cell.duplicate(c));
		}
		col.dupConstraint(this);
		return col;
	}
}
