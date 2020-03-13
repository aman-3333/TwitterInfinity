package com.twitter.infinity.common;

import java.net.URI;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.twitter.infinity.vo.QueryParameterVo;

public class Helper {
	static String[] sentiments={"Great","Good","Neutral","Bad","Terrible"};
	 
	 public static String  findSentiments()
	 {
		 int index=0;
		  return Helper.sentiments[getRandomNumberInts(0,4)];
	 }

	 public int randomIndex()
	 {
		  return 0;
	 }
	 
	 
	 public static int getRandomNumberInts(int min, int max){
		    Random random = new Random();
		    return random.ints(min,(max+1)).findFirst().getAsInt();
		}
	 
	 public static String parseHtml(String str)
	 { 
		 try
		 {
		 Document document = Jsoup.parse(str);
		 Element link = document.select("a").first();     
		 str=link.text();
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
		 }
		 return str;
	 }
	 
	 
	 public static String getHostName(String url) {
		    String hostname=url;
			try
			{
		      URI uri = new URI(url);
		      hostname = uri.getHost();
			    // to provide faultproof result, check if not null then return only hostname, without www.
			    /*if (hostname != null) {
			        return hostname.startsWith("www.") ? hostname.substring(4) : hostname;
			    }*/
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		    return hostname;
		}
	 
	 
	    public static String subQuery(QueryParameterVo parameterVo)
	    {
	    	String query="";
	    	if(parameterVo.hasPlainTweet)
	    	{
	           query +=" and tweet_type='OriginalTweet'";		
	    	}
	    	if(parameterVo.hasReTweet)
	    	{
		      query +=" and tweet_type='ReTweet'";		
	    	}
	    	if(parameterVo.hasReplies)
	    	{
			     query +=" and tweet_type='Replie'";		
	    	}
	    	if(parameterVo.hasMention)
	    	{
			     query +=" and hasMention='yes'";		
	    	}

	    	if(parameterVo.hasPicture)
	    	{
			     query +=" and hasPicture='yes'";		
	    	}
	    	if(parameterVo.hasVideo)
	    	{
			     query +=" and hasVideo='yes'";		
	    	}
	    	if(parameterVo.hasLink)
	    	{
			     query +=" and hasLink='yes'";		
	    	}
	    	if(parameterVo.hasVerified)
	    	{
			     query +=" and isUserVerified='yes'";		
	    	}
	    	if(parameterVo.hasUsers)
	    	{
			     query +=" and tweet_user_id is not null";		
	    	}
	    	if(parameterVo.hasHashTag)
	    	{
			     query +=" and hasHashtag='yes'";		
	    	}
	    	
	    	
	    	if(parameterVo.getKeyword()!=null && parameterVo.getKeyword().trim().length()>0)
	    	{
	    		  query+=" and text like '%"+parameterVo.getKeyword()+"%'";
	    	}

	    	if(parameterVo.getSentimentFilterList()!=null 
	    	   && parameterVo.getSentimentFilterList().size()>0)
	    	{
	    		    query+= " and sentiment in("+parseArrayListToString(parameterVo.getSentimentFilterList())+")";
	    	}
	    	if(parameterVo.getTopContributerFilterList()!=null 
	 	    	   && parameterVo.getTopContributerFilterList().size()>0)
	 	    	{
    		       query+= " and tweet_user_screen_name in("+parseArrayListToString(parameterVo.getTopContributerFilterList())+")";
	 	    	}
	    	if(parameterVo.getTopInfluencerFilterList()!=null 
		 	    	   && parameterVo.getTopInfluencerFilterList().size()>0)
		 	    	{
    		          query+= " and tweet_user_screen_name in("+parseArrayListToString(parameterVo.getTopInfluencerFilterList())+")";
 		 	    	}

	    	if(parameterVo.getTopHashtagFilterList()!=null 
		 	    	   && parameterVo.getTopHashtagFilterList().size()>0)
		 	    	{
		               query+= " and tbl_tweet.id in (select tweetid from tbl_hashtag where hashtag in ("+parseArrayListToString(parameterVo.getTopHashtagFilterList())+"))";
		 	    	}

	    	if(parameterVo.getMentionFilterList()!=null 
		 	    	   && parameterVo.getMentionFilterList().size()>0)
		 	    	{
	                  query+= " and tbl_tweet.id in (select tweetid from tbl_mention where mention_screen_name in ("+parseArrayListToString(parameterVo.getMentionFilterList())+"))";
		 	    	}

	    	if(parameterVo.getDomainFilterList()!=null 
		 	    	   && parameterVo.getDomainFilterList().size()>0)
		 	    	{
                query+= " and tbl_tweet.id in (select tweetId from tbl_domain where domain in ("+parseArrayListToString(parameterVo.getDomainFilterList())+"))";
		 	    	}

	    	if(parameterVo.getUrlFilterList()!=null 
		 	    	   && parameterVo.getUrlFilterList().size()>0)
		 	    	{
	               query+= " and tbl_tweet.id in (select tweet_id from tbl_url where url in ("+parseArrayListToString(parameterVo.getUrlFilterList())+"))";
		 	    	}
	    	if(parameterVo.getTwitterSourceFilterList()!=null 
		 	    	   && parameterVo.getTwitterSourceFilterList().size()>0)
		 	    	{
                query+= " and source in ("+parseArrayListToString(parameterVo.getTwitterSourceFilterList())+")";
		 	    	}
	    	if(parameterVo.getUserLocationFilterList()!=null 
		 	    	   && parameterVo.getUserLocationFilterList().size()>0)
		 	    	{
                query+= " and tweet_user_id in (select id from tbl_user where location in ("+parseArrayListToString(parameterVo.getUserLocationFilterList())+"))";
		 	    	}
	    	if(parameterVo.getLanguageFilterList()!=null 
		 	    	   && parameterVo.getLanguageFilterList().size()>0)
		 	    	{
               query+= " and lang in ("+parseArrayListToString(parameterVo.getLanguageFilterList())+")";
		 	    	}
	    	return query;
	    }
	    
	    
	    public static String parseArrayListToString(ArrayList<String> lst)
	    {
	    	String val="";
	    	for (String string : lst) {
	    		val+= "'"+string+"',";
			}
	    	
	    	
	    	if(val.trim().length()>0)
	    	{
	    		val = val.trim().substring(0, val.length() - 1);
	    	}
	    	return val;
	    }
	    
	    
	    public static String sortParam(String sortVal)
	    {
	    	String sort="created_at desc";
	    	try
	    	{
	    		switch (sortVal) {

				case "new":
					sort="created_at desc";
					break;
				case "old":
					sort="created_at asc";
					break;

	    		
	    		case "rt":
					sort="retweet_count desc";
					break;
				case "rt-l":
					sort="retweet_count asc";
					break;
				case "fv":
					sort="favorite_count desc";
					break;
				case "fv-l":
					sort="favorite_count asc";
					break;
				case "fl":
					sort="followers_count desc";
					break;
				case "fl-l":
					sort="followers_count asc";
					break;
				case "snt":
					sort="sentiment desc";
					break;
				case "snt-l":
					sort="sentiment asc";
					break;

				default:
					break;
				}
	    	}
	    	catch (Exception e) {
				e.printStackTrace();
			}
	    	return sort;
	    }
	    
	    
	    public static String convertUnixTimeToDateDisplayFormat(Long dt)
	    {
	    String dateRtn=Long.toString(dt);
	    try
	    {
	    //Date date = new Date(dt*1000);
	    Date date = new Date(dt);
	    dateRtn=DateFormat.getDateTimeInstance().format(date);
	    }
	    catch(Exception ex)
	    {
	    ex.printStackTrace();
	    }
	    return dateRtn;
	    }
	 
	 
	 
	 public static void main(String[] args) {
		// parseHtml("<a href=\"http://twitter.com/download/android\" rel=\"nofollow\">Twitter for Android</a>");
		System.out.println(convertUnixTimeToDateDisplayFormat(1580216983000l));
		 
		 
	}

	 
	 
	 
}
