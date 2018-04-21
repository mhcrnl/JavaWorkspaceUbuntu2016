/*
	------------------------------------------------
	ComponentCell.java
	------------------------------------------------

	This software is distribiuted unde the 
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
/**
	<code>ComponentCell</code> is a class 
	that wraps an object of the type <code>java.awt.Component</code>   
	in a {@link pagelayout.Cell Cell} object. 

	@version  1.16  05/10/2008
**/
public class ComponentCell extends Cell
{
	private Component c;
	private int baselineOffset;
	protected int vgap, hgap;
/**
	The default height of the empty strip around horizontal edges of the
	component.
**/
	public static final int VGAP=3; 
/**
	The default width of the empty strip around vertical edges of the
	component.
**/
	public static final int HGAP=3;
	private static int TEXTFIELD_HEIGHT=-1;
/**
	Creates the <code>Cell</code> object for a component.

	@param component the component to be wrapped in this cell.
**/
	
	public ComponentCell(Component component)
	{
		this.c=component;
		if(component instanceof JTextField)setTFHeight();
		vgap=VGAP;
		hgap=HGAP;
		baselineOffset=0;
	}
/**
	Creates the <code>Cell</code> object for a component with specified gaps.

	@param component the component to be wrapped in this cell.
	@param horizontalGap the width of the empty strips to be added adjacent to the vertical edges of the component.
	@param verticalGap the height of the empty strips to be added adjacent to the horizontal edges of the component.
**/
	public ComponentCell(Component component, int horizontalGap, int verticalGap)
	{
		this.c=component;
		if(component instanceof JTextField)setTFHeight();
		this.vgap=verticalGap;
		this.hgap=horizontalGap;
	}
	public void setBaselineOffset(int b)
	{
		baselineOffset=b;
	}
/**
	Return the string representation of this cell.
	
	@return the string representation of this cell.
**/

	public String toString()
	{
		return "[Component] "+c.toString();
	}
/**
	Add the component wrapped in this cell to the given container.

	@param container the container to which the component must be added.

**/
	public void addComponentsToContainer(Container container)
	{	
		if(container==null)return;
		Container p=c.getParent();
		if(p!=null)
		{
			if(p==container)return;
			p.remove(c);
		}
		container.add(c);
	}
/**
	Resizes the enclosed component to occupy the specified rectangle of the container with appropriate empty strips around it.

	@param x the x-coordinate of the top left of the rectangle.
	@param y the y-coordinate of the top left of the rectangle.
	@param width the width of the rectangle.
	@param height the height of the rectangle.
**/
	protected void setBounds(int x, int y, int width, int height)
	{
		int w=width;
		int h=height;
		BoundSpring bs=getBoundSpring();
		if(bs.isXFixed())width=bs.getPreferredWidth();	
		if(bs.isYFixed())height=bs.getPreferredHeight();	
		c.setBounds(new Rectangle(x+hgap,y+vgap+baselineOffset,
				width=width-hgap*2,height=height-vgap*2-baselineOffset));
	}
/**
	Returns the two-dimensional spring associated with this cell.

	@return The two-dimensional spring associated with this cell.
**/
	public BoundSpring computeBoundSpring()
	{
		BoundSpring bs= new BoundSpring(c);
		bs.setInset(2*hgap,2*vgap+baselineOffset);
		return bs;
	}
/**
	Returns the component that this object wraps.

	@return The component enclosed within this cell.
**/
	public Component getComponent()
	{
		return c;
	}
	private void setTFHeight()
	{
		if(TEXTFIELD_HEIGHT<0)
		{
			JLabel label=new JLabel("TEST");
			Dimension d=label.getPreferredSize();
			TEXTFIELD_HEIGHT=(int)(d.height*1.5+1);
		}
		Dimension d=c.getPreferredSize();
		int h=d.height;
		c.setPreferredSize(new Dimension(d.width,h));
		d=c.getMinimumSize();
		c.setMinimumSize(new Dimension(d.width,h));
		d=c.getMaximumSize();
		c.setMaximumSize(new Dimension(d.width,h));
	}
/**
	Sets the size of the empty area around all enclosed components.
	
	@param hgap the width of the vertical strips around the vertical edges.
	@param vgap the height of the horizontal strips around the horizontal edges.
**/
	public void setComponentGaps(int hgap, int vgap)
	{
		this.hgap=hgap;
		this.vgap=vgap;
		invalidate();
	}
/**
	Returns the <i>baseline</i> of the component wrapped by this cell.
	The baseline is defined for components which display a single 
	line of text, and it is
	the distance between the baseline of the text and the upper edge
	of the component. 

	@return The baseline for the component, if defined, and -1 if not.

@see Cell#getBaseline
@see Row#getBaseline
@see Cell#getBaseline
**/
	public int getBaseline()
	{
		
		if(pagelayout.util.NamedSeparator.class.isInstance(c))
		{
		 return ((pagelayout.util.NamedSeparator)c).getBaseline();	
		}
		else if(JComponent.class.isInstance(c))
		{
			//Dimension d=c.getPreferredSize();
			//return ((JComponent)c).getBaseline(d.width,d.height);
			return org.jdesktop.layout.Baseline.getBaseline(
			(JComponent)c);
		}
		return -1;
	}
	public ComponentCell getComponentCell()
	{
		return this;
	}
	public Cell duplicate(ComponentDuplicator creator)
	{
		ComponentCell cell=new ComponentCell(
				creator.dupComponent(c));
		cell.baselineOffset=baselineOffset;
		cell.vgap=vgap;
		cell.hgap=hgap;
		return cell;
	}
	public void xmlserialize(XMLPrintStream out, ComponentXMLSerializer xc)
	{
		String element="Cell";
		String cname="ComponentCell";
		out.beginElement(element);
		out.addAttribute("TypeName",cname);
		out.addAttribute("xgap",hgap);
		out.addAttribute("ygap",vgap);
		out.addAttribute("baselineoffset",baselineOffset);
		out.beginChildrenList(element);
		xc.xmlserialize(out,c);
		out.endChildrenList(element);
		out.endElement(element);
	}
	
}
