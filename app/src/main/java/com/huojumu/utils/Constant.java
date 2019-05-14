package com.huojumu.utils;

/**
 * @author : Jie
 * Date: 2018/5/25
 * Description:
 */
public interface Constant {

    /* api interface */
    //base url
    String BASE_URL = "https://www.goodb2b.cn/pos/sale_inte/";//http://192.168.1.177:9873/pos/sale_inte/      39：https://www.goodb2b.cn/pos/sale_inte
    String SOCKET = "wss://www.goodb2b.cn/pos/sale_inte/shopws";
    String BAND = "{\"task\": \"heartbeat\",\"machineCode\":\"%s\"}";
    String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    int WORK_BACK_OVER = 0x002;
    int WORK_BACK_DAILY = WORK_BACK_OVER << 1;
    /* debug param */
    String UUID = "pos_uuid";
    String STORE_ID = "sId";
    String STORE_NAME = "sName";
    String STORE_ADDRESS = "sAddress";
    String STORE_TEL = "storeTel";
    String PINPAI_ID = "ppId";
    String ENT_ID = "entId";
    String ENT_NAME = "entName";
    String ENT_DIS = "entDis";
    String HTML = "HTML";
    String WORK_P = "woeker_p";
    String ORDER_NUM = "orderNum";

    String SERVICE_ACTION = "newServiceAction";

    /* network params */
    String MY_TOKEN = "my_token";
    String TOKEN = "Authorization";

    /* database params */
    String SP_NAME = "POS_SP";

    /* default string */
    String DEFAULT_STR = "";
    int DEFAULT_INT = 1;
    int LOGIN = 2;
    int HOME = 3;
    int LOGIN_FAILED = 4;

    /* 是否绑定设备 */
    String HAS_BAND = "has_banded";
    String EQP_NO = "eqp_no";//机器码

    /* works' info */
    String WORKER_NAME = "worker_name";
    /* 小票宽度 */
    String PRINT_WIDTH = "w58or80";
    String PRINT_LANG = "language";

    /* socket 字段*/
    String BIND = "machinebind";
    String PAYCODE = "payAndPrint";
    String START = "start";

}
