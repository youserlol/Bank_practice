package testBank;

/*
 * 객체지향 원칙 적용하기
 * 캡슐화 
 * 클래스 분리
 * 상속 / 인터페이스 (확장을 고려한 구조 설계)
 */
// 클래스는 괄호를 붙이는 게 아니다

import java.util.ArrayList;
import java.util.Scanner;

class Account { // 계좌 정보
	// 계좌 번호, 사용자 이름, 잔액
	// 메서드 : 입금, 출금, 잔액 조회

	private int accountNumber;
	private String userName;
	private int balance;

	public Account(int accountNumber, String userName){
		this.accountNumber = accountNumber;
		this.userName = userName;
	}

	public void withdraw(int newBalance) { // 출금
		if (balance >= newBalance) {
			balance -= newBalance;
		} else {
			System.out.println("잔액이 부족합니다");
		}
	}

	public void deposit(int newBalance) { // 입금
		balance += newBalance;
	}

	public int getBalance() { // 잔액 조회
		return balance;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public String getUserName() {
		return userName;
	}
}

class Bank { // 은행 기능 정보
	// 계좌들 리스트
	// 메서드 : 계좌 생성, 조회

	// 동적 리스트로 계좌 리스트를 관리
	// Account 객체를 담아야 하니까 Account 타입의 리스트로 선언
	private ArrayList<Account> accountList = new ArrayList<>();

	public void addAccount(Account account) {
		accountList.add(account); // 동적 리스트 배열에 계좌 객체 추가
	}

	// Account 객체를 반환해야 해서 메서드 리턴 타입은 Account
	public Account findAccount(int accountNumber) { // Account 객체의 숫자를 비교
		for (Account acc : accountList) {
			if (acc.getAccountNumber() == accountNumber) {
				return acc;
			}
		}
		// 못 찾았을 경우 null을 반환해야 함. 없으면 오류!
		return null;
	}
}

public class Main {

	// 사용자 클래스, 작동부
	public static void main(String[] args) {

		/*
		 * === 은행 시스템 ===
		 * 1. 입금
		 * 2. 출금
		 * 3. 잔액 조회
		 * 4. 계좌 조회
		 * 5. 계좌 추가
		 * 0. 종료
		 */
		Scanner s = new Scanner(System.in);
		Bank bank = new Bank();

		// 기본 계좌를 하나 설정해둠
		System.out.println("기본 계좌를 설정합니다.");
		Account ac = new Account(1111222233, "Na");
		bank.addAccount(ac);

		while(true) {
			System.out.println("\n메뉴를 선택하세요");
			System.out.println("(1: 입금, 2: 출금, 3: 잔액 조회, 4: 계좌 조회, 5: 계좌 추가, 0: 종료)");
			int choice = s.nextInt();
			s.nextLine(); // 버퍼 클리어 (nextInt 후 nextLine 사용 시 필요)

			switch (choice) {

			case 1: // 입금
				System.out.print("계좌 번호를 입력하세요: ");
				int depAccNum = s.nextInt();
				s.nextLine(); // 버퍼 클리어

				Account depAcc = bank.findAccount(depAccNum);
				if (depAcc != null) {
					System.out.print("입금 금액을 입력하세요: ");
					int depositAmount = s.nextInt();
					s.nextLine();
					depAcc.deposit(depositAmount);
					System.out.println("입금 완료. 현재 잔액: " + depAcc.getBalance());
				} else {
					System.out.println("계좌를 찾을 수 없습니다.");
				}
				break;

			case 2: // 출금
				System.out.print("계좌 번호를 입력하세요: ");
				int witAccNum = s.nextInt();
				s.nextLine();

				Account witAcc = bank.findAccount(witAccNum);
				if (witAcc != null) {
					System.out.print("출금 금액을 입력하세요: ");
					int withdrawAmount = s.nextInt();
					s.nextLine();
					witAcc.withdraw(withdrawAmount);
					System.out.println("출금 완료. 현재 잔액: " + witAcc.getBalance());
				} else {
					System.out.println("계좌를 찾을 수 없습니다.");
				}
				break;

			case 3: // 잔액 조회
				System.out.print("계좌 번호를 입력하세요: ");
				int balAccNum = s.nextInt();
				s.nextLine();

				Account balAcc = bank.findAccount(balAccNum);
				if (balAcc != null) {
					System.out.println("현재 잔액: " + balAcc.getBalance());
				} else {
					System.out.println("계좌를 찾을 수 없습니다.");
				}
				break;

			case 4: // 계좌 조회
				System.out.print("계좌 번호를 입력하세요: ");
				int findAccNum = s.nextInt();
				s.nextLine();

				Account found = bank.findAccount(findAccNum);
				if (found != null) {
					System.out.println("계좌 번호: " + found.getAccountNumber());
					System.out.println("사용자 이름: " + found.getUserName());
					System.out.println("잔액: " + found.getBalance());
				} else {
					System.out.println("계좌 정보가 일치하지 않습니다.");
				}
				break;

			case 5: // 계좌 추가
				System.out.println("계좌를 추가하세요");

				System.out.print("새 계좌 번호를 입력하세요: ");
				int newAccNum = s.nextInt();
				s.nextLine();
				
				/* nextInt()는 숫자만 읽어서 다음 nextLine을 만나면 남겨진 공백을 읽고 끝나는 것 처럼 보이게 된다
				 * 그러므로 다음에 입력을 받으려면 nextLine()을 통해 남은 공백을 다 읽어 버퍼를 비우고 입력 받아야한다.
				 */

				System.out.print("사용자 이름을 입력하세요: ");
				String newUser = s.nextLine();

				Account newAcc = new Account(newAccNum, newUser);
				bank.addAccount(newAcc);
				System.out.println("새 계좌가 등록되었습니다.");
				break;

			case 0: // 종료
				System.out.println("시스템이 종료됩니다.");
				s.close(); // 자원 해제
				return;

			default:
				System.out.println("올바른 메뉴를 선택해주세요.");
			}
		}
	}
}


