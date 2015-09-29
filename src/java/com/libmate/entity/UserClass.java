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
public class  UserClass {
    public List<User> users;

    public UserClass(List<User> users) {
            this.users = users;
        }

    public Object[][] userToArray() {
        Object[][] arrUser = new Object[users.size()][4];
        int i = 0;
        for (User user : users){
            arrUser[i][0]=user.username;
            arrUser[i][1]=user.usergroup;
            arrUser[i][2]=user.userupdate_date;
            arrUser[i][3]=user.userupdate_by;
            i = i+1;
        }
        return arrUser;
    }

    public static class User {
        private String username;
        private String usergroup;
        private String userupdate_date;
        private String userupdate_by;

        public User(String username,String usergroup,String userupdate_date,String userupdate_by) {
            this.username = username;
            this.usergroup = usergroup;
            this.userupdate_date = userupdate_date;
            this.userupdate_by = userupdate_by;
        }
    }
}