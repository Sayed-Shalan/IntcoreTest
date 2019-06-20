package com.sayed.intcoretest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MoviesResult implements Parcelable {
    private int page,total_results,total_pages;
    private List<Movie> results;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoviesResult that = (MoviesResult) o;

        if (page != that.page) return false;
        if (total_results != that.total_results) return false;
        if (total_pages != that.total_pages) return false;
        return results != null ? results.equals(that.results) : that.results == null;
    }

    @Override
    public int hashCode() {
        int result = page;
        result = 31 * result + total_results;
        result = 31 * result + total_pages;
        result = 31 * result + (results != null ? results.hashCode() : 0);
        return result;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_results() {
        return total_results;
    }

    public void setTotal_results(int total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.page);
        dest.writeInt(this.total_results);
        dest.writeInt(this.total_pages);
        dest.writeList(this.results);
    }

    public MoviesResult() {
    }

    protected MoviesResult(Parcel in) {
        this.page = in.readInt();
        this.total_results = in.readInt();
        this.total_pages = in.readInt();
        this.results = new ArrayList<Movie>();
        in.readList(this.results, Movie.class.getClassLoader());
    }

    public static final Parcelable.Creator<MoviesResult> CREATOR = new Parcelable.Creator<MoviesResult>() {
        @Override
        public MoviesResult createFromParcel(Parcel source) {
            return new MoviesResult(source);
        }

        @Override
        public MoviesResult[] newArray(int size) {
            return new MoviesResult[size];
        }
    };
}
