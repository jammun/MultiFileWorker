/* 
 *  Copyleft (C) 2014 Jinho Lee
 *  All rights reserved.
 * 
 *  THIS SOFTWARE IS PROVIDED BY JINHO LEE ''AS IS'' AND ANY EXPRESS OR 
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN
 *  NO EVENT SHALL KYLE GORMAN BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 *  TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 *  SimDetector : make duplication check program in java
 *  @author Jinho Lee <jammun@gmail.com>, <jhlee@dlab.kr>
 * 
 * 
 */  
package kr.dlab.tt.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import kr.dlab.tt.config.ConfigLoader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class SimDetector {

	//Configuration Loader
	private static final ConfigLoader cl = ConfigLoader.getInstance();
	
	//initialize Logger
	private static final Logger myLoger = Logger.getLogger(SimDetector.class);
	
	public SimDetector(String[] args) throws FileNotFoundException, IOException {
		super();
		doJob(args);
	}


	private void doJob(String[] args) throws FileNotFoundException, IOException {
		
		File rawFile = new File(cl.getLawDataFile());
		
		//raw data 
		CSVParser parser = new CSVParser(new FileReader(rawFile), CSVFormat.RFC4180);
		List<CSVRecord> list = parser.getRecords();
		long rowCnt = list.size() - 1; //save for first record
		double rowsPerThread = Math.ceil(rowCnt / (float)cl.getThreadNumbers());
		
		String patternStr = readUserDictionary();
		Pattern pattern = Pattern.compile(patternStr);

		

		System.out.println(rowCnt + " / " + rowsPerThread);
		System.out.println(patternStr);
		
		
		long[] idxs = new long[cl.getThreadNumbers()];
		
		//if rawdata has header, start with 1
		long point = 1;
		
		for ( int i = 0; i < idxs.length; i++ ) {
			idxs[i] = point;
			point = point + (long)rowsPerThread;
		}
		System.out.println();
		
		RowWorker[] threads = new RowWorker[cl.getThreadNumbers()];
		
		for ( int i = 0; i < threads.length; i++ ) {
			RowWorker worker = new RowWorker("["+i+"]", list, idxs[i], (long)rowsPerThread-1, pattern);
			threads[i] = worker;
			worker.start();
		}
		
		
		
		
		
	}


	/**
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private String readUserDictionary() throws IOException,
			FileNotFoundException {
		//user dictionary
		File userDic = new File(cl.getUserDictionary());
		CSVParser userDicParser = new CSVParser(new FileReader(userDic), CSVFormat.RFC4180);
		List<CSVRecord> dicList = userDicParser.getRecords();
		
		List <String> tokens = new ArrayList<String>();
		
		for ( int i = 1; i < dicList.size(); i++ ) {
			String keyword = ((CSVRecord)dicList.get(i)).get(1);
			tokens.add(keyword);
		}
		
		String patternStr = "\\b(" + StringUtils.join(tokens, "(.+)|") + ")\\b";
		Pattern ptrn = Pattern.compile(patternStr);
		return patternStr;
	}
	
	
}
