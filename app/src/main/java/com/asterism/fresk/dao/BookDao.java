package com.asterism.fresk.dao;

import android.content.Context;
import android.util.Log;

import com.asterism.fresk.dao.bean.BookBean;
import com.asterism.fresk.dao.core.DatabaseHelper;
import com.asterism.fresk.util.DateUtils;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.Where;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * 书籍表访问器类
 *
 * @author Ashinch
 * @email Glaxyinfinite@outlook.com
 * @date on 2019-07-02 14:53
 */
public class BookDao {
    private Context context;

    // ORMLite提供的DAO类对象
    // 第一个泛型是要操作的数据表映射成的实体类
    // 第二个泛型是这个实体类中ID的数据类型
    private Dao<BookBean, Integer> dao;

    /**
     * 构造方法
     *
     * @param context 上下文对象
     */
    public BookDao(Context context) {
        this.context = context;
        try {
            // 从数据库访问基类的单例对象获取Dao
            this.dao = DatabaseHelper.getInstance(context).getDao(BookBean.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 增
     *
     * @param data 欲增加的记录实体类
     */
    public void insert(BookBean data) {
        try {
            dao.create(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删
     *
     * @param data 欲删除的记录实体类
     */
    public void delete(BookBean data) {
        try {
            dao.delete(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 改
     *
     * @param data 欲更改的记录实体类
     */
    public void update(BookBean data) {
        try {
            dao.update(data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有记录
     *
     * @return 返回所有记录实体类集合
     */
    public List<BookBean> selectAll() {
        List<BookBean> beanList = null;
        try {
            beanList = dao.queryBuilder().orderBy("read_date",false)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beanList;
    }

    /**
     * 根据书本名称修改阅读时间
     *
     * @param bookname 书本名称
     */
    public void updateBookByBookName(String bookname) {
        BookBean bookBean ;
        try {
            bookBean = dao.queryForEq("name",bookname).get(0);
            bookBean.setReadDate(new Date());
            update(bookBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据上次阅读日期降序排序并通过索引查询记录
     *
     * @param index 索引
     *
     * @return 返回查询到的BookBean
     */
    public BookBean selectByIndexSortReadDate(int index) {
        List<BookBean> beanList = selectAll();
        if (beanList == null || index < 0 || index >= beanList.size()) {
            return null;
        }
        BookBean bean = beanList.get(index);
        beanList.clear();
        return bean;
    }

    /**
     * 根据Path来查询数据库中是否存在
     *
     * @param path 要进行比对的Path
     * @return 返回数据库中是否有此书籍
     */
    public boolean queryIsExistByPath(String path) {

        List<BookBean> beanList = null;
        try {
            beanList =  dao.queryBuilder().where().eq("file_path", path).query();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //判断是否有值
        if(beanList == null || beanList.size() == 0) {
            return false;
        }
        return true;
    }

    /**
     * 根据书名查询书籍
     *
     * @param BookId 书名
     * @return 书籍实体类
     */
    public BookBean getbookbyid(int BookId){
        BookBean bookBean=null;
        try {
            bookBean=dao.queryForId(BookId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookBean;
    }
}