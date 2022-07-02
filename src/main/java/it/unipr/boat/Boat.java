package it.unipr.boat;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

/**
 * Class that manages all the information about a Boat
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @since 1.0
 */
public class Boat implements Serializable {
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String id;
    private String name;
    private double length;
    private double price;
    private boolean paid;
    private String owner;
    private Date expireDate;


    /**
     * Class parametrized constructor
     *
     * @param id     id to be set
     * @param name   name to be set
     * @param length length to be set
     * @param price  price to be set
     * @param paid   payment status to be set
     * @param owner  owner to be set
     */
    public Boat(String id, String name, double length, double price, boolean paid, String owner) {
        this.id = id;
        this.name = name;
        this.length = length;
        this.price = price;
        this.paid = paid;
        this.owner = owner;
    }

    /**
     * Class copy constructor
     *
     * @param b {@code Boat} object to be copied
     */
    public Boat(Boat b) {
        this.id = b.getId();
        this.name = b.getName();
        this.length = b.getLength();
        this.price = b.getPrice();
        this.paid = b.isPaid();
        this.owner = b.getOwner();
    }

    /**
     * Class specific constructor
     *
     * @param values {@code HashMap<String, String>} containing all the information to create the new Boat
     */
    public Boat(HashMap<String, String> values) {
        this.id = values.get("id");
        this.name = values.get("name");
        this.length = Double.parseDouble(values.get("length"));
        this.price = Double.parseDouble(values.get("storageCost"));
        this.owner = values.get("username");
        String paid = values.get("paid");
        this.paid = !Objects.equals(paid, "0");
        try {
            this.expireDate = sdf.parse(values.get("expireDate"));
        } catch (Exception e) {
            this.expireDate = null;
        }
    }

    /**
     * Class default constructor
     */
    public Boat() {
    }

    /**
     * Getter of the id attribute
     *
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter of the id attribute
     *
     * @param id id to be set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter of the name attribute
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name attribute
     *
     * @param name name to be set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of the length attribute
     *
     * @return length
     */
    public double getLength() {
        return length;
    }

    /**
     * Setter of the length attribute
     *
     * @param length length to be set
     */
    public void setLength(double length) {
        this.length = length;
    }

    /**
     * Getter of the price attribute
     *
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter of the price attribute
     *
     * @param price price to be set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter of the paid attribute
     *
     * @return true if the boat has been paid, false otherwise
     */
    public boolean isPaid() {
        return paid;
    }

    /**
     * Setter of the paid attribute
     *
     * @param paid status to be set
     */
    public void setPaid(boolean paid) {
        this.paid = paid;
    }


    /**
     * ToString method for the class
     *
     * @return Object as String
     */
    @Override
    public String toString() {
        return "Boat{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", length=" + length +
                ", price=" + price +
                ", paid=" + paid +
                '}';
    }

    /**
     * Getter of the owner attribute
     *
     * @return owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * Setter of the owner attribute
     *
     * @param owner owner to be set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * Getter of the expireDate attribute
     *
     * @return expireDate
     */
    public Date getExpireDate() {
        return expireDate;
    }

    /**
     * Setter of the expireDate attribute
     *
     * @param expireDate expireDate to be set
     */
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}
