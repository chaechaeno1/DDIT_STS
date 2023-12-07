package kr.or.ddit.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component는 스프링 빈으로 등록하기 위한 어노테이션
@Component("aopController") 
//@Aspect는 어노테이션을 붙여 이 클래스가 Aspect를 나타내는 클래스라는 걸 명시
//AOPController 클래스 빈 등록시, aOPController로 할지, aoPController로 할지가 명확하지 않을 수 있어서
//aopController라는 이름을 명시함.
@Aspect
public class AOPController {
	
	/*
	 * [14장 : AOP ]
	 * 
	 * 	1. AOP 설명
	 * 
	 * 		[예시 - 가장 많은 예시로 처리 속도에 대한 내용]
	 *		306호 반장 최룡학생이 신입으로 프로젝트를 진행하고 있음
	 *		그러던 어느날, 지웅 팀장님을 통해서 '반장님, 지금 개발중인 서비스 처리 속도좀 로그로 남겨주세요!'라는 요청을 받음
	 *		반장 최룡학생은 부탁받은 요구사항을 이행하기 위해 본인이 만들고 있는 서비스 로직에서 처리속도를 찍어볼 메소드를
	 *		개발해 처리 속도가 잘 찍히는 걸 
	 *		지웅 팀장님께 해당 내용을 컨펌받고 지웅 팀장님은 긍정적인 검토안을 최룡 학생에게 전달하며
	 *		우리 서비스 전체에도 각 처리 속도를 찍어주세요 라고 다시 전달
	 *		최룡 학생은 본인이 만들어낸 메소드를 각 기능별 서비스 로직에 하나하나씩 약 2만개쯤 작성하던 중, 의문을 가진다.
	 *
	 *			"서비스 로직에서 제일 중요한 로직은 본래의 기능이 제일 중요하고 내가 지금 작성하고 있는 로직은 옵션(부가기능)
	 *			이 추가되는게 아닌가?"
	 *			"그럼 이걸 하나의 묶음으로는 처리가 불가능한건가?"
	 *
	 *		라는 생각 하게 된다.
	 *		여기서, 시간을 측정하고 권한을 체크하는 등의 기능은 옵션과 같은 부가기능으로 일종의 인프라 로직이라고 하는데,
	 *		이 인프라 로직은 애플리케이션 전 영역에서 나타날 수 있고, 중복 코드를 만들어 내 개발의 효율성을 저하시키고
	 *		유지보수가 힘들어질 수 있다. 
	 *
	 *		이러한 인프라 로직은 아래처럼 하나의 관심사를 가질 수 있는데 이런 관심사들의 중복이 횡단으로 나타나는데,
	 *		이것을 가르켜 '횡단 관심사(Cross-Cutting Concern)'라고 한다.
	 *
	 *
	 *		┌─────────────────────────────────────────────────────────────────┐
	 *		│	[처리속도 측정]		[처리속도 측정]		[처리속도 측정]		[처리속도 측정]	  │
	 *		└─────────────────────────────────────────────────────────────────┘
	 *			[비즈니스 로직]		[비즈니스 로직]		[비즈니스 로직]		[비즈니스 로직]		
	 *			[처리내용 로깅]		[처리내용 로깅]		[처리내용 로깅]		[처리내용 로깅]		
	 *		└─────────────────────────────────────────────────────────────────┘
	 *			로그인 기능			회원가입 기능		게시판 목록			게시판 등록			......
	 *
	 *		이러한 횡단 관심사를 통해서 프로그래밍하는 것이 AOP라고 한다.
	 *
	 *		# 간단하게 보고 넘어가기 (용어)
	 *		- Aspect(애스팩트)
	 *				: AOP의 단위가 되는 횡단 관심사
	 *		- 횡단 관심사(Cross-Cutting Concern)
	 *				: 핵심(core) 비즈니스 로직(메소드 실행 시작 시간 출력, 메소드 처리 후 시간 출력 등)과 다소 거리가 있지만,
	 *				여러 모듈에서 공통적이고 반복적인 처리를 요구하는 내용(메소드 실행 시작 시간 출력 등...)
	 *		- 횡단 관심사 분리(Seperation Of Cross-Cutting Concern)
	 *				: 횡단 관심사에 해당하는 부분(메소드 실행 시작 시간 출력 등)을 분리해서 한 곳으로 모으는 것을 의미
	 *		- @Component
	 *				: @Aspect와 짝꿍
	 *				: Component-Scan시 "나 여기 있어요~ 여기 봐주세요~" 라는 의미
	 *		- JoinPoint
	 *				: 어드바이스가 적용될 수 있는 위치
	 *		- Advice
	 *				: 어떤 부가기능(메소드 실행 시작 시간 출력)을 언제(메소드 실행 전, 후 등) 사용할지 정의
	 *				> Advice(부가기능)는 타겟을 감싸서 위장된 프록시가 실행되기 위한 시점에 따라 옵션으로 나뉘어 진다.
	 *				> 아래에서 각 시점을 알아본다.
	 *					- before : 조인포인트 전에 실행 (삼겹살을 굽기 전에)
	 *					- After : 조인 포인트에서 처리가 완료된 후에 실행 (삼겹살을 굽고 먹은 직후 실행)
	 *					- After Returning : 조인 포인트가 정상적으로 종료 후 실행
	 *					- After Throwing : 조인 포인트에서 예외 발생 시 실행. 예외가 발생 안되면 실행 안함
	 *					- Around : 조인 포인트 전후에 실행(삼겹살을 굽기 직전과 먹은 직후 실행)
	 *					
	 *		# AOP(관점 지향 프로그래밍(Aspect Oridented Programing))
	 *		- 관점 지향 프로그래밍(Aspect Oriented Programing)을 의미하는 약자이다.
	 *
	 *
	 *			1-1) 관점 지향 프로그래밍
	 *				
	 *				소스코드의 여기저기 흩어져 있는 횡단 관심사를 중심으로 설계와 구현을 하는 프로그래밍 기법
	 *				간단하게 설명하면 관점 지향 프로그래밍은 횡단 관심사의 분리를 실현하는 방법
	 *
	 *			1-2) AOP 개발 순서
	 *
	 *				1) 핵심 비즈니스 로직에만 근거해서 코드 작성
	 *				2) 주변 로직에 해당하는 관심사들은 분리해서 따로 작성
	 *				3) 핵심 비즈니스 로직 대상 객체에 어떤 관심사들을 결합할 것인지 설정
	 *
	 *			1-3) AOP 사용 예(로.보.트.에)
	 *				- 로깅 (우리가 사용할 AOP)
	 *				- 보안 (스프링 시큐리티를 활용한 보안)
	 *				- 트랜잭션 관리 (@Transactional을 활용한 트랜잭션 관리)
	 *				- 예외처리(어노테이션 및 xml 파일 등을 활용한 예외처리)
	 *	
	 *
	 *			1-4) AOP 관련 용어
	 *
	 *				- Aspect : AOP의 단위가 되는 횡단 관심사에 해당한다.
	 *				- 조인 포인트(JoinPoint)
	 *					: 횡단 관심사가 실행될 지점이나 시점(메소드 실행이나 예외발생 등)을 말한다.
	 *					: 어디에 적용할 것인지 결정, 메소드/객체생성시/필드접근 등등
	 *				- 어드바이스(Advice) 
	 *					: 특정 조인 포인트에서 실행되는 코드로, 횡단 관심사를 실제로 구현해서 처리하는 부분
	 *					: 어떤 부가기능을 구현할 것인지 결정
	 *					(Before, AfterReturning, AfterThrowing, After, Around)
	 *				- 포인트 컷(Pointcut)
	 *					: 수 많은 조인 포인트 중에서 실제로 어드바이스를 적용할 곳을 선별하기 위한 표현식(expression)
	 *					: 어드바이스가 적용될 지점
	 *				- 위빙(Weaving)
	 *					: 애플리케이션 코드의 적절한 지점에 Aspect를 적용하는 것을 말한다.
	 *				- 타켓(Target)
	 *					: AOP처리에 의해 처리 흐름에 변화가 생긴 객체를 말한다.
	 *					: 어떤 대상에 대해 부가기능을 설정할 것인지 결정
	 *
	 *			1-5) 스프링 지원 어드바이스 유형(부가기능)
	 *				- Before
	 *					: 조인 포인트 전에 실행
	 *					: 예외가 발생하는 경우만 제외하고 항상 실행
	 *				- After Returning
	 *					: 조인 포인트가 정상적으로 종료한 후에 실행
	 *					: 예외가 발생하면 실행X
	 *				- After Throwing
	 *					: 조인 포인트에서 예외가 발생했을 때 실행
	 *					: 예외가 발생하지 않고 정상적으로 종료하면 실행X
	 *				- After
	 *					: 조인 포인트에서 처리가 완료된 후 실행
	 *					: 예외 발생이나 정상 종료 여부와 상관없이 항상 실행
	 *				- Around (제일 중요!!!!, 가장 많이 쓰임)
	 *					: 조인 포인트 전후에 실행된다.
	 *
	 *			1-6) AOP의 기능을 활용하기 위한 설정
	 *
	 *				- 의존 관계 등록 (pom.xml 설정)
	 *					> aspectjrt(이미 등록되어 있음)
	 *					> aspectjweaver (**버전 1.5.4로 등록)
	 *
	 *				- 스프링 AOP 설정
	 *					> root-context.xml 설정
	 *						: AOP를 활성화하기 위한 태그 작성
	 *
	 *
	 *		# 스프링 AOP
	 *			스프링 AOP는 동작 시점이 여러가지 있지만 (컴파일 시점, 클래스 로딩 시점, 런타임 시점....) 그 중,
	 *			런 타임 시점에 프록시 객체를 생성 후 기능 삽입
	 *			> 스프링 AOP의 프록시는 메소드의 오버라이딩(인터페이스 기반의 참조) 개념으로 동작하므로,
	 *				메소드 실행 시점 시 동작
	 *
	 *
	 *
	 *
	 *		2. 포인트 컷 표현식
	 *
	 *			- execution 지시자에 대해서 알아봅시다!
	 *
	 *				# 포인트컷(Pointcut)
	 *
	 *				- Advice가 실행될 지점을 표현하는 표현식
	 *				
	 *					2-1) execution 지시자의 표현 방식
	 *						- execution 지시자를 활용해 포인트컷을 표현한 것이다.
	 *
	 *						- 포인트 컷 표현 요소
	 *						예) execution(Board kr.or.ddit.service.IBoardService.BoardService*.read*(..))
	 *						
	 *						┌─────────────────────────────────────────────────────────────────┐
	 *						│	표현 요소				│		설명								  │
	 *						───────────────────────────────────────────────────────────────────
	 *							execution			│	지시자
	 *							Board				│	반환값
	 *							kr.or.ddit.service	│	패키지
	 *							BoardService*		│	클래스(타입)
	 *							read*				│	메소드
	 *							(..)				│	인수, 파라미터
	 *						───────────────────────────────────────────────────────────────────
	 *
	 *
	 *					2-2) 포인트컷 표현식에 사용되는 와일드카드
	 *						───────────────────────────────────────────────────────────────────
	 *							와일드 카드			│		설명
	 *						───────────────────────────────────────────────────────────────────
	 *								*			│	임의의 패키지 1개 계층을 의미하거나 임의의 인수 1개를 의미
	 *								..			│	임의의 패키지 0개 이상 계층을 의미하거나 임의의 인수 0개 이상을 의미
	 *								+			│	클래스명 뒤에 붙여 쓰며, 해당 클래스와 해당 클래스의 서브클래스, 혹은 구현 클래스 모두를 의미
	 *						───────────────────────────────────────────────────────────────────
	 *											
	 *					2-3) 포인트 컷 표현식을 적용한 모습
	 *
	 *						@Before("execution(* kr.or.ddit.service.IBoardService.BoardServiceImpl*.*(..)")
	 *						public void startLog(JoinPoint jp){
	 *							log.info("startLog : " + jp.getSignature()
	 *						}
	 *
	 *		
	 * 	
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	
	
	/*
	 * 3. Before 어드바이스 - 조인 포인트 전에 실행됨. 예외가 발생하는 경우만 제외하고 항상 타겟이 실행되기 전에 실행됨
	 */	
	@Before("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public void startLog(JoinPoint jp) {
		log.info("[@Before]startLog");
		// getSignature() : 어떤 클래스의 어떤 메소드가 실행되었는지 보여준다. (파라미터 타입은 무엇인지 보여줌)
		log.info("[@Before]startLog : " + jp.getSignature());
		// getArgs() : 전달된 파라미터 정보를 보여줌
		// 예) [Board [boardNo=127, title=개똥이]]
		log.info("[@Before]startLog : " + Arrays.toString(jp.getArgs()));
		
		// 8. 메서드 정보 획득 시 사용
		// 프록시가 입혀지기 전의 원본 대상 객체를 가져온다.
		Object targetObject = jp. getTarget();
		log.info("targetObejct : " + targetObject);
		
		//프록시를 가져온다.
		Object thisObject= jp.getThis();
		log.info("thisObject : "+ thisObject);
		
		//인수를 가져온다.
		Object[] args = jp.getArgs();
		log.info("args.length : " + args.length);
		for(int i = 0; i < args.length; i++) {
			log.info("args["+i+"] : " + args[i]);
		}
		
		
		
		
	}
	
	
	
	
	/*
	 * 4. After Returning 어드바이스
	 * 	- 조인 포인트가 정상적으로 종료한 후에 실행된다.
	 * 		예외가 발생하면 실행X
	 */
	
	@AfterReturning("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public void logReturning(JoinPoint jp) {
		log.info("[@AfterReturning]");
		log.info("[@AfterReturning] : " + jp.getSignature());
		
	}
	
	
	
	/*
	 * 5. After Throwing
	 * 	- 조인 포인트에서 예외가 발생했을 때 실행된다. 예외가 발생하지 않고 정상적으로 종료하면 실행되지 않는다.
	 * 
	 * 	예) crud board에서 delete 쿼리를 'no = 2' 에서 'no 2 =' 으로 변경해서 진행
	 * 
	 */
	
	@AfterThrowing(pointcut = "execution(* kr.or.ddit.service.IBoardService.*(..))", throwing = "e")
	public void logException(JoinPoint jp, Exception e) {
		log.info("[@AfterThrowing]logExcetion");
		log.info("[@AfterThrowing]logExcetion : " + jp.getSignature());
		log.info("[@AfterThrowing]logExcetion : " + e);
	}
	
	
	/*
	 * 6. After 어드바이스
	 * 	- 조인 포인트에서 처리가 완료된 후에 실행된다.
	 */
	
	@After("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public void endLog(JoinPoint jp) {
		log.info("[@After]endLog");
		log.info("[@After]endLog : " + jp.getSignature());
		log.info("[@After]endLog : " + Arrays.toString(jp.getArgs()));
	}
	
	
	/*
	 * 7. Around 어드바이스
	 * 	- 조인포인트 전후에 실행된다.
	 * 
	 * 		-ProceedingJoinPoint
	 * 		: around 어드바이스 에서 사용함
	 * 
	 * 		스프링프레임워크가 컨트롤 하고 있는 비즈니스 로직 호출을 가로챈다.
	 * 		책임이 around 어드바이스로 전가되고 그래서 비즈니스 메소드에 대한 정보를 around 어드바이스
	 * 		메소드가 가지고 있어야 하고 그 정보를 스프링 컨테이너가 around 어드바이스 메소드로 넘겨주면
	 * 		ProceedingJoinPoint 객체로 받아서 around 어드바이스가 컨트롤 시 활용함
	 * 
	 * 
	 * 
	 * 
	 */
	
	
	@Around("execution(* kr.or.ddit.service.IBoardService.*(..))")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable {
		 // 대상 메서드의 실행 전에 수행되는 로직
		long startTime = System.currentTimeMillis();
		log.info("[@Around] : " + Arrays.toString(pjp.getArgs()));
		
		//메소드 실행
		Object result = pjp.proceed();
		
		// 대상 메서드의 실행 후에 수행되는 로직
		long endTime = System.currentTimeMillis();
		log.info("[@Around]pjpEnd : "+ Arrays.toString(pjp.getArgs()));
		
		// 대상 메서드의 실행 시간을 로깅
		log.info("[@Around] : " + pjp.getSignature().getName() + "[메소드 실행 시간: ]" + (endTime-startTime));
		
		// 대상 메서드의 실행 결과를 반환
		return result;
	}
	
	
	/*
	 * 8. 메서드 정도 획득
	 * 
	 * - @Before 어노테이션이 붙은 메소드는 JoinPoint라는 매개변수를 통해 실행중인 메서드의 정보를 구할 수 있습니다.
	 * 
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	

}
