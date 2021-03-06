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
package net.refractions.udig.project.internal.commands.edit;

import net.refractions.udig.project.command.MapCommand;
import net.refractions.udig.project.internal.Messages;

import org.eclipse.core.runtime.IProgressMonitor;

/**
 * A command that rollsback current changes.
 * 
 * @author jgarnett
 * @since 0.6.0
 */
public class RollbackCommand extends AbstractEditCommand {

    /*
     * @see net.refractions.udig.project.command.MapCommand#run()
     */
    public void run( IProgressMonitor monitor ) throws Exception {
        map.getEditManagerInternal().rollbackTransaction();
    }

    /*
     * @see net.refractions.udig.project.command.MapCommand#copy()
     */
    public MapCommand copy() {
        return new RollbackCommand();
    }

    /*
     * @see net.refractions.udig.project.command.MapCommand#getName()
     */
    public String getName() {
        return Messages.RollbackCommand_name; 
    }

}
