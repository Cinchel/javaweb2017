package com;

import org.hibernate.dialect.MySQL5Dialect;

/**
 * Created by mzzhang on 2017/5/27.
 */
public class MySQL5DialectUTF8 extends MySQL5Dialect {
    @Override
    public String getTableTypeString() {
        return " ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
