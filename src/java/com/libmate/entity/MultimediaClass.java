/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libmate.entity;

import java.util.List;

/**
 *
 * @author Pairoch.J
 */
public class MultimediaClass {

    public List<Multimedia> multimedia;

    public MultimediaClass(List<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

    public Object[][] ToArray() {
        Object[][] arrImage = new Object[multimedia.size()][4];
        int i = 0;
        for (Multimedia a_multimedia : multimedia) {
            arrImage[i][0] = a_multimedia.fileid;
            arrImage[i][1] = a_multimedia.filename;
            arrImage[i][2] = a_multimedia.filetype;
            arrImage[i][3] = a_multimedia.filesize;
            i = i + 1;
        }
        return arrImage;
    }

    public Object[][] MediaBodyToArray() {
        Object[][] areMultimedia = new Object[multimedia.size()][5];
        int i = 0;
        for (Multimedia a_multimedia : multimedia) {
            areMultimedia[i][0] = a_multimedia.fileid;
            areMultimedia[i][1] = a_multimedia.filename;
            areMultimedia[i][2] = a_multimedia.filebody;
            areMultimedia[i][3] = a_multimedia.filetype;
            areMultimedia[i][4] = a_multimedia.filesize;
            i = i + 1;
        }
        return areMultimedia;
    }

    public static class Multimedia {

        private String fileid;
        private byte[] filebody;
        private String filename;
        private String filetype;
        private String filesize;


        public Multimedia(String fileid, String filename, String filetype, String filesize) {
            this.filename = filename;
            this.fileid = fileid;
            this.filetype = filetype;
            this.filesize = filesize;
        }

        public Multimedia(String fileid, byte[] filebody, String filename, String filetype, String filesize) {
            this.filename = filename;
            this.filebody = filebody;
            this.fileid = fileid;
            this.filetype = filetype;
            this.filesize = filesize;
        }
    }
}
