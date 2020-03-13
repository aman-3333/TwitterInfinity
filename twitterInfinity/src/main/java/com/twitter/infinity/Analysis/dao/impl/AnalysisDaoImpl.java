package com.twitter.infinity.Analysis.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.twitter.infinity.Analysis.dao.AnalysisDao;
import com.twitter.infinity.common.Helper;
import com.twitter.infinity.vo.KeywordVo;
import com.twitter.infinity.vo.QueryParameterVo;
import com.twitter.infinity.vo.StatsCountVo;
import com.twitter.infinity.vo.TwitterVo;

import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;
import twitter4j.conf.ConfigurationBuilder;

@Repository
public class AnalysisDaoImpl implements AnalysisDao {

	@Autowired
	JdbcTemplate jdbcTemplate;


	@Override
	public boolean addKeyword(KeywordVo keywordVo) {
		boolean flag=false;
		try {
			String query="insert ignore into tbl_keyword(keyword,insertedDate,userId) values(?,now(),?)";
			System.out.println(query);
			Object[] obj=new Object[]{keywordVo.getKeyword(),keywordVo.getUserId()};
			jdbcTemplate.update(query, obj);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean updateKeyword(KeywordVo keywordVo) {
		boolean flag=false;
		try {
			String query="update tbl_keyword set keyword=? where id=?";
			System.out.println(query);
			Object[] obj=new Object[]{keywordVo.getKeyword(),keywordVo.getId()};
			jdbcTemplate.update(query, obj);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean deleteKeyword(int id) {
		boolean flag=false;
		try {
			String query="delete from tbl_keyword where id=?";
			System.out.println(query);
			Object[] obj=new Object[]{id};
			jdbcTemplate.update(query, obj);
			flag=true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public ArrayList<KeywordVo> selectKeyword(String keyword) {
		ArrayList<KeywordVo> lstKeyword=new ArrayList<>();
		try {
			String query="select * from tbl_keyword";
			System.out.println(query);
			//Object[] obj=new Object[]{keyword};
			List<Map<String, Object>> lst= jdbcTemplate.queryForList(query);
			for (Map<String, Object> map : lst) {
				KeywordVo keywordVo=new KeywordVo();
				keywordVo.setId( Integer.parseInt(map.get("id").toString()));
				keywordVo.setInsertedDate((map.get("insertedDate").toString()));
				keywordVo.setKeyword((map.get("keyword").toString()));
				keywordVo.setUserId((map.get("userId").toString()));
				lstKeyword.add(keywordVo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return lstKeyword;
	}

	@Override
	public KeywordVo selectKeywordById(int id) {
		KeywordVo keywordVo=new KeywordVo();
		try {
			String query="select * from tbl_keyword where id =?";
			System.out.println(query);
			Object[] obj=new Object[]{id};
			List<Map<String, Object>> lst= jdbcTemplate.queryForList(query, obj);
			for (Map<String, Object> map : lst) {
				keywordVo.setId( Integer.parseInt(map.get("id").toString()));
				keywordVo.setInsertedDate((map.get("insertedDate").toString()));
				keywordVo.setKeyword((map.get("keyword").toString()));
				keywordVo.setUserId((map.get("userId").toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return keywordVo;
	}

	@Override
	public boolean fetchTwitterByKeyword(String keyword) {
		{
			try {
				ConfigurationBuilder cb = new ConfigurationBuilder();
				cb.setDebugEnabled(true)
				.setOAuthConsumerKey("YDsKelEwf0neZ3nxWOQgEuOjs")
				.setOAuthConsumerSecret("b6KpHEZUHDWHfFACcYmsFpmA4COJjKGTfICvaGTuhdJHlnnIGV")
				.setOAuthAccessToken("3114654933-51hnGFPJwTMYZYwW5CxiijAxnIus0MkXpfDmPzc")
				.setOAuthAccessTokenSecret("OpHadWCemVTINLa1WIBbUtMmJ2qFNIYfq1RZf019JjAnK");
				TwitterFactory tf = new TwitterFactory(cb.build());
				Twitter twitter = tf.getInstance();

				Query query = new Query(keyword);
				query.setCount(100);
				QueryResult result = twitter.search(query);

				List<Status> lst = result.getTweets();
				System.out.println(lst.size());
				for (Status status : lst) {
					// System.out.println(test);
					Date created_at = (Date) status.getCreatedAt();
					int id = (int) status.getUser().getId();

					String text = status.getText();
					String source =  status.getSource();
					boolean truncated = (boolean) status.isTruncated();
					long tweet_user_id = (long) status.getUser().getId();
					String tweet_user_screen_name = (String) status.getUser().getScreenName();
					long in_reply_to_status_id = (long) status.getInReplyToStatusId();
					long in_reply_to_user_id = (long) status.getInReplyToUserId();
					String in_reply_to_screen_name = (String) status.getInReplyToScreenName();
					int retweet_count = (int) status.getRetweetCount();
					int favorite_count = (int) status.getFavoriteCount();
					boolean favorited = (boolean) status.isFavorited();
					boolean possibly_sensitive = (boolean) status.isPossiblySensitive();
					String lang = (String) status.getLang();
					
                     
					String tweeetType="OriginalTweet";
					if(status.getRetweetedStatus() !=null)
					{
						tweeetType="ReTweet";						
					}
					if((long) status.getInReplyToUserId()>0)
					{
						tweeetType="Replie";
					}
					
					  //Has Mention
					  String hasMention="no";
					  if(status.getUserMentionEntities().length>0)
					  {
						  hasMention="yes";
					  }
					  String hasHashtag="no";
					  if(status.getHashtagEntities().length>0)
					  {
						  hasHashtag="yes";
					  }
					  String hasUrl="no";
					  if(status.getURLEntities().length>0)
					  {
						  hasUrl="yes";
					  }
					
					  	String hasImage="no";
					  	String hasVideo="no";
						MediaEntity[] mediaEntityss=status.getMediaEntities();
						for (MediaEntity mediaEntity : mediaEntityss) {
							if(mediaEntity.getType().equals("photo"))
							{
								hasImage="yes";
							}
							else{
								hasVideo="yes";
							}
						}
						
						String isUserVerified="no";
						if(status.getUser().isVerified())
						{
							isUserVerified="yes";
						}
					//table video>>
					long tweetid = (long) status.getId();
					String url1 = (String) status.getUser().getURL();
					//table mention>>

					// System.out.println(test);
					String sql = "INSERT INTO tbl_tweet"
							+ "(created_at,"
							+ "id,"
							+ "TEXT,"
							+ "source,"
							+ "truncated,"
							+ "tweet_user_id,"
							+ "tweet_user_screen_name,"
							+ "in_reply_to_status_id,"
							+ "in_reply_to_user_id,"
							+ "in_reply_to_screen_name,"
							+ "retweet_count,"
							+ "favorite_count,"
							+ "favorited,"
							+ "possibly_sensitive,"
							+ "lang,"
							+ "sentiment,"
							+ "tweet_type,"

							+ "hasMention,"
							+ "hasHashtag,"
							+ "hasPicture,"
							+ "hasVideo,"
							+ "hasLink,"
							+ "isUserVerified"

							
							
							
							
							+ ") "
							+ " VALUES('" + (created_at.getTime()) + "',"
							+ "'" + tweetid + "',"
							+ "'" + StringEscapeUtils.escapeEcmaScript(text.trim()) + "',"
							+ "'" + Helper.parseHtml(source.trim()) + "',"
							+ "'" + (truncated == true ? 1 : 0) + "',"
							+ "'" + tweet_user_id + "',"
							+ "'" + tweet_user_screen_name + "',"
							+ "'" + in_reply_to_status_id + "',"
							+ "'" + in_reply_to_user_id + "',"
							+ "'" + in_reply_to_screen_name + "',"
							+ "'" + retweet_count + "',"
							+ "'" + favorite_count + "',"
							+ "'" + (favorited == true ? 1 : 0) + "',"
							+ "'" + (possibly_sensitive == true ? 1 : 0) + "',"
							+ "'" + lang + "',"
							+ "'" + Helper.findSentiments() + "',"
							+ "'" + tweeetType + "',"
							+ "'" + hasMention + "',"
							+ "'" + hasHashtag + "',"
							+ "'" + hasImage + "',"
							+ "'" + hasVideo + "',"
							+ "'" + hasUrl + "',"
							+ "'" + isUserVerified + "'"
							+ ")";
					System.out.println("Tweet::::" + sql);
					try
					{
						jdbcTemplate.execute(sql);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}
					//***User*******************************//

					//User table >>>
					long userId = (long) status.getUser().getId();
					String name = (String) status.getUser().getName();
					String screen_name1 = (String) status.getUser().getScreenName();
					String location = (String) status.getUser().getLocation();
					String url = (String) status.getUser().getURL();
					String description = (String) status.getUser().getDescription();
					boolean verified = (boolean) status.getUser().isVerified();
					int followers_count = (int) status.getUser().getFollowersCount();
					int freinds_count = (int) status.getUser().getFriendsCount();
					int listed_count = (int) status.getUser().getListedCount();
					int favourites_count = (int) status.getUser().getFavouritesCount();
					int statuses_count = (int) status.getUser().getStatusesCount();
					Date created_at1 = (Date) status.getUser().getCreatedAt();
					int utc_offset = (int) status.getUser().getUtcOffset();
					String time_zone = (String) status.getUser().getTimeZone();
					boolean geo_enabled = (boolean) status.getUser().isGeoEnabled();
					String userLang = (String) status.getUser().getLang();
					String profile_background_image_url = (String) status.getUser().getProfileBackgroundImageURL();
					String profile_image_url = (String) status.getUser().getProfileImageURL();
					String profile_banner_url = (String) status.getUser().getProfileBannerURL();

					sql="INSERT ignore INTO tbl_user(\n" +
							"id,\n" +
							"NAME,\n" +
							"screen_name,\n" +
							"location,\n" +
							"url,\n" +
							"description,\n" +
							"verified,\n" +
							"followers_count,\n" +
							"friends_count,\n" +
							"listed_count,\n" +
							"favourites_count,\n" +
							"statuses_count,\n" +
							"created_at,\n" +
							"utc_offset,\n" +
							"time_zone,\n" +
							"geo_enabled,\n" +
							"lang,\n" +
							"profile_background_image_url,\n" +
							"profile_image_url,\n" +
							"profile_banner_url\n" +
							")"
							+ "values("
							+ "'" + userId + "',"
							+ "'" + StringEscapeUtils.escapeEcmaScript(name) + "',"
							+ "'" + screen_name1 + "',"
							+ "'" + location + "',"
							+ "'" + url1 + "',"
							+ "'" + StringEscapeUtils.escapeEcmaScript(description) + "',"
							+ "'" + (verified==true?1:0) + "',"
							+ "'" + followers_count + "',"
							+ "'" + freinds_count + "',"
							+ "'" + listed_count + "',"
							+ "'" + favorite_count + "',"
							+ "'" + statuses_count + "',"
							+ "'" + created_at1.getTime() + "',"
							+ "'" + utc_offset + "',"
							+ "'" + time_zone + "',"
							+ "'" + (geo_enabled==true?1:0) + "',"
							+ "'" + lang + "',"
							+ "'" + profile_background_image_url + "',"
							+ "'" + profile_image_url + "',"
							+ "'" + profile_banner_url + "'"
							+ ")"
							+ "";

					System.out.println("Users:::"+sql);

					try
					{
						jdbcTemplate.execute(sql);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
					}

					//HashTags

					//System.out.println("com.mycompany.twittercollector.TwitterCollection.main()::"+status.toString());
					HashtagEntity[] hashtag=    status.getHashtagEntities();
					for (HashtagEntity hashtagEntity : hashtag) {

						sql="INSERT IGNORE INTO tbl_hashtag(tweetid,hashtag) values("
								+ "'" + tweetid + "',"
								+ "'" + hashtagEntity.getText() + "'"
								+ ")";
						System.out.println("Hashtag Query:::::"+sql);
						jdbcTemplate.execute(sql);
					}

					UserMentionEntity[] mentions=    status.getUserMentionEntities();
					for (UserMentionEntity mention : mentions) {

						sql="INSERT IGNORE INTO tbl_mention(tweetid,mentionid,mention_screen_name)"
								+ "values("
								+ "'" + tweetid + "',"
								+ "'" + mention.getId() + "',"
								+ "'" + mention.getScreenName() + "'"
								+ ")";
						System.out.println("Mention Query:::::"+sql);
						try
						{
							jdbcTemplate.execute(sql);
						}
						catch(Exception ex)
						{
							ex.printStackTrace();
						}
					}


					URLEntity[] urls=      status.getURLEntities();
					for (URLEntity url2 : urls) {
						sql="INSERT IGNORE INTO tbl_url(tweet_id,url,domain) values("
								+ "'" + tweetid + "',"
								+ "'" + url2.getExpandedURL() + "',"
								+ "'" + Helper.getHostName(url2.getExpandedURL()) + "'"
								+ ")";
						System.out.println("URL Query:::::"+sql);
						jdbcTemplate.execute(sql);
					}

					MediaEntity[] mediaEntitys=status.getMediaEntities();
					for (MediaEntity mediaEntity : mediaEntitys) {
						if(mediaEntity.getType().equals("photo"))
						{
							sql="INSERT IGNORE INTO tbl_image(tweet_id,image_url) values("
									+ "'" + tweetid + "',"
									+ "'" + mediaEntity.getExpandedURL() + "'"
									+ ")";
							System.out.println("Image Query:::::"+sql);
							try
							{
								jdbcTemplate.execute(sql);
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
							}        
						}
						else{
							sql="INSERT IGNORE INTO tbl_video(tweet_id,video_url) values("
									+ "'" + tweetid + "',"
									+ "'" + mediaEntity.getExpandedURL() + "'"
									+ ")";
							System.out.println("Video Query:::::"+sql);
							try
							{
								jdbcTemplate.execute(sql);
							}
							catch(Exception ex)
							{
								ex.printStackTrace();
							}
						}
					}
				}

			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} 			
		return true;
	}

	@Override
	public ArrayList<StatsCountVo> countStats(String query) {
		ArrayList<StatsCountVo> lstStats=new ArrayList<>();
		try
		{
			//Tweet Type
			String sql="SELECT tweet_type as keyword ,COUNT(*) as cnt FROM tbl_tweet where text like '%"+query+"%' GROUP BY tweet_type";
			System.out.println(sql);
			List<Map<String, Object>> lst= jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : lst) {
				StatsCountVo countVo=new StatsCountVo();
				countVo.setType(map.get("keyword").toString());
				countVo.setCount(Integer.parseInt(map.get("cnt").toString()));
				lstStats.add(countVo);
			}
			
			
			//Mention Count
		    sql="SELECT COUNT(*) as cnt FROM tbl_mention INNER JOIN tbl_tweet ON tbl_tweet.id=tbl_mention.tweetid where text like '%"+query+"%'";
		    System.out.println(sql);
		    int count=jdbcTemplate.queryForObject(sql, Integer.class);
			StatsCountVo countVo=new StatsCountVo();
			countVo.setType("mention");
			countVo.setCount(count);
			lstStats.add(countVo);
		    
			//Hashtag Count
		    sql=" SELECT COUNT(*) FROM tbl_hashtag INNER JOIN tbl_tweet ON tbl_tweet.id=tbl_hashtag.tweetid where text like '%"+query+"%'";
		    System.out.println(sql);
		    count=jdbcTemplate.queryForObject(sql, Integer.class);
			countVo=new StatsCountVo();
			countVo.setType("hashtag");
			countVo.setCount(count);
			lstStats.add(countVo);

			//image Count
		    sql=" SELECT COUNT(*) FROM tbl_image INNER JOIN tbl_tweet ON tbl_tweet.id=tbl_image.tweet_id  where text like '%"+query+"%'";
		    System.out.println(sql);
		    count=jdbcTemplate.queryForObject(sql, Integer.class);
			countVo=new StatsCountVo();
			countVo.setType("image");
			countVo.setCount(count);
			lstStats.add(countVo);

			//video Count
		    sql=" SELECT COUNT(*) FROM tbl_video INNER JOIN tbl_tweet ON tbl_tweet.id=tbl_video.tweet_id  where text like '%"+query+"%'";
		    System.out.println(sql);
		    count=jdbcTemplate.queryForObject(sql, Integer.class);
			countVo=new StatsCountVo();
			countVo.setType("video");
			countVo.setCount(count);
			lstStats.add(countVo);

			//URL Count
		    sql=" SELECT COUNT(*) FROM tbl_url INNER JOIN tbl_tweet ON tbl_tweet.id=tbl_url.tweet_id  where text like '%"+query+"%'";
		    System.out.println(sql);
		    count=jdbcTemplate.queryForObject(sql, Integer.class);
			countVo=new StatsCountVo();
			countVo.setType("url");
			countVo.setCount(count);
			lstStats.add(countVo);

			//User Count
		    sql=" SELECT  COUNT(DISTINCT tweet_user_id) FROM tbl_tweet  where text like '%"+query+"%'";
		    System.out.println(sql);
		    count=jdbcTemplate.queryForObject(sql, Integer.class);
			countVo=new StatsCountVo();
			countVo.setType("user");
			countVo.setCount(count);
			lstStats.add(countVo);

			
			//verifiedUser Count
		    sql=" SELECT  COUNT(*) FROM tbl_user INNER JOIN tbl_tweet ON tbl_tweet.tweet_user_id=tbl_user.id WHERE verified='1' and text like '%"+query+"%'";
		    System.out.println(sql);
		    count=jdbcTemplate.queryForObject(sql, Integer.class);
			countVo=new StatsCountVo();
			countVo.setType("verified");
			countVo.setCount(count);
			lstStats.add(countVo);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return lstStats;
	}

	@Override
	public ArrayList<StatsCountVo> sentimentCountStats(String query) {
		ArrayList<StatsCountVo> lstStats=new ArrayList<>();
		try
		{
			//Sentiment Type
			String sql="SELECT sentiment ,COUNT(*) as cnt FROM tbl_tweet where text like '%"+query+"%' GROUP BY sentiment";
			List<Map<String, Object>> lst= jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : lst) {
				StatsCountVo countVo=new StatsCountVo();
				countVo.setType(map.get("sentiment").toString());
				countVo.setCount(Integer.parseInt(map.get("cnt").toString()));
				lstStats.add(countVo);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return lstStats;
	}

	@Override
	public ArrayList<StatsCountVo> languageCountStats(String query) {
		ArrayList<StatsCountVo> lstStats=new ArrayList<>();
		try
		{
			//Language Count
			String sql="SELECT lang ,COUNT(*) as cnt FROM tbl_tweet where text like '%"+query+"%' GROUP BY lang";
			List<Map<String, Object>> lst= jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : lst) {
				StatsCountVo countVo=new StatsCountVo();
				countVo.setType(map.get("lang").toString());
				countVo.setCount(Integer.parseInt(map.get("cnt").toString()));
				lstStats.add(countVo);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return lstStats;
	}

	@Override
	public ArrayList<StatsCountVo> maxCountStats(String query) {
		ArrayList<StatsCountVo> lstStats=new ArrayList<>();
		try
		{
			String sql=" SELECT  MAX(retweet_count) as count  FROM tbl_tweet where text like '%"+query+"%'";
			int count=jdbcTemplate.queryForObject(sql, Integer.class);
			StatsCountVo countVo=new StatsCountVo();
			countVo.setType("retweet_count");
			countVo.setCount(count);
			lstStats.add(countVo);

		
			sql=" SELECT  MAX(favorite_count) as count  FROM tbl_tweet where text like '%"+query+"%'";
			count=jdbcTemplate.queryForObject(sql, Integer.class);
			countVo=new StatsCountVo();
			countVo.setType("favorite_count");
			countVo.setCount(count);
			lstStats.add(countVo);

			sql=" SELECT  MAX(followers_count) as count FROM tbl_user INNER JOIN tbl_tweet ON tbl_tweet.tweet_user_id=tbl_user.id  where text like '%"+query+"%'";
			count=jdbcTemplate.queryForObject(sql, Integer.class);
			countVo=new StatsCountVo();
			countVo.setType("followers_count");
			countVo.setCount(count);
			lstStats.add(countVo);

			
			sql=" SELECT  MAX(friends_count) as count FROM tbl_user INNER JOIN tbl_tweet ON tbl_tweet.tweet_user_id=tbl_user.id  where text like '%"+query+"%'";
			count=jdbcTemplate.queryForObject(sql, Integer.class);
			countVo=new StatsCountVo();
			countVo.setType("friends_count");
			countVo.setCount(count);
			lstStats.add(countVo);

		
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return lstStats;

	}

	@Override
	public ArrayList<StatsCountVo> topMentions(String query) {
		ArrayList<StatsCountVo> lstStats=new ArrayList<>();
		try
		{
			//top Mentions
			String sql=" SELECT tbl_mention.mention_screen_name as screenName,COUNT(*) AS cnt FROM tbl_mention INNER JOIN tbl_tweet ON tbl_tweet.id=tbl_mention.tweetid   where text like '%"+query+"%' GROUP BY tbl_mention.mention_screen_name ORDER BY cnt DESC  LIMIT 10";
			List<Map<String, Object>> lst= jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : lst) {
				StatsCountVo countVo=new StatsCountVo();
				countVo.setType(map.get("screenName").toString());
				countVo.setCount(Integer.parseInt(map.get("cnt").toString()));
				lstStats.add(countVo);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return lstStats;
	}

	@Override
	public ArrayList<StatsCountVo> topHashtags(String query) {
		ArrayList<StatsCountVo> lstStats=new ArrayList<>();
		try
		{
			//top Hashtag
			String sql=" SELECT tbl_hashtag.hashtag as hashtag,COUNT(*) AS cnt FROM tbl_hashtag INNER JOIN tbl_tweet ON tbl_tweet.id=tbl_hashtag.tweetid   where text like '%"+query+"%' GROUP BY tbl_hashtag.hashtag ORDER BY cnt DESC  LIMIT 10";
			List<Map<String, Object>> lst= jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : lst) {
				StatsCountVo countVo=new StatsCountVo();
				countVo.setType(map.get("hashtag").toString());
				countVo.setCount(Integer.parseInt(map.get("cnt").toString()));
				lstStats.add(countVo);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return lstStats;
	}

	@Override
	public ArrayList<StatsCountVo> topDomain(String query) {
		ArrayList<StatsCountVo> lstStats=new ArrayList<>();
		try
		{
			//top Domain
			String sql=" SELECT tbl_url.domain as domain,COUNT(*) AS cnt FROM tbl_url INNER JOIN tbl_tweet ON tbl_tweet.id=tbl_url.tweet_id  where domain is not null and text like '%"+query+"%' GROUP BY tbl_url.domain ORDER BY cnt DESC  LIMIT 10";
		    System.out.println(sql);
			List<Map<String, Object>> lst= jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : lst) {
				StatsCountVo countVo=new StatsCountVo();
				countVo.setType(map.get("domain").toString());
				countVo.setCount(Integer.parseInt(map.get("cnt").toString()));
				lstStats.add(countVo);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return lstStats;
	}

	@Override
	public ArrayList<StatsCountVo> topUrl(String query) {
		ArrayList<StatsCountVo> lstStats=new ArrayList<>();
		try
		{
			//top URL
			String sql=" SELECT tbl_url.url as url,COUNT(*) AS cnt FROM tbl_url INNER JOIN tbl_tweet ON tbl_tweet.id=tbl_url.tweet_id where text like '%"+query+"%' GROUP BY tbl_url.url ORDER BY cnt DESC  LIMIT 10";
			List<Map<String, Object>> lst= jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : lst) {
				StatsCountVo countVo=new StatsCountVo();
				countVo.setType(map.get("url").toString());
				countVo.setCount(Integer.parseInt(map.get("cnt").toString()));
				lstStats.add(countVo);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return lstStats;
	}

	@Override
	public ArrayList<StatsCountVo> topSource(String query) {
		ArrayList<StatsCountVo> lstStats=new ArrayList<>();
		try
		{
			//top Source
			String sql=" SELECT source ,COUNT(*) AS cnt FROM tbl_tweet where text like '%"+query+"%' GROUP BY source ORDER BY cnt DESC  LIMIT 10";
			List<Map<String, Object>> lst= jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : lst) {
				StatsCountVo countVo=new StatsCountVo();
				countVo.setType(map.get("source").toString());
				countVo.setCount(Integer.parseInt(map.get("cnt").toString()));
				lstStats.add(countVo);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return lstStats;
	}

	@Override
	public ArrayList<StatsCountVo> topUsers(String query) {
		ArrayList<StatsCountVo> lstStats=new ArrayList<>();
		try
		{
			//top User 
			String sql=" SELECT tbl_user.name,tweet_user_screen_name ,COUNT(tbl_tweet.id) AS cnt,tbl_user.profile_image_url FROM tbl_tweet "
					+ " INNER JOIN tbl_user ON tbl_user.id=tbl_tweet.tweet_user_id "
					+ " where text like '%"+query+"%' GROUP BY tweet_user_screen_name ORDER BY cnt DESC  LIMIT 10";
			List<Map<String, Object>> lst= jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : lst) {
				StatsCountVo countVo=new StatsCountVo();
				countVo.setType(map.get("tweet_user_screen_name").toString());
				countVo.setCount(Integer.parseInt(map.get("cnt").toString()));
				countVo.setUserImg(map.get("profile_image_url").toString());
				countVo.setUserName(map.get("name").toString());
				lstStats.add(countVo);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return lstStats;
	}

	@Override
	public ArrayList<StatsCountVo> topLocaions(String query) {
		ArrayList<StatsCountVo> lstStats=new ArrayList<>();
		try
		{
			//top User location
			String sql="SELECT tbl_user.location as location,COUNT(*) AS cnt FROM tbl_user INNER JOIN tbl_tweet ON tbl_tweet.tweet_user_id=tbl_user.id where text like '%"+query+"%' and tbl_user.location !='' GROUP BY location ORDER BY cnt DESC  LIMIT 10";
			List<Map<String, Object>> lst= jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : lst) {
				StatsCountVo countVo=new StatsCountVo();
				countVo.setType(map.get("location").toString());
				countVo.setCount(Integer.parseInt(map.get("cnt").toString()));
				lstStats.add(countVo);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return lstStats;
	}

	@Override
	public ArrayList<StatsCountVo> topUsersInfluencers(String query) {
		ArrayList<StatsCountVo> lstStats=new ArrayList<>();
		try
		{
			//top User  Influencer
			String sql=" SELECT tbl_user.name,tweet_user_screen_name ,followers_count AS cnt,tbl_user.profile_image_url "
					+ " FROM tbl_tweet "
					+ " INNER JOIN tbl_user ON tbl_user.id=tbl_tweet.tweet_user_id "
					+ " where text like '%"+query+"%' GROUP BY tweet_user_screen_name "
					+ " ORDER BY cnt DESC  LIMIT 10";
			List<Map<String, Object>> lst= jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : lst) {
				StatsCountVo countVo=new StatsCountVo();
				countVo.setType(map.get("tweet_user_screen_name").toString());
				countVo.setCount(Integer.parseInt(map.get("cnt").toString()));
				countVo.setUserImg(map.get("profile_image_url").toString());
				countVo.setUserName(map.get("name").toString());
				
				lstStats.add(countVo);
			}
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return lstStats;
	}

	@Override
	public int totalTweetsCount(QueryParameterVo parameterVo) {
		int count=0;
		try
		{
	       String query=" select count(*) as cnt from tbl_tweet where 1=1 "+	Helper.subQuery(parameterVo);
	       System.out.println("totalTweetsCount:::"+query);
	       count= jdbcTemplate.queryForObject(query,Integer.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public long totalTimeFrame(QueryParameterVo parameterVo) {
		long count=0;
		try
		{
	       String query=" SELECT COALESCE((MAX(created_at)- MIN(created_at)),0) as timeframe FROM tbl_tweet where 1=1 "+	Helper.subQuery(parameterVo);
	       System.out.println("totalTimeFrame:::"+query);
	       count= jdbcTemplate.queryForObject(query,Long.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public long totalReach(QueryParameterVo parameterVo) {
		long count=0;
		try
		{
	       String query=" SELECT COALESCE (SUM(tbl_user.followers_count),0) as count FROM tbl_user "
	       		+ " INNER JOIN tbl_tweet on tbl_user.id=tbl_tweet.tweet_user_id where 1=1 "+	Helper.subQuery(parameterVo);
	       System.out.println("totalReach:::"+query);
	       count= jdbcTemplate.queryForObject(query,Long.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public long totalImpression(QueryParameterVo parameterVo) {
		long count=0;
		try
		{
	       String query=" SELECT COALESCE (SUM(tbl_user.followers_count),0) as count FROM tbl_user "
	       		+ " INNER JOIN tbl_tweet on tbl_user.id=tbl_tweet.tweet_user_id where 1=1 "+	Helper.subQuery(parameterVo);
	      System.out.println("totalImpression::"+query);
	       count= jdbcTemplate.queryForObject(query,Long.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public long totalRt(QueryParameterVo parameterVo) {
		long count=0;
		try
		{
	       String query=" SELECT COALESCE(SUM(tbl_tweet.retweet_count),0) from tbl_tweet where 1=1 "+	Helper.subQuery(parameterVo);
	       System.out.println("totalRt:::"+query);
	       count= jdbcTemplate.queryForObject(query,Long.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public long totalFavorite(QueryParameterVo parameterVo) {
		long count=0;
		try
		{
	       String query=" SELECT COALESCE(SUM(tbl_tweet.favorite_count),0) from tbl_tweet where 1=1 "+	Helper.subQuery(parameterVo);
	       System.out.println("totalFavorite:::"+query);
	       //count= jdbcTemplate.queryForObject(query,Long.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public ArrayList<StatsCountVo> tweetBySentiment(QueryParameterVo parameterVo) {
		ArrayList<StatsCountVo> lstStats=new ArrayList<>();
		try
		{
			//Sentiment Type
			String sql="SELECT sentiment ,COUNT(*) as cnt FROM tbl_tweet where 1=1 "+Helper.subQuery(parameterVo)+"GROUP BY sentiment";
			List<Map<String, Object>> lst= jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : lst) {
				StatsCountVo countVo=new StatsCountVo();
				countVo.setType(map.get("sentiment").toString());
				countVo.setCount(Integer.parseInt(map.get("cnt").toString()));
				lstStats.add(countVo);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return lstStats;
	}

	@Override
	public ArrayList<StatsCountVo> tweetBySource(QueryParameterVo parameterVo) {
		ArrayList<StatsCountVo> lstStats=new ArrayList<>();
		try
		{
			String sql=" SELECT source ,COUNT(*) AS cnt FROM tbl_tweet where 1=1 "+Helper.subQuery(parameterVo)+" GROUP BY source ORDER BY cnt DESC  LIMIT 10";
			List<Map<String, Object>> lst= jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : lst) {
				StatsCountVo countVo=new StatsCountVo();
				countVo.setType(map.get("source").toString());
				countVo.setCount(Integer.parseInt(map.get("cnt").toString()));
				lstStats.add(countVo);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return lstStats;
	}

	@Override
	public ArrayList<StatsCountVo> tweetByType(QueryParameterVo parameterVo) {
		ArrayList<StatsCountVo> lstStats=new ArrayList<>();
		try
		{
		String sql="SELECT tweet_type as keyword ,COUNT(*) as cnt FROM tbl_tweet where 1=1 "+Helper.subQuery(parameterVo)+" GROUP BY tweet_type";
		System.out.println(sql);
		List<Map<String, Object>> lst= jdbcTemplate.queryForList(sql);
		for (Map<String, Object> map : lst) {
			StatsCountVo countVo=new StatsCountVo();
			countVo.setType(map.get("keyword").toString());
			countVo.setCount(Integer.parseInt(map.get("cnt").toString()));
			lstStats.add(countVo);
		}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return lstStats;
	}

	@Override
	public ArrayList<StatsCountVo> tweetByDomain(QueryParameterVo parameterVo) {
		return null;
	}

	@Override
	public ArrayList<StatsCountVo> tweetsOverTime(QueryParameterVo parameterVo) {
		return null;
	}

	@Override
	public ArrayList<StatsCountVo> hashTagWordCloud(QueryParameterVo parameterVo) {
		return null;
	}

	@Override
	public ArrayList<StatsCountVo> mentionWordCloud(QueryParameterVo parameterVo) {
		return null;
	}

	@Override
	public ArrayList<TwitterVo> showTweet(QueryParameterVo parameterVo) {
		ArrayList<TwitterVo> lstStats=new ArrayList<>();
		try
		{
			String sql="SELECT tbl_tweet.created_at,"+
						"tbl_tweet.id,"+
						"tbl_tweet.text,"+
						"tbl_tweet.source,"+
						"tbl_tweet.tweet_user_id,"+
						"tbl_tweet.tweet_user_screen_name,"+
						"tbl_tweet.retweet_count,"+
						"tbl_tweet.favorite_count,"+
						"tbl_tweet.sentiment,"+
						"tbl_tweet.tweet_type,"+
						"tbl_user.name,"+
						"tbl_user.verified,"+
						"tbl_user.followers_count,"+
						"tbl_user.friends_count,"+
						"tbl_user.listed_count,"+
						"tbl_user.favourites_count,"+
						"tbl_user.statuses_count,"+
						"tbl_user.profile_image_url"+
						" FROM tbl_tweet INNER JOIN tbl_user ON tbl_user.id=tbl_tweet.tweet_user_id "+Helper.subQuery(parameterVo) + " order by "+Helper.sortParam(parameterVo.getSortTweet()) +" limit 30";
			System.out.println("showTweet::::"+sql);
			List<Map<String, Object>> lst= jdbcTemplate.queryForList(sql);
			for (Map<String, Object> map : lst) {
				TwitterVo twitterVo=new TwitterVo();
				twitterVo.setTweetCreatedAt(Helper.convertUnixTimeToDateDisplayFormat(Long.parseLong(map.get("created_at")==null?"0":map.get("created_at").toString())));
				twitterVo.setTweetId(map.get("id")==null?"":map.get("id").toString());
				twitterVo.setTweetText(map.get("text")==null?"":map.get("text").toString());
				twitterVo.setUserId(map.get("tweet_user_id")==null?"":map.get("tweet_user_id").toString());
				twitterVo.setUserName(map.get("name")==null?"":map.get("name").toString());
				twitterVo.setUserScreenName(map.get("tweet_user_screen_name")==null?"":map.get("tweet_user_screen_name").toString());
				twitterVo.setRetweetCount(map.get("retweet_count")==null?"":map.get("retweet_count").toString());
				twitterVo.setTweetFavoriteCount(map.get("favorite_count")==null?"":map.get("favorite_count").toString());
				twitterVo.setTweetSentiment(map.get("sentiment")==null?"":map.get("sentiment").toString());
				twitterVo.setTweetType(map.get("tweet_type")==null?"":map.get("tweet_type").toString());
				twitterVo.setUserVerified(map.get("verified")==null?"0":map.get("verified").toString());
				twitterVo.setUserFollowerCount(map.get("followers_count")==null?"":map.get("followers_count").toString());
				twitterVo.setUserFriendCount(map.get("friends_count")==null?"":map.get("friends_count").toString());
				twitterVo.setUserListedCount(map.get("listed_count")==null?"":map.get("listed_count").toString());
				twitterVo.setUserFavouriteCount(map.get("favourites_count")==null?"":map.get("favourites_count").toString());
				twitterVo.setUserStatusCount(map.get("statuses_count")==null?"0":map.get("statuses_count").toString());
				twitterVo.setUserPhoto(map.get("profile_image_url")==null?"":map.get("profile_image_url").toString());
				lstStats.add(twitterVo);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return lstStats;
	}

	@Override
	public long totalReplies(QueryParameterVo parameterVo) {
		int count=0;
		try
		{
		   parameterVo.setHasReplies(true);
	       String query=" select count(*) as cnt from tbl_tweet where 1=1  "+	Helper.subQuery(parameterVo);
	       System.out.println("totalReplies:::"+query);
	       count= jdbcTemplate.queryForObject(query,Integer.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
}
