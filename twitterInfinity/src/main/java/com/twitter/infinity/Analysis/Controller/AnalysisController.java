package com.twitter.infinity.Analysis.Controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.twitter.infinity.Analysis.dao.AnalysisDao;
import com.twitter.infinity.vo.KeywordVo;
import com.twitter.infinity.vo.QueryParameterVo;
import com.twitter.infinity.vo.StatsCountVo;
import com.twitter.infinity.vo.TwitterVo;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/analysis")
public class AnalysisController {

	@Autowired
	AnalysisDao analysisDao;
	
	 @PostMapping(value="/addKeyword")
	 @ResponseBody	
	 public Boolean addKeyword(@RequestBody KeywordVo keywordVo)
	  {
		 return analysisDao.addKeyword(keywordVo);
	  }

	 @PostMapping(value="/updateKeyword")
	 @ResponseBody	
	 public Boolean updateKeyword(@RequestBody KeywordVo keywordVo)
	  {
		 return analysisDao.updateKeyword(keywordVo);
	  }

	 @PostMapping(value="/deleteKeyword")
	 @ResponseBody	
	 public Boolean deleteKeyword(@RequestParam("id") int id)
	  {
		 return analysisDao.deleteKeyword(id);
	  }
	 
	 @PostMapping(value="/selectAllKeyword")
	 @ResponseBody	
	 public ArrayList<KeywordVo> selectKeyword(@RequestParam("keyword") String keyword)
	  {
		 return analysisDao.selectKeyword(keyword);
	  }
	 
	 @PostMapping(value="/selectKeywordById")
	 @ResponseBody	
	 public KeywordVo selectKeywordById(@RequestParam("id") int id)
	  {
		 return analysisDao.selectKeywordById(id);
	  }
	 
	 @PostMapping(value="/fetchTwitterByKeyword")
	 @ResponseBody	
	 public Boolean fetchTwitterByKeyword(@RequestParam("keyword") String keyword)
	  {
		 return analysisDao.fetchTwitterByKeyword(keyword);
	  }
	 
	 @PostMapping(value="/countStats")
	 @ResponseBody	
	 public ArrayList<StatsCountVo> countStats(@RequestParam("keyword") String keyword)
	  {
		 System.out.println("inside countStats::::");
		 return analysisDao.countStats(keyword);
	  }

	 @PostMapping(value="/sentimentCountStats")
	 @ResponseBody	
	 public ArrayList<StatsCountVo> sentimentCountStats(@RequestParam("keyword") String keyword)
	  {
		 return analysisDao.sentimentCountStats(keyword);
	  }

	 @PostMapping(value="/languageCountStats")
	 @ResponseBody	
	 public ArrayList<StatsCountVo> languageCountStats(@RequestParam("keyword") String keyword)
	  {
		 return analysisDao.languageCountStats(keyword);
	  }
	 
	 @PostMapping(value="/maxCountStats")
	 @ResponseBody	
	 public ArrayList<StatsCountVo> maxCountStats(@RequestParam("keyword") String keyword)
	  {
		 return analysisDao.maxCountStats(keyword);
	  }

	 @PostMapping(value="/topMentions")
	 @ResponseBody	
	 public ArrayList<StatsCountVo> topMentions(@RequestParam("keyword") String keyword)
	  {
		 return analysisDao.topMentions(keyword);
	  }

	 @PostMapping(value="/topHashtags")
	 @ResponseBody	
	 public ArrayList<StatsCountVo> topHashtags(@RequestParam("keyword") String keyword)
	  {
		 return analysisDao.topHashtags(keyword);
	  }

	 @PostMapping(value="/topDomain")
	 @ResponseBody	
	 public ArrayList<StatsCountVo> topDomain(@RequestParam("keyword") String keyword)
	  {
		 return analysisDao.topDomain(keyword);
	  }

	 @PostMapping(value="/topUrl")
	 @ResponseBody	
	 public ArrayList<StatsCountVo> topUrl(@RequestParam("keyword") String keyword)
	  {
		 return analysisDao.topUrl(keyword);
	  }

	 @PostMapping(value="/topSource")
	 @ResponseBody	
	 public ArrayList<StatsCountVo> topSource(@RequestParam("keyword") String keyword)
	  {
		 return analysisDao.topSource(keyword);
	  }

	 @PostMapping(value="/topUsers")
	 @ResponseBody	
	 public ArrayList<StatsCountVo> topUsers(@RequestParam("keyword") String keyword)
	  {
		 return analysisDao.topUsers(keyword);
	  }

	 
	 @PostMapping(value="/topUsersInfluencers")
	 @ResponseBody	
	 public ArrayList<StatsCountVo> topUsersInfluencers(@RequestParam("keyword") String keyword)
	  {
		 return analysisDao.topUsersInfluencers(keyword);
	  }

	 
	 
	 @PostMapping(value="/topLocaions")
	 @ResponseBody	
	 public ArrayList<StatsCountVo> topLocaions(@RequestParam("keyword") String keyword)
	  {
		 return analysisDao.topLocaions(keyword);
	  }

	 @PostMapping(value="/totalTweetsCount")
	 @ResponseBody	
	   public int totalTweetsCount(@RequestBody QueryParameterVo parameterVo)
	   {
		 //System.out.println("parameterVo::::"+parameterVo.toString());
		     return analysisDao.totalTweetsCount(parameterVo);
	   }

	 @PostMapping(value="/totalTimeFrame")
	 @ResponseBody	
	   public long totalTimeFrame(@RequestBody QueryParameterVo parameterVo)
	   {
		     return analysisDao.totalTimeFrame(parameterVo);
	   }

	 @PostMapping(value="/totalReach")
	 @ResponseBody	
	   public long totalReach(@RequestBody QueryParameterVo parameterVo)
	   {
		     return analysisDao.totalReach(parameterVo);
	   }

	 @PostMapping(value="/totalImpression")
	 @ResponseBody	
	   public long totalImpression(@RequestBody QueryParameterVo parameterVo)
	   {
		     return analysisDao.totalImpression(parameterVo);
	   }

	 @PostMapping(value="/totalRt")
	 @ResponseBody	
	   public long totalRt(@RequestBody QueryParameterVo parameterVo)
	   {
		     return analysisDao.totalRt(parameterVo);
	   }
	 
	   @PostMapping(value="/totalFavorite")
	   @ResponseBody	
	   public long totalFavorite(@RequestBody QueryParameterVo parameterVo)
	   {
		     return analysisDao.totalFavorite(parameterVo);
	   }
	 
	   @PostMapping(value="/totalReplies")
	   @ResponseBody	
	   public long totalReplies(@RequestBody QueryParameterVo parameterVo)
	   {
		     return analysisDao.totalReplies(parameterVo);
	   }

	   @PostMapping(value="/showTweet")
	   @ResponseBody	
	   public ArrayList<TwitterVo> showTweet(@RequestBody QueryParameterVo parameterVo)
	   {
		     return analysisDao.showTweet(parameterVo);
	   }

		 @PostMapping(value="/tweetBySentiment")
		 @ResponseBody	
		 public ArrayList<StatsCountVo> tweetBySentiment(@RequestBody QueryParameterVo parameterVo)
		  {
			 return analysisDao.tweetBySentiment(parameterVo);
		  }

		 @PostMapping(value="/tweetBySource")
		 @ResponseBody	
		 public ArrayList<StatsCountVo> tweetBySource(@RequestBody QueryParameterVo parameterVo)
		  {
			 return analysisDao.tweetBySource(parameterVo);
		  }

		 
		 @PostMapping(value="/tweetByType")
		 @ResponseBody	
		 public ArrayList<StatsCountVo> tweetByType(@RequestBody QueryParameterVo parameterVo)
		  {
			 return analysisDao.tweetByType(parameterVo);
		  }
	   
}
