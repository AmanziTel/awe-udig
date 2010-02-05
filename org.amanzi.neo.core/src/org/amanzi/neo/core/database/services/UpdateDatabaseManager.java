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
package org.amanzi.neo.core.database.services;

import org.amanzi.neo.core.database.listener.IUpdateDatabaseListener;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;

/**
 * Manager updating cells from bd
 * 
 * @author Cinkel_A
 * 
 */
// TODO create extension point?
public class UpdateDatabaseManager {

	private ListenerList listeners = new ListenerList();

	/**
	 * Adds new listener
	 * 
	 * @param listener
	 */
	public void addListener(IUpdateDatabaseListener listener) {
		getListeners().add(listener);
	}

	/**
	 * Remove Listener
	 * 
	 * @param listener
	 */
	public void removeListener(IUpdateDatabaseListener listener) {
		getListeners().remove(listener);
	}

	/**
	 * Create and send UpdateBdEvent
	 * 
	 * @param rubyProjectName
	 *            ruby project name
	 * @param spreadSheetName
	 *            spreadsheet name
	 * @param fullCellID
	 *            full cell id
	 */
	public void updateCell(String rubyProjectName, String spreadSheetName,
			String fullCellID) {
		UpdateDatabaseEvent event = new UpdateDatabaseEvent(rubyProjectName,
				spreadSheetName, fullCellID);
		fireUpdateDatabase(event);

	}

	/**
	 * Gets all listeners
	 * 
	 * @return all listeners
	 */
	protected ListenerList getListeners() {
		return listeners;
	}

    /**
     * Fires <code>UpdateDatabaseEvent</code> to listeners.
     * 
     * @param event UpdateDatabaseEvent
     */
    public void fireUpdateDatabase(final UpdateDatabaseEvent event) {
		Object[] allListeners = getListeners().getListeners();
		for (Object listener : allListeners) {
			final IUpdateDatabaseListener singleListener = (IUpdateDatabaseListener) listener;
			SafeRunner.run(new ISafeRunnable() {
				@Override
                public void run() throws Exception {
                    if (singleListener.getType().contains(event.getType())) {
                        singleListener.databaseUpdated(event);
                    }
				}

				@Override
				public void handleException(Throwable exception) {
				}
			});
		}
	}

}
