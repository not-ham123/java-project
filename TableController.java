package com.fun.scrapescrapefx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class TableController implements Initializable {

    private Map<Category, List<Article>> aggregatedArticles;

    private ObservableList<Article> tableData;

    private double currentProgress = 0.0;

    TableView<Article> articleListTable;

    @FXML
    ToggleButton newestButton;

    @FXML
    ToggleButton covidButton;

    @FXML
    ToggleButton politicsButton;

    @FXML
    ToggleButton businessButton;

    @FXML
    ToggleButton technologyButton;

    @FXML
    ToggleButton healthButton;

    @FXML
    ToggleButton sportsButton;

    @FXML
    ToggleButton entertainmentButton;

    @FXML
    ToggleButton worldButton;

    @FXML
    ToggleButton othersButton;

    @FXML
    ToggleGroup newsSourceSelectorGroup;

    @FXML
    Pagination articleListPagination;

    @FXML
    Label loadingLabel;

    @FXML
    ProgressBar newsScrapingProgressBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        setUpTable();
        setUpTableData();
        moveProgressbarAhead();
        articleListTable.setItems(tableData);
        moveProgressbarAhead();
        articleListPagination.setPageFactory(this::createPaginationPage);
        moveProgressbarAhead();
        int maximumNumberOfPageIndicators = 5;
        articleListPagination.setMaxPageIndicatorCount(maximumNumberOfPageIndicators);
        articleListPagination.setPageCount(maximumNumberOfPageIndicators);
        moveProgressbarAhead();
        initializeButtons();
        newsScrapingProgressBar.setVisible(false);
        loadingLabel.setVisible(false);
    }

    private void setUpTable() {
        articleListTable = new TableView<>();

        TableColumn<Article, String> picture = new TableColumn<>("Picture");
        TableColumn<Article, String> title = new TableColumn<>("Title");
        TableColumn<Article, String> source = new TableColumn<>("Source");
        TableColumn<Article, String> timeDurationSincePosted = new TableColumn<>("Posted");
        TableColumn<Article, Button> navigateToNewArticleButton = new TableColumn<>("Full Article");

        picture.setCellValueFactory(
                new PropertyValueFactory<Article, String>("linkToHeaderPicture")
        );
        picture.setMaxWidth(100.0);

        title.setCellValueFactory(
                new PropertyValueFactory<>("title")
        );
        source.setCellValueFactory(
                new PropertyValueFactory<>("source")
        );

        timeDurationSincePosted.setCellValueFactory(
                new PropertyValueFactory<>("timeSincePosted")
        );
        navigateToNewArticleButton.setCellValueFactory(
                new PropertyValueFactory<>("navigateToFullArticleButton")
        );

        articleListTable.getColumns().addAll(picture , title, source, timeDurationSincePosted, navigateToNewArticleButton);

        newsScrapingProgressBar.setVisible(false);
        loadingLabel.setVisible(false);
    }

    private void setUpTableData() {
        newsScrapingProgressBar.setVisible(true);
        loadingLabel.setVisible(true);

        ArticleLoader articleLoader = new ArticleLoader();
        VNExpressArticleAggregator vnExpressArticleAggregator = new VNExpressArticleAggregator(articleLoader);
        vnExpressArticleAggregator.aggregateArticles();

        moveProgressbarAhead();
        aggregatedArticles = vnExpressArticleAggregator.getCategorizedArticles();


        tableData =  FXCollections.observableList(aggregatedArticles.get(Category.NEWEST));
    }

    private void changeTableData(Category category) {
        tableData =  FXCollections.observableList(aggregatedArticles.get(category));
        articleListTable.setItems(tableData);
    }

    private Node createPaginationPage(int pageIndex) {
        int numberOfElementsPerPage = 10;
        int startIndex = pageIndex * numberOfElementsPerPage;
        int endIndex = Math.min(startIndex + numberOfElementsPerPage, tableData.size());
        articleListTable.setItems(FXCollections.observableArrayList(tableData.subList(startIndex, endIndex)));
        return articleListTable;
    }

    private void initializeButtons() {

        newestButton.setOnAction(
                event -> {
                    changeTableData(Category.NEWEST);
                });
        covidButton.setOnAction(
                event -> {
                    changeTableData(Category.COVID);
                });
        politicsButton.setOnAction(
                event -> {
                    changeTableData(Category.POLITICS);
                });
        businessButton.setOnAction(
                event -> {
                    changeTableData(Category.BUSINESS);
                });
        technologyButton.setOnAction(
                event -> {
                    changeTableData(Category.TECHNOLOGY);
                });
        healthButton.setOnAction(
                event -> {
                    changeTableData(Category.HEALTH);
                });
        sportsButton.setOnAction(
                event -> {
                    changeTableData(Category.SPORTS);
                });
        entertainmentButton.setOnAction(
                event -> {
                    changeTableData(Category.ENTERTAINMENT);
                });
        worldButton.setOnAction(
                event -> {
                    changeTableData(Category.WORLD);
                });
        othersButton.setOnAction(
                event -> {
                    changeTableData(Category.OTHERS);
                });
    }

    public void moveProgressbarAhead() {
        currentProgress += 0.2;
        newsScrapingProgressBar.setProgress(currentProgress);
    }
}
