package com.fun.scrapescrapefx;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class ArticleLoader {

    public List<Article> generateVnExpressArticleLinkFromRSS(String rssFeedUrl) {
        ArrayList<Article> articleArrayList = new ArrayList<>();
        Document document = attemptToConnectToUrl(rssFeedUrl);

        if (document != null) {
            Elements itemsOnPageElements = document.select("item");

            for (int i = 0; i < Math.min(itemsOnPageElements.size(), 50); i++) {

                Element currentItem = itemsOnPageElements.get(i);

                String description = currentItem.select("description").text();
                if (!description.contains("img src=")) {
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
                articleToAdd.setLinkToArticle(currentItem.select("link").text());

                articleArrayList.add(articleToAdd);
            }
        }

        return articleArrayList;
    }

    public List<Article> generateZingNewsArticleLinkFromURL(String url) {
        ArrayList<Article> articleArrayList = new ArrayList<>();
        Document document = attemptToConnectToUrl(url);

        if (document != null) {
            Elements itemsOnPageElements = document.select("item");

            for (int i = 0; i < Math.min(itemsOnPageElements.size(), 50); i++) {

                Element currentItem = itemsOnPageElements.get(i);

                Article articleToAdd = new Article();

                articleToAdd.setTitle(currentItem.select("p.article-title a[href]").text());
                articleToAdd.setSource("VN Express");
                articleToAdd.setTimeSincePosted(currentItem.select("p.article-meta").select("span.date").text());
                articleToAdd.setLinkToArticle(currentItem.select("p.article-title a[href]").attr("abs:href"));

                articleArrayList.add(articleToAdd);
            }
        }

        return articleArrayList;
    }

    public List<Article> generateTuoiTreiArticleLinkFromRSS(String rssFeedUrl) {
        ArrayList<Article> articleArrayList = new ArrayList<>();
        Document document = attemptToConnectToUrl(rssFeedUrl);

        if (document != null) {
            Elements itemsOnPageElements = document.select("item");

            for (int i = 0; i < Math.min(itemsOnPageElements.size(), 50); i++) {

                Element currentItem = itemsOnPageElements.get(i);

                String description = currentItem.select("description").text();
                if (!description.contains("img src=")) {
                    continue;
                }

                Article articleToAdd = new Article();

                int startingIndexOfThumbnailLink = description.indexOf("img src=");
                int endingIndexOfThumbnailLink = description.indexOf("\" ", startingIndexOfThumbnailLink + 1);
                int offsetToRemoveImgTagAndAttributeName = 9;
                articleToAdd.setLinkToHeaderPicture(description.substring(startingIndexOfThumbnailLink + offsetToRemoveImgTagAndAttributeName, endingIndexOfThumbnailLink));

                articleToAdd.setTitle(currentItem.select("title").text());
                articleToAdd.setSource("Tuoi Trei");
                articleToAdd.setTimeSincePosted(currentItem.select("pubdate").text());
                articleToAdd.setLinkToArticle(currentItem.select("link").text());

                articleArrayList.add(articleToAdd);
            }
        }

        return articleArrayList;
    }

    public List<Article> generateThanhNienArticleLinkFromRSS(String rssFeedUrl) {
        ArrayList<Article> articleArrayList = new ArrayList<>();
        Document document = attemptToConnectToUrl(rssFeedUrl);

        if (document != null) {
            Elements itemsOnPageElements = document.select("item");

            for (int i = 0; i < Math.min(itemsOnPageElements.size(), 50); i++) {

                Element currentItem = itemsOnPageElements.get(i);

                String description = currentItem.select("description").text();
                if (!description.contains("img src=")) {
                    continue;
                }

                Article articleToAdd = new Article();

                int startingIndexOfThumbnailLink = description.indexOf("img src=");
                int endingIndexOfThumbnailLink = description.indexOf("\" ", startingIndexOfThumbnailLink + 1);
                int offsetToRemoveImgTagAndAttributeName = 9;
                articleToAdd.setLinkToHeaderPicture(description.substring(startingIndexOfThumbnailLink + offsetToRemoveImgTagAndAttributeName, endingIndexOfThumbnailLink));

                articleToAdd.setTitle(currentItem.select("title").text());
                articleToAdd.setSource("Thanh Nien");
                articleToAdd.setTimeSincePosted(currentItem.select("pubdate").text());
                articleToAdd.setLinkToArticle(currentItem.select("link").text());

                articleArrayList.add(articleToAdd);
            }
        }

        return articleArrayList;
    }

    public List<Article> generateNhanDanArticleLinkFromURL(String url) {
        ArrayList<Article> articleArrayList = new ArrayList<>();
        Document document = attemptToConnectToUrl(url);

        if (document != null) {
            Elements itemsOnPageElements = document.select("item");

            for (int i = 0; i < Math.min(itemsOnPageElements.size(), 50); i++) {

                Element currentItem = itemsOnPageElements.get(i);

                String description = currentItem.select("description").text();
                if (!description.contains("img src=")) {
                    continue;
                }

                Article articleToAdd = new Article();

                int startingIndexOfThumbnailLink = description.indexOf("img src=");
                int endingIndexOfThumbnailLink = description.indexOf("\" ", startingIndexOfThumbnailLink + 1);
                int offsetToRemoveImgTagAndAttributeName = 9;
                articleToAdd.setLinkToHeaderPicture(description.substring(startingIndexOfThumbnailLink + offsetToRemoveImgTagAndAttributeName, endingIndexOfThumbnailLink));

                articleToAdd.setTitle(currentItem.select("title").text());
                articleToAdd.setSource("Nhan Dan");
                articleToAdd.setTimeSincePosted(currentItem.select("pubdate").text());
                articleToAdd.setLinkToArticle(currentItem.select("link").text());

                articleArrayList.add(articleToAdd);
            }
        }

        return articleArrayList;
    }


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
