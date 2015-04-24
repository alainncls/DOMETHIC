package fr.epf.medfile.daos.news;

import java.util.List;

import fr.epf.medfile.models.News;

public interface NewsService {
    long addNews(int iD, int patientID, int authorID, String date, String title, String content);
    List<News> getNews();
    List<News> getNewsPatient(int idPatient);
    List<News> getNews(String service);
    News getNews(int id);
    boolean removeNews(int id);
    boolean clearNews();
    boolean isEmpty();
}
