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

package org.amanzi.awe.afp.testing.engine;

import java.io.File;
import java.util.ArrayList;

import org.amanzi.neo.loader.core.CommonConfigData;
import org.amanzi.neo.loader.core.ILoader;
import org.apache.log4j.Logger;

/**
 * TODO Purpose of
 * <p>
 * </p>
 * 
 * @author gerzog
 * @since 1.0.0
 */
public class LoadNetworkConfigDataAction extends AbstractLoadAction {
    
    private static final Logger LOGGER = Logger.getLogger(LoadNetworkConfigDataAction.class);

    /**
     * @param file
     * @param projectName
     * @param rootName
     */
    public LoadNetworkConfigDataAction(File file, String projectName, String rootName) {
        super(file, projectName, rootName);
    }

    @Override
    protected CommonConfigData getConfigData() {
        CommonConfigData configData = super.getConfigData();
        
        ArrayList<File> fileList = new ArrayList<File>();
        for (File singleFile : file.listFiles()) {
            LOGGER.info(singleFile.getAbsolutePath());
            fileList.add(singleFile);
        }
        configData.setFileToLoad(fileList);
        
        return configData;       
    }

    @Override
    protected ILoader< ? , CommonConfigData> getLoader() {
        return FakeLoaderFactory.getNetworkConfigLoader();
    }

}
