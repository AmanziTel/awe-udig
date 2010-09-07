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

package org.amanzi.awe.parser.header;

import java.util.regex.Pattern;

/**
 * TODO Purpose of 
 * <p>
 *
 * </p>
 * @author Lagutko_N
 * @since 1.0.0
 */
public class Header {
    
    private String headerName;
    
    private String propertyName;
    
    private Pattern[] headerRegex;

    public Header(String propertyName, Pattern[] headerRegex) {
        this.propertyName = propertyName;
        this.headerRegex = headerRegex;
    }
    
    public Header(String headerName) {
        this.headerName = headerName;
    }
    
    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }
    
    public Pattern[] getHeaderRegex() {
        return headerRegex;
    }
    
    public String getPropertyName() {
        return propertyName;
    }
    
    public String getHeaderName() {
        return headerName;
    }
}
