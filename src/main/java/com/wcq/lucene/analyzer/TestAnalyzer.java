package com.wcq.lucene.analyzer;

import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;
/**
	在创建索引时会用到分词器，在使用字符串搜索时也会用到分词器，这两个地方要使用同一个分词器，否则可能会搜索不出结果。
	Analyzer（分词器）的作用是把一段文本中的词按规则取出所包含的所有词。
	对应的是Analyzer类，这是一个抽象类，切分词的具体规则是由子类实现的，所以对于不同的语言（规则），要用不同的分词器。
	一段文本 ===> 按照规则使用分词器切分 ===> 使用出现的所有分词
 * @author Administrator
 *
 */
public class TestAnalyzer {
	
	public static void main(String[] args) {
		String text = "腾讯网(www.QQ.com)是中国浏览量最大的中文门户网站,是腾讯公司推出的集新闻信息、互动社区、娱乐产品和基础服务为一体的大型综合门户网站。腾讯网服务于全球华人...";
//		standardAnalyzer(text);
//		CJKAnalyzer(text);
		IKAnalyzer(text);
	}
	/**
	 * 单字分词器
	 */
	public static void standardAnalyzer(String text) {
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
		testAnalyzer(analyzer, text);
	}
	/**
	 * 二分法分词器
	 */
	public static void CJKAnalyzer(String text) {
		Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_44);
		testAnalyzer(analyzer, text);
	}
	/**
	 * 第三方的中文分词器，庖丁分词，中文分词，特点：扩展新的词，自定义停用词
	 */
	public static void IKAnalyzer(String text) {
		Analyzer analyzer = new IKAnalyzer();
		testAnalyzer(analyzer, text);
	}
	
	private static void testAnalyzer(Analyzer analyzer, String text) {
		System.out.println("当前使用的分词器是: " + analyzer.getClass().getSimpleName());
		try {
			TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(text));
			tokenStream.addAttribute(CharTermAttribute.class);
			tokenStream.reset();
			while(tokenStream.incrementToken()) {
				CharTermAttribute charTermAttribute = tokenStream.getAttribute(CharTermAttribute.class);
				System.out.println(new String(charTermAttribute.toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
