package com.pucho.core.instrumentation;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.activejpa.entity.Condition;
import org.activejpa.entity.Filter;

import com.pucho.domain.BlockedPhones;

import io.dropwizard.lifecycle.Managed;

public class MemoryCache {
    public static MemoryCache INSTANCE = new MemoryCache();
    private ConcurrentHashMap<String, Object> cache;
    private final String BLOCKED_PHONE_KEY = "block_";
    private boolean initialized = false;

    private MemoryCache() {
        this.cache = new ConcurrentHashMap<>();
    }

    public void set(String key, Object value) {
        initializeBlockedPhoneCache();
        this.cache.put(key, value);
    }
    
    public Object get(String key, Object value) {
        initializeBlockedPhoneCache();
        if (this.cache.containsKey(key)) {
            return this.cache.get(key);
        }
        return value;
    }
    
    public Object pop(String key) {
        initializeBlockedPhoneCache();
        Object value = this.cache.get(key);
        this.cache.remove(key);
        return value;
    }
    
    private void initializeBlockedPhoneCache() {
        if (!this.initialized) {
            List<BlockedPhones> blockedPhones = BlockedPhones.all();
            String key;
            for (BlockedPhones blockedPhone : blockedPhones) {
                key = BLOCKED_PHONE_KEY + blockedPhone.getPhone();
                this.set(key, true);
            }
            this.initialized = true;
        }
    }
    
    public boolean isBlocked(String phone) {
        String key = BLOCKED_PHONE_KEY + phone;
        return (boolean)this.get(key, false);
    }
    
    public void blockPhone(String phone) {
        initializeBlockedPhoneCache();
        BlockedPhones blockedPhone = new BlockedPhones();
        blockedPhone.setPhone(phone);
        blockedPhone.persist();
        String key = BLOCKED_PHONE_KEY + phone;
        this.set(key, true);
    }
    
    public void unBlock(String phone) {
        initializeBlockedPhoneCache();
        Filter filter = new Filter();
        filter.addCondition("phone", Condition.Operator.eq, phone);
        List<BlockedPhones> blockedPhonesList = BlockedPhones.where(filter);
        BlockedPhones blockedPhone;
        if (blockedPhonesList == null || blockedPhonesList.size() == 0) {
            //nothing to unblock
        }
        else {
            blockedPhone = blockedPhonesList.get(0);
            String key = BLOCKED_PHONE_KEY+blockedPhone.getPhone();
            blockedPhone.delete();
            this.pop(key);
        }
    }
}
