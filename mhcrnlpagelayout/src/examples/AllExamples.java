package examples;

import pagelayout.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.reflect.*;

public class AllExamples extends JDialog
{
	public AllExamples()
	{
		super();
	}
	public void createAndShow()
	{
		Column c=new Column(Cell.CENTER,Cell.CENTER);
		JButton b=null;
		JButton[] buttons=new JButton[12];
		for(int i=1;i<=13;i++)
		{
			b=new JButton("Example"+i);
			if(i<=12)buttons[i-1]=b;
			b.addActionListener(new ExampleLauncher(i));
			c.add(b);
		}	
		c.linkWidth(b,1,buttons);
		JButton ok=new JButton("Exit");
		c.add(new Gap(10));
		c.add(ok);
		ok.addActionListener(new OKListener());
		c.createLayout(getContentPane());
		pack();
		setModal(false);
		right(this);
		setAlwaysOnTop(false);
		show();	
	}
	public class OKListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.exit(1);
		}
	}
	public class ExampleLauncher implements ActionListener
	{
		private int exampleNum; 
		public ExampleLauncher(int num)
		{
			exampleNum=num;
		}
		public void actionPerformed(ActionEvent e)
		{
			launch();
		}
		public void launch()
		{
                   try
		   {
			Class clz=Class.forName("examples.Example"+exampleNum);
			Method m[]=clz.getDeclaredMethods();
			Method method=null;
			for(int i=0;i<m.length;i++)
			{
				if(m[i].getName().equals("run"))
				{
					method=m[i];
					break;
				}
			}
			method.invoke(null,(Object[])null);
		   }
                   catch(Exception e){ e.printStackTrace();}
		}
	}
	public static void right(Component c)
	{
		Rectangle p=GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		int w=c.getWidth();
		int h=c.getHeight();
		c.setBounds(p.x+p.width-w-5,5,w,h);
	}
	public static void main(String[] args)
	{
		AllExamples a=new AllExamples();
		a.createAndShow();
	}
}
