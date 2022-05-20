package com.example.demo.src.profile.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PutProfileReq {
    private String profileUserID;
    private String profileImageUrl;
    private String profileName;
    private String profileCategory;
    private String profileContent;
    private String profileLink;
}
