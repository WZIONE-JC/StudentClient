package com.example.studentclient;

import android.os.Environment;

class MyStaticValue {


    static final int STATE_OK = 0;
    static final String LOGIN_PATH = "http://122.51.186.91:8081/student/login";
    static final String SIGNUP_PATH = "http://122.51.186.91:8081/student/register";
    static final String UPDATE_INFO_PATH = "http://122.51.186.91:8081/student/updateInfo";
    static final String UPLOAD_AVATAR_PATH = "http://122.51.186.91:8081/file/uploadAvatar";
    static final String GET_AVATAR_PATH = "http://122.51.186.91:8081/file/getAvatar";
    static final String GET_INFO_PATH = "http://122.51.186.91:8081/student/personal";
    static final String JOIN_CLASS = "http://122.51.186.91:8081/course/joinClass";
    static final String GET_CLASS_INFO = "http://122.51.186.91:8081/course/getClassInfo";
    static final String GET_ANNOUNCE = "http://122.51.186.91:8081/course/getAnnounce";
    static final String GET_FILE_INFO = "http://122.51.186.91:8081/file/getFileInfo";
    static final String DOWNLOAD_FILE = "http://122.51.186.91:8081/file/getFile";
    static final String GET_HOMEWORK = "http://122.51.186.91:8081/course/getHomeworkInfo";
    static final String GET_TEST = "http://122.51.186.91:8081/course/getTestInfo";
    static final String GET_CALL_INFO = "http://122.51.186.91:8081/course/getCallInfo";
    static final String JOIN_CALL = "http://122.51.186.91:8081/course/joinCall";
}
