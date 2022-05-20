package com.example.demo.src.follow;


import com.example.demo.config.BaseException;
import com.example.demo.src.follow.model.*;
import com.example.demo.src.profile.ProfileProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class FollowProvider {
    private final FollowDao followDao;
    private final ProfileProvider profileProvider;

    @Autowired
    public FollowProvider(FollowDao followDao, ProfileProvider profileProvider) {
        this.followDao = followDao;
        this.profileProvider = profileProvider;
    }

    public List<GetFollowerRes> getFollower(String followerHost) throws BaseException{
        if(profileProvider.checkprofileUserID(followerHost)==1) {
            try {
                List<GetFollowerRes> getFollowerRes = followDao.getFollower(followerHost);
                return getFollowerRes;
            } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR);
            }
        } else {
            throw new BaseException(NON_EXISTENT_USER);
        }
    }

    public List<GetFollowingRes> getFollowing(String followingUserID) throws BaseException{
        if(profileProvider.checkprofileUserID(followingUserID)==1) {
            try {
                List<GetFollowingRes> getFollowingRes = followDao.getFollowing(followingUserID);
                return getFollowingRes;
            } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR);
            }
        } else {
                throw new BaseException(NON_EXISTENT_USER);
        }
    }

    public int checkFollow(PostFollowReq postFollowReq) throws BaseException{
        try {
            return followDao.checkFollow(postFollowReq);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkAddFollowing(PatchAddFriendlyReq patchAddFriendlyReq) throws BaseException{
        try {
            return followDao.checkAddFollowing(patchAddFriendlyReq);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkDelFollowing(PatchDelFriendlyReq patchDelFriendlyReq) throws BaseException{
        try {
            return followDao.checkDelFollowing(patchDelFriendlyReq);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkAddAlreadyFriendly(PatchAddFriendlyReq patchAddFriendlyReq) throws BaseException{
        try {
            return followDao.checkAddAlreadyFriendly(patchAddFriendlyReq);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkDelAlreadyFriendly(PatchDelFriendlyReq patchDelFriendlyReq) throws BaseException{
        try {
            return followDao.checkDelAlreadyFriendly(patchDelFriendlyReq);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetFollowCntRes> getFollowCnt(String profileUserID) throws BaseException {
        try {
            List<GetFollowCntRes> getFollowCntRes = followDao.getFollowCnt(profileUserID);
            return getFollowCntRes;
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetFriendlyRes> getFriendly(String followingHost) throws BaseException{
        if(profileProvider.checkprofileUserID(followingHost)==1) {
            try {
                List<GetFriendlyRes> getFriendlyRes = followDao.getFriendly(followingHost);
                return getFriendlyRes;
            } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR);
            }
        } else {
            throw new BaseException(NON_EXISTENT_USER);
        }
    }

    public List<GetFriendlyRecoRes> getFriendlyReco(String followingHost) throws BaseException{
        if(profileProvider.checkprofileUserID(followingHost)==1) {
            try {
                List<GetFriendlyRecoRes> getFriendlyRecoRes = followDao.getFriendlyreco(followingHost);
                return getFriendlyRecoRes;
            } catch (Exception exception){
                throw new BaseException(DATABASE_ERROR);
            }
        } else {
            throw new BaseException(NON_EXISTENT_USER);
        }
    }
}
