package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import config.NewsConnection;
import entity.NewsEntity;


public class NewsRepository {
	public List<NewsEntity> findAll() {
		List<NewsEntity> ListNews = new ArrayList<NewsEntity>();
		
		String query = "SELECT * FROM news";

		Connection connection = NewsConnection.getConnection();
		
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			
			while (result.next()) {
				NewsEntity newsEntity = new NewsEntity();
				newsEntity.setId(result.getInt("id"));
				newsEntity.setTitle(result.getString("title"));
				newsEntity.setContent(result.getString("content"));
				newsEntity.setImage_url(result.getString("image_url"));
				
				ListNews.add(newsEntity);
				
			}
		
			
		} catch (Exception e) {
			System.out.println("findAll : " + e.getLocalizedMessage());
		}
		
		return ListNews;
	}
}
