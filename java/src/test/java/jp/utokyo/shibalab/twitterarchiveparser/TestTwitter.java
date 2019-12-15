package jp.utokyo.shibalab.twitterarchiveparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class TestTwitter {
	
	
	/** tweet parser Test */
	@Test
	@DisplayName("testTweet")
	public void testTweet() throws Exception {
		
		File inputFile = new File( "/Users/knsg/Documents/ibank/consortium2nd/temp/20191015.sunahara-data/twitter-2019-10-15-38589baaee6218ad584ddc4765e4711e76cb0842e2c9e49119ec55bbb6282564/tweet.js" );
		// Follower.class.getClassLoader().getResources("follower.js").nextElement().getFile() );

		StringBuffer buf = new StringBuffer("[{");
		try(BufferedReader br=Files.newBufferedReader(inputFile.toPath())) {
			String line = br.readLine();
			while ((line=br.readLine())!=null) {
				buf.append(line);
			}
		}
		catch(IOException exp) { 
			exp.printStackTrace();
		}
		
		
		long t0 = System.currentTimeMillis();
		try {
			// parse JSON data ////////////////////////////
			ObjectMapper jsonMapper = new ObjectMapper();
			Tweet[]      tweets  = jsonMapper.readValue(buf.toString(), Tweet[].class);
			
			// export results /////////////////////////////
			for(Tweet tweet:tweets) { 
				System.out.println(tweet);
			}
		}
		catch(JsonMappingException|JsonParseException exp) {
			exp.printStackTrace();
		}
		catch(IOException exp) { 
			exp.printStackTrace();
		}
		long t1 = System.currentTimeMillis();
		System.out.printf("time duration: %.03f (sec)\n", (t1-t0)/1000d);
	}
	
	
	/** follower parser Test */
	@Test
	@DisplayName("testFollower")
	public void testFollower() throws Exception {
		
		File inputFile = new File( Follower.class.getClassLoader().getResources("follower.js").nextElement().getFile() );
		// "/Users/knsg/Documents/ibank/consortium2nd/temp/20191015.sunahara-data/twitter-2019-10-15-38589baaee6218ad584ddc4765e4711e76cb0842e2c9e49119ec55bbb6282564/follower.js" );

		StringBuffer buf = new StringBuffer("[{");
		try(BufferedReader br=Files.newBufferedReader(inputFile.toPath())) {
			String line = br.readLine();
			while ((line=br.readLine())!=null) {
				buf.append(line);
			}
		}
		catch(IOException exp) { 
			exp.printStackTrace();
		}
		
		
		long t0 = System.currentTimeMillis();
		try {
			// parse JSON data ////////////////////////////
			ObjectMapper jsonMapper = new ObjectMapper();
			Follower[]   followers  = jsonMapper.readValue(buf.toString(), Follower[].class);
			
			// export results /////////////////////////////
			for(Follower follower:followers) {
				System.out.println(follower);
			}
		}
		catch(JsonMappingException|JsonParseException exp) {
			exp.printStackTrace();
		}
		catch(IOException exp) { 
			exp.printStackTrace();
		}
		long t1 = System.currentTimeMillis();
		System.out.printf("time duration: %.03f (sec)\n", (t1-t0)/1000d);
	}
}
