package com.yd1994.alpaca.statisticsservice;

import com.huaban.analysis.jieba.JiebaSegmenter;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
public class TestJieba {

    @Test
    public void testDemo() {
        JiebaSegmenter segmenter = new JiebaSegmenter();
        String[] sentences = new String[] {
                        "在旷野上游走的牧人，能否听懂牛羊噬草时齿动声的语意？",
                        "那年的杜鹃已化做次年的春泥，为何，为何你的湖水碧绿依然如今？那年的人事已散成凡间的风尘，为何，为何你的春闺依旧年年年轻？",
                        "人之将老，若无忠言，必有落叶。",
                        "忽然忘记生气的技术，像断臂人不知如何接对方递来的一杯酒。"
        };
        for (String sentence : sentences) {
            System.out.println(segmenter.process(sentence, JiebaSegmenter.SegMode.SEARCH).toString());
        }
    }

    @Test
    public void test() {
        Integer[] is = new Integer[1000];
        for (int i = 0; i < 1000; i++) {
            is[i] = i;
        }
        Arrays.asList(is).stream().forEach(i -> {
            System.out.print(i);
            System.out.print(' ');
        });
    }

}
