MealGo
- 2021.12.08 ~ 진행 중
- 개인 프로젝트
- Retrofit, Room, DataStore

### 설명
![image](https://user-images.githubusercontent.com/65935582/170903341-be783d08-d192-458b-9fe9-a3d87d1715cc.png)
![image](https://user-images.githubusercontent.com/65935582/170903380-f05cf066-92d9-481b-b627-a89b6453344b.png)
![image](https://user-images.githubusercontent.com/65935582/170903385-55e810b2-3ae9-42cc-a54b-eeb486d67c83.png)

간단하게 원하는 학교의 급식 정보를 보여줍니다.

### 기능

- 학교 검색 - Flow의 debounce를 사용한 Instant Search를 구현
- 학교 저장 - DataStore를 통해 학교 정보를 저장
- 급식 표시 - Retrofit을 통해 급식 요청 후 표시
- 급식 정보 저장 - Room을 통해 한 번 본 급식은 로컬에 저장해 표시
- 푸시 알림 - 사용자가 원하는 시각에 푸시 알림으로 당일 급식 전송 (개발 중)
- Room과 Paging3를 조합해 UX 개선 (개발 중)
