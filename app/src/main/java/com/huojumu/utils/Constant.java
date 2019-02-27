package com.huojumu.utils;

/**
 * @author : Jie
 * Date: 2018/5/25
 * Description:
 */
public interface Constant {

    /* api interface */
    //base url
    String BASE_URL = "http://47.105.207.183/pos/sale_inte/";//http://47.105.207.183/pos/sale_inte/ ；http://192.168.1.177:9873/pos/sale_inte/
    String SOCKET = "ws://47.105.207.183/pos/sale_inte/shopws";
    String HEART = "{\"task\": \"heartbeat\",\"machineCode\":\"%s\",\"shopID\":%s,\"eqpType\":\"3\"}";
    String BAND = "{\"task\": \"heartbeat\",\"machineCode\":\"%s\"}";
    String PMENT = "{\"task\": \"pay\",\"data\":{\"orderCode\":\"%s\",\"state\": \"%s\"}}}";

    /* debug param */
    boolean IS_DEBUG = true;
    String NO_DATA_BACK = "无返回值";
    String UUID = "pos_uuid";
    String STORE_ID = "sId";
    String STORE_NAME = "sName";
    String STORE_ADDRESS = "sAddress";
    String STORE_TEL = "storeTel";
    String PINPAI_ID = "ppId";
    String ENT_ID = "entId";
    String ENT_NAME = "entName";
    String ENT_DIS = "entDis";

    /* network params */
    int REQUEST_CODE_PICK_IMAGE = 1;
    String TOKEN_TIME = "token_time";
    String TOKEN_LAST_TIME = "token_last_time";
    int TOKEN_KEEP_ALIVE_TIME = 10 * 60 * 1000;
    String MY_TOKEN = "my_token";
    String TOKEN = "Authorization";
    String EQP_TYPE = "POS";

    /* database params */
    String ORDER_TEMP = "order_temp";
    String ORDER_NO = "order_no";
    String ORDER_INFO = "order_table";
    String SP_NAME = "POS_SP";

    /* default string */
    String DEFAULT_STR = "";
    int DEFAULT_INT = 1;
    int PAY = 1;
    int LOGIN = 2;
    int HOME = 3;

    /* pay method */
    String PAY_BY_ALI = "PayByAli";
    String PAY_BY_WX = "PayByWX";
    String PAY_BY_BANK = "PayByBank";
    String PICK_CODE = "pickUpCode";

    /* 是否绑定设备 */
    String HAS_BAND = "has_banded";
    String EQP_NO = "eqp_no";//机器码

    /* works' info */
    String WORKER_NAME = "worker_name";
    String WORKER_TEL = "worker_tel";
    String TYPE = "type";

    /* 小票宽度 */
    String PRINT_WIDTH = "w58or80";

    /* socket 字段*/
    String BIND = "machinebind";
    String PAYCODE = "pay";
    String START = "start";

}
