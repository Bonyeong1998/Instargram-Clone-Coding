package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Profile {
    private int userNo;
    private String profileUserID;
    private String profileImageUrl;
    private String profileName;
    private String profileCategory;
    private String profileContent;
    private String profileLink;
    private String profileUserPW;
    private String profileisDeleted;
    private String profileActNow;
    private Timestamp profileLastActTime;
    private Timestamp profileCreatedAt;
    private Timestamp profileUpdatedAt;
}
