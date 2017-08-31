package com.xavier.nginxlog;

import com.xavier.commom.Constant;
import com.xavier.commom.NginxLogVariable;
import com.xavier.commom.Utils;
import com.xavier.nginxlog.structs.EndNode;
import com.xavier.nginxlog.structs.FormatNode;
import com.xavier.nginxlog.structs.Node;
import com.xavier.nginxlog.structs.SeparatorNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <strong>This class was used to split every text row of log file.</strong>
 * <p>
 * It contains a 'format' linked list that struts is like: <br/>
 * FormatVarNode -> SplitVarNode -> FormatVarNode -> SplitVarNode ... ->
 * EndSplitVarNode
 * </p>
 * <p>
 * This linked list represent a single text row of log file.
 * As we all know, the nginx 'access log file' is formatted by the nginx
 * config file's 'log_format' directive. At the nginx 'log_format'
 * directive, every variables spited by some characters, like '-', ' '
 * (blank),'~' and so on. <br/>
 * So, these variety split character be abstracted SplitVarNode instance,
 * and origin variables that 'log_format' directive were abstracted
 * FormatVarNode instance, the end of config string that not belong
 * format variable or split variable was abstracted the EndSplitVarNode.
 * </p>
 *
 * @author xavier
 * @date 2017-08-22
 */
public class LogFormatter {
    // log format pattern string
    private static String LOG_FORMAT_CONFIG = null;
    private static Pattern PATTERN = null;
    private static Map<String, Integer> FIELD_SEQUENCE_MAP = new HashMap<>();

    public static void init(String logFormat) {
        if (null == LOG_FORMAT_CONFIG || null == PATTERN) {
            synchronized (LogFormatter.class) {
                if (null == LOG_FORMAT_CONFIG || null == PATTERN) {
                    LOG_FORMAT_CONFIG = logFormat;
                    initPattern();
                }
            }
        }
    }

    public static AccessLog format(String logText) {
        if (null == logText || logText.length() == 0) {
            return null;
        }
        Matcher matcher = PATTERN.matcher(logText);
        matcher.find();
        int groupCount = matcher.groupCount();
        List<String> groupString = new ArrayList<>();
        for (int i = 1; i <= groupCount; i++) {
            groupString.add(matcher.group(i));
        }
        AccessLog.Builder builder = new AccessLog.Builder();

        String timePattern = "";
        if (LOG_FORMAT_CONFIG.contains("$time_local")) {
            timePattern = "dd/MMM/yyyy:HH:mm:ss Z";
        } else {
            timePattern = "yyyy-MM-ddTHH:mm:ssZ";
        }

        builder.setIp(getValue(Constant.IP, groupString))
                .setBodyBytesSent(getValue(Constant.BODY_BYTES_SENT, groupString))
                .setBytesSent(getValue(Constant.BYTES_SENT, groupString))
                .setConnection(getValue(Constant.CONNECTION, groupString))
                .setConnectionRequests(getValue(Constant.CONNECTION_REQUESTS, groupString))
                .setHttpReferer(getValue(Constant.HTTP_REFERER, groupString))
                .setPipe(getValue(Constant.PIPE, groupString))
                .setRemoteUser(getValue(Constant.REMOTE_USER, groupString))
                .setRequest(getValue(Constant.REQUEST, groupString))
                .setRequestDateTime(getValue(Constant.REQUEST_DATE_TIME, groupString), timePattern)
                .setRequestLength(getValue(Constant.REQUEST_LENGTH, groupString))
                .setRequestTime(getValue(Constant.REQUEST_TIME, groupString))
                .setStatus(getValue(Constant.STATUS, groupString))
                .setUserAgent(getValue(Constant.USER_AGENT, groupString))
                .setUpstreamCacheStatus(getValue(Constant.UPSTREAM_CACHE_STATUS, groupString))
                .setUpstreamResponseTime(getValue(Constant.UPSTREAM_RESPONSE_TIME, groupString));

        return builder.build();
    }

    /**
     * init PATTERN object, assembly regex expression by sequence of node chain
     */
    private static void initPattern() {
        Node headNode = headNode();
        Node currentNode = headNode;
        int i = -1;
        StringBuilder sb = new StringBuilder();
        while (true) {
            i++;
            if (currentNode == null) break;
            if (i % 2 != 0) {
                String splitValue = Utils.replaceEscapeCode(currentNode.nodeValue);
                sb.append(splitValue);
                currentNode = currentNode.nextNode;
                continue;
            }

            NginxLogVariable nlv = NginxLogVariable.match(currentNode.nodeValue);
            if (nlv == null) {
                throw new IllegalArgumentException("given format string error.");
            }
            String extractRegx = nlv.getExtractRegx();
            sb.append(extractRegx);

            currentNode = currentNode.nextNode;
        }
        System.out.println("regx: " + sb.toString());
        PATTERN = Pattern.compile(sb.toString());
    }

    private static String getValue(String valueName, List<String> groupString) {
        Integer index = FIELD_SEQUENCE_MAP.getOrDefault(valueName, -1);
        if (index == -1) {
            return null;
        }
        return groupString.get(index);
    }

    private static void initFormatVarSequence(List<String> formatVarList) {
        int len = formatVarList.size();

        for (int i = 0; i < len; i++) {
            String format = formatVarList.get(i);
            NginxLogVariable nlv = NginxLogVariable.match(format);
            String key = nlv.getFieldName();
            FIELD_SEQUENCE_MAP.put(key, i);
        }

    }

    /**
     * create a list that contain some node order by : <br/>
     * fNode(formatNode), sNode(separatorNode),fNode,sNode,fNode...eNode(endNode)
     *
     * @return
     */
    private static List<Node> getNodeList() {
        List<String> formatVarList = getFormatVariableList();
        List<String> configSeparatorList = getSeparatorVariableList(formatVarList);
        String endingSeparator = getEndingSeparator(formatVarList);

        initFormatVarSequence(formatVarList);

        int len = formatVarList.size() - 1;
        List<Node> nodeInstanceList = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            String formatVar = formatVarList.get(i);
            Node fvn = new FormatNode(formatVar);
            nodeInstanceList.add(fvn);
            String separatorVar = configSeparatorList.get(i);
            Node svn = new SeparatorNode(separatorVar);
            nodeInstanceList.add(svn);
        }

        Node fvn = new FormatNode(formatVarList.get(len));
        nodeInstanceList.add(fvn);

        Node evn = new EndNode(endingSeparator);
        nodeInstanceList.add(evn);
        return nodeInstanceList;
    }

    /**
     * make node chain list by given params that contains some nodes.
     * @param nodeInstanceList
     * @return
     */
    private static Node makeNodeChain(List<Node> nodeInstanceList) {
        int len = nodeInstanceList.size() - 1;
        for (int i = len; i > 0; i--) {
            Node node = nodeInstanceList.get(i);
            Node preNode = nodeInstanceList.get(i - 1);
            preNode.nextNode = node;
        }
        return nodeInstanceList.get(0);
    }

    private static Node headNode() {
        List<Node> nodeList = getNodeList();
        return makeNodeChain(nodeList);
    }


    /**
     * get config of log format var's sequence list.
     * <p>
     * every variable start with '$', variable's name can contain:
     * <ul>
     * <li>Uppercase(A-Z)</li>
     * <li>Lowercase(a-z)</li>
     * <li>Number(0-9)</li>
     * <li>'_'</li>
     * </ul>
     * if a var start with '$',then the var was deemed end that the var's
     * name string are contain none of above list.
     * In short, a var start with '$' character and end with character
     * that except A-Z,a-z,0-9 and '_'.
     * </p>
     * <p>
     *     e.g: origin format string: ($a...$b~$c - $d$e_f) -->
     *     result list: {$a, $b, $c, $d, $e_f}
     * </p>
     * @return list of format variable
     */
    private static List<String> getFormatVariableList() {
        char[] formatCharArray = LOG_FORMAT_CONFIG.toCharArray();
        int start = -1;
        List<String> formatList = new ArrayList<>();
        for (int i = 0; i < formatCharArray.length; i++) {
            if (Utils.isDollar(formatCharArray[i]) && start == -1) {
                start = i;
            } else if ((!Utils.isVariabelName(formatCharArray[i])) && (start != -1)){
                String formatVar = LOG_FORMAT_CONFIG.substring(start, i);
                formatList.add(formatVar);
                start = -1;
                i--;
            } else if (i == formatCharArray.length - 1 && start != -1) {
                String formatVar = LOG_FORMAT_CONFIG.substring(start, i + 1);
                formatList.add(formatVar);
                start = -1;
            }
        }
        return formatList;
    }

    /**
     * get config of log format separator of vars sequence list.
     * <p>
     * the function of this method is same as {@link #getFormatVariableList},but
     * there has a different part that this method get separator that middle
     * of variables.
     * </p>
     * <p>
     *     e.g: origin format string: ($a...$b~$c - $d$e_f&&&) -->
     *     result list: {...,~, - ,,} (NOTE: not contain the last separator)
     * </p>
     *
     * @param formatVarList
     * @return list of separator
     * @throws IllegalArgumentException when params is null or empty.
     */
    private static List<String> getSeparatorVariableList(List<String> formatVarList) {
        if (formatVarList == null || formatVarList.isEmpty()) {
            throw new IllegalArgumentException("the params is null or empty.");
        }

        int iteratorCount = formatVarList.size() - 1;
        List<String> separatorList = new ArrayList<>(iteratorCount);

        for (int i = 0; i < iteratorCount; i++) {
            String formatVar = formatVarList.get(i);
            int start = LOG_FORMAT_CONFIG.indexOf(formatVar);
            start = start + formatVar.length();
            formatVar = formatVarList.get(i + 1);
            int end = LOG_FORMAT_CONFIG.indexOf(formatVar);
            separatorList.add(LOG_FORMAT_CONFIG.substring(start, end));
        }
        return separatorList;
    }

    /**
     * get the last of log format config's identifier.
     * <p>
     * it's different with {@link #getSeparatorVariableList}, this method
     * only get the identifier or symbol (to be precise, is characters
     * that not a format variable) that end of format string.
     * </p>
     *
     * @param formatVarList
     * @return string of end separator
     * @throws IllegalArgumentException when params is null or empty.
     */
    private static String getEndingSeparator(List<String> formatVarList) {
        if (formatVarList == null || formatVarList.isEmpty()) {
            throw new IllegalArgumentException("the params is null or empty.");
        }

        String endingSeparator = "";
        String formatVar = formatVarList.get(formatVarList.size() - 1);
        int index = LOG_FORMAT_CONFIG.indexOf(formatVar);
        index = index + formatVar.length();

        if (index < LOG_FORMAT_CONFIG.length()) {
            endingSeparator = LOG_FORMAT_CONFIG.substring(index);
        }

        return endingSeparator;

    }

}
