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
public class  BookClass {
    public List<SubBook> subbooks;

    public BookClass(List<SubBook> subbooks) {
        this.subbooks = subbooks;
    //this.books.addAll(books);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("Library with " + subbooks.size() + " books:\n");
        for (SubBook subbook : subbooks) {
            b.append(subbook.toString());
            // Skip a line
            b.append("\n");
        }
        return b.toString();
    }
    public Object[][] BooktoArray() {
        Object[][] arrBook = new Object[subbooks.size()][26];
        int i = 0;
        //StringBuilder b = new StringBuilder();
        for (SubBook subbook : subbooks){
            arrBook[i][0]=subbook.isbn;
            arrBook[i][1]=subbook.title;
            arrBook[i][2]=subbook.author;
            arrBook[i][3]=subbook.price;
            arrBook[i][4]=subbook.book_flag;
            arrBook[i][5]=subbook.book_catID;
            arrBook[i][6]=subbook.recieve_date;
            arrBook[i][7]=subbook.category_no;
            arrBook[i][8]=subbook.publishing;
            arrBook[i][9]=subbook.year_print;
            arrBook[i][10]=subbook.edition;
            arrBook[i][11]=subbook.volumn;
            arrBook[i][12]=subbook.issue;
            arrBook[i][13]=subbook.prefix_author;
            arrBook[i][14]=subbook.penname;
            arrBook[i][15]=subbook.translator;
            arrBook[i][16]=subbook.title2;
            arrBook[i][17]=subbook.short_name;
            arrBook[i][18]=subbook.series_name;
            arrBook[i][19]=subbook.num_page;
            arrBook[i][20]=subbook.picture;
            arrBook[i][21]=subbook.short_topic;
            arrBook[i][22]=subbook.book_size;
            arrBook[i][23]=subbook.source;
            arrBook[i][24]=subbook.remark;
            arrBook[i][25]=subbook.book_id;
            //System.out.println("TITLE="+arrBook[i][0]+" AUTHOR="+arrBook[i][1]);
            i = i+1;
        }
        return arrBook;
    }

    public static class SubBook {
        private String isbn;
        private String title;
        private String author;

        private String price;
        private String book_flag;
        private String book_catID;
        private String recieve_date;
        private String category_no;
        private String publishing;
        private String year_print;
        private String edition;
        private String volumn;
        private String issue;
        private String prefix_author;
        private String penname;
        private String translator;
        private String title2;
        private String short_name;
        private String series_name;
        private String num_page;
        private String picture;
        private String short_topic;
        private String book_size;
        private String source;
        private String remark;
        private String book_id;

        public SubBook(String isbn, String title, String author) {
            this.isbn = isbn;
            this.title = title;
            this.author = author;
        }

        public SubBook(String isbn, String title, String author,String price, String book_flag, String book_catID,
                String recieve_date, String category_no, String publishing,
                String year_print, String edition, String volumn,
                String issue, String prefix_author, String penname,
                String translator, String title2, String short_name,
                String series_name, String num_page, String picture,
                String short_topic, String book_size, String source, String remark,String book_id) {
            this.isbn = isbn;
            this.title = title;
            this.author = author;
            this.price = price;
            this.book_flag = book_flag;
            this.book_catID = book_catID;
            this.recieve_date = recieve_date;
            this.category_no = category_no;
            this.publishing = publishing;
            this.year_print = year_print;
            this.edition = edition;
            this.volumn = volumn;
            this.issue = issue;
            this.prefix_author = prefix_author;
            this.penname = penname;
            this.translator = translator;
            this.title2 = title2;
            this.short_name = short_name;
            this.series_name = series_name;
            this.num_page = num_page;
            this.picture = picture;
            this.short_topic = short_topic;
            this.book_size = book_size;
            this.source = source;
            this.remark = remark;
            this.book_id = book_id;
        }

        @Override
        public String toString() {
            return isbn+"," + title + "," + author;
        }
    }

}

