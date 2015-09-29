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
public class  BookOutClass {
        public List<BookOut> bookouts;

        public BookOutClass(List<BookOut> bookouts) {
	        this.bookouts = bookouts;
            //this.books.addAll(books);
	    }

        public Object[][] bookOutToArray() {
            Object[][] arrBookOut = new Object[bookouts.size()][8];
            int i = 0;

	        for (BookOut bookout : bookouts){
                arrBookOut[i][0]=bookout.out_year;
                arrBookOut[i][1]=bookout.out_no;
                arrBookOut[i][2]=bookout.isbn;
                arrBookOut[i][3]=bookout.recieve_date;
                arrBookOut[i][4]=bookout.title;
                arrBookOut[i][5]=bookout.title2;
                arrBookOut[i][6]=bookout.author;
                arrBookOut[i][7]=bookout.category;
                //System.out.println("TITLE="+arrBook[i][0]+" AUTHOR="+arrBook[i][1]);
                i = i+1;
            }
	        return arrBookOut;
	    }

	    public static class BookOut {
	       private String out_year;
	        private String out_no;
	        private String isbn;
                private String recieve_date;
	        private String title;
                private String title2;
                private String author;
	        private String category;

	        public BookOut(String out_year, String out_no, String isbn, String recieve_date, String title, String title2, String author, String category) {
	            this.isbn = isbn;
                    this.out_no = out_no;
	            this.out_year = out_year;
                    this.recieve_date = recieve_date;
                    this.title = title;
	            this.title2 = title2;
                    this.author = author;
                    this.category = category;
	        }

	    }

	}