package com.fun.scrapescrapefx;

import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArticleAggregator 
{


    private final Pair<Category, String> vnExpressNewestRSSLink = new Pair<>(Category.NEWEST, "https://vnexpress.net/rss/tin-moi-nhat.rss");
    private final Pair<Category, String> vnExpressCovidRSSLink = new Pair<>(Category.COVID,"https://vnexpress.net/rss/covid-19.rss");
    private final Pair<Category, String> vnExpressPoliticsRSSLink = new Pair<>(Category.POLITICS,"https://vnexpress.net/rss/chinh-tri.rss");
    private final Pair<Category, String> vnExpressBusinessRSSLink = new Pair<>(Category.BUSINESS,"https://vnexpress.net/rss/kinh-doanh.rss");
    private final Pair<Category, String> vnExpressTechnologyRSSLink = new Pair<>(Category.TECHNOLOGY,"https://vnexpress.net/rss/cong-nghe.rss");
    private final Pair<Category, String> vnExpressHealthRSSLink = new Pair<>(Category.HEALTH,"https://vnexpress.net/rss/suc-khoe.rss");
    private final Pair<Category, String> vnExpressSportsRSSLink = new Pair<>(Category.SPORTS,"https://vnexpress.net/rss/the-thao.rss");
    private final Pair<Category, String> vnExpressEntertainmentRSSLink = new Pair<>(Category.ENTERTAINMENT,"https://vnexpress.net/rss/giai-tri.rss");
    private final Pair<Category, String> vnExpressRSSWorldLink = new Pair<>(Category.WORLD,"https://vnexpress.net/rss/the-gioi.rss");
    private final Pair<Category, String> vnExpressOthersRSSLink = new Pair<>(Category.OTHERS,"https://vnexpress.net/rss/tin-xem-nhieu.rss");
    private final List<Pair<Category, String>> vnExpressRSSLinks = List.of(vnExpressNewestRSSLink, vnExpressCovidRSSLink, vnExpressPoliticsRSSLink,
            vnExpressBusinessRSSLink, vnExpressTechnologyRSSLink, vnExpressHealthRSSLink, vnExpressSportsRSSLink,
            vnExpressEntertainmentRSSLink, vnExpressRSSWorldLink, vnExpressOthersRSSLink);

    private final Map<Category, List<Article>> categorizedVnExpressArticles = new HashMap<>();

    public Map<Category, List<Article>> getCategorizedVnExpressArticles() 
    {
        return categorizedVnExpressArticles;
    }

    ArticleLoader articleLoader;

    public ArticleAggregator(ArticleLoader articleLoader) 
    {
        this.articleLoader = articleLoader;
    }

    public void aggregateArticles()
    {
        vnExpressRSSLinks.forEach( link -> 
            {
            System.out.println(link.getKey());
            List<Article> vnExpressArticles = this.articleLoader.generateVnExpressArticleLinkFromRSS(link.getValue());
            categorizedVnExpressArticles.put(link.getKey(), vnExpressArticles);
            }
        );
    }
