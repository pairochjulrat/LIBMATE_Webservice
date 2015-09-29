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
public class  PictureClass {
	    public List<Image> images;

	    public PictureClass(List<Image> images) {
	        this.images = images;
	    }

        public Object[][] ToArray() {
            Object[][] arrImage = new Object[images.size()][2];
            int i = 0;
	        for (Image image : images){
                arrImage[i][0]=image.filename;
                arrImage[i][1]=image.filebody;
                i = i+1;
            }
	        return arrImage;
	    }

        public Object[][] IMGMemberToArray() {
            Object[][] arrImage = new Object[images.size()][3];
            int i = 0;
	        for (Image image : images){
                arrImage[i][0]=image.memberid;
                arrImage[i][1]=image.filename;
                arrImage[i][2]=image.filebody;
                i = i+1;
            }
	        return arrImage;
	}

	    public static class Image {
	        private String filename;
                private byte[] filebody;
                private String memberid;

	        public Image(String filename, byte[] filebody) {
	            this.filename = filename;
                    this.filebody = filebody;
	        }

                public Image(String filename, byte[] filebody, String memberid) {
	            this.filename = filename;
                    this.filebody = filebody;
                    this.memberid = memberid;
	        }

	        /*@Override
	        public String toString() {
	            return filename+"," + filebody;
	        }  */
	    }

	}

