
package com.rtaware.sacredthread.parts;

import javax.inject.Inject;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.PostConstruct;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.swtchart.Chart;
import org.swtchart.IBarSeries;
import org.swtchart.ILineSeries;
import org.swtchart.ISeries.SeriesType;

import com.rtaware.sacredthread.model.ThreadDump;
import com.rtaware.sacredthread.model.ThreadInfo;
import com.rtaware.sacredthread.model.ThreadStatus;

public class DumpAnalyzer
{

	private	Composite parent;
	private ThreadDump threadDump;
	private  List<ThreadInfo> 	threadList;
	private  Chart statusChart = null;
	private String[] threadCategories = new String[]
			{
					ThreadStatus.BLOCKED.toString(),
					ThreadStatus.OBJECT_WAIT.toString(),
					ThreadStatus.RUNNABLE.toString(),
					ThreadStatus.RUNNING.toString(),
					ThreadStatus.WAITING.toString(),
					ThreadStatus.UNKNOWN.toString(),
					ThreadStatus.TIMED_WAITING.toString()
			};
	
	private double[] threadCategoryCount  	= null;
	private double[] threadPrioityCount 		= null;
	private double[] osPrioityCount  			= null;
	private Chart priorityChart;
	
	
	public ThreadDump getThreadDump()
	{
		return threadDump;
	}

	public void setThreadDump(ThreadDump threadDump)
	{
		 this.threadDump 			= threadDump;		
		 threadList 					= threadDump.getThreadList();
		 threadCategoryCount  	= new double[threadCategories.length];
		 threadPrioityCount 		= new double[threadList.size()];
		 osPrioityCount  			= new double[threadPrioityCount.length]; 
		  AtomicInteger				categoryIndex = new AtomicInteger(-1);

		 for(String threadCategory : threadCategories)
		 {
			 int count =  (int) threadList.stream().filter(	threadInfo -> threadCategory.equals( threadInfo.getThreadStatus().toString() ) ).count();
			 threadCategoryCount[categoryIndex.incrementAndGet()] = count;
		 }
		  AtomicInteger			priorityIndex =  new AtomicInteger(-1);
		 threadList.stream().mapToInt( threadInfo-> threadInfo.getThreadPriority()).forEach(i -> {threadPrioityCount[priorityIndex.incrementAndGet()] = i;});

		 AtomicInteger osPriorityIndex =  new AtomicInteger(-1);
		 threadList.stream().mapToInt( threadInfo-> threadInfo.getThreadOSPriority()).forEach(i -> {osPrioityCount[osPriorityIndex.incrementAndGet()] = i;});
 
		 for (Control control : parent.getChildren()) 
		 {
		        control.dispose();
		  }
		 
		 showStatusGraph();
		 showPriorityGraph();
	}


	public void showStatusGraph()
	{
		if(null != threadCategoryCount)
		{
			statusChart = new Chart(parent, SWT.NONE);
			statusChart.getTitle().setText("Status Distribution "+threadDump.getCreateDate().toString());

			statusChart.getAxisSet().getXAxis(0).getTitle().setText("Status");
			statusChart.getAxisSet().getYAxis(0).getTitle().setText("Count");

			statusChart.getAxisSet().getXAxis(0).enableCategory(true);
			statusChart.getAxisSet().getXAxis(0).setCategorySeries(threadCategories);

			IBarSeries threadCount = (IBarSeries) statusChart.getSeriesSet().createSeries(SeriesType.BAR,"Thread Count");
			threadCount.setYSeries(threadCategoryCount);
			threadCount.setBarColor(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
			statusChart.getAxisSet().adjustRange();

			
		}

	}
	
	private void showPriorityGraph()
	{
		if(null!= threadPrioityCount && null != osPrioityCount)
		{
			priorityChart = new Chart(parent, SWT.NONE);
			priorityChart.getTitle().setText("Priority Distribution "+threadDump.getCreateDate().toString());

			priorityChart.getAxisSet().getXAxis(0).getTitle().setText("Thread");
			priorityChart.getAxisSet().getYAxis(0).getTitle().setText("Priority");

			ILineSeries  threadPriority = (ILineSeries ) priorityChart.getSeriesSet().createSeries(SeriesType.LINE, "Thread Priority");
			threadPriority.setYSeries(threadPrioityCount);
			threadPriority.setLineColor(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));

			ILineSeries osPriority = (ILineSeries) priorityChart.getSeriesSet().createSeries(SeriesType.LINE,"OS Priority");
			
			osPriority.enableArea(true);
			osPriority.setYSeries(osPrioityCount);
			osPriority.setLineColor(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
			priorityChart.getAxisSet().adjustRange();
		}

	}
	
	
	@Inject
	public DumpAnalyzer()
	{

	}

	@PostConstruct
	public void postConstruct(Composite parent)
	{
		this.parent = parent;
		showStatusGraph();
		showPriorityGraph();
	}

}