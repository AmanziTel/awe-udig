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

package org.amanzi.neo.wizards;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;

import org.amanzi.neo.core.service.NeoServiceProvider;
import org.amanzi.neo.core.utils.NeoUtils;
import org.amanzi.neo.loader.internal.NeoLoaderPluginMessages;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.neo4j.api.core.NeoService;
import org.neo4j.api.core.Node;

/**
 * <p>
 * </p>
 * 
 * @author Cinkel_A
 * @since 1.0.0
 */
public class GPEHImportWizardPage extends WizardPage {

    private Combo dataset;
    private final NeoService service;
    protected String datasetName;
    private DirectoryFieldEditor editor;
    private String directory;

    /**
     * @param pageName
     */
    protected GPEHImportWizardPage(String pageName) {
        super(pageName);
        setTitle(NeoLoaderPluginMessages.GpehTitle);
        setDescription(NeoLoaderPluginMessages.GpehDescr);
        service = NeoServiceProvider.getProvider().getService();
        validateFinish();
    }

    @Override
    public void createControl(Composite parent) {
        Composite main = new Composite(parent, SWT.NULL);
        main.setLayout(new GridLayout(3, false));
        Label label = new Label(main, SWT.LEFT);
        label.setText(NeoLoaderPluginMessages.GpehLbOSS);
        label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
        dataset = new Combo(main, SWT.DROP_DOWN);
        dataset.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
        dataset.setItems(getOssData());
        dataset.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                datasetName = dataset.getText();
                validateFinish();
            }
        });
        dataset.addSelectionListener(new SelectionListener() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                boolean selected = dataset.getSelectionIndex() < 0;
                datasetName = dataset.getText();
                validateFinish();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }
        });

        editor = new DirectoryFieldEditor(NeoLoaderPluginMessages.GpehImportDirEditorTitle, NeoLoaderPluginMessages.ETSIImport_directory, main); // NON-NLS-1
        editor.getTextControl(main).addModifyListener(new ModifyListener() {
            public void modifyText(ModifyEvent e) {
                setFileName(editor.getStringValue());
            }
        });
        setControl(main);

    }

    /**
     * @param stringValue
     */
    protected void setFileName(String dirName) {
        directory = dirName;
        validateFinish();

    }

    /**
     *check correct input
     */
    private void validateFinish() {
        setPageComplete(isValidPage());
    }

    /**
     * validate page
     * @return
     */
    protected boolean isValidPage() {
        try {
            if (!StringUtils.isEmpty(directory) && !StringUtils.isEmpty(datasetName)){
                File file = new File(directory);
                return file.isDirectory()&&file.isAbsolute()&&file.exists();
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     *get all OSS
     * 
     * @return
     */
    private String[] getOssData() {
        Collection<Node> allOss = NeoUtils.getAllOss(service);
        Iterator<Node> it = allOss.iterator();
        String[] result = new String[allOss.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = NeoUtils.getNodeName(it.next(), service);
        }
        return result;
    }

    /**
     * @return Returns the datasetName.
     */
    public String getDatasetName() {
        return datasetName;
    }

    /**
     * @return Returns the directory.
     */
    public String getDirectory() {
        return directory;
    }
    
}