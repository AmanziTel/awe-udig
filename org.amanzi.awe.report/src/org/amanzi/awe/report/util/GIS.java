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

package org.amanzi.awe.report.util;

import java.util.Collection;

import net.refractions.udig.project.IMap;
import net.refractions.udig.project.ui.ApplicationGIS;

/**
 * TODO Purpose of
 * <p>
 * </p>
 * 
 * @author Pechko_E
 * @since 1.0.0
 */
public class GIS {
    
    public static final IMap NO_MAP = ApplicationGIS.NO_MAP;

    public static Collection< ? extends IMap> getOpenMaps() {
        System.out.println("[DEBUG] GIS.getOpenMaps");
        return ApplicationGIS.getOpenMaps();
    }

}