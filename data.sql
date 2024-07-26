create table Board(
    id int primary key auto_increment,
    boardId int,
    userId int,
    title varchar(200),
    content TEXT,
    createAt timestamp default  current_timestamp,
    updateAt timestamp default  current_timestamp on update current_timestamp
);

create table User(
    id int primary key auto_increment,
    email varchar(200),
    password varchar(200),
    name varchar(100),
    createAt timestamp default  current_timestamp,
    updateAt timestamp default  current_timestamp on update current_timestamp

);

create table Comment(
    id int primary key auto_increment,
    userId int,
    boardId int,
    content varchar(500),
    createAt timestamp default  current_timestamp,
    updateAt timestamp default  current_timestamp on update current_timestamp
);