package com.qskx.springbootnio;

/**
 * @author 111111
 * @date 2019-02-16 21:01
 */
public class NodeList {

    private int num;
    private NodeList next;

    public NodeList(int num, NodeList next) {
        this.num = num;
        this.next = next;
    }

    private static NodeList three = new NodeList(8, null);
    private static NodeList two = new NodeList(5, three);
    private static NodeList one = new NodeList(2, two);

    private static NodeList node_3 = new NodeList(6, null);
    private static NodeList node_2 = new NodeList(3, node_3);
    private static NodeList node_1 = new NodeList(1, node_2);

    public static void main(String[] args) {
        NodeList resNode = null;
        NodeList group_1 = node_1;
        NodeList group_2 = one;
        NodeList tempNode = null;
        while (group_1 != null && group_2 != null) {
            int num_1 = group_1.num;
            int num_2 = group_2.num;
            if (num_1 <= num_2) {
                if (resNode == null) {
                    resNode = group_1;
                    tempNode = resNode;
                } else {
                    tempNode.next = group_1;
                    tempNode = tempNode.next;
                }

                group_1 = group_1.next;
            } else {
                if (resNode == null) {
                    resNode = group_2;
                    tempNode = resNode;
                } else {
                    tempNode.next =group_2;
                    tempNode = tempNode.next;
                }
                group_2 = group_2.next;
            }

        }

        if (group_1 != null) {
            NodeList nextNode = resNode;
            while (nextNode.next != null) {
                nextNode = resNode.next;
            }
            nextNode.next = group_1;
        }

        if (group_2 != null) {
            NodeList nextNode = resNode;
            while (nextNode.next != null) {
                nextNode = nextNode.next;
            }
            nextNode.next = group_2;
        }

        while (resNode != null) {
            System.out.print(resNode.num + " ");
            resNode = resNode.next;
        }
    }

}
