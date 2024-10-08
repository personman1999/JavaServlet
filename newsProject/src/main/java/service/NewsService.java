package service;

import java.util.List;

import entity.NewsEntity;
import repository.NewsRepository;

public class NewsService {
	private NewsRepository newsRepository = new NewsRepository();
	
	public List<NewsEntity> getAllNews(){
		return newsRepository.findAll();
	}


}
