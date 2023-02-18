package com.futuremap.erp.utils;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.futuremap.erp.common.security.entity.CustomUserDetails;
import com.futuremap.erp.module.auth.entity.Column;
import com.futuremap.erp.module.auth.entity.RoleColumn;
import com.futuremap.erp.module.auth.entity.User;
import com.futuremap.erp.module.auth.entity.UserRole;
import com.futuremap.erp.module.auth.service.impl.ColumnServiceImpl;
import com.futuremap.erp.module.auth.service.impl.RoleColumnServiceImpl;
import com.futuremap.erp.module.auth.service.impl.UserRoleServiceImpl;
import com.futuremap.erp.module.auth.service.impl.UserServiceImpl;
import com.futuremap.erp.module.constants.Constants;
import io.swagger.models.auth.In;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author rubio@futuremap.com.cn
 * @date 2021/6/3 10:17
 */
public class GeneralUtils {
    public static void main(String[] args) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime ldt = LocalDateTime.parse("2018-01-12 17:07:05", df);


        System.out.println(ldt);
        System.out.println(ldt);
    }

    /**
     * 根据输入的月份yyyyMM，输出该月的开始和结束的日期yyyy-MM-dd
     *
     * @param yearMonth
     * @return
     */

    public static String[] getStartAndEndDay(String yearMonth) {
        // 获取当月的天数（需完善）
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2 = new SimpleDateFormat("yyyyMM");
        SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM");
        // 定义当前期间的1号的date对象
        Date date = null;
        try {
            date = df2.parse(yearMonth);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);//月增加1天
        calendar.add(Calendar.DAY_OF_MONTH, -1);//日期倒数一日,既得到本月最后一天
        Date voucherDate = calendar.getTime();

        String[] res = new String[2];
        res[0] = df3.format(date) + "-01 00:00:00";
        res[1] = df1.format(voucherDate) + " 23:59:59";

        return res;
    }

    public static String list2str(List<String> strlist, String splitstr) {

        if (strlist.size() > 0 && splitstr != null && splitstr.length() <= 2) {
            StringBuffer sb = new StringBuffer("");
            for (String str : strlist) {
                sb.append(str);
                sb.append(splitstr);
            }
            String res = sb.toString();
            res = res.substring(0, res.length() - splitstr.length());
            return res;
        }
        return null;

    }

    public static User getUser(UserServiceImpl userServiceImpl) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
            Integer id = principal.getUser().getId();
            if (id != null) {
                return userServiceImpl.getById(id);
            }
        }
        return null;
    }

    public static List<Integer> getRoleIds(Integer userId, UserRoleServiceImpl userRoleServiceImpl) {
        if (userId != null) {
            List<UserRole> userRoleList = userRoleServiceImpl.list(new QueryWrapper<UserRole>().eq("user_id", userId));
            List<Integer> roleIds = new ArrayList<>();
            for (UserRole userRole : userRoleList) {
                roleIds.add(userRole.getRoleId());
            }
            return roleIds;
        }
        return null;
    }

    //行权限控制
    public static String getRowVisit(UserServiceImpl userServiceImpl, UserRoleServiceImpl userRoleServiceImpl) {
        User user = GeneralUtils.getUser(userServiceImpl);
        if (user != null) {
            List<Integer> roleIds = GeneralUtils.getRoleIds(user.getId(), userRoleServiceImpl);
            String code = user.getCode();
            if (code != null && roleIds != null && !roleIds.isEmpty() && (roleIds.contains(20) || roleIds.contains(3))) {
                return code;
            }
        }
        return null;
    }

    //列权限控制
    public static String getColumnVisit(UserRoleServiceImpl userRoleServiceImpl, RoleColumnServiceImpl roleColumnServiceImpl, ColumnServiceImpl columnServiceImpl, String tableName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {

            CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
            Integer id = principal.getUser().getId();
            if (id != null) {
                List<UserRole> userRoleList = userRoleServiceImpl.list(new QueryWrapper<UserRole>().eq("user_id", id));
                List<Integer> roleIds = new ArrayList<>();
                for (UserRole userRole : userRoleList) {
                    roleIds.add(userRole.getRoleId());
                }
                if (!roleIds.isEmpty()) {
                    List<RoleColumn> roleColumnList = roleColumnServiceImpl.list(new QueryWrapper<RoleColumn>().in("role_id", roleIds));
                    List<Integer> columnIds = new ArrayList<>();
                    for (RoleColumn roleColumn : roleColumnList) {
                        columnIds.add(roleColumn.getColumnId());
                    }
                    if (!columnIds.isEmpty()) {
                        List<Column> columns = columnServiceImpl.list(new QueryWrapper<Column>().in("id", columnIds).eq("table_name", tableName));
                        if (!columns.isEmpty()) {


                            List<String> columnNames = new ArrayList<>();
                            columnNames.add("id");
                            for (Column column : columns) {
                                String columnName = column.getColumnFiled();
                                columnNames.add(columnName);
                            }
                            if (!columnNames.isEmpty()) {
                                return GeneralUtils.list2str(columnNames, ",");

                            }

                        }
                    }

                }
            }
        }
        return "";
    }


    public static BigDecimal getBigDecimalVal(BigDecimal val) {
        if (val == null) {
            return BigDecimal.ZERO;
        }
        val = val.setScale(4, BigDecimal.ROUND_HALF_UP);
        return val;
    }

    public static String getStringVal(String val) {
        if (val == null) {
            return Constants.DEFAULT_STR;
        }
        return val;
    }

    public static Integer getIntegerVal(Integer val) {
        if (val == null) {
            return Constants.DEFAULT_INT;
        }
        return val;
    }


    /**
     * 获取给定日期N天后的日期
     */
    public static String getDateAfterNDays(String dateTime, int days) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(dateTime);
            Calendar calendar = Calendar.getInstance();//获取日历
            calendar.setTime(date);//当date的值是当前时间，则可以不用写这段代码。
            calendar.add(Calendar.DATE, days);
            Date d = calendar.getTime();//把日历转换为Date
            return sdf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }


    public static String getCurrMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
        Date date = new Date();
        return format.format(date);
    }

    public static String getShortName(String ds) {
        if (ds != null) {
            Map<String, String> cnsMap = new HashMap<>();
            cnsMap.put("erp002", "宜心");
            cnsMap.put("erp003", "横琴宜心");
            cnsMap.put("erp006", "宜成");
            cnsMap.put("erp004", "MEX");
            cnsMap.put("erp010", "智拓");

            cnsMap.put("9999", "宜心");
            cnsMap.put("EASI001", "横琴宜心");
            cnsMap.put("HD154", "宜成");
            cnsMap.put("W0001", "MEX");
            cnsMap.put("HD151", "智拓");

            return cnsMap.get(ds);
        }
        return "";
    }

    public static String getShortDs(String code) {
        if (code != null) {
            Map<String, String> cnsMap = new HashMap<>();
            cnsMap.put("erp002", "宜心");
            cnsMap.put("erp003", "横琴宜心");
            cnsMap.put("erp006", "宜成");
            cnsMap.put("erp004", "MEX");
            cnsMap.put("erp010", "智拓");

            cnsMap.put("9999", "erp002");
            cnsMap.put("EASI001", "erp003");
            cnsMap.put("HD154", "erp006");
            cnsMap.put("W0001", "erp004");
            cnsMap.put("HD151", "erp010");

            return cnsMap.get(code);
        }
        return "";
    }


    public static String date2string(LocalDateTime date) {
        if (date != null) {
            try {
                DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return sdf.format(date);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        return null;
    }

    public static String date2myString(LocalDateTime date, DateTimeFormatter sdf) {
        if (date != null) {
            try {
                return sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        return null;
    }


    public static LocalDateTime string2date(String str) {
        if (str != null) {
            try {
                if (str.length() > 10) {
                    str = str.substring(0, 10);
                }
                str = str + " 00:00:00";
                DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return LocalDateTime.parse(str, sdf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static LocalDateTime string2myDate(String str, String endStr, DateTimeFormatter sdf) {
        if (str != null) {
            try {
                str = str + endStr;
                return LocalDateTime.parse(str, sdf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }


}
