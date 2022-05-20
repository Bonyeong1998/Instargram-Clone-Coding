package com.example.demo.src.follow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Following {
    private int followingNo;
    private String followingHost;
    private String followingUserID;
    private Timestamp followingCreatedAt;
    private String followingFriendly;
}
