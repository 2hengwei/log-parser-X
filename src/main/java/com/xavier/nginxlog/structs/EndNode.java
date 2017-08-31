package com.xavier.nginxlog.structs;

/**
 * @author zhengwei
 * @date 2017-08-24
 */
public class EndNode extends Node {

    private static final int END_NODE_TYPE = 3;

    public EndNode(String nodeValue) {
        super(nodeValue, null, END_NODE_TYPE);
    }
}
