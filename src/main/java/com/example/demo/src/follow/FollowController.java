package com.example.demo.src.follow;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.follow.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;


@RestController
@RequestMapping("/app/follow")
public class FollowController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final FollowProvider followProvider;
    @Autowired
    private final FollowService followService;

    public FollowController(FollowProvider followProvider, FollowService followService) {
        this.followProvider = followProvider;
        this.followService = followService;
    }

    /**
     * 특정 유저 팔로워 목록 조회 API
     * [GET] /follow/follower/:followerHost
     * @return BaseResponse<List<GetFollowerRes>>
     */
    @ResponseBody
    @GetMapping("/follower/{followerHost}")
    public BaseResponse<List<GetFollowerRes>> getFollower(@PathVariable("followerHost") String followerHost){
        try {
            List<GetFollowerRes> getFollowerRes = followProvider.getFollower(followerHost);
            return new BaseResponse<>(getFollowerRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 유저 팔로잉 목록 조회 API
     * [GET] /follow/following/:followingUserID
     * @return BaseResponse<List<GetFollowingRes>>
     */
    @ResponseBody
    @GetMapping("/following/{followingUserID}")
    public BaseResponse<List<GetFollowingRes>> getFollowing(@PathVariable("followingUserID") String followingUserID){
        try {
            List<GetFollowingRes> getFollowingRes = followProvider.getFollowing(followingUserID);
            return new BaseResponse<>(getFollowingRes);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 신규 팔로우 API
     * [POST] /app/follow/newFollow
     * @return BaseResponse<PostFollowRes>
     */
    @ResponseBody
    @PostMapping("/newFollow")
    public BaseResponse<PostFollowRes> newFollow(@RequestBody PostFollowReq postFollowReq){

        if(postFollowReq.getFollowingHost() == null){
            return new BaseResponse<>(POST_NEWFOLLOW_EMPTY_FOLLOWINGHOST);
        }
        if(postFollowReq.getFollowingUserID() == null){
            return new BaseResponse<>(POST_NEWFOLLOW_EMPTY_FOLLOWINGUSERID);
        }
        try {
            PostFollowRes postFollowRes = followService.newFollow(postFollowReq);
            return new BaseResponse<>(postFollowRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 팔로우 취소 API
     * [POST] /app/follow/unfollow
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PostMapping("/unFollow")
    public BaseResponse<String> unFollow(@RequestBody PostFollowReq postFollowReq){
        if(postFollowReq.getFollowingHost() == null){
            return new BaseResponse<>(POST_NEWFOLLOW_EMPTY_FOLLOWINGHOST);
        }
        if(postFollowReq.getFollowingUserID() == null){
            return new BaseResponse<>(POST_NEWFOLLOW_EMPTY_FOLLOWINGUSERID);
        }
        try {
            followService.unFollow(postFollowReq);

            String result = "언팔로우 성공";
            return new BaseResponse<>(result);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 팔로워 팔로잉 수 조회 API
     * [GET] /app/follow/followcnt/:profileUserID
     * @return BaseResponse<List<GetFollowCntRes>>
     */
    @ResponseBody
    @GetMapping("/followcnt/{profileUserID}")
    public BaseResponse<List<GetFollowCntRes>> getFollowCnt(@PathVariable("profileUserID") String profileUserID){
        try {
            List<GetFollowCntRes> getFollowCnt = followProvider.getFollowCnt(profileUserID);
            return new BaseResponse<>(getFollowCnt);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 친한 친구 목록 조회 API
     * [GET] /app/follow/friendly/:followingHost
     * @return BaseResponse<List<GetFriendlyRes>>
     */
    @ResponseBody
    @GetMapping("/friendly/{followingHost}")
    public BaseResponse<List<GetFriendlyRes>> getFriendly(@PathVariable("followingHost") String followingHost){
        try {
            List<GetFriendlyRes> getFriendlyRes = followProvider.getFriendly(followingHost);
            return new BaseResponse<>(getFriendlyRes);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 친한 친구 추천 조회 API
     * [GET] /app/follow/friendlyreco/:followingHost
     * @return BaseResponse<List<GetFriendlyRecoRes>>
     */
    @ResponseBody
    @GetMapping("/friendlyreco/{followingHost}")
    public BaseResponse<List<GetFriendlyRecoRes>> getFriendlyReco(@PathVariable("followingHost") String followingHost){
        try {
            List<GetFriendlyRecoRes> getFriendlyRecoRes = followProvider.getFriendlyReco(followingHost);
            return new BaseResponse<>(getFriendlyRecoRes);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 친한 친구 추가 API
     * [PATCH] /app/follow/addfriendly/:followingHost
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/addfriendly/{followingHost}")
    public BaseResponse<String> addFriendly(@PathVariable("followingHost") String followingHost, @RequestBody Following following){
        try {
            PatchAddFriendlyReq patchAddFriendlyReq = new PatchAddFriendlyReq(followingHost, following.getFollowingUserID());
            followService.addFriendly(patchAddFriendlyReq);

            String result = "친한 친구 목록에 추가하였습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 친한 친구 삭제 API
     * [PATCH] /app/follow/delfriendly/:followingHost
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/delfriendly/{followingHost}")
    public BaseResponse<String> delFriendly(@PathVariable("followingHost") String followingHost, @RequestBody Following following){
        try {
            PatchDelFriendlyReq patchDelFriendlyReq = new PatchDelFriendlyReq(followingHost, following.getFollowingUserID());
            followService.delFriendly(patchDelFriendlyReq);

            String result = "친한 친구 목록에서 제거되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
