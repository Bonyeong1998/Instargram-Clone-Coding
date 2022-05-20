package com.example.demo.src.profile;

import com.example.demo.src.profile.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProfileDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) { this.jdbcTemplate = new JdbcTemplate(dataSource); }

    public List<GetProfileRes> getProfiles(){
        String getProfilesQuery = "select * from ProfileTable";
        return this.jdbcTemplate.query(getProfilesQuery,
                (rs, rowNum) -> new GetProfileRes(
                        rs.getInt("userNo"),
                        rs.getString("profileUserID"),
                        rs.getString("profileImageUrl"),
                        rs.getString("profileName"),
                        rs.getString("profileCategory"),
                        rs.getString("profileContent"),
                        rs.getString("profileLink"),
                        rs.getString("profileisDeleted"),
                        rs.getString("profileActNow"),
                        rs.getTimestamp("profileLastActTime"),
                        rs.getTimestamp("profileCreatedAt"),
                        rs.getTimestamp("profileUpdatedAt"))
                );
    }

    public GetProfileRes getProfile(String profileUserID){
        String getProfileQuery = "select * from ProfileTable where profileUserID = ?";
        String getProfileParams = profileUserID;
        return this.jdbcTemplate.queryForObject(getProfileQuery,
                (rs, rowNum) -> new GetProfileRes(
                        rs.getInt("userNo"),
                        rs.getString("profileUserID"),
                        rs.getString("profileImageUrl"),
                        rs.getString("profileName"),
                        rs.getString("profileCategory"),
                        rs.getString("profileContent"),
                        rs.getString("profileLink"),
                        rs.getString("profileisDeleted"),
                        rs.getString("profileActNow"),
                        rs.getTimestamp("profileLastActTime"),
                        rs.getTimestamp("profileCreatedAt"),
                        rs.getTimestamp("profileUpdatedAt")),
                getProfileParams);
    }

    public int createProfile(PostProfileReq postProfileReq){
        String createProfileQuery = "insert into ProfileTable(profileUserID, profileUserPW, profileEmail) VALUES(?, ?, ?)";
        Object[] createProfileParams = new Object[]{postProfileReq.getProfileUserID(),postProfileReq.getProfileUserPW(),postProfileReq.getProfileEmail()};
        this.jdbcTemplate.update(createProfileQuery, createProfileParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int modifyProfileUserName(PatchProfileNameReq patchProfileNameReq){
        String modifyProfileUserNameQuery = "update ProfileTable set profileName = ? where profileUserID = ?";
        Object[] modifyProfileUserNameParams =
                new Object[]{patchProfileNameReq.getProfileName(), patchProfileNameReq.getProfileUserID()};
        return this.jdbcTemplate.update(modifyProfileUserNameQuery,modifyProfileUserNameParams);
    }

    public int modifyChatRoomUserName(PatchProfileNameReq patchProfileNameReq){
        String modifyChatRoomUserNameQuery = "update ChatTable\n" +
                                             "set chatRoomUserName = ?\n" +
                                             "where chatRoomUserName = (select profileName\n" +
                                             "                          from (select profileName\n" +
                                             "                          from ProfileTable\n" +
                                             "                          where profileUserID = ?) tmp)";
        Object[] modifyChatRoomUserNameParams =
                new Object[]{patchProfileNameReq.getProfileName(), patchProfileNameReq.getProfileUserID()};
        return this.jdbcTemplate.update(modifyChatRoomUserNameQuery, modifyChatRoomUserNameParams);
    }

    public int modifyChatRoomUserNameAll(PutProfileReq putProfileReq){
        String modifyChatRoomUserNameAllQuery = "update ChatTable\n" +
                                                "set chatRoomUserName = ?\n" +
                                                "where chatRoomUserName = (select profileName\n" +
                                                "                          from (select profileName\n" +
                                                "                          from ProfileTable\n" +
                                                "                          where profileUserID = ?) tmp)";
        Object[] modifyChatRoomUserNameAllParams =
                new Object[]{putProfileReq.getProfileName(), putProfileReq.getProfileUserID()};
        return this.jdbcTemplate.update(modifyChatRoomUserNameAllQuery, modifyChatRoomUserNameAllParams);
    }

    public int checkChatRoomUserName(String chatTalker){
        String checkChatRoomUserNameQuery = "select exists(select chatTalker from ChatTable where chatTalker =?)";
        String checkChatRoomUserNameParam = chatTalker;
        return this.jdbcTemplate.queryForObject(checkChatRoomUserNameQuery,
                int.class,
                checkChatRoomUserNameParam);
    }

    public int checkprofileUserID(String profileUserID){
        String checkprofileUserIDQuery = "select exists(select profileUserID from ProfileTable where profileUserID =?)";
        String checkprofileUserIDParam = profileUserID;
        return this.jdbcTemplate.queryForObject(checkprofileUserIDQuery,
                int.class,
                checkprofileUserIDParam);
    }

    public int checkprofileEmail(String profileEmail){
        String checkprofileEmailQuery = "select exists(select profileEmail from ProfileTable where profileEmail = ?)";
        String checkprofileEmailParam = profileEmail;
        return this.jdbcTemplate.queryForObject(checkprofileEmailQuery,
                int.class,
                checkprofileEmailParam);
    }

    public int modifyProfileLink(PatchProfileLinkReq patchProfileLinkReq){
        String modifyProfileLinkQuery = "update ProfileTable set profileLink = ? where profileUserID = ?";
        Object[] modifyProfileLinkParams =
                new Object[]{patchProfileLinkReq.getProfileLink(), patchProfileLinkReq.getProfileUserID()};
        return this.jdbcTemplate.update(modifyProfileLinkQuery, modifyProfileLinkParams);
    }

    public int modifyProfileImage(PatchProfileImageReq patchProfileImageReq){
        String modifyProfileImageQuery = "update ProfileTable set profileImageUrl = ? where profileUserID = ?";
        Object[] modifyProfileImageParams =
                new Object[]{patchProfileImageReq.getProfileImageUrl(), patchProfileImageReq.getProfileUserID()};
        return this.jdbcTemplate.update(modifyProfileImageQuery, modifyProfileImageParams);
    }

    public int modifyProfileCategory(PatchProfileCategoryReq patchProfileCategoryReq){
        String modifyProfileCategoryQuery = "update ProfileTable set profileCategory = ? where profileUserID = ?";
        Object[] modifyProfileCategoryParams =
                new Object[]{patchProfileCategoryReq.getProfileCategory(), patchProfileCategoryReq.getProfileUserID()};
        return this.jdbcTemplate.update(modifyProfileCategoryQuery, modifyProfileCategoryParams);
    }

    public int modifyProfileContent(PatchProfileContentReq patchProfileContentReq){
        String modifyProfileContentQuery = "update ProfileTable set profileContent = ? where profileUserID = ?";
        Object[] modifyProfileContentParams =
                new Object[]{patchProfileContentReq.getProfileContent(), patchProfileContentReq.getProfileUserID()};
        return this.jdbcTemplate.update(modifyProfileContentQuery, modifyProfileContentParams);
    }

    public int modifyProfilePwd(PatchProfilePwdReq patchProfilePwdReq){
        String modifyProfilePwdQuery = "update ProfileTable set profileUserPW = ? where profileUserID = ?";
        Object[] modifyProfilePwdParams =
                new Object[]{patchProfilePwdReq.getProfileUserPW(), patchProfilePwdReq.getProfileUserID()};
        return this.jdbcTemplate.update(modifyProfilePwdQuery, modifyProfilePwdParams);
    }

    public int modifyProfileAll(PutProfileReq putProfileReq){
        String modifyProfileAllQuery = "update ProfileTable\n" +
                                       "set profileImageUrl = ?,\n" +
                                       "    profileName = ?,\n" +
                                       "    profileCategory = ?,\n" +
                                       "    profileContent = ?,\n" +
                                       "    profileLink =?\n" +
                                       "where profileUserID = ?";
        Object[] modifyProfileAllParams =
                new Object[]{putProfileReq.getProfileImageUrl(),
                             putProfileReq.getProfileName(),
                             putProfileReq.getProfileCategory(),
                             putProfileReq.getProfileContent(),
                             putProfileReq.getProfileLink(),
                             putProfileReq.getProfileUserID()};
        return this.jdbcTemplate.update(modifyProfileAllQuery, modifyProfileAllParams);
    }

    public Profile getPwd(PostLoginReq postLoginReq){
        String getPwdQuery = "select * from ProfileTable where profileUserID = ?";
        String getPwdParams = postLoginReq.getProfileUserID();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs, rowNum) -> new Profile(
                        rs.getInt("userNo"),
                        rs.getString("profileUserID"),
                        rs.getString("profileImageUrl"),
                        rs.getString("profileName"),
                        rs.getString("profileCategory"),
                        rs.getString("profileContent"),
                        rs.getString("profileLink"),
                        rs.getString("profileUserPW"),
                        rs.getString("profileisDeleted"),
                        rs.getString("profileActNow"),
                        rs.getTimestamp("profileLastActTime"),
                        rs.getTimestamp("profileCreatedAt"),
                        rs.getTimestamp("profileUpdatedAt")),
                getPwdParams
        );
    }

    public GetProfileNoRes getNo(String profileUserID){
        String getNoQuery = "select userNo from ProfileTable where profileUserID = ?";
        String getNoParams = profileUserID;
        return this.jdbcTemplate.queryForObject(getNoQuery,
                (rs, rowNum) -> new GetProfileNoRes(
                        rs.getInt("userNo")),
                getNoParams);
    }
}
