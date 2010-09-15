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

package org.amanzi.neo.loader.ui.wizards;

import java.util.ArrayList;
import java.util.List;

import org.amanzi.neo.loader.core.CommonConfigData;
import org.amanzi.neo.loader.internal.NeoLoaderPluginMessages;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.ui.IWorkbench;


/**
 * TODO Purpose of 
 * <p>
 *
 * </p>
 * @author tsinkel_a
 * @since 1.0.0
 */
public class OssImportWizard extends AbstractLoaderWizard<CommonConfigData> {
    CommonConfigData data=null;
    @Override
    protected List<IWizardPage> getMainPagesList() {
        List<IWizardPage> result=new ArrayList<IWizardPage>();
        result.add(new LoadOssMainPage());
        return result;
    }

    @Override
    public CommonConfigData getConfigurationData() {
        if (data==null){
            data=new CommonConfigData();
        }
        return data;
    }
@Override
public void init(IWorkbench workbench, IStructuredSelection selection) {
    super.init(workbench, selection);
    setWindowTitle(NeoLoaderPluginMessages.GpehWindowTitle);

}
}