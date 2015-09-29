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
public class  MediaOutClass {
        public List<MediaOut> mediaouts;

        public MediaOutClass(List<MediaOut> mediaouts) {
            this.mediaouts = mediaouts;
        //this.books.addAll(books);
        }

        public Object[][] mediaOutToArray() {
            Object[][] arrMediaOut = new Object[mediaouts.size()][7];
            int i = 0;

	        for (MediaOut mediaout : mediaouts){
                arrMediaOut[i][0]=mediaout.out_year;
                arrMediaOut[i][1]=mediaout.out_no;
                arrMediaOut[i][2]=mediaout.isbn;
                arrMediaOut[i][3]=mediaout.recieve_date;
                arrMediaOut[i][4]=mediaout.title;
                arrMediaOut[i][5]=mediaout.title2;
                arrMediaOut[i][6]=mediaout.author;
                //System.out.println("TITLE="+arrBook[i][0]+" AUTHOR="+arrBook[i][1]);
                i = i+1;
            }
	    return arrMediaOut;
	}
        public static class MediaOut {
            private String out_year;
            private String out_no;
            private String isbn;
            private String recieve_date;
            private String title;
            private String title2;
            private String author;

            public MediaOut(String out_year, String out_no, String isbn, String recieve_date, String title, String title2, String author) {
                this.isbn = isbn;
                this.out_no = out_no;
                this.out_year = out_year;
                this.recieve_date = recieve_date;
                this.title = title;
                this.title2 = title2;
                this.author = author;
            }

        }

    }

