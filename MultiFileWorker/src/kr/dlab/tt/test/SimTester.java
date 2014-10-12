package kr.dlab.tt.test;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
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

public class SimTester {
	
	private static final SimpleDateFormat dbformat = 
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	private static ConfigLoader cl = ConfigLoader.getInstance();
		
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		
		String pttern = readUserDictionary();
		System.out.println(pttern);
		
		String s1 = "row MBC 伦敦奥运 少女时... 01:32 【中字】120508onstyle_f(x)... 03:30 胡书涵_Caelyn 的VCR 00:45 [花絮]120726.Official CeCi... 01:54 120803 「GiRL DE PROVENCE... 06:19 少女时代120722 LG Cinema 3... 02:09 120719 少女時代-The Boys K... 03:50 少女时代120722 LG 家庭影院... 00:32 120719 MBC SMTOWN in LA 少... 28:11 【LIVE】120719.MBC.SM Town... 29:57 120724 MBC 奥运会宣传视频 ... 00:51 少女时代120728 西卡.Yuri.... 00:54 视频信息 了解更多 wo4fc 上传于 了解更多 频道： 娱乐 标签： 少女时代 CF LG 3D 挖： 埋： 评论： 私藏： 站外播放： 播放数: 最热推荐 李宗瑞案13秒不雅视频曝光 李母疑藏匿通缉犯遭传唤 台名嘴曝李宗瑞淫照由京城四少之一外泄 李宗瑞案不雅照外泄疯传 吴亚馨被证为淫照案受害者 警方悬赏追捕李宗瑞 众女星纷纷澄清以表清白 播客信息 wo4fc 豆花6级 更多该播客上传的视频 >> 120805.Fanmade.Now & Forever.五... 12:34 120803.Fanmade.日SONE制作五周年... 04:38 120719.MBC.Korean.Music.Wave.SMT... 1:28:30 土豆推广 土豆网 排行榜 频道 创新产品 软件 帮助 公司 原创 电视剧 电影 综艺 动漫 热点 财富 汽车 科技 体育 娱乐 音乐 游戏 搞笑 风尚 美容 女性 乐活 健康 教育 城市 Channel豆 推广场 飞速土豆 iTudou 无线客户端 帮助反馈 玩转土豆 繁體版 关于土豆 About Us 土豆新闻 招贤纳士 联系土豆 合作伙伴 媒体声音 使用土豆前必读 Copyright © 2005-2012 土豆网 (www.tudou.com) 沪ICP证： 沪B2-20120009   网络视听许可证： 0908301 广播电视节目制作经营许可证： (沪)字第678号 沪公网安备：3101040256 药品服务许可证： (沪)-非经营性-2008-0051 网络文化经营许可证： 沪网文[2012]0101-015 互联网医疗卫生许可证： 沪卫(中医)网审[2012]第10015号 请使用者仔细阅读土豆 使用协议 和 版权政策 中国互联网违法和不良信息举报中心 上海市举报中心 网络违法犯罪举报网站 “扫黄打非”办公室举报中心：12390 一定要开启浏览器的 JavaScript 支持喔！否则不能正常使用土豆～ 启用之后，刷新页面就可以看啦。 有问题请 联系客服 如果你无法播放此视频, 请先确认是否安装了 Flash Player 有问题请 联系客服 安装Flash Player 你的Flash版本低于 9.0.115，点击“自动升级”更新到最新版 有问题请 联系客服 自动升级";
		String s2 = "삼성전자 와 일하니, 삼성전자가 가구저널은 대체 뭐하는겨제기랄 별삼전자는 바로저기서 삼송전자는 워디서 삼 송전자따위가 배은망덕하게시리";
		
		Pattern keywordsPattern = Pattern.compile(pttern);
		Matcher matcher = keywordsPattern.matcher(s2);
		
		int  k = 0;
		while ( matcher.find() ) {
			System.out.println(k++);
//			System.out.println(matcher.groupCount());
			System.out.println(matcher.group());
//			System.out.println(matcher.regionStart());
//			System.out.println(matcher.regionEnd());

			
//			System.out.println(matcher.group(1));
//			System.out.println(matcher.group(2));
//			System.out.println(matcher.group(3));
//			
//			System.out.println(s2.substring(matcher.start(), matcher.end() )) ;
			for  ( int i =0; i < matcher.groupCount(); i++ ) {
				
				if ( matcher.group(i) != null ) {
					System.out.println("group idx : " + i + " " + matcher.group(i));
				}
			}
		}
		
		
//		System.out.println(matcher.groupCount());
//		System.out.println(matcher.group(0));
//		System.out.println(matcher.group(1));
//		System.out.println(matcher.group(2));
//		System.out.println(matcher.group(3));
//		System.out.println(matcher.group(4));
//		System.out.println(matcher.group(5));
//		System.out.println(matcher.group(6));

		
		
	}
	
	/**
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static String readUserDictionary() throws IOException,
			FileNotFoundException {
		//user dictionary
		File userDic = new File(cl.getUserDictionary());
		CSVParser userDicParser = new CSVParser(new FileReader(userDic), CSVFormat.RFC4180);
		List<CSVRecord> dicList = userDicParser.getRecords();
		
		List <String> tokens = new ArrayList<String>();
		
		for ( int i = 1; i < dicList.size(); i++ ) {
			String keyword = ((CSVRecord)dicList.get(i)).get(1);
			tokens.add("(" + keyword + ").*?");
		}
		
		String patternStr = "\\b(" + StringUtils.join(tokens, "|") + ")\\b";
		Pattern ptrn = Pattern.compile(patternStr);
		return patternStr;
	}
}
