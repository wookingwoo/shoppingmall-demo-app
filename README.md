# Shoppingmall Demo

## introduction

### Demo video
   - [Go Youtube](https://youtu.be/8szmXZp9tOo)

### Development environment.

- system image: API 30 (Android 11.0)
- device: Pixel 4


### Back-End (firebase)

- Account information.: Authentication
- Product information: Firestore Database
- bind key: user UID
- Use the demo ID when guest mode.
- Since DB is configured on a server, synchronization of the device is possible (real-time
  synchronization was excluded due to the life cycle of BaseAdapter. When a change is detected, save
  the previous data on the server.)

## Features

- supported English and Korean

### Sign in

- 아이디(이메일) 또는 비밀번호가 일치하지 않으면 Toast로 에러메시지 발생
- 회원정보는 firebase의 Authentication을 불러옵니다.
- 정상적으로 로그인을 완료하면 장바구니로 이동
- 'GUEST LOGIN" 버튼을 클릭하면 로그인 없이 장바구니로 이동 가능 (게스트 데모 계정으로 사용)
- "CREATE ACCOUNT" 버튼을 클릭하면 회원가입 페이지로 이동

### Sign up

- ID는 이메일 형식으로 사용
- 비밀번호는 문자, 숫자, 특수문자를 모두 포함한 8~15자로 구성해야하며 유효성 검사를 통해 오류메시지를 보여줍니다.
- 이름은 필수값이며 전화번호와 주소는 선택항목으로 구성했습니다.
- 필수값을 입력하지 않을경우 Toast로 해당부분을 알려주며 회원가입이 진행되지 않습니다.
- 개인정보 사용 동의 약관에 동의를 해야만 가입이 가능합니다.
- "VIEW PRIVACY" 버튼을 누르면 dialog 창으로 개인정보정책 열람이 가능합니다.


### Shopping cart

- 담은 상품의 이미지와 상품명을 리스트 형태로 보여줍니다.
- 상품 옆에 있는 "X" 아이콘을 누르면 해당 상품이 삭제됩니다.
- 우측 하단에 "+" 모양의 FloatingButton을 누르면 장바구니에 상품을 추가할 수 있습니다.
- 최초 회원가입을 하여 장바구니가 비어있는 경우에 기본적으로 5개의 item을 넣어줍니다.
- 좌측 하단에 회원정보를 보여줍니다.
- 로그인이 되어있을때 회원정보를 클릭하면 로그아웃을 물어보며 "예"를 누르면 로그아웃이 되며 첫번째화면으로 이동합니다.
- 로그인이 되어있지 않은 경우에는 "SIGN UP"이 뜨며 클릭하면 "회원가입 하시겠습니까?"의 알림이 나오며 "예"를 누르면 두번째 화면으로 이동합니다.
