/**
 */
package org.eclipse.emf.emfstore.bowling.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Module;
import org.eclipse.emf.emfstore.bowling.TwoInOneModule;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Two In One Module</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.bowling.impl.TwoInOneModuleImpl#getModule1 <em>Module1</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.impl.TwoInOneModuleImpl#getModule2 <em>Module2</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TwoInOneModuleImpl extends ModuleImpl implements TwoInOneModule {
	/**
	 * The cached value of the '{@link #getModule1() <em>Module1</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getModule1()
	 * @generated
	 * @ordered
	 */
	protected Module module1;

	/**
	 * The cached value of the '{@link #getModule2() <em>Module2</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getModule2()
	 * @generated
	 * @ordered
	 */
	protected Module module2;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TwoInOneModuleImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BowlingPackage.Literals.TWO_IN_ONE_MODULE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Module getModule1() {
		if (module1 != null && module1.eIsProxy()) {
			InternalEObject oldModule1 = (InternalEObject) module1;
			module1 = (Module) eResolveProxy(oldModule1);
			if (module1 != oldModule1) {
				InternalEObject newModule1 = (InternalEObject) module1;
				NotificationChain msgs = oldModule1.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- BowlingPackage.TWO_IN_ONE_MODULE__MODULE1, null, null);
				if (newModule1.eInternalContainer() == null) {
					msgs = newModule1.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- BowlingPackage.TWO_IN_ONE_MODULE__MODULE1, null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						BowlingPackage.TWO_IN_ONE_MODULE__MODULE1, oldModule1, module1));
			}
		}
		return module1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Module basicGetModule1() {
		return module1;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetModule1(Module newModule1, NotificationChain msgs) {
		Module oldModule1 = module1;
		module1 = newModule1;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				BowlingPackage.TWO_IN_ONE_MODULE__MODULE1, oldModule1, newModule1);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setModule1(Module newModule1) {
		if (newModule1 != module1) {
			NotificationChain msgs = null;
			if (module1 != null)
				msgs = ((InternalEObject) module1).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- BowlingPackage.TWO_IN_ONE_MODULE__MODULE1, null, msgs);
			if (newModule1 != null)
				msgs = ((InternalEObject) newModule1).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- BowlingPackage.TWO_IN_ONE_MODULE__MODULE1, null, msgs);
			msgs = basicSetModule1(newModule1, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.TWO_IN_ONE_MODULE__MODULE1,
				newModule1, newModule1));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Module getModule2() {
		if (module2 != null && module2.eIsProxy()) {
			InternalEObject oldModule2 = (InternalEObject) module2;
			module2 = (Module) eResolveProxy(oldModule2);
			if (module2 != oldModule2) {
				InternalEObject newModule2 = (InternalEObject) module2;
				NotificationChain msgs = oldModule2.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- BowlingPackage.TWO_IN_ONE_MODULE__MODULE2, null, null);
				if (newModule2.eInternalContainer() == null) {
					msgs = newModule2.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- BowlingPackage.TWO_IN_ONE_MODULE__MODULE2, null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE,
						BowlingPackage.TWO_IN_ONE_MODULE__MODULE2, oldModule2, module2));
			}
		}
		return module2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Module basicGetModule2() {
		return module2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetModule2(Module newModule2, NotificationChain msgs) {
		Module oldModule2 = module2;
		module2 = newModule2;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				BowlingPackage.TWO_IN_ONE_MODULE__MODULE2, oldModule2, newModule2);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setModule2(Module newModule2) {
		if (newModule2 != module2) {
			NotificationChain msgs = null;
			if (module2 != null)
				msgs = ((InternalEObject) module2).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- BowlingPackage.TWO_IN_ONE_MODULE__MODULE2, null, msgs);
			if (newModule2 != null)
				msgs = ((InternalEObject) newModule2).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- BowlingPackage.TWO_IN_ONE_MODULE__MODULE2, null, msgs);
			msgs = basicSetModule2(newModule2, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.TWO_IN_ONE_MODULE__MODULE2,
				newModule2, newModule2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
		case BowlingPackage.TWO_IN_ONE_MODULE__MODULE1:
			return basicSetModule1(null, msgs);
		case BowlingPackage.TWO_IN_ONE_MODULE__MODULE2:
			return basicSetModule2(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case BowlingPackage.TWO_IN_ONE_MODULE__MODULE1:
			if (resolve)
				return getModule1();
			return basicGetModule1();
		case BowlingPackage.TWO_IN_ONE_MODULE__MODULE2:
			if (resolve)
				return getModule2();
			return basicGetModule2();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case BowlingPackage.TWO_IN_ONE_MODULE__MODULE1:
			setModule1((Module) newValue);
			return;
		case BowlingPackage.TWO_IN_ONE_MODULE__MODULE2:
			setModule2((Module) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case BowlingPackage.TWO_IN_ONE_MODULE__MODULE1:
			setModule1((Module) null);
			return;
		case BowlingPackage.TWO_IN_ONE_MODULE__MODULE2:
			setModule2((Module) null);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case BowlingPackage.TWO_IN_ONE_MODULE__MODULE1:
			return module1 != null;
		case BowlingPackage.TWO_IN_ONE_MODULE__MODULE2:
			return module2 != null;
		}
		return super.eIsSet(featureID);
	}

} // TwoInOneModuleImpl
