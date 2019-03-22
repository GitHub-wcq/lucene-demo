package com.wcq.lucene.document;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneUtils {
	
	public static Directory directory = null;
	public static IndexWriterConfig conf = null;
	public static Version matchVersion = null;
	public static Analyzer analyzer = null;
	
	static {
		try {
			directory = FSDirectory.open(new File("E://indexDir/news"));
			matchVersion = Version.LUCENE_44;
			//该分词器是单字分词器
			analyzer = new StandardAnalyzer(matchVersion);
			conf = new IndexWriterConfig(matchVersion, analyzer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取版本信息
	 * @return
	 */
	public static Version getVersion() {
		return matchVersion;
	}
	/**
	 * 获取分词器
	 * @return
	 */
	public static Analyzer getAnalyzer() {
		return analyzer;
	}
	/**
	 * 获取操作索引的对象
	 * @return
	 * @throws IOException
	 */
	public static IndexWriter getIndexWriter() throws IOException {
		IndexWriter indexWriter = new IndexWriter(directory, conf);
		return indexWriter;
	}
	/**
	 * 获取读取索引的对象
	 * @return
	 * @throws IOException
	 */
	public static IndexSearcher getIndexSearcher() throws IOException {
		IndexReader indexReader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		return indexSearcher;
	}

}
