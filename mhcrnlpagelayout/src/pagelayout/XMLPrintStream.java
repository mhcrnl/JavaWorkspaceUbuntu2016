/*
	------------------------------------------------
	XMLPrintStream.java
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
/**
   This class is used by Gola ( http://gola.mathnium.com ), 
   a visual layout editor, and is not
   needed if you want to hand code the layout.

	@version  1.16  05/10/2008
**/

public interface XMLPrintStream
{
	public void beginElement(String element);
	public void endElement(String element);
	public void beginChildrenList(String element);
	public void endChildrenList(String element);
	public void addAttribute(String name, String value);
	public void addAttribute(String name, int value);
	public void addAttribute(String name, double value);
	public void addAttribute(String name, boolean value);
	public void newAttributeLine();
}
