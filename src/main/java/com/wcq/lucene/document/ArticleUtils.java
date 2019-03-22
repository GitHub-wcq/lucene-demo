package com.wcq.lucene.document;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

public class ArticleUtils {

	public static Document articleToDocument(Article article) {
		Document document = new Document();
		IntField idField = new IntField("id", article.getId(), Store.YES);
		//StringField不进行分词（整体算一个）
		StringField authoField = new StringField("author", article.getAuthor(), Store.YES);
		StringField linkField = new StringField("link", article.getLink(), Store.YES);
		//TextField进行分词
		TextField titleField = new TextField("title", article.getTitle(), Store.YES);
		//这里可以添加权重值，默认是1f，添加之后检索的时候排名就会靠前
		titleField.setBoost(article.getId());
		TextField contentField = new TextField("content", article.getContent(), Store.YES);
		document.add(idField);
		document.add(authoField);
		document.add(linkField);
		document.add(titleField);
		document.add(contentField);
		return document;
	}
	
	public static Article documentToArticle(Document document) {
		Article article = new Article();
		article.setId(Integer.valueOf(document.get("id")));
		article.setAuthor(document.get("author"));
		article.setLink(document.get("link"));
		article.setTitle(document.get("title"));
		article.setContent(document.get("content"));
		return article;
	}
	
}
