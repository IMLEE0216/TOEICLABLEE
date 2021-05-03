# TOEIC_LAB

1. 소개

     - 코로나 시대에 맞는 비대면 토익관련 스터디 생성 및 토익 모의고사, 파트별 연습문제 등 다양한 영어관련 학습자료 제공.

     - 사용자가 직접 영어 사진 문제를 업로드하여 API를 통한 '이미지 -> 텍스트' 를 통한 문제 등록하여 다른 사용자와 공유하는 게시판기능 제공.

     - 파트별 다양한 문제를 제공.

       

2. 사용한 프로그램의 정보

     - 웹 서버 구현 
       ​		IntelliJ IDEA Ultimate Edition

      - 자바 버전

        ​		 jdk 11.0.9

      - 내장 WAS 

        ​		Tomcat

      - Front End

        ​		Thymeleaf, Bootstrap

      - 운영 서버 

        ​		AWS EC2

      - DB 

        ​		H2 1.4.199

      - API

        ​		Google Vision

        ​		Google Oauth2

        ​		Google Mail SMTP

        ​		Google SpeechToText

        ​		Daum Postcode Service

      - Library

        ​		Jsoup 1.13.1

        ​		Spring Security

        ​		Spring JPA

        ​		Lombok

        ​		


3. 잘한 점

   - 구글 Oauth2 로그인 연동 페이지 연결

     - 사용자가 원하는 분량의 모의고사, 연습문제 제공

     - 조건이 맞는 사용자끼리 자동으로 스터디 매칭

     - 영어 팝업사전기능 제공

     - 사용자가 업로드한 이미지 파일에서 자동 텍스트 추출을 통한 문제등록 및 공유

     - 자유게시판 기능 제공

     - 시험을 응시한 사용자에게 오답노트기능 및 comment 제공

     - 사용자가 녹음을 통해 말한 내용을 텍스트로 받아오기 제공

       

4. 힘들었던 점

   4-1)

   - 스터디그룹 매칭 시 StudyGroupApplication 객체의 tag 값들을 일일히 조회하여 비교하는 작업의 시간소요가 너무 클 것으로 예상하여 각각의 tag에 소수(Prime Number) 값들을 대응한 뒤 StudyGroupApplication 에 사용자가 입력한 모든 tag들의 값의 곱을 Value로써 저장하였고, 소수를 활용함으로써  Value의 최대공약수 및 배수관계를 활용하여 Applicaion들의 tag값의 일치여부를 int값의 계산만으로 해결할 수 있었다.
   - Value 값들의 최대공약수 : 두 Application이 공동으로 선택한  모든 Tag 들의 곱, 이때 모든 Tag의 값들이 소수이므로 최대공약수만으로 공통 Tag들을 특정할 수 있다.

   4-2)

   - QuestionSet의 생성 시 Part7 의 경우 단일지문 과 복합지문 두 종류가 있으며 각각의 지문유형은 3 ~ 5 개의 문항으로 구성된다.

   - 이 점을 고려하여 단일지문과 복합지문을 정해진 문항수만큼 생성해야 한다. 총 문항 수를 만족하는 3 ~ 5개씩 생성되는 문제 set 의 수를 결정하는 것이 포인트 3x+4y+5z를 만족하는 모든 정수해를 구한 뒤 임의로 이 정수해들 중 하나를 택하여 QuestionSet을 생성하도록 하였다.  총 가지수가 많은 경우 Gauss-Jordan 소거법을 활용하는 코드를 작성 했겠지만 이 경우에는 직접 계산해도 문제가 없었다.

   -  단일질문의 경우 총 문항수는 29문항이며 3x+4y+5z=29를 만족하는 모든 자연수해는
       (x, y, z) = (0, 1, 5), (3, 0, 4), (2, 2, 3), (1, 4, 2), (5, 1, 2), (4, 3, 1) 의 6가지,

       복합질문의 경우 총 문항수는 25문항이며 3x+4y+5z=25를 만족하는 모든 자연수해는
       (x, y, z) = (0, 0, 5), (5, 0, 2), (0, 5, 1), (3, 4, 0) 의 4가지이다.

   4-3)

   - 사전 팝업 기능을 사용하다보니 자식창에서 부모창으로 데이터를 보낼때 문제가 _csrf데이터 보내기에 문제가 생겼다.  스프링부트에서는 _csrf 데이터 토큰을 보내서 체크 후  다음 스탭을 진행하기 때문에 자식창에도 _csrf를 meta로 선언하고 ajax에 _csrf 데이터랑 같이 부모창에 보내서 해결하였다.

   4-4)

   - 구글 로그인 API를 처음 사용하다 보니 어떤 방식으로 데이터가 전송되는지 모르는 등 전반적인 API사용법에 대한 이해도 부족하여 연습미니프로젝트를 따로 만들어서 기능연습 후 toeicLab프로젝트에 적용하였다.

   4-5)

   - 네이버 등 저작권의 문제로 적절한 사전 API를 찾지 못하였다. 그래서  Jsoup를 이용한 데이터 크롤링 방식을 대안으로 적용하였다.

   4-6)

   - 반복문을 사용하여 리스트를 나열할때 각 요소들의 id값을 개별로 주는 것에 대해 어려움이 있었다. 인덱스 및 반복되는 요소의 value를 달리 주어 form을 활용하거나 onclick event 함수를 활용하였다.

   4-7)

   - 오답노트 collapse 기능에 반복문을 사용해서 view에서  버튼을 하나 누르면 다같이 작동하는 문제
     data-target이 되는 버튼에 th:attr 을 선언하여 “|---|” ← 안에 태그 id 값을 주어 해결하였다.

   4-8)

   - 단어장 단어 삭제에도 어려움이 있었다. (오답노트 삭제와 유사한듯 하나 단어장은 map으로 들어온 getKey의 활용하는 방식인데 getKey()가 작동하지 않았다.) Modal을 반복문 안에 넣고 각 단어별 id값을 따로 주어 삭제 기능을 해결하였다.

   

5. 개선해야 할 점
   - 연락처를 통한 인증 기능
   - 스피킹 문제 추가
   - 백엔드 리팩터링 

   

6. ERD

![TOEIC ERD](https://user-images.githubusercontent.com/73643473/116818438-483fe580-aba6-11eb-814c-bd724e9c3a69.jpeg)

​																						동영상

<div style="text-align: center">
	<a href="https://www.youtube.com/watch?v=t2VPNYM-lMQ" target="_blank"><image src = "https://img.youtube.com/vi/t2VPNYM-lMQ/mqdefault.jpg"></a>	
</div>

