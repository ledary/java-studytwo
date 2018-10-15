package main.java.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 *给定一个包括 n 个整数的数组 S，找出 S 中的三个整数
 * 使得他们的和与给定的数 target 最接近。返回这三个数的和。
 * Created by wgp on 2018/10/11.
 */
public class LeetCode16 {
    public static void main(String[] args) {
        int[] nums = {1,2,4,6,8,32,64,128};
        Integer target = 82;
        System.out.println(new LeetCode16().test(nums,target));
    }
    private Integer test(int[] nums,int target){

       Arrays.sort(nums);
        Integer result = nums[0] + nums[1] + nums[nums.length-1];
        for (int i=0;i<nums.length-2;i++){
            int start = i+1;
            int end = nums.length-1;
            while (start<end ){
                Integer sum = nums[i] + nums[start] + nums[end];
                if(sum>target){
                    end--;
                }
                if(sum<target){
                    start++;
                }
                if(sum == target){
                    result=target;
                    return result;
                }
                if(Math.abs(sum-target)<=Math.abs(result-target)){
                    result = sum;
                }
            }

        }
        return result;

    }

}
