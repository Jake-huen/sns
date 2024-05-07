# SNS - TDD 개발 적용

## 기능 및 요구사항

### 1. 회원가입 및 로그인 기능 테스트 개발

- 유저 네임, 패스워드로 회원가입

- 회원가입 실패
  - 아이디 중복 : 아이디가 중복되었다는 message와 함께 실패 반환

### 2. 로그인 기능

- 유저 네임, 패스워드로 로그인

- 로그인 실패
  - 아이디 존재하지 않는 경우 : 존재하지 않는 아이디 message와 실패 반환
  - 패스워드 틀린 경우 : 실패 반환
