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
public class MasterDataMediaClass {
    public List<MasterMedia> mastermedias;

	    public MasterDataMediaClass(List<MasterMedia> mastermedias) {
	        this.mastermedias = mastermedias;
            //this.books.addAll(books);
	    }

	    /*@Override
	    public String toString() {
	        StringBuilder b = new StringBuilder();
	        b.append("Library with " + books.size() + " books:\n");
	        for (Book book : books) {
	            b.append(book.toString());
	            // Skip a line
	            b.append("\n");
	        }
	        return b.toString();
	    }*/
        public Object[][] MasterMediatoArray() {
            Object[][] arrMedia = new Object[mastermedias.size()][3];
            int i = 0;
            //StringBuilder b = new StringBuilder();
	        for (MasterMedia mastermedia : mastermedias){
                arrMedia[i][0]=mastermedia.media_catID;
                arrMedia[i][1]=mastermedia.media_cat_name;
                arrMedia[i][2]=mastermedia.media_num_day;
                //System.out.println("TITLE="+arrBook[i][0]+" AUTHOR="+arrBook[i][1]);
                i = i+1;
            }
	        return arrMedia;
	    }

	    public static class MasterMedia {
	        private String media_catID;
                private String media_cat_name;
	        private String media_num_day;

	        public MasterMedia(String media_catID, String media_cat_name, String media_num_day) {
	            this.media_catID = media_catID;
                    this.media_cat_name = media_cat_name;
	            this.media_num_day = media_num_day;
	        }

	        /*@Override
	        public String toString() {
	            return media_catID+"," + media_cat_name + "," + media_num_day;
	        }*/
	    }
}
