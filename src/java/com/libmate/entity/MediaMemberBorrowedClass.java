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
public class  MediaMemberBorrowedClass {
	    public List<MediaMember> mediamember;

	    public MediaMemberBorrowedClass(List<MediaMember> mediamember) {
	        this.mediamember = mediamember;
	    }

	    /*@Override
	    public String toString() {
	        StringBuilder b = new StringBuilder();
	        b.append("Member with " + mediamember.size() + " users:\n");
	        for (MediaMember user : mediamember) {
	            b.append(user.toString());
	            // Skip a line
	            b.append("\n");
	        }
	        return b.toString();
	    }*/
        public Object[][] MediaMembertoArray() {
            Object[][] arrMediaMember = new Object[mediamember.size()][9];
            int i = 0;
	        for (MediaMember user : mediamember){
                arrMediaMember[i][0]=user.member_cardid;
                arrMediaMember[i][1]=user.firstname;
                arrMediaMember[i][2]=user.lastname;

                arrMediaMember[i][3]=user.isbn;
                arrMediaMember[i][4]=user.title;
                arrMediaMember[i][5]=user.author;

                arrMediaMember[i][6]=user.borrowDate;
                arrMediaMember[i][7]=user.returnDate;
                arrMediaMember[i][8]=user.memberType;
                i = i+1;
            }
	        return arrMediaMember;
	    }

	    public static class MediaMember {
	        private String member_cardid;
                private String firstname;
	        private String lastname;
                private String isbn;
                private String title;
                private String author;
                private String borrowDate;
                private String returnDate;
                private String memberType;

	        public MediaMember(String member_cardid, String firstname, String lastname,
                    String isbn, String title, String author, String borrowDate, String returnDate,String member_type) {
	            this.member_cardid = member_cardid;
                    this.firstname = firstname;
	            this.lastname = lastname;
                    this.isbn = isbn;
                    this.title = title;
                    this.author = author;
                    this.borrowDate = borrowDate;
                    this.returnDate = returnDate;
                    this.memberType = member_type;
	        }

	        @Override
	        public String toString() {
	            return member_cardid+","+ firstname +","+ lastname +","+isbn+","+ title+","+ author+
                        ","+borrowDate+","+returnDate;
	        }
	    }

	}

