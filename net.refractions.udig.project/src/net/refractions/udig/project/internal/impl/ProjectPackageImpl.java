/**
 * <copyright>Hello Jesse </copyright> $Id: ProjectPackageImpl.java 12218 2005-03-15 19:09:57Z
 * jgarnett $
 */
package net.refractions.udig.project.internal.impl;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.util.List;

import net.refractions.udig.catalog.IGeoResource;
import net.refractions.udig.catalog.IResolveChangeListener;
import net.refractions.udig.core.IBlockingAdaptable;
import net.refractions.udig.project.IAbstractContext;
import net.refractions.udig.project.IBlackboard;
import net.refractions.udig.project.IEditManager;
import net.refractions.udig.project.ILayer;
import net.refractions.udig.project.IMap;
import net.refractions.udig.project.IProject;
import net.refractions.udig.project.IProjectElement;
import net.refractions.udig.project.ISpreadsheet;
import net.refractions.udig.project.IRubyFile;
import net.refractions.udig.project.IRubyProject;
import net.refractions.udig.project.IRubyProjectElement;
import net.refractions.udig.project.command.CommandStack;
import net.refractions.udig.project.command.EditCommand;
import net.refractions.udig.project.command.MapCommand;
import net.refractions.udig.project.command.NavCommand;
import net.refractions.udig.project.command.NavCommandStack;
import net.refractions.udig.project.internal.AbstractContext;
import net.refractions.udig.project.internal.Blackboard;
import net.refractions.udig.project.internal.BlackboardEntry;
import net.refractions.udig.project.internal.CatalogRef;
import net.refractions.udig.project.internal.ContextModel;
import net.refractions.udig.project.internal.EditManager;
import net.refractions.udig.project.internal.Layer;
import net.refractions.udig.project.internal.LayerFactory;
import net.refractions.udig.project.internal.Map;
import net.refractions.udig.project.internal.PicoBlackboard;
import net.refractions.udig.project.internal.Project;
import net.refractions.udig.project.internal.ProjectElement;
import net.refractions.udig.project.internal.ProjectFactory;
import net.refractions.udig.project.internal.ProjectPackage;
import net.refractions.udig.project.internal.ProjectRegistry;
import net.refractions.udig.project.internal.Spreadsheet;
import net.refractions.udig.project.internal.RubyFile;
import net.refractions.udig.project.internal.RubyProject;
import net.refractions.udig.project.internal.RubyProjectElement;
import net.refractions.udig.project.internal.SpreadsheetType;
import net.refractions.udig.project.internal.StyleBlackboard;
import net.refractions.udig.project.internal.StyleEntry;
import net.refractions.udig.project.internal.render.RenderPackage;
import net.refractions.udig.project.internal.render.impl.RenderPackageImpl;
import net.refractions.udig.project.render.IRenderManager;
import net.refractions.udig.project.render.IViewportModel;
import net.refractions.udig.project.render.displayAdapter.IMapDisplay;
import net.refractions.udig.ui.palette.ColourScheme;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.jface.resource.ImageDescriptor;
import org.geotools.brewer.color.BrewerPalette;
import org.geotools.data.FeatureEvent;
import org.geotools.data.FeatureResults;
import org.geotools.data.Query;
import org.geotools.feature.Feature;
import org.geotools.filter.Filter;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
import org.picocontainer.MutablePicoContainer;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Envelope;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package </b>. <!-- end-user-doc -->
 * @generated
 */
public class ProjectPackageImpl extends EPackageImpl implements ProjectPackage {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "uDig - User Friendly Desktop Internet GIS client http://udig.refractions.net (C) 2004, Refractions Research Inc. This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; version 2.1 of the License. This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details."; //$NON-NLS-1$

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass comparableEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass iMapEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass iLayerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass iEditManagerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass iProjectEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass iAbstractContextEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass iBlackboardEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass iProjectElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass iRenderManagerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass iViewportModelEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass abstractContextEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass contextModelEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass editManagerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass layerEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass mapEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass projectEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass projectElementEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass projectRegistryEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass styleBlackboardEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass styleEntryEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass cloneableEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass layerFactoryEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass iAdaptableEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass iBlockingAdaptableEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass picoBlackboardEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass blackboardEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass blackboardEntryEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass iResolveChangeListenerEClass = null;
    
    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iRubyProjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iRubyProjectElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iSpreadsheetEClass = null;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iRubyFileEClass = null;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rubyProjectEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rubyProjectElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass spreadsheetEClass = null;
	
	/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EDataType spreadsheetTypeEDataType = null;
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rubyFileEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType coordinateEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType mapDisplayEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType featureResultsEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType listEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType affineTransformEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType navCommandStackEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType iGeoResourceEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType filterEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType coordinateReferenceSystemEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType commandStackEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType featureEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType pointEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType adapterEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType iProgressMonitorEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType queryEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType urlEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType imageDescriptorEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType editCommandEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType navCommandEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType envelopeEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType editManagerControlCommandEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType commandEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType uriEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType catalogRefEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType colourPaletteEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType colourSchemeEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType defaultColorEDataType;
    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType mutablePicoContainerEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType referencedEnvelopeEDataType = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EDataType featureEventEDataType = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see net.refractions.udig.project.internal.ProjectPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private ProjectPackageImpl() {
        super(eNS_URI, ProjectFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this
     * model, and for any others upon which it depends.  Simple
     * dependencies are satisfied by calling this method on all
     * dependent packages before doing anything else.  This method drives
     * initialization for interdependent packages directly, in parallel
     * with this package, itself.
     * <p>Of this package and its interdependencies, all packages which
     * have not yet been registered by their URI values are first created
     * and registered.  The packages are then initialized in two steps:
     * meta-model objects for all of the packages are created before any
     * are initialized, since one package's meta-model objects may refer to
     * those of another.
     * <p>Invocation of this method will not affect any packages that have
     * already been initialized.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static ProjectPackage init() {
        if (isInited)
            return (ProjectPackage) EPackage.Registry.INSTANCE.getEPackage(ProjectPackage.eNS_URI);

        // Obtain or create and register package
        ProjectPackageImpl theProjectPackage = (ProjectPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(eNS_URI) instanceof ProjectPackageImpl ? EPackage.Registry.INSTANCE
                .getEPackage(eNS_URI) : new ProjectPackageImpl());

        isInited = true;

        // Obtain or create and register interdependencies
        RenderPackageImpl theRenderPackage = (RenderPackageImpl) (EPackage.Registry.INSTANCE
                .getEPackage(RenderPackage.eNS_URI) instanceof RenderPackageImpl
                ? EPackage.Registry.INSTANCE.getEPackage(RenderPackage.eNS_URI)
                : RenderPackage.eINSTANCE);

        // Create package meta-data objects
        theProjectPackage.createPackageContents();
        theRenderPackage.createPackageContents();

        // Initialize created meta-data
        theProjectPackage.initializePackageContents();
        theRenderPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theProjectPackage.freeze();

        return theProjectPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getComparable() {
        return comparableEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getIMap() {
        return iMapEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getILayer() {
        return iLayerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getIEditManager() {
        return iEditManagerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getIProject() {
        return iProjectEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getIAbstractContext() {
        return iAbstractContextEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getIBlackboard() {
        return iBlackboardEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getIProjectElement() {
        return iProjectElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getIRenderManager() {
        return iRenderManagerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getIViewportModel() {
        return iViewportModelEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getAbstractContext() {
        return abstractContextEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getAbstractContext_RenderManagerInternal() {
        return (EReference) abstractContextEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getAbstractContext_MapInternal() {
        return (EReference) abstractContextEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getContextModel() {
        return contextModelEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getContextModel_Layers() {
        return (EReference) contextModelEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getContextModel_Map() {
        return (EReference) contextModelEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getEditManager() {
        return editManagerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEditManager_EditFeature() {
        return (EAttribute) editManagerEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getEditManager_MapInternal() {
        return (EReference) editManagerEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getEditManager_EditLayerInternal() {
        return (EReference) editManagerEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEditManager_TransactionType() {
        return (EAttribute) editManagerEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getEditManager_EditLayerLocked() {
        return (EAttribute) editManagerEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getEditManager_SelectedLayer() {
        return (EReference) editManagerEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getLayer() {
        return layerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getLayer_ContextModel() {
        return (EReference) layerEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLayer_Filter() {
        return (EAttribute) layerEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getLayer_StyleBlackboard() {
        return (EReference) layerEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLayer_Zorder() {
        return (EAttribute) layerEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLayer_Status() {
        return (EAttribute) layerEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLayer_Selectable() {
        return (EAttribute) layerEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLayer_Name() {
        return (EAttribute) layerEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLayer_CatalogRef() {
        return (EAttribute) layerEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLayer_ID() {
        return (EAttribute) layerEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLayer_Visible() {
        return (EAttribute) layerEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLayer_GeoResource() {
        return (EAttribute) layerEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLayer_GeoResources() {
        return (EAttribute) layerEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLayer_Glyph() {
        return (EAttribute) layerEClass.getEStructuralFeatures().get(12);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLayer_CRS() {
        return (EAttribute) layerEClass.getEStructuralFeatures().get(13);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getLayer_Properties() {
        return (EReference) layerEClass.getEStructuralFeatures().get(14);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLayer_ColourScheme() {
        return (EAttribute) layerEClass.getEStructuralFeatures().get(15);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLayer_DefaultColor() {
        return (EAttribute) layerEClass.getEStructuralFeatures().get(16);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getLayer_FeatureChanges() {
        return (EAttribute) layerEClass.getEStructuralFeatures().get(17);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getMap() {
        return mapEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMap_ContextModel() {
        return (EReference) mapEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMap_Abstract() {
        return (EAttribute) mapEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMap_NavCommandStack() {
        return (EAttribute) mapEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMap_CommandStack() {
        return (EAttribute) mapEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMap_LayerFactory() {
        return (EReference) mapEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMap_ViewportModelInternal() {
        return (EReference) mapEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMap_ColorPalette() {
        return (EAttribute) mapEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMap_EditManagerInternal() {
        return (EReference) mapEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMap_RenderManagerInternal() {
        return (EReference) mapEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getMap_ColourScheme() {
        return (EAttribute) mapEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getMap_BlackBoardInternal() {
        return (EReference) mapEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getProject() {
        return projectEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProject_Name() {
        return (EAttribute) projectEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProject_ElementsInternal() {
        return (EReference) projectEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getProjectElement() {
        return projectElementEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getProjectElement_Name() {
        return (EAttribute) projectElementEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProjectElement_ProjectInternal() {
        return (EReference) projectElementEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getProjectRegistry() {
        return projectRegistryEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProjectRegistry_CurrentProject() {
        return (EReference) projectRegistryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getProjectRegistry_Projects() {
        return (EReference) projectRegistryEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getStyleBlackboard() {
        return styleBlackboardEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getStyleBlackboard_Content() {
        return (EReference) styleBlackboardEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getStyleEntry() {
        return styleEntryEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStyleEntry_ID() {
        return (EAttribute) styleEntryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStyleEntry_Memento() {
        return (EAttribute) styleEntryEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStyleEntry_Style() {
        return (EAttribute) styleEntryEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getStyleEntry_StyleClass() {
        return (EAttribute) styleEntryEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getCloneable() {
        return cloneableEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getLayerFactory() {
        return layerFactoryEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getLayerFactory_Map() {
        return (EReference) layerFactoryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getIAdaptable() {
        return iAdaptableEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getIBlockingAdaptable() {
        return iBlockingAdaptableEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getPicoBlackboard() {
        return picoBlackboardEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getPicoBlackboard_PicoContainer() {
        return (EAttribute) picoBlackboardEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getBlackboard() {
        return blackboardEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getBlackboard_Entries() {
        return (EReference) blackboardEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getBlackboardEntry() {
        return blackboardEntryEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBlackboardEntry_Key() {
        return (EAttribute) blackboardEntryEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBlackboardEntry_Memento() {
        return (EAttribute) blackboardEntryEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBlackboardEntry_ObjectClass() {
        return (EAttribute) blackboardEntryEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getBlackboardEntry_Object() {
        return (EAttribute) blackboardEntryEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getIResolveChangeListener() {
        return iResolveChangeListenerEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getCoordinate() {
        return coordinateEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getMapDisplay() {
        return mapDisplayEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getFeatureResults() {
        return featureResultsEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getList() {
        return listEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAffineTransform() {
        return affineTransformEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getNavCommandStack() {
        return navCommandStackEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getIGeoResource() {
        return iGeoResourceEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getFilter() {
        return filterEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getCoordinateReferenceSystem() {
        return coordinateReferenceSystemEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getCommandStack() {
        return commandStackEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getFeature() {
        return featureEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getPoint() {
        return pointEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getAdapter() {
        return adapterEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getIProgressMonitor() {
        return iProgressMonitorEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getQuery() {
        return queryEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getURL() {
        return urlEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getImageDescriptor() {
        return imageDescriptorEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getEditCommand() {
        return editCommandEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getNavCommand() {
        return navCommandEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getEnvelope() {
        return envelopeEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getEditManagerControlCommand() {
        return editManagerControlCommandEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getCommand() {
        return commandEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getURI() {
        return uriEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getCatalogRef() {
        return catalogRefEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getColourPalette() {
        return colourPaletteEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getColourScheme() {
        return colourSchemeEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getMutablePicoContainer() {
        return mutablePicoContainerEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getReferencedEnvelope() {
        return referencedEnvelopeEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EDataType getFeatureEvent() {
        return featureEventEDataType;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ProjectFactory getProjectFactory() {
        return (ProjectFactory) getEFactoryInstance();
    }
    
    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRubyFile() {
		return rubyFileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSpreadsheet() {
		return spreadsheetEClass;
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSpreadsheet_SpreadsheetPath() {
		return (EAttribute) spreadsheetEClass.getEStructuralFeatures().get(0);
	}
	
	/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getSpreadsheet_SpreadsheetType() {
        return (EAttribute) spreadsheetEClass.getEStructuralFeatures().get(1);
    }
    
    /**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSpreadsheet_ChildSpreadsheets() {
		return (EReference) spreadsheetEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getSpreadsheet_ParentSpreadsheet() {
		return (EReference) spreadsheetEClass.getEStructuralFeatures().get(3);
	}
	
	/**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EDataType getSpreadsheetType() {
        return spreadsheetTypeEDataType;
    }

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRubyProjectElement() {
		return rubyProjectElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRubyProjectElement_RubyProjectInternal() {
		return (EReference) rubyProjectElementEClass.getEStructuralFeatures()
				.get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRubyProject() {
		return rubyProjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRubyProject_RubyElementsInternal() {
		return (EReference) rubyProjectEClass.getEStructuralFeatures().get(0);
	}
	
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getISpreadsheet() {
		return iSpreadsheetEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIRubyFile() {
		return iRubyFileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIRubyProject() {
		return iRubyProjectEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIRubyProjectElement() {
		return iRubyProjectElementEClass;
	}

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;


    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated)
            return;
        isCreated = true;

        // Create classes and their features
        comparableEClass = createEClass(COMPARABLE);

        iMapEClass = createEClass(IMAP);

        iLayerEClass = createEClass(ILAYER);

        iEditManagerEClass = createEClass(IEDIT_MANAGER);

        iProjectEClass = createEClass(IPROJECT);

        iAbstractContextEClass = createEClass(IABSTRACT_CONTEXT);

        iBlackboardEClass = createEClass(IBLACKBOARD);

        iProjectElementEClass = createEClass(IPROJECT_ELEMENT);

        iRenderManagerEClass = createEClass(IRENDER_MANAGER);

        iViewportModelEClass = createEClass(IVIEWPORT_MODEL);
        
        iRubyProjectEClass = createEClass(IRUBY_PROJECT);

		iRubyProjectElementEClass = createEClass(IRUBY_PROJECT_ELEMENT);

		iSpreadsheetEClass = createEClass(ISPREADSHEET);

		iRubyFileEClass = createEClass(IRUBY_FILE);

        abstractContextEClass = createEClass(ABSTRACT_CONTEXT);
        createEReference(abstractContextEClass, ABSTRACT_CONTEXT__RENDER_MANAGER_INTERNAL);
        createEReference(abstractContextEClass, ABSTRACT_CONTEXT__MAP_INTERNAL);

        contextModelEClass = createEClass(CONTEXT_MODEL);
        createEReference(contextModelEClass, CONTEXT_MODEL__LAYERS);
        createEReference(contextModelEClass, CONTEXT_MODEL__MAP);

        editManagerEClass = createEClass(EDIT_MANAGER);
        createEAttribute(editManagerEClass, EDIT_MANAGER__EDIT_FEATURE);
        createEReference(editManagerEClass, EDIT_MANAGER__MAP_INTERNAL);
        createEReference(editManagerEClass, EDIT_MANAGER__EDIT_LAYER_INTERNAL);
        createEAttribute(editManagerEClass, EDIT_MANAGER__TRANSACTION_TYPE);
        createEAttribute(editManagerEClass, EDIT_MANAGER__EDIT_LAYER_LOCKED);
        createEReference(editManagerEClass, EDIT_MANAGER__SELECTED_LAYER);

        layerEClass = createEClass(LAYER);
        createEReference(layerEClass, LAYER__CONTEXT_MODEL);
        createEAttribute(layerEClass, LAYER__FILTER);
        createEReference(layerEClass, LAYER__STYLE_BLACKBOARD);
        createEAttribute(layerEClass, LAYER__ZORDER);
        createEAttribute(layerEClass, LAYER__STATUS);
        createEAttribute(layerEClass, LAYER__SELECTABLE);
        createEAttribute(layerEClass, LAYER__NAME);
        createEAttribute(layerEClass, LAYER__CATALOG_REF);
        createEAttribute(layerEClass, LAYER__ID);
        createEAttribute(layerEClass, LAYER__VISIBLE);
        createEAttribute(layerEClass, LAYER__GEO_RESOURCE);
        createEAttribute(layerEClass, LAYER__GEO_RESOURCES);
        createEAttribute(layerEClass, LAYER__GLYPH);
        createEAttribute(layerEClass, LAYER__CRS);
        createEReference(layerEClass, LAYER__PROPERTIES);
        createEAttribute(layerEClass, LAYER__COLOUR_SCHEME);
        createEAttribute(layerEClass, LAYER__DEFAULT_COLOR);
        createEAttribute(layerEClass, LAYER__FEATURE_CHANGES);

        mapEClass = createEClass(MAP);
        createEReference(mapEClass, MAP__CONTEXT_MODEL);
        createEAttribute(mapEClass, MAP__ABSTRACT);
        createEAttribute(mapEClass, MAP__NAV_COMMAND_STACK);
        createEAttribute(mapEClass, MAP__COMMAND_STACK);
        createEReference(mapEClass, MAP__LAYER_FACTORY);
        createEReference(mapEClass, MAP__VIEWPORT_MODEL_INTERNAL);
        createEAttribute(mapEClass, MAP__COLOR_PALETTE);
        createEReference(mapEClass, MAP__EDIT_MANAGER_INTERNAL);
        createEReference(mapEClass, MAP__RENDER_MANAGER_INTERNAL);
        createEAttribute(mapEClass, MAP__COLOUR_SCHEME);
        createEReference(mapEClass, MAP__BLACK_BOARD_INTERNAL);
        
        rubyProjectEClass = createEClass(RUBY_PROJECT);
		createEReference(rubyProjectEClass, RUBY_PROJECT__RUBY_ELEMENTS_INTERNAL);

		rubyProjectElementEClass = createEClass(RUBY_PROJECT_ELEMENT);
		createEReference(rubyProjectElementEClass, RUBY_PROJECT_ELEMENT__RUBY_PROJECT_INTERNAL);

		rubyFileEClass = createEClass(RUBY_FILE);

		spreadsheetEClass = createEClass(SPREADSHEET);
		createEAttribute(spreadsheetEClass, SPREADSHEET__SPREADSHEET_PATH);
		createEAttribute(spreadsheetEClass, SPREADSHEET__SPREADSHEET_TYPE);
		createEReference(spreadsheetEClass, SPREADSHEET__CHILD_SPREADSHEETS);
		createEReference(spreadsheetEClass, SPREADSHEET__PARENT_SPREADSHEET);

        projectEClass = createEClass(PROJECT);
        createEAttribute(projectEClass, PROJECT__NAME);
        createEReference(projectEClass, PROJECT__ELEMENTS_INTERNAL);

        projectElementEClass = createEClass(PROJECT_ELEMENT);
        createEAttribute(projectElementEClass, PROJECT_ELEMENT__NAME);
        createEReference(projectElementEClass, PROJECT_ELEMENT__PROJECT_INTERNAL);

        projectRegistryEClass = createEClass(PROJECT_REGISTRY);
        createEReference(projectRegistryEClass, PROJECT_REGISTRY__CURRENT_PROJECT);
        createEReference(projectRegistryEClass, PROJECT_REGISTRY__PROJECTS);        
        
        styleBlackboardEClass = createEClass(STYLE_BLACKBOARD);
        createEReference(styleBlackboardEClass, STYLE_BLACKBOARD__CONTENT);

        styleEntryEClass = createEClass(STYLE_ENTRY);
        createEAttribute(styleEntryEClass, STYLE_ENTRY__ID);
        createEAttribute(styleEntryEClass, STYLE_ENTRY__MEMENTO);
        createEAttribute(styleEntryEClass, STYLE_ENTRY__STYLE);
        createEAttribute(styleEntryEClass, STYLE_ENTRY__STYLE_CLASS);

        cloneableEClass = createEClass(CLONEABLE);

        layerFactoryEClass = createEClass(LAYER_FACTORY);
        createEReference(layerFactoryEClass, LAYER_FACTORY__MAP);

        iAdaptableEClass = createEClass(IADAPTABLE);

        iBlockingAdaptableEClass = createEClass(IBLOCKING_ADAPTABLE);

        picoBlackboardEClass = createEClass(PICO_BLACKBOARD);
        createEAttribute(picoBlackboardEClass, PICO_BLACKBOARD__PICO_CONTAINER);

        blackboardEClass = createEClass(BLACKBOARD);
        createEReference(blackboardEClass, BLACKBOARD__ENTRIES);

        blackboardEntryEClass = createEClass(BLACKBOARD_ENTRY);
        createEAttribute(blackboardEntryEClass, BLACKBOARD_ENTRY__KEY);
        createEAttribute(blackboardEntryEClass, BLACKBOARD_ENTRY__MEMENTO);
        createEAttribute(blackboardEntryEClass, BLACKBOARD_ENTRY__OBJECT_CLASS);
        createEAttribute(blackboardEntryEClass, BLACKBOARD_ENTRY__OBJECT);

        iResolveChangeListenerEClass = createEClass(IRESOLVE_CHANGE_LISTENER);

        // Create data types
        coordinateEDataType = createEDataType(COORDINATE);
        mapDisplayEDataType = createEDataType(MAP_DISPLAY);
        featureResultsEDataType = createEDataType(FEATURE_RESULTS);
        listEDataType = createEDataType(LIST);
        affineTransformEDataType = createEDataType(AFFINE_TRANSFORM);
        navCommandStackEDataType = createEDataType(NAV_COMMAND_STACK);
        iGeoResourceEDataType = createEDataType(IGEO_RESOURCE);
        filterEDataType = createEDataType(FILTER);
        coordinateReferenceSystemEDataType = createEDataType(COORDINATE_REFERENCE_SYSTEM);
        commandStackEDataType = createEDataType(COMMAND_STACK);
        featureEDataType = createEDataType(FEATURE);
        pointEDataType = createEDataType(POINT);
        adapterEDataType = createEDataType(ADAPTER);
        iProgressMonitorEDataType = createEDataType(IPROGRESS_MONITOR);
        queryEDataType = createEDataType(QUERY);
        urlEDataType = createEDataType(URL);
        imageDescriptorEDataType = createEDataType(IMAGE_DESCRIPTOR);
        editCommandEDataType = createEDataType(EDIT_COMMAND);
        navCommandEDataType = createEDataType(NAV_COMMAND);
        envelopeEDataType = createEDataType(ENVELOPE);
        editManagerControlCommandEDataType = createEDataType(EDIT_MANAGER_CONTROL_COMMAND);
        commandEDataType = createEDataType(COMMAND);
        uriEDataType = createEDataType(URI);
        catalogRefEDataType = createEDataType(CATALOG_REF);
        colourPaletteEDataType = createEDataType(COLOUR_PALETTE);
        colourSchemeEDataType = createEDataType(COLOUR_SCHEME);
        defaultColorEDataType = createEDataType(DEFAULT_COLOR);
        mutablePicoContainerEDataType = createEDataType(MUTABLE_PICO_CONTAINER);
        referencedEnvelopeEDataType = createEDataType(REFERENCED_ENVELOPE);
        featureEventEDataType = createEDataType(FEATURE_EVENT);
        spreadsheetTypeEDataType = createEDataType(SPREADSHEET_TYPE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    public void initializePackageContents() {
        if (isInitialized)
            return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        RenderPackageImpl theRenderPackage = (RenderPackageImpl) EPackage.Registry.INSTANCE
                .getEPackage(RenderPackage.eNS_URI);

        // Add supertypes to classes
        abstractContextEClass.getESuperTypes().add(this.getCloneable());
        abstractContextEClass.getESuperTypes().add(this.getIAbstractContext());
        editManagerEClass.getESuperTypes().add(this.getIEditManager());
        layerEClass.getESuperTypes().add(this.getILayer());
        layerEClass.getESuperTypes().add(this.getIAdaptable());
        layerEClass.getESuperTypes().add(this.getIBlockingAdaptable());
        layerEClass.getESuperTypes().add(this.getIResolveChangeListener());
        mapEClass.getESuperTypes().add(this.getProjectElement());
        mapEClass.getESuperTypes().add(this.getIMap());
        projectEClass.getESuperTypes().add(this.getIProject());
        projectElementEClass.getESuperTypes().add(this.getIProjectElement());
        projectElementEClass.getESuperTypes().add(this.getIAdaptable());
        styleBlackboardEClass.getESuperTypes().add(this.getIBlackboard());
        styleBlackboardEClass.getESuperTypes().add(this.getCloneable());
        picoBlackboardEClass.getESuperTypes().add(this.getIBlackboard());
        blackboardEClass.getESuperTypes().add(this.getIBlackboard());        
        rubyProjectEClass.getESuperTypes().add(this.getProjectElement());
		rubyProjectEClass.getESuperTypes().add(this.getIRubyProject());
		rubyFileEClass.getESuperTypes().add(this.getIRubyFile());
		rubyFileEClass.getESuperTypes().add(this.getProjectElement());
		rubyFileEClass.getESuperTypes().add(this.getRubyProjectElement());
		spreadsheetEClass.getESuperTypes().add(this.getISpreadsheet());
		spreadsheetEClass.getESuperTypes().add(this.getRubyProjectElement());
		spreadsheetEClass.getESuperTypes().add(this.getProjectElement());
		rubyProjectElementEClass.getESuperTypes().add(this.getProjectElement());
		rubyProjectElementEClass.getESuperTypes().add(this.getIRubyProjectElement());
		rubyProjectElementEClass.getESuperTypes().add(this.getIAdaptable());		

        // Initialize classes and features; add operations and parameters
        initEClass(comparableEClass, Comparable.class,
                "Comparable", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(iMapEClass, IMap.class,
                "IMap", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        
        initEClass(iRubyProjectEClass, IRubyProject.class,
				"IRubyProject", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(iRubyProjectElementEClass, IRubyProjectElement.class,
				"IRubyProjectElement", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(iSpreadsheetEClass, ISpreadsheet.class,
				"ISpreadsheet", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(iRubyFileEClass, IRubyFile.class,
				"IRubyFile", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(iLayerEClass, ILayer.class,
                "ILayer", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(iEditManagerEClass, IEditManager.class,
                "IEditManager", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(iProjectEClass, IProject.class,
                "IProject", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(iAbstractContextEClass, IAbstractContext.class,
                "IAbstractContext", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(iBlackboardEClass, IBlackboard.class,
                "IBlackboard", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(iProjectElementEClass, IProjectElement.class,
                "IProjectElement", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(iRenderManagerEClass, IRenderManager.class,
                "IRenderManager", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(iViewportModelEClass, IViewportModel.class,
                "IViewportModel", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        
        initEClass(abstractContextEClass, AbstractContext.class,
                "AbstractContext", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(
                getAbstractContext_RenderManagerInternal(),
                theRenderPackage.getRenderManager(),
                null,
                "renderManagerInternal", null, 0, 1, AbstractContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(
                getAbstractContext_MapInternal(),
                this.getMap(),
                null,
                "mapInternal", null, 0, 1, AbstractContext.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(contextModelEClass, ContextModel.class,
                "ContextModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(
                getContextModel_Layers(),
                this.getLayer(),
                this.getLayer_ContextModel(),
                "layers", null, 0, -1, ContextModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(
                getContextModel_Map(),
                this.getMap(),
                this.getMap_ContextModel(),
                "map", null, 0, 1, ContextModel.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        EOperation op = addEOperation(contextModelEClass, null, "lowerLayer"); //$NON-NLS-1$
        addEParameter(op, this.getLayer(), "layer"); //$NON-NLS-1$

        op = addEOperation(contextModelEClass, null, "raiseLayer"); //$NON-NLS-1$
        addEParameter(op, this.getLayer(), "layer"); //$NON-NLS-1$

        initEClass(editManagerEClass, EditManager.class,
                "EditManager", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(
                getEditManager_EditFeature(),
                this.getFeature(),
                "editFeature", null, 0, 1, EditManager.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(
                getEditManager_MapInternal(),
                this.getMap(),
                this.getMap_EditManagerInternal(),
                "mapInternal", null, 0, 1, EditManager.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(
                getEditManager_EditLayerInternal(),
                this.getLayer(),
                null,
                "editLayerInternal", null, 0, 1, EditManager.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getEditManager_TransactionType(),
                ecorePackage.getEJavaClass(),
                "transactionType", null, 0, 1, EditManager.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getEditManager_EditLayerLocked(),
                ecorePackage.getEBoolean(),
                "editLayerLocked", null, 0, 1, EditManager.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(
                getEditManager_SelectedLayer(),
                this.getLayer(),
                null,
                "selectedLayer", null, 0, 1, EditManager.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        op = addEOperation(editManagerEClass, null, "setEditFeature"); //$NON-NLS-1$
        addEParameter(op, this.getFeature(), "value"); //$NON-NLS-1$
        addEParameter(op, this.getLayer(), "layer"); //$NON-NLS-1$

        addEOperation(editManagerEClass, null, "startTransaction"); //$NON-NLS-1$

        addEOperation(editManagerEClass, null, "commitTransaction"); //$NON-NLS-1$

        addEOperation(editManagerEClass, null, "rollbackTransaction"); //$NON-NLS-1$

        initEClass(layerEClass, Layer.class,
                "Layer", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(
                getLayer_ContextModel(),
                this.getContextModel(),
                this.getContextModel_Layers(),
                "contextModel", null, 0, 1, Layer.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getLayer_Filter(),
                this.getFilter(),
                "filter", null, 0, 1, Layer.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(
                getLayer_StyleBlackboard(),
                this.getStyleBlackboard(),
                null,
                "styleBlackboard", null, 0, 1, Layer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getLayer_Zorder(),
                ecorePackage.getEInt(),
                "zorder", null, 0, 1, Layer.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getLayer_Status(),
                ecorePackage.getEInt(),
                "status", "0", 0, 1, Layer.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEAttribute(
                getLayer_Selectable(),
                ecorePackage.getEBoolean(),
                "selectable", "true", 0, 1, Layer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEAttribute(
                getLayer_Name(),
                ecorePackage.getEString(),
                "name", null, 0, 1, Layer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getLayer_CatalogRef(),
                this.getCatalogRef(),
                "catalogRef", null, 0, 1, Layer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getLayer_ID(),
                this.getURL(),
                "iD", null, 0, 1, Layer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getLayer_Visible(),
                ecorePackage.getEBoolean(),
                "visible", null, 0, 1, Layer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getLayer_GeoResource(),
                this.getIGeoResource(),
                "geoResource", null, 0, 1, Layer.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getLayer_GeoResources(),
                this.getIGeoResource(),
                "geoResources", null, 0, -1, Layer.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getLayer_Glyph(),
                this.getImageDescriptor(),
                "glyph", null, 0, 1, Layer.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getLayer_CRS(),
                this.getCoordinateReferenceSystem(),
                "cRS", null, 0, 1, Layer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(
                getLayer_Properties(),
                this.getIBlackboard(),
                null,
                "properties", null, 0, 1, Layer.class, IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getLayer_ColourScheme(),
                this.getColourScheme(),
                "colourScheme", null, 0, 1, Layer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getLayer_DefaultColor(),
                getDefaultColor(),
                "defaultColor", null, 0, 1, Layer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getLayer_FeatureChanges(),
                this.getFeatureEvent(),
                "featureChanges", null, 0, -1, Layer.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        op = addEOperation(layerEClass, this.getQuery(), "getQuery"); //$NON-NLS-1$
        addEParameter(op, ecorePackage.getEBoolean(), "selection"); //$NON-NLS-1$

        op = addEOperation(layerEClass, this.getCoordinateReferenceSystem(), "getCRS"); //$NON-NLS-1$
        addEParameter(op, this.getIProgressMonitor(), "monitor"); //$NON-NLS-1$

        initEClass(mapEClass, Map.class,
                "Map", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(
                getMap_ContextModel(),
                this.getContextModel(),
                this.getContextModel_Map(),
                "contextModel", null, 0, 1, Map.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getMap_Abstract(),
                ecorePackage.getEString(),
                "abstract", null, 0, 1, Map.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getMap_NavCommandStack(),
                this.getNavCommandStack(),
                "navCommandStack", null, 0, 1, Map.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getMap_CommandStack(),
                this.getCommandStack(),
                "commandStack", null, 0, 1, Map.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(
                getMap_LayerFactory(),
                this.getLayerFactory(),
                this.getLayerFactory_Map(),
                "layerFactory", null, 0, 1, Map.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(
                getMap_ViewportModelInternal(),
                theRenderPackage.getViewportModel(),
                theRenderPackage.getViewportModel_MapInternal(),
                "viewportModelInternal", null, 0, 1, Map.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getMap_ColorPalette(),
                this.getColourPalette(),
                "colorPalette", null, 0, 1, Map.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(
                getMap_EditManagerInternal(),
                this.getEditManager(),
                this.getEditManager_MapInternal(),
                "editManagerInternal", null, 0, 1, Map.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(
                getMap_RenderManagerInternal(),
                theRenderPackage.getRenderManager(),
                theRenderPackage.getRenderManager_MapInternal(),
                "renderManagerInternal", null, 0, 1, Map.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getMap_ColourScheme(),
                this.getColourScheme(),
                "colourScheme", null, 0, 1, Map.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(
                getMap_BlackBoardInternal(),
                this.getBlackboard(),
                null,
                "blackBoardInternal", null, 0, 1, Map.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        op = addEOperation(mapEClass, this.getReferencedEnvelope(), "getBounds"); //$NON-NLS-1$
        addEParameter(op, this.getIProgressMonitor(), "monitor"); //$NON-NLS-1$

        op = addEOperation(mapEClass, ecorePackage.getEDouble(), "getAspectRatio"); //$NON-NLS-1$
        addEParameter(op, this.getIProgressMonitor(), "monitor"); //$NON-NLS-1$

        addEOperation(mapEClass, null, "redo"); //$NON-NLS-1$

        addEOperation(mapEClass, null, "undo"); //$NON-NLS-1$

        addEOperation(mapEClass, null, "backwardHistory"); //$NON-NLS-1$

        addEOperation(mapEClass, null, "forewardHistory"); //$NON-NLS-1$

        initEClass(rubyProjectEClass, RubyProject.class,
				"RubyProject", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getRubyProject_RubyElementsInternal(),
				this.getRubyProjectElement(),
				this.getRubyProjectElement_RubyProjectInternal(),
				"rubyElementsInternal", null, 0, -1, RubyProject.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(rubyProjectElementEClass, RubyProjectElement.class,
				"RubyProjectElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEReference(
				getRubyProjectElement_RubyProjectInternal(),
				this.getRubyProject(),
				this.getRubyProject_RubyElementsInternal(),
				"rubyProjectInternal", null, 0, 1, RubyProjectElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

		initEClass(rubyFileEClass, RubyFile.class,
				"RubyFile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

		initEClass(spreadsheetEClass, Spreadsheet.class,
				"Spreadsheet", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
		initEAttribute(
				getSpreadsheet_SpreadsheetPath(),
				this.getURL(),
				"spreadsheetPath", "", 0, 1, Spreadsheet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEAttribute(
		        getSpreadsheet_SpreadsheetType(),
		        this.getSpreadsheetType(),
		        "spreadsheetType", "", 0, 1, Spreadsheet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
		initEReference(
				getSpreadsheet_ChildSpreadsheets(),
				this.getSpreadsheet(),
				this.getSpreadsheet_ParentSpreadsheet(),
				"childSpreadsheets", null, 0, -1, Spreadsheet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		initEReference(
				getSpreadsheet_ParentSpreadsheet(),
				this.getSpreadsheet(),
				this.getSpreadsheet_ChildSpreadsheets(),
				"parentSpreadsheet", null, 0, 1, Spreadsheet.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
		
		initEClass(projectEClass, Project.class,
                "Project", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(
                getProject_Name(),
                ecorePackage.getEString(),
                "name", null, 0, 1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(
                getProject_ElementsInternal(),
                this.getProjectElement(),
                this.getProjectElement_ProjectInternal(),
                "elementsInternal", null, 0, -1, Project.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        
        initEClass(projectElementEClass, ProjectElement.class,
                "ProjectElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(
                getProjectElement_Name(),
                ecorePackage.getEString(),
                "name", null, 0, 1, ProjectElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(
                getProjectElement_ProjectInternal(),
                this.getProject(),
                this.getProject_ElementsInternal(),
                "projectInternal", null, 0, 1, ProjectElement.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        
        initEClass(projectRegistryEClass, ProjectRegistry.class,
                "ProjectRegistry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(
                getProjectRegistry_CurrentProject(),
                this.getProject(),
                null,
                "currentProject", null, 0, 1, ProjectRegistry.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(
                getProjectRegistry_Projects(),
                this.getProject(),
                null,
                "projects", null, 0, -1, ProjectRegistry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        op = addEOperation(projectRegistryEClass, this.getProject(), "getProject"); //$NON-NLS-1$
        addEParameter(op, this.getURI(), "uri"); //$NON-NLS-1$

        op = addEOperation(projectRegistryEClass, this.getProject(), "getProject"); //$NON-NLS-1$
        addEParameter(op, ecorePackage.getEString(), "projectPath"); //$NON-NLS-1$

        initEClass(styleBlackboardEClass, StyleBlackboard.class,
                "StyleBlackboard", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(
                getStyleBlackboard_Content(),
                this.getStyleEntry(),
                null,
                "content", null, 0, -1, StyleBlackboard.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        op = addEOperation(styleBlackboardEClass, ecorePackage.getEJavaObject(), "get"); //$NON-NLS-1$
        addEParameter(op, ecorePackage.getEString(), "styleId"); //$NON-NLS-1$

        op = addEOperation(styleBlackboardEClass, ecorePackage.getEJavaObject(), "lookup"); //$NON-NLS-1$
        addEParameter(op, ecorePackage.getEJavaClass(), "theClass"); //$NON-NLS-1$

        op = addEOperation(styleBlackboardEClass, ecorePackage.getEBoolean(), "contains"); //$NON-NLS-1$
        addEParameter(op, ecorePackage.getEString(), "styleId"); //$NON-NLS-1$

        op = addEOperation(styleBlackboardEClass, null, "put"); //$NON-NLS-1$
        addEParameter(op, ecorePackage.getEString(), "styleId"); //$NON-NLS-1$
        addEParameter(op, ecorePackage.getEJavaObject(), "style"); //$NON-NLS-1$

        op = addEOperation(styleBlackboardEClass, null, "put"); //$NON-NLS-1$
        addEParameter(op, this.getURL(), "url"); //$NON-NLS-1$
        addEParameter(op, this.getIProgressMonitor(), "monitor"); //$NON-NLS-1$

        op = addEOperation(styleBlackboardEClass, ecorePackage.getEJavaObject(), "remove"); //$NON-NLS-1$
        addEParameter(op, ecorePackage.getEString(), "styleId"); //$NON-NLS-1$

        addEOperation(styleBlackboardEClass, ecorePackage.getEJavaObject(), "clone"); //$NON-NLS-1$

        initEClass(styleEntryEClass, StyleEntry.class,
                "StyleEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(
                getStyleEntry_ID(),
                ecorePackage.getEString(),
                "iD", null, 0, 1, StyleEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getStyleEntry_Memento(),
                ecorePackage.getEString(),
                "memento", null, 0, 1, StyleEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getStyleEntry_Style(),
                ecorePackage.getEJavaObject(),
                "style", null, 0, 1, StyleEntry.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getStyleEntry_StyleClass(),
                ecorePackage.getEJavaClass(),
                "styleClass", null, 0, 1, StyleEntry.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(cloneableEClass, Cloneable.class,
                "Cloneable", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(layerFactoryEClass, LayerFactory.class,
                "LayerFactory", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(
                getLayerFactory_Map(),
                this.getMap(),
                this.getMap_LayerFactory(),
                "map", null, 0, 1, LayerFactory.class, IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(iAdaptableEClass, IAdaptable.class,
                "IAdaptable", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(iBlockingAdaptableEClass, IBlockingAdaptable.class,
                "IBlockingAdaptable", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        initEClass(picoBlackboardEClass, PicoBlackboard.class,
                "PicoBlackboard", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(
                getPicoBlackboard_PicoContainer(),
                this.getMutablePicoContainer(),
                "picoContainer", null, 0, 1, PicoBlackboard.class, !IS_TRANSIENT, !IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(blackboardEClass, Blackboard.class,
                "Blackboard", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEReference(
                getBlackboard_Entries(),
                this.getBlackboardEntry(),
                null,
                "entries", null, 0, -1, Blackboard.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(blackboardEntryEClass, BlackboardEntry.class,
                "BlackboardEntry", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(
                getBlackboardEntry_Key(),
                ecorePackage.getEString(),
                "key", null, 0, 1, BlackboardEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getBlackboardEntry_Memento(),
                ecorePackage.getEString(),
                "memento", null, 0, 1, BlackboardEntry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getBlackboardEntry_ObjectClass(),
                ecorePackage.getEJavaClass(),
                "objectClass", null, 0, 1, BlackboardEntry.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(
                getBlackboardEntry_Object(),
                ecorePackage.getEJavaObject(),
                "object", null, 0, 1, BlackboardEntry.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(iResolveChangeListenerEClass, IResolveChangeListener.class,
                "IResolveChangeListener", IS_ABSTRACT, IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        // Initialize data types
        initEDataType(coordinateEDataType, Coordinate.class,
                "Coordinate", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(mapDisplayEDataType, IMapDisplay.class,
                "MapDisplay", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(featureResultsEDataType, FeatureResults.class,
                "FeatureResults", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(listEDataType, List.class,
                "List", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(affineTransformEDataType, AffineTransform.class,
                "AffineTransform", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(navCommandStackEDataType, NavCommandStack.class,
                "NavCommandStack", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(iGeoResourceEDataType, IGeoResource.class,
                "IGeoResource", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(filterEDataType, Filter.class,
                "Filter", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(coordinateReferenceSystemEDataType, CoordinateReferenceSystem.class,
                "CoordinateReferenceSystem", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(commandStackEDataType, CommandStack.class,
                "CommandStack", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(featureEDataType, Feature.class,
                "Feature", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(pointEDataType, Point.class,
                "Point", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(adapterEDataType, Adapter.class,
                "Adapter", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(iProgressMonitorEDataType, IProgressMonitor.class,
                "IProgressMonitor", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(queryEDataType, Query.class,
                "Query", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(urlEDataType, java.net.URL.class,
                "URL", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(imageDescriptorEDataType, ImageDescriptor.class,
                "ImageDescriptor", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(editCommandEDataType, EditCommand.class,
                "EditCommand", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(navCommandEDataType, NavCommand.class,
                "NavCommand", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(envelopeEDataType, Envelope.class,
                "Envelope", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(commandEDataType, MapCommand.class,
                "Command", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(uriEDataType, org.eclipse.emf.common.util.URI.class,
                "URI", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(catalogRefEDataType, CatalogRef.class,
                "CatalogRef", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(colourPaletteEDataType, BrewerPalette.class,
                "ColourPalette", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(colourSchemeEDataType, ColourScheme.class,
                "ColourScheme", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(defaultColorEDataType, Color.class,
                "DefaultColor", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(mutablePicoContainerEDataType, MutablePicoContainer.class,
                "MutablePicoContainer", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(referencedEnvelopeEDataType, ReferencedEnvelope.class,
                "ReferencedEnvelope", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(featureEventEDataType, FeatureEvent.class,
                "FeatureEvent", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEDataType(spreadsheetTypeEDataType, SpreadsheetType.class,
                "SpreadsheetType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$

        // Create resource
        createResource(eNS_URI);
    }

    private EClassifier getDefaultColor() {
        return defaultColorEDataType ;
    }	

} // ProjectPackageImpl
