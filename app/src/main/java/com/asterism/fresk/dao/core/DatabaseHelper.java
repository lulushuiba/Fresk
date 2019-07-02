package com.asterism.fresk.dao.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.asterism.fresk.dao.bean.BookBean;
import com.asterism.fresk.dao.bean.BookTypeBean;
import com.asterism.fresk.dao.bean.NoteBean;
import com.asterism.fresk.dao.bean.NoteTagBean;
import com.asterism.fresk.dao.bean.NoteTypeBean;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库访问器类
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-02 15:30
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    // 数据库名称
    public static final String DATABASE_NAME = "fresk.db";
    // 本类的单例实例
    private static DatabaseHelper instance;
    // 存储APP中所有的DAO对象的Map集合
    private Map<String, Dao> daos = new HashMap<>();

    /**
     * 获取单例
     *
     * @param context 上下文对象
     *
     * @return 返回单例
     */
    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (DatabaseHelper.class) {
                if (instance == null) {
                    instance = new DatabaseHelper(context);
                }
            }
        }
        return instance;
    }

    /**
     * 构造方法
     *
     * @param context 上下文对象
     */
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * 根据用户表实体类获取访问器
     *
     * @param clazz 用户表实体类
     *
     * @return 返回对应的访问器
     *
     * @throws SQLException
     */
    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /**
     * 重写 数据库创建事件
     *
     * @param database         数据库
     * @param connectionSource 连接源
     */
    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            // 创建表
            TableUtils.createTable(connectionSource, BookBean.class);
            TableUtils.createTable(connectionSource, BookTypeBean.class);
            TableUtils.createTable(connectionSource, NoteBean.class);
            TableUtils.createTable(connectionSource, NoteTagBean.class);
            TableUtils.createTable(connectionSource, NoteTypeBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重写 数据库更新事件
     *
     * @param database         数据库
     * @param connectionSource 连接源
     * @param oldVersion       旧版本号
     * @param newVersion       新版本号
     */
    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            // 删表重建 (注意：记录将会一并删除)
            TableUtils.dropTable(connectionSource, BookBean.class, true);
            TableUtils.dropTable(connectionSource, BookTypeBean.class, true);
            TableUtils.dropTable(connectionSource, NoteBean.class, true);
            TableUtils.dropTable(connectionSource, NoteTagBean.class, true);
            TableUtils.dropTable(connectionSource, NoteTypeBean.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 重写 数据库关闭事件
     */
    @Override
    public void close() {
        super.close();
        // 释放Dao集合资源
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }
}