package com.utudo.hwwd.helpers;

import org.springframework.data.domain.PageImpl;

import java.util.ArrayList;
import java.util.HashMap;

public class PageHelper {
    private long count;
    private int pageSize = 9;
    private boolean isfirstPage;
    private boolean islastPage;
    private long totalPage;
    private long currentPage = 0;
    private long offset = 0;

    private long limit = 20;
    private String baseUrl;

    private class PageItem{
        private String key;
        private String url;
        public PageItem(String key,String url){
            this.key = key;
            this.url = url;
        }
        public void setKey(String key) {
            this.key = key;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getKey() {
            return key;
        }

        public String getUrl() {
            return url;
        }
    }

    private ArrayList<PageItem> pages = new ArrayList<PageItem>();


    public void setCount(long count) {
        this.count = count;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setIsfirstPage(boolean isfirstPage) {
        this.isfirstPage = isfirstPage;
    }

    public void setIslastPage(boolean islastPage) {
        this.islastPage = islastPage;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getCount() {
        return count;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public long getLimit() {
        return limit;
    }

    public long getTotalPage() {
        this.totalPage = (long) Math.ceil(Double.parseDouble(String.valueOf(this.count)) / this.limit);
        return totalPage;
    }

    public ArrayList<PageItem> getPages() {
        this.pages.clear();
        if (getTotalPage()>0){
            if (currentPage>1){
                this.pages.add(new PageItem("<<",PageHelper.this.getBaseUrl()+"?page="+(currentPage-1)));
            }
            if (getTotalPage()<=12){
                for (int i = 0; i < totalPage; i++) {
                    this.pages.add(new PageItem(i+1+"",this.baseUrl+"?page="+i));
                }
            }else{
                for (int i = 0; i<12;i++){
                    if (currentPage>6){
                        if (i==0){
                            this.pages.add(new PageItem(i+1+"...",this.baseUrl+"?page="+0));
                        }
                        if (currentPage+12>totalPage){
                            this.pages.add(new PageItem(totalPage-11+i+"",this.baseUrl+"?page="+(totalPage-12+i)));
                        }else{
                            this.pages.add(new PageItem(currentPage-6+i+"",this.baseUrl+"?page="+(currentPage-7+i)));
                        }
                        if (i==11){
                            this.pages.add(new PageItem("..." + (totalPage),this.baseUrl+"?page="+(totalPage-1)));
                        }
                    }else{
                        if (i==0){
                            this.pages.add(new PageItem(i+1+"...",this.baseUrl+"?page="+0));
                        }
                        this.pages.add(new PageItem(i+1+"",this.baseUrl+"?page="+i));

                        if (i==11){
                            this.pages.add(new PageItem("..." + (totalPage),this.baseUrl+"?page="+(totalPage-1)));
                        }
                    }

                }
            }


            if (currentPage<totalPage-1){
                this.pages.add(new PageItem(">>",this.getBaseUrl()+"?page="+(currentPage+1)));
            }
        }
        return pages;
    }

    public long getOffset() {
        this.offset = currentPage * limit;
        return offset;
    }


    @Override
    public String toString() {
        return "PageHelper{" +
                "count=" + count +
                ", pageSize=" + pageSize +
                ", isfirstPage=" + isfirstPage +
                ", islastPage=" + islastPage +
                ", totalPage=" + totalPage +
                ", currentPage=" + currentPage +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
