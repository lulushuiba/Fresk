package com.asterism.fresk.dao.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_book_type")
public class BookTypeBean {

    // id 字段 主键 自动增长
    @DatabaseField(generatedId = true, useGetSet = true, columnName = "id")
    private int id;

    // 类型名称
    @DatabaseField(useGetSet = true, columnName = "type", canBeNull = false)
    private String type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BookTypeBean [id=" + id
                + ", type=" + type
                + "]";
    }
}
