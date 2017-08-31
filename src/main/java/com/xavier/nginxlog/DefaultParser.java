package com.xavier.nginxlog;


import com.xavier.nginxlog.structs.Node;

public class DefaultParser {

    public String parse(Node configStruct) {

//        LogFormatter.Node currentNode = configStruct;
//        int i = -1;
//        StringBuilder sb = new StringBuilder();
//        while (true) {
//            i++;
//            if (currentNode == null) break;
//            if (i % 2 != 0) {
//                String splitValue = currentNode.nodeValue;
//                splitValue = splitValue.replaceAll("\\s", "\\\\s");
//                splitValue = splitValue.replaceAll("\\[", "\\\\[");
//                //splitValue = splitValue.replaceAll("]", "\\\\]");
//                sb.append(splitValue);
//                currentNode = currentNode.nextNode;
//                continue;
//            }
//
//            NginxLogVariable nlv =
//                    NginxLogVariable.match(currentNode.nodeValue);
//            String extractRegx = nlv.getExtractRegx();
//            sb.append(extractRegx);
//
//            currentNode = currentNode.nextNode;
//        }
//        return sb.toString();
        return null;

    }

}
