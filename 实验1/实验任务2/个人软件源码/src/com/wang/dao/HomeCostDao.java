package com.wang.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wang.pojo.HomeCost;
import com.wang.utils.DBUtils;

public class HomeCostDao {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeCostDao.class);
	
	/**
	 * updatesql()用来执行 insert/update/delete语句
	 * @param sql 具体的sql语句
	 * @return 返回-1，说明执行失败；否则为影响数据条数
	 */
	public int updatesql(String sql) {
		Connection conn = DBUtils.getConn();//获取连接对象
        Statement state = null;
        try {
            state = conn.createStatement();
            return state.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("错误信息:{} ", e.getMessage());   
        } finally {
            DBUtils.close(state, conn);
        }
        return -1;       
	}
	
	
	//添加
    public int add(HomeCost homecost) {
    	//insert语句，形如insert into 表名(字段1，字段2) values('值1','值2');
        String sql = "insert into home(name,money) values('"+ homecost.getName() 
        	+ "','" + homecost.getMoney() +  "')";
        return updatesql(sql);
    }
    
  //添加
    public int add_copy(HomeCost homecost) {
    	//insert语句，形如insert into 表名(字段1，字段2) values('值1','值2');
        String sql = "insert into home_copy1(name,money,ip) values('"+ homecost.getName() 
        	+ "','" + homecost.getMoney()+ "','" + homecost.getIp() +  "')";
        return updatesql(sql);
    }
    
    public static void main(String[] args) {
    	//HomeCostDao HomeCostDao = new HomeCostDao ();
    	//HomeCostDao.add_copy(new HomeCost("0",new BigDecimal(0),"127.0.0.1"));
	}
    
    //删除
    public int delete (int id) {
    	//delete语句，形如delete from 表名 where id='值';
        String sql = "delete from home where id='" + id + "'";
        return updatesql(sql);
    }
     
    //修改
    public int update(HomeCost homecost) {
    	//update语句，形如update 表名 set 字段1 = '值1',字段2 = '值2'where id = '值3';
        String sql = "update home set name='" + homecost.getName() + "', money='" 
        		+ homecost.getMoney()+ "' where id='" + homecost.getId() + "'";
        return updatesql(sql);
        
    }
    
    //查询
    public List<HomeCost> query(String keyword) {
    	String sql = "select * from home WHERE name LIKE '%"+keyword+"%' OR money LIKE '%"+keyword
    			+"%'OR date LIKE binary '%"+keyword+ "%'";
        List<HomeCost> list = new ArrayList<>();
        Connection conn = DBUtils.getConn();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");//获取查询结果中的id
                String name = rs.getString("name");//获取查询结果中的name
                BigDecimal money = rs.getBigDecimal("money");//获取查询结果中的money
                String date = rs.getString("date");//获取查询结果中的date
                HomeCost homeCost = new HomeCost(id,name,money,date);//调用构造方法赋值
                list.add(homeCost);//添加到list集合中
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("错误信息:{} 查询关键词{}", e.getMessage(),keyword); 
        } finally {
            DBUtils.close(rs, state, conn);
        }
        return list;
    }
    
    //通过id找到某条信息
    public HomeCost getHomeCostById(int id) {
        String sql = "select * from home where id ='" + id + "'";
        Connection conn = DBUtils.getConn();
        Statement state = null;
        ResultSet rs = null;
        HomeCost homeCost = null;
        try {
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
                String name = rs.getString("name");
                BigDecimal money = rs.getBigDecimal("money");
                String date = rs.getString("date");
                homeCost = new HomeCost(id,name,money,date);         
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("错误信息:{} ", e.getMessage()); 
        } finally {
            DBUtils.close(rs, state, conn);
        }
        return homeCost;
    }
    
    /**
     * 通过id计算该条消费记录累计消费金额
     * @return BigDecimal类型money
     */
    public BigDecimal queryMoneySum(int id) {
        String sql = "select money from home where id <="+id;
        BigDecimal sum = new BigDecimal("0.00");
        Connection conn = DBUtils.getConn();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            while (rs.next()) {
            	BigDecimal money = rs.getBigDecimal("money");
            	//sum是money累加值
                sum = sum.add(money);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("错误信息:{} ", e.getMessage()); 
        } finally {
            DBUtils.close(rs, state, conn);
        }
        return sum;
    }
         
    //获取全部数据  
    public List<HomeCost> list() {
        String sql = "select * from home";
        List<HomeCost> list = new ArrayList<>();
        Connection conn = DBUtils.getConn();
        Statement state = null;
        ResultSet rs = null;
        try {
            state = conn.createStatement();
            rs = state.executeQuery(sql);
            HomeCost homeCost = null;
            while (rs.next()) {
                int id = rs.getInt("id");
                BigDecimal sum = queryMoneySum(id);
                String name = rs.getString("name");
                BigDecimal money = rs.getBigDecimal("money");
                String date = rs.getString("date");
                homeCost = new HomeCost(id,name,money,date,sum);
                list.add(homeCost);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            logger.error("错误信息:{} ", e.getMessage()); 
        } finally {
            DBUtils.close(rs, state, conn);
        }
        return list;
    }
    
    
}
