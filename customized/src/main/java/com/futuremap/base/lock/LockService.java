package com.futuremap.base.lock;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class LockService {


    @Resource
    private RedissonClient redissonClient;

    private Integer lockReTryTime = 3;

    private long waiteTime = 10;

    private long expireTime = 30;
    /**
     * 获取读锁用于存数据
     *
     * @param computeUuids
     */
    public void getReadLock(List<String> computeUuids) {
        getLock(waiteTime, expireTime, computeUuids, true);
    }

    /**
     * 释放读锁
     *
     * @param computeUuids
     */
    public void unReadLock(List<String> computeUuids) {
        unLock(computeUuids, true);
    }

    /**
     * 获取读锁用于存数据
     *
     * @param computeUuids
     */
    public void getWriteLock(List<String> computeUuids) {
        getLock(waiteTime, expireTime, computeUuids, false);
    }

    /**
     * 释放读锁
     *
     * @param computeUuids
     */
    public void unWriteLock(List<String> computeUuids) {
        unLock(computeUuids, false);
    }

    /**
     * 获取读锁用于存数据
     *
     * @param computeUuids
     */
    public void getLock(long waiteTime, long expireTime, List<String> computeUuids, boolean read) {
        log.info("批量获取{}锁 computeUuid:{}", read ? "读" : "写", JSON.toJSONString(computeUuids));
        List<RLock> lockedList = new ArrayList<>();
        try {
            for (String computeUuid : computeUuids) {
               String lockName = computeUuid;
                RLock rLock;
                if (read) {
                    rLock = redissonClient.getReadWriteLock(lockName).readLock();
                } else {
                    rLock = redissonClient.getReadWriteLock(lockName).writeLock();
                }

                boolean isLocked = false;
                int count = 0;
                log.info("lockName:{} 开始获取{}锁", lockName, read ? "读" : "写");
                // 尝试3次获取，获取失败全部释放
                do {
                    try {
                        // 尝试获取锁（等待时间,过期时间,时间单位）
                        isLocked = rLock.tryLock(waiteTime, expireTime, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        log.info("获取锁失败：{}", rLock.getName());
                        count++;
                    }
                } while (!isLocked && count < lockReTryTime);

                if (count == 3) {
                    log.info("================={}锁申请失败{}，开始释放============================", read ? "读" : "写", lockName);
                    break;
                } else {
                    lockedList.add(rLock);
                    log.info("================={}锁申请成功{}============================", read ? "读" : "写", lockName);
                }
            }
        } finally {
            if (computeUuids.size() != lockedList.size()) {
                log.info("=================存在{}锁申请失败，开始释放============================", read ? "读" : "写");
                lockedList.forEach(lock -> {
                    lock.unlock();
                    log.info("================={}锁释放成功{}============================", read ? "读" : "写", lock.getName());
                });
            }
        }


    }

    /**
     * @param computeUuids
     */
    public void unLock(List<String> computeUuids, boolean read) {
        log.info("批量释放{}锁 computeUuid:{}", read ? "读" : "写", JSON.toJSONString(computeUuids));
        for (String computeUuid : computeUuids) {
            String lockName = computeUuid;
            log.info("lockName:{} 开始释放{}锁", lockName, read ? "读" : "写");
            RLock rLock;
            try {
                if (read) {
                    rLock = redissonClient.getReadWriteLock(lockName).readLock();
                } else {
                    rLock = redissonClient.getReadWriteLock(lockName).writeLock();
                }
                rLock.unlock();
            } catch (Exception e) {
                log.info("释放时获取锁失败，继续获取下一把:{}", lockName);
            }
            log.info("lockName:{} 释放{}锁成功", lockName, read ? "读" : "写");
        }
        log.info("================={}锁释放成功============================", read ? "读" : "写");
    }
}
