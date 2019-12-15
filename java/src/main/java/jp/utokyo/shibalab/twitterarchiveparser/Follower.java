package jp.utokyo.shibalab.twitterarchiveparser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * class for follower
 */
public class Follower extends AFollow {
	

	@JsonCreator
	protected Follower(@JsonProperty("follower") FollowContent content) {
		super(content);
	}
}
