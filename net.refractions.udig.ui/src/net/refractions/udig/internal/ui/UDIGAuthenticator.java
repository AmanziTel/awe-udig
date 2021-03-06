/*
 *    uDig - User Friendly Desktop Internet GIS client
 *    http://udig.refractions.net
 *    (C) 2004, Refractions Research Inc.
 *
 *    This library is free software; you can redistribute it and/or
 *    modify it under the terms of the GNU Lesser General Public
 *    License as published by the Free Software Foundation;
 *    version 2.1 of the License.
 *
 *    This library is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *    Lesser General Public License for more details.
 *
 */
package net.refractions.udig.internal.ui;

import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;

import net.refractions.udig.ui.PlatformGIS;

import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.Preferences;

public class UDIGAuthenticator extends Authenticator {
    private static final String NAME = "NAME"; //$NON-NLS-1$
    private static final String PASSWORD = "PASSWORD"; //$NON-NLS-1$
    private static final String URL_AUTHENTICATION = "URL_AUTHENTICATION"; //$NON-NLS-1$
    private String username;
    private String password;
    private boolean storePassword;
    
    /**
     * The {@link Set} of nodeKeys that this authenticator has tried the stored username/password
     * pair for. This is to make sure that the user is asked to reenter username/password instead of
     * reusing the old invalid username/password.
     */
    private Set<String> triedStoredForNodeKey = new HashSet<String>();

    protected PasswordAuthentication getPasswordAuthentication() {
        final String[] name=new String[1];
        final String[] pass=new String[1];
        
        // only try the stored username/password once before asking the user
        // for a new username/password.
        if (!isTriedStored()) {
            name[0] = loadName();
            pass[0] = loadPassword();
            setTriedStored(true);
        }
        
        if (name[0] == null && pass[0] == null) {
            //TODO check if credentials have been previously entered and remembered
            PlatformGIS.syncInDisplayThread(new Runnable(){
                public void run() {
                    promptForPassword();
                    name[0]=username;
                    pass[0]=password;
                }
            });
        }
        if (name[0] == null && pass[0] == null) {
            return null;
        }
        if( storePassword )
            store(name[0], pass[0]);
        return new PasswordAuthentication(name[0], pass[0].toCharArray());
    }
    
    private boolean isTriedStored() {
        try {
            return triedStoredForNodeKey.contains(getNodeKey());
        } catch (UnsupportedEncodingException e) {
            UiPlugin.log("", e); //$NON-NLS-1$
            return false;
        }
    }
    
    private void setTriedStored(boolean mark) {
        try {
            if(mark) {
                triedStoredForNodeKey.add(getNodeKey());
            } else {
                triedStoredForNodeKey.remove(getNodeKey());
            }
        } catch (UnsupportedEncodingException e) {
            UiPlugin.log("", e); //$NON-NLS-1$
        }
    }
    
    private void store(String name, String pass) {
        try {
            Preferences node = UiPlugin.getUserPreferences().node(getNodeKey());
            node.put(NAME, name);
            node.put(PASSWORD, pass);
        } catch (Exception e) {
            UiPlugin.log("", e); //$NON-NLS-1$
        }
    }

    private String loadPassword() {
        try {
            Preferences node = UiPlugin.getUserPreferences().node(getNodeKey());
            String pass = node.get(PASSWORD, null);
            if( pass == null )
                return null;
            
            return pass;
        } catch (Exception e) {
            UiPlugin.log("", e); //$NON-NLS-1$
            return null;
        }
    }

    private String getNodeKey() throws UnsupportedEncodingException {
        return URL_AUTHENTICATION + URLEncoder.encode(getRequestingURL().toString(), "UTF-8"); //$NON-NLS-1$
    }

    private String loadName() {
        try {
            Preferences node = UiPlugin.getUserPreferences().node(getNodeKey());
            return node.get(NAME, null);
            
        } catch (Exception e) {
            UiPlugin.log("", e); //$NON-NLS-1$
            return null;
        }
    }

    protected void promptForPassword() {
        
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        AuthenticationDialog dialog = new AuthenticationDialog(shell);
        dialog.setBlockOnOpen(true);
        int result = dialog.open();
        if (result == Window.CANCEL) {
            username = null;
            password = null;
            return;
        }
        username = dialog.getUsername();
        if (username == null) {
            username = ""; //$NON-NLS-1$
        }
        password = dialog.getPassword();
        if (password == null) {
            password = ""; //$NON-NLS-1$
        }
        storePassword = dialog.shouldRemember();
    }
}
