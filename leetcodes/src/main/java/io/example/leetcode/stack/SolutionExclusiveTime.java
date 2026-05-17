package io.example.leetcode.stack;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import lombok.extern.slf4j.Slf4j;

/**
 * On a single-threaded CPU, we execute a program containing n functions. Each function has a unique ID between 0 and n-1.
 * <p>
 * Function calls are stored in a call stack: when a function call starts, its ID is pushed onto the stack, and when a function call ends, its ID is popped off the stack. The function whose ID is at the top of the stack is the current function being executed. Each time a function starts or ends, we write a log with the ID, whether it started or ended, and the timestamp.
 * <p>
 * You are given a list logs, where logs[i] represents the ith log message formatted as a string "{function_id}:{"start" | "end"}:{timestamp}". For example, "0:start:3" means a function call with function ID 0 started at the beginning of timestamp 3, and "1:end:2" means a function call with function ID 1 ended at the end of timestamp 2. Note that a function can be called multiple times, possibly recursively.
 * <p>
 * A function's exclusive time is the sum of execution times for all function calls in the program. For example, if a function is called twice, one call executing for 2 time units and another call executing for 1 time unit, the exclusive time is 2 + 1 = 3.
 * <p>
 * Return the exclusive time of each function in an array, where the value at the ith index represents the exclusive time for the function with ID i.
 */
@Slf4j
public class SolutionExclusiveTime {
    public int[] exclusiveTime(int n, List<String> logs) {
        var result = new int[n];
        var allStack = new Stack<StackItem>();
        var startStack = new Stack<StackItem>();
        var lastUnterminatedStart = parse(logs.get(0));
        allStack.push(lastUnterminatedStart);
        startStack.push(lastUnterminatedStart);
        for (int i = 1; i < logs.size(); i++) {

            var item = parse(logs.get(i));
            var top = allStack.peek();
            if (top.functionId == item.functionId && top.start && item.end()) {
                result[top.functionId] += item.timestamp - top.timestamp;
                startStack.pop();
            } else {

                if (top.start && item.start) {
                    result[top.functionId] += item.timestamp - top.timestamp;
                    startStack.push(item);
                } else if (top.end() && item.end()) {
                    result[item.functionId] += item.timestamp - top.timestamp;
                    startStack.pop();
                } else if (top.end() && item.start) {
                    if (startStack.empty()) {
                        System.out.println("no enclosing invocation");
                    } else {
                        result[startStack.peek().functionId] += item.timestamp - top.timestamp;
                    }
                    startStack.push(item);
                } else {
                    System.out.println("Hmm: item: " + item + ", top: " + top);
                }
            }
            allStack.push(item);
        }
        System.out.println("Stack: " + allStack.size() + ": " + allStack);
        System.out.println("Stack: " + startStack.size() + ": " + startStack);
        return result;
    }

    private StackItem parse(String log) {
        var items = log.split(":");
        var functionId = Integer.parseInt(items[0]);
        var isStart = items[1].equals("start");
        var timestamp = isStart ? Integer.parseInt(items[2]) : Integer.parseInt(items[2]) + 1;
        return new StackItem(functionId, isStart, timestamp);
    }

    public record StackItem(int functionId, boolean start, int timestamp) {
        public boolean end() {
            return !start;
        }
    }

}
