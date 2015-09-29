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
public class  BookMemberBorrowedClass {
    public List<BookMember> bookmember;

    public BookMemberBorrowedClass(List<BookMember> bookmember) {
        this.bookmember = bookmember;
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("Member with " + bookmember.size() + " users:\n");
        for (BookMember user : bookmember) {
            b.append(user.toString());
            // Skip a line
            b.append("\n");
        }
        return b.toString();
    }
    public Object[][] BookBorrowedtoArray() {
        Object[][] arrBookMember = new Object[bookmember.size()][9];
        int i = 0;
        for (BookMember user : bookmember){
            arrBookMember[i][0]=user.member_cardid;
            arrBookMember[i][1]=user.firstname;
            arrBookMember[i][2]=user.lastname;

            arrBookMember[i][3]=user.isbn;
            arrBookMember[i][4]=user.title;
            arrBookMember[i][5]=user.author;

            arrBookMember[i][6]=user.borrowDate;
            arrBookMember[i][7]=user.returnDate;
            arrBookMember[i][8]=user.memberType;
            i = i+1;
        }
        return arrBookMember;
    }

    public static class BookMember {
        private String member_cardid;
        private String firstname;
        private String lastname;
        private String isbn;
        private String title;
        private String author;
        private String borrowDate;
        private String returnDate;
        private String memberType;

        public BookMember(String member_cardid, String firstname, String lastname,
                String isbn, String title, String author, String borrowDate, String returnDate) {
            this.member_cardid = member_cardid;
            this.firstname = firstname;
            this.lastname = lastname;
            this.isbn = isbn;
            this.title = title;
            this.author = author;
            this.borrowDate = borrowDate;
            this.returnDate = returnDate;
        }
        
        public BookMember(String member_cardid, String firstname, String lastname,
                String isbn, String title, String author, String borrowDate, String returnDate, String memberType) {
            this.member_cardid = member_cardid;
            this.firstname = firstname;
            this.lastname = lastname;
            this.isbn = isbn;
            this.title = title;
            this.author = author;
            this.borrowDate = borrowDate;
            this.returnDate = returnDate;
            this.memberType = memberType;
        }

        public void BookBorrowList(String member_cardid, String isbn, String title, String borrowDate, String returnDate) {
            this.member_cardid = member_cardid;
            this.isbn = isbn;
            this.title = title;
            this.borrowDate = borrowDate;
            this.returnDate = returnDate;
            }

        @Override
        public String toString() {
                return member_cardid+","+ firstname +","+ lastname +","+isbn+","+ title+","+ author+
                    ","+borrowDate+","+returnDate;
        }
    }
}

