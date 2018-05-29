/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.User;
import Services.UserService;
import Utilities.ToolsUtilities;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.sun.javafx.scene.control.skin.NestedTableColumnHeader;
import com.sun.javafx.scene.control.skin.TableColumnHeader;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import com.sun.javafx.scene.control.skin.TableViewSkin;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author cobwi
 */
public class UserAdminPanelController implements Initializable {

    @FXML
    private JFXComboBox<String> userTypeCombo;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<User> userData;

    private ObservableList<ObservableList> data;
    private User oldUser = null;

    // popover shit
    TextField nomField = new TextField();
    TextField prenomField = new TextField();
    DatePicker DateNaissanceField = new DatePicker();
    TextField telephoneField = new TextField();
    ImageView imgProfil = new ImageView();
    VBox vBox = new VBox(imgProfil, nomField, prenomField, DateNaissanceField, telephoneField);
    PopOver popOver = new PopOver(vBox);
    PopOver printPopover = new PopOver();
    private ObservableList<User> userList;
    @FXML
    private JFXButton printButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //********** initializing shit related popover here 
        //**** initializing combo
        ObservableList<String> observableComboList = FXCollections.observableArrayList("Tous", "Administrateur", "Utilisateur");
        userTypeCombo.setItems(observableComboList);
        //************************

        UserService userService = new UserService();
        /*TableColumn<User, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));*/

        TableColumn<User, String> imgCol = new TableColumn<>("Profile");
        //imgCol.setCellValueFactory(new PropertyValueFactory<>("photoProfil"));
        imgCol.setCellFactory((param) -> {
            //Set up the ImageView
            final ImageView imageview = new ImageView();
            imageview.setFitHeight(50);
            imageview.setFitWidth(50);

            //Set up the Table
            TableCell<User, String> cell = new TableCell<User, String>() {
                public void updateItem(String item, boolean empty) {
                    if (item != null) {
                        Image image = new Image(item);
                        imageview.setImage(image);
                    }
                }
            };
            // Attach the imageview to the cell
            cell.setGraphic(imageview);
            return cell;
        });
        imgCol.setCellValueFactory(new PropertyValueFactory<>("photoProfil"));

        TableColumn<User, String> firstNameCol = new TableColumn<>("Nom");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        TableColumn<User, String> lastNameCol = new TableColumn<>("Prenom");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        TableColumn<User, String> dnCol = new TableColumn<>("Date de Naissance");
        dnCol.setCellValueFactory(new PropertyValueFactory<>("dateNaissance"));

        TableColumn<User, String> phoneCol = new TableColumn<>("Telephone");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, User> userType = new TableColumn<>("Type");
        userType.setCellFactory((param) -> {
            //Set up the cell
            final Button btn = new Button();
            //Set up the Table
            TableCell<User, User> cell = new TableCell<User, User>() {
                @Override
                public void updateItem(User item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        User user = new User();
                        btn.setText(item.getType());
                        if (btn.getText().equals("admin")) {
                            btn.setStyle("-fx-background-color: #D91E18");
                        } else {
                            btn.setStyle("-fx-background-color: #446CB3");
                        }
                        btn.setOnAction(event -> {
                            if (btn.getText().equals("admin")) {
                                btn.setStyle("-fx-background-color: #446CB3");
                                user.setType("user");
                                HashMap<String, String> args = new HashMap<>();
                                args.put("TYPE_USR", "user");
                                userService.update(item, args);
                                btn.setText("user");
                            } else {
                                btn.setStyle("-fx-background-color: #D91E18");
                                user.setType("admin");
                                HashMap<String, String> args = new HashMap<>();
                                args.put("TYPE_USR", "admin");
                                userService.update(item, args);
                                btn.setText("admin");
                            }
                        });
                    }
                }
            };
            cell.setGraphic(btn);
            return cell;
        });
        userType.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        userData.getColumns().addAll(imgCol, firstNameCol, lastNameCol, dnCol, phoneCol, emailCol, userType);
        //userList = FXCollections.observableArrayList(userService.getAllTruncConnectedUser());
        //userData.getItems().addAll(userList);
        initializeTableView();
    }

    private void setValues(User newvalue) {
        oldUser = newvalue;
    }

    private void handleEditWithPopOver(User user, double sceneX, double sceneY) {
        try {
            // load fxml inside
            Node content = null;

            FXMLLoader fxmlLoader = new FXMLLoader();
            content = (Parent) fxmlLoader.load(getClass().getResourceAsStream("/View/popOverContent.fxml"));
            popOver.headerAlwaysVisibleProperty().setValue(Boolean.TRUE);
            popOver.consumeAutoHidingEventsProperty().setValue(Boolean.FALSE);
            //PopOverContentController popOverController = new PopOverContentController();
            PopOverContentController popOverController = fxmlLoader.getController();
            popOverController.initWithData(this, user);
            popOver.setContentNode(content);
            popOver.show(userData.getParent(), sceneX, sceneY);
        } catch (IOException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = from handleEditwith popover" + ex.getMessage());
            }
        }
    }

    public void refreshView() {
        //userData.clear();
//        userData.getColumns().clear();

        initializeTableView();

        popOver.hide(Duration.ONE);
        userData.refresh();

    }

    @FXML
    private void comboFilter(ActionEvent event) {

    }

    private void initializeTableView() {
        //userData.getItems().clear();
        UserService userService = new UserService();
//        userList.clear();
        userList = FXCollections.observableArrayList(userService.getAllTruncConnectedUser());
        // re check needed
        userData.getSelectionModel().selectedItemProperty().addListener((Observable, oldvalue, newvalue) -> setValues(newvalue));
        // ***************** filtering 
        userData.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                User userRow = userData.getSelectionModel().getSelectedItem();
                System.out.println("userRow.() = " + userRow);
                if (userRow != null) {
                    handleEditWithPopOver(userRow, event.getSceneX(), event.getSceneY());
                }

            }
        });

        //////////////////
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<User> filteredData = new FilteredList<>(userList, p -> true);

        userTypeCombo.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends String> selected, String oldValue, String newValue) -> {
            if (newValue.equalsIgnoreCase("administrateur")) {
                filteredData.setPredicate((person) -> {
                    if (person.getType().equalsIgnoreCase("administrateur")) {
                        return true;
                    }
                    return false;
                });
            } else if (newValue.equalsIgnoreCase("utilisateur")) {
                filteredData.setPredicate((person) -> {
                    if (person.getType().equalsIgnoreCase("utilisateur")) {
                        return true;
                    }
                    return false;
                });
            }
        });

        // 2. Set the filter Predicate whenever the filter changes.
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                try {
                    if (person.getNom().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (person.getLogin().contains(lowerCaseFilter))/*.toLowerCase().contains(lowerCaseFilter)) */ {
                        return true; // Filter matches last name.
                    } else if (person.getPrenom().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (person.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    } else if (person.getTelephone().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false; // Does not match.
                } catch (NullPointerException ex) {
                    if (ToolsUtilities.DEBUG) {
                        System.out.println("Catched from filter= " + ex.getMessage());
                    }
                    return false;
                }
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<User> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(userData.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.

        userData.skinProperty().addListener((a, b, newSkin) -> {
            TableViewSkin<?> skin = (TableViewSkin<?>) userData.getSkin();
            TableHeaderRow headerRow = skin.getTableHeaderRow();
            NestedTableColumnHeader rootHeader = headerRow.getRootHeader();
            for (TableColumnHeader columnHeader : rootHeader.getColumnHeaders()) {
                try {
                    TableColumn<?, ?> column = (TableColumn<?, ?>) columnHeader.getTableColumn();
                    if (column != null) {
                        Method method = skin.getClass().getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);
                        method.setAccessible(true);
                        method.invoke(skin, column, 30);
                    }
                } catch (Throwable e) {
                    e = e.getCause();
                    e.printStackTrace(System.err);
                }
            }

        });

        // adding a button to make it admin or else
        userData.setItems(sortedData);
    }

    @FXML
    private void popPopUp(ActionEvent event) {
        try {
            // load fxml inside
            Node content = null;

            FXMLLoader fxmlLoader = new FXMLLoader();
            content = (Parent) fxmlLoader.load(getClass().getResourceAsStream("/View/PopOverPrint.fxml"));

            //PopOverContentController popOverController = new PopOverContentController();
            PopOverPrintController popOverController = fxmlLoader.getController();
            popOverController.table = this.userData;
            printPopover.setContentNode(content);
            printPopover.show(printButton, printButton.getLayoutX(), printButton.getLayoutY());
        } catch (IOException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = from print popover" + ex.getMessage());
            }
        }
    }

    public static class EditButton extends Button {

        public EditButton(String fileName) {
            super("Edit");
            setOnAction((event) -> {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Hey!");
                alert.setHeaderText(null);
                alert.setContentText("You're editing \"" + fileName + "\"");
                alert.showAndWait();
            });
        }
    }

}
