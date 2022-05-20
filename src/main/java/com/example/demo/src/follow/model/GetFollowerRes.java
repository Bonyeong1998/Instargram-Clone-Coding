package com.example.demo.src.follow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetFollowerRes {
    private String followerUserProfileImage;
    private String isStoryWatching;
    private String followerUserID;
    private String followerName;
    private String followerIsFollowing;
}
