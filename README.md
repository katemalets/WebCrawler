# KM Web Crawler 
###  What is KM Web Crawler?
This is a console java application, which mainly traverses websites following set of parameters (see next chapter) and collects statistic of occurrence of predefined terms.
***
### How it works?
It implements Depth-first search technique with predefined depth – a min number of transitions from seed to any traversed link. Results are saved to .csv files in project directory.
***
### Dependencies
Project is using following maven dependencies:
- jsoup
- commons-csv
- hamcrest-library
- hamcrest-core
***
### Parameters
 - *String* seed : a URL which determines Web Crawler's entry point to start surfing web.
 > defaultSeed = "https://en.wikipedia.org/wiki/Quentin_Tarantino";
 - *String[]* terms : set of words (or word combinations) to be searched by Web Crawler.
 > defaultTerms = {"and", "la", "Статья", "Julian", "no"}; 
 - *int* depthLimit : maximum value of depth (defined above).
 > defaultDepthLimit = 8;
 - *int* visitedPagesLimit : max number of web-pages to be traversed by Web Crawler.
 > defaultVisitedPagesLimit = 15;
 ***
 ### Output
All statistics of visited pages are saved in allStatistics-<hh_mm_ss>.csv. 

Top 10 statistics on the total number of terms are printed to the console and saved in a separate topStatistics-<hh_mm_ss>.csv file.
 ***
### Usage
Build and run project. Consistently input terms, seed, depth limit and visited pages limit (any kind of typo leads to usage of default value and entail corresponding message). you can safely close console after getting following message:
> *Statistics collected successfully and saved to .csv files*

Performing another Web Crawl task needs project to be rerun.
