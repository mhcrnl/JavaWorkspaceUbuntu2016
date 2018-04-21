/*
	------------------------------------------------
	Gap.java
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
/**
	A gap is a {@link pagelayout.Cell Cell} object that represents an empty
	space between components. Both flexible and fixed gaps may be placed 
	between the components. Typically, this class is not used directly, but
	by calling the appropriate {@link pagelayout.CellContainer#add add}
	methods of a {@link pagelayout.Row Row} or a 
	{@link pagelayout.Column Column} object.

	@version  1.16  05/10/2008
**/
public class Gap extends Cell
{
	private int min, pref, max;
	private boolean isFixed;
	private boolean isX;
/**
	Creates a fixed gap with the specified orientation.

	@param gap the length of the gap.
	@param isHorizontal <code> true</code> for horizontal gap, and <code>false</code> otherwise.
**/
	public Gap(int gap,boolean isHorizontal)
	{
		isFixed=true;
		pref=gap;
		this.isX=isHorizontal;
	}
/**
	Creates a fixed vertical gap. 

	@param gap the length of the gap.
**/
	public Gap(int gap)
	{
		isFixed=true;
		pref=gap;
		this.isX=false;
	}
/**
	Creates a flexible gap with the specified orientation. The flexibility
	of the gap is indicated by the difference between 
	<code>preferredGap</code> and <code>maxGap</code>. 
	If these arguments are identical, the gap is fixed. 

	@param minGap the minimum length of the gap.
	@param preferredGap the preferred length of the gap.
	@param maxGap the maximum length of the gap.
	@param isHorizontal <code> true</code> for horizontal gap, and <code>false</code> otherwise.
**/
	public Gap(int minGap,int preferredGap,int maxGap,boolean isHorizontal)
	{
		isFixed=false;
		this.min=minGap;
		this.max=maxGap;
		this.pref=preferredGap;
		this.isX=isHorizontal;
	}
/**
	Creates a flexible vertical gap. The flexibility
	of the gap is indicated by the difference between 
	<code>preferredGap</code> and <code>maxGap</code>. 
	If these arguments are identical, the gap is fixed. 

	@param minGap the minimum length of the gap.
	@param preferredGap the preferred length of the gap.
	@param maxGap the maximum length of the gap.
**/
	public Gap(int minGap,int preferredGap,int maxGap)
	{
		isFixed=false;
		this.min=minGap;
		this.max=maxGap;
		this.pref=preferredGap;
		this.isX=false;
	}
/**
	Returns the string representation of the gap.
	
	@return The string representation of the gap.

**/
	public String toString()
	{
		return "[GAP] "+
		(isFixed?("[ "+pref+"]"):
		("[ "+min+", "+pref+", "+max+"]"));
	}
/**
	Returns the two-dimensional spring associated with this gap.

	@return The two-dimensional spring associated with this gap.
**/
	public BoundSpring computeBoundSpring()
	{
		BoundSpring bs= (isFixed? new BoundSpring(pref,pref,pref,isX):
				new BoundSpring(min,pref,max,isX));
		return bs;
	}
	public Cell duplicate(ComponentDuplicator c)
	{
		return new Gap(min,pref,max,isX);
	}
	public void xmlserialize(XMLPrintStream out, ComponentXMLSerializer c)
	{
		String element="Cell";
		String cname=(isX?"HGap":"VGap");
		out.beginElement(element);
		out.addAttribute("TypeName",cname);
		out.addAttribute("min",min);
		out.addAttribute("pref",pref);
		out.addAttribute("max",max);
		out.endElement(element);
	}
}
