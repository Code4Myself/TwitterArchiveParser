package jp.utokyo.shibalab.twitterarchiveparser;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.SPACE;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class Tweet implements Comparable<Tweet>, ICsv {
	
	protected static List<String> MONTH = Arrays.asList(new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"});


	/* ==============================================================
	 * static methods
	 * ============================================================== */
	
	protected static Date parseDate(String timeString) { 
		//  "created_at" : "Mon Oct 14 04:28:46 +0000 2019",
		String[] tokens   = StringUtils.split(timeString,SPACE);
		int      monthIdx = MONTH.indexOf(tokens[1]);
		
		String   timeString2 = String.format("%s-%02d-%s %s %s", tokens[5], monthIdx+1, tokens[2], tokens[3], tokens[4]);
		
		ZonedDateTime ldt = ZonedDateTime.parse(timeString2, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss Z"));
		return  Date.from( ldt.toInstant() );
	}
	
	/**
	 * get CSV header
	 * @return csv header line
	 */
	public static String getCsvHeader() { 
		return getCsvHeader("\t");
	}

	/**
	 * get CSV header 	
	 * @param delim delimiter 
	 * @return csv header line
	 */
	public static String getCsvHeader(String delim) { 
		String[] tokens = new String[] {
			"retweeted",
			"source", 
			"displayTextRange",
			"favoriteCount",
			"idStr",
			"truncated",
			"retweetCount",
			"id",
			"possiblySensitive",
			"createdAt",
			"favorited",
			"fullText",
			"lang"
		};
		return StringUtils.join(tokens,delim);
	}
	
	
	private Boolean   _retweeted;
	
	private String    _source;
	
	private Entities  _entities;
	
	private Integer[] _displayTextRange;
	
	private Integer   _favoriteCount;
	
	private String    _idStr;
	
	private Boolean   _truncated;
	
	private Integer   _retweetCount;
	
	private Long      _id;
	
	private Boolean   _possiblySensitive;
	
	private Date      _createdAt;
	
	private Boolean   _favorited;
	
	private String    _fullText;
	
	private String    _lang;
	
//	private Entities  _extendedEntities;
	
	@JsonCreator
	private Tweet(
			@JsonProperty("retweeted")          Boolean   retweeted,
			@JsonProperty("source")             String    source, 
			@JsonProperty("entities")           Entities  entities,
			@JsonProperty("display_text_range") Integer[] displayTextRange, 
			@JsonProperty("favorite_count")     Integer   favoriteCount,
			@JsonProperty("id_str")             String    idStr,
			@JsonProperty("truncated")          Boolean   truncated,
			@JsonProperty("retweet_count")      Integer   retweetCount,
			@JsonProperty("id")                 Long      id, 
			@JsonProperty("possibly_sensitive") Boolean   possiblySensitive, 
			@JsonProperty("created_at")         String    createdAt, 
			@JsonProperty("favorited")          Boolean   favorited, 
			@JsonProperty("full_text")          String    fullText,
			@JsonProperty("lang")               String    lang
//			@JsonProperty("extended_entities")  Entities  extendedEntities
	)
	{
		_retweeted         = retweeted;
		_source            = source;
		_entities          = entities;
		_displayTextRange  = displayTextRange;
		_favoriteCount     = favoriteCount;
		_idStr             = idStr;
		_truncated         = truncated;
		_retweetCount      = retweetCount;
		_id                = id;
		_possiblySensitive = possiblySensitive;
		_favorited         = favorited;
		_fullText          = fullText;
		_lang              = lang;
//		_extendedEntities  = extendedEntities;
		
		//  "created_at" : "Mon Oct 14 04:28:46 +0000 2019",
		_createdAt  = parseDate(createdAt);
	}
	
	
	
	public Date getCreatedAt() {
		return _createdAt;
	}
	
	@Override
	public String toCsvString() {
		return toCsvString("\t");
	}
	
	@Override
	public String toCsvString(String delim) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
		String[] tokens = new String[] {
			_retweeted != null         ? String.valueOf(_retweeted)              : EMPTY, 
			_source    != null         ? _source                                 : EMPTY, 
//			_entities  != null         ? _entities.toString()                    : EMPTY, // TODO
			_displayTextRange != null  ? StringUtils.join(_displayTextRange,"|") : EMPTY, 
			_favoriteCount != null     ? String.valueOf(_favoriteCount)          : EMPTY, 
			_idStr != null             ? _idStr                                  : EMPTY, 
			_truncated != null         ? String.valueOf(_truncated)              : EMPTY, 
			_retweetCount != null      ? String.valueOf(_retweetCount)           : EMPTY, 
			_id != null                ? String.valueOf(_id)                     : EMPTY, 
			_possiblySensitive != null ? String.valueOf(_possiblySensitive)      : EMPTY, 
			_createdAt != null         ? sdf.format(_createdAt)                  : EMPTY,
			_favorited != null         ? String.valueOf(_favorited)              : EMPTY, 
			_fullText != null          ? _fullText.replaceAll("\n","\\\\n")       : EMPTY, 
			_lang != null              ? _lang                                   : EMPTY, 
//			_extendedEntities != null  ? _extendedEntities.toString()            : EMPTY, // TODO
		};
		
		return StringUtils.join(tokens,delim);
	}
	
	@Override
	public String toString() {
		return toCsvString();
	}
	
	@Override
	public int compareTo(Tweet obj) {
		if( obj == null ) { 
    		return 1;
    	}
    	if( obj.getCreatedAt() == null ) {
    		return 1;
    	}
    	if( this == obj ) {
    		return 0;
    	}
    	if( getCreatedAt() == null ) {
    		return -1;
    	}
    	
    	return getCreatedAt().compareTo(obj.getCreatedAt());
	}
	
	
	
	
	
	
	
	private static class Entities {
		
		@JsonProperty("hashtags")
		private Hashtag[] _hashtags;
		
		@JsonProperty("symbols")
		private Symbol[]  _symbols;
		
		@JsonProperty("media")
		private Media[] _medias;
		
		@JsonProperty("user_mentions")
		private UserMention[] _userMentions;
		
		@JsonProperty("urls")
		private Url[]         _urls;
	}
	
	
	private static abstract class Indices {
		@JsonProperty("indices")
		private Integer[] _indices;
		
		public Integer[] getIndices() {
			return _indices;
		}
	}
	
	private static class Hashtag extends Indices {
		@JsonProperty("text")
		private String    _text;
		
		public String getText() {
			return _text;
		}
	}
	
	private static class Symbol extends Indices {
		// ???
	}
	
	private static class Media extends Indices {
		@JsonProperty("expanded_url")
		private String _expandedUrl;
		@JsonProperty("source_status_id")
		private Long   _sourceStatusId;
		@JsonProperty("url")
		private String _url;
		@JsonProperty("media_url")
		private String _mediaUrl;
		@JsonProperty("id_str")
		private String _idStr;
		@JsonProperty("source_user_id")
		private Long   _sourceUserId;
		@JsonProperty("id")
		private Long   _id;
		@JsonProperty("media_url_https")
		private String _mediaUrlHttps;
		@JsonProperty("source_user_id_str")
		private String _sourceUserIdStr;
		@JsonProperty("sizes")
		private Sizes _sizes;
		@JsonProperty("type")
		private String _type;
		@JsonProperty("source_status_id_str")
		private String _sourceStatusIdStr;
		@JsonProperty("display_url")
		private String _displayUrl;
	}
	
	private static class Sizes {
		@JsonProperty("thumb")
		private Size _thumb;
		@JsonProperty("large")
		private Size _large;
		@JsonProperty("medium")
		private Size _medium;
		@JsonProperty("small")
		private Size _small;
	}
	
	private static class Size {
		@JsonProperty("w")
		private Integer _w;
		@JsonProperty("h")
		private Integer _h;
		@JsonProperty("resize")
		private String _resize;
	}
	
	
	private static class UserMention extends Indices {
		@JsonProperty("name")
		private String _name;
		@JsonProperty("screen_name")
		private String _screenName;
		@JsonProperty("id_str")
		private String _idStr;
		@JsonProperty("id")
		private Long   _id;
		
		public String getName() {
			return _name;
		}
		public String getScreenName() {
			return _screenName;
		}
		public String getIdStr() {
			return _idStr;
		}
		public Long getId() {
			return _id;
		}
	}
	
	private static class Url extends Indices {
		@JsonProperty("url")
		private String _url;
		@JsonProperty("expanded_url")
		private String _expandedUrl;
		@JsonProperty("display_url")
		private String _displayUrl;
		
		public String getUrl() {
			return _url;
		}
		public String getExpandedUrl() {
			return _expandedUrl;
		}
		public String getDisplayUrl() {
			return _displayUrl;
		}
	}
	
}
