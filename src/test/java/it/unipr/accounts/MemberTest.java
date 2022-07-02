package it.unipr.accounts;

import it.unipr.accounts.personalinformation.Address;
import it.unipr.accounts.personalinformation.CreditCard;
import it.unipr.boat.Boat;
import it.unipr.race.Race;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MemberTest {
    Member m = null;

    @BeforeEach
    void setUp() {
        m = new Member();
        m.setUsername("carlo12");
        m.setPassword("1234");

        m.setAddress(new Address("Via Lazzaro 18", "Milano", "MI", "Italia", "41022"));

        List<Boat> boats = new ArrayList<>();
        Random rand = new Random();
        Double value = rand.nextDouble(30);
        for (Integer i = 0; i < 10; i++, value = rand.nextDouble(30))
            boats.add(new Boat(i.toString(), "Barca" + i, value, 2 * value, (value >= 15), m.getUsername()));
        m.setBoats(boats);

        List<CreditCard> cards = new ArrayList<>();
        Integer numCard = rand.nextInt(2147483647);
        for (Integer i = 0; i < 10; i++, numCard = rand.nextInt(2147483647))
            cards.add(new CreditCard(numCard.toString(), "Carlo", "Rossi", "2024-10-10", "123"));
        m.setCreditCards(cards);

        List<Race> races = new ArrayList<>();
        Integer id = rand.nextInt(2147483647);
        for (Integer i = 0; i < 10; i++, id = rand.nextInt(2147483647))
            races.add(new Race(id.toString(), "Gara" + i, "2022-04-22", "TBD", "TBD", "100"));
        m.setRaces(races);

    }

    @Test
    void addBoat() {
        List<Boat> boats = m.getBoats();
        boats.add(new Boat("10", "Barca10", 10, 2 * 10, false, m.getUsername()));

        m.addBoat(new Boat("10", "Barca10", 10, 2 * 10, false, m.getUsername()));
        List<Boat> boats1 = m.getBoats();

        assertArrayEquals(boats.toArray(), boats1.toArray(), "Lists should be equal");
    }


    @Test
    void addCreditCard() {
        List<CreditCard> cards = m.getCreditCards();
        cards.add(new CreditCard("1234567890", "Angelo", "Verdi", "2023-11-12", "123"));

        m.addCreditCard(new CreditCard("1234567890", "Angelo", "Verdi", "2023-11-12", "123"));
        List<CreditCard> cards1 = m.getCreditCards();

        assertArrayEquals(cards.toArray(), cards1.toArray());
    }

    @Test
    void updateAddress() {
        Member m1 = new Member();
        m1.setAddress(new Address("Via Ramingo 9", "Napoli", "NP", "Italia", "41022"));
        m.updateAddress(new Address("Via Ramingo 9", "Napoli", "NP", "Italia", "41022"));
        assertEquals(m.getAddress().toString(), m1.getAddress().toString());
    }

    @Test
    void deleteBoat() {
        List<Boat> boats = m.getBoats();
        boats.remove(boats.size() - 1);


        m.deleteBoat(m.getBoats().get(m.getBoats().size() - 1));
        assertArrayEquals(m.getBoats().toArray(), boats.toArray());
    }

    @Test
    void payBoat() {
        List<Boat> boats = m.getBoats();
        for (Boat b : boats)
            b.setPaid(true);
        for (Boat b : m.getBoats())
            m.payBoat(b);

        assertArrayEquals(boats.toArray(), m.getBoats().toArray());
    }

    @Test
    void addRace() {
        List<Race> races = m.getRaces();
        races.add(new Race("12309123", "Gara20", "2022-05-30", "TBD", "TBD", "200"));

        m.addRace(new Race("12309123", "Gara20", "2022-05-30", "TBD", "TBD", "200"));
        assertArrayEquals(races.toArray(), m.getRaces().toArray());
    }

    @Test
    void delRace() {
        List<Race> races = m.getRaces();
        races.remove(races.size() - 1);


        m.delRace(m.getRaces().get(m.getRaces().size() - 1));
        assertArrayEquals(m.getRaces().toArray(), races.toArray());
    }

    @Test
    void getCity() {
        Address a = m.getAddress();
        assertEquals(a.getCity(), m.getCity());
    }
}