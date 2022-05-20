package com.example.demo.src.follow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetFriendlyRes {
    private String friendlyProfileImageUrl;
    private String friendlyUserID;
    private String friendlyName;
    private String friendlyState;
}
