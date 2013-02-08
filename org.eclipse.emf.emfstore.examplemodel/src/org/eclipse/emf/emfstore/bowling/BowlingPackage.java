/**
 * Copyright (c) 2008-2011 Chair for Applied Software Engineering,
 * Technische Universitaet Muenchen.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 */
package org.eclipse.emf.emfstore.bowling;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * 
 * @see org.eclipse.emf.emfstore.bowling.BowlingFactory
 * @model kind="package"
 * @generated
 */
public interface BowlingPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNAME = "bowling";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_URI = "http://org/eclipse/example/bowling";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	String eNS_PREFIX = "org.eclipse.example.bowling";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	BowlingPackage eINSTANCE = org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.PlayerImpl <em>Player</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.PlayerImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getPlayer()
	 * @generated
	 */
	int PLAYER = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAYER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Date Of Birth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAYER__DATE_OF_BIRTH = 1;

	/**
	 * The feature id for the '<em><b>Height</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAYER__HEIGHT = 2;

	/**
	 * The feature id for the '<em><b>Is Professional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAYER__IS_PROFESSIONAL = 3;

	/**
	 * The feature id for the '<em><b>EMail</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAYER__EMAIL = 4;

	/**
	 * The number of structural features of the '<em>Player</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAYER_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.LeagueImpl <em>League</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.LeagueImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getLeague()
	 * @generated
	 */
	int LEAGUE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEAGUE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Players</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEAGUE__PLAYERS = 1;

	/**
	 * The number of structural features of the '<em>League</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int LEAGUE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.TournamentImpl <em>Tournament</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.TournamentImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getTournament()
	 * @generated
	 */
	int TOURNAMENT = 2;

	/**
	 * The feature id for the '<em><b>Matchups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TOURNAMENT__MATCHUPS = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TOURNAMENT__TYPE = 1;

	/**
	 * The feature id for the '<em><b>Player Points</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TOURNAMENT__PLAYER_POINTS = 2;

	/**
	 * The feature id for the '<em><b>Players</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TOURNAMENT__PLAYERS = 3;

	/**
	 * The feature id for the '<em><b>Referees</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TOURNAMENT__REFEREES = 4;

	/**
	 * The number of structural features of the '<em>Tournament</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TOURNAMENT_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.MatchupImpl <em>Matchup</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.MatchupImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getMatchup()
	 * @generated
	 */
	int MATCHUP = 3;

	/**
	 * The feature id for the '<em><b>Games</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MATCHUP__GAMES = 0;

	/**
	 * The number of structural features of the '<em>Matchup</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MATCHUP_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.GameImpl <em>Game</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.GameImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getGame()
	 * @generated
	 */
	int GAME = 4;

	/**
	 * The feature id for the '<em><b>Matchup</b></em>' container reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GAME__MATCHUP = 0;

	/**
	 * The feature id for the '<em><b>Player</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GAME__PLAYER = 1;

	/**
	 * The feature id for the '<em><b>Frames</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GAME__FRAMES = 2;

	/**
	 * The number of structural features of the '<em>Game</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GAME_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.PlayerToPointsMapImpl
	 * <em>Player To Points Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.PlayerToPointsMapImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getPlayerToPointsMap()
	 * @generated
	 */
	int PLAYER_TO_POINTS_MAP = 5;

	/**
	 * The feature id for the '<em><b>Key</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAYER_TO_POINTS_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAYER_TO_POINTS_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Player To Points Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAYER_TO_POINTS_MAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.RefereeImpl <em>Referee</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.RefereeImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getReferee()
	 * @generated
	 */
	int REFEREE = 6;

	/**
	 * The feature id for the '<em><b>League</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REFEREE__LEAGUE = 0;

	/**
	 * The number of structural features of the '<em>Referee</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REFEREE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.RefereeToGamesMapImpl
	 * <em>Referee To Games Map</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.RefereeToGamesMapImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getRefereeToGamesMap()
	 * @generated
	 */
	int REFEREE_TO_GAMES_MAP = 7;

	/**
	 * The feature id for the '<em><b>Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REFEREE_TO_GAMES_MAP__KEY = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REFEREE_TO_GAMES_MAP__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Referee To Games Map</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int REFEREE_TO_GAMES_MAP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.AreaImpl <em>Area</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.AreaImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getArea()
	 * @generated
	 */
	int AREA = 8;

	/**
	 * The feature id for the '<em><b>Areas</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AREA__AREAS = 0;

	/**
	 * The feature id for the '<em><b>Tournaments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AREA__TOURNAMENTS = 1;

	/**
	 * The number of structural features of the '<em>Area</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int AREA_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.TournamentType <em>Tournament Type</em>}'
	 * enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.TournamentType
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getTournamentType()
	 * @generated
	 */
	int TOURNAMENT_TYPE = 9;

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.bowling.Player <em>Player</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Player</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Player
	 * @generated
	 */
	EClass getPlayer();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Player#getName <em>Name</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Player#getName()
	 * @see #getPlayer()
	 * @generated
	 */
	EAttribute getPlayer_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Player#getDateOfBirth
	 * <em>Date Of Birth</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Date Of Birth</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Player#getDateOfBirth()
	 * @see #getPlayer()
	 * @generated
	 */
	EAttribute getPlayer_DateOfBirth();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Player#getHeight
	 * <em>Height</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Height</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Player#getHeight()
	 * @see #getPlayer()
	 * @generated
	 */
	EAttribute getPlayer_Height();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Player#isIsProfessional
	 * <em>Is Professional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Is Professional</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Player#isIsProfessional()
	 * @see #getPlayer()
	 * @generated
	 */
	EAttribute getPlayer_IsProfessional();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Player#getEMail
	 * <em>EMail</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>EMail</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Player#getEMail()
	 * @see #getPlayer()
	 * @generated
	 */
	EAttribute getPlayer_EMail();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.bowling.League <em>League</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>League</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.League
	 * @generated
	 */
	EClass getLeague();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.League#getName <em>Name</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.League#getName()
	 * @see #getLeague()
	 * @generated
	 */
	EAttribute getLeague_Name();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.bowling.League#getPlayers <em>Players</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Players</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.League#getPlayers()
	 * @see #getLeague()
	 * @generated
	 */
	EReference getLeague_Players();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.bowling.Tournament <em>Tournament</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Tournament</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Tournament
	 * @generated
	 */
	EClass getTournament();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.bowling.Tournament#getMatchups <em>Matchups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Matchups</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Tournament#getMatchups()
	 * @see #getTournament()
	 * @generated
	 */
	EReference getTournament_Matchups();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Tournament#getType
	 * <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Tournament#getType()
	 * @see #getTournament()
	 * @generated
	 */
	EAttribute getTournament_Type();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.emf.emfstore.bowling.Tournament#getPlayerPoints
	 * <em>Player Points</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the map '<em>Player Points</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Tournament#getPlayerPoints()
	 * @see #getTournament()
	 * @generated
	 */
	EReference getTournament_PlayerPoints();

	/**
	 * Returns the meta object for the reference list '{@link org.eclipse.emf.emfstore.bowling.Tournament#getPlayers
	 * <em>Players</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Players</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Tournament#getPlayers()
	 * @see #getTournament()
	 * @generated
	 */
	EReference getTournament_Players();

	/**
	 * Returns the meta object for the map '{@link org.eclipse.emf.emfstore.bowling.Tournament#getReferees
	 * <em>Referees</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the map '<em>Referees</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Tournament#getReferees()
	 * @see #getTournament()
	 * @generated
	 */
	EReference getTournament_Referees();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.bowling.Matchup <em>Matchup</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Matchup</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Matchup
	 * @generated
	 */
	EClass getMatchup();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.bowling.Matchup#getGames <em>Games</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Games</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Matchup#getGames()
	 * @see #getMatchup()
	 * @generated
	 */
	EReference getMatchup_Games();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.bowling.Game <em>Game</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Game</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Game
	 * @generated
	 */
	EClass getGame();

	/**
	 * Returns the meta object for the container reference '{@link org.eclipse.emf.emfstore.bowling.Game#getMatchup
	 * <em>Matchup</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the container reference '<em>Matchup</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Game#getMatchup()
	 * @see #getGame()
	 * @generated
	 */
	EReference getGame_Matchup();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.emfstore.bowling.Game#getPlayer
	 * <em>Player</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Player</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Game#getPlayer()
	 * @see #getGame()
	 * @generated
	 */
	EReference getGame_Player();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emf.emfstore.bowling.Game#getFrames
	 * <em>Frames</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Frames</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Game#getFrames()
	 * @see #getGame()
	 * @generated
	 */
	EAttribute getGame_Frames();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Player To Points Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Player To Points Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.eclipse.emf.emfstore.bowling.Player"
	 *        valueDataType="org.eclipse.emf.ecore.EIntegerObject"
	 * @generated
	 */
	EClass getPlayerToPointsMap();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getPlayerToPointsMap()
	 * @generated
	 */
	EReference getPlayerToPointsMap_Key();

	/**
	 * Returns the meta object for the attribute '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getPlayerToPointsMap()
	 * @generated
	 */
	EAttribute getPlayerToPointsMap_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.bowling.Referee <em>Referee</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Referee</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Referee
	 * @generated
	 */
	EClass getReferee();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.emfstore.bowling.Referee#getLeague
	 * <em>League</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>League</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Referee#getLeague()
	 * @see #getReferee()
	 * @generated
	 */
	EReference getReferee_League();

	/**
	 * Returns the meta object for class '{@link java.util.Map.Entry <em>Referee To Games Map</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Referee To Games Map</em>'.
	 * @see java.util.Map.Entry
	 * @model keyType="org.eclipse.emf.emfstore.bowling.Referee" keyContainment="true" keyResolveProxies="true"
	 *        valueType="org.eclipse.emf.emfstore.bowling.Game"
	 * @generated
	 */
	EClass getRefereeToGamesMap();

	/**
	 * Returns the meta object for the containment reference '{@link java.util.Map.Entry <em>Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Key</em>'.
	 * @see java.util.Map.Entry
	 * @see #getRefereeToGamesMap()
	 * @generated
	 */
	EReference getRefereeToGamesMap_Key();

	/**
	 * Returns the meta object for the reference '{@link java.util.Map.Entry <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see java.util.Map.Entry
	 * @see #getRefereeToGamesMap()
	 * @generated
	 */
	EReference getRefereeToGamesMap_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.bowling.Area <em>Area</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Area</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Area
	 * @generated
	 */
	EClass getArea();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.bowling.Area#getAreas <em>Areas</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Areas</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Area#getAreas()
	 * @see #getArea()
	 * @generated
	 */
	EReference getArea_Areas();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.bowling.Area#getTournaments <em>Tournaments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Tournaments</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Area#getTournaments()
	 * @see #getArea()
	 * @generated
	 */
	EReference getArea_Tournaments();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.emf.emfstore.bowling.TournamentType
	 * <em>Tournament Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Tournament Type</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.TournamentType
	 * @generated
	 */
	EEnum getTournamentType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BowlingFactory getBowlingFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 * <li>each class,</li>
	 * <li>each feature of each class,</li>
	 * <li>each enum,</li>
	 * <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.PlayerImpl <em>Player</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.PlayerImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getPlayer()
		 * @generated
		 */
		EClass PLAYER = eINSTANCE.getPlayer();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PLAYER__NAME = eINSTANCE.getPlayer_Name();

		/**
		 * The meta object literal for the '<em><b>Date Of Birth</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PLAYER__DATE_OF_BIRTH = eINSTANCE.getPlayer_DateOfBirth();

		/**
		 * The meta object literal for the '<em><b>Height</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PLAYER__HEIGHT = eINSTANCE.getPlayer_Height();

		/**
		 * The meta object literal for the '<em><b>Is Professional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PLAYER__IS_PROFESSIONAL = eINSTANCE.getPlayer_IsProfessional();

		/**
		 * The meta object literal for the '<em><b>EMail</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PLAYER__EMAIL = eINSTANCE.getPlayer_EMail();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.LeagueImpl <em>League</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.LeagueImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getLeague()
		 * @generated
		 */
		EClass LEAGUE = eINSTANCE.getLeague();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute LEAGUE__NAME = eINSTANCE.getLeague_Name();

		/**
		 * The meta object literal for the '<em><b>Players</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference LEAGUE__PLAYERS = eINSTANCE.getLeague_Players();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.TournamentImpl
		 * <em>Tournament</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.TournamentImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getTournament()
		 * @generated
		 */
		EClass TOURNAMENT = eINSTANCE.getTournament();

		/**
		 * The meta object literal for the '<em><b>Matchups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TOURNAMENT__MATCHUPS = eINSTANCE.getTournament_Matchups();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TOURNAMENT__TYPE = eINSTANCE.getTournament_Type();

		/**
		 * The meta object literal for the '<em><b>Player Points</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TOURNAMENT__PLAYER_POINTS = eINSTANCE.getTournament_PlayerPoints();

		/**
		 * The meta object literal for the '<em><b>Players</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TOURNAMENT__PLAYERS = eINSTANCE.getTournament_Players();

		/**
		 * The meta object literal for the '<em><b>Referees</b></em>' map feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TOURNAMENT__REFEREES = eINSTANCE.getTournament_Referees();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.MatchupImpl <em>Matchup</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.MatchupImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getMatchup()
		 * @generated
		 */
		EClass MATCHUP = eINSTANCE.getMatchup();

		/**
		 * The meta object literal for the '<em><b>Games</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MATCHUP__GAMES = eINSTANCE.getMatchup_Games();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.GameImpl <em>Game</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.GameImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getGame()
		 * @generated
		 */
		EClass GAME = eINSTANCE.getGame();

		/**
		 * The meta object literal for the '<em><b>Matchup</b></em>' container reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference GAME__MATCHUP = eINSTANCE.getGame_Matchup();

		/**
		 * The meta object literal for the '<em><b>Player</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference GAME__PLAYER = eINSTANCE.getGame_Player();

		/**
		 * The meta object literal for the '<em><b>Frames</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute GAME__FRAMES = eINSTANCE.getGame_Frames();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.PlayerToPointsMapImpl
		 * <em>Player To Points Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.PlayerToPointsMapImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getPlayerToPointsMap()
		 * @generated
		 */
		EClass PLAYER_TO_POINTS_MAP = eINSTANCE.getPlayerToPointsMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference PLAYER_TO_POINTS_MAP__KEY = eINSTANCE.getPlayerToPointsMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PLAYER_TO_POINTS_MAP__VALUE = eINSTANCE.getPlayerToPointsMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.RefereeImpl <em>Referee</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.RefereeImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getReferee()
		 * @generated
		 */
		EClass REFEREE = eINSTANCE.getReferee();

		/**
		 * The meta object literal for the '<em><b>League</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference REFEREE__LEAGUE = eINSTANCE.getReferee_League();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.RefereeToGamesMapImpl
		 * <em>Referee To Games Map</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.RefereeToGamesMapImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getRefereeToGamesMap()
		 * @generated
		 */
		EClass REFEREE_TO_GAMES_MAP = eINSTANCE.getRefereeToGamesMap();

		/**
		 * The meta object literal for the '<em><b>Key</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference REFEREE_TO_GAMES_MAP__KEY = eINSTANCE.getRefereeToGamesMap_Key();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference REFEREE_TO_GAMES_MAP__VALUE = eINSTANCE.getRefereeToGamesMap_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.AreaImpl <em>Area</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.AreaImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getArea()
		 * @generated
		 */
		EClass AREA = eINSTANCE.getArea();

		/**
		 * The meta object literal for the '<em><b>Areas</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference AREA__AREAS = eINSTANCE.getArea_Areas();

		/**
		 * The meta object literal for the '<em><b>Tournaments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference AREA__TOURNAMENTS = eINSTANCE.getArea_Tournaments();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.TournamentType
		 * <em>Tournament Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.TournamentType
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getTournamentType()
		 * @generated
		 */
		EEnum TOURNAMENT_TYPE = eINSTANCE.getTournamentType();

	}

} // BowlingPackage