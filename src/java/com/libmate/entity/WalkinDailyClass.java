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
public class  WalkinDailyClass {
    public List<SubWalkin> walkins;

    public WalkinDailyClass(List<SubWalkin> walkins) {
        this.walkins = walkins;
    //this.books.addAll(books);
    }

    public Object[][] WalkintoArray() {
        Object[][] arrBook = new Object[walkins.size()][2];
        int i = 0;
        //StringBuilder b = new StringBuilder();
        for (SubWalkin subwalkin : walkins){
            arrBook[i][0]=subwalkin.walkin_date;
            arrBook[i][1]=subwalkin.walkin_number;
            //System.out.println("TITLE="+arrBook[i][0]+" AUTHOR="+arrBook[i][1]);
            i = i+1;
        }
        return arrBook;
    }

    public static class SubWalkin {
        private String walkin_date;
        private String walkin_number;

        public SubWalkin(String walkin_date, String walkin_number) {
            this.walkin_date = walkin_date;
            this.walkin_number = walkin_number;
        }


    }

}

