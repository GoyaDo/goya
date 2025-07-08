package com.ysmjjsy.goya.module.ip2region.domain;

import com.google.common.base.MoreObjects;
import com.ysmjjsy.goya.module.ip2region.searcher.IpV4Searcher;

public class Header {

    public final int version;
    public final int indexPolicy;
    public final int createdAt;
    public final int startIndexPtr;
    public final int endIndexPtr;
    public final byte[] buffer;

    public Header(byte[] buff) {
        assert buff.length >= 16;
        version = IpV4Searcher.getInt2(buff, 0);
        indexPolicy = IpV4Searcher.getInt2(buff, 2);
        createdAt = IpV4Searcher.getInt(buff, 4);
        startIndexPtr = IpV4Searcher.getInt(buff, 8);
        endIndexPtr = IpV4Searcher.getInt(buff, 12);
        buffer = buff;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("version", version)
                .add("indexPolicy", indexPolicy)
                .add("createdAt", createdAt)
                .add("startIndexPtr", startIndexPtr)
                .add("endIndexPtr", endIndexPtr)
                .add("buffer", buffer)
                .toString();
    }
}
