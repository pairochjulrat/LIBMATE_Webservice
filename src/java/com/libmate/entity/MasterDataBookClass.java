/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.libmate.entity;

import java.util.List;
/**
 *
 * @author Administrator
 */
public class MasterDataBookClass {
    public List<MasterBook> masterbooks;

	    public MasterDataBookClass(List<MasterBook> masterbooks) {
	        this.masterbooks = masterbooks;
            //this.books.addAll(books);
	    }

	    @Override
	    public String toString() {
	        StringBuilder b = new StringBuilder();
	        b.append("Library with " + masterbooks.size() + " books:\n");
	        for (MasterBook book : masterbooks) {
	            b.append(book.toString());
	            // Skip a line
	            b.append("\n");
	        }
	        return b.toString();
            }

        public Object[][] MasterBooktoArray() {
            Object[][] arrBook = new Object[masterbooks.size()][3];
            int i = 0;
            //StringBuilder b = new StringBuilder();
	        for (MasterBook masterbook : masterbooks){
                arrBook[i][0]=masterbook.book_catID;
                arrBook[i][1]=masterbook.book_cat_name;
                arrBook[i][2]=masterbook.book_num_day;
                //System.out.println("TITLE="+arrBook[i][0]+" AUTHOR="+arrBook[i][1]);
                i = i+1;
            }
	        return arrBook;
	    }

	    public static class MasterBook {
	        private String book_catID;
                private String book_cat_name;
	        private String book_num_day;

	        public MasterBook(String book_catID, String book_cat_name, String book_num_day) {
	            this.book_catID = book_catID;
                    this.book_cat_name = book_cat_name;
	            this.book_num_day = book_num_day;
	        }

	        @Override
	        public String toString() {
	            return book_catID+"," + book_cat_name + "," + book_num_day;
	        }
	    }
}
