/*
	------------------------------------------------
	Row.java
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
	A <code>Row</code> is a collection of {@link pagelayout.Cell Cell} objects
	in which the elements are
	arranged sequentially in the horizontal direction from left to right. 
	Both horizontal and vertical alignment of the elements can be specified. Components  and {@link pagelayout.Cell Cell} objects (including {@link pagelayout.Gap Gap} objects which are also cells) that form the columns of a row can be specified in one of the constructors of this class, or may be added to an already constructed row by using the various {@link pagelayout.CellContainer#add add} methods of the class {@link pagelayout.CellContainer CellContainer}, which is the superclass of <code>Row</code>. Note that by using the 
{@link pagelayout.CellContainer#add(int align, Component... components) add}
or
{@link pagelayout.CellContainer#add(int align, Cell... cellarray) add}
method of the  superclass 
{@link pagelayout.CellContainer CellContainer}, the various columns of the
row may be assigned vertical alignments which differ from the alignment
specified in the constructor.

	@version  1.16  05/10/2008
**/
public class Row extends CellContainer
{
	private int[] offsets;
	private BoundSpring columnSprings[];
/**
		Creates a row with the specified alignments.
		
		@param halignment Horizontal alignment specification. May have one of the following four values:       {@link pagelayout.Cell#LEFT Cell.LEFT}, {@link pagelayout.Cell#CENTER Cell.CENTER}, {@link pagelayout.Cell#RIGHT Cell.RIGHT}, {@link pagelayout.Cell#NO_ALIGNMENT Cell.NO_ALIGNMENT}.

		@param valignment Vertical alignment specification. May have one of the following four values:       {@link pagelayout.Cell#TOP Cell.TOP}, {@link pagelayout.Cell#CENTER Cell.CENTER}, {@link pagelayout.Cell#BOTTOM Cell.BOTTOM}, {@link pagelayout.Cell#NO_ALIGNMENT Cell.NO_ALIGNMENT}.
**/
	public Row(int halignment,int valignment)
	{
		super(halignment,valignment,true);
	}
/**
	Creates a row with no alignment.
**/
	public Row()
	{
		super(Cell.NO_ALIGNMENT,Cell.NO_ALIGNMENT,true);
	}
/**
	Creates a row with no alignment. The input cells are arranged sequentially from left to right.
	
	@param columnCells The variable argument list of cells, one for each columns.
**/
	public Row(Cell... columnCells)
	{
		super(Cell.NO_ALIGNMENT,Cell.NO_ALIGNMENT,true);
		if(columnCells!=null)	
		for(Cell columnCell:columnCells)
			cells.add(columnCell);
	}
/**
	Creates a row with the specified alignments. The input cells are arranged sequentially from left to right.
	
	@param halign Horizontal alignment specification.
	@param valign Vertical alignment specification.
	@param columnCells The variable argument list of cells, one for each column.
**/
       public Row(int halign,int valign,Cell... columnCells)
	{
		super(halign,valign,true);
		for(Cell columnCell:columnCells)
			cells.add(columnCell);
	} 
/**
	Creates a row with the specified alignments. The input components are arranged sequentially from left to right.
	
	@param halign Horizontal alignment specification.
	@param valign Vertical alignment specification.
	@param components The variable argument list of components, one for each column.
**/	
	
	public Row(int halign, int valign,Component... components)
	{
		super(halign,valign,true);
		for(Component comp:components)add(comp);
	}
/**
	Creates a row with the no alignments. The input components are arranged sequentially from left to right.
	
	@param components The variable argument list of components, one for each column.
**/
	public Row(Component... components)
	{
		super(Cell.NO_ALIGNMENT,Cell.NO_ALIGNMENT,true);
		for(Component comp:components)
		{
			add(comp);
		}
	}	
/**
	Creates a row with the no alignments. The input component arrays are are used to form columns which are arranged sequentially from left to right.
	
	@param components The variable argument list of component arrays, one for each column.
**/
	public Row(Component[]... components)
	{
		super(Cell.NO_ALIGNMENT,Cell.NO_ALIGNMENT,true);	
		if(components.length==1)
		{
			for (Component[] crow:components)
			{
				int n=crow.length;
				for(int i=0;i<n;i++)
					add(crow[i]);
			}		
		}
		else
		  for(Component[] crow:components)
			add(new Column(crow));	
	}
/**
	Creates a row with the specified alignments. The input component arrays are are used to form columns which are arranged sequentially from left to right.
	
	@param halign Horizontal alignment specification.
	@param valign Vertical alignment specification.
	@param components The variable argument list of component arrays, one for each column.
**/
	public Row(int halign, int valign,Component[]... components)
	{
		super(halign,valign,true);	
		if(components.length==1)
		{
			for (Component[] crow:components)
			{
				int n=crow.length;
				for(int i=0;i<n;i++)
					add(crow[i]);
			}		
		}	
		else
		for(Component[] crow:components)
			add(new Column(crow));	
	}	
/**
	Specifies that this {@link pagelayout.CellContainer CellContainer} is a row.

	@return true.
**/
	protected boolean isHorizontal()
	{
		return true;
	}
/**
	Arranges the columns of the row within the specified rectangle of the container.

	@param x the x-coordinate of the top left of the rectangle.
	@param y the y-coordinate of the top left of the rectangle.
	@param width the width of the rectangle.
	@param height the height of the rectangle.
**/
	protected void setBounds(int x, int y, int width, int height)
	{
		int w=width;
		int h=height;
		int columnWidth[]=getColumnWidths(x,y,w,h);
		int x0=x;	
		filledSizeX=filledSizeY=0;
		int n=cells.size();
		if(n==0)return;
		int yi=y;
		int hi=h;
		int dh=0;
		boolean offset=isBaseline();
		int[] constrainedHeight=new int[n];
		for(int i=0;i<n;i++)constrainedHeight[i]=-1;
		Constraint constraint=getConstraint();
		if(constraint!=null)constraint.setSize(constrainedHeight,n,h);
		filledSizeX=x;
		for(int i=0;i<n;i++)
		{
			yi=y;
			hi=h;
			int chi=constrainedHeight[i];
			Cell cell=cells.elementAt(i);
			if(cell==null)continue;
			BoundSpring bs=cell.getBoundSpring();
			if(bs==null)continue;
			//int valign=getParallelAlignment(i);
			//valign=(valign<0?valignment:valign);
			int valign=valignment;
			if(!offset&&bs.isYFixed()||(chi>0))
			{
				if(chi>0)
					hi=chi;
				else hi=bs.getPreferredHeight();
				yi=y;
				if(hi<h)
				{
					dh=h-hi;	
					switch(valign)
					{
						case Cell.BOTTOM:yi+=dh;break;
						case Cell.CENTER:yi+=(dh/2);
					}
				}
				else hi=h;
			}
			if(offset)
			{
				//System.out.printf("yi %d offsets[i] %d\n",
				//		yi,offsets[i]);
				//yi+=offsets[i];
			}
			cell.setBounds(x,yi,columnWidth[i],hi);	
			x+=columnWidth[i];
			if(!CellVector.isFiller(cell))
				filledSizeX=x;
			
		}
		filledSizeX-=x0;
		filledSizeY=0;
	}
	private int[] getColumnWidths(int x,int y,int w,int h)
	{
		int n=cells.size();
		if(n==0)return null;
		if((columnSprings==null)||(columnSprings.length<n))
			invalidate();
		BoundSpring p=getBoundSpring();
		return getColumnWidths(getName(),x,y,w,h,p,columnSprings,n);
	}
/**
	Computes the widths of the individual columns contained in a row bounded by the specified rectangle. This method is used by this class and {@link pagelayout.CellGrid CellGrid} to layout the colums within a row when the method {@link pagelayout.Column#setBounds setBounds} of the two classes is called.

	@param x the x-coordinate of the top left of the rectangle.
	@param y the y-coordinate of the top left of the rectangle.
	@param width the width of the rectangle.
	@param height the height of the rectangle.
	@param boundSpring the two-dimensional spring associated with the row.
	@param columnSprings the array of two-dimensional springs associated with the  columns of the row.
	@param n the number of columns in the row.

	@return The array of widths of the individual columns to fit in the specified rectangle.
**/
	protected static int[] getColumnWidths(int x,int y,int width,int height,
		BoundSpring boundSpring, BoundSpring columnSprings[], int n)
	{
		return getColumnWidths(null,x,y,width,height,boundSpring,
                           columnSprings,n);
        }
	protected static int[] getColumnWidths(
                String name,
                int x,int y,int width,int height,
		BoundSpring boundSpring, BoundSpring columnSprings[], int n)
	{
                if(name==null)name="[NULL]";
		BoundSpring p=boundSpring;
		BoundSpring csprings[]=columnSprings;
		int w=width;
		int h=height;
		int[] widthArray=new int[n]; 
		//int pw=p.getPreferredWidth();
		int pw=p.getMinimumWidth();
		if(w>pw)
		{
			float available=0;
			int last=-1;
			int mingap=0;
			int lastc=-1;
			int numGap=0;
			int numComp=0;
			int comp=0;
			for(int i=0;i<n;i++)
			{
				int cw=csprings[i].getPreferredWidth();
				if(csprings[i].isXFixed())
				{
					widthArray[i]=cw;
					w-=cw;
				}
				else 
				{
					available+=cw;
					last=i;
					if(csprings[i].isGap())
					{
					  mingap+=csprings[i].getMinimumWidth();
					  numGap++;
					}
					else
					{
					  lastc=i;
					  numComp++;
					  comp+=cw;
			
					}
				}
			}
			if(last>=0)
			{
			  int used=0;
		          int minw=0;
                          int mw=0;
                          int cw=0;
			  if((numGap==0)||(mingap>w)||(numComp==0))
		          {
			     float ratio=w/available;
			     available=0;
			     for(int i=0;i<=last;i++)
			     {
				if(csprings[i].isXFixed())continue;
				int pcw=csprings[i].getPreferredWidth();
				cw=(int)round(pcw*ratio);
				if(i<last)widthArray[i]=cw;
			        else cw=widthArray[last]=(int)round(w-used);
				used+=cw;
				mw =csprings[i].getMinimumWidth();
				if(mw>widthArray[i])minw+=mw;
				else available+=pcw;
			     }
			     if((minw>0)&&(minw<w))
			     { 
   				ratio=(w-minw)/available;
			        for(int i=0;i<=last;i++)
			        {
				   if(csprings[i].isXFixed())continue;
				   int pcw=csprings[i].getPreferredWidth();
				   mw=csprings[i].getMinimumWidth();
				   if(i<last)
				   {
					if(widthArray[i]<mw)
						cw=mw;
				   	 else cw=(int)round(pcw*ratio);
					widthArray[i]=cw;
					used+=cw;
				   }
			           else widthArray[last]=(int)round(w-used);
                               }
			     }
			     
			  }
			  else
			  {
			     float wc=w-mingap;
			     float ratio=wc/comp;
			     used=0;
			     cw=0;
			     minw=0;
			     for(int i=0;i<=last;i++)
			     {
				if(csprings[i].isXFixed())continue;
				if(csprings[i].isGap())
				{
					cw=csprings[i].getMinimumWidth();
					widthArray[i]=cw;
					continue;
				}
				int cwe=csprings[i].getPreferredWidth();
				mw=csprings[i].getMinimumWidth();
				if(i<lastc)
				{	
					cw=(int)round(cwe*ratio);
					widthArray[i]=cw;
					used+=cw;
				}
			        else
			          cw=widthArray[lastc]=(int)round(wc-used);
					if(cw<mw)minw+=cw;
					else comp+=cwe;
			     }
			     if((minw>0)&&(minw<wc))
			     {
			       ratio=(wc-minw)/comp;
			       used=0;
			       for(int i=0;i<=last;i++)
			       {
				if(csprings[i].isXFixed())continue;
				if(csprings[i].isGap())continue;
				mw=csprings[i].getMinimumWidth();
				int cwe=csprings[i].getPreferredWidth();
				if(i<last)
				{
			            if(widthArray[i]<mw)cw=mw;
				    else cw=(int)(ratio*cwe);
				    widthArray[i]=cw;
				    used+=cw;
			       }
			       else widthArray[i]=(int)Math.round(wc-used);
	                      }
			     }
	                    }
				
	                }
		}
		else for(int i=0;i<n;i++)
			widthArray[i]=csprings[i].getMinimumWidth();
			//widthArray[i]=csprings[i].getPreferredWidth();
		return widthArray;
	}
/**
	Computes the two-dimensional spring associated with the row that results from stacking horizontally the two-dimensional springs associated with each column of the row.

	@return The computed two-dimensional spring.
**/
	public BoundSpring computeBoundSpring()
	{
		updateBaselineAlignments();
		int n=cells.size();
		if(n==0)return new BoundSpring((BoundSpring)null);
		cells.removeLastGapIfNeeded();
		boolean offset=isBaseline();
		if(offset)getOffsets();
		BoundSpring[] bs=columnSprings=new BoundSpring[n];
		BoundSpring b=cells.elementAt(0).getBoundSpring();
		if(offset)
		b.setInset(0,offsets[0]);
		bs[0]=b;
		BoundSpring spring=new BoundSpring(b);
		if(n>1)
		{
			bs[1]=cells.elementAt(1).getBoundSpring();
			if(offset)
			bs[1].setInset(0,offsets[1]);
		  	BoundSpring.addx(spring,bs[1]);
		}
		for(int i=2;i<n;i++)
		{
			bs[i]= cells.elementAt(i).getBoundSpring();
			if(offset)
				bs[i].setInset(0,offsets[i]);
			spring.addx(spring,bs[i]);
		}
		if(offset)
		for(int i=0;i<n;i++)
			bs[i].setInset(0,-offsets[i]);
		return spring;
	}
        private void updateBaselineAlignments()
	{
		int n=numberOfChildren();
		if(n<=1)return;
		int m=0;
		for(int i=0;i<n;i++)
		{
			Cell cell=getChildAt(i);
			if(cell instanceof ComponentCell)m++;
		}
		if(m<=1)return;
		ComponentCell cells[]=new ComponentCell[m];
		int k=0;
		for(int i=0;(i<n)&&(m>k);i++)
		{
			Cell cell=getChildAt(i);
			if(cell instanceof ComponentCell)
			{
				cells[k]=(ComponentCell)cell;
				k++;	
			}
		}
		alignBaseline(cells,m);
	}
/**
	Returns the maximum of the baseline of all those cells in this
	row whose baseline is defined. If baseline of none of the
	cells in this row is defined, it returns -1.

	@return  The baseline for this row as defined above.

@see ComponentCell#getBaseline
@see Column#getBaseline
@see Cell#getBaseline
**/
	public int getBaseline()
	{
		int n=cells.size();
		int g=-1;
		for(int i=0;i<n;i++)
		{
			int b=cells.elementAt(i).getBaseline();
			if(b<0)continue;
			if(g<b)g=b;
		}
		return g;
	}
	private void getOffsets()
	{
		int b=getBaseline();
		int n=cells.size();
		if(n>0)
			offsets=new int[n];
		if(b<0)
		{
			for(int i=0;i<n;i++)offsets[i]=0;
			return;
		}
		for(int i=0;i<n;i++)
		{
			offsets[i]=0;
			int bi=cells.elementAt(i).getBaseline();
			if(bi>=0)offsets[i]=b-bi;
		}
	} 
	private boolean isBaseline()
	{
		return valignment==BASELINE;
	}
/**
	Creates a column of components with the specified alignments. The input components are arranged sequentially from top to bottom. The newly created column is added to the row
and returned as the output of this method.
	
	@param halign Horizontal alignment specification.
	@param valign Vertical alignment specification.
	@param components The variable argument list of cells.

	@return The newly created column.
**/
	public Column newColumn(int halign, int valign, Component... components)
	{
		Column c=new Column(halign,valign,components);
		add(c);
		return c;
	}
/**
	Creates a column of cells with the specified alignments. The input cells are arranged sequentially from top to bottom. The newly created column is added to the row
and returned as the output of this method.
	
	@param halign Horizontal alignment specification.
	@param valign Vertical alignment specification.
	@param cells The variable argument list of cells.

	@return The newly created column.
**/
	public Column newColumn(int halign, int valign, Cell... cells)
	{
		Column c=new Column(halign,valign,cells);
		add(c);
		return c;
	}
/**
	Creates a column of components with default alignments. The input components are arranged sequentially from top to bottom. The newly created column is added to the row
and returned as the output of this method.
	
	@param components The variable argument list of cells.

	@return The newly created column.
**/
	public Column newColumn( Component... components)
	{
		Column c=new Column(components);
		add(c);
		return c;
	}
/**
	Creates a column of cells with default alignments. The input cells are arranged sequentially from top to bottom. The newly created column is added to the row
and returned as the output of this method.
	
	@param cells The variable argument list of cells.

	@return The newly created column.
**/
	public Column newColumn(Cell... cells)
	{
		Column c=new Column(cells);
		add(c);
		return c;
		
	}

/**
	Creates an empty column  with default alignments. The newly created column is added to the row and returned as the output of this method.
	

	@return The newly created column.
**/
	public Column newColumn()
	{
		Column c=new Column();
		add(c);
		return c;
		
	}
/**
	Creates an empty column  with specified alignments. The newly created column is added to the row and returned as the output of this method.
	

	@return The newly created column.
**/
	public Column newColumn(int halign, int valign)
	{
		Column c=new Column(halign,valign);
		add(c);
		return c;
		
	}
	public Cell duplicate(ComponentDuplicator c)
	{
		Row row=new Row(halignment,valignment);
		int n=numberOfChildren();
		for(int i=0;i<n;i++)
		{
			Cell cell=getChildAt(i);
			if(CellVector.isFiller(cell))continue;
			row.add(cell.duplicate(c));
		}
		row.dupConstraint(this);
		return row;
	}	
	
}
