package com.fun.scrapescrapefx;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ArticleAggregator 
{

    public Map<Category, List<Article>> getCategorizedArticles();

    public void aggregateArticles() throws IOException;
}
