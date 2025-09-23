# 🤖 9th-Android
> 9th 서경대 **UMC Android 파트 Repository** 입니다.  

<p>
  <img src="https://img.shields.io/badge/UMC-9th-6A5ACD?style=for-the-badge&logo=github&logoColor=white" />
  <img src="https://img.shields.io/badge/Repository-Android%20Part-3DDC84?style=for-the-badge" />
</p>

<br>
  
## 📌 몇 주차 워크북인가요?
- Week02

---

## ✨ 이번 주에 작업한 내용
- BottomNavigationView + FragmentContainerView 실습 구현  
  - `activity_bottom_nav_test.xml` 작성 (FragmentContainerView + BottomNavigationView 구조)  
  - `bottom_nav_menu.xml` 작성 (홈, 일기, 캘린더, 친구, 마이페이지 탭 구성)  
  - `BottomNavTestActivity.kt` 작성 및 Fragment 전환 로직 구현  
  - Home, Diary, Calendar, Friend, Mypage Fragment 생성 → 중앙 TextView 변경 확인 완료  
- Manifest 수정  
  - 런처 Activity를 `BottomNavTestActivity`로 설정  

---

## 🙋 리뷰 요청/확인 받고 싶은 부분
- Navigation Component(NavGraph) 대신 FragmentManager로 전환을 구현했는데, 이 방식도 괜찮은지 궁금합니다.  
- Fragment 상태 저장/백스택 관리에 대한 개선 포인트가 있는지 피드백 받고 싶습니다.  

---

## ✅ 체크리스트
- [x] `week02/` 폴더 안에 과제 정리 완료  
- [x] PR 생성 시 **base = 조직 내 본인 브랜치**, **compare = 내 Fork main 브랜치**로 설정했는지 확인  
- [x] PR 제목에 **`[Week02] 제이/김민재 미션 제출`** 규칙 맞게 작성  
