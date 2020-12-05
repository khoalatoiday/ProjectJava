package view;

import controller.ControllerUtility;
import controller.DataController;
import model.Book;
import model.BookReaderManagement;
import model.Reader;

import java.util.ArrayList;
import java.util.Scanner;

public class view {
    public static void main(String[] args) {
        int choice = 0;
        var booksFileName = "BOOK.DAT";
        var controller = new DataController();
        var books = new ArrayList<Book>();
        var isBookChecked = false;

        var readersFileName = "READER.DAT";
        var readers = new ArrayList<Reader>();
        var isReaderChecked = false;

        var BrmsFileName = "BRM.DAT";
        var brms = new ArrayList<BookReaderManagement>();

        var Utility = new ControllerUtility();


        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("_______________MENU_______________");
            System.out.println("1. Thêm một đầu sách vào file.");
            System.out.println("2. Hiển thị danh sách các sách có trong file.");
            System.out.println("3. Thêm một bạn đọc vào file");
            System.out.println("4. Hiển thị danh sách bạn đọc có trong file");
            System.out.println("5. Lập thông tin quản lý mượn:");
            System.out.println("6. Sắp xếp");
            System.out.println("7. Tìm kiếm bạn đọc:");
            System.out.println("0. Thoát khỏi ứng dụng.");
            System.out.println("Bạn chọn ? ");

            choice = scanner.nextInt();
            scanner.nextLine();// doc bo dong chua lua chon

            switch (choice) {
                case 0:
                    System.out.println("Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi!");
                    break;

                case 1:
                    if (!isBookChecked) {
                        checkBookID(controller, booksFileName);
                        isBookChecked = true;
                    }
                    String[] specs = {"Science", "Art", "Economic", "IT"};
                    String bookName, author, spec;
                    int year, quan, sp;
                    System.out.println("Nhập tên sách: ");
                    bookName = scanner.nextLine();

                    System.out.println("Nhập tên tác giả: ");
                    author = scanner.nextLine();

                    do {
                        System.out.println("Nhập thể loại sách: ");
                        System.out.println("1. Science.\n2. Art.\n3. Economic.\n4. IT.");
                        sp = scanner.nextInt();
                    } while (sp < 1 || sp > 4);

                    spec = specs[sp - 1];

                    System.out.println("Nhập năm xuất bản: ");
                    year = scanner.nextInt();

                    System.out.println("Nhập số lượng: ");
                    quan = scanner.nextInt();
                    //public Book(int bookID, String bookName, String author,
                    //                String specialization, int publishYear, int quantity)
                    Book book = new Book(0, bookName, author, spec, year, quan);
                    controller.writeBookToFile(book, booksFileName);
                    break;

                case 2:
                    books = controller.readBooksFromFile(booksFileName);
                    showBookInfo(books);
                    break;
                case 3:
//                    reader.getReaderID() + "|" + reader.getFullName() + "|"
//                        + reader.getAddress() + "|" + reader.getPhoneNumber()

                    if (!isBookChecked) {
                        checkReaderID(controller, readersFileName);
                        isBookChecked = true;
                    }

                    String fullname, address, phonenumber;

                    System.out.println("Nhập tên của reader:");
                    fullname = scanner.nextLine();

                    System.out.println("Nhập địa chỉ của reader: ");
                    address = scanner.nextLine();


                    do {
                        System.out.println("Nhập số điện thoại của reader:"); // có 10 số
                        phonenumber = scanner.nextLine();
                    } while (phonenumber.matches("\\d{10}"));
                    //int readerID, String fullName, String address, String phoneNumber
                    Reader reader = new Reader(0, fullname, address, phonenumber);
                    controller.writeReaderToFile(reader, readersFileName);
                    break;
                case 4:
                    readers = controller.readReadersFromFile(readersFileName);
                    showReaderInfo(readers);
                    break;
                case 5:
                    // đọc các thông tin trong danh sách bạn đọc, sách và quản lý mượn
                    readers = controller.readReadersFromFile(readersFileName);
                    books = controller.readBooksFromFile(booksFileName);
                    brms = controller.readBRMsFromFile(BrmsFileName);

                    // Chọn một bạn đọc, thực hiện chức năng cho mượn

                    int readerID;
                    boolean isBorrowalbe = false;

                    do {
                        showReaderInfo(readers);
                        System.out.println("_________________________________");
                        System.out.println("Nhập mã bạn đọc,nhập 0 để bỏ qua:");
                        readerID = scanner.nextInt();
                        if (readerID == 0) { // tất cả bạn đọc đã mượn sách theo quy định
                            break;
                        }
                        isBorrowalbe = checkBorrowed(brms, readerID);
                        if (isBorrowalbe) {
                            break;
                        } else {
                            System.out.println("Bạn đã mượn quá số lượng cho phép!");
                        }
                    } while (true);


                    // chọn một đầu sách cho bạn đọc mượn

                    int booksID;
                    boolean isFull = false;
                    do {
                        showBookInfo(books);
                        System.out.println("__________________________________");
                        System.out.println("Nhập mã sách, nhập 0 để bỏ qua:");
                        booksID = scanner.nextInt();
                        if (booksID == 0) {
                            break;
                        }
                        isFull = checkFull(brms, readerID, booksID); // true nếu đã mượn đủ 3
                        if (isFull) {
                            System.out.println("Vui lòng chọn đầu sách khác!");
                        } else {
                            break;
                        }
                    } while (true);

                    // nếu được mượn tiếp thì cập nhập tình trạng sách
                    int total = getTotal(brms, readerID, booksID);
                    do {
                        System.out.println("Nhập số lượng muốn mượn(đã mượn" + total + "): ");
                        int x = scanner.nextInt();
                        if (x + total >= 1 && x + total <= 3) { // thỏa mãn số lượng có thể mượn
                            total += x;
                            break;
                        } else {
                            System.out.println("Vượt quá số lượng cần mượn cho một đầu sách!" +
                                    " Vui lòng mượn đầu sách khác");
                        }
                    } while (true);
                    scanner.nextLine();

                    System.out.println("Nhập tình trạng: ");
                    String status = "";
                    status = scanner.nextLine();

                    Book currentBook = getBook(books, booksID);
                    Reader currentReader = getReader(readers, readerID);
                    BookReaderManagement b = new BookReaderManagement(currentBook, currentReader,
                            total, status, 0);

                    //B4:
                    brms = Utility.updateBrms(brms, b); // cập nhật danh sách quản lý mượn
                    controller.updateBRMFile(brms, BrmsFileName);// cập nhật file

                    //B5: Hiển thị ra màn hình
                    showBrmInfo(brms);
                    break;

                case 6:
                    brms = controller.readBRMsFromFile(BrmsFileName); // đọc ra danh sách quản lý
                    // update tổng số lượng mượn
                    brms = Utility.updateTotalBorrowed(brms);
                    System.out.println("______________________________________");
                    System.out.println("Các lựa chọn sắp xếp: ");
                    int x = 0;
                    do {
                        System.out.println("1. Sắp xếp theo tên bạn đọc(từ A->Z: ");
                        System.out.println("2. Sắp xếp theo tổng số lượng mượn(giảm dần): ");
                        System.out.println("0. Trở lại menu:");
                        System.out.println("Bạn chọn? ");
                        x = scanner.nextInt();
                        if (x == 0) {
                            break;
                        }
                        switch (x) {
                            case 1:
                                brms= Utility.sortbyReaderName(brms);
                                showBrmInfo(brms);
                                break;
                            case 2:
                                brms=Utility.sortbyNumofBorrowed(brms);
                                showBrmInfo(brms);
                                break;
                        }
                    } while (true);
                    break;
                case 7: // tìm kiếm bạn đọc theo tên
                    brms=controller.readBRMsFromFile(BrmsFileName);
                    System.out.println("Nhập cụm từ có tên bạn đọc cần tìm: ");
                    String key= scanner.nextLine();
                    var  result =Utility.SearchByReaderName(brms,key);

                    if(result.size()==0){
                        System.out.println("Không tìm thấy bạn đọc!");
                    }
                    else{
                        showBrmInfo(result);
                    }
                    System.out.println();
                    break;
            }
        } while (choice != 0);
    }

    private static void showBrmInfo(ArrayList<BookReaderManagement> brms) {

        for (var brm : brms) {
            System.out.println(brm);
        }
    }

    private static Reader getReader(ArrayList<Reader> readers, int readerID) {
        for (int i = 0; i < readers.size(); i++) {
            if (readers.get(i).getReaderID() == readerID) {
                return readers.get(i);
            }
        }
        return null;
    }

    private static Book getBook(ArrayList<Book> books, int booksID) {
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getBookID() == booksID) {
                return books.get(i);
            }
        }
        return null;
    }

    private static int getTotal(ArrayList<BookReaderManagement> brms, int readerID, int booksID) {
        for (var r : brms) {
            if (r.getReader().getReaderID() == readerID && r.getBook().getBookID() == booksID) {
                return r.getNumOfBorrow();  // lấy tổng số lượng sách đã mượn của đầu sách booksID
            }
        }
        return 0;
    }

    private static boolean  checkFull(ArrayList<BookReaderManagement> brms, int readerID, int booksID) {
        for (var r : brms) {
            if (r.getReader().getReaderID() == readerID && r.getBook().getBookID() == booksID
                    && r.getNumOfBorrow() == 3) {
                return true; // nếu bạn đọc mượn một đầu sách tối đa đến 3 lần thì không được mượn nữa
            }
        }
        return false;
    }

    private static boolean checkBorrowed(ArrayList<BookReaderManagement> brms, int readerID) {
        int count = 0;
        for (var r : brms) {
            if (r.getReader().getReaderID() == readerID) {
                count += r.getNumOfBorrow();
            }
        }
        if (count == 15) {
            return false;
        }
        return true;
    }

    private static void showReaderInfo(ArrayList<Reader> readers) {
        System.out.println("_________________Thông tin readers trong file_________________");
        for (var r : readers) {
            System.out.println(r);
        }

    }

    private static void checkReaderID(DataController controller, String readersFileName) {
        var readers = controller.readReadersFromFile(readersFileName);
        if (readers.size() == 0) {

        } else {
            Reader.setId(readers.get(readers.size() - 1).getReaderID() + 1);
        }
    }

    private static void checkBookID(DataController controller, String fileName) {
        var listBooks = controller.readBooksFromFile(fileName);
        if (listBooks.size() == 0) {

        } else {
            Book.setId(listBooks.get(listBooks.size() - 1).getBookID() + 1);
        }
    }

    private static void showBookInfo(ArrayList<Book> books) {
        System.out.println("_________________Thông tin sách trong file_________________");
        for (var b : books) {
            System.out.println(b);
        }

    }
}
