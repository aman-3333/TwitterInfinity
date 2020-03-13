package com.twitter.infinity.vo;

import java.util.ArrayList;

public class QueryParameterVo {
    public String keyword;
    public boolean hasPlainTweet;
    public boolean hasReTweet;
    public boolean hasReplies;
    public boolean hasMention;
    public boolean hasPicture;
    public boolean hasVideo;
    public boolean hasLink;
    public boolean hasVerified;
    public boolean hasUsers;
    public boolean hasHashTag;

    public ArrayList<String> sentimentFilterList;
    public ArrayList<String> languageFilterList;
    public ArrayList<String> topContributerFilterList;
    public ArrayList<String> topInfluencerFilterList;
    public ArrayList<String> topHashtagFilterList;
    public ArrayList<String> mentionFilterList;
    public ArrayList<String> domainFilterList;
    public ArrayList<String> urlFilterList;
    public ArrayList<String> twitterSourceFilterList;
    public ArrayList<String> userLocationFilterList;
    public String sortTweet;
    
    
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public boolean isHasPlainTweet() {
		return hasPlainTweet;
	}
	public void setHasPlainTweet(boolean hasPlainTweet) {
		this.hasPlainTweet = hasPlainTweet;
	}
	public boolean isHasReTweet() {
		return hasReTweet;
	}
	public void setHasReTweet(boolean hasReTweet) {
		this.hasReTweet = hasReTweet;
	}
	public boolean isHasReplies() {
		return hasReplies;
	}
	public void setHasReplies(boolean hasReplies) {
		this.hasReplies = hasReplies;
	}
	public boolean isHasMention() {
		return hasMention;
	}
	public void setHasMention(boolean hasMention) {
		this.hasMention = hasMention;
	}
	public boolean isHasPicture() {
		return hasPicture;
	}
	public void setHasPicture(boolean hasPicture) {
		this.hasPicture = hasPicture;
	}
	public boolean isHasVideo() {
		return hasVideo;
	}
	public void setHasVideo(boolean hasVideo) {
		this.hasVideo = hasVideo;
	}
	public boolean isHasLink() {
		return hasLink;
	}
	public void setHasLink(boolean hasLink) {
		this.hasLink = hasLink;
	}
	public boolean isHasVerified() {
		return hasVerified;
	}
	public void setHasVerified(boolean hasVerified) {
		this.hasVerified = hasVerified;
	}
	public boolean isHasUsers() {
		return hasUsers;
	}
	public void setHasUsers(boolean hasUsers) {
		this.hasUsers = hasUsers;
	}
	public boolean isHasHashTag() {
		return hasHashTag;
	}
	public void setHasHashTag(boolean hasHashTag) {
		this.hasHashTag = hasHashTag;
	}
	public ArrayList<String> getSentimentFilterList() {
		return sentimentFilterList;
	}
	public void setSentimentFilterList(ArrayList<String> sentimentFilterList) {
		this.sentimentFilterList = sentimentFilterList;
	}
	public ArrayList<String> getLanguageFilterList() {
		return languageFilterList;
	}
	public void setLanguageFilterList(ArrayList<String> languageFilterList) {
		this.languageFilterList = languageFilterList;
	}
	public ArrayList<String> getTopContributerFilterList() {
		return topContributerFilterList;
	}
	public void setTopContributerFilterList(ArrayList<String> topContributerFilterList) {
		this.topContributerFilterList = topContributerFilterList;
	}
	public ArrayList<String> getTopInfluencerFilterList() {
		return topInfluencerFilterList;
	}
	public void setTopInfluencerFilterList(ArrayList<String> topInfluencerFilterList) {
		this.topInfluencerFilterList = topInfluencerFilterList;
	}
	public ArrayList<String> getTopHashtagFilterList() {
		return topHashtagFilterList;
	}
	public void setTopHashtagFilterList(ArrayList<String> topHashtagFilterList) {
		this.topHashtagFilterList = topHashtagFilterList;
	}
	public ArrayList<String> getMentionFilterList() {
		return mentionFilterList;
	}
	public void setMentionFilterList(ArrayList<String> mentionFilterList) {
		this.mentionFilterList = mentionFilterList;
	}
	public ArrayList<String> getDomainFilterList() {
		return domainFilterList;
	}
	public void setDomainFilterList(ArrayList<String> domainFilterList) {
		this.domainFilterList = domainFilterList;
	}
	public ArrayList<String> getUrlFilterList() {
		return urlFilterList;
	}
	public void setUrlFilterList(ArrayList<String> urlFilterList) {
		this.urlFilterList = urlFilterList;
	}
	public ArrayList<String> getTwitterSourceFilterList() {
		return twitterSourceFilterList;
	}
	public void setTwitterSourceFilterList(ArrayList<String> twitterSourceFilterList) {
		this.twitterSourceFilterList = twitterSourceFilterList;
	}
	public ArrayList<String> getUserLocationFilterList() {
		return userLocationFilterList;
	}
	public void setUserLocationFilterList(ArrayList<String> userLocationFilterList) {
		this.userLocationFilterList = userLocationFilterList;
	}
	
	
	public String getSortTweet() {
		return sortTweet;
	}
	public void setSortTweet(String sortTweet) {
		this.sortTweet = sortTweet;
	}
	@Override
	public String toString() {
		return "QueryParameterVo [keyword=" + keyword + ", hasPlainTweet=" + hasPlainTweet + ", hasReTweet="
				+ hasReTweet + ", hasReplies=" + hasReplies + ", hasMention=" + hasMention + ", hasPicture="
				+ hasPicture + ", hasVideo=" + hasVideo + ", hasLink=" + hasLink + ", hasVerified=" + hasVerified
				+ ", hasUsers=" + hasUsers + ", hasHashTag=" + hasHashTag + ", sentimentFilterList="
				+ sentimentFilterList + ", languageFilterList=" + languageFilterList + ", topContributerFilterList="
				+ topContributerFilterList + ", topInfluencerFilterList=" + topInfluencerFilterList
				+ ", topHashtagFilterList=" + topHashtagFilterList + ", mentionFilterList=" + mentionFilterList
				+ ", domainFilterList=" + domainFilterList + ", urlFilterList=" + urlFilterList
				+ ", twitterSourceFilterList=" + twitterSourceFilterList + ", userLocationFilterList="
				+ userLocationFilterList + ", sortTweet=" + sortTweet + ", getKeyword()=" + getKeyword()
				+ ", isHasPlainTweet()=" + isHasPlainTweet() + ", isHasReTweet()=" + isHasReTweet()
				+ ", isHasReplies()=" + isHasReplies() + ", isHasMention()=" + isHasMention() + ", isHasPicture()="
				+ isHasPicture() + ", isHasVideo()=" + isHasVideo() + ", isHasLink()=" + isHasLink()
				+ ", isHasVerified()=" + isHasVerified() + ", isHasUsers()=" + isHasUsers() + ", isHasHashTag()="
				+ isHasHashTag() + ", getSentimentFilterList()=" + getSentimentFilterList()
				+ ", getLanguageFilterList()=" + getLanguageFilterList() + ", getTopContributerFilterList()="
				+ getTopContributerFilterList() + ", getTopInfluencerFilterList()=" + getTopInfluencerFilterList()
				+ ", getTopHashtagFilterList()=" + getTopHashtagFilterList() + ", getMentionFilterList()="
				+ getMentionFilterList() + ", getDomainFilterList()=" + getDomainFilterList() + ", getUrlFilterList()="
				+ getUrlFilterList() + ", getTwitterSourceFilterList()=" + getTwitterSourceFilterList()
				+ ", getUserLocationFilterList()=" + getUserLocationFilterList() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}


}
