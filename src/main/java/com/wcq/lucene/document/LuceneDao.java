package com.wcq.lucene.document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class LuceneDao {
	
	/**
	 * 通过IndexWriter对象增加索引
	 * @param article
	 */
	public void addIndex(Article article) {
		try {
			IndexWriter indexWriter = LuceneUtils.getIndexWriter();
			indexWriter.addDocument(ArticleUtils.articleToDocument(article));
			indexWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deleteIndex(String fieldName, String fieldValue) throws IOException {
		IndexWriter indexWriter = LuceneUtils.getIndexWriter();
		//使用词条删除
		Term term = new Term(fieldName,fieldValue);
		indexWriter.deleteDocuments(term);
		indexWriter.close();
	}
	/**
	 * 更新索引，先根据词条删除索引，然后再创建一条新的记录
	 * @param fieldName
	 * @param fieldValue
	 * @param article
	 * @throws IOException
	 */
	public void updateIndex(String fieldName, String fieldValue, Article article) throws IOException {
		IndexWriter indexWriter = LuceneUtils.getIndexWriter();
		Term term = new Term(fieldName,fieldValue);
		Document document = ArticleUtils.articleToDocument(article);
		indexWriter.updateDocument(term, document);
		indexWriter.close();
	}
	/**
	 * 通过IndexSearch查询（分页）
	 * @param keyWords
	 * @param start
	 * @param count
	 * @return
	 */
	public List<Article> findIndex(String keyWords, int start, int count) {
		try {
			IndexSearcher indexSearcher = LuceneUtils.getIndexSearcher();
			//这里是第二种query方式，不是termQuery
			QueryParser queryParser = new MultiFieldQueryParser(
					LuceneUtils.getVersion(), new String[] {"title","content"}, LuceneUtils.getAnalyzer());
			Query query = queryParser.parse(keyWords);
			TopDocs topDocs = indexSearcher.search(query, 100);
			System.out.println("总记录数：" + topDocs.totalHits);
			//表示返回的结果集
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			List<Article> list = new ArrayList<>();
			int min = Math.min(scoreDocs.length, start + count);
			for(int i = start; i < min; i++) {
				System.out.println("相关度得分：" + scoreDocs[i].score);
				int doc = scoreDocs[i].doc;
				Document document = indexSearcher.doc(doc);
				Article article = ArticleUtils.documentToArticle(document);
				list.add(article);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
