package io.github.xh_dev.rnr2.utils;

import java.util.Arrays;

public class LogMessageFormatter{
    private static String loadingClassingName=null;
    private static String loadingMethodName=null;

    private final String methodName;
    private final String className;

    public LogMessageFormatter(String className, String methodName) {
        this.methodName = methodName;
        this.className=className;
    }

    public static LogMessageFormatter instance(){
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        if (loadingClassingName == null) {
            StackTraceElement curStack = Thread.currentThread().getStackTrace()[1];
            loadingClassingName = Arrays.stream(curStack.getClassName().split("\\.")).reduce((fisrt, second) -> second).get();
        }
        if (loadingMethodName == null) {
            StackTraceElement curStack = Thread.currentThread().getStackTrace()[1];
            loadingMethodName = curStack.getMethodName();
        }
        int foundAt = -1;
        for(int i=0; i<stacks.length; i++){
            if(stacks[i].getClassName().endsWith(loadingClassingName) && stacks[i].getMethodName().equals(loadingMethodName)){
                foundAt = i+1;
                break;
            }
        }
        String classNameDetected = Arrays.stream(
                stacks[foundAt].getClassName()
                .split("\\.")
        )
                .reduce((first, second) -> second)
                .orElseThrow(()->new RuntimeException("Fail to extract class name"));
        String methodNameDetected = stacks[foundAt].getMethodName();
        return new LogMessageFormatter(classNameDetected, methodNameDetected);
    }

    public String msg(String msg){
        return String.format("%s : %s : %s", className, methodName, msg);
    }

    public String methodStart(){
        return msg("Starts");
    }

    public String methodEnd(){
        return msg("Ends");
    }
}
