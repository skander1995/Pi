/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Entities.Reclamation;
import EnumPack.Etat;
import EnumPack.ReclamationType;
import Services.ReclamationService;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author cobwi
 */
public class ReclamationAdminPanelController implements Initializable {

    @FXML
    private JFXComboBox<ReclamationType> reclamationTypeCombo;
    @FXML
    private TextField searchField;
    @FXML
    private TableView<Reclamation> reclamationData;

    private ObservableList<ObservableList> data;
    private Reclamation oldUser = null;

    // popover shit
    TextField nomField = new TextField();
    TextField prenomField = new TextField();
    DatePicker DateNaissanceField = new DatePicker();
    TextField telephoneField = new TextField();
    ImageView imgProfil = new ImageView();
    VBox vBox = new VBox(imgProfil, nomField, prenomField, DateNaissanceField, telephoneField);
    PopOver popOver = new PopOver(vBox);
    PopOver printPopover = new PopOver();
    private ObservableList<Reclamation> reclamationList;
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

        reclamationTypeCombo.getItems().addAll(ReclamationType.ContenuInnaproprié, ReclamationType.MessageInnapproprie, ReclamationType.PhotosInnapproprié, ReclamationType.autre);

        //************************
        ReclamationService reclamationService = new ReclamationService();

        TableColumn<Reclamation, String> firstNameCol = new TableColumn<>("Sujet");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        TableColumn<Reclamation, String> lastNameCol = new TableColumn<>("Description");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        TableColumn<Reclamation, String> dateCol = new TableColumn<>("Date de publication");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("datePub"));

        TableColumn<Reclamation, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));

        TableColumn<Reclamation, Reclamation> reclamationType = new TableColumn<>("Type");
        reclamationType.setCellFactory((param) -> {
            //Set up the cell
            final Button btn = new Button();
            //Set up the Table
            TableCell<Reclamation, Reclamation> cell = new TableCell<Reclamation, Reclamation>() {
                @Override
                public void updateItem(Reclamation item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        Reclamation reclamation = new Reclamation();
                        btn.setText(item.getEtat().name());
                        if (btn.getText().equals("terminer")) {
                            btn.setStyle("-fx-background-color: #2D882D");
                        } else if (btn.getText().equals("enattente")) {
                            btn.setStyle("-fx-background-color: #FFA62C");
                        }
                        btn.setOnAction(event -> {
                            if (btn.getText().equals("terminer")) {
                                btn.setStyle("-fx-background-color: #2D882D");
                                btn.setText("encours");
                                reclamation.setEtat(Etat.encours);
                                HashMap<String, String> args = new HashMap<>();
                                args.put("ETAT", Etat.encours.name());
                                reclamationService.update(item, args);
                            } else {
                                btn.setStyle("-fx-background-color: #FFA62C");
                                btn.setText("terminer");
                                reclamation.setEtat(Etat.terminer);
                                HashMap<String, String> args = new HashMap<>();
                                args.put("ETAT", Etat.terminer.name());
                                reclamationService.update(item, args);
                            }
                        });
                    }
                }
            };
            cell.setGraphic(btn);
            return cell;
        });
        reclamationType.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        reclamationData.getColumns().addAll(firstNameCol, lastNameCol, dateCol, typeCol, reclamationType);
        //reclamationList = FXCollections.observableArrayList(reclamationService.getAllTruncConnectedUser());
        //reclamationData.getItems().addAll(reclamationList);
        initializeTableView();
    }

    private void setValues(Reclamation newvalue) {
        oldUser = newvalue;
    }

    private void handleEditWithPopOver(Reclamation reclamation, double sceneX, double sceneY) {
        try {
            // load fxml inside
            Node content = null;

            FXMLLoader fxmlLoader = new FXMLLoader();
            content = (Parent) fxmlLoader.load(getClass().getResourceAsStream("/View/popOverContent.fxml"));
            popOver.headerAlwaysVisibleProperty().setValue(Boolean.TRUE);
            popOver.consumeAutoHidingEventsProperty().setValue(Boolean.FALSE);
            //PopOverContentController popOverController = new PopOverContentController();
            PopOverContentController popOverController = fxmlLoader.getController();
            //popOverController.initWithData(this, reclamation);
            popOver.setContentNode(content);
            popOver.show(reclamationData.getParent(), sceneX, sceneY);
        } catch (IOException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = from handleEditwith popover" + ex.getMessage());
            }
        }
    }

    public void refreshView() {
        //reclamationData.clear();
//        reclamationData.getColumns().clear();

        initializeTableView();

        popOver.hide(Duration.ONE);
        reclamationData.refresh();

    }

    @FXML
    private void comboFilter(ActionEvent event) {

    }

    private void initializeTableView() {
        //reclamationData.getItems().clear();
        ReclamationService reclamationService = new ReclamationService();
//        reclamationList.clear();
        reclamationList = FXCollections.observableArrayList(reclamationService.getAll());
        // re check needed
        reclamationData.getSelectionModel().selectedItemProperty().addListener((Observable, oldvalue, newvalue) -> setValues(newvalue));
        // ***************** filtering 
        reclamationData.setOnMouseClicked((event) -> {
            if (event.getClickCount() == 2) {
                Reclamation reclamationRow = reclamationData.getSelectionModel().getSelectedItem();
                System.out.println("reclamationRow.() = " + reclamationRow);
                if (reclamationRow != null) {
                    handleEditWithPopOver(reclamationRow, event.getSceneX(), event.getSceneY());
                }

            }
        });

        //////////////////
        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Reclamation> filteredData = new FilteredList<>(reclamationList, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(reclamation -> {
                // If filter text is empty, display all reclamations.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every reclamation with filter text.
                String lowerCaseFilter = newValue.toLowerCase();
                try {
                    if (reclamation.getDescription().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (reclamation.getSujet().contains(lowerCaseFilter))/*.toLowerCase().contains(lowerCaseFilter)) */ {
                        return true; // Filter matches last name.
                    } else if (reclamation.getDatePub().toString().contains(lowerCaseFilter)) {
                        return true;
                    } else if (reclamation.getEtat().name().toLowerCase().contains(lowerCaseFilter)) {
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
        SortedList<Reclamation> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(reclamationData.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.

        reclamationData.skinProperty().addListener((a, b, newSkin) -> {
            TableViewSkin<?> skin = (TableViewSkin<?>) reclamationData.getSkin();
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
        reclamationData.setItems(sortedData);
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
            popOverController.table = this.reclamationData;
            printPopover.setContentNode(content);
            printPopover.show(printButton, printButton.getLayoutX(), printButton.getLayoutY());
        } catch (IOException ex) {
            if (ToolsUtilities.DEBUG) {
                System.out.println("Catched = from print popover" + ex.getMessage());
            }
        }
    }

    @FXML
    private void popPopUp(MouseEvent event) {
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
