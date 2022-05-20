package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.src.profile.model.*;
import com.example.demo.utils.JwtService;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ProfileProvider {

    private final ProfileDao profileDao;
    private final JwtService jwtService;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProfileProvider(ProfileDao profileDao, JwtService jwtService) {
        this.profileDao = profileDao;
        this.jwtService = jwtService;
    }

    public List<GetProfileRes> getProfiles() throws BaseException{
        try{
            List<GetProfileRes> getProfileRes = profileDao.getProfiles();
            return getProfileRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public GetProfileRes getProfile(String profileUserID) throws BaseException{
        try {
            GetProfileRes getProfileRes = profileDao.getProfile(profileUserID);
            return getProfileRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkChatRoomUserName(String profileUserID) throws BaseException{
        try{
            return profileDao.checkChatRoomUserName(profileUserID);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkprofileUserID(String profileUserID) throws BaseException{
        try{
            return profileDao.checkprofileUserID(profileUserID);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException{
        Profile profile = profileDao.getPwd(postLoginReq);
        String encryptPwd;
        if(profile.getProfileisDeleted().equals("N")){
            try {
                encryptPwd=new SHA256().encrypt(postLoginReq.getProfileUserPW());
            } catch (Exception ignored) {
                throw new BaseException(PASSWORD_DECRYPTION_ERROR);
            }

            if (profile.getProfileUserPW().equals(encryptPwd)){
                int userNo = profile.getUserNo();
                String jwt = jwtService.createJwt(userNo);
                return new PostLoginRes(userNo, jwt);
            } else {
                throw new BaseException(FAILED_TO_LOGIN);
            }
        } else {
            throw new BaseException(DELETE_USER);
        }
    }

    public GetProfileNoRes getNo(String profileUserID) throws BaseException{
        try {
            return profileDao.getNo(profileUserID);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
