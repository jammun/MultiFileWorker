/**
 * 
 */
package kr.dlab.tt.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVRecord;

/**
 * @author Administrator
 *
 */
public class RowWorker extends Thread {

	private List rawList;
	private long myStartNo;
	private long quota;
	private long myEndNo;
	private long rawSize;
	
	private Pattern keywordsPattern;
	
	public RowWorker(String name, List rawList, long myStartNo, long quota,
			Pattern exactKeywords) {
//		System.out.println("constructor called");
		super(name);
		this.rawList = rawList;
		this.myStartNo = myStartNo;
		this.quota = quota;
		this.myEndNo = myStartNo + quota;
		this.rawSize = rawList.size()-1;
		
		if ( myEndNo > rawList.size() ) {
			myEndNo = rawSize;
		}
		this.keywordsPattern = exactKeywords;
		
	}
	

	/**
	 * @override
	 */	
	public void run() {
		
		System.out.println("RowWorker" + getName() + " work between" + myStartNo +"-"+ myEndNo );
		
		Matcher keywordMatcher = null;
		
		
		for ( long i = myStartNo; i <= myEndNo; i++) {
			
//			System.out.println("RowWorker" + getName() + " working  " + i + "/" + rawSize );
			
			CSVRecord record = (CSVRecord)rawList.get((int)i);
			String content = record.get(9);
			
			//System.out.println(record.get(9));
			
			keywordMatcher = keywordsPattern.matcher(content);
			
			if ( keywordMatcher.find() ) {
//				System.out.println("RowWorker" + getName() + " find pattern " + i + " row " + keywordMatcher.group(0));
				System.out.println("RowWorker" + getName() + " find pattern " + i + " row " + content.substring(keywordMatcher.start(), keywordMatcher.end()));
			}
		}
		
	}
	
	
	public List getRawList() {
		return rawList;
	}



	public void setRawList(List rawList) {
		this.rawList = rawList;
	}



	public long getMyStartNo() {
		return myStartNo;
	}



	public void setMyStartNo(long myStartNo) {
		this.myStartNo = myStartNo;
	}



	public long getQuota() {
		return quota;
	}



	public void setQuota(long quota) {
		this.quota = quota;
	}

}
