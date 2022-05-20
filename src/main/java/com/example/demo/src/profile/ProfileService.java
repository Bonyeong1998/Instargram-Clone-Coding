package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.src.profile.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ProfileService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProfileDao profileDao;
    private final ProfileProvider profileProvider;
    private final JwtService jwtService;

    @Autowired
    public ProfileService(ProfileDao profileDao, ProfileProvider profileProvider, JwtService jwtService) {
        this.profileDao = profileDao;
        this.profileProvider = profileProvider;
        this.jwtService = jwtService;
    }

    public PostProfileRes createProfile(PostProfileReq postProfileReq) throws BaseException{

        if(profileProvider.checkprofileUserID(postProfileReq.getProfileUserID())==1){
            throw new BaseException(POST_PROFILE_EXISTS_ID);
        }

        if(profileProvider.checkprofileEmail(postProfileReq.getProfileEmail())==1){
            throw new BaseException(POST_USERS_EXISTS_EMAIL);
        }

        String pwd;
        try {
            pwd = new SHA256().encrypt(postProfileReq.getProfileUserPW());
            postProfileReq.setProfileUserPW(pwd);
        } catch (Exception ignored){
            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
        }
        try {
            int userNo = profileDao.createProfile(postProfileReq);

            System.out.println(pwd);
            String jwt = jwtService.createJwt(userNo);
            return new PostProfileRes(jwt, userNo);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public void modifyProfileName(PatchProfileNameReq patchProfileNameReq) throws BaseException{
        if(profileProvider.checkprofileUserID(patchProfileNameReq.getProfileUserID())==1){
            if(profileProvider.checkChatRoomUserName(patchProfileNameReq.getProfileUserID())==1){
                try {
                    int resultChat = profileDao.modifyChatRoomUserName(patchProfileNameReq);
                    if(resultChat == 0){
                        throw new BaseException(MODIFY_FAIL_USERNAME_CHAT);
                    }
                } catch (Exception exception) {
                    throw new BaseException(DATABASE_ERROR);
                }
            }
            try {
                int resultProfileName = profileDao.modifyProfileUserName(patchProfileNameReq);
                if(resultProfileName == 0) {
                    throw new BaseException(MODIFY_FAIL_USERNAME);
                }
            } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR);
            }
        } else {
            throw new BaseException(NON_EXISTENT_USER);
        }
    }

    public void modifyProfileLink(PatchProfileLinkReq patchProfileLinkReq) throws BaseException{
        if(profileProvider.checkprofileUserID(patchProfileLinkReq.getProfileUserID())==1){
            try {
                int resultProfileLink = profileDao.modifyProfileLink(patchProfileLinkReq);
                if(resultProfileLink == 0){
                    throw new BaseException(MODIFY_FAIL_LINK);
                }
            } catch (Exception exception){
                throw new BaseException(DATABASE_ERROR);
            }
        } else {
            throw new BaseException(NON_EXISTENT_USER);
        }
    }

    public void modifyProfileImage(PatchProfileImageReq patchProfileImageReq) throws BaseException{
        if(profileProvider.checkprofileUserID(patchProfileImageReq.getProfileUserID())==1){
            try {
                int resultProfileImage = profileDao.modifyProfileImage(patchProfileImageReq);
                if(resultProfileImage ==0){
                    throw new BaseException(MODIFY_FAIL_IMAGE);
                }
            } catch (Exception exception){
                throw new BaseException(DATABASE_ERROR);
            }
        } else {
            throw new BaseException(NON_EXISTENT_USER);
        }
    }

    public void modifyProfileCategory(PatchProfileCategoryReq patchProfileCategoryReq) throws BaseException{
        if(profileProvider.checkprofileUserID(patchProfileCategoryReq.getProfileUserID())==1){
            try{
                int resultProfileCategory = profileDao.modifyProfileCategory(patchProfileCategoryReq);
                if (resultProfileCategory == 0){
                    throw new BaseException(MODIFY_FAIL_CATEGORY);
                }
            } catch (Exception exception){
                throw new BaseException(DATABASE_ERROR);
            }
        } else {
            throw new BaseException(NON_EXISTENT_USER);
        }
    }

    public void modifyProfileContent(PatchProfileContentReq patchProfileContentReq) throws BaseException{
        if(profileProvider.checkprofileUserID(patchProfileContentReq.getProfileUserID())==1){
            try{
                int resultProfileContent = profileDao.modifyProfileContent(patchProfileContentReq);
                if (resultProfileContent == 0){
                    throw new BaseException(MODIFY_FAIL_CONTENT);
                }
            } catch (Exception exception){
                throw new BaseException(DATABASE_ERROR);
            }
        } else {
            throw new BaseException(NON_EXISTENT_USER);
        }
    }

    public void modifyProfilePwd(PatchProfilePwdReq patchProfilePwdReq) throws BaseException{
        if(profileProvider.checkprofileUserID(patchProfilePwdReq.getProfileUserID())==1){
            String pwd;
            try{
                pwd = new SHA256().encrypt(patchProfilePwdReq.getProfileUserPW());
                patchProfilePwdReq.setProfileUserPW(pwd);
            } catch (Exception ignored){
                throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
            }
            try {
                int resultProfilePwd = profileDao.modifyProfilePwd(patchProfilePwdReq);
                if (resultProfilePwd == 0){
                    throw new BaseException(MODIFY_FAIL_PASSWORD);
                }
            } catch (Exception exception){
                throw new BaseException(DATABASE_ERROR);
            }
        } else {
            throw new BaseException(NON_EXISTENT_USER);
        }
    }


    public void modifyProfileAll(PutProfileReq putProfileReq) throws BaseException{
        if(profileProvider.checkprofileUserID(putProfileReq.getProfileUserID())==1) {
            if (profileProvider.checkChatRoomUserName(putProfileReq.getProfileUserID())==1){
                try {
                    int resultChat = profileDao.modifyChatRoomUserNameAll(putProfileReq);
                    if(resultChat == 0){
                        throw new BaseException(MODIFY_FAIL_USERNAME_CHAT);
                    }
                } catch (Exception exception) {
                    throw new BaseException(DATABASE_ERROR);
                }
            }
            try{
                int resultAll = profileDao.modifyProfileAll(putProfileReq);
                if (resultAll == 0){
                    throw new BaseException(MODIFY_FAIL_ALL);
                }
            } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR);
            }
        } else {
            throw new BaseException(NON_EXISTENT_USER);
        }
    }
}
