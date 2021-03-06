/*
 * uDig - User Friendly Desktop Internet GIS client http://udig.refractions.net (C) 2004,
 * Refractions Research Inc. This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation; version 2.1 of the License. This library is distributed in the hope that it will be
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 */
package net.refractions.udig.project.command;

import java.util.List;

import net.refractions.udig.project.ILayer;
import net.refractions.udig.project.internal.commands.selection.BBoxSelectionCommand;
import net.refractions.udig.project.internal.commands.selection.FIDSelectCommand;
import net.refractions.udig.project.internal.commands.selection.NoSelectCommand;
import net.refractions.udig.project.internal.commands.selection.SelectCommand;

import org.geotools.feature.Feature;
import org.geotools.filter.Filter;

import com.vividsolutions.jts.geom.Envelope;

/**
 * A factory which can be used to create all the standard selection commands.
 * 
 * API use
 * 
 * @author jeichar
 * @deprecated
 * @since 0.3
 */
public class SelectionCommandFactory {
	/**
	 * Creates a new SelectionCommandFactory object
	 * 
	 * @return a new SelectionCommandFactory object
	 */
	public static SelectionCommandFactory getInstance() {
		return instance;
	}
	private static final SelectionCommandFactory instance = new SelectionCommandFactory();
    protected SelectionCommandFactory(){
		// no op;
	}

	/**
	 * Creates a new {@linkplain BBoxSelectionCommand}
	 * 
	 * @param bbox A bounding used as the filter, all features intersecting the bbox will be
	 *        considered selected
	 * @param modifiers Options include: BBoxSelectionCommand.ADD, BBoxSelectionCommand.NONE,
	 *        BBoxSelectionCommand.SUBTRACT
	 * @return A new BBoxSelectionCommand. The command should be sent to the
	 *         {@linkplain SelectionManager}to be executed.
	 * @see Envelope
	 * @see MapCommand
	 */
	public MapCommand createBBoxSelectionCommand(Envelope bbox, int modifiers) {
		return new BBoxSelectionCommand(bbox, modifiers);
	}

    /**
     * Creates a new {@linkplain BBoxSelectionCommand}.  
     * Same as createBBoxSelectionCommand(bbox, BBoxSelectionCommand.NONE)
     * 
     * @param bbox A bounding used as the filter, all features intersecting the bbox will be
     *        considered selected
     * @return A new BBoxSelectionCommand. The command should be sent to the
     *         {@linkplain SelectionManager}to be executed.
     * @see Envelope
     * @see MapCommand
     */
    public MapCommand createBBoxSelectionCommand( Envelope boundingBox ) {
        return new BBoxSelectionCommand(boundingBox, BBoxSelectionCommand.NONE);
    }
    

	/**
	 * Creates a {@linkplain NoSelectCommand}
	 * 
	 * @return a {@linkplain NoSelectCommand}object. The command should be sent to the
	 *         {@linkplain SelectionManager}to be executed.
	 * @see MapCommand
	 */
	public MapCommand createNoSelectCommand() {
		return new NoSelectCommand();
	}

    /**
     * Create a MapCommand that sets the layer selection to be a fidfilter.
     * 
     * @return a {@linkplain FIDSelectCommand}
     * @see MapCommand
     */
    public MapCommand createFIDSelectCommand(ILayer layer, String fid) {
        return new FIDSelectCommand(layer, fid);
    }

    /**
     * Create a MapCommand that sets the layer selection to be a fidfilter.
     * 
     * @return a {@linkplain FIDSelectCommand}
     * @see MapCommand
     */
    public MapCommand createFIDSelectCommand(ILayer layer, Feature feature) {
        return new FIDSelectCommand(layer, feature.getID());
    }

    /**
     * Create a MapCommand that sets the layer selection to be the filter.
     * 
     * @return a {@linkplain SelectCommand}
     * @see MapCommand
     */
    public MapCommand createSelectCommand(ILayer layer, Filter filter) {
        return new SelectCommand(layer, filter);
    }

	/**
	 * Create a CompositeCommand
	 * 
	 * @param commands the commands to be executed as a single command
	 * @return a {@linkplain CompositeCommand}
	 * @see MapCommand
	 */
	public MapCommand createCompositeCommand(List<? extends MapCommand> commands) {
		return new CompositeCommand(commands);
	}

	/**
	 * Create a CompositeCommand
	 * 
	 * @param commands the commands to be executed as a single command
	 * @return a {@linkplain CompositeCommand}
	 * @see MapCommand
	 */
	public MapCommand createUndoableCompositeCommand(List<? extends UndoableMapCommand> commands) {
		return new UndoableComposite(commands);
	}

}
