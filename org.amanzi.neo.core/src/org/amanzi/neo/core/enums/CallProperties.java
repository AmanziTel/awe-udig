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

package org.amanzi.neo.core.enums;

/**
 * <p>
 * Enum of all Calls properties
 * </p>
 * 
 * @author Cinkel_A
 * @since 1.0.0
 */
public enum CallProperties {

    SETUP_DURATION("setup_duration"), CALL_TYPE("call_type") {
        @Override
        public boolean needMappedCount() {
            return true;
        }
    },
    CALL_DIRECTION("call_direction") {
        @Override
        public boolean needMappedCount() {
            return true;
        }
    },
    TERMINATION_DURATION("termination_duration"),
    CALL_DURATION("call_duration"),
    LQ("Listening quality"),
    DELAY("Audio delay");

    private final String id;

    /**
     * constructor
     * 
     * @param id - property name
     */
    private CallProperties(String id) {
        this.id = id;
    }

    /**
     * @return Returns the id.
     */
    public String getId() {
        return id;
    }

    /**
     * Need mapped count
     * 
     * @return
     */
    public boolean needMappedCount() {
        return false;
    }

    /**
     * is properties needs to analyse
     * 
     * @return coolean
     */
    public boolean isAnalysed() {
        return true;
    }

    /**
     * Gets enum by id
     * 
     * @param enumId - enum id
     * @return CallProperties or null
     */
    public static CallProperties getEnumById(String enumId) {
        if (enumId == null) {
            return null;
        }
        for (CallProperties call : CallProperties.values()) {
            if (call.getId().equals(enumId)) {
                return call;
            }
        }
        return null;
    }

    public enum CallType {
        SUCCESS, FAILURE;
    }

    public enum CallDirection {
        INCOMING, OUTGOING;
    }
}
