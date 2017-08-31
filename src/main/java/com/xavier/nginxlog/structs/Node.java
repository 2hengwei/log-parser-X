package com.xavier.nginxlog.structs;


/**
 * @author xavier
 * @date 2017-08-24
 */
public abstract class Node {

    public String nodeValue;
    public Node nextNode;
    public int nodeType;

    Node(String nodeValue, Node nextNode, int nodeType) {
        this.nodeValue = nodeValue;
        this.nextNode = nextNode;
        this.nodeType = nodeType;
    }

}
