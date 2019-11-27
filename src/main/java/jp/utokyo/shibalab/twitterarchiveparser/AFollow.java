package jp.utokyo.shibalab.twitterarchiveparser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class for follower
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public abstract class AFollow implements ICsv {
	/* ==============================================================
	 * instance fields
	 * ============================================================== */

	private FollowContent _content;
	
	
	protected AFollow(FollowContent content) {
		_content = content;
	}
	

	/* ==============================================================
	 * instance methods
	 * ============================================================== */
	/**
	 * get account ID
	 * @return account ID
	 */
	public String getAccountId() {
		return _content._accountId;
	}
	
	/**
	 * get user link
	 * @return user link 
	 */
	public String getUserLink() {
		return _content._userLink;
	}
	
	@Override
	public String toCsvString() { 
		return toCsvString("\t");
	}
	
	@Override
	public String toCsvString(String delim) { 
		return String.format("%s%s%s", getAccountId(), delim, getUserLink());
	}
	
	@Override
	public String toString() {
		return toCsvString();
	}
	
	
	protected static class FollowContent {
		/** account ID */
		@JsonProperty("accountId")
		private String _accountId;
		
		/** user link */
		@JsonProperty("userLink")
		private String _userLink;
	}
}
