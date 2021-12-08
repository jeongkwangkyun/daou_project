# daou_project
# 인턴사원 정광균 pr 요청드립니다.

## 주요 테이블
- 회원 
  - 로그인 처리할때 필요한 id,pw와 적립금 테이블과 1대1 관계여서 적립금 컬럼을 포함시켰습니다.

- 포인트
  - 모든 포인트 정보가 있는 `point`테이블과 결제 포인트만 저장하는 'used_point'테이블로 분리시켰습니다.
  
- 할인 쿠폰
  - 5%, 10%, 20%의 상황을 A,B,C로 분리하기위해서 type 컬럼을 만들어줬습니다. 또한, 결제시 하나의 쿠폰만 적용 가능하다고 상황을 가정했습니다.

- 결제 
  - 상품과의 관계에서 N:M 관계이기 때문에 `product_payment_list'를 만들었습니다. 포인트가 복수 유형의 포인트가 사용될 수 있는 상황이 발생하여 `used_point`테이블을 만들었습니다.

## 고민점
- `user`테이블에서 적립금 컬럼을 따로 테이블로 빼야될지 고민입니다. 확장성을 고려하면 빼는게 맞지만, 테이블을 합쳐도 요구사항은 만족시키는 상황이여서 프로젝트 크기를 어느정도로 잡아야하나요?

- view단에서 point나 적립금 창에 이상한 값(ex 음수값이나, 문자열) 넣게 되면 프론트 or 백 or 둘다 처리해야하나요?


# erd 다이어그램
![daou_기술세미나 (1)](https://user-images.githubusercontent.com/59038419/145183298-c6a0bd15-bee1-400f-855c-8a200f3e014f.png)

# 결제 플로우차트
![결제 drawio](https://user-images.githubusercontent.com/59038419/145172428-7f9668b3-5aff-451b-8ba5-b357598f27a0.png)

# 환불 플로우차트
![환불 drawio](https://user-images.githubusercontent.com/59038419/145172492-1a871fb3-0201-455a-bc33-27fc4277034e.png)

# 시퀀스 다이어그램

![시퀀스 다이어그램 drawio](https://user-images.githubusercontent.com/59038419/145172539-97ae79a7-0859-49af-b4d9-418699d8aea6.png)


# 좋은 하루되세요.
# 감사합니다.
