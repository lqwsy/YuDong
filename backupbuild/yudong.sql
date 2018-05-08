/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2018-01-12 09:04:22                          */
/*==============================================================*/


drop table if exists book_classification;

drop table if exists books;

drop table if exists users;

/*==============================================================*/
/* Table: book_classification                                   */
/*==============================================================*/
create table book_classification
(
   iBookClassificationId int not null AUTO_INCREMENT,
   vBookClassificationName varchar(10) not null,
   primary key (iBookClassificationId)
);

/*==============================================================*/
/* Table: books                                                 */
/*==============================================================*/
create table books
(
   iBookId              int not null AUTO_INCREMENT,
   vBookName            varchar(20) not null,
   iBookClassificationId int not null,
   vBookIntroduction    varchar(200) not null,
   vBookAuthor          varchar(20) not null,
   fBookSize            float not null,
   dBookPrice           double not null,
   vBookCoverPath       varchar(100) not null,
   vBookLocation        varchar(100) not null,
   vUploadPerson        varchar(20) not null,
   dUploadTime          datetime not null,
   iBookState           int not null,
   iBookDownloads       int,
   primary key (iBookId)
);

/*==============================================================*/
/* Table: users                                                 */
/*==============================================================*/
create table users
(
   iUserId              int not null AUTO_INCREMENT,
   vUserName            varchar(20) not null,
   vPassword            varchar(20) not null,
   vUserNickName        varchar(20),
   iRole                int not null,
   vHeadImage           varchar(100),
   dRegisteTime         datetime not null,
   vRegCode             varchar(200),
   iUserState           int not null,
   primary key (iUserId)
);

alter table books add constraint FK_Relationship_3 foreign key (iBookClassificationId)
      references book_classification (iBookClassificationId) on delete restrict on update restrict;

