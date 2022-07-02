package it.unipr.constants;

/**
 * The {@code Constants} class defines the utilities needed for the proper functioning of the software
 *
 * @author Tanzi  Manuel       307720
 * @author Mamone Maximiliano  308214
 * @version 1.0
 * @since 1.0
 */
public final class Constants {
    /**
     * Constant port used for connection
     */
    public static final int LISTEN_PORT = 4444;
    /**
     * Constant max number of simultanous connections
     */
    public static final int MAX_CONNECTION = 10;
    /**
     * Constant message null
     */
    public static final String MSG_NULL = "NULL";
    /**
     * Constant message fail
     */
    public static final String MSG_FAIL = "FAIL";
    //-----------------------------
    //STANDARD PROTOCOL------------
    //-----------------------------
    /**
     * Constant message fake
     */
    public static final String MSG_FAKE = "FAKE";
    /**
     * Constant message ok
     */
    public static final String MSG_OK = "OK";
    /**
     * Constant message connection error
     */
    public static final String MSG_NOCONNECTION = "NO_CONN";
    /**
     * Constant protocol separator *Not used*
     */
    public static final String SEP_PROT = ";";
    /**
     * Constant parameter separator
     */
    public static final String SEP_PARAM = "#";
    /**
     * Constant protocol for user login
     */
    public static final String PROT_LOGIN_USER = "USER_LOGIN";
    /**
     * Constant protocol for staff login
     */
    public static final String PROT_LOGIN_STAFF = "STAFF_LOGIN";
    /**
     * Constant protocol that checks if the user already exists
     */
    public static final String PROT_SIGNUP_CHECK = "CHECK_SIGNUP";
    /**
     * Constant protocol that signs the user up
     */
    public static final String PROT_SIGNUP_USER = "USER_SIGNUP";
    /**
     * Constant protocol that loads all the races the user is not subscribed to
     */
    public static final String PROT_NOTSUBRACE_LOAD = "LOAD_NSRACES";
    /**
     * Constant protocol that loads all the races the user is subscribed to
     */
    public static final String PROT_SUBRACE_LOAD = "LOAD_SRACES";
    /**
     * Constant protocol that subscribes the user to a race
     */
    public static final String PROT_SUBSCRIBE_RACE = "SUBSCRIBE_RACE";
    /**
     * Constant protocol that deletes the user subscription tu a race
     */
    public static final String PROT_DELETE_SUBSCRIPTION = "DELETE_SUBSCRIPTION";
    /**
     * Constant protocol that sets a new address for a user
     */
    public static final String PROT_SET_ADDRESS = "SET_ADDRESS";
    /**
     * Constant protocol that gets the user address
     */
    public static final String PROT_GET_ADDRESS = "GET_ADDRESS";
    /**
     * Constant protocol that gets a list of all members
     */
    public static final String PROT_GET_ALLMEMBERS = "GET_ALLMEMBER";
    /**
     * Constant protocol that gets all races
     */
    public static final String PROT_GET_ALLRACES = "GET_ALLRACES";
    /**
     * Constant protocol that adds a new race
     */
    public static final String PROT_ADD_RACE = "ADD_RACE";
    /**
     * Constant protocol that gest all transactions
     */
    public static final String PROT_GET_ALLTRANS = "GET_ALLTRANS";
    /**
     * Constant protocol that gets the boat a user subscribed to a race with
     */
    public static final String PROT_GET_SUBSCRIBEDBOAT = "GET_BOAT";
    /**
     * Constant protocol that gets all user's boats
     */
    public static final String PROT_BOAT_LOAD = "LOAD_BOATS";
    /**
     * Constant protocol that gets all boats
     */
    public static final String PROT_GET_ALLBOATS = "GET_ALLBOAT";
    /**
     * Constant protocol that deletes a boat
     */
    public static final String PROT_DELETE_BOAT = "DELETE_BOAT";
    /**
     * Constant protocol that adds a boat
     */
    public static final String PROT_ADD_BOAT = "ADD_BOAT";
    /**
     * Constant protocol that updates a boat payment status
     */
    public static final String PROT_UPDATE_BOAT = "UPDATE_BOAT";
    /**
     * Constant protocol that gets a credit card
     */
    public static final String PROT_GET_CARD = "GET_CARD";
    /**
     * Constant protocol that adds a new credit card
     */
    public static final String PROT_ADD_CARD = "ADD_CARD";
    /**
     * Constant protocol that deletes a credit card
     */
    public static final String PROT_DELETE_CARD = "DELETE_CARD";
    /**
     * Constant protocol that adds a notification
     */
    public static final String PROT_ADD_NOTIFICATION = "ADD_NOTIFICATION";
    /**
     * Constant protocol that gets a notification
     */
    public static final String PROT_GET_NOTIFICATION = "GET_NOTIFICATION";
    /**
     * Constant protocol that adds a transaction
     */
    public static final String PROT_ADD_TRANSACTION = "ADD_TRANSACTION";
    /**
     * Constant protocol that deletes a race
     */
    public static final String PROT_DELETE_RACE = "DELETE_RACE";
    /**
     * Constant protocol that gets a list of all the members with expired season pass
     */
    public static final String PROT_GET_EXPBMEMBER = "GET_EXPBMEMBER";
    /**
     * Constant protocol that deletes a notification
     */
    public static final String PROT_DELETE_NOTIFICATION = "DELETE_NOTIFICATION";
    /**
     * Constant protocol that updates a member's season pass payment status
     */
    public static final String PROT_UPDATE_MEMBER = "UPDATE_MEMBER";
    /**
     * Constant protocol that gets all completed races
     */
    public static final String PROT_GET_DONERACES = "GET_DONERACE";
    /**
     * Constant protocol gets all members subscribed to a race
     */
    public static final String PROT_GET_ALLSUB = "GET_ALLSUB";
    /**
     * Constant protocol that sets a winner for a race
     */
    public static final String PROT_SET_WINNER = "SET_WINNER";
    /**
     * Constant protocol that sets unpaid all expired boats' passes
     */
    public static final String PROT_UPDATE_EXPBOAT = "UPDATE_EXPBOAT";
    /**
     * Constant protocol that deletes a member
     */
    public static final String PROT_DELETE_MEMBER = "DELETE_MEMBER";
    /**
     * Constant that contains the database address
     */
    public static final String SQL_SERVER = "jdbc:mysql://localhost:3306/lunarossa";
    /**
     * Constant that contains the database username
     */
    public static final String SQL_USER = "admin";
    //-----------------------------
    //SQL DATE         ------------
    //-----------------------------
    /**
     * Constant thta contains the database password
     */
    public static final String SQL_PASS = "admin";
    /**
     * Constant array of string with the boat parameters
     */
    public static final String[] BOATS_PARAMS = {"Boat", "id", "name", "length", "storageCost", "username", "paid", "expireDate"};
    /**
     * Constant array of string with the member parameters
     */
    public static final String[] MEMBER_PARAMS = {"Member", "username", "password", "personId", "firstname", "lastname", "fiscalCode", "seasonPass"};
    //-----------------------------
    //SQL Params       ------------
    //-----------------------------
    /**
     * Constant array of string with the staff parameters
     */
    public static final String[] STAFF_PARAMS = {"Staff", "username", "password", "personId", "firstname", "lastname", "fiscalCode", "admin"};
    /**
     * Constant array of string with the race parameters
     */
    public static final String[] RACE_PARAMS = {"Race", "id", "name", "date", "winner", "winnerboat", "price"};
    /**
     * Constant array of string with the address parameters
     */
    public static final String[] ADDRESS_PARAMS = {"Address", "id", "street", "city", "province", "country", "postalcode"};
    /**
     * Constant array of string with the trans parameters
     */
    public static final String[] TRANS_PARAMS = {"Transaction", "id", "motive", "total", "method", "username", "date"};
    /**
     * Constant array of string with the credit parameters
     */
    public static final String[] CREDIT_PARAMS = {"CreditCard", "number", "name", "lastname", "date", "cvc"};
    /**
     * Constant array of string with the notification parameters
     */
    public static final String[] NOTIFICATION_PARAMS = {"Notification", "description", "color"};
    /**
     * Constant that contains the price per meter of a boat
     */
    public static final double PRICE_X_METER = 2;
    /**
     * Constant that contains the price of the season pass
     */
    public static final double PRICE_SEASON_PASS = 500;
    //-----------------------------
    //CONSTANTS PROGRAM------------
    //-----------------------------
    /**
     * Constant transfer method
     */
    public static final String TRANSFER = "TRANSFER";
    /**
     * Constant error about missing fields in login or signup
     */
    public static final String MISSING_FIELDS = "Please fill all text fields";
    /**
     * Constant error about wrong credentials
     */
    public static final String WRONG_CREDENTIALS = "Something went wrong!  Invalid Username or Password";
    //-----------------------------
    //ERROR MESSAGES   ------------
    //-----------------------------
    /**
     * Constant error about no connection status
     */
    public static final String NO_CONNECTION = "Cannot establish connection with server";
    /**
     * Variable containing the total number of requests
     */
    public static int NUM_REQUEST = 0;
    /**
     * Variable containing the present number of total connections
     */
    public static int PRESENT_CONNECTION = 0;


}
