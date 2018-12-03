package com.github.se307;

import java.net.URL;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Browser extends Region {
	 
    final WebView browserView = new WebView();
    final WebEngine webEngine = browserView.getEngine();
     
    public Browser() {
        //apply the styles
        getStyleClass().add("browser");
        
        // load the web page
        URL url = this.getClass().getResource("/com/github/se307/searchPage.html");
        webEngine.load(url.toString());

        //add the web view to the scene
        getChildren().add(browserView);
 
    }
    
    @Override 
    protected void layoutChildren() {
        double w = getWidth();
        double h = getHeight();
        layoutInArea(browserView,0,0,w,h,0, HPos.CENTER, VPos.CENTER);
    }
 
    @Override 
    protected double computePrefWidth(double height) {
        return 750;
    }
 
    @Override 
    protected double computePrefHeight(double width) {
        return 500;
    }
}