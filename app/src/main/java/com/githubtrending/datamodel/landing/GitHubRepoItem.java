package com.githubtrending.datamodel.landing;

import org.parceler.Parcel;

/**
 * Created by kevin.adhitama on 2020-03-09.
 */
@Parcel
public class GitHubRepoItem {
    String author;
    String name;
    String avatar;
    String url;
    String description;
    String language;
    String languageColor;
    Integer stars;
    Integer forks;
    Integer currentPeriodStars;
    boolean expanded;

    public GitHubRepoItem() {
    }

    public GitHubRepoItem(String author, String name, String avatar, String url, String description, String language, String languageColor, Integer stars, Integer forks, Integer currentPeriodStars, boolean expanded) {
        this.author = author;
        this.name = name;
        this.avatar = avatar;
        this.url = url;
        this.description = description;
        this.language = language;
        this.languageColor = languageColor;
        this.stars = stars;
        this.forks = forks;
        this.currentPeriodStars = currentPeriodStars;
        this.expanded = expanded;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getUrl() {
        return url;
    }

    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getLanguageColor() {
        return languageColor;
    }

    public Integer getStars() {
        return stars;
    }

    public Integer getForks() {
        return forks;
    }

    public Integer getCurrentPeriodStars() {
        return currentPeriodStars;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
