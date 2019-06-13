#!/usr/bin/python
# -*- coding: utf-8 -*-
import json
import subprocess
#import commands
import os
import sys
import os.path
import io

packageJson = "./%s/package.json"
cachePackageJson = ".git/hooks/.versionconfig-%s"
def versiontuple(v):
    return tuple(map(int, (v.split("."))))

def _cmd2(cmd):
    '''window下会报错，改用第二个方法Popen'''
    (status, output) = commands.getstatusoutput(cmd)
    return output

def _cmd(cmd):
    proc = subprocess.Popen(cmd, stdout=subprocess.PIPE)
    output, _ = proc.communicate()
    return output #(proc.returncode, output)

def _run(cmd):
    proc = subprocess.Popen(cmd, shell=True, stdout=subprocess.PIPE)
    while True:
        print(proc.stdout.readline() )
        if proc.poll() != None:
            break

def writeVersion(filePath, version):
    file_object = open(filePath, 'w')
    file_object.write(json.dumps({"version":version}))
    file_object.close()

if __name__ == '__main__':
    project=''

    if len(sys.argv) == 2:
        project = sys.argv[1]
    elif len(sys.argv) == 3:
        project = sys.argv[1]

    if (os.path.exists(project)):
        packageJson = packageJson %(project)
    else:
        packageJson = "package.json"
    json_data = json.loads(io.open(packageJson,'r',encoding='utf-8').read())
    version = json_data["version"]
    c_version = "0.0.0"

    print("installed version: %s" %(version))
    filePath = cachePackageJson %(project)
    if not os.path.exists(filePath):
        writeVersion(filePath, version)
    else:
        c_json_data = json.loads(io.open(filePath,'r',encoding='utf-8').read())
        c_version = c_json_data["version"]

    if versiontuple(c_version) < versiontuple(version):
        writeVersion(filePath, version)
        cmdScript = 'npm install'
        if (os.path.exists(project)):
            cmdScript = "cd %s && npm install" %(project)
        print(cmdScript)
        _run(cmdScript)
