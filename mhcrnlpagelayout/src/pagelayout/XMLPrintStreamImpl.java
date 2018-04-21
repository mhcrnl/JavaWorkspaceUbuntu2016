/*
	------------------------------------------------
	XMLPrintStreamImpl.java
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

import java.io.*;
/**
   This class is used by Gola ( http://gola.mathnium.com ), 
   a visual layout editor, and is not
   needed if you want to hand code the layout.

	@version  1.16  05/10/2008
**/

public class XMLPrintStreamImpl implements XMLPrintStream
{
	private final static  int CLEAR=0;
	private final static  int ELEMENTBEGAN=1;
	private final static  int CHILDRENBEGAN=2;
	private final static  int CHILDRENDUMPED=3;
	private final static  int SPACE=2;
	private PrintStream ps;
	private int state;
	private int indent;
	public XMLPrintStreamImpl(PrintStream ps)
	{
		this.ps=ps;
		indent=0;
		state=CLEAR;
	}
	public void beginElement(String element)
	{
		beginElementLine();
		ps.print("<");
		ps.print(element);
		state=ELEMENTBEGAN;
	}
	public void endElement(String element)
	{
		beginElementLine();
		if(state==CHILDRENDUMPED)
		{
			ps.print("</");
			ps.print(element);
			ps.println(">");
		}
		else if(state==ELEMENTBEGAN)
		{
			ps.println(" />");	
		}
		state=CLEAR;
	}
	public void addAttribute(String name, String value)
	{
		ps.printf(" %s = \"%s\" ",name,value);
	}
	public void addAttribute(String name, int value)
	{
		ps.printf(" %s = \"%d\" ",name,value);
	}
	public void addAttribute(String name, double value)
	{
		ps.printf(" %s = \"%f\" ",name,value);
	}
	public void addAttribute(String name, boolean value)
	{
		ps.printf(" %s = \"%s\" ",name,value?"true":"false");
	}
	public void beginChildrenList(String element)
	{
		ps.println(">");
		incIndent();
		state=CHILDRENBEGAN;
	}
	public void endChildrenList(String element)
	{
		decIndent();
		state=CHILDRENDUMPED;
	}
	public void incIndent()
	{
		indent+=SPACE;
	}
	public void decIndent()
	{
		indent-=SPACE;
	}
	public void beginElementLine()
	{
		for(int i=0;i<indent;i++)ps.print(' ');
	}
	public void newAttributeLine()
	{
		ps.print("\n");
		beginElementLine();
		ps.print("        ");
	}
}
