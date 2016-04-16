package com.hou.recruitment.vo;

import java.io.Serializable;

/**
 * @author Fred Liu(liuxiaokun0410@gmail.com)
 * @version v1.0.0
 * @since 2016-04-16 14:03
 */
public class LoginResponse implements Serializable{

    private ExpertInfo expert;


    public ExpertInfo getExpert() {
        return expert;
    }

    public void setExpert(ExpertInfo expert) {
        this.expert = expert;
    }
}
