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
public class  MediaClass {
        public List<SubMedia> submedias;

        public MediaClass(List<SubMedia> submedias) {
            this.submedias = submedias;
        //this.books.addAll(books);
        }


        public Object[][] MediatoArray() {
            Object[][] arrMedia = new Object[submedias.size()][14];
            int i = 0;
            //StringBuilder b = new StringBuilder();
	        for (SubMedia submedia : submedias){
                arrMedia[i][0]=submedia.isbn;
                arrMedia[i][1]=submedia.title;
                arrMedia[i][2]=submedia.author;
                arrMedia[i][3]=submedia.price;
                arrMedia[i][4]=submedia.media_flag;
                arrMedia[i][5]=submedia.media_catID;
                arrMedia[i][6]=submedia.store;
                arrMedia[i][7]=submedia.publishing;
                arrMedia[i][8]=submedia.year_print;
                arrMedia[i][9]=submedia.receive_date;
                arrMedia[i][10]=submedia.source;
                arrMedia[i][11]=submedia.short_topic;
                arrMedia[i][12]=submedia.remark;
                arrMedia[i][13]=submedia.title2;

                //System.out.println("TITLE="+arrBook[i][0]+" AUTHOR="+arrBook[i][1]);
                i = i+1;
            }
	        return arrMedia;
	}

	public static class SubMedia{
	    private String isbn;
            private String title;
            private String author;
            private String price;
            private String media_flag;
            private String media_catID;
            private String store;
            private String publishing;
            private String year_print;
            private String receive_date;
            private String source;
            private String short_topic;
            private String remark;
            private String title2;

            /*public SubMedia(String isbn, String title, String author) {
                this.isbn = isbn;
                this.title = title;
                this.author = author;
            }*/

            public SubMedia(String isbn, String title, String author,
                    String title2, String price, String media_flag,String media_catID,
                    String store, String publishing,String year_print, String receive_date,
                    String source, String short_topic, String remark) {
                this.isbn = isbn;
                this.title = title;
                this.author = author;
                this.title2 = title2;
                this.price = price;
                this.media_flag = media_flag;
                this.media_catID = media_catID;
                this.store = store;
                this.publishing = publishing;
                this.year_print = year_print;
                this.receive_date = receive_date;
                this.source = source;
                this.short_topic = short_topic;
                this.remark = remark;
            }

            @Override
            public String toString() {
                return isbn+"," + title + "," + author;
            }
	}

}

