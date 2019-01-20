package com.rtaware.bulkloader.parts;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;


import com.rtaware.bulkloader.model.MainTreeContentProvider;
import com.rtaware.bulkloader.model.MainTreeeLabelProvider;
import com.rtaware.personinfo.ContactInfo;
import com.rtaware.personinfo.ContactType;
import com.rtaware.personinfo.PersonDetails;
import com.rtaware.personinfo.PersonInfo;


public class ActionSelector
{

	@Inject
	EPartService partService;
	private TreeViewer treeViewer;
	protected Text text;


	@PostConstruct
	public void createComposite(Composite parent) 
	{
		GridLayout layout = new GridLayout();
		layout.numColumns 		= 1;
		layout.verticalSpacing 	= 1;
		layout.marginWidth 		= 0;
		layout.marginHeight 		= 1;
		parent.setLayout(layout);
		
		GridData layoutData = new GridData();
	
		treeViewer = new TreeViewer(parent);
		treeViewer.setContentProvider	(new MainTreeContentProvider());
		treeViewer.setLabelProvider		(new MainTreeeLabelProvider());
		
	
		layoutData.grabExcessHorizontalSpace 	= true;
		layoutData.grabExcessVerticalSpace	 	= true;
		layoutData.horizontalAlignment 				= GridData.FILL;
		layoutData.verticalAlignment 					= GridData.FILL;
		treeViewer.getControl().setLayoutData(layoutData);

		treeViewer.setInput(getInitalInput());
		//treeViewer.expandAll();

		
		treeViewer.addDoubleClickListener(new IDoubleClickListener()
		{
			@Override
			public void doubleClick(DoubleClickEvent event)
			{
//				TreeViewer viewer = (TreeViewer) event.getViewer();
				IStructuredSelection thisSelection = (IStructuredSelection) event.getSelection();
				Object selectedNode = thisSelection.getFirstElement();
				if (selectedNode instanceof ContactInfo)
				{
					ContactInfo contactInfo = (ContactInfo) selectedNode;
					System.out.println(contactInfo.getContactType()+ " : "+contactInfo.getContactSpecification());
					
					MPart myPart = partService.findPart("com.rtaware.bulkloader.part.executioncommands");
					partService.activate(myPart, true);

				}
				else if (selectedNode instanceof PersonInfo)
				{
					PersonInfo personInfo = (PersonInfo) selectedNode;
					System.out.println(personInfo.getPersonFirstName()+ " : "+personInfo.getPersonLastName()+ " : "+personInfo.getPersonDOB());
					MPart mPart = partService.findPart("com.rtaware.bulkloader.part.xmlgenerator");
					partService.activate(mPart, true);

					XMLGenerator xmlGenerator = (XMLGenerator) mPart.getObject();
					xmlGenerator.setPersonInfo(personInfo);
					
				}
			}
		});
		
	}

	public PersonDetails getInitalInput()
	{
		
		PersonDetails   personDetails = new PersonDetails();	
		List<PersonInfo> personList = new ArrayList<>();
		List<ContactInfo> contactDetails = new ArrayList<>();

		contactDetails.add(new ContactInfo(ContactType.EMAIL_PERSONAL,"bipinbanathur@gmail.com"));
		contactDetails.add(new ContactInfo(ContactType.EMAIL_OFFICIAL,"bipin.banathur@huawei.com"));
		contactDetails.add(new ContactInfo(ContactType.ADDRESS_OFFICE,"Whitefeild"));
		contactDetails.add(new ContactInfo(ContactType.ADDRESS_RESIDENCE,"CV Raman Nagar"));
		contactDetails.add(new ContactInfo(ContactType.CONTACT_MOBILE,"+919912350393"));
		contactDetails.add(new ContactInfo(ContactType.CONTACT_PHONE,"+914962765158"));
		
		PersonInfo personInfo = new PersonInfo();
		personInfo.setPersonFirstName("Bipin");
		personInfo.setPersonLastName("Banathur");
		personInfo.setPersonDOB(new java.util.Date());
		personInfo.setContactDetails(contactDetails);
		
		personList.add(personInfo);
		
		contactDetails = new ArrayList<>();

		contactDetails.add(new ContactInfo(ContactType.EMAIL_PERSONAL,"aswathibipin@gmail.com"));
		contactDetails.add(new ContactInfo(ContactType.ADDRESS_OFFICE,"B Narayanapura"));
		contactDetails.add(new ContactInfo(ContactType.ADDRESS_RESIDENCE,"CV Raman Nagar"));
		contactDetails.add(new ContactInfo(ContactType.CONTACT_MOBILE,"+919642526822"));
		contactDetails.add(new ContactInfo(ContactType.CONTACT_PHONE,"+914962765158"));
		
		personInfo = new PersonInfo();
		personInfo.setPersonFirstName("Aswathi");
		personInfo.setPersonLastName("Bipin");
		personInfo.setPersonDOB(new java.util.Date());
		personInfo.setContactDetails(contactDetails);
		
		personList.add(personInfo);
		
		
		contactDetails = new ArrayList<>();

		contactDetails.add(new ContactInfo(ContactType.EMAIL_PERSONAL,"anuprabha.banathur@gmail.com"));
		contactDetails.add(new ContactInfo(ContactType.ADDRESS_OFFICE,"CV Raman Nagar"));
		contactDetails.add(new ContactInfo(ContactType.ADDRESS_RESIDENCE,"CV Raman Nagar"));
		contactDetails.add(new ContactInfo(ContactType.CONTACT_MOBILE,"+919642526822"));
		contactDetails.add(new ContactInfo(ContactType.CONTACT_PHONE,"+914962765158"));
		
		personInfo = new PersonInfo();
		personInfo.setPersonFirstName("Anuprabha");
		personInfo.setPersonLastName("Banathur");
		personInfo.setPersonDOB(new java.util.Date());
		personInfo.setContactDetails(contactDetails);
		
		personList.add(personInfo);
		
		personDetails.setPersonDetails(personList);
		return personDetails;
	}
	
	public void setFocus() {}
	


}