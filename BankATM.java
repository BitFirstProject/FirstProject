package com.mystudy.am03_scanner_bank;

import java.util.Scanner;

//[실습] 은행의 ATM
//1.입금  2.출금  3.통장확인  0.종료
//------------------------------
//계좌를 하나 만들고 입금/출금/통장확인 작업을 처리
public class BankATM {
	private int money; //통장계좌
	private Scanner sc = new Scanner(System.in);
	
//	public static void main(String[] args) {
//		
//		BankATM atm = new BankATM();
//		atm.startBank();
//	}
	
	public void startBank() {
		System.out.println(">> 어서오세요");
		while (true) {
			if (bankATM() == false) {
				break;
			};
		}
		System.out.println(">>작업을 종료했습니다.");
	}
	
	private boolean bankATM() {
		boolean jobContinue = true;
		showMenu();
		int select = 99;
		try {
			select = Integer.parseInt(sc.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("잘못된 값이 입력되었습니다." 
					+ " 메뉴(0~3) 숫자만 선택하세요");
			return jobContinue;
		}
		if (select == 1) {
			//System.out.println("입금작업처리~~");
			inputMoney();
		} else if (select == 2) {
			//System.out.println("출금작업처리~~");
			outputMoney();
		} else if (select == 3) {
			//System.out.println("통장확인 작업처리~~");
			showMoney();
		} else if (select == 0) {
			System.out.println("종료작업처리~~");
			jobContinue = false; //작업종료인 경우 false 리턴
		} else {
			System.out.println("잘못된 값이 입력되었습니다." 
					+ " 메뉴(0~3) 숫자만 선택하세요");
		}
		
		return jobContinue;
	}
	private void showMenu() {
		System.out.println("------------------------");
		System.out.println("1.입금  2.출금  3.통장확인  0.종료");
		System.out.println("------------------------");
		System.out.print(">>작업선택 : ");
	}
	
	private void inputMoney() {
		while (true) {
			System.out.print(">>입금액 : ");
			try {
				money += Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("잘못된 값이 입력되었습니다." 
						+ " 숫자(0~9)만 입력하세요"
						+ "\n다시 작업선택하세요.");
				continue;
			}
			break;
		}
	}
	
	private void outputMoney() {
		while (true) {
			System.out.print(">>출금액 : ");
			try {
				money -= Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("잘못된 값이 입력되었습니다." 
						+ " 숫자(0~9)만 입력하세요"
						+ "\n다시 작업선택하세요.");
				continue;
			}
			break;
		}
	}
	
	private void showMoney() {
		System.out.println(">>통장금액 : " + money + "원");
	}

}
