package jp.utokyo.shibalab.twitterarchiveparser;

import java.time.format.DateTimeFormatter;

/**
 * interface to export CSV data
 */
public interface ICsv {
	/* ==============================================================
	 * instance methods
	 * ============================================================== */
	/** Date time formatter */
	public static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	
	
	/* ==============================================================
	 * instance methods
	 * ============================================================== */
	/**
	 * to CsvString 
	 * @return csv string
	 */
	public String toCsvString();
	
	/**
	 * to CSV string 
	 * @param delim delimiter 
	 * @return csv string 
	 */
	public String toCsvString(String delim);
}