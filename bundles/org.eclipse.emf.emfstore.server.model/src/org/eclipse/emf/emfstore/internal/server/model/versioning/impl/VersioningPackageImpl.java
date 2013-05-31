/*******************************************************************************
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 ******************************************************************************/
package org.eclipse.emf.emfstore.internal.server.model.versioning.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.emfstore.internal.common.model.ModelPackage;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.AccesscontrolPackage;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.impl.AccesscontrolPackageImpl;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.RolesPackage;
import org.eclipse.emf.emfstore.internal.server.model.accesscontrol.roles.impl.RolesPackageImpl;
import org.eclipse.emf.emfstore.internal.server.model.impl.ModelPackageImpl;
import org.eclipse.emf.emfstore.internal.server.model.url.UrlPackage;
import org.eclipse.emf.emfstore.internal.server.model.url.impl.UrlPackageImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.AncestorVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.BranchVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ChangePackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.DateVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HeadVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryInfo;
import org.eclipse.emf.emfstore.internal.server.model.versioning.HistoryQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.LogMessage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.ModelElementQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PagedUpdateVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PathQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.PrimaryVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.RangeQuery;
import org.eclipse.emf.emfstore.internal.server.model.versioning.TagVersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.Version;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionProperty;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersionSpec;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningFactory;
import org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.events.EventsPackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.events.impl.EventsPackageImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.events.server.ServerPackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.events.server.impl.ServerPackageImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.OperationsPackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.impl.OperationsPackageImpl;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.semantic.SemanticPackage;
import org.eclipse.emf.emfstore.internal.server.model.versioning.operations.semantic.impl.SemanticPackageImpl;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!--
 * end-user-doc -->
 * 
 * @generated
 */
public class VersioningPackageImpl extends EPackageImpl implements VersioningPackage {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass tagVersionSpecEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass dateVersionSpecEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass primaryVersionSpecEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass versionSpecEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass logMessageEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass changePackageEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass historyInfoEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass historyQueryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass rangeQueryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass pathQueryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass modelElementQueryEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass versionEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass headVersionSpecEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass versionPropertyEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass branchVersionSpecEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass branchInfoEClass = null;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass ancestorVersionSpecEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private EClass pagedUpdateVersionSpecEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with {@link org.eclipse.emf.ecore.EPackage.Registry
	 * EPackage.Registry} by the
	 * package package URI value.
	 * <p>
	 * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
	 * performs initialization of the package, or returns the registered package, if one already exists. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.emf.emfstore.internal.server.model.versioning.VersioningPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private VersioningPackageImpl() {
		super(eNS_URI, VersioningFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>
	 * This method is used to initialize {@link VersioningPackage#eINSTANCE} when that field is accessed. Clients should
	 * not invoke it directly. Instead, they should simply access that field to obtain the package. <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static VersioningPackage init() {
		if (isInited)
			return (VersioningPackage) EPackage.Registry.INSTANCE.getEPackage(VersioningPackage.eNS_URI);

		// Obtain or create and register package
		VersioningPackageImpl theVersioningPackage = (VersioningPackageImpl) (EPackage.Registry.INSTANCE.get(eNS_URI) instanceof VersioningPackageImpl ? EPackage.Registry.INSTANCE
			.get(eNS_URI) : new VersioningPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		ModelPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		ModelPackageImpl theModelPackage_1 = (ModelPackageImpl) (EPackage.Registry.INSTANCE
			.getEPackage(org.eclipse.emf.emfstore.internal.server.model.ModelPackage.eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(org.eclipse.emf.emfstore.internal.server.model.ModelPackage.eNS_URI)
			: org.eclipse.emf.emfstore.internal.server.model.ModelPackage.eINSTANCE);
		OperationsPackageImpl theOperationsPackage = (OperationsPackageImpl) (EPackage.Registry.INSTANCE
			.getEPackage(OperationsPackage.eNS_URI) instanceof OperationsPackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(OperationsPackage.eNS_URI) : OperationsPackage.eINSTANCE);
		SemanticPackageImpl theSemanticPackage = (SemanticPackageImpl) (EPackage.Registry.INSTANCE
			.getEPackage(SemanticPackage.eNS_URI) instanceof SemanticPackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(SemanticPackage.eNS_URI) : SemanticPackage.eINSTANCE);
		EventsPackageImpl theEventsPackage = (EventsPackageImpl) (EPackage.Registry.INSTANCE
			.getEPackage(EventsPackage.eNS_URI) instanceof EventsPackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(EventsPackage.eNS_URI) : EventsPackage.eINSTANCE);
		ServerPackageImpl theServerPackage = (ServerPackageImpl) (EPackage.Registry.INSTANCE
			.getEPackage(ServerPackage.eNS_URI) instanceof ServerPackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(ServerPackage.eNS_URI) : ServerPackage.eINSTANCE);
		AccesscontrolPackageImpl theAccesscontrolPackage = (AccesscontrolPackageImpl) (EPackage.Registry.INSTANCE
			.getEPackage(AccesscontrolPackage.eNS_URI) instanceof AccesscontrolPackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(AccesscontrolPackage.eNS_URI) : AccesscontrolPackage.eINSTANCE);
		RolesPackageImpl theRolesPackage = (RolesPackageImpl) (EPackage.Registry.INSTANCE
			.getEPackage(RolesPackage.eNS_URI) instanceof RolesPackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(RolesPackage.eNS_URI) : RolesPackage.eINSTANCE);
		UrlPackageImpl theUrlPackage = (UrlPackageImpl) (EPackage.Registry.INSTANCE.getEPackage(UrlPackage.eNS_URI) instanceof UrlPackageImpl ? EPackage.Registry.INSTANCE
			.getEPackage(UrlPackage.eNS_URI) : UrlPackage.eINSTANCE);

		// Create package meta-data objects
		theVersioningPackage.createPackageContents();
		theModelPackage_1.createPackageContents();
		theOperationsPackage.createPackageContents();
		theSemanticPackage.createPackageContents();
		theEventsPackage.createPackageContents();
		theServerPackage.createPackageContents();
		theAccesscontrolPackage.createPackageContents();
		theRolesPackage.createPackageContents();
		theUrlPackage.createPackageContents();

		// Initialize created meta-data
		theVersioningPackage.initializePackageContents();
		theModelPackage_1.initializePackageContents();
		theOperationsPackage.initializePackageContents();
		theSemanticPackage.initializePackageContents();
		theEventsPackage.initializePackageContents();
		theServerPackage.initializePackageContents();
		theAccesscontrolPackage.initializePackageContents();
		theRolesPackage.initializePackageContents();
		theUrlPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theVersioningPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(VersioningPackage.eNS_URI, theVersioningPackage);
		return theVersioningPackage;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getTagVersionSpec() {
		return tagVersionSpecEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getTagVersionSpec_Name() {
		return (EAttribute) tagVersionSpecEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getDateVersionSpec() {
		return dateVersionSpecEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getDateVersionSpec_Date() {
		return (EAttribute) dateVersionSpecEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getPrimaryVersionSpec() {
		return primaryVersionSpecEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getPrimaryVersionSpec_Identifier() {
		return (EAttribute) primaryVersionSpecEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getPrimaryVersionSpec_ProjectStateChecksum() {
		return (EAttribute) primaryVersionSpecEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getVersionSpec() {
		return versionSpecEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVersionSpec_Branch() {
		return (EAttribute) versionSpecEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getLogMessage() {
		return logMessageEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getLogMessage_Message() {
		return (EAttribute) logMessageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getLogMessage_Date() {
		return (EAttribute) logMessageEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getLogMessage_ClientDate() {
		return (EAttribute) logMessageEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getLogMessage_Author() {
		return (EAttribute) logMessageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getChangePackage() {
		return changePackageEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getChangePackage_Operations() {
		return (EReference) changePackageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getChangePackage_Events() {
		return (EReference) changePackageEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getChangePackage_LogMessage() {
		return (EReference) changePackageEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getChangePackage_VersionProperties() {
		return (EReference) changePackageEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getHistoryInfo() {
		return historyInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getHistoryInfo_PrimarySpec()
	{
		return (EReference) historyInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getHistoryInfo_NextSpec() {
		return (EReference) historyInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getHistoryInfo_PreviousSpec() {
		return (EReference) historyInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getHistoryInfo_MergedFrom() {
		return (EReference) historyInfoEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getHistoryInfo_MergedTo() {
		return (EReference) historyInfoEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getHistoryInfo_LogMessage() {
		return (EReference) historyInfoEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getHistoryInfo_TagSpecs() {
		return (EReference) historyInfoEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getHistoryInfo_VersionProperties() {
		return (EReference) historyInfoEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getHistoryInfo_ChangePackage() {
		return (EReference) historyInfoEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getHistoryQuery() {
		return historyQueryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getHistoryQuery_Source() {
		return (EReference) historyQueryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getHistoryQuery_IncludeChangePackages() {
		return (EAttribute) historyQueryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getHistoryQuery_IncludeAllVersions() {
		return (EAttribute) historyQueryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getRangeQuery() {
		return rangeQueryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRangeQuery_UpperLimit() {
		return (EAttribute) rangeQueryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRangeQuery_LowerLimit() {
		return (EAttribute) rangeQueryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRangeQuery_IncludeIncoming() {
		return (EAttribute) rangeQueryEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getRangeQuery_IncludeOutgoing() {
		return (EAttribute) rangeQueryEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getPathQuery() {
		return pathQueryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getPathQuery_Target() {
		return (EReference) pathQueryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getModelElementQuery() {
		return modelElementQueryEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getModelElementQuery_ModelElements() {
		return (EReference) modelElementQueryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getVersion() {
		return versionEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getVersion_PrimarySpec() {
		return (EReference) versionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getVersion_TagSpecs() {
		return (EReference) versionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getVersion_NextVersion() {
		return (EReference) versionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getVersion_PreviousVersion() {
		return (EReference) versionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getVersion_LogMessage() {
		return (EReference) versionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getVersion_AncestorVersion() {
		return (EReference) versionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getVersion_BranchedVersions() {
		return (EReference) versionEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getVersion_MergedToVersion() {
		return (EReference) versionEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getVersion_MergedFromVersion() {
		return (EReference) versionEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getHeadVersionSpec() {
		return headVersionSpecEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getVersionProperty() {
		return versionPropertyEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVersionProperty_Name() {
		return (EAttribute) versionPropertyEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getVersionProperty_Value() {
		return (EAttribute) versionPropertyEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getBranchVersionSpec() {
		return branchVersionSpecEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getBranchInfo() {
		return branchInfoEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getBranchInfo_Name() {
		return (EAttribute) branchInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getBranchInfo_Head() {
		return (EReference) branchInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getBranchInfo_Source() {
		return (EReference) branchInfoEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getAncestorVersionSpec() {
		return ancestorVersionSpecEClass;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getAncestorVersionSpec_Target() {
		return (EReference) ancestorVersionSpecEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getAncestorVersionSpec_Source() {
		return (EReference) ancestorVersionSpecEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EClass getPagedUpdateVersionSpec()
	{
		return pagedUpdateVersionSpecEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EAttribute getPagedUpdateVersionSpec_MaxChanges()
	{
		return (EAttribute) pagedUpdateVersionSpecEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public EReference getPagedUpdateVersionSpec_BaseVersionSpec()
	{
		return (EReference) pagedUpdateVersionSpecEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public VersioningFactory getVersioningFactory() {
		return (VersioningFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package. This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		tagVersionSpecEClass = createEClass(TAG_VERSION_SPEC);
		createEAttribute(tagVersionSpecEClass, TAG_VERSION_SPEC__NAME);

		dateVersionSpecEClass = createEClass(DATE_VERSION_SPEC);
		createEAttribute(dateVersionSpecEClass, DATE_VERSION_SPEC__DATE);

		primaryVersionSpecEClass = createEClass(PRIMARY_VERSION_SPEC);
		createEAttribute(primaryVersionSpecEClass, PRIMARY_VERSION_SPEC__IDENTIFIER);
		createEAttribute(primaryVersionSpecEClass, PRIMARY_VERSION_SPEC__PROJECT_STATE_CHECKSUM);

		versionSpecEClass = createEClass(VERSION_SPEC);
		createEAttribute(versionSpecEClass, VERSION_SPEC__BRANCH);

		logMessageEClass = createEClass(LOG_MESSAGE);
		createEAttribute(logMessageEClass, LOG_MESSAGE__AUTHOR);
		createEAttribute(logMessageEClass, LOG_MESSAGE__MESSAGE);
		createEAttribute(logMessageEClass, LOG_MESSAGE__DATE);
		createEAttribute(logMessageEClass, LOG_MESSAGE__CLIENT_DATE);

		changePackageEClass = createEClass(CHANGE_PACKAGE);
		createEReference(changePackageEClass, CHANGE_PACKAGE__OPERATIONS);
		createEReference(changePackageEClass, CHANGE_PACKAGE__EVENTS);
		createEReference(changePackageEClass, CHANGE_PACKAGE__LOG_MESSAGE);
		createEReference(changePackageEClass, CHANGE_PACKAGE__VERSION_PROPERTIES);

		historyInfoEClass = createEClass(HISTORY_INFO);
		createEReference(historyInfoEClass, HISTORY_INFO__PRIMARY_SPEC);
		createEReference(historyInfoEClass, HISTORY_INFO__NEXT_SPEC);
		createEReference(historyInfoEClass, HISTORY_INFO__PREVIOUS_SPEC);
		createEReference(historyInfoEClass, HISTORY_INFO__MERGED_FROM);
		createEReference(historyInfoEClass, HISTORY_INFO__MERGED_TO);
		createEReference(historyInfoEClass, HISTORY_INFO__LOG_MESSAGE);
		createEReference(historyInfoEClass, HISTORY_INFO__TAG_SPECS);
		createEReference(historyInfoEClass, HISTORY_INFO__VERSION_PROPERTIES);
		createEReference(historyInfoEClass, HISTORY_INFO__CHANGE_PACKAGE);

		historyQueryEClass = createEClass(HISTORY_QUERY);
		createEReference(historyQueryEClass, HISTORY_QUERY__SOURCE);
		createEAttribute(historyQueryEClass, HISTORY_QUERY__INCLUDE_CHANGE_PACKAGES);
		createEAttribute(historyQueryEClass, HISTORY_QUERY__INCLUDE_ALL_VERSIONS);

		rangeQueryEClass = createEClass(RANGE_QUERY);
		createEAttribute(rangeQueryEClass, RANGE_QUERY__UPPER_LIMIT);
		createEAttribute(rangeQueryEClass, RANGE_QUERY__LOWER_LIMIT);
		createEAttribute(rangeQueryEClass, RANGE_QUERY__INCLUDE_INCOMING);
		createEAttribute(rangeQueryEClass, RANGE_QUERY__INCLUDE_OUTGOING);

		pathQueryEClass = createEClass(PATH_QUERY);
		createEReference(pathQueryEClass, PATH_QUERY__TARGET);

		modelElementQueryEClass = createEClass(MODEL_ELEMENT_QUERY);
		createEReference(modelElementQueryEClass, MODEL_ELEMENT_QUERY__MODEL_ELEMENTS);

		versionEClass = createEClass(VERSION);
		createEReference(versionEClass, VERSION__PRIMARY_SPEC);
		createEReference(versionEClass, VERSION__TAG_SPECS);
		createEReference(versionEClass, VERSION__NEXT_VERSION);
		createEReference(versionEClass, VERSION__PREVIOUS_VERSION);
		createEReference(versionEClass, VERSION__LOG_MESSAGE);
		createEReference(versionEClass, VERSION__ANCESTOR_VERSION);
		createEReference(versionEClass, VERSION__BRANCHED_VERSIONS);
		createEReference(versionEClass, VERSION__MERGED_TO_VERSION);
		createEReference(versionEClass, VERSION__MERGED_FROM_VERSION);

		headVersionSpecEClass = createEClass(HEAD_VERSION_SPEC);

		versionPropertyEClass = createEClass(VERSION_PROPERTY);
		createEAttribute(versionPropertyEClass, VERSION_PROPERTY__NAME);
		createEAttribute(versionPropertyEClass, VERSION_PROPERTY__VALUE);

		branchVersionSpecEClass = createEClass(BRANCH_VERSION_SPEC);

		branchInfoEClass = createEClass(BRANCH_INFO);
		createEAttribute(branchInfoEClass, BRANCH_INFO__NAME);
		createEReference(branchInfoEClass, BRANCH_INFO__HEAD);
		createEReference(branchInfoEClass, BRANCH_INFO__SOURCE);

		ancestorVersionSpecEClass = createEClass(ANCESTOR_VERSION_SPEC);
		createEReference(ancestorVersionSpecEClass, ANCESTOR_VERSION_SPEC__TARGET);
		createEReference(ancestorVersionSpecEClass, ANCESTOR_VERSION_SPEC__SOURCE);

		pagedUpdateVersionSpecEClass = createEClass(PAGED_UPDATE_VERSION_SPEC);
		createEAttribute(pagedUpdateVersionSpecEClass, PAGED_UPDATE_VERSION_SPEC__MAX_CHANGES);
		createEReference(pagedUpdateVersionSpecEClass, PAGED_UPDATE_VERSION_SPEC__BASE_VERSION_SPEC);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model. This
	 * method is guarded to have no affect on any invocation but its first. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		OperationsPackage theOperationsPackage = (OperationsPackage) EPackage.Registry.INSTANCE
			.getEPackage(OperationsPackage.eNS_URI);
		EventsPackage theEventsPackage = (EventsPackage) EPackage.Registry.INSTANCE.getEPackage(EventsPackage.eNS_URI);
		ModelPackage theModelPackage = (ModelPackage) EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Add subpackages
		getESubpackages().add(theOperationsPackage);
		getESubpackages().add(theEventsPackage);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		tagVersionSpecEClass.getESuperTypes().add(this.getVersionSpec());
		dateVersionSpecEClass.getESuperTypes().add(this.getVersionSpec());
		primaryVersionSpecEClass.getESuperTypes().add(this.getVersionSpec());
		rangeQueryEClass.getESuperTypes().add(this.getHistoryQuery());
		pathQueryEClass.getESuperTypes().add(this.getHistoryQuery());
		modelElementQueryEClass.getESuperTypes().add(this.getRangeQuery());
		headVersionSpecEClass.getESuperTypes().add(this.getVersionSpec());
		branchVersionSpecEClass.getESuperTypes().add(this.getVersionSpec());
		ancestorVersionSpecEClass.getESuperTypes().add(this.getVersionSpec());
		pagedUpdateVersionSpecEClass.getESuperTypes().add(this.getVersionSpec());

		// Initialize classes and features; add operations and parameters
		initEClass(tagVersionSpecEClass, TagVersionSpec.class, "TagVersionSpec", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTagVersionSpec_Name(), ecorePackage.getEString(), "name", null, 1, 1, TagVersionSpec.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dateVersionSpecEClass, DateVersionSpec.class, "DateVersionSpec", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDateVersionSpec_Date(), ecorePackage.getEDate(), "date", null, 1, 1, DateVersionSpec.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(primaryVersionSpecEClass, PrimaryVersionSpec.class, "PrimaryVersionSpec", !IS_ABSTRACT,
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPrimaryVersionSpec_Identifier(), ecorePackage.getEInt(), "identifier", null, 1, 1,
			PrimaryVersionSpec.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(getPrimaryVersionSpec_ProjectStateChecksum(), ecorePackage.getELong(), "projectStateChecksum",
			null, 0, 1, PrimaryVersionSpec.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(versionSpecEClass, VersionSpec.class, "VersionSpec", IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVersionSpec_Branch(), ecorePackage.getEString(), "branch", "trunk", 0, 1, VersionSpec.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(logMessageEClass, LogMessage.class, "LogMessage", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLogMessage_Author(), ecorePackage.getEString(), "author", null, 1, 1, LogMessage.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogMessage_Message(), ecorePackage.getEString(), "message", null, 1, 1, LogMessage.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogMessage_Date(), ecorePackage.getEDate(), "date", null, 1, 1, LogMessage.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogMessage_ClientDate(), ecorePackage.getEDate(), "clientDate", null, 0, 1, LogMessage.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(changePackageEClass, ChangePackage.class, "ChangePackage", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(getChangePackage_Operations(), theOperationsPackage.getAbstractOperation(), null, "operations",
			null, 0, -1, ChangePackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangePackage_Events(), theEventsPackage.getEvent(), null, "events", null, 0, -1,
			ChangePackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangePackage_LogMessage(), this.getLogMessage(), null, "logMessage", null, 0, 1,
			ChangePackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getChangePackage_VersionProperties(), this.getVersionProperty(), null, "versionProperties",
			null, 0, -1, ChangePackage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(historyInfoEClass, HistoryInfo.class, "HistoryInfo", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHistoryInfo_PrimarySpec(), this.getPrimaryVersionSpec(), null, "primarySpec", null, 1, 1,
			HistoryInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHistoryInfo_NextSpec(), this.getPrimaryVersionSpec(), null, "nextSpec", null, 1, -1,
			HistoryInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHistoryInfo_PreviousSpec(), this.getPrimaryVersionSpec(), null, "previousSpec", null, 1, 1,
			HistoryInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHistoryInfo_MergedFrom(), this.getPrimaryVersionSpec(), null, "mergedFrom", null, 1, -1,
			HistoryInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHistoryInfo_MergedTo(), this.getPrimaryVersionSpec(), null, "mergedTo", null, 1, -1,
			HistoryInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHistoryInfo_LogMessage(), this.getLogMessage(), null, "logMessage", null, 1, 1,
			HistoryInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHistoryInfo_TagSpecs(), this.getTagVersionSpec(), null, "tagSpecs", null, 0, -1,
			HistoryInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHistoryInfo_VersionProperties(), this.getVersionProperty(), null, "versionProperties", null,
			0, -1, HistoryInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getHistoryInfo_ChangePackage(), this.getChangePackage(), null, "changePackage", null, 0, 1,
			HistoryInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(historyQueryEClass, HistoryQuery.class, "HistoryQuery", IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(getHistoryQuery_Source(), this.getPrimaryVersionSpec(), null, "source", null, 0, 1,
			HistoryQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHistoryQuery_IncludeChangePackages(), ecorePackage.getEBoolean(), "includeChangePackages",
			null, 0, 1, HistoryQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getHistoryQuery_IncludeAllVersions(), ecorePackage.getEBoolean(), "includeAllVersions", null, 0,
			1, HistoryQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);

		initEClass(rangeQueryEClass, RangeQuery.class, "RangeQuery", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRangeQuery_UpperLimit(), ecorePackage.getEInt(), "upperLimit", null, 0, 1, RangeQuery.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRangeQuery_LowerLimit(), ecorePackage.getEInt(), "lowerLimit", null, 0, 1, RangeQuery.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRangeQuery_IncludeIncoming(), ecorePackage.getEBoolean(), "includeIncoming", null, 0, 1,
			RangeQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEAttribute(getRangeQuery_IncludeOutgoing(), ecorePackage.getEBoolean(), "includeOutgoing", null, 0, 1,
			RangeQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);

		initEClass(pathQueryEClass, PathQuery.class, "PathQuery", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPathQuery_Target(), this.getPrimaryVersionSpec(), null, "target", null, 0, 1,
			PathQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(modelElementQueryEClass, ModelElementQuery.class, "ModelElementQuery", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEReference(getModelElementQuery_ModelElements(), theModelPackage.getModelElementId(), null,
			"modelElements", null, 0, -1, ModelElementQuery.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
			IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(versionEClass, Version.class, "Version", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getVersion_PrimarySpec(), this.getPrimaryVersionSpec(), null, "primarySpec", null, 1, 1,
			Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVersion_TagSpecs(), this.getTagVersionSpec(), null, "tagSpecs", null, 0, -1, Version.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEReference(getVersion_NextVersion(), this.getVersion(), this.getVersion_PreviousVersion(), "nextVersion",
			null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVersion_PreviousVersion(), this.getVersion(), this.getVersion_NextVersion(),
			"previousVersion", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVersion_LogMessage(), this.getLogMessage(), null, "logMessage", null, 0, 1, Version.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEReference(getVersion_AncestorVersion(), this.getVersion(), this.getVersion_BranchedVersions(),
			"ancestorVersion", null, 0, 1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVersion_BranchedVersions(), this.getVersion(), this.getVersion_AncestorVersion(),
			"branchedVersions", null, 0, -1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVersion_MergedToVersion(), this.getVersion(), this.getVersion_MergedFromVersion(),
			"mergedToVersion", null, 0, -1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVersion_MergedFromVersion(), this.getVersion(), this.getVersion_MergedToVersion(),
			"mergedFromVersion", null, 0, -1, Version.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE,
			IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(headVersionSpecEClass, HeadVersionSpec.class, "HeadVersionSpec", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);

		initEClass(versionPropertyEClass, VersionProperty.class, "VersionProperty", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVersionProperty_Name(), ecorePackage.getEString(), "name", null, 0, 1, VersionProperty.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVersionProperty_Value(), ecorePackage.getEString(), "value", null, 0, 1,
			VersionProperty.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);

		initEClass(branchVersionSpecEClass, BranchVersionSpec.class, "BranchVersionSpec", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);

		initEClass(branchInfoEClass, BranchInfo.class, "BranchInfo", !IS_ABSTRACT, !IS_INTERFACE,
			IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBranchInfo_Name(), ecorePackage.getEString(), "name", null, 0, 1, BranchInfo.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBranchInfo_Head(), this.getPrimaryVersionSpec(), null, "head", null, 0, 1, BranchInfo.class,
			!IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE,
			!IS_DERIVED, IS_ORDERED);
		initEReference(getBranchInfo_Source(), this.getPrimaryVersionSpec(), null, "source", null, 0, 1,
			BranchInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(ancestorVersionSpecEClass, AncestorVersionSpec.class, "AncestorVersionSpec", !IS_ABSTRACT,
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAncestorVersionSpec_Target(), this.getPrimaryVersionSpec(), null, "target", null, 0, 1,
			AncestorVersionSpec.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAncestorVersionSpec_Source(), this.getPrimaryVersionSpec(), null, "source", null, 0, 1,
			AncestorVersionSpec.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, IS_RESOLVE_PROXIES,
			!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(pagedUpdateVersionSpecEClass, PagedUpdateVersionSpec.class, "PagedUpdateVersionSpec", !IS_ABSTRACT,
			!IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPagedUpdateVersionSpec_MaxChanges(), ecorePackage.getEInt(), "maxChanges", null, 0, 1,
			PagedUpdateVersionSpec.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID,
			IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPagedUpdateVersionSpec_BaseVersionSpec(), this.getPrimaryVersionSpec(), null,
			"baseVersionSpec", null, 0, 1, PagedUpdateVersionSpec.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE,
			IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
	}

} // VersioningPackageImpl