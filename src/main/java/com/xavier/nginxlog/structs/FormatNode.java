package com.xavier.nginxlog.structs;

/**
 * @author zhengwei
 * @date 2017-08-24
 */
public class FormatNode extends Node {

    private static final int FORMAT_NODE_TYPE = 1;

    public FormatNode(String nodeValue) {
        super(nodeValue, null, FORMAT_NODE_TYPE);
    }
}
