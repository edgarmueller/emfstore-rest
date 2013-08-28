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
	 * The feature id for the '<em><b>EMails</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAYER__EMAILS = 4;

	/**
	 * The feature id for the '<em><b>Number Of Victories</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAYER__NUMBER_OF_VICTORIES = 5;

	/**
	 * The feature id for the '<em><b>Played Tournament Types</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAYER__PLAYED_TOURNAMENT_TYPES = 6;

	/**
	 * The feature id for the '<em><b>Win Loss Ratio</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAYER__WIN_LOSS_RATIO = 7;

	/**
	 * The feature id for the '<em><b>Gender</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAYER__GENDER = 8;

	/**
	 * The number of structural features of the '<em>Player</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int PLAYER_FEATURE_COUNT = 9;

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
	 * The feature id for the '<em><b>Price Money</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TOURNAMENT__PRICE_MONEY = 5;

	/**
	 * The feature id for the '<em><b>Receives Trophy</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TOURNAMENT__RECEIVES_TROPHY = 6;

	/**
	 * The feature id for the '<em><b>Match Days</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TOURNAMENT__MATCH_DAYS = 7;

	/**
	 * The number of structural features of the '<em>Tournament</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TOURNAMENT_FEATURE_COUNT = 8;

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
	 * The feature id for the '<em><b>Nr Spectators</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MATCHUP__NR_SPECTATORS = 1;

	/**
	 * The number of structural features of the '<em>Matchup</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MATCHUP_FEATURE_COUNT = 2;

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
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.FanImpl <em>Fan</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.FanImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getFan()
	 * @generated
	 */
	int FAN = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FAN__NAME = 0;

	/**
	 * The feature id for the '<em><b>Date Of Birth</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FAN__DATE_OF_BIRTH = 1;

	/**
	 * The feature id for the '<em><b>Has Season Ticket</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FAN__HAS_SEASON_TICKET = 2;

	/**
	 * The feature id for the '<em><b>EMails</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FAN__EMAILS = 3;

	/**
	 * The feature id for the '<em><b>Gender</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FAN__GENDER = 4;

	/**
	 * The feature id for the '<em><b>Favourite Player</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FAN__FAVOURITE_PLAYER = 5;

	/**
	 * The feature id for the '<em><b>Fan Merchandise</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FAN__FAN_MERCHANDISE = 6;

	/**
	 * The feature id for the '<em><b>Favourite Merchandise</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FAN__FAVOURITE_MERCHANDISE = 7;

	/**
	 * The feature id for the '<em><b>Ticket</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FAN__TICKET = 8;

	/**
	 * The feature id for the '<em><b>Visited Tournaments</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FAN__VISITED_TOURNAMENTS = 9;

	/**
	 * The feature id for the '<em><b>Number Of Tournaments Visited</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FAN__NUMBER_OF_TOURNAMENTS_VISITED = 10;

	/**
	 * The feature id for the '<em><b>Money Spent On Tickets</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FAN__MONEY_SPENT_ON_TICKETS = 11;

	/**
	 * The number of structural features of the '<em>Fan</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int FAN_FEATURE_COUNT = 12;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.TicketImpl <em>Ticket</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.TicketImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getTicket()
	 * @generated
	 */
	int TICKET = 10;

	/**
	 * The feature id for the '<em><b>Venue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TICKET__VENUE = 0;

	/**
	 * The feature id for the '<em><b>Anti Theft Module</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TICKET__ANTI_THEFT_MODULE = 1;

	/**
	 * The number of structural features of the '<em>Ticket</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TICKET_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.SeasonTicketImpl <em>Season Ticket</em>}
	 * ' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.SeasonTicketImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getSeasonTicket()
	 * @generated
	 */
	int SEASON_TICKET = 11;

	/**
	 * The feature id for the '<em><b>Venue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEASON_TICKET__VENUE = TICKET__VENUE;

	/**
	 * The feature id for the '<em><b>Anti Theft Module</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEASON_TICKET__ANTI_THEFT_MODULE = TICKET__ANTI_THEFT_MODULE;

	/**
	 * The feature id for the '<em><b>From</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEASON_TICKET__FROM = TICKET_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEASON_TICKET__TO = TICKET_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Season Ticket</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int SEASON_TICKET_FEATURE_COUNT = TICKET_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.DayTicketImpl <em>Day Ticket</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.DayTicketImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getDayTicket()
	 * @generated
	 */
	int DAY_TICKET = 12;

	/**
	 * The feature id for the '<em><b>Venue</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DAY_TICKET__VENUE = TICKET__VENUE;

	/**
	 * The feature id for the '<em><b>Anti Theft Module</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DAY_TICKET__ANTI_THEFT_MODULE = TICKET__ANTI_THEFT_MODULE;

	/**
	 * The feature id for the '<em><b>Date</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DAY_TICKET__DATE = TICKET_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Day Ticket</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int DAY_TICKET_FEATURE_COUNT = TICKET_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.MerchandiseImpl <em>Merchandise</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.MerchandiseImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getMerchandise()
	 * @generated
	 */
	int MERCHANDISE = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERCHANDISE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Price</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERCHANDISE__PRICE = 1;

	/**
	 * The feature id for the '<em><b>Serial Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERCHANDISE__SERIAL_NUMBER = 2;

	/**
	 * The feature id for the '<em><b>Chip</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERCHANDISE__CHIP = 3;

	/**
	 * The number of structural features of the '<em>Merchandise</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MERCHANDISE_FEATURE_COUNT = 4;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.AntiTheftChipImpl
	 * <em>Anti Theft Chip</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.AntiTheftChipImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getAntiTheftChip()
	 * @generated
	 */
	int ANTI_THEFT_CHIP = 14;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ANTI_THEFT_CHIP__DESCRIPTION = 0;

	/**
	 * The feature id for the '<em><b>Module</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ANTI_THEFT_CHIP__MODULE = 1;

	/**
	 * The number of structural features of the '<em>Anti Theft Chip</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ANTI_THEFT_CHIP_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.ModuleImpl <em>Module</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.ModuleImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getModule()
	 * @generated
	 */
	int MODULE = 15;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE__DESCRIPTION = 0;

	/**
	 * The number of structural features of the '<em>Module</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int MODULE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.GPSModuleImpl <em>GPS Module</em>}'
	 * class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.GPSModuleImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getGPSModule()
	 * @generated
	 */
	int GPS_MODULE = 16;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GPS_MODULE__DESCRIPTION = MODULE__DESCRIPTION;

	/**
	 * The number of structural features of the '<em>GPS Module</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int GPS_MODULE_FEATURE_COUNT = MODULE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.ElectroMagneticModuleImpl
	 * <em>Electro Magnetic Module</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.ElectroMagneticModuleImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getElectroMagneticModule()
	 * @generated
	 */
	int ELECTRO_MAGNETIC_MODULE = 17;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ELECTRO_MAGNETIC_MODULE__DESCRIPTION = MODULE__DESCRIPTION;

	/**
	 * The number of structural features of the '<em>Electro Magnetic Module</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int ELECTRO_MAGNETIC_MODULE_FEATURE_COUNT = MODULE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.impl.TwoInOneModuleImpl
	 * <em>Two In One Module</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.impl.TwoInOneModuleImpl
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getTwoInOneModule()
	 * @generated
	 */
	int TWO_IN_ONE_MODULE = 18;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TWO_IN_ONE_MODULE__DESCRIPTION = MODULE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Module1</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TWO_IN_ONE_MODULE__MODULE1 = MODULE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Module2</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TWO_IN_ONE_MODULE__MODULE2 = MODULE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Two In One Module</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 * @ordered
	 */
	int TWO_IN_ONE_MODULE_FEATURE_COUNT = MODULE_FEATURE_COUNT + 2;

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
	int TOURNAMENT_TYPE = 19;

	/**
	 * The meta object id for the '{@link org.eclipse.emf.emfstore.bowling.Gender <em>Gender</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.eclipse.emf.emfstore.bowling.Gender
	 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getGender()
	 * @generated
	 */
	int GENDER = 20;

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
	 * Returns the meta object for the attribute list '{@link org.eclipse.emf.emfstore.bowling.Player#getEMails
	 * <em>EMails</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>EMails</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Player#getEMails()
	 * @see #getPlayer()
	 * @generated
	 */
	EAttribute getPlayer_EMails();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Player#getNumberOfVictories
	 * <em>Number Of Victories</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Number Of Victories</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Player#getNumberOfVictories()
	 * @see #getPlayer()
	 * @generated
	 */
	EAttribute getPlayer_NumberOfVictories();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link org.eclipse.emf.emfstore.bowling.Player#getPlayedTournamentTypes <em>Played Tournament Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Played Tournament Types</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Player#getPlayedTournamentTypes()
	 * @see #getPlayer()
	 * @generated
	 */
	EAttribute getPlayer_PlayedTournamentTypes();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Player#getWinLossRatio
	 * <em>Win Loss Ratio</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Win Loss Ratio</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Player#getWinLossRatio()
	 * @see #getPlayer()
	 * @generated
	 */
	EAttribute getPlayer_WinLossRatio();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Player#getGender
	 * <em>Gender</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Gender</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Player#getGender()
	 * @see #getPlayer()
	 * @generated
	 */
	EAttribute getPlayer_Gender();

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
	 * Returns the meta object for the attribute list '{@link org.eclipse.emf.emfstore.bowling.Tournament#getPriceMoney
	 * <em>Price Money</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Price Money</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Tournament#getPriceMoney()
	 * @see #getTournament()
	 * @generated
	 */
	EAttribute getTournament_PriceMoney();

	/**
	 * Returns the meta object for the attribute list '
	 * {@link org.eclipse.emf.emfstore.bowling.Tournament#getReceivesTrophy <em>Receives Trophy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Receives Trophy</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Tournament#getReceivesTrophy()
	 * @see #getTournament()
	 * @generated
	 */
	EAttribute getTournament_ReceivesTrophy();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emf.emfstore.bowling.Tournament#getMatchDays
	 * <em>Match Days</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>Match Days</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Tournament#getMatchDays()
	 * @see #getTournament()
	 * @generated
	 */
	EAttribute getTournament_MatchDays();

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
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Matchup#getNrSpectators
	 * <em>Nr Spectators</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Nr Spectators</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Matchup#getNrSpectators()
	 * @see #getMatchup()
	 * @generated
	 */
	EAttribute getMatchup_NrSpectators();

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
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.bowling.Fan <em>Fan</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Fan</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Fan
	 * @generated
	 */
	EClass getFan();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Fan#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Fan#getName()
	 * @see #getFan()
	 * @generated
	 */
	EAttribute getFan_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Fan#getDateOfBirth
	 * <em>Date Of Birth</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Date Of Birth</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Fan#getDateOfBirth()
	 * @see #getFan()
	 * @generated
	 */
	EAttribute getFan_DateOfBirth();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Fan#isHasSeasonTicket
	 * <em>Has Season Ticket</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Has Season Ticket</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Fan#isHasSeasonTicket()
	 * @see #getFan()
	 * @generated
	 */
	EAttribute getFan_HasSeasonTicket();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.emf.emfstore.bowling.Fan#getEMails
	 * <em>EMails</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute list '<em>EMails</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Fan#getEMails()
	 * @see #getFan()
	 * @generated
	 */
	EAttribute getFan_EMails();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Fan#getGender <em>Gender</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Gender</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Fan#getGender()
	 * @see #getFan()
	 * @generated
	 */
	EAttribute getFan_Gender();

	/**
	 * Returns the meta object for the reference '{@link org.eclipse.emf.emfstore.bowling.Fan#getFavouritePlayer
	 * <em>Favourite Player</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference '<em>Favourite Player</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Fan#getFavouritePlayer()
	 * @see #getFan()
	 * @generated
	 */
	EReference getFan_FavouritePlayer();

	/**
	 * Returns the meta object for the reference list '
	 * {@link org.eclipse.emf.emfstore.bowling.Fan#getVisitedTournaments <em>Visited Tournaments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the reference list '<em>Visited Tournaments</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Fan#getVisitedTournaments()
	 * @see #getFan()
	 * @generated
	 */
	EReference getFan_VisitedTournaments();

	/**
	 * Returns the meta object for the attribute '
	 * {@link org.eclipse.emf.emfstore.bowling.Fan#getNumberOfTournamentsVisited <em>Number Of Tournaments Visited</em>}
	 * '.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Number Of Tournaments Visited</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Fan#getNumberOfTournamentsVisited()
	 * @see #getFan()
	 * @generated
	 */
	EAttribute getFan_NumberOfTournamentsVisited();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Fan#getMoneySpentOnTickets
	 * <em>Money Spent On Tickets</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Money Spent On Tickets</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Fan#getMoneySpentOnTickets()
	 * @see #getFan()
	 * @generated
	 */
	EAttribute getFan_MoneySpentOnTickets();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.bowling.Ticket <em>Ticket</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Ticket</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Ticket
	 * @generated
	 */
	EClass getTicket();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Ticket#getVenue
	 * <em>Venue</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Venue</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Ticket#getVenue()
	 * @see #getTicket()
	 * @generated
	 */
	EAttribute getTicket_Venue();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.bowling.Ticket#getAntiTheftModule <em>Anti Theft Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Anti Theft Module</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Ticket#getAntiTheftModule()
	 * @see #getTicket()
	 * @generated
	 */
	EReference getTicket_AntiTheftModule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.bowling.SeasonTicket <em>Season Ticket</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Season Ticket</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.SeasonTicket
	 * @generated
	 */
	EClass getSeasonTicket();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.SeasonTicket#getFrom
	 * <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>From</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.SeasonTicket#getFrom()
	 * @see #getSeasonTicket()
	 * @generated
	 */
	EAttribute getSeasonTicket_From();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.SeasonTicket#getTo
	 * <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>To</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.SeasonTicket#getTo()
	 * @see #getSeasonTicket()
	 * @generated
	 */
	EAttribute getSeasonTicket_To();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.bowling.DayTicket <em>Day Ticket</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Day Ticket</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.DayTicket
	 * @generated
	 */
	EClass getDayTicket();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.DayTicket#getDate
	 * <em>Date</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Date</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.DayTicket#getDate()
	 * @see #getDayTicket()
	 * @generated
	 */
	EAttribute getDayTicket_Date();

	/**
	 * Returns the meta object for the containment reference list '
	 * {@link org.eclipse.emf.emfstore.bowling.Fan#getFanMerchandise <em>Fan Merchandise</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference list '<em>Fan Merchandise</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Fan#getFanMerchandise()
	 * @see #getFan()
	 * @generated
	 */
	EReference getFan_FanMerchandise();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.bowling.Fan#getFavouriteMerchandise <em>Favourite Merchandise</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Favourite Merchandise</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Fan#getFavouriteMerchandise()
	 * @see #getFan()
	 * @generated
	 */
	EReference getFan_FavouriteMerchandise();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.emf.emfstore.bowling.Fan#getTicket
	 * <em>Ticket</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Ticket</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Fan#getTicket()
	 * @see #getFan()
	 * @generated
	 */
	EReference getFan_Ticket();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.bowling.Merchandise <em>Merchandise</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Merchandise</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Merchandise
	 * @generated
	 */
	EClass getMerchandise();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Merchandise#getName
	 * <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Merchandise#getName()
	 * @see #getMerchandise()
	 * @generated
	 */
	EAttribute getMerchandise_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Merchandise#getPrice
	 * <em>Price</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Price</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Merchandise#getPrice()
	 * @see #getMerchandise()
	 * @generated
	 */
	EAttribute getMerchandise_Price();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Merchandise#getSerialNumber
	 * <em>Serial Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Serial Number</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Merchandise#getSerialNumber()
	 * @see #getMerchandise()
	 * @generated
	 */
	EAttribute getMerchandise_SerialNumber();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.bowling.Merchandise#getChip <em>Chip</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Chip</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Merchandise#getChip()
	 * @see #getMerchandise()
	 * @generated
	 */
	EReference getMerchandise_Chip();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.bowling.AntiTheftChip
	 * <em>Anti Theft Chip</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Anti Theft Chip</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.AntiTheftChip
	 * @generated
	 */
	EClass getAntiTheftChip();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.AntiTheftChip#getDescription
	 * <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.AntiTheftChip#getDescription()
	 * @see #getAntiTheftChip()
	 * @generated
	 */
	EAttribute getAntiTheftChip_Description();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.bowling.AntiTheftChip#getModule <em>Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Module</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.AntiTheftChip#getModule()
	 * @see #getAntiTheftChip()
	 * @generated
	 */
	EReference getAntiTheftChip_Module();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.bowling.Module <em>Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Module</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Module
	 * @generated
	 */
	EClass getModule();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.emf.emfstore.bowling.Module#getDescription
	 * <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Module#getDescription()
	 * @see #getModule()
	 * @generated
	 */
	EAttribute getModule_Description();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.bowling.GPSModule <em>GPS Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>GPS Module</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.GPSModule
	 * @generated
	 */
	EClass getGPSModule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.bowling.ElectroMagneticModule
	 * <em>Electro Magnetic Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Electro Magnetic Module</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.ElectroMagneticModule
	 * @generated
	 */
	EClass getElectroMagneticModule();

	/**
	 * Returns the meta object for class '{@link org.eclipse.emf.emfstore.bowling.TwoInOneModule
	 * <em>Two In One Module</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for class '<em>Two In One Module</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.TwoInOneModule
	 * @generated
	 */
	EClass getTwoInOneModule();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.bowling.TwoInOneModule#getModule1 <em>Module1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Module1</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.TwoInOneModule#getModule1()
	 * @see #getTwoInOneModule()
	 * @generated
	 */
	EReference getTwoInOneModule_Module1();

	/**
	 * Returns the meta object for the containment reference '
	 * {@link org.eclipse.emf.emfstore.bowling.TwoInOneModule#getModule2 <em>Module2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for the containment reference '<em>Module2</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.TwoInOneModule#getModule2()
	 * @see #getTwoInOneModule()
	 * @generated
	 */
	EReference getTwoInOneModule_Module2();

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
	 * Returns the meta object for enum '{@link org.eclipse.emf.emfstore.bowling.Gender <em>Gender</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @return the meta object for enum '<em>Gender</em>'.
	 * @see org.eclipse.emf.emfstore.bowling.Gender
	 * @generated
	 */
	EEnum getGender();

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
		 * The meta object literal for the '<em><b>EMails</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PLAYER__EMAILS = eINSTANCE.getPlayer_EMails();

		/**
		 * The meta object literal for the '<em><b>Number Of Victories</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PLAYER__NUMBER_OF_VICTORIES = eINSTANCE.getPlayer_NumberOfVictories();

		/**
		 * The meta object literal for the '<em><b>Played Tournament Types</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PLAYER__PLAYED_TOURNAMENT_TYPES = eINSTANCE.getPlayer_PlayedTournamentTypes();

		/**
		 * The meta object literal for the '<em><b>Win Loss Ratio</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PLAYER__WIN_LOSS_RATIO = eINSTANCE.getPlayer_WinLossRatio();

		/**
		 * The meta object literal for the '<em><b>Gender</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute PLAYER__GENDER = eINSTANCE.getPlayer_Gender();

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
		 * The meta object literal for the '<em><b>Price Money</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TOURNAMENT__PRICE_MONEY = eINSTANCE.getTournament_PriceMoney();

		/**
		 * The meta object literal for the '<em><b>Receives Trophy</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TOURNAMENT__RECEIVES_TROPHY = eINSTANCE.getTournament_ReceivesTrophy();

		/**
		 * The meta object literal for the '<em><b>Match Days</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TOURNAMENT__MATCH_DAYS = eINSTANCE.getTournament_MatchDays();

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
		 * The meta object literal for the '<em><b>Nr Spectators</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MATCHUP__NR_SPECTATORS = eINSTANCE.getMatchup_NrSpectators();

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
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.FanImpl <em>Fan</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.FanImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getFan()
		 * @generated
		 */
		EClass FAN = eINSTANCE.getFan();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FAN__NAME = eINSTANCE.getFan_Name();

		/**
		 * The meta object literal for the '<em><b>Date Of Birth</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FAN__DATE_OF_BIRTH = eINSTANCE.getFan_DateOfBirth();

		/**
		 * The meta object literal for the '<em><b>Has Season Ticket</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FAN__HAS_SEASON_TICKET = eINSTANCE.getFan_HasSeasonTicket();

		/**
		 * The meta object literal for the '<em><b>EMails</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FAN__EMAILS = eINSTANCE.getFan_EMails();

		/**
		 * The meta object literal for the '<em><b>Gender</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FAN__GENDER = eINSTANCE.getFan_Gender();

		/**
		 * The meta object literal for the '<em><b>Favourite Player</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FAN__FAVOURITE_PLAYER = eINSTANCE.getFan_FavouritePlayer();

		/**
		 * The meta object literal for the '<em><b>Visited Tournaments</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FAN__VISITED_TOURNAMENTS = eINSTANCE.getFan_VisitedTournaments();

		/**
		 * The meta object literal for the '<em><b>Number Of Tournaments Visited</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FAN__NUMBER_OF_TOURNAMENTS_VISITED = eINSTANCE.getFan_NumberOfTournamentsVisited();

		/**
		 * The meta object literal for the '<em><b>Money Spent On Tickets</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute FAN__MONEY_SPENT_ON_TICKETS = eINSTANCE.getFan_MoneySpentOnTickets();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.TicketImpl <em>Ticket</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.TicketImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getTicket()
		 * @generated
		 */
		EClass TICKET = eINSTANCE.getTicket();

		/**
		 * The meta object literal for the '<em><b>Venue</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute TICKET__VENUE = eINSTANCE.getTicket_Venue();

		/**
		 * The meta object literal for the '<em><b>Anti Theft Module</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TICKET__ANTI_THEFT_MODULE = eINSTANCE.getTicket_AntiTheftModule();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.SeasonTicketImpl
		 * <em>Season Ticket</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.SeasonTicketImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getSeasonTicket()
		 * @generated
		 */
		EClass SEASON_TICKET = eINSTANCE.getSeasonTicket();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SEASON_TICKET__FROM = eINSTANCE.getSeasonTicket_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute SEASON_TICKET__TO = eINSTANCE.getSeasonTicket_To();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.DayTicketImpl
		 * <em>Day Ticket</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.DayTicketImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getDayTicket()
		 * @generated
		 */
		EClass DAY_TICKET = eINSTANCE.getDayTicket();

		/**
		 * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute DAY_TICKET__DATE = eINSTANCE.getDayTicket_Date();

		/**
		 * The meta object literal for the '<em><b>Fan Merchandise</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FAN__FAN_MERCHANDISE = eINSTANCE.getFan_FanMerchandise();

		/**
		 * The meta object literal for the '<em><b>Favourite Merchandise</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FAN__FAVOURITE_MERCHANDISE = eINSTANCE.getFan_FavouriteMerchandise();

		/**
		 * The meta object literal for the '<em><b>Ticket</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference FAN__TICKET = eINSTANCE.getFan_Ticket();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.MerchandiseImpl
		 * <em>Merchandise</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.MerchandiseImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getMerchandise()
		 * @generated
		 */
		EClass MERCHANDISE = eINSTANCE.getMerchandise();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MERCHANDISE__NAME = eINSTANCE.getMerchandise_Name();

		/**
		 * The meta object literal for the '<em><b>Price</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MERCHANDISE__PRICE = eINSTANCE.getMerchandise_Price();

		/**
		 * The meta object literal for the '<em><b>Serial Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MERCHANDISE__SERIAL_NUMBER = eINSTANCE.getMerchandise_SerialNumber();

		/**
		 * The meta object literal for the '<em><b>Chip</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference MERCHANDISE__CHIP = eINSTANCE.getMerchandise_Chip();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.AntiTheftChipImpl
		 * <em>Anti Theft Chip</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.AntiTheftChipImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getAntiTheftChip()
		 * @generated
		 */
		EClass ANTI_THEFT_CHIP = eINSTANCE.getAntiTheftChip();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute ANTI_THEFT_CHIP__DESCRIPTION = eINSTANCE.getAntiTheftChip_Description();

		/**
		 * The meta object literal for the '<em><b>Module</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference ANTI_THEFT_CHIP__MODULE = eINSTANCE.getAntiTheftChip_Module();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.ModuleImpl <em>Module</em>}'
		 * class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.ModuleImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getModule()
		 * @generated
		 */
		EClass MODULE = eINSTANCE.getModule();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EAttribute MODULE__DESCRIPTION = eINSTANCE.getModule_Description();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.GPSModuleImpl
		 * <em>GPS Module</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.GPSModuleImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getGPSModule()
		 * @generated
		 */
		EClass GPS_MODULE = eINSTANCE.getGPSModule();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.ElectroMagneticModuleImpl
		 * <em>Electro Magnetic Module</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.ElectroMagneticModuleImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getElectroMagneticModule()
		 * @generated
		 */
		EClass ELECTRO_MAGNETIC_MODULE = eINSTANCE.getElectroMagneticModule();

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.impl.TwoInOneModuleImpl
		 * <em>Two In One Module</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.impl.TwoInOneModuleImpl
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getTwoInOneModule()
		 * @generated
		 */
		EClass TWO_IN_ONE_MODULE = eINSTANCE.getTwoInOneModule();

		/**
		 * The meta object literal for the '<em><b>Module1</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TWO_IN_ONE_MODULE__MODULE1 = eINSTANCE.getTwoInOneModule_Module1();

		/**
		 * The meta object literal for the '<em><b>Module2</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @generated
		 */
		EReference TWO_IN_ONE_MODULE__MODULE2 = eINSTANCE.getTwoInOneModule_Module2();

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

		/**
		 * The meta object literal for the '{@link org.eclipse.emf.emfstore.bowling.Gender <em>Gender</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 * @see org.eclipse.emf.emfstore.bowling.Gender
		 * @see org.eclipse.emf.emfstore.bowling.impl.BowlingPackageImpl#getGender()
		 * @generated
		 */
		EEnum GENDER = eINSTANCE.getGender();

	}

} // BowlingPackage