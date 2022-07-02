package it.unipr.controllers.dashboard;

import animatefx.animation.ZoomIn;
import it.unipr.accounts.Member;
import it.unipr.accounts.personalinformation.Address;
import it.unipr.accounts.personalinformation.CreditCard;
import it.unipr.accounts.personalinformation.Notification;
import it.unipr.boat.Boat;
import it.unipr.connection.Connection;
import it.unipr.controllers.login.SignInUpController;
import it.unipr.race.Race;
import it.unipr.transaction.Transaction;
import it.unipr.utilities.MyListener;
import it.unipr.utilities.Utilities;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static it.unipr.constants.Constants.*;

/**
 * Class that defines and manages the interactions of the user with the client user interface
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class DashboardController implements Initializable {
    @FXML
    private Label passPrice, passBonific, memberFirstname, memberLastname, memberFiscal;
    @FXML
    private TableView tableAll;
    @FXML
    private TableColumn namePrix, datePrix, pricePrix;
    @FXML
    private TableView tablePart;
    @FXML
    private TableColumn namePrix1, datePrix1;
    @FXML
    private Label nameRace, dateRace, priceRace, bonificCodeRace;
    @FXML
    private VBox boxPayment;
    @FXML
    private Label street, city, province, country, postal;
    @FXML
    private TextField txtStreet, txtCity, txtProvince, txtCountry, txtPostal;
    @FXML
    private TextField txtNumberCard, txtFirstCredit, txtLastCredit, txtDateCredit, txtCvcCredit;
    @FXML
    private StackPane stackPane;
    @FXML
    private Label nameBoatToPay, lenghtBoatToPay, priceBoatToPay, bonificCode,
            nameBoatToPay1, lenghtBoatToPay1, priceBoatToPay1, bonificCode1;
    @FXML
    private ChoiceBox<String> typePayment, typePayment1, typePaymentRace, passChoice, typeBoatRace;
    @FXML
    private TextField txtNameBoat, txtLenghtBoat;
    @FXML
    private GridPane gridpaneBoats, gridpanePay;
    @FXML
    private ScrollPane gridBoats, gridPayment;
    @FXML
    private ImageView imgBell;
    @FXML
    private Pane paneSettings, paneBoats, paneDashboard, panePrix, panePayment, paneAddBoat, panePayNow,
            paneAddCreditcard, paneClickBoat, paneAddRace, paneCheckPass;
    @FXML
    private Label username, txtPriceBoat;
    @FXML
    private AnchorPane parentPane;
    @FXML
    private Button btnDashboardBar, btnBoatBar, btnPrixBar, btnPayBar, btnSettBar, btnProfileBar, btnNotBar;

    private double x, y;
    private Member member;
    private boolean notOnOff, boatOnOff, prixOnOff, payOnOff, accOnOff, dashOnOff, addBoatOnOff, payNowBoatOnOff,
            addCreditOnOff, clickBoatOnOff, addRaceOnOff;
    private List<Pane> paneNot;
    private List<Image> imgListBoat, imgListCredit;
    private MyListener myListener;
    private Boat toDelete;
    private Race raceToPay;
    private Connection connection;
    private Thread threadNots;

    /**
     * Method used to set interal data recieved from login procedure
     *
     * @param member Object of class {@code Member} with the data of the logged user
     * @param ip     String containing the ip address of the server
     */
    public void setData(Member member, String ip) {
        String[] serverAddress = ip.split(":");
        connection = new Connection(serverAddress[0], Integer.parseInt(serverAddress[1]));

        this.member = new Member(member);
        this.member.setBoats(getBoatDB());
        this.member.setAddress(getAddressDB());
        this.member.setCreditCards(getCreditCardDB());
        this.member.setRaces(getSubRaceDB());
        this.member.setNotifications(getNotificationsDB());

        this.username.setText(this.member.getUsername());
        this.memberFirstname.setText(this.member.getFirstName());
        this.memberLastname.setText(this.member.getLastName());
        this.memberFiscal.setText(this.member.getFiscalCode());

        threadNots = new Thread(new ThreadNotification(ip, imgBell, this.member.getUsername()));
        threadNots.start();

        checkPass();
    }


    private List<Notification> getNotificationsDB() {
        return (List<Notification>) connection.doAction(PROT_GET_NOTIFICATION, member.getUsername());
    }

    private List<Boat> getBoatDB() {
        return (List<Boat>) connection.doAction(PROT_BOAT_LOAD, member.getUsername());
    }

    private List<Race> getNotSubRaceDB() {
        return (List<Race>) connection.doAction(PROT_NOTSUBRACE_LOAD, member.getUsername() + SEP_PARAM + LocalDateTime.now().toString());
    }

    private List<Race> getSubRaceDB() {
        return (List<Race>) connection.doAction(PROT_SUBRACE_LOAD, member.getUsername() + SEP_PARAM + LocalDateTime.now().toString());
    }

    private List<CreditCard> getCreditCardDB() {
        return (List<CreditCard>) connection.doAction(PROT_GET_CARD, member.getUsername());
    }

    //Listener for the various actions
    private MyListener setListener() {
        return new MyListener() {
            @Override
            public void clickOpenBoat(Boat b) {
                openBoat(b);
            }

            @Override
            public void clickAddBoat() {
                addBoat();
            }

            @Override
            public void clickAddCredit() {
                addCredit();
            }

            @Override
            public void clickOpenCredit(CreditCard c) {
                openCredit(c);
            }
        };
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        makeDragable();
        this.paneNot = new ArrayList<>();
        this.imgListBoat = new ArrayList<>();
        this.imgListBoat.add(new Image(Objects.requireNonNull(DashboardController.class.getResourceAsStream("sailing.png"))));
        this.imgListBoat.add(new Image(Objects.requireNonNull(DashboardController.class.getResourceAsStream("boatColored.png"))));
        this.imgListBoat.add(new Image(Objects.requireNonNull(DashboardController.class.getResourceAsStream("ship.png"))));

        this.imgListCredit = new ArrayList<>();
        this.imgListCredit.add(new Image(Objects.requireNonNull(DashboardController.class.getResourceAsStream("maestro.png"))));
        this.imgListCredit.add(new Image(Objects.requireNonNull(DashboardController.class.getResourceAsStream("visa.png"))));

        this.myListener = this.setListener();

        validatorTextFields();
        this.txtPriceBoat.setText(PRICE_X_METER + " x METER");
        setTables();
    }

    private void setTables() {
        namePrix.setCellValueFactory(new PropertyValueFactory<>("name"));
        datePrix.setCellValueFactory(new PropertyValueFactory<>("date"));
        pricePrix.setCellValueFactory(new PropertyValueFactory<>("price"));
        namePrix1.setCellValueFactory(new PropertyValueFactory<>("name"));
        datePrix1.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    //Method that creates the transparent effect upon dragging the window
    private void makeDragable() {
        parentPane.setOnMousePressed(((event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        }));

        parentPane.setOnMouseDragged(((event) -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
            stage.setOpacity(0.8f);
        }));

        parentPane.setOnDragDone(((event) -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setOpacity(1.0f);
        }));

        parentPane.setOnMouseReleased(((event) -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setOpacity(1.0f);
        }));
    }

    private void setScreen(boolean b, boolean pri, boolean pay, boolean acc, boolean d) {
        this.boatOnOff = b;
        this.prixOnOff = pri;
        this.payOnOff = pay;
        this.accOnOff = acc;
        this.dashOnOff = d;
        this.addBoatOnOff = false;
        this.payNowBoatOnOff = false;
        this.addCreditOnOff = false;
        this.notOnOff = false;
        this.clickBoatOnOff = false;
        this.addRaceOnOff = false;
        hideNotification(false);
    }

    private void visibility(Pane pane) {
        paneDashboard.setVisible(pane.equals(paneDashboard));
        paneSettings.setVisible(pane.equals(paneSettings));
        paneBoats.setVisible(pane.equals(paneBoats));
        panePrix.setVisible(pane.equals(panePrix));
        panePayment.setVisible(pane.equals(panePayment));
        paneAddBoat.setVisible(pane.equals(paneAddBoat));
        panePayNow.setVisible(pane.equals(panePayNow));
        paneAddCreditcard.setVisible(pane.equals(paneAddCreditcard));
        paneClickBoat.setVisible(pane.equals(paneClickBoat));
        paneAddRace.setVisible(pane.equals(paneAddRace));
    }


    //Method that sets the listeners for the various text field which need checks on the input data
    private void validatorTextFields() {
        this.txtLenghtBoat.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (txtLenghtBoat.getText().isEmpty() || txtLenghtBoat.getText().isBlank()) {
                    txtLenghtBoat.setText("");
                    txtPriceBoat.setText(PRICE_X_METER + " x METER");
                } else if (txtLenghtBoat.getText().equals("") && newValue.matches("\\d*"))
                    txtLenghtBoat.setText(newValue);
                else if (!newValue.matches("\\d*"))
                    txtLenghtBoat.setText(newValue.replaceAll("[^\\d]", ""));
                else
                    txtPriceBoat.setText(String.valueOf((Double.parseDouble(txtLenghtBoat.getText()) * PRICE_X_METER)));
            }
        });
        this.txtNumberCard.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (txtNumberCard.getText().isEmpty() || txtNumberCard.getText().isBlank())
                    txtNumberCard.setText("");
                else if (txtNumberCard.getText().equals("") && newValue.matches("\\d*"))
                    txtNumberCard.setText(newValue);
                else if (!newValue.matches("\\d*"))
                    txtNumberCard.setText(newValue.replaceAll("[^\\d]", ""));
                else if (txtNumberCard.getText().length() > 16)
                    txtNumberCard.setText(oldValue);
                else txtNumberCard.setText(newValue);
            }
        });
        this.txtCvcCredit.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (txtCvcCredit.getText().isEmpty() || txtCvcCredit.getText().isBlank())
                    txtCvcCredit.setText("");
                else if (txtCvcCredit.getText().equals("") && newValue.matches("\\d*"))
                    txtCvcCredit.setText(newValue);
                else if (!newValue.matches("\\d*"))
                    txtCvcCredit.setText(newValue.replaceAll("[^\\d]", ""));
                else if (txtCvcCredit.getText().length() > 3)
                    txtCvcCredit.setText(oldValue);
                else txtCvcCredit.setText(newValue);
            }
        });

    }

    @FXML
    private void close(MouseEvent mouseEvent) {
        System.exit(0);
    }

    @FXML
    private void dashboard() {
        if (!this.dashOnOff) {
            setScreen(false, false, false, false, true);
            new ZoomIn(paneDashboard).play();
            visibility(paneDashboard);
        }
    }

    @FXML
    private void payments() {
        if (!this.payOnOff) {
            setScreen(false, false, true, false, false);
            this.member.setCreditCards(getCreditCardDB());
            new ZoomIn(panePayment).play();
            visibility(panePayment);
            showCreditCards();
        }
    }

    //Method that manages the credit card visualization
    private void showCreditCards() {
        int column = 0, row = 0, minHeight = 175;
        Random rand = new Random();
        this.gridpanePay.getChildren().clear();

        for (CreditCard c : this.member.getCreditCards()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(CreditController.class.getResource("credit_screen.fxml"));
                AnchorPane pane = fxmlLoader.load();
                CreditController itemController = fxmlLoader.getController();
                itemController.setData(c, this.imgListCredit.get(rand.nextInt(this.imgListCredit.size())), this.myListener);

                if (column == 2) {
                    column = 0;
                    row++;
                    minHeight += 152;
                }
                gridpanePay.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));
            } catch (IOException ignored) {
            }
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AddCreditController.class.getResource("addCredit_screen.fxml"));
            AnchorPane pane = fxmlLoader.load();
            AddCreditController itemController = fxmlLoader.getController();
            itemController.setData(this.myListener);

            if (column == 2) {
                column = 0;
                row++;
                minHeight += 152;
            }
            gridpanePay.add(pane, column, row);
            GridPane.setMargin(pane, new Insets(10));
        } catch (IOException ignored) {
        }
        if (row == 0) minHeight += 152;
        gridpanePay.setMinWidth(750);
        gridpanePay.setMinHeight(minHeight + 175);
    }

    private void deleteCardDB(CreditCard cc) {
        this.member.deleteCreditCard(cc);
        connection.doAction(PROT_DELETE_CARD, cc.getNumCard());

    }

    private void openCredit(CreditCard c) {
        Boolean confirmation = Utilities.showConfirmation("Delete card", "Delete card", "Are you sure you want to delete Card Number: " + c.getNumCard() + "?");
        if (confirmation) {
            deleteCardDB(c);
            showCreditCards();
        }
    }


    private void addCredit() {
        if (!this.addCreditOnOff) {
            setScreen(false, false, false, false, false);
            this.addCreditOnOff = true;
            txtNumberCard.setText("");
            txtFirstCredit.setText("");
            txtLastCredit.setText("");
            txtDateCredit.setText("");
            txtCvcCredit.setText("");
            new ZoomIn(paneAddCreditcard).play();
            visibility(paneAddCreditcard);
        }
    }

    @FXML
    private void backToCreditcard(ActionEvent actionEvent) {
        payments();
    }

    @FXML
    private void addCreditCard(ActionEvent actionEvent) {
        if (txtNumberCard.getText().isBlank() || txtNumberCard.getText().isEmpty())
            Utilities.showAlert("Input Error!", "Input empty!", "Insert a value for number card ");
        else if (txtFirstCredit.getText().isBlank() || txtFirstCredit.getText().isEmpty())
            Utilities.showAlert("Input Error!", "Input empty!", "Insert a value on firstname ");
        else if (txtLastCredit.getText().isBlank() || txtLastCredit.getText().isEmpty())
            Utilities.showAlert("Input Error!", "Input empty!", "Insert a value on lastname ");
        else if (txtDateCredit.getText().isBlank() || txtDateCredit.getText().isEmpty())
            Utilities.showAlert("Input Error!", "Input empty!", "Insert a value on date ");
        else if (txtCvcCredit.getText().isBlank() || txtCvcCredit.getText().isEmpty())
            Utilities.showAlert("Input Error!", "Input empty!", "Insert a value on cvc ");
        else if (!txtDateCredit.getText().matches("^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$"))
            Utilities.showAlert("Input Error!", "Input date error!", "Insert a correct value of date ");
        else {
            String num = txtNumberCard.getText();
            String first = txtFirstCredit.getText();
            String last = txtLastCredit.getText();
            String date = txtDateCredit.getText();
            String cvc = txtCvcCredit.getText();
            this.addCreditCardQuery(new CreditCard(num, first, last, date, cvc));
        }
    }

    private void addCardDB(CreditCard cc) {
        connection.doAction(PROT_ADD_CARD, cc.getNumCard() + SEP_PARAM + cc.getCvc() + SEP_PARAM + cc.getDate() + SEP_PARAM + cc.getFirstname() + SEP_PARAM + cc.getLastname() + SEP_PARAM + member.getUsername());
    }

    private void addCreditCardQuery(CreditCard c) {
        this.member.addCreditCard(c);
        addCardDB(c);

        Utilities.showAlert("Add new Credit Card", "New Credit Card inserted", "Successful operation");
        payments();
    }

    @FXML
    private void account() {
        if (!this.accOnOff) {
            setScreen(false, false, false, true, false);
            this.member.setAddress(getAddressDB());
            street.setText(this.member.getAddress().getStreet());
            city.setText(this.member.getAddress().getCity());
            country.setText(this.member.getAddress().getCountry());
            province.setText(this.member.getAddress().getProvince());
            postal.setText(this.member.getAddress().getPostalCode());

            txtStreet.setText(this.member.getAddress().getStreet());
            txtCity.setText(this.member.getAddress().getCity());
            txtCountry.setText(this.member.getAddress().getCountry());
            txtProvince.setText(this.member.getAddress().getProvince());
            txtPostal.setText(this.member.getAddress().getPostalCode());

            new ZoomIn(paneSettings).play();
            visibility(paneSettings);
        }
    }

    @FXML
    private void changeAddress(ActionEvent actionEvent) {
        if (txtStreet.getText().isBlank() || txtStreet.getText().isEmpty())
            Utilities.showAlert("Input Error!", "Input empty!", "Insert a street ");
        else if (txtCity.getText().isBlank() || txtCity.getText().isEmpty())
            Utilities.showAlert("Input Error!", "Input empty!", "Insert a city ");
        else if (txtCountry.getText().isBlank() || txtCountry.getText().isEmpty())
            Utilities.showAlert("Input Error!", "Input empty!", "Insert a country ");
        else if (txtProvince.getText().isBlank() || txtProvince.getText().isEmpty())
            Utilities.showAlert("Input Error!", "Input empty!", "Insert a province ");
        else if (txtPostal.getText().isBlank() || txtPostal.getText().isEmpty())
            Utilities.showAlert("Input Error!", "Input empty!", "Insert a postal code ");
        else {
            String street = txtStreet.getText();
            String city = txtCity.getText();
            String country = txtCountry.getText();
            String prov = txtProvince.getText();
            String post = txtPostal.getText();

            this.updateAddress(new Address(street, city, prov, country, post));
        }
    }

    private Address getAddressDB() {
        List<Address> address = (List<Address>) connection.doAction(PROT_GET_ADDRESS, this.member.getFiscalCode());
        if (!address.isEmpty())
            return address.get(0);
        return null;
    }

    private void setAddressDB(Address a) {
        connection.doAction(PROT_SET_ADDRESS, a.getStreet() + SEP_PARAM + a.getCity() + SEP_PARAM + a.getProvince() + SEP_PARAM + a.getCountry() + SEP_PARAM + a.getPostalCode() + SEP_PARAM + this.member.getFiscalCode());
    }

    private void updateAddress(Address a) {
        this.member.updateAddress(a);
        setAddressDB(a);

        this.accOnOff = false;
        account();
    }

    @FXML
    private void grandPrix() {
        if (!this.prixOnOff) {
            setScreen(false, true, false, false, false);
            this.member.setRaces(getSubRaceDB());
            updateTableAll();
            updateTablePart();
            new ZoomIn(panePrix).play();
            visibility(panePrix);
        }
    }

    private void updateTableAll() {
        tableAll.getItems().clear();
        for (Race r : this.getNotSubRaceDB())
            tableAll.getItems().add(r);
    }

    private void updateTablePart() {
        tablePart.getItems().clear();
        for (Race r : this.member.getRaces())
            tablePart.getItems().add(r);
    }

    @FXML //Method that manages the subscription to a race
    private void subscribe() {
        if (this.member.getBoats().isEmpty()) {
            Utilities.showAlert("Error", "No Boats", "You don't own any boats so you cannot subscribe");
            return;
        }
        if (!this.addRaceOnOff) {
            setScreen(false, false, false, false, false);
            this.addRaceOnOff = true;

            raceToPay = (Race) tableAll.getSelectionModel().getSelectedItem();
            nameRace.setText(raceToPay.getName());
            dateRace.setText(raceToPay.getDate());
            priceRace.setText(raceToPay.getPrice());
            typeBoatRace.getItems().clear();
            for (Boat b : this.member.getBoats())
                typeBoatRace.getItems().add(b.getName());
            typeBoatRace.setValue(this.member.getBoats().get(0).getName());
            typePaymentRace.getItems().clear();
            typePaymentRace.getItems().add(TRANSFER);
            for (CreditCard c : this.member.getCreditCards())
                typePaymentRace.getItems().add(c.getNumCard());
            bonificCodeRace.setText(UUID.randomUUID().toString());
            typePaymentRace.setValue(TRANSFER);

            new ZoomIn(paneAddRace).play();
            visibility(paneAddRace);
        }
    }

    @FXML
    private void unsubscribe(ActionEvent actionEvent) {
        Race r = (Race) tablePart.getSelectionModel().getSelectedItem();

        if (r != null) {
            this.member.delRace(r);
            updateTablePart();
            deleteSubscription(r.getId());
            updateTableAll();
        }
    }

    @FXML
    private void backToPrix(ActionEvent actionEvent) {
        grandPrix();
    }


    private String setSubscribtion(Boat boat) {
        return ((List<String>) connection.doAction(PROT_SUBSCRIBE_RACE, raceToPay.getId() + SEP_PARAM + member.getUsername() + SEP_PARAM + boat.getId())).get(0);
    }

    private void deleteSubscription(String raceID) {
        String boatID = ((List<String>) connection.doAction(PROT_GET_SUBSCRIBEDBOAT, raceID + SEP_PARAM + member.getUsername())).get(0);
        connection.doAction(PROT_DELETE_SUBSCRIPTION, raceID + SEP_PARAM + member.getUsername() + SEP_PARAM + boatID);
    }

    private void addTransactionDB(Transaction t) {
        connection.doAction(PROT_ADD_TRANSACTION, t.getMotive() + SEP_PARAM + t.getTotal() + SEP_PARAM + member.getUsername() + SEP_PARAM + t.getMethod() + SEP_PARAM + t.getDate());
    }

    @FXML
    private void partecipateRace(ActionEvent actionEvent) {
        Boat toRide = new Boat();
        for (Boat b : this.member.getBoats())
            if (b.getName().equals(typeBoatRace.getSelectionModel().getSelectedItem()))
                toRide = new Boat(b);
        if (setSubscribtion(toRide).equals(MSG_OK)) {
            addTransactionDB(new Transaction("RACE: " + raceToPay.getName(), Double.parseDouble(raceToPay.getPrice()), member.getUsername(), typePaymentRace.getSelectionModel().getSelectedItem(), LocalDateTime.now().toString()));
            this.member.addRace(raceToPay);
            grandPrix();
        } else {
            Utilities.showAlert("Error revealed", "Impossible partecipate race", "Impossibile to subscribe");
        }
    }

    @FXML
    private void notification(ActionEvent actionEvent) {
        if (!notOnOff) {
            if (checkImage(imgBell.getImage(), new Image(Objects.requireNonNull(DashboardController.class.getResourceAsStream("notification.png"))))) {
                this.member.setNotifications(getNotificationsDB());
                this.showNotification();
            }
        } else
            this.hideNotification(true);
    }

    //Method that switches the notification image according to the situation
    private boolean checkImage(Image firstImage, Image secondImage) {
        // Prevent `NullPointerException`
        if (firstImage != null && secondImage == null) return false;
        if (firstImage == null) return secondImage == null;

        // Compare images size
        if (firstImage.getWidth() != secondImage.getWidth()) return false;
        if (firstImage.getHeight() != secondImage.getHeight()) return false;

        // Compare images color
        for (int x = 0; x < firstImage.getWidth(); x++) {
            for (int y = 0; y < firstImage.getHeight(); y++) {
                int firstArgb = firstImage.getPixelReader().getArgb(x, y);
                int secondArgb = secondImage.getPixelReader().getArgb(x, y);

                if (firstArgb != secondArgb) return false;
            }
        }
        return true;
    }

    private void showNotification() {
        this.notOnOff = true;
        int yGap = 0;
        for (Notification n : this.member.getNotifications()) {
            try {
                for (int i = 0; i <= this.stackPane.getChildren().size() - 1; i++) {
                    FXMLLoader fxmlLoader = new FXMLLoader(NotificationController.class.getResource("notification_screen.fxml"));
                    Pane pane = fxmlLoader.load();
                    NotificationController itemController = fxmlLoader.getController();
                    itemController.setData(n);
                    pane.setTranslateX(455);
                    pane.setTranslateY(yGap);
                    Pane paneVis = (Pane) this.stackPane.getChildren().get(i);
                    paneVis.getChildren().add(pane);
                    if (!paneNot.contains(pane))
                        paneNot.add(pane);
                }
                yGap += 80;
            } catch (IOException ignored) {
            }
        }
    }

    private void hideNotification(boolean action) {
        this.notOnOff = false;
        if (action) {
            for (Pane p : paneNot) {
                this.paneDashboard.getChildren().remove(p);
                this.paneBoats.getChildren().remove(p);
                this.panePayment.getChildren().remove(p);
                this.panePrix.getChildren().remove(p);
                this.paneSettings.getChildren().remove(p);
                this.paneAddBoat.getChildren().remove(p);
                this.panePayNow.getChildren().remove(p);
                this.paneAddCreditcard.getChildren().remove(p);
                this.paneAddRace.getChildren().remove(p);
                this.paneClickBoat.getChildren().remove(p);
            }
            paneNot.clear();
            this.member.getNotifications().clear();
            connection.doAction(PROT_DELETE_NOTIFICATION, this.member.getUsername());
            Image image = new Image(Objects.requireNonNull(DashboardController.class.getResourceAsStream("bell.png")));
            imgBell.setImage(image);
        }
    }


    @FXML
    private void boats() {
        if (!this.boatOnOff) {
            setScreen(true, false, false, false, false);
            this.member.setBoats(getBoatDB());
            new ZoomIn(paneBoats).play();
            visibility(paneBoats);
            showBoats();
        }
    }

    //Method that manages the boats' visualization
    private void showBoats() {
        int column = 0, row = 0, minHeight = 175;
        Random rand = new Random();
        this.gridpaneBoats.getChildren().clear();

        for (Boat b : this.member.getBoats()) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(BoatController.class.getResource("boat_screen.fxml"));
                AnchorPane pane = fxmlLoader.load();
                BoatController itemController = fxmlLoader.getController();
                if (b.isPaid())
                    itemController.setData(b, this.imgListBoat.get(rand.nextInt(this.imgListBoat.size())), b.getPrice(), "Yes", "#2fb523", this.myListener);
                else
                    itemController.setData(b, this.imgListBoat.get(rand.nextInt(this.imgListBoat.size())), b.getPrice(), "Not yet!", "#ff0000", this.myListener);

                if (column == 2) {
                    column = 0;
                    row++;
                    minHeight += 152;
                }
                gridpaneBoats.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));
            } catch (IOException ignored) {
            }
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(AddBoatController.class.getResource("addBoat_screen.fxml"));
            AnchorPane pane = fxmlLoader.load();
            AddBoatController itemController = fxmlLoader.getController();
            itemController.setData(this.myListener);

            if (column == 2) {
                column = 0;
                row++;
                minHeight += 152;
            }
            gridpaneBoats.add(pane, column, row);
            GridPane.setMargin(pane, new Insets(10));
        } catch (IOException ignored) {
        }
        if (row == 0) minHeight += 152;
        gridpaneBoats.setMinWidth(775);
        gridpaneBoats.setMinHeight(minHeight + 175);
    }

    private void addBoat() {
        if (!this.addBoatOnOff) {
            setScreen(false, false, false, false, false);
            this.addBoatOnOff = true;
            txtNameBoat.setText("");
            txtLenghtBoat.setText("");
            new ZoomIn(paneAddBoat).play();
            visibility(paneAddBoat);
        }
    }

    private void openBoat(Boat b) {
        if (!this.clickBoatOnOff) {
            setScreen(false, false, false, false, false);
            this.clickBoatOnOff = true;
            nameBoatToPay1.setText(b.getName());
            lenghtBoatToPay1.setText(String.valueOf(b.getLength()));
            priceBoatToPay1.setText(String.valueOf(b.getPrice()));
            typePayment1.getItems().clear();
            typePayment1.getItems().add(TRANSFER);
            for (CreditCard c : this.member.getCreditCards())
                typePayment1.getItems().add(c.getNumCard());
            bonificCode1.setText(UUID.randomUUID().toString());
            typePayment1.setValue(TRANSFER);
            boxPayment.setDisable(b.isPaid());
            toDelete = b;
            new ZoomIn(paneClickBoat).play();
            visibility(paneClickBoat);
        }
    }

    @FXML
    private void backToShowBoat(ActionEvent actionEvent) {
        boats();
    }

    private void deleteBoatDB(String boatID) {
        connection.doAction(PROT_DELETE_BOAT, boatID);
    }

    @FXML
    private void deleteBoat(MouseEvent mouseEvent) {
        if (Utilities.showConfirmation("Delete Boat", "Delete Boat", "Are you sure you want to delete " + this.toDelete.getName() + "?")) {
            this.member.deleteBoat(this.toDelete);
            deleteBoatDB(toDelete.getId());
            boats();
        }
    }

    private void updateBoatDB(Boat b, Boolean paid) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date now = sdf.parse(String.valueOf(LocalDateTime.now()));
            Calendar c = Calendar.getInstance();
            c.setTime(now);
            c.add(Calendar.YEAR, 1);
            Date nextYear = c.getTime();
            String nextYearDate = sdf.format(nextYear);
            connection.doAction(PROT_UPDATE_BOAT, paid.toString() + SEP_PARAM + b.getId() + SEP_PARAM + nextYearDate);
        } catch (Exception ignored) {
        }
    }

    @FXML
    private void confirmClickPayBoat(ActionEvent actionEvent) {
        String name = nameBoatToPay1.getText();
        double lenght = Double.parseDouble(lenghtBoatToPay1.getText());
        double price = Double.parseDouble(priceBoatToPay1.getText());
        String transferType = typePayment1.getValue();
        this.member.payBoat(toDelete);
        this.updateBoatDB(toDelete, true);
        Utilities.showAlert("Updating Boat", "Payment executed", "Successful payment!");
        doTransfer(name, price, transferType);
        boats();
    }


    @FXML
    private void backToBoat() {
        boats();
    }

    @FXML
    private void backToAddBoat(ActionEvent actionEvent) {
        addBoat();
    }

    @FXML
    private void addNewBoat(ActionEvent actionEvent) {
        if (txtNameBoat.getText().isBlank() || txtNameBoat.getText().isEmpty()) {
            Utilities.showAlert("Error input!", "Value error", "Insert a name");
            return;
        }
        String name = txtNameBoat.getText();
        double lenght = 0;
        double price = 0;
        try {
            lenght = Double.parseDouble(txtLenghtBoat.getText());
            price = Double.parseDouble(txtPriceBoat.getText());
        } catch (Exception e) {
            Utilities.showAlert("Error input!", "Value error", "Value of length incorrect!");
            return;
        }

        if (lenght > 0)
            this.payBoat(new Boat("-1", name, lenght, price, false, this.member.getUsername()), false);
        else Utilities.showAlert("Error input!", "Value error", "Value of length incorrect");
    }

    private void addBoatDB(Boat boat, boolean date) {
        if (date) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date now = sdf.parse(String.valueOf(LocalDateTime.now()));
                Calendar c = Calendar.getInstance();
                c.setTime(now);
                c.add(Calendar.YEAR, 1);
                Date nextYear = c.getTime();
                String nextYearDate = sdf.format(nextYear);
                connection.doAction(PROT_ADD_BOAT, boat.getName() + SEP_PARAM + boat.getLength() + SEP_PARAM + boat.getPrice() + SEP_PARAM + member.getUsername() + SEP_PARAM + boat.isPaid() + SEP_PARAM + nextYearDate);
                return;

            } catch (Exception ignored) {
            }
        }
        connection.doAction(PROT_ADD_BOAT, boat.getName() + SEP_PARAM + boat.getLength() + SEP_PARAM + boat.getPrice() + SEP_PARAM + member.getUsername() + SEP_PARAM + boat.isPaid());
    }

    private void payBoat(Boat b, boolean date) {
        this.member.addBoat(b);
        addBoatDB(b, date);
        Utilities.showAlert("Added new Boat", "Added new Boat", "Successfully added new boat");
        boats();
    }

    @FXML //Method that manages the payment of a new boat
    private void payNowBoat(ActionEvent actionEvent) {
        if (txtNameBoat.getText().isBlank() || txtNameBoat.getText().isEmpty()) {
            Utilities.showAlert("Error input!", "Value error", "Insert a name");
            return;
        }
        try {
            Double.parseDouble(txtLenghtBoat.getText());
            Double.parseDouble(txtPriceBoat.getText());
        } catch (Exception e) {
            Utilities.showAlert("Error input!", "Value error", "Value of length incorrect!");
            return;
        }

        nameBoatToPay.setText(txtNameBoat.getText());
        lenghtBoatToPay.setText(txtLenghtBoat.getText());
        priceBoatToPay.setText(txtPriceBoat.getText());
        typePayment.getItems().clear();
        typePayment.getItems().add(TRANSFER);
        for (CreditCard c : this.member.getCreditCards())
            typePayment.getItems().add(c.getNumCard());
        bonificCode.setText(UUID.randomUUID().toString());
        typePayment.setValue(TRANSFER);

        if (!this.payNowBoatOnOff) {
            setScreen(false, false, false, false, false);
            this.payNowBoatOnOff = true;
            new ZoomIn(panePayNow).play();
            visibility(panePayNow);
        }
    }

    @FXML
    private void confirmPayAddBoat(ActionEvent actionEvent) {
        String name = txtNameBoat.getText();
        double lenght = Double.parseDouble(txtLenghtBoat.getText());
        double price = Double.parseDouble(txtPriceBoat.getText());
        Boat b = new Boat("-1", name, lenght, price, true, this.member.getUsername());
        this.payBoat(b, true);

        String transferType = typePayment.getValue();

        doTransfer(name, price, transferType);
    }

    private void doTransfer(String name, double price, String type) {
        if (type.equals(TRANSFER))
            addTransactionDB(new Transaction("BOAT: " + name, price, member.getUsername(), TRANSFER, LocalDateTime.now().toString()));
        else
            addTransactionDB(new Transaction("BOAT: " + name, price, member.getUsername(), type, LocalDateTime.now().toString()));
    }

    @FXML
    private void logout(ActionEvent actionEvent) {
        if (threadNots != null) threadNots.interrupt();
        Utilities.loadScene((Stage) txtNameBoat.getScene().getWindow(), "signInUp_screen", "Login", SignInUpController.class);
    }

    //Method that verifies if the user has an expired season pass, and in which case disables all functionality until he pays the subscription
    private void checkPass() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date now = sdf.parse(LocalDate.now().toString());
            Date dMember = sdf.parse(this.member.getSeason());
            if (dMember.before(now)) {
                setScreen(false, false, false, false, false);
                this.paneCheckPass.setVisible(true);
                this.paneCheckPass.toFront();
                passPrice.setText(PRICE_SEASON_PASS + " €");
                passChoice.getItems().clear();
                passChoice.getItems().add(TRANSFER);
                for (CreditCard c : this.member.getCreditCards())
                    passChoice.getItems().add(c.getNumCard());
                passBonific.setText(UUID.randomUUID().toString());
                passChoice.setValue(TRANSFER);

                btnDashboardBar.setDisable(true);
                btnBoatBar.setDisable(true);
                btnPrixBar.setDisable(true);
                btnPayBar.setDisable(true);
                btnSettBar.setDisable(true);
                btnProfileBar.setDisable(true);
                btnNotBar.setDisable(true);
            } else {
                setScreen(false, false, false, false, true);
                this.paneDashboard.toFront();
                this.paneCheckPass.setVisible(false);
                btnDashboardBar.setDisable(false);
                btnBoatBar.setDisable(false);
                btnPrixBar.setDisable(false);
                btnPayBar.setDisable(false);
                btnSettBar.setDisable(false);
                btnProfileBar.setDisable(false);
                btnNotBar.setDisable(false);
            }
        } catch (Exception ignored) {
        }
    }

    private void updateMemberDB() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date now = sdf.parse(String.valueOf(LocalDateTime.now()));
            Calendar c = Calendar.getInstance();
            c.setTime(now);
            c.add(Calendar.YEAR, 1);
            Date nextYear = c.getTime();
            String nextYearDate = sdf.format(nextYear);
            connection.doAction(PROT_UPDATE_MEMBER, nextYearDate + SEP_PARAM + member.getUsername());
        } catch (Exception ignored) {
        }
    }

    @FXML
    private void passPay(ActionEvent actionEvent) {
        addTransactionDB(new Transaction("PASS: " + member.getUsername(), PRICE_SEASON_PASS, member.getUsername(), passChoice.getSelectionModel().getSelectedItem().toString(), LocalDateTime.now().toString()));
        updateMemberDB();

        setScreen(false, false, false, false, true);
        this.paneDashboard.toFront();
        this.paneCheckPass.setVisible(false);
        btnDashboardBar.setDisable(false);
        btnBoatBar.setDisable(false);
        btnPrixBar.setDisable(false);
        btnPayBar.setDisable(false);
        btnSettBar.setDisable(false);
        btnProfileBar.setDisable(false);
        btnNotBar.setDisable(false);
    }
}
