package com.huanglulu.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huanglulu.wiki.domain.User;
import com.huanglulu.wiki.domain.UserExample;
import com.huanglulu.wiki.mapper.UserMapper;
import com.huanglulu.wiki.req.UserQueryReq;
import com.huanglulu.wiki.req.UserSaveReq;
import com.huanglulu.wiki.resp.UserQueryResp;
import com.huanglulu.wiki.resp.PageResp;
import com.huanglulu.wiki.util.CopyUtil;
import com.huanglulu.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<UserQueryResp> list(UserQueryReq req){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        //模糊查询
        //只能根据内容查找criteria.andNameLike("%"+req.getName()+"%");
        if(!ObjectUtils.isEmpty(req.getLoginName())){
            criteria.andLoginNameEqualTo(req.getLoginName());
        }

        //分页最基本数据：两个请求参数：pageNum，pageSize，两个返回参数：userList，getTotal（）
        PageHelper.startPage( req.getPage(), req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);

        PageInfo<User>pageInfo = new PageInfo<>(userList);
        LOG.info("总行数：{}"+pageInfo.getTotal());
        LOG.info("总页数：{}"+pageInfo.getPages());


        //列表复制
        List<UserQueryResp> list = CopyUtil.copyList(userList, UserQueryResp.class);
//        List<UserResp> respList = new ArrayList<>();
//        for (User user : userList) {
//            //从user拷贝到userResp，（对象的复制）
//            UserResp copyResp = CopyUtil.copy(user, UserResp.class);
//            respList.add(userResp);
//        }
       PageResp<UserQueryResp> pageResp = new PageResp();
       pageResp.setTotal(pageInfo.getTotal());
       pageResp.setList(list);
       return pageResp;
    }

    /**
     * 保存
     * @param req
     */

    public void save(UserSaveReq req){
        User user = CopyUtil.copy(req,User.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //新增
            user.setId(snowFlake.nextId());
            userMapper.insert(user);
        }else{
            //更新
            userMapper.updateByPrimaryKey(user);
        }
    }

    /**
     * 删除
     * @param id
     */
    public void delete(Long id){
        userMapper.deleteByPrimaryKey(id);
    }
}
