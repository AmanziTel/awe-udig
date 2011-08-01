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

package org.amanzi.neo.services.statistic;

import java.util.List;

/**
 * TODO Purpose of
 * <p>
 * </p>
 * 
 * @author Kruglik_A
 * @since 1.0.0
 */
public interface IVault {
    /**
     * @return
     */
    public List<IVault> getSubVaults();

    /**
     * @return
     */
    public int getCount();

    /**
     * @return
     */
    public String getType();

    /**
     * @param vault
     */
    public void addSubVault(IVault vault);

    /**
     * @param count
     */
    public void setCount(int count);

    /**
     * @param type
     */
    public void setType(String type);

    /**
     * 
     */
    public void index();

    /**
     * 
     */
    public void parse();
}
