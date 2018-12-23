 
package com.rtaware.bulkloader.parts;

import javax.inject.Inject;
import javax.annotation.PostConstruct;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

public class MessageConsole 
{
	private StringBuilder messageText = new StringBuilder("");
	
	public StringBuilder getMessageText()
	{
		return messageText;
	}

	public void setMessageText(StringBuilder messageText)
	{
		this.messageText.append(messageText) ;
	}
	
	public void clearConsole()
	{
		this.messageText.setLength(0);
	}

	@Inject
	public MessageConsole() 
	{
		
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) 
	{
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());		
		
		Text text = new Text(composite, SWT.BORDER);
		text.setText(messageText.toString());
	}
	
	
	
	
}