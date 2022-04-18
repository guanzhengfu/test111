#!/bin/bash
echo '开始maven 构建服务'
#export BUILD_ID=dontKillMe这一句很重要，这样指定了，项目启动之后才不会被Jenkins杀掉。
echo 'export BUILD_ID=dontKillMe这一句很重要，这样指定了，项目启动之后才不会被Jenkins杀掉'
export BUILD_ID=dontKillMe
echo 'mvn clean install begin'
mvn clean install
#指定最后编译好的jar存放的位置
echo '指定最后编译好的jar存放的位置'
www_path=/home/wwwroot/jenkins
#Jenkins中编译好的jar位置
echo 'Jenkins中编译好的jar位置'
jar_path=/var/lib/jenkins/workspace/test3/target
cd  ${jar_path}
#获取Jenkins中编译好的jar名称，其中XXX为你的pom文件中的artifactId的值，这一步主要是为了根据项目版本号动态获取项目文件名
#jar_name=`ls |grep XXX-|grep -v original`
jar_name=`ls |grep test3-|grep -v original`
#jar_name=mybatis-generator-1.0-SNAPSHOT.jar
#需要注意的是，初次构建时并没有对应的pid，所以需要判断一下是否存在该文件
#获取运行编译好的进程ID，便于我们在重新部署项目的时候先杀掉以前的进程
if [ -f "/home/wwwroot/jenkins/test3.pid" ];then
 pid=$(cat /home/wwwroot/jenkins/test3.pid)
 #杀掉以前可能启动的项目进程
 kill -9 ${pid}
fi
#进入最后指定存放jar的位置
cd  ${www_path}
#先删除原来的jar文件
rm -rf ./${jar_name}
#进入指定的编译好的jar的位置
cd  ${jar_path}
#将编译好的jar复制到最后指定的位置
cp  -r ${jar_path}/${jar_name} ${www_path}
#进入最后指定存放jar的位置
cd  ${www_path}
#启动jar，指定SpringBoot的profiles为beta,后台启动
#java -jar -Dspring.profiles.active=beta ${jar_name} &
nohup java -jar ${jar_name}  > log.file  2>&1 &
#将进程ID存入到ufind-web.pid文件中
echo $! > /home/wwwroot/jenkins/test3.pid