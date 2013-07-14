/**
 */
package org.eclipse.emf.emfstore.bowling;

import java.util.Date;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Fan</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Fan#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Fan#getDateOfBirth <em>Date Of Birth</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Fan#isHasSeasonTicket <em>Has Season Ticket</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Fan#getEMails <em>EMails</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Fan#getGender <em>Gender</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Fan#getFavouritePlayer <em>Favourite Player</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Fan#getFanMerchandise <em>Fan Merchandise</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Fan#getFavouriteMerchandise <em>Favourite Merchandise</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Fan#getVisitedTournaments <em>Visited Tournaments</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Fan#getNumberOfTournamentsVisited <em>Number Of Tournaments Visited</em>}</li>
 *   <li>{@link org.eclipse.emf.emfstore.bowling.Fan#getMoneySpentOnTickets <em>Money Spent On Tickets</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getFan()
 * @model
 * @generated
 */
public interface Fan extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #isSetName()
	 * @see #unsetName()
	 * @see #setName(String)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getFan_Name()
	 * @model unsettable="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #isSetName()
	 * @see #unsetName()
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetName()
	 * @see #getName()
	 * @see #setName(String)
	 * @generated
	 */
	void unsetName();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getName <em>Name</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Name</em>' attribute is set.
	 * @see #unsetName()
	 * @see #getName()
	 * @see #setName(String)
	 * @generated
	 */
	boolean isSetName();

	/**
	 * Returns the value of the '<em><b>Date Of Birth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date Of Birth</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date Of Birth</em>' attribute.
	 * @see #isSetDateOfBirth()
	 * @see #unsetDateOfBirth()
	 * @see #setDateOfBirth(Date)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getFan_DateOfBirth()
	 * @model unsettable="true"
	 * @generated
	 */
	Date getDateOfBirth();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getDateOfBirth <em>Date Of Birth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date Of Birth</em>' attribute.
	 * @see #isSetDateOfBirth()
	 * @see #unsetDateOfBirth()
	 * @see #getDateOfBirth()
	 * @generated
	 */
	void setDateOfBirth(Date value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getDateOfBirth <em>Date Of Birth</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDateOfBirth()
	 * @see #getDateOfBirth()
	 * @see #setDateOfBirth(Date)
	 * @generated
	 */
	void unsetDateOfBirth();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getDateOfBirth <em>Date Of Birth</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Date Of Birth</em>' attribute is set.
	 * @see #unsetDateOfBirth()
	 * @see #getDateOfBirth()
	 * @see #setDateOfBirth(Date)
	 * @generated
	 */
	boolean isSetDateOfBirth();

	/**
	 * Returns the value of the '<em><b>Has Season Ticket</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Has Season Ticket</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has Season Ticket</em>' attribute.
	 * @see #isSetHasSeasonTicket()
	 * @see #unsetHasSeasonTicket()
	 * @see #setHasSeasonTicket(boolean)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getFan_HasSeasonTicket()
	 * @model unsettable="true"
	 * @generated
	 */
	boolean isHasSeasonTicket();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#isHasSeasonTicket <em>Has Season Ticket</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Has Season Ticket</em>' attribute.
	 * @see #isSetHasSeasonTicket()
	 * @see #unsetHasSeasonTicket()
	 * @see #isHasSeasonTicket()
	 * @generated
	 */
	void setHasSeasonTicket(boolean value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#isHasSeasonTicket <em>Has Season Ticket</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetHasSeasonTicket()
	 * @see #isHasSeasonTicket()
	 * @see #setHasSeasonTicket(boolean)
	 * @generated
	 */
	void unsetHasSeasonTicket();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#isHasSeasonTicket <em>Has Season Ticket</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Has Season Ticket</em>' attribute is set.
	 * @see #unsetHasSeasonTicket()
	 * @see #isHasSeasonTicket()
	 * @see #setHasSeasonTicket(boolean)
	 * @generated
	 */
	boolean isSetHasSeasonTicket();

	/**
	 * Returns the value of the '<em><b>EMails</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>EMails</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>EMails</em>' attribute list.
	 * @see #isSetEMails()
	 * @see #unsetEMails()
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getFan_EMails()
	 * @model unsettable="true"
	 * @generated
	 */
	EList<String> getEMails();

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getEMails <em>EMails</em>}' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetEMails()
	 * @see #getEMails()
	 * @generated
	 */
	void unsetEMails();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getEMails <em>EMails</em>}' attribute list is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>EMails</em>' attribute list is set.
	 * @see #unsetEMails()
	 * @see #getEMails()
	 * @generated
	 */
	boolean isSetEMails();

	/**
	 * Returns the value of the '<em><b>Gender</b></em>' attribute.
	 * The literals are from the enumeration {@link org.eclipse.emf.emfstore.bowling.Gender}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gender</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gender</em>' attribute.
	 * @see org.eclipse.emf.emfstore.bowling.Gender
	 * @see #isSetGender()
	 * @see #unsetGender()
	 * @see #setGender(Gender)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getFan_Gender()
	 * @model unsettable="true"
	 * @generated
	 */
	Gender getGender();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getGender <em>Gender</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gender</em>' attribute.
	 * @see org.eclipse.emf.emfstore.bowling.Gender
	 * @see #isSetGender()
	 * @see #unsetGender()
	 * @see #getGender()
	 * @generated
	 */
	void setGender(Gender value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getGender <em>Gender</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetGender()
	 * @see #getGender()
	 * @see #setGender(Gender)
	 * @generated
	 */
	void unsetGender();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getGender <em>Gender</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Gender</em>' attribute is set.
	 * @see #unsetGender()
	 * @see #getGender()
	 * @see #setGender(Gender)
	 * @generated
	 */
	boolean isSetGender();

	/**
	 * Returns the value of the '<em><b>Favourite Player</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Favourite Player</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Favourite Player</em>' reference.
	 * @see #isSetFavouritePlayer()
	 * @see #unsetFavouritePlayer()
	 * @see #setFavouritePlayer(Player)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getFan_FavouritePlayer()
	 * @model unsettable="true"
	 * @generated
	 */
	Player getFavouritePlayer();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getFavouritePlayer <em>Favourite Player</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Favourite Player</em>' reference.
	 * @see #isSetFavouritePlayer()
	 * @see #unsetFavouritePlayer()
	 * @see #getFavouritePlayer()
	 * @generated
	 */
	void setFavouritePlayer(Player value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getFavouritePlayer <em>Favourite Player</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetFavouritePlayer()
	 * @see #getFavouritePlayer()
	 * @see #setFavouritePlayer(Player)
	 * @generated
	 */
	void unsetFavouritePlayer();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getFavouritePlayer <em>Favourite Player</em>}' reference is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Favourite Player</em>' reference is set.
	 * @see #unsetFavouritePlayer()
	 * @see #getFavouritePlayer()
	 * @see #setFavouritePlayer(Player)
	 * @generated
	 */
	boolean isSetFavouritePlayer();

	/**
	 * Returns the value of the '<em><b>Visited Tournaments</b></em>' reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.bowling.Tournament}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Visited Tournaments</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Visited Tournaments</em>' reference list.
	 * @see #isSetVisitedTournaments()
	 * @see #unsetVisitedTournaments()
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getFan_VisitedTournaments()
	 * @model unsettable="true"
	 * @generated
	 */
	EList<Tournament> getVisitedTournaments();

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getVisitedTournaments <em>Visited Tournaments</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetVisitedTournaments()
	 * @see #getVisitedTournaments()
	 * @generated
	 */
	void unsetVisitedTournaments();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getVisitedTournaments <em>Visited Tournaments</em>}' reference list is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Visited Tournaments</em>' reference list is set.
	 * @see #unsetVisitedTournaments()
	 * @see #getVisitedTournaments()
	 * @generated
	 */
	boolean isSetVisitedTournaments();

	/**
	 * Returns the value of the '<em><b>Number Of Tournaments Visited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number Of Tournaments Visited</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Tournaments Visited</em>' attribute.
	 * @see #isSetNumberOfTournamentsVisited()
	 * @see #unsetNumberOfTournamentsVisited()
	 * @see #setNumberOfTournamentsVisited(int)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getFan_NumberOfTournamentsVisited()
	 * @model unsettable="true"
	 * @generated
	 */
	int getNumberOfTournamentsVisited();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getNumberOfTournamentsVisited <em>Number Of Tournaments Visited</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Tournaments Visited</em>' attribute.
	 * @see #isSetNumberOfTournamentsVisited()
	 * @see #unsetNumberOfTournamentsVisited()
	 * @see #getNumberOfTournamentsVisited()
	 * @generated
	 */
	void setNumberOfTournamentsVisited(int value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getNumberOfTournamentsVisited <em>Number Of Tournaments Visited</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetNumberOfTournamentsVisited()
	 * @see #getNumberOfTournamentsVisited()
	 * @see #setNumberOfTournamentsVisited(int)
	 * @generated
	 */
	void unsetNumberOfTournamentsVisited();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getNumberOfTournamentsVisited <em>Number Of Tournaments Visited</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Number Of Tournaments Visited</em>' attribute is set.
	 * @see #unsetNumberOfTournamentsVisited()
	 * @see #getNumberOfTournamentsVisited()
	 * @see #setNumberOfTournamentsVisited(int)
	 * @generated
	 */
	boolean isSetNumberOfTournamentsVisited();

	/**
	 * Returns the value of the '<em><b>Money Spent On Tickets</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Money Spent On Tickets</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Money Spent On Tickets</em>' attribute.
	 * @see #isSetMoneySpentOnTickets()
	 * @see #unsetMoneySpentOnTickets()
	 * @see #setMoneySpentOnTickets(double)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getFan_MoneySpentOnTickets()
	 * @model unsettable="true"
	 * @generated
	 */
	double getMoneySpentOnTickets();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getMoneySpentOnTickets <em>Money Spent On Tickets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Money Spent On Tickets</em>' attribute.
	 * @see #isSetMoneySpentOnTickets()
	 * @see #unsetMoneySpentOnTickets()
	 * @see #getMoneySpentOnTickets()
	 * @generated
	 */
	void setMoneySpentOnTickets(double value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getMoneySpentOnTickets <em>Money Spent On Tickets</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetMoneySpentOnTickets()
	 * @see #getMoneySpentOnTickets()
	 * @see #setMoneySpentOnTickets(double)
	 * @generated
	 */
	void unsetMoneySpentOnTickets();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getMoneySpentOnTickets <em>Money Spent On Tickets</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Money Spent On Tickets</em>' attribute is set.
	 * @see #unsetMoneySpentOnTickets()
	 * @see #getMoneySpentOnTickets()
	 * @see #setMoneySpentOnTickets(double)
	 * @generated
	 */
	boolean isSetMoneySpentOnTickets();

	/**
	 * Returns the value of the '<em><b>Fan Merchandise</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.emf.emfstore.bowling.Merchandise}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fan Merchandise</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fan Merchandise</em>' containment reference list.
	 * @see #isSetFanMerchandise()
	 * @see #unsetFanMerchandise()
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getFan_FanMerchandise()
	 * @model containment="true" resolveProxies="true" unsettable="true"
	 * @generated
	 */
	EList<Merchandise> getFanMerchandise();

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getFanMerchandise <em>Fan Merchandise</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetFanMerchandise()
	 * @see #getFanMerchandise()
	 * @generated
	 */
	void unsetFanMerchandise();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getFanMerchandise <em>Fan Merchandise</em>}' containment reference list is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Fan Merchandise</em>' containment reference list is set.
	 * @see #unsetFanMerchandise()
	 * @see #getFanMerchandise()
	 * @generated
	 */
	boolean isSetFanMerchandise();

	/**
	 * Returns the value of the '<em><b>Favourite Merchandise</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Favourite Merchandise</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Favourite Merchandise</em>' containment reference.
	 * @see #isSetFavouriteMerchandise()
	 * @see #unsetFavouriteMerchandise()
	 * @see #setFavouriteMerchandise(Merchandise)
	 * @see org.eclipse.emf.emfstore.bowling.BowlingPackage#getFan_FavouriteMerchandise()
	 * @model containment="true" resolveProxies="true" unsettable="true"
	 * @generated
	 */
	Merchandise getFavouriteMerchandise();

	/**
	 * Sets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getFavouriteMerchandise <em>Favourite Merchandise</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Favourite Merchandise</em>' containment reference.
	 * @see #isSetFavouriteMerchandise()
	 * @see #unsetFavouriteMerchandise()
	 * @see #getFavouriteMerchandise()
	 * @generated
	 */
	void setFavouriteMerchandise(Merchandise value);

	/**
	 * Unsets the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getFavouriteMerchandise <em>Favourite Merchandise</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetFavouriteMerchandise()
	 * @see #getFavouriteMerchandise()
	 * @see #setFavouriteMerchandise(Merchandise)
	 * @generated
	 */
	void unsetFavouriteMerchandise();

	/**
	 * Returns whether the value of the '{@link org.eclipse.emf.emfstore.bowling.Fan#getFavouriteMerchandise <em>Favourite Merchandise</em>}' containment reference is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Favourite Merchandise</em>' containment reference is set.
	 * @see #unsetFavouriteMerchandise()
	 * @see #getFavouriteMerchandise()
	 * @see #setFavouriteMerchandise(Merchandise)
	 * @generated
	 */
	boolean isSetFavouriteMerchandise();

} // Fan
