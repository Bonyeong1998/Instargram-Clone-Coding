package com.example.demo.src.follow;

import com.example.demo.config.BaseException;
import com.example.demo.src.follow.model.PatchAddFriendlyReq;
import com.example.demo.src.follow.model.PatchDelFriendlyReq;
import com.example.demo.src.follow.model.PostFollowReq;
import com.example.demo.src.follow.model.PostFollowRes;
import com.example.demo.src.profile.ProfileProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class FollowService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FollowDao followDao;
    private final FollowProvider followProvider;
    private final ProfileProvider profileProvider;

    @Autowired
    public FollowService(FollowDao followDao, FollowProvider followProvider, ProfileProvider profileProvider) {
        this.followDao = followDao;
        this.followProvider = followProvider;
        this.profileProvider = profileProvider;
    }

    public PostFollowRes newFollow(PostFollowReq postFollowReq) throws BaseException{
        if(profileProvider.checkprofileUserID(postFollowReq.getFollowingUserID())==1){
            if (followProvider.checkFollow(postFollowReq)==1){
                throw new BaseException(ALREADY_FOLLOWING);
            }
            PostFollowRes showResult = followDao.newFollow(postFollowReq);
            return new PostFollowRes(showResult.getFollowingHost(), showResult.getFollowingUserID());
        } else {
            throw new BaseException(NON_EXISTENT_USER);
        }
    }

    @Transactional
    public void unFollow(PostFollowReq postFollowReq) throws BaseException{
        if(profileProvider.checkprofileUserID(postFollowReq.getFollowingUserID())==1){
            if (followProvider.checkFollow(postFollowReq)==0){
                throw new BaseException(NOT_FOLLOW_USER);
            }
            int resultUnFollowing = followDao.unFollowing(postFollowReq);
            if (resultUnFollowing == 0){
                throw new BaseException(DELETE_FAIL_UNFOLLOW);
            }
            int resultUnFollower = followDao.unFollower(postFollowReq);
            if (resultUnFollower == 0){
                throw new BaseException(DELETE_FAIL_UNFOLLOW);
            }
        } else {
            throw new BaseException(NON_EXISTENT_USER);
        }
    }

    public void addFriendly(PatchAddFriendlyReq patchAddFriendlyReq) throws BaseException{
        if(profileProvider.checkprofileUserID(patchAddFriendlyReq.getFollowingHost())==1){
            if(followProvider.checkAddFollowing(patchAddFriendlyReq)==1){
                if(followProvider.checkAddAlreadyFriendly(patchAddFriendlyReq)==0){
                    try {
                        int resultAddFriendly = followDao.addFriendly(patchAddFriendlyReq);
                        if(resultAddFriendly == 0){
                            throw new BaseException(ADD_FAIL_FRIENDLY);
                        }
                    } catch (Exception exception){
                        throw new BaseException(DATABASE_ERROR);
                    }
                } else {
                    throw new BaseException(ALREADY_FRIENDLY);
                }
            } else {
                throw new BaseException(NOT_FOLLOW_USER);
            }
        } else {
            throw new BaseException(NON_EXISTENT_USER);
        }
    }

    public void delFriendly(PatchDelFriendlyReq patchDelFriendlyReq) throws BaseException{
        if(profileProvider.checkprofileUserID(patchDelFriendlyReq.getFollowingHost())==1){
            if(followProvider.checkDelFollowing(patchDelFriendlyReq)==1){
                if(followProvider.checkDelAlreadyFriendly(patchDelFriendlyReq)==0){
                    try {
                        int resultDelFriendly = followDao.delFriendly(patchDelFriendlyReq);
                        if(resultDelFriendly == 0){
                            throw new BaseException(DEL_FAIL_FRIENDLY);
                        }
                    } catch (Exception exception){
                        throw new BaseException(DATABASE_ERROR);
                    }
                } else {
                    throw new BaseException(ALREADY_NOT_FRIENDLY);
                }
            } else {
                throw new BaseException(NOT_FOLLOW_USER);
            }
        } else {
            throw new BaseException(NON_EXISTENT_USER);
        }
    }
}
