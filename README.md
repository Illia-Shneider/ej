# Effective Java
1. [Simple Java Cache Service](src/main/java/xyz/mpdn/ej/simplecache/SimpleCacheService.java) (Strategy: LFU)
2. [Guava Cache Service](src/main/java/xyz/mpdn/ej/guavacache/GuavaCacheService.java) (Strategy: LRU)

  Fit next requirements:

* Max Size = 100 000;
* Eviction policy;
* Time-based on last access (5 seconds);
* Removal listener; 
* Just add to log of removed entry; 
* Give statistic to user; 
* Average time spent for putting new values into the cache; 
* Number of cache evictions; 
* Support concurrency;
