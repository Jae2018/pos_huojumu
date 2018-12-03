package com.huojumu.printer;

/**
 * @author : Jie
 * Date: 2018/6/1
 * Description:
 */
public interface InterfaceAPI {

    int SUCCESS = 0;
    int FAIL = -1;
    int ERR_PARAM = -2;

    int openDevice();

    int closeDevice();

    Boolean isOpen();

    int readBuffer(byte[] readBuffer, int offsetSize, int readSize, int ReadTimeOut);

    int writeBuffer(byte[] writeBuffer, int offsetSize, int writeSize, int waitTime);

}
