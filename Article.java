package com.fun.scrapescrapefx;
import javafx.scene.control.Button;

public class Article 
{

    private String title;

    private String linkToHeaderPicture;
    private String text;

    private String source;
    private String timeSincePosted;
    private String linkToArticle;

    private Button navigateToFullArticleButton;


    public String getText() 
    {
        return text;
    }

    public void setText(String text) 
    {
        this.text = text;
    }

    public String getLinkToHeaderPicture() 
    {
        return linkToHeaderPicture;
    }

    public void setLinkToHeaderPicture(String linkToHeaderPicture) 
    {
        this.linkToHeaderPicture = linkToHeaderPicture;
    }

    public String getTitle() 
    {
        return title;
    }

    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getSource() 
    {
        return source;
    }

    public void setSource(String source) 
    {
        this.source = source;
    }

    public String getTimeSincePosted() 
    {
        return timeSincePosted;
    }

    public void setTimeSincePosted(String timeSincePosted) 
    {
        this.timeSincePosted = timeSincePosted;
    }

    public String getLinkToArticle() 
    {
        return linkToArticle;
    }

    public void setLinkToArticle(String linkToArticle) 
    {
        this.linkToArticle = linkToArticle;
    }

    public Button getNavigateToFullArticleButton() 
    {
        return navigateToFullArticleButton;
    }

    public void setNavigateToFullArticleButton(Button navigateToFullArticleButton) 
    {
        this.navigateToFullArticleButton = navigateToFullArticleButton;
    }
}
