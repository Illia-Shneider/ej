package xyz.mpdn.ej.simplecache;

public class Statistics {
    private long totalPutTime;
    private long totalCacheEvictions;
    private long putCount;

    public Statistics() {
        this.totalPutTime = 0;
        this.totalCacheEvictions = 0;
        this.putCount = 0;
    }

    public void addPutTime(long putTime) {
        totalPutTime += putTime;
        putCount++;
    }

    public void incrementCacheEvictions() {
        totalCacheEvictions++;
    }

    public long getTotalPutTime() {
        return totalPutTime;
    }

    public long getAveragePutTime() {
        return (putCount > 0) ? totalPutTime / putCount : 0;
    }

    public long getTotalCacheEvictions() {
        return totalCacheEvictions;
    }
}
