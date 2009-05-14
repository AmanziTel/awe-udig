/*
 * uDig - User Friendly Desktop Internet GIS client http://udig.refractions.net (C) 2004, Refractions Research Inc. This
 * library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; version 2.1 of the License. This library is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 */
package net.refractions.udig.project.ui.internal.wizard;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import net.refractions.udig.project.ui.internal.ImageConstants;
import net.refractions.udig.project.ui.internal.Images;
import net.refractions.udig.project.ui.internal.Messages;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * A wizard page to create a new project.
 * 
 * @author vitalus
 * 
 * @since 0.3
 */
public class NewProjectWizardPage extends WizardPage {
	
	DirectoryFieldEditor projectDirectoryEditor;
	
	StringFieldEditor projectNameEditor;
	
    /**
     * Construct <code>NewProjectWizardPage</code>.
     */
    public NewProjectWizardPage() {
        super(
                Messages.NewProjectWizardPage_newProject, Messages.NewProjectWizardPage_newProject, Images.getDescriptor(ImageConstants.NEWPROJECT_WIZBAN));   
        setDescription(Messages.NewProjectWizardPage_newProject_description); 
    }

    /**
     * Set up this page for use.
     * 
     * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
     * @param parent
     */
    public void createControl( Composite parent ) {
    	
    	
        Composite composite = new Composite(parent, SWT.NONE);
    	

    	projectNameEditor = new StringFieldEditor("newproject.name", Messages.NewProjectWizardPage_label_projectName, composite);   //$NON-NLS-1$
    	projectNameEditor.setPage(this);
    	projectNameEditor.setValidateStrategy(StringFieldEditor.VALIDATE_ON_KEY_STROKE);
    	Text textControl = projectNameEditor.getTextControl(composite);
    	GridData gd = new GridData(SWT.LEFT, SWT.NONE, false, false);
    	gd.widthHint = 100;
    	gd.horizontalSpan = 2;
    	textControl.setLayoutData(gd);
    	

    	
    	projectDirectoryEditor = new DirectoryFieldEditor("newproject.directory", Messages.NewProjectWizardPage_label_projectDir, composite);   //$NON-NLS-1$
    	projectDirectoryEditor.setPage(this);
    	projectDirectoryEditor.fillIntoGrid(composite, 3);
    	
    	String defaultProjectName = Messages.NewProjectWizardPage_default_name; 

    	final IPath homepath = Platform.getLocation();
    	String projectPath = new File(homepath.toString()).getAbsolutePath(); //$NON-NLS-1$
    	
    	projectNameEditor.setStringValue(defaultProjectName);
    	projectDirectoryEditor.setStringValue(projectPath);

		composite.pack();

        setControl(composite);
        setPageComplete(true);
    }
    
    
    /**
     * Returns specified project name.
     * 
     * @return
     */
    public String getProjectName(){
    	return projectNameEditor.getStringValue();
    }
    
    /**
     * Returns specified project path.
     * 
     * @return
     */
    public String getProjectPath(){
    	return projectDirectoryEditor.getStringValue();
    }

    
    /**
     * Validates the form with project name and path.
     * 
     * @return
     * 		<code>true</code> if valid
     */
    public boolean validate(){
    	
    	if(!projectNameEditor.isValid()){
    		setErrorMessage(Messages.NewProjectWizardPage_err_project_name); 
    		return false;
    	}
    	
        final String projectPath = getProjectPath();
        final String projectName = getProjectName();
        

        if(projectPath == null || projectPath.length() == 0){
        	setErrorMessage(Messages.NewProjectWizardPage_err_project_dir_valid); 
        	return false;
        }

        File projectPathFolder = null;
        try {
        	URL projectURL = new URL("file:///"+projectPath); //$NON-NLS-1$
        	projectPathFolder = new File(projectURL.getFile());

        	String absolutePath = projectPathFolder.getAbsolutePath();
        	if(!projectPath.equals(absolutePath)){
        		setErrorMessage(Messages.NewProjectWizardPage_err_project_dir_absolute); 
        		return false;
        	}

        } catch (MalformedURLException e) {
        	e.printStackTrace();
        	return false;
        }
        
        if(projectPathFolder.exists()){
        	String projectFileAbsolutePath = projectPathFolder.getAbsolutePath() + File.separatorChar + "project.uprj"; //$NON-NLS-1$;
        	File projectFile = new File(projectFileAbsolutePath);
        	if(projectFile.exists()){
        		setErrorMessage(Messages.NewProjectWizardPage_err_project_exists); 
        		return false;
        	}
        }
        
        if(projectName == null || projectName.length() == 0){
        	setErrorMessage(Messages.NewProjectWizardPage_err_project_name); 
        	return false;
        }
        return true;
    }



}
