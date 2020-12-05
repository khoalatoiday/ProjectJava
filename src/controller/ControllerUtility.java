package controller;

import model.Book;
import model.BookReaderManagement;

import java.util.ArrayList;

public class ControllerUtility {
    public ArrayList<BookReaderManagement> updateBrms(ArrayList<BookReaderManagement> list,
                                                      BookReaderManagement brm) {
        boolean isUpdate = false;
        for (int i = 0; i < list.size(); i++) {
            BookReaderManagement b = list.get(i);
            if (b.getBook().getBookID() == brm.getBook().getBookID() &&
                    b.getReader().getReaderID() == brm.getReader().getReaderID()) {
                list.set(i, brm); // cập nhật lại đối tượng quản lý mượn
                isUpdate = true;
                break;
            }
        }
        if (!isUpdate) {
            list.add(brm);
        }
        return list;
    }


    public ArrayList<BookReaderManagement> updateTotalBorrowed(ArrayList<BookReaderManagement> list) {
        for (int i = 0; i < list.size(); i++) {
            BookReaderManagement b = list.get(i);
            int count = 0;
            for (int j = 0; j < list.size(); j++) {
                if (list.get(j).getReader().getReaderID() == b.getReader().getReaderID()) {
                    count += list.get(j).getNumOfBorrow();
                }
            }
            b.setTotalBorrowed(count);
            list.set(i, b);
        }
        return list;
    }

    public ArrayList<BookReaderManagement> sortbyReaderName(ArrayList<BookReaderManagement> list) {
        for (int i = 0; i < list.size(); i++) {// sắp nổi bọt
            for (int j = list.size() - 1; j > i; j--) {
                BookReaderManagement bj = list.get(j);
                BookReaderManagement bbj = list.get(j - 1);
                String[] names1= bj.getReader().getFullName().split(" \\s"); // tách các từ trong fullname
                String[] names2= bj.getReader().getFullName().split(" \\s");
                if (names1[names1.length-1].compareTo(names2[names2.length-1])<0) {
                    list.set(j, bbj);
                    list.set(j - 1, bj);
                }
            }
        }
        return list;
    }


    public ArrayList<BookReaderManagement> sortbyNumofBorrowed(ArrayList<BookReaderManagement> list) {
        for (int i = 0; i < list.size(); i++) {// sắp nổi bọt
            for (int j = list.size() - 1; j > i; j--) {
                BookReaderManagement bj = list.get(j);
                BookReaderManagement bbj = list.get(j - 1);
                if (bj.getTotalBorrowed() > bbj.getTotalBorrowed()) {
                    list.set(j, bbj);
                    list.set(j - 1, bj);
                }
            }
        }
        return list;
    }

    public ArrayList<BookReaderManagement> SearchByReaderName
            (ArrayList<BookReaderManagement> list,String key) {
        ArrayList<BookReaderManagement> result =new ArrayList<>();
        String pattern = ".*"+key.toLowerCase()+".*";
        for(int i=0;i<list.size();i++){
            BookReaderManagement b= list.get(i);
            if(b.getReader().getFullName().toLowerCase().matches(pattern)){
                result.add(b);
            }
        }
        return result;
    }

}
