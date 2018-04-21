/*
	------------------------------------------------
	PageLayout.java
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
import java.io.*;
import static java.lang.Math.*;
/**
	<p>
	PageLayout is a layout manager which can be used by placing the 
	components in rows, columns, or grids of cells
	with specified horizontal and vertical alignment
	(akin to text on a page of a document, and hence the appellation).
	
	At its simplest, a cell, which is just a rectangle,
	may contain a single component or a gap. 
	However, a cell may recursively contain other cells 
	which are themselves rows, columns or grids of cells containing
	components or gaps.
	As a result, components may be arranged in relatively
	complex layouts with comparatively little programming effort. 
	</p>
	
	<p>
	In principle, it is possible to duplicate the functionality 
	of {@link #PageLayout PageLayout}
	by using objects of the class <code>javax.swing.JPanel</code> 
	together with one of the many other existing layout
	managers, but only at the cost of substantially increased complexity. 
	Conversely, the functionality of the various layout managers that
	are a part of the standard Java distribution can be very simply
	duplicated by using just a single layout manager, 
	the <code> PageLayout</code>.
	</p>


	<p>
	Here is an example of the use of PageLayout for managing the layout of
	the dialog box shown below.
	</p>

	<br>
	<br>
	<center>
	<img src="doc-files/example2.gif" width="410" height="139"/>
	</center>
	<br>
	<br>
	<p>
	<pre>
		<b>// Main frame and its container</b>
		JFrame frame=new JFrame();
		Container container=frame.getContentPane();

		<b>// Create the components</b>
		JLabel findWhat=new JLabel("Find What:");
		JTextField textInput=new JTextField();
		JButton find=new JButton("Find");
		JCheckBox matchCase=new JCheckBox("Match Case");
		JCheckBox wrapAround= new JCheckBox("Wrap Around");
		JButton cancel=new JButton("Cancel");
		JCheckBox wholeWords=new JCheckBox("Whole Words");
		JCheckBox searchBackwards=new JCheckBox("Search Backwards");

		<b>// First column</b>
		Column col1=new Column(findWhat);

		<b>// Second column</b>
		Column col2=new Column(
			new Row(textInput),
			new Row(matchCase,wrapAround),
			new Row(wholeWords,searchBackwards)
			);

		<b>// Third column</b>
		Column col3=new Column(find,cancel);

		<b>// Create page from columns</b>
		Row row=new Row(col1,col2,col3);
		<b>// Add size constraints</b>
		row.linkHeight(find,new Component[]{textInput},
			new double[]{1});
		row.linkWidth(cancel,new Component[]{find},
			new double[]{1});
		row.linkWidth(wholeWords,new Component[]{matchCase},
			new double[]{1});

		<b>// Construct the layout</b>
		PageLayout pageLayout=row.createLayout(container);

		frame.pack();
		frame.setSize(frame.getPreferredSize());
		frame.setResizable(false);
		frame.show();

	</pre>
	</p>

	<p>
	Note that the constructor for <code>PageLout</code> 
	is not called directly, but by calling the 
	{@link pagelayout.Cell#createLayout createLayout} method of the class
	{@link pagelayout.Cell}. Finally the methods
	{@link pagelayout.Cell#linkHeight linkHeight} and
	{@link pagelayout.Cell#linkHeight linkHeight} are used 
	to impose some width and height constraints.
	</p>

	@version  1.16  05/10/2008

@see Cell  
@see Column  
@see Row  
@see CellGrid  
**/

public class PageLayout implements LayoutManager, LayoutManager2
{
	private Container mainContainer;
	private Vector<ContainerSizeLink> links;
	private int vgap, hgap;
	private static final int VGAP=5, HGAP=5;
	private Cell page;
	/**
		The constructor for the Layout Manager. 
		Note that the {@link pagelayout.Cell#createLayout createLayout} method 
		of the top level cell should be called to instantiate 
		an object of this class.

		@param parent  the container whose layout is to be
		 managed by the PageLayout object.
		@param topLevelCell  the top-level cell which may be any of the
		concrete sub-classes of the abstract class Cell. 
	**/
	protected PageLayout (Container parent,Cell topLevelCell )
	{
		mainContainer=parent;
		vgap=VGAP;
		hgap=HGAP;
		topLevelCell.addComponentsToContainer(parent);
		this.page=topLevelCell;  
		topLevelCell.invalidate();
		setDimensions(parent);
	}
	public void setDimensions(Container parent)
	{
		parent.setPreferredSize(preferredLayoutSize(parent));
		parent.setMinimumSize(minimumLayoutSize(parent));
                //parent.setSize(preferredLayoutSize(parent));
	}
	/**
		Sets the so called container gaps so that the components
		are laid out, if possible, in an area that leaves out 
		empty rectangular areas of specified size
		 on the edges of the container.
		
		@param horizontalGap the desired width of the empty strips inside the
		vertical edges of the container.

		@param verticalGap the desired height of the empty strips inside the
		horizontal edges of the container.
	**/
	public void setContainerGaps(int horizontalGap, int verticalGap)
	{
		hgap=horizontalGap;
		vgap=verticalGap;
		mainContainer.setPreferredSize(
			preferredLayoutSize(mainContainer));
                mainContainer.setMinimumSize(
			minimumLayoutSize(mainContainer));
		mainContainer.invalidate();	
	}
/**
	Returns the top level cell, which is the cell whose
	{@link pagelayout.Cell#createLayout createLayout} method was used to
	create this layout manager.

	@return The top level cell as defined above.
**/
	public Cell getTopLevelCell()
	{
		return page;
	}
/**
	This method of the LayoutManager interface is not implemented. 
	To replace an existing component, use the 
	{@link pagelayout.Cell#replaceCell  replaceCell} method of  the 
	class {@link pagelayout.Cell Cell}, which can also be used to
	add a new component to an existing 
	{@link pagelayout.Row Row} or 
	{@link pagelayout.Column Column} object.
**/
	public void addLayoutComponent(String name, Component component)
	{
	}	
/**
	This method of the LayoutManager2 interface is not implemented. 
	To replace an existing component, use the 
	{@link pagelayout.Cell#replaceCell  replaceCell} method of  the 
	class {@link pagelayout.Cell Cell}, which can also be used to
	add a new component to an existing 
	{@link pagelayout.Row Row} or 
	{@link pagelayout.Column Column} object.
**/
	public void addLayoutComponent(Component component, Object constraint)
	{
	}
/**
	This method of the LayoutManager interface is called by the
	container whenever it needs to layout the components within it.
	The <code>PageLayout</code> calls the
	{@link pagelayout.Cell#layout  layout} method of the top level cell
	to actually perform the layout.

	@param parent the container within which the components need to be
	laid out.
**/
	public void layoutContainer(Container parent)
	{
		if(parent==null)return;
	        //page.invalidate();
		Dimension d=parent.getSize();
		int w=d.width;
		int h=d.height;
		Insets inset=parent.getInsets();
		w-=(2*hgap+inset.left+inset.right);
		h-=(2*vgap+inset.top+inset.bottom);
		satisfyContainerLinks(w,h);
		int x=hgap+inset.left;
		int y=vgap+inset.right;
		page.layout(hgap+inset.left,vgap+inset.top,w,h);
		
	}
	/**
		Returns the minimum layout size of the container. 

		@param parent the container whose minimum size is required.

		@return The minimum layout size.
	**/
	public Dimension minimumLayoutSize(Container parent)
	{
		BoundSpring spring=page.getBoundSpring();
		Insets inset=parent.getInsets();
		return new Dimension(spring.getMinimumWidth()+2*hgap+
				inset.left+inset.right,
					spring.getMinimumHeight()+2*vgap+
				inset.top+inset.bottom);
	}
	/**
		Returns the maximum layout size of the container. 

		@param parent the container whose maximum size is required.

		@return The maximum layout size.
	**/
	public Dimension maximumLayoutSize(Container parent)
	{
		BoundSpring spring=page.getBoundSpring();
		Insets inset=parent.getInsets();
		return new Dimension(spring.getMaximumWidth()+2*hgap+
				inset.left+inset.right,
					spring.getMaximumHeight()+2*vgap+
				inset.top+inset.bottom);
	}
	/**
		Returns the preferred layout size of the container. 

		@param parent the container whose preferred size is required.

		@return The preferred layout size.
	**/
	public Dimension preferredLayoutSize(Container parent)
	{
	        page.invalidate();
		BoundSpring spring=page.getBoundSpring();
		Insets inset=parent.getInsets();
		return new Dimension(spring.getPreferredWidth()+2*hgap
				+inset.left+inset.right,
				spring.getPreferredHeight()+2*vgap
				+inset.top+inset.bottom);
		
	}
/**
	This method of the LayoutManager is not implemented. To remove
	a component, use the 
	{@link pagelayout.Cell#removeComponent  removeComponent} method
	of the top level cell, which can be retrieved by  using the 
	{@link pagelayout.PageLayout#getTopLevelCell  getTopLevelCell} 
	method of this class.
**/
	public void removeLayoutComponent(Component component)
	{
	
	} 
	/**
		Returns the 0.f, which specifies that the component
	would like to be aligned along the x-axis  at the origin.
	**/
	public float getLayoutAlignmentX(Container target)
	{
		return 0.f;
	}
	/**
		Returns the 0.f, which specifies that the component
	would like to be aligned along the y-axis  at the origin.
	**/

	public float getLayoutAlignmentY(Container target)
	{
		return 0.f;
	}
	/**
		Invalidates the layout.
	**/
	public void invalidateLayout(Container target)
	{
		getTopLevelCell().invalidate();	
	}
	private void satisfyContainerLinks(int w,int h)
	{
		if(links==null)return;
		int n=links.size();
		for(int i=0;i<n;i++)
			links.elementAt(i).setSize(w,h);
		if(n>0)
		{
			page.invalidate();
			BoundSpring b=page.getBoundSpring();
		}
	}
/**
	This method can be used to impose constraints on the widths
	of the components within the container as  linear functions 
	 of the width of the container itself. 
	In particular, in terms of the current width 
	<code>W</code> of the container, the 
	the width of the component <code>components[i]</code> is reset to be
	<code>W*affineConstants[i][0]+affineConstants[i][1]</code>
	everytime the container is resized. Thus, for example, 
	with <code>affineConstants[i][0]=0.5</code> and 
	<code>affineConstants[i][1]=-10</code>, the width of the component
	is restricted to be ten pixels less than half the width of the
	container.

	@param components the array of components whose widths are to be linked
		with the width of the container.

	@param affineConstants the array of constraint parameters as
		defined above.
	
**/
	public void linkToContainerWidth(Component[] components, 
		double[][] affineConstants)
	{
		linkToContainerDimension(components,affineConstants,true);
	}
/**
	This method can be used to impose constraints on the heights
	of the components within the container as  linear functions 
	 of the height of the container itself. 
	In particular, in terms of the current height 
	<code>H</code> of the container, the 
	the height of the component <code>components[i]</code> is reset to be
	<code>H*affineConstants[i][0]+affineConstants[i][1]</code>
	everytime the container is resized. Thus, for example, 
	with <code>affineConstants[i][0]=0.5</code> and 
	<code>affineConstants[i][1]=-10</code>,   the height of the component
	is restricted to be ten pixels less than half the height of the
	container.

	@param components the array of components whose heights are to be linked
		with the height of the container.

	@param affineConstants the array of constraint parameters as
		defined above.
	
**/
	public void linkToContainerHeight(Component[] components, 
			double[][] affineConstants)
	{
		linkToContainerDimension(components,affineConstants,false);
	}
	private void linkToContainerDimension(Component[] components, 
		double[][] fraction, boolean isX)
	{
		ContainerSizeLink link=createLink(components,fraction,isX);
		if(link==null)return;
		if(links==null)links=new Vector<ContainerSizeLink>();
		links.add(link);
	}
	public void setLinks(Vector<ContainerSizeLink> v)
	{
		links=v;
	}
	public static ContainerSizeLink createLink(
		Component[] components, double[][] fraction, boolean isX)
	{
		int n=fraction.length;
		if(n!=components.length)
		{
			return null;
		}
		for(int i=0;i<n;i++)
		{
			if(fraction[i]==null)return null;
			if(fraction[i].length<2)
			{
				double[] d=new double[2];
				d[0]=fraction[i][0];
				d[1]=0;
				fraction[i]=d;
			}	
		}
		ContainerSizeLink cwl=null;
		if(isX)
			cwl=new ContainerWidthLink(components,fraction);
		else
			cwl=new ContainerHeightLink(components,fraction);
		return cwl;
	}
	public abstract static class ContainerSizeLink
	{
		protected Component[] components;
		protected double fraction[][];
		public abstract void setSize(int w,int h);
		public ContainerSizeLink(Component[] components,
				double[][] fraction)
		{
			this.components=components;
			this.fraction=fraction;
		}
	}
	public static class ContainerWidthLink extends ContainerSizeLink
	{
		public ContainerWidthLink(Component[] components,
				double[][] affineConstants)
		{
			super(components,affineConstants);
		}
		public void setSize(int w, int h)
		{
			int n=components.length;
				
			for(n--;n>=0;n--)
			{

				int cw=(int)round(
					w*fraction[n][0]+fraction[n][1]);
				if(cw>=w)continue;
				for(int type=0;type<3;type++)
				{
					Dimension d=Cell.getSize(components[n],type);
					Cell.setSize(components[n],
					 new Dimension(cw,
						d.height),type);
				}
			}
		}
	}
	public static class ContainerHeightLink extends ContainerSizeLink
	{
		public ContainerHeightLink(Component[] components,
				double[][] affineConstants)
		{
			super(components,affineConstants);
		}
		public void setSize(int w, int h)
		{
			int n=components.length;
				
			for(n--;n>=0;n--)
			{
				int ch=(int)round(
					h*fraction[n][0]+fraction[n][1]);
				if(ch>=h)continue;
				
				for(int type=0;type<3;type++)
				{
					Dimension d=Cell.getSize(components[n],type);
					Cell.setSize(components[n],
					 new Dimension(d.width,ch),type);
				}
			}
		}
	}
	public Insets getInset()
	{
		Insets b=new Insets(0,0,0,0);
		Insets inset=mainContainer.getInsets();
		b.left=hgap+inset.left;
		b.right=hgap+inset.right;
		b.top=vgap+inset.top;
		b.bottom=vgap+inset.bottom;
		b.bottom=vgap+inset.bottom;
		return b;
	}
}
