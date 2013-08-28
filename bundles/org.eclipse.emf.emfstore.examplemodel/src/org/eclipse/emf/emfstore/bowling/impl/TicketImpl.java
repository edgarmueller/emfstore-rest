/**
 */
package org.eclipse.emf.emfstore.bowling.impl;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Module;
import org.eclipse.emf.emfstore.bowling.Ticket;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Ticket</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.bowling.impl.TicketImpl#getVenue <em>Venue</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.impl.TicketImpl#getAntiTheftModule <em>Anti Theft Module</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TicketImpl extends EObjectImpl implements Ticket {
	/**
	 * The default value of the '{@link #getVenue() <em>Venue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getVenue()
	 * @generated
	 * @ordered
	 */
	protected static final String VENUE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVenue() <em>Venue</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getVenue()
	 * @generated
	 * @ordered
	 */
	protected String venue = VENUE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getAntiTheftModule() <em>Anti Theft Module</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getAntiTheftModule()
	 * @generated
	 * @ordered
	 */
	protected Module antiTheftModule;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TicketImpl() {
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
		return BowlingPackage.Literals.TICKET;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getVenue() {
		return venue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setVenue(String newVenue) {
		String oldVenue = venue;
		venue = newVenue;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.TICKET__VENUE, oldVenue, venue));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Module getAntiTheftModule() {
		if (antiTheftModule != null && antiTheftModule.eIsProxy()) {
			InternalEObject oldAntiTheftModule = (InternalEObject) antiTheftModule;
			antiTheftModule = (Module) eResolveProxy(oldAntiTheftModule);
			if (antiTheftModule != oldAntiTheftModule) {
				InternalEObject newAntiTheftModule = (InternalEObject) antiTheftModule;
				NotificationChain msgs = oldAntiTheftModule.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- BowlingPackage.TICKET__ANTI_THEFT_MODULE, null, null);
				if (newAntiTheftModule.eInternalContainer() == null) {
					msgs = newAntiTheftModule.eInverseAdd(this, EOPPOSITE_FEATURE_BASE
						- BowlingPackage.TICKET__ANTI_THEFT_MODULE, null, msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BowlingPackage.TICKET__ANTI_THEFT_MODULE,
						oldAntiTheftModule, antiTheftModule));
			}
		}
		return antiTheftModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public Module basicGetAntiTheftModule() {
		return antiTheftModule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetAntiTheftModule(Module newAntiTheftModule, NotificationChain msgs) {
		Module oldAntiTheftModule = antiTheftModule;
		antiTheftModule = newAntiTheftModule;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				BowlingPackage.TICKET__ANTI_THEFT_MODULE, oldAntiTheftModule, newAntiTheftModule);
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
	public void setAntiTheftModule(Module newAntiTheftModule) {
		if (newAntiTheftModule != antiTheftModule) {
			NotificationChain msgs = null;
			if (antiTheftModule != null)
				msgs = ((InternalEObject) antiTheftModule).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- BowlingPackage.TICKET__ANTI_THEFT_MODULE, null, msgs);
			if (newAntiTheftModule != null)
				msgs = ((InternalEObject) newAntiTheftModule).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- BowlingPackage.TICKET__ANTI_THEFT_MODULE, null, msgs);
			msgs = basicSetAntiTheftModule(newAntiTheftModule, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.TICKET__ANTI_THEFT_MODULE,
				newAntiTheftModule, newAntiTheftModule));
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
		case BowlingPackage.TICKET__ANTI_THEFT_MODULE:
			return basicSetAntiTheftModule(null, msgs);
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
		case BowlingPackage.TICKET__VENUE:
			return getVenue();
		case BowlingPackage.TICKET__ANTI_THEFT_MODULE:
			if (resolve)
				return getAntiTheftModule();
			return basicGetAntiTheftModule();
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
		case BowlingPackage.TICKET__VENUE:
			setVenue((String) newValue);
			return;
		case BowlingPackage.TICKET__ANTI_THEFT_MODULE:
			setAntiTheftModule((Module) newValue);
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
		case BowlingPackage.TICKET__VENUE:
			setVenue(VENUE_EDEFAULT);
			return;
		case BowlingPackage.TICKET__ANTI_THEFT_MODULE:
			setAntiTheftModule((Module) null);
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
		case BowlingPackage.TICKET__VENUE:
			return VENUE_EDEFAULT == null ? venue != null : !VENUE_EDEFAULT.equals(venue);
		case BowlingPackage.TICKET__ANTI_THEFT_MODULE:
			return antiTheftModule != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (venue: ");
		result.append(venue);
		result.append(')');
		return result.toString();
	}

} // TicketImpl
