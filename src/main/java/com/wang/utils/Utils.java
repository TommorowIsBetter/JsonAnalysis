package main.java.com.wang.utils;

import com.alibaba.fastjson.JSON;

import main.java.com.wang.entity.JsonBean;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    /**
     * 分割数组
     * @param source 原数组
     * @param number 每个数组的集合
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> spilt(List<T> source, int number) {
        int divisor = source.size() / number; // 获取商
        List<List<T>> totallist = new ArrayList<List<T>>();
        for (int i = 0; i < number; i++) {
            if (i != (number - 1)) {
                totallist.add(source.subList(i * divisor, (i + 1) * divisor));
            } else {
                totallist.add(source.subList(i * divisor, source.size()));
            }
        }
        return totallist;
    }


    /**
     * 读取复制文件
     * @param filepath 文件路径
     * @param tempFile 文件
     */
   public static void ReadAndMove(String filepath,File tempFile){
        String fileName = tempFile.getName();
        String pathName = filepath + File.separator + fileName;
        File file = new File(pathName);
        StringBuffer stringBuffer = new StringBuffer();
        BufferedReader bf=null;
        try {
            bf = new BufferedReader(new FileReader(file));
            String line = null;
            System.out.println("开始读取文件,文件名:"+fileName);
            while ((line = bf.readLine()) != null) {
                stringBuffer.append(line);
            }
            bf.close();
            System.out.println("文件:"+fileName+"读取结束");
            JsonBean jsonBean = new JsonBean();

            try {
                //将字符串转换json对象
                jsonBean = JSON.parseObject(stringBuffer.toString(), JsonBean.class);

            } catch (Exception e) {
                System.out.println("文件格式不对");
            }
            String packageName = "";
            try {
                //获取包名
                packageName = jsonBean.getEvents().get(0).getSource().getPackagename();
            } catch (Exception e) {
                System.out.println(e.getMessage() + ":" + fileName);

            }
            //文件移动
            File tempFileMove = new File(filepath + File.separator + packageName + File.separator + fileName);
            System.out.println("文件开始复制");
            FileUtils.copyFile(file, tempFileMove);
            System.out.println("文件复制结束");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("找不到文件");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
