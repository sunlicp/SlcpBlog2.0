package com.slcp.devops.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.slcp.devops.NotFountException;
import com.slcp.devops.config.DoQueryCache;
import com.slcp.devops.dto.*;
import com.slcp.devops.dto.BlogDTO;
import com.slcp.devops.entity.Blog;
import com.slcp.devops.entity.Contact;
import com.slcp.devops.mapper.IBlogMapper;
import com.slcp.devops.service.IBlogService;
import com.slcp.devops.utils.ColorUtil;
import com.slcp.devops.utils.MarkdownUtils;
import com.slcp.devops.utils.StringUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: Slcp
 * @date: 2020/9/22 14:33
 * @code: 一生的挚爱
 * @description:
 */
@Service("blogService")
public class BlogServiceImpl extends ServiceImpl<IBlogMapper, Blog> implements IBlogService {

    private static final ExecutorService simpleExecutorService = new ThreadPoolExecutor(
            8,
            16,
            60,
            TimeUnit.SECONDS,
            new LinkedBlockingDeque<>(32),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @Override
    public IPage<BlogQueryDTO> listBlogs(IPage<BlogQueryDTO> listInfoByPage, String title) {
        return this.baseMapper.listBlogs(listInfoByPage, title);
    }

    @Override
    public int saveBlog(BlogDTO blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        blog.setViews(0);
        blog.setCommentCount(0);
        return this.baseMapper.saveBlog(blog);
    }

    @Override
    public ShowDTO getBlogById(Long id) {
        return this.baseMapper.getBlogById(id);
    }


    @Override
    public void getBolgOneById(Long id, int views) {
        simpleExecutorService.execute(() -> this.lambdaUpdate().set(Blog::getViews, views + 1).eq(Blog::getId, id).update());
    }

    @Override
    @DoQueryCache
    public FirstPageDTO getFirstPageDTO(Long id) {
        List<FirstPageDTO> blogs = this.baseMapper.getBolgOneById(id);
        if (null != blogs && blogs.size() > 0) {
            FirstPageDTO firstPageDTO = blogs.get(0);
            firstPageDTO.setColor(StrUtil.format("background: linear-gradient(to bottom right, {} 0, {} 100%)", ColorUtil.getRandColor(), ColorUtil.getRandColor()));
            firstPageDTO.setTagStr(StringUtil.getTagStr(firstPageDTO.getTags()));
            firstPageDTO.setFirstPicture("background-image: url(" + firstPageDTO.getFirstPicture() + ")");
            return firstPageDTO;
        }
        return null;
    }

    @Override
    public int updateBlog(ShowDTO showBlog) {
        showBlog.setUpdateTime(new Date());
        return this.baseMapper.updateBlog(showBlog);
    }

    @Override
    public int deleteBlogById(Long id) {
        return this.baseMapper.deleteBlogById(id);
    }

    @Override
    public List<SearchDTO> searchBlogs(SearchDTO searchBlog) {
        return this.baseMapper.searchBlogs(searchBlog);
    }

    @Override
    @DoQueryCache
    public IPage<FirstPageDTO> getFirstPageBlogs(Integer pageNum) {
        IPage<FirstPageDTO> page = new Page<>(Convert.toInt(pageNum, 1), pageNum == 1 ? 6 : 9);
        IPage<FirstPageDTO> firstPageBlogs = this.baseMapper.getFirstPageBlogs(page, null);
        if (pageNum != null && pageNum == 1) {
            firstPageBlogs.getRecords().get(0).setFirstPicture("https://img.sunlicp.cn/ft/IMG_3409.PNG");
            firstPageBlogs.getRecords().get(1).setFirstPicture("https://img.sunlicp.cn/ft/IMG_3410.PNG");
            firstPageBlogs.getRecords().get(2).setFirstPicture("https://img.sunlicp.cn/ft/IMG_3411.PNG");
            firstPageBlogs.getRecords().get(3).setFirstPicture("https://img.sunlicp.cn/ft/IMG_3417.PNG");
            firstPageBlogs.getRecords().get(4).setFirstPicture("https://img.sunlicp.cn/ft/IMG_3416.PNG");
            firstPageBlogs.getRecords().get(5).setFirstPicture("https://img.sunlicp.cn/ft/IMG_3415.PNG");
        }
        return firstPageBlogs;
    }

    @Override
    @DoQueryCache
    public List<FirstPageDTO> getCategoryBlogs(String name) {
        return this.baseMapper.getPageBlogs(name);
    }

    @Override
    public List<FirstPageDTO> getBlogsByTime(String name) {
        return this.baseMapper.getBlogsByTime(name);
    }

    @Override
    @DoQueryCache
    public List<RecommendDTO> getHotBlogs() {
        List<RecommendDTO> hotBlogs = this.baseMapper.getHotBlogs();
        hotBlogs.get(0).setFirstPicture("https://img.sunlicp.cn/ft/IMG_3404.PNG");
        hotBlogs.get(1).setFirstPicture("https://img.sunlicp.cn/ft/IMG_3405.PNG");
        hotBlogs.get(2).setFirstPicture("https://img.sunlicp.cn/ft/IMG_3419.PNG");
        return hotBlogs;
    }

    @Override
    @DoQueryCache
    public List<FirstPageDTO> getSearchBlogs(String query) {
        return this.baseMapper.getSearchBlogs(query);
    }

    @Override
    @DoQueryCache
    public List<FirstPageDTO> getSearchBlogsByTagName(Long tid) {
        return this.baseMapper.getSearchBlogsByTagName(tid);
    }

    @Override
    @DoQueryCache
    public List<RecommendDTO> getRecommendBlogs() {
        return this.baseMapper.getRecommendBlogs();
    }

    @Override
    @DoQueryCache
    public List<RecommendDTO> getTopBlogs() {
        return this.baseMapper.getTopBlogs();
    }

    @Override
    @DoQueryCache
    public DetailedDTO getDetailedBlogById(Long id) {
        DetailedDTO detailedBlog = this.baseMapper.getDetailedBlogById(id);
        if (ObjectUtil.isEmpty(detailedBlog)) {
            throw new NotFountException("该博客不存在");
        }
        String content = detailedBlog.getContent();
        detailedBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        this.baseMapper.getCommentCountById(id);

        return detailedBlog;
    }

    @Override
    @DoQueryCache
    public Integer getBlogTotal() {
        return getTotal();
    }

    @DoQueryCache
    private Integer getTotal() {
        return this.baseMapper.getBlogTotal();
    }

    @Override
    @DoQueryCache
    public Integer getBlogViewTotal() {
        return this.baseMapper.getBlogViewTotal();
    }

    @Override
    public Integer getBlogCommentTotal() {
        return this.baseMapper.getBlogCommentTotal();
    }

    @Override
    public Integer getBlogMessageTotal() {
        return this.baseMapper.getBlogMessageTotal();
    }

    @Override
    public List<FirstPageDTO> getBlogsByTypeId(Long id) {
        return this.baseMapper.getBlogsByTypeId(id);
    }

    @Override
    public Integer getCount() {
        return this.baseMapper.getCount();
    }

    @Override
    public BlogQueryDTO blogInfo(String id) {
        if (StringUtil.isBlank(id)) {
            return null;
        }
        BlogQueryDTO blogInfo = this.baseMapper.getBlogInfoById(id);
        List<String> tagIds = this.baseMapper.getTagId(Long.valueOf(id));
        blogInfo.setTagIds(tagIds);
        return blogInfo;
    }

    @Override
    public List<Contact> getAlgoliaData() {
        List<Contact> algoliaData = this.baseMapper.getAlgoliaData();
        algoliaData.forEach(contact -> contact.setPath("article/read/"+contact.getObjectID()));
        return algoliaData;
    }
}