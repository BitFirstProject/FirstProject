package com.mystudy.login;

import java.util.Calendar;

public class testMain {
    public static void main(String[] args) {
    	
    	Calendar cal = Calendar.getInstance();
    	int y = cal.get(Calendar.YEAR);
    	int m = cal.get(Calendar.MONTH) + 1;
    	
        printCalender(y, m);
        System.exit(0);
    }
    
    // 숫자 여부 체크
    public static boolean isNumber(String str){
        boolean result = false; 
        try{
            Double.parseDouble(str) ;
            result = true ;
        }catch(Exception e){}
         
         
        return result ;
    }
     
    
    // 달력의 타이틀과 바디
    private static void printCalender(int year, int month) {
        printMonthTitle(year, month);
        printMonthBody(year, month);
 
    }
 
    // 해당 년도가 윤년인지 판별
    public static boolean isLeapYear(int year) {
        return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
    }
 
    // 달력 바디 출력
    private static void printMonthBody(int year, int month) {
        int count = 0;
 
        for (int i = 1; i <= getNumberOfDaysInMonth(year, month); i++) {
            if (i == 1) {
                for (int y = 1; y <= getStartDay(year, month); y++) {
                    System.out.print("\t");
                    count++;
                }
            }
 
            System.out.printf(" %2d\t", i);
            count += 1;
            if (count == 7) {
                System.out.println();
                count = 0;
            }
        }
    }
 
    // 해당 달의 첫 요일을 구해서 돌려줌.
    private static int getStartDay(int year, int month) {
        int monthSum = 0;
        int leapYear = 0;
        int daySum = 1;
 
        for (int i = 1; i < year; i++) {
            monthSum += 365;
            if (isLeapYear(i) == true) {
                leapYear++;
            }
        }
 
        for (int j = 1; j < month; j++) {
            daySum += getNumberOfDaysInMonth(year, j);
        }
 
        return (monthSum + leapYear + daySum) % 7;
 
    }
 
    // 달의 마지막 일을 구함
    private static int getNumberOfDaysInMonth(int year, int month) {
 
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            return 30;
        } else if (month == 2 && isLeapYear(year) == true) {
            return 29;
        } else if (month == 2 && isLeapYear(year) == false) {
            return 28;
        } else {
            return 31;
        }
    }
 
    // 달의 타이틀 부분 출력
    private static void printMonthTitle(int year, int month) {
        System.out.println();
        System.out.println("" + year + "년\t\t\t\t\t\t" + getMonthName(month));
        System.out.println("===================================================");
        System.out.println("   일\t   월\t   화\t   수\t   목\t   금\t   토");
        System.out.println("===================================================");
    }
    
    private static String getMonthName(int month) {
        String monthName = null;
        switch (month) {
        case 1:
            monthName = "1월";
            break;
        case 2:
            monthName = "2월";
            break;
        case 3:
            monthName = "3월";
            break;
        case 4:
            monthName = "4월";
            break;
        case 5:
            monthName = "5월";
            break;
        case 6:
            monthName = "6월";
            break;
        case 7:
            monthName = "7월";
            break;
        case 8:
            monthName = "8월";
            break;
        case 9:
            monthName = "9월";
            break;
        case 10:
            monthName = "10월";
            break;
        case 11:
            monthName = "11월";
            break;
        case 12:
            monthName = "12월";
            break;
        }
        return monthName;
    }
}
