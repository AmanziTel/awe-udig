/* uDig - User Friendly Desktop Internet GIS client
 * http://udig.refractions.net
 * (C) 2004, Refractions Research Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation;
 * version 2.1 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 */
package net.refractions.udig.style.sld.editor;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import net.refractions.udig.catalog.IGeoResource;
import net.refractions.udig.project.internal.SetDefaultStyleProcessor;
import net.refractions.udig.style.internal.StyleLayer;
import net.refractions.udig.style.sld.SLDContent;
import net.refractions.udig.style.sld.internal.Messages;
import net.refractions.udig.ui.graphics.SLDs;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.geotools.data.FeatureSource;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.geotools.styling.StyleFactoryFinder;
import org.geotools.styling.StyledLayerDescriptor;
import org.geotools.styling.visitor.DuplicatorStyleVisitor;
import org.opengis.coverage.grid.GridCoverage;

class StyleEditorButtonListener implements Listener {

    /** StyleEditorButtonListener styleEditorDialog field */
    private final StyleEditorDialog styleEditorDialog;

    /**
     * @param styleEditorDialog
     */
    StyleEditorButtonListener( StyleEditorDialog styleEditorDialog ) {
        this.styleEditorDialog = styleEditorDialog;
    }

    public void handleEvent( Event event ) {
        
        int buttonId = (Integer) event.widget.getData();
        
        switch( buttonId ) {
        case StyleEditorDialog.IMPORT_ID:
            doImport();
            break;
        case StyleEditorDialog.EXPORT_ID:
            doExport();
            break;
        case StyleEditorDialog.DEFAULTS_ID:
            doDefaults();
            break;
        case StyleEditorDialog.APPLY_ID:
            doApply();
            break;
        case StyleEditorDialog.REVERT_ID:
            doRevert();
            break;
        case StyleEditorDialog.OK_ID:
            if( doApply() ){
                this.styleEditorDialog.close();
            }
            break;
        case StyleEditorDialog.CANCEL_ID:
            this.styleEditorDialog.close();
            break;

        default:
            break;
        }
        
    }

    private boolean doApply() {
        if (this.styleEditorDialog.getCurrentPage().performApply()) {
            this.styleEditorDialog.setExitButtonState();
            this.styleEditorDialog.selectedLayer.apply();
            return true;
        }
        return false;
    }
    private void doDefaults() {
            StyleLayer layer = styleEditorDialog.selectedLayer;
            layer.getStyleBlackboard().clear();
            SetDefaultStyleProcessor p = new SetDefaultStyleProcessor(layer.getGeoResource(), layer);
            p.run();
            Style style = (Style) layer.getStyleBlackboard().get(SLDContent.ID);
            StyledLayerDescriptor oldSLD=null;
            if(style!=null){
                oldSLD = this.styleEditorDialog.getSLD();
            }
            this.styleEditorDialog.selectedLayer.apply();
            this.styleEditorDialog.selectedLayer.getMap().getRenderManager().refresh(this.styleEditorDialog.selectedLayer, null);
            
            if( oldSLD!=null ){
                StyledLayerDescriptor newSLD = this.styleEditorDialog.getSLD();
                this.styleEditorDialog.moveListeners(oldSLD, newSLD);

            }
            this.styleEditorDialog.setExitButtonState();
            this.styleEditorDialog.getCurrentPage().refresh();
    }
    private void doRevert() {
        //store the old sld
        StyledLayerDescriptor oldSLD = this.styleEditorDialog.getSLD();
        //return to the blackboard state before we loaded the dialog
        this.styleEditorDialog.selectedLayer.revertAll();
        this.styleEditorDialog.selectedLayer.apply();
        this.styleEditorDialog.selectedLayer.getMap().getRenderManager().refresh(this.styleEditorDialog.selectedLayer, null);
        //move listeners to new sld
        StyledLayerDescriptor newSLD = this.styleEditorDialog.getSLD();
        this.styleEditorDialog.moveListeners(oldSLD, newSLD);
        this.styleEditorDialog.setExitButtonState();
        this.styleEditorDialog.getCurrentPage().refresh();
    }
    
    private void doImport() {
        ImportSLD importe = new ImportSLD();
        StyledLayerDescriptor sld = null;
        File file = importe.promptFile(Display.getDefault(), sld);
        if (file != null) {
            try {
                sld = (StyledLayerDescriptor) importe.importFrom(file, null);
            } catch (Exception e1) {
                MessageBox mb = new MessageBox(this.styleEditorDialog.getShell(), SWT.ICON_ERROR | SWT.OK);
                mb.setMessage(MessageFormat.format(Messages.StyleEditor_import_failed, e1.getLocalizedMessage())); 
                mb.open();
                throw (RuntimeException) new RuntimeException().initCause(e1);
            }
        }
        if (sld != null) {
            Style newStyle = SLDs.getDefaultStyle(sld);
            // TODO: assert there is only 1 style
            this.styleEditorDialog.setStyle(newStyle);
            //refresh the page (there's a new SLD in town)
            this.styleEditorDialog.getCurrentPage().refresh();
        }
    }
    
    private void doExport() {
        Object sld = this.styleEditorDialog.getSLD();
        ExportSLD export = new ExportSLD();
        File file = export.promptFile(Display.getDefault(), sld);
        if (file != null) {
            try {
                export.exportTo(sld, file, null);
            } catch (Exception e1) {
                // TODO Handle Exception
                throw (RuntimeException) new RuntimeException().initCause(e1);
            }
        }
    }
    
}