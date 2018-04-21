/*
	------------------------------------------------
	TabbedPaneCell.java
	------------------------------------------------

	This software is distributed unde the 
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
/**
	TabbedPaneCell encloses a JTabbedPane.

	@version  1.16  05/10/2008
**/

public class TabbedPaneCell extends Cell
{
	private Vector<Cell> cells;
	private Vector<Boolean> wrappedComponentFlag;
	private JTabbedPane pane;
	public TabbedPaneCell ()
	{
		this(new JTabbedPane());
	}
	public TabbedPaneCell (JTabbedPane container)
	{
		this.pane=container;
		cells=new Vector<Cell>();
		wrappedComponentFlag=new Vector<Boolean>();
	}
	public void add(String name, Component p)
	{
		pane.addTab(name,p);
	}

	public void add(String name, Cell p)
	{
		int n=wrappedComponentFlag.size();
		int m=pane.getTabCount();
		for(int i=n;i<(m-1);i++)
			wrappedComponentFlag.add(new Boolean(false));
		wrappedComponentFlag.add(new Boolean(true));
		cells.add(p);
		Container c=null;
		if(PanelCell.class.isInstance(p))
		{
			c=(Container)p.getComponent();
			PanelCell pc=(PanelCell)p;
			p=pc.getCell();
		}
		else c=new CellManagedPanel();
		pane.addTab(name,c);
		//p.fixMaxSize=true;
		p.createLayout(c);
		p.setParent(this);
	}
	public BoundSpring computeBoundSpring()
	{
		boundSpring=new BoundSpring(pane);
		return boundSpring;
	}
	public void show(String tab)
	{
		int i=pane.indexOfTab(tab);
		if(i>=0)pane.setSelectedIndex(i);
	}


	public void addComponentsToContainer(Container parent)
	{	
		if(parent!=pane)
		{
			if(pane.getParent()!=parent)
			parent.add(pane);
		}
	}
	protected void setBounds(int x, int y, int width, int height)
	{
		pane.setBounds(x,y,width,height);
	}
	public Container removeAllComponents(Container parent)
	{
		parent=pane.getParent();
		if(parent!=null) parent.remove(pane);
		return pane;
	}
	public boolean isComponentCell()
	{
		return false;
	}
	public Component getComponent()
	{
		return pane;
	}
	public Cell duplicate(ComponentDuplicator c)
	{
		JTabbedPane   jtb=(JTabbedPane)c.dupContainer(pane);
		TabbedPaneCell tc=
		  new TabbedPaneCell (jtb);
		int n=pane.getTabCount();
		for(int i=0;i<n;i++)
		{
			Component comp=pane.getComponentAt(i);
			if(isTabCell(i))
			  tc.add(pane.getTitleAt(i),
				getTopLevelCell(comp).duplicate(c));
			else jtb.addTab(pane.getTitleAt(i),c.dupComponent(comp));
			
		}
		return tc;
	}
	public boolean isTabCell(int ind)
	{
		if(ind>=wrappedComponentFlag.size())return false;
		return (wrappedComponentFlag.elementAt(ind).booleanValue());
	}
	public void xmlserialize(XMLPrintStream out, ComponentXMLSerializer c)
	{
		int n=pane.getTabCount();
		String element="Cell";
		String cname="TabbedPaneCell";
		out.beginElement(element);
		  out.addAttribute("TypeName",cname);
		  out.addAttribute("name",getName());
		  out.addAttribute("numTabs",n);
		  out.beginChildrenList(element);
			c.xmlserialize(out,pane);
			for(int i=0;i<n;i++)
			{
				out.beginElement("Tab");
				out.addAttribute("name",pane.getTitleAt(i));
		  		out.beginChildrenList("Tab");
				Component comp=pane.getComponentAt(i);
				if(!xmlserialize(comp,out,c))
					c.xmlserialize(out,comp);
		  		out.endChildrenList("Tab");
		  		out.endElement("Tab");

			}
		  out.endChildrenList(element);
		out.endElement(element);
	}
	private boolean xmlserialize(Component comp,
			XMLPrintStream out, ComponentXMLSerializer c)
	{
		if(!(Container.class.isInstance(comp)))return false;
		Container container=(Container)comp;
		LayoutManager lOut=container.getLayout();
		if((lOut==null)||(!PageLayout.class.isInstance(lOut)))
			return false;
		Cell cell=((PageLayout)lOut).getTopLevelCell();
		cell.xmlserialize(out,c);
		return true;
	}
	public int numberOfChildren(){ return cells.size();}
	public Cell getChildAt(int i){ return cells.elementAt(i);}
	public Cell getTopLevelCell(Component c)
	{
		Container container=(Container)c;
		return ((PageLayout)container.getLayout()).getTopLevelCell();
	}
	public static class CellManagedPanel extends JPanel
	{
	}
	
}
