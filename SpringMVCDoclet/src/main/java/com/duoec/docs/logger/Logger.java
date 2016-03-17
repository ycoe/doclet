package com.duoec.docs.logger;

import com.duoec.doclet.helper.StringHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ycoe on 16/1/27.
 */
public class Logger {
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();

    private static Map<String, Logger> loggerMap = new HashMap<String, Logger>();

    /**
     * 时间格式化
     */
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSS");

    private String name;

    private Logger(String name){
        this.name = name;
    };


    public static Logger getInstance(Class clazz){
        String name = clazz.getName();
        if(loggerMap.containsKey(name)){
            return loggerMap.get(name);
        }
        Logger logger = new Logger(name);
        loggerMap.put(name, logger);
        return logger;
    }

    public void info(final String msg, final Object... params){
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(format("INFO", msg, params));
            }
        });
    }

    public void error(final String msg, final Object... params){
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.err.println(format("ERROR", msg, params));
            }
        });
    }

    public void error(final Exception e, final String msg, final Object... params){
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.err.println(format("ERROR", msg, params));
                e.printStackTrace(System.err);
            }
        });
    }


    private String format(String level, String msg, Object... params){
        StringBuilder sb = new StringBuilder();

        sb.append(sdf.format(new Date()));
        sb.append(" [");
        sb.append(level);
        sb.append("] ");
        sb.append(name);
        sb.append(": ");
        if(StringHelper.isNotEmpty(msg)){
            if(params != null && params.length > 0){
                FormattingTuple formated = MessageFormatter.arrayFormat(msg, params);
                sb.append(formated.getMessage());
            }else {
                sb.append(msg);
            }
        }

        return sb.toString();
    }

    public static void shutdown(){
        threadPool.shutdown();
    }
}
