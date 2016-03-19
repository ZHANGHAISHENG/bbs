CREATE DATABASE bbs2014;
USE bbs2014;

                                                                                                                                                       
                                                                                                                                       --  0：叶子帖子，1：非叶子帖子
                                                                                                                                       
CREATE TABLE _admin(id INT AUTO_INCREMENT  PRIMARY KEY,NAME VARCHAR(40),sex VARCHAR(1),PASSWORD VARCHAR(40),email VARCHAR(100),phone BIGINT,rdate DATETIME,LEVEL INT,_DESC TEXT);

CREATE TABLE _user(id INT AUTO_INCREMENT PRIMARY KEY,NAME VARCHAR(40),sex VARCHAR(2),PASSWORD VARCHAR(40),email VARCHAR(100),phone BIGINT,rdate DATETIME,_desc TEXT);
 																			  -- 0:超级管理员，1：普通管理员

CREATE TABLE _article(id INT AUTO_INCREMENT ,pid INT,rootid INT ,userid INT ,title VARCHAR(50),content TEXT,pdate DATETIME,isleaf INT,grade INT,
                      PRIMARY KEY(id),FOREIGN KEY(userid)  REFERENCES _user(id)  );
                      
    
 DROP TABLE _article;                  
 DROP TABLE _user;

 DROP TABLE _admin;
 
DELETE FROM _user; 
 
 SELECT * FROM _user;
 SELECT * FROM _article;
 SELECT * FROM _admin;
SELECT * FROM _user,_article WHERE _user.id =_article.id;


SELECT  a.*,u.id uid,u.name,u.sex,u.password,u.email,u.phone,u.rdate,u._desc FROM _article a,_user  u WHERE a.userid=u.id  ORDER BY pdate DESC;

 SELECT * FROM _article ORDER BY pdate DESC;
 
 DELETE FROM _article WHERE id=35;

