package com.rtaware.bulkloader.handlers;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import com.rtaware.bulkloader.ApplicationConfigurations;

public class AboutHandler 
{
	@Execute
	public void execute(Shell shell)
	{
		MessageDialog.openInformation(shell, "About", ApplicationConfigurations.APPLICATION_NAME+"\n"+ApplicationConfigurations.APPLICATION_VERSION);
	}
}
