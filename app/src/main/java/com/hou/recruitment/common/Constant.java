package com.hou.recruitment.common;

/**
 * @author Fred Liu(liuxiaokun0410@gmail.com)
 * @version v1.0.0
 * @since 2016-04-12 17:21
 */
public class Constant {

    public static final String BASE_URL = "http://192.168.1.101:8080/rest/mobile/";

    public static final String LOGIN_URL =  BASE_URL + "getExpert/";
    public static final String GET_STUDENT_INFO = BASE_URL + "getSingleStudent";
    public static final String SUBMIT_SCORE = BASE_URL + "uploadScore/";




    public static final String RE = "{\"expert\":{\"expertId\":\"id\",\"id\":\"id\",\"kcNum\":\"kcnum\",\"name\":\"name\",\"password\":\"pwd\",\"sex\":\"sex\",\"subjectId\":\"subjectId\",\"subjectName\":\"subjectName\"}}";

}
