package main.java.com.wang.thread;


import java.io.File;
import java.util.List;

import main.java.com.wang.utils.Utils;

 public class MoveThread implements Runnable{

    private List<File> files;   //文件集合

    private String filePath;   //文件路径

     public MoveThread(List<File> files, String filePath) {
         this.files = files;
         this.filePath = filePath;
     }

     //开启线程读取 复制文件
     public void run() {
        for(int i=0;i<files.size();i++){
            Utils.ReadAndMove(filePath,files.get(i));
            System.out.println("线程:"+Thread.currentThread().getName()+"正在执行任务");
        }
    }
}