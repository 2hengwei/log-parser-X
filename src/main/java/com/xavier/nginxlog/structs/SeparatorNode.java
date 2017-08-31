package com.xavier.nginxlog.structs;

/**
 * @author zhengwei
 * @date 2017-08-24
 */
public class SeparatorNode extends Node {

    private static final int SEPARATOR_NODE_TYPE = 2;

    public SeparatorNode(String nodeValue) {
        super(nodeValue, null, SEPARATOR_NODE_TYPE);
    }
}
