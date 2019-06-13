#!/usr/bin/python
# coding: utf-8
from __future__ import unicode_literals

__author__ = 'liuchao'

import sys
import os
import re
import glob
import ConfigParser
import StringIO
import xml.etree.ElementTree as ET
import codecs

def usage():
    usage_cn()

def usage_en():
    print "\
Description: Docker helper script to manage dubbo dependencies.\n\
Note: \n\
   1. Need to put aicai-project, cps-project and common-project in the same directory !!!!\n\
   2. Valid output starts with \"-\"\n\
Usage: python docker-helper.py [cmd] componentA [componentB...]\n\
例子：\n\
   1. python docker-helper.py consume-facades aicai-services-jnh cps-web-site\n\
      => Print dubbo facades consumed by the specified two components\n\
   2. python docker-helper.py update-version .tliu aicai-services-account cps-services-active\n\
      => Set dubbo version of the specified two components (prepended with \".tliu\")\n\
\n\
cmd:\n\
   provide-vkeys: Print version keys of dubbo facades provided by component A/B\n\
   consume-vkeys: Print version keys of dubbo facades consumed by component A/B\n\
   provide-facades: Print dubbo facades provided by component A/B\n\
   consume-facades: Print dubbo facades consumed by component A/B\n\
   consumers: Print components which depend on one of component A/B\n\
   providers: Print components which are depended by one of component A/B\n\
   set-version version componentA [componentB...]:\n\
          Set dubbo version of component A/B, reset versions of other components to default.\n\
          Also return consumers of all updated components (version set or reset) which may need redeployment\n\
   set-version:\n\
          Reset versions of all components to default\n\
          Also return consumers of all updated components (version set or reset) which may need redeployment\n\
   update-version version componentA [componentB...]:\n\
          Set dubbo version of component A/B, do not change versions of any other components\n\
          Also return consumers of component A/B which may need redeployment\n\
\n\
component: something like cps-services-active aicai-web-backend\n"

def usage_cn():
    print "\
说明：管理dubbo依赖以及版本号的脚本\n\
注意：\n\
   1. 需要将项目aicai-project, cps-project和common-project的代码放置在同一个父目录之下！！！\n\
   2. 打印的有效输出以\"-\"开头\n\
用法：python docker-helper.py [命令] 模块A [模块B...]\n\
例子：\n\
   1. python docker-helper.py consume-facades aicai-services-jnh cps-web-site\n\
      => 打印指定的两个模块所依赖的所有dubbo facade的信息\n\
   2. python docker-helper.py update-version .tliu aicai-services-account cps-services-active\n\
      => 设置指定两个模块的dubbo版本（添加\".tliu\"的后缀）\n\
\n\
cmd:\n\
   provide-vkeys: 打印模块所提供的dubbo facade的版本在配置文件中对应的键名\n\
   consume-vkeys: 打印模块所依赖的dubbo facade的版本在配置文件中对应的键名\n\
   provide-facades: 打印模块所提供的dubbo facade的信息\n\
   consume-facades: 打印模块所依赖的dubbo facade的信息\n\
   consumers: 打印依赖此模块的所有其他模块名称\n\
   providers: 打印此模块依赖的所有其他模块名称\n\
   set-version 版本号 模块A [模块B...]:\n\
          设置模块A/B的dubbo版本号，并且重置指定模块之外的其他模块的dubbo版本号\n\
          同时返回版本被更新的模块（设置或者重置）的名称列表，列表里的模块需要重新发布以体现dubbo版本的更改\n\
   set-version:\n\
          重置所有模块的dubbo版本号\n\
          同时返回版本被更新的模块（设置或者重置）的名称列表，列表里的模块需要重新发布以体现dubbo版本的更改\n\
   update-version 版本号 模块A [模块B...]:\n\
          设置模块A/B的dubbo版本号，指定模块之外的其他模块的dubbo版本号不做变动\n\
          同时返回依赖指定模块的其他模块的名称列表，列表里的模块需要重新发布以体现dubbo版本的更改\n\
\n\
模块: 如 cps-services-active aicai-web-backend 等等\n"

# read properties file and return dict
def read_properties_file(file_path):
    try:
        with codecs.open(file_path, encoding="utf-8") as f:
            config = StringIO.StringIO()
            config.write('[dummy_section]\n')
            config.write(f.read().replace('%', '%%'))
            config.seek(0, os.SEEK_SET)

            cp = ConfigParser.SafeConfigParser()
            cp.optionxform = str    # case sensitive
            cp.readfp(config)

            return dict(cp.items('dummy_section'))
    except:
        print "WARN: file not found, will return empty set: ", file_path
        return dict()

# locate workspace dir
def _locate_workspace_dir(project='*'):
    curr_dir = os.path.dirname(os.path.abspath(__file__))
    ws_dir = os.path.join(curr_dir, '..', '..', '..', project + '-project')

    return ws_dir

def _parse_service_name(service=''):
    detect = re.match('(.*?)-(.*?)-.*', service)
    project = None
    type = None
    if detect:
        project = detect.group(1)
        type = detect.group(2)

    return project, type

# locate dubbo conf file base dir
# service: something like cps-services-active
# return (dir, project, type)
def _locate_dubbo_conf_dir(service):
    dir = None
    type = None # service or web
    project = None #cps or aicai or common

    # workspace dir
    ws_dir = _locate_workspace_dir()
    project, type = _parse_service_name(service)

    if type == 'services':
        dir = os.path.join(ws_dir, project + '-publish-project', project + '-publish-dubbo', 'src', 'main', 'resources', service)
    elif type == 'web':
        dir = os.path.join(ws_dir, project + '-web-project', service, 'src', 'main', 'resources')
    elif type == 'task':
        dir = os.path.join(ws_dir, project + '-task-project', service, 'src', 'main', 'resources')
    return dir, project, type

# return list of provider files in absolute paths
def _locate_dubbo_provider_files(dir, type):
    if dir is None:
        return None

    # no provider for web projects
    if type != 'services':
        return None

    return glob.glob(os.path.join(dir, '*-dubbo-provider-*.xml'))

# return list of consumer files in absolute paths
def _locate_dubbo_consumer_files(dir, type):
    if dir is None:
        return None

    return glob.glob(os.path.join(dir, '*-dubbo-consumer*.xml'))

# xml namespace
ns = {'dubbo':'http://code.alibabatech.com/schema/dubbo'}
# dubbo.properties
#dubbo_conf = {'cps': read_properties_file(os.path.join(_locate_workspace_dir('cps'), 'filter', 'dubbo.properties')),
#              'aicai': read_properties_file(os.path.join(_locate_workspace_dir('aicai'), 'filter', 'dubbo.properties')),
#              'common': read_properties_file(os.path.join(_locate_workspace_dir('common'), 'filter', 'dubbo.properties'))}

#dubbo_overwrite_conf = {'cps': dubbo_conf['cps'].copy(),
#                        'aicai': dubbo_conf['aicai'].copy(),
#                        'common': dubbo_conf['common'].copy()}

#dubbo_overwrite_conf['cps'].update(read_properties_file(os.path.join(_locate_workspace_dir('cps'), 'filter', 'dubbo-docker.properties')))
#dubbo_overwrite_conf['aicai'].update(read_properties_file(os.path.join(_locate_workspace_dir('aicai'), 'filter', 'dubbo-docker.properties')))
#dubbo_overwrite_conf['common'].update(read_properties_file(os.path.join(_locate_workspace_dir('common'), 'filter', 'dubbo-docker.properties')))

#print len(dubbo_conf['cps']), len(dubbo_conf['cps'].copy())

# parse xml and return list of version keys
def _find_dubbo_version_keys(filepath, xml_node):
    versions = []
    tree = ET.parse(filepath)
    doc = tree.getroot()
    for e in doc.findall('dubbo:' + xml_node, ns):
        version = e.attrib.get('version')
        if version:
            expected = re.match('\${(.*)}', version)
            if expected:
                version = expected.group(1)
            versions.append(version)
    return versions

# parse xml and return tuple
#  (dubbo_interface, version_key_original, version_key, default_version, actual_version)
# list of dubbo services/references
def _find_dubbo_nodes(project, filepath, xml_node):
    # print filepath
    versions = []
    tree = ET.parse(filepath)
    doc = tree.getroot()
    for e in doc.findall('dubbo:' + xml_node, ns):
        version_key = e.attrib.get('version')
        version = ''
        actual_version = ''
        vkey = ''
        interface = e.attrib.get('interface')
        try:
            if version_key:
                expected = re.match('(.*)\${(.*)}(.*)', version_key)
                if expected:
                    vkey = expected.group(2)
                    # print vkey, dubbo_conf.get(vkey), dubbo_conf[vkey]
                    #version = expected.group(1) + dubbo_conf[project].get(vkey) + expected.group(3)
                    #actual_version = expected.group(1) + dubbo_overwrite_conf[project].get(vkey) + expected.group(3)

                    version='6.6.6'
                    actual_version=''
                else:
                    version = version_key
                    vkey = ''
                    actual_version = ''
                versions.append((interface, version_key, vkey, version, actual_version))
        except:
            print "WARN: failed to find dubbo interface: ", interface
    # print versions
    return versions

# find dubbo consumers for a certain service component
def _find_dubbo_consumers(service):
    #print service
    filter_path, xx, yy = _locate_dubbo_conf_dir('*-services-*')
    #print filter_path
    possible_consumers = glob.glob(os.path.join(filter_path, '*-dubbo-consumer*.xml'))
    filter_path, xx, yy = _locate_dubbo_conf_dir('*-web-*')
    #print filter_path
    possible_consumers.extend(glob.glob(os.path.join(filter_path, '*-dubbo-consumer*.xml')))
    filter_path, xx, yy = _locate_dubbo_conf_dir('*-task-*')
    possible_consumers.extend(glob.glob(os.path.join(filter_path, '*-dubbo-consumer*.xml')))
    #print filter_path
    #print possible_consumers

    provide_interfaces = [x[0] for x in find_dubbo_provide_facades(service)]
    # print provide_vkeys

    consumers = set()
    for pconsumer in possible_consumers:
        pconsumer_component = _get_service_name(pconsumer)
        consume_interfaces = [x[0] for x in find_dubbo_consume_facades(pconsumer_component)]
        if (set(provide_interfaces) & set(consume_interfaces)):
            consumers.add(pconsumer_component)

    #print consumers
    return consumers

# find dubbo providers consumed by a certain service component
def _find_dubbo_consumed_by(service):
    #print service
    #ws_dir, project, type = _locate_workspace_dir(service)
    filter_path, xx, yy = _locate_dubbo_conf_dir('*-services-*')
    #print filter_path
    possible_providers = _locate_dubbo_provider_files(filter_path, 'services')
    #print possible_providers
    consume_interfaces = [x[0] for x in find_dubbo_consume_facades(service)]
    #print consume_interfaces
    providers = set()
    for pprovider in possible_providers:
        pprovider_component = _get_service_name(pprovider)
        #print pprovider_component
        provider_interfaces = [x[0] for x in find_dubbo_provide_facades(pprovider_component)]
        #print consume_interfaces
        #print provider_interfaces
        if (set(consume_interfaces) & set(provider_interfaces)):
            providers.add(pprovider_component)

    #print providers
    return providers

def _get_service_name(path):
    dir = os.path.dirname(path)
    base = os.path.basename(dir)
    while len(dir) > 3 and not re.match('(cps|aicai|common|laimi|duns)-.*-.*', base):
        # print dir
        dir = os.path.dirname(dir)
        base = os.path.basename(dir)
    return base

####################################

# find providing dubbo facade version keys
def find_dubbo_provide_vkeys(*services):
    versions = []
    for service in services:
        # print service
        (dir, project, type) = _locate_dubbo_conf_dir(service)
        # print dir, project, type

        files = None
        xml_node = None
        files = _locate_dubbo_provider_files(dir, type)

        if files:
            for f in files:
                versions.extend(_find_dubbo_version_keys(f, 'service'))
    return versions

# find consuming dubbo facade version keys
def find_dubbo_consume_vkeys(*services):
    versions = []
    for service in services:
        # print service
        (dir, project, type) = _locate_dubbo_conf_dir(service)
        #print dir, project, type

        files = None
        xml_node = None
        files = _locate_dubbo_consumer_files(dir, type)
        #print files
        if files:
            for f in files:
                versions.extend(_find_dubbo_version_keys(f, 'reference'))
    return versions

# find providing dubbo facades
def find_dubbo_provide_facades(*services):
    versions = []
    for service in services:
        # print service
        (dir, project, type) = _locate_dubbo_conf_dir(service)
        # print dir, project, type

        files = None
        xml_node = None
        files = _locate_dubbo_provider_files(dir, type)

        if files:
            for f in files:
                versions.extend(_find_dubbo_nodes(project, f, 'service'))
    return versions

# find consuming dubbo facades
def find_dubbo_consume_facades(*services):
    versions = []
    for service in services:
        # print service
        (dir, project, type) = _locate_dubbo_conf_dir(service)
        #print dir, project, type

        files = None
        xml_node = None
        files = _locate_dubbo_consumer_files(dir, type)
        #print files
        if files:
            for f in files:
                versions.extend(_find_dubbo_nodes(project, f, 'reference'))
    return versions

# find dubbo consumer components for given services list
def find_dubbo_consumers(*services):
    cset = set()
    for service in services:
        cset = cset | _find_dubbo_consumers(service)

    # TODO: remove itself or not??
    for service in services:
        try:
            cset.remove(service)
        except:
            pass

    return [x for x in cset]

# find dubbo provider components consumed by given services list
def find_dubbo_consumed_by(*services):
    cset = set()
    for service in services:
        cset = cset | _find_dubbo_consumed_by(service)

    # TODO: remove itself or not??
    for service in services:
        try:
            cset.remove(service)
        except:
            pass

    return [x for x in cset]

# append 'postfix' version to current dubbo version
# return list of consumer services that might need redeployment
def update_dubbo_version(append, version_postfix, *services):
    dir = _locate_workspace_dir()
    #overwrite_filepath = os.path.join(_locate_workspace_dir(), 'filter', 'dubbo-docker.properties')
    #files = glob.glob(overwrite_filepath)
    facades = find_dubbo_provide_facades(*services)


    overwrite_filepath = os.path.join('/tmp', 'dubbo-docker.properties')
    print "docker_dubbo_file=",overwrite_filepath
    files = glob.glob(overwrite_filepath)
    if not files:
        files.append(overwrite_filepath)

    components = set()
    for f in files:
        versions = {}

        new_components = set(services)
        old_overwrites = read_properties_file(f)
        #components = set()
        if old_overwrites.has_key('____components____'):
            components = set(old_overwrites.pop('____components____').split(','))
        components = components | new_components

        if append:
            versions.update(old_overwrites)
            new_components = components

        for facade in facades:
            versions[facade[2]] = facade[3]+version_postfix

        with(open(f, 'w')) as f:
            f.write("# DO NOT EDIT THIS FILE!!\n")
            f.write("____components____="+",".join(new_components) + "\n")
            for vk in sorted(versions):
                f.write(vk + '=' + versions[vk] +"\n")

    return find_dubbo_consumers(*[x for x in components])

if __name__ == '__main__':
    args = len(sys.argv)
    if args < 2:
        usage()
        sys.exit()
    cmd = sys.argv[1]
    services = args > 2 and sys.argv[2:] or []
    len_services = len(services)
    if len_services == 0:
        usage()

    if cmd == 'consumers':
        components = find_dubbo_consumers(*services)
        for c in components:
            print "-", c

    elif cmd == 'providers':
        components = find_dubbo_consumed_by(*services)
        for c in components:
            print "-", c

    elif cmd == 'provide-vkeys':
        versions = find_dubbo_provide_vkeys(*services)
        for v in versions:
            print "-", v

    elif cmd == 'consume-vkeys':
        versions = find_dubbo_consume_vkeys(*services)
        for v in versions:
            print "-", v

    elif cmd == 'provide-facades':
        versions = find_dubbo_provide_facades(*services)
        for v in versions:
            print "-", v

    elif cmd == 'consume-facades':
        versions = find_dubbo_consume_facades(*services)
        for v in versions:
            print "-", v

    elif cmd == 'set-version':
        version_postfix = len_services > 0 and services[0] or ''
        services = len_services > 1 and services[1:] or []
        consumers = update_dubbo_version(False, version_postfix, *services)
        for v in consumers:
            print "-", v

    elif cmd == 'update-version':
        version_postfix = len_services > 0 and services[0] or ''
        services = len_services > 1 and services[1:] or []
        consumers = update_dubbo_version(True, version_postfix, *services)
        for v in consumers:
            print "-", v