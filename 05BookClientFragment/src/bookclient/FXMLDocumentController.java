/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookclient;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import pkgData.Book;
import pkgMisc.Controller;

/**
 *
 * @author gerald
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private ImageView picBook;

    @FXML
    private Button btnConnection;

    @FXML
    private Button btnGetBook;

    @FXML
    private Button btnGetAllBooks;

    @FXML
    private Button btnAddBook;

    @FXML
    private Button btnUpdateBook;

    @FXML
    private Button btnDeleteBook;

    @FXML
    private Button btnImage;

    @FXML
    private TextField txtMessage;

    @FXML
    private TextField txtImageName;

    @FXML
    private TextField txtUrl;

    @FXML
    private TextField txtBookId;
    @FXML
    private TextField txtBookTitle;
    @FXML
    private TextField txtBookAuthor;

    @FXML
    private TextField txtBookDetails;

    @FXML
    private TableView<Book> tblBooks;

    @FXML
    private TableColumn<Book, Integer> colId;

    @FXML
    private TableColumn<Book, String> colTitle;

    @FXML
    private TableColumn<Book, String> colAuthor;

    @FXML
    void onBtnAddBook(ActionEvent event) {
        try {
            Book book = new Book();
            book.setId(Integer.parseInt(txtBookId.getText()));
            book.setTitle(txtBookTitle.getText());
            book.setAuthor(txtBookAuthor.getText());
            book.setNameOfImage(txtImageName.getText());
            String retString = controller.addBook(book);
            txtMessage.setText("book added with: " + retString);
        } catch (Exception ex) {
            txtMessage.setText("error: " + ex.getMessage());
        }
    }

    @FXML
    void onBtnConnection(ActionEvent event) {
        try {
            controller.setUri(txtUrl.getText());
            txtMessage.setText("connection set, but not proofed");
        } catch (Exception ex) {
            txtMessage.setText("error: " + ex.getMessage());
            
        }
    }

    @FXML
    void onBtnDeleteBook(ActionEvent event) {
        try {
            String retString = controller.deleteBook(Integer.parseInt(txtBookId.getText()));
            txtMessage.setText("book deleted with: " + retString);
        } catch (Exception ex) {
            txtMessage.setText("error: " + ex.getMessage());
        }
    }

    @FXML
    void onBtnGetBook(ActionEvent event) {
        try {
            Book book = controller.getBook(txtBookId.getText());
            txtBookDetails.setText(book.toString());
            txtBookId.setText(Integer.toString(book.getId()));
            txtBookTitle.setText(book.getTitle());
            txtBookAuthor.setText(book.getAuthor());
            txtMessage.setText("book found");
        } catch (Exception ex) {
            txtMessage.setText("error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @FXML
    void onBtnUpdateBook(ActionEvent event) {
        try {
            Book book = new Book();
            book.setId(Integer.parseInt(txtBookId.getText()));
            book.setTitle(txtBookTitle.getText());
            book.setAuthor(txtBookAuthor.getText());
            book.setNameOfImage(txtImageName.getText());
            String retString = controller.updateBook(book);
            txtMessage.setText("book updated with: " + retString);
        } catch (Exception ex) {
            txtMessage.setText("error: " + ex.getMessage());
        }
    }

    @FXML
    void onButtonGetAllBooks(ActionEvent event) {
//        try {
//            ArrayList<Book> collBooks = controller.getBooks();
//            //assign table columns to class attributes (bean - definition(!))
//            colId.setCellValueFactory(new PropertyValueFactory<>("id"));
//            colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
//            colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
//            tblBooks.getItems().clear();
//            tblBooks.getItems().addAll(collBooks);
//
//            txtMessage.setText(collBooks.size() + " books listed");
//
//        } catch (Exception ex) {
//            txtMessage.setText("error: " + ex.getMessage());
//        }
    }

    @FXML
    void onTableEntrySelected(MouseEvent event) {
        Book book = tblBooks.getSelectionModel().getSelectedItem();
        txtBookDetails.setText(book.toString());
        txtBookId.setText(Integer.toString(book.getId()));
        txtBookTitle.setText(book.getTitle());
        txtBookAuthor.setText(book.getAuthor());
        this.txtImageName.setText(book.getNameOfImage());
        txtMessage.setText("book selected");
    }

    @FXML
    void onBtnUploadBook(ActionEvent event) {
//        try {
//            String msg = controller.addImage(Integer.parseInt(txtBookId.getText()), txtImageName.getText());
//            txtMessage.setText(msg);
//        } catch (Exception ex) {
//            txtMessage.setText("error: " + ex.getMessage());
//        }
    }

    @FXML
    void onBtnDownloadBook(ActionEvent event) {
//        try {
//            controller.downloadImage(Integer.parseInt(txtBookId.getText()));
//            showImage();
//            txtMessage.setText("image should be shown");
//        } catch (Exception ex) {
//            txtMessage.setText("error: " + ex.getMessage());
//        }
    }

    @FXML
    void onBtnImage(ActionEvent event) {
        showImage();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * other methods
     */
    private void showImage() {
//        try {
//            Image img = new Image("file:" + Controller.DEFAULT_IMAGENAME);
//            picBook.setImage(img);
//            txtMessage.setText("image should be shown: " + img);
//        } catch (Exception ex) {
//            txtMessage.setText("error: " + ex.getMessage());
//        }
    }
    /**
     * other attributes
     */
    private final Controller controller = new Controller();
}
