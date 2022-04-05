package com.utudo.hwwd.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HwDatas {


    public static final String PERSON_MALE = "先生";
    public static final String PERSON_FEMALE = "女士";
    public static final String PERSON_OTHER = "其他";

    public static final int PERSON_TYPE_USER = 0x00000001;
    public static final int PERSON_TYPE_PARTNER = 0x00000002;
    public static final int PERSON_TYPE_ADMIN = 0x00000003;
    public static final int PERSON_TYPE_COBBER = 0x00000004;


    public static final int PERSON_TYPE_ADMIN_STAGE = 0x00000006;
    public static final int PERSON_TYPE_ADMIN_NORMAL = 0x00000007;
    public static final int PERSON_TYPE_ADMIN_MANAGE = 0x00000008;
    public static final int PERSON_TYPE_ADMIN_SUPER = 0x00000009;



    public static final int PERSON_TYPE_USER_PROFILE = 0x00000011;
    public static final int PERSON_TYPE_USER_EMAIL = 0x00000012;


    public static final int PERSON_TYPE_USER_BLOCK = 0x00000000;
    public static final int PERSON_TYPE_USER_ACTIVE = 0x00000001;


    public static final int PERSON_TYPE_ADMIN_STATUS_BLOCK = 0x00000000;
    public static final int PERSON_TYPE_ADMIN_STATUS_ACTIVE = 0x00000001;


    public static final int WEB_MESSAGE_STATUS_UNREAD = 0x00000000;
    public static final int WEB_MESSAGE_STATUS_READ = 0x00000001;


    public static final int APPLY_PERSON_STUDENT = 0x0001;
    public static final int APPLY_PERSON_NORMAL = 0x0002;

    public static final int APPLY_MESSAGE_STATUS_UNREAD = 0x0000;
    public static final int APPLY_MESSAGE_STATUS_READ = 0x0002;
    public static final int APPLY_MESSAGE_STATUS_HANDLE = 0x0001;


    public static final int RESIDENCE_STATUS_DROP = 0x000;
    public static final int RESIDENCE_STATUS_SUCCESS = 0x001;
    public static final int RESIDENCE_STATUS_WAITTING = 0x002;
//    public static final int RESIDENCE_STATUS_

    public static final String ADMIN_FILE_PATH = "/var/www/html/admins/";
//    public static final String ADMIN_FILE_PATH = "/Library/WebServer/Documents/admins/";

//        public static final String PARTNER_FILE_PATH = "/Library/WebServer/Documents/imgs/partners/";
    public static final String PARTNER_FILE_PATH = "/var/www/html/imgs/partners/";


//    public static final String USER_FILE_PATH = "/Library/WebServer/Documents/imgs/users/";
    public static final String USER_FILE_PATH = "/var/www/html/imgs/users/";

    public static final String COBBER_FILE_PATH = "/var/www/html/imgs/cobbers/";

//    public static final String COBBER_FILE_PATH = "/Library/WebServer/Documents/imgs/cobbers/";


    public static final List<String> dataTypes = new ArrayList<>(){{
        add("住房");
        add("商业地产");
        add("其他");
    }};

    public static final List<String> dataHoseTypeOne = new ArrayList<>(){{
        add("别墅");
        add("公寓");
        add("整栋楼");
        add("船");
        add("养老房");
    }};

    public static final List<String> dataHouseTypeTwo = new ArrayList<>(){{
        add("办公室");
        add("商铺");
        add("商铺转让");
    }};

    public static final List<String> dataHouseTypeThree = new ArrayList<>(){{
        add("停车场");
        add("地皮");
    }};

    public static final List<String> dataRecommand = new ArrayList<>(){{
        add("担保");
        add("贷款");
        add("保险");
        add("开电");
        add("留学");
        add("招聘");
        add("旅游");
        add("导游");
        add("司机");
        add("搬家");
        add("美食");
        add("外卖");
        add("网超");
        add("快递");
        add("保洁");
    }};


    public static final List<String> dataRolesBussiness = new ArrayList<>(){{
        add("全部房源");
        add("平台自营");
        add("中介房源");
        add("个人房源");
    }};


    public static final List<String> dataMeubles = new ArrayList<>(){{
        add("不带家具");
        add("家具齐全");
    }};

    public static final List<String> dataNeedsStatus = new ArrayList<>(){{
        add("未付款");
        add("已付款");
        add("未匹配");
        add("已经完成");
    }};

    public static final List<String> dataPayMethods = new ArrayList<>(){{
        add("3月一付");
        add("6月一付");
        add("一年一付");
    }};

    public static final List<String> dataBaseFunctions = new ArrayList<>(){{
        add("独立厨房");
        add("独立浴室");
        add("独立厕所");
        add("共用厨房");
        add("共用浴室");
        add("共用厕所");
    }};

    public static final List<String> dataFunctions = new ArrayList<String>(){{
        add("中央供暖");
        add("地窖");
        add("阳台");
        add("洗衣机");
        add("洗碗机");
        add("电梯");
        add("停车场");
        add("冰箱");
        add("烘干机");
    }};

    public static final HashMap<Integer,String> dataAdmins = new HashMap<>(){{
        put(PERSON_TYPE_ADMIN_STAGE,"实习生");
        put(PERSON_TYPE_ADMIN_NORMAL,"普通管理员");
        put(PERSON_TYPE_ADMIN_MANAGE,"金牌管理员");
        put(PERSON_TYPE_ADMIN_SUPER,"超级管理员");
    }};


    public static final List<String> dataAdminStatus = new ArrayList<String>(){{
        add("屏蔽");
        add("激活");
        add("离职");
    }};

    public static final List<String> dataSexs = new ArrayList<String>() {{
        add("先生");
        add("女士");
    }};


    public static final List<String> dataHouse = new ArrayList<String>(){{
        add("房间");
        add("studio 或者 T1");
        add("公寓T2");
        add("公寓T3");
        add("公寓T4");
        add("别墅");
    }};

    public static final List<String> dataBussinessHouse = new ArrayList<String>(){{
        add("-----都不是-----");
        add("studio T1");
        add("T2 一室一厅");
        add("T3 二室一厅");
        add("T4 三室一厅");
    }};

    public static final List<String> dataGaranties = new ArrayList<String>(){{
       add("个人担保");
       add("公司担保或政府担保");
       add("提前给付房租");
    }};


    public static final List<String> dataProfession = new ArrayList<String>(){{
        add("实习生");
        add("学生");
        add("待业");
        add("临时工");
        add("自由职业者");
        add("雇员");
        add("商人");
        add("艺术家");
        add("退休");
    }};

    public static final List<String> dataFurniture = new ArrayList<String>(){{
        add("无家具");
        add("有家具");
        add("不重要");
    }};

    public static final List<String> dataTrans = new ArrayList<String>(){{
        add("不重要");
        add("5 min");
        add("10 min");
        add("15 min");
        add("30 min");
    }};

    static final List<String> data = new ArrayList<String>(){{
        add("水");
        add("电");
        add("网");
        add("暖气");
        add("家具");
    }};

    public static final List<String> dataEnergy = new ArrayList<String>(){{
        add("无等级");
        add("A");
        add("B");
        add("C");
        add("D");
        add("E");
        add("F");
        add("G");
    }};

    static final List<Integer> studioArea = new ArrayList<Integer>(){{
        add(10);
        add(35);
    }};

    static final List<Integer> T1Area = new ArrayList<Integer>(){{
        add(18);
        add(65);
    }};

    static final List<Integer> T2Area = new ArrayList<Integer>(){{
        add(18);
        add(65);
    }};

    static final List<Integer> T3Area = new ArrayList<Integer>(){{
        add(18);
        add(65);
    }};

    static final List<Integer> T4Area = new ArrayList<Integer>(){{
        add(18);
        add(65);
    }};

    static final List<Integer> T5Area = new ArrayList<Integer>(){{
        add(18);
        add(65);
    }};

    static final List<Integer> T6Area = new ArrayList<Integer>(){{
        add(18);
        add(65);
    }};

    static final List<Integer> T7Area = new ArrayList<Integer>(){{
        add(18);
        add(65);
    }};

    static final List<Integer> T8Area = new ArrayList<Integer>(){{
        add(18);
        add(65);
    }};

    public static final List<String> appartmentType = new ArrayList<String>(){{
        add("T2");
        add("T3");
        add("T4");
        add("T5");
        add("T6");
        add("T7");
        add("T8");
    }};

    public static final List<String> HouseType = new ArrayList<String>(){{
        add("F2");
        add("F3");
        add("F4");
        add("F5");
        add("F6");
        add("F7");
        add("F8");
    }};


    public static final List<String> typePerson = new ArrayList<String>(){{
        add("个人");
        add("房管");
        add("中介");
        add("加盟商");
        add("平台自营");
    }};


    public static final List<String> typeAnnonces = new ArrayList<>(){{
        add("隐藏");
        add("已出租");
        add("正在签约");
        add("空房源");
    }};

    public static final List<String> residenceServices = new ArrayList<>(){{
        add("辅助功能");
        add("互联网");
        add("停车处");
        add("自行车位");
        add("体育馆");
        add("光纤");
        add("自助洗衣");
        add("冷气机");
        add("公共卫生");
        add("监控摄像头");
        add("对讲机");
        add("熨斗");
        add("餐具");
        add("早餐");
        add("管理员");
        add("会客厅");
        add("前台");
        add("电话");
        add("桑拿");
        add("自动贩卖机");
        add("游泳池");
        add("电视");
        add("吸尘器");
        add("打印室");
        add("四件套");
        add("室内清洁");
        add("热饮机");
        add("暖气");
        add("微波炉");
        add("冰箱");
        add("洗碗机");
        add("电");
        add("阅览室");
        add("电暖气");
        add("冷热水");
    }};

    public static final List<String> residenceServicesIconns = new ArrayList<>(){{
        add("fas fa-wheelchair");
        add("fas fa-wifi");
        add("fas fa-parking");
        add("fas fa-bicycle");
        add("fas fa-dumbbell");
        add("fas fa-router");
        add("fas fa-dryer-alt");
        add("fas fa-air-conditioner");
        add("fas fa-male");
        add("fas fa-cctv");
        add("fas fa-phone-office");
        add("fas fa-tshirt");
        add("fas fa-utensils-alt");
        add("fas fa-croissant");
        add("fas fa-user-tie");
        add("fas fa-users-class");
        add("fas fa-concierge-bell");
        add("fas fa-phone-alt");
        add("fas fa-heat");
        add("fas fa-cookie-bite");
        add("fas fa-swimmer");
        add("fas fa-tv");
        add("fas fa-vacuum");
        add("fas fa-print");
        add("fas fa-blanket");
        add("fas fa-broom");
        add("fas fa-soup");
        add("fas fa-temperature-hot");
        add("fas fa-microwave");
        add("fas fa-refrigerator");
        add("fas fa-water");
        add("fas fa-bolt");
        add("fas fa-books");
        add("fas fa-heat");
        add("fas fa-dewpoint");
    }};

    public static final List<String> dataResidenceTypes = new ArrayList<String>(){{
        add("studio T1");
        add("T2");
        add("T3");
        add("T5");
        add("T6");
        add("T7");
        add("T8");
        add("T9");
    }};

    public static final List<String> dataResidenceCharges = new ArrayList<>(){{
        add("冷水");
        add("热水");
        add("电暖");
        add("电");
        add("公共卫生费用");
    }};


    public static final List<String> dataNeufHouseTypes = new ArrayList<>(){{
        add("一间房");
        add("两间房");
        add("三间房");
        add("四间房");
        add("五间房以上");
    }};


    public static final List<String> dataNeufHouseStatus = new ArrayList<>(){{
        add("即将施工");
        add("正在施工");
        add("内部认购");
        add("现房");
        add("最后房源");
    }};
}
