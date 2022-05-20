package com.example.demo.src.follow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
public class Follower {
    private int followerNo;
    private String followerHost;
    private String followerUserID;
    private Timestamp followerCreatedAt;
}
