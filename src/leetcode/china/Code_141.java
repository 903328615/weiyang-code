package leetcode.china;

import base.structure.ListNode;

/**
 * @program: weiyang-code
 * @description: 141. 环形链表
 * 给定一个链表，判断链表中是否有环。
 * @author: wangzibin
 * @create: 2020-12
 **/
public class Code_141 {


    /*
     *  @Description: 判断链表中是否有环
     * 思路为 使用快慢指针，如果链表有环快慢指针一定可以重合
     * 时间 O(n) 空间 O(1)
     *  @Param head
     *  @Return: boolean
     *  @Author: wangzibin
     *  @Date: 2020/12/30
     */
    public boolean hasCycle(ListNode head) {

        if(head==null||head.next==null){
            return false;
        }
        ListNode slow=head;
        ListNode fast=head.next;
        while(fast!=null&&fast.next!=null){
            if(fast==slow){
                return true;
            }
            //快指针走两步 慢指针走一步
            fast=fast.next.next;
            slow=slow.next;
        }
        return false;

    }
}