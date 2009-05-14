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
package net.refractions.udig.project.ui.commands;

import java.awt.Color;
import java.awt.Point;
import java.awt.Shape;
import java.io.IOException;
import java.util.List;

import net.refractions.udig.project.ILayer;
import net.refractions.udig.project.command.UndoableMapCommand;
import net.refractions.udig.project.render.IViewportModel;
import net.refractions.udig.project.render.displayAdapter.IMapDisplay;
import net.refractions.udig.project.ui.IAnimation;
import net.refractions.udig.project.ui.internal.commands.draw.CompositeDrawCommand;
import net.refractions.udig.project.ui.internal.commands.draw.DrawEditFeatureCommand;
import net.refractions.udig.project.ui.internal.commands.draw.DrawFeatureCommand;
import net.refractions.udig.project.ui.internal.commands.draw.DrawShapeCommand;
import net.refractions.udig.project.ui.internal.commands.draw.StartAnimationCommand;
import net.refractions.udig.project.ui.internal.commands.draw.StopAnimationCommand;
import net.refractions.udig.project.ui.internal.commands.draw.TranslateCommand;
import net.refractions.udig.project.ui.internal.commands.draw.ZoomDrawCommand;
import net.refractions.udig.ui.graphics.ViewportGraphics;

import org.geotools.feature.Feature;
import org.opengis.referencing.crs.CoordinateReferenceSystem;

/**
 * Creates draw commands.
 * 
 * @author jeichar
 * @since 0.3
 */
public class DrawCommandFactory {

    private DrawCommandFactory() {
        // no op
    }
    private static final DrawCommandFactory instance = new DrawCommandFactory();

    /**
     * Creates a new DrawCommandFactory object
     * 
     * @return a new DrawCommandFactory object
     */
    public static DrawCommandFactory getInstance() {
        return instance;
    }

    /**
     * Creates a new {@linkplain DrawShapeCommand}
     * 
     * @param shape The shape to draw
     * @param paint the shape outline color
     * @param lineStyle see {@linkplain ViewportGraphics} for line styles
     * @param lineWidth The width of the shape outline
     * @return a new DrawShapeCommand object
     * @see DrawShapeCommand
     */
    public DrawShapeCommand createDrawShapeCommand( Shape shape, Color paint, int lineStyle,
            int lineWidth ) {
        return new DrawShapeCommand(shape, paint, lineStyle, lineWidth);
    }
    /**
     * Creates a new {@linkplain DrawShapeCommand}
     * 
     * @param shape
     * @param paint
     * @return a new DrawShapeCommand object
     * @see DrawShapeCommand
     */
    public DrawShapeCommand createDrawShapeCommand( Shape shape, Color paint ) {
        return createDrawShapeCommand(shape, paint, -1, 1);
    }
    /**
     * Creates a new {@linkplain DrawShapeCommand}
     * 
     * @param shape
     * @return a new DrawShapeCommand object
     */
    public DrawShapeCommand createDrawShapeCommand( Shape shape ) {
        return createDrawShapeCommand(shape, null, -1, 1);
    }
    /**
     * Creates a new {@linkplain DrawEditFeatureCommand}
     * 
     * @param model The viewport model associated with the viewport that will be rendered to.
     * @return a new DrawFeatureCommand object
     * @see DrawEditFeatureCommand
     */
    public IDrawCommand createEditFeaturesCommand( IViewportModel model ) {
        return new DrawEditFeatureCommand(model);
    }
    /**
     * Creates a new {@linkplain TranslateCommand}
     * 
     * @param offset The amount of translation
     * @return a new TranslateCommand object
     * @see TranslateCommand
     */
    public TranslateCommand createTranslateCommand( Point offset ) {
        return new TranslateCommand(offset);
    }
    /**
     * Creates a new {@linkplain TranslateCommand}
     * 
     * @param x the amount of y translation
     * @param y the amount of y translation
     * @return a new {@linkplain TranslateCommand}
     * @see TranslateCommand
     */
    public TranslateCommand createTranslateCommand( int x, int y ) {
        return new TranslateCommand(x, y);
    }
    /**
     * Creates a new {@linkplain TranslateCommand}
     * 
     * @param x the amount of y translation
     * @param y the amount of y translation
     * @return a new {@linkplain ZoomDrawCommand}
     * @see ZoomDrawCommand
     */
    public ZoomDrawCommand createZoomDrawCommand( int centerx, int centery, double amount ) {
        return new ZoomDrawCommand(centerx, centery, amount);
    }

    /**
     * Creates a new {@linkplain DrawFeatureCommand}
     * 
     * @param feature the feature to draw
     * @param layer the layer that the feature is part of.
     * @param model the ViewportModel that is used to calculate size and position.
     * @return a new {@linkplain DrawFeatureCommand}
     * @see DrawFeatureCommand
     */
    public DrawFeatureCommand createDrawFeatureCommand( Feature feature, ILayer layer ) {
        try {
            return new DrawFeatureCommand(feature, layer);
        } catch (IOException e) {
            return new DrawFeatureCommand(feature);
        }
    }

    /**
     * Creates a new {@linkplain DrawFeatureCommand}
     * 
     * @param feature the feature to draw
     * @param evaluationObject the layer that the feature is part of.
     * @param model the ViewportModel that is used to calculate size and position.
     * @return a new {@linkplain DrawFeatureCommand}
     * @see DrawFeatureCommand
     */
    public DrawFeatureCommand createDrawFeatureCommand( Feature feature ) {
        return new DrawFeatureCommand(feature);
    }

    /**
     * Creates a new {@linkplain DrawFeatureCommand}
     * 
     * @param feature the feature to draw
     * @param evaluationObject the layer that the feature is part of.
     * @return a new {@linkplain DrawFeatureCommand}
     * @see DrawFeatureCommand
     */
    public DrawFeatureCommand createDrawFeatureCommand( Feature feature,
            CoordinateReferenceSystem crs ) {
        return new DrawFeatureCommand(feature, crs);
    }

    
    /**
     * Creates a new {@linkplain StartAnimationCommand}
     * 
     * @return a new {@linkplain StartAnimationCommand}
     * @see StartAnimationCommand
     */
    public UndoableMapCommand createStartAnimationCommand(IMapDisplay display, List<IAnimation> animations ) {
        return new StartAnimationCommand(display, animations);
    }

    /**
     * Creates a new {@linkplain StartAnimationCommand}
     * 
     * @return a new {@linkplain StartAnimationCommand}
     * @see StartAnimationCommand
     */
    public UndoableMapCommand createStopAnimationCommand( IMapDisplay display, List<IAnimation> animations ) {
        return new StopAnimationCommand(display, animations);
    }
    
    /**
     * Creates a new {@linkplain CompositeDrawCommand}
     * 
     * @param commandsArray
     * @return
     */
    public IDrawCommand createCompositeDrawCommand(IDrawCommand[] commandsArray){
    	return new CompositeDrawCommand(commandsArray);
    }
    
    /**
     * Creates a new {@linkplain CompositeDrawCommand}
     * 
     * @param commandsList
     * @return
     */
    public IDrawCommand createCompositeDrawCommand(List<? extends IDrawCommand> commandsList){
    	return new CompositeDrawCommand(commandsList);
    }
    
}