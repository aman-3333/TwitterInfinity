package com.twitter.infinity.Analysis.dao;

import java.util.ArrayList;

import com.twitter.infinity.vo.KeywordVo;
import com.twitter.infinity.vo.QueryParameterVo;
import com.twitter.infinity.vo.StatsCountVo;
import com.twitter.infinity.vo.TwitterVo;

public interface AnalysisDao {
   public boolean addKeyword(KeywordVo keywordVo);
   public boolean updateKeyword(KeywordVo keywordVo);
   public boolean deleteKeyword(int id);
   public ArrayList<KeywordVo> selectKeyword(String keyword);
   public KeywordVo selectKeywordById(int id);
   public boolean fetchTwitterByKeyword(String keyword);
   
   public ArrayList<StatsCountVo> countStats(String query);
   public ArrayList<StatsCountVo> sentimentCountStats(String query);
   public ArrayList<StatsCountVo> languageCountStats(String query);

   public ArrayList<StatsCountVo> maxCountStats(String query);
   public ArrayList<StatsCountVo> topMentions(String query);
   public ArrayList<StatsCountVo> topHashtags(String query);
   public ArrayList<StatsCountVo> topDomain(String query);
   public ArrayList<StatsCountVo> topUrl(String query);
   public ArrayList<StatsCountVo> topSource(String query);
   public ArrayList<StatsCountVo> topUsers(String query);
   public ArrayList<StatsCountVo> topLocaions(String query);
   public ArrayList<StatsCountVo> topUsersInfluencers(String query);
   
   public int totalTweetsCount(QueryParameterVo parameterVo);
   public long totalTimeFrame(QueryParameterVo parameterVo);
   public long totalReach(QueryParameterVo parameterVo);
   public long totalImpression(QueryParameterVo parameterVo);
   public long totalRt(QueryParameterVo parameterVo);
   public long totalFavorite(QueryParameterVo parameterVo);
   public long totalReplies(QueryParameterVo parameterVo);
   
   
   public ArrayList<StatsCountVo>  tweetBySentiment(QueryParameterVo parameterVo);
   public ArrayList<StatsCountVo>  tweetBySource(QueryParameterVo parameterVo);
   public ArrayList<StatsCountVo>  tweetByType(QueryParameterVo parameterVo);
   public ArrayList<StatsCountVo>  tweetByDomain(QueryParameterVo parameterVo);
   public ArrayList<StatsCountVo>  tweetsOverTime(QueryParameterVo parameterVo);
   public ArrayList<StatsCountVo>  hashTagWordCloud(QueryParameterVo parameterVo);
   public ArrayList<StatsCountVo>  mentionWordCloud(QueryParameterVo parameterVo);
   public ArrayList<TwitterVo> showTweet(QueryParameterVo parameterVo);
   
}
