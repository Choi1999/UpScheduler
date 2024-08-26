API 설계
일정 API
일정 생성 메서드:
POST
엔드포인트: /api/schedules
상태 코드: 201 Created: 일정 생성 성공
일정 조회 메서드:
GET
엔드포인트: /api/schedules/{id}
상태 코드: 200 OK: 일정 조회 성공
일정 수정 메서드:
PUT
엔드포인트: /api/schedules/{id}
상태 코드: 200 OK: 일정 수정 성공
일정 삭제 메서드:
DELETE
엔드포인트: /api/schedules/{id}
상태 코드: 204 No Content: 일정 삭제 성공
댓글 API
댓글 생성 메서드:
POST
엔드포인트: /api/schedules/{scheduleId}/comments
상태 코드: 201 Created: 댓글 생성 성공
댓글 조회 메서드:
GET
엔드포인트: /api/comments/{id}
상태 코드: 200 OK: 댓글 조회 성공
댓글 전체 조회 메서드:
GET
엔드포인트: /api/schedules/{scheduleId}/comments
상태 코드: 200 OK: 댓글 목록 조회 성공
댓글 수정 메서드:
PUT
엔드포인트: /api/comments/{id}
상태 코드: 200 OK: 댓글 수정 성공
댓글 삭제 메서드:
DELETE
엔드포인트: /api/comments/{id}
상태 코드: 204 No Content: 댓글 삭제 성공
유저 관련 API
유저 생성 메서드:
POST
엔드포인트: /api/users
상태 코드: 201 Created: 유저 생성 성공
유저 조회 메서드:
GET
엔드포인트: /api/users/{id}
상태 코드: 200 OK: 유저 조회 성공
유저 전체 조회 메서드:
GET
엔드포인트: /api/users
상태 코드: 200 OK: 유저 목록 조회 성공
유저 수정 메서드:
PUT
엔드포인트: /api/users/{id}
상태 코드: 200 OK: 유저 수정 성공
유저 삭제 메서드:
DELETE
엔드포인트: /api/users/{id}
상태 코드: 204 No Content: 유저 삭제 성공
일정 페이징 조회 API

![ERD](https://github.com/user-attachments/assets/bc833655-4847-4bfe-9459-d25f36e38c12)

CREATE TABLE users (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE schedules (
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           title VARCHAR(100) NOT NULL,
                           description TEXT,
                           user_id INT,
                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 수정일 추가
                           FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE comments (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          schedule_id INT,
                          user_id INT,
                          content TEXT NOT NULL,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 수정일 추가
                          FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE,
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
CREATE TABLE schedule_users (
                                schedule_id INT,
                                user_id INT,
                                PRIMARY KEY (schedule_id, user_id),
                                FOREIGN KEY (schedule_id) REFERENCES schedules(id) ON DELETE CASCADE,
                                FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


