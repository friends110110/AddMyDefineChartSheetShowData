create database dscsystem;

use dscsystem;


create table dtudata(id int auto_increment primary key auto_increment,time datetime not null,content varchar(50))


--从最后一个开始增长
insert into dtudata (time,content)values('2013-07-11 09:12:01','wfwfwf33')



insert into dtudata (id,time,content)values(7,'2013-07-11 09:12:01','wfwfwf33')
