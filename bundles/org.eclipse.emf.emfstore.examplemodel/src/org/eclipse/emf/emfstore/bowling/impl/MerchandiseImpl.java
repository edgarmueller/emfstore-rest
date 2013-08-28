/**
 */
package org.eclipse.emf.emfstore.bowling.impl;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.emfstore.bowling.AntiTheftChip;
import org.eclipse.emf.emfstore.bowling.BowlingPackage;
import org.eclipse.emf.emfstore.bowling.Merchandise;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Merchandise</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.eclipse.emf.emfstore.bowling.impl.MerchandiseImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.impl.MerchandiseImpl#getPrice <em>Price</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.impl.MerchandiseImpl#getSerialNumber <em>Serial Number</em>}</li>
 * <li>{@link org.eclipse.emf.emfstore.bowling.impl.MerchandiseImpl#getChip <em>Chip</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class MerchandiseImpl extends EObjectImpl implements Merchandise {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPrice() <em>Price</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getPrice()
	 * @generated
	 * @ordered
	 */
	protected static final BigDecimal PRICE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPrice() <em>Price</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getPrice()
	 * @generated
	 * @ordered
	 */
	protected BigDecimal price = PRICE_EDEFAULT;

	/**
	 * This is true if the Price attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean priceESet;

	/**
	 * The default value of the '{@link #getSerialNumber() <em>Serial Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSerialNumber()
	 * @generated
	 * @ordered
	 */
	protected static final BigInteger SERIAL_NUMBER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSerialNumber() <em>Serial Number</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSerialNumber()
	 * @generated
	 * @ordered
	 */
	protected BigInteger serialNumber = SERIAL_NUMBER_EDEFAULT;

	/**
	 * This is true if the Serial Number attribute has been set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	protected boolean serialNumberESet;

	/**
	 * The cached value of the '{@link #getChip() <em>Chip</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getChip()
	 * @generated
	 * @ordered
	 */
	protected AntiTheftChip chip;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MerchandiseImpl() {
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
		return BowlingPackage.Literals.MERCHANDISE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.MERCHANDISE__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setPrice(BigDecimal newPrice) {
		BigDecimal oldPrice = price;
		price = newPrice;
		boolean oldPriceESet = priceESet;
		priceESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.MERCHANDISE__PRICE, oldPrice, price,
				!oldPriceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void unsetPrice() {
		BigDecimal oldPrice = price;
		boolean oldPriceESet = priceESet;
		price = PRICE_EDEFAULT;
		priceESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BowlingPackage.MERCHANDISE__PRICE, oldPrice,
				PRICE_EDEFAULT, oldPriceESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isSetPrice() {
		return priceESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public BigInteger getSerialNumber() {
		return serialNumber;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void setSerialNumber(BigInteger newSerialNumber) {
		BigInteger oldSerialNumber = serialNumber;
		serialNumber = newSerialNumber;
		boolean oldSerialNumberESet = serialNumberESet;
		serialNumberESet = true;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.MERCHANDISE__SERIAL_NUMBER,
				oldSerialNumber, serialNumber, !oldSerialNumberESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public void unsetSerialNumber() {
		BigInteger oldSerialNumber = serialNumber;
		boolean oldSerialNumberESet = serialNumberESet;
		serialNumber = SERIAL_NUMBER_EDEFAULT;
		serialNumberESet = false;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.UNSET, BowlingPackage.MERCHANDISE__SERIAL_NUMBER,
				oldSerialNumber, SERIAL_NUMBER_EDEFAULT, oldSerialNumberESet));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public boolean isSetSerialNumber() {
		return serialNumberESet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AntiTheftChip getChip() {
		if (chip != null && chip.eIsProxy()) {
			InternalEObject oldChip = (InternalEObject) chip;
			chip = (AntiTheftChip) eResolveProxy(oldChip);
			if (chip != oldChip) {
				InternalEObject newChip = (InternalEObject) chip;
				NotificationChain msgs = oldChip.eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- BowlingPackage.MERCHANDISE__CHIP, null, null);
				if (newChip.eInternalContainer() == null) {
					msgs = newChip.eInverseAdd(this, EOPPOSITE_FEATURE_BASE - BowlingPackage.MERCHANDISE__CHIP, null,
						msgs);
				}
				if (msgs != null)
					msgs.dispatch();
				if (eNotificationRequired())
					eNotify(new ENotificationImpl(this, Notification.RESOLVE, BowlingPackage.MERCHANDISE__CHIP,
						oldChip, chip));
			}
		}
		return chip;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public AntiTheftChip basicGetChip() {
		return chip;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public NotificationChain basicSetChip(AntiTheftChip newChip, NotificationChain msgs) {
		AntiTheftChip oldChip = chip;
		chip = newChip;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this, Notification.SET,
				BowlingPackage.MERCHANDISE__CHIP, oldChip, newChip);
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
	public void setChip(AntiTheftChip newChip) {
		if (newChip != chip) {
			NotificationChain msgs = null;
			if (chip != null)
				msgs = ((InternalEObject) chip).eInverseRemove(this, EOPPOSITE_FEATURE_BASE
					- BowlingPackage.MERCHANDISE__CHIP, null, msgs);
			if (newChip != null)
				msgs = ((InternalEObject) newChip).eInverseAdd(this, EOPPOSITE_FEATURE_BASE
					- BowlingPackage.MERCHANDISE__CHIP, null, msgs);
			msgs = basicSetChip(newChip, msgs);
			if (msgs != null)
				msgs.dispatch();
		}
		else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, BowlingPackage.MERCHANDISE__CHIP, newChip, newChip));
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
		case BowlingPackage.MERCHANDISE__CHIP:
			return basicSetChip(null, msgs);
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
		case BowlingPackage.MERCHANDISE__NAME:
			return getName();
		case BowlingPackage.MERCHANDISE__PRICE:
			return getPrice();
		case BowlingPackage.MERCHANDISE__SERIAL_NUMBER:
			return getSerialNumber();
		case BowlingPackage.MERCHANDISE__CHIP:
			if (resolve)
				return getChip();
			return basicGetChip();
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
		case BowlingPackage.MERCHANDISE__NAME:
			setName((String) newValue);
			return;
		case BowlingPackage.MERCHANDISE__PRICE:
			setPrice((BigDecimal) newValue);
			return;
		case BowlingPackage.MERCHANDISE__SERIAL_NUMBER:
			setSerialNumber((BigInteger) newValue);
			return;
		case BowlingPackage.MERCHANDISE__CHIP:
			setChip((AntiTheftChip) newValue);
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
		case BowlingPackage.MERCHANDISE__NAME:
			setName(NAME_EDEFAULT);
			return;
		case BowlingPackage.MERCHANDISE__PRICE:
			unsetPrice();
			return;
		case BowlingPackage.MERCHANDISE__SERIAL_NUMBER:
			unsetSerialNumber();
			return;
		case BowlingPackage.MERCHANDISE__CHIP:
			setChip((AntiTheftChip) null);
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
		case BowlingPackage.MERCHANDISE__NAME:
			return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
		case BowlingPackage.MERCHANDISE__PRICE:
			return isSetPrice();
		case BowlingPackage.MERCHANDISE__SERIAL_NUMBER:
			return isSetSerialNumber();
		case BowlingPackage.MERCHANDISE__CHIP:
			return chip != null;
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
		result.append(" (name: ");
		result.append(name);
		result.append(", price: ");
		if (priceESet)
			result.append(price);
		else
			result.append("<unset>");
		result.append(", serialNumber: ");
		if (serialNumberESet)
			result.append(serialNumber);
		else
			result.append("<unset>");
		result.append(')');
		return result.toString();
	}

} // MerchandiseImpl
