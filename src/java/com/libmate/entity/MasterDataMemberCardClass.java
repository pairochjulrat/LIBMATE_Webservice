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
public class MasterDataMemberCardClass {
    public List<MemberCard> cards;

	    public MasterDataMemberCardClass(List<MemberCard> cards) {
	        this.cards = cards;
            //this.books.addAll(books);
	    }

        public Object[][] memberCardToArray() {
            Object[][] arrCard = new Object[cards.size()][8];
            int i = 0;
            //StringBuilder b = new StringBuilder();
	        for (MemberCard card : cards){
                arrCard[i][0]=card.header;
                arrCard[i][1]=card.line1;
                arrCard[i][2]=card.line2;
                arrCard[i][3]=card.line3;
                arrCard[i][4]=card.line4;
                arrCard[i][5]=card.line5;
                arrCard[i][6]=card.line6;
                arrCard[i][7]=card.line7;
                i = i+1;
            }
	        return arrCard;
	    }

	    public static class MemberCard {
	        private String header;
                private String line1;
	        private String line2;
                private String line3;
                private String line4;
                private String line5;
                private String line6;
                private String line7;

	        public MemberCard(String header, String line1, String line2,
                    String line3, String line4, String line5, String line6, String line7 ) {
	            this.header = header;
                    this.line1 = line1;
	            this.line2 = line2;
                    this.line3 = line3;
                    this.line4 = line4;
                    this.line5 = line5;
                    this.line6 = line6;
                    this.line7 = line7;
	        }

	    }
}
