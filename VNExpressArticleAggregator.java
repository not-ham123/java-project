package com.fun.scrapescrapefx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
public class ArticleLoader 
  
{ 
 private Stage stage;
 private Scene scene;
 private Parent root;
  
 public List<Article> generateVnExpressArticleDataFromRSSLink(String rssFeedUrl) throws IOException 
       {
        ArrayList<Article> articleArrayList = new ArrayList<>();
        Document document = attemptToConnectToUrl(rssFeedUrl);
        int maximumNumberOfArticles = 50;
if (document != null) 
       {
            Elements itemsOnPageElements = document.select("item");

            for (int i = 0; i < Math.min(itemsOnPageElements.size(), maximumNumberOfArticles); i++) 
               {

                Element currentItem = itemsOnPageElements.get(i);


                String description = currentItem.select("description").text();
                if (!description.contains("img src=")) 
                {
                    continue;
                }

                Article articleToAdd = new Article();

                int startingIndexOfThumbnailLink = description.indexOf("img src=");
                int endingIndexOfThumbnailLink = description.indexOf("\" ", startingIndexOfThumbnailLink + 1);
                int offsetToRemoveImgTagAndAttributeName = 9;
                articleToAdd.setLinkToHeaderPicture(description.substring(startingIndexOfThumbnailLink + offsetToRemoveImgTagAndAttributeName, endingIndexOfThumbnailLink));

                articleToAdd.setTitle(currentItem.select("title").text());
                articleToAdd.setSource("VN Express");
                articleToAdd.setTimeSincePosted(currentItem.select("pubdate").text());

                Button buttonToNavigateToFullArticle = new Button();
                buttonToNavigateToFullArticle.setText("Open");
                buttonToNavigateToFullArticle.setMinWidth(100.0);
                buttonToNavigateToFullArticle.setOnAction(
                        event -> 
                              {
                            try 
                              {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/fun/scrapescrapefx/article-detail-view.fxml"));
                                root = loader.load();

                                ArticleViewController articleViewController = loader.getController();
                                articleViewController.setArticleText(generateVnExpressFullArticleText(currentItem.select("link").text()));
                                articleViewController.setArticleTitle(currentItem.select("title").text());

                                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                                scene = new Scene(root);
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException e) 
                            {
                                throw new RuntimeException(e);
                            }
                        });
                articleToAdd.setNavigateToFullArticleButton(buttonToNavigateToFullArticle);

                articleArrayList.add(articleToAdd);
            }
        }

        return articleArrayList;
    }

    public String generateVnExpressFullArticleText(String fullArticleUrl) 
       {
        String fullArticle = "";

        Document document = attemptToConnectToUrl(fullArticleUrl);

        if (document != null) 
        {
            Elements fullArticleElements = document.select("article");

            fullArticle = fullArticleElements.stream()
                    .map(element -> element.getElementsByClass("Normal").html())
                    .collect(Collectors.joining("\n"));
        }
        return fullArticle;
      }

    // TODO
//    public List<Article> generateZingNewsArticleDataFromRSSLink(String url) 
      {
//        ArrayList<Article> articleArrayList = new ArrayList<>();
//        return articleArrayList;
//    }

    // TODO
//    public List<Article> generateTuoiTreiArticleDataFromRSSLink(String rssFeedUrl) 
      {
//        ArrayList<Article> articleArrayList = new ArrayList<>();
//        return articleArrayList;
//    }

    // TODO
//    public List<Article> generateThanhNienArticleDataFromRSSLink(String rssFeedUrl) 
      {
//        ArrayList<Article> articleArrayList = new ArrayList<>();
//        return articleArrayList;
//    }

    // TODO
//    public List<Article> generateNhanDanArticleDataFromRSSLink(String url) 
      {
//        ArrayList<Article> articleArrayList = new ArrayList<>();
//        return articleArrayList;
//    }
public Document attemptToConnectToUrl(String url) {
        Document document = null;
        int numberOfRetries = 0;
        int maximumNumberOfRetries = 10;
        boolean successfullyConnected = false;

        while (!successfullyConnected && numberOfRetries < maximumNumberOfRetries) {
            try {
                System.out.println("Jsoup is trying to connect to " + url);
                document = Jsoup.connect(url).timeout(2000).userAgent("Mozilla").get();
                successfullyConnected = true;
                System.out.println("Successfully connected");
            } catch (IOException e) {
                System.out.println("Jsoup is re-trying to connect to " + url + " Attempt " + (numberOfRetries + 1) + " of " + maximumNumberOfRetries);
                numberOfRetries++;
            }
        }

        return document;
    }
}
  
