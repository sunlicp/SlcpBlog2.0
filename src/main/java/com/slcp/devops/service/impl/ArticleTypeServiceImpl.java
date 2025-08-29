package com.slcp.devops.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slcp.devops.entity.ArticleType;
import com.slcp.devops.mapper.IArticleTypeMapper;
import com.slcp.devops.service.IArticleTypeService;
import org.springframework.stereotype.Service;

/**
 * @author: Slcp
 * @create: 2022-07-09 23:59:19
 **/
@Service("articleTypeService")
public class ArticleTypeServiceImpl extends ServiceImpl<IArticleTypeMapper, ArticleType> implements IArticleTypeService {

}
