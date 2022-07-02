package it.unipr.accounts.personalinformation;

import java.io.Serializable;
import java.util.HashMap;

/**
 * The {@code Address} class through its attributes describes the position of a house in a city
 *
 * @author Mamone Maximiliano 308214
 * @author Tanzi  Manuel      307720
 * @version 1.0
 * @since 1.0
 */
public class Address implements Serializable {
    private String street;
    private String city;
    private String province;
    private String country;
    private String postalCode;

    /**
     * Class default constructor
     */
    public Address() {
    }

    /**
     * Class specific constructor
     *
     * @param values {@code HashMap<String, String>} contains all the information to create the Address
     */
    public Address(HashMap<String, String> values) {
        this.street = values.get("street");
        this.city = values.get("city");
        this.country = values.get("country");
        this.province = values.get("province");
        this.postalCode = values.get("postalcode");
    }

    /**
     * Parametrized Constructor
     *
     * @param street     {@code String} contains the name of the street
     * @param city       {@code String} contains the name of the city
     * @param province   {@code String} contains the abbreviation of the province
     * @param country    {@code String} contains the name of the country
     * @param postalCode {@code String} contains the postal code
     */
    public Address(String street, String city, String province, String country, String postalCode) {
        this.street = street;
        this.city = city;
        this.province = province;
        this.country = country;
        this.postalCode = postalCode;
    }

    /**
     * Get Method of the {@code street} attribute
     *
     * @return {@code String} - street attribute's value
     */
    public String getStreet() {
        return street;
    }

    /**
     * Set Method of the {@code street} attribute
     *
     * @param street {@code String} - new value to set to the attribute
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Get Method of the {@code city} attribute
     *
     * @return {@code String} - city attribute's value
     */
    public String getCity() {
        return city;
    }

    /**
     * Set Method of the {@code city} attribute
     *
     * @param city {@code String} - new value to set to the attribute
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Get Method of the {@code province} attribute
     *
     * @return {@code String} - province attribute's value
     */
    public String getProvince() {
        return province;
    }

    /**
     * Set Method of the {@code province} attribute
     *
     * @param province {@code String} - new value to set to the attribute
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Get Method of the {@code country} attribute
     *
     * @return {@code String} - country attribute's value
     */
    public String getCountry() {
        return country;
    }

    /**
     * Set Method of the {@code country} attribute
     *
     * @param country {@code String} - new value to set to the attribute
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Get Method of the {@code postalCode} attribute
     *
     * @return {@code postalCode} - street attribute's value
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Set Method of the {@code postalCode} attribute
     *
     * @param postalCode {@code String} - new value to set to the attribute
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Default toString method of the class
     *
     * @return {@code String} - contains the value of all the attributes of the object
     */
    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
