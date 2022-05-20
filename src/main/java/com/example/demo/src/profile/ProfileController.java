package com.example.demo.src.profile;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.profile.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/profile")
public class ProfileController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ProfileProvider profileProvider;
    @Autowired
    private final ProfileService profileService;
    @Autowired
    private final JwtService jwtService;


    public ProfileController(ProfileProvider profileProvider, ProfileService profileService, JwtService jwtService) {
        this.profileProvider = profileProvider;
        this.profileService = profileService;
        this.jwtService = jwtService;
    }

    /**
     * 회원 조회 API
     * [GET] /profile
     * @return BaseResponse<List<GetProfileRes>>
     */
    //Query String
    @ResponseBody
    @GetMapping("")
    public BaseResponse<List<GetProfileRes>> getProfiles() {
        try {
            List<GetProfileRes> getProfileRes = profileProvider.getProfiles();
            return new BaseResponse<>(getProfileRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 특정 유저 조회 API
     * [GET] /profile/:profileUserID
     * @return BaseResponse<GetProfileRes>
     */
    @ResponseBody
    @GetMapping("/{profileUserID}")
    public BaseResponse<GetProfileRes> getProfile(@PathVariable("profileUserID") String profileUserID) {
        try {
            GetProfileRes getProfileRes = profileProvider.getProfile(profileUserID);
            return new BaseResponse<>(getProfileRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원가입 API
     * [POST] /profile
     * @return BaseResponse<PostProfileRes>
     */
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostProfileRes> createProfile(@RequestBody PostProfileReq postProfileReq){
        if(postProfileReq.getProfileUserID() == null){
            return new BaseResponse<>(POST_PROFILE_EMPTY_ID);
        }
        if(postProfileReq.getProfileUserPW() == null){
            return new BaseResponse<>(POST_PROFILE_EMPTY_PW);
        }
        if(postProfileReq.getProfileEmail() == null){
            return new BaseResponse<>(POST_USERS_EMPTY_EMAIL);
        }
        try {
            PostProfileRes postProfileRes = profileService.createProfile(postProfileReq);
            return new BaseResponse<>(postProfileRes);
        } catch (BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 로그인 API
     * [POST] /profile/login
     * @return BaseResponse<PostLoginRes>
     */
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq){
        try {
            PostLoginRes postLoginRes = profileProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저 이름 변경 API
     * [PATCH] /profile/name/:profileUserID
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping ("/name/{profileUserID}")
    public BaseResponse<String> modifyProfileName(@PathVariable("profileUserID") String profileUserID, @RequestBody Profile profile){
        try {
            int userNoByJwt = jwtService.getUserNo();
            if(profileProvider.getNo(profileUserID).getUserNo() != userNoByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchProfileNameReq patchProfileNameReq = new PatchProfileNameReq(profileUserID, profile.getProfileName());
            profileService.modifyProfileName(patchProfileNameReq);

            String result = "변경되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저 프로필 링크 변경 API
     * [PATCH] /profile/link/:profileUserID
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/link/{profileUserID}")
    public BaseResponse<String> modifyProfileLink(@PathVariable("profileUserID") String profileUserID, @RequestBody Profile profile){
        try {
            int userNoByJwt = jwtService.getUserNo();
            if(profileProvider.getNo(profileUserID).getUserNo() != userNoByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchProfileLinkReq patchProfileLinkReq = new PatchProfileLinkReq(profileUserID, profile.getProfileLink());
            profileService.modifyProfileLink(patchProfileLinkReq);

            String result = "변경되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }

    /**
     * 유저 프로필 사진 변경 API
     * [PATCH] /profile/image/:profileUserID
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/image/{profileUserID}")
    public BaseResponse<String> modifyProfileImage(@PathVariable("profileUserID") String profileUserID, @RequestBody Profile profile){
        try {
            int userNoByJwt = jwtService.getUserNo();
            if(profileProvider.getNo(profileUserID).getUserNo() != userNoByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchProfileImageReq patchProfileImageReq = new PatchProfileImageReq(profileUserID, profile.getProfileImageUrl());
            profileService.modifyProfileImage(patchProfileImageReq);

            String result = "변경되었습니다";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }

    /**
     * 유저 카테고리 변경 API
     * [PATCH] /profile/category/:profileUserID
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/category/{profileUserID}")
    public BaseResponse<String> modifyProfileCategory(@PathVariable("profileUserID") String profileUserID, @RequestBody Profile profile){
        try {
            int userNoByJwt = jwtService.getUserNo();
            if(profileProvider.getNo(profileUserID).getUserNo() != userNoByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchProfileCategoryReq patchProfileCategoryReq = new PatchProfileCategoryReq(profileUserID, profile.getProfileCategory());
            profileService.modifyProfileCategory(patchProfileCategoryReq);

            String result = "변경되었습니다";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }

    /**
     * 유저 소개 내용 변경 API
     * [PATCH] /profile/content/:profileUserID
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/content/{profileUserID}")
    public BaseResponse<String> modifyProfileContent(@PathVariable("profileUserID") String profileUserID, @RequestBody Profile profile){
        try {
            int userNoByJwt = jwtService.getUserNo();
            if(profileProvider.getNo(profileUserID).getUserNo() != userNoByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchProfileContentReq patchProfileContentReq = new PatchProfileContentReq(profileUserID, profile.getProfileContent());
            profileService.modifyProfileContent(patchProfileContentReq);

            String result = "변경되었습니다";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }

    /**
     * 유저 패스워드 변경 API
     * [PATCH] /profile/pwd/:profileUserPW
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/pwd/{profileUserID}")
    public BaseResponse<String> modifyProfilePwd(@PathVariable("profileUserID") String profileUserID, @RequestBody Profile profile){
        try {
            int userNoByJwt = jwtService.getUserNo();
            if(profileProvider.getNo(profileUserID).getUserNo() != userNoByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PatchProfilePwdReq patchProfilePwdReq = new PatchProfilePwdReq(profileUserID, profile.getProfileUserPW());
            profileService.modifyProfilePwd(patchProfilePwdReq);

            String result = "변경되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저 프로필 전체 변경 API
     * [PUT] /app/profile/all/{profileUserID}
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PutMapping("/all/{profileUserID}")
    public BaseResponse<String> modifyProfileAll(@PathVariable("profileUserID") String profileUserID, @RequestBody Profile profile){
        try{
            int userNoByJwt = jwtService.getUserNo();
            if(profileProvider.getNo(profileUserID).getUserNo() != userNoByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            PutProfileReq putProfileReq = new PutProfileReq(profileUserID,
                                                            profile.getProfileImageUrl(),
                                                            profile.getProfileName(),
                                                            profile.getProfileCategory(),
                                                            profile.getProfileContent(),
                                                            profile.getProfileLink());
            profileService.modifyProfileAll(putProfileReq);

            String result = "변경되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException e) {
            return new BaseResponse<>((e.getStatus()));
        }
    }
}
