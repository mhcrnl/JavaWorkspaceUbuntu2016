/*
	------------------------------------------------
	GridRows.java
	------------------------------------------------

	This software is distribiuted unde the 
	GNU Lesser General Public  License (LGPL) Version 2.1.

	You should have received a copy of the license with this
	distribution.

	The license is also available at

	http://www.gnu.org/licenses/lgpl.txt	
	
	Copyright (c) 2006 Mathnium Associates.
*/
package  pagelayout;
import java.util.*;
import java.awt.*;
/**
	GridRows is a utility class that may make it easier to create
	a grid using GridRow.

	It just provides some syntactic sugar so the code for using
	{@link pagelayout.GridRow GridRow}
	 may appear to be more compact to some users.

	Here is the alternate code for {@link examples.Example10 Example10}
	that replaces the lines that create the seven rows for the grid and then
	the grid itself. This is available as {@link examples.Example11 Example11}.

<pre>
		GridRows rows=new GridRows();
		
		<b>// First row </b>
		rows.newRow().add(lastName,lastNameE,firstName,firstNameE);

		
		<b>// Second row </b>
		rows.newRow().add(phone,phoneE)
		    .add(new Row(NO_ALIGNMENT,CENTER,email,emailE)).span(2);
		
		
		<b>// Third row </b>
		rows.newRow().add(address1,address1E).span(3);

		
		<b>// Fourth row </b>
		rows.newRow().add(address2,address2E).span(3);

		
		<b>// Fifth row </b>
		rows.newRow().add(city,cityE);

		
		<b>// Sixth row </b>
		rows.newRow().add(state,stateE,postal,postalE);

		<b>// Seventh row</b>
		rows.newRow().add(county,countyE);

		CellGrid grid=rows.createCellGrid();
</pre>
	@version  1.16  05/10/2008

**/
	

public class GridRows
{
	private Vector<GridRow> rows;
	private GridRow currentRow;

/**
	Creates instance of this class.

**/
	public GridRows()
	{
		rows=new Vector<GridRow>();
	}
/**
	Creates and returns a new {@link pagelayout.GridRow GridRow} object.
	This row is added to the existing rows.

	@return The newly created <code>GridRow</code>.

**/
	public GridRow newRow()
	{
		currentRow=new GridRow();
		rows.add(currentRow);
		return currentRow;
		
	}
/**
	Returns the current row represented by
	a {@link pagelayout.GridRow GridRow} object.

	@return The current row.

**/
	public GridRow getCurrentRow()
	{
		if(rows.size()==0)return newRow();
		return currentRow;
	}
/**
	Creates a {@link pagelayout.CellGrid CellGrid} object from the
	rows created by calling the {@link pagelayout.GridRows#newRow newRow}
	 method of this object.

	@return The CellGrid object.

**/
	public CellGrid createCellGrid()
	{
		int n=rows.size();
		int k=0;
		for(int i=0;i<n;i++)
		{
			GridRow r=rows.elementAt(i);
			if(r.size()>0)k++;
		}
		if(k==0)return null;
		GridRow gridRows[]=new GridRow[k];
		k=0;
		for(int i=0;i<n;i++)
		{
			GridRow r=rows.elementAt(i);
			if(r.size()>0)gridRows[k++]=r;
		}
		return CellGrid.createCellGrid(gridRows);
	}
}
