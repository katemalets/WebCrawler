# KM Web Crawler 
###  What is KM Web Crawler?
This is a **console java application**, which mainly **traverses websites** following set of parameters (see next chapter) and **collects statistic** of occurrence of predefined terms.
***
### How it works?
It implements **Depth-first search** technique with predefined depth -- a min number of transitions from seed to any traversed link. Results are saved to **.csv** files in project directory.
***
### Code overview
You can find the project guide in English at this link - https://drive.google.com/file/d/1bnB_tBCoNYmsPu2-LkdFgFmICgJRJRmW/view?usp=sharing. Enjoy it! (Filmed at 6:30 am with 1 take)
***
### Dependencies
Project is using following maven dependencies:
- junit
- jsoup
- commons-csv
- hamcrest-library
- hamcrest-core
***
### Parameters
 - *String* **seed** : a URL which determines Web Crawler's entry point to start surfing web.
 > defaultSeed = "https://en.wikipedia.org/wiki/Quentin_Tarantino";
 - *String[]* **terms** : set of words (or word combinations) to be searched by Web Crawler.
 > defaultTerms = {"and", "la", "Статья", "Julian", "no"}; 
 - *int* **depthLimit** : maximum value of depth (defined above).
 > defaultDepthLimit = 8;
 - *int* **visitedPagesLimit** : max number of web-pages to be traversed by Web Crawler.
 > defaultVisitedPagesLimit = 15;
 ***
 ### Output
All statistics of visited pages are saved in **allStatistics-<hh_mm_ss>.csv**. 

Top 10 statistics on the total number of terms are printed to the console and saved in a separate **topStatistics-<hh_mm_ss>.csv** file.
 ***
### Usage
1. Build and run project.
> One simple way to do it is to install Intellij IDEA, click "Get from version control" and pull that repository. Then you might need to update maven dependencies and add Application configuration with main class Main.java.

2. Consistently input terms, seed, depth limit and visited pages limit (any kind of typo leads to usage of default value and entail corresponding message). 
3. You can safely close console after getting following message:
> ***Statistics collected successfully and saved to .csv files***

Performing another Web Crawl task needs project to be **rerun**.
