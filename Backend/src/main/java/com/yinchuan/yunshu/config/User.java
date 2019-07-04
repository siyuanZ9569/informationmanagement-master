package com.yinchuan.yunshu.config;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * User Object Class
 *
 * @author Siyuan Zeng
 *
 */

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /**
     * The id of the user. Automatically created when the user is created
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * The username of the user.
     */
    @Column(name = "USERNAME")
    private String username;

    /**
     * The password of the user
     */
    @Column(name = "PASSWORD")
    private String password;

    /**
     * The user type of the user
     * 1: Admin
     * 2: Buyer
     * 3: Deliver
     */
    @Column(name = "USERTYPE")
    private int userType;

    /**
     * The name of the user
     */
    @Column(name= "NICKNAME")
    private String nickname;

    /**
     * The company of the user
     */
    @Column(name= "COMPANY")
    private String companyName;

    /**
     * The location of the user
     */
    @Column(name= "LATITUDE")
    private Double latitude;

    /**
     * The location of the user
     */
    @Column(name= "LONGTITUDE")
    private double longtitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }
}
