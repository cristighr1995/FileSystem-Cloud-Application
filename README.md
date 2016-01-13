# Description
This application is a simulator of a Unix based Operating System and a Cloud Service.

Available commands
------------------
-> ls, cd, cat, pwd, mkdir, rm, touch, echo, login, logout, newuser, userinfo

-> listcloud, upload, sync

Commands' format
----------------
-> ls [-r | -a | -ar] [path] [-POO]

-> cd path

-> cat path

-> pwd

-> mkdir permissionNumber* [name | path/name]

-> touch permissionNumber [name | path/name]

-> rm [-r] path

-> echo text [-POO]

-> login username password

-> logout

-> newuser username password lastname firstname

-> userinfo [-POO]

-> upload directorName

-> sync directorName

-> listcloud


* permissionNumber = a number from 0 to 3 to set the permission

    --0: nobody can read or write;

    --1: everyone can read, nobody can write;

    --2: nobody can write, everyone can write;

    --3: everyone can read or write;
    
# How to use
Download the archive, extract the project and Open Project with NetBeans or Eclipse, then run it.

Application uses SwingX and JLayer libraries. If needed you need to add these libraries to the project.

Have Fun!
--------
