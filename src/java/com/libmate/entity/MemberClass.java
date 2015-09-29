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
public class  MemberClass {
	    public List<Member> users;
	    public MemberClass(List<Member> users) {
	        this.users = users;
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
        public Object[][] memberToArray() {
            Object[][] arrUser = new Object[users.size()][3];
            int i = 0;
	        for (Member user : users){
                arrUser[i][0]=user.userid;
                arrUser[i][1]=user.firstname;
                arrUser[i][2]=user.lastname;
                i = i+1;
            }
	        return arrUser;
	}

        public Object[][] memberImageToArray() {
            Object[][] arrUser = new Object[users.size()][18];
            int i = 0;
	        for (Member user : users){
                arrUser[i][0]=user.userid;
                arrUser[i][1]=user.firstname;
                arrUser[i][2]=user.lastname;
                arrUser[i][3]=user.imgname;
                arrUser[i][4]=user.imgbody;

                arrUser[i][5]=user.prefix;
                arrUser[i][6]=user.sex;
                arrUser[i][7]=user.age;
                arrUser[i][8]=user.occupation;
                arrUser[i][9]=user.address;
                arrUser[i][10]=user.address_province;
                arrUser[i][11]=user.address_zipcode;
                arrUser[i][12]=user.phone;
                arrUser[i][13]=user.phone2;
                arrUser[i][14]=user.contact_address;
                arrUser[i][15]=user.membertype;
                arrUser[i][16]=user.registerdate;
                arrUser[i][17]=user.expireddate;
                i = i+1;
            }
	        return arrUser;
	}

	    public static class Member {
	        private String userid;
                private String prefix;
                private String firstname;
	        private String lastname;
                private String imgname;
                private byte[] imgbody;

                private String sex;
                private String age;
                private String address;
                private String address_province;
                private String address_zipcode;
	        private String phone;
                private String phone2;
                private String contact_address;
                private String occupation;
                private String membertype;
                private String registerdate;
                private String expireddate;

	        public Member(String userid, String firstname, String lastname) {
	            this.userid = userid;
                    this.firstname = firstname;
	            this.lastname = lastname;
	        }

                public Member(String userid, String firstname, String lastname, String imgname, byte[] imgbody,
                        String prefix,String sex,String age,String occupation,String address,String address_prvince,
                        String address_zipcode,String phone,String phone2,String contact_address,String membertype,String registerdate,String expireddate) {
	            this.userid = userid;
                    this.firstname = firstname;
                    this.lastname = lastname;
                    this.imgname = imgname;
                    this.imgbody = imgbody;

                    this.prefix = prefix;
                    this.sex = sex;
                    this.age = age;
                    this.occupation = occupation;
                    this.address = address;
                    this.address_province = address_prvince;
                    this.address_zipcode = address_zipcode;
                    this.phone = phone;
                    this.phone2 = phone2;
                    this.contact_address = contact_address;       
                    this.membertype = membertype;
                    this.registerdate = registerdate;
                    this.expireddate = expireddate;
	        }

	        @Override
	        public String toString() {
	            return userid+"," + firstname + "," + lastname;
	        }

	    }

	}

