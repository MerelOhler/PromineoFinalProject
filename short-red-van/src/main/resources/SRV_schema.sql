DROP DATABASE IF EXISTS SRV_DB;
CREATE DATABASE SRV_DB;
USE SRV_DB;

DROP TABLE IF EXISTS MessageChangeHistory;
DROP TABLE IF EXISTS Message;
DROP TABLE IF EXISTS PartyLoginUser;
DROP TABLE IF EXISTS PartyChangeHistory;
DROP TABLE IF EXISTS Party;
DROP TABLE IF EXISTS LoginUser;
DROP TABLE IF EXISTS UserRole;

CREATE TABLE UserRole(
UserRoleID smallint not null auto_increment,
NameKey varchar(50),
primary key (UserRoleId)
);

CREATE TABLE LoginUser(
LoginUserId INT NOT NULL AUTO_INCREMENT,
Email varchar(50) NOT NULL UNIQUE,
FirstName VARCHAR(50) NOT NULL,
LastName VARCHAR(50),
Password VARCHAR(50),
UserRole smallint,
DateCreated datetime,
DateModified datetime,
ModifiedBy int,
Primary KEY(LoginUserId),
foreign key(UserRole) references UserRole(UserRoleId)
);

CREATE TABLE Party(
PartyId INT NOT NULL AUTO_INCREMENT,
Name VARCHAR(50) not null,
AdminUserId int not null,
DateCreated datetime not null,
CreatedBy int not null,
DateModified datetime,
ModifiedBy int,
PRIMARY KEY(PartyId),
foreign key(AdminUserId) references LoginUser(LoginUserID),
foreign key(CreatedBy) references LoginUser(LoginUserID),
foreign key(ModifiedBy) references LoginUser(LoginUserID)
);

CREATE TABLE PartyChangeHistory(
PartyChangeHistoryID int not null auto_increment,
Action varchar(50) not null,
ColumnChanged varchar(50) not null,
OldValue varchar(256),
NewValue varchar(256),
DateModified datetime,
ModifiedBy int,
primary key(PartyChangeHistoryID),
foreign key(ModifiedBy) references LoginUser(LoginUserId)
);

CREATE TABLE PartyLoginUser(
PartyId INT not null,
LoginUserId int not null,
DateCreated datetime not null,
CreatedBy int not null,
PRIMARY KEY(PartyId, LoginUserId),
FOREIGN KEY(PartyId) REFERENCES Party(PartyId),
foreign key(LoginUserId) references LoginUser(LoginUserId),
foreign key(CreatedBy) references LoginUser(LoginUserId)
);

CREATE TABLE PartyLoginUserDeleted(
PartyLoginUserIdDeleted INT NOT NULL AUTO_INCREMENT,
PartyId INT not null,
LoginUserId int not null,
DateCreated datetime not null,
CreatedBy int not null,
DateDeleted datetime not null,
DeletedBy int not null,
PRIMARY KEY(PartyLoginUserIdDeleted),
FOREIGN KEY(PartyId) REFERENCES Party(PartyId),
foreign key(LoginUserId) references LoginUser(LoginUserId),
foreign key(CreatedBy) references LoginUser(LoginUserId),
foreign key(DeletedBy) references LoginUser(LoginUserId)
);

CREATE TABLE Message(
MessageId INT NOT NULL,
MessageText varchar(1000) NOT NULL,
ParentMessageId int,
PartyId int not null,
DateCreated datetime not null,
CreatedBy int not null,
DateModified datetime,
ModifiedBy int,
DateDeleted datetime,
DeletedBy int,
PRIMARY KEY(MessageId),
foreign key(ParentMessageId) references Message(MessageId),
FOREIGN KEY(PartyId) REFERENCES Party(PartyId),
foreign key(CreatedBy) references LoginUser(LoginUserId),
foreign key(ModifiedBy) references LoginUser(LoginUserId),
foreign key(DeletedBy) references LoginUser(LoginUserId)
);

CREATE TABLE MessageChangeHistory(
MessageChangeHistoryId int not null auto_increment,
Action varchar(50) not null,
ColumnChanged varchar(50) not null,
OldValue varchar(256),
NewValue varchar(256),
DateModified datetime,
ModifiedBy int,
primary key(MessageChangeHistoryId),
foreign key(ModifiedBy) references LoginUser(LoginUserId)
);

