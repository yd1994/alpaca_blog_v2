package com.yd1994.alpacablog.blogservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yd1994.alpacablog.blogservice.common.exception.ResourceIsDeletedException;
import com.yd1994.alpacablog.blogservice.common.exception.ResourceNotFoundException;
import com.yd1994.alpacablog.blogservice.dto.Category;
import com.yd1994.alpacablog.blogservice.dto.SysInformation;
import com.yd1994.alpacablog.blogservice.entity.CategoryDO;
import com.yd1994.alpacablog.blogservice.entity.FileDO;
import com.yd1994.alpacablog.blogservice.entity.SysInformationDO;
import com.yd1994.alpacablog.blogservice.mapper.SysInformationMapper;
import com.yd1994.alpacablog.blogservice.service.ISysInformationService;
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
 * 系统信息 service 实现
 *
 * @author yd
 */
@Slf4j
@CacheConfig(cacheNames = "sys_information")
@Service
public class SysInformationServiceImpl implements ISysInformationService {

    @Autowired
    private SysInformationMapper sysInformationMapper;

    @Cacheable(key = "#name", unless = "#result == null")
    @Override
    public SysInformation get(String name) throws Exception {

        SysInformationDO sysInformationDO = Optional.ofNullable(
                this.sysInformationMapper.selectOne(new QueryWrapper<SysInformationDO>().eq("name", name))
        ).orElseThrow(() -> new ResourceNotFoundException());

        if (sysInformationDO.getDeleted()) {
            throw new ResourceIsDeletedException();
        }

        return new SysInformation(sysInformationDO);
    }

    @CacheEvict(key = "#sysInformation.name")
    @Override
    public int updateByName(SysInformation sysInformation, String username) throws Exception {
        Assert.notNull(sysInformation, "参数有误。");

        SysInformationDO sysInformationDO = sysInformation.toSysInformationDO();

        sysInformationDO.setGmtModified(LocalDateTime.now());
        sysInformationDO.setModifiedBy(username);

        int result = this.sysInformationMapper.update(sysInformationDO,
                new UpdateWrapper<SysInformationDO>().eq("name", sysInformation.getName()));

        if (result == 0) {
            log.error("修改失败：" + sysInformation);
            throw new IllegalArgumentException("参数有误");
        }

        return result;
    }

    @Cacheable(key = "#id", unless = "#result == null")
    @Override
    public SysInformation get(Long id) throws Exception {
        return new SysInformation(Optional.ofNullable(this.sysInformationMapper.selectById(id)).orElseThrow(() -> new ResourceNotFoundException()));
    }

    @Transactional
    @Override
    public int add(SysInformation sysInformation, String username) throws Exception {
        Assert.notNull(sysInformation, "参数有误。");

        SysInformationDO sysInformationDO = sysInformation.toSysInformationDO();
        sysInformationDO.setId(null);

        sysInformationDO.setGmtCreated(LocalDateTime.now());
        sysInformationDO.setCreatedBy(username);
        sysInformationDO.setGmtModified(LocalDateTime.now());
        sysInformationDO.setModifiedBy(username);

        int result = this.sysInformationMapper.insert(sysInformationDO);

        if (result == 0) {
            log.error("添加失败：" + sysInformation);
            throw new IllegalArgumentException("参数有误");
        }

        sysInformation.setName(sysInformationDO.getName());

        return result;
    }

    @CacheEvict(key = "#sysInformation.id")
    @Transactional
    @Override
    public int update(SysInformation sysInformation, String username) throws Exception {
        Assert.notNull(sysInformation, "参数有误。");

        SysInformationDO sysInformationDO = sysInformation.toSysInformationDO();

        sysInformationDO.setGmtModified(LocalDateTime.now());
        sysInformationDO.setModifiedBy(username);

        int result = this.sysInformationMapper.update(sysInformationDO,
                new QueryWrapper<SysInformationDO>().eq("name", sysInformationDO.getName()));

        if (result == 0) {
            log.error("修改失败：" + sysInformation);
            throw new IllegalArgumentException("参数有误");
        }

        return result;
    }

    @CacheEvict(key = "#id")
    @Transactional
    @Override
    public int delete(Long id, String username) throws Exception {
        SysInformationDO sysInformationDO = new SysInformationDO();
        sysInformationDO.setId(id);
        sysInformationDO.setDeleted(true);

        sysInformationDO.setGmtModified(LocalDateTime.now());
        sysInformationDO.setModifiedBy(username);

        int result = this.sysInformationMapper.updateById(sysInformationDO);

        if (result == 0) {
            log.error("删除失败：" + id);
            throw new ResourceNotFoundException();
        }

        return result;
    }

}
