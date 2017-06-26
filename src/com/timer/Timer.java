package com.timer;


import com.dao.ExamDao;
import com.entity.Exam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;


public class Timer {
    @Autowired
    private ExamDao examDao;

    @Scheduled(cron="0 0 15 * * *")
   public void sendMesssage(){

        System.out.print("近期监考");


         List<Exam> list=examDao.examList();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
   }
    public static void main(String[] args){
        Timer timer=new Timer();
        timer.sendMesssage();

    }
}

