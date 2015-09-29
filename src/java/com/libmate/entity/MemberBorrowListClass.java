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
public class  MemberBorrowListClass {
        public List<BorrowList> borrowlists;

        public MemberBorrowListClass(List<BorrowList> borrowlists) {
	        this.borrowlists = borrowlists;
	    }

	    /*@Override
	    public String toString() {
	        StringBuilder b = new StringBuilder();
	        b.append("MemberClass with " + users.size() + " users:\n");
	        for (User user : users) {
	            b.append(user.toString());
	            // Skip a line
	            b.append("\n");
	        }
	        return b.toString();
	    }*/

        public Object[][] borrowListToArray() {
            Object[][] arrUser = new Object[borrowlists.size()][6];
            int i = 0;
            //userid+"," + isbn + "," + title+ "," + borrowDate+ "," + returnDate+ "," + borrowType
	        for (BorrowList borrowlist : borrowlists){
                arrUser[i][0]=borrowlist.userid;
                arrUser[i][1]=borrowlist.isbn;
                arrUser[i][2]=borrowlist.title;
                arrUser[i][3]=borrowlist.borrowDate;
                arrUser[i][4]=borrowlist.returnDate;
                arrUser[i][5]=borrowlist.borrowType;
                i = i+1;
            }
	        return arrUser;
	    }

        public static class BorrowList {
	        private String userid;
                private String isbn;
                private String title;
                private String borrowDate;
                private String returnDate;
                private String borrowType;

	        public BorrowList(String member_cardid, String isbn,String title, String borrowDate, String returnDate,String borrowType) {
	            this.userid = member_cardid;
                    this.isbn = isbn;
                    this.title = title;
                    this.borrowDate = borrowDate;
                    this.returnDate = returnDate;
                    this.borrowType = borrowType;// as type:book,media
	        }

	        @Override
	        public String toString() {
	            return userid+","+isbn+ ","+title+","+ borrowDate+ ","+ returnDate+"," +borrowType;
	        }

	    }

	}

