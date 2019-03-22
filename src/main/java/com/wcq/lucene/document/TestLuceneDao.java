package com.wcq.lucene.document;

import java.util.List;

import org.junit.Test;

public class TestLuceneDao {
	
	private LuceneDao dao = new LuceneDao();
	
	@Test
	public void addIndex() {
		for(int i = 0; i <= 25; i++) {
			Article article = new Article();
			article.setId(i);
			article.setTitle("腾讯qq");
			article.setAuthor("马化腾");
			article.setContent("腾讯网(www.QQ.com)是中国浏览量最大的中文门户网站,是腾讯公司推出的集新闻信息、互动社区、娱乐产品和基础服务为一体的大型综合门户网站。腾讯网服务于全球华人...");
			article.setLink("http://www.qq.com/");
			dao.addIndex(article);
		}
		System.out.println("end addIndex");
	}
	@Test
	public void findIndex() {
		String keyWords = "一";
		List<Article> list = dao.findIndex(keyWords, 0, 30);
		for(Article article : list) {
			System.out.println(article.getId());
			System.out.println(article.getAuthor());
			System.out.println(article.getContent());
            System.out.println(article.getTitle());
            System.out.println(article.getLink());
		}
		System.out.println("end findIndex");
	}
	@Test
	public void deleteIndex() {
		try {
			dao.deleteIndex("author", "马化腾");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("end deleteIndex");
	}
	@Test
	public void updateIndex() {
		String fieldName = "title";
		String fieldValue = "qq";
		Article article = new Article();
		article.setId(1);
		article.setAuthor("马化腾");
		article.setLink("http://www.baidu.com");
		article.setTitle("天下第一");
		article.setContent("天下第一一一一一一");
		try {
			dao.updateIndex(fieldName, fieldValue, article);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("end updateIndex");
	}
}
