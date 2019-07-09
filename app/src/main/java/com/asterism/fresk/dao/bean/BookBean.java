package com.asterism.fresk.dao.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 书籍表实体类
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-09 09:13
 */
@DatabaseTable(tableName = "tb_book")
public class BookBean {

    // id 字段 主键 自动增长
    @DatabaseField(generatedId = true, useGetSet = true, columnName = "id")
    private int id;

    // 书籍类型
    @DatabaseField(useGetSet = true, foreign = true, columnName = "type", canBeNull = false)
    private BookTypeBean type;

    // 文件路径
    @DatabaseField(useGetSet = true, columnName = "file_path", canBeNull = false)
    private String filePath;

    // 书籍名称
    @DatabaseField(useGetSet = true, columnName = "name", canBeNull = false)
    private String name;

    // 阅读时长分钟
    @DatabaseField(useGetSet = true, columnName = "read_timing", canBeNull = false)
    private int readTiming;

    // 阅读进度百分比
    @DatabaseField(useGetSet = true, columnName = "read_progress", canBeNull = false)
    private int readProgress;

    // 上次阅读所处章节名
    @DatabaseField(useGetSet = true, columnName = "last_chapter", canBeNull = false)
    private String lastChapter;

    // 书籍封面图片文件名称
    @DatabaseField(useGetSet = true, columnName = "pic_name", canBeNull = false)
    private String picName;

    // 添加日期 2018-01-01-00:00
    @DatabaseField(useGetSet = true, columnName = "add_date", canBeNull = false)
    private String addDate;

    // 阅读日期 2018-01-01-00:00
    @DatabaseField(useGetSet = true, columnName = "read_date", canBeNull = false)
    private String readDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookTypeBean getType() {
        return type;
    }

    public void setType(BookTypeBean type) {
        this.type = type;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReadTiming() {
        return readTiming;
    }

    public void setReadTiming(int readTiming) {
        this.readTiming = readTiming;
    }

    public int getReadProgress() {
        return readProgress;
    }

    public void setReadProgress(int readProgress) {
        this.readProgress = readProgress;
    }

    public String getLastChapter() {
        return lastChapter;
    }

    public void setLastChapter(String lastChapter) {
        this.lastChapter = lastChapter;
    }

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public String getReadDate() {
        return readDate;
    }

    public void setReadDate(String readDate) {
        this.readDate = readDate;
    }

    @Override
    public String toString() {
        return "BookBean [id=" + id
                + ", type=" + type
                + ", file_path=" + filePath
                + ", name=" + name
                + ", read_timing=" + readTiming
                + ", read_progress=" + readProgress
                + ", last_chapter=" + lastChapter
                + ", pic_name=" + picName
                + ", add_date=" + addDate
                + ", read_date=" + readDate
                + "]";
    }
}
