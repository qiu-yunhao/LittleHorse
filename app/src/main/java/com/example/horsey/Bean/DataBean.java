package com.example.horsey.Bean;

import java.util.List;

public class DataBean {
    List<Integer> title;//题目上图画
    List<Integer> answer;//题目下图画
    List<Integer> result;//题目答案
    List<Integer> change;//对应替换
    String music;//题目语音

    public DataBean(List<Integer> title,List<Integer> answer,List<Integer> result,String music,List<Integer> change){
        this.title = title;
        this.answer = answer;
        this.result = result;
        this.music = music;
        this.change = change;
    }

    public List<Integer> getTitle() {
        return title;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public List<Integer> getResult() {
        return result;
    }

    public String getMusic() {
        return music;
    }

    public List<Integer> getChange() {
        return change;
    }
}
