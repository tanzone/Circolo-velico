package it.unipr.accounts;

import it.unipr.accounts.personalinformation.Address;
import it.unipr.accounts.personalinformation.CreditCard;
import it.unipr.accounts.personalinformation.Notification;
import it.unipr.accounts.personalinformation.Person;
import it.unipr.boat.Boat;
import it.unipr.race.Race;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * The {@code Member} class through its attributes describes the member of the club.
 *
 * @author Mamone Maximiliano 308214
 * @author Tanzi  Manuel      307720
 * @version 1.0
 * @since 1.0
 */
public class Member extends Person implements Serializable {
    private String username;
    private String password;
    private List<Boat> boats;
    private List<Notification> notifications;
    private String season;
    private List<CreditCard> creditCards;
    private List<Race> races;

    /**
     * Class default constructor
     */
    public Member() {
    }

    /**
     * Class copy constructor
     *
     * @param m {@code Member} object to copy
     */
    public Member(Member m) {
        super(m.getFirstName(), m.getLastName(), m.getAddress(), m.getFiscalCode());
        this.username = m.getUsername();
        this.password = m.getPassword();
        this.season = m.getSeason();
    }

    /**
     * Class specific constructor
     *
     * @param values {@code HashMap<String, String>} containing the information for the member to be created
     */
    public Member(HashMap<String, String> values) {
        super(values.get("firstname"), values.get("lastname"), new Address(), values.get("fiscalCode"));
        this.username = values.get("username");
        this.password = values.get("password");
        try {
            this.season = values.get("seasonPass");
        } catch (Exception e) {
            this.season = "2000-01-01";
        }
    }

    /**
     * Getter of the username attribute
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Setter of the username attribute
     *
     * @param username username to be set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter of the password attribute
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter of the password attribute
     *
     * @param password password to be set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter of the boats attribute
     *
     * @return {@code Boat} list
     */
    public List<Boat> getBoats() {
        return boats;
    }

    /**
     * Setter of the boats attribute
     *
     * @param boats boats' list to be set
     */
    public void setBoats(List<Boat> boats) {
        this.boats = boats;
    }


    /**
     * Getter of the creditCards attribute
     *
     * @return {@code CreditCard} list
     */
    public List<CreditCard> getCreditCards() {
        return creditCards;
    }

    /**
     * Setter of the creditCards attribute
     *
     * @param creditCards creditCards' to be set
     */
    public void setCreditCards(List<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    /**
     * Getter of the notifications attribute
     *
     * @return notifications' list
     */
    public List<Notification> getNotifications() {
        return notifications;
    }

    /**
     * Setter of the notifications attribute
     *
     * @param notifications notifications' list to be set
     */
    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }

    /**
     * Getter of the season attribute
     *
     * @return expire date of season pass
     */
    public String getSeason() {
        return season;
    }

    /**
     * Setter of the season attribute
     *
     * @param season expire date to be set
     */
    public void setSeason(String season) {
        this.season = season;
    }

    /**
     * Method that adds a boat to the boats attribute
     *
     * @param b {@code Boat} to be added
     */
    public void addBoat(Boat b) {
        this.boats.add(b);
    }

    /**
     * Method that adds a credit card to the creditCards attribute
     *
     * @param c {@code CreditCard} to be added
     */
    public void addCreditCard(CreditCard c) {
        this.creditCards.add(c);
    }

    /**
     * Method that updates the member address
     *
     * @param a {@code Address} to be set
     */
    public void updateAddress(Address a) {
        super.setAddress(a);
    }

    /**
     * Method that deletes a boat from the boats attribute
     *
     * @param toDelete {@code Boat} to be deleted
     */
    public void deleteBoat(Boat toDelete) {
        for (Boat b : this.boats)
            if (b.getName().equals(toDelete.getName()) && b.getLength() == toDelete.getLength()) {
                this.boats.remove(b);
                return;
            }
    }

    /**
     * Method that sets the boat payment status
     *
     * @param paid status to be set
     */
    public void payBoat(Boat paid) {
        for (Boat b : this.boats)
            if (b.getName().equals(paid.getName()) && b.getLength() == paid.getLength())
                b.setPaid(true);
    }

    /**
     * ToString of the class
     *
     * @return Object as string
     */
    @Override
    public String toString() {
        return "Member{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", boats=" + boats +
                ", notifications=" + notifications +
                ", season='" + season + '\'' +
                ", creditCards=" + creditCards +
                '}';
    }

    /**
     * Getter of the races attribute
     *
     * @return {@code Race} list
     */
    public List<Race> getRaces() {
        return races;
    }

    /**
     * Setter of the races attribute
     *
     * @param races races to be set
     */
    public void setRaces(List<Race> races) {
        this.races = races;
    }

    /**
     * Method that adds a race to the races attribute
     *
     * @param race {@code Race} to be added
     */
    public void addRace(Race race) {
        this.races.add(race);
    }

    /**
     * Method that deletes a race from the races attribute
     *
     * @param r {@code Race} to delete
     */
    public void delRace(Race r) {
        this.races.remove(r);
    }

    /**
     * Method that returns the city the member lives in
     *
     * @return city
     */
    public String getCity() {
        return super.getAddress().getCity();
    }

    /**
     * Method that deletes a credit card form the creditCards attribute
     *
     * @param cc {@code CreditCard} to remove
     */
    public void deleteCreditCard(CreditCard cc) {
        this.creditCards.remove(cc);
    }
}
