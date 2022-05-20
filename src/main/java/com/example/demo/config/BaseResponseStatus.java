package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // profile
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /profile
    POST_PROFILE_EMPTY_ID(false, 2011, "아이디를 입력해주세요."),
    POST_PROFILE_EXISTS_ID(false, 2012, "중복된 아이디입니다."),
    POST_PROFILE_EMPTY_PW(false, 2013, "비밀번호를 입력해주세요."),

    // [POST] / User
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),

    // [POST] /follow/newFollow
    POST_NEWFOLLOW_EMPTY_FOLLOWINGUSERID(false, 2018, "팔로잉 대상을 적어주세요"),
    POST_NEWFOLLOW_EMPTY_FOLLOWINGHOST(false,2019, "호스트 이름을 적어주세요"),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),
    //non-existent user
    NON_EXISTENT_USER(false, 3001, "존재하지 않는 사용자입니다."),
    //이미 팔로잉중
    ALREADY_FOLLOWING(false, 3002, "이미 팔로잉중입니다."),
    NOT_FOLLOW_USER(false, 3003, "팔로우관계가 아닙니다."),
    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),


    //[PATCH] /profile/name/{profileUserID}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),
    MODIFY_FAIL_USERNAME_CHAT(false, 4015,"채팅방 유저 네임 수정 실패."),

    //[PATCH]  /profile/link/{profileUserID}
    MODIFY_FAIL_LINK(false, 4016, "프로필 링크 수정 실패"),

    //[PATCH] /profile/image/{profileUserID}
    MODIFY_FAIL_IMAGE(false, 4017, "프로필 이미지 수정 실패"),

    //[PATCH] /profile/category/{profileUserID}
    MODIFY_FAIL_CATEGORY(false, 4018, "프로필 카테고리 수정 실패"),

    //[PATCH] /profile/content/{profileUserID}
    MODIFY_FAIL_CONTENT(false, 4019, "프로필 소개 내용 수정 실패"),

    //[PATCH] /profile/pwd/{profileUserID}
    MODIFY_FAIL_PASSWORD(false, 4019, "프로필 소개 내용 수정 실패"),

    //[PATCH] /profile/all/{profileUserID}
    MODIFY_FAIL_ALL(false, 4020, "프로필 변경 실패"),

    //[POST] /follow/unfollow
    DELETE_FAIL_UNFOLLOW(false, 4021, "언팔로우 실패"),
    //[PATCH] /follow/addfriendly/{followingHost}
    ADD_FAIL_FRIENDLY(false, 4022, "친한 친구 추가 실패"),
    ALREADY_FRIENDLY(false, 4023, "이미 친한 친구입니다."),

    //[PATCH] /follow/delfriendly/{followingHost}
    DEL_FAIL_FRIENDLY(false, 4024, "친한 친구 삭제 실패"),
    ALREADY_NOT_FRIENDLY(false, 4025, "친한 친구가 아닙니다."),

    DELETE_USER(false, 4026,"탈퇴한회원입니다."),
    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
