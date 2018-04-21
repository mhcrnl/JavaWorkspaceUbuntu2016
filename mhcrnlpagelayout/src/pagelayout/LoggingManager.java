package pagelayout;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.util.Hashtable;

public class LoggingManager
{
		private PrintStream ps;
		private Hashtable<String, Logger> loggers=
			new Hashtable<String,Logger>();
		private static LoggingManager instance;
		private LoggingManager()
		{
		   try
		   {
			ps= new
				PrintStream(new FileOutputStream(
					"loginfo.log"));
			return;
		   }
		   catch(Exception e){e.printStackTrace();}
		   System.exit(1);
		}
		private static LoggingManager getInstance()
		{
			if(instance==null)
			{
				instance=new LoggingManager();
			}
			return instance;
		}
		public class LoggerImpl implements Logger
		{
			private String name;
			public LoggerImpl(String name)
			{
				this.name=name;
			}
			public void info(String info, String... messages )
			{
				synchronized(ps)
				{
					ps.print(name);
					ps.print(":");
					ps.print(":");
					ps.print(info);
					for(String p:messages)
					{
						ps.print(" ");
						ps.print(p);
					}
					ps.println();
					ps.flush();
				}
			
			}
		}
		public static Logger getLogger(Class cl)
		{
			return getLogger(cl.getName());
		}
		public static Logger getLogger(Object o)
		{
			return getLogger(o.getClass());
		}
		public static Logger getLogger(String name)
		{
			return LoggingManager.getInstance().getLoggerImpl(name);
		}
		private Logger getLoggerImpl(String name)
		{
			Logger s=loggers.get(name);
			if(s==null)
			{
				s=new LoggerImpl(name);
				loggers.put(name,s);
			}
			return s;
		}
}
