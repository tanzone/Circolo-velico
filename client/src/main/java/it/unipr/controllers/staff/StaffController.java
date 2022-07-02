package it.unipr.controllers.staff;

import animatefx.animation.ZoomIn;
import it.unipr.accounts.Member;
import it.unipr.accounts.Staff;
import it.unipr.accounts.personalinformation.Address;
import it.unipr.accounts.personalinformation.Notification;
import it.unipr.boat.Boat;
import it.unipr.connection.Connection;
import it.unipr.controllers.dashboard.DashboardController;
import it.unipr.controllers.login.SignInUpController;
import it.unipr.race.Race;
import it.unipr.transaction.Transaction;
import it.unipr.utilities.MyListenerStaff;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static it.unipr.constants.Constants.*;

/**
 * Class that manages the interactions with the staff visual interface
 *
 * @author Tanzi Manuel 307720
 * @author Mamone Maximiliano 308214
 * @version 1.0
 * @see it.unipr.accounts.Staff
 * @since 1.0
 */
public class StaffController implements Initializable {
    @FXML
    private TextField txtNameBoat, txtUserBoat;
    @FXML
    private Button btnDeleteMember, btnRemoveRace;
    @FXML
    private DatePicker dateRace;
    @FXML
    private TableColumn memberCity, memberUser, memberName, memberLast;
    @FXML
    private TableView<Member> tableMembers;
    @FXML
    private TableColumn histMethod, histTotal, histMotive, histUser, histDate;
    @FXML
    private TableView<Transaction> tableHist;
    @FXML
    private TableColumn namePrix, datePrix, pricePrix, winBoatPrix, winUserPrix;
    @FXML
    private TableView<Race> tableAll;
    @FXML
    private Label username;
    @FXML
    private TextField nameRace, priceRace;
    @FXML
    private GridPane gridpaneBoats;
    @FXML
    private TextField userPay, racePay, boatPay, userMember, cityMember;
    @FXML
    private Pane paneDashboard, paneMembers, paneHistory, paneBoats, panePrix;
    @FXML
    private AnchorPane parentPane;


    private double x, y;
    private String ip;
    private boolean boatOnOff, dashOnOff, prixOnOff, histOnOff, memberOnOff, admin;
    private ArrayList<Image> imgListBoat;
    private MyListenerStaff myListener;
    private Thread threadBoats, threadPrix, threadMember, threadTrans;
    private Connection c;


    /**
     * Method used to set interal data recieved from login procedure
     *
     * @param s  Staff object containing the data on the logged in staff
     * @param ip String containing the ip of the server
     */
    public void setData(Staff s, String ip) {
        this.ip = ip;
        this.admin = s.isAdmin();
        this.myListener = this.setListener();
        this.imgListBoat = new ArrayList<>();
        this.imgListBoat.add(new Image(Objects.requireNonNull(DashboardController.class.getResourceAsStream("sailing.png"))));
        this.imgListBoat.add(new Image(Objects.requireNonNull(DashboardController.class.getResourceAsStream("boatColored.png"))));
        this.imgListBoat.add(new Image(Objects.requireNonNull(DashboardController.class.getResourceAsStream("ship.png"))));

        this.username.setText(s.getUsername());
        this.dateRace.setValue(LocalDate.now());

        String[] serverAddress = ip.split(":");
        this.c = new Connection(serverAddress[0], Integer.parseInt(serverAddress[1]));

        controlsAdmin();
    }

    private void controlsAdmin() {
        btnDeleteMember.setDisable(!admin);
        btnRemoveRace.setDisable(!admin);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setScreen(false, false, false, false, true);
        makeDragable();
        setTables();
        validatorTextFields();
    }

    //Method that checks all the text field constraints
    private void validatorTextFields() {
        this.priceRace.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (priceRace.getText().isEmpty() || priceRace.getText().isBlank()) {
                    priceRace.setText("");
                } else if (priceRace.getText().equals("") && newValue.matches("\\d*"))
                    priceRace.setText(newValue);
                else if (!newValue.matches("\\d*"))
                    priceRace.setText(newValue.replaceAll("[^\\d]", ""));
                else
                    priceRace.setText(newValue);
            }
        });
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

    //Listener for the various actions
    private MyListenerStaff setListener() {
        return new MyListenerStaff() {
            @Override
            public void clickSendNot(Boat b) {
                sendNotification(b);
            }

            @Override
            public void clickDelBoat(Boat b) {
                deleteBoat(b);
            }
        };
    }

    private void setTables() {
        namePrix.setCellValueFactory(new PropertyValueFactory<>("name"));
        datePrix.setCellValueFactory(new PropertyValueFactory<>("date"));
        pricePrix.setCellValueFactory(new PropertyValueFactory<>("price"));
        winBoatPrix.setCellValueFactory(new PropertyValueFactory<>("winnerBoat"));
        winUserPrix.setCellValueFactory(new PropertyValueFactory<>("winner"));
        histMethod.setCellValueFactory(new PropertyValueFactory<>("method"));
        histMotive.setCellValueFactory(new PropertyValueFactory<>("motive"));
        histUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        histTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        histDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        memberCity.setCellValueFactory(new PropertyValueFactory<>("city"));
        memberUser.setCellValueFactory(new PropertyValueFactory<>("username"));
        memberName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        memberLast.setCellValueFactory(new PropertyValueFactory<>("lastName"));

    }

    private void sendNotification(Boat b) {
        addNotificationDB(new Notification("You need to pay this boat pass: " + b.getName(), "red"), b.getOwner());
    }

    private void deleteBoat(Boat b) {
        if (Utilities.showConfirmation("Delete Boat", "Delete boat", "Are you sure you want to delete: " + b.getName() + "?")) {
            c.doAction(PROT_DELETE_BOAT, b.getId());
        }
    }

    @FXML
    private void close(MouseEvent mouseEvent) {
        System.exit(0);
    }

    @FXML
    private void logout(ActionEvent actionEvent) {
        if (threadBoats != null) threadBoats.interrupt();
        if (threadPrix != null) threadPrix.interrupt();
        if (threadMember != null) threadMember.interrupt();
        if (threadTrans != null) threadTrans.interrupt();
        Utilities.loadScene((Stage) parentPane.getScene().getWindow(), "signInUp_screen", "Login", SignInUpController.class);
    }

    //Method used to set visibility of each pane
    private void setScreen(boolean b, boolean pri, boolean hist, boolean mem, boolean d) {
        this.boatOnOff = b;
        this.prixOnOff = pri;
        this.histOnOff = hist;
        this.dashOnOff = d;
        this.memberOnOff = mem;
        if (threadBoats != null) threadBoats.interrupt();
        if (threadPrix != null) threadPrix.interrupt();
        if (threadMember != null) threadMember.interrupt();
        if (threadTrans != null) threadTrans.interrupt();
    }

    private void visibility(Pane pane) {
        paneDashboard.setVisible(pane.equals(paneDashboard));
        paneBoats.setVisible(pane.equals(paneBoats));
        panePrix.setVisible(pane.equals(panePrix));
        paneHistory.setVisible(pane.equals(paneHistory));
        paneMembers.setVisible(pane.equals(paneMembers));
    }

    @FXML
    private void dashboard(ActionEvent actionEvent) {
        if (!this.dashOnOff) {
            setScreen(false, false, false, false, true);
            new ZoomIn(paneDashboard).play();
            visibility(paneDashboard);
        }
    }

    @FXML
    private void boats() {
        if (!this.boatOnOff) {
            setScreen(true, false, false, false, false);
            threadBoats = new Thread(new ThreadBoats(gridpaneBoats, imgListBoat, ip, myListener, admin));
            threadBoats.start();
            new ZoomIn(paneBoats).play();
            visibility(paneBoats);
        }
    }

    @FXML
    private void grandPrix() {
        if (!this.prixOnOff) {
            setScreen(false, true, false, false, false);
            threadPrix = new Thread(new ThreadPrix(tableAll, ip));
            threadPrix.start();
            new ZoomIn(panePrix).play();
            visibility(panePrix);
        }
    }

    @FXML
    private void payments() {
        if (!this.histOnOff) {
            setScreen(false, false, true, false, false);
            threadTrans = new Thread(new ThreadTransaction(tableHist, ip));
            threadTrans.start();
            new ZoomIn(paneHistory).play();
            visibility(paneHistory);
        }
    }

    @FXML
    private void account() {
        if (!this.memberOnOff) {
            setScreen(false, false, false, true, false);
            threadMember = new Thread(new ThreadMember(tableMembers, ip));
            threadMember.start();
            new ZoomIn(paneMembers).play();
            visibility(paneMembers);
        }
    }


    private List<Boat> getAllBoatDB() {
        return (List<Boat>) c.doAction(PROT_GET_ALLBOATS, "pizza-acciuga");
    }

    //Method that manages the visualization of the boats in the staff menu
    private void showBoats(List<Boat> boats, String username, String nameBoat) {
        threadBoats.interrupt();
        int column = 0, row = 0, minHeight = 350;
        Random rand = new Random();
        gridpaneBoats.getChildren().clear();

        for (Boat b : boats) {
            if ((b.getName().equals(nameBoat) && username.equals("-1")) || (b.getOwner().equals(username) && nameBoat.equals("-1")))
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(BoatStaffController.class.getResource("boat_screen.fxml"));
                    AnchorPane pane = fxmlLoader.load();
                    BoatStaffController itemController = fxmlLoader.getController();
                    if (b.isPaid())
                        itemController.setData(b, imgListBoat.get(rand.nextInt(imgListBoat.size())), b.getPrice(), "Yes", "#2fb523", b.getOwner(), myListener, admin);
                    else
                        itemController.setData(b, imgListBoat.get(rand.nextInt(imgListBoat.size())), b.getPrice(), "Not yet!", "#ff0000", b.getOwner(), myListener, admin);

                    if (column == 2) {
                        column = 0;
                        row++;
                        minHeight += 250;
                    }
                    gridpaneBoats.add(pane, column++, row);
                    GridPane.setMargin(pane, new Insets(10));
                } catch (IOException ignored) {
                }
            if (row == 0) minHeight += 200;
            gridpaneBoats.setMinWidth(775);
            gridpaneBoats.setMinHeight(minHeight);
        }
    }

    @FXML
    private void searchBoatMember(ActionEvent actionEvent) {
        showBoats(getAllBoatDB(), txtUserBoat.getText(), "-1");
    }

    @FXML
    private void searchBoatName(ActionEvent actionEvent) {
        showBoats(getAllBoatDB(), "-1", txtNameBoat.getText());
    }

    @FXML
    private void sendNotAllMember(ActionEvent actionEvent) {
        sendNotSomeMembers(new Notification("You need to pay some boat passes", "red"), getExpiredBMember());
    }

    @FXML
    private void resetBoat(ActionEvent actionEvent) {
        threadBoats = new Thread(new ThreadBoats(gridpaneBoats, imgListBoat, ip, myListener, admin));
        threadBoats.start();
    }

    @FXML
    private void addRace(ActionEvent actionEvent) {
        String name = nameRace.getText();
        String date = dateRace.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String price = priceRace.getText();
        SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d1 = sdformat.parse(date);
            Date d2 = sdformat.parse(LocalDate.now().toString());
            if (name.isBlank() || name.isEmpty())
                Utilities.showAlert("Input Error!", "Input empty!", "Insert a name ");
            else if (date.isBlank() || date.isEmpty() || d1.before(d2) || d1.equals(d2))
                Utilities.showAlert("Input Error!", "Input empty!", "Insert a date ");
            else if (price.isBlank() || price.isEmpty())
                Utilities.showAlert("Input Error!", "Input empty!", "Insert a price ");
            else {
                c.doAction(PROT_ADD_RACE, name + SEP_PARAM + date + SEP_PARAM + "TBD" + SEP_PARAM + "TBD" + SEP_PARAM + price);
                nameRace.setText("");
                priceRace.setText("");
                dateRace.setValue(LocalDate.now());
                Utilities.showAlert("Adding race!", "Add new race", "Successful operation ");
                sendNotSomeMembers(new Notification("New race available", "green"), getAllMemberDB());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Member> getExpiredBMember() {
        return (List<Member>) c.doAction(PROT_GET_EXPBMEMBER, "pizza-acciuga");
    }

    private void sendNotSomeMembers(Notification n, List<Member> members) {
        for (Member m : members) {
            addNotificationDB(n, m.getUsername());
        }
    }

    @FXML
    private void removeRace(ActionEvent actionEvent) {
        if (tableAll.getSelectionModel().getSelectedItem() != null) {
            Race r = tableAll.getSelectionModel().getSelectedItem();
            if (Utilities.showConfirmation("Delete race", "Delete race", "Are you sure you want to delete Race: " + r.getName() + "?"))
                deleteRaceDB(r);
        } else
            Utilities.showAlert("Error", "No item selected", "Please select an item");
    }

    private void deleteRaceDB(Race r) {
        c.doAction(PROT_DELETE_RACE, r.getId());
    }

    private List<Race> getAllRaceDB() {
        return (List<Race>) c.doAction(PROT_GET_ALLRACES, "pizza-acciuga");
    }

    //Method that manages the visualization of the races in the staff menu
    private void showPrix(List<Race> races, boolean held) {
        threadPrix.interrupt();
        tableAll.getItems().clear();
        for (Race r : races) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date1 = sdf.parse(r.getDate());
                Date date2 = sdf.parse(String.valueOf(LocalDateTime.now()));
                if ((date1.before(date2)) && held)
                    tableAll.getItems().add(r);
                else if ((date1.after(date2) || date1.equals(date2)) && !held)
                    tableAll.getItems().add(r);
            } catch (ParseException ignored) {
            }
        }
    }

    @FXML
    private void heldRace(ActionEvent actionEvent) {
        showPrix(getAllRaceDB(), true);
    }

    @FXML
    private void availableRace(ActionEvent actionEvent) {
        showPrix(getAllRaceDB(), false);
    }

    @FXML
    private void allRace(ActionEvent actionEvent) {
        threadPrix = new Thread(new ThreadPrix(tableAll, ip));
        threadPrix.start();
    }


    private List<Transaction> getAllTransDB() {
        return (List<Transaction>) c.doAction(PROT_GET_ALLTRANS, "pizza-acciuga");
    }

    //Method that manages the visualization of the transactions
    private void showTrans(List<Transaction> trans, String username, String race, String boat) {
        threadTrans.interrupt();
        tableHist.getItems().clear();
        for (Transaction t : trans) {
            if (t.getUsername().equals(username) && race.equals("-1") && boat.equals("-1")) tableHist.getItems().add(t);

            if (t.getMotive().split(":")[0].equals("RACE"))
                if (t.getMotive().split(":")[1].contains(race) && username.equals("-1") && boat.equals("-1"))
                    tableHist.getItems().add(t);

            if (t.getMotive().split(":")[0].equals("BOAT"))
                if (t.getMotive().split(":")[1].contains(boat) && username.equals("-1") && race.equals("-1"))
                    tableHist.getItems().add(t);
        }
    }

    @FXML
    private void searchUserPay(ActionEvent actionEvent) {
        showTrans(getAllTransDB(), userPay.getText(), "-1", "-1");
    }

    @FXML
    private void searchRacePay(ActionEvent actionEvent) {
        showTrans(getAllTransDB(), "-1", racePay.getText(), "-1");
    }

    @FXML
    private void searchBoatPay(ActionEvent actionEvent) {
        showTrans(getAllTransDB(), "-1", "-1", boatPay.getText());
    }

    @FXML
    private void resetPay(ActionEvent actionEvent) {
        threadTrans = new Thread(new ThreadTransaction(tableHist, ip));
        threadTrans.start();
    }

    private List<Member> getAllMemberDB() {
        return (List<Member>) c.doAction(PROT_GET_ALLMEMBERS, "pizza-acciuga");
    }

    //Method that manages the visualization of all the users
    private void showMembers(List<Member> members, String username, String city) {
        threadMember.interrupt();
        tableMembers.getItems().clear();
        for (Member m : members) {
            List<Address> address = (List<Address>) c.doAction(PROT_GET_ADDRESS, m.getFiscalCode());
            if (!address.isEmpty())
                m.setAddress(address.get(0));
            if (m.getUsername().equals(username) && city.equals("-1")) tableMembers.getItems().add(m);
            if (m.getCity().contains(city) && username.equals("-1")) tableMembers.getItems().add(m);
        }
    }

    @FXML
    private void searchUserMember(ActionEvent actionEvent) {
        showMembers(getAllMemberDB(), userMember.getText(), "-1");
    }

    @FXML
    private void searchCityMember(ActionEvent actionEvent) {
        showMembers(getAllMemberDB(), "-1", cityMember.getText());
    }

    private void addNotificationDB(Notification n, String username) {
        c.doAction(PROT_ADD_NOTIFICATION, n.getMessage() + SEP_PARAM + n.getColor() + SEP_PARAM + username);
    }

    @FXML
    private void sendNotMember(ActionEvent actionEvent) {
        if (tableMembers.getSelectionModel().getSelectedItem() != null) {
            Member m = tableMembers.getSelectionModel().getSelectedItem();
            addNotificationDB(new Notification("Your season pass is expiring soon", "orange"), m.getUsername());
        } else
            Utilities.showAlert("Error", "No item selected", "Please select an item");
    }

    @FXML
    private void deleteMember(ActionEvent actionEvent) {
        if (tableMembers.getSelectionModel().getSelectedItem() != null) {
            Member m = tableMembers.getSelectionModel().getSelectedItem();
            if (Utilities.showConfirmation("Delete", "Delete Member", "Are you sure you want to delete user: " + m.getUsername() + "?"))
                c.doAction(PROT_DELETE_MEMBER, m.getUsername());
        } else
            Utilities.showAlert("Error", "No item selected", "Please select an item");
    }

    @FXML
    private void resetMember(ActionEvent actionEvent) {
        threadMember = new Thread(new ThreadMember(tableMembers, ip));
        threadMember.start();
    }
}
