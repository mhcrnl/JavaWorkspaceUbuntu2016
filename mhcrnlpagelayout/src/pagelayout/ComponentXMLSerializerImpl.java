/*
	------------------------------------------------
	ComponentXMLSerializerImpl.java
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
   This class is used by Gola ( http://gola.mathnium.com ), 
   a visual layout editor, and is not
   needed if you want to hand code the layout.

	@version  1.16  05/10/2008
**/
public class ComponentXMLSerializerImpl implements ComponentXMLSerializer
{
	public void xmlserialize(XMLPrintStream ps, Component component)
	{
		ps.beginElement("Component");
		ps.addAttribute("class",component.getClass().getName());
		ps.endElement("Component");
	}
}
