package jp.utokyo.shibalab.twitterarchiveparser;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * class for following
 */
public class Following extends AFollow {
	@JsonCreator
	protected Following(@JsonProperty("following") FollowContent content) {
		super(content);
	}
}
