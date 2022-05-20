package com.example.demo.src.follow;

import com.example.demo.src.follow.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FollowDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetFollowerRes> getFollower(String followerHost){
        String getFollowerQuery = "select profileImageUrl as followerUserProfileImage,\n" +
                                  "       if(followerUserID in (select storyUserID\n" +
                                  "                             from StoryTable\n" +
                                  "                             where timestampdiff(day, storyCreatedAt, CURRENT_TIMESTAMP) < 1\n" +
                                  "                               and 'No' =\n" +
                                  "                                   if(storyNo in (select storyWatchingStoryNo\n" +
                                  "                                                  from StoryWatchingTable\n" +
                                  "                                                  where storyWatchingUserID = ?),\n" +
                                  "                                      'Yes', 'No')),\n" +
                                  "          'No', 'Yes') as isStoryWatching,\n" +
                                  "       followerUserID,\n" +
                                  "       profileName as followerName,\n" +
                                  "       '팔로우' as followerIsFollowing\n" +
                                  "from (select *\n" +
                                  "      from FollowerTable\n" +
                                  "      where followerHost = ?) as followerBonyeong inner join ProfileTable on profileUserID = followerUserID\n" +
                                  "where followerUserID not in (select followerHost\n" +
                                  "                             from FollowerTable\n" +
                                  "                             where followerUserID = ?)\n" +
                                  "union\n" +
                                  "select profileImageUrl as followerUserProfileImage,\n" +
                                  "       if(followerUserID in (select storyUserID\n" +
                                  "                             from StoryTable\n" +
                                  "                             where timestampdiff(day, storyCreatedAt, CURRENT_TIMESTAMP) < 1\n" +
                                  "                               and 'No' =\n" +
                                  "                                   if(storyNo in (select storyWatchingStoryNo\n" +
                                  "                                                  from StoryWatchingTable\n" +
                                  "                                                  where storyWatchingUserID = ?),\n" +
                                  "                                      'Yes', 'No')),\n" +
                                  "          'No', 'Yes') as isStoryWatching,\n" +
                                  "       followerUserID,\n" +
                                  "       profileName as followerName,\n" +
                                  "       '' as followerIsFollowing\n" +
                                  "from (select followerUserID\n" +
                                  "      from FollowerTable\n" +
                                  "      where followerHost = ?) as followerBonyeong inner join ProfileTable on profileUserID = followerUserID\n" +
                                  "where followerUserID in (select followerHost\n" +
                                  "                         from FollowerTable\n" +
                                  "                         where followerUserID = ?)";
        Object[] getFollowerParams = new Object[]{followerHost,followerHost,followerHost,followerHost,followerHost,followerHost};

        return this.jdbcTemplate.query(getFollowerQuery,
                (rs, rowNum) -> new GetFollowerRes(
                        rs.getString("followerUserProfileImage"),
                        rs.getString("isStoryWatching"),
                        rs.getString("followerUserID"),
                        rs.getString("followerName"),
                        rs.getString("followerIsFollowing")),
                getFollowerParams);
    }

    public List<GetFollowingRes> getFollowing(String followingUserID){
        String getFollowingQuery = "select profileImageUrl as followingUserProfileImage,\n" +
                                   "       if(followerHost in (select storyUserID\n" +
                                   "                           from StoryTable\n" +
                                   "                           where timestampdiff(day, storyCreatedAt, CURRENT_TIMESTAMP) < 1\n" +
                                   "                                 and 'No' = if(storyNo in (select storyWatchingStoryNo\n" +
                                   "                                                           from StoryWatchingTable\n" +
                                   "                                                           where storyWatchingUserID = ?),\n" +
                                   "                                               'Yes', 'No')),\n" +
                                   "          'No', 'Yes') as isStoryWatching,\n" +
                                   "       followerHost    as followingUserID,\n" +
                                   "       profileName     as followingName\n" +
                                   "from FollowerTable inner join ProfileTable on profileUserID = followerHost\n" +
                                   "where followerUserID = ?";
        Object[] getFollowingParams = new Object[]{followingUserID, followingUserID};

        return this.jdbcTemplate.query(getFollowingQuery,
                (rs, rowNum) -> new GetFollowingRes(
                        rs.getString("followingUserProfileImage"),
                        rs.getString("isStoryWatching"),
                        rs.getString("followingUserID"),
                        rs.getString("followingName")),
                getFollowingParams);
    }

    public int checkFollow(PostFollowReq postFollowReq){
        String checkFollowQuery = "select exists(select followingUserID\n" +
                                  "              from FollowingTable\n" +
                                  "              where followingHost = ? and followingUserID = ?)";
        Object[] checkFollowParams = new Object[]{postFollowReq.getFollowingHost(), postFollowReq.getFollowingUserID()};
        return this.jdbcTemplate.queryForObject(checkFollowQuery,
                int.class,
                checkFollowParams);
    }

    public int checkAddFollowing(PatchAddFriendlyReq patchAddFriendlyReq){
        String checkFollowingQuery = "select exists(select followingUserID\n" +
                                     "              from FollowingTable\n" +
                                     "              where followingHost = ? and followingUserID = ?)";
        Object[] checkFollowingParams = new Object[]{patchAddFriendlyReq.getFollowingHost(), patchAddFriendlyReq.getFollowingUserID()};
        return this.jdbcTemplate.queryForObject(checkFollowingQuery,
                int.class,
                checkFollowingParams);
    }

    public int checkDelFollowing(PatchDelFriendlyReq patchDelFriendlyReq){
        String checkDelFollowingQuery = "select exists(select followingUserID\n" +
                                        "              from FollowingTable\n" +
                                        "              where followingHost = ? and followingUserID = ?)";
        Object[] checkDelFollowingParams = new Object[]{patchDelFriendlyReq.getFollowingHost(), patchDelFriendlyReq.getFollowingUserID()};
        return this.jdbcTemplate.queryForObject(checkDelFollowingQuery,
                int.class,
                checkDelFollowingParams);
    }

    public int checkAddAlreadyFriendly(PatchAddFriendlyReq patchAddFriendlyReq){
        String checkAlreadyFriendlyQuery = "select exists(select followingUserID\n" +
                                           "              from FollowingTable\n" +
                                           "              where followingHost = ?\n" +
                                           "                and followingUserID = ?\n" +
                                           "                and followingFriendly = 'Y')";
        Object[] checkAlreadyFriendlyParams =
                new Object[]{patchAddFriendlyReq.getFollowingHost(), patchAddFriendlyReq.getFollowingUserID()};
        return this.jdbcTemplate.queryForObject(checkAlreadyFriendlyQuery,
                int.class,
                checkAlreadyFriendlyParams);
    }

    public int checkDelAlreadyFriendly(PatchDelFriendlyReq patchDelFriendlyReq){
        String checkDelAlreadyFriendlyQuery = "select exists(select followingUserID\n" +
                                              "              from FollowingTable\n" +
                                              "              where followingHost = ?\n" +
                                              "                and followingUserID = ?\n" +
                                              "                and followingFriendly = 'N')";
        Object[] checkDelAlreadyFriendlyParams =
                new Object[]{patchDelFriendlyReq.getFollowingHost(),patchDelFriendlyReq.getFollowingUserID()};
        return this.jdbcTemplate.queryForObject(checkDelAlreadyFriendlyQuery,
                int.class,
                checkDelAlreadyFriendlyParams);
    }

    @Transactional
    public PostFollowRes newFollow(PostFollowReq postFollowReq){
        String newFollowingQuery = "insert into FollowingTable(followingHost, followingUserID) VALUES (?,?)";
        Object[] newFollowingParams = new Object[]{postFollowReq.getFollowingHost(), postFollowReq.getFollowingUserID()};
        this.jdbcTemplate.update(newFollowingQuery, newFollowingParams);

        String newFollowerQuery = "insert into FollowerTable(followerHost, followerUserID) VALUES (?,?)";
        Object[] newFollowerParams = new Object[]{postFollowReq.getFollowingUserID(), postFollowReq.getFollowingHost()};
        this.jdbcTemplate.update(newFollowerQuery, newFollowerParams);

        String showResultQuery = "select followingHost, followingUserID\n" +
                                 "from FollowingTable\n" +
                                 "where followingHost = ? and followingUserID = ?";
        Object[] showResultParams = new Object[]{postFollowReq.getFollowingHost(), postFollowReq.getFollowingUserID()};

        return this.jdbcTemplate.queryForObject(showResultQuery,
                (rs, rowNum) -> new PostFollowRes(
                        rs.getString("followingHost"),
                        rs.getString("followingUserID")),
                showResultParams);
    }

    public int unFollowing(PostFollowReq postFollowReq){
        String unFollowingQuery = "delete FROM FollowingTable where followingHost = ? and followingUserID = ?";
        Object[] unFollowingParams = new Object[]{postFollowReq.getFollowingHost(), postFollowReq.getFollowingUserID()};

        return this.jdbcTemplate.update(unFollowingQuery, unFollowingParams);
    }

    public int unFollower(PostFollowReq postFollowReq){
        String unFollowerQuery = "delete From FollowerTable where followerHost = ? and followerUserID = ?";
        Object[] unFollowerParams = new Object[]{postFollowReq.getFollowingUserID(), postFollowReq.getFollowingHost()};

        return this.jdbcTemplate.update(unFollowerQuery, unFollowerParams);
    }

    public List<GetFollowCntRes> getFollowCnt(String profileUserID){
        String getFollowCntQuery = "select (select count(followerNo)\n" +
                                   "        from FollowerTable\n" +
                                   "        where followerHost = ?) as followerCnt,\n" +
                                   "       (select count(followerHost)\n" +
                                   "        from FollowerTable\n" +
                                   "        where followerUserID = ?) as followingCnt";
        Object[] getFollowCntParams = new Object[]{profileUserID, profileUserID};

        return this.jdbcTemplate.query(getFollowCntQuery,
                (rs, rowNum) -> new GetFollowCntRes(
                        rs.getInt("followerCnt"),
                        rs.getInt("followingCnt")),
                getFollowCntParams);
    }

    public List<GetFriendlyRes> getFriendly(String followingHost){
        String getFriendlyQuery = "select profileImageUrl as friendlyProfileImageUrl,\n" +
                                  "       followingUserID as friendlyUserID,\n" +
                                  "       profileName as friendlyName,\n" +
                                  "       followingFriendly as friendlyState\n" +
                                  "from FollowingTable inner join ProfileTable on profileUserID = followingUserID\n" +
                                  "where followingFriendly='Y' and followingHost = ?";
        String getFriendlyParams = followingHost;
        return this.jdbcTemplate.query(getFriendlyQuery,
                (rs, rowNum) -> new GetFriendlyRes(
                        rs.getString("friendlyProfileImageUrl"),
                        rs.getString("friendlyUserID"),
                        rs.getString("friendlyName"),
                        rs.getString("friendlyState")),
                getFriendlyParams);
    }

    public List<GetFriendlyRecoRes> getFriendlyreco(String followingHost){
        String getFriendlyrecoQuery = "select profileImageUrl as recoFriendlyProfileImageUrl,\n" +
                                      "       followingUserID as recoFriendlyUserID,\n" +
                                      "       profileName as recoFriendlyName,\n" +
                                      "       followingFriendly as friendlyState\n" +
                                      "from FollowingTable inner join ProfileTable on profileUserID = followingUserID\n" +
                                      "where followingFriendly='N' and followingHost = ?";
        String getFriendlyrecoParams = followingHost;
        return this.jdbcTemplate.query(getFriendlyrecoQuery,
                (rs, rowNum) -> new GetFriendlyRecoRes(
                        rs.getString("recoFriendlyProfileImageUrl"),
                        rs.getString("recoFriendlyUserID"),
                        rs.getString("recoFriendlyName"),
                        rs.getString("friendlyState")),
                getFriendlyrecoParams);
    }

    public int addFriendly(PatchAddFriendlyReq patchAddFriendlyReq){
        String addFriendlyQuery = "update FollowingTable set followingFriendly = 'Y' where followingHost = ? and followingUserID = ?";
        Object[] addFriendlyParams = new Object[]{patchAddFriendlyReq.getFollowingHost(), patchAddFriendlyReq.getFollowingUserID()};
        return this.jdbcTemplate.update(addFriendlyQuery,addFriendlyParams);
    }

    public int delFriendly(PatchDelFriendlyReq patchDelFriendlyReq){
        String delFriendlyQuery = "update FollowingTable set followingFriendly = 'N' where followingHost = ? and followingUserID = ?";
        Object[] delFriendlyParams = new Object[]{patchDelFriendlyReq.getFollowingHost(), patchDelFriendlyReq.getFollowingUserID()};
        return this.jdbcTemplate.update(delFriendlyQuery, delFriendlyParams);
    }
}
