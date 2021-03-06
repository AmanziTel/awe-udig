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
package net.refractions.udig.project.ui.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.refractions.udig.catalog.IGeoResource;
import net.refractions.udig.catalog.URLUtils;
import net.refractions.udig.catalog.ui.export.CatalogExport;
import net.refractions.udig.catalog.ui.export.CatalogExportWizard;
import net.refractions.udig.catalog.ui.export.Data;
import net.refractions.udig.catalog.ui.export.ExportResourceSelectionState;
import net.refractions.udig.catalog.ui.workflow.Workflow;
import net.refractions.udig.catalog.ui.workflow.WorkflowWizard;
import net.refractions.udig.catalog.ui.workflow.WorkflowWizardPageProvider;
import net.refractions.udig.catalog.ui.workflow.Workflow.State;
import net.refractions.udig.project.internal.Layer;
import net.refractions.udig.project.internal.LayerFactory;
import net.refractions.udig.project.internal.StyleBlackboard;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;

public final class MapSaveStrategy extends CatalogExport {
	private final ExportResourceSelectionState state;
	private MapEditor editor;

	public MapSaveStrategy(ExportResourceSelectionState state, MapEditor editor) {
		super(false);
		this.state = state;
		this.editor = editor;

		init();
	}

	@Override
	protected Workflow createWorkflow() {
		final Workflow workflow = new Workflow(new Workflow.State[] { state });

		// create the workflow for the export wizard
		return workflow;
	}

	@Override
	protected WorkflowWizard createWorkflowWizard(Workflow workflow,
			java.util.Map<Class<? extends State>, WorkflowWizardPageProvider> map) {
		CatalogExportWizard catalogExportWizard = new CatalogExportWizard(
				workflow, map) {
			@Override
			protected boolean performFinish(IProgressMonitor monitor) {
				boolean result = super.performFinish(monitor);
				replaceExportedLayers(state);
				try {
					editor.getMap().getEditManagerInternal()
							.commitTransaction();
					editor.setDirty(false);
				} catch (IOException e) {
					ProjectUIPlugin.log("failed committing transaction", e); //$NON-NLS-1$
					MessageDialog.openError(editor.getSite().getShell(), Messages.MapSaveStrategy_error_title, Messages.MapSaveStrategy_error_messages);
				}
				return result;
			}

			private void replaceExportedLayers(
					final ExportResourceSelectionState layerState) {
				List<Data> exportedLayers = layerState.getExportData();
				for (Data data : exportedLayers) {
					Collection<IGeoResource> exported = data
							.getExportedResources();
					replaceLayer(data.getResource(), exported);
				}
			}

			private void replaceLayer(IGeoResource resource,
					Collection<IGeoResource> exported) {
				List<Layer> layers = MapSaveStrategy.this.editor.getMap()
						.getLayersInternal();
				Layer found;
				do {
					found = null;
					for (Layer layer : layers) {
						if (URLUtils.urlEquals(layer.getID(), resource
								.getIdentifier(), false)) {
							found = layer;
							break;
						}
					}

					if (found != null) {
						layers.addAll(layers.indexOf(found), toLayers(found,
								exported));
						layers.remove(found);
					}
				} while (found != null);

			}

			private Collection<Layer> toLayers(Layer found,
					Collection<IGeoResource> exported) {
				LayerFactory layerFactory = MapSaveStrategy.this.editor
						.getMap().getLayerFactory();
				Collection<Layer> newLayers = new ArrayList<Layer>();

				for (IGeoResource exportedResource : exported) {
					try {
						Layer createLayer = layerFactory
								.createLayer(exportedResource);
						StyleBlackboard clone = (StyleBlackboard) found
								.getStyleBlackboard().clone();
						createLayer.setStyleBlackboard(clone);
						newLayers.add(createLayer);
					} catch (IOException e) {
						throw (RuntimeException) new RuntimeException()
								.initCause(e);
					}
				}

				return newLayers;
			}

		};
		catalogExportWizard.setSelectExportedInCatalog(false);
		return catalogExportWizard;
	}
}