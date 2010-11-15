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
package org.amanzi.neo.services.enums;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * GIS Types of GIS node
 * </p>
 * 
 * @author Cinkel_A
 * @since 1.0.0
 */
public enum GisTypes {
    /** GisTypes Network */
    NETWORK("network"),
    /** GisTypes TEMS */
    DRIVE("drive"),
    /** GisTypes OSS */
    OSS("oss");

    private String header;

    private GisTypes(String header) {
        this.header = header;
    }

    /**
     * gets header
     * 
     * @return header
     */
    public String getHeader() {
        return header;
    }

    /**
     * Find enum by header
     * 
     * @param header
     * @return GisTypes
     */
    public static GisTypes findGisTypeByHeader(String header) {
        if (StringUtils.isEmpty(header)){
            return null;
        }
        for (GisTypes gisType : GisTypes.values()) {
            if (gisType.getHeader().equals(header)) {
                return gisType;
            }
        }
        return null;
    }

    /**
     *
     * @param property
     * @return
     */
    public static GisTypes getGisTypeFromRootType(String rootType) {
        if (NodeTypes.NETWORK.getId().equals(rootType)){
            return NETWORK;
        }else if (NodeTypes.DATASET.getId().equals(rootType)){
            return DRIVE;
        }else {
            return OSS;
        }
    }
}