package com.xavier.nginxlog;

import com.xavier.nginxlog.structs.Node;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xavier
 * @date 2017-08-25
 */
public class TestLogFormatter {

    @Test
    public void testGetFormatVariableList() {
        String format = "$remote_addr$a..$B~$c$d$e_f";
        LogFormatter.LOG_FORMAT_CONFIG = format;
        List<String> list = LogFormatter.getFormatVariableList();
        list.forEach(System.out::println);
    }

    @Test
    public void testGetSeparatorVariableList() {
        String format = "$remote_addr";
        LogFormatter.LOG_FORMAT_CONFIG = format;

        List<String> list = LogFormatter.getFormatVariableList();
        List<String> separatorVariableList = LogFormatter.getSeparatorVariableList(list);
        System.out.println(separatorVariableList.size());
        System.out.println(separatorVariableList);
    }

    @Test
    public void testGetEndingSeparator() {
        String format = "$remote_addr~";
        LogFormatter.LOG_FORMAT_CONFIG = format;

        List<String> list = LogFormatter.getFormatVariableList();
        String endingSeparator = LogFormatter.getEndingSeparator(list);
        System.out.println("last separator:" + endingSeparator);
    }

    @Test
    public void testGetNodeList() {
        String format = "$remote_addr % $remote_user";
        LogFormatter.LOG_FORMAT_CONFIG = format;

        List<Node> nodeList = LogFormatter.getNodeList();
        System.out.println("size:" + nodeList.size());
        System.out.println(nodeList);
    }

    @Test
    public void testMakeNodeChain() {
        String format = "$remote_addr~";
        LogFormatter.LOG_FORMAT_CONFIG = format;

        List<Node> nodeList = LogFormatter.getNodeList();
        Node node = LogFormatter.makeNodeChain(nodeList);

        System.out.println(node);

    }

    @Test
    public void testInitPattern() {
        String format = "$remote_addr - $remote_user%%%fuck";
        LogFormatter.LOG_FORMAT_CONFIG = format;

        LogFormatter.initPattern();
    }

    @Test
    public void testRegex() {
        String format = "$remote_addr";
        //LogFormatter.init(format);


        Pattern compile = Pattern.compile("(\\d{0,3}\\.\\d{0,3}\\.\\d{0,3}\\.\\d{0,3})");
        LogFormatter.PATTERN = compile;
        String logText = "110.19.104.123";

        Matcher matcher = LogFormatter.PATTERN.matcher(logText);
        matcher.find();
        int groupCount = matcher.groupCount();
        System.out.println("count:" + groupCount);

        for (int i = 1; i <= groupCount; i++) {
            System.out.println(matcher.group(i));
        }
    }

}
