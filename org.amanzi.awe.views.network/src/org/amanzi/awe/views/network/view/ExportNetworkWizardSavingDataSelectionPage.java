/* AWE - Amanzi Wireless Explorer
 * http://awe.amanzi.org
 * (C) 2008-2009, AmanziTel AB
 *
 * This library is provided under the terms of the Eclipse Public License
 * as described at http://www.eclipse.org/legal/epl-v10.html. Any use,
 * reproduction or distribution of the library constitutes recipient's
 * acceptance of this agreement.
 *
 * This library is distributed WITHOUT ANY WARRANTY; without even the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package org.amanzi.awe.views.network.view;

import java.io.File;
import java.util.HashMap;

import org.eclipse.jface.dialogs.DialogPage;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

/**
 * TODO Purpose of 
 * <p>
 *
 * </p>
 * @author Kasnitskij_V
 * @since 1.0.0
 */
public class ExportNetworkWizardSavingDataSelectionPage extends WizardPage {

    private static final String FREQUENCY_CONSTRAINT_DATA = "Frequency constraint data";
    private static final String SEPARATION_CONSTRAINT_DATA = "Separation constraint data";
    private static final String TRAFFIC_DATA = "Traffic data";
    private static final String TRX_DATA = "Trx data";
    private static final String INTERFERENCE_MATRIX = "Interference matrix";
    
    private FileFieldEditor editor;
    private Group main;
    private String fileWithPrefixName = "";
    private Button checkboxFrequencyConstraintData;
    private Button checkboxSeparationConstraintData;
    private Button checkboxTrafficData;
    private Button checkboxTrxData;
    private Button checkboxInterferenceMatrix;
    
    protected ExportNetworkWizardSavingDataSelectionPage(String pageName) {
        super(pageName);
        setDescription("Choose data that should be exported and prefix of target files for export");
        setPageComplete(false);
    }
    
    @Override
    public void createControl(Composite parent) {
        main = new Group(parent, SWT.NULL);
        main.setLayout(new GridLayout(3, false));

        Label label = new Label(main, SWT.LEFT);
        label.setText("Network");
        label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 3, 1));
        
        editor = new FileFieldEditor("DirectorySelect", "Postfix name", main);

        editor.getTextControl(main).addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                setDirectoryName(editor.getStringValue());
            }
        });
        
        GridData checkboxLayoutData = new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1);
        
        checkboxFrequencyConstraintData = new Button(main, SWT.CHECK);
        checkboxFrequencyConstraintData.setText(FREQUENCY_CONSTRAINT_DATA);
        checkboxFrequencyConstraintData.setLayoutData(checkboxLayoutData);
        
        checkboxInterferenceMatrix = new Button(main, SWT.CHECK);
        checkboxInterferenceMatrix.setText(INTERFERENCE_MATRIX);
        checkboxInterferenceMatrix.setLayoutData(checkboxLayoutData);
        
        checkboxSeparationConstraintData = new Button(main, SWT.CHECK);
        checkboxSeparationConstraintData.setText(SEPARATION_CONSTRAINT_DATA);
        checkboxSeparationConstraintData.setLayoutData(checkboxLayoutData);
        
        checkboxTrafficData = new Button(main, SWT.CHECK);
        checkboxTrafficData.setText(TRAFFIC_DATA);
        checkboxTrafficData.setLayoutData(checkboxLayoutData);
        
        checkboxTrxData = new Button(main, SWT.CHECK);
        checkboxTrxData.setText(TRX_DATA);
        checkboxTrxData.setLayoutData(checkboxLayoutData);
        
        
        setControl(main);
    }

    /**
     * Sets the file name.
     * 
     * @param fileName the new file name
     */
    protected void setDirectoryName(String directoryName) {
        this.fileWithPrefixName = directoryName;
        validate();
    }

    /**
     * Gets the file name.
     * 
     * @return the file name
     */
    public String getFileWithPrefixName() {
        return this.fileWithPrefixName;
    }
    
    /**
     * Gets checkboxes state.
     *
     * @return the hashmap with checkboxes state
     */
    public HashMap<String, Boolean> getCheckBoxesState() {
        HashMap<String, Boolean> checkboxesState = new HashMap<String, Boolean>();
        checkboxesState.put(FREQUENCY_CONSTRAINT_DATA, checkboxFrequencyConstraintData.getSelection());
        checkboxesState.put(SEPARATION_CONSTRAINT_DATA, checkboxSeparationConstraintData.getSelection());
        checkboxesState.put(TRAFFIC_DATA, checkboxTrafficData.getSelection());
        checkboxesState.put(TRX_DATA, checkboxTrxData.getSelection());
        checkboxesState.put(INTERFERENCE_MATRIX, checkboxInterferenceMatrix.getSelection());
        
        return checkboxesState;
    }
    
    /**
     * Validate page content.
     */
    private void validate() {
        if (fileWithPrefixName.isEmpty()) {
            setPageComplete(false);
            setMessage("Target file is not selected.", DialogPage.ERROR);
            return;
        }
        if (!new File(fileWithPrefixName).isAbsolute() || new File(fileWithPrefixName).isDirectory()) {
            setPageComplete(false);
            setMessage(String.format("File path '%s' is not valid.", fileWithPrefixName), DialogPage.ERROR);
            return;
        }

        if (new File(fileWithPrefixName).isFile()) {
            setPageComplete(true);
            setMessage(String.format("File path '%s' is already exist.", fileWithPrefixName), DialogPage.WARNING);
            return;
        }

        setMessage("", DialogPage.NONE);
        setPageComplete(true);
    }

}