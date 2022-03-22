package com.huanglulu.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.huanglulu.wiki.domain.User;
import com.huanglulu.wiki.domain.UserExample;
import com.huanglulu.wiki.exception.BusinessException;
import com.huanglulu.wiki.exception.BusinessExceptionCode;
import com.huanglulu.wiki.mapper.UserMapper;
import com.huanglulu.wiki.req.UserQueryReq;
import com.huanglulu.wiki.req.UserResetPasswordReq;
import com.huanglulu.wiki.req.UserSaveReq;
import com.huanglulu.wiki.resp.PageResp;
import com.huanglulu.wiki.resp.UserQueryResp;
import com.huanglulu.wiki.util.CopyUtil;
import com.huanglulu.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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
        if(!ObjectUtils.isEmpty(req.getLoginName())){
            criteria.andLoginNameEqualTo(req.getLoginName());
        }

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
            User userDB = selectByLoginName(req.getLoginName());
            if(ObjectUtils.isEmpty(userDB)){
                //新增
                user.setId(snowFlake.nextId());
                userMapper.insert(user);
            }else{
                //用户名已存在
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
//                try {
//                    throw new Exception("用户名已存在");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

            }
        }else{
            //更新
            user.setLoginName(null);
            user.setPassword(null);
            userMapper.updateByPrimaryKeySelective(user);
        }
    }

    /**
     * 删除
     * @param id
     */
    public void delete(Long id){
        userMapper.deleteByPrimaryKey(id);
    }

    /**
     * 通过用户名搜索
     * @param loginName
     * @return
     */
    public User selectByLoginName(String loginName){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(loginName);
        List<User> userList = userMapper.selectByExample(userExample);
        if(CollectionUtils.isEmpty(userList)){
            return null;
        }else{
            return userList.get(0);
        }
    }

    public void resetPassword(UserResetPasswordReq req){
        User user = CopyUtil.copy(req,User.class);
        userMapper.updateByPrimaryKeySelective(user);
    }

}
