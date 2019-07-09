package com.asterism.fresk.dao.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 笔记表实体类
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-09 09:13
 */
@DatabaseTable(tableName = "tb_note")
public class NoteBean {

    // id 字段 主键 自动增长
    @DatabaseField(generatedId = true, useGetSet = true, columnName = "id")
    private int id;

    // 所属书籍
    @DatabaseField(useGetSet = true, foreign = true, columnName = "book", canBeNull = false)
    private BookBean book;

    // 笔记类型
    @DatabaseField(useGetSet = true, foreign = true, columnName = "type", canBeNull = false)
    private NoteTypeBean type;

    // 笔记内容 被选择的书籍内容原文
    @DatabaseField(useGetSet = true, columnName = "content", canBeNull = false)
    private String content;

    // 笔记描述
    @DatabaseField(useGetSet = true, columnName = "describe", canBeNull = false)
    private String describe;

    // 笔记标签类型
    @DatabaseField(useGetSet = true, foreign = true, columnName = "tag", canBeNull = false)
    private NoteTagBean tag;

    // 添加日期 2018-01-01-00:00
    @DatabaseField(useGetSet = true, columnName = "add_date", canBeNull = false)
    private String addDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public NoteTypeBean getType() {
        return type;
    }

    public void setType(NoteTypeBean type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public NoteTagBean getTag() {
        return tag;
    }

    public void setTag(NoteTagBean tag) {
        this.tag = tag;
    }

    public String getAddDate() {
        return addDate;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    @Override
    public String toString() {
        return "NoteBean [id=" + id
                + ", book=" + book
                + ", type=" + type
                + ", content=" + content
                + ", describe=" + describe
                + ", tag=" + tag
                + ", add_date=" + addDate
                + "]";
    }
}
