/**
 * 
 */
package kr.dlab.tt.starter;

import java.io.FileNotFoundException;
import java.io.IOException;

import kr.dlab.tt.util.SimDetector;

/**
 * @author admin
 *
 */
public class SSDetectStarter {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		new SimDetector(args);
	}
}
