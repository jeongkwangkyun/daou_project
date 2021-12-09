# daou_project
# 인턴사원 정광균 pr 요청드립니다.

## 주요 테이블

- 회원 
  - 로그인 처리할때 필요한 id,pw 컬럼을 만들어 유저 자체 상태를 나타낼 수 있는 테이블을 만들었습니다.

- 적립금
  - 적립금 정보를 조회할 때 필요한 테이블을 만들고, 회원 아이디를 fk로 받았습니다. 적립금을 결제, 환불할때마다 +,- update 처리할 계획입니다.

- 포인트
  - 모든 포인트 정보가 있는 `point`테이블과 결제 포인트만 저장하는 `payment_point` 테이블로 분리시켰습니다. 결제 과정에서 잔여 포인트를 - 해주고 결제포인트에 insert하는 로직과, 환불 과정에서 포인트를 다시 잔여 포인트에 + 할 계획입니다.
  
- 쿠폰
  - 5%, 10%, 20%의 상황을 A,B,C로 분리하기위해서 type 컬럼을 만들어줬습니다. 또한, 결제시 하나의 쿠폰만 적용 가능하다고 상황을 가정했습니다. 

- 결제 
  - 상품과의 관계에서 N:M 관계이기 때문에 `product_payment_list'를 만들었습니다. 포인트가 복수 유형의 포인트가 사용될 수 있는 상황이 발생하여 `payment_point`테이블을 만들었습니다. `환불신청여부` 컬럼을 추가하여 환불 신청시 `refund` 테이블에 추가되도록 설계했습니다. 


# erd 다이어그램
![daou_기술세미나_v2](https://user-images.githubusercontent.com/59038419/145314373-adffb4cd-173d-485f-9b6f-cbfaf142d1c4.png)

# 결제 플로우차트
![결제 drawio](https://user-images.githubusercontent.com/59038419/145172428-7f9668b3-5aff-451b-8ba5-b357598f27a0.png)

# 환불 플로우차트
![환불 drawio](https://user-images.githubusercontent.com/59038419/145172492-1a871fb3-0201-455a-bc33-27fc4277034e.png)

# 시퀀스 다이어그램

![시퀀스 다이어그램 drawio](https://user-images.githubusercontent.com/59038419/145172539-97ae79a7-0859-49af-b4d9-418699d8aea6.png)


# 좋은 하루되세요.
# 감사합니다.
