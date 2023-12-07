package kr.or.ddit.controller.transaction;

public class TransactionController {
	
	/*
	 * [15장 트랜잭션]
	 * 
	 * 
	 * 	1. 트랜잭션 설명
	 * 
	 * 		- 한번에 이루어지는 작업의 단위를 의미
	 * 
	 * 			# 트랜잭션 성격(ACID 원칙)
	 * 
	 * 			- 원자성(Atomicity)
	 * 			> 하나의 트랜잭션은 모두 하나의 단위로 처리해야 한다.
	 * 			- 일관성(Consistency)
	 * 			> 트랜잭션이 성공했다면 모든 데이터는 일관성을 유지해야 한다.
	 * 			- 격리성(Isolation)
	 * 			> 트랜잭션이 처리되는 동안에는 외부에서의 간섭이 없어야 한다.
	 * 			- 영속성(Durability)
	 * 			> 트랜잭션이 성공적으로 처리되면 그 결과는 영속적으로 보관되어야 한다.
	 * 
	 * 
	 * 
	 * 	2. 트랜잭션 설정
	 * 		
	 * 		# 스프링 설정
	 * 
	 * 			- root-context.xml 설정
	 * 				> 네임스페이스, 스키마 추가
	 * 			- transactionManager 빈 등록
	 * 			- 어노테이션 기반의 트랜잭션 제어 활성화
	 * 
	 * 
	 * 	3. 트랜잭션 적용
	 * 
	 * 		[테스트는 AOP가 적용된 CrudBoardController와 연계된 ServiceImpl에서 진행]
	 * 		[테스트는 AOP가 적용된 CrudMemberController와 연계된 ServiceImpl에서 진행]
	 * 
	 * 
	 * 		# 트랜잭션 상황 부여 
	 * 
	 * 		예) 회원은 반드시 하나의 권한을 가진다는 비즈니스 규칙이 있다.
	 * 			회원과 회원권한 테이블이 각각 개별로 존재하지만, 회원 정보를 저장할 때 반드시 회원권한 정보를 동시에 저장해야 한다.
	 * 			클래스나 메소드에 대해 @Transactional 어노테이션을 부여하여 트랜잭션을 적용할 수 있다.
	 * 
	 * 		# @Transactional 어노테이션의 예외처리 견해
	 * 			(구글링하다보면 대표적인 예시가 있는데 이는 잘못된 정보이다)
	 * 			
	 * 			@Transactional을 적용하고 중간에 에러를 발생시킨 후, 롤백된 상태를 확인하려고 했으나 롤백이 되지 않고
	 * 			데이터베이스에 데이터가 등록된다. 그 이유는 스프링 프레임워크에서 @Transactional은 기본적으로
	 * 			Checked Exception에 대해서는 롤백처리를 하지 않도록 설계되어있다.
	 * 			기본적으로 스프링에서 트랜잭션 처리는 RuntiemException계열(Unchecked Exception)이라면
	 * 			Rollback 처리를 한다
	 * 			
	 * 			여기서, 트랜잭션으로 국한된 롤백 정책이 아니라 스프링 프레임워크에서의 기본 정책에 대한 내용일 뿐입니다.
	 * 			실제로 '트랜잭션 롤백 처리', 'checked Exception vs Unchecked Exception' 등 검색해보면
	 * 			잘못된 정보들로 복사/붙여넣기 되어있는 내용이 수두룩하여 많은 에러 정보를 공유하고 있습니다.
	 * 			(모든 블로그가 그렇지 않지만, 대표적인 표가 무수히 많이 공유되고 있음을 알 수 있다.)
	 * 
	 * 			***
	 * 			'예외처리 시 트랜잭션 처리'에 대한 정보가 잘못 표기된 경우가 많은데..
	 * 			이럴 때 되고 저럴떄 되고가 아니라, 기본적인 스프링 트랜잭션의 정책이 있고 그 안에서 정책 이외에 옵션으로
	 * 			특정 에러가 발생했을 때 롤백을 진행 할 수 있도록 제공한다. (많은 블로그들을 100% 믿지 말기..)
	 * 			혹여나 블로그 관리 한다면 검증된 내용으로만 작성하길...
	 * 
	 * 
	 * 		# Exception(예외)과 Error(에러)
	 * 
	 * 			- Exception : 개발 알고리즘에서 발생하는 오류로 개발자가 작성한 코드에서 발생하므로 예외를 상황에 맞춰 처리할 수 있다.
	 * 			- Error : 시스템에서 발생하는 심각한 수준의 에러로 개발자가 미리 예측하여 대응할 수 없기 때문에 예외처리에 대한 부분을
	 * 						신경쓰지 않아도 된다.
	 * 
	 * 			CheckedException과 UncheckedException
	 * 			- RuntimeException의 상속 여부에 따라 Checked Exception과 Unchecked Exception으로 나누어진다.
	 * 
	 * 		────────────────────────────────────────────────────────────────────────────────────────────
	 * 						│	Checked Exception			│	Unchecked Exception
	 * 		────────────────────────────────────────────────────────────────────────────────────────────
	 * 		예외처리 여부 		│	반드시 예외 처리 코드가 있어야 한다.	│	강제로 예외처리는 아니다.
	 * 		예외 확인 시점		│	컴파일 단계에서부터 컴파일 되지 않는다.	│	런타임 중 예외가 확인된다.
	 * 		클래스 			│ 	IOException, SQLException...│	NullPointException, IndexOf....
	 * 		────────────────────────────────────────────────────────────────────────────────────────────
	 *		
	 *		*** 트랜잭션도 AOP의 개념이 반영된 관점 지향 프로그래밍이라 할 수 있다.
	 *		
	 *		
	 *		# RuntimeException 계열의 종류
	 *		- ArithmeticException
	 *		- ArraysStoreException
	 *		- ArrayIndexOfBoundsException
	 *		- NullPointerException
	 *		- NegativeArraySizeException
	 *		- NoClassDefFoundException
	 *		- OutOfMemoryException
	 *		- IndexOutOfBoundsException
	 *		- IllegalAraumentException
	 *		- IllegalMonitorStateException
	 *
	 *		
	 *		# 선언적 트랜잭션 @Transactional
	 *	
	 *			- 컨트롤러 메소드 각 단위로 세밀한 트랜잭션 속성 제어가 가능
	 *			- 해당 어노테이션이 클래스 수준에서 선언되면 선언 클래스 및 해당 하위 클래스의 모든 메서드에 기본값으로 적용된다.
	 *			- RuntimeException, Error 예외에 대해서는 RollBack이 가능하다. (공식문서 내용)
	 *
	 * 			1) isolation (격리 수준)
	 * 				- 각 트랜잭션이 존재할 때, 트랜잭션들 끼리 서로 고립된 수준을 나타내며 서로간에 가용된 데이터를 컨트롤 할지에 대한 부분들을 설정할 수 있다.
	 * 				- isolation 기본값은 DEFAULT 이다.
	 * 				- 새롭게 시작된 트랜잭션에만 적용되므로, Propagation.REQUIRED 또는 Propagation.REQUIRED_NEW와 함께
	 * 					사용되도록 독점 설계되었다.
	 * 			
	 * 				1-1) 옵션
	 * 				
	 * 				*** 용어
	 * 				#  Dirth read
	 * 				- Commit이 이뤄지지 않은 다른 트랜잭션의 데이터를 읽는 것을 의미
	 * 				# Non-repeatable Read(데이터의 수정/삭제)
	 * 				- 처리중인 트랜잭션에서 다른 트랜잭션이 Commit한 데이터를 읽을 수 있는 것을 의미
	 * 				(처음 조회에 대한 트랜잭션과 두번째 조회에 대한 트랜잭션 결과가 다를 수 있다.)
	 * 				# Phantom Read
	 * 				- 자신이 처리중인 트랜잭션에서 처리했던 내용안에서 다른 트랜잭션이 데이터를 수정 후 Commit 하더라도
	 * 					자신의 트랜잭션에서 처리한 내용만 사용하는 것을 의미
	 * 				
	 * 				- DEFAULT : 기본 데이터 저장소의 기본 격리 수준을 사용합니다.
	 * 				- READ_COMMITED : Dirth read가 방지됨을 나타내는 상수
	 * 								Non-repeatable Read 및 Phantom Read가 발생할 수 있습니다.
	 * 					> 하나의 트랜잭션 처리가 이루어진 변경 내용이 Commit 된 후, 다른 트랜잭션에서 조회가 가능하다.
	 * 					> A 트랜잭션이 데이터를 변경하고 B 트랜잭션이 조회를 진행할 때 B 트랜잭션은 Shared lock이 걸린다.
	 * 
	 * 				- READ_UNCOMMITED : Dirth read, Non-repeatable Read 및 Phantom Read가 발생할 수 있음을 나타내는 상수
	 * 					> 다른 트랜잭션의 내용이 Commit 또는 Rollback되거나 되지 않아도 다른 트랜잭션에서 조회가 가능
	 * 				- REPEATABLE_READ : Dirth read, Non-repeatable Read 및 Phantom Read가 방지됨을 나타내는 상수
	 * 									Phantom Read가 발생할 수 있음
	 * 					> 트랜잭션 Commit이 일어난 데이터에 대해서 조회가 가능
	 * 						(트랜잭션 완료시까지 조회에 대한 Shared lock이 걸리지 않음) 		
	 * 				- SERIALIZABLE : Dirth read, Non-repeatable Read 및 Phantom Read가 방지됨을 나타냄
	 * 					> Phantom Read가 발생하지 않는다. (거의 사용하지 않는 옵션임)
	 * 
	 * 			2) propagation (전파 옵션)
	 * 				- 기존 진행중인 트랜잭션 외에 추가적으로 진행중인 트랜잭션이 존재할 때 추가적인 트랜잭션에 대해서 어떻게 처리할지에 대한 설정
	 * 				- 추가적인 트랜잭션을 기존 트랜잭션에 포함 시켜 함께 처리할 수도 있고, 추가적인 트랜잭션처럼 별도의 트랜잭션으로 추가할 수도 있고,
	 * 					다른 트랜잭션처럼 진행되다 에러를 발생시킬 수도 있다.
	 * 
	 * 				2-1) 옵션
	 * 
	 * 				- REQUIRED : 현재 트랜잭션을 지원하고 존재하지 않는 경우 새 트랜잭션을 만듭니다.
	 * 					> propagation 기본 default 옵션
	 * 					> 부모/자식간에 상관관계에서 자식부분의 트랜잭션이 rollback 처리 시, 부모까지 영향이 가서
	 * 						rollback 처리가 됩니다.
	 * 				- REQUIRED_NEW : 새로운 트랜잭션을 생성한다.
	 * 					> rollback은 각각 이루어진다.
	 * 				- SUPPORTS : 트랜잭션이 있으면 현재 트랜잭션을 지원하고 트랜잭션이 없으면 트랜잭션이 아닌 방식으로 실행한다.
	 * 				- MANDATORY : 현재 트랜잭션을 지원하고, 없으면 예외를 발생시킨다.
	 * 					> 독립적인 트랜잭션으로 진행하면 안되는 경우 사용
	 * 				- NESTED : 현재 트랜잭션이 있는 경우 중첩된(부모-자식) 트랜잭션 내용에서 실행하고 그렇지 않은 경우 REQUIRED와 같이 동작합니다.
	 * 					> 부모에서 예외가 발생했을 때, 자식까지 영향이 가서 Commit 되지 않는다.
	 * 				- NEVER : 트랜잭션이 아닌 방식으로 실행하고 트랜잭션이 있으면 예외를 발생시킨다.
	 * 					> 실행 자체가 트랜잭션을 필요로 하지 않고, 트랜잭션이 존재한다면 예외를 발생시키도록 한다.
	 * 					> Existing transaction found for transaction marked with propagation 'never' 에러
	 * 
	 * 			3) readOnly (읽기전용 설정) (읽기만 가능한 것은 아님!!)
	 * 				- 읽기 전용인 경우 설정할 수 있는 Bool Flag, 런타임시 최적화를 허용한다.
	 * 				- readOnly 속성을 설정햇다고 해서, 읽기 전용으로 무조건 설정된다는 보장이 없음(쓰기와 같은 트랜잭션이 실행될 수도 있음)
	 * 					> 읽기 전용에 대한 힌트를 분석할 수 없는 트랜잭션인 경우 throw를 던지지 않고 조용히 힌트를 무시한다.
	 * 			
	 * 			4) timeout (트랜잭션 제한시간)
	 * 
	 * 				- 기본값은 -1로 무제한이다.
	 * 				- timeout은 클라이언트와 서버와의 통신 중, 서버측 문제로 다음 처리를 이어나가지 못하는 'Deadlock'을 방지할 수 있는 속성
	 * 				- 클라이언트와 서버간의 Restful API를 개발 시 고려해볼 속성
	 * 
	 * 			5) rollbackFor
	 * 				- 트랜잭션 롤백을 유발해야 하는 예외 유형을 나타내는 0개 이상의 예외 유형을 정의한다.
	 * 				- 기본적으로 트랜잭션은 롤백 되지만, CheckedException 계열의 에러는 롤백되지 않는다.
	 * 					> 공식문서에서 제공하는 것처럼 RuntimeException 계열과 Error에 대해서는 기본적으로 롤백 가능
	 * 				- 기본적인 정책 이외에 에러를 처리할 경우, 해당 에러를 선언하여 롤백 정책을 추가한다.
	 * 				
	 * 
	 * 
	 * 		
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
	

}
