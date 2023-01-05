package com.digicade.service.dto;

import com.digicade.domain.GameBadge;
import java.util.HashSet;
import java.util.Set;

public class UserProfileDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String phoneNumber;
    private String email;
    private String gender;
    private String imageUrl;
    private Integer level;
    private Integer xp;
    private int tix;
    private int comp;
    private int credit;
    private Set<GameBadge> gameBadges = new HashSet<>();

    public UserProfileDTO() {}

    public UserProfileDTO(
        String firstName,
        String lastName,
        String username,
        String phoneNumber,
        String email,
        String gender,
        String imageUrl,
        Integer xp,
        int tix,
        int comp,
        int credit
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.imageUrl = imageUrl;
        this.xp = xp;
        this.tix = tix;
        this.comp = comp;
        this.credit = credit;
        this.gameBadges = gameBadges;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getXp() {
        return xp;
    }

    public void setXp(Integer xp) {
        this.xp = xp;
    }

    public int getTix() {
        return tix;
    }

    public void setTix(int tix) {
        this.tix = tix;
    }

    public int getComp() {
        return comp;
    }

    public void setComp(int comp) {
        this.comp = comp;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public Set<GameBadge> getGameBadges() {
        return gameBadges;
    }

    public void setGameBadges(Set<GameBadge> gameBadges) {
        this.gameBadges = gameBadges;
    }
}
