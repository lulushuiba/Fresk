package com.asterism.fresk.dao.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 笔记标签表实体类
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-09 09:14
 */
@DatabaseTable(tableName = "tb_note_tag")
public class NoteTagBean {

    // id 字段 主键 自动增长
    @DatabaseField(generatedId = true, useGetSet = true, columnName = "id")
    private int id;

    // 标签名称
    @DatabaseField(useGetSet = true, columnName = "name", canBeNull = false)
    private String name;

    // 标签颜色十六进制数
    @DatabaseField(useGetSet = true, columnName = "color", canBeNull = false)
    private int color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "NoteTagBean [id=" + id
                + ", name=" + name
                + ", color=" + color
                + "]";
    }
}
