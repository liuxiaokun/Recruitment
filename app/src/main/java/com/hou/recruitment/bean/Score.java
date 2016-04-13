package com.hou.recruitment.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * @author Fred Liu(liuxiaokun0410@gmail.com)
 * @version v1.0.0
 * @since 2016-04-13 13:55
 */
@DatabaseTable(tableName = "score")
public class Score {

    @DatabaseField(columnName = "id", generatedId = true)
    private Integer id;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "first_score")
    private int firstScore;
    @DatabaseField(columnName = "final_score")
    private int finalScore;
    @DatabaseField(columnName = "sync")
    private int sync;

    public Score() {

    }
    public int getFirstScore() {
        return firstScore;
    }

    public void setFirstScore(int firstScore) {
        this.firstScore = firstScore;
    }

    public int getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(int finalScore) {
        this.finalScore = finalScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSync() {
        return sync;
    }

    public void setSync(int sync) {
        this.sync = sync;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
