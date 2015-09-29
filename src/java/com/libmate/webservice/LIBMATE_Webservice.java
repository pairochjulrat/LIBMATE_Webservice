/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.libmate.webservice;

import com.libmate.entity.BookClass;
import com.libmate.entity.BookClass.SubBook;
import com.libmate.entity.BookMemberBorrowedClass;
import com.libmate.entity.BookMemberBorrowedClass.BookMember;
import com.libmate.entity.BookOutClass;
import com.libmate.entity.BookOutClass.BookOut;
import com.libmate.entity.MasterDataBookClass;
import com.libmate.entity.MasterDataBookClass.MasterBook;
import com.libmate.entity.MasterDataLibraryClass;
import com.libmate.entity.MasterDataLibraryClass.MasterLibrary;
import com.libmate.entity.MasterDataMediaClass;
import com.libmate.entity.MasterDataMediaClass.MasterMedia;
import com.libmate.entity.MasterDataMemberCardClass;
import com.libmate.entity.MasterDataMemberCardClass.MemberCard;
import com.libmate.entity.MediaClass;
import com.libmate.entity.MediaClass.SubMedia;
import com.libmate.entity.MediaMemberBorrowedClass;
import com.libmate.entity.MediaMemberBorrowedClass.MediaMember;
import com.libmate.entity.MediaOutClass;
import com.libmate.entity.MediaOutClass.MediaOut;
import com.libmate.entity.MemberBorrowListClass;
import com.libmate.entity.MemberBorrowListClass.BorrowList;
import com.libmate.entity.MemberClass;
import com.libmate.entity.MemberClass.Member;
import com.libmate.entity.MultimediaClass;
import com.libmate.entity.MultimediaClass.Multimedia;
import com.libmate.entity.PictureClass;
import com.libmate.entity.PictureClass.Image;
import com.libmate.entity.UserClass;
import com.libmate.entity.UserClass.User;
import com.libmate.entity.WalkinDailyClass;
import com.libmate.entity.WalkinDailyClass.SubWalkin;
import com.libmate.entity.utilFunction;
//import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
//import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author pairoch@realitlimited.com
 */
@WebService(serviceName = "LIBMATE_Webservice")
public class LIBMATE_Webservice {

    Connection con = null;
    Statement stmt = null;
    ResultSet rsult = null;
    String driver = "com.mysql.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/libmate?characterEncoding=tis620";
    String dbusername = "libmate";
    String dbpassword = "libmate123?";
    String uploaded_path = "/var/www/LIBMATE_UPLOADED/";
    //String uploaded_path = "c:\\apache\\";

    @WebMethod(operationName = "mediaMasterSearch")
    public Object[][] mediaMasterSearch(@WebParam(name = "p_isbn") String p_isbn,
            @WebParam(name = "p_title") String p_title,
            @WebParam(name = "p_author") String p_author) {
        String isbnReturn = "";
        String titleReturn = "";
        String authorReturn = "";
        String priceReturn = "";
        String mediaFlagReturn = "";
        String memberIDReturn = "";
        String storeReturn = "";
        String publishingReturn = "";
        String yearPrintReturn = "";
        String receiveDateReturn = "";
        String sourceReturn = "";
        String shortTopicReturn = "";
        String remarkReturn = "";
        String title2Return = "";


        String sql_sel = "";
        List<SubMedia> medias = new ArrayList<SubMedia>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "select * from medias where ";

                String where = "";
                if (!p_isbn.equalsIgnoreCase("")) {
                    where += "isbn like '%" + p_isbn + "%'";
                }

                if (!p_title.equalsIgnoreCase("")) {
                    if (!where.equalsIgnoreCase("")) {
                        where += " and title like '%" + p_title + "%' ";
                    } else {
                        where += " title like '%" + p_title + "%' ";
                    }
                }

                if (!p_author.equalsIgnoreCase("")) {
                    if (!where.equalsIgnoreCase("")) {
                        where += " and author like '%" + p_author + "%'";
                    } else {
                        where += " author like '%" + p_author + "%'";
                    }
                }

                if (where.equalsIgnoreCase("")) {
                    where = "1";
                }

                sql_sel = sql_sel + " " + where + " order by isbn ";
                System.out.println("sql_sel = " + sql_sel);

                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {
                    isbnReturn = rsult.getString("isbn");
                    titleReturn = rsult.getString("title");
                    authorReturn = rsult.getString("author");

                    priceReturn = rsult.getString("price");
                    mediaFlagReturn = rsult.getString("media_flag");
                    memberIDReturn = rsult.getString("media_catID");
                    storeReturn = rsult.getString("store");
                    publishingReturn = rsult.getString("publishing");
                    yearPrintReturn = rsult.getString("year_print");
                    receiveDateReturn = rsult.getString("receive_date");
                    sourceReturn = rsult.getString("source");
                    shortTopicReturn = rsult.getString("short_topic");
                    remarkReturn = rsult.getString("remark");
                    title2Return = rsult.getString("title2");
                    medias.add(new SubMedia(isbnReturn, titleReturn, authorReturn, title2Return,
                            priceReturn, mediaFlagReturn, memberIDReturn, storeReturn,
                            publishingReturn, yearPrintReturn, receiveDateReturn, sourceReturn,
                            shortTopicReturn, remarkReturn));

                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        MediaClass lib = new MediaClass(medias);
        Object rsultArray[][] = lib.MediatoArray();

        return rsultArray;
    }

    @WebMethod(operationName = "mediaMasterSearchISBN")
    public Object[][] mediaMasterSearchISBN(@WebParam(name = "p_isbn") String p_isbn) {
        String isbnReturn = "";
        String titleReturn = "";
        String authorReturn = "";
        String priceReturn = "";
        String mediaFlagReturn = "";
        String memberIDReturn = "";
        String storeReturn = "";
        String publishingReturn = "";
        String yearPrintReturn = "";
        String receiveDateReturn = "";
        String sourceReturn = "";
        String shortTopicReturn = "";
        String remarkReturn = "";
        String title2Return = "";
        String sql_sel = "";
        List<SubMedia> submedias = new ArrayList<SubMedia>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "select * from medias where ";

                String where = "";
                if (!p_isbn.equalsIgnoreCase("")) {
                    where += "isbn = '" + p_isbn + "'";
                }

                if (where.equalsIgnoreCase("")) {
                    where = "1";
                }

                sql_sel = sql_sel + " " + where + " order by isbn ";
                System.out.println("sql_sel = " + sql_sel);

                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {
                    isbnReturn = rsult.getString("isbn");
                    titleReturn = rsult.getString("title");
                    authorReturn = rsult.getString("author");

                    priceReturn = rsult.getString("price");
                    mediaFlagReturn = rsult.getString("media_flag");
                    memberIDReturn = rsult.getString("media_catID");
                    storeReturn = rsult.getString("store");
                    publishingReturn = rsult.getString("publishing");
                    yearPrintReturn = rsult.getString("year_print");
                    receiveDateReturn = rsult.getString("receive_date");
                    sourceReturn = rsult.getString("source");
                    shortTopicReturn = rsult.getString("short_topic");
                    remarkReturn = rsult.getString("remark");
                    title2Return = rsult.getString("title2");
                    submedias.add(new SubMedia(isbnReturn, titleReturn, authorReturn, title2Return,
                            priceReturn, mediaFlagReturn, memberIDReturn, storeReturn,
                            publishingReturn, yearPrintReturn, receiveDateReturn, sourceReturn,
                            shortTopicReturn, remarkReturn));

                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        MediaClass lib = new MediaClass(submedias);
        Object rsultArray[][] = lib.MediatoArray();

        return rsultArray;
    }

    @WebMethod(operationName = "mediaMasterNew")
    public int mediaMasterNew(@WebParam(name = "p_isbn") String p_isbn,
            @WebParam(name = "p_title") String p_title,
            @WebParam(name = "p_author") String p_author,
            @WebParam(name = "p_price") String p_price,
            @WebParam(name = "p_media_flag") String p_media_flag,
            @WebParam(name = "p_media_catID") String p_media_catID,
            @WebParam(name = "p_store") String p_store,
            @WebParam(name = "p_publishing") String p_publishing,
            @WebParam(name = "p_year_print") String p_year_print,
            @WebParam(name = "p_receive_date") String p_receive_date,
            @WebParam(name = "p_source") String p_source,
            @WebParam(name = "p_short_topic") String p_short_topic,
            @WebParam(name = "p_remark") String p_remark,
            @WebParam(name = "p_title2") String p_title2) {
        int addResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from medias where isbn='" + p_isbn + "'");
                if (rsult.next()) {
                    addResult = 9;
                    //addResult = "ISBN EXISTS";
                } else {
                    int tmpIns = stmt.executeUpdate("insert into medias (isbn,title,author,price,media_flag,"
                            + "media_catID,store,publishing,year_print,receive_date,"
                            + "source,short_topic,remark,title2) "
                            + "values('" + p_isbn + "','" + p_title + "','" + p_author + "','" + p_price + "','" + p_media_flag + "',"
                            + "'" + p_media_catID + "','" + p_store + "','" + p_publishing + "','" + p_year_print + "','" + p_receive_date + "',"
                            + "'" + p_source + "','" + p_short_topic + "','" + p_remark + "','" + p_title2 + "') ");
                    if (tmpIns != -1) {
                        addResult = 1;
                        //addResult = "ADD COMPLETED";
                    } else {
                        addResult = 0;
                        //addResult = "ADD FAILED";
                    }
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return addResult;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "mediaMasterDelete")
    public int mediaMasterDelete(@WebParam(name = "p_isbn") String p_isbn) {
        int deleteResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from medias where isbn='" + p_isbn + "'");

                if (rsult.next()) {
                    int tmpDel = stmt.executeUpdate("delete from medias where isbn='" + p_isbn + "' ");
                    if (tmpDel != -1) {
                        //deleteResult = "Delete Completed";
                        deleteResult = 1;
                    } else {
                        //deleteResult = "Delete Failed";
                        deleteResult = 0;
                    }
                } else {
                    //deleteResult = "No ISBN Found";
                    deleteResult = 9;
                }

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleteResult;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "mediaMasterUpdate")
    public int mediaMasterUpdate(@WebParam(name = "p_isbn") String p_isbn,
            @WebParam(name = "p_title") String p_title,
            @WebParam(name = "p_author") String p_author,
            @WebParam(name = "p_price") String p_price,
            @WebParam(name = "p_media_flag") String p_media_flag,
            @WebParam(name = "p_media_catID") String p_media_catID,
            @WebParam(name = "p_store") String p_store,
            @WebParam(name = "p_publishing") String p_publishing,
            @WebParam(name = "p_year_print") String p_year_print,
            @WebParam(name = "p_receive_date") String p_receive_date,
            @WebParam(name = "p_source") String p_source,
            @WebParam(name = "p_short_topic") String p_short_topic,
            @WebParam(name = "p_remark") String p_remark,
            @WebParam(name = "p_title2") String p_title2) {
        //TODO write your implementation code here:
        int updateResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                int tmpUpd = stmt.executeUpdate("update medias set title='" + p_title + "', author='" + p_author + "', "
                        + "price='" + p_price + "',"
                        + "media_catID='" + p_media_catID + "',store='" + p_store + "',publishing='" + p_publishing + "', "
                        + "year_print='" + p_year_print + "',receive_date='" + p_receive_date + "',"
                        + "source='" + p_source + "',short_topic='" + p_short_topic + "',remark='" + p_remark + "',title2='" + p_title2 + "' "
                        + "where isbn='" + p_isbn + "'");
                if (tmpUpd == 1) {
                    //updateResult = "Update Completed";
                    updateResult = 1;
                } else {
                    //updateResult = "Update Failed";
                    updateResult = 0;
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updateResult;
    }

    @WebMethod(operationName = "mediaMasterMaxAutoID")
    public String mediaMasterMaxAutoID() {
        String mediaID = "";
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select max( media_id ) as media_id from medias ");

                if (rsult.next()) {
                    mediaID = rsult.getString("media_id");
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        int nextMediaID = Integer.parseInt(mediaID) + 1;
        return nextMediaID + "";
    }

    @WebMethod(operationName = "bookCategorySearch")
    public Object[][] bookCategorySearch(@WebParam(name = "p_book_catid") String p_book_catid) {
        String bookCatIDReturn = "";
        String bookCatNameReturn = "";
        String bookCatNumberReturn = "";
        String sql_sel = "";
        List<MasterBook> masterbooks = new ArrayList<MasterBook>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "select book_catID,book_cat_name,book_num_day_borrow from book_category where ";

                String where = "";
                if (!p_book_catid.equalsIgnoreCase("")) {
                    where += "book_catID like '%" + p_book_catid + "%' ORDER BY BOOK_ID DESC";
                }

                if (where.equalsIgnoreCase("")) {
                    where = "1";
                }
                sql_sel = sql_sel + " " + where + " order by book_catID ";
                //System.out.println("sql_sel = "+sql_sel);

                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {
                    bookCatIDReturn = rsult.getString("book_catID");
                    bookCatNameReturn = rsult.getString("book_cat_name");
                    bookCatNumberReturn = rsult.getString("book_num_day_borrow");
                    masterbooks.add(new MasterBook(bookCatIDReturn, bookCatNameReturn, bookCatNumberReturn));

                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        MasterDataBookClass lib = new MasterDataBookClass(masterbooks);
        Object rsultArray[][] = lib.MasterBooktoArray();

        return rsultArray;
    }

    @WebMethod(operationName = "bookCategoryNew")
    public int bookCategoryNew(@WebParam(name = "p_book_catid") String p_book_catid, @WebParam(name = "p_book_cat_name") String p_book_cat_name, @WebParam(name = "p_book_cat_number") String p_book_cat_number) {
        int addResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select book_catID,book_cat_name,book_num_day_borrow from book_category where book_catID='" + p_book_catid + "'");
                if (rsult.next()) {
                    addResult = 9;
                    //addResult = "ISBN EXISTS";
                } else {
                    int tmpIns = stmt.executeUpdate("insert into book_category (book_catID,book_cat_name,book_num_day_borrow) values('" + p_book_catid + "','" + p_book_cat_name + "','" + p_book_cat_number + "') ");
                    if (tmpIns != -1) {
                        addResult = 1;
                        //addResult = "ADD COMPLETED";
                    } else {
                        addResult = 0;
                        //addResult = "ADD FAILED";
                    }
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return addResult;
    }

    @WebMethod(operationName = "bookCategoryDelete")
    public int bookCategoryDelete(@WebParam(name = "p_book_catid") String p_book_catid) {
        int deleteResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from book_category where book_catID='" + p_book_catid + "'");

                if (rsult.next()) {
                    int tmpDel = stmt.executeUpdate("delete from book_category where book_catID='" + p_book_catid + "' ");
                    if (tmpDel != -1) {
                        //deleteResult = "Delete Completed";
                        deleteResult = 1;
                    } else {
                        //deleteResult = "Delete Failed";
                        deleteResult = 0;
                    }
                } else {
                    //deleteResult = "No ISBN Found";
                    deleteResult = 9;
                }

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleteResult;
    }

    @WebMethod(operationName = "bookCategoryUpdate")
    public int bookCategoryUpdate(@WebParam(name = "p_book_catid") String p_book_catid, @WebParam(name = "p_book_cat_name") String p_book_cat_name, @WebParam(name = "p_book_cat_number") String p_book_cat_number) {
        //TODO write your implementation code here:
        int updateResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                int tmpUpd = stmt.executeUpdate("update book_category set book_cat_name='" + p_book_cat_name + "', book_num_day_borrow='" + p_book_cat_number + "' where book_catID='" + p_book_catid + "'");
                if (tmpUpd == 1) {
                    //updateResult = "Update Completed";
                    updateResult = 1;
                } else {
                    //updateResult = "Update Failed";
                    updateResult = 0;
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updateResult;
    }

    @WebMethod(operationName = "bookMasterSearch")
    public Object[][] bookMasterSearch(@WebParam(name = "p_isbn") String p_isbn,
            @WebParam(name = "p_title") String p_title,
            @WebParam(name = "p_author") String p_author) {
        String isbnReturn = "";
        String titleReturn = "";
        String authorReturn = "";
        String priceReturn = "";
        String bookFlagReturn = "";
        String bookCatIDReturn = "";
        String recieveDateReturn = "";
        String categoryNoReturn = "";
        String publishingReturn = "";
        String yearPrintReturn = "";
        String editionReturn = "";
        String volumnReturn = "";
        String issueReturn = "";
        String prefixAuthorReturn = "";
        String pennameReturn = "";
        String translatorReturn = "";
        String title2Return = "";
        String shortNameReturn = "";
        String seriesNameReturn = "";
        String numPageReturn = "";
        String pictureReturn = "";
        String shortTopicReturn = "";
        String bookSizeReturn = "";
        String sourceReturn = "";
        String remarkReturn = "";
        String bookIdReturn = "";

        String sql_sel = "";
        List<SubBook> subbooks = new ArrayList<SubBook>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "select * from books where ";

                String where = "";
                if (!p_isbn.equalsIgnoreCase("")) {
                    where += "isbn like '%" + p_isbn + "%'";
                }

                if (!p_title.equalsIgnoreCase("")) {
                    if (!where.equalsIgnoreCase("")) {
                        where += " and title like '%" + p_title + "%' ";
                    } else {
                        where += " title like '%" + p_title + "%' ";
                    }
                }

                if (!p_author.equalsIgnoreCase("")) {
                    if (!where.equalsIgnoreCase("")) {
                        where += " and author like '%" + p_author + "%'";
                    } else {
                        where += " author like '%" + p_author + "%'";
                    }
                }

                if (where.equalsIgnoreCase("")) {
                    where = "1";
                }
                /*if(!p_isbn.equalsIgnoreCase("") && !p_title.equalsIgnoreCase(""))
                 sql_sel += " isbn like '%"+p_isbn+"%' and title like '%"+p_title+"%' ";
                 else if(!p_isbn.equalsIgnoreCase("") )
                 sql_sel += " isbn like '%"+p_isbn+"%' ";
                 else if(!p_title.equalsIgnoreCase("") )
                 sql_sel += " title like '%"+p_title+"%' ";
                 else sql_sel += " 1 ";
                 */
                sql_sel = sql_sel + " " + where + " order by  cast(isbn as UNSIGNED) ";
                //System.out.println("sql_sel = "+sql_sel);

                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {
                    isbnReturn = rsult.getString("isbn");
                    titleReturn = rsult.getString("title");
                    authorReturn = rsult.getString("author");
                    priceReturn = rsult.getString("price");
                    bookFlagReturn = rsult.getString("book_flag");
                    bookCatIDReturn = rsult.getString("book_catID");
                    recieveDateReturn = rsult.getString("recieve_date");
                    categoryNoReturn = rsult.getString("category_no");
                    publishingReturn = rsult.getString("publishing");
                    yearPrintReturn = rsult.getString("year_print");
                    editionReturn = rsult.getString("edition");
                    volumnReturn = rsult.getString("volumn");
                    issueReturn = rsult.getString("issue");
                    prefixAuthorReturn = rsult.getString("prefix_author");
                    pennameReturn = rsult.getString("penname");
                    translatorReturn = rsult.getString("translator");
                    title2Return = rsult.getString("title2");
                    shortNameReturn = rsult.getString("short_name");
                    seriesNameReturn = rsult.getString("series_name");
                    numPageReturn = rsult.getString("num_page");
                    pictureReturn = rsult.getString("picture");
                    shortTopicReturn = rsult.getString("short_topic");
                    bookSizeReturn = rsult.getString("book_size");
                    sourceReturn = rsult.getString("source");
                    remarkReturn = rsult.getString("remark");
                    bookIdReturn = rsult.getString("book_id");
                    subbooks.add(new SubBook(isbnReturn, titleReturn, authorReturn, priceReturn, bookFlagReturn, bookCatIDReturn,
                            recieveDateReturn, categoryNoReturn, publishingReturn,
                            yearPrintReturn, editionReturn, volumnReturn,
                            issueReturn, prefixAuthorReturn, pennameReturn,
                            translatorReturn, title2Return,
                            shortNameReturn, seriesNameReturn, numPageReturn,
                            pictureReturn, shortTopicReturn, bookSizeReturn,
                            sourceReturn, remarkReturn, bookIdReturn));

                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        BookClass lib = new BookClass(subbooks);
        Object rsultArray[][] = lib.BooktoArray();

        return rsultArray;
    }

    @WebMethod(operationName = "bookSearchBorrowed")
    public Object[][] bookSearchBorrowed(@WebParam(name = "p_isbn") String p_isbn) {
        String isbnReturn = "";
        String titleReturn = "";
        String authorReturn = "";
        String priceReturn = "";
        String bookFlagReturn = "";
        String bookCatIDReturn = "";
        String recieveDateReturn = "";
        String categoryNoReturn = "";
        String publishingReturn = "";
        String yearPrintReturn = "";
        String editionReturn = "";
        String volumnReturn = "";
        String issueReturn = "";
        String prefixAuthorReturn = "";
        String pennameReturn = "";
        String translatorReturn = "";
        String title2Return = "";
        String shortNameReturn = "";
        String seriesNameReturn = "";
        String numPageReturn = "";
        String pictureReturn = "";
        String shortTopicReturn = "";
        String bookSizeReturn = "";
        String sourceReturn = "";
        String remarkReturn = "";
        String bookIdReturn = "";
        String sql_sel = "";
        List<SubBook> subbooks = new ArrayList<SubBook>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "select * from books where ";

                String where = "";
                if (!p_isbn.equalsIgnoreCase("")) {
                    where += "isbn = '" + p_isbn + "'";
                }

                if (where.equalsIgnoreCase("")) {
                    where = "1";
                }
                sql_sel = sql_sel + " " + where + " order by isbn ";
                //System.out.println("sql_sel = "+sql_sel);

                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {
                    isbnReturn = rsult.getString("isbn");
                    titleReturn = rsult.getString("title");
                    authorReturn = rsult.getString("author");
                    priceReturn = rsult.getString("price");
                    bookFlagReturn = rsult.getString("book_flag");
                    bookCatIDReturn = rsult.getString("book_catID");
                    recieveDateReturn = rsult.getString("recieve_date");
                    categoryNoReturn = rsult.getString("category_no");
                    publishingReturn = rsult.getString("publishing");
                    yearPrintReturn = rsult.getString("year_print");
                    editionReturn = rsult.getString("edition");
                    volumnReturn = rsult.getString("volumn");
                    issueReturn = rsult.getString("issue");
                    prefixAuthorReturn = rsult.getString("prefix_author");
                    pennameReturn = rsult.getString("penname");
                    translatorReturn = rsult.getString("translator");
                    title2Return = rsult.getString("title2");
                    shortNameReturn = rsult.getString("short_name");
                    seriesNameReturn = rsult.getString("series_name");
                    numPageReturn = rsult.getString("num_page");
                    pictureReturn = rsult.getString("picture");
                    shortTopicReturn = rsult.getString("short_topic");
                    bookSizeReturn = rsult.getString("book_size");
                    sourceReturn = rsult.getString("source");
                    remarkReturn = rsult.getString("remark");
                    bookIdReturn = rsult.getString("book_id");
                    subbooks.add(new SubBook(isbnReturn, titleReturn, authorReturn, priceReturn, bookFlagReturn, bookCatIDReturn,
                            recieveDateReturn, categoryNoReturn, publishingReturn,
                            yearPrintReturn, editionReturn, volumnReturn,
                            issueReturn, prefixAuthorReturn, pennameReturn,
                            translatorReturn, title2Return,
                            shortNameReturn, seriesNameReturn, numPageReturn,
                            pictureReturn, shortTopicReturn, bookSizeReturn,
                            sourceReturn, remarkReturn, bookIdReturn));

                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        BookClass lib = new BookClass(subbooks);
        Object rsultArray[][] = lib.BooktoArray();

        return rsultArray;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "bookMasterNew")
    public int bookMasterNew(@WebParam(name = "p_isbn") String p_isbn,
            @WebParam(name = "p_title") String p_title,
            @WebParam(name = "p_author") String p_author,
            @WebParam(name = "p_price") String p_price,
            @WebParam(name = "p_book_flag") String p_book_flag,
            @WebParam(name = "p_book_catID") String p_book_catID,
            @WebParam(name = "p_recieve_date") String p_recieve_date,
            @WebParam(name = "p_category_no") String p_category_no,
            @WebParam(name = "p_publishing") String p_publishing,
            @WebParam(name = "p_year_print") String p_year_print,
            @WebParam(name = "p_edition") String p_edition,
            @WebParam(name = "p_volumn") String p_volumn,
            @WebParam(name = "p_issue") String p_issue,
            @WebParam(name = "p_prefix_author") String p_prefix_author,
            @WebParam(name = "p_penname") String p_penname,
            @WebParam(name = "p_translator") String p_translator,
            @WebParam(name = "p_title2") String p_title2,
            @WebParam(name = "p_short_name") String p_short_name,
            @WebParam(name = "p_series_name") String p_series_name,
            @WebParam(name = "p_num_page") String p_num_page,
            @WebParam(name = "p_picture") String p_picture,
            @WebParam(name = "p_short_topic") String p_short_topic,
            @WebParam(name = "p_book_size") String p_book_size,
            @WebParam(name = "p_sourcepicture") String p_source,
            @WebParam(name = "p_remark") String p_remark) {
        int addResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                // rsult = stmt.executeQuery("select * from books where isbn='" + p_isbn + "'");
                // if (rsult.next()) {
                //addResult = 9; //"ISBN EXISTS"

                //} else {
                int tmpIns = stmt.executeUpdate("insert into books (isbn,title,author,"
                        + "price,book_flag,book_catID,"
                        + "recieve_date,category_no,publishing,"
                        + "year_print,edition,volumn,"
                        + "issue,prefix_author,penname,"
                        + "translator,title2,short_name,"
                        + "series_name,num_page,picture,"
                        + "short_topic,book_size,source,remark) "
                        + "values ('" + p_isbn + "','" + p_title + "','" + p_author + "',"
                        + "'" + p_price + "','" + p_book_flag + "','" + p_book_catID + "',"
                        + "'" + p_recieve_date + "','" + p_category_no + "','" + p_publishing + "',"
                        + "'" + p_year_print + "','" + p_edition + "','" + p_volumn + "',"
                        + "'" + p_issue + "','" + p_prefix_author + "','" + p_penname + "',"
                        + "'" + p_translator + "','" + p_title2 + "','" + p_short_name + "',"
                        + "'" + p_series_name + "','" + p_num_page + "','" + p_picture + "',"
                        + "'" + p_short_topic + "','" + p_book_size + "','" + p_source + "','" + p_remark + "') ");
                if (tmpIns != -1) {
                    addResult = 1;
                    //addResult = "ADD COMPLETED";
                } else {
                    addResult = 0;
                    //addResult = "ADD FAILED";
                }
                //}
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return addResult;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "bookMasterDelete")
    public int bookMasterDelete(@WebParam(name = "p_book_id") String p_book_id) {
        int deleteResult = 0;

        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from books where book_id='" + p_book_id + "'");

                if (rsult.next()) {
                    int tmpDel = stmt.executeUpdate("delete from books where book_id='" + p_book_id + "' ");
                    if (tmpDel != -1) {
                        //deleteResult = "Delete Completed";
                        deleteResult = 1;
                    } else {
                        //deleteResult = "Delete Failed";
                        deleteResult = 0;
                    }
                } else {
                    //deleteResult = "No ISBN Found";
                    deleteResult = 9;
                }

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleteResult;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "bookMasterUpdate")
    public int bookMasterUpdate(@WebParam(name = "p_isbn") String p_isbn,
            @WebParam(name = "p_title") String p_title,
            @WebParam(name = "p_author") String p_author,
            @WebParam(name = "p_price") String p_price,
            @WebParam(name = "p_book_flag") String p_book_flag,
            @WebParam(name = "p_book_catID") String p_book_catID,
            @WebParam(name = "p_recieve_date") String p_recieve_date,
            @WebParam(name = "p_category_noauthor") String p_category_no,
            @WebParam(name = "p_publishing") String p_publishing,
            @WebParam(name = "p_year_print") String p_year_print,
            @WebParam(name = "p_edition") String p_edition,
            @WebParam(name = "p_volumn") String p_volumn,
            @WebParam(name = "p_issue") String p_issue,
            @WebParam(name = "p_prefix_author") String p_prefix_author,
            @WebParam(name = "p_penname") String p_penname,
            @WebParam(name = "p_translator") String p_translator,
            @WebParam(name = "p_title2") String p_title2,
            @WebParam(name = "p_short_name") String p_short_name,
            @WebParam(name = "p_series_name") String p_series_name,
            @WebParam(name = "p_num_page") String p_num_page,
            @WebParam(name = "p_picture") String p_picture,
            @WebParam(name = "p_short_topic") String p_short_topic,
            @WebParam(name = "p_book_size") String p_book_size,
            @WebParam(name = "p_sourcepicture") String p_source,
            @WebParam(name = "p_remark") String p_remark,
            @WebParam(name = "p_book_id") String p_book_id) {
        //TODO write your implementation code here:
        int updateResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                int tmpUpd = stmt.executeUpdate("update books set isbn='" + p_isbn + "', title='" + p_title + "', author='" + p_author + "', "
                        + "price='" + p_price + "',book_catID='" + p_book_catID + "',"
                        + "recieve_date='" + p_recieve_date + "',category_no='" + p_category_no + "',publishing='" + p_publishing + "',"
                        + "year_print='" + p_year_print + "',edition='" + p_edition + "',volumn='" + p_volumn + "',"
                        + "issue='" + p_issue + "',prefix_author='" + p_prefix_author + "',penname='" + p_penname + "',"
                        + "translator='" + p_translator + "',title2='" + p_title2 + "',short_name='" + p_short_name + "',"
                        + "series_name='" + p_series_name + "',num_page='" + p_num_page + "',picture='" + p_picture + "',"
                        + "short_topic='" + p_short_topic + "',book_size='" + p_book_size + "',source='" + p_source + "',remark='" + p_remark + "' "
                        //   + "where book_id='" + p_book_id + "'");
                        + "where book_id='" + p_book_id + "'");
                if (tmpUpd == 1) {
                    //updateResult = "Update Completed";
                    updateResult = 1;
                } else {
                    //updateResult = "Update Failed";
                    updateResult = 0;
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updateResult;
    }

    @WebMethod(operationName = "bookMasterMaxAutoID")
    public String bookMasterMaxAutoID() {
        String bookID = "";
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select max( book_id ) as book_id from books ");

                if (rsult.next()) {
                    bookID = rsult.getString("book_id");
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        int nextBookID = Integer.parseInt(bookID) + 1;
        return nextBookID + "";
    }

    @WebMethod(operationName = "memberPictureSearch")
    @SuppressWarnings("RedundantStringConstructorCall")
    public Object[][] memberPictureSearch(@WebParam(name = "p_fileName") String p_fileName) {
        String nameReturn = "";
        byte[] bodyReturn;
        List<Image> images = new ArrayList<Image>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();

                rsult = stmt.executeQuery("SELECT * FROM member_picture where img_name like '%" + p_fileName + "%' ");
                if (rsult.next()) {
                    System.out.println("   id = " + rsult.getString("img_id"));
                    System.out.println("   Body = " + new String(rsult.getBytes("img_body")));
                    System.out.println("   img_name = " + new String(rsult.getString("img_name")));
                    nameReturn = rsult.getString("img_name");
                    bodyReturn = rsult.getBytes("img_body");
                    images.add(new Image(nameReturn, bodyReturn));
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        PictureClass lib = new PictureClass(images);
        Object rsultArray[][] = lib.ToArray();
        return rsultArray;
    }

    /*@WebMethod(operationName = "memberImageNew")
     public int memberImageNew(@WebParam(name = "p_file_byte") String p_file_byte,
     @WebParam(name = "p_fileName") String p_fileName,
     @WebParam(name = "p_file_length") long p_file_length,
     @WebParam(name = "p_member_id") String p_member_id,
     @WebParam(name = "p_member_fname") String p_member_fname,
     @WebParam(name = "p_member_lname") String p_member_lname) {
     int addResult = 0;
     try {            
     String sDecode = URLDecoder.decode(p_file_byte, "UTF-8");
     byte[] p_file = sDecode.getBytes();
     Blob blob=null; //is our blob object
     try {
     //is our byte array
     blob = new SerialBlob(p_file);
     } catch (SerialException ex) {
     Logger.getLogger(RFID_Book_Manager_Webservice.class.getName()).log(Level.SEVERE, null, ex);
     } catch (SQLException ex) {
     Logger.getLogger(RFID_Book_Manager_Webservice.class.getName()).log(Level.SEVERE, null, ex);
     }
    
     try {
     Class.forName(driver);
     try {
     con = DriverManager.getConnection(url, dbusername, dbpassword);
     stmt = con.createStatement();
    
     rsult = stmt.executeQuery("select * from members where member_cardID='" + p_member_id + "'");
     if (rsult.next()) {
     addResult = 9;
     //addResult = "MEMBER EXISTS";
     } else {
     //---------------------
     rsult = stmt.executeQuery("select * from member_picture where img_name='" + p_fileName + "' and member_id='"+p_member_id+"' ");
     if (rsult.next()) {
     addResult = 9;
     //addResult = "MEMBER EXISTS";
     } else {
     PreparedStatement pre = con.prepareStatement("insert into member_picture (img_body,img_name,member_id) values(?,?,?)");
     InputStream is = blob.getBinaryStream();
     //pre.setBlob(1, is);
     pre.setBinaryStream(1,is,(int)p_file_length);
     pre.setString(2, p_fileName);
     pre.setString(3, p_member_id);
     int tmpIMGIns =  pre.executeUpdate();
     if (tmpIMGIns != -1) {
     addResult = 1;
     //addResult = "ADD COMPLETED";
     } else {
     addResult = 0;
     //addResult = "ADD FAILED";
     }
     pre.close();
     }
     //---------------------
     }
     con.close();
     } catch (SQLException ex) {
     Logger.getLogger(RFID_Book_Manager_Webservice.class.getName()).log(Level.SEVERE, null, ex);
     }
    
     } catch (ClassNotFoundException ex) {
     Logger.getLogger(RFID_Book_Manager_Webservice.class.getName()).log(Level.SEVERE, null, ex);
     }
     } catch (UnsupportedEncodingException ex) {
     Logger.getLogger(RFID_Book_Manager_Webservice.class.getName()).log(Level.SEVERE, null, ex);
     }
     return addResult;
     }*/

    /*@WebMethod(operationName = "imageNew")
     public int imageNew(@WebParam(name = "p_file") byte[] p_file,
     @WebParam(name = "p_fileName") String p_fileName,
     @WebParam(name = "p_file_length") long p_file_length,
     @WebParam(name = "p_member_id") String p_member_id) {
    
     int addResult = 0;
     Blob blob=null; //is our blob object
     try {
     //is our byte array
     blob = new SerialBlob(p_file);
     } catch (SerialException ex) {
     Logger.getLogger(RFID_Book_Manager_Webservice.class.getName()).log(Level.SEVERE, null, ex);
     } catch (SQLException ex) {
     Logger.getLogger(RFID_Book_Manager_Webservice.class.getName()).log(Level.SEVERE, null, ex);
     }
     try {
     Class.forName(driver);
     try {
     con = DriverManager.getConnection(url, dbusername, dbpassword);
     stmt = con.createStatement();
    
     //addResult = "ADD COMPLETED";
     //---------------------
     rsult = stmt.executeQuery("select * from member_picture where img_name='" + p_fileName + "' and member_id='"+p_member_id+"' ");
     if (rsult.next()) {
     addResult = 9;
     //addResult = "MEMBER EXISTS";
     } else {
     PreparedStatement pre = con.prepareStatement("insert into member_picture (img_body,img_name,member_id) values(?,?,?)");
     InputStream is = blob.getBinaryStream();
     //pre.setBlob(1, is);
     pre.setBinaryStream(1,is,(int)p_file_length);
     pre.setString(2, p_fileName);
     pre.setString(3, p_member_id);
     int tmpIMGIns =  pre.executeUpdate();
     if (tmpIMGIns != -1) {
     addResult = 1;
     //addResult = "ADD COMPLETED";
     } else {
     addResult = 0;
     //addResult = "ADD FAILED";
     }
     pre.close();
     }
     //---------------------
    
     con.close();
     } catch (SQLException ex) {
     Logger.getLogger(RFID_Book_Manager_Webservice.class.getName()).log(Level.SEVERE, null, ex);
     }
    
     } catch (ClassNotFoundException ex) {
     Logger.getLogger(RFID_Book_Manager_Webservice.class.getName()).log(Level.SEVERE, null, ex);
     }
     return addResult;
     }*/
    @WebMethod(operationName = "mediaCategorySearch")
    public Object[][] mediaCategorySearch(@WebParam(name = "p_media_catid") String p_media_catid) {
        String mediaCatIDReturn = "";
        String mediaCatNameReturn = "";
        String mediaCatNumberReturn = "";
        String sql_sel = "";
        List<MasterMedia> mastermedias = new ArrayList<MasterMedia>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "select media_catID,media_cat_name,media_num_day_borrow from media_category where ";

                String where = "";
                if (!p_media_catid.equalsIgnoreCase("")) {
                    where += "media_catID like '%" + p_media_catid + "%'";
                }

                if (where.equalsIgnoreCase("")) {
                    where = "1";
                }
                sql_sel = sql_sel + " " + where + " order by media_catID ";
                //System.out.println("sql_sel = "+sql_sel);

                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {
                    mediaCatIDReturn = rsult.getString("media_catID");
                    mediaCatNameReturn = rsult.getString("media_cat_name");
                    mediaCatNumberReturn = rsult.getString("media_num_day_borrow");
                    mastermedias.add(new MasterMedia(mediaCatIDReturn, mediaCatNameReturn, mediaCatNumberReturn));

                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        MasterDataMediaClass lib = new MasterDataMediaClass(mastermedias);
        Object rsultArray[][] = lib.MasterMediatoArray();

        return rsultArray;
    }

    @WebMethod(operationName = "mediaCategoryNew")
    public int mediaCategoryNew(@WebParam(name = "p_media_catid") String p_media_catid, @WebParam(name = "p_media_cat_name") String p_media_cat_name, @WebParam(name = "p_media_cat_number") String p_media_cat_number) {
        int addResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select media_catID,media_cat_name,media_num_day_borrow from media_category where media_catID='" + p_media_catid + "'");
                if (rsult.next()) {
                    addResult = 9;
                    //addResult = "ISBN EXISTS";
                } else {
                    int tmpIns = stmt.executeUpdate("insert into media_category (media_catID,media_cat_name,media_num_day_borrow) values('" + p_media_catid + "','" + p_media_cat_name + "','" + p_media_cat_number + "') ");
                    if (tmpIns != -1) {
                        addResult = 1;
                        //addResult = "ADD COMPLETED";
                    } else {
                        addResult = 0;
                        //addResult = "ADD FAILED";
                    }
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return addResult;
    }

    @WebMethod(operationName = "mediaCategoryDelete")
    public int mediaCategoryDelete(@WebParam(name = "p_media_catid") String p_media_catid) {
        int deleteResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from media_category where media_catID='" + p_media_catid + "'");

                if (rsult.next()) {
                    int tmpDel = stmt.executeUpdate("delete from media_category where media_catID='" + p_media_catid + "' ");
                    if (tmpDel != -1) {
                        //deleteResult = "Delete Completed";
                        deleteResult = 1;
                    } else {
                        //deleteResult = "Delete Failed";
                        deleteResult = 0;
                    }
                } else {
                    //deleteResult = "No ISBN Found";
                    deleteResult = 9;
                }

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleteResult;
    }

    @WebMethod(operationName = "mediaCategoryUpdate")
    public int mediaCategoryUpdate(@WebParam(name = "p_media_catid") String p_media_catid, @WebParam(name = "p_media_cat_name") String p_media_cat_name, @WebParam(name = "p_media_cat_number") String p_media_cat_number) {
        //TODO write your implementation code here:
        int updateResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                int tmpUpd = stmt.executeUpdate("update media_category set media_cat_name='" + p_media_cat_name + "', media_num_day_borrow='" + p_media_cat_number + "' where media_catID='" + p_media_catid + "'");
                if (tmpUpd == 1) {
                    //updateResult = "Update Completed";
                    updateResult = 1;
                } else {
                    //updateResult = "Update Failed";
                    updateResult = 0;
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updateResult;
    }

    @WebMethod(operationName = "mediaBorrowSearch")
    public Object[][] mediaBorrowSearch(@WebParam(name = "p_member") String p_member) {
        String memberReturn = "";
        String firstnameReturn = "";
        String lastnameReturn = "";
        String isbnReturn = "";
        String titleReturn = "";
        String authorReturn = "";
        String sql_sel = "";
        String borrowdateReturn = "";
        String returndateReturn = "";
        String memberTypeReturn = "";
        List<MediaMember> mediamember = new ArrayList<MediaMember>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                //sql_sel = "select mt.isbn,mt.member_cardID,mt.borrow_date,mt.return_date,m.title,m.author,mm.member_firstName,mm.member_lastName from media_transaction mt,medias m,members mm where ";
                sql_sel = "select m.title,m.isbn,m.author,mt.borrow_date,mt.return_date,mm.member_cardID,mm.member_firstName,mm.member_lastName,mm.member_type from (media_transaction mt right join members mm on mt.member_cardID = mm.member_cardID) left join medias m on mt.isbn = m.isbn where ";
                String where = "";
                if (!p_member.equalsIgnoreCase("")) {
                    where += "mm.member_cardID = '" + p_member + "' ";
                    //where += "mt.member_cardID = '"+p_member+"' and mt.isbn = m.isbn and mt.member_cardID = mm.member_cardID ";
                }

                if (where.equalsIgnoreCase("")) {
                    where = "1";
                }

                sql_sel = sql_sel + " " + where + " order by mt.isbn ";
                System.out.println("sql_sel = " + sql_sel);

                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {

                    memberReturn = rsult.getString("member_cardID");
                    firstnameReturn = rsult.getString("member_firstName");
                    lastnameReturn = rsult.getString("member_lastName");
                    isbnReturn = rsult.getString("isbn");
                    titleReturn = rsult.getString("title");
                    authorReturn = rsult.getString("author");
                    borrowdateReturn = rsult.getString("borrow_date");
                    returndateReturn = rsult.getString("return_date");
                    memberTypeReturn = rsult.getString("member_type");
                    mediamember.add(new MediaMember(memberReturn, firstnameReturn, lastnameReturn, isbnReturn, titleReturn, authorReturn, borrowdateReturn, returndateReturn, memberTypeReturn));

                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        MediaMemberBorrowedClass lib = new MediaMemberBorrowedClass(mediamember);
        Object rsultArray[][] = lib.MediaMembertoArray();

        return rsultArray;
    }

    @WebMethod(operationName = "mediaBorrowSearchISBN")
    public Object[][] mediaBorrowSearchISBN(@WebParam(name = "p_isbn") String p_isbn) {
        String memberReturn = "";
        String firstnameReturn = "";
        String lastnameReturn = "";
        String isbnReturn = "";
        String titleReturn = "";
        String authorReturn = "";
        String sql_sel = "";
        String borrowdateReturn = "";
        String returndateReturn = "";
        String memberTypeReturn = "";
        List<MediaMember> mediamember = new ArrayList<MediaMember>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "select mt.isbn,mt.member_cardID,mt.borrow_date,mt.return_date,m.title,m.author,mm.member_firstName,mm.member_lastName from media_transaction mt,medias m,members mm where ";
                //where bm.member_cardID = '0001'
                //and bm.isbn = b.isbn
                //and bm.member_cardID = m.member_cardID

                String where = "";
                if (!p_isbn.equalsIgnoreCase("")) {
                    where += "mt.isbn = '" + p_isbn + "' and mt.isbn = m.isbn and mt.member_cardID = mm.member_cardID ";
                }

                if (where.equalsIgnoreCase("")) {
                    where = "1";
                }

                sql_sel = sql_sel + " " + where + " order by mt.isbn ";
                System.out.println("sql_sel = " + sql_sel);

                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {

                    memberReturn = rsult.getString("member_cardID");
                    firstnameReturn = rsult.getString("member_firstName");
                    lastnameReturn = rsult.getString("member_lastName");
                    isbnReturn = rsult.getString("isbn");
                    titleReturn = rsult.getString("title");
                    authorReturn = rsult.getString("author");
                    borrowdateReturn = rsult.getString("borrow_date");
                    returndateReturn = rsult.getString("return_date");
                    mediamember.add(new MediaMember(memberReturn, firstnameReturn, lastnameReturn, isbnReturn, titleReturn, authorReturn, borrowdateReturn, returndateReturn, memberTypeReturn));

                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        MediaMemberBorrowedClass lib = new MediaMemberBorrowedClass(mediamember);
        Object rsultArray[][] = lib.MediaMembertoArray();

        return rsultArray;
    }

    @WebMethod(operationName = "mediaBorrowNew")
    public int mediaBorrowNew(@WebParam(name = "p_member") String p_member,
            @WebParam(name = "p_isbn") String p_isbn,
            @WebParam(name = "p_borrowDate") String p_borrowDate,
            @WebParam(name = "p_returnDate") String p_returnDate,
            @WebParam(name = "p_memberName") String p_memberName,
            @WebParam(name = "p_mediaName") String p_mediaName) {
        int addResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from media_transaction where isbn='" + p_isbn + "'");
                System.out.println("select * from media_transaction where isbn='" + p_isbn + "'");
                if (rsult.next()) {
                    addResult = 9;
                    //addResult = "ISBN EXISTS";
                } else {
                    //borrowDateTime = Calendar.getInstance().getTime();
                    int tmpIns = stmt.executeUpdate("insert into media_transaction (member_cardID,isbn,borrow_date,return_date) values('" + p_member + "','" + p_isbn + "','" + p_borrowDate + "','" + p_returnDate + "') ");
                    System.out.println("insert into media_transaction (member_cardID,isbn,borrow_date,return_date) values('" + p_member + "','" + p_isbn + "','" + p_borrowDate + "','" + p_returnDate + "') ");
                    if (tmpIns != -1) {

                        int tmpUpd = stmt.executeUpdate("update medias set media_flag='1' where isbn='" + p_isbn + "'");
                        System.out.println(">insert into medias_log (member_cardID,member_name,isbn,borrow_date,return_date,media_name) values('" + p_member + "','" + p_memberName + "','" + p_isbn + "','" + p_borrowDate + "','" + p_returnDate + "','" + p_mediaName + "') ");
                        int tmpInsLog = stmt.executeUpdate("insert into medias_log (member_cardID,member_name,isbn,borrow_date,return_date,media_name) values('" + p_member + "','" + p_memberName + "','" + p_isbn + "','" + p_borrowDate + "','" + p_returnDate + "','" + p_mediaName + "') ");
                        addResult = 1;
                        //addResult = "ADD COMPLETED";
                    } else {
                        addResult = 0;
                        //addResult = "ADD FAILED";
                    }
                }
                con.close();
            } catch (SQLException ex) {
                addResult = 0;
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            addResult = 0;
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return addResult;
    }

    @WebMethod(operationName = "mediaBorrowDelete")
    public int mediaBorrowDelete(@WebParam(name = "p_isbn") String p_isbn) {
        int deleteResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from media_transaction where isbn='" + p_isbn + "'");
                System.out.println(rsult.getRow() + " select * from media_transaction where isbn='" + p_isbn + "'");

                if (rsult.next()) {
                    int tmpDel = stmt.executeUpdate("delete from media_transaction where isbn='" + p_isbn + "' ");
                    System.out.println("delete from media_transaction where isbn='" + p_isbn + "' ");
                    if (tmpDel != -1) {
                        //deleteResult = "Delete Completed";
                        int tmpUpd = stmt.executeUpdate("update medias set media_flag='0' where isbn='" + p_isbn + "'");
                        deleteResult = 1;
                    } else {
                        //deleteResult = "Delete Failed";
                        deleteResult = 0;
                    }
                } else {
                    //deleteResult = "No ISBN Found";
                    deleteResult = 9;
                }

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleteResult;
    }

    @WebMethod(operationName = "bookBorrowSearch")
    public Object[][] bookBorrowSearch(@WebParam(name = "p_member") String p_member) {
        String memberReturn = "";
        String firstnameReturn = "";
        String lastnameReturn = "";
        String isbnReturn = "";
        String titleReturn = "";
        String authorReturn = "";
        String sql_sel = "";
        String borrowdateReturn = "";
        String returndateReturn = "";
        String memberTypeReturn = "";
        List<BookMember> bookmember = new ArrayList<BookMember>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                //sql_sel = "select bm.isbn,bm.member_cardID,bm.borrow_date,bm.return_date,b.title,b.author,m.member_firstName,m.member_lastName from book_transaction bm,books b,members m where ";
                //where bm.member_cardID = '0001'
                //and bm.isbn = b.isbn
                //and bm.member_cardID = m.member_cardID

                /*
                 select tmp.*,bm.member_cardID,bm.isbn from
                 (select member_cardID,member_firstName,member_lastName,title,author,isbn from members,books) tmp
                 right join book_transaction bm on (bm.isbn = tmp.isbn and bm.member_cardID=tmp.member_cardID)
                 where tmp.member_cardID = '0002'
                 */
                //sql_sel = "select b.title,b.isbn,bm.isbn,bm.borrow_date,bm.return_date,m.member_cardID,m.member_firstName,m.member_lastName from (book_transaction bm right join members m on bm.member_cardID = m.member_cardID) left join books b on bm.isbn = b.isbn where  ";
                sql_sel = "select b.title,b.isbn,b.author,bm.borrow_date,bm.return_date,m.member_cardID,m.member_firstName,m.member_lastName,m.member_type from (book_transaction bm right join members m on bm.member_cardID = m.member_cardID) left join books b on bm.isbn = b.isbn where  ";

                String where = "";
                if (!p_member.equalsIgnoreCase("")) {
                    //where += "bm.member_cardID = '"+p_member+"' and bm.isbn = b.isbn and bm.member_cardID = m.member_cardID ";
                    where += "m.member_cardID = '" + p_member + "' ";
                }

                if (where.equalsIgnoreCase("")) {
                    where = "1";
                }

                sql_sel = sql_sel + " " + where + " order by bm.isbn ";
                System.out.println("sql_sel = " + sql_sel);

                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {

                    memberReturn = rsult.getString("member_cardID");
                    firstnameReturn = rsult.getString("member_firstName");
                    lastnameReturn = rsult.getString("member_lastName");
                    isbnReturn = rsult.getString("isbn");
                    titleReturn = rsult.getString("title");
                    authorReturn = rsult.getString("author");
                    borrowdateReturn = rsult.getString("borrow_date");
                    returndateReturn = rsult.getString("return_date");
                    memberTypeReturn = rsult.getString("member_type");

                    //authorReturn = rsult.getString("author");
                    bookmember.add(new BookMember(memberReturn, firstnameReturn, lastnameReturn, isbnReturn, titleReturn, authorReturn, borrowdateReturn, returndateReturn, memberTypeReturn));

                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        BookMemberBorrowedClass lib = new BookMemberBorrowedClass(bookmember);
        Object rsultArray[][] = lib.BookBorrowedtoArray();

        return rsultArray;
    }

    @WebMethod(operationName = "bookBorrowSearchISBN")
    public Object[][] bookBorrowSearchISBN(@WebParam(name = "p_isbn") String p_isbn) {
        String memberReturn = "";
        String firstnameReturn = "";
        String lastnameReturn = "";
        String isbnReturn = "";
        String titleReturn = "";
        String authorReturn = "";
        String sql_sel = "";
        String borrowdateReturn = "";
        String returndateReturn = "";
        List<BookMember> bookmember = new ArrayList<BookMember>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "select bm.isbn,bm.member_cardID,bm.borrow_date,bm.return_date,b.title,b.author,m.member_firstName,m.member_lastName from book_transaction bm,books b,members m where ";
                //where bm.member_cardID = '0001'
                //and bm.isbn = b.isbn
                //and bm.member_cardID = m.member_cardID

                String where = "";
                if (!p_isbn.equalsIgnoreCase("")) {
                    where += "bm.isbn = '" + p_isbn + "' and bm.isbn = b.isbn and bm.member_cardID = m.member_cardID ";
                }

                if (where.equalsIgnoreCase("")) {
                    where = "1";
                }

                sql_sel = sql_sel + " " + where + " order by bm.isbn";
                System.out.println("sql_sel = " + sql_sel);

                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {

                    memberReturn = rsult.getString("member_cardID");
                    firstnameReturn = rsult.getString("member_firstName");
                    lastnameReturn = rsult.getString("member_lastName");
                    isbnReturn = rsult.getString("isbn");
                    titleReturn = rsult.getString("title");
                    authorReturn = rsult.getString("author");
                    borrowdateReturn = rsult.getString("borrow_date");
                    returndateReturn = rsult.getString("return_date");

                    //authorReturn = rsult.getString("author");
                    bookmember.add(new BookMember(memberReturn, firstnameReturn, lastnameReturn, isbnReturn, titleReturn, authorReturn, borrowdateReturn, returndateReturn));

                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        BookMemberBorrowedClass lib = new BookMemberBorrowedClass(bookmember);
        Object rsultArray[][] = lib.BookBorrowedtoArray();

        return rsultArray;
    }

    @WebMethod(operationName = "bookBorrowNew")
    public int bookBorrowNew(@WebParam(name = "p_member") String p_member,
            @WebParam(name = "p_isbn") String p_isbn,
            @WebParam(name = "p_borrowDate") String p_borrowDate,
            @WebParam(name = "p_returnDate") String p_returnDate,
            @WebParam(name = "p_memberName") String p_memberName,
            @WebParam(name = "p_bookName") String p_bookName) {
        int addResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from book_transaction where isbn='" + p_isbn + "'");
                System.out.println("select * from book_transaction where isbn='" + p_isbn + "'");
                if (rsult.next()) {
                    addResult = 9;
                    //addResult = "ISBN EXISTS";
                } else {
                    //borrowDateTime = Calendar.getInstance().getTime();
                    int tmpIns = stmt.executeUpdate("insert into book_transaction (member_cardID,isbn,borrow_date,return_date) values('" + p_member + "','" + p_isbn + "','" + p_borrowDate + "','" + p_returnDate + "') ");
                    System.out.println("insert into book_transaction (member_cardID,isbn,borrow_date,return_date) values('" + p_member + "','" + p_isbn + "','" + p_borrowDate + "','" + p_returnDate + "') ");
                    if (tmpIns != -1) {
                        addResult = 1;
                        int tmpUpd = stmt.executeUpdate("update books set book_flag='1' where isbn='" + p_isbn + "'");
                        int tmpInsLog = stmt.executeUpdate("insert into books_log (member_cardID,member_name,isbn,borrow_date,return_date,book_name) values('" + p_member + "','" + p_memberName + "','" + p_isbn + "','" + p_borrowDate + "','" + p_returnDate + "','" + p_bookName + "') ");
                        //addResult = "ADD COMPLETED";
                    } else {
                        addResult = 0;
                        //addResult = "ADD FAILED";
                    }
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return addResult;
    }

    @WebMethod(operationName = "bookBorrowDelete")
    public int bookBorrowDelete(@WebParam(name = "p_isbn") String p_isbn) {
        int deleteResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from book_transaction where isbn='" + p_isbn + "'");
                System.out.println(rsult.getRow() + " select * from book_transaction where isbn='" + p_isbn + "'");

                if (rsult.next()) {
                    int tmpDel = stmt.executeUpdate("delete from book_transaction where isbn='" + p_isbn + "' ");
                    System.out.println("delete from book_transaction where isbn='" + p_isbn + "' ");
                    if (tmpDel != -1) {
                        //deleteResult = "Delete Completed";
                        int tmpUpd = stmt.executeUpdate("update books set book_flag='0' where isbn='" + p_isbn + "'");
                        deleteResult = 1;
                    } else {
                        //deleteResult = "Delete Failed";
                        deleteResult = 0;
                    }
                } else {
                    //deleteResult = "No ISBN Found";
                    deleteResult = 9;
                }

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleteResult;
    }

    @WebMethod(operationName = "bookWriteOffSearch")
    public Object[][] bookWriteOffSearch() {
        String outYearReturn = "";
        String outNoReturn = "";
        String isbnReturn = "";
        String recieveDateReturn = "";
        String titleReturn = "";
        String title2Return = "";
        String authorReturn = "";
        String categoryReturn = "";
        String sql_sel = "";
        List<BookOut> bookouts = new ArrayList<BookOut>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "select out_year,out_no,isbn,recieve_date,title,title2,author,category from books_out order by isbn ";
                //System.out.println("sql_sel = "+sql_sel);

                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {
                    outYearReturn = rsult.getString("out_year");
                    outNoReturn = rsult.getString("out_no");
                    isbnReturn = rsult.getString("isbn");
                    recieveDateReturn = rsult.getString("recieve_date");
                    titleReturn = rsult.getString("title");
                    title2Return = rsult.getString("title2");
                    authorReturn = rsult.getString("author");
                    categoryReturn = rsult.getString("category");
                    bookouts.add(new BookOut(outYearReturn, outNoReturn, isbnReturn, recieveDateReturn, titleReturn, title2Return, authorReturn, categoryReturn));

                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }

        BookOutClass lib = new BookOutClass(bookouts);
        Object rsultArray[][] = lib.bookOutToArray();

        return rsultArray;
    }

    @WebMethod(operationName = "bookWriteOffNew")
    public int bookWriteOffNew(@WebParam(name = "p_out_year") String p_out_year,
            @WebParam(name = "p_out_no") String p_out_no,
            @WebParam(name = "p_isbn") String p_isbn,
            @WebParam(name = "p_recieve_date") String p_recieve_date,
            @WebParam(name = "p_title") String p_title,
            @WebParam(name = "p_title2") String p_title2,
            @WebParam(name = "p_author") String p_author,
            @WebParam(name = "p_category") String p_category) {
        int addResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from books_out where isbn='" + p_isbn + "'");
                if (rsult.next()) {
                    addResult = 9;
                    //addResult = "ISBN EXISTS";
                } else {
                    int tmpIns = stmt.executeUpdate("insert into books_out (out_year,out_no,isbn,recieve_date,title,title2,author,category) values('" + p_out_year + "','" + p_out_no + "','" + p_isbn + "', '" + p_recieve_date + "','" + p_title + "','" + p_title2 + "','" + p_author + "','" + p_category + "') ");
                    if (tmpIns != -1) {
                        addResult = 1;
                        //addResult = "ADD COMPLETED";
                    } else {
                        addResult = 0;
                        //addResult = "ADD FAILED";
                    }
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return addResult;
    }

    @WebMethod(operationName = "bookWriteOffDelete")
    public int bookWriteOffDelete(@WebParam(name = "p_isbn") String p_isbn) {
        int deleteResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from books_out where isbn='" + p_isbn + "'");

                if (rsult.next()) {
                    int tmpDel = stmt.executeUpdate("delete from books_out where isbn='" + p_isbn + "' ");
                    if (tmpDel != -1) {
                        //deleteResult = "Delete Completed";
                        deleteResult = 1;
                    } else {
                        //deleteResult = "Delete Failed";
                        deleteResult = 0;
                    }
                } else {
                    //deleteResult = "No ISBN Found";
                    deleteResult = 9;
                }

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleteResult;
    }

    @WebMethod(operationName = "bookWriteOffUpdate")
    public int bookWriteOffUpdate(@WebParam(name = "p_out_year") String p_out_year,
            @WebParam(name = "p_out_no") String p_out_no,
            @WebParam(name = "p_isbn") String p_isbn,
            @WebParam(name = "p_recieve_date") String p_recieve_date,
            @WebParam(name = "p_title") String p_title,
            @WebParam(name = "p_title2") String p_title2,
            @WebParam(name = "p_author") String p_author,
            @WebParam(name = "p_category") String p_category) {
        //TODO write your implementation code here:
        int updateResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                int tmpUpd = stmt.executeUpdate("update books_out set out_year='" + p_out_year + "', out_no='" + p_out_no + "', recieve_date='" + p_recieve_date + "',title='" + p_title + "',title2='" + p_title2 + "',category='" + p_category + "' where isbn='" + p_isbn + "'");
                if (tmpUpd == 1) {
                    //updateResult = "Update Completed";
                    updateResult = 1;
                } else {
                    //updateResult = "Update Failed";
                    updateResult = 0;
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updateResult;
    }

    @WebMethod(operationName = "memberImageSearch")
    public Object[][] memberImageSearch(@WebParam(name = "p_id") String p_id,
            @WebParam(name = "p_firstname") String p_firstname,
            @WebParam(name = "p_lastname") String p_lastname) {

        String idReturn = "";
        String firstnameReturn = "";
        String lastnameReturn = "";
        String imgNameReturn = "";
        byte[] imgBodyReturn;
        String sexReturn = "";
        String addressReturn = "";
        String phoneReturn = "";
        String membertypeReturn = "";
        String registerDateReturn = "";
        String expiryDateReturn = "";
        String prefixReturn = "";
        String ageReturn = "";
        String occupationReturn = "";
        String addressProvinceReturn = "";
        String addressZipcodeReturn = "";
        String phone2Return = "";
        String contactAddressReturn = "";

        String sql_sel = "";
        List<Member> users = new ArrayList<Member>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                //sql_sel = "select m.member_cardID,m.member_firstName,m.member_lastName,mp.img_body,mp.img_name, " +
                //        " m.member_sex,m.member_address,m.member_tel,m.member_type,m.member_registerDate,m.member_expiryDate from members m left join member_picture mp on (m.member_cardID=mp.member_id) where ";
                sql_sel = "select m.*,mp.img_body,mp.img_name from members m left join member_picture mp on (m.member_cardID=mp.member_id) where ";
                String where = "";
                if (!p_id.equalsIgnoreCase("")) {
                    where += "m.member_cardID like '%" + p_id + "%'";
                }

                if (!p_firstname.equalsIgnoreCase("")) {
                    if (!where.equalsIgnoreCase("")) {
                        where += " and m.member_firstName like '%" + p_firstname + "%' ";
                    } else {
                        where += " m.member_firstName like '%" + p_firstname + "%' ";
                    }
                }

                if (!p_lastname.equalsIgnoreCase("")) {
                    if (!where.equalsIgnoreCase("")) {
                        where += " and m.member_lastName like '%" + p_lastname + "%'";
                    } else {
                        where += " m.member_lastName like '%" + p_lastname + "%'";
                    }
                }

                if (where.equalsIgnoreCase("")) {
                    where = "1";
                }

                sql_sel = sql_sel + " " + where + " order by m.member_cardID ";
                System.out.println("sql_sel = " + sql_sel);

                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {
                    idReturn = rsult.getString("member_cardID");
                    firstnameReturn = rsult.getString("member_firstName");
                    lastnameReturn = rsult.getString("member_lastName");
                    imgNameReturn = rsult.getString("img_name");
                    imgBodyReturn = rsult.getBytes("img_body");

                    prefixReturn = rsult.getString("member_prefixName");
                    sexReturn = rsult.getString("member_sex");
                    ageReturn = rsult.getString("member_age");
                    occupationReturn = rsult.getString("member_occupation");
                    addressReturn = rsult.getString("member_address");
                    addressProvinceReturn = rsult.getString("member_address_province");
                    addressZipcodeReturn = rsult.getString("member_address_zipcode");
                    phoneReturn = rsult.getString("member_tel");
                    phone2Return = rsult.getString("member_tel2");
                    contactAddressReturn = rsult.getString("member_contact_address");
                    membertypeReturn = rsult.getString("member_type");
                    registerDateReturn = rsult.getString("member_registerDate");
                    expiryDateReturn = rsult.getString("member_expiryDate");
                    //(String userid, String firstname, String lastname, String imgname, byte[] imgbody,
                    //    String prefix,String sex,String age,String occupation,String address,String address_prvince,
                    //    String address_zipcode,String phone,String phone2,String contact_address,String membertype,String registerdate,String expireddate)

                    users.add(new Member(idReturn, firstnameReturn, lastnameReturn, imgNameReturn, imgBodyReturn, prefixReturn, sexReturn, ageReturn, occupationReturn, addressReturn, addressProvinceReturn, addressZipcodeReturn, phoneReturn, phone2Return, contactAddressReturn, membertypeReturn, registerDateReturn, expiryDateReturn));
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        MemberClass lib = new MemberClass(users);
        //Object rsultArray[][] = lib.memberToArray();
        Object rsultArray[][] = lib.memberImageToArray();
        return rsultArray;
    }

    @WebMethod(operationName = "memberMasterMaxAutoID")
    public String memberMasterMaxAutoID() {
        String memberID = "";
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                String sql = "";
                //rsult = stmt.executeQuery("select max( member_id ) as member_id from members ");
                // rsult = stmt.executeQuery("select  lpad(max( member_id )+1,6,'0')  as member_id from members ");
                sql = "select  concat( substring(EXTRACT(YEAR FROM current_date) +543,3,2),";
                sql = sql + "lpad( substring(member_cardID,3,4)+1 ,4,'0')) as  member_id   from members a  order by last_update_date desc  limit 1";
                //Should be start with 00001 when each new year
                rsult = stmt.executeQuery(sql);

                if (rsult.next()) {
                    memberID = rsult.getString("member_id");
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        //int nextMemberID = Integer.parseInt(memberID) + 1;
        //return nextMemberID+"";
        return memberID;
    }

    @WebMethod(operationName = "memberMasterDelete")
    public int memberMasterDelete(@WebParam(name = "p_memberid") String p_memberid) {
        int deleteResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from members where member_cardID='" + p_memberid + "'");

                if (rsult.next()) {
                    int tmpDel = stmt.executeUpdate("delete from members where member_cardID='" + p_memberid + "' ");
                    if (tmpDel != -1) {
                        //deleteResult = "Delete Completed";
                        deleteResult = 1;
                        //------------------------------
                        rsult = stmt.executeQuery("select * from member_picture where member_id='" + p_memberid + "'");

                        if (rsult.next()) {
                            int tmpIMGDel = stmt.executeUpdate("delete from member_picture where member_id='" + p_memberid + "' ");
                            if (tmpIMGDel != -1) {
                                //deleteResult = "Delete Completed";
                                deleteResult = 1;
                            } else {
                                //deleteResult = "Delete Failed";
                                deleteResult = 0;
                            }
                        } else {
                            //deleteResult = "No ISBN Found";
                            deleteResult = 9;
                        }
                        //------------------------------
                    } else {
                        //deleteResult = "Delete Failed";
                        deleteResult = 0;
                    }
                } else {
                    //deleteResult = "No ISBN Found";
                    deleteResult = 9;
                }

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleteResult;
    }

    @WebMethod(operationName = "MemberPictureGetImageDefault")
    public Object[][] MemberPictureGetImageDefault() {
        String nameReturn = "";
        byte[] bodyReturn;
        String memberidReturn = "";
        List<Image> images = new ArrayList<Image>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();

                rsult = stmt.executeQuery("SELECT * FROM image_default ");
                if (rsult.next()) {
                    nameReturn = rsult.getString("img_name");
                    bodyReturn = rsult.getBytes("img_body");
                    images.add(new Image(nameReturn, bodyReturn));
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        PictureClass lib = new PictureClass(images);
        Object rsultArray[][] = lib.ToArray();
        return rsultArray;
    }

    @WebMethod(operationName = "memberBorrowedStatus")
    public Object[][] memberBorrowedStatus(@WebParam(name = "p_id") String p_id) {
        String idReturn = "";
        String isbnReturn = "";
        String titleReturn = "";
        String borrowDateReturn = "";
        String returnDateReturn = "";
        String typeReturn = "";
        String sql_sel = "";
        String book_sel = "";
        String media_sel = "";
        List<BorrowList> borrowlists = new ArrayList<BorrowList>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                //title,borrow_date,return_date,type={book,media}
                book_sel = "select bt.member_cardID,bt.isbn as isbn,b.title,bt.borrow_date,bt.return_date,'book' as borrowtype from book_transaction bt,books b where ";
                media_sel = "select mt.member_cardID,mt.isbn as isbn,m.title,mt.borrow_date,mt.return_date,'media' as borrowtype from media_transaction mt,medias m where ";
                String where_b = "";
                String where_m = "";
                if (!p_id.equalsIgnoreCase("")) {
                    where_b += "bt.member_cardID = '" + p_id + "' and bt.isbn=b.isbn  ";
                    where_m += "mt.member_cardID = '" + p_id + "' and mt.isbn=m.isbn  ";
                }

                if (where_b.equalsIgnoreCase("")) {
                    where_b = "1";
                    where_m = "1";
                }

                book_sel = book_sel + " " + where_b;
                media_sel = media_sel + " " + where_m;
                //System.out.println("book_sel = "+book_sel);
                //System.out.println("media_sel = "+media_sel);

                //sql_sel = "(select bt.member_cardID,bt.isbn from book_transaction bt where bt.member_cardID='0001' )union(select mt.member_cardID,mt.isbn from media_transaction mt where mt.member_cardID='0001')" ;
                //book_sel = "select bt.member_cardID,bt.isbn,b.title,bt.borrow_date,bt.return_date,'book' as type from book_transaction bt,books b where bt.member_cardID='0001' and bt.isbn=b.isbn ";
                //media_sel = "select mt.member_cardID,mt.isbn,m.title,mt.borrow_date,mt.return_date,'media' as type from media_transaction mt,medias m where mt.member_cardID='0001' and mt.isbn=m.isbn ";
                sql_sel = "(" + book_sel + ")union(" + media_sel + ") order by borrowtype,isbn";
                System.out.println("sql_sel=" + sql_sel);
                rsult = stmt.executeQuery(sql_sel);

                while (rsult.next()) {
                    idReturn = rsult.getString("member_cardID");
                    isbnReturn = rsult.getString("isbn");
                    titleReturn = rsult.getString("title");
                    borrowDateReturn = rsult.getString("borrow_date");
                    returnDateReturn = rsult.getString("return_date");
                    typeReturn = rsult.getString("borrowtype");

                    borrowlists.add(new BorrowList(idReturn, isbnReturn, titleReturn, borrowDateReturn, returnDateReturn, typeReturn));
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        MemberBorrowListClass lib = new MemberBorrowListClass(borrowlists);
        Object rsultArray[][] = lib.borrowListToArray();

        return rsultArray;
    }

    @WebMethod(operationName = "systemSignIn")
    public Boolean systemSignIn(@WebParam(name = "p_username") String p_username, @WebParam(name = "p_password") String p_password, @WebParam(name = "p_client_ip") String p_client_ip, @WebParam(name = "p_client_name") String p_client_name) {
        //TODO write your implementation code here:

        Boolean signInSuccess = false;
        Date loginDateTime;
        String sql = "";
        try {
            Class.forName(driver);
            try {
                con = (Connection) DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = (Statement) con.createStatement();
                rsult = (ResultSet) stmt.executeQuery("select 1 as hasUser from app_user where username=" + utilFunction.quotate(p_username) + " and userpass=password(" + utilFunction.quotate(p_password) + ")");
                //loginDateTime = Calendar.getInstance().getTime();
                loginDateTime = Calendar.getInstance().getTime();
                System.out.println("select 1 as hasUser from app_user where username=" + utilFunction.quotate(p_username) + " and userpass=password(" + utilFunction.quotate(p_password) + ")");
                while (rsult.next()) {
                    if (rsult.getString("hasUser").equals("1")) {
                        signInSuccess = true;
                    }
                }
                sql = "insert into app_user_login_history( login_datetime, login_username, login_client_ip, login_client_name, login_success) ";
                sql = sql + " values(";
                sql = sql + utilFunction.quotate(loginDateTime.toString()) + ",";
                sql = sql + utilFunction.quotate(p_username) + ",";
                sql = sql + utilFunction.quotate(p_client_ip) + ",";
                sql = sql + utilFunction.quotate(p_client_name) + ",";
                sql = sql + utilFunction.quotate((signInSuccess ? "Y" : "N")) + ")";
                stmt.executeUpdate(sql);
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return signInSuccess;
    }

    @WebMethod(operationName = "memberPictureNew")
    public int memberPictureNew(@WebParam(name = "p_fileBase64") String p_fileBase64,
            @WebParam(name = "p_fileName") String p_fileName,
            @WebParam(name = "p_file_length") long p_file_length,
            @WebParam(name = "p_member_id") String p_member_id,
            @WebParam(name = "p_firstname") String p_firstname,
            @WebParam(name = "p_lastname") String p_lastname,
            @WebParam(name = "p_prefix") String p_prefix,
            @WebParam(name = "p_sex") String p_sex,
            @WebParam(name = "p_age") String p_age,
            @WebParam(name = "p_occupation") String p_occupation,
            @WebParam(name = "p_address") String p_address,
            @WebParam(name = "p_address_province") String p_address_province,
            @WebParam(name = "p_address_zipcode") String p_address_zipcode,
            @WebParam(name = "p_tel1") String p_tel1,
            @WebParam(name = "p_tel2") String p_tel2,
            @WebParam(name = "p_contact_address") String p_contact_address,
            @WebParam(name = "p_membertype") String p_membertype,
            @WebParam(name = "p_registerDate") String p_registerDate,
            @WebParam(name = "p_expiryDate") String p_expiryDate) {
        int addResult = 0;
        //String sDecode = URLDecoder.decode(p_file_byte, "UTF-8");
        //byte[] p_file = sDecode.getBytes();
        byte[] p_file = null;
        try {
            p_file = Base64.decodeBase64(p_fileBase64);
        } catch (Exception ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }

        Blob blob = null; //is our blob object
        try {
            //is our byte array
            blob = new SerialBlob(p_file);
        } catch (SerialException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from members where member_cardID='" + p_member_id + "'");
                if (rsult.next()) {
                    addResult = 9;
                    //addResult = "MEMBER EXISTS";
                } else {
                    int tmpIns = stmt.executeUpdate("insert into members (member_cardID,member_prefixName,member_firstName,member_lastName,"
                            + "member_sex, member_address, member_address_province, member_address_zipcode, member_tel,"
                            + "member_tel2, member_contact_address, member_type, member_registerDate, member_expiryDate, member_occupation, member_age) "
                            + "values('" + p_member_id + "','" + p_prefix + "','" + p_firstname + "','" + p_lastname + "',"
                            + "'" + p_sex + "', '" + p_address + "', '" + p_address_province + "', '" + p_address_zipcode + "', '" + p_tel1 + "',"
                            + "'" + p_tel2 + "', '" + p_contact_address + "', '" + p_membertype + "', '" + p_registerDate + "', '" + p_expiryDate + "', '" + p_occupation + "', '" + p_age + "') ");
                    if (tmpIns != -1) {
                        addResult = 1;
                        //addResult = "ADD COMPLETED";
                        //---------------------
                        rsult = stmt.executeQuery("select * from member_picture where img_name='" + p_fileName + "' and member_id='" + p_member_id + "' ");
                        if (rsult.next()) {
                            //addResult = 9;
                            //addResult = "MEMBER EXISTS";
                        } else {
                            if (!(p_fileBase64.equalsIgnoreCase(""))) {
                                PreparedStatement pre = con.prepareStatement("insert into member_picture (img_body,img_name,member_id) values(?,?,?)");
                                InputStream is = blob.getBinaryStream();
                                //pre.setBlob(1, is);
                                pre.setBinaryStream(1, is, (int) p_file_length);
                                pre.setString(2, p_fileName);
                                pre.setString(3, p_member_id);
                                int tmpIMGIns = pre.executeUpdate();
                                if (tmpIMGIns != -1) {
                                    addResult = 1;
                                    //addResult = "ADD COMPLETED";
                                } else {
                                    addResult = 0;
                                    //addResult = "ADD FAILED";
                                }
                                pre.close();
                            } else {
                                int tmpIMGIns = stmt.executeUpdate("insert into member_picture (img_body,img_name,member_id) select img_body,img_name,'" + p_member_id + "' from image_default");
                                if (tmpIMGIns != -1) {
                                    addResult = 1;
                                    //addResult = "ADD COMPLETED";
                                } else {
                                    addResult = 0;
                                    //addResult = "ADD FAILED";
                                }
                            }
                        }
                        //---------------------

                    } else {
                        addResult = 0;
                        //addResult = "ADD FAILED";
                    }
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }

        return addResult;
    }

    @WebMethod(operationName = "memberPictureUpdate")
    public int memberPictureUpdate(@WebParam(name = "p_fileBase64") String p_fileBase64,
            @WebParam(name = "p_fileName") String p_fileName,
            @WebParam(name = "p_file_length") long p_file_length,
            @WebParam(name = "p_member_id") String p_member_id,
            @WebParam(name = "p_firstname") String p_firstname,
            @WebParam(name = "p_lastname") String p_lastname,
            @WebParam(name = "p_prefix") String p_prefix,
            @WebParam(name = "p_sex") String p_sex,
            @WebParam(name = "p_age") String p_age,
            @WebParam(name = "p_occupation") String p_occupation,
            @WebParam(name = "p_address") String p_address,
            @WebParam(name = "p_address_province") String p_address_province,
            @WebParam(name = "p_address_zipcode") String p_address_zipcode,
            @WebParam(name = "p_tel1") String p_tel1,
            @WebParam(name = "p_tel2") String p_tel2,
            @WebParam(name = "p_contact_address") String p_contact_address,
            @WebParam(name = "p_membertype") String p_membertype,
            @WebParam(name = "p_registerDate") String p_registerDate,
            @WebParam(name = "p_expiryDate") String p_expiryDate) {
        int updateResult = 0;
        //String sDecode = URLDecoder.decode(p_file_byte, "UTF-8");
        //byte[] p_file = sDecode.getBytes();
        byte[] p_file = null;
        Blob blob = null; //is our blob object


        if (!(p_fileBase64.equalsIgnoreCase(""))) {
            try {
                //p_file = new Base64.decode(p_fileBase64);
                //  p_file =new  Base64Decoder().decode(p_fileBase64);
                //  p_file=  sun.misc.BASE64Decoder().decodeBuffer(p_fileBase64);
                p_file = p_file = Base64.decodeBase64(p_fileBase64);
            } catch (Exception ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
                //is our byte array
                blob = new SerialBlob(p_file);
            } catch (SerialException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                // int tmpUpd = stmt.executeUpdate("update members set member_firstName='" + p_firstname + "', member_lastName='"+p_lastname+"' where member_cardID='" + p_member_id + "'");
                int tmpUpd = stmt.executeUpdate("update members set member_firstName='" + p_firstname + "', member_lastName='" + p_lastname + "', "
                        + "member_prefixName='" + p_prefix + "',member_sex='" + p_sex + "',member_address='" + p_address + "',"
                        + "member_address_province='" + p_address_province + "',member_address_zipcode='" + p_address_zipcode + "',"
                        + "member_tel='" + p_tel1 + "',member_tel2='" + p_tel2 + "',member_contact_address='" + p_contact_address + "',"
                        + "member_type='" + p_membertype + "',member_registerDate='" + p_registerDate + "',member_expiryDate='" + p_expiryDate + "',member_occupation='" + p_occupation + "',member_age='" + p_age + "' where member_cardID='" + p_member_id + "' ");

                if (tmpUpd == 1) {
                    //updateResult = "Update Completed";
                    updateResult = 1;
                    if (!(p_fileBase64.equalsIgnoreCase(""))) {
                        rsult = stmt.executeQuery("SELECT * FROM member_picture where member_id = '" + p_member_id + "' ");
                        if (rsult.next()) {
                            //blob = rs.getBlob("RawData");
                            PreparedStatement pre = con.prepareStatement("UPDATE member_picture SET img_body= ?,img_name= ? where member_id=? ");

                            InputStream is = blob.getBinaryStream();
                            pre.setBinaryStream(1, is, (int) p_file_length);
                            pre.setString(2, p_fileName);
                            //pre.setString(1,"kook.gif");
                            pre.setString(3, p_member_id);

                            int tmpIns = pre.executeUpdate();
                            updateResult = tmpIns;
                            if (tmpIns == 1) {
                                updateResult = 1;
                                //addResult = "ADD COMPLETED";
                            } else {
                                updateResult = 0;
                                //addResult = "ADD FAILED";
                            }
                            pre.close();
                        } else {
                            PreparedStatement pre = con.prepareStatement("insert into member_picture (img_body,img_name,member_id) values(?,?,?)");
                            InputStream is = blob.getBinaryStream();
                            //pre.setBlob(1, is);
                            pre.setBinaryStream(1, is, (int) p_file_length);
                            pre.setString(2, p_fileName);
                            pre.setString(3, p_member_id);
                            int tmpIns = pre.executeUpdate();
                            if (tmpIns != -1) {
                                updateResult = 1;
                                //addResult = "ADD COMPLETED";
                            } else {
                                updateResult = 0;
                                //addResult = "ADD FAILED";
                            }
                            pre.close();
                        }
                    }
                } else {
                    //updateResult = "Update Failed";
                    updateResult = 9;
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updateResult;
    }

    @WebMethod(operationName = "memberCardSearch")
    public Object[][] memberCardSearch() {
        String headerReturn = "";
        String line1Return = "";
        String line2Return = "";
        String line3Return = "";
        String line4Return = "";
        String line5Return = "";
        String line6Return = "";
        String line7Return = "";

        String sql_sel = "";
        List<MemberCard> cards = new ArrayList<MemberCard>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "select * from master_member_card_detail ";

                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {
                    headerReturn = rsult.getString("header");
                    line1Return = rsult.getString("line1");
                    line2Return = rsult.getString("line2");
                    line3Return = rsult.getString("line3");
                    line4Return = rsult.getString("line4");
                    line5Return = rsult.getString("line5");
                    line6Return = rsult.getString("line6");
                    line7Return = rsult.getString("line7");
                    cards.add(new MemberCard(headerReturn, line1Return, line2Return,
                            line3Return, line4Return, line5Return, line6Return, line7Return));

                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        MasterDataMemberCardClass card = new MasterDataMemberCardClass(cards);
        Object rsultArray[][] = card.memberCardToArray();
        return rsultArray;
    }

    @WebMethod(operationName = "memberCardUpdate")
    public int memberCardUpdate(@WebParam(name = "header") String header,
            @WebParam(name = "line1") String line1,
            @WebParam(name = "line2") String line2,
            @WebParam(name = "line3") String line3,
            @WebParam(name = "line4") String line4,
            @WebParam(name = "line5") String line5,
            @WebParam(name = "line6") String line6,
            @WebParam(name = "line7") String line7) {
        int updateResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                int tmpUpd = stmt.executeUpdate("update master_member_card_detail set header='" + header + "', line1='" + line1 + "',"
                        + "line2='" + line2 + "',line3='" + line3 + "',line4='" + line4 + "',line5='" + line5 + "',line6='" + line6 + "',line7='" + line7 + "' ");
                if (tmpUpd == 1) {
                    //updateResult = "Update Completed";
                    updateResult = 1;
                } else {
                    //updateResult = "Update Failed";
                    updateResult = 0;
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updateResult;
    }

    @WebMethod(operationName = "mediaWriteOffSearch")
    public Object[][] mediaWriteOffSearch() {
        String outYearReturn = "";
        String outNoReturn = "";
        String isbnReturn = "";
        String recieveDateReturn = "";
        String titleReturn = "";
        String title2Return = "";
        String authorReturn = "";
        String sql_sel = "";
        List<MediaOut> mediaouts = new ArrayList<MediaOut>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "select out_year,out_no,isbn,recieve_date,title,title2,author from medias_out order by isbn ";
                //System.out.println("sql_sel = "+sql_sel);

                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {
                    outYearReturn = rsult.getString("out_year");
                    outNoReturn = rsult.getString("out_no");
                    isbnReturn = rsult.getString("isbn");
                    recieveDateReturn = rsult.getString("recieve_date");
                    titleReturn = rsult.getString("title");
                    title2Return = rsult.getString("title2");
                    authorReturn = rsult.getString("author");
                    mediaouts.add(new MediaOut(outYearReturn, outNoReturn, isbnReturn, recieveDateReturn, titleReturn, title2Return, authorReturn));
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }

        MediaOutClass lib = new MediaOutClass(mediaouts);
        Object rsultArray[][] = lib.mediaOutToArray();

        return rsultArray;
    }

    @WebMethod(operationName = "mediaWriteOffNew")
    public int mediaWriteOffNew(@WebParam(name = "p_out_year") String p_out_year,
            @WebParam(name = "p_out_no") String p_out_no,
            @WebParam(name = "p_isbn") String p_isbn,
            @WebParam(name = "p_recieve_date") String p_recieve_date,
            @WebParam(name = "p_title") String p_title,
            @WebParam(name = "p_title2") String p_title2,
            @WebParam(name = "p_author") String p_author) {
        int addResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from medias_out where isbn='" + p_isbn + "'");
                if (rsult.next()) {
                    addResult = 9;
                    //addResult = "ISBN EXISTS";
                } else {
                    int tmpIns = stmt.executeUpdate("insert into medias_out (out_year,out_no,isbn,recieve_date,title,title2,author) values('" + p_out_year + "','" + p_out_no + "','" + p_isbn + "', '" + p_recieve_date + "','" + p_title + "','" + p_title2 + "','" + p_author + "') ");
                    if (tmpIns != -1) {
                        addResult = 1;
                        //addResult = "ADD COMPLETED";
                    } else {
                        addResult = 0;
                        //addResult = "ADD FAILED";
                    }
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return addResult;
    }

    @WebMethod(operationName = "mediaWriteOffDelete")
    public int mediaWriteOffDelete(@WebParam(name = "p_isbn") String p_isbn) {
        int deleteResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from medias_out where isbn='" + p_isbn + "'");

                if (rsult.next()) {
                    int tmpDel = stmt.executeUpdate("delete from medias_out where isbn='" + p_isbn + "' ");
                    if (tmpDel != -1) {
                        //deleteResult = "Delete Completed";
                        deleteResult = 1;
                    } else {
                        //deleteResult = "Delete Failed";
                        deleteResult = 0;
                    }
                } else {
                    //deleteResult = "No ISBN Found";
                    deleteResult = 9;
                }

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleteResult;
    }

    @WebMethod(operationName = "mediaWriteOffUpdate")
    public int mediaWriteOffUpdate(@WebParam(name = "p_out_year") String p_out_year,
            @WebParam(name = "p_out_no") String p_out_no,
            @WebParam(name = "p_isbn") String p_isbn,
            @WebParam(name = "p_recieve_date") String p_recieve_date,
            @WebParam(name = "p_title") String p_title,
            @WebParam(name = "p_title2") String p_title2,
            @WebParam(name = "p_author") String p_author) {
        //TODO write your implementation code here:
        int updateResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                int tmpUpd = stmt.executeUpdate("update medias_out set out_year='" + p_out_year + "', out_no='" + p_out_no + "', recieve_date='" + p_recieve_date + "',title='" + p_title + "',title2='" + p_title2 + "' where isbn='" + p_isbn + "'");
                if (tmpUpd == 1) {
                    //updateResult = "Update Completed";
                    updateResult = 1;
                } else {
                    //updateResult = "Update Failed";
                    updateResult = 0;
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updateResult;
    }

    @WebMethod(operationName = "systemUserUpdate")
    public int systemUserUpdate(@WebParam(name = "p_username") String p_username,
            @WebParam(name = "p_useroldpass") String p_useroldpass,
            @WebParam(name = "p_usernewpass") String p_usernewpass) {
        //TODO write your implementation code here:
        int updateResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from app_user where username='" + p_username + "' and userpass=password('" + p_useroldpass + "') ");
                if (rsult.next()) {
                    //-----------------------
                    int tmpUpd = stmt.executeUpdate("update app_user set username='" + p_username + "', userpass=password('" + p_usernewpass + "') where userpass=password('" + p_useroldpass + "')");
                    if (tmpUpd == 1) {
                        //updateResult = "Update Completed";
                        updateResult = 1;
                    } else {
                        //updateResult = "Update Failed";
                        updateResult = 0;
                    }
                    //----------------------
                } else {
                    updateResult = 9;
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updateResult;
    }

    @WebMethod(operationName = "systemUserSearch")
    public Object[][] systemUserSearch(@WebParam(name = "p_username") String p_username) {
        String usernameReturn = "";
        String lastUpdateDateReturn = "";
        String lastUpdateByReturn = "";
        String userGroupKeyReturn = "";

        String sql_sel = "";
        List<User> users = new ArrayList<User>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "select username,usergroup_key,last_update_date,last_update_by from app_user where ";
                String where = "";
                if (!p_username.equalsIgnoreCase("")) {
                    where += "username like '%" + p_username + "%'";
                }
                if (where.equalsIgnoreCase("")) {
                    where = "1";
                }
                sql_sel = sql_sel + " " + where + " order by username ";
                //System.out.println("sql_sel = "+sql_sel);

                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {
                    usernameReturn = rsult.getString("username");
                    lastUpdateDateReturn = rsult.getString("usergroup_key");
                    lastUpdateByReturn = rsult.getString("last_update_date");
                    userGroupKeyReturn = rsult.getString("last_update_by");

                    users.add(new User(usernameReturn, userGroupKeyReturn, lastUpdateDateReturn, lastUpdateByReturn));
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }

        UserClass lib = new UserClass(users);
        Object rsultArray[][] = lib.userToArray();

        return rsultArray;
    }

    @WebMethod(operationName = "systemUserNew")
    public int systemUserNew(@WebParam(name = "p_username") String p_username,
            @WebParam(name = "p_userpass") String p_userpass,
            @WebParam(name = "p_usergroupby") String p_usergroupby,
            @WebParam(name = "p_lastupdate_date") String p_lastupdate_date,
            @WebParam(name = "p_lastupdate_by") String p_lastupdate_by) {
        int addResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from app_user where username='" + p_username + "' and userpass=password('" + p_userpass + "') ");
                if (rsult.next()) {
                    addResult = 9;
                    //addResult = "ISBN EXISTS";
                } else {
                    int tmpIns = stmt.executeUpdate("insert into app_user (username,userpass,usergroup_key,last_update_date,last_update_by) "
                            + "values('" + p_username + "',password('" + p_userpass + "'),'" + p_usergroupby + "','" + p_lastupdate_date + "','" + p_lastupdate_by + "') ");
                    if (tmpIns != -1) {
                        addResult = 1;
                        //addResult = "ADD COMPLETED";
                    } else {
                        addResult = 0;
                        //addResult = "ADD FAILED";
                    }
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return addResult;
    }

    @WebMethod(operationName = "systemUserDelete")
    public int systemUserDelete(@WebParam(name = "p_username") String p_username,
            @WebParam(name = "p_userpass") String p_userpass) {
        int deleteResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from app_user where username='" + p_username + "' and userpass=password('" + p_userpass + "') ");

                if (rsult.next()) {
                    int tmpDel = stmt.executeUpdate("delete from app_user where where username='" + p_username + "' and userpass=password('" + p_userpass + "') ");
                    if (tmpDel != -1) {
                        //deleteResult = "Delete Completed";
                        deleteResult = 1;
                    } else {
                        //deleteResult = "Delete Failed";
                        deleteResult = 0;
                    }
                } else {
                    //deleteResult = "No ISBN Found";
                    deleteResult = 9;
                }

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleteResult;
    }

    @WebMethod(operationName = "systemLibrarySearch")
    public Object[][] systemLibrarySearch() {
        String nameReturn = "";
        String districtReturn = "";
        String provinceReturn = "";
        String zipcodeReturn = "";
        String telReturn = "";
        String idReturn = "";
        String sql_sel = "";
        List<MasterLibrary> masterlibraries = new ArrayList<MasterLibrary>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "select id,name,district,province,zipcode,tel from library ";
                //System.out.println("sql_sel = "+sql_sel);

                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {
                    nameReturn = rsult.getString("name");
                    districtReturn = rsult.getString("district");
                    provinceReturn = rsult.getString("province");
                    zipcodeReturn = rsult.getString("zipcode");
                    telReturn = rsult.getString("tel");
                    idReturn = rsult.getString("id");
                    masterlibraries.add(new MasterLibrary(nameReturn, districtReturn, provinceReturn, zipcodeReturn, telReturn, idReturn));
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        MasterDataLibraryClass lib = new MasterDataLibraryClass(masterlibraries);
        Object rsultArray[][] = lib.masterLibrarytoArray();

        return rsultArray;
    }

    @WebMethod(operationName = "systemLibraryUpdate")
    public int systemLibraryUpdate(@WebParam(name = "p_id") String p_id,
            @WebParam(name = "p_name") String p_name,
            @WebParam(name = "p_district") String p_district,
            @WebParam(name = "p_province") String p_province,
            @WebParam(name = "p_zipcode") String p_zipcode,
            @WebParam(name = "p_tel") String p_tel) {
        //TODO write your implementation code here:
        int updateResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                int tmpUpd = stmt.executeUpdate("update library set name='" + p_name + "', district='" + p_district + "', province='" + p_province + "', zipcode='" + p_zipcode + "', tel='" + p_tel + "' where id='" + p_id + "'");
                if (tmpUpd == 1) {
                    //updateResult = "Update Completed";
                    updateResult = 1;
                } else {
                    //updateResult = "Update Failed";
                    updateResult = 0;
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return updateResult;
    }

    @WebMethod(operationName = "systemLibraryNew")
    public int systemLibraryNew(@WebParam(name = "p_name") String p_name,
            @WebParam(name = "p_district") String p_district,
            @WebParam(name = "p_province") String p_province,
            @WebParam(name = "p_zipcode") String p_zipcode,
            @WebParam(name = "p_tel") String p_tel) {
        int addResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from library where name='" + p_name + "'");
                if (rsult.next()) {
                    addResult = 9;
                    //addResult = "ISBN EXISTS";
                } else {
                    int tmpIns = stmt.executeUpdate("insert into library (name,district,province,zipcode,tel) values('" + p_name + "','" + p_district + "','" + p_province + "','" + p_zipcode + "','" + p_tel + "') ");
                    if (tmpIns != -1) {
                        addResult = 1;
                        //addResult = "ADD COMPLETED";
                    } else {
                        addResult = 0;
                        //addResult = "ADD FAILED";
                    }
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return addResult;
    }

    @WebMethod(operationName = "systemLibraryDelete")
    public int systemLibraryDelete(@WebParam(name = "p_name") String p_name) {
        int deleteResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from library where name='" + p_name + "'");

                if (rsult.next()) {
                    int tmpDel = stmt.executeUpdate("delete from library where name='" + p_name + "' ");
                    if (tmpDel != -1) {
                        //deleteResult = "Delete Completed";
                        deleteResult = 1;
                    } else {
                        //deleteResult = "Delete Failed";
                        deleteResult = 0;
                    }
                } else {
                    //deleteResult = "No ISBN Found";
                    deleteResult = 9;
                }

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleteResult;
    }

    @WebMethod(operationName = "systemTableTruncated")
    public int systemTableTruncated(@WebParam(name = "p_tableName") String p_tableName) {
        int truncateResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();

                int truncateTable = stmt.executeUpdate("truncate table " + p_tableName + " ");
                if (truncateTable != -1) {
                    truncateResult = 1;
                    //addResult = "TRUNCATE COMPLETED";
                    if (p_tableName.equalsIgnoreCase("members")) {
                        int truncateMember = stmt.executeUpdate("truncate table member_picture ");
                        if (truncateMember != -1) {
                            truncateResult = 1;
                        } else {
                            truncateResult = 0;
                        }
                    }
                } else {
                    truncateResult = 0;
                    //"TRUNCATE FAILED";
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }

        return truncateResult;
    }

    @WebMethod(operationName = "memberPictureDelete")
    public int memberPictureDelete(@WebParam(name = "p_memberid") String p_memberid) {
        int deleteResult = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from members where member_cardID='" + p_memberid + "'");

                if (rsult.next()) {
                    int tmpDel = stmt.executeUpdate("delete from members where member_cardID='" + p_memberid + "' ");
                    if (tmpDel != -1) {
                        //deleteResult = "Delete Completed";
                        deleteResult = 1;
                        //------------------------------
                        rsult = stmt.executeQuery("select * from member_picture where member_id='" + p_memberid + "'");

                        if (rsult.next()) {
                            int tmpIMGDel = stmt.executeUpdate("delete from member_picture where member_id='" + p_memberid + "' ");
                            if (tmpIMGDel != -1) {
                                //deleteResult = "Delete Completed";
                                deleteResult = 1;
                            } else {
                                //deleteResult = "Delete Failed";
                                deleteResult = 0;
                            }
                        } else {
                            //deleteResult = "No ISBN Found";
                            deleteResult = 9;
                        }
                        //------------------------------
                    } else {
                        //deleteResult = "Delete Failed";
                        deleteResult = 0;
                    }
                } else {
                    //deleteResult = "No ISBN Found";
                    deleteResult = 9;
                }

                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return deleteResult;
    }

    @WebMethod(operationName = "libraryDailyWalkInNumber")
    public int libraryDailyWalkInNumber(@WebParam(name = "p_walkin_date") String p_walkin_date, @WebParam(name = "p_walkin_number") String p_walkin_number) {
        int addResult = 0;
        int tmpIns = 0;
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                rsult = stmt.executeQuery("select * from library_daily_walkin where walkin_date='" + p_walkin_date + "'");
                if (rsult.next()) {
                    //addResult = 9;
                    //addResult = "ISBN EXISTS";
                    tmpIns = stmt.executeUpdate("update library_daily_walkin  set walkin_number ='" + p_walkin_number + "'  where walkin_date='" + p_walkin_date + "'");
                    if (tmpIns != -1) {
                        addResult = 1;
                        //addResult = "update COMPLETED";
                    } else {
                        addResult = 0;
                        //addResult = "update FAILED";
                    }
                } else {
                    tmpIns = stmt.executeUpdate("insert into library_daily_walkin (walkin_date,walkin_number) values('" + p_walkin_date + "','" + p_walkin_number + "') ");
                    if (tmpIns != -1) {
                        addResult = 1;
                        //addResult = "ADD COMPLETED";
                    } else {
                        addResult = 0;
                        //addResult = "ADD FAILED";
                    }
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        return addResult;
    }

    @WebMethod(operationName = "libraryDailyWalkInList")
    public Object[][] libraryDailyWalkInList() {
        String walkinDateReturn = "";
        String walkinNumberReturn = "";

        String sql_sel = "";
        List<SubWalkin> walkins = new ArrayList<SubWalkin>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "SELECT * FROM library_daily_walkin order by substring(walkin_date,7,2) desc,substring(walkin_date,4,2) desc,substring(walkin_date,1,2)  desc";


                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {
                    walkinDateReturn = rsult.getString("walkin_date");
                    walkinNumberReturn = rsult.getString("walkin_number");

                    walkins.add(new SubWalkin(walkinDateReturn, walkinNumberReturn));
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }

        WalkinDailyClass lib = new WalkinDailyClass(walkins);
        Object rsultArray[][] = lib.WalkintoArray();

        return rsultArray;
    }

    @WebMethod(operationName = "MultimediaUpload")
    public int MultimediaUpload(
            @WebParam(name = "p_fileBase64") String p_fileBase64,
            @WebParam(name = "p_fileName") String p_fileName,
            @WebParam(name = "p_fileType") String p_fileType,
            @WebParam(name = "p_fileLength") long p_fileLength) {
        int addResult = 0;
        byte[] p_data = null;
        try {
            p_data = Base64.decodeBase64(p_fileBase64);
        } catch (Exception ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        File outFile = new File(uploaded_path + p_fileName);
        try {
            OutputStream outStream = new FileOutputStream(outFile);
            outStream.write(p_data);
            outStream.close();
            try {
                Class.forName(driver);
                try {
                    con = DriverManager.getConnection(url, dbusername, dbpassword);
                    stmt = con.createStatement();
                    rsult = stmt.executeQuery("select * from multimedia_file where multimedia_name='" + p_fileName + "'");
                    if (rsult.next()) {
                        //addResult = 9;
                        //addResult = "multimedia_file EXISTS";
                    } else {
                        if (!(p_fileBase64.equalsIgnoreCase(""))) {
                            PreparedStatement pre = con.prepareStatement("insert into multimedia_file (multimedia_name,multimedia_file_type,multimedia_length_byte) values(?,?,?)");
                            //  pre.setBinaryStream(1, is, (int) p_fileLength);
                            pre.setString(1, p_fileName);
                            pre.setString(2, p_fileType);
                            pre.setDouble(3, (int) p_fileLength);
                            int tmpIMGIns = pre.executeUpdate();
                            if (tmpIMGIns != -1) {
                                addResult = 1;
                                //addResult = "ADD COMPLETED";
                            } else {
                                addResult = 0;
                                //addResult = "ADD FAILED";
                            }
                            pre.close();
                        }
                    }
                    //---------------------


                    con.close();
                } catch (SQLException ex) {
                    Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }



        return addResult;
    }

    /**
     * Web service operation
     */
    /**
     * Web service operation
     */
    @WebMethod(operationName = "systemUserGroup")
    public String systemUserGroup(@WebParam(name = "p_username") String p_username) {

        String userGroupKeyReturn = "";
        String sql_sel = "";
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "select usergroup_key from app_user where username = '" + p_username + "'";

                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {
                    userGroupKeyReturn = rsult.getString("usergroup_key");
                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }


        return userGroupKeyReturn;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "MultimediaSearch")
    public Object[][] MultimediaSearch(@WebParam(name = "p_filename") String p_filename) {

        String fileIdReturn = "";
        String fileNameReturn = "";
        String fileTypeReturn = "";
        String fileLenReturn = "";

        String sql_sel = "";
        List<Multimedia> multimedias = new ArrayList<Multimedia>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "select multimedia_id,multimedia_name,multimedia_file_type,(multimedia_length_byte/1024/1024) as multimedia_length_mb from multimedia_file a where multimedia_name like '%" + p_filename + "%' order by  multimedia_name  ";
                System.out.println(sql_sel);
                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {
                    fileIdReturn = rsult.getString("multimedia_id");
                    fileNameReturn = rsult.getString("multimedia_name");
                    fileTypeReturn = rsult.getString("multimedia_file_type");
                    fileLenReturn = rsult.getString("multimedia_length_mb");
                    multimedias.add(new Multimedia(fileIdReturn, fileNameReturn, fileTypeReturn, fileLenReturn));

                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        MultimediaClass lib = new MultimediaClass(multimedias);
        Object rsultArray[][] = lib.ToArray();

        return rsultArray;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "MultimediaStream")
    public Object[][] MultimediaStream(@WebParam(name = "p_fileid") String p_fileid) {
        String fileIdReturn = "";
        String fileNameReturn = "";
        String fileTypeReturn = "";
        String fileLenReturn = "";
        byte[] fileBodyReturn;

        String sql_sel = "";
        List<Multimedia> multimedias = new ArrayList<Multimedia>();
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "select multimedia_id,multimedia_name,multimedia_body,multimedia_file_type,(multimedia_length_byte/1024/1024) as multimedia_length_mb from multimedia_file a where multimedia_id = '" + p_fileid + "'  ";
                System.out.println(sql_sel);
                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {
                    fileIdReturn = rsult.getString("multimedia_id");
                    fileNameReturn = rsult.getString("multimedia_name");
                    fileTypeReturn = rsult.getString("multimedia_file_type");
                    fileLenReturn = rsult.getString("multimedia_length_mb");
                    fileBodyReturn = rsult.getBytes("multimedia_body");
                    multimedias.add(new Multimedia(fileIdReturn, fileBodyReturn, fileNameReturn, fileTypeReturn, fileLenReturn));

                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }
        MultimediaClass lib = new MultimediaClass(multimedias);
        Object rsultArray[][] = lib.ToArray();

        return rsultArray;
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "MultimediaSteamByte")
    public byte[] MultimediaSteamByte(@WebParam(name = "p_fileid") String p_fileid) {

        byte[] fileBodyReturn = null;

        String sql_sel = "";
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, dbusername, dbpassword);
                stmt = con.createStatement();
                sql_sel = "select multimedia_body from multimedia_file a where multimedia_id = '" + p_fileid + "'  ";
                System.out.println(sql_sel);
                rsult = stmt.executeQuery(sql_sel);
                while (rsult.next()) {
                    fileBodyReturn = rsult.getBytes("multimedia_body");

                }
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LIBMATE_Webservice.class.getName()).log(Level.SEVERE, null, ex);
        }

        return fileBodyReturn;
    }
}
