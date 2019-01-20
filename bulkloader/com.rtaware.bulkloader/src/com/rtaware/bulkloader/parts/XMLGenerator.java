 
package com.rtaware.bulkloader.parts;

import javax.inject.Inject;
import javax.annotation.PostConstruct;
import org.eclipse.swt.widgets.Composite;

import com.rtaware.personinfo.PersonInfo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class XMLGenerator 
{
	
	private PersonInfo personInfo= null;
	private Table table;
	private Text firstName;
	private Text lastName;
	private Text dateOfBirth;
	
	public PersonInfo getPersonInfo()
	{
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo)
	{
		this.personInfo = personInfo;
		firstName.setText("");
		lastName.setText("");
		dateOfBirth.setText("");
		table.removeAll();
		if(null!= personInfo)
		{
			firstName.setText(personInfo.getPersonFirstName());
			lastName.setText(personInfo.getPersonLastName());
			dateOfBirth.setText(personInfo.getPersonDOB().toString());
			
			personInfo.getContactDetails().forEach(contact ->
				{
				    TableItem item1 = new TableItem(table, SWT.NONE);
				    item1.setText(new String[] {contact.getContactType().toString() ,contact.getContactSpecification() });
				}
			);
				
			
		}
	}

	@Inject
	public XMLGenerator() 
	{
			System.out.println("Injected XMLGenerator");
	}
	
	@PostConstruct
	public void postConstruct(Composite parent) 
	{
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FormLayout());
		
		Label lblFirstName = new Label(composite, SWT.NONE);
		FormData fd_lblFirstName = new FormData();
		fd_lblFirstName.left = new FormAttachment(0, 45);
		lblFirstName.setLayoutData(fd_lblFirstName);
		lblFirstName.setText("First Name");
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		fd_lblFirstName.bottom = new FormAttachment(100, -244);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.top = new FormAttachment(lblFirstName, 20);
		fd_lblNewLabel.right = new FormAttachment(lblFirstName, 0, SWT.RIGHT);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("Last Name");
		
		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		FormData fd_lblNewLabel_1 = new FormData();
		fd_lblNewLabel_1.top = new FormAttachment(lblNewLabel, 22);
		fd_lblNewLabel_1.left = new FormAttachment(lblFirstName, 0, SWT.LEFT);
		lblNewLabel_1.setLayoutData(fd_lblNewLabel_1);
		lblNewLabel_1.setText("Date of Birth");
		
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		FormData fd_table = new FormData();
		fd_table.bottom = new FormAttachment(100, -22);
		fd_table.right = new FormAttachment(0, 347);
		fd_table.left = new FormAttachment(0, 45);
		table.setLayoutData(fd_table);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		 TableColumn tc1 = new TableColumn(table, SWT.LEFT);
		 TableColumn tc2 = new TableColumn(table, SWT.LEFT);
		 tc1.setWidth(91);
		 tc2.setWidth(195);
		 tc1.setText("Contact Type");
		 tc2.setText("Contact Details");
		
		firstName = new Text(composite, SWT.BORDER);
		FormData fd_text = new FormData();
		fd_text.left = new FormAttachment(lblFirstName, 64);
		fd_text.right = new FormAttachment(100, -106);
		fd_text.top = new FormAttachment(lblFirstName, -3, SWT.TOP);
		firstName.setLayoutData(fd_text);
		
		lastName = new Text(composite, SWT.BORDER);
		FormData fd_text_1 = new FormData();
		fd_text_1.left = new FormAttachment(lblNewLabel, 64);
		fd_text_1.right = new FormAttachment(100, -106);
		fd_text_1.top = new FormAttachment(lblNewLabel, -3, SWT.TOP);
		lastName.setLayoutData(fd_text_1);
		
		dateOfBirth = new Text(composite, SWT.BORDER);
		fd_table.top = new FormAttachment(dateOfBirth, 19);
		FormData fd_text_2 = new FormData();
		fd_text_2.right = new FormAttachment(firstName, 0, SWT.RIGHT);
		fd_text_2.left = new FormAttachment(lblNewLabel_1, 52);
		fd_text_2.top = new FormAttachment(lblNewLabel_1, -3, SWT.TOP);
		dateOfBirth.setLayoutData(fd_text_2);
		
	}
}