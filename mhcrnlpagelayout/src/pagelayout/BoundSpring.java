/*
	------------------------------------------------
	BoundSpring.java
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
	The <code>BoundSpring</code> class 
	encapsulates the geometrical 
	properties of an object of the class 
	{@link pagelayout.Cell Cell} in terms of 
	<i>minimum</i>, <i>maximum</i>, and <i>preferred</i> width and
	height of the rectangle that encloses the cell.
	</p>
	
	<p>
	Also defined here are methods 
	{@link pagelayout.BoundSpring#addx addx},
	{@link pagelayout.BoundSpring#addy addy}, and
	{@link pagelayout.BoundSpring#outerProduct outerProduct} that are 
	used to determine the <code>BoundSpring</code> of cells 
	that may contain other cells, such as rows
	( {@link pagelayout.Row Row}), columns
	( {@link pagelayout.Column Column}), and rectangular grids
	( {@link pagelayout.CellGrid CellGrid}). 
	</p>
	
	@version  1.16  05/10/2008
**/
public class BoundSpring
{
	private int wMin, wPref, wMax;
	private int hMin, hPref, hMax;
	private boolean isGap;
/**
	Creates the <code>BoundSpring</code>  for a component.

	@param component the component for which the <code>BoundSpring</code>  is to be created.
**/		
	public BoundSpring(Component component)
	{
		Dimension d=component.getMinimumSize();
		wMin=min(d.width,Cell.MAX);
		hMin=min(d.height,Cell.MAX);
		d=component.getPreferredSize();
		wPref=min(d.width,Cell.MAX);
		hPref=min(d.height,Cell.MAX);
		d=component.getMaximumSize();
		wMax=min(d.width,Cell.MAX);
		hMax=min(d.height,Cell.MAX);
		isGap=false;
	}
/**
	Creates the <code>BoundSpring</code>  for a horizontal or a vertical gap. 

	@param minGap the minimum length of the gap.
	@param preferredGap the preferred length of the gap.
	@param maxGap the maximum length of the gap.
	@param isHorizontalGap <code>true</code> for a horizontal gap, 
		<code>false</code> otherwise.
**/
	
	public BoundSpring(int minGap,int preferredGap, int maxGap, boolean isHorizontalGap)
	{
		wMin=wMax=wPref=0;
		hMin=hMax=hPref=0;
		isGap=true;
		if(isHorizontalGap)
		{
			wMin=minGap;
			wMax=maxGap;
			wPref=preferredGap;
		}
		else
		{
			hMin=minGap;
			hMax=maxGap;
			hPref=preferredGap;
		}
		checkBounds();
	}
/**
	Creates a <code>BoundSpring</code>  which is an exact copy of the given <code>BoundSpring</code>.

	@param boundSpring the <code>BoundSpring</code>  object to be duplicated.

**/
	public BoundSpring(BoundSpring boundSpring)
	{
		if(boundSpring==null)
		{
			wMin=wMax=wPref=hMin=hMax=hPref=0;
			isGap=true;
			return;
		}
		wMin=boundSpring.wMin;
		wMax=boundSpring.wMax;
		wPref=boundSpring.wPref;
		hMin=boundSpring.hMin;
		hMax=boundSpring.hMax;
		hPref=boundSpring.hPref;
		isGap=boundSpring.isGap;
		checkBounds();
	}
/**
	Computes the <code>BoundSpring</code>  resulting from horizontal stacking of this
	object and another <code>BoundSpring</code>  object.

	@param boundSpring the second <code>BoundSpring</code>  object.

	@return The <code>BoundSpring</code>  object resulting from the horizontal stacking.

**/
	
	public BoundSpring addx(BoundSpring boundSpring)
	{
		BoundSpring c=new BoundSpring(this);
		return addx(c,boundSpring);
	}
/**
	Computes the <code>BoundSpring</code>  resulting from 
        horizontal stacking of two <code>BoundSpring</code> objects.

	@param aBoundSpring the first <code>BoundSpring</code>  object. 
	Note  that the result is copied to this object which is returned 
	as the output of this method. 

	@param bBoundSpring the second <code>BoundSpring</code>  object.

	@return The <code>BoundSpring</code>  object resulting from the horizontal stacking.
**/
	public static BoundSpring addx(BoundSpring aBoundSpring, BoundSpring bBoundSpring)
	{
		aBoundSpring.wMin+=bBoundSpring.wMin;
		aBoundSpring.wMax+=bBoundSpring.wMax;
		aBoundSpring.wPref+=bBoundSpring.wPref;
		   aBoundSpring.hMin=max(aBoundSpring.hMin,bBoundSpring.hMin);
		   aBoundSpring.hMax=max(aBoundSpring.hMax,bBoundSpring.hMax);
		   aBoundSpring.hPref=max(aBoundSpring.hPref,
				bBoundSpring.hPref);
	
		aBoundSpring.isGap=aBoundSpring.isGap&&bBoundSpring.isGap;
		aBoundSpring.checkWBounds();
		return aBoundSpring;
	}
/**
	Computes the <code>BoundSpring</code>  resulting from vertical stacking of this
	object and another <code>BoundSpring</code>  object.

	@param bBoundSpring the second <code>BoundSpring</code>  object.

	@return The <code>BoundSpring</code>  object resulting from the vertical stacking.

**/
	public BoundSpring addy(BoundSpring bBoundSpring)
	{
		BoundSpring aBoundSpring=new BoundSpring(this);
		return addy(aBoundSpring,bBoundSpring);
	}
/**
	Computes the <code>BoundSpring</code>  resulting from vertical 
	stacking of two <code>BoundSpring</code>  objects.

	@param aBoundSpring the first <code>BoundSpring</code>  object. 
	Note  that the result is copied to this object which is returned 
	as the output of this method.  

	@param bBoundSpring the second <code>BoundSpring</code>  object.

	@return The <code>BoundSpring</code>  object resulting from the 
	vertical stacking.
**/
	public static BoundSpring addy(BoundSpring aBoundSpring, BoundSpring bBoundSpring)
	{
		aBoundSpring.hMin+=bBoundSpring.hMin;
		aBoundSpring.hMax+=bBoundSpring.hMax;
		aBoundSpring.hPref+=bBoundSpring.hPref;
		   aBoundSpring.wMin=max(aBoundSpring.wMin,bBoundSpring.wMin);
		   aBoundSpring.wMax=max(aBoundSpring.wMax,bBoundSpring.wMax);
		   aBoundSpring.wPref=max(aBoundSpring.wPref,
					bBoundSpring.wPref);
		aBoundSpring.isGap=aBoundSpring.isGap&&bBoundSpring.isGap;
		aBoundSpring.checkHBounds();
		return aBoundSpring;
	}
	private void checkBounds()
	{
		checkWBounds();
		checkHBounds();
	}
	private void checkWBounds()
	{
		wMin=min(wMin,Cell.MAX);
		wMax=min(wMax,Cell.MAX);
		wPref=min(wPref,Cell.MAX);
		
	}
	private void checkHBounds()
	{
		hMin=min(hMin,Cell.MAX);
		hMax=min(hMax,Cell.MAX);
		hPref=min(hPref,Cell.MAX);
	}
/**
	Returns the minimum width of this <code>BoundSpring</code>  object.
	@return The minimum width of this <code>BoundSpring</code>  object.
**/
	public int getMinimumWidth()
	{
		return wMin;
	}
/**
	Returns the minimum height of this <code>BoundSpring</code>  object.
	@return The minimum height of this <code>BoundSpring</code>  object.
**/
	public int getMinimumHeight()
	{
		return hMin;
	}
/**
	Returns the preferred width of this <code>BoundSpring</code>  object.
	@return The preferred width of this <code>BoundSpring</code>  object.
**/
	public int getPreferredWidth()
	{
		return wPref;
	}
/**
	Returns the preferred height of this <code>BoundSpring</code>  object.
	@return The preferred height of this <code>BoundSpring</code>  object.
**/
	public int getPreferredHeight()
	{
		return hPref;
	}
/**
	Returns the maximum width of this <code>BoundSpring</code>  object.
	@return The maximum width of this <code>BoundSpring</code>  object.
**/
	public int getMaximumWidth()
	{
		return wMax;
	}
/**
	Returns the maximum height of this <code>BoundSpring</code>  object.
	@return The maximum height of this <code>BoundSpring</code>  object.
**/
	public int getMaximumHeight()
	{
		return hMax;
	}
/**
	Returns <code>true</code> if this object is rigid in the x-direction.

	@return <code>true</code> if this object is rigid in the x-direction,<code>false</code> otherwise.
**/
	public boolean isXFixed()
	{
		return (wMax==wPref);
	}
/**
	Returns <code>true</code> if this object is rigid in the y-direction.

	@return <code>true</code> if this object is rigid in the y-direction,<code>false</code> otherwise.
**/
	public boolean isYFixed()
	{
		return (hMax==hPref);
	}
/**
	Returns <code>true</code> if this object represents the enclosing
	rectangle of a gap.

	@return <code>true</code> of this object is a in the y-direction,<code>false</code> otherwise.
**/
	public boolean isGap()
	{
		return isGap;	
	}
/**
	Returns the string representation of this object.
	@return The string representation of this object.
**/
	public String toString()
	{
		String s="min["+wMin+", "+hMin+" ] max["+wMax+", "+hMax+"]";
		return s+" pref["+wPref+", "+hPref+" ]";
	}
/**
	Sets the height of the components in the input component array
	to the height of this <code>BoundSpring</code>. Only the elements
	in the array starting from the index <code>fromOffset</code>
	are affected in this manner.
	
	@param componentArray the array  of components to resize.
	@param fromOffset the index of the first element in the array to modify.         

**/
	public void linkHeight(Component[] componentArray, int fromOffset)
	{
		Component[] c=componentArray;	
		int i=fromOffset;
		int n=c.length;
		for(;i<n;i++)
		{
			Dimension d=c[i].getMinimumSize();
			c[i].setMinimumSize(new Dimension(d.width,hMin));
			d=c[i].getMaximumSize();
			c[i].setMaximumSize(new Dimension(d.width,hMax));
			d=c[i].getPreferredSize();
			c[i].setPreferredSize(new Dimension(d.width,hPref));
		}
	}
/**
	Sets the width of the components in the input component array
	to the width of this <code>BoundSpring</code>. 
	Only the elements in the array starting from the index 
	<code>fromOffset</code> are affected in this manner.
	
	@param componentArray the array  of components to resize.
	@param fromOffset the index of the first element in the array to modify.         

**/
	public void linkWidth(Component[] componentArray, int fromOffset)
	{
		Component[] c=componentArray;	
		int i=fromOffset;
		int n=c.length;
		for(;i<n;i++)
		{
			Dimension d=c[i].getMinimumSize();
			c[i].setMinimumSize(new Dimension(wMin,d.height));
			d=c[i].getMaximumSize();
			c[i].setMaximumSize(new Dimension(wMax,d.height));
			d=c[i].getPreferredSize();
			c[i].setPreferredSize(new Dimension(wPref,d.height));
		}
	}
/**
	Returns the outer product of this object with another 
	<code>BoundSpring</code>
	object. The outer product of two 
	the <code>BoundSpring</code> objects is <code>BoundSpring</code> of the
	rectangle formed by the horizontal
	spring (i.e. minimum, maximum and preferred width)
	of the first object and the vertical spring 
	(i.e. minimum, maximum and preferred height) of the second.

	@param verticalSpring the  object whose vertical spring is copied to
		the vertical spring of the result.

	@return The <code>BoundSpring</code> object 
	resulting from the operation defined above.
**/
	public BoundSpring outerProduct(BoundSpring verticalSpring)
	{
		return outerProduct(new BoundSpring(this),verticalSpring);
	}
/**
	Returns the outer product of the two input <code>BoundSpring</code>
	objects.

	@param horizontalSpring the  object whose horizontal spring is copied to
		the horizontal spring of the result. The result is copied
		to this object which is returned as the output of this method.
	@param verticalSpring the  object whose vertical spring is copied to
		the vertical spring of the result.

	@return The outer product of the inputs.
**/
	public static BoundSpring outerProduct(
		BoundSpring horizontalSpring, BoundSpring verticalSpring)
	{
		horizontalSpring.hMin=verticalSpring.hMin;
		horizontalSpring.hMax=verticalSpring.hMax;
		horizontalSpring.hPref=verticalSpring.hPref;
		horizontalSpring.isGap=horizontalSpring.isGap&&
				       verticalSpring.isGap;
		return horizontalSpring;
	}
/**
	Increases the width and height of this object by the specified inputs.

	@param horizontalGap the specified increase in the width.
	@param verticalGap the specified increase in the height.
**/
	public void setInset(int horizontalGap,int verticalGap)
	{
		wMin+=horizontalGap;
		hMin+=verticalGap;
		wMax+=horizontalGap;
		hMax+=verticalGap;
		wPref+=horizontalGap;
		hPref+=verticalGap;
	}
	
/**
	Compact utility method for getting various dimensions of this 
	<code>BoundSpring</code>.

	@param type 0, 1, or any other number for getting the
	        minimum, maximum or preferred
	        size along the coordinate specified the
		selcond paramter.
	@param coord 0 for x axis (width), 1 for y axis (height)

	@return The size dependent upon the inputs as specified above.
**/
	protected int getSize(int type, int coord)
	{
		switch(type)
		{
			case 0: return (coord==0?wMin:hMin);
			case 1: return (coord==0?wMax:hMax);
			default: return (coord==0?wPref:hPref);
		}
	}
/**
	Compact utility method for setting various dimensions of this 
	<code>BoundSpring</code>.

	@param value the actual value.
	@param type 0, 1, or any other number for setting the
	        minimum, maximum or preferred
	        size along the coordinate specified the
		selcond paramter.
	@param coord 0 for x axis (width), 1 for y axis (height)
**/
	protected void setSize(int value,int type, int coord)
	{
		switch(type)
		{
			case 0: if(coord==0)wMin=value;else hMin=value;break;
			case 1: if(coord==0)wMax=value;else hMax=value;break;
			default:if(coord==0)wPref=value;else hPref=value;
		}
	}
	protected boolean isFixed(int c)
	{
		if(c==0)return isXFixed();
		else return isYFixed();
	}
	protected void setFixedWidth()
	{
		wMax=wPref;
	}
	protected void setFixedHeight()
	{
		hMax=hPref;
	}
	protected void encloseBoundSpring(BoundSpring bs)
	{
		wPref=max(bs.wPref,wPref);
		hPref=max(bs.hPref,hPref);
		wMin=max(bs.wMin,wMin);
		hMin=max(bs.hMin,hMin);
		wMax=max(bs.wMax,wMax);
		hMax=max(bs.hMax,hMax);
	}
	protected void fixMax()
	{
		if(wPref!=wMax)wMax=Math.min(2*wPref,wMax);
		if(hPref!=hMax)hMax=Math.min(2*hPref,hMax);
	}
}
