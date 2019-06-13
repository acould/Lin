#!/bin/sh

BASE=`pwd`
cmd=$1
value=$2

logfile=$BASE/bin/node.`date +%s`.log

file=$BASE/bin/config/project.conf

# if config file exists
if test -e $file; then
    count=`awk 'END{print NR}' $file `
    for ((i=1;i<=$count;i++));do
        fileValue=`cat $file | sed -n ''$i'p' | sed 's/^\s*//;s/\s*$//'`
        projectKey=$(echo $fileValue |awk -F'=' '{print $1}')
        projectName=$(echo $fileValue |awk -F'=' '{print $2}')
        if [ "$projectName" == "$value" ]; then
            value=$projectKey
        fi
    done
fi

#package check
python $BASE/bin/precheck.py $value | tee $logfile

# if subfolder exists
if test -e $value; then
    cd $value
fi

echo `pwd`

#2调用nodejs 并保存临时文件
#node build/build.js $cmd $value --env=production | tee $logfile
npm run build | tee $logfile

#检查nodejs的返回结果
if grep -i "exec error:\|Errors" $logfile
then
echo -e "\033[31m Node task [$cmd $value] fail! Please check and try again! \033[0m"
else
echo -e "\033[32m Node task [$cmd $value] success! \033[0m"
#  3 scp

fi

#删除临时文件
rm $logfile

