package com.example.demo.src.follow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetFollowingRes {
    private String followingUserProfileImage;
    private String isStoryWatching;
    private String followingUserID;
    private String followingName;
}
