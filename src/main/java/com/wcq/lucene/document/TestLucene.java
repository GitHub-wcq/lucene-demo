package com.wcq.lucene.document;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class TestLucene {
	
	/**
	 * 使用IndexWriter对数据创建索引
	 * @throws IOException 
	 */
	public void testCreateIndex() throws IOException {
		//索引存放的位置
		Directory directory = FSDirectory.open(new File("E://indexDir/"));
		//lucene的版本Version.LUCENE_36
		//文本分析器
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
		
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_44, analyzer);
		
		//IndexWriter writes new index files to the directory
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
		
		//索引要遵循的数据结构document
		Document document = new Document();
		//1.字段的名称 2.该字段的值 3.字段在数据库中是否存储
		IndexableField field = new IntField("id", 1, Store.YES);
		IndexableField title = new StringField("title","java培训零基础，从入门到精通",Store.YES);
		IndexableField content = new TextField("content","java培训，中软国际独创实训模式，三免一终身，学java多项保障让您无后顾之忧。中软国际java培训，全日制教学，真实项目实战，名企定制培训，四个月速成java工程师!",Store.YES);
		document.add(field);
		document.add(title);
		document.add(content);
		indexWriter.addDocument(document);
		indexWriter.close();
		
	}
	
	public void testSearcher() throws IOException {
		//索引存放的位置
		Directory d = FSDirectory.open(new File("E://indexDir/"));
		//通过indexSearcher去检索索引目录
		IndexReader indexReader = DirectoryReader.open(d);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		//根据搜索条件进行查找
		//term是根据哪个字段进行检索,以及字段对应值.注意：这样是查询不出，只有单字才能查询出来
		Query query = new TermQuery(new Term("context", "培训"));
		
		//先搜索索引目录,找到符合条件的前100tiao数据
		TopDocs topDocs = indexSearcher.search(query, 100);
		System.out.println("总记录数:" + topDocs.totalHits);
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		for(ScoreDoc scoreDoc : scoreDocs) {
			//得分采用VSM算法
			System.out.println("相关度得分:" + scoreDoc.score);
			//获取查询文档的唯一编号,只有获取文档的唯一编号，才能获取该编号对应的数据
			int doc = scoreDoc.doc;
			Document document = indexSearcher.doc(doc);
			System.out.println(document.get("id"));
			System.out.println(document.get("title"));
			System.out.println(document.get("content"));
			
		}
		
	}
	

}
