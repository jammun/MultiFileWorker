/**
 * 
 */
package kr.dlab.tt.config;

import java.io.File;
import java.util.List;

import org.apache.commons.configuration.CombinedConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.DefaultConfigurationBuilder;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.log4j.Logger;

public class ConfigLoader {

	private static final ConfigLoader myInstance = new ConfigLoader();
	private static XMLConfiguration xmlConfig;
	
	private ConfigLoader() {
		configLoad();
	}
	
	public static ConfigLoader getInstance() {
		return myInstance;
	}
	
	private void configLoad() {
		
		try {
			
			DefaultConfigurationBuilder builder = new DefaultConfigurationBuilder ();
			builder.setFile( new File ("env.xml"));
			CombinedConfiguration conf = builder.getConfiguration(true);
			xmlConfig = (XMLConfiguration)conf.getConfiguration(0);

//			myLogger.info(xmlConfig.getString("sas.host"));
//			myLogger.info(xmlConfig.getString("sas.port"));
//			myLogger.info(xmlConfig.getString("sas.nTaggerPath[@value]"));
//			myLogger.info(xmlConfig.getString("sas.tmpTaggerFile[@value]"));
		
		} catch (Exception ce){
			ce.printStackTrace();
			System.exit(255);
		}
	}



	public String getLawDataFile() {
		return xmlConfig.getString("rawData");
	}
	public String getUserDictionary() {
		return xmlConfig.getString("userDictionary");
	}
	public String getLawDataType() {
		return xmlConfig.getString("dataType");
	}
	public int getThreadNumbers() {
		return xmlConfig.getInt("numberOfThreads",1);
	}
	
}
