package com.yd1994.alpacablog.blogservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yd1994.alpacablog.blogservice.common.exception.ResourceIsDeletedException;
import com.yd1994.alpacablog.blogservice.common.exception.ResourceNotFoundException;
import com.yd1994.alpacablog.blogservice.dto.Category;
import com.yd1994.alpacablog.blogservice.dto.FileInfo;
import com.yd1994.alpacablog.blogservice.entity.CategoryDO;
import com.yd1994.alpacablog.blogservice.entity.FileDO;
import com.yd1994.alpacablog.blogservice.mapper.FileMapper;
import com.yd1994.alpacablog.blogservice.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * <p></p>
 *
 * @author yd
 */
@Slf4j
@CacheConfig(cacheNames = "file")
@Service
public class FileServiceImpl implements IFileService {

    @Autowired
    private FileMapper fileMapper;

    @Cacheable(key = "#id", unless = "#result == null")
    @Override
    public FileInfo get(Long id, Integer type) throws Exception {
        FileDO fileDO = Optional.ofNullable(this.fileMapper.selectOne(
                new QueryWrapper<FileDO>().eq("id", id).eq("type", type)
        )).orElseThrow(() -> new ResourceNotFoundException());
        if (fileDO.getDeleted()) {
            throw new ResourceIsDeletedException();
        }
        return new FileInfo(fileDO);
    }

    @Cacheable(key = "#id", unless = "#result == null")
    @Override
    public FileInfo get(Long id) throws Exception {
        FileDO fileDO = Optional.ofNullable(this.fileMapper.selectById(id))
                .orElseThrow(() -> new ResourceNotFoundException());
        if (fileDO.getDeleted()) {
            throw new ResourceIsDeletedException();
        }
        return new FileInfo(fileDO);
    }

    @Transactional
    @Override
    public int add(FileInfo fileInfo, String username) throws Exception {
        Assert.notNull(fileInfo, "参数有误。");

        FileDO fileDO = fileInfo.toFileDO();
        fileDO.setId(null);

        fileDO.setGmtCreated(LocalDateTime.now());
        fileDO.setCreatedBy(username);
        fileDO.setGmtModified(LocalDateTime.now());
        fileDO.setModifiedBy(username);

        int result = this.fileMapper.insert(fileDO);

        if (result == 0) {
            log.info("添加失败：" + fileInfo);
            throw new IllegalArgumentException("参数有误");
        }

        fileInfo.setId(fileDO.getId());

        return result;
    }

    @CacheEvict(key = "#fileInfo.id")
    @Transactional
    @Override
    public int update(FileInfo fileInfo, String username) throws Exception {
        Assert.notNull(fileInfo, "参数有误。");

        FileDO fileDO = fileInfo.toFileDO();

        fileDO.setGmtModified(LocalDateTime.now());
        fileDO.setModifiedBy(username);

        int result = this.fileMapper.updateById(fileDO);

        if (result == 0) {
            log.info("修改失败：" + fileInfo);
            throw new IllegalArgumentException("参数有误");
        }
        fileInfo.setId(fileDO.getId());

        return result;
    }

    @CacheEvict(key = "#id")
    @Transactional
    @Override
    public int delete(Long id, String username) throws Exception {
        FileDO fileDO = new FileDO();

        fileDO.setId(id);
        fileDO.setDeleted(true);

        fileDO.setGmtModified(LocalDateTime.now());
        fileDO.setModifiedBy(username);

        int result = this.fileMapper.updateById(fileDO);

        if (result == 0) {
            log.debug("删除失败：" + id);
            throw new ResourceNotFoundException("参数有误");
        }

        return result;
    }

}
