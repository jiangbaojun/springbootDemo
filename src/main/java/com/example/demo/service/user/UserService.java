package com.example.demo.service.user;

import java.util.*;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.mapper.user.UserMapper;
import com.example.demo.model.user.User;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    /**
     * 获得分页数据
     * @return
     */
    public PageInfo<User> findByUsersPage() {
        PageHelper.startPage(1,10);
        List<User> list = userMapper.findByUsers();
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        return pageInfo;
    }

    /**
     * 获得所有数据
     */
    public List<User> findByUsersAll() {
        return userMapper.findByUsers();
    }

    public List<Map<String, Object>> queryOthers() {
        return userMapper.queryOthers();
    }

    /**
     * 批量操作
     * mysql连接，添加rewriteBatchedStatements=true
     * 使用ExecutorType.BATCH类型的sqlsession
     * 10000条数据，耗时500毫秒左右
     */
    public void batch() {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
        UserMapper mapperBatch = sqlSession.getMapper(UserMapper.class);
        Long startTime = new Date().getTime();
        try {
            for(int i=0;i<100;i++){
                User user = new User();
                user.setId(UUID.randomUUID().toString().replaceAll("-",""));
                user.setName("小明"+i);
                user.setAge((i+100)%100);
                user.setBirthday(new Date());
                mapperBatch.insertOne(user);
//                sqlSession.insert(UserMapper.class.getName()+".batchTest", user);
            }
            System.out.println("addbatch耗时："+(new Date().getTime()-startTime));
            Long commitStartTime = new Date().getTime();
            sqlSession.commit();
            System.out.println("commit耗时："+(new Date().getTime()-commitStartTime));
            sqlSession.clearCache();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        } finally {
            sqlSession.close();
        }
        sqlSession.close();
        System.out.println("耗时："+(new Date().getTime()-startTime));
    }

    /**
     * 批量操作
     * mysql连接，添加rewriteBatchedStatements=true
     * 采用sql拼接，形如：insert into user1(cols) values(),(),()....
     * 10000条数据，耗时500毫秒左右
     * 但是不能插入过多，否则发生异常: ### Error querying database. Cause: com.mysql.cj.jdbc.exceptions.PacketTooBigException: Packet for query is too large (8,169,936 > 4,194,304).
     */
    public void batch2() {
        List<User> list = new ArrayList<>();
        for(int i=0;i<10000;i++){
            User user = new User();
            user.setId(UUID.randomUUID().toString().replaceAll("-",""));
            user.setName("小明"+i);
            user.setAge((i+100)%100);
            user.setBirthday(new Date());
            list.add(user);
        }
        Long startTime = new Date().getTime();
        userMapper.batchTest2(list);
        System.out.println("耗时："+(new Date().getTime()-startTime));
    }

    /**
     * 批量操作
     * mysql连接，添加allowMultiQueries=true支持
     */
    public void batch1() {
        /*******************一次执行10000条，耗时：70多秒 *****************************/
//        List<User> list = new ArrayList<>();
//        for(int i=0;i<10000;i++){
//            User user = new User();
//            user.setId(UUID.randomUUID().toString().replaceAll("-",""));
//            user.setName("小明"+i);
//            user.setAge((i+100)%100);
//            user.setBirthday(new Date());
//            list.add(user);
//        }
//        Long startTime = new Date().getTime();
//        userMapper.batchTest1(list);
//        System.out.println("耗时："+(new Date().getTime()-startTime));

        /*******************一次执行1000条，分10次执行, 耗时：70多秒,和上面的方式区别不大*****************************/
        int index = 10;
        Long startTime = new Date().getTime();
        while (--index>=0){
            List<User> list = new ArrayList<>();
            for(int i=0;i<1000;i++){
                User user = new User();
                user.setId(UUID.randomUUID().toString().replaceAll("-",""));
                user.setName("小明"+i);
                user.setAge((i+100)%100);
                user.setBirthday(new Date());
                list.add(user);
            }
            userMapper.batchTest1(list);
        }
        System.out.println("耗时："+(new Date().getTime()-startTime));
    }

    /**
     * 插入一条数据
     * @param name
     */
    public void insertOne(String name) {
        User user = new User();
        user.setId(UUID.randomUUID().toString().replaceAll("-",""));
        user.setName(name==null?"小明":name);
        user.setAge(10);
        user.setBirthday(new Date());
        userMapper.insertOne(user);
    }
}
