package main.java.com.wang;

import main.java.com.wang.thread.MoveThread;
import main.java.com.wang.utils.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Read {
    //源文件在resource下
    static String filepath = "E:\\json";//D盘下的json文件夹的目录

    static int threadNum = 4; //开启线程的数量

    static void read() {
        //创建线程池 核心线程数100个 容量200个 堵塞队列100个
        ThreadPoolExecutor executor = new ThreadPoolExecutor(100, 200, 200,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        File baseFile = new File(filepath);//File类型可以是文件也可以是文件夹

        File[] fileList = baseFile.listFiles();//将该目录下的所有文件放置在一个File类型的数组中

        //将数组转成集合
        List<File> files=new ArrayList(Arrays.asList(fileList));
        //将大集合分割成100个小集合
        List<List<File>> fileslist=Utils.spilt(files,100);
           //开启线程池
        for(int i=0;i<fileslist.size();i++){
            executor.execute(new MoveThread(fileslist.get(i),filepath));
            showlog(executor);
        }
        executor.shutdown();//关闭线程池


    }

    public static void main(String[] args) {
        read();
    }


    /**
     * 打印线程池的一些数据
     * @param executor
     */
    static void showlog(ThreadPoolExecutor executor ){
        System.out.println("线程池中线程数目:" + ((ThreadPoolExecutor) executor).getPoolSize()
                + ",队列中等待执行的任务数目:" + ((ThreadPoolExecutor) executor).getQueue().size()
                + ",已执行完别的任务数目:" + ((ThreadPoolExecutor) executor).getCompletedTaskCount()
                + ",任务总数" + ((ThreadPoolExecutor) executor).getTaskCount());
    }






}
