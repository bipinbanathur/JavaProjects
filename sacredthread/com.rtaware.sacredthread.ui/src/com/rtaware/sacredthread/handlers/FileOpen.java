
package com.rtaware.sacredthread.handlers;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.rtaware.sacredthread.dumpreader.ThreadDumpReader;
import com.rtaware.sacredthread.model.ThreadDump;
import com.rtaware.sacredthread.model.ThreadDumpList;
import com.rtaware.sacredthread.parts.DumpBrowser;

public class FileOpen
{
	private ThreadDump 				threadDump;
	private ThreadDumpList 		threadDumpList;
	private ThreadDumpReader 	threadDumpReader = new ThreadDumpReader();
	
	@Inject
	EPartService partService;
	
	@Execute
	public void execute(Shell shell)
	{
		FileDialog dialog 	= new FileDialog(shell);
		String selectedFile = dialog.open();
		if(null != selectedFile)
		{
			try
			{
				threadDump = threadDumpReader.geThreadDump(selectedFile);
				
				MPart mPart = partService.findPart("com.rtaware.sacredthread.ui.part.dumpnavigator");
				partService.activate(mPart, true);
				
				DumpBrowser dumpBrowser = (DumpBrowser) mPart.getObject();
				
				threadDumpList = dumpBrowser.getThreadDumpList();
				threadDumpList.addThreadDump(threadDump);
				dumpBrowser.setThreadDumpList(threadDumpList);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}
	}

}