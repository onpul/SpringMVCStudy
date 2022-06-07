SELECT USER
FROM DUAL;
--==>> SCOTT

--○ 기존 테이블 제거
DROP TABLE TBL_MEMBERLIST;
--==>> Table TBL_MEMBERLIST이(가) 삭제되었습니다.


--○ 실습 테이블 다시 생성
CREATE TABLE TBL_MEMBERLIST
( ID    VARCHAR2(30)
, PW    VARCHAR2(20)
, NAME  VARCHAR2(50)
, TEL   VARCHAR2(50)
, EMAIL VARCHAR2(100)
, CONSTRAINT MEMBERLIST_ID_PK PRIMARY KEY(ID)
);
--==>> Table TBL_MEMBERLIST이(가) 생성되었습니다.


--○ 데이터 입력
INSERT INTO TBL_MEMBERLIST(ID, PW, NAME, TEL, EMAIL)
VALUES('admin', CRYPTPACK.ENCRYPT('java006$', 'java006$')
    , '김태형', '010-1212-3434', 'kth@test.com');
--> 한 줄 구성
INSERT INTO TBL_MEMBERLIST(ID, PW, NAME, TEL, EMAIL) VALUES('admin', CRYPTPACK.ENCRYPT('java006$', 'java006$'), '김태형', '010-1212-3434', 'kth@test.com')
;
--==>> 1 행 이(가) 삽입되었습니다.


--○ 확인
SELECT *
FROM TBL_MEMBERLIST;
--==>> admin	???^	김태형	010-1212-3434	kth@test.com


--○ 커밋
COMMIT;
--==>> 커밋 완료.