
package com.rtaware.sacredthread.parts;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.swtchart.Chart;
import org.swtchart.IBarSeries;
import org.swtchart.ISeries.SeriesType;

public class ThreadVisualizations
{

	@Inject
	public ThreadVisualizations()
	{

	}

	@PostConstruct
	public void postConstruct(Composite parent)
	{

		Chart chart = new Chart(parent, SWT.NONE);
		chart.getTitle().setText("Status Distribution");

		chart.getAxisSet().getXAxis(0).getTitle().setText("Status");
		chart.getAxisSet().getYAxis(0).getTitle().setText("Count");

		chart.getAxisSet().getXAxis(0).enableCategory(true);
		chart.getAxisSet().getXAxis(0).setCategorySeries(new String[] { "Blocked", "Running", "Runnable", "Waiting", "Object Wait" });

		IBarSeries threadStatus = (IBarSeries) chart.getSeriesSet().createSeries(SeriesType.BAR, "Thread Status");
		threadStatus.setYSeries(new double[] { 1, 2, 3, 4, 5 });
		threadStatus.setBarColor(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));

		IBarSeries threadCount = (IBarSeries) chart.getSeriesSet().createSeries(SeriesType.BAR,"Thread Count");
		threadCount.setYSeries(new double[] { 5, 4, 3, 2, 1 });
		threadStatus.setBarColor(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
		chart.getAxisSet().adjustRange();

		// chart.getAxisSet().getXAxis(0).enableCategory(true);
		//
		// IAxisSet axisSet = chart.getAxisSet();
		// IAxis xAxis = axisSet.getXAxis(0);
		// xAxis.setCategorySeries(new String[] { "Linux", "Windows" });
		// xAxis.enableCategory(true);
		//
		// IBarSeries series = (IBarSeries) chart.getSeriesSet().createSeries(
		// SeriesType.BAR, "line series");
		// series.setBarColor(parent.getDisplay().getSystemColor(SWT.COLOR_RED));
		// double[] values = { 0.7, 0.2 };
		// series.setYSeries(values);

	}

}