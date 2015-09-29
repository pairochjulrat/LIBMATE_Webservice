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
public class  MasterDataLibraryClass {
        public List<MasterLibrary> masterlibraries;

        public MasterDataLibraryClass(List<MasterLibrary> masterlibraries) {
	        this.masterlibraries = masterlibraries;
            //this.books.addAll(books);
	    }

        public Object[][] masterLibrarytoArray() {
            Object[][] arrLibrary = new Object[masterlibraries.size()][6];
            int i = 0;

	        for (MasterLibrary masterlibrary : masterlibraries){
                arrLibrary[i][0]=masterlibrary.name;
                arrLibrary[i][1]=masterlibrary.district;
                arrLibrary[i][2]=masterlibrary.province;
                arrLibrary[i][3]=masterlibrary.zipcode;
                arrLibrary[i][4]=masterlibrary.tel;
                arrLibrary[i][5]=masterlibrary.id;
                //System.out.println("TITLE="+arrBook[i][0]+" AUTHOR="+arrBook[i][1]);
                i = i+1;
            }
	        return arrLibrary;
	    }

	    public static class MasterLibrary {
	       private String name;
	        private String district;
	        private String province;
                private String zipcode;
	        private String tel;
                private String id;

	        public MasterLibrary(String name, String district, String province, String zipcode,String tel,String id) {
	            this.name = name;
                    this.district = district;
	            this.province = province;
                    this.zipcode = zipcode;
                    this.tel = tel;
                    this.id = id;
	        }
	    }
	}