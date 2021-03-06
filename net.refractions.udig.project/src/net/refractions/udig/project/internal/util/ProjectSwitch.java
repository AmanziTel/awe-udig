/**
 * <copyright></copyright> $Id: ProjectSwitch.java 23326 2006-12-08 02:42:32Z jeichar $
 */
package net.refractions.udig.project.internal.util;

import java.util.List;

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
import net.refractions.udig.project.internal.AbstractContext;
import net.refractions.udig.project.internal.Blackboard;
import net.refractions.udig.project.internal.BlackboardEntry;
import net.refractions.udig.project.internal.ContextModel;
import net.refractions.udig.project.internal.EditManager;
import net.refractions.udig.project.internal.Layer;
import net.refractions.udig.project.internal.LayerFactory;
import net.refractions.udig.project.internal.Map;
import net.refractions.udig.project.internal.PicoBlackboard;
import net.refractions.udig.project.internal.Project;
import net.refractions.udig.project.internal.ProjectElement;
import net.refractions.udig.project.internal.ProjectPackage;
import net.refractions.udig.project.internal.ProjectRegistry;
import net.refractions.udig.project.internal.Spreadsheet;
import net.refractions.udig.project.internal.RubyFile;
import net.refractions.udig.project.internal.RubyProject;
import net.refractions.udig.project.internal.RubyProjectElement;
import net.refractions.udig.project.internal.StyleBlackboard;
import net.refractions.udig.project.internal.StyleEntry;
import net.refractions.udig.project.render.IRenderManager;
import net.refractions.udig.project.render.IViewportModel;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> The <b>Switch </b> for the model's inheritance hierarchy. It supports the
 * call {@link #doSwitch(EObject) doSwitch(object)}to invoke the <code>caseXXX</code> method for
 * each class of the model, starting with the actual class of the object and proceeding up the
 * inheritance hierarchy until a non-null result is returned, which is the result of the switch.
 * <!-- end-user-doc -->
 * @see net.refractions.udig.project.internal.ProjectPackage
 * @generated
 */
public class ProjectSwitch {

    /**
     * The cached model package
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated NOT
     */
    static ProjectPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ProjectSwitch() {
        if (modelPackage == null) {
            modelPackage = ProjectPackage.eINSTANCE;
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    public Object doSwitch( EObject theEObject ) {
        return doSwitch(theEObject.eClass(), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected Object doSwitch( EClass theEClass, EObject theEObject ) {
        if (theEClass.eContainer() == modelPackage) {
            return doSwitch(theEClass.getClassifierID(), theEObject);
        } else {
            List eSuperTypes = theEClass.getESuperTypes();
            return eSuperTypes.isEmpty() ? defaultCase(theEObject) : doSwitch((EClass) eSuperTypes
                    .get(0), theEObject);
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected Object doSwitch( int classifierID, EObject theEObject ) {
        switch( classifierID ) {
        case ProjectPackage.ABSTRACT_CONTEXT: {
            AbstractContext abstractContext = (AbstractContext) theEObject;
            Object result = caseAbstractContext(abstractContext);
            if (result == null)
                result = caseIAbstractContext(abstractContext);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case ProjectPackage.CONTEXT_MODEL: {
            ContextModel contextModel = (ContextModel) theEObject;
            Object result = caseContextModel(contextModel);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case ProjectPackage.EDIT_MANAGER: {
            EditManager editManager = (EditManager) theEObject;
            Object result = caseEditManager(editManager);
            if (result == null)
                result = caseIEditManager(editManager);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case ProjectPackage.LAYER: {
            Layer layer = (Layer) theEObject;
            Object result = caseLayer(layer);
            if (result == null)
                result = caseILayer(layer);
            if (result == null)
                result = caseIAdaptable(layer);
            if (result == null)
                result = caseIBlockingAdaptable(layer);
            if (result == null)
                result = caseIResolveChangeListener(layer);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case ProjectPackage.MAP: {
            Map map = (Map) theEObject;
            Object result = caseMap(map);
            if (result == null)
                result = caseProjectElement(map);
            if (result == null)
                result = caseIMap(map);
            if (result == null)
                result = caseIProjectElement(map);
            if (result == null)
                result = caseIAdaptable(map);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case ProjectPackage.PROJECT: {
            Project project = (Project) theEObject;
            Object result = caseProject(project);
            if (result == null)
                result = caseIProject(project);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case ProjectPackage.PROJECT_ELEMENT: {
            ProjectElement projectElement = (ProjectElement) theEObject;
            Object result = caseProjectElement(projectElement);
            if (result == null)
                result = caseIProjectElement(projectElement);
            if (result == null)
                result = caseIAdaptable(projectElement);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case ProjectPackage.RUBY_PROJECT: {
        	RubyProject rubyProject = (RubyProject) theEObject;
        	Object result = caseRubyProject(rubyProject);
        	if (result == null) {
        		result = caseProjectElement(rubyProject);
        	}
        	if (result == null) {
        		result = caseIRubyProject(rubyProject);
        	}
        	if (result == null) {
        		result = caseIProjectElement(rubyProject);
        	}
        	if (result == null) {
        		result = caseIAdaptable(rubyProject);
        	}
        	if (result == null) {
        		result = defaultCase(theEObject);
        	}
        	return result;
        }
        case ProjectPackage.RUBY_PROJECT_ELEMENT: {
        	RubyProjectElement rubyProjectElement = (RubyProjectElement) theEObject;
        	Object result = caseRubyProjectElement(rubyProjectElement);
        	if (result == null) {
        		result = caseProjectElement(rubyProjectElement);
        	}
        	if (result == null) {
        		result = caseIRubyProjectElement(rubyProjectElement);
        	}
        	if (result == null) {
        		result = caseIProjectElement(rubyProjectElement);
        	}
        	if (result == null) {
        		result = caseIAdaptable(rubyProjectElement);
        	}
        	if (result == null) {
        		result = defaultCase(theEObject);
        	}
        	return result;
        }        
        case ProjectPackage.SPREADSHEET: {
        	Spreadsheet Spreadsheet = (Spreadsheet) theEObject;
        	Object result = caseSpreadsheet(Spreadsheet);
        	if (result == null) {
        		result = caseRubyProjectElement(Spreadsheet);
        	}
        	if (result == null) {
        		result = caseProjectElement(Spreadsheet);
        	}
        	if (result == null) {
        		result = caseISpreadsheet(Spreadsheet);
        	}
        	if (result == null) {
        		result = caseIRubyProjectElement(Spreadsheet);
        	}
        	if (result == null) {
        		result = caseIProjectElement(Spreadsheet);
        	}
        	if (result == null) {
        		result = caseIAdaptable(Spreadsheet);
        	}
        	if (result == null) {
        		result = defaultCase(theEObject);
        	}
        	return result;
        }        
        case ProjectPackage.RUBY_FILE: {
        	RubyFile rubyFile = (RubyFile) theEObject;
        	Object result = caseRubyFile(rubyFile);
        	if (result == null) {
        		result = caseRubyProjectElement(rubyFile);
        	}
        	if (result == null) {
        		result = caseProjectElement(rubyFile);
        	}
        	if (result == null) {
        		result = caseIRubyFile(rubyFile);
        	}
        	if (result == null) {
        		result = caseIRubyProjectElement(rubyFile);
        	}
        	if (result == null) {
        		result = caseIProjectElement(rubyFile);
        	}
        	if (result == null) {
        		result = caseIAdaptable(rubyFile);
        	}
        	if (result == null) {
        		result = defaultCase(theEObject);
        	}
        	return result;
        }        
        case ProjectPackage.PROJECT_REGISTRY: {
            ProjectRegistry projectRegistry = (ProjectRegistry) theEObject;
            Object result = caseProjectRegistry(projectRegistry);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case ProjectPackage.STYLE_BLACKBOARD: {
            StyleBlackboard styleBlackboard = (StyleBlackboard) theEObject;
            Object result = caseStyleBlackboard(styleBlackboard);
            if (result == null)
                result = caseIBlackboard(styleBlackboard);
            if (result == null)
                result = caseCloneable(styleBlackboard);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case ProjectPackage.STYLE_ENTRY: {
            StyleEntry styleEntry = (StyleEntry) theEObject;
            Object result = caseStyleEntry(styleEntry);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case ProjectPackage.LAYER_FACTORY: {
            LayerFactory layerFactory = (LayerFactory) theEObject;
            Object result = caseLayerFactory(layerFactory);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case ProjectPackage.PICO_BLACKBOARD: {
            PicoBlackboard picoBlackboard = (PicoBlackboard) theEObject;
            Object result = casePicoBlackboard(picoBlackboard);
            if (result == null)
                result = caseIBlackboard(picoBlackboard);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case ProjectPackage.BLACKBOARD: {
            Blackboard blackboard = (Blackboard) theEObject;
            Object result = caseBlackboard(blackboard);
            if (result == null)
                result = caseIBlackboard(blackboard);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        case ProjectPackage.BLACKBOARD_ENTRY: {
            BlackboardEntry blackboardEntry = (BlackboardEntry) theEObject;
            Object result = caseBlackboardEntry(blackboardEntry);
            if (result == null)
                result = defaultCase(theEObject);
            return result;
        }
        default:
            return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>Context Model</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>Context Model</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseContextModel( ContextModel object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>Layer</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>Layer</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseLayer( Layer object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>Map</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>Map</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseMap( Map object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>Project</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>Project</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseProject( Project object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>Element</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseProjectElement( ProjectElement object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>Registry</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>Registry</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseProjectRegistry( ProjectRegistry object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>Cloneable</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>Cloneable</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseCloneable( Cloneable object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>Layer Factory</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>Layer Factory</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseLayerFactory( LayerFactory object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>IAdaptable</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>IAdaptable</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseIAdaptable( IAdaptable object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>IBlocking Adaptable</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>IBlocking Adaptable</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseIBlockingAdaptable( IBlockingAdaptable object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>Pico Blackboard</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>Pico Blackboard</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object casePicoBlackboard( PicoBlackboard object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>Blackboard</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>Blackboard</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseBlackboard( Blackboard object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>Blackboard Entry</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>Blackboard Entry</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseBlackboardEntry( BlackboardEntry object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>IResolve Change Listener</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>IResolve Change Listener</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseIResolveChangeListener( IResolveChangeListener object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>Abstract Context</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>Abstract Context</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseAbstractContext( AbstractContext object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>Style Blackboard</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>Style Blackboard</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseStyleBlackboard( StyleBlackboard object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>Style Entry</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>Style Entry</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseStyleEntry( StyleEntry object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>Comparable</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>Comparable</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseComparable( Comparable object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>IMap</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>IMap</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseIMap( IMap object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>ILayer</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>ILayer</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseILayer( ILayer object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>IEdit Manager</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>IEdit Manager</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseIEditManager( IEditManager object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>IProject</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>IProject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseIProject( IProject object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>IAbstract Context</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>IAbstract Context</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseIAbstractContext( IAbstractContext object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>IBlackboard</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>IBlackboard</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseIBlackboard( IBlackboard object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>IProject Element</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>IProject Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseIProjectElement( IProjectElement object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>IRender Manager</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>IRender Manager</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseIRenderManager( IRenderManager object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>IViewport Model</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>IViewport Model</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseIViewportModel( IViewportModel object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>Edit Manager</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>Edit Manager</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public Object caseEditManager( EditManager object ) {
        return null;
    }

    /**
     * Returns the result of interpretting the object as an instance of '<em>EObject</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch, but this is the last case anyway. <!-- end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public Object defaultCase( EObject object ) {
        return null;
    }
    
    /**
     * Returns the result of interpretting the object as an instance of '<em>EObject</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch, but this is the last case anyway. <!-- end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public Object caseIRubyProject( IRubyProject object) {
    	return null;
    }
    
    /**
     * Returns the result of interpretting the object as an instance of '<em>EObject</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch, but this is the last case anyway. <!-- end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public Object caseIRubyProjectElement( IRubyProjectElement object) {
    	return null;
    }       
    
    /**
     * Returns the result of interpretting the object as an instance of '<em>EObject</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch, but this is the last case anyway. <!-- end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public Object caseIRubyFile( IRubyFile object) {
    	return null;
    }
    
    /**
     * Returns the result of interpretting the object as an instance of '<em>EObject</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch, but this is the last case anyway. <!-- end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public Object caseISpreadsheet( ISpreadsheet object) {
    	return null;
    }
    
    /**
     * Returns the result of interpretting the object as an instance of '<em>EObject</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch, but this is the last case anyway. <!-- end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public Object caseRubyProject( RubyProject object) {
    	return null;
    }
    
    /**
     * Returns the result of interpretting the object as an instance of '<em>EObject</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch, but this is the last case anyway. <!-- end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public Object caseRubyProjectElement( RubyProjectElement object) {
    	return null;
    }        
    
    /**
     * Returns the result of interpretting the object as an instance of '<em>EObject</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch, but this is the last case anyway. <!-- end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public Object caseRubyFile( RubyFile object) {
    	return null;
    }
    
    /**
     * Returns the result of interpretting the object as an instance of '<em>EObject</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch, but this is the last case anyway. <!-- end-user-doc -->
     * 
     * @param object the target of the switch.
     * @return the result of interpretting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public Object caseSpreadsheet( Spreadsheet object) {
    	return null;
    }

} // ProjectSwitch
