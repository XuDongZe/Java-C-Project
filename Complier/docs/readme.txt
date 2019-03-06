姓名：	许东泽
学号：	2015302462
班级：	10011506

Complier项目
	使用eclipse c项目开发, 使用MinGW编译器
	GBK编码，否则注释可能会乱码
	main.c 程序入口
	目前只做到了语法树生成和打印，四元式未做。

makefile 
	使用MinGW自带的 make工具, 在bin目录下。需要设置环境变量。
	make
		生成Complier.exe，包含词法、语法、符号表、语法树。
	clean
		del 所有的obj文件和Complier.exe文件

使用
	cmd进入Complier目录下，运行Complier.exe。
	直接运行Complier.exe会出错，原因是不能传参。