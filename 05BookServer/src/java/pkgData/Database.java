package pkgData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
 * Singleton
 */
public class Database {
	private final Map<Integer, Book> hmBooks = new HashMap<>();
	private static Database instance = null;

	private Database() {
		fillMap();
	}

	public static Database newInstance() {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}
	public ArrayList<Book> getBooks(){
	    return new ArrayList<>(hmBooks.values());
	}
	public Book getBook(int _id) throws Exception {
		Book retBook = null;

		retBook = hmBooks.get(_id);

		if (retBook == null)
			throw new Exception("no book found with id: " + _id);

		return retBook;
	}
	public void setBook(Book book) throws Exception {
		if (hmBooks.get(book.getId()) == null) {
			hmBooks.put(book.getId(), book);
		}
		else {
			throw new Exception("error: book-id already used");
		}
	}
	public void deleteBook(int _id) throws Exception {
		if (hmBooks.get(_id) != null) {
			hmBooks.remove(_id);
		}
		else {
			//the following exception cannot never be reached from client (@delete - bug)
			throw new Exception("error: book-id not used");
		}

	}
	private void fillMap() {
		Book b = new Book(11, "Raeuber Hotzenplotz","Ottfried Preussler");
		hmBooks.put(b.getId(), b);
		b = new Book(22, "Schatz am Silbersee", "Karl May");
		hmBooks.put(b.getId(), b);
		b = new Book(33, "Hercule Poirot","Agatha Christie" );
		hmBooks.put(b.getId(), b);
	}

}