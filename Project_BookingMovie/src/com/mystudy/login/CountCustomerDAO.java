package com.mystudy.login;

import java.util.Scanner;

public class CountCustomerDAO {
	
	Scanner sc = new Scanner(System.in);
	public void selPeople() {
		System.out.println("예매할 인원 수를 선택하세요");
		System.out.println("------------------");
		System.out.println("  1. 1명       2. 2명\n  3. 3명       4. 4명");
		System.out.println("------------------");
		
		String sel = "";
		int num = 0;
		
		while (true) {
			System.out.print("입력>");
			sel = sc.nextLine();
			try {
				num = Integer.parseInt(sel);
				if (num >= 1 && num <= 4) {
					TicketVO.vo.setCntCustomer(num);
					System.out.println(num+ "명을 선택하셨습니다");
					break;
				} else {
					System.out.println("최대 4명까지만 예매하실 수 있습니다.");
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자만 입력해 주세요");
			}
		}
	}
}
